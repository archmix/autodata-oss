package autodata.relational.infra;

import autodata.relational.interfaces.Column;
import autodata.relational.interfaces.ConstraintType;
import autodata.relational.interfaces.DatabaseCatalog;
import autodata.relational.interfaces.DatabaseSchema;
import autodata.relational.interfaces.ForeignKey;
import autodata.relational.interfaces.IndexName;
import autodata.relational.interfaces.Table;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static autodata.relational.interfaces.Table.Type.*;

@RequiredArgsConstructor(staticName = "create")
public class DatabaseMetadata {
  private final Logger logger = LoggerFactory.getLogger(DatabaseMetadata.class);

  private final Connection connection;

  public Map<String, Table> getTables(DatabaseCatalog dbCatalog, DatabaseSchema dbSchema) {
    Map<String, Table> tables = new HashMap<>();

    try (ResultSet resultSet = this.connection.getMetaData().getTables(dbCatalog.value(), dbSchema.value(), null, new String[]{TABLE.value()});) {
      while (resultSet.next()) {
        final DatabaseCatalog catalog = DatabaseCatalog.create(resultSet.getString("TABLE_CAT"));
        final DatabaseSchema schema = DatabaseSchema.create(resultSet.getString("TABLE_SCHEM"));
        final Table.Type type = Table.Type.of(resultSet.getString("TABLE_TYPE")).orElse(null);
        final String name = resultSet.getString("TABLE_NAME");

        final Table table = Table.create(catalog, schema, name, type);
        this.setColumns(table);
        this.setConstraints(table);
        this.setPrimaryKeys(table);
        tables.put(table.getName(), table);
      }
    } catch (SQLException e) {
      logger.error("Could not retrieve data", e);
    }

    this.setForeignKeys(tables);

    return tables;
  }

  private void setColumns(Table table) {
    Map<String, Column> columns = new HashMap<>();

    try (ResultSet resultSet = this.connection.getMetaData().getColumns(table.getCatalog().value(), table.getSchema().value(), table.getName(), null);) {
      while (resultSet.next()) {
        final Column column = Column.create(
          table,
          resultSet.getString("COLUMN_NAME"),
          Column.Type.of(resultSet.getInt("DATA_TYPE")).orElse(null),
          resultSet.getLong("COLUMN_SIZE"),
          resultSet.getLong("DECIMAL_DIGITS"),
          "YES".equalsIgnoreCase(resultSet.getString("IS_AUTOINCREMENT")),
          "YES".equalsIgnoreCase(resultSet.getString("IS_GENERATEDCOLUMN"))
        );

        column.add(ConstraintType.NOT_NULL, resultSet.getInt("NULLABLE") == 1);
        column.add(ConstraintType.DEFAULT, resultSet.getString("COLUMN_DEF"));

        table.add(column);
      }
    } catch (SQLException e) {
      logger.error("Could not retrieve data", e);
    }
  }

  private void setConstraints(Table table) {
    try (ResultSet resultSet = this.connection.getMetaData().getIndexInfo(table.getCatalog().value(), table.getSchema().value(), table.getName(), false, false);) {
      while (resultSet.next()) {
        final String columnName = resultSet.getString("COLUMN_NAME");
        final String indexName = resultSet.getString("INDEX_NAME");
        final String check = resultSet.getString("FILTER_CONDITION");
        final Boolean unique = !resultSet.getBoolean("NON_UNIQUE");

        ConstraintType type = ConstraintType.INDEX;
        if(unique) {
          type = ConstraintType.UNIQUE;
        } else if (check != null){
          type = ConstraintType.CHECK;
        }

        final ConstraintType constraintType = type;
        table.getColumn(columnName).ifPresent(column -> {
          column.add(constraintType, IndexName.create(indexName));
        });
      }
    } catch (SQLException e) {
      logger.error("Could not retrieve data", e);
    }
  }

  private void setPrimaryKeys(Table table) {
    try (ResultSet resultSet = this.connection.getMetaData().getPrimaryKeys(table.getCatalog().value(), table.getSchema().value(), table.getName());) {
      while (resultSet.next()) {
        final String columnName = resultSet.getString("COLUMN_NAME");
        final IndexName indexName = IndexName.create(resultSet.getString("PK_NAME"));

        table.getColumn(columnName).ifPresent(column -> {
          column.add(ConstraintType.PRIMARY_KEY, indexName);
        });
      }
    } catch (SQLException e) {
      logger.error("Could not retrieve data", e);
    }
  }

  private void setForeignKeys(Map<String, Table> tables) {
    tables.values().forEach(table ->{
      this.setForeignKeys(tables, table);
    });
  }

  private void setForeignKeys(Map<String, Table> tables, Table table) {
    try (ResultSet resultSet = this.connection.getMetaData().getImportedKeys(table.getCatalog().value(), table.getSchema().value(), table.getName());) {
      while (resultSet.next()) {
        final String fkColumnName = resultSet.getString("FKCOLUMN_NAME");
        final String pkColumnName = resultSet.getString("PKCOLUMN_NAME");
        final IndexName fkIndexName = IndexName.create(resultSet.getString("FK_NAME"));
        final ForeignKey.Rule onUpdate = ForeignKey.Rule.of(resultSet.getString("UPDATE_RULE")).orElse(null);
        final ForeignKey.Rule onDelete = ForeignKey.Rule.of(resultSet.getString("DELETE_RULE")).orElse(null);

        final Table pktable = tables.get(resultSet.getString("PKTABLE_NAME"));

        final Column pkColumn = pktable.getColumn(pkColumnName).get();

        table.getColumn(fkColumnName).ifPresent(fkColumn -> {
          ForeignKey foreignKey = ForeignKey.create(fkIndexName, pkColumn, onUpdate, onDelete);
          fkColumn.add(ConstraintType.FOREIGN_KEY, fkIndexName);
        });
      }
    } catch (SQLException e) {
      logger.error("Could not retrieve data", e);
    }
  }
}
