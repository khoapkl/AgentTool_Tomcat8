<%@page import="com.dell.enterprise.agenttool.util.Constants"%>

<table align="left" width="100%" height="30" bgcolor="white" cellspacing="5" cellpadding="5" border="0">
	<tr valign="top" height="30">
		<td align="left" width="600" colspan="2">
			<input type="radio" id="reportdate" name="reportdate" value="exporteddate" checked>Report By OOR Date:
			<input type="radio" id="reportdate" name="reportdate" value="createddate">Report By Order Date:
		</td>
	</tr>
	<tr valign="top" height="30">
		<td align="left" width="600" colspan="2">
			<input type="text" id="datepickerFrom"  readonly="readonly" value="<%=Constants.getCurrentDate() %>"> (MM/DD/YYYY)			
			<input type="text" id="datepickerTo"   readonly="readonly" value="<%=Constants.getCurrentDate() %>"> (MM/DD/YYYY)			
			<input type="button" name="report" value="Run Report" id="RunAssetReport">
		</td>
	</tr>		
	<tr valign="top"><td colspan="2"><div id="order_list_results"></div></td></tr>
</table>

<input type="hidden" id="hiddenDatepickerFrom" value="" />
<input type="hidden" id="hiddenDatepickerTo" value="" />
<input type="hidden" id="hiddenreportdate" value="" />

<%@include file="/html/scripts/calendarScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
<%@include file="/html/scripts/asset_report_Script.jsp"%>
