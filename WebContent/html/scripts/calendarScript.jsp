<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>

<%@page import="com.dell.enterprise.agenttool.util.Constants"%><link type="text/css" href="<%=request.getContextPath() %>/styles/jquery.ui.all.css" rel="stylesheet" />
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.ui.widget.js"></script>
<link type="text/css" href="<%=request.getContextPath() %>/styles/demos.css"
	rel="stylesheet" />	

<script>
$(document).ready(function() {	
	
	$('#datepickerFrom').val();
	$('#datepickerTo').val();
	
    // put all your jQuery goodness in here.

});

$(function() {		
	$("#datepickerFrom").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true,
			 onSelect: function( selectedDate, inst) {
				 if($("#datepickerTo") != null)
				 {
					 var startDate = selectedDate;
					 var endDate = $("#datepickerTo").datepicker( "getDate" );
					 if(compareDate(startDate,endDate)==false)
					 {
						 $("#datepickerFrom").datepicker( "hide" );
						 $("#datepickerFrom").datepicker( "setDate" , endDate );
						 alert("From Date must be less than To Date !");
					 }
				 }
			 }
			 });
	$("#datepickerTo").datepicker({
			showOn: 'button', 
			buttonImage: '<%=request.getContextPath() %>/images/calendar.gif',
		    buttonImageOnly: true,
		    changeMonth: true,
			changeYear: true
			});
	$(".datepicker").datepicker({
		showOn: 'button', 
		buttonImage: '<%=request.getContextPath() %>/images/calendar.gif',		
	    buttonImageOnly: true,
	    changeMonth: true,
		changeYear: true});

	$(".datepickerCommissions").datepicker({
		showOn: 'button', 
		buttonImage: '<%=request.getContextPath() %>/images/calendar.gif',	
		minDate : <%=Constants.getCurrentDate()%>,
	    buttonImageOnly: true,
	    changeMonth: true,
		changeYear: true});
	
	var tem=getMinDate()+':'+getMaxDate();
	
	function getMaxDate(){
		var d = new Date();
		var temp =  d.getFullYear() + 10;
		return temp;
	}
	function getMinDate(){
		var d = new Date();
		var temp =  d.getFullYear() - 10;
		return temp;
	}
	 
	
	$("#tax_exempt_expire").datepicker({
		 showOn: 'button',
		 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
		 buttonImageOnly: true,
		 changeMonth: true,
		 changeYear: true,
		 yearRange: tem
		 
	});
	
	
	
	 if ($("#latexpdate") != null)
	 {
		 $("#latexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }

	 if ($("#insexpdate") != null)
	 {
		 $("#insexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }

	 if ($("#optexpdate") != null)
	 {
		 $("#optexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }

	 if ($("#dimexpdate") != null)
	 {
		 $("#dimexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }

	 if ($("#monexpdate") != null)
	 {
		 $("#monexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }

	 if ($("#serexpdate") != null)
	 {
		 $("#serexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }

	 if ($("#worexpdate") != null)
	 {
		 $("#worexpdate").datepicker({
			 showOn: 'button',
			 buttonImage: '<%=request.getContextPath() %>/images/calendar.gif', 
			 buttonImageOnly: true,
			 changeMonth: true,
			 changeYear: true}); 
	 }
});



function getCurrentTime(){
	var currentTime = new Date();
	var month = currentTime.getMonth() + 1;
	var day = currentTime.getDate();
	var year = currentTime.getFullYear();
	if(month<10){month="0"+month;}	
	if(day<10){month="0"+day;}	;
	return month + "/" + day + "/" + year;		
}
</script>