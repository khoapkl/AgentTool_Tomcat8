<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<script>
$(document).ready(function() {
	$('#pageTitle').html("New Shoppers by Day: "+"<%=Converter.changeRequest(request.getParameter("month"))%>"+" / "+"<%=Converter.changeRequest(request.getParameter("day"))%>"+" / "+"<%=request.getParameter("year") %>");
	$('#topTitle').html("New Shoppers by Day: "+"<%=Converter.changeRequest(request.getParameter("month"))%>"+" / "+"<%=Converter.changeRequest(request.getParameter("day"))%>"+" / "+"<%=request.getParameter("year") %>");
	
	$('a.shopper').click(function() {
		$('#shopperId').val($(this).attr('id'));
		$('#frmViewShopper').submit();
	});
	
	var pageNo = $('#noPage').text();
	var numOfPage = $('#maxPage').text();

	if (pageNo == 1) {
		$('#firstPage').attr('disabled', 'disabled');
		$('#prePage').attr('disabled', 'disabled');
	} else {
		$('#firstPage').removeAttr('disabled');
		$('#prePage').removeAttr('disabled');
	}

	if (pageNo == numOfPage) {
		$('#lastPage').attr('disabled', 'disabled');
		$('#nextPage').attr('disabled', 'disabled');
	} else {
		$('#lastPage').removeAttr('disabled');
		$('#nextPage').removeAttr('disabled');
	}
});

function movePaging(casePage,method,divResult) {
	var numOfPage = $('#maxPage').text();
	var totalRecord = $('#totalRecord').text();
	var noPage = $('#noPage').text();
	var year = "<%=request.getParameter("year") %>";
	var month = "<%=Converter.changeRequest(request.getParameter("month"))%>";
	var day = "<%=Converter.changeRequest(request.getParameter("day"))%>";
	$.ajax( {
		url : 'new_shoppers.do',
		type : 'post',
		cache : false,
		data : {
			method : method,
			casePage : casePage,
			numOfPage : numOfPage,
			totalRecord : totalRecord,
			noPage : noPage,
			year : year,
			month : month,
			day : day
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
				$('#'+divResult).html(data);
				}
		},
		error : function() {
			alert("Error execute in search process!");
		}
	});
};
</script>

