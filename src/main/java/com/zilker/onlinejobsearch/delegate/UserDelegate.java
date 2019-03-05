package com.zilker.onlinejobsearch.delegate;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobRequest;
import com.zilker.onlinejobsearch.beans.Technology;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserTechnologyMapping;
import com.zilker.onlinejobsearch.dao.UserDAO;

@Service
public class UserDelegate {

	public boolean register(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userdao = new UserDAO();
			flag = userdao.register(user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public int addTechnologyDetails(UserTechnologyMapping usertechnology) throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userdao = new UserDAO();
			flag = userdao.addTechnologyDetails(usertechnology);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public String fetchUserNameById(int userId) throws SQLException {
		String userName = "";
		try {
			
			UserDAO userDao = new UserDAO();
			userName = userDao.fetchUserNameById(userId);
			
			return userName;
		} catch (SQLException e) {
			throw e;
		} 
	}

	
	public int fetchTechnologyId(Technology technology) throws SQLException {
		// TODO Auto-generated method stub
		int technologyId = 0;
		// TODO Auto-generated method stub
		try {
			UserDAO userDao = new UserDAO();
			technologyId = userDao.fetchTechnologyId(technology);
			return technologyId;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	public int fetchUserId(User user) throws SQLException {
		int userId = 0;
		// TODO Auto-generated method stub
		try {
			UserDAO userDao = new UserDAO();
			userId = userDao.fetchUserId(user);
			return userId;
		} catch (SQLException e) {
			throw e;
		}
	}

	public int login(User user) throws SQLException {
		// TODO Auto-generated method stub
		int i = 0;
		try {
			UserDAO userdao = new UserDAO();
			i = userdao.login(user);
		} catch (SQLException e) {
			throw e;
		}
		return i;
	}

	public boolean requestNewVacancy(JobRequest jobrequest, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.requestNewVacancy(jobrequest, user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public boolean reviewAndRateCompany(User user, Company company) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.reviewAndRateCompany(user, company);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public boolean interviewProcess(User user, Company company, JobMapping jobmapping) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.interviewProcess(user, company, jobmapping);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public int checkPasswordBeforeDelete(User user) throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.checkPasswordBeforeDelete(user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public ArrayList<Technology> displayTechnologies(Technology technology) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Technology> tech = new ArrayList<Technology>();
		try {
			UserDAO userDao = new UserDAO();
			tech = userDao.displayTechnologies(technology);

		} catch (SQLException e) {
			throw e;
		}
		return tech;

	}

	public ArrayList<Company> retrieveReview(Company company) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			UserDAO userDao = new UserDAO();
			comp = userDao.retrieveReview(company);

		} catch (SQLException e) {
			throw e;
		}

		return comp;
	}

	public ArrayList<Company> retrieveInterviewProcess(Company company) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			UserDAO userDao = new UserDAO();
			comp = userDao.retrieveInterviewProcess(company);

		} catch (SQLException e) {
			throw e;
		}

		return comp;
	}
	

	public ArrayList<User> retrieveUserData(User user)throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<User> userData = new ArrayList<User>();
		try {
			UserDAO userDao = new UserDAO();
			userData = userDao.retrieveUserData(user);

		} catch (SQLException e) {
			throw e;
		}

		return userData;
	}

	public boolean ifAlreadyExists(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.ifAlreadyExists(user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public boolean ifTechnologyIdExists(Technology technology) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.ifTechnologyIdExists(technology);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}
	
	
	
	
	public boolean registerAsAdmin(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.registerAsAdmin(user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public int insertIntoAdmin(User user, Company company) throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.insertIntoAdmin(user, company);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public void insertIntoUser(User user) throws SQLException {
		// TODO Auto-generated method stub
		try {
			UserDAO userDao = new UserDAO();
			userDao.insertIntoUser(user);
		} catch (SQLException e) {
			throw e;
		}
	}

	public int fetchCompanyIdByAdmin(User user) throws SQLException {
		// TODO Auto-generated method stub
		int companyId = 0;
		try {
			UserDAO userDao = new UserDAO();
			companyId = userDao.fetchCompanyIdByAdmin(user);
			return companyId;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateUserName(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateUserName(user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateCompanyName(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateCompanyName(user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateUserDesignation(User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			;
			flag = userDao.updateUserDesignation(user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public ArrayList<UserTechnologyMapping> displayUserTechnologies(UserTechnologyMapping userTechnologyMapping,
			User user) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<UserTechnologyMapping> usertechnology = new ArrayList<UserTechnologyMapping>();
		try {
			UserDAO userDao = new UserDAO();
			usertechnology = userDao.displayUserTechnologies(userTechnologyMapping, user);

		} catch (SQLException e) {
			throw e;
		}
		return usertechnology;
	}

	public boolean updateUserTechnology(UserTechnologyMapping userTechnologyMapping, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.updateUserTechnology(userTechnologyMapping, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public int addNewTechnology(Technology technology, User user) throws SQLException {
		// TODO Auto-generated method stub
		int technologyId = 0;
		try {
			UserDAO userDao = new UserDAO();
			technologyId = userDao.addNewTechnology(technology, user);
			return technologyId;
		} catch (SQLException e) {
			throw e;
		}
	}

	public int deleteUserAccount(User user)throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.deleteUserAccount(user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public boolean markContacted(Company company, User user)throws SQLException  {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
		
			UserDAO userDao = new UserDAO();
			flag = userDao.markContacted(company,user);
		} catch (SQLException e) {
			
			throw e;
		}
		return flag;
	}
	
	public boolean applyForJob(Company company, User user)throws SQLException  {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.applyForJob(company,user);
		} catch (SQLException e) {
			
			throw e;
		}
		return flag;
	}

	public boolean deleteTechnologyDetails(UserTechnologyMapping userTechnology, User user)throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			UserDAO userDao = new UserDAO();
			flag = userDao.deleteTechnologyDetails(userTechnology,user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}


}

