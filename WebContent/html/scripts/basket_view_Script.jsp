<script type="text/javascript">
function setExpirationDay(temp,orderId,shopperid,expiDate, expCurrentDate)
{
	var DateDiff = {			 
		    inDays: function(d1, d2) {
		        var t2 = d2.getTime();
		        var t1 = d1.getTime();
		 
		        return parseInt((t2-t1)/(24*3600*1000));
		    },
		 
		    inWeeks: function(d1, d2) {
		        var t2 = d2.getTime();
		        var t1 = d1.getTime();
		 
		        return parseInt((t2-t1)/(24*3600*1000*7));
		    },
		 
		    inMonths: function(d1, d2) {
		        var d1Y = d1.getFullYear();
		        var d2Y = d2.getFullYear();
		        var d1M = d1.getMonth();
		        var d2M = d2.getMonth();
		 
		        return (d2M+12*d2Y)-(d1M+12*d1Y);
		    },
		 
		    inYears: function(d1, d2) {
		        return d2.getFullYear()-d1.getFullYear();
		    }
		};

	var expirationDate = $(temp).parent('td').children('.datepicker').val();	    
	var datepicker = $(temp).parent('td').children('.datepicker');
	var d1 = new Date(expirationDate);
	var d2 = new Date();
	
	if(DateDiff.inDays(d2, d1)<=(expiDate-1))
	{
		setExpirationDate(expirationDate, orderId, shopperid, datepicker, expCurrentDate);	
	}
	else
	{
		alert('Max Expiration Date is '+expiDate+'.\nThe New Expiration Date should be less than or equal Max Expiration Date');
		datepicker.datepicker( "setDate" , expCurrentDate);
	}
}

function setExpirationDate(expirationDate, orderId, shopperid, datepicker, expCurrentDate) 
{	
	$.ajax({			
		url : "shopper_manager.do",
		type : 'POST',
		cache : false,
		data : 
		{
			 method : "setExpirationDate",				 
			 expirationDate : expirationDate,
			 orderId : orderId,
			 shopperid : shopperid			 
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			var data = jQuery.trim(html);
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	
			{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer)
			{ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else
			{
				alert(data);
				if(data != 'Set expiration date is successful.')
				{
					datepicker.datepicker( "setDate" , expCurrentDate );
				}
			}
		},
		error : function() 
		{
			alert("Error occured in ajax call");
		}
	});
}
</script>