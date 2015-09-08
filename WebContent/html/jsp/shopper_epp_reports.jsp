<%@ page contentType="text/html; charset=UTF-8" language="java"%>
<%@include file="/html/scripts/shopper_epp_reportsScript.jsp"%>

<%@page import="com.dell.enterprise.agenttool.util.Constants"%><form
	action="<%=request.getContextPath()%>/shopper_epp_reports.do?method=searchEppReport"
	method="POST">
<table style="border: 0">
	<tr>
		<td>Report Month:</td>
		<td><select name=report_month_select>
			<option value="1">January</option>
			<option value="2">February</option>
			<option value="3">March</option>
			<option value="4">April</option>
			<option value="5">May</option>
			<option value="6">June</option>
			<option value="7">July</option>
			<option value="8">August</option>
			<option value="9">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select></td>
		<td>
		
		<select id="iyear" name=report_year_select>
	<%
	    for (int i = 2000; i <= Constants.I_YEAR(); i++)
	    {
	%>
	<option value="<%=i%>"><%=i%></option>
	<%
	    }
	%>
	</select>
	</td>
	</tr>

	<tr>
		<td>Report Type:</td>
		<td><input type="Radio" name="report_type_rdo" value="asset"
			align="LEFT" checked>Asset</td>
		<td><input type="Radio" name="report_type_rdo" value="promotion"
			align="LEFT">Promotion</td>
	</tr>

	<tr>
		<td>Output Type:</td>
		<td><input type="Radio" name="output_type_rdo" value="html"
			align="LEFT" checked>HTML</td>
		<td><input type="Radio" name="output_type_rdo" value="csv"
			align="LEFT">CSV</td>
	</tr>

	<tr>
		<td colspan=3><input type=submit name="submit" value="Run Report"></td>
	</tr>

</table>
</form>