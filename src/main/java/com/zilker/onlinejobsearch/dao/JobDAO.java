package com.zilker.onlinejobsearch.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.constants.QueryConstants;
import com.zilker.onlinejobsearch.utils.DButils;

@Repository
public class JobDAO {

	private Connection connection = null;
	private PreparedStatement preparestatement, preparestatement1,preparestatement2 = null;
	private ResultSet resultset, resultset1,resultset2 = null;
	private Statement statement = null;

	/*
	 * method for displaying existing job designations.
	 */
	public ArrayList<JobMapping> displayJobs() throws SQLException {
		ArrayList<JobMapping> job = new ArrayList<JobMapping>();
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVEJOBDATA);
			while (resultset.next()) {
				JobMapping jm = new JobMapping();
				jm.setJobId(resultset.getInt(1));
				jm.setJobRole(resultset.getString(2));
				job.add(jm);
			}

		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}

		return job;
	}

	/*
	 * method for adding new jobs.
	 */
	public boolean addNewJob(String jobRole, int userId) throws SQLException {
		try {
			boolean flag=false;
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTJOB);
			preparestatement.setString(1, jobRole);
			preparestatement.setInt(2, userId);
			preparestatement.setInt(3, userId);
			preparestatement.executeUpdate();
			flag=true;
			return flag;
		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}

	}

	/*
	 * method for fetching job id given job designation.
	 */
	public int fetchJobId(String jobDesignation) throws SQLException {
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVEJOBDATA);
			int jobId=0;
			while (resultset.next()) {
				if (jobDesignation.equalsIgnoreCase(resultset.getString(2))) {
					jobId = resultset.getInt(1);
					break;
				}
			}
			return jobId;
		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

	/*
	 * method 1 for retrieving vacancy based on job.
	 */
	public ArrayList<Company> retrieveVacancyByJob(Company company) throws SQLException {
		ArrayList<Company> comp = new ArrayList<Company>();
		try {

			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEJOBDESIGNATION);
			preparestatement.setInt(1, company.getJobId());
			resultset = preparestatement.executeQuery();
			while (resultset.next()) {
				Company c = new Company();
				c.setJobRole(resultset.getString(1));
				comp.add(c);
			}

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return comp;
	}

	
	/*
	 * method 2 for retrieving vacancy based on job.
	 */
	public ArrayList<Company> retrieveVacancyByJob1(int jobId,int userId) throws SQLException {
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			float averageRating = 0;
			CompanyDAO companyDao = new CompanyDAO();
			connection = DButils.getConnection();
			int companyId = 0;
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEVACANCYBYJOBID);
			preparestatement.setInt(1, jobId);
			resultset = preparestatement.executeQuery();
			while (resultset.next()) {

				Company c = new Company();
				c.setLocation(resultset.getString(3));
				c.setJobDescription(resultset.getString(4));
				c.setSalary(resultset.getFloat(5));
				c.setVacancyCount(resultset.getInt(6));

				String companyid = resultset.getString(1);
				companyId = Integer.parseInt(companyid);
				averageRating = companyDao.calculateAverageRating(companyId);
				preparestatement1 = connection.prepareStatement(QueryConstants.RETRIEVECOMPANYNAME);
				preparestatement1.setInt(1, companyId);
				resultset1 = preparestatement1.executeQuery();
				while (resultset1.next()) {

					c.setCompanyName(resultset1.getString(1));
					c.setCompanyWebsiteUrl(resultset1.getString(2));
					c.setAverageRating(averageRating);
					c.setCompanyId(resultset1.getInt(3));
					
					
					preparestatement2 = connection.prepareStatement(QueryConstants.USERAPPLIEDJOBS);
					
					preparestatement2.setInt(1, userId);
					resultset2 = preparestatement2.executeQuery();
					while(resultset2.next()) {
						
						if((resultset1.getInt(3)==resultset2.getInt(1))  && ((resultset.getString(3).equals(resultset2.getString(3)))) 
								&&((jobId== resultset2.getInt(2)))) 
						{
						
								c.setFlag(1);
								
						}
					}
					
					comp.add(c);
				}

			}
		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
			DButils.closeConnection(connection, preparestatement1, resultset1);
		}
		return comp;
	}
	
	

	public boolean ifTechnologyIdExists(JobMapping jobmapping) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVEJOBDATA);
			int jobId = jobmapping.getJobId();
			while (resultset.next()) {
				int check = Integer.parseInt(resultset.getString(1));
				if (jobId == check) {
					flag = true;
					break;
				}
			}
			return flag;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

}

