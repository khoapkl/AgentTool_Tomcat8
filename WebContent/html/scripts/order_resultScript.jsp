<script>
	function movePaging(casePage,method,divResult) {
		var numOfPage = $('#maxPage').text();
		var totalRecord = $('#totalRecord').text();
		var noPage = $('#noPage').text();
		dialogOpen();
		$.ajax( {
			url : 'order_db.do',
			type : 'post',
			cache : false,
			data : {
				method : method,
				casePage : casePage,
				totalRecord : totalRecord,
				noPage : noPage
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
					$('#'+divResult).html(data);
					}				
			},
			error : function() {
				alert("Error execute in search process!");
			}
		});
	};
	$(document).ready(function() {
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