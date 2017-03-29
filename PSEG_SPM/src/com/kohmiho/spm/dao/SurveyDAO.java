package com.kohmiho.spm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kohmiho.spm.bean.Question;
import com.kohmiho.spm.bean.Section;
import com.kohmiho.spm.bean.Survey;
import com.kohmiho.spm.bean.Survey.AcceptedValueType;
import com.kohmiho.spm.bean.Survey.AnswerType;
import com.kohmiho.spm.bean.Survey.QuestionType;
import com.kohmiho.spm.config.ConnectDB;

public class SurveyDAO {

	ConnectDB connectDB = null;

	public SurveyDAO(ConnectDB connectDB) {
		this.connectDB = connectDB;
	}

	public List<Survey> findAllBySurveyID(String surveyID) throws SQLException {

		AcceptedAnswerDAO acceptedAnswerDAO = new AcceptedAnswerDAO(connectDB);

		ArrayList<String[]> rs = connectDB
				.exeQuery(
						"select QUES_NUM, TYPE, DESCRIPTION, ANSWER_TYPE, ACPT_VALUE_TYPE, ANS_FIELD, CMT_FIELD, QUES_ID, SRVY_ID, PARENT_QUES_ID from SPM_QUES where SRVY_ID=? and PARENT_QUES_ID is null",
						new String[] { surveyID });
		if (rs.size() > 0) {
			ArrayList<Survey> list = new ArrayList<>(rs.size());
			for (String[] result : rs) {
				Survey bean = toSurveyQuestion(result);
				list.add(bean);

				findAllChildren(acceptedAnswerDAO, bean);
			}

			return list;

		} else {
			return null;
		}
	}

	private void findAllChildren(AcceptedAnswerDAO acceptedAnswerDAO, Survey parent) throws SQLException {

		ArrayList<String[]> rs = connectDB
				.exeQuery(
						"select QUES_NUM, TYPE, DESCRIPTION, ANSWER_TYPE, ACPT_VALUE_TYPE, ANS_FIELD, CMT_FIELD, QUES_ID, SRVY_ID, PARENT_QUES_ID from SPM_QUES where PARENT_QUES_ID=?",
						new String[] { Integer.toString(parent.getQuestionID()) });

		for (String[] result : rs) {
			Survey child = toSurveyQuestion(result);
			parent.add(child);

			if (QuestionType.Section == child.getQuestionType()) {

				findAllChildren(acceptedAnswerDAO, child);

			} else if (QuestionType.Question == child.getQuestionType()) {

				Question questionBean = (Question) child;
				if (AcceptedValueType.List_of_Choice == questionBean.getAcceptedValueType()) {
					questionBean.setAcceptedAnswers(acceptedAnswerDAO.findAllByID(Integer.toString(questionBean.getSurveyID()),
							Integer.toString(questionBean.getQuestionID())));
				}
			}
		}
	}

	public Survey findByQuestionNumber(String questionNumber, String surveyID) throws SQLException {

		AcceptedAnswerDAO acceptedAnswerDAO = new AcceptedAnswerDAO(connectDB);

		ArrayList<String[]> rs = connectDB
				.exeQuery(
						"select QUES_NUM, TYPE, DESCRIPTION, ANSWER_TYPE, ACPT_VALUE, ANS_FIELD, CMT_FIELD, QUES_ID, SRVY_ID, PARENT_QUES_ID from SPM_QUES where QUES_NUM=? and SRVY_ID=?",
						new String[] { questionNumber, surveyID });
		if (rs.size() > 0) {
			Survey bean = toSurveyQuestion(rs.get(0));
			findAllChildren(acceptedAnswerDAO, bean);
			return bean;

		} else {
			return null;
		}
	}

	private Survey toSurveyQuestion(String[] result) {

		QuestionType type = toQuestionType(result[1]);

		if (QuestionType.Section == type) {
			Section bean = new Section();
			bean.setQuestionNumber(result[0]);
			bean.setQuestionType(type);
			bean.setDescription(result[2]);
			bean.setQuestionID(Integer.parseInt(result[7]));
			bean.setSurveyID(Integer.parseInt(result[8]));
			bean.setParentQuestionID(null != result[9] ? Integer.parseInt(result[9]) : 0);
			return bean;
		} else if (QuestionType.Question == type) {
			Question bean = new Question();
			bean.setQuestionNumber(result[0]);
			bean.setQuestionType(type);
			bean.setDescription(result[2]);
			bean.setAnswerType(toAnswerType(result[3]));
			bean.setAcceptedValueType(toAcceptedValueType(result[4]));
			bean.setAnswerField(result[5]);
			bean.setCommentField(result[6]);
			bean.setQuestionID(Integer.parseInt(result[7]));
			bean.setSurveyID(Integer.parseInt(result[8]));
			bean.setParentQuestionID(Integer.parseInt(result[9]));
			return bean;
		}

		return null;
	}

	private QuestionType toQuestionType(String str) {
		if (null == str)
			return null;

		switch (str) {
		case "Section":
			return QuestionType.Section;
		case "Question":
			return QuestionType.Question;
		default:
			return null;
		}
	}

	private AnswerType toAnswerType(String str) {
		if (null == str)
			return null;

		switch (str) {
		case "Percentage":
			return AnswerType.Percentage;
		case "Whole Number":
			return AnswerType.Whole_Number;
		case "Text (single line limited)":
			return AnswerType.Text_Single_Line;
		case "Text (multiple lines)":
			return AnswerType.Text_Multiple_Line;
		default:
			return null;
		}
	}

	private AcceptedValueType toAcceptedValueType(String str) {
		if (null == str)
			return null;

		switch (str) {
		case "Any Value":
			return AcceptedValueType.Any_Value;
		case "List of Choices":
			return AcceptedValueType.List_of_Choice;
		default:
			return null;
		}
	}

}
