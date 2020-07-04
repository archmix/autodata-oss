package autodata.relational.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class IndexName {
  private final String value;

  public String value() {
    return value;
  }

  public String toString() {
    return this.value;
  }
}
