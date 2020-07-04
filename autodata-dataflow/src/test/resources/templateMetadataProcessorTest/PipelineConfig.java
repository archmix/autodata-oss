package templateMetadataProcessorTest;

import autodata.dataflow.interfaces.TemplateMetadataSpecification;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;

@TemplateMetadataSpecification(name = "Options")
public interface PipelineConfig extends PipelineOptions {
  @Description("Ambiente de execução (LOCAL, TEST, SERVER)")
  @Default.String("LOCAL")
  String getEnvironment();
  void setEnvironment(String environment);

  @Description("ERP database host")
  @Default.String("localhost")
  String getERPDatabaseHost();
  void setERPDatabaseHost(String host);

  @Description("ERP database port")
  @Default.Integer(1521)
  Integer getERPDatabasePort();
  void setERPDatabasePort(Integer port);

  @Description("ERP database schema")
  @Default.String("Maxicon")
  String getERPDatabaseSchema();
  void setERPDatabaseSchema(String schema);

  @Description("ERP database service")
  @Default.String("xe")
  String getERPDatabaseService();
  void setERPDatabaseService(String service);

  @Description("ERP database username")
  @Default.String("data_lake")
  String getERPDatabaseUsername();
  void setERPDatabaseUsername(String username);

  @Description("ERP database password")
  @Default.String("oracle")
  String getERPDatabasePassword();
  void setERPDatabasePassword(String password);

  @Description("CRM database host")
  @Default.String("localhost")
  String getCRMDatabaseHost();
  void setCRMDatabaseHost(String host);

  @Description("CRM database port")
  @Default.Integer(1433)
  Integer getCRMDatabasePort();
  void setCRMDatabasePort(Integer port);

  @Description("CRM database username")
  @Default.String("data_lake")
  String getCRMDatabaseUsername();
  void setCRMDatabaseUsername(String username);

  @Description("CRM database password")
  @Default.String("$qlS3rver")
  String getCRMDatabasePassword();
  void setCRMDatabasePassword(String password);

  @Description("CRM database name")
  @Default.String("CRM")
  String getCRMDatabaseName();
  void setCRMDatabaseName(String databaseName);

  @Description("Site database host")
  @Default.String("localhost")
  String getSiteDatabaseHost();
  void setSiteDatabaseHost(String host);

  @Description("Site database port")
  @Default.Integer(5432)
  Integer getSiteDatabasePort();
  void setSiteDatabasePort(Integer port);

  @Description("Site database username")
  @Default.String("data_lake")
  String getSiteDatabaseUsername();
  void setSiteDatabaseUsername(String username);

  @Description("Site database password")
  @Default.String("postgre")
  String getSiteDatabasePassword();
  void setSiteDatabasePassword(String password);

  @Description("Site database name")
  @Default.String("Sites")
  String getSiteDatabaseName();
  void setSiteDatabaseName(String databaseName);
}