<%@page import="com.dell.enterprise.agenttool.util.Constants"%><SCRIPT LANGUAGE="JavaScript" type="text/javascript">

$('document').ready(function() {
	/* $("#BillingInformationTable :input").attr('disabled','true');
	
	$('#changeProfile').change(function (){
		if($('#changeProfile').is(':checked')){
			$("#BillingInformationTable :input").removeAttr('disabled');
			
		}else{
			$("#BillingInformationTable :input").attr('disabled','true');
			
		}
		
	}); */
	
	/*  if(session.getAttribute(Constants.IS_CUSTOMER)!=null){
		 alert('aaa');
		$('#changeProfile').detach();
	} */
			
		
	if (!($('#tax_exempt').attr("checked")))
	{
		$('#tax_exempt_number').attr("readonly","readonly");	
		$('#tax_exempt_number').addClass("disabled");			
					
		$("#tax_exempt_expire").datepicker("disable");	
		$("#tax_exempt_expire").attr("disabled","");
		$("#tax_exempt_expire").addClass("disabled");
	}
	
	enableDiscountItem('lat');
	enableDiscountItem('inst');
	enableDiscountItem('opt');
	enableDiscountItem('dim');
	enableDiscountItem('mon');
	enableDiscountItem('ser');
	enableDiscountItem('wor');
	
			
	if (($("#lat_pd") != null) && !($('#lat_pd').attr("checked")))	 
		$("#latexpdate").datepicker("disable"); 

	if (($("#ins_pd") != null) && !($('#ins_pd').attr("checked")))	 
		$("#insexpdate").datepicker("disable");

	if (($("#opt_pd") != null) && !($('#opt_pd').attr("checked")))	 
		$("#optexpdate").datepicker("disable");
	
	if (($("#dim_pd") != null) && !($('#dim_pd').attr("checked")))	 
		$("#dimexpdate").datepicker("disable");

	if (($("#mon_pd") != null) && !($('#mon_pd').attr("checked")))	 
		$("#monexpdate").datepicker("disable");

	if (($("#ser_pd") != null) && !($('#ser_pd').attr("checked")))	 
		$("#serexpdate").datepicker("disable");

	if (($("#wor_pd") != null) && !($('#wor_pd').attr("checked")))	 
		$("#worexpdate").datepicker("disable");
	
	<%if ((session.getAttribute(Constants.IS_CUSTOMER)==null) && !rightToEditBillInfo(agent.isAdmin(),agent.getUserLevel()))
	{
		%>
		toggleBillingInfo(0);	
	<%} %>
	
	if (document.location.toString().search('bill_to_fname') > 0)
	{
		$('#bill_to_fname').focus();
	}
	else if (document.location.toString().search('ship_to_fname') > 0)
	{
		$('#ship_to_fname').focus();
	}
});

function toggleBillingInfo(toggle)
{
	if (toggle == 1)
	{
		
	}
	else
	{		
		$('#bill_to_fname').attr("disabled","disabled");
		$('#bill_to_lname').attr("disabled","disabled");
		$('#bill_to_title').attr("disabled","disabled");
		$('#bill_to_company').attr("disabled","disabled");
		$('#bill_to_address1').attr("disabled","disabled");
		$('#bill_to_address2').attr("disabled","disabled");
		$('#bill_to_city').attr("disabled","disabled");
		$('#bill_to_state').attr("disabled","disabled");
		$('#bill_to_country').attr("disabled","disabled");
		$('#bill_to_postal').attr("disabled","disabled");
		$('#bill_to_phone_area').attr("disabled","disabled");
		$('#bill_to_phone_exch').attr("disabled","disabled");
		$('#bill_to_phone').attr("disabled","disabled");
		$('#bill_to_phoneext').attr("disabled","disabled");		
		$('#bill_to_fax_area').attr("disabled","disabled");
		$('#bill_to_fax_exch').attr("disabled","disabled");
		$('#bill_to_fax').attr("disabled","disabled");
		$('#bill_to_email').attr("disabled","disabled");
		$('#credit_line').attr("disabled","disabled");
		$('#credit_expire').attr("disabled","disabled");
		$('#credit_available').attr("disabled","disabled");
		$('#credit_comment').attr("disabled","disabled");		
	}
}

function resetTax(toggle)
{	
	if (toggle == 1)
	{
		$('#tax_exempt_number').attr("readonly","");
		
		
		$('#tax_exempt_number').removeClass("disabled");
		
		
		$("#tax_exempt_expire").datepicker("enable");

		if ($("#tax_exempt_expire").val() == "01/01/1900")
		{
			$("#tax_exempt_expire").val(getNextDate());			
		}
	}
	else
	{
		$('#tax_exempt_number').attr("readonly","readonly");
		$("#tax_exempt_expire").datepicker("disable");
		
		
		$('#tax_exempt_number').addClass("disabled");
			
		$("#tax_exempt_expire").attr("disabled","");
		$("#tax_exempt_expire").addClass("disabled");
	}
}

function getNextDate()
{ 
 	var today = new Date();
 	today.setDate(today.getDate()+1);
	var Ndate = (today.getMonth()+ 1) + "/" + today.getDate() + "/" + today.getFullYear();
 	return Ndate;
}

function check_len(checkedfield, lensize)
{
	var thisfield = checkedfield.value;
	if (thisfield.length > lensize)
	{
		alert("Too many characters have been entered (" + thisfield.length + "). Number of characters must be less than " + lensize);
		$(checkedfield).focus();
	}
}

function changeAgentSelection()
{	
	if (($('#agent_ID').val() == 0) || (agentNameEmail[$('#agent_ID').val()] == 'null'))
		$('#lblEmail').html('');	
	else
		$('#lblEmail').html(agentNameEmail[$('#agent_ID').val()]);
}

function validateCustomer(addnew){	
	
		/*$.post("shopper.do?method=validateCustomer", $("#NewUser").serialize(),
			        function(html){
		var data = html.replace(/[\r\n]+/g, "");	
		$('#error_results').html(data);
							alert(data);		
			        }, "json");*/

	dialogOpen();
	if(addnew!=null && addnew!='null' && addnew!=''){				 
		$.ajax({
		      url: "shopper.do?method=validateCustomer&manage=true",
		      type: "POST",
		      data: ($("#NewUser").serialize()),
		      dataType: "html",
		      async:true,
		      success: function(msg){
		    	  	dialogClose();	
		    	 	var data = msg.replace(/[\r\n]+/g, "");
		    		if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1)
					{
						<% if (session.getAttribute(Constants.IS_CUSTOMER) != null)
							{
						%>
							document.location = "<%=request.getContextPath()%>/authenticate.do";
						<%
						} else {
						%>	
							document.location = "<%=request.getContextPath()%>/login.do";
						<% }%>
							
					}
		    		else
		    		{		
						$('#error_results').html(data);
						window.location.hash= "body-master";
		    		}
		      },
				error : function() {
		    	  dialogClose();
		    	  $('#error_results').html("<b>Error execute in process!</b>");	
			}
		   });
	}else{
		$.ajax({
		      url: "shopper.do?method=validateCustomer",
		      type: "POST",
		      data: ($("#NewUser").serialize()),
		      dataType: "html",
		      async:true,
		      success: function(msg){
		    	 	dialogClose();	
		    	  	var data = msg.replace(/[\r\n]+/g, "");	
		    		if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1)
					{
						<% if (session.getAttribute(Constants.IS_CUSTOMER) != null)
							{
						%>
							document.location = "<%=request.getContextPath()%>/authenticate.do";
						<%
						} else {
						%>	
							document.location = "<%=request.getContextPath()%>/login.do";
						<% }%>
							
					}
		    		else
		    		{					
						$('#error_results').html(data);
		    		}
		      },
				error : function() {
		    	  $('#error_results').html("<b>Error execute in search process!</b>");
			}
		   });
	}
};

function copyBilling(theForm)
{
  theForm.ship_to_fname.value             = theForm.bill_to_fname.value;
  theForm.ship_to_lname.value             = theForm.bill_to_lname.value;
  theForm.ship_to_title.value             = theForm.bill_to_title.value;
  theForm.ship_to_company.value           = theForm.bill_to_company.value;
  theForm.ship_to_address1.value          = theForm.bill_to_address1.value;
  theForm.ship_to_address2.value          = theForm.bill_to_address2.value;
  theForm.ship_to_city.value              = theForm.bill_to_city.value;
  theForm.ship_to_state.selectedIndex     = theForm.bill_to_state.selectedIndex;
  theForm.ship_to_postal.value            = theForm.bill_to_postal.value;
  theForm.ship_to_country.selectedIndex   = theForm.bill_to_country.selectedIndex;
  theForm.ship_to_phone_area.value        = theForm.bill_to_phone_area.value;
  theForm.ship_to_phone_exch.value        = theForm.bill_to_phone_exch.value;
  theForm.ship_to_phone.value             = theForm.bill_to_phone.value;
  theForm.ship_to_phoneext.value          = theForm.bill_to_phoneext.value;
  theForm.ship_to_email.value			  = theForm.bill_to_email.value;
}

function trimString(userInput){	
    var iStart, iEnd;
    var sTrimmed;
    var cChar;

    iEnd = userInput.length - 1;
    iStart = 0;
    bLoop = true;

    cChar = userInput.charAt(iStart);
    while ((iStart < iEnd) && ((cChar == "\n") || (cChar == "\r") ||
                              (cChar == "\t") || (cChar == " "))){
       iStart ++;
       cChar = userInput.charAt(iStart);
    }

    cChar = userInput.charAt(iEnd);
    while ((iEnd >= 0) && ((cChar == "\n") || (cChar == "\r") ||
                          (cChar == "\t") || (cChar == " "))){
       iEnd --;
       cChar = userInput.charAt(iEnd);
    }

    if (iStart <= iEnd){
       sTrimmed = userInput.substring(iStart, iEnd + 1);
    } else {
       sTrimmed = "";
    }
    
    return sTrimmed;
 }

function isNumberString (InString)  {
	if(InString.length==0) return (false);
	var RefString="1234567890";
	for (Count=0; Count < InString.length; Count++)  {
		TempChar= InString.substring (Count, Count+1);
		if (RefString.indexOf (TempChar, 0)==-1)  
			return (false);
	}
	return (true);
}

function checkPD(theChkBox, theAmtField, thePerField, theExpField, theProduct)
{		
	if (theChkBox.checked) 
	{		
		if (theExpField.value=="") 
		{	
			window.alert('Incomplete Product Discount\n--------------------------------------------\nYou must specify a ' +theProduct+ ' Expiration Date.');
			theExpField.focus();
			return false;
		}
		else
		{
		  	var currentDate = new Date('<%= Constants.getCurrentDate() %>');
		   	var expDate = new Date(theExpField.value);
		   	
		   	if(currentDate.getTime() > expDate.getTime())
		   	{
		   		window.alert('Incomplete Product Discount\n--------------------------------------------\n'+theProduct+' Expiration Date is invalid, must be later from today.');
				theExpField.focus();
			    return false; 			   
			}
		}
		if ((thePerField.value=="") || ($.trim(thePerField.value).length == 0)) 
		{	
			thePerField.value = $.trim(thePerField.value);
			theAmtField.value = $.trim(theAmtField.value);
				
			if (theAmtField.value=="")
			{		
				window.alert('Incomplete Product Discount\n--------------------------------------------\nYou must specify a ' +theProduct+ ' percent or dollar discount.');
				thePerField.focus();
				return false;
			}
			else
			{				
				if (isNaN(theAmtField.value))
				{
					window.alert('Invalid Product Discount\n--------------------------------------------\nThe ' +theProduct+ ' Discount $ must be a number.');
					theAmtField.focus();
					return false;
				}				
				else if (parseInt(theAmtField.value) > <%= session.getAttribute("maxDiscountAmt") %>)					
				{
					window.alert('Invalid Product Discount\n--------------------------------------------\nThe ' +theProduct+ ' Discount $ exceeds the maximum allowable discount of $' +<%= session.getAttribute("maxDiscountAmt") %>+ '.');
					theAmtField.focus();
					return false;
				}
				else if (parseInt(theAmtField.value) < 0)
				{
					window.alert('Invalid Product Discount\n--------------------------------------------\nThe ' +theProduct+ ' Discount $ must be a value greater than 0.');
					theAmtField.focus();
					return false;
				}
			}
		}
		else if ((theAmtField.value=="") || ($.trim(theAmtField.value).length == 0))
		{
			thePerField.value = $.trim(thePerField.value);
			theAmtField.value = $.trim(theAmtField.value);
			
			if (isNaN(thePerField.value))
			{
				window.alert('Invalid Product Discount\n--------------------------------------------\nThe ' +theProduct+ ' Discount % must be a number.');
				thePerField.focus();
				return false;
			}	
			
			if (parseInt(thePerField.value) > <%= session.getAttribute("maxDiscount") %>)
			{
				window.alert('Invalid Product Discount\n--------------------------------------------\nThe ' +theProduct+ ' Discount % exceeds the maximum allowable discount of ' +<%= session.getAttribute("maxDiscount") %>+ '%.');
				thePerField.focus();
				return false;
			}
			else if (parseInt(thePerField.value) < 0)
			{
				window.alert('Invalid Product Discount\n--------------------------------------------\nThe ' +theProduct+ ' Discount % must be a value greater than 0.');
				thePerField.focus();
				return false;
			}
		}
		else
		{
			window.alert('Invalid Product Discount\n--------------------------------------------\nYou may only specify a ' +theProduct+ ' percent or dollar discount.');
			thePerField.focus();
			return false;
		}
		return true;
    }
	else
	{		
		<% if (usingManager) { %>
			<% 
			if (agent_level == "A")
			{ %>
				if (theExpField.value!="")
				{
					window.alert('Invalid Product Discount\n--------------------------------------------\nYou have entered data without checking the box!.');
					theChkBox.focus();
					return false;
				}
				else if (theAmtField.value!="")
				{
					window.alert('Invalid Product Discount\n--------------------------------------------\nYou have entered data without checking the box!');
					theChkBox.focus();
					return false;
				}
				else if (thePerField.value!="")
				{
					window.alert('Invalid Product Discount\n--------------------------------------------\nYou have entered data without checking the box!');
					theChkBox.focus();
					return false;
				}
			<% } %>
				return true;
		<% } else { %>
			if (theExpField.value!="")
			{
				window.alert('Invalid Product Discount\n--------------------------------------------\nYou have entered data without checking the box!.');
				theChkBox.focus();
				return false;
			}
			else if (theAmtField.value!="")
			{
				window.alert('Invalid Product Discount\n--------------------------------------------\nYou have entered data without checking the box!');
				theChkBox.focus();
				return false;
			}
			else if (thePerField.value!="")
			{
				window.alert('Invalid Product Discount\n--------------------------------------------\nYou have entered data without checking the box!');
				theChkBox.focus();
				return false;
			}
		<% } %>
			return true;
	}
}

function check_onError(form_object, input_object, object_value, error_message, obj_section, focus) {
	  window.alert('Incomplete '+obj_section+'\n--------------------------------------------\n'+error_message);
	  if(focus == 1) {
	  input_object.focus();
	  }
	  return false;	
}

function check_hasValue(obj, obj_type) {

	  if (obj_type == "TEXT") {
	    var strTemp;

	    strTemp = trimString(obj.value);
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

	    TestVar = isNumberString (obj.value)
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

	    TestVar = isNumberString (obj.value)
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
	    if (obj.value.length > 20 || obj.value.length < 6) {
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

function DefaultDate(theForm, indexInt)
{	
	if (indexInt==0)
	{
		if (theForm.lat_pd.checked)
		{
			theForm.latexpdate.value = "<%=calculateExpiredDate(90)%>";				
		}
		else
		{
			theForm.latperdiscount.value = "";
			theForm.latamtdiscount.value = "";
			theForm.latexpdate.value = "";
		}
	}
	else if (indexInt==1)
	{
		if (theForm.ins_pd.checked)
		{
			theForm.insexpdate.value = "<%=calculateExpiredDate(90)%>";
		}
		else
		{
			theForm.insperdiscount.value = "";
			theForm.insamtdiscount.value = "";
			theForm.insexpdate.value = "";
		}
	}
	else if (indexInt==2)
	{
		if (theForm.opt_pd.checked)
		{
			theForm.optexpdate.value = "<%=calculateExpiredDate(90)%>";
		}
		else
		{
			theForm.optperdiscount.value = "";
			theForm.optamtdiscount.value = "";
			theForm.optexpdate.value = "";
		}
	}
	else if (indexInt==3)
	{
		if (theForm.dim_pd.checked)
		{
			theForm.dimexpdate.value = "<%=calculateExpiredDate(90)%>";
		}
		else
		{
			theForm.dimperdiscount.value = "";
			theForm.dimamtdiscount.value = "";
			theForm.dimexpdate.value = "";
		}
	}
	else if (indexInt==4)
	{
		if (theForm.mon_pd.checked)
		{
			theForm.monexpdate.value = "<%=calculateExpiredDate(90)%>";
		}
		else
		{
			theForm.monperdiscount.value = "";
			theForm.monamtdiscount.value = "";
			theForm.monexpdate.value = "";
		}
	}
	else if (indexInt==5)
	{
		if (theForm.ser_pd.checked)
		{
			theForm.serexpdate.value = "<%=calculateExpiredDate(90)%>";
		}
		else
		{
			theForm.serperdiscount.value = "";
			theForm.seramtdiscount.value = "";
			theForm.serexpdate.value = "";
		}
	}
	else if (indexInt==6)
	{
		if (theForm.wor_pd.checked)
		{
			theForm.worexpdate.value = "<%=calculateExpiredDate(90)%>";
		}
		else
		{
			theForm.worperdiscount.value = "";
			theForm.woramtdiscount.value = "";
			theForm.worexpdate.value = "";
		}
	}
}


function check_Form(checkForm,addnew) {
	/* $("#BillingInformationTable :input").removeAttr('disabled');
	$("#ShippingTable :input").removeAttr('disabled'); */
	
	  if (!check_hasValue(checkForm.bill_to_fname, "TEXT" )) {	
	   if (!check_onError(checkForm, checkForm.bill_to_fname, checkForm.bill_to_fname.value, "You must give your first name.", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.bill_to_lname, "TEXT" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_lname, checkForm.bill_to_lname.value, "You must give your last name.", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	
	  if (!check_hasValue(checkForm.bill_to_address1, "TEXT" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_address1, checkForm.bill_to_address1.value, "You must give your address.", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.bill_to_city, "TEXT" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_city, checkForm.bill_to_city.value, "You must give your city.", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.bill_to_country, "SELECT" )) {
	    if (!check_onError(checkForm, checkForm.bill_to_country, checkForm.bill_to_country.value, "You must give your country.", "Billing Address", 1)) {
	      return false; 
	    }
	  } else {
	    CountrySelected = checkForm.bill_to_country.value;
	    if (CountrySelected=="US") {

	      if (!check_hasValue(checkForm.bill_to_state, "SELECT" )) {
	        if (!check_onError(checkForm, checkForm.bill_to_state, checkForm.bill_to_state.value, "You must give your state.", "Billing Address", 1)) {
	          return false; 
	        }
	      }

	      if (!check_hasValue(checkForm.bill_to_postal, "TEXT" )) {
	       if (!check_onError(checkForm, checkForm.bill_to_postal, checkForm.bill_to_postal.value, "You must give your zip.", "Billing Address", 1)) {
	          return false; 
	        }
	      }
	    }
	  }

	  if (!check_hasValue(checkForm.bill_to_phone_area, "PHONE3" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_phone_area, checkForm.bill_to_phone_area.value, "Your area code must be 3 numeric digits.\n  (XXX) xxx-xxxx", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.bill_to_phone_exch, "PHONE3" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_phone_exch, checkForm.bill_to_phone_exch.value, "Your phone number exchange must be 3 numeric digits.\n  (xxx) XXX-xxxx", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.bill_to_phone, "PHONE4" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_phone, checkForm.bill_to_phone.value, "Your phone number must end with 4 numeric digits.\n  (xxx) xxx-XXXX", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if (checkForm.bill_to_phone.value.length < 4) {
	   if (!check_onError(checkForm, checkForm.bill_to_phone, checkForm.bill_to_phone.value, "Your phone number must end with 4 digits.", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	  if ((checkForm.bill_to_fax_area.value.length > 0) || (checkForm.bill_to_fax_exch.value.length > 0) ||
			  (checkForm.bill_to_fax.value.length > 0))
	  {	  
			  if (!check_hasValue(checkForm.bill_to_fax_area, "PHONE3" )) {
				   if (!check_onError(checkForm, checkForm.bill_to_fax_area, checkForm.bill_to_fax_area.value, "Your fax area code must be 3 numeric digits.\n  (XXX) xxx-xxxx", "Billing Address", 1)) {
				      return false; 
				    }
				  }
		
			  if (!check_hasValue(checkForm.bill_to_fax_exch, "PHONE3" )) {
			   if (!check_onError(checkForm, checkForm.bill_to_fax_exch, checkForm.bill_to_fax_exch.value, "Your fax number exchange must be 3 numeric digits.\n  (xxx) XXX-xxxx", "Billing Address", 1)) {
			      return false; 
			    }
			  }
		
			  if (!check_hasValue(checkForm.bill_to_fax, "PHONE4" )) {
			   if (!check_onError(checkForm, checkForm.bill_to_fax, checkForm.bill_to_fax.value, "Your fax number must end with 4 numeric digits.\n  (xxx) xxx-XXXX", "Billing Address", 1)) {
			      return false; 
			    }
			  }
		
			  if (checkForm.bill_to_fax.value.length < 4) {
			   if (!check_onError(checkForm, checkForm.bill_to_fax, checkForm.bill_to_fax.value, "Your fax number must end with 4 digits.", "Billing Address", 1)) {
			      return false; 
			    }
			  }
	  }
  
	  if (!check_hasValue(checkForm.bill_to_email, "EMAIL" )) {
	   if (!check_onError(checkForm, checkForm.bill_to_email, checkForm.bill_to_email.value, "You must enter a valid E-mail contact address.", "Billing Address", 1)) {
	      return false; 
	    }
	  }

	
	 
	<% if (usingManager) { %>

			if (!checkPD(checkForm.lat_pd,checkForm.latamtdiscount,checkForm.latperdiscount,checkForm.latexpdate,"Latitude Notebooks"))
			{
				return false;
			}
		
			if (!checkPD(checkForm.ins_pd,checkForm.insamtdiscount,checkForm.insperdiscount,checkForm.insexpdate,"Inspiron Notebooks"))
			{
				return false;
			}
			
			if (!checkPD(checkForm.opt_pd,checkForm.optamtdiscount,checkForm.optperdiscount,checkForm.optexpdate,"Optiplex Desktops"))
			{
				return false;
			}

			if (!checkPD(checkForm.dim_pd,checkForm.dimamtdiscount,checkForm.dimperdiscount,checkForm.dimexpdate,"Dimension Desktops"))
			{
				return false;
			}
			
			if (!checkPD(checkForm.mon_pd,checkForm.monamtdiscount,checkForm.monperdiscount,checkForm.monexpdate,"Monitors"))
			{
				return false;
			}
			
			if (!checkPD(checkForm.ser_pd,checkForm.seramtdiscount,checkForm.serperdiscount,checkForm.serexpdate,"Servers"))
			{
				return false;
			}
			
			if (!checkPD(checkForm.wor_pd,checkForm.woramtdiscount,checkForm.worperdiscount,checkForm.worexpdate,"Workstations"))
			{
				return false;
			}
			
			if (checkForm.tax_exempt[0].checked) 
			{
			    if (!check_hasValue(checkForm.tax_exempt_number, "TEXT" )) 
				{	
			      if (!check_onError(checkForm, checkForm.tax_exempt_number, checkForm.tax_exempt_number.value, "You must specifify a tax exemption Number.", "Discount Information", 1)) 
				  {
			        return false; 
			      }
			    }
			
			   	if (!check_hasValue(checkForm.tax_exempt_expire, "TEXT" )) 
				{
				    if (!check_onError(checkForm, checkForm.tax_exempt_expire, checkForm.tax_exempt_expire.value, "You must specifify a tax exemption Expiration Date.", "Discount Information", 1)) 
					{
				       return false; 
				    }
			   	}

			  // 	var currentDate = new Date(' < Constants.getCurrentDate() > ');
			   //	var expDate = new Date(checkForm.tax_exempt_expire.value);
			   	
			   	/*if(currentDate.getTime() > expDate.getTime())
			   	{
			   	 	if (!check_onError(checkForm, checkForm.tax_exempt_expire, checkForm.tax_exempt_expire.value, "Expiration Date is invalid.", "Discount Information", 1)) 
					{
				       return false; 
				    }
				}*/
			}

			
	<% } %>

	  if (!check_hasValue(checkForm.ship_to_fname, "TEXT" )) {
	    if (!check_onError(checkForm, checkForm.ship_to_fname, checkForm.ship_to_fname.value, "You must give your first name.", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.ship_to_lname, "TEXT" )) {
	    if (!check_onError(checkForm, checkForm.ship_to_lname, checkForm.ship_to_lname.value, "You must give your last name.", "Shipping Address", 1)) {
	      return false; 
	    }
	  }


	  if (!check_hasValue(checkForm.ship_to_address1, "TEXT" )) {
	    if (!check_onError(checkForm, checkForm.ship_to_address1, checkForm.ship_to_address1.value, "You must give your address.", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.ship_to_email, "EMAIL" )) {
		   if (!check_onError(checkForm, checkForm.ship_to_email, checkForm.ship_to_email.value, "You must enter a valid E-mail contact address.", "Shipping Address", 1)) {
		      return false; 
		    }
		}
	  
	  if (!check_hasValue(checkForm.ship_to_city, "TEXT" )) {
	   if (!check_onError(checkForm, checkForm.ship_to_city, checkForm.ship_to_city.value, "You must give your city.", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.ship_to_country, "SELECT" )) {
	    if (!check_onError(checkForm, checkForm.ship_to_country, checkForm.ship_to_country.value, "You must give your country.", "Shipping Address", 1)) {
	      return false; 
	    }
	  } else {

	    CountrySelected = checkForm.ship_to_country.value;
	    if (CountrySelected == "US") {
		    if (!check_hasValue(checkForm.ship_to_state, "SELECT" )) {
	        if (!check_onError(checkForm, checkForm.ship_to_state, checkForm.ship_to_state.value, "You must give your state.", "Shipping Address", 1)) {
	          return false; 
	        }
	      }

	      if (!check_hasValue(checkForm.ship_to_postal, "TEXT" )) {
	       if (!check_onError(checkForm, checkForm.ship_to_postal, checkForm.ship_to_postal.value, "You must give your zip code / postal code.", "Shipping Address", 1)) {
	          return false; 
	        }
	      }

	    }
	  }

	  if (!check_hasValue(checkForm.ship_to_phone_area, "PHONE3" )) {
	   if (!check_onError(checkForm, checkForm.ship_to_phone_area, checkForm.ship_to_phone_area.value, "Your area code must be three digits.\n  (XXX) xxx-xxxx", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.ship_to_phone_exch, "PHONE3" )) {
	   if (!check_onError(checkForm, checkForm.ship_to_phone_exch, checkForm.ship_to_phone_exch.value, "Your phone number exchange must be three digits.\n  (xxx) XXX-xxxx", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  if (!check_hasValue(checkForm.ship_to_phone, "PHONE4" )) {
	   if (!check_onError(checkForm, checkForm.ship_to_phone, checkForm.ship_to_phone.value, "Your phone number must end with four digits.\n  (xxx) xxx-XXXX", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  if (checkForm.ship_to_phone.value.length < 4) {
	   if (!check_onError(checkForm, checkForm.ship_to_phone, checkForm.ship_to_phone.value, "Your phone number must end with 4 digits.", "Shipping Address", 1)) {
	      return false; 
	    }
	  }

	  <% if (usingManager) { %>
		if ((checkForm.loginID != null) && !check_hasValue(checkForm.loginID, "TEXT"))
		{				
			   if (!check_onError(checkForm, checkForm.loginID, checkForm.loginID.value, "You must give Username.", "User Name Setup", 1)) 
			   {
			       return false; 
			   }
		}
		else if  ((checkForm.loginID != null) && check_hasValue(checkForm.loginID, "TEXT"))
		{
			 if (checkForm.loginID.value.length > 20) 
			 {
				 if (!check_onError(checkForm, checkForm.loginID, checkForm.loginID.value, "User name must be between 1 and 20 characters.", "User Name Setup", 1)) 
				   {
				       return false; 
				   }
			 }
		}
		
		if ((checkForm.pwd1 != null) && !check_hasValue(checkForm.pwd1, "PASSWORD"))
		{				
			   if (!check_onError(checkForm, checkForm.pwd1, checkForm.pwd1.value, "Password must be between 6 and 20 characters.", "User Name Setup", 1)) 
			   {
			       return false; 
			   }
		}
		else if ((checkForm.pwd1 != null) && (checkForm.pwd1.value != checkForm.pwd2.value))
		{				
				if (!check_onError(checkForm, checkForm.pwd2, checkForm.pwd2.value, "Confirm password does not match.", "User Name Setup", 1)) 
			    	return false; 					 
		}			

		if ((checkForm.hint != null) && !check_hasValue(checkForm.hint, "TEXT"))
		{				
			   if (!check_onError(checkForm, checkForm.hint, checkForm.hint.value, "You must give User Setup hint.", "User Name Setup", 1)) 
			   {
			       return false; 
			   }
		}
		else if ((checkForm.hint != null) && check_hasValue(checkForm.hint, "TEXT"))
		{
			
			 if (checkForm.hint.value.length > 50) 
			 {
				 if (!check_onError(checkForm, checkForm.hint, checkForm.hint.value, "User Setup hint must be between 1 and 50 characters.", "User Name Setup", 1)) 
				   {
				       return false; 
				   }
			 }
		}

		if ((checkForm.answer != null) && !check_hasValue(checkForm.answer, "TEXT"))
		{				
			   if (!check_onError(checkForm, checkForm.answer, checkForm.answer.value, "You must give User Setup answer.", "User Name Setup", 1)) 
			   {
			       return false; 
			   }
		}
		else if ((checkForm.answer != null) && check_hasValue(checkForm.answer, "TEXT"))
		{
			
			 if (checkForm.answer.value.length > 50) 
			 {
				 if (!check_onError(checkForm, checkForm.answer, checkForm.answer.value, "User Setup answer must be between 1 and 50 characters.", "User Name Setup", 1)) 
				   {
				       return false; 
				   }
			 }
		}
<% } %>
	  	
	<% if ((section=="new") || (section=="new_checkout") || (section=="new_register")) { %>
	  if (!check_hasValue(checkForm.equip_use, "RADIO" )) {
	    if (!check_onError(checkForm, checkForm.equip_use, checkForm.equip_use.value, "Please tell us how you intend to use DFS Direct Sales equipment.", "User Profile", 0)) {
	      return false; 
	    }
	  }
	<% } %>
	
	<% if (((section=="new") || (section=="new_checkout") || (section=="new_register")) && usingManager) {  %>

		if (checkForm.agent_ID.value==0) 
			{	
					window.alert('Incomplete Agent\n--------------------------------------------\nPlease select an Agent before submitting.');
					return false;				
			}
	<% } %>
	 validateCustomer(addnew);
	  return true;
}



<% if (usingManager) { %>
<%

java.text.SimpleDateFormat sdf = 
    new java.text.SimpleDateFormat("M-dd-yy");
java.util.Calendar cal = java.util.Calendar.getInstance();
String outputDate = sdf.format(cal.getTime());
outputDate = outputDate.replaceAll("-","");

sdf = new java.text.SimpleDateFormat("HH:mm:ss");
String outputTime = sdf.format(cal.getTime());
outputTime = outputTime.replaceAll(":","");

//Hardcode. Remember to remove or replace after combine 
//with another module which has "agent_name"
String agent_name = "agent_name";

String newID = outputDate + "_" + outputTime; 
%>
function blankInfo(theForm)
{

	
  theForm.bill_to_fname.value             = "held";
  theForm.bill_to_lname.value             = "<%= agent_name %>";
  theForm.bill_to_title.value             = "";
  theForm.bill_to_company.value           = "fake company";
  theForm.bill_to_address1.value          = "1234 nowhere st.";
  theForm.bill_to_address2.value          = "";
  theForm.bill_to_city.value              = "Austin";
  theForm.bill_to_state.selectedIndex     = 43;
  theForm.bill_to_postal.value            = "78728";
  //theForm.bill_to_country.selectedIndex   = 0;  
	$('#bill_to_country').val('US');
  theForm.bill_to_phone_area.value        = "512";
  theForm.bill_to_phone_exch.value        = "310";
  theForm.bill_to_phone.value             = "9903";
  theForm.bill_to_phoneext.value          = "";
  theForm.bill_to_email.value             = "<%= agent_name + newID %>@magrabbit.com";
  theForm.loginID.value                   = "newID_<%= newID %>";
  theForm.pwd1.value                      = "pass_<%= newID %>";
  theForm.pwd2.value                      = "pass_<%= newID %>";
  theForm.hint.value                      = "<%= agent_name + newID %>";
  theForm.answer.value                    = "<%= newID %>"; 
  


  theForm.ship_to_fname.value             = theForm.bill_to_fname.value;
  theForm.ship_to_lname.value             = theForm.bill_to_lname.value;
  theForm.ship_to_title.value             = theForm.bill_to_title.value;
  theForm.ship_to_company.value           = theForm.bill_to_company.value;
  theForm.ship_to_address1.value          = theForm.bill_to_address1.value;
  theForm.ship_to_address2.value          = theForm.bill_to_address2.value;
  theForm.ship_to_city.value              = theForm.bill_to_city.value;
  theForm.ship_to_state.selectedIndex     = theForm.bill_to_state.selectedIndex;
  theForm.ship_to_postal.value            = theForm.bill_to_postal.value;
  theForm.ship_to_country.selectedIndex   = theForm.bill_to_country.selectedIndex;
  theForm.ship_to_phone_area.value        = theForm.bill_to_phone_area.value;
  theForm.ship_to_phone_exch.value        = theForm.bill_to_phone_exch.value;
  theForm.ship_to_phone.value             = theForm.bill_to_phone.value;
  theForm.ship_to_phoneext.value          = theForm.bill_to_phoneext.value;
  theForm.ship_to_email.value             = theForm.bill_to_email.value;
}
<% } %>

function enableDiscountItem(family)
{	
	if($("#"+family+"_pd").attr('checked'))
	{
		$("#"+family+"perdiscount").attr("disabled","");
		$("#"+family+"amtdiscount").attr("disabled","");
		//$("#"+family+"expdate").attr("disabled","");
		$("#"+family+"expdate").datepicker("enable");
	}else
	{
		$("#"+family+"perdiscount").attr("disabled","disabled");
		$("#"+family+"amtdiscount").attr("disabled","disabled");
		//$("#"+family+"expdate").attr("disabled","disabled");
		$("#"+family+"expdate").datepicker("disable");
	}
}

</SCRIPT>