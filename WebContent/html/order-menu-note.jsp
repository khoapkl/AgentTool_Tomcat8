<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%
Agent agent =(Agent)session.getAttribute(Constants.AGENT_INFO);
Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
%>
<table id="MenuTable" width="100%" cellspacing="0" cellpadding="0" border="0">
	<tbody>
		<tr>
			<td width="100%" valign="bottom" align="right">		
			<td valign="MIDDLE" align="RIGHT" nowrap="nowrap"
				width="251" valign="top" height="62"><b><span
				id="topTitle"></span></b>&nbsp;</td>
		</tr>
	</tbody>
</table>