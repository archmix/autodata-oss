package autodata.pipeline.interfaces;

import org.apache.beam.sdk.coders.Coder;
import org.apache.beam.sdk.coders.KvCoder;
import toolbox.data.interfaces.DataObject;

import java.io.Serializable;

public class Coders {
  public static <Key extends Serializable> KvCoder<Key, DataObject> kvCoder(KeyConverter<Key> keyConverter) {
    return KvCoder.of(ObjectCoder.of(keyConverter.keyClass()), ObjectCoder.of(DataObject.class));
  }

  public static Coder<DataObject> dataObjectCoder() {
    return ObjectCoder.of(DataObject.class);
  }
}
