package autodata.dataflow.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateMetadataSpecification {
  String name();

  String description() default "Configure the following parameters";

  String NAME_FIELD = "name";

  String DESCRIPTION_FIELD = "description";
}
