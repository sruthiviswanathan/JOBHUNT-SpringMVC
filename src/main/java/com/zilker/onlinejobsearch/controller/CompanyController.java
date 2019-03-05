package com.zilker.onlinejobsearch.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
	
	 @RequestMapping(value = "/findjobs", method = RequestMethod.GET)
	  public ModelAndView DisplayFindJobs(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView mav = new ModelAndView("findjob");
		 try {
				
				HttpSession session = request.getSession();
				if(session.getAttribute("email")==null){
					response.sendRedirect("index.jsp");
				}	
				
			Company company = new Company();
			ArrayList<Company> companyDetails = new ArrayList<Company>();
			companyDetails = companyDelegate.displayCompanies(company);	
		    mav.addObject("companyList", companyDetails);
		    
			
			}catch(Exception e) {
				
			}
		 return mav;
	  }
	
	 @RequestMapping(value = "/findcompany", method = RequestMethod.GET)
	  public ModelAndView DisplayFindCompany(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView mav = new ModelAndView("findcompany");
		 try {
				
				HttpSession session = request.getSession();
				if(session.getAttribute("email")==null){
					response.sendRedirect("index.jsp");
				}	
				
			Company company = new Company();
			ArrayList<Company> companyDetails = new ArrayList<Company>();
			companyDetails = companyDelegate.displayCompanies(company);	
		    mav.addObject("companyList", companyDetails);
		    
			
			}catch(Exception e) {
				
			}
		 return mav;
	  }
	 
	 @RequestMapping(value = "/findlocation", method = RequestMethod.GET)
	  public ModelAndView DisplayFindLocation(HttpServletRequest request, HttpServletResponse response) {
			ModelAndView mav = new ModelAndView("searchbylocation");
		 try {
				
				HttpSession session = request.getSession();
				if(session.getAttribute("email")==null){
					response.sendRedirect("index.jsp");
				}	
				
			Company company = new Company();
			ArrayList<Company> companyDetails = new ArrayList<Company>();
			companyDetails = companyDelegate.displayCompanies(company);	
		    mav.addObject("companyList", companyDetails);
		    
			
			}catch(Exception e) {
				
			}
		 return mav;
	  }
	
	 @RequestMapping(value = "/addcompany", method = RequestMethod.POST)
	  public ModelAndView AddNewCompany(HttpServletRequest request, HttpServletResponse response) {
		  ModelAndView mav = null;
		  try {
				Company company = new Company();
				
				String companyName = request.getParameter("companyName");
				String websiteUrl = request.getParameter("websiteUrl");
				String companyLogo = request.getParameter("companyLogo");
				company.setCompanyName(companyName);
				company.setCompanyWebsiteUrl(websiteUrl);
				company.setCompanyLogo(companyLogo);
				if (companyDelegate.addNewCompany(company)) {
					//response.sendRedirect("AddNewCompany");
					mav = new ModelAndView("signup");
					ArrayList<Company> displayCompanies = new ArrayList<Company>();
					displayCompanies = companyDelegate.displayCompanies(company);
					mav.addObject("companies", displayCompanies);
					
				} else {
				
					mav = new ModelAndView("error");
				}

			} catch (Exception e) {
				
				mav = new ModelAndView("error");
			}
		return mav;
	  }
	 
	 @RequestMapping(value = "/company", method = RequestMethod.POST)
	 @ResponseBody
	  public ModelAndView findCompany(@RequestParam("companyName") String companyName,HttpSession session) {
		  ModelAndView mav = new ModelAndView("companydetails");
		  try {
				int companyId = 0;
				//HttpSession session = request.getSession();
				if(session.getAttribute("email")==null){
					//response.sendRedirect("index.jsp");
				}
				String email = (String) session.getAttribute("email");
				User user = new User();
				user.setEmail(email);
				
				ArrayList<Company> companyDetails = new ArrayList<Company>();
				ArrayList<Company> vacancyDetails = new ArrayList<Company>();
				ArrayList<Company> companyReviews = new ArrayList<Company>();
				Company company = new Company();
				
				int userId=0;
				userId=userDelegate.fetchUserId(user);
				user.setUserId(userId);

				company.setCompanyName(companyName);
				companyId = companyDelegate.fetchCompanyId(company);
				if (companyId == 0) {
					
					mav.addObject("noCompany", "yes");
				

				} else {
					company.setCompanyId(companyId);
					companyDetails = companyDelegate.retrieveVacancyByCompany(company);
					for (Company j : companyDetails) {
						
						mav.addObject("displayCompany", companyDetails);
					}
					vacancyDetails = companyDelegate.retrieveVacancyByCompany1(company,user);

					if (vacancyDetails.isEmpty()) {
						
						mav.addObject("noVacancy", "yes");
						companyReviews = userDelegate.retrieveReview(company);

						if (companyReviews.isEmpty()) {
							mav.addObject("noReviews", "yes");
							
						} else {
							for (Company i : companyReviews) {
								
								mav.addObject("displayCompanyReviews", companyReviews);
							}
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
						mav.addObject("noReviews","yes");
					
					} else {
						for (Company i : companyReviews) {
							mav.addObject("displayCompanyReviews", companyReviews);
							
						}
					}
					
				}
				
			} 
			catch (Exception e) {
				
				
				}
		  return mav;
	 }
}
