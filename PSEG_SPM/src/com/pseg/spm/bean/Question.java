package com.pseg.spm.bean;

import java.util.List;

public class Question extends Survey {

	private String answerField;
	private String commentField;
	private AnswerType answerType;
	private AcceptedValueType acceptedValueType;
	private List<String> acceptedAnswers;

	public String getAnswerField() {
		return answerField;
	}

	public void setAnswerField(String answerField) {
		this.answerField = answerField;
	}

	public String getCommentField() {
		return commentField;
	}

	public void setCommentField(String commentField) {
		this.commentField = commentField;
	}

	public AnswerType getAnswerType() {
		return answerType;
	}

	public void setAnswerType(AnswerType answerType) {
		this.answerType = answerType;
	}

	public AcceptedValueType getAcceptedValueType() {
		return acceptedValueType;
	}

	public void setAcceptedValueType(AcceptedValueType acceptedValueType) {
		this.acceptedValueType = acceptedValueType;
	}

	public List<String> getAcceptedAnswers() {
		return acceptedAnswers;
	}

	public void setAcceptedAnswers(List<String> acceptedAnswers) {
		this.acceptedAnswers = acceptedAnswers;
	}

}
