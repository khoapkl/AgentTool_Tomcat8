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
		<td colspan="2"><font color="#3399CC"><u><b>Order
		Total:</b></u></font></td>
		<td align="left" colspan="10"><font color="#3399CC"><b><%= totalHeld%></b></font>
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
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingHeldByAgent","held_order_results");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingHeldByAgent","held_order_results");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingHeldByAgent","held_order_results");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingHeldByAgent","held_order_results");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingHeldByAgent","held_order_results");' />
                 </div>
            </td>
        </tr>
	</tbody>
</table>
<%}if(mapOrderHeld.size()>0) {%>
<table id="OrderTable" width="100%" cellpadding="2" border="0" class="tablers main">
	<tbody>
	<tr style="background-color:#CCCCCC">
	<td align="right" colspan="12"><a href="exportOrder.do?method=excelHeldOrder&typehold=AGENT"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
		<tr style="background-color:#0099FF">
			<td width="5%" valign="BOTTOM" align="LEFT"><b># </b></td>
			<td width="10%" valign="BOTTOM" align="LEFT"><b>Order</b></td>
			<td width="10%" valign="BOTTOM" align="LEFT"><b>Modified</b></td>
			<td width="20%" valign="BOTTOM" align="LEFT"><b>Ship to Name</b></td>
			<td width="3%" valign="BOTTOM" align="left" nowrap="nowrap"><b>Items
			Held</b></td>
			<td width="10%" valign="BOTTOM" align="RIGHT"><b>Order Total</b></td>
			<td width="7%" valign="BOTTOM" align="RIGHT"><b>Sys Total</b></td>
			<td width="8%" valign="BOTTOM" align="RIGHT"><b>ASP</b></td>
			<td width="7%" valign="BOTTOM" align="RIGHT"><b>Avg $/Mhz</b></td>
			<td width="5%" valign="BOTTOM" align="RIGHT"><b>Avg Mhz</b></td>
			<td width="15%" valign="BOTTOM" align="center"><b>Agent</b></td>
			<td width="5%" valign="BOTTOM" align="RIGHT" nowrap="nowrap"><b>Discount</b></td>
		</tr>
		<%	
		String style = "#CCCCCC";
    for (OrderHeld orderHeld : mapOrderHeld) {
       String sysTotal = (orderHeld.getAvgMhz().getTotal_price() != BigDecimal.valueOf(0))? Converter.getMoney(orderHeld.getAvgMhz().getTotal_price()): "N/A";
       String average_mhz = (orderHeld.getAvgMhz().getUnit_mhz() >0)?(String.valueOf(Math.round(orderHeld.getAvgMhz().getSpeed_total()/orderHeld.getAvgMhz().getUnit_mhz()))):"N/A";
       String company =(orderHeld.getShip_to_company() != null )? orderHeld.getShip_to_company(): "";
       if(style.equals("#CCCCCC"))
           style = "#99CCFF";
       else 
           style = "#CCCCCC";

       %>
		<tr style="background-color:<%= style %>">
			<td valign="TOP" align="left"><%=orderHeld.getId()%></td>

			<td valign="TOP" align="left"><a href="shopper_manager.do?method=getViewBasketAgent&mscssid=<%= orderHeld.getShopId() %>&orderId=<%=orderHeld.getOrderId()%>"><%=orderHeld.getOrderId() %></a></td>
			<td valign="TOP" align="left"><%=orderHeld.getDayOrder() %></td>
			<td valign="TOP" align="LEFT"><a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=orderHeld.getShopId()%>"><%=orderHeld.getShip_to_name() %></a></td>

			<td width="80" valign="TOP" align="RIGHT"><%=orderHeld.getItem()%></td>
			<td width="80" valign="TOP" align="RIGHT"><%=Converter.getMoney(orderHeld.getTotal_total().divide(BigDecimal.valueOf(100))) %></td>

			<td width="80" valign="TOP" align="RIGHT"><%=sysTotal%></td>

			<td width="80" valign="TOP" align="RIGHT"><%=Converter.getMoney(orderHeld.getAvgMhz().getTotal_price().divide(BigDecimal.valueOf(orderHeld.getItem()),2)) %></td>
			<td width="80" valign="TOP" align="RIGHT"><%=orderHeld.getAvgMhz().getAvgmhz() %></td>
			<td width="80" valign="TOP" align="RIGHT"><%=average_mhz %></td>
			<td width="280" valign="TOP" align="center"><%=orderHeld.getUser_hold() %></td>
			<td width="80" valign="top" align="right"><%=Converter.getHeldDiscount(orderHeld.getPlace_price(),orderHeld.getModi_price())%>%</td>

		</tr>
		<%} %>
	</tbody>
</table>
<%}else{ %>
<b>No order is held</b>
<%} %>
<script>
$(document).ready(function() {
	$('#pageTitle').text("Agent-Held Orders ");
	$('#topTitle').text("Agent-Held Orders ");
});
</script>
</div>
<%@include file="/html/scripts/order_resultScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>