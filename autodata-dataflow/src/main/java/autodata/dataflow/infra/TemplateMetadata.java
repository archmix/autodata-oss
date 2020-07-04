package autodata.dataflow.infra;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class TemplateMetadata {
  private final String name;
  private final String description;
  private final List<Parameter> parameters = new ArrayList<>();

  public void add(Parameter parameter) {
    this.parameters.add(parameter);
  }

  public void add(Collection<Parameter> parameters){
    this.parameters.addAll(parameters);
  }

  @RequiredArgsConstructor(staticName = "create")
  @Getter
  public static class Parameter {
    private final String name;
    private final String label;
    private final String help_text;
    private final Boolean is_optional;
    private final List<String> regexes = new ArrayList<>();

    public void add(String regex){
      this.regexes.add(regex);
    }
  }
}