package autodata.relational.interfaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor(staticName = "create")
@Getter
public class ForeignKey {
  private final IndexName indexName;

  private final Column column;

  private final Rule onUpdate;

  private final Rule onDelete;

  public static enum Rule {
    NO_ACTION("importedNoAction"),
    CASCADE("importedKeyCascade"),
    SET_NULL("importedKeySetNull"),
    SET_DEFAULT("importedKeySetDefault"),
    RESTRICT("importedKeyRestrict");

    private String value;

    Rule(String value) {
      this.value = value;
    }

    public String value() {
      return value;
    }

    public static Optional<Rule> of(String code) {
      for(Rule rule : Rule.values()) {
        if(rule.value.equalsIgnoreCase(code)){
          return Optional.of(rule);
        }
      }
      return Optional.empty();
    }
  }
}
