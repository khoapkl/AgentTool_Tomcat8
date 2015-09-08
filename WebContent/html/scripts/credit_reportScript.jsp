<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%><link type="text/css"
	href="<%=request.getContextPath() %>/styles/ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery-1.4.4.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/ui.datepicker.js"></script>
<link type="text/css" href="<%=request.getContextPath() %>/styles/demos.css"
	rel="stylesheet" />	
<script>

	$('#pageTitle').text("Credit Report");
	$('#topTitle').text("Credit Report");
	$(function() {		
		$("#datepickerFrom").datepicker({showOn: 'button', buttonImage: '/AgentTool/images/calendar.gif', buttonImageOnly: true});
		$("#datepickerTo").datepicker({showOn: 'button', buttonImage: '/AgentTool/images/calendar.gif', buttonImageOnly: true});
	});	
	
	
	$("#searchCreditReport").click(function(){
		var datepickerFrom= $('#datepickerFrom').val();
		var datepickerTo= $('#datepickerTo').val();	
		var datepickerFromTextField = document.getElementById('datepickerFrom');
		var datepickerToTextField = document.getElementById('datepickerTo');			
		if((validateDate(datepickerFromTextField))& (validateDate(datepickerToTextField))){
			searchCreditReport(datepickerFrom, datepickerTo);
		}
		else{
		alert('The date format should be : mm/dd/yyyy ');
				}	
	});

	function searchCreditReport(datepickerFrom, datepickerTo) {			
		$.ajax({			
			url : "order_db.do",
			type : 'POST',
			cache : false,
			data : {
				 method : "searchCreditReport",				 
				 datepickerFrom : datepickerFrom,
				 datepickerTo : datepickerTo			 
			},
			dataType : 'html',
			async : true,
			success : function(html) {
				var data = html.replace(/[\r\n]+/g, "");
				if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
					document.location = "<%=request.getContextPath()%>/authenticate.do";
				}
				else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
					document.location = "<%=request.getContextPath()%>/login.do";
				}
				else{
					$('#order_list_results').html(data);
					}
			},
			error : function() {
				alert("Error occured in ajax call");
			}
		});
	}
	 function isInteger(s){
			var i;
		    for (i = 0; i < s.length; i++){   
		        // Check that current character is number.
		        var c = s.charAt(i);
		        if (((c < "0") || (c > "9"))){ 
		        return false;
		        }
		    }
		    // All characters are numbers.
		    return true;
		}
	 function stripCharsInBag(s, bag){
		   	var i;
		       var returnString = "";
		       // Search through string's characters one by one.
		       // If character is not in bag, append to returnString.
		       for (i = 0; i < s.length; i++){   
		           var c = s.charAt(i);
		           if (bag.indexOf(c) == -1) returnString += c;
		       }
		       return returnString;
		   }

	 function daysInFebruary (year){

			// February has 29 days in any year evenly divisible by four,
		    // EXCEPT for centurial years which are not also divisible by 400.
		    return (((year % 4 == 0) && ( (!(year % 100 == 0)) || (year % 400 == 0))) ? 29 : 28 );
		}
		 
	   var dtCh= "/";
	   var minYear=1900;
	   var maxYear=2100;
	 function isDate(dtStr){
		   	var daysInMonth = DaysArray(12);
		   	var pos1=dtStr.indexOf(dtCh);
		   	var pos2=dtStr.indexOf(dtCh,pos1+1);
		   	var strMonth=dtStr.substring(0,pos1);
		   	var strDay=dtStr.substring(pos1+1,pos2);
		   	var strYear=dtStr.substring(pos2+1);
		   	strYr=strYear;
		   	if (strDay.charAt(0)=="0" && strDay.length>1) strDay=strDay.substring(1);
		   	if (strMonth.charAt(0)=="0" && strMonth.length>1) strMonth=strMonth.substring(1);
		   	for (var i = 1; i <= 3; i++) {
		   		if (strYr.charAt(0)=="0" && strYr.length>1) strYr=strYr.substring(1);
		   	}
		   	month=parseInt(strMonth);
		   	day=parseInt(strDay);
		   	year=parseInt(strYr);
		   	if (pos1==-1 || pos2==-1){		   		
		   		return false;
		   	}
		   	if (strMonth.length<1 || month<1 || month>12){
		   		alert("Please enter a valid month");
		   		return false;
		   	}
		   	if (strDay.length<1 || day<1 || day>31 || (month==2 && day>daysInFebruary(year)) || day > daysInMonth[month]){
		   		alert("Please enter a valid day");
		   		return false;
		   	}
		   	if (strYear.length != 4 || year==0 || year<minYear || year>maxYear){
		   		alert("Please enter a valid 4 digit year between "+minYear+" and "+maxYear);
		   		return false;
		   	}
		   	if (dtStr.indexOf(dtCh,pos2+1)!=-1 || isInteger(stripCharsInBag(dtStr, dtCh))==false){
		   		alert("Please enter a valid date");
		   		return false;
		   	}
		   return true;
		   }

	
	 function validateDate(date){		
				if (isDate(date.value)==false){
					date.value="";
					date.focus();
					return false;
				}
				return true;		
		    }		   
	function DaysArray(n) {
		for (var i = 1; i <= n; i++) {
			this[i] = 31;
			if (i==4 || i==6 || i==9 || i==11) {this[i] = 30;};
			if (i==2) {this[i] = 29;};
	   } 
	   return this;
	}

		
	</script>