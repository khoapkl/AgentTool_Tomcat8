<%@page import="com.dell.enterprise.agenttool.model.Security"%>
<script>
<%  Security security = (Security)request.getAttribute("security");%>

	if (<%=request.getAttribute("message") != null %>){ 
		$('#message').removeClass("warning-message");
		<%
		String message = (String)request.getAttribute("message");
		if("incorrectpassword".equals(message)){
			%>
			$('#message').html('Incorrect Password. Please try again');
			<%
		}
		if("confirmpassword".equals(message)){
			%>
				$('#message').html('New Password does not match. Please try again');	
			<%
		}
		if("sercuritypassword".equals(message)){
			if(security.isUppercase()||security.isNumber()||security.isSymbol()){
			%>
			$('#message').html('Your password must contain at least <%=security.getCharNumber()%> characters including at least'
					 +'<ul>'+ 
						  '<%=security.isUppercase() ? "<li>one uppercase letter</li>":"" %>'+
						  '<%=security.isNumber() ? "<li>one number</li>":"" %>'+
						  '<%=security.isSymbol() ? "<li>one symbol</li>":"" %>'+
					  '</ul>');

			  <%}else{%>
				 $('#message').html('Your password must contain at least <%=security.getCharNumber()%> characters');
			<%
			}%>
			
			<%
			}
		if("reusepassword".equals(message)){
			%>
				$('#message').html('Passwords cannot be reused. Please select another one');	
			<%
			}
		if("emptypassword".equals(message)){
			%>
				$('#message').html(' Please fill in the blank fields');	
			<%
			}
	%>
	} 


	$(function() {
         $("#oldpassword").focus();
    });

     
	function resetChangePW(item) {
		 	
			$("#oldpassword").val('');
			$("#password").val('');
			$("#rePassword").val('');
			$("#message").html('<%if(secu.isUppercase()||secu.isNumber()||secu.isSymbol()){%>'+
				'<div class="message-top"><img alt="Agent Tool" src="images/warning-icon.png">'
				 +'<span>Your password must contain at least <%=secu.getCharNumber()%>'+ 
				 ' characters including at least</span></div>'
				 +'<ul>'
					+   '<%=secu.isUppercase() ? "<li>one uppercase letter</li>":"" %>'
					+	'<%=secu.isNumber() ? "<li>one number</li>":"" %>'
					+	'<%=secu.isSymbol() ? "<li>one symbol</li>":"" %>'
				 +'</ul>'
			
			+'<%}else{ %>'
			+	'<div class="message-top"><img alt="Agent Tool" src="images/warning-icon.png">'+ '<span>Your password must contain at least <%=secu.getCharNumber()%> '+
			'characters</span></div>'
			+'<%} %>');
			$('#message').addClass("warning-message");
		}
	}
	 </script>
