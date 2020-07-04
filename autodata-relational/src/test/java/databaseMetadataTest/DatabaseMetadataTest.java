package databaseMetadataTest;

import autodata.relational.infra.DatabaseMetadata;
import autodata.relational.interfaces.DatabaseCatalog;
import autodata.relational.interfaces.DatabaseSchema;
import autodata.relational.interfaces.Table;
import legolas.async.api.interfaces.Promise;
import legolas.config.api.interfaces.Configuration;
import legolas.oracle.interfaces.OracleEntry;
import legolas.oracle.interfaces.OracleServiceId;
import legolas.runtime.core.interfaces.RunningEnvironment;
import legolas.runtime.core.interfaces.RuntimeEnvironment;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

public class DatabaseMetadataTest {
  private static RunningEnvironment environment;

  @BeforeClass
  public static void create() {
    Promise<RunningEnvironment> promise = RuntimeEnvironment.TEST.start(Executors.newSingleThreadExecutor());
    environment = promise.get();
  }

  @Test
  public void givenOracleWithTablesWhenGetTablesThenReturns() {
    Configuration oracleConfig = environment.get(OracleServiceId.INSTANCE).get().configuration();

    String url = oracleConfig.getString(OracleEntry.URL).get();
    String driver = oracleConfig.getString(OracleEntry.DRIVER).get();
    String username = oracleConfig.getString(OracleEntry.USERNAME).get();
    String password = oracleConfig.getString(OracleEntry.PASSWORD).get();

    Connection connection = openConnection(url, driver, username, password);
    Map<String, Table> tables = DatabaseMetadata.create(connection).getTables(DatabaseCatalog.empty(), DatabaseSchema.create("BRAZ"));
    Assert.assertEquals(3, tables.size());
  }

  private static Connection openConnection(String url, String driver, String username, String password){
    try {
      Class.forName(driver);
      return DriverManager.getConnection(url, username, password);
    } catch (Exception e) {
      throw new IllegalStateException(e);
    }
  }
}
