<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Order"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter" %>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);

	List<Order> listOrder = (List<Order>)request.getAttribute(Constants.ORDER_LIST_RESULT);
	int totalRecord =(Integer) request.getAttribute(Constants.ORDER_TOTAL);
	int pageRecord = Constants.PAGE_RECORD;
	if(totalRecord > pageRecord){
	  int  numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
	  int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
<table cellspacing="3" border="0" class="main">
        <tbody><tr>
            <td bgcolor="lightgrey" align="center"><b>
                <nobr><span id="totalRecord"><%=totalRecord %></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp; Page: <span id="noPage"><%=noPage %></span> of <span id="maxPage"><%=numOfPage %></span></nobr>
            </b></td>
        </tr>
        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingCustomerOrder","order_results");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingCustomerOrder","order_results");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingCustomerOrder","order_results");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingCustomerOrder","order_results");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingCustomerOrder","order_results");' />
                 </div>
            </td>
        </tr>
  </tbody></table>
 <%}if(listOrder.size()>0){ %> 
<table border=0 cellpadding=2 width=1100 class="tablers main">
	<tr>
	<td align="right" colspan="9" bgcolor="#ffffff"><a href="exportOrder.do?method=searchCustomerOrderExport"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
<tr style="background-color:#0099FF">
	<td colspan="11"></td>
	<td colspan="2" align="center"><b>Notebooks</b></td>
	<td colspan="2" align="center"><b>Desktops</b></td>
</tr>
<tr style="background-color:#0099FF">
    <td align=left valign=bottom><b>#</b></td>
    <td align=left valign=bottom><b>Order</b></td>
    <td align=left valign=bottom><b>Date</b></td>
    <td align=left valign=bottom><b>Ship To Name</b></td>
    <td align=left valign=bottom><b>Listing#</b></td>
    <td align=right valign=bottom nowrap="nowrap"><b> Sale Type</b></td>
    <td align=right valign=bottom><b>Items</b></td>
    <td align=right valign=bottom><b>Discount</b></td>
    <td align=right valign=bottom><b>System Total</b></td>
    <td align=right valign=bottom><b>Freight</b></td>
    <td align=right valign=bottom><b>Total</b></td>
	<td align=right valign=bottom><b>ASP</b></td>
	<td align=right valign=bottom><b>$/Mhz</b></td>
	<td align=right valign=bottom><b>ASP</B></td>
	<td align=right valign=bottom><b>$/Mhz</b></td>
</tr>
<%
    for (Order order :listOrder) {
       %>
     <tr >
    <td align=left valign=bottom><%=order.getId()%></td>
  	<td align=left valign=bottom><a href="order_db.do?method=orderViewPendingSearch&order_id=<%=order.getOrderId()%>" ><%=order.getOrderId()%></a></td>
    <td align=left valign=bottom><%=order.getDayOrder()%></td>
    <td align=left valign=bottom>
    	<%
        if(userLevel!=null && !userLevel.equals("C")){
        %>
    	<a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=order.getShopId()%>" ><%=order.getShip_to_name() %></a>
    	<%}else{ %>
    	<%=order.getShip_to_name() %>
    	<%} %>
    </td>
    <td align=left valign=bottom><%=Converter.stringOfNumber(order.getListing()) %></td>
    <td align=right valign=bottom nowrap="nowrap"><%=order.getOrderType()%></td>
    <td align=right valign=bottom><%=order.getItem() %></td>
    <td align=right valign=bottom><%=order.getDiscount() %>%</td>
    <td align=right valign=bottom><%=Converter.getMoney(order.getTotal_total())%></td>
    <td align=right valign=bottom><%=Converter.getMoney(order.getFreight_total()) %></td>
    <td align=right valign=bottom><%=Converter.getMoney(order.getOadjust_total()) %></td>
	<td align=right valign=bottom><%=Converter.getMoney(order.getNote().getAsp())%></td>
	<td align=right valign=bottom><%=Converter.getMoney(order.getNote().getMhz()) %></td>
	<td align=right valign=bottom><%=Converter.getMoney(order.getDesk().getAsp())%></td>
	<td align=right valign=bottom><%=Converter.getMoney(order.getDesk().getAsp())%></td>  
     </tr>           
 <%   }%>
</table>
<%}else{ %>
 	<b>No order found</b> 	
<%} %>
<%
Boolean isCustomer =(Boolean) session.getAttribute(Constants.IS_CUSTOMER);
if(isCustomer!=null && isCustomer){
 String shopNumber=String.valueOf(session.getAttribute(Constants.SHOPPER_NUMBER));
%>
<script>
$('#pageTitle').text("Previous Orders of: "+"<%=shopNumber%>");
$('#topTitle').text("Previous Orders of: "+"<%=shopNumber%>");
</script>    
<%}
%>
<%@include file="/html/scripts/order_resultScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>