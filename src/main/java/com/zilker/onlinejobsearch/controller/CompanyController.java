package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.JobMapping;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.JobDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;

@Controller
public class CompanyController {

	@Autowired
	CompanyDelegate companyDelegate;

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	JobDelegate jobDelegate;

	@RequestMapping(value = "/findjobs", method = RequestMethod.GET)
	public ModelAndView DisplayFindJobs(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("findjob");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				response.sendRedirect("index.jsp");
			}

			Company company = new Company();
			ArrayList<Company> companyDetails = null;
			companyDetails = companyDelegate.displayCompanies(company);
			mav.addObject("companyList", companyDetails);

		} catch (Exception e) {

		}
		return mav;
	}

	@RequestMapping(value = "/findcompany", method = RequestMethod.GET)
	public ModelAndView DisplayFindCompany(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("findcompany");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				response.sendRedirect("index.jsp");
			}

			Company company = new Company();
			ArrayList<Company> companyDetails = null;
			companyDetails = companyDelegate.displayCompanies(company);
			mav.addObject("companyList", companyDetails);

		} catch (Exception e) {

		}
		return mav;
	}

	@RequestMapping(value = "/findlocation", method = RequestMethod.GET)
	public ModelAndView DisplayFindLocation(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("searchbylocation");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				response.sendRedirect("index.jsp");
			}

			Company company = new Company();
			ArrayList<Company> companyDetails = null;
			companyDetails = companyDelegate.displayCompanies(company);
			mav.addObject("companyList", companyDetails);

		} catch (Exception e) {

		}
		return mav;
	}

	@RequestMapping(value = "/companies", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView AddNewCompany(@RequestParam("companyName") String companyName,
			@RequestParam("websiteUrl") String websiteUrl, @RequestParam("companyLogo") String companyLogo, HttpSession session) {
		ModelAndView mav = new ModelAndView("login");
		try {
			Company company = new Company();

			company.setCompanyName(companyName);
			company.setCompanyWebsiteUrl(websiteUrl);
			company.setCompanyLogo(companyLogo);
			if (companyDelegate.addNewCompany(company)) {

				mav = new ModelAndView("signup");
				Company company1 = new Company();
				ArrayList<Company> displayCompanies = null;
				displayCompanies = companyDelegate.displayCompanies(company1);
				mav.addObject("companies", displayCompanies);
				mav.addObject("login", new User());
			} else {

				mav = new ModelAndView("error");
			}

		} catch (Exception e) {

			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/company", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView findCompany(@RequestParam("companyName") String companyName, HttpSession session) {
		ModelAndView mav = new ModelAndView("companydetails");
		try {
			int companyId = 0;
			// HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				// response.sendRedirect("index.jsp");
			}
			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);

			ArrayList<Company> companyDetails = null;
			ArrayList<Company> vacancyDetails = null;
			ArrayList<Company> companyReviews = null;
			Company company = new Company();

			int userId = 0;
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);

			company.setCompanyName(companyName);
			companyId = companyDelegate.fetchCompanyId(company);
			if (companyId == 0) {
				mav = new ModelAndView("errorcompanyresults");
				mav.addObject("noCompany", "yes");

			} else {
				company.setCompanyId(companyId);
				companyDetails = companyDelegate.retrieveVacancyByCompany(company);

				mav.addObject("displayCompany", companyDetails);

				vacancyDetails = companyDelegate.retrieveVacancyByCompany1(company, user);

				if (vacancyDetails.isEmpty()) {

					mav.addObject("noVacancy", "yes");
					companyReviews = userDelegate.retrieveReview(company);

					if (companyReviews.isEmpty()) {
						mav.addObject("noReviews", "yes");

					} else {

						mav.addObject("displayCompanyReviews", companyReviews);

					}

				} else {
					for (Company i : vacancyDetails) {
						int jobId = i.getJobId();
						mav.addObject("displayVacancies", vacancyDetails);

						company.setJobId(jobId);

					}
				}
				companyReviews = userDelegate.retrieveReview(company);

				if (companyReviews.isEmpty()) {
					mav.addObject("noReviews", "yes");

				} else {

					mav.addObject("displayCompanyReviews", companyReviews);

				}

			}

		} catch (SQLException e) {

		}
		return mav;
	}

	
	@RequestMapping(value = "/location/companies", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView findByLocation(@RequestParam("location") String location, HttpSession session) {
		ModelAndView mav = new ModelAndView("viewbylocation");
		try {

			String email = (String) session.getAttribute("email");
			if (session.getAttribute("email") == null) {
				// response.sendRedirect("index.jsp");
			}
			User user = new User();
			user.setEmail(email);
			ArrayList<Company> retrieveByLocation = null;
			Company company = new Company();
			int userId = 0;
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);

			company.setLocation(location);
			retrieveByLocation = companyDelegate.retrieveVacancyByLocation(company, user);
			if (retrieveByLocation.isEmpty()) {

				mav.addObject("noVacancy", "yes");

			} else {

				mav.addObject("retrieveByLocation", retrieveByLocation);

			}

		} catch (SQLException e) {

			mav = new ModelAndView("error");

		}
		return mav;
	}

	@RequestMapping(value = "/company/reviews", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView viewCompanyReviews(@RequestParam("company") String companyName, HttpSession session) {
		ModelAndView mav = new ModelAndView("viewallreviews");
		try {
			int companyId = 0;
			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			ArrayList<Company> companyReviews = null;
			ArrayList<Company> companyDetails = null;
			Company company = new Company();

			company.setCompanyName(companyName);
			companyId = companyDelegate.fetchCompanyId(company);
			company.setCompanyId(companyId);
			companyDetails = companyDelegate.retrieveVacancyByCompany(company);

			mav.addObject("displayCompany", companyDetails);

			companyReviews = userDelegate.retrieveReview(company);
			if (companyReviews.isEmpty()) {
				mav.addObject("noReviews", "yes");
			} else {

				mav.addObject("displayCompanyReviews", companyReviews);

			}

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/company/interviews", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView viewReviewsOnInterviewProcess(@RequestParam("company") String companyName,
			HttpSession session) {
		ModelAndView mav = new ModelAndView("interviewprocess");
		try {
			int companyId = 0;
			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			ArrayList<Company> interviewProcess = null;
			ArrayList<Company> companyDetails = null;
			Company company = new Company();

			company.setCompanyName(companyName);
			companyId = companyDelegate.fetchCompanyId(company);
			company.setCompanyId(companyId);

			companyDetails = companyDelegate.retrieveVacancyByCompany(company);

			mav.addObject("displayCompany", companyDetails);

			interviewProcess = userDelegate.retrieveInterviewProcess(company);
			if (interviewProcess.isEmpty()) {

				mav.addObject("noReviews", "yes");
			}

			mav.addObject("displayInterviewProcess", interviewProcess);

		} catch (Exception e) {
			mav = new ModelAndView("error");

		}
		return mav;
	}

	@RequestMapping(value = "/company/rate", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView ViewRatingPage(@RequestParam("companyname") String companyName, HttpSession session) {
		ModelAndView mav = new ModelAndView("reviewandrating");
		try {

			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			if (session.getAttribute("email") == null) {
				// response.sendRedirect("index.jsp");
			}
			JobMapping jobMapping = new JobMapping();
			ArrayList<JobMapping> job = null;
			job = jobDelegate.displayJobs(jobMapping);
			mav.addObject("jobs", job);
			mav.addObject("companyname", companyName);

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/company/rate", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView RateACompany(@RequestParam("company") String companyName, @RequestParam("rating") String rating,
			@RequestParam("review") String review, @RequestParam("job") String jobRole,
			@RequestParam("interview") String interviewProcess, HttpSession session) {
		ModelAndView mav = new ModelAndView("findcompany");
		try {
			int companyId = 0, userId = 0, jobId = 0;

			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			Company company = new Company();
			JobMapping jobmapping = new JobMapping();

			company.setCompanyName(companyName);
			companyId = companyDelegate.fetchCompanyId(company);
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);
			company.setCompanyId(companyId);
			company.setReview(review);
			company.setRating(Float.parseFloat(rating));

			if (userDelegate.reviewAndRateCompany(user, company)) {
				mav = new ModelAndView("findcompany");
				Company companies = new Company();
				ArrayList<Company> companyDetails = null;
				companyDetails = companyDelegate.displayCompanies(companies);
				mav.addObject("companyList", companyDetails);

			} else {

				mav = new ModelAndView("error");
			}

			jobmapping.setJobRole(jobRole);
			jobId = jobDelegate.fetchJobId(jobmapping);
			jobmapping.setJobId(jobId);
			company.setInterviewProcess(interviewProcess);
			if (jobRole != "" && interviewProcess != "") {
				if (userDelegate.interviewProcess(user, company, jobmapping)) {
					mav = new ModelAndView("findcompany");
					Company companies = new Company();
					ArrayList<Company> companyDetails = null;
					companyDetails = companyDelegate.displayCompanies(companies);
					mav.addObject("companyList", companyDetails);

				} else {

					mav = new ModelAndView("error");
				}
			}

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users/applied", method = RequestMethod.GET)
	public ModelAndView AppliedUsers(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewinterestedusers");
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				// response.sendRedirect("index.jsp");
			}
			ArrayList<Company> appliedUsers = null;

			String email = (String) session.getAttribute("email");
			User user = new User();
			Company company = new Company();
			user.setEmail(email);
			int userId = 0, companyId = 0;
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);
			companyId = userDelegate.fetchCompanyIdByAdmin(user);
			company.setCompanyId(companyId);
			appliedUsers = companyDelegate.viewAppliedUsers(company);
			if (appliedUsers.isEmpty()) {

				mav.addObject("noInterestedUsers", "yes");

			} else {
				mav.addObject("appliedUsers", appliedUsers);

			}

		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/users/contacted", method = RequestMethod.POST)
	@ResponseBody
	public void UpdateContactedUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/html;charset=UTF-8");

			int companyId = 0, jobId = 0, userId = 0;
			Company company = new Company();
			JobMapping jobMapping = new JobMapping();

			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);

			String location = request.getParameter("location");
			String emailId = request.getParameter("emailId");
			String jobDesignation = request.getParameter("job");

			companyId = userDelegate.fetchCompanyIdByAdmin(user);
			jobMapping.setJobRole(jobDesignation);
			jobId = jobDelegate.fetchJobId(jobMapping);
			company.setEmail(emailId);
			company.setCompanyId(companyId);
			company.setJobId(jobId);
			company.setLocation(location);

			if (userDelegate.markContacted(company, user)) {

				response.setContentType("application/json");
				out.print("success");
				out.flush();

			} else {

			}
		}

		catch (Exception e) {

		}
	}

	@RequestMapping(value = "company/jobspublished", method = RequestMethod.GET)
	public ModelAndView ViewPublishedJobs(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewpublishedjobs");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				// response.sendRedirect("index.jsp");
			}
			response.setContentType("text/html;charset=UTF-8");
			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			Company company = new Company();
			ArrayList<Company> vacancyDetails = null;

			int userId = 0, companyId = 0;
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);
			companyId = userDelegate.fetchCompanyIdByAdmin(user);
			company.setCompanyId(companyId);
			vacancyDetails = companyDelegate.retrieveVacancyByCompanyAdmin(company);

			JobMapping jobMapping = new JobMapping();
			ArrayList<JobMapping> job = null;
			job = jobDelegate.displayJobs(jobMapping);
			mav.addObject("jobs", job);
			if (vacancyDetails.isEmpty()) {

				mav.addObject("noVacancy", "yes");
			} else {
				mav.addObject("vacancyDetails", vacancyDetails);

			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "company/jobspublished", method = RequestMethod.POST)
	public ModelAndView UpdatePublishedJobs(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewpublishedjobs");
		try {

			int oldJobId = 0, newJobId = 0, userId = 0, companyId = 0;
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			User user = new User();
			user.setEmail(email);
			ArrayList<Company> vacancyDetails = null;

			Company company = new Company();
			JobMapping jobMapping = new JobMapping();

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
			LocalDateTime now = LocalDateTime.now();
			user.setCurrentTime(dtf.format(now));
			String action = request.getParameter("action");
			String jobDesignation = request.getParameter("jobdesignation");

			jobMapping.setJobRole(jobDesignation);
			oldJobId = jobDelegate.fetchJobId(jobMapping);
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);
			companyId = userDelegate.fetchCompanyIdByAdmin(user);
			company.setCompanyId(companyId);

			if ("UPDATE".equals(action)) {

				company.setOldJobId(oldJobId);
				String newJobDesignation = request.getParameter("job");

				newJobId = Integer.parseInt(newJobDesignation);
				company.setJobId(newJobId);
				if (companyDelegate.updateVacancyJobId(company, user)) {
					mav.addObject("status", "updated");

				}

				String location = request.getParameter("location");
				company.setLocation(location);
				if (companyDelegate.updateVacancyLocation(company, user)) {
					mav.addObject("status", "updated");
				}

				String jobDescription = request.getParameter("description");
				company.setJobDescription(jobDescription);
				if (companyDelegate.updateVacancyDescription(company, user)) {
					mav.addObject("status", "updated");
				}

				String salary = request.getParameter("salary");
				company.setSalary(Float.parseFloat(salary));
				if (companyDelegate.updateVacancySalary(company, user)) {
					mav.addObject("status", "updated");

				}

				String count = request.getParameter("count");
				int vacancyCount = Integer.parseInt(count);
				company.setVacancyCount(vacancyCount);
				if (vacancyCount == 0) {
					company.setVacancyStatus("expired");
				} else {
					company.setVacancyStatus("available");
				}
				if (companyDelegate.updateVacancyCount(company, user)) {
					mav.addObject("status", "updated");
				}

			} else if ("DELETE".equals(action)) {

				company.setJobId(oldJobId);
				if (companyDelegate.removeVacancy(company, user)) {
					mav.addObject("status", "deleted");
				}

			}

			vacancyDetails = companyDelegate.retrieveVacancyByCompanyAdmin(company);

			JobMapping jobs = new JobMapping();
			ArrayList<JobMapping> job = null;
			job = jobDelegate.displayJobs(jobs);
			mav.addObject("jobs", job);
			if (vacancyDetails.isEmpty()) {

				mav.addObject("noVacancy", "yes");
			} else {
				mav.addObject("vacancyDetails", vacancyDetails);

			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}
}
