<script>
$(document).ready(function() {
	$('#newShoppersByYearForm').submit(function() {
		return false;
	});
	
	$('#btnUpdate').click(function() {
		getNewShoppersByYear();
	});
	
	var year = '<%=request.getAttribute("iyear")%>';
	if (year != null) {
		$('#yearPicker').val(year);
		getNewShoppersByYear();
	}
	$('#pageTitle').html("New Shoppers by Year: "+year);
	$('#topTitle').html("New Shoppers by Year: "+year);
	
	$('a.month').click(function() {
		$('#year').val($('#yearPicker').val());
		$('#month').val($(this).attr('id'));
		$('form').submit();
	});
});

function getNewShoppersByYear() {
	var year = $('#yearPicker').val();
	
	$.ajax({
		url : "new_shoppers.do",
		type : 'POST',
		cache : false,
		data : {
			 method : "getNewShoppersByYear",
			 year : year
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
				$('#shopper_year_results').html(data);
				}
		},
		error : function() {
			alert("Error occured in ajax call");
		}
	});
}
</script>