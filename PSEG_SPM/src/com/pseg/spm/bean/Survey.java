package com.pseg.spm.bean;

public abstract class Survey {

	public enum QuestionType {
		Section, Question
	}

	public enum AnswerType {
		Text_Single_Line("Text"), Text_Multiple_Line("Text"), Percentage("Percentage"), Whole_Number("Whole Number");

		private String type;

		private AnswerType(String type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return type;
		}
	}

	public enum AcceptedValueType {
		List_of_Choice, Any_Value
	}

	public Survey add(Survey survey) {
		throw new RuntimeException("This method is not support for the class: " + this.getClass());
	}

	private int surveyID;
	private int questionID;
	private int parentQuestionID;
	private String questionNumber;
	private QuestionType questionType;
	private String description;

	public int getSurveyID() {
		return surveyID;
	}

	public void setSurveyID(int surveyID) {
		this.surveyID = surveyID;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public int getParentQuestionID() {
		return parentQuestionID;
	}

	public void setParentQuestionID(int parentQuestionID) {
		this.parentQuestionID = parentQuestionID;
	}

	public String getQuestionNumber() {
		return questionNumber;
	}

	public void setQuestionNumber(String questionNumber) {
		this.questionNumber = questionNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

}
