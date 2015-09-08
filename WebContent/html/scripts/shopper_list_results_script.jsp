<script>
$(document).ready(function() {
	var shopperCount = <%=session.getAttribute("shopper_count") %>;
	
	if (shopperCount == 0) {
		$('#shopper_list_results').html('<b><i>No Shopper found</i></b>');
	}
	
	var currentPage = <%=session.getAttribute("shopper_current_page") %>;
	var pageCount = <%=session.getAttribute("shopper_page_count") %>;
	
	if (pageCount > 1) {
		$('#navigation').show();
	} else {
		$('#navigation').hide();
	}
	
	if (currentPage == 1) {
		$('#firstPage').attr('disabled', 'disabled');
		$('#prevPage').attr('disabled', 'disabled');
	} else {
		$('#firstPage').removeAttr('disabled');
		$('#prevPage').removeAttr('disabled');
	}
	
	if(currentPage == pageCount) {
		$('#lastPage').attr('disabled', 'disabled');
		$('#nextPage').attr('disabled', 'disabled');
	} else {
		$('#lastPage').removeAttr('disabled');
		$('#nextPage').removeAttr('disabled');
	}
	
	$('#firstPage').click(function() {
		changePage(1);
	});
	
	$('#prevPage').click(function() {
		changePage(2);
	});
	
	$('#nextPage').click(function() {
		changePage(3);
	});
	
	$('#lastPage').click(function() {
		changePage(4);
	});
	
	$('#requery').click(function() {
		$('#firstPage').click();
	});
});

function changePage(changeCase) {
	$.ajax( {
		url : "shopper_list.do",
		type : 'POST',
		cache : false,
		data : {
			method : "changePage",
			changeCase : changeCase
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
				$('#shopper_list_results').html(data);
				}
			
		},
		error : function() {
			alert("Error!");
		}
	});
}
</script>