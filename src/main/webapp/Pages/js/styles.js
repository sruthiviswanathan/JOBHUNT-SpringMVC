
function openNav() {
	
 document.getElementById("mySidenav").style.marginLeft = "0";
 
}

function closeNav() {

  document.getElementById("mySidenav").style.marginLeft = "-250px";
 
}


function openForm() {
  document.getElementById("myForm").style.display = "block";
}

function closeForm() {
  document.getElementById("myForm").style.display = "none";
}


function displaymodal(id) {
	
	  console.log(id);
	  var btn = document.getElementById(id);
	  var modal = document.getElementsByClassName(id);
	  console.log(modal);
	  
	
	  for (var i = 0; i < modal.length; i++) {
		  console.log(modal[i]);
		  modal[i].style.display ="block";
		}
}

function closeModal(cl) {
	 	
	var x = document.getElementsByClassName("modal");
	for(var i=0;i<x.length;i++){
		 x[i].style.display ="none";
	}
	}

function displayjobs(id) {
	console.log(id);
	var y = document.getElementsByClassName("rightside");
	for (var i = 0; i < y.length; i++) {
		  console.log(y[i]);
		  y[i].style.display ="none";
		}
  var x = document.getElementsByClassName(id);
  console.log(x);
  for (var i = 0; i < x.length; i++) {
	  console.log(x[i]);
	  x[i].style.display ="block";
	}

}

function displayFirstVacancy(){
	var y = document.getElementsByClassName("rightside");
	for (var i = 0; i < y.length; i++) {
		  console.log(y[i]);
		  y[i].style.display ="none";
		}
var x = document.getElementsByClassName("btn1");
console.log(x);
for (var i = 0; i < x.length; i++) {
	  console.log(x[i]);
	  x[i].style.display ="block";
	}
}




function displaymenu(id) {
  var x = document.getElementsByClassName("rightside");
  for (var i = 0; i < x.length; i++) {
	  console.log(x[i]);
	  x[i].style.display ="none";
	}
}

function removeErrors(){
	var elem = document.getElementById('jobError').remove();
    return false;
}
function submitFindJob(){

var elem = document.getElementById('jobError').remove();	
document.getElementById("findJob").submit();

}
function removeLocationErrors(){
	var elem1 = document.getElementById('locationError').remove();;
    return false;
}
function submitFindLocation(){

	var elem = document.getElementById('locationError').remove();	
	document.getElementById("findLocation").submit();

	}

function removeCompanyErrors(){
	var elem1 = document.getElementById('companyError');
	if(elem1){
		console.log(elem1);	
		elem1.parentNode.removeChild(elem1);
	}

}
function submitFindCompany(){

	var elem = document.getElementById('companyError');	
	if(elem){
		console.log(elem);	
		elem.parentNode.removeChild(elem);
  }
}

function apply(event,id){
	console.log(event);

	    var formEl = $(event);
	    var submit = document.getElementById(id);
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
	    	  console.log(submit);
	      },
	      dataType:"text",
	      success:function(msg){
	    	  console.log(msg);
	    	  if (msg === 'success'){
	    	 
	    	  var para = document.createElement("p");
	    	  var node = document.createTextNode("YOU HAVE APPLIED FOR THIS JOB!!COMPANY MAY CONTACT YOU ANY TIME");
	    	  para.appendChild(node);
	    	  var element = document.getElementById("snackbar");
	    	  element.className="show";
	    	  element.appendChild(para);
	    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);
	    	
	    	  
	    	  } 
	    	  else if(msg === 'error'){
	    		  var para = document.createElement("p");
		    	  var node = document.createTextNode("YOU HAVE ALREADY APPLIED FOR THIS JOB");
		    	  para.appendChild(node);
		    	  var element = document.getElementById("snackbar");
		    	  element.className="show";
		    	  element.appendChild(para);
		    	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);  
	    	  }
	      }
	    }).done(function(data) {
	    	console.log(submit);
	    	submit.disabled=true;
	    	submit.className = "disabled";
	    	submit.value="APPLIED";
	    });

return false;
}

function displaySuccessMessage(){
	 console.log("hi");
	 var para = document.createElement("p");
	  var node = document.createTextNode("YOUR REQUEST IS SAVED!!YOU WILL BE NOTIFIED WHEN YOUR REQUIREMENT MATCHES ANY VACANCIES!!");
	  para.appendChild(node);
	  var element = document.getElementById("snackbar");
	  element.className="show";
	  element.appendChild(para);
	  setTimeout(function(){ element.className = element.className.replace("show", ""); }, 5000);
}

function apply1(event,id){
	console.log(event);

	    var formEl = $(event);
	    var submit = document.getElementById(id);
	
	    
	    $.ajax({
	      type: 'POST',
	      url: formEl.prop('action'),
	      accept: {
	        javascript: 'application/javascript'
	      },
	      data: formEl.serialize(),
	      beforeSend: function() {
	    	  console.log(submit);
	      },
	      dataType:"text",
	      success:function(msg){
	    	  console.log(msg);
	    	 
	      }
	    }).done(function(data) {
	    	console.log(submit);
	    	submit.disabled=true;
	    	submit.className = "disabled";
	    	submit.value="CONTACTED";
	    });

return false;
}

function displayRatings(id,ratings) {
	console.log(ratings);
	console.log(id);
document.getElementById("stars"+id).innerHTML = getStars(ratings);

function getStars(rating) {
 
  rating = Math.round(rating * 2) / 2;
  let output = [];

  for (var i = rating; i >= 1; i--)
    output.push('<i class="fa fa-star" aria-hidden="true" style="color: gold;"></i>&nbsp;');

  if (i == .5) output.push('<i class="fa fa-star-half-o" aria-hidden="true" style="color: gold;"></i>&nbsp;');

  for (let i = (5 - rating); i >= 1; i--)
    output.push('<i class="fa fa-star-o" aria-hidden="true" style="color: gold;"></i>&nbsp;');

  return output.join('');

}
}
