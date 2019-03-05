var header = document.getElementById("myDIV");
var btns = header.getElementsByClassName("btn");
for (var i = 0; i < btns.length; i++) {
  btns[i].addEventListener("click", function() {
  var current = document.getElementsByClassName("active");
  current[0].className = current[0].className.replace(" active", "");
  this.className += " active";
  });
}


function viewlogin() {
	var x = document.getElementById("hideonlogin");
    var y = document.getElementById("login");
    x.style.display = "none";
    y.style.display = "block";

  }
  
  function viewsignup() {
	  var z = document.getElementById("hideonlogin");
    var y = document.getElementById("signup");
    var x = document.getElementById("login");
    y.style.display = "block";
    x.style.display = "none";
    z.style.display = "block";
  
  }
  
  function viewadminfields() {
	
	    var y = document.getElementById("defaultfields");
	    var x = document.getElementById("adminoptional");
	    y.style.display = "none";
	    x.style.display = "block";
	   
	  }

	  function viewuserfields() {
	    var y = document.getElementById("defaultfields");
	    var x = document.getElementById("adminoptional");
	    y.style.display = "block";
	    x.style.display = "none";
	  
	  }


  function openForm() {
    document.getElementById("myForm").style.display = "block";
  }
  
  function closeForm() {
    document.getElementById("myForm").style.display = "none";
  }  

