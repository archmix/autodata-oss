package autodata.pipeline.interfaces;

import toolbox.data.interfaces.DataObject;

import java.io.Serializable;

public interface KeyConverter<Key extends Serializable> extends Serializable {
  Key toKey(DataObject dataObject);

  Class<Key> keyClass();
}
