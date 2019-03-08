package com.zilker.onlinejobsearch.delegate;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.dao.JobDAO;

@Service
public class JobDelegate {

	public ArrayList<JobMapping> displayJobs() throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<JobMapping> job = new ArrayList<JobMapping>();
		try {
			JobDAO jobDao = new JobDAO();
			job = jobDao.displayJobs();

		} catch (SQLException e) {
			throw e;
		}
		return job;
	}

	public int fetchJobId(String jobDesignation) throws SQLException {
		// TODO Auto-generated method stub
		int jobId = 0;
		try {
			JobDAO jobDao = new JobDAO();
			jobId = jobDao.fetchJobId(jobDesignation);
			return jobId;
		} catch (SQLException e) {
			throw e;
		}
	}

	public ArrayList<Company> retrieveVacancyByJob(Company company) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			JobDAO jobDao = new JobDAO();
			comp = jobDao.retrieveVacancyByJob(company);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

	
	public ArrayList<Company> retrieveVacancyByJob1(int jobId,int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			JobDAO jobDao = new JobDAO();
			comp = jobDao.retrieveVacancyByJob1(jobId,userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

	public boolean addNewJob(String jobRole, int userId) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false ;
		try {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.addNewJob(jobRole, userId);
			return flag;
		} catch (SQLException e) {
			throw e;
		}

	}

	public boolean ifJobIdExists(JobMapping jobmapping) throws SQLException{
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			JobDAO jobDao = new JobDAO();
			flag = jobDao.ifTechnologyIdExists(jobmapping);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	
	
}

