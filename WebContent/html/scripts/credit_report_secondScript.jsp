<script type="text/javascript">
	
	$('#pageTitle').text("Credit Report");
	$('#topTitle').text("Credit Report");
	
	$("#searchCreditReport").click(function(){		
		var datepickerFrom= $('#datepickerFrom').val();
		var datepickerTo= $('#datepickerTo').val();	
		$('#hiddenDatepickerFrom').val(datepickerFrom);
		$('#hiddenDatepickerTo').val(datepickerTo);
			
		var checkdayFrom=new Date(datepickerFrom)	;
		var checkdayTo=new Date(datepickerTo)	;		
		if(checkdayFrom<=checkdayTo)
		{
			searchCreditReport(1);
		}
		else
		{
			alert('ERROR. FROM value should be less than or equal to TO one');
		}	
	});
	
	function searchCreditReport(page) 
	{	
		var datepickerFrom= $('#hiddenDatepickerFrom').val();
		var datepickerTo= $('#hiddenDatepickerTo').val();		
		dialogOpen();			
		if(page==0 || page == null || page ==""){
			page = 1 ;
		}
		$.ajax({			
			url : "order_db.do",
			type : 'POST',
			cache : false,
			data : {
				 method : "searchCreditReport",				 
				 datepickerFrom : datepickerFrom,
				 datepickerTo : datepickerTo,
				 page : page
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
					dialogClose();
					$('#order_list_results').html(data);
				}	
					
			}
		});
	}
</script>
	
	