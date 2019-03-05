package com.zilker.onlinejobsearch.constants;


/*
 * Class containing all Database Queries.
 */
public class QueryConstants {
	public static final String CONNECTIONSTRING = "jdbc:mysql://localhost:3306/jobsearchsystem";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "root@123";
	public static final String INSERTUSER = "insert into user_classification(user_name,email,password,company_name,designation) values(?,?,?,?,?)";
	public static final String INSERTINTOUSER = "update user_classification set created_by=?,updated_by=? where email=?";
	public static final String INSERTADMIN = "insert into user_classification(user_name,email,password,company_name,designation,role) values(?,?,?,?,?,?)";
	public static final String INSERTCOMPANYADMIN = "insert into company_admin(user_id,company_id,created_by,updated_by) values(?,?,?,?)";
	public static final String RETRIEVEUSERDATA = "select email,password,role,user_id from user_classification";
	public static final String RETRIEVEUSERDETAILS = "select user_name,company_name,designation from user_classification where user_id=?";
	public static final String INSERTCOMPANY = "insert into company_details(company_name,website_url,company_logo) values(?,?,?)";
	public static final String INSERTCOMPANYBYSITEADMIN = "insert into company_details(company_name,website_url,created_by,updated_by) values(?,?,?,?)";
	public static final String UPDATECOMPANYCREATER = "update company_details set created_by=?,updated_by=? where company_id=?";
	public static final String RETRIEVECOMPANYDATA = "select company_id,company_name,company_logo from company_details";
	public static final String RETRIEVEJOBDATA = "select job_id,job_designation from job";
	public static final String RETRIEVETECHNOLOGYDATA = "select technology_id,technology from technology";
	public static final String RETRIEVETECHNOLOGYID = "select technology_id,technology from technology where technology=?";
	public static final String INSERTVACANCY = "insert into vacancy_publish(company_id,job_id,location,job_description,salary,vacancy_count,created_by,updated_by) values(?,?,?,?,?,?,?,?)";
	public static final String INSERTJOB = "insert into job(job_designation,created_by,updated_by)" + "values(?,?,?)";
	public static final String DELETEVACANCY = "update vacancy_publish set vacancy_count=0 ,vacancy_status='expired',updated_by=? where company_id=? and job_id=?";
	public static final String RETRIEVEVACANCYBYCOMPID ="select c.company_name,v.job_id,v.location,v.job_description,v.salary,v.vacancy_count from vacancy_publish v ,company_details c where v.company_id = ? and c.company_id=v.company_id and vacancy_status='available'";
	public static final String APPLYFORJOB ="insert into applyforjob(user_id,email,company_id,job_id,location,created_by,updated_by) values(?,?,?,?,?,?,?)";
	public static final String MARKCONTACTED ="update applyforjob set contacted=?, created_by=? ,updated_by=? where company_id=? and job_id=? and location=? and email=?";
	public static final String VIEWAPPLIEDUSERS="select user_classification.user_name,company_id,job.job_designation,applyforjob.email,applyforjob.location,applyforjob.contacted from applyforjob,user_classification,job where company_id=? and user_classification.user_id=applyforjob.user_id and applyforjob.job_id = job.job_id";
	public static final String VIEWAPPLIEDJOBS="select company_details.company_name,job.job_designation,applyforjob.location from applyforjob,company_details,job where user_id = ? and company_details.company_id=applyforjob.company_id and applyforjob.job_id = job.job_id"; 
	public static final String RETRIEVEVACANCYADMIN = "select company_id,job_id,location,job_description,salary,vacancy_count from vacancy_publish where company_id=?";
	public static final String RETRIEVECOMPANYNAME = "select company_name,website_url,company_id,company_logo from company_details where company_id=?";
	public static final String RETRIEVEJOBDESIGNATION = "select job_designation from job where job_id=?";
	public static final String RETRIEVEVACANCYBYJOBID = "select company_id,job_id,location,job_description,salary,vacancy_count from vacancy_publish where job_id=? and vacancy_status='available'";
	public static final String INSERTJOBREQUEST = "insert into job_request(email,job_id,location,salary,created_by,updated_by) values(?,?,?,?,?,?)";
	public static final String INSERTJOBREVIEW = "insert into job_reviews(user_id,company_id,job_id,interview_process,created_by,updated_by) values(?,?,?,?,?,?)";
	public static final String INSERTREVIEWRATING = "insert into company_reviews(user_id,company_id,reviews,rating,updated_by,created_by) values(?,?,?,?,?,?)";
	public static final String RETRIEVEUSERNAMEBYID = "select user_name,email,password from user_classification where user_id=?";
	public static final String RETRIEVEEMAILBYID="select password from user_classification where user_id=?";
	public static final String RETRIEVERATINGSFORCOMPANY = "select rating from company_reviews where company_id=?";
	public static final String DELETECOMPANY = "delete from company_details where company_id = ?";
	public static final String DELETEUSER = "delete from user_classification where user_id=?";
	public static final String INSERTTECHNOLOGY = "insert into user_technology_mapping(user_id,technology_id,created_by,updated_by)values(?,?,?,?)";
	public static final String INSERTTECHNOLOGYDATA = "insert into technology(technology,created_by,updated_by) values(?,?,?)";
	public static final String RETRIEVEJOBREQUESTS = "select email,job_id,location from job_request";
	public static final String RETRIEVEREVIEW = "select  reviews,company_reviews.user_id,rating,user_classification.user_name,user_classification.company_name,user_classification.designation from company_reviews,user_classification where company_id=? and user_classification.user_id = company_reviews.user_id order by rating desc";
	public static final String RETRIEVEINTERVIEWPROCESS	= "select job.job_designation,interview_process, user_classification.user_name,user_classification.company_name, user_classification.designation from job_reviews,user_classification,job where user_classification.user_id= job_reviews.user_id and job_reviews.job_id=job.job_id and company_id=?";
	public static final String RETRIEVECOMPANYID = "select user_id,company_id from company_admin";
	public static final String DELETEJOBREQUEST = "delete from job_request where email=?";
	public static final String UPDATEUSERNAME = "update user_classification set user_name=?,updated_by=?,update_timestamp=? where user_id=?";
	public static final String UPDATEUSERCOMPANY = "update user_classification set company_name=?,updated_by=?,update_timestamp=? where user_id=?";
	public static final String UPDATEUSERDESIGNATION = "update user_classification set designation=?,updated_by=?,update_timestamp=? where user_id=?";
	public static final String RETRIEVEUSERTECHNOLOGY = "select t.technology from user_technology_mapping,technology t where user_id=? and user_technology_mapping.technology_id=t.technology_id";
	public static final String UPDATEUSERTECHNOLOGY = "update user_technology_mapping set technology_id=?,updated_by=?,update_timestamp=? where user_id=? and technology_id=?";
	public static final String UPDATEVACANCYDESIGNATION = "update vacancy_publish set job_id=?,updated_by=?,update_timestamp=? where company_id=? and job_id=?";
	public static final String UPDATEVACANCYLOCATION = "update vacancy_publish set location=?,updated_by=?,update_timestamp=? where company_id=? and job_id=?";
	public static final String UPDATEVACANCYDESCRIPTION = "update vacancy_publish set job_description=?,updated_by=?,update_timestamp=? where company_id=? and job_id=?";
	public static final String UPDATEVACANCYSALARY = "update vacancy_publish set salary=?,updated_by=?,update_timestamp=? where company_id=? and job_id=?";
	public static final String UPDATEVACANCYCOUNT = "update vacancy_publish set vacancy_count=?,vacancy_status=?,updated_by=?,update_timestamp=? where company_id=? and job_id=?";
	public static final String RETRIEVECOMPANYBYLOCATION ="select company_details.company_name,company_details.website_url,job.job_designation,vacancy_publish.location,vacancy_publish.job_description,vacancy_publish.salary,vacancy_publish.vacancy_count,vacancy_publish.company_id,vacancy_publish.job_id from vacancy_publish,company_details,job where location=? and vacancy_status='available' and vacancy_publish.company_id= company_details.company_id and vacancy_publish.job_id= job.job_id";
	public static final String FETCHJOBDESIGNATIONBYID = "select job_designation from job where job_id=?";
	public static final String DELETEUSERTECHNOLOGY ="delete from user_technology_mapping where user_id=?";
	public static final String USERAPPLIEDJOBS="select company_id,job_id,location from applyforjob where user_id=?";
	
}
