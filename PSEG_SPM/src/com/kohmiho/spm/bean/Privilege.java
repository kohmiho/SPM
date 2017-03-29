package com.kohmiho.spm.bean;

public class Privilege {

	private int id;
	private String lanID;
	private boolean isAdmin;
	private boolean isSuperUser;
	private boolean isViewAll;
	private boolean isReadOnly;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isViewAll() {
		return isViewAll;
	}

	public void setViewAll(boolean isViewAll) {
		this.isViewAll = isViewAll;
	}

	public void setViewAll(String str) {
		this.isViewAll = null != str && "Y".equals(str.trim().toUpperCase()) ? true : false;
	}

	public String getLanID() {
		return lanID;
	}

	public void setLanID(String lanID) {
		this.lanID = lanID;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public void setAdmin(String str) {
		this.isAdmin = null != str && "Y".equals(str.trim().toUpperCase()) ? true : false;
	}

	public boolean isSuperUser() {
		return isSuperUser;
	}

	public void setSuperUser(boolean isPowerUser) {
		this.isSuperUser = isPowerUser;
	}

	public void setSuperUser(String str) {
		this.isSuperUser = null != str && "Y".equals(str.trim().toUpperCase()) ? true : false;
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

}
