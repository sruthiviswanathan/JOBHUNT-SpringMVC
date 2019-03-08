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

	@Autowired
	JobDelegate jobDelegate;

	/*
	 * controller that gets all companies
	 */
	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("login");
		try {

			ArrayList<Company> displayCompanies = companyDelegate.displayCompanies();
			model.addObject("companies", displayCompanies);
			model.addObject("login", new User());
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller that does login process and redirects user based on their role
	 */
	@RequestMapping(value = "/users-login", method = RequestMethod.POST)
	public ModelAndView loginProcess(HttpSession session, @ModelAttribute("login") User user) {
		ModelAndView model = null;
		try {

			int role = userDelegate.login(user);
			int userId = userDelegate.fetchUserId(user.getEmail());
			String userName = userDelegate.fetchUserNameById(userId);
			session.setAttribute("email", user.getEmail());
			session.setAttribute("userName", userName);
			session.setAttribute("userId", userId);

			if (role == 0) {

				model = new ModelAndView("login");
				ArrayList<Company> displayCompanies = companyDelegate.displayCompanies();
				model.addObject("companies", displayCompanies);
				model.addObject("loginError", "error");

			} else if (role == 1) {

				model = new ModelAndView("findjob");
				ArrayList<Company> companyDetails = companyDelegate.displayCompanies();
				model.addObject("companyList", companyDetails);

			} else if (role == 2) {

				model = new ModelAndView("admin");
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				model.addObject("appliedUsers", companyDelegate.numberOfAppliedUsers(companyId));
				model.addObject("postedJobs", companyDelegate.numberOfVacancyPublished(companyId));
			}

		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller that does register process
	 */

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ModelAndView registerProcess(@RequestParam("userName") String name, @RequestParam("psw") String password,
			@RequestParam("cpsw") String confirmPassword, @RequestParam("email") String email,
			@RequestParam("companyName") String companyName, @RequestParam("designation") String designation,
			@RequestParam("skillset") String skills, HttpSession session) {
		ModelAndView model = null;
		try {

			if (userDelegate.register(name, email, password, companyName, designation, skills)) {
				model = new ModelAndView("findjob");
				int userId = userDelegate.fetchUserId(email);
				session.setAttribute("email", email);
				session.setAttribute("userId", userId);
				session.setAttribute("userName", name);
				model.addObject("registerSuccess", "yes");
				ArrayList<Company> companyDetails = companyDelegate.displayCompanies();
				model.addObject("companyList", companyDetails);
			}

		} catch (SQLIntegrityConstraintViolationException e) {
			model = new ModelAndView("signup");
		}

		catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller for admin registration
	 */
	@RequestMapping(value = "/register/admin", method = RequestMethod.POST)
	public ModelAndView registerAdminProcess(@RequestParam("userName") String name,
			@RequestParam("psw") String password, @RequestParam("cpsw") String confirmPassword,
			@RequestParam("email") String email, @RequestParam("companyName") String companyId, HttpSession session) {
		ModelAndView model = new ModelAndView("admin");
		try {
			if (userDelegate.registerAsAdmin(name, email, password, companyId)) {
				session.setAttribute("userName", name);
				session.setAttribute("email", email);
				int userId = userDelegate.fetchUserId(email);
				session.setAttribute("userId", userId);
				model.addObject("registerSuccess", "yes");
				model.addObject("appliedUsers", companyDelegate.numberOfAppliedUsers(Integer.parseInt(companyId)));
				model.addObject("postedJobs", companyDelegate.numberOfVacancyPublished(Integer.parseInt(companyId)));
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			model = new ModelAndView("signup");
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller for displaying applied jobs to user
	 */
	@RequestMapping(value = "/users/appliedjobs", method = RequestMethod.GET)
	public ModelAndView DisplayAppliedJobs(HttpSession session) {
		ModelAndView model = new ModelAndView("viewinterestedjobs");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				ArrayList<Company> appliedJobs = companyDelegate.viewAppliedJobs(userId);
				model.addObject("appliedJobs", appliedJobs);
			}

		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * logout controller
	 */

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView Logout(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("home");
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
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller for fetching user details and displaying user profile
	 */
	@RequestMapping(value = "/users/update", method = RequestMethod.GET)
	public ModelAndView ViewUsers(HttpSession session) {
		ModelAndView model = new ModelAndView("viewprofile");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {

				UserTechnologyMapping userTechnologyMapping = new UserTechnologyMapping();
				int userId = (Integer) session.getAttribute("userId");
				ArrayList<User> userList = userDelegate.retrieveUserData(userId);
				ArrayList<UserTechnologyMapping> userTechnology = userDelegate.displayUserTechnologies(userTechnologyMapping, userId);
				model.addObject("userData", userList);
				model.addObject("userTech", userTechnology);
			}

		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller for updating user profile
	 */
	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	public void UpdateUsers(@RequestParam("username") String userName, @RequestParam("cname") String companyName,
			@RequestParam("designation") String designation, HttpSession session,
			@RequestParam("skillset") String skills, HttpServletResponse response) throws IOException {

		PrintWriter out = response.getWriter();
		try {
			if (session.getAttribute("email") == null) {
				response.sendRedirect("home.jsp");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				if (userDelegate.updateUserProfile(userName, companyName, designation, skills, userId)) {
					out.print("success");
					out.flush();
				}
			}
		} catch (Exception e) {

			out.print("error");
			out.flush();
		}
	}

	/*
	 * controller for fetching job designation and displaying request vacancy page
	 */
	@RequestMapping(value = "/users/request", method = RequestMethod.GET)
	public ModelAndView ViewRequestVacancy(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		ModelAndView model = new ModelAndView("requestvacancy");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
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
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				request.setAttribute("jobs", job);
					if (userDelegate.requestNewVacancy(email, userId, jobDesignation, location, salary)) {
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
	public ModelAndView showAdminPage(HttpSession session) {
		ModelAndView model = new ModelAndView("admin");
		try {
			if (session.getAttribute("email") == null) {
				new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				model.addObject("appliedUsers", companyDelegate.numberOfAppliedUsers(companyId));
				model.addObject("postedJobs", companyDelegate.numberOfVacancyPublished(companyId));
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

}
