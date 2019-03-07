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

	@RequestMapping(value = "/jobdesignation/companies", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView findJobs(@RequestParam("job") String jobDesignation, HttpSession session,
			HttpServletResponse response) throws IOException {
		ModelAndView model =new ModelAndView("viewjobs");
		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {

				int userId = (Integer) session.getAttribute("userId");
				ArrayList<String> jobRole = new ArrayList<String>();
				jobRole.add(jobDesignation);
				int jobId = jobDelegate.fetchJobId(jobDesignation);
				if (jobId == 0) {
					model.addObject("noJobDesignation", "yes");
				} else {
					ArrayList<Company> vacancyDetails = jobDelegate.retrieveVacancyByJob1(jobId, userId);
					model.addObject("job", jobRole);
					model.addObject("displayVacancy", vacancyDetails);
				}

			}
		} catch (SQLException e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	@RequestMapping(value = "/company/jobs/apply", method = RequestMethod.POST)
	public void ApplyJobs(@RequestParam("location") String location,@RequestParam("companyName") String companyName,@RequestParam("jobDesignation") String jobDesignation, HttpServletResponse response,HttpSession session)
			throws IOException {
		PrintWriter out = response.getWriter();
		try {
			response.setContentType("text/html;charset=UTF-8");
			int userId = (Integer) session.getAttribute("userId");
			String email = (String) session.getAttribute("email");
			int companyId = companyDelegate.fetchCompanyId(companyName);
			int jobId = jobDelegate.fetchJobId(jobDesignation);
			if (userDelegate.applyForJob(companyId,jobId,location,userId,email)) {
				out.print("success");
				out.flush();
			} 
		}
		catch (SQLIntegrityConstraintViolationException e) {
			out.print("error");
			out.flush();
		}
		catch (Exception e) {
			out.print("error");
			out.flush();
		}
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.GET)
	public ModelAndView GetAllJobDesignations(HttpServletRequest request, HttpServletResponse response,HttpSession session) {
		ModelAndView model = new ModelAndView("postjob");
		try {
			if (session.getAttribute("email") == null) {
				response.sendRedirect("index.jsp");
			} else {
				ArrayList<JobMapping> job = jobDelegate.displayJobs();
				model.addObject("jobs", job);
			}
		} catch (Exception e) {
			model = new ModelAndView("error");
		}
		return model;
	}

	@RequestMapping(value = "/company/vacancy", method = RequestMethod.POST)
	public void PublishNewVacancy(@RequestParam("job") String jobDesignation, @RequestParam("location") String location,
			@RequestParam("salary") String salary, @RequestParam("count") String count,
			@RequestParam("description") String description, HttpSession session, HttpServletResponse response)
			throws IOException {

		PrintWriter out = response.getWriter();
		try {
			if (session.getAttribute("email") == null) {
				response.sendRedirect("index.jsp");
			}else {
			int userId = (Integer) session.getAttribute("userId");
			int companyId = userDelegate.fetchCompanyIdByAdmin(userId);
			if (companyDelegate.publishVacancy(userId,companyId,jobDesignation,location,salary,count,description)) {
				companyDelegate.compareVacancyWithRequest(jobDesignation,location);
				out.print("success");
				out.flush();
			} else {
				out.print("error");
				out.flush();
			}
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			out.print("error");
			out.flush();
		}
		catch (Exception e) {
			out.print("error");
			out.flush();
		}
	}

	@RequestMapping(value = "/jobs", method = RequestMethod.POST)
	public ModelAndView AddNewJobDesignation(@RequestParam("newjob") String jobRole, HttpSession session)
			throws ServletException, IOException {
		ModelAndView model = new ModelAndView("postjob");

		try {
			if (session.getAttribute("email") == null) {
				model = new ModelAndView("home");
			} else {
				int userId = (Integer) session.getAttribute("userId");
				if (jobDelegate.addNewJob(jobRole, userId)) {
					ArrayList<JobMapping> job = jobDelegate.displayJobs();
					model.addObject("jobs", job);
				} 
			}
		} catch (SQLException e) {
			model = new ModelAndView("error");
		}
		return model;
	}
}
