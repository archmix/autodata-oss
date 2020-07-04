package autodata.pipeline.interfaces;

import org.apache.beam.sdk.io.jdbc.JdbcIO;

public class JdbcIOWriteBuilder<Entity> {
  private JdbcIO.Write<Entity> write;

  private JdbcIOWriteBuilder(JdbcConfiguration configuration) {
    this.write = JdbcIO.write();
    this.write = this.write.withDataSourceConfiguration(configuration.toDatasourceConfiguration());
  }

  static JdbcIOWriteBuilder of(JdbcConfiguration configuration) {
    return new JdbcIOWriteBuilder(configuration);
  }

  public JdbcIO.Write<Entity> build() {
    return this.write;
  }

  public JdbcIOWriteBuilder withStatement(String sqlStatement) {
    this.write = this.write.withStatement(sqlStatement);
    return this;
  }

  public JdbcIOWriteBuilder withPreparedStatementSetter(JdbcIO.PreparedStatementSetter<Entity> setter) {
    this.write = this.write.withPreparedStatementSetter(setter);
    return this;
  }
}
