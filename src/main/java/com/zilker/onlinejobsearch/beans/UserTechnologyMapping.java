package com.zilker.onlinejobsearch.beans;

/*
 * bean class for user and technology mapping.
 */
public class UserTechnologyMapping {

	private int userId;
	private int technologyId;
	private int oldTechnologyId;
	private String technologyName;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTechnologyId() {
		return technologyId;
	}

	public void setTechnologyId(int technologyId) {
		this.technologyId = technologyId;
	}

	public int getOldTechnologyId() {
		return oldTechnologyId;
	}

	public void setOldTechnologyId(int oldTechnologyId) {
		this.oldTechnologyId = oldTechnologyId;
	}

	public String getTechnologyName() {
		return technologyName;
	}

	public void setTechnologyName(String technologyName) {
		this.technologyName = technologyName;
	}

}
