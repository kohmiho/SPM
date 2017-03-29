package com.kohmiho.spm.bean;

public class User {

	private String lanID;
	private String name;

	public String getLanID() {
		return lanID;
	}

	public void setLanID(String lanID) {
		this.lanID = null != lanID ? lanID : null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("%s - %s", lanID, name);
	}
}
