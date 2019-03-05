package com.zilker.onlinejobsearch.controller;

import java.sql.SQLException;
import java.util.ArrayList;


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
public class JobController {

	@Autowired 
	UserDelegate userDelegate;
	
	@Autowired
	CompanyDelegate companyDelegate;
	
	@Autowired
	JobDelegate jobDelegate;
	
	
	 @RequestMapping(value = "/company/jobs", method = RequestMethod.POST)
	 @ResponseBody
	  public ModelAndView findJobs(@RequestParam("job") String jobDesignation,HttpSession session) {
		  ModelAndView mav = null;
		  try {
				
				
				if(session.getAttribute("email")==null){
					//response.sendRedirect("index.jsp");
				}else {
				String email = (String) session.getAttribute("email");
				User user= new User();
				user.setEmail(email);
				ArrayList<String> jobRole = new ArrayList<String>();
				ArrayList<Company> vacancyDetails = new ArrayList<Company>();
				UserDelegate userDelegate = new UserDelegate();
				JobDelegate jobDelegate = new JobDelegate();
				Company company = new Company();
				JobMapping jobmapping = new JobMapping();

				int jobId = 0,userId=0;
				userId=userDelegate.fetchUserId(user);
				user.setUserId(userId);			
				jobRole.add(jobDesignation);
				jobmapping.setJobRole(jobDesignation);
				
				jobId = jobDelegate.fetchJobId(jobmapping);
				if(jobId == 0) {
					
					 mav = new ModelAndView("viewjobs");
					 mav.addObject("noJobDesignation","yes");
				}
				else {
				
					company.setJobId(jobId);
					vacancyDetails = jobDelegate.retrieveVacancyByJob1(company,user);
					if (vacancyDetails.isEmpty()) {
						
						 mav = new ModelAndView("viewjobs");
						 mav.addObject("noVacancy","yes");
					
					}
					else {
					
						mav = new ModelAndView("viewjobs");
						mav.addObject("displayVacancy", vacancyDetails);
					
					
					
				
					}
			}
					
				}	
			} catch (SQLException e) {
				 mav = new ModelAndView("error");
			}
		return mav;
	  }
	
}
