package com.kohmiho.spm.config;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.kohmiho.spm.config.DBConfig.ConnectionType;
import com.kohmiho.spm.config.DBConfig.DatasourceHost;

public class ConnectDB {

	private Connection conn = null;

	public ConnectDB(DBConfig dbConfig) throws SQLException {

		if (ConnectionType.JDBC == dbConfig.getConnectionType()) {
			try {
				Class.forName(dbConfig.getDBDriver());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			conn = DriverManager.getConnection(dbConfig.getDBURL(), dbConfig.getDBUserName(), dbConfig.getDBPassword());

		} else if (ConnectionType.DataSource == dbConfig.getConnectionType()) {

			Hashtable<String, String> ht = new Hashtable<String, String>();
			ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

			if (DatasourceHost.REMOTE == dbConfig.getDatasourceHost()) {
				ht.put(Context.PROVIDER_URL, "t3://" + dbConfig.getWebServerHost() + ":" + dbConfig.getWebServerPort());
			}

			Context ctx = null;
			DataSource ds = null;
			try {
				ctx = new InitialContext(ht);
				ds = (javax.sql.DataSource) ctx.lookup(dbConfig.getDatasourceName().name());
			} catch (NamingException e) {
				e.printStackTrace();
			} finally {
				if (ctx != null) {
					try {
						ctx.close();
					} catch (NamingException e) {
						e.printStackTrace();
					}
				}
			}

			conn = ds.getConnection();
		}
	}

	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
	}

	/**
	 * execute SQL query and return result
	 * 
	 * @param sql
	 * @param params
	 * @return use String[] to get query result from the array list
	 */
	public ArrayList<String[]> exeQuery(String sql, String[] params) throws SQLException {

		ArrayList<String[]> returnResult = new ArrayList<String[]>();

		PreparedStatement preStmt = conn.prepareStatement(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				preStmt.setString(i + 1, params[i]);
			}
		}

		ResultSet rs = preStmt.executeQuery();
		preStmt.clearParameters();

		String[] result = null;
		while (rs.next()) {
			result = new String[rs.getMetaData().getColumnCount()];
			for (int i = 0; i < result.length; i++) {
				result[i] = rs.getString(i + 1);
			}
			returnResult.add(result);
		}

		rs.close();
		preStmt.close();

		return returnResult;
	}

	public void exeProcedure(String sql, String[] params) throws SQLException {

		CallableStatement callStmt = conn.prepareCall(sql);
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				callStmt.setString(i + 1, params[i]);
			}
		}
		callStmt.execute();
		callStmt.clearParameters();
		callStmt.close();
	}

	public void exeUpdate(ArrayList<String> sqls, ArrayList<String[]> params) throws SQLException {

		conn.setAutoCommit(false);
		for (int i = 0; i < sqls.size(); i++) {
			update(sqls.get(i), params.get(i));
		}
		conn.commit();
	}

	public void exeUpdate(String sql, String[] param) throws SQLException {

		conn.setAutoCommit(false);
		update(sql, param);
		conn.commit();
	}

	public void exeUpdate(String sqls[], String[][] params) throws SQLException {

		conn.setAutoCommit(false);
		for (int i = 0; i < sqls.length; i++) {
			update(sqls[i], params[i]);
		}
		conn.commit();
	}

	private void update(String sql, String[] param) throws SQLException {

		PreparedStatement preStmt = conn.prepareStatement(sql);

		if (param != null) {
			for (int i = 0; i < param.length; i++) {
				preStmt.setString(i + 1, param[i]);
			}
		}

		preStmt.executeUpdate();
		preStmt.clearParameters();
		preStmt.close();
	}

	public byte[] runGetBLOB(String sql, String[] param) {

		byte[] allBytesInBlob = null;

		try {
			PreparedStatement stmnt = conn.prepareStatement(sql);

			ResultSet rs = stmnt.executeQuery();

			while (rs.next()) {
				try {
					Blob aBlob = rs.getBlob(1);
					allBytesInBlob = aBlob.getBytes(1, (int) aBlob.length());
					return (allBytesInBlob);

				} catch (Exception ex) {
					// The driver could not handle this as a BLOB...
					// Fallback to default (and slower) byte[] handling

					// byte[] bytes = rs.getBytes(1);
				}
			}

			rs.close();
			stmnt.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return allBytesInBlob;
	}

}