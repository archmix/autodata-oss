package autodata.pipeline.interfaces;

import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;

import java.io.Serializable;
import java.util.Arrays;

public class JdbcIOReadBuilder<T> {
  private JdbcIO.Read<T> read;

  private JdbcIOReadBuilder(JdbcConfiguration configuration) {
    this.read = JdbcIO.read();
    this.init(configuration);
  }

  static <Key extends Serializable> JdbcIOReadBuilder of(JdbcConfiguration configuration) {
    return new JdbcIOReadBuilder(configuration);
  }

  private void init(JdbcConfiguration configuration) {
    this.read = this.read
      .withOutputParallelization(false)
      .withDataSourceConfiguration(configuration.toDatasourceConfiguration());
  }

  public JdbcIO.Read<T> build() {
    return this.read;
  }

  public JdbcIOReadBuilder<T> withFetchSize(int fetchSize){
    this.read = this.read.withFetchSize(fetchSize);
    return this;
  }

  public JdbcIOReadBuilder<T> withQuery(String sqlQuery, Object... args) {
    this.read = this.read.withQuery(sqlQuery);

    if (args == null || args.length == 0) {
      return this;
    }

    this.read = this.read.withStatementPreparator(preparedStatement -> {
      int index = 1;
      for (Object value : Arrays.asList(args)) {
        preparedStatement.setObject(index++, args);
      }
    });
    return this;
  }

  JdbcIOReadBuilder<T> with(JdbcIO.RowMapper<T> rowMapper, Coder<T> coder) {
    this.read = this.read.withRowMapper(rowMapper).withCoder(coder);
    return this;
  }
}