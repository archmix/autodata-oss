package autodata.dataflow.infra;

import autodata.dataflow.infra.TemplateMetadata.Parameter;
import autodata.dataflow.interfaces.TemplateMetadataSpecification;
import com.google.common.collect.Maps;
import compozitor.processor.core.interfaces.AnnotationModel;
import compozitor.processor.core.interfaces.MethodModel;
import compozitor.processor.core.interfaces.TypeModel;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.Validation;

import java.util.Map;
import java.util.Optional;

public class TemplateMetadataFactory {
  public static Optional<TemplateMetadata> create(TypeModel typeModel) {
    Optional<AnnotationModel> annotation = typeModel.getAnnotations().getBy(TemplateMetadataSpecification.class);
    if(!annotation.isPresent()) {
      return Optional.empty();
    }

    AnnotationModel specification = annotation.get();
    String name = specification.value(TemplateMetadataSpecification.NAME_FIELD);
    String description = specification.value(TemplateMetadataSpecification.DESCRIPTION_FIELD);

    TemplateMetadata metadata = TemplateMetadata.create(name, description);
    Map<String, Parameter> parameters = Maps.newHashMap();

    for (MethodModel methodModel : typeModel.getMethods()) {
      Parameter parameter = toParameter(methodModel);
      Parameter previous = parameters.get(parameter.getName());
      if(previous == null || previous.getHelp_text() != null) {
        parameters.put(parameter.getName(), parameter);
        continue;
      }
    }

    metadata.add(parameters.values());

    return Optional.of(metadata);
  }

  private static Parameter toParameter(MethodModel methodModel) {
    String label = methodModel.getName().replace("set", "").replace("get", "");
    String description = null;

    AnnotationModel descriptionAnnotation = methodModel.getAnnotations().getBy(Description.class).orElse(null);
    if(descriptionAnnotation != null) {
      description = descriptionAnnotation.value("value");
    }

    AnnotationModel requiredAnnotation = methodModel.getAnnotations().getBy("org.apache.beam.sdk.options.Validation.Required").orElse(null);
    Boolean is_optional = requiredAnnotation == null;

    return Parameter.create(label, label, description, is_optional);
  }
}
