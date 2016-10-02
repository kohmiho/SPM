package com.pseg.spm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.pseg.spm.config.ConnectDB;

public class AcceptedAnswerDAO {

	ConnectDB connectDB = null;

	public AcceptedAnswerDAO(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}

	public List<String> findAllByID(String surveyID, String questID) throws SQLException {
		ArrayList<String[]> rs = connectDB.exeQuery("select ACPT_VALUE from SPM_ANS_OPTION where SRVY_ID=? and QUES_ID=? order by SORT_ORDER", new String[] {
				surveyID, questID });
		if (rs.size() > 0) {
			ArrayList<String> list = new ArrayList<>(rs.size());
			for (String[] result : rs) {
				list.add(result[0]);
			}
			return list;
		} else {
			return null;
		}
	}

}
