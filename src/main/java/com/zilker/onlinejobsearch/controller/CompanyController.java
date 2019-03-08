package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

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

	
	/*
	 * controller to retrieve all details and vacancy in company
	*/
	@RequestMapping(value = "/company", method = RequestMethod.GET)
	public ModelAndView findCompany(@RequestParam("companyName") String companyName, HttpSession session) {
		ModelAndView model = new ModelAndView("companydetails");
		try {
	
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				int companyId = companyDelegate.fetchCompanyId(companyName);
				if (companyId == 0) {
					model = new ModelAndView("errorcompanyresults");
					model.addObject("noCompany", "yes");
				} else {
					ArrayList<Company> companyDetails = companyDelegate.retrieveVacancyByCompany(companyId);
					ArrayList<Company> vacancyDetails = companyDelegate.retrieveVacancyByCompany1(companyId, userId);
					model.addObject("displayCompany", companyDetails);
					model.addObject("displayVacancies", vacancyDetails);
				}
			}
		} catch (SQLException e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	
	/*
	 * controller to find vacancies by location
	 */
	@RequestMapping(value = "/location/companies", method = RequestMethod.GET)
	public ModelAndView findByLocation(@RequestParam("location") String location, HttpSession session) {
		ModelAndView model = new ModelAndView("viewbylocation");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				ArrayList<Company> retrieveByLocation = companyDelegate.retrieveVacancyByLocation(location, userId);
				model.addObject("retrieveByLocation", retrieveByLocation);
			}
		} catch (SQLException e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller to view all reviews of a company
	 */
	@RequestMapping(value = "/company/reviews", method = RequestMethod.POST)
	public ModelAndView viewCompanyReviews(@RequestParam("company") String companyName, HttpSession session) {
		ModelAndView model = new ModelAndView("viewallreviews");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
		
				int companyId = companyDelegate.fetchCompanyId(companyName);
				ArrayList<Company> companyDetails  = companyDelegate.retrieveVacancyByCompany(companyId);
				ArrayList<Company> companyReviews = userDelegate.retrieveReview(companyId);
				model.addObject("displayCompany", companyDetails);
				model.addObject("displayCompanyReviews", companyReviews);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller to view all reviews of all interview process of a company
	 */
	@RequestMapping(value = "/company/interviews", method = RequestMethod.POST)
	public ModelAndView viewReviewsOnInterviewProcess(@RequestParam("company") String companyName,
			HttpSession session) {
		ModelAndView model = new ModelAndView("interviewprocess");
		try {

			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				int companyId = companyDelegate.fetchCompanyId(companyName);
				ArrayList<Company> companyDetails = companyDelegate.retrieveVacancyByCompany(companyId);
				ArrayList<Company> interviewProcess = userDelegate.retrieveInterviewProcess(companyId);
				model.addObject("displayCompany", companyDetails);
				model.addObject("displayInterviewProcess", interviewProcess);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller to display rating page 
	 */
	@RequestMapping(value = "/company/rate", method = RequestMethod.GET)
	public ModelAndView ViewRatingPage(@RequestParam("companyname") String companyName, HttpSession session) {
		ModelAndView model = new ModelAndView("reviewandrating");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
				model.addObject("companyname", companyName);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller to rate a company
	 */
	@RequestMapping(value = "/company/rate", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView RateACompany(@RequestParam("company") String companyName, @RequestParam("rating") String rating,
			@RequestParam("review") String review, @RequestParam("job") String jobRole,
			@RequestParam("interview") String interviewProcess, HttpSession session) {
		ModelAndView model = new ModelAndView("companydetails");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {

				int userId = (Integer) session.getAttribute("userId");
				int companyId = companyDelegate.fetchCompanyId(companyName);
				if (userDelegate.reviewAndRateCompany(userId, companyId,review,rating)) {
					if (jobRole != "" && interviewProcess != "") {
						if (userDelegate.interviewProcess(userId, companyId, jobRole,interviewProcess)) {
							
						} 
					}	
				} 
				ArrayList<Company> companyDetails = companyDelegate.retrieveVacancyByCompany(companyId);
				ArrayList<Company> vacancyDetails = companyDelegate.retrieveVacancyByCompany1(companyId, userId);
				model.addObject("displayCompany", companyDetails);
				model.addObject("displayVacancies", vacancyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}
/*
 * controller to fetch applied users
 */
	@RequestMapping(value = "/applied-users", method = RequestMethod.GET)
	public ModelAndView AppliedUsers(HttpSession session) {
		ModelAndView model = new ModelAndView("viewinterestedusers");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
		
				int userId = (Integer) session.getAttribute("userId");
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				ArrayList<Company> appliedUsers = companyDelegate.viewAppliedUsers(companyId);
				model.addObject("appliedUsers", appliedUsers);
							
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller to mark users as contacted
	 */
	@RequestMapping(value = "/contacted-users", method = RequestMethod.POST)
	public void UpdateContactedUsers(@RequestParam("location") String location,@RequestParam("emailId") String emailId
			,@RequestParam("job") String jobDesignation,HttpServletResponse response,HttpSession session) throws  IOException {
		PrintWriter out = response.getWriter();
		try {
			if (session.getAttribute("email") == null) {
				response.sendRedirect("home.jsp");
			} else {

				response.setContentType("text/html;charset=UTF-8");
				int userId = (Integer) session.getAttribute("userId");
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				int jobId = jobDelegate.fetchJobId(jobDesignation);
				if (userDelegate.markContacted(userId,emailId,companyId,jobId,location)) {
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
	 * controller to fetch all published jobs by admin
	 */
	@RequestMapping(value = "company/jobspublished", method = RequestMethod.GET)
	public ModelAndView ViewPublishedJobs(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("viewpublishedjobs");
		try {

			HttpSession session = request.getSession();
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				response.setContentType("text/html;charset=UTF-8");
				int userId = (Integer) session.getAttribute("userId");				
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				ArrayList<Company> vacancyDetails = companyDelegate.retrieveVacancyByCompanyAdmin(companyId);
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
				model.addObject("vacancyDetails", vacancyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	/*
	 * controller to update published jobs
	 */
	@RequestMapping(value = "company/jobspublished", method = RequestMethod.POST)
	public ModelAndView UpdatePublishedJobs(@RequestParam("action") String action,@RequestParam("jobdesignation") String jobDesignation,
			@RequestParam("job") String newJobDesignation,@RequestParam("location") String location,
			@RequestParam("description") String jobDescription,@RequestParam("salary") String salary,
			@RequestParam("count") String count,HttpSession session) {
		ModelAndView model = new ModelAndView("viewpublishedjobs");
		try {

			
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				
				int userId = (Integer) session.getAttribute("userId");
				int oldJobId = jobDelegate.fetchJobId(jobDesignation);
				int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
				if ("UPDATE".equals(action)) {
					if(userDelegate.UpdateVacancy(oldJobId,companyId,userId,newJobDesignation,location,jobDescription,salary,count)) {
					model.addObject("status", "updated");
					}
				}else if ("DELETE".equals(action)) {
					if (companyDelegate.removeVacancy(companyId, userId,oldJobId)) {
						model.addObject("status", "deleted");
					}
				}
				ArrayList<Company> vacancyDetails  = companyDelegate.retrieveVacancyByCompanyAdmin(companyId);
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
				model.addObject("vacancyDetails", vacancyDetails);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}
}
