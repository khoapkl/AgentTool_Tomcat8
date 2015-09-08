<script>

function resetForm() {
	  $('#agentId').val("");
	  $('#fullname').val("");
	  $('#userLevel').val("");
	  $('#username').val("");
	  $('#email').val("");
	  $('#isReport').val("");
	  $('#isAdmin').val("");
	  $('#isActive').val("");
	  $('#userType').val("");	  
}

function newAgent() {	
	dialogOpen();
	$.ajax({
		url : "agent_setup.do",
		type : 'post',
		cache : false,
		data : {
			 method : "newAgent"
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
				$('#divAgent').html(html);
				dialogNewOpen();
				}
		},
		error : function() {
			dialogClose();
			$('#search_results').html("<b>Error execute in search process!</b>");				
	}
	}); 	
}

function getListAgent(){
	
		if (isNaN($('#agentId').val()))
		{
				alert("Agent # must be numeric.");
				return;
		}
		
		dialogOpen();
		var agentId = jQuery.trim($('#agentId').val());		
		var fullname = jQuery.trim($('#fullname').val());			
		var userLevel = jQuery.trim($('#userLevel').val());		
		var username = jQuery.trim($('#username').val());
		var email = jQuery.trim($('#email').val());
		var isReport = jQuery.trim($('#isReport').val());
		var isAdmin = jQuery.trim($('#isAdmin').val());
		var isActive = jQuery.trim($('#isActive').val());
		var userType = jQuery.trim($('#userType').val());	
			
		$.ajax({
			url : "agent_setup.do",
			type : 'POST',
			cache : false,
			data : {
				 method : "search",					 	 
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
}

//Number Validation
function isNumberKey(obj ,evt,allowDecimal)
{							
	 var key;
	 var isCtrl = false;
	 var keychar;
	 var reg;
	  
	 if (window.event) {
	  key = evt.keyCode;
	  isCtrl = window.event.ctrlKey;
	 }
	 else if (evt.which) {
	  key = evt.which;
	  isCtrl = evt.ctrlKey;
	 }
	 
	 if (isNaN(key)) {
	  return true;
	 }
	 
	 keychar = String.fromCharCode(key);
	 
	 //check for backspace or delete, or if Ctrl was pressed
	 if (key == 8 || isCtrl) {
	  return true;
	 }

	 reg = /\d/;					 
	 var isFirstD = allowDecimal ? keychar == '.' && obj.value.indexOf('.') == -1 : false;

	 var maxInput = (obj.value.length < 8);
	 return (isFirstD || reg.test(keychar)) && maxInput;
}
</script>