<SCRIPT LANGUAGE="JavaScript">

function submitPayment() 
{	
	if (check_Payment(document.Payment))
	{	
		var isCus = $("#iCus").val();
		
		if(isCus == null || !(isCus=='true')){
			dialogOpen();
			document.Payment.submit();
				}else{

					if($("#chk").is(':checked')){	
						//alert("Ban Da Check Agree");
						dialogOpen();
						document.Payment.submit();
					}else{
						alert("You must agree to the terms and conditions of sale");
						}
		 		}
		//dialogOpen();
		//document.Payment.submit();
	}
}

function submitHeld() 
{	
	var expHoldDate = $("#expHoldDate").val();
	var expCurrentDate = $("#expCurrentDate").val();
	var expHoldDateSystem = $("#expHoldDateSystem").val();
	
	if(validateDate(expHoldDate))
	{	
		var s1Date = new Date(expHoldDate);
		var s2Date = new Date(expCurrentDate);
		var s3Date = new Date(expHoldDateSystem);
		
		if((s1Date.getTime() >= s2Date.getTime()) && (s1Date.getTime() <= s3Date.getTime()) )
		{
			dialogOpen();
			document.Payment.action = "checkout3.do?method=holdOrder";
			document.Payment.submit();
		}else
		{
			alert("Expiration hold date great than or equal current date and less than or equal expiration hold date in system !");
		}
	}else
	{
		alert("Expiration hold date is invalid !");
	}	
}

function check_Payment(checkForm) {
	  PaymentIndex = checkForm.cc_type.selectedIndex;
	  list = checkForm.payment_type.value.split(",");

	  if (!check_hasValue(list[PaymentIndex], "NUMBER" )) {
	    if (!check_onError(checkForm, checkForm.cc_type, checkForm.cc_type.value, "You must select a Payment Option.", "Payment Information", 1)) {
	      return false; 
	    }
	  }

	  if (list[PaymentIndex] == 0) {
	    if (!check_hasValue(checkForm.terms, "TEXT" )) {
	      if (!check_onError(checkForm, checkForm.terms, checkForm.terms.value, "You must enter Payment Information.", "Payment Information", 1)) {
	        return false; 
	      }
	    }
	  } else {
		if (!check_CC(checkForm)) {
		    return false;
		}
	  }
	  
	  //vinhhq comment code
	  //checkForm.payment_type_selected.value = list[PaymentIndex];
	  return true;
}

function check_CC(checkForm) 
{
	  if (!check_hasValue(checkForm.cc_type, "SELECT" )) 
	  {
	    if (!check_onError(checkForm, checkForm.cc_type, checkForm.cc_type.value, "You must select a Credit Card Type.", "Payment Information", 1)) 
		{
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.cc_name, "TEXT" )) {
	   if (!check_onError(checkForm, checkForm.cc_name, checkForm.cc_name.value, "You must enter the name on the credit card.", "Payment Information", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm._cc_number, "TEXT" )) {
	   	if (!check_onError(checkForm, checkForm._cc_number, checkForm._cc_number.value, "You must enter a credit card number.", "Payment Information", 1)) {
	      return false; 
	    }
	  }
	  
	  if(isNaN(checkForm._cc_number.value))
	  {
		  if (!check_onError(checkForm, checkForm._cc_number, checkForm._cc_number.value, "Credit card number is invalid.", "Payment Information", 1)) 
		  {
		      return false; 
		  }
	  }
	  
	  if((checkForm._cc_number.value.length < 13) || (checkForm._cc_number.value.length > 19))
	  {
		  if (!check_onError(checkForm, checkForm._cc_number, checkForm._cc_number.value, "Credit card number must be between 13 and 19 characters.", "Payment Information", 1)) 
		  {
		      return false; 
		  }
	  }
	  //Credit card number must be between 13 and 19 characters.
	 if (!check_hasValue(checkForm._cc_cvv2, "TEXT" )) {
	   if (!check_onError(checkForm, checkForm._cc_cvv2, checkForm._cc_cvv2.value, "You must enter a CSC Number.", "Payment Information", 1)) {
	      return false; 
	    }
	  }
	

	 if (isNaN(checkForm._cc_cvv2.value)) {
	   if (!check_onError(checkForm, checkForm._cc_cvv2, checkForm._cc_cvv2.value, "CSC Number is invalid..", "Payment Information", 1)) {
	      return false; 
	    }
	}
	  var now = new Date();
	  var now_year   = now.getFullYear();
	  var now_month  = now.getMonth()+1;
	  var idx_year   = checkForm._cc_expyear.selectedIndex;
	  var exp_year   = checkForm._cc_expyear.options[idx_year].value;
	  var idx_month  = checkForm._cc_expmonth.selectedIndex;
	  var exp_month  = checkForm._cc_expmonth.options[idx_month].value;
	 if((exp_year < now_year) || (exp_year == now_year && exp_month < now_month))
	 {
		if (!check_onError(checkForm, checkForm._cc_expmonth, checkForm._cc_expmonth.value, "You must enter a valid expiration date.\nThe date you entered has expired.", "Payment Information", 1)) 
		{
	      return false; 
	    }
	 }
	 return true;
}


function check_hasValue(obj, obj_type) {
	  if (obj_type == "TEXT") {
	    var strTemp;

	    strTemp = jQuery.trim(obj.value);
	    obj.value = strTemp;

	    if (strTemp.length == 0) {
	      return false;
	    } else {
	      return true;
	    }

	  } else if (obj_type == "NUMBER") {

	    if (obj >= 0) {
	      return true;
	    } else {
	      return false;
	    }

	  } else if (obj_type == "EMAIL") {

	    var supported = 0;
	    if (window.RegExp) {
	      var tempStr = "a";
	      var tempReg = new RegExp(tempStr);
	      if (tempReg.test(tempStr)) supported = 1;
	    }
	    if (!supported) return (obj.value.indexOf(".") > 2) && (obj.value.indexOf("@") > 0);
	    var r1 = new RegExp("(@.*@)|(\\.\\.)|(@\\.)|(^\\.)");
	    var r2 = new RegExp("^.+\\@(\\[?)[a-zA-Z0-9\\-\\.]+\\.([a-zA-Z]{2,3}|[0-9]{1,3})(\\]?)$");
	    return (!r1.test(obj.value) && r2.test(obj.value));

	  } else if (obj_type == "PHONE3") {

	    TestVar = isNumberString (obj.value);
	    if (obj.value.length != 3) {
	      return false;
	    } else {
	      if (TestVar != 1) {
	        return false;
	      } else {
	        return true;
	      }
	    }

	  } else if (obj_type == "PHONE4") {

	    TestVar = isNumberString (obj.value);
	    if (obj.value.length != 4) {
	      return false;
	    } else {
	      if (TestVar != 1) {
	        return false;
	      } else {
	        return true;
	      }
	    }

	  } else if (obj_type == "PASSWORD") {

	    if (obj.value.length == 0) {
	      return false;
	    } else {
	      return true;
	    }

	  } else if (obj_type == "SELECT") {
	    
	    Item = obj.selectedIndex;
	    Result = obj.options[Item].value;

	    if (Result.length == 0) 
	      	return false;
	    else 
	      	return true;

	  } else if (obj_type == "SINGLE_VALUE_RADIO" || obj_type == "SINGLE_VALUE_CHECKBOX") {

	    if (obj.checked)
	      return true;
	    else
	      return false;      

	  } else if (obj_type == "RADIO" || obj_type == "CHECKBOX") {

	    for (i=0; i < obj.length; i++) {
	      if (obj[i].checked)
	        return true;
	    }
	    return false;      

	  }
}

function check_onError(form_object, input_object, object_value, error_message, obj_section, focus) 
{
	window.alert('Incomplete '+obj_section+'\n--------------------------------------------\n'+error_message);
	if(focus == 1) {
		input_object.focus();
	}
	return false;	
}

$("#cc_type").change(function(){
	var index = $("#cc_type").attr("selectedIndex");
	
	if(index == 0)
	{
		index = "";
	}else
	{
		index = index - 1;
	}
	$("#payment_type_selected").val(index);
});

function showRemote()
{
	openwindow('checkout3.do?method=showCSCNumberHelp');
}
</script>