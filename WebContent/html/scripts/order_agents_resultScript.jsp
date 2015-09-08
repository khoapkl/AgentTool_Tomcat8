<script>
	function movePaging(casePage,method,divResult,date1,date2) {
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
				noPage : noPage,
				date1:date1,
				date2:date2
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
	
	function movePagingAgent(casePage,method,divResult,date1,date2,agentId,agentName) {
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
				noPage : noPage,
				 date1 : date1,
				 date2 : date2,
				 agentId : agentId,
				 agentName : agentName
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

	function getAgentDetail(agentId,agentName,date1,date2){
		document.location.href="<%=request.getContextPath()%>/order_db.do?method=detailAgent&agentId="+agentId+"&date1="+date1+"&date2="+date2+"&agentName="+agentName;		
	}
</script>