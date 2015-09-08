<script type="text/javascript">
	
	$('#pageTitle').text("Sold Asset Report");
	$('#topTitle').text("Sold Asset Report");
	
	$("#RunAssetReport").click(function(){		
		var datepickerFrom= $('#datepickerFrom').val();
		var datepickerTo= $('#datepickerTo').val();		
		var datetype = document.getElementsByName('reportdate');
		$('#hiddenDatepickerFrom').val(datepickerFrom);
		$('#hiddenDatepickerTo').val(datepickerTo);
		if (datetype[0].checked){
			$('#hiddenreportdate').val(datetype[0].value);
		}
		else {
			$('#hiddenreportdate').val(datetype[1].value);
		}
		
		var checkdayFrom=new Date(datepickerFrom)	;
		var checkdayTo=new Date(datepickerTo)	;		
		if(checkdayFrom<=checkdayTo)
		{
			searchAssetReport(1);
		}
		else
		{
			alert('ERROR. FROM value should be less than or equal to TO one');
		}	
	});
	
	function searchAssetReport(page) 
	{	
		var datepickerFrom= $('#hiddenDatepickerFrom').val();
		var datepickerTo= $('#hiddenDatepickerTo').val();	
		var datetype = $('#hiddenreportdate').val();
		dialogOpen();			
		if(page==0 || page == null || page ==""){
			page = 1 ;
		}
		$.ajax({			
			url : "order_db.do",
			type : 'POST',
			cache : false,
			data : {
				 method : "searchAssetReport",				 
				 datepickerFrom : datepickerFrom,
				 datepickerTo : datepickerTo,
				 datetype : datetype,
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
	
	