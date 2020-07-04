package autodata.relational.interfaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor(staticName = "create")
@Getter
public class Table {
  private final DatabaseCatalog catalog;

  private final DatabaseSchema schema;

  private final String name;

  private final Type type;

  private final Map<String, Column> columns = new HashMap<>();

  public void add(Column column) {
    this.columns.put(column.getName(), column);
  }

  public Optional<Column> getColumn(String name) {
    return Optional.ofNullable(this.columns.get(name));
  }

  public static enum Type {
    TABLE("TABLE"),
    VIEW("VIEW"),
    SYSTEM_TABLE("SYSTEM TABLE"),
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    LOCAL_TEMPORARY("LOCAL TEMPORARY"),
    ALIAS("ALIAS"),
    SYNONYM("SYNONYM");

    private String value;

    Type(String value) {
      this.value = value;
    }

    public String value() {
      return this.value;
    }

    public static Optional<Type> of(String code) {
      for (Type type : Type.values()) {
        if (code.equalsIgnoreCase(type.value)) {
          return Optional.of(type);
        }
      }
      return Optional.empty();
    }
  }
}