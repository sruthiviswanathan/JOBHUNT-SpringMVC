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
import com.zilker.onlinejobsearch.beans.JobRequest;
import com.zilker.onlinejobsearch.beans.Technology;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserTechnologyMapping;
import com.zilker.onlinejobsearch.constants.QueryConstants;
import com.zilker.onlinejobsearch.utils.DButils;

@Repository
public class UserDAO {

	private Connection connection, connection1 = null;
	private PreparedStatement preparestatement, preparestatement1 = null;
	private ResultSet resultset, resultset1 = null;
	private Statement statement = null;

	/*
	 * method for registering new user.
	 */
	public boolean register(User user) throws SQLException {
		boolean flag = false ;
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTUSER);
			preparestatement.setString(1, user.getUserName());
			preparestatement.setString(2, user.getEmail());
			preparestatement.setString(3, user.getPassword());
			preparestatement.setString(4, user.getCompany());
			preparestatement.setString(5, user.getDesignation());
			preparestatement.executeUpdate();
			flag = true;
		} catch (SQLException e) {
			flag = false;
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	/*
	 * method for adding technology details of a user.
	 */
	public int addTechnologyDetails(UserTechnologyMapping usertechnology) throws SQLException {
		int flag = 0;
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTTECHNOLOGY);
			preparestatement.setInt(1, usertechnology.getUserId());
			preparestatement.setInt(2, usertechnology.getTechnologyId());
			preparestatement.setInt(3, usertechnology.getUserId());
			preparestatement.setInt(4, usertechnology.getUserId());
			preparestatement.executeUpdate();
			flag = 1;
		} catch (SQLException e) {
			flag = 0;
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	/*
	 * method for logging in.
	 */
	public int login(User user) throws SQLException {
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVEUSERDATA);
			String email = user.getEmail();
			String password = user.getPassword();
			int roleId = 0;
			while (resultset.next()) {
				if ((email.equals(resultset.getString(1))) && (password.equals(resultset.getString(2)))) {

					roleId = resultset.getInt(3);
					break;
				}
			}
			return roleId;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

	/*
	 * method for displaying technologies to the user.
	 */
	public ArrayList<Technology> displayTechnologies(Technology technology) throws SQLException {
		ArrayList<Technology> tech = new ArrayList<Technology>();
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVETECHNOLOGYDATA);
			while (resultset.next()) {
				Technology t = new Technology();
				t.setTechnologyId(resultset.getInt(1));
				t.setTechnology(resultset.getString(2));
				tech.add(t);
			}

		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return tech;
	}

	/*
	 * method for displaying reviews of a particular company.
	 */
	public ArrayList<Company> retrieveReview(Company company) throws SQLException {
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEREVIEW);
			preparestatement.setInt(1, company.getCompanyId());
			resultset = preparestatement.executeQuery();
			while (resultset.next()) {
				Company c = new Company();
				c.setReview(resultset.getString(1));
				c.setRating(resultset.getFloat(3));
				c.setUserName(resultset.getString(4));
				c.setUserCompany(resultset.getString(5));
				c.setUserDesignation(resultset.getString(6));
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
	 * method for displaying interview process of a job in a company.
	 */
	public ArrayList<Company> retrieveInterviewProcess(Company company) throws SQLException {
		ArrayList<Company> comp = new ArrayList<Company>();

		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEINTERVIEWPROCESS);
			preparestatement.setInt(1, company.getCompanyId());
			resultset = preparestatement.executeQuery();
			while (resultset.next()) {
				Company c = new Company();
				c.setJobRole(resultset.getString(1));
				c.setInterviewProcess(resultset.getString(2));
				c.setUserName(resultset.getString(3));
				c.setUserCompany(resultset.getString(4));
				c.setUserDesignation(resultset.getString(5));
				comp.add(c);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return comp;
	}
	
	public ArrayList<User> retrieveUserData(User user)throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<User> userData = new ArrayList<User>();

		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEUSERDETAILS);
			preparestatement.setInt(1, user.getUserId());
			resultset = preparestatement.executeQuery();
			while (resultset.next()) {
				User u = new User();
				u.setUserName(resultset.getString(1));
				u.setCompany(resultset.getString(2));
				u.setDesignation(resultset.getString(3));
				userData.add(u);
			}
		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return userData;
	}

	public String fetchUserNameById(int userId) throws SQLException {

		try {
			String userName = "";
		
			connection1 = DButils.getConnection();
			preparestatement1 = connection1.prepareStatement(QueryConstants.RETRIEVEUSERNAMEBYID);
			preparestatement1.setInt(1, userId);
			resultset1 = preparestatement1.executeQuery();
			while (resultset1.next()) {
				userName = resultset1.getString(1);
			}

			return userName;
		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection1, preparestatement1, resultset1);
		}

	}

	public int fetchTechnologyId(Technology technology) throws SQLException {
		// TODO Auto-generated method stub
		try {
			int technologyId = 0;
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVETECHNOLOGYID);
			preparestatement.setString(1, technology.getTechnology());
			resultset = preparestatement.executeQuery();
			while (resultset.next()) {
				if (technology.getTechnology().equalsIgnoreCase(resultset.getString(2))) {
					technologyId = resultset.getInt(1);
					break;
				}
			}
			return technologyId;
		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

	public int addNewTechnology(Technology technology, User user) throws SQLException {
		// TODO Auto-generated method stub
		try {
			int technologyId = 0;
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTTECHNOLOGYDATA);
			preparestatement.setString(1, technology.getTechnology());
			preparestatement.setInt(2, user.getUserId());
			preparestatement.setInt(3, user.getUserId());
			preparestatement.executeUpdate();
			technologyId = fetchTechnologyId(technology);
			return technologyId;
		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

	/*
	 * method for fetching user id given user mail.
	 */
	public int fetchUserId(User user) throws SQLException {
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVEUSERDATA);
			int userId = 0;
			String email = user.getEmail();
			while (resultset.next()) {
				if (email.equals(resultset.getString(1))) {
					userId = resultset.getInt(4);
					break;
				}
			}
			return userId;
		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}

	}

	/*
	 * method for deleting an user account.
	 */
	public int checkPasswordBeforeDelete(User user) throws SQLException {
		int flag = 0;
		try {
			connection = DButils.getConnection();
			String pass = user.getPassword();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEEMAILBYID);
			preparestatement.setInt(1, user.getUserId());
			resultset = preparestatement.executeQuery();
			while(resultset.next()){
			if(pass.equals(resultset.getString(1))){
				flag = 1;
				}	
			}
		} catch (SQLException e) {
			flag=0;
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}
	/*
	 * method for deleting user job request.
	 */
	public int deleteUserAccount(User user) throws SQLException {
		int flag = 0;
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.DELETEUSER);
			preparestatement.setInt(1, user.getUserId());
			preparestatement.executeUpdate();
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.DELETEJOBREQUEST);
			preparestatement.setString(1, user.getEmail());
			preparestatement.executeUpdate();
			flag = 1;

		} catch (SQLException e) {

			flag = 0;
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}
	
	
	
	/*
	 * method for requesting vacancy.
	 */
	public boolean requestNewVacancy(JobRequest jobrequest, User user) throws SQLException {
		boolean flag=false;
		try {
			
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTJOBREQUEST);
			preparestatement.setString(1, jobrequest.getEmail());
			preparestatement.setInt(2, jobrequest.getJobId());
			preparestatement.setString(3, jobrequest.getLocation());
			preparestatement.setFloat(4, jobrequest.getSalary());
			preparestatement.setInt(5, user.getUserId());
			preparestatement.setInt(6, user.getUserId());
			preparestatement.executeUpdate();
			flag=true;
		} catch (SQLException e) {

			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	/*
	 * method for adding interview process of a company
	 */
	public boolean interviewProcess(User user, Company company, JobMapping jobmapping) throws SQLException {
		boolean flag=false;
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTJOBREVIEW);
			preparestatement.setInt(1, user.getUserId());
			preparestatement.setInt(2, company.getCompanyId());
			preparestatement.setInt(3, jobmapping.getJobId());
			preparestatement.setString(4, company.getInterviewProcess());
			preparestatement.setInt(5, user.getUserId());
			preparestatement.setInt(6, user.getUserId());
			preparestatement.executeUpdate();
			flag=true;
		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	/*
	 * method for adding review and rating of a company
	 */
	public boolean reviewAndRateCompany(User user, Company company) throws SQLException {
		boolean flag= false;
		try {

			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTREVIEWRATING);
			preparestatement.setInt(1, user.getUserId());
			preparestatement.setInt(2, company.getCompanyId());
			preparestatement.setString(3, company.getReview());
			preparestatement.setFloat(4, company.getRating());
			preparestatement.setInt(5, user.getUserId());
			preparestatement.setInt(6, user.getUserId());
			preparestatement.executeUpdate();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVECOMPANYNAME);
			preparestatement.setInt(1, company.getCompanyId());
			resultset = preparestatement.executeQuery();
			flag=true;
			
		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public boolean ifAlreadyExists(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVEUSERDATA);
			String email = user.getEmail();
			while (resultset.next()) {
				if (email.equals(resultset.getString(1))) {
					System.out.println(email);
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
	
	public boolean ifTechnologyIdExists(Technology technology)throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVETECHNOLOGYDATA);
			int technologyId = technology.getTechnologyId();
			while (resultset.next()) {
				int check = Integer.parseInt(resultset.getString(1));
				if (technologyId == check) {
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

	
	

	public boolean registerAsAdmin(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTADMIN);
			preparestatement.setString(1, user.getUserName());
			preparestatement.setString(2, user.getEmail());
			preparestatement.setString(3, user.getPassword());
			preparestatement.setString(4, user.getCompany());
			preparestatement.setString(5, user.getDesignation());
			preparestatement.setInt(6, 2);
			preparestatement.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			flag = false;
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public int insertIntoAdmin(User user, Company company) throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0, userId = 0, companyId = 0;
		try {
			connection = DButils.getConnection();
			userId = user.getUserId();
			companyId = company.getCompanyId();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTCOMPANYADMIN);
			preparestatement.setInt(1, userId);
			preparestatement.setInt(2, companyId);
			preparestatement.setInt(3, userId);
			preparestatement.setInt(4, userId);
			preparestatement.executeUpdate();
			flag = 1;

		} catch (SQLException e) {
			flag = 0;
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public void insertIntoUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.INSERTINTOUSER);
			preparestatement.setInt(1, user.getUserId());
			preparestatement.setInt(2, user.getUserId());
			preparestatement.setString(3, user.getEmail());
			preparestatement.executeUpdate();

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

	public int fetchCompanyIdByAdmin(User user) throws SQLException {
		// TODO Auto-generated method stub
		try {
			connection = DButils.getConnection();
			statement = connection.createStatement();
			resultset = statement.executeQuery(QueryConstants.RETRIEVECOMPANYID);
			int companyId = 0;
			int userId = user.getUserId();
			while (resultset.next()) {
				if (userId == resultset.getInt(1)) {
					companyId = resultset.getInt(2);
					break;
				}

			}

			return companyId;
		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
	}

	public boolean updateUserName(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			int userId = user.getUserId();
			preparestatement = connection.prepareStatement(QueryConstants.UPDATEUSERNAME);
			preparestatement.setString(1, user.getUserName());
			preparestatement.setInt(2, userId);
			preparestatement.setString(3, user.getCurrentTime());
			preparestatement.setInt(4, userId);
			preparestatement.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public boolean updateCompanyName(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			int userId = user.getUserId();
			preparestatement = connection.prepareStatement(QueryConstants.UPDATEUSERCOMPANY);
			preparestatement.setString(1, user.getCompany());
			preparestatement.setInt(2, userId);
			preparestatement.setString(3, user.getCurrentTime());
			preparestatement.setInt(4, userId);
			preparestatement.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public boolean updateUserDesignation(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			int userId = user.getUserId();
			preparestatement = connection.prepareStatement(QueryConstants.UPDATEUSERDESIGNATION);
			preparestatement.setString(1, user.getDesignation());
			preparestatement.setInt(2, userId);
			preparestatement.setString(3, user.getCurrentTime());
			preparestatement.setInt(4, userId);
			preparestatement.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public ArrayList<UserTechnologyMapping> displayUserTechnologies(UserTechnologyMapping userTechnologyMapping,
			User user) throws SQLException {
		// TODO Auto-generated method stub

		ArrayList<UserTechnologyMapping> userTechnology = new ArrayList<UserTechnologyMapping>();
	
		try {
			
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.RETRIEVEUSERTECHNOLOGY);
			preparestatement.setInt(1, user.getUserId());
			resultset = preparestatement.executeQuery();

			while (resultset.next()) {
				UserTechnologyMapping t = new UserTechnologyMapping();
				//t.setTechnologyId(resultset.getInt(1));
				t.setTechnologyName(resultset.getString(1));
				
				userTechnology.add(t);
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return userTechnology;
	}

	public boolean updateUserTechnology(UserTechnologyMapping userTechnologyMapping, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			int userId = user.getUserId();
			preparestatement = connection.prepareStatement(QueryConstants.UPDATEUSERTECHNOLOGY);
			preparestatement.setInt(1, userTechnologyMapping.getTechnologyId());
			preparestatement.setInt(2, userId);
			preparestatement.setString(3, user.getCurrentTime());
			preparestatement.setInt(4, userId);
			preparestatement.setInt(5, userTechnologyMapping.getOldTechnologyId());
			preparestatement.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	public boolean applyForJob(Company company, User user) throws SQLException{
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.APPLYFORJOB);
			preparestatement.setInt(1, user.getUserId());
			preparestatement.setString(2, user.getEmail());
			preparestatement.setInt(3, company.getCompanyId());
			preparestatement.setInt(4, company.getJobId());
			preparestatement.setString(5, company.getLocation());
			preparestatement.setInt(6, user.getUserId());
			preparestatement.setInt(7, user.getUserId());
			preparestatement.executeUpdate();
			flag = true;
			
		} catch (SQLException e) {
			
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	
	public boolean markContacted(Company company, User user) throws SQLException{
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			System.out.println("done1");
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.MARKCONTACTED);
			preparestatement.setString(1, "yes");
			preparestatement.setInt(2, user.getUserId());
			preparestatement.setInt(3, user.getUserId());
			preparestatement.setInt(4, company.getCompanyId());
			preparestatement.setInt(5, company.getJobId());
			preparestatement.setString(6, company.getLocation());
			preparestatement.setString(7, company.getEmail());
			preparestatement.executeUpdate();
			flag = true;
			System.out.println("done");	
		} catch (SQLException e) {
		
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}
	
	
	public boolean deleteTechnologyDetails(UserTechnologyMapping userTechnology, User user)throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			connection = DButils.getConnection();
			preparestatement = connection.prepareStatement(QueryConstants.DELETEUSERTECHNOLOGY);
			preparestatement.setInt(1, user.getUserId());
			preparestatement.executeUpdate();
			flag = true;

		} catch (SQLException e) {
			throw e;

		} finally {
			DButils.closeConnection(connection, preparestatement, resultset);
		}
		return flag;
	}

	

	
}

