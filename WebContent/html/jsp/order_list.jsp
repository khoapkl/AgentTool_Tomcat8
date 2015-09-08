
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.List"%><div>
<form id="orderSearch" action="#" method="post" autocomplete="off">
<table border="0" width="100%" class="main">
	<tr>
		<td colspan="9"><b>Please enter search condition:</b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Order #</td>
		<td><input type="text" id="ordernumber" size="10" onkeypress="return numericOnly(this, event);"/></td>
		<td>Sort Order</td>
		<td><select name="sort_order" id="sort_order">
			<option selected="selected" value="">Order Date</option>
			<option value="1">Ship to Name</option>
			<option value="2">Items Sold</option>
			<option value="3">Discount</option>
			<option value="4">Total</option>
		</select></td>
		<td>Service Tag</td>
		<td><input type="text" id="itemsku" size="12" /></td>
		<td>Listing #</td>
		<td><input type="text" id="listing" size="12" onkeypress="return numericOnly(this, event);" /></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Customer #</td>
		<td><input type="text" id="linknumber" size="18" onkeypress="return numericOnly(this, event);" /></td>
		<td>Order Type</td>
		<td><select name="filter_type" id="filter_type">
			<option value="" selected="selected">All</option>
			<option value="1">Store</option>
			<option value="2">Agent</option>
			<option value="3">Auction</option>
			<option value="4">eBay</option>
			<option value="5">Agent Store</option>
			<option value="6">Canadian Orders</option>
		</select></td>
		<td>Shipping Email Address</td>
		<td><input type="text"  id="ship_to_email" size="35" maxlength="100" /></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td >Shipping :</td>
		<td>First Name</td>
		<td><input type="text"  id="ship_to_fname" size="18" /></td>
		<td>Last Name</td>
		<td><input type="text"  id="ship_to_lname" size="18" /></td>
		<td>Company</td>
		<td><input type="text"  id="ship_to_company" size="35" /></td>
		<td>Phone</td>
		<td><input type="text" id="ship_to_phone" size="12" onkeypress="return numericOnly(this, event);" /></td>
	</tr>
	<tr>
		<td>Billing :</td>
		<td>First Name</td>
		<td><input type="text"  id="bill_to_fname" size="18" /></td>
		<td>Last Name</td>
		<td><input type="text"  id="bill_to_lname" size="18" /></td>
		<td>Company</td>
		<td><input type="text"  id="bill_to_company" size="35" /></td>
		<td>Phone</td>
		<td><input type="text"  id="bill_to_phone" size="12" onkeypress="return numericOnly(this, event);" onblur="validateInputNumber(this, 'Bill Phone Number');"/></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input style="width: 100;"
			type="button" value="Filter" id="bttFilter" onClick='getListOrder();'>	
			</td>			
		<td colspan="3" align="center"><input style="width: 100;"
			type="reset" value="Clear Filter" name="bttClear" />
			</td>
		<td colspan="3" align="center">&nbsp;</td>
	</tr>
</table>
<i>Please specify filter criteria</i>
</form>
</div>
<div id="order_results"></div>
<%@include file="/html/scripts/order_listScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
<script>
$(document).ready(function() {
	$('#pageTitle').text("Order Lookup");
	$('#topTitle').text("Order Lookup");
	$('#orderSearch').keydown(function(e){
		if (e.keyCode == '13'){
			getListOrder();
			}
	});	

	<%
	if(request.getAttribute("backPage")!=null){
	List<String> searchOrder =(List<String>)session.getAttribute(Constants.ORDER_CRITERIA);
	%>
	getBackListOrder();
	<%
	if(searchOrder != null){%>
	$('#ordernumber').val('<%=searchOrder.get(0)%>');
	$('#sort_order').val('<%=searchOrder.get(1)%>'); //select
	$('#itemsku').val('<%=searchOrder.get(2)%>');
	$('#listing').val('<%=searchOrder.get(3)%>');
	$('#linknumber').val('<%=searchOrder.get(4)%>');
	$('#filter_type').val('<%=searchOrder.get(5)%>'); //select
	$('#ship_to_email').val('<%=searchOrder.get(6)%>');
	$('#ship_to_fname').val('<%=searchOrder.get(7)%>');
	$('#ship_to_lname').val('<%=searchOrder.get(8)%>');
	$('#ship_to_company').val('<%=searchOrder.get(9)%>');
	$('#ship_to_phone').val('<%=searchOrder.get(10)%>');
	$('#bill_to_fname').val('<%=searchOrder.get(11)%>');
	$('#bill_to_lname').val('<%=searchOrder.get(12)%>');
	$('#bill_to_company').val('<%=searchOrder.get(13)%>');
	$('#bill_to_phone').val('<%=searchOrder.get(14)%>');
	<%	
	}
	}
	%>
});
</script>