<div id="order_by_shopper">
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Order"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderShopper"%>
<%
List<OrderShopper> listShopper =(List<OrderShopper> )session.getAttribute(Constants.ORDER_SHOPPER_DETAIL);
String shopperId =(String)session.getAttribute(Constants.ORDER_SHOPPER_ID) ;
%>
<input type="button" id="shopperBackButton" value="<<Back" />

<script>
$('#shopperBackButton').click(function() {
	javascript:document.location.href='<%=request.getContextPath()%>/order_db.do?method=backShopperForwardPage&backPage=true';
});
</script>
<br/>
<br/>
<a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=shopperId%>">
<font color="red">
<u>
View Shopper
</u>
</font>
</a>

<%
List<Order> list =(List<Order>) request.getAttribute(Constants.ORDER_BY_SHOPPER_RESULT);
int pageRecord = Constants.PAGE_ORDER_RECORD;
int totalRecord =(Integer) request.getAttribute(Constants.ORDER_TOTAL);
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
                <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingShopperOrder","order_by_shopper");'/>
                <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingShopperOrder","order_by_shopper");'/>
                <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingShopperOrder","order_by_shopper");'/>
                <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingShopperOrder","order_by_shopper");'/>
                <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingShopperOrder","order_by_shopper");' />
             </div>
        </td>
    </tr>
</tbody>
</table>
<%}if(list != null && list.size()>0){%>

<form method="POST" action="#">

<table id="OrderTable" width="600" cellpadding="2" border="0" class="tablers main">
    <tbody><tr style="background-color:  #0099FF"> 
    <td width="10" valign="BOTTOM" align="RIGHT"><b> #           </b></td>
    <td width="50" valign="BOTTOM" align="LEFT"><b> Order ID    </b></td>
    <td valign="BOTTOM" align="LEFT"><b> Date / Time </b></td>
    <td valign="BOTTOM" align="RIGHT"><b> Total       </b></td>
    </tr>
<%for (Order order : list){ %>
    <tr> 
    <td width="10" valign="TOP" align="RIGHT"><%=order.getId() %> </td>
    
    <td width="50" valign="TOP" align="LEFT"> <a href="order_db.do?method=orderViewPendingSearch&order_id=<%=order.getOrderId()%>"><%=order.getOrderId() %></a></td>
    <td valign="TOP" align="LEFT"> <%=order.getDayOrder() %> <%=order.getTime() %> </td>
    <td width="80" valign="TOP" align="RIGHT"> <%=Converter.getMoney(order.getTotal_total()) %> </td>
    </tr>
<%} %>
</tbody></table>
</form>
<%}else{%>
<b><i>No Order by Shopper</i></b>
<%}%>
<%
String shipName = "";
String shopNumber = "";
for(OrderShopper orderShopper : listShopper){
   shipName = orderShopper.getShip_to_name(); 
   shopNumber = orderShopper.getShopId();
} %>
<script>
$(document).ready(function() {
	$('#pageTitle').text("Shopper - "+'<%=shipName%>'+" ("+'<%=shopNumber%>'+")");
	$('#topTitle').text("Shopper - "+'<%=shipName%>'+" ("+'<%=shopNumber%>'+")");
});
</script>
<%@include file="/html/scripts/order_resultScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
</div>