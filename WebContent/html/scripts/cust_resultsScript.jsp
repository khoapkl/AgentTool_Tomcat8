<script>
function movePaging(casePage) {
	dialogOpen();
	var numOfPage = $('#numPage').text();
	var totalRecord = $('#totalRecord').text();
	var noPage = $('#noPage').text();
	var linknumber = $('#linknumber').val();		
	var ship_to_fname = $('input#ship_to_fname').val();		
	var ship_to_lname = $('#ship_to_lname').val();
	var ship_to_company = $('#ship_to_company').val();
	var ship_to_phone = $('#ship_to_phone').val();
	var bill_to_fname = $('#bill_to_fname').val();
	var bill_to_lname = $('#bill_to_lname').val();
	var bill_to_company = $('#bill_to_company').val();
	var bill_to_phone = $('#bill_to_phone').val();	
	
	$.ajax( {
		url : "cust_lookup.do",
		type : 'post',
		cache : false,
		data : {
			method : "pagingMove",
			casePage : casePage,
			totalRecord : totalRecord,
			numOfPage : numOfPage,
			noPage : noPage,
			linknumber : linknumber,				 
			ship_to_fname : ship_to_fname,
			ship_to_lname : ship_to_lname,
			ship_to_company : ship_to_company,
			ship_to_phone : ship_to_phone ,
			bill_to_fname : bill_to_fname,
			bill_to_lname : bill_to_lname,
			bill_to_company : bill_to_company,
			bill_to_phone : bill_to_phone
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			dialogClose();
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#search_results').html(data);				
				}
		},
		error : function() {
			dialogClose();
			$('#search_results').html("<b>Error execute in search process!</b>");
		}
	});
};
$('document').ready(function() {
	var pageNo = $('#noPage').text();
	var numOfPage = $('#numPage').text();
	if (pageNo == 1) {
		$('#firstPage').attr('disabled', 'disabled');
		$('#prePage').attr('disabled', 'disabled');
	} else {
		$('#firstPage').removeAttr('disabled');
		$('#prePage').removeAttr('disabled');
	}
	if(pageNo == numOfPage){
		$('#lastPage').attr('disabled', 'disabled');
		$('#nextPage').attr('disabled', 'disabled');
	}
	else{
		$('#lastPage').removeAttr('disabled');
		$('#nextPage').removeAttr('disabled');
		}
});
</script>