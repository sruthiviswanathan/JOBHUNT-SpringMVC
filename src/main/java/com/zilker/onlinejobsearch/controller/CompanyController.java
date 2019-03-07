package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

	/*
	 * fetch all company details and display findByJobs page
	 */
	@RequestMapping(value = "/findjobs", method = RequestMethod.GET)
	public ModelAndView DisplayFindJobs(HttpSession session) {
		ModelAndView model = new ModelAndView("findjob");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				ArrayList<Company> companyDetails = companyDelegate.displayCompanies();
				model.addObject("companyList", companyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * fetch all company details and display findByCompany page
	 */
	@RequestMapping(value = "/findcompany", method = RequestMethod.GET)
	public ModelAndView DisplayFindCompany(HttpSession session) {
		ModelAndView model = new ModelAndView("findcompany");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				ArrayList<Company> companyDetails = companyDelegate.displayCompanies();
				model.addObject("companyList", companyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * fetch all company details and display findByLocation page
	 */
	@RequestMapping(value = "/findlocation", method = RequestMethod.GET)
	public ModelAndView DisplayFindLocation(HttpSession session) {
		ModelAndView model = new ModelAndView("searchbylocation");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				ArrayList<Company> companyDetails = companyDelegate.displayCompanies();
				model.addObject("companyList", companyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	
	/*
	 * controller to add a new company
	 */
	@RequestMapping(value = "/companies", method = RequestMethod.POST)
	public ModelAndView AddNewCompany(@RequestParam("companyName") String companyName,
			@RequestParam("websiteUrl") String websiteUrl, @RequestParam("companyLogo") String companyLogo,
			HttpSession session) {
		ModelAndView model = new ModelAndView("login");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {

				if (companyDelegate.addNewCompany(companyName, websiteUrl, companyLogo)) {

					model = new ModelAndView("signup");
					ArrayList<Company> displayCompanies = companyDelegate.displayCompanies();
					model.addObject("companies", displayCompanies);
					model.addObject("login", new User());
				} else {
					model = new ModelAndView("error");
				}
			}
		} catch (Exception e) {

			model = new ModelAndView("error");
		}
		return model;
	}

	
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView findCompany(@RequestParam("companyName") String companyName, HttpSession session) {
		ModelAndView model = new ModelAndView("companydetails");
		try {
			int companyId = 0;

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");

				ArrayList<Company> vacancyDetails = null;
				ArrayList<Company> companyReviews = null;
				Company company = new Company();
				companyId = companyDelegate.fetchCompanyId(companyName);
				if (companyId == 0) {
					model = new ModelAndView("errorcompanyresults");
					model.addObject("noCompany", "yes");
				} else {
					ArrayList<Company> companyDetails = companyDelegate.retrieveVacancyByCompany(companyId);
					model.addObject("displayCompany", companyDetails);
					vacancyDetails = companyDelegate.retrieveVacancyByCompany1(companyId, userId);

					if (vacancyDetails.isEmpty()) {
						model.addObject("noVacancy", "yes");
						companyReviews = userDelegate.retrieveReview(companyId);
						if (companyReviews.isEmpty()) {
							model.addObject("noReviews", "yes");
						} else {
							model.addObject("displayCompanyReviews", companyReviews);
						}
					} else {
						for (Company i : vacancyDetails) {
							int jobId = i.getJobId();
							model.addObject("displayVacancies", vacancyDetails);
							company.setJobId(jobId);
						}
					}
					companyReviews = userDelegate.retrieveReview(companyId);
					if (companyReviews.isEmpty()) {
						model.addObject("noReviews", "yes");

					} else {
						model.addObject("displayCompanyReviews", companyReviews);
					}
				}
			}
		} catch (SQLException e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	@RequestMapping(value = "/location/companies", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView findByLocation(@RequestParam("location") String location, HttpSession session) {
		ModelAndView mav = new ModelAndView("viewbylocation");
		try {

			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				ArrayList<Company> retrieveByLocation = null;
				int userId = (Integer) session.getAttribute("userId");
				retrieveByLocation = companyDelegate.retrieveVacancyByLocation(location, userId);
				if (retrieveByLocation.isEmpty()) {
					mav.addObject("noVacancy", "yes");
				} else {
					mav.addObject("retrieveByLocation", retrieveByLocation);
				}
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
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				int companyId = 0;
				ArrayList<Company> companyReviews = null;
				ArrayList<Company> companyDetails = null;
				companyId = companyDelegate.fetchCompanyId(companyName);
				companyDetails = companyDelegate.retrieveVacancyByCompany(companyId);
				mav.addObject("displayCompany", companyDetails);
				companyReviews = userDelegate.retrieveReview(companyId);
				if (companyReviews.isEmpty()) {
					mav.addObject("noReviews", "yes");
				} else {
					mav.addObject("displayCompanyReviews", companyReviews);
				}
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

			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				int companyId = 0;
				ArrayList<Company> interviewProcess = null;
				ArrayList<Company> companyDetails = null;
				companyId = companyDelegate.fetchCompanyId(companyName);
				companyDetails = companyDelegate.retrieveVacancyByCompany(companyId);
				mav.addObject("displayCompany", companyDetails);
				interviewProcess = userDelegate.retrieveInterviewProcess(companyId);
				if (interviewProcess.isEmpty()) {
					mav.addObject("noReviews", "yes");
				}
				mav.addObject("displayInterviewProcess", interviewProcess);
			}
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
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				ArrayList<JobMapping> job = null;
				job = jobDelegate.displayJobs();
				mav.addObject("jobs", job);
				mav.addObject("companyname", companyName);
			}
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

			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {

				int companyId = 0, jobId = 0;
				int userId = (Integer) session.getAttribute("userId");

				User user = new User();
				Company company = new Company();
				JobMapping jobmapping = new JobMapping();

				company.setCompanyName(companyName);
				companyId = companyDelegate.fetchCompanyId(companyName);

				user.setUserId(userId);
				company.setCompanyId(companyId);
				company.setReview(review);
				company.setRating(Float.parseFloat(rating));

				if (userDelegate.reviewAndRateCompany(user, company)) {
					mav = new ModelAndView("findcompany");

					ArrayList<Company> companyDetails = null;
					companyDetails = companyDelegate.displayCompanies();
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

						ArrayList<Company> companyDetails = null;
						companyDetails = companyDelegate.displayCompanies();
						mav.addObject("companyList", companyDetails);

					} else {

						mav = new ModelAndView("error");
					}
				}
			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/applied-users", method = RequestMethod.GET)
	public ModelAndView AppliedUsers(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewinterestedusers");
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				ArrayList<Company> appliedUsers = null;
				int userId = (Integer) session.getAttribute("userId");

				User user = new User();
				Company company = new Company();

				int companyId = 0;
				user.setUserId(userId);
				companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				company.setCompanyId(companyId);
				appliedUsers = companyDelegate.viewAppliedUsers(company);
				if (appliedUsers.isEmpty()) {

					mav.addObject("noInterestedUsers", "yes");

				} else {
					mav.addObject("appliedUsers", appliedUsers);

				}
			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}

	@RequestMapping(value = "/contacted-users", method = RequestMethod.POST)
	@ResponseBody
	public void UpdateContactedUsers(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				response.sendRedirect("home.jsp");
			} else {

				response.setContentType("text/html;charset=UTF-8");

				int companyId = 0, jobId = 0;
				Company company = new Company();
				JobMapping jobMapping = new JobMapping();

				int userId = (Integer) session.getAttribute("userId");

				User user = new User();

				user.setUserId(userId);

				String location = request.getParameter("location");
				String emailId = request.getParameter("emailId");
				String jobDesignation = request.getParameter("job");

				companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				jobMapping.setJobRole(jobDesignation);
				jobId = jobDelegate.fetchJobId(jobMapping);
				company.setEmail(emailId);
				company.setCompanyId(companyId);
				company.setJobId(jobId);
				company.setLocation(location);

				if (userDelegate.markContacted(company, user)) {

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

	@RequestMapping(value = "company/jobspublished", method = RequestMethod.GET)
	public ModelAndView ViewPublishedJobs(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("viewpublishedjobs");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {
				response.setContentType("text/html;charset=UTF-8");
				int userId = (Integer) session.getAttribute("userId");

				User user = new User();

				Company company = new Company();
				ArrayList<Company> vacancyDetails = null;

				int companyId = 0;

				user.setUserId(userId);
				companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				company.setCompanyId(companyId);
				vacancyDetails = companyDelegate.retrieveVacancyByCompanyAdmin(company);

				ArrayList<JobMapping> job = null;
				job = jobDelegate.displayJobs();
				mav.addObject("jobs", job);
				if (vacancyDetails.isEmpty()) {

					mav.addObject("noVacancy", "yes");
				} else {
					mav.addObject("vacancyDetails", vacancyDetails);

				}
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

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				mav = new ModelAndView("home");
			} else {

				int oldJobId = 0, newJobId = 0, companyId = 0;
				int userId = (Integer) session.getAttribute("userId");

				User user = new User();

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
				user.setUserId(userId);
				companyId = userDelegate.fetchCompanyIdByAdmin(userId);
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

				ArrayList<JobMapping> job = null;
				job = jobDelegate.displayJobs();
				mav.addObject("jobs", job);
				if (vacancyDetails.isEmpty()) {

					mav.addObject("noVacancy", "yes");
				} else {
					mav.addObject("vacancyDetails", vacancyDetails);

				}
			}
		} catch (Exception e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}
}
