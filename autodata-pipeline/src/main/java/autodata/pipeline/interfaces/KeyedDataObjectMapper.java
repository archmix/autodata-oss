package autodata.pipeline.interfaces;

import lombok.RequiredArgsConstructor;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import org.apache.beam.sdk.values.KV;
import toolbox.data.interfaces.DataObject;

import java.io.Serializable;
import java.sql.ResultSet;

@RequiredArgsConstructor(staticName = "of")
class KeyedDataObjectMapper<Key extends Serializable> implements JdbcIO.RowMapper<KV<Key, DataObject>> {
  private final KeyConverter<Key> converter;

  private final DataObjectMapper dataObjectMapper = DataObjectMapper.of();

  @Override
  public KV<Key, DataObject> mapRow(ResultSet resultSet) throws Exception {
    DataObject dataObject = this.dataObjectMapper.mapRow(resultSet);
    return KV.of(this.converter.toKey(dataObject), dataObject);
  }
}
