package com.kohmiho.spm.bean;

public class SurveyCount {

	private String totalTaken;
	private String applicable;
	private String submitted;
	private String remaining;

	public String getTotalTaken() {
		return totalTaken;
	}

	public void setTotalTaken(String totalTaken) {
		this.totalTaken = totalTaken;
	}

	public String getApplicable() {
		return applicable;
	}

	public void setApplicable(String applicable) {
		this.applicable = applicable;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getRemaining() {
		return remaining;
	}

	public void setRemaining(String remaining) {
		this.remaining = remaining;
	}

}
