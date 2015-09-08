<%
	HttpServletResponse httpResponse = (HttpServletResponse) response;
	httpResponse.setHeader("Cache-Control", "private");
%>

<div>
<form id="shopperSearchForm">
<table border="0" width="100%">
	<tr>
		<td colspan="9"><b>Please enter search condition:</b></td>
	</tr>
	<tr>
		<td colspan="2">Customer #</td>
		<td><input type="text" id="linknumber" size="12" value="" onkeypress="return numericOnly(this, event);"></td>
		<td>Shipping Email</td>
		<td colspan="3"><input type="text" id="ship_to_email" size="40"
			value=""></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td>Shipping:</td>
		<td>First Name</td>
		<td><input type="text" id="ship_to_fname" size="18" value=""></td>
		<td>Last Name</td>
		<td><input type="text" id="ship_to_lname" size="18" value=""></td>
		<td>Company</td>
		<td><input type="text" id="ship_to_company" size="35" value=""></td>
		<td>Phone</td>
		<td><input type="text" id="ship_to_phone" size="12" value="" onkeypress="return numericOnly(this, event);"></td>
	</tr>
	<tr>
		<td>Billing:</td>
		<td>First Name</td>
		<td><input type="text" id="bill_to_fname" size="18" value=""></td>
		<td>Last Name</td>
		<td><input type="text" id="bill_to_lname" size="18" value=""></td>
		<td>Company</td>
		<td><input type="text" id="bill_to_company" size="35" value=""></td>
		<td>Phone</td>
		<td><input type="text" id="bill_to_phone" size="12" value="" onkeypress="return numericOnly(this, event);"></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input style="width: 100;"
			type="submit" value="Filter" id="shopperListFilter" /></td>
		<td colspan="3" align="center"><input style="width: 100;"
			type="reset" value="Clear Filter" name="button"></td>
		<td colspan="3" align="center">&nbsp;</td>
	</tr>
</table>
<i>Please specify filter criteria</i>
</form>
</div>

<div id="shopper_list_results"></div>

<%@include file="/html/scripts/shopper_list_script.jsp" %>