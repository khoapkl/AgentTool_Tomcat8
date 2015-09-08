
<%@include file="/html/scripts/calendarScript.jsp"%>
<%@include file="/html/scripts/reportScript.jsp"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>

<form 
	action="<%=request.getContextPath()%>/ship_report.do?method=searchShippingReport"
	method="POST" id="formShippingReport">

<table style="border: 0;">
	<tr>
		<td>Report Date Range:</td>
	</tr>
	<tr>
		<td>From Date:</td>
		<td></td>
		<td ><input type="text" id="datepickerFrom" name="datepickerFrom" readonly ></td>
		<td>			
		</td>
	</tr>
	<tr>
		<td>To Date:</td>
		<td></td>
		<td><input type="text" id="datepickerTo" name="datepickerTo" readonly></input> </td>
		<td>		
	</td>
	</tr>
	<tr id="formatOuput">
		<td>Output Type:</td>
		<td><input type="Radio" name="output_type_rdo" value="html"
			align="LEFT" checked id="formatHtml">HTML</td>
		<td><input type="Radio" name="output_type_rdo" value="csv"
			align="LEFT" id="formatCsv">CSV</td>
	</tr>

	<tr>
		<td colspan=3><input type="button" value="Run Report" id="runShippingReport"/>			
		</td>
	</tr>
</table>



</form>

