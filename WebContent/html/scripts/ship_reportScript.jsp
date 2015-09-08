
<script>
$('#pageTitle').text("MRI Order Number : Monthly Reports");
$('#topTitle').text("MRI Order Number : Monthly Reports");

$(function() {		
	$("#datepickerFrom").datepicker({showOn: 'button', buttonImage: '/AgentTool/images/calendar.gif', buttonImageOnly: true});
	$("#datepickerTo").datepicker({showOn: 'button', buttonImage: '/AgentTool/images/calendar.gif', buttonImageOnly: true});
});	


$("#runShippingReport").click(function(){		
	var datepickerFrom= $('#datepickerFrom').val();
	var datepickerTo= $('#datepickerTo').val();		
	var checkdayFrom=new Date(datepickerFrom)	;
	var checkdayTo=new Date(datepickerTo)	;		
	if(checkdayFrom<=checkdayTo){
		searchShippingReport(datepickerFrom, datepickerTo);
	}
	else{
	alert('From Date should be less than or equal To Date.');
			}	
});

function searchShippingReport(datepickerFrom, datepickerTo) {			
	$.ajax({			
		url : "ship_report.do",
		type : 'POST',
		cache : false,
		data : {
			 method : "searchShippingReport",				 
			 datepickerFrom : datepickerFrom,
			 datepickerTo : datepickerTo			 
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			alert('suss');
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#resultShippingReport').html(data);
				}	
			
		},
		error : function() {
			alert("Error occured in ajax call");
		}
	});
}
</script>

