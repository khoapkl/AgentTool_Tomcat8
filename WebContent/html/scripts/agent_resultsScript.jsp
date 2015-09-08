
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%><script>
function movePaging(casePage) {
	dialogOpen();
	var numOfPage = $('#numPage').text();
	var totalRecord = $('#totalRecord').text();
	var noPage = $('#noPage').text();
	var agentId = $('#agentId').val();		
	var fullname = $('#fullname').val();			
	var userLevel = $('#userLevel').val();		
	var username = $('#username').val();
	var email = $('#email').val();
	var isReport = $('#isReport').val();
	var isAdmin = $('#isAdmin').val();
	var isActive = $('#isActive').val();
	var userType = $('#userType').val();
	
	$.ajax( {
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			method : "pagingMove",
			casePage : casePage,
			totalRecord : totalRecord,
			numOfPage : numOfPage,
			noPage : noPage,
			 agentId : agentId,				 
			 fullname : fullname,
			 userLevel : userLevel,
			 username : username,
			 email : email ,
			 isReport : isReport,
			 isAdmin : isAdmin,
			 isActive : isActive,
			 userType : userType
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

function editAgent(agentId)
{
	dialogOpen();
	$.ajax( {
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			method : "getAgent",
			agentId : agentId			
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
				$("#divAgent").html(html);
				dialogEditOpen();
				}
		},
		error : function() {
			dialogClose();
			$('#search_results').html("<b>Error execute in delete process!</b>");
		}
	});
	
}

function checkForAgentInUse(agentId)
{
	dialogOpen();
	$.ajax( {
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			method : "checkInUseAgent",
			agentId : agentId			
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
				if (data.toString().indexOf("id=\"agentInUse\"") == -1)	
					deleteAgent(agentId);
				else
					$('#info').html("<script language='javascript'>alert('Cannot delete agent because this agent is in use.');<\/script>");								
				}				
		},
		error : function() {
			dialogClose();
			$('#search_results').html("<b>Error execute in delete process!</b>");
		}
	});
}

function deleteAgent(agentId)
{
	dialogOpen();
	$.ajax( {
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			method : "deleteAgent",
			agentId : agentId			
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
					$('#row'+agentId).html("");
					$('#info').html("<script language='javascript'>alert('Agent deleted successfully.')<\/script>");								
			
				}
		},
		error : function() {
			dialogClose();
			$('#search_results').html("<b>Error execute in delete process!</b>");
		}
	});
}
</script>