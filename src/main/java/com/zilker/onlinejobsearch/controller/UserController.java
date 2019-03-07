package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

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

			ArrayList<Company> displayCompanies = null;
			displayCompanies = companyDelegate.displayCompanies();
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
			session.setAttribute("userId", userId);

			if (role == 0) {

				mav = new ModelAndView("login");

				ArrayList<Company> displayCompanies = null;
				displayCompanies = companyDelegate.displayCompanies();
				mav.addObject("companies", displayCompanies);
				mav.addObject("loginError", "error");
			} else if (role == 1) {

				mav = new ModelAndView("findjob");
				ArrayList<Company> companyDetails = null;
				companyDetails = companyDelegate.displayCompanies();
				mav.addObject("companyList", companyDetails);

			} else if (role == 2) {
				mav = new ModelAndView("admin");

				Company company = new Company();
				user.setEmail(user.getEmail());
				int companyId = 0;
				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				company.setCompanyId(companyId);
				int appliedUsers = companyDelegate.numberOfAppliedUsers(company);
				int postedJobs = companyDelegate.numberOfVacancyPublished(company);
				mav.addObject("appliedUsers", appliedUsers);
				mav.addObject("postedJobs", postedJobs);
			}

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ModelAndView registerProcess(@RequestParam("userName") String name,@RequestParam("psw") String password,@RequestParam("cpsw") String confirmPassword,
			@RequestParam("email") String email,@RequestParam("companyName") String companyName,@RequestParam("designation") String designation,
			@RequestParam("skillset") String skills,HttpSession session) {
		ModelAndView mav = null;
		try {
			
			int userId = 0;			
			User user = new User();
			user.setEmail(email);

			if (userDelegate.register(name,email,password,companyName,designation)) {

				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				userDelegate.insertIntoUser(user);

			}
			userDelegate.addSkillsToProfile(skills,userId);
			
	
			mav = new ModelAndView("findjob");
			session.setAttribute("email", email);
			mav.addObject("registerSuccess", "yes");
			session.setAttribute("userId", userId);
			session.setAttribute("userName", name);
			

			ArrayList<Company> companyDetails = null;
			companyDetails = companyDelegate.displayCompanies();
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
	public ModelAndView registerAdminProcess(@RequestParam("userName") String name,@RequestParam("psw") String password,@RequestParam("cpsw") String confirmPassword,
			@RequestParam("email") String email,@RequestParam("companyName") String companyId,HttpSession session) {
		ModelAndView mav = new ModelAndView("admin");
		try {
		
			int userId = 0, flag = 0;
			User user = new User();
			Company company = new Company();
						

			if (userDelegate.registerAsAdmin(name,email,password,companyId)) {
				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				user.setEmail(email);
				userDelegate.insertIntoUser(user);

				if (userId != 0) {
					user.setUserId(userId);
					company.setCompanyId(Integer.parseInt(companyId));
					flag = userDelegate.insertIntoAdmin(userId, Integer.parseInt(companyId));
					companyDelegate.insertIntoCompanyDetails(userId,  Integer.parseInt(companyId));
					if (flag == 1) {

						session.setAttribute("userName", name);
						session.setAttribute("email", email);
						mav.addObject("registerSuccess", "yes");
						session.setAttribute("userId", userId);
						company.setCompanyId(Integer.parseInt(companyId));
						mav.addObject("appliedUsers",companyDelegate.numberOfAppliedUsers(company));
						mav.addObject("postedJobs", companyDelegate.numberOfVacancyPublished(company));
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
			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				ArrayList<Company> appliedJobs = null;

				int userId = (Integer) session.getAttribute("userId");
				appliedJobs = companyDelegate.viewAppliedJobs(userId);

				if (appliedJobs.isEmpty()) {
					mav.addObject("noAppliedJobs", "yes");
				} else {
					mav.addObject("appliedJobs", appliedJobs);
				}
			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView Logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("home");
		try {

			HttpSession session = request.getSession();
			if (session != null) {

				response.setHeader("Cache-Control", "no-cache");
				response.setHeader("Pragma", "no-cache");
				response.setDateHeader("max-age", 0);
				response.setDateHeader("Expires", 0);
				session.invalidate();
			}

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.GET)
	public ModelAndView ViewUsers(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewprofile");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				
				ArrayList<User> userList = null;
				UserTechnologyMapping userTechnologyMapping = new UserTechnologyMapping();
				ArrayList<UserTechnologyMapping> userTechnology = null;
				userList = userDelegate.retrieveUserData(userId);
				userTechnology = userDelegate.displayUserTechnologies(userTechnologyMapping, userId);
				mav.addObject("userData", userList);
				
				if (userTechnology.isEmpty()) {
					mav.addObject("userTech", userTechnology);
				} else {
					mav.addObject("userTech", userTechnology);
				}

			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	@ResponseBody
	public void UpdateUsers(@RequestParam("username") String userName, @RequestParam("cname") String companyName,
			@RequestParam("designation") String designation, HttpSession session,
			@RequestParam("skillset") String skills,HttpServletResponse response)
			throws IOException {
	    	PrintWriter out = response.getWriter();
		try {

			if (session.getAttribute("email") == null) {
				response.sendRedirect("home.jsp");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				if(userDelegate.updateUserProfile(userName,companyName,designation,skills,userId)) {
				out.print("success");
				out.flush();
				}
			}
		} catch (Exception e) {

			out.print("error");
			out.flush();
		}

	}

	@RequestMapping(value = "/users/request", method = RequestMethod.GET)
	public ModelAndView ViewRequestVacancy(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("requestvacancy");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				
				ArrayList<JobMapping> job = new ArrayList<JobMapping>();
				JobDelegate jobDelegate = new JobDelegate();
				job = jobDelegate.displayJobs();
				mav.addObject("jobs", job);
			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users/request", method = RequestMethod.POST)
	public void RequestVacancy(@RequestParam("job") String jobDesignation, @RequestParam("location") String location,
			@RequestParam("salary") String salary, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		try {

			if (session.getAttribute("email") == null) {
				response.sendRedirect("home.jsp");
			} else {
				

				int userId = (Integer) session.getAttribute("userId");
				String email = (String) session.getAttribute("email");
					
				ArrayList<JobMapping> job = null;
				JobDelegate jobDelegate = new JobDelegate();
				job = jobDelegate.displayJobs();
				request.setAttribute("jobs", job);
				if (userDelegate.requestNewVacancy(email,userId,jobDesignation,location,salary)) {

					out.print("success");
					out.flush();
				} else {

					out.print("error");
					out.flush();
				}
			}
		} catch (Exception e) {

			out.print("error");
			out.flush();
		}

	}

	@RequestMapping(value = "/users/admin", method = RequestMethod.GET)
	public ModelAndView showAdminPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("admin");
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				User user = new User();
				Company company = new Company();

				int companyId = 0;
				user.setUserId(userId);
				companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				company.setCompanyId(companyId);
				mav.addObject("appliedUsers", companyDelegate.numberOfAppliedUsers(company));
				mav.addObject("postedJobs",  companyDelegate.numberOfVacancyPublished(company));
			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

}
