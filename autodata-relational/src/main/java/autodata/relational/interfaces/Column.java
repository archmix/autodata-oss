package autodata.relational.interfaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@RequiredArgsConstructor(staticName = "create")
public class Column {
  private final Table table;

  private final String name;

  private final Type type;

  private final Long size;

  private final Long decimalDigits;

  private final Boolean autoIncrement;

  private final Boolean generated;

  private final Map<ConstraintType, Object> constraints = new HashMap<>();

  public void add(ConstraintType constraintType, Object value){
    this.constraints.put(constraintType, value);
  }

  public static enum Type {
    BINARY_DOUBLE(101),
    BINARY_FLOAT(100),
    BFILE(-13),
    BLOB(2004),
    CHAR(1),
    CLOB(2005),
    COLLECTION(2003),
    DATE(93),
    FLOAT(6),
    LONG(-1),
    LONG_RAW(-4),
    NCHAR(-15),
    NCLOB(2011),
    NUMBER(2),
    NVARCHAR2(-9),
    OBJECT(2002),
    OPAQUE_XMLTYPE(2009),
    RAW(-3),
    REF(2006),
    ROWID(-8),
    SQLXML(2009),
    UROWID(-8),
    VARCHAR2(12),
    VARRAY(2003),
    XMLTYPE(2009);

    private int value;

    Type(int value) {
      this.value = value;
    }

    public static Optional<Type> of(int code){
      for(Type type : Type.values()) {
        if(type.value == code){
          return Optional.of(type);
        }
      }
      return Optional.empty();
    }

    public int value() {
      return this.value;
    }
  }
}
