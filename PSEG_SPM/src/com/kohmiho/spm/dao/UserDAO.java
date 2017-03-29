package com.kohmiho.spm.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.kohmiho.spm.bean.User;
import com.kohmiho.spm.config.ConnectDB;

public class UserDAO {

	ConnectDB connectDB = null;

	public UserDAO(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}

	public User findByLanID(String lanID) throws SQLException {
		ArrayList<String[]> rs = connectDB.exeQuery("select LAN_ID, Name from SPM_USER where LAN_ID=?", new String[] { lanID.toUpperCase() });
		if (rs.size() != 0) {
			String[] cols = rs.get(0);
			int i = 0;
			User user = new User();
			user.setLanID(cols[i++]);
			user.setName(cols[i++]);
			return user;
		}

		return null;
	}

}
