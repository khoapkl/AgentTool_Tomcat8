<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Order"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<br/>
<div id="timePeriod"></div>
<br/>
<% List<Order> mapDay = (List<Order>) request.getAttribute(Constants.ORDER_DAY_RESULT);
	String year =String.valueOf(request.getAttribute(Constants.ORDER_YEAR_PARAM));
	String month =String.valueOf(request.getAttribute(Constants.ORDER_MONTH_PARAM));
	String day =String.valueOf(request.getAttribute(Constants.ORDER_DAY_PARAM));
	int pageRecord = Constants.PAGE_RECORD;
	int totalRecord =(Integer) request.getAttribute(Constants.ORDER_TOTAL);
	if(totalRecord >pageRecord){
	  int  numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
	  int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
<table  cellspacing="3" border="0" class="main">
	<tbody>
		<tr>
			<td bgcolor="lightgrey" align="center"><b> <nobr><span
				id="totalRecord"><%=totalRecord %></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp;
			Page: <span id="noPage"><%=noPage %></span> of <span id="maxPage"><%=numOfPage %></span></nobr>
			</b></td>
		</tr>
		        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingDay","year_result");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingDay","year_result");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingDay","year_result");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingDay","year_result");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingDay","year_result");' />
                 </div>
            </td>
        </tr>
	</tbody>
</table>
<%
}
if (mapDay.size() > 0)
{ 
%>
<table id="OrderTable" width="80%" cellpadding="2" border="0" class="tablers main">
	<tbody>
	<tr>
	<td align="right" colspan="8" bgcolor="#ffffff"><a href="exportOrder.do?method=excelDayOrder&iyear=<%=year %>&imonth=<%=month %>&iday=<%=day%>"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
		<tr style="background-color:#0099FF">
			<td valign="BOTTOM" align="LEFT"><b> # </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Order </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Time </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Ship To Name </b></td>
			<td valign="BOTTOM" align="RIGHT" nowrap="nowrap"><b> Sale Type </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Items </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Discount </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Total </b></td>
		</tr>
		<%
			String style = "#CCCCCC";
		    for (Order order : mapDay)
		    {
		        style = style.equals("#CCCCCC") ? "#99CCFF" : "#CCCCCC" ;
		%>
		<tr style="background-color:<%= style %>">
			<td valign="TOP" align="CENTER"><%=order.getId() %></td>

			<td valign="TOP" align="LEFT"><a href="order_db.do?method=orderViewPendingSearch&order_id=<%=order.getOrderId() %>"><%=order.getOrderId() %></a></td>
			<td valign="TOP" align="LEFT" nowrap="nowrap"><%=order.getTime() %></td>
			<td valign="TOP" align="LEFT"><a href='shopper_manager.do?method=getShopperDetails&shopperId=<%=order.getShopId() %>'><%=order.getShip_to_name() %></a></td>
			<td valign="TOP" align="RIGHT"><%=order.getOrderType() %></td>
			<td valign="TOP" align="RIGHT"><%=order.getItem() %></td>
			<td valign="TOP" align="RIGHT"><%=order.getDiscount() %>%</td>
			<td width="80" valign="TOP" align="RIGHT"><%=Converter.getMoney(order.getTotal_total()) %></td>
		</tr>
		<%
		    }
		%>

	</tbody>
</table>
<%
}else
{ %>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><i>No Order in this day</i></b>
<%
}
%>
<%@include file="/html/scripts/order_dayScript.jsp"%>
