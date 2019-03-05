package com.zilker.onlinejobsearch.beans;

/*
 * bean class for company details.
 */
public class Company {

	private int userId;
	private int companyId;
	private String companyName, userName,userCompany,userDesignation;
	private int jobId, vacancyCount;
	private int oldJobId;
	private String companyWebsiteUrl, jobDescription, location;
	private float salary;
	private String review;
	private float rating, averageRating;
	private String interviewProcess;
	private String jobRole;
	private String vacancyStatus;
	private String email;
	private String companyLogo;
	private String contacted;
	private int flag;
	
/*
 * Getters and setters for all variables
 */
	
	
	
	public int getUserId() {
		return userId;
	}

	public String getUserCompany() {
		return userCompany;
	}

	public void setUserCompany(String userCompany) {
		this.userCompany = userCompany;
	}

	public String getUserDesignation() {
		return userDesignation;
	}

	public void setUserDesignation(String userDesignation) {
		this.userDesignation = userDesignation;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getOldJobId() {
		return oldJobId;
	}

	public void setOldJobId(int oldJobId) {
		this.oldJobId = oldJobId;
	}

	public int getVacancyCount() {
		return vacancyCount;
	}

	public void setVacancyCount(int vacancyCount) {
		this.vacancyCount = vacancyCount;
	}

	public String getVacancyStatus() {
		return vacancyStatus;
	}

	public void setVacancyStatus(String vacancyStatus) {
		this.vacancyStatus = vacancyStatus;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getInterviewProcess() {
		return interviewProcess;
	}

	public void setInterviewProcess(String interviewProcess) {
		this.interviewProcess = interviewProcess;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	
	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}

	public float getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(float averageRating) {
		this.averageRating = averageRating;
	}

	public int getJobId() {
		return jobId;
	}

	public void setJobId(int jobId) {
		this.jobId = jobId;
	}

	public String getJobRole() {
		return jobRole;
	}

	public void setJobRole(String jobRole) {
		this.jobRole = jobRole;
	}
	
	public String getContacted() {
		return contacted;
	}

	public void setContacted(String contacted) {
		this.contacted = contacted;
	}

	public String getCompanyWebsiteUrl() {
		return companyWebsiteUrl;
	}

	public void setCompanyWebsiteUrl(String companyWebsiteUrl) {
		this.companyWebsiteUrl = companyWebsiteUrl;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
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

