var currentTab = 0; // Current tab is set to be the first tab (0)
showTab(currentTab); // Display the current tab

function showTab(n) {
  // This function will display the specified tab of the form...
  var x = document.getElementsByClassName("tab");
  x[n].style.display = "block";
  //... and fix the Previous/Next buttons:
  if (n == 0) {
    document.getElementById("prevBtn").style.display = "none";
  } else {
    document.getElementById("prevBtn").style.display = "inline";
  }
  if (n == (x.length - 1)) {
    document.getElementById("nextBtn").innerHTML = "SUBMIT";
  } else {
    document.getElementById("nextBtn").innerHTML = "NEXT";
  }
  //... and run a function that will display the correct step indicator:
  fixStepIndicator(n)
}

function nextPrev(n) {
  // This function will figure out which tab to display
  
  if(currentTab == 0){
  var x = document.getElementsByClassName("tab");
  // Exit the function if any field in the current tab is invalid:

  if (n == 1 && !validateForm()) return false;
  // Hide the current tab:
  x[currentTab].style.display = "none";
  // Increase or decrease the current tab by 1:
  currentTab = currentTab + n;
  // if you have reached the end of the form...
  if (currentTab >= x.length) {
    // ... the form gets submitted:
    document.getElementById("reviewForm").submit();
    return false;
  }

  // Otherwise, display the correct tab:
  showTab(currentTab);
}
else if(currentTab == 1){
  var x = document.getElementsByClassName("tab");
  x[currentTab].style.display = "none";
    currentTab = currentTab + n;
    if (currentTab >= x.length) {
    document.getElementById("reviewForm").submit();
    return false;
    }
    
  showTab(currentTab);
}
}

function validateForm() {
  // This function deals with validation of the form fields
  var x, y, z, i, valid = true;
  x = document.getElementsByClassName("tab");
  y = x[currentTab].getElementsByTagName("input");
 // z = x[currentTab].getElementsByTagName("select");
 
    if (y[1].value == "") {
      // add an "invalid" class to the field:
      y[1].className += " invalid";
      valid=false;
    }
   /* if(z[0].value == "0"){ 
    z[0].className += " invalid";
      // and set the current valid status to false
      valid = false;
    }*/

  
  if (valid) {
    document.getElementsByClassName("step")[currentTab].className += " finish";
  }
  return valid; 
}

function fixStepIndicator(n) {
  // This function removes the "active" class of all steps...
  var i, x = document.getElementsByClassName("step");
  for (i = 0; i < x.length; i++) {
    x[i].className = x[i].className.replace(" active", "");
  }
  //... and adds the "active" class on the current step:
  x[n].className += " active";
}
