<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderCriteria"%>
<form method="post" action="#" id="order_shop">
<table border="0" width="100%" class="main">
	<tr>
		<td colspan="9"><b>Please enter search condition:</b></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Order #</td>
		<td><input type="text" id="ordernumber" size="18" onkeypress="return numericOnly(this, event);"/></td>
		<td>Sort Order</td>
		<td><select name="sort_order" id="sort_order" >
			<option selected="selected" value="">Ship to Name</option>
			<option value="1">Orders</option>
			<option value="2">Total</option>
		</select></td>
		<td>Item Sku</td>
		<td><input type="text" id="itemsku" size="18" /></td>
	
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>Customer #</td>
		<td><input type="text" id="linknumber" size="18" onkeypress="return numericOnly(this, event);"/></td>
		<td colspan="2">&nbsp;</td>
	</tr>
	<tr>
		<td >Shipping :</td>
		<td>First Name</td>
		<td><input type="text" id="ship_to_fname" size="18" /></td>
		<td>Last Name</td>
		<td><input type="text" id="ship_to_lname" size="18" /></td>
		<td>Company</td>
		<td><input type="text" id="ship_to_company" size="18" /></td>
		<td>Phone</td>
		<td><input type="text" id="ship_to_phone" size="12" onkeypress="return numericOnly(this, event);"/></td>
	</tr>
	<tr>
		<td>Billing :</td>
		<td>First Name</td>
		<td><input type="text" id="bill_to_fname" size="18" /></td>
		<td>Last Name</td>
		<td><input type="text" id="bill_to_lname" size="18" /></td>
		<td>Company</td>
		<td><input type="text" id="bill_to_company" size="18" /></td>
		<td>Phone</td>
		<td><input type="text" id="bill_to_phone" size="12" onkeypress="return numericOnly(this, event);"/></td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input style="width: 100;"
			type="button" value="Filter" id="bttFilter" onClick='getShopOrder();'>	
			</td>			
		<td colspan="3" align="center"><input style="width: 100;"
			type="reset" value="Clear Filter" name="bttClear" />
			</td>
		<td colspan="3" align="center">&nbsp;</td>
	</tr>
</table>
</form>
<div id="order_shopper_results"></div>
<%@include file="/html/scripts/order_listScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
<script>
$(document).ready(function() {
	$('#pageTitle').text("Orders by Shopper");
	$('#topTitle').text("Orders by Shopper");
	$('#order_shop').keydown(function(e){
		if (e.keyCode == '13'){
			getShopOrder();
			}
	});

	<%
	if(request.getAttribute("backPage")!=null){
		OrderCriteria searchOrder =(OrderCriteria)session.getAttribute(Constants.ORDER_SHOP_CRITERIA);
	%>
		getBackShopOrder();
	<%
		if(searchOrder != null){
	%>
			$('#ordernumber').val('<%=searchOrder.getOrderId()%>');
			$('#sort_order').val('<%=searchOrder.getOrderType()%>');
			$('#itemsku').val('<%=searchOrder.getItemSku()%>');
			$('#linknumber').val('<%=searchOrder.getCustomerId()%>');
			$('#ship_to_fname').val('<%=searchOrder.getSfname()%>');
			$('#ship_to_lname').val('<%=searchOrder.getSlname()%>');
			$('#ship_to_company').val('<%=searchOrder.getScom()%>');
			$('#ship_to_phone').val('<%=searchOrder.getSphone()%>');
			$('#bill_to_fname').val('<%=searchOrder.getBfname()%>');
			$('#bill_to_lname').val('<%=searchOrder.getBlname()%>');
			$('#bill_to_company').val('<%=searchOrder.getBcom()%>');
			$('#bill_to_phone').val('<%=searchOrder.getBphone()%>');
	<%	
		}
	}
	%>
});
</script>