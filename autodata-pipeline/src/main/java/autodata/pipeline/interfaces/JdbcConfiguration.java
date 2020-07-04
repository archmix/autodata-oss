package autodata.pipeline.interfaces;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.beam.sdk.io.jdbc.JdbcIO.DataSourceConfiguration;

@RequiredArgsConstructor(staticName = "create")
@Getter
public class JdbcConfiguration {
  private final String driver;
  private final String url;
  private final String username;
  private final String password;

  public DataSourceConfiguration toDatasourceConfiguration() {
    return DataSourceConfiguration.create(this.driver, this.url).withUsername(this.username).withPassword(this.password);
  }
}