<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Order"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter" %>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderShopper"%>
<%
	List<OrderShopper> shopOrder = (List<OrderShopper>)request.getAttribute(Constants.ORDER_SHOP_RESULT);
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
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingShopper","order_shopper_results");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingShopper","order_shopper_results");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingShopper","order_shopper_results");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingShopper","order_shopper_results");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingShopper","order_shopper_results");' />
                 </div>
            </td>
        </tr>
  </tbody></table>
 <%}if(shopOrder.size()>0){ %> 
 
 <table  id="OrderTable" width="100%" cellpadding="2" border="0" class="tablers main">
    <tbody>
    <tr style="background-color:#CCCCCC">
	<td align="right" colspan="7"><a href="exportOrder.do?method=excelShopperOrder"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
    <tr style="background-color:#0099FF"> 
	<td valign="BOTTOM" align="LEFT"><b> #           </b></td>
	<td valign="BOTTOM" align="LEFT"><b> Shopper Name  </b></td>
	<td valign="BOTTOM" align="RIGHT"><b> Num Orders </b></td>
	<td valign="BOTTOM" align="RIGHT"><b> Total $    </b></td>
	<td valign="BOTTOM" align="RIGHT"><b> Avg $      </b></td>
	<td valign="BOTTOM" align="RIGHT"><b> Max $      </b></td>
	<td valign="BOTTOM" align="RIGHT"><b> Min $      </b></td>
</tr>
<%
String style = "#CCCCCC";
for(OrderShopper orderShopper : shopOrder){ 
    if(style.equals("#CCCCCC"))
        style = "#99CCFF";
    else 
        style = "#CCCCCC";
%>
<tr style="background-color:<%= style %>">
    <td valign="TOP" align="LEFT"><%=orderShopper.getId()%></td>
    <td valign="TOP" align="LEFT"> 
    	<a href="shopper_manager.do?method=getShopperDetails&shopperId=<%=orderShopper.getShopId()%>&from=orderByShopper">
    		<%=orderShopper.getShip_to_name() %>
    	</a> 
    </td>
    <td valign="TOP" align="RIGHT"> <a href="order_db.do?method=getShopperOrder&shopperId=<%=orderShopper.getShopId()%>&from=orderByShopper"><%=orderShopper.getNumOrder() %> </a></td>
    <td valign="TOP" align="RIGHT"> <%=Converter.getMoney(orderShopper.getTotal()) %>  </td>
    <td valign="TOP" align="RIGHT"> <%=Converter.getMoney(orderShopper.getAvg()) %>  </td>
    <td valign="TOP" align="RIGHT"> <%=Converter.getMoney(orderShopper.getMax()) %>  </td>
    <td valign="TOP" align="RIGHT"> <%=Converter.getMoney(orderShopper.getMin()) %>  </td>
</tr>
<%} %>
</tbody></table>
<%}else{%>
<b>No shopper found</b>
<%}%>
<%@include file="/html/scripts/order_resultScript.jsp"%>