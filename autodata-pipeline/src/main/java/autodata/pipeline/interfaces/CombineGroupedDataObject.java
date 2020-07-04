package autodata.pipeline.interfaces;

import lombok.NoArgsConstructor;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.KV;
import toolbox.data.interfaces.DataObject;

@NoArgsConstructor(staticName = "of")
public class CombineGroupedDataObject extends SimpleFunction<KV<String, Iterable<DataObject>>, DataObject> {

  @Override
  public DataObject apply(KV<String, Iterable<DataObject>> input) {
    DataObject merged = DataObject.create();
    input.getValue().forEach(data ->{
      merged.set(data.toMap());
    });

    return merged;
  }
}
