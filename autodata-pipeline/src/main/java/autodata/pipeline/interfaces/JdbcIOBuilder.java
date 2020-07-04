package autodata.pipeline.interfaces;

import org.apache.beam.sdk.values.KV;
import toolbox.data.interfaces.DataObject;

import java.io.Serializable;

public class JdbcIOBuilder {

  public static JdbcIOReadBuilder<DataObject> read(JdbcConfiguration configuration) {
    return JdbcIOReadBuilder.of(configuration).with(DataObjectMapper.of(), ObjectCoder.of(DataObject.class));
  }

  public static <Key extends Serializable> JdbcIOReadBuilder<KV<Key, DataObject>> read(JdbcConfiguration configuration, KeyConverter<Key> keyConverter) {
    return JdbcIOReadBuilder.of(configuration).with(KeyedDataObjectMapper.of(keyConverter), Coders.kvCoder(keyConverter));
  }

  public static <P> JdbcIOReadParametersBuilder<P, DataObject> readWithParameter(JdbcConfiguration configuration) {
    return JdbcIOReadParametersBuilder.<DataObject>of(configuration).with(DataObjectMapper.of(), Coders.dataObjectCoder());
  }

  public static <P, Key extends Serializable> JdbcIOReadParametersBuilder<P, KV<Key, DataObject>> readWithParameter(JdbcConfiguration configuration, KeyConverter<Key> keyConverter) {
    return JdbcIOReadParametersBuilder.<KV<Key, DataObject>>of(configuration).with(KeyedDataObjectMapper.of(keyConverter), Coders.kvCoder(keyConverter));
  }

  public static <Entity> JdbcIOWriteBuilder<Entity> writer(JdbcConfiguration configuration) {
    return JdbcIOWriteBuilder.of(configuration);
  }
}
