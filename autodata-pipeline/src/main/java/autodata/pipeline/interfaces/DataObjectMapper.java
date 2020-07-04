package autodata.pipeline.interfaces;

import lombok.NoArgsConstructor;
import org.apache.beam.sdk.io.jdbc.JdbcIO;
import toolbox.data.interfaces.DataObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

@NoArgsConstructor(staticName = "of")
class DataObjectMapper implements JdbcIO.RowMapper<DataObject> {

  @Override
  public DataObject mapRow(ResultSet resultSet) throws Exception {
    ResultSetMetaData metaData = resultSet.getMetaData();
    Integer numberOfColumns = metaData.getColumnCount();

    DataObject dataObject = DataObject.create();
    for (int index = 1; index <= numberOfColumns; index++) {
      String property = metaData.getColumnName(index);
      Object value = resultSet.getObject(index);
      dataObject.set(property, value);
    }

    return dataObject;
  }
}
