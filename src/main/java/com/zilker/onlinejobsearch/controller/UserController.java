package com.zilker.onlinejobsearch.controller;

import java.sql.SQLIntegrityConstraintViolationException;
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
import org.springframework.web.servlet.ModelAndView;

import com.zilker.onlinejobsearch.beans.Company;
import com.zilker.onlinejobsearch.beans.Technology;
import com.zilker.onlinejobsearch.beans.User;
import com.zilker.onlinejobsearch.beans.UserTechnologyMapping;
import com.zilker.onlinejobsearch.delegate.CompanyDelegate;
import com.zilker.onlinejobsearch.delegate.UserDelegate;


@Controller
public class UserController {

	
	@Autowired 
	UserDelegate userDelegate;
	
	@Autowired
	CompanyDelegate companyDelegate;

	
	 @RequestMapping(value = "/companies", method = RequestMethod.GET)
	  public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
	    ModelAndView mav = new ModelAndView("login");
	   try {
	    Company company = new Company();
		ArrayList<Company> displayCompanies = null;
		displayCompanies = companyDelegate.displayCompanies(company);
   	    mav.addObject("companies",displayCompanies);
		mav.addObject("login", new User());
	   }catch(Exception e) {
		   
	   }
	    return mav;
	  }
	
	
	  @RequestMapping(value = "/users-login", method = RequestMethod.POST)
	  public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
	  @ModelAttribute("login") User user) {
		  ModelAndView mav = null;
		try {  
		HttpSession session=request.getSession(); 
		
	    int role=0,userId=0;
	    String userName="";
	    role = userDelegate.login(user);
	      
	    
	      session.setAttribute("email",user.getEmail()); 
		  userId = userDelegate.fetchUserId(user);
		  userName = userDelegate.fetchUserNameById(userId);  
		  session.setAttribute("userName",userName);
		 
	    
	    if (role == 0) {
	    mav = new ModelAndView("login");
	  
	    } else if (role == 1){
	    
	    	mav = new ModelAndView("findjob");
	    	Company company = new Company();
			ArrayList<Company> companyDetails = null;
			companyDetails = companyDelegate.displayCompanies(company);	
		    mav.addObject("companyList", companyDetails);
		  
	  
	    }else if(role == 2) {
	    mav = new ModelAndView("admin");
	    }
	    
	  }catch(Exception e) {
		  
	  }
		return mav;
	  }
	  
	  @RequestMapping(value = "/users", method = RequestMethod.POST)
	  public ModelAndView registerProcess(HttpServletRequest request, HttpServletResponse response) {
		  ModelAndView mav = null;
		  try {
				HttpSession session = request.getSession();
				String[] technology;
				String skills="";
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

					userName = userDelegate.fetchUserNameById(userId);
					session.setAttribute("userName", userName);
					 mav = new ModelAndView("findjob");
				

			}

			catch (SQLIntegrityConstraintViolationException e) {

				//request.setAttribute("userRegistrationError", "error");
				//response.sendRedirect("RegisterServlet");
				   
				    mav = new ModelAndView("signup");
				

			}

			catch (Exception e) {
				
				 mav = new ModelAndView("error");
			}
		  return mav;
	  }  
	
	  @RequestMapping(value = "/adminregister", method = RequestMethod.POST)
	  public ModelAndView registerAdminProcess(HttpServletRequest request, HttpServletResponse response) {
		  ModelAndView mav = null;
		  try {
				HttpSession session=request.getSession(); 
				int userId = 0, flag = 0;
				String userName="";
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
							session.setAttribute("userName",userName);
							session.setAttribute("email",email);
							request.setAttribute("registerSuccess","yes");
							 mav = new ModelAndView("admin");
						
						}
					}

				} 

			} 
			
			 catch (SQLIntegrityConstraintViolationException e) {
				
				  //request.setAttribute("adminRegistrationError","error");
				  //response.sendRedirect("RegisterAdminServlet"); 
				  mav = new ModelAndView("signup");
				  }
				

			catch (Exception e) {
				
				 mav = new ModelAndView("error");
			}

		  return mav;
	  }  
}
