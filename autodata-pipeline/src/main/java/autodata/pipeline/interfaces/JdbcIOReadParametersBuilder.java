package autodata.pipeline.interfaces;

import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.io.jdbc.JdbcIO.RowMapper;
import org.apache.beam.sdk.transforms.SerializableFunction;

public class JdbcIOReadParametersBuilder<P, T> {
  private JdbcIO.ReadAll<P, T> read;

  private JdbcIOReadParametersBuilder(JdbcConfiguration configuration) {
    this.read = JdbcIO.readAll();
    this.init(configuration);
  }

  static JdbcIOReadParametersBuilder of(JdbcConfiguration configuration) {
    return new JdbcIOReadParametersBuilder(configuration);
  }

  private void init(JdbcConfiguration configuration) {
    this.read = this.read
      .withOutputParallelization(false)
      .withDataSourceConfiguration(configuration.toDatasourceConfiguration());
  }

  public JdbcIO.ReadAll<P, T> build() {
    return this.read;
  }

  public JdbcIOReadParametersBuilder<P, T> withFetchSize(int fetchSize){
    this.read = this.read.withFetchSize(fetchSize);
    return this;
  }

  public JdbcIOReadParametersBuilder<P, T> withQuery(String sqlQuery, SerializableFunction<P, Iterable<?>> toParameters) {
    this.read = this.read
      .withQuery(sqlQuery)
      .withParameterSetter((element, preparedStatement) -> {
        int index = 1;
        for (Object value : toParameters.apply(element)) {
          preparedStatement.setObject(index++, value);
        }
      });

    return this;
  }

  JdbcIOReadParametersBuilder<P, T> with(RowMapper<T> rowMapper, Coder<T> coder) {
    this.read = this.read.withRowMapper(rowMapper).withCoder(coder);
    return this;
  }
}