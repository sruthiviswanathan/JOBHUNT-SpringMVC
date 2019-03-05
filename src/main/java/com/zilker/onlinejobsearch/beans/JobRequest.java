package com.zilker.onlinejobsearch.beans;



/*
 * bean class for job details.
 */
public class JobRequest {

	private int requestId;
	private String email;
	private int jobId, vacancy_status;
	private String location;
	private float salary;

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public int getVacancy_status() {
		return vacancy_status;
	}

	public void setVacancy_status(int vacancy_status) {
		this.vacancy_status = vacancy_status;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

}
