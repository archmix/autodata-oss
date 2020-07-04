package autodata.dataflow.infra;

import autodata.dataflow.interfaces.TemplateMetadataSpecification;
import com.google.common.collect.Sets;
import compozitor.processor.core.interfaces.AnnotationProcessor;
import compozitor.processor.core.interfaces.JavaResource;
import compozitor.processor.core.interfaces.JavaResources;
import compozitor.processor.core.interfaces.Processor;
import compozitor.processor.core.interfaces.ResourceName;
import compozitor.processor.core.interfaces.TypeModel;
import toolbox.json.interfaces.Json;

import javax.tools.FileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;
import java.util.Set;

@Processor
public class TemplateMetadataProcessor extends AnnotationProcessor {
  private Optional<TemplateMetadata> metadata;

  @Override
  public Set<String> getSupportedAnnotationTypes() {
    return Sets.newHashSet(TemplateMetadataSpecification.class.getName());
  }

  @Override
  protected void process(TypeModel typeModel) {
    this.metadata = TemplateMetadataFactory.create(typeModel);
    this.context.info("Found metadata? {0}", this.metadata.isPresent());
  }

  @Override
  protected void postProcess() {
    this.metadata.ifPresent(this::write);
  }

  private void write(TemplateMetadata metadata) {
    try {
      FileObject jsonFile = file(metadata);
      String json = Json.toJson(metadata).get();

      try(Writer writer = jsonFile.openWriter()) {
        writer.write(json);
      }
    } catch (IOException e) {
      this.context.warning("An exception occurred. Cause was: {0}", e.getMessage());
    }
  }

  private FileObject file(TemplateMetadata metadata) {
    ResourceName resourceName = ResourceName.create("options/metadata.json");
    JavaResource javaResource = JavaResources.create(resourceName);
    return this.context.getJavaFiles().resourceFile(javaResource);
  }
}
