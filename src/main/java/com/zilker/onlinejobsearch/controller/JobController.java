package com.zilker.onlinejobsearch.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
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
public class JobController {

	@Autowired
	UserDelegate userDelegate;

	@Autowired
	JobDelegate jobDelegate;
	
	@Autowired 
	CompanyDelegate companyDelegate;

	@RequestMapping(value = "/company/jobs", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView findJobs(@RequestParam("job") String jobDesignation, HttpSession session) {
		ModelAndView mav = null;
		try {

			if (session.getAttribute("email") == null) {
				// response.sendRedirect("index.jsp");
			} else {
				String email = (String) session.getAttribute("email");
				User user = new User();
				user.setEmail(email);
				ArrayList<String> jobRole = new ArrayList<String>();
				ArrayList<Company> vacancyDetails = null;

				Company company = new Company();
				JobMapping jobmapping = new JobMapping();

				int jobId = 0, userId = 0;
				userId = userDelegate.fetchUserId(user);
				user.setUserId(userId);
				jobRole.add(jobDesignation);
				jobmapping.setJobRole(jobDesignation);

				jobId = jobDelegate.fetchJobId(jobmapping);
				if (jobId == 0) {

					mav = new ModelAndView("viewjobs");
					mav.addObject("noJobDesignation", "yes");
				} else {

					company.setJobId(jobId);
					vacancyDetails = jobDelegate.retrieveVacancyByJob1(company, user);
					if (vacancyDetails.isEmpty()) {

						mav = new ModelAndView("viewjobs");
						mav.addObject("noVacancy", "yes");

					} else {

						mav = new ModelAndView("viewjobs");
						mav.addObject("job",jobRole);
						mav.addObject("displayVacancy", vacancyDetails);

					}
				}

			}
		} catch (SQLException e) {
			mav = new ModelAndView("error");
		}
		return mav;
	}
	
	@RequestMapping(value = "/company/jobs/apply", method = RequestMethod.POST)
	public void ApplyJobs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/html;charset=UTF-8");
			
			int companyId=0,jobId=0,userId=0;
			Company company = new Company();
			JobMapping jobMapping = new JobMapping();
			HttpSession session = request.getSession();
			String email = (String) session.getAttribute("email");
			User user= new User();
			user.setEmail(email);
			userId = userDelegate.fetchUserId(user);
			user.setUserId(userId);
			String location = request.getParameter("location");
			String companyName = request.getParameter("companyName");
			String jobDesignation = request.getParameter("jobDesignation");
			company.setCompanyName(companyName);
			companyId = companyDelegate.fetchCompanyId(company);
			jobMapping.setJobRole(jobDesignation);
			jobId = jobDelegate.fetchJobId(jobMapping);
			company.setCompanyId(companyId);
			company.setJobId(jobId);
			company.setLocation(location);
			
			if(userDelegate.applyForJob(company,user)) {
				response.setContentType("application/json");
				out.print("success");
				out.flush();
			
			}else {
				
			}
		}
		
		catch (SQLIntegrityConstraintViolationException e) {
			 
			 response.setContentType("application/json");
			 out.print("error");
			 out.flush();
			  
			}
		
		
		catch(Exception e) {
			
		}
	}

}
