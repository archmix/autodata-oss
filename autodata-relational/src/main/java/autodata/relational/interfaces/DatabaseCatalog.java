package autodata.relational.interfaces;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(staticName = "create")
public class DatabaseCatalog {
  private final String value;

  public static DatabaseCatalog empty(){
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
