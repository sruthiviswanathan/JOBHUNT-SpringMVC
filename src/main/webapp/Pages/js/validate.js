function loginForm(){
	var email = document.forms["login"]["email"];  
	 var password = document.forms["login"]["psw"];  
	  
	 if (email.value == "")                                   
	    { 
	    	 emailError = "Please enter a valid e-mail address.";
	      	 document.getElementById("log_em_error").innerHTML = emailError;
	        email.focus(); 
	        return false; 
	    } 
	   
	    if (email.value.indexOf("@", 0) < 0)                 
	    { 
	    	 emailError = "Please enter a valid e-mail address";
	      	 document.getElementById("log_em_error").innerHTML = emailError;
	        email.focus(); 
	        return false; 
	    } 
	   
	    if (email.value.indexOf(".", 0) < 0)                 
	    { 
	    	 emailError = "Please enter a valid e-mail address.";
	      	 document.getElementById("log_em_error").innerHTML = emailError;
	        email.focus(); 
	        return false; 
	    } 
	   
	    if (password.value == "")                        
	    { 
	    	 passwordError = "Please enter your password";
	      	 document.getElementById("log_psw_error").innerHTML = passwordError;
	        password.focus(); 
	        return false; 
	    }
	   removeLoginError();
	    
	 return true;
}
function removeLoginError() {
   var elem = document.getElementById("loginError");
   elem.parentNode.removeChild(elem);
	
}


function removeRegisterError() {
   var elem = document.getElementById("RegisterError");
   elem.parentNode.removeChild(elem);
	
}

function removeAdminRegisterError() {
   var elem = document.getElementById("adminRegisterError");
   elem.parentNode.removeChild(elem);
	
}

function registerForm()                                    
{ 
    var name = document.forms["signup"]["userName"];               
    var email = document.forms["signup"]["email"];    
    var company =  document.forms["signup"]["companyName"];  
    var designation =  document.forms["signup"]["designation"];  
    var password = document.forms["signup"]["psw"];  
    var confirm = document.forms["signup"]["cpsw"]; 
    var spans = document.getElementById('skill').getElementsByTagName('span');
    var obj = [];
    console.log("validate");
    var str="";
    
    if (name.value == "")                                  
    { 
  	    nameError = "Please enter your name";
  	    document.getElementById("reg_name_error").innerHTML = nameError;
        name.focus(); 
        return false; 
    } 
    if (email.value == "")                                   
    { 
    	 emailError = "Please enter a valid e-mail address.";
      	 document.getElementById("reg_email_error").innerHTML = emailError;
        email.focus(); 
        return false; 
    } 
   
    if (email.value.indexOf("@", 0) < 0)                 
    { 
    	 emailError = "Please enter a valid e-mail address";
      	 document.getElementById("reg_email_error").innerHTML = emailError; 
        email.focus(); 
        return false; 
    } 
   
    if (email.value.indexOf(".", 0) < 0)                 
    { 
    	 emailError = "Please enter a valid e-mail address.";
      	 document.getElementById("reg_email_error").innerHTML = emailError;
        email.focus(); 
        return false; 
    } 
   
    if (password.value == "")                        
    { 
    	 passwordError = "Please enter your password";
      	 document.getElementById("reg_psw_error").innerHTML = passwordError; 
        password.focus(); 
        return false; 
    } 
   
    if (confirm.value == "")                               
    { 
    	 confirmError = "Please reenter your password.";
      	 document.getElementById("reg_cpsw_error").innerHTML = confirmError;
        confirm.focus(); 
        return false; 
    } 
    
    if(password.value !== confirm.value){
    	 matchError = "Both password and Confirm password must match";
      	 document.getElementById("reg_cpsw_error").innerHTML = matchError; 
        confirm.focus(); 
        return false; 
    }
       
    if (company.value == "")                  
    { 
    	 companyError = "Please Enter a Company Name";
      document.getElementById("reg_comp_error").innerHTML = companyError;
        company.focus(); 
        return false; 
    } 
    
    if (designation.value == "")                  
    { 
    	 designationError = "Please Enter your designation";
    	 document.getElementById("reg_des_error").innerHTML = designationError;
        designation.focus(); 
        return false; 
    }
    
    

    for(var i = 0, l = spans.length; i < l; i++){
    obj[spans[i].textContent] = spans[i].textContent || spans[i].innerText || "@";
    str +=  obj[spans[i].textContent] ;
   
    }
  
document.getElementById('skillset').value = str;

    console.log(str);
    removeRegisterError();
	   
    return true; 
}


function validateForm()                                    
{ 
    var name = document.forms["admin"]["userName"];               
    var email = document.forms["admin"]["email"];    
    var what =  document.forms["admin"]["companyName"];  
    var password = document.forms["admin"]["psw"];  
    var confirm = document.forms["admin"]["cpsw"];  
    console.log("validate");
    if (name.value == "")                                  
    { 
  	 nameError = "Please enter your name";
  	 document.getElementById("name_error").innerHTML = nameError;
     
        name.focus(); 
        return false; 
    } 
    if (email.value == "")                                   
    { 
    	 emailError = "Please enter a valid e-mail address.";
      	 document.getElementById("email_error").innerHTML = emailError;
        email.focus(); 
        return false; 
    } 
   
    if (email.value.indexOf("@", 0) < 0)                 
    { 
    	 emailError = "Please enter a valid e-mail address";
      	 document.getElementById("email_error").innerHTML = emailError;
      
        email.focus(); 
        return false; 
    } 
   
    if (email.value.indexOf(".", 0) < 0)                 
    { 
    	 emailError = "Please enter a valid e-mail address.";
      	 document.getElementById("email_error").innerHTML = emailError;
        email.focus(); 
        return false; 
    } 
   
    if (password.value == "")                        
    { 
    	 passwordError = "Please enter your password";
      	 document.getElementById("psw_error").innerHTML = passwordError;      
        password.focus(); 
        return false; 
    } 
   
    if (confirm.value == "")                               
    { 
    	 confirmError = "Please reenter your password.";
      	 document.getElementById("cpsw_error").innerHTML = confirmError;
        confirm.focus(); 
        return false; 
    } 
       
    if (what.selectedIndex < 1)                  
    { 
    	 selectError = "Please select a Company Name";
      document.getElementById("select_error").innerHTML = selectError;
        what.focus(); 
        return false; 
    } 
    removeAdminRegisterError();
    return true; 
}

function validatePostJob()                                    
{ 
    var location = document.forms["postjob"]["location"];               
    var description = document.forms["postjob"]["description"]; 
    var what =  document.forms["postjob"]["job"];  
    var salary = document.forms["postjob"]["salary"];  
    var count = document.forms["postjob"]["count"];  
   
    if (what.selectedIndex < 1)                  
    { 
    	
    	 jobError = "Please Select a Job Designation";
      	 document.getElementById("job_error").innerHTML = jobError;    	
        what.focus(); 
        return false; 
    } 
   

    if (location.value == "")                                  
    { 
    	 locationError = "Please enter the location of job.";
      	 document.getElementById("location_error").innerHTML = locationError; 
        location.focus(); 
        return false; 
    }

    if (salary.value == "")                               
    { 
    	 salaryError = "Please enter a valid salary amount.";
      	 document.getElementById("salary_error").innerHTML =  salaryError ;
        salary.focus(); 
        return false; 
    }

    if (count.value == "" || count.value === "0")                               
    { 
    	 countError = "Please enter a valid vacancy count.";
      	 document.getElementById("count_error").innerHTML = countError;
        count.focus(); 
        return false; 
    } 

    if (description.value == "")                                  
    { 
    	 descError = "Please enter job description.";
      	 document.getElementById("desc_error").innerHTML = descError;
        description.focus(); 
        return false; 
    }       
    
    return true; 
}


function validateRequestVacancy(){
    var location = document.forms["requestvacancy"]["location"];               
    var what =  document.forms["requestvacancy"]["job"];  
    var salary = document.forms["requestvacancy"]["salary"];   
   
    if (what.selectedIndex < 1)                  
    { 
    	 jobError = "Please Select a Job Designation";
      	 document.getElementById("job_error").innerHTML = jobError; 
        what.focus(); 
        return false; 
    } 
   

    if (location.value == "")                                  
    { 
    	 locationError = "Please enter the location of job.";
      	 document.getElementById("location_error").innerHTML = locationError; 
        location.focus(); 
        return false; 
    }

    if (salary.value == "")                               
    { 
    	 salaryError = "Please enter a valid salary amount.";
      	 document.getElementById("salary_error").innerHTML =  salaryError ;
        salary.focus(); 
        return false; 
    }
    
    return true; 
}

function updateUser()                                    
{ 
    var name = document.forms["update"]["username"];                
     var cname =  document.forms["update"]["cname"];   
    var designation = document.forms["update"]["designation"];  
    var spans = document.getElementById('skill').getElementsByTagName('span');
    var obj = [];
    console.log("validate");
    var str="";
   
    if (name.value == "")                                  
    { 
    	nameError = "Please enter your name";
     	 document.getElementById("name_error").innerHTML = nameError;
        name.focus(); 
        return false; 
    } 
       
    if (cname.value == "")                        
    { 
    	 companyError = "Please Enter a Company Name";
         document.getElementById("comp_error").innerHTML = companyError;
        cname.focus(); 
        return false; 
    } 
    if (designation.value == "")                               
    { 
    	 designationError = "Please Enter your designation";
    	 document.getElementById("des_error").innerHTML = designationError;
        designation.focus(); 
        return false; 
    } 
    
    for(var i = 0, l = spans.length; i < l; i++){
        obj[spans[i].textContent] =  spans[i].innerText.trim() || "@";
        str +=  obj[spans[i].textContent] ;
       
        }
      
    document.getElementById('skillset').value = str;

        console.log(str);
    
   
    return true; 
}
function requestVacancy(event){

	if(validateRequestVacancy()){
	    var formEl = $(event);
	    
	    var snackbar = document.getElementById("snackbar");   
	    snackbar.removeChild(snackbar.childNodes[0]);
	    
	    $.ajax({
	      type: 'POST',
	      url: formEl.prop('action'),
	      accept: {
	        javascript: 'application/javascript'
	      },
	      data: formEl.serialize(),
	      beforeSend: function() {
	    	 
	      },
	      dataType:"text",
	      success:function(msg){
	    	  console.log(msg);
	    	  if (msg === 'success'){
	    	 
	    	  var para = document.createElement("p");
	    	  var node = document.createTextNode("YOUR REQUEST IS SAVED!!YOU WILL BE NOTIFIED WHEN YOUR REQUIREMENT MATCHES ANY VACANCIES!!");
	    	  para.appendChild(node);
	    	  var element = document.getElementById("snackbar");
	    	  element.className="show";
	    	  element.appendChild(para);
	    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);
	    	  document.getElementById("requestvacancy").reset();
	    	  } 
	    	  else if(msg === 'error'){
	    		  var para = document.createElement("p");
		    	  var node = document.createTextNode("OOPS!SOMETHING WENT WRONG");
		    	  para.appendChild(node);
		    	  var element = document.getElementById("snackbar");
		    	  element.className="show";
		    	  element.appendChild(para);
		    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);  
	    	  }
	      }
	    });

return true;
	}
}

function removeErrorMessages() {
	    var location = document.getElementById("location_error"); ;               
	    var what =  document.getElementById("job_error");  
	    var salary = document.getElementById("salary_error"); ;   
	   
	    if (what.value != "")                  
	    {	
	    	 document.getElementById("job_error").innerHTML = ""; 	      
	    } 
	   

	    if (location.value != "")                                  
	    { 	    	
	      	 document.getElementById("location_error").innerHTML = ""; 	      
	    }

	    if (salary.value != "")                               
	    { 
	    	 	 document.getElementById("salary_error").innerHTML = "" ;	         
	    }
	    
	    return true; 
}

function UpdateProfile(event){

	if(updateUser()){
	    var formEl = $(event);
	    
	    var snackbar = document.getElementById("snackbar");   
	    snackbar.removeChild(snackbar.childNodes[0]);
	    	
	    $.ajax({
	      type: 'POST',
	      url: formEl.prop('action'),
	      accept: {
	        javascript: 'application/javascript'
	      },
	      data: formEl.serialize(),
	      beforeSend: function() {
	    	
	      },
	      dataType:"text",
	      success:function(msg){
	    	  console.log(msg);
	    	 
	    	  if (msg === 'success'){
	    	  var para = document.createElement("p");
	    	  var node = document.createTextNode("YOUR PROFILE IS UPDATED SUCCESSFULLY");
	    	  para.appendChild(node);
	    	  var element = document.getElementById("snackbar");
	    	  element.className="show";
	    	  element.appendChild(para);
	    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);
	    	  } 
	    	  
	    	  else if(msg === 'error'){
	    		 var para = document.createElement("p");
		    	  var node = document.createTextNode("OOPS!SOMETHING WENT WRONG");
		    	  para.appendChild(node);
		    	  var element = document.getElementById("snackbar");
		    	  element.className="show";
		    	  element.appendChild(para);
		    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);  
	    	  }
	    	
	      }
	    });
	   
return true;
	}
	
}

function postJob(event){

	if(validatePostJob()){
	    var formEl = $(event);
	    
	    var snackbar = document.getElementById("snackbar");   
	    snackbar.removeChild(snackbar.childNodes[0]);
	    
	    $.ajax({
	      type: 'POST',
	      url: formEl.prop('action'),
	      accept: {
	        javascript: 'application/javascript'
	      },
	      data: formEl.serialize(),
	      beforeSend: function() {
	    	
	      },
	      dataType:"text",
	      success:function(msg){
	    	  console.log(msg);
	    	  if (msg === 'success'){
	    	 
	    	  var para = document.createElement("p");
	    	  var node = document.createTextNode("YOUR JOB VACANCY IS POSTED SUCCESSFULLY!!");
	    	  para.appendChild(node);
	    	  var element = document.getElementById("snackbar");
	    	  element.className="show";
	    	  element.appendChild(para);
	    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);
	    	  document.getElementById("postjob").reset();
	    	  } 
	    	  else if(msg === 'error'){
	    		  var para = document.createElement("p");
		    	  var node = document.createTextNode("OOPS!!ERROR THIS JOB VACANCY IN YOUR COMPANY IS ALREADY PUBLISHED!!TRY TO UPDATE");
		    	  para.appendChild(node);
		    	  var element = document.getElementById("snackbar");
		    	  element.className="show";
		    	  element.appendChild(para);
		    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);  
	    	  }
	      }
	    });

return true;
	}
}


function deleteTag(id){
	console.log(id);
	var elem = document.getElementById("item"+id).remove();
	
}
