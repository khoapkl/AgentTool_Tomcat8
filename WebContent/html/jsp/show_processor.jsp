<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
List<String> list= (List<String>)request.getAttribute(Constants.SHOW_PROCESSOR_RESULT);
%>




<select size="10" name="processorList" multiple>
	<%
	if(list != null && !list.isEmpty()){
	%>
	<option value="All">All Processors</option>
	<%    
	    for(String str : list) {
		
	%>				
			<option value="<%=str%>"><%=str%></option>
		<%	}
	}%>
</select>
