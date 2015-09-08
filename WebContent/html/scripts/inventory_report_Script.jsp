<script type="text/javascript">
	
	$('#pageTitle').text("Summary Inventory Report");
	$('#topTitle').text("Summary Inventory Report");
	
	$("#searchCreditReport").click(function(){		
		searchCreditReport(1);
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
				 method : "searchSummaryInventoryReport",				 
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
	
	