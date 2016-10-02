package com.pseg.spm.config;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.ResourceBundle;

public class DBConfig {

	public enum TargetDB {
		Staging_Prod, Staging_Dev, HSQL
	}

	private static final HashMap<TargetDB, DBConfig> dbConfigMap = new HashMap<TargetDB, DBConfig>();
	static {
		dbConfigMap.put(TargetDB.Staging_Prod, new DBConfig(ResourceBundle.getBundle(Staging_Prod.class.getName())));
		dbConfigMap.put(TargetDB.Staging_Dev, new DBConfig(ResourceBundle.getBundle(Staging_Dev.class.getName())));
		dbConfigMap.put(TargetDB.HSQL, new DBConfig(ResourceBundle.getBundle(HSQL.class.getName())));
	}

	public static DBConfig getConfig(TargetDB db) {
		return dbConfigMap.get(db);
	}

	public enum ConnectionType {
		JDBC, DataSource
	}

	public enum DatasourceName {
		STAGING_PROD, STAGING_QA, STAGING_DEV
	}

	public enum DatasourceHost {
		LOCAL, REMOTE
	}

	protected ConnectionType connectionType;
	protected DatasourceName datasourceName;
	protected DatasourceHost datasourceHost;
	protected String dbURL;
	protected String dbDriver;
	protected String dbUserName;
	protected String dbPassword;
	protected String webServerHost;
	protected String webServerPort;

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public DatasourceName getDatasourceName() {
		return datasourceName;
	}

	public DatasourceHost getDatasourceHost() {
		return datasourceHost;
	}

	public String getDBURL() {
		return dbURL;
	}

	public String getDBDriver() {
		return dbDriver;
	}

	public String getDBUserName() {
		return dbUserName;
	}

	public String getDBPassword() {
		return dbPassword;
	}

	public String getWebServerHost() {
		return webServerHost;
	}

	public String getWebServerPort() {
		return webServerPort;
	}

	private DBConfig(ResourceBundle bundle) {
		connectionType = ConnectionType.valueOf(bundle.getString("connectionType"));
		if (ConnectionType.DataSource == connectionType) {
			datasourceName = DatasourceName.valueOf(bundle.getString("datasourceName"));
			datasourceHost = DatasourceHost.valueOf(bundle.getString("datasourceHost"));
			if (DatasourceHost.REMOTE == datasourceHost) {
				webServerHost = bundle.getString("webServerHost");
				webServerPort = bundle.getString("webServerPort");
			}
		} else if (ConnectionType.JDBC == connectionType) {
			dbURL = bundle.getString("dbURL");
			dbDriver = bundle.getString("dbDriver");
			dbUserName = bundle.getString("dbUserName");
			dbPassword = bundle.getString("dbPassword");
		}
	}

	public void printConfig(PrintWriter pw) {

		String row = "<tr><th>%s<td>%s";

		StringBuilder sb = new StringBuilder();
		sb.append("<table border=1>");

		sb.append(String.format(row, "Connection Type", getConnectionType()));

		if (ConnectionType.DataSource == getConnectionType()) {
			sb.append(String.format(row, "Data Source Name", getDatasourceName()));
			sb.append(String.format(row, "Data Source Host", getDatasourceHost()));

			if (DatasourceHost.REMOTE == getDatasourceHost()) {
				sb.append(String.format(row, "Host", getWebServerHost()));
				sb.append(String.format(row, "Port", getWebServerPort()));
			}
		} else {
			sb.append(String.format(row, "URL", getDBURL()));
			sb.append(String.format(row, "User Name", getDBUserName()));
		}

		sb.append("</table>");

		pw.write(sb.toString());
	}

}

class Staging_Dev {

}

class Staging_Prod {

}

class HSQL {

}
