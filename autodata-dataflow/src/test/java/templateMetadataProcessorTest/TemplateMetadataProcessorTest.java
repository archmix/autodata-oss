package templateMetadataProcessorTest;

import autodata.dataflow.infra.TemplateMetadataProcessor;
import compozitor.processor.core.interfaces.CompilationBuilder;
import compozitor.processor.core.interfaces.CompileAssertion;
import compozitor.processor.core.interfaces.TestResources;
import org.junit.Test;

public class TemplateMetadataProcessorTest {
  private final TestResources resources = TestResources.create(TemplateMetadataProcessorTest.class);

  @Test
  public void givenOptionsWhenCompileThenGeneratesDataflowTemplateMetadataFile(){
    CompileAssertion compilation = CompilationBuilder.create()
      .withProcessors(new TemplateMetadataProcessor())
      .withJavaSource(
        resources.testFile("PipelineConfig.java")
      ).build();

    compilation
      .assertSuccess()
      .assertGeneratedFiles(2)
      .assertGeneratedResourceFile("options/metadata.json");
  }
}
