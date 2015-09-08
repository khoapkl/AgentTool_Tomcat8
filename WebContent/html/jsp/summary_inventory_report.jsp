<%@page import="com.dell.enterprise.agenttool.util.Constants"%>

<table align="left" width="100%" height="30" bgcolor="white" cellspacing="1" cellpadding="1" border="0">
	<tr valign="top" height="30">
		<td align="left" width="600" colspan="2">	
			<input type="button" name="search" value="Click this button to run The Summary Inventory Report" id="searchCreditReport">
		</td>
	</tr>		
	<tr valign="top"><td colspan="2"><div id="order_list_results"></div></td></tr>
</table>
<%@include file="/html/scripts/dialogScript.jsp"%>
<%@include file="/html/scripts/inventory_report_Script.jsp"%>
