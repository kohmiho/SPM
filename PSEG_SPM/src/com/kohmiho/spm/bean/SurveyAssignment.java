package com.kohmiho.spm.bean;

public class SurveyAssignment {

	private String id;
	private String role;
	private String spplier;
	private boolean isReadOnly;

	public SurveyAssignment() {

	}

	public SurveyAssignment(String id) {
		setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getSpplier() {
		return spplier;
	}

	public void setSpplier(String spplier) {
		this.spplier = spplier;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}

	public void setReadOnly(String str) {
		this.isReadOnly = null != str && "Y".equals(str.trim().toUpperCase()) ? true : false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		SurveyAssignment project = (SurveyAssignment) obj;
		if (project.getId().toUpperCase().equals(this.id.toUpperCase()))
			return true;

		return false;
	}
}