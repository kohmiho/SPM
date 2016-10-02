package com.pseg.spm.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.pseg.spm.bean.SurveyCount;
import com.pseg.spm.config.ConnectDB;

public class SurveyCountDAO {

	ConnectDB connectDB = null;

	public SurveyCountDAO(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}

	public SurveyCount findByUserId(String userId) throws SQLException {
		SurveyCount surveyCount = new SurveyCount();

		ArrayList<String[]> rs1 = connectDB.exeQuery("select count(*) from spm where user_id=? and (apply is null or apply = 'Yes')", new String[] { userId });
		if (rs1.size() != 0) {
			surveyCount.setTotalTaken(rs1.get(0)[0]);
		}
		ArrayList<String[]> rs2 = connectDB.exeQuery("select count(*) from spm where user_id=? and apply = 'Yes'", new String[] { userId });
		if (rs2.size() != 0) {
			surveyCount.setApplicable(rs2.get(0)[0]);
		}
		ArrayList<String[]> rs3 = connectDB.exeQuery("select count(*) from spm where user_id=? and submit='Yes'", new String[] { userId });
		if (rs3.size() != 0) {
			surveyCount.setSubmitted(rs3.get(0)[0]);
		}
		ArrayList<String[]> rs4 = connectDB
				.exeQuery("select count(*) from spm where user_id=? and ((apply is null) or (apply = 'Yes' and submit is null))", new String[] { userId });
		if (rs4.size() != 0) {
			surveyCount.setRemaining(rs4.get(0)[0]);
		}

		return surveyCount;
	}
}
