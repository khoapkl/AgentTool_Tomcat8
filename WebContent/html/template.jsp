<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@ include file="init.jsp"%>

<%@ taglib uri="struts-tiles" prefix="tiles" %>

<!-- content -->
<tiles:useAttribute id="PortletHeader" name="header" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="PortletMenu" name="menu" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="PortletTitle" name="title" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="PortletContent" name="body" classname="java.lang.String" ignore="true" />
<tiles:useAttribute id="PortletFooter" name="footer" classname="java.lang.String" ignore="true" />
<!DOCTYPE html>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8;">
	<meta http-equiv="X-UA-Compatible" content="IE=8">
	<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery-1.4.4.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/jscripts/jquery.history.js"></script>
	<script src="<%=request.getContextPath() %>/jscripts/commons.js" language="javascript"></script>
	
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/global.css"/>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/changepass_style.css"/>	
	<link rel="SHORTCUT ICON" href="images/dfs.ico">

  	<title>AgentTool Build 173</title>
  	<!-- link style css IE 10 -->
  	<script> 
			if (/*@cc_on!@*/false) { 
				try {
			    var headHTML = document.getElementsByTagName('head')[0].innerHTML; 
				headHTML    += '<link type="text/css" rel="stylesheet"  href="<%=request.getContextPath() %>/styles/style_ie.css">'; 
				document.getElementsByTagName('head')[0].innerHTML = headHTML;
				}catch(err){
					
					}
			} 
		</script>
	<!-- hover on IE -->	
  	<!--[if IE]><!-->
  		<%@include file="/html/scripts/ie_Script.jsp"%>
	<!--<![endif]-->
	
	<!-- link style css IE 9 -->
	
	<!--[if IE 9]>
	   		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/styles/login_style_ie.css"/>	
	   		<%@include file="/html/scripts/ie9_Script.jsp"%>
	<![endif]-->
	

</head>
  <body id="body-master" leftmargin="0" topmargin="0" marginheight="0" marginwidth="0" bottommargin="0" rightmargin="0" >
	<table cellpadding="0" cellspacing="0" border="0" width="100%" height="99%" align="center" class="main_content">
		<tr><td align="center">
			<table id="HeaderTable" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr bgcolor="FFFFFF">
				<td width="315">
					<jsp:include page="<%= PortletHeader%>" flush="true" />
				</td>
				<td><jsp:include page="<%= PortletMenu%>" flush="true" /></td>
				</tr>
			</table>
		</td></tr>
		<tr><td> 
				<jsp:include page="<%= PortletTitle%>" flush="true" /></td>
				</tr>
		<tr id="ContentTR" valign="top">
			<td height="100%" valign="top">				
				<jsp:include page="<%= PortletContent%>" flush="true">
					<jsp:param name="path_app" value="<%=path_app%>"/>
				</jsp:include>		
			</td>
		</tr>
		<tr><td colspan='2' align='center' ><jsp:include page="<%= PortletFooter%>" flush="true" /></td></tr>
    </table>
  </body>
</html>