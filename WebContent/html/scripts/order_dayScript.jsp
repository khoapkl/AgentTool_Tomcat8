<script>
function movePaging(casePage,method,divResult) 
{
	dialogOpen();
	var numOfPage = $('#maxPage').text();
	var totalRecord = $('#totalRecord').text();
	var noPage = $('#noPage').text();
	var iyear = $("#order_year option:selected").val();
	var imonth = $("#imonth").text();
	var iday = $("#iday").text();
	$.ajax( {
		url : 'order_db.do',
		type : 'post',
		cache : false,
		data : {
			method : method,
			casePage : casePage,
			totalRecord : totalRecord,
			noPage : noPage,
			iyear : iyear,
			imonth : imonth,
			iday : iday
		},
		dataType : 'html',
		async : true,
		success : function(html) {			
			var data = html.replace(/[\r\n]+/g, "");
			$('#pageTitle').text("New Orders by Year: "+iyear);
			$('#topTitle').text("New Orders by Year: "+iyear);
			$('#yearUpdate').attr("style","display:none");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#'+divResult).html(data);
				}
			
			$('#timePeriod').html("Time Period: <a href=\"#\" onclick='getOrderByYear("+iyear+");' ><span id=\"iyear\"> "+iyear+"</span></a> / <a href=\"#\" onclick='getOrderByMonth("+iyear+","+imonth+");' >"+month[imonth]+"</a><span id=\"imonth\" style =\"display:none\">"+imonth+"</span> / <span id=\"iday\"> "+iday+"</span>");
			dialogClose();
		}
	});
};
$('document').ready(function() {
	var pageNo = $('#noPage').text();
	var numOfPage = $('#maxPage').text();
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