<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderHeld"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.math.BigDecimal"%>
<div id="held_order_results">
<%
	List<OrderHeld> mapOrderHeld = (List<OrderHeld>) request.getAttribute(Constants.ORDER_HELD_RESULT);
	String totalHeld = (String)session.getAttribute(Constants.ORDER_HELD_TOTAL);
	int pageRecord = Constants.PAGE_RECORD;
	int totalRecord =(Integer) request.getAttribute(Constants.ORDER_TOTAL);
%>
<table>
	<tr>
		<td colspan="2"><font color="#3399cc"><u><b>Order
		Total:</b></u></font></td>
		<td align="left" colspan="10"><font color="#3399cc"><b><%= totalHeld%></b></font>
		</td>
	</tr>
</table>
<%
	if(totalRecord >pageRecord){
	  int  numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
	  int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
<table cellspacing="3" border="0" class="main">
	<tbody>
		<tr>
			<td bgcolor="lightgrey" align="center"><b> <nobr><span
				id="totalRecord"><%=totalRecord %></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp;
			Page: <span id="noPage"><%=noPage %></span> of <span id="maxPage"><%=numOfPage %></span></nobr>
			</b></td>
		</tr>
		        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","heldCustomerPaging","held_order_results");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","heldCustomerPaging","held_order_results");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","heldCustomerPaging","held_order_results");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","heldCustomerPaging","held_order_results");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","heldCustomerPaging","held_order_results");' />
                 </div>
            </td>
        </tr>
	</tbody>
</table>
<%}if(mapOrderHeld.size()>0) {%>
<table id="OrderTable" width="600" cellpadding="2" border="0" class="tablers main">
	<tbody>
		<tr>
		<td align="right" colspan="6" bgcolor="#ffffff"><a href="exportOrder.do?method=excelOrderHeldCustomerMap"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
		</tr>
		<tr style="background-color:#0099FF">
			<td width="5%" valign="BOTTOM" align="LEFT"><b># </b></td>
			<td width="10%" valign="BOTTOM" align="LEFT"><b>Order</b></td>
			<td width="10%" valign="BOTTOM" align="LEFT"><b>Modified</b></td>
			<td width="15%" valign="BOTTOM" align="LEFT"><b>Ship to Name</b></td>
			<td width="3%" valign="BOTTOM" align="LEFT" nowrap="nowrap"><b>Items Held</b></td>
			<td width="10%" valign="BOTTOM" align="right" ><b>Order Total</b></td>
			<td width="10%" valign="BOTTOM" align="right" ><b>Cancel</b></td>
		</tr>
		<%	
    for (OrderHeld orderHeld : mapOrderHeld) {
       String sysTotal = (orderHeld.getAvgMhz().getTotal_price() != BigDecimal.valueOf(0))? Converter.getMoney(orderHeld.getAvgMhz().getTotal_price()): "N/A";
       String average_mhz = (orderHeld.getAvgMhz().getUnit_mhz() >0)?(String.valueOf(Math.round(orderHeld.getAvgMhz().getSpeed_total()/orderHeld.getAvgMhz().getUnit_mhz()))):"N/A";
       %>
		<tr>
			<td valign="TOP" align="left"><%=orderHeld.getId()%></td>
			<td valign="TOP" align="left"><a href="shopper_manager.do?method=getViewBasketCustomer&mscssid=<%= orderHeld.getShopId() %>&orderId=<%=orderHeld.getOrderId()%>"><%=orderHeld.getOrderId() %></a></td>
			<td valign="TOP" align="left"><%=orderHeld.getDayOrder() %></td>
			<td valign="TOP" align="LEFT"><a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=orderHeld.getShopId()%>"><%=orderHeld.getShip_to_name() %></a></td>
			<td  valign="TOP" align="left"><%=orderHeld.getItem()%></td>
			<td valign="TOP" align="right"><%=Converter.getMoney(orderHeld.getTotal_total().divide(BigDecimal.valueOf(100))) %></td>
			<td valign="TOP" align="right"><a href="order_db.do?method=cancelHeldOrder&orderNumber=<%= orderHeld.getOrderId()%>" >Cancel</a></td>
		</tr>
		<%} %>
	</tbody>
</table>
<%}else{ %>
<b>No order is held</b>
<%} %>
<%
	Boolean isCustomer =(Boolean) session.getAttribute(Constants.IS_CUSTOMER);
	if(isCustomer!=null && isCustomer){
	 String shopNumber=String.valueOf(session.getAttribute(Constants.SHOPPER_NUMBER));
%>
<script>
$(document).ready(function() {
	$('#pageTitle').text("Held Orders of: "+"<%=shopNumber%>");
	$('#topTitle').text("Held Orders of: "+"<%=shopNumber%>");
});
</script>
<%}
%>
</div>
<%@include file="/html/scripts/order_resultScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>