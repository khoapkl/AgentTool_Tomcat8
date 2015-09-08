<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<div id="agentDate">
<table align="left" width="100%" height="30" bgcolor="white" cellspacing="5" cellpadding="5" border="0">
	<tr valign="top" height="30">
	<td align="left" colspan="2">
	<b>From Date:</b><input type="text" id="datepickerFrom"  onblur="validateDate(this)" readonly="readonly" value="<%=Constants.getCurrentDate() %>">(MM/DD/YYYY) 			
	<b>To Date:</b><input type="text" id="datepickerTo"   onblur="validateDate(this)" readonly="readonly" value="<%=Constants.getCurrentDate() %>">(MM/DD/YYYY)			
	<input type="button" value="Search" id="searchAgents"></td>
	</tr>		
	<tr valign="top"><td colspan="2"><div id="order_agents_result"></div></td></tr>
</table>
</div>
<div id="agentDateDetail">
</div>
<%@include file="/html/scripts/order_agentsScript.jsp"%>
<%@include file="/html/scripts/calendarScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
<script>
<%
if(request.getAttribute("backPage")!=null)
{
    String date2 = "";
	if(session.getAttribute(Constants.DATE2) != null)
	{
		date2 = session.getAttribute(Constants.DATE2).toString();
		date2 = (date2.split(" "))[0];
	}

%>
	getBackAgentsOrder();
		
	$('#datepickerFrom').val('<%=session.getAttribute(Constants.DATE1)%>');
	$('#datepickerTo').val('<%=date2%>');
<%
}
%>
</script>
