package com.zilker.onlinejobsearch.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.JobRequest;
import com.zilker.onlinejobsearch.beans.Technology;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserTechnologyMapping;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;

@Controller
public class UserController {

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	CompanyDelegate companyDelegate;

	
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("login");
		try {
			Company company = new Company();
			ArrayList<Company> displayCompanies = null;
			displayCompanies = companyDelegate.displayCompanies(company);
			mav.addObject("companies", displayCompanies);
			mav.addObject("login", new User());
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	
	@RequestMapping(value = "/users-login", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute("login") User user) {
		ModelAndView mav = null;
		try {
			HttpSession session = request.getSession();

			int role = 0, userId = 0;
			String userName = "";
			role = userDelegate.login(user);

			session.setAttribute("email", user.getEmail());
			userId = userDelegate.fetchUserId(user);
			userName = userDelegate.fetchUserNameById(userId);
			session.setAttribute("userName", userName);
			session.setAttribute("userId",userId);
			
			if (role == 0) {

				mav = new ModelAndView("login");
				Company company = new Company();
				ArrayList<Company> displayCompanies = null;
				displayCompanies = companyDelegate.displayCompanies(company);
				mav.addObject("companies", displayCompanies);
				mav.addObject("loginError", "error");
			} else if (role == 1) {

				mav = new ModelAndView("findjob");
				Company company = new Company();
				ArrayList<Company> companyDetails = null;
				companyDetails = companyDelegate.displayCompanies(company);
				mav.addObject("companyList", companyDetails);

			} else if (role == 2) {
				mav = new ModelAndView("admin");
				
				Company company = new Company();
				user.setEmail(user.getEmail());
				int companyId=0;
				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				companyId = userDelegate.fetchCompanyIdByAdmin(user);
				company.setCompanyId(companyId);
				int appliedUsers=companyDelegate.numberOfAppliedUsers(company);
				int postedJobs = companyDelegate.numberOfVacancyPublished(company);
				mav.addObject("appliedUsers",appliedUsers);
				mav.addObject("postedJobs",postedJobs);
			}

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ModelAndView registerProcess(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		try {
			HttpSession session = request.getSession();
			String[] technology;
			String skills = "";
			String userName = "";
			int userId = 0, flag = 0, technologyId = 0;
			Technology techh = new Technology();
			UserTechnologyMapping usertechnology = new UserTechnologyMapping();
			User user = new User();
			String name = request.getParameter("userName");
			String password = request.getParameter("psw");
			String confirmPassword = request.getParameter("cpsw");
			String email = request.getParameter("email");
			String companyName = request.getParameter("companyName");
			String designation = request.getParameter("designation");

			user.setUserName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setCompany(companyName);
			user.setDesignation(designation);

			if (userDelegate.register(user)) {

				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				userDelegate.insertIntoUser(user);

			}
			skills = request.getParameter("skillset");
			if (skills != "") {
				technology = skills.split("@");
				if (technology != null) {

					for (int i = 0; i < technology.length; i++) {

						usertechnology.setUserId(user.getUserId());
						techh.setTechnology(technology[i]);
						technologyId = userDelegate.fetchTechnologyId(techh);
						if (technologyId == 0) {
							techh.setTechnology(technology[i]);
							technologyId = userDelegate.addNewTechnology(techh, user);
							usertechnology.setTechnologyId(technologyId);
							flag = userDelegate.addTechnologyDetails(usertechnology);
						} else {
							usertechnology.setTechnologyId(technologyId);
							flag = userDelegate.addTechnologyDetails(usertechnology);
						}
					}

				}
			}

			session.setAttribute("email", email);
			request.setAttribute("registerSuccess", "yes");
			session.setAttribute("userId",userId);
			userName = userDelegate.fetchUserNameById(userId);
			session.setAttribute("userName", userName);
			mav = new ModelAndView("findjob");
			Company company = new Company();
			ArrayList<Company> companyDetails = null;
			companyDetails = companyDelegate.displayCompanies(company);
			mav.addObject("companyList", companyDetails);

		}

		catch (SQLIntegrityConstraintViolationException e) {

		

			mav = new ModelAndView("signup");

		}

		catch (Exception e) {

			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/register/admin", method = RequestMethod.POST)
	public ModelAndView registerAdminProcess(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = null;
		try {
			HttpSession session = request.getSession();
			int userId = 0, flag = 0;
			String userName = "";
			User user = new User();
			Company company = new Company();
			String name = request.getParameter("userName");
			String password = request.getParameter("psw");
			String confirmPassword = request.getParameter("cpsw");
			String email = request.getParameter("email");
			String companyid = request.getParameter("companyName");

			String companyname = companyDelegate.fetchCompanyName(Integer.parseInt(companyid));

			user.setUserName(name);
			user.setEmail(email);
			user.setPassword(password);
			user.setCompany(companyname);
			user.setDesignation("admin");
			user.setRoleId(2);

			if (userDelegate.registerAsAdmin(user)) {
				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				userDelegate.insertIntoUser(user);

				if (userId != 0) {
					user.setUserId(userId);
					company.setCompanyId(Integer.parseInt(companyid));
					flag = userDelegate.insertIntoAdmin(user, company);
					CompanyDelegate.insertIntoCompanyDetails(user, company);
					if (flag == 1) {

						userName = userDelegate.fetchUserNameById(userId);
						session.setAttribute("userName", userName);
						session.setAttribute("email", email);
						mav = new ModelAndView("admin");
						mav.addObject("registerSuccess", "yes");
						
						user.setEmail(user.getEmail());
					
						int companyId=0;
						userId = userDelegate.fetchUserId(user);
						user.setUserId(userId);
						session.setAttribute("userId",userId);
						companyId = userDelegate.fetchCompanyIdByAdmin(user);
						company.setCompanyId(companyId);
						int appliedUsers=companyDelegate.numberOfAppliedUsers(company);
						int postedJobs = companyDelegate.numberOfVacancyPublished(company);
						mav.addObject("appliedUsers",appliedUsers);
						mav.addObject("postedJobs",postedJobs);
					}
				}

			}

		}

		catch (SQLIntegrityConstraintViolationException e) {

		
			mav = new ModelAndView("signup");

		}

		catch (Exception e) {

			mav = new ModelAndView("error");
		}

		return mav;
	}
	
	
	@RequestMapping(value = "/users/appliedjobs", method = RequestMethod.GET)
	public ModelAndView DisplayAppliedJobs(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewinterestedjobs");
		try {
			HttpSession session=request.getSession(); 
			if(session.getAttribute("email")==null){
				//response.sendRedirect("index.jsp");
			}
			ArrayList<Company> appliedJobs = null;
			
			int userId =(Integer)session.getAttribute("userId"); 
			User user= new User();
			user.setUserId(userId);	
			appliedJobs=companyDelegate.viewAppliedJobs(user);
		
			if (appliedJobs.isEmpty()) {
				
				mav.addObject("noAppliedJobs","yes");
				
			} else {
				
					mav.addObject("appliedJobs", appliedJobs);	
			}
			
		}catch(Exception e) {
			mav = new ModelAndView("error");
		
		}
		return mav;
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void Logout(HttpServletRequest request, HttpServletResponse response) {
		//ModelAndView mav = new ModelAndView("index");
		try {
			HttpSession session = request.getSession();
			
			if (session != null) {
			  
			    response.setHeader("Cache-Control", "no-cache");
			    response.setHeader("Pragma","no-cache");
			    response.setDateHeader("max-age",0);
			    response.setDateHeader("Expires",0);
			    session.invalidate();
			    response.sendRedirect("index.jsp");
			}
			
		}catch(Exception e) {
			//mav = new ModelAndView("error");
		}
		//return mav;
	}
	@RequestMapping(value = "/users/update", method = RequestMethod.GET)
	public ModelAndView ViewUsers(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewprofile");
		try {
			
			HttpSession session = request.getSession();
			if(session.getAttribute("email")==null){
				response.sendRedirect("index.jsp");
			}
	
			int userId =(Integer)session.getAttribute("userId"); 
			User user= new User();
		
			user.setUserId(userId);
			ArrayList<User> userList = null;
			UserTechnologyMapping userTechnologyMapping = new UserTechnologyMapping();
			ArrayList<UserTechnologyMapping> userTechnology = null;
			userList = userDelegate.retrieveUserData(user);
			userTechnology = userDelegate.displayUserTechnologies(userTechnologyMapping, user);
			Technology technology = new Technology();
			ArrayList<Technology> tech = null;
			tech = userDelegate.displayTechnologies(technology);
			mav.addObject("technologies",tech);
			mav.addObject("userData", userList);
			if(userTechnology.isEmpty()) {
				mav.addObject("userTech", userTechnology);
			}else {
				mav.addObject("userTech", userTechnology);
			}
			
			
			}catch(Exception e) {
				mav = new ModelAndView("error");
			}
		return mav;
	}
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView UpdateUsers(@RequestParam("username") String userName,@RequestParam("cname") String companyName,
			@RequestParam("designation") String designation,HttpSession session,@RequestParam("skillset") String skills) {
		ModelAndView mav = new ModelAndView("viewprofile");
		try {
		
			
			int userId =(Integer)session.getAttribute("userId"); 
			String[] technology;
		
			int technologyId=0;
			UserTechnologyMapping usertechnology = new UserTechnologyMapping();
			UserTechnologyMapping userTechnologyMapping = new UserTechnologyMapping();
			ArrayList<UserTechnologyMapping> userTechnology = null;
			ArrayList<User> userList = null;
			
			Technology techh = new Technology();
			
			User user= new User();
			
			int flag=0;
			user.setUserId(userId);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			user.setCurrentTime(dtf.format(now));
	
			user.setUserName(userName);
			if(userDelegate.updateUserName(user)) {
					
			}
			user.setCompany(companyName);
			if(userDelegate.updateCompanyName(user)) {
					
			}
			user.setDesignation(designation);
			if(userDelegate.updateUserDesignation(user)) {
				
			}
			
			if (skills != "") {
				technology = skills.split("@");
				if (technology != null) {

					userTechnology = userDelegate.displayUserTechnologies(userTechnologyMapping, user);
					if(userTechnology.isEmpty()) {
					}else {
					userDelegate.deleteTechnologyDetails(userTechnologyMapping,user);
					}
					for (int i = 0; i < technology.length; i++) {
						
					
						usertechnology.setUserId(user.getUserId());
						techh.setTechnology(technology[i]);
						technologyId = userDelegate.fetchTechnologyId(techh);
						if (technologyId == 0) {
							techh.setTechnology(technology[i]);
							technologyId = userDelegate.addNewTechnology(techh, user);
							usertechnology.setTechnologyId(technologyId);
							flag = userDelegate.addTechnologyDetails(usertechnology);
						} else {
							usertechnology.setTechnologyId(technologyId);
							flag = userDelegate.addTechnologyDetails(usertechnology);
						}
					}

				}	
			}
			
			   	userList = userDelegate.retrieveUserData(user);
				userTechnology = userDelegate.displayUserTechnologies(userTechnologyMapping, user); 
				mav.addObject("userData", userList); 
				mav.addObject("userTech", userTechnology); 
				mav.addObject("updated","yes");
					
			}catch(Exception e) {
				mav = new ModelAndView("error");
			}
		return mav;
	}
	
	@RequestMapping(value = "/users/request", method = RequestMethod.GET)
	public ModelAndView ViewRequestVacancy(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("requestvacancy");
		try {
			
			HttpSession session = request.getSession();
			if(session.getAttribute("email")==null){
				//response.sendRedirect("index.jsp");
			}
			
			JobMapping jobMapping = new JobMapping();
			ArrayList<JobMapping> job = new ArrayList<JobMapping>();
			JobDelegate jobDelegate = new JobDelegate();
			job = jobDelegate.displayJobs(jobMapping);
			mav.addObject("jobs", job); 
			
			}catch(Exception e) {
				mav = new ModelAndView("error");
			}
		return mav;
	}
	@RequestMapping(value = "/users/request", method = RequestMethod.POST)
	public ModelAndView RequestVacancy(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("requestvacancy");
		try {
			
	
			int jobId=0;
			HttpSession session = request.getSession();
			
			int userId =(Integer)session.getAttribute("userId"); 
			User user= new User();
			
			JobRequest jobrequest = new JobRequest();
			
			String jobDesignation = request.getParameter("job");
			String location = request.getParameter("location");
			String salary = request.getParameter("salary");
			
			jobrequest.setEmail(user.getEmail());
			
			jobId = Integer.parseInt(jobDesignation);
			jobrequest.setJobId(jobId);
			jobrequest.setLocation(location);
			jobrequest.setSalary(Float.parseFloat(salary));
			user.setUserId(userId);
			JobMapping jobMapping = new JobMapping();
			
			ArrayList<JobMapping> job = null;
			JobDelegate jobDelegate = new JobDelegate();
			job = jobDelegate.displayJobs(jobMapping);
			request.setAttribute("jobs", job); 
			if(userDelegate.requestNewVacancy(jobrequest, user)) {
				mav.addObject("saved","yes");
				
			}else {
				mav = new ModelAndView("error");
			}
		}catch(Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}
	
	@RequestMapping(value = "/users/admin", method = RequestMethod.GET)
	public ModelAndView showAdminPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("admin");
		try {
			HttpSession session=request.getSession(); 
			if(session.getAttribute("email")==null){
				response.sendRedirect("index.jsp");
			}
		
			int userId =(Integer)session.getAttribute("userId"); 
			User user= new User();
			Company company = new Company();
			
			int companyId=0;
			user.setUserId(userId);
			companyId = userDelegate.fetchCompanyIdByAdmin(user);
			company.setCompanyId(companyId);
			int appliedUsers=companyDelegate.numberOfAppliedUsers(company);
			int postedJobs = companyDelegate.numberOfVacancyPublished(company);
			mav.addObject("appliedUsers",appliedUsers);
			mav.addObject("postedJobs",postedJobs);
			
		}catch(Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

}
