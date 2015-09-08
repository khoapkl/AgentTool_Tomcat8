<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@ page import="org.apache.struts.Globals"%>
<%@ page import="java.util.Locale"%>
<%
	String path_app = request.getContextPath();
	if(session.getAttribute(Constants.IS_CUSTOMER) != null){
%>
	<script>
	var isCustommer = true;
	</script>
<%}else{%>
	<script>
	var isCustommer = false;
	</script>
<%}%>