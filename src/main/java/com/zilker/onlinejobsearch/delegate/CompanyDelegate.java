package com.zilker.onlinejobsearch.delegate;


import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.dao.CompanyDAO;

@Service
public class CompanyDelegate {

	public ArrayList<Company> displayCompanies() throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = null;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.displayCompanies();
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}
	
		
	public int fetchCompanyId(String companyName) throws SQLException {
		// TODO Auto-generated method stub
		int companyId = 0;
		try {

			CompanyDAO companyDao = new CompanyDAO();
			companyId = companyDao.fetchCompanyId(companyName);
			return companyId;
		} catch (SQLException e) {
			throw e;
		}
	}
	
	
	public String fetchCompanyName(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		String companyName="";
		try {

			CompanyDAO companyDao = new CompanyDAO();
			companyName = companyDao.fetchCompanyName(companyId);
			return companyName;
		} catch (SQLException e) {
			throw e;
		}
	}

	public ArrayList<Company> retrieveVacancyByCompany(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {

			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany(companyId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}


	public ArrayList<Company> retrieveVacancyByCompany1(int companyId,int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompany1(companyId,userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

	public ArrayList<Company> retrieveVacancyByCompanyAdmin(Company company) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByCompanyAdmin(company);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

	public int numberOfVacancyPublished(int companyId) throws SQLException {
		// TODO Auto-generated method stub
		int count=0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			count = companyDao.numberOfVacancyPublished(companyId);
		} catch (SQLException e) {
			throw e;
		}
		return count;
	}

	public ArrayList<Company> retrieveVacancyByLocation(String location,int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.retrieveVacancyByLocation(location,userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}


	public boolean addNewCompany(String companyName,String websiteUrl,String companyLogo) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			Company company = new Company();
			CompanyDAO companyDao = new CompanyDAO();
			company.setCompanyName(companyName);
			company.setCompanyWebsiteUrl(websiteUrl);
			company.setCompanyLogo(companyLogo);
			flag = companyDao.addNewCompany(company);
		} catch (SQLException e) {
			throw e;
		}

		return flag;
	}

	public int addNewCompanyBySiteAdmin(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.addNewCompanyBySiteAdmin(company, user);
		} catch (SQLException e) {
			throw e;
		}

		return flag;
	}

	public boolean publishVacancy(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.publishVacancy(company, user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public void compareVacancyWithRequest(Company company) throws SQLException {
		// TODO Auto-generated method stub
		try {
		
			CompanyDAO companyDao = new CompanyDAO();
			companyDao.compareVacancyWithRequest(company);
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean removeVacancy(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.removeVacancy(company, user);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public int deleteCompany(Company company) throws SQLException {
		// TODO Auto-generated method stub
		int flag = 0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.deleteCompany(company);
		} catch (SQLException e) {
			throw e;
		}
		return flag;
	}

	public boolean updateVacancyJobId(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyJobId(company, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancyLocation(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyLocation(company, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancyDescription(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyDescription(company, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancySalary(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancySalary(company, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public boolean updateVacancyCount(Company company, User user) throws SQLException {
		// TODO Auto-generated method stub
		boolean flag = false;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			flag = companyDao.updateVacancyCount(company, user);
			return flag;
		} catch (SQLException e) {
			throw e;
		}
	}

	public void insertIntoCompanyDetails(int userId, int companyId) throws SQLException {
		// TODO Auto-generated method stub
		try {
			CompanyDAO companyDao = new CompanyDAO();
			companyDao.insertIntoCompanyDetails(userId, companyId);
		} catch (SQLException e) {
			throw e;
		}

	}


	public ArrayList<Company> viewAppliedUsers(Company company)throws SQLException{
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.viewAppliedUsers(company);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}
	
	public int numberOfAppliedUsers(int companyId)throws SQLException{
		// TODO Auto-generated method stub
		int count=0;
		try {
			CompanyDAO companyDao = new CompanyDAO();
			count = companyDao.numberOfAppliedUsers(companyId);
		} catch (SQLException e) {
			throw e;
		}
		return count;
	}


	public ArrayList<Company> viewAppliedJobs(int userId) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Company> comp = new ArrayList<Company>();
		try {
			CompanyDAO companyDao = new CompanyDAO();
			comp = companyDao.viewAppliedJobs(userId);
		} catch (SQLException e) {
			throw e;
		}
		return comp;
	}

}
