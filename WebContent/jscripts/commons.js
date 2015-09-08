function numericOnly (obj, e) {
	var key;
	var keychar;
	var isShift = false;
	var isCtrl = false;
	if (window.event)
		key = window.event.keyCode;
	else if (e)
		key = e.which;
	else
		return true;
	keychar = String.fromCharCode(key);
	if(e.ctrlKey)
		isCtrl = true;
	if( key == 16)
		isShift = true;
	/* control keys */
	if ((key==null) || (key==0) || (key==8) || (key==9) || (key==13) || (key==27) )
	   return true;
	/* numbers */
	else if ((("0123456789").indexOf(keychar) > -1)&& !isShift)
	   return true;
	else if ((key==99)&& isCtrl)
		 return true;
	else if ((key==118)&& isCtrl)
			   return true;
	else if ((key==88)&& isCtrl)
		   return true;
	else
	   return false;

}
function checkInteger(obj, subjectName){
	value = obj.value;
	if(isNaN(value)){
		obj.value = "";
		showErrorMsg(subjectName +" is not a number, please re-enter.");
		return false;
	}
	if(value== null || value=="") return true;
	if(parseInt(value)<0){
		showErrorMsg("Please enter "+ subjectName +" value equal or greater than 0.");
		obj.value = "";
		return false;
	}
	else if(value.indexOf('.') > 0){
		showErrorMsg("Field "+ subjectName +" allowed integer number only.");
		obj.value = "";
		return false;
	}
	return true;
}
function showErrorMsg(msg){
	alert(msg);
}
function showWarningMsg(msg){
	alert(msg);
}
function showSuccessMsg(msg){
	alert(msg);
}
function validateInputNumber(obj, objName) {
	value = obj.value;
	if (value != "") {
		if (isNaN(value)) {
			obj.value = "";
			obj.focus();
			return false;
		} else {
			if (checkInteger(obj, objName))
				return true;
			else {
				obj.value = "";
				obj.focus();
				return false;
			}
		}
	}
	else {
		   return true;
	   }
}
function validateEmail(emailInput) 
{
	var reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
	var address = emailInput.value.replace(/^\s+|\s+$/g, '');
	if(reg.test(address) == false && address != "") 
	{
		  alert('Invalid Email Address');
		  emailInput.value = "";
		  emailInput.focus();
	      return false;
   }else 
   {
	   return true;
   }
}

function openwindow(url)
{
	var day = new Date();
	var id = day.getTime();
	window.open(url,"_blank","status=1,toolbar=0,scrollbars=1,location=0,statusbars=1,menubar=0,resizable=0,width=540,height=440",false);
}


function validateDate(value)
{
	//Please enter a date in the format dd/mm/yyyy
	return value.match(/^\d\d?\/\d\d?\/\d\d\d\d$/);
}

function compareDate(startDate,endDate)
{
	var sDate = new Date(startDate);
	var eDate = new Date(endDate);
	if(sDate.getTime() > eDate.getTime())
		return false ;
	else 
		return true;
}

function checkNumber(x)
{
	var numericExpression = /^[0-9]+$/;
	if(x.match(numericExpression)) 
	{
		return true;
	} else {
		return false;
	}
}
