package autodata.relational.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class DatabaseSchema {
  private final String value;

  public static DatabaseSchema empty() {
    return create(null);
  }

  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value;
  }
}
