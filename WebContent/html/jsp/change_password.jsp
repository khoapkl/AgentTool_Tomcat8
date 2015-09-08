<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.util.*"%>

	<!--[if IE 9]>
   		 	<script src="<%=request.getContextPath() %>/jscripts/jquery-1.8.3.js" language="javascript"></script>
   		 	<script src="<%=request.getContextPath() %>/jscripts/jquery.placeholder.js" language="javascript"></script>		
	   	<script> 	
			$(document).ready(function() {
			$('.input_text').placeholder();
			$("#clean").on('click', function(e){
				$("#oldpassword").trigger('change');
				$("#password").trigger('change');
				$("#rePassword").trigger('change');	
			});
		});
		</script>
	<![endif]-->
<%  
	String expire = "";
	String oldPassword = "";
	if(request.getAttribute("expire") != null){
		expire = (String)request.getAttribute("expire");
	}	
	if(request.getAttribute(Constants.AGENT_PASSWORD) != null){
		oldPassword = (String)request.getAttribute(Constants.AGENT_PASSWORD);
	}	
	String userName =(String)session.getAttribute(Constants.AGENT_NAME);
	Security secu = (Security)request.getAttribute("security");
%>
	<script type="text/javascript">
	
	if(<%=session.getAttribute(Constants.AGENT_NAME)== null%> )
	{ 
		 var url = "<%=request.getContextPath()%>" + "/authenticate.do";
	  	 window.location = url;	 
	}
	</script>
<div id="wrapper_change">
       <div id="logo_change"><img alt="Agent Tool" id="imgDefault" name="imgDefault" src="images/dfs_direct_sale.png"></div> 
	<div id="container_change">
		<h1><span class="arrow1"></span>CHANGE PASSWORD<span class="arrow"></span></h1>
		<%if("1".equals(expire)){%>
		<div id="title">Your password has expired. Please change your password now</div>
		<%} %>
		<div id="message" class="error-message text-center warning-message"> 
		<%if(secu.isUppercase()||secu.isNumber()||secu.isSymbol()){%>
			<div class="message-top"><img alt="Agent Tool" src="images/warning-icon.png"> <span>Your password must contain at least <%=secu.getCharNumber()%> characters including at least</span></div>
			<ul>
					 <%=secu.isUppercase() ? "<li>one uppercase letter</li>":"" %>
					 <%=secu.isNumber() ? "<li>one number</li>":"" %>
					 <%=secu.isSymbol() ? "<li>one symbol</li>":"" %>
			 </ul>
		
		<%}else{ %>
			<div class="message-top"><img alt="Agent Tool" src="images/warning-icon.png"> <span>Your password must contain at least <%=secu.getCharNumber()%> characters</span></div>
		<%} %>
			
		</div>
		<form class="changePasswordForm" action="<%=request.getContextPath()%>/change_password.do?method=updatePassword&expire=<%=expire%>" method="POST">
			<p>
				<label>User Name:</label><br>
				<input class="input_text" type="text" id="userName1" name="userName1" disabled="disabled" value="<%=userName%>" >
				<input class="input_text" type="hidden" id="userName" name="userName" value="<%=userName%>" >
			</p>
			<p>
				<label>Old Password:</label>
				<input class="input_text" type="password" id="oldpassword" name="oldPassword" placeholder="Password" value="<%=oldPassword%>"  >
			</p>
			<p>
				<label>New Password:</label>
				<input class="input_text" type="password" id="password" name="password" placeholder="New Password" >
			</p>
			<p>
				<label>Confirm New Password:</label>
				<input class="input_text" type="password" id="rePassword" name="rePassword" placeholder="Confirm New Password" >
			</p>
			<p>
				<input class="input_bt" type="submit" id="login" value="OK"> &nbsp;&nbsp;&nbsp;
				<input class="input_bt" type="button"  onclick="resetChangePW(this)" id="clean" value="RESET">
			</p>
		</form>
	</div><!-- End #container -->
</div><!-- End #wrapper -->
<%@include file="/html/scripts/changepassword_script.jsp"%>