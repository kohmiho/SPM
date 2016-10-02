package com.pseg.spm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import com.pseg.spm.bean.Privilege;
import com.pseg.spm.bean.SurveyAssignment;
import com.pseg.spm.config.ConnectDB;

public class UserSurveyDAO {

	ConnectDB connectDB = null;

	public UserSurveyDAO(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}

	public ArrayList<SurveyAssignment> findByPrivilege(Privilege privilege) throws SQLException {
		return loadSurveys("select SPM_ID, ROLE_NAME, SUPPLIER_NAME, READ_ONLY from SPM where USER_LAN_ID=?", new String[] { privilege.getLanID() });
	}

	private ArrayList<SurveyAssignment> loadSurveys(String queryString, String[] param) throws SQLException {

		ArrayList<String[]> surveyList = connectDB.exeQuery(queryString, param);

		if (surveyList.size() != 0) {
			ArrayList<SurveyAssignment> surveys = new ArrayList<SurveyAssignment>();

			for (int i = 0; i < surveyList.size(); i++) {
				SurveyAssignment survey = new SurveyAssignment();
				survey.setId(surveyList.get(i)[0]);
				survey.setRole(surveyList.get(i)[1]);
				survey.setSpplier(surveyList.get(i)[2]);
				survey.setReadOnly(surveyList.get(i)[3]);
				surveys.add(survey);
			}
			return surveys;
		}

		return null;
	}

}
