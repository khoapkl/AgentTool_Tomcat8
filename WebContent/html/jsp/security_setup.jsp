<%@page import="com.dell.enterprise.agenttool.model.Security"%>
    <script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery-ui-1.9.2.custom.min.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/jquery-ui-1.9.2.custom.css"/>
<%  
	Security security = new Security();

	if(request.getAttribute("security") != null){
		security = (Security)request.getAttribute("security");
	} else{
		security.setExpiryDays(30);
		security.setCharNumber(8);
		security.setUppercase(false);
		security.setSymbol(false);
		security.setNumber(false);
		security.setLockoutCount(5);
		security.setLockoutTime(15);
		security.setResetHistory(12);
	}	
	
%>
<div id="security_wrapper">	
	<p class="security_title">Security Settings</p>
	
	<div id="security_content">
	<form id="security" name="security"
	action="<%=request.getContextPath()%>/security_setup.do?method=securityEdit"
	method="POST">
		<div id="security_errorMessage"></div>
		<p class="wrap-expiry-day">1. Force user to change password every &nbsp;<input class='spinner1' id="expiryDays" onchange="TextChange(this)" name="expiryDays" min="30" max="180" value="<%= security.getExpiryDays()%>"> &nbsp;days</p>
		<p>2. Password requirements</p>
			<div class="secu_check">
				<p>Minimum &nbsp;<input class="spinner"   name="charNumber" min="8" max="15" onchange="charNumberChange(this)" value="<%=security.getCharNumber()%>"> &nbsp;characters</p>
				<p><input class="clear-margin float-left" type="checkbox" name="isUppercase" value="1" <%=security.isUppercase() ? "checked":"" %>  > <span class="space-checkbox">UPPERCASE</span></p>
				<p><input class="clear-margin float-left" type="checkbox" name="isNumber" value="1" <%=security.isNumber() ? "checked":"" %> > <span class="space-checkbox">Number</span> </p>
				<p><input class="clear-margin float-left" type="checkbox" name="isSymbol" value="1" <%=security.isSymbol() ? "checked":"" %> > <span class="space-checkbox">Symbol</span> </p>
			</div>
		<p>3. Account lockout after &nbsp;<input class='spinner' name="lockoutCount" onchange="countChange(this)" min="1" max="20" value="<%=security.getLockoutCount()%>"> &nbsp;failed attempts</p>
		<p class="pad_left wrap-expiry-day"> Account lockout time period&nbsp;
		 <select name="lockoutTime" class="input-time lockout-time">
		     <option value="15" <%=security.getLockoutTime()==1 ? "selected":"" %> >
		        15 Mins
		     </option> 
		     <option value="30" <%=security.getLockoutTime()==30 ? "selected":"" %> >
		        30 Mins
		     </option>
		     <option value="45" <%=security.getLockoutTime()==45 ? "selected":"" %> >
		        45 Mins
		     </option>
		     <option value="60" <%=security.getLockoutTime()==60 ? "selected":"" %> >
		        1 Hour
		     </option>
		     <option value="120" <%=security.getLockoutTime()==120 ? "selected":"" %> >
		        2 Hours
		     </option>
		 </select>  
		 </p>
		 <p>4. Password reusability</p>
		 <p class="pad_left">Clear password history after every &nbsp;<input class="spinner" onchange="resetChange(this)" name="resetHistory" min="1" max="24" value="<%=security.getResetHistory()%>"> &nbsp;months</p>
		  
		 <input class="input_bt" type="submit" name="Submit" value="Save"/>
	 </form>  
	 </div>
 </div>
 <%@include file="/html/scripts/security_script.jsp"%>