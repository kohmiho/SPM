package com.pseg.spm.dao;

import java.sql.SQLException;

import com.pseg.spm.bean.Privilege;
import com.pseg.spm.config.ConnectDB;

public class PrivilegeDAO {

	ConnectDB connectDB = null;

	public PrivilegeDAO(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}

	public Privilege findByLanID(String lanID) throws SQLException {
		return loadModulePrivilege("select ID, LAN_ID, ADMIN, SUPERUSER, VIEW_ALL, READ_ONLY from SPM_USER where LAN_ID=?", new String[] { lanID });
	}

	private Privilege loadModulePrivilege(String queryString, String[] param) throws SQLException {
		Privilege privilege = new Privilege();
		String[] result = connectDB.exeQuery(queryString, param).get(0);
		int i = 0;
		privilege.setId(Integer.parseInt(result[i++]));
		privilege.setLanID(result[i++]);
		privilege.setAdmin(result[i++]);
		privilege.setSuperUser(result[i++]);
		privilege.setViewAll(result[i++]);
		privilege.setReadOnly(result[i++]);
		return privilege;
	}

}
