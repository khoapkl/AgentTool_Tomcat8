<%@page import="java.util.List"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.util.Locale"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderPending"%>
<%@include file="/html/scripts/order_pending_listScript.jsp"%>


<%@page import="com.dell.enterprise.agenttool.util.Constants"%>

<%@page import="java.math.BigDecimal"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%><TABLE BORDER=0 CELLPADDING=2 WIDTH=600>
<table style='min-width:70%'>
	<tr>
		<td colspan="2">	
<div id="note_result">
<%
List<OrderPending> listOrderPending = (List<OrderPending>)request.getAttribute(Constants.LIST_ORDER_PENDING_FORWARD);
   	if(listOrderPending.size()>0){	
	int totalRecord = (Integer) request.getAttribute(Constants.ORDER_TOTAL);
    int pageRecord = 40;
    if (totalRecord > pageRecord)
    {
        int numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
        int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>

<table cellspacing="3" border="0" class="main">
        <tbody>    
       
        <tr>
            <td bgcolor="lightgrey" align="center"><b>
                <nobr><span id="totalRecord"><%=totalRecord%></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp; Page: <span id="noPage"><%=noPage%></span> of <span id="maxPage"><%=numOfPage%></span></nobr>
            </b></td>
        </tr>
        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingListAllOrderPending","note_result");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingListAllOrderPending","note_result");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingListAllOrderPending","note_result");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingListAllOrderPending","note_result");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingListAllOrderPending","note_result");' />
                 </div>
            </td>
        </tr>
  </tbody></table>
 <%} %> 
		<table id="OrderTable">
		  <tr><td colspan="8" align="right" style="background-color:#CCCCCC"><A HREF="order_db.do?method=exportExcelAllpending"><font color="red"><u>EXPORT TO EXCEL</u></font></A></td></tr>
		<TR style="background-color:#0099FF"> 
		<TD ALIGN=LEFT VALIGN=BOTTOM width="10%"><B>Order</B></TD>
		<TD ALIGN=LEFT VALIGN=BOTTOM width="30%" nowrap="nowrap"><B>Ship To Name</B></TD>
		<TD ALIGN=RIGHT VALIGN=BOTTOM width="15%"><B>Total</B></TD>
		<TD ALIGN=LEFT VALIGN=BOTTOM width="15%"><B>Agent</B></TD>
		<TD ALIGN=LEFT VALIGN=BOTTOM width="30%"><B>Term</B></TD>
    </TR>
    
<% 
String style = "#CCCCCC";
		
NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
	
for(OrderPending order : listOrderPending) 
{
    if(style.equals("#CCCCCC"))
        style = "#99CCFF";
    else 
        style = "#CCCCCC";
%>	
		<tr style="background-color:<%= style %>">
			<td width="10%" nowrap="nowrap"><a href="order_db.do?method=orderViewPending&order_id=<%=order.getOrdernumber()%>"><%=order.getOrdernumber()%></a></td>
			<td width="30%" nowrap="nowrap"><%=order.getShip_to_name()%></td>
			<td width="15%" nowrap="nowrap" align="right"><%= Constants.FormatCurrency(new Float(order.getTotal_total_pending()))%></td>
			<td width="15%" nowrap="nowrap"><%=order.getUsername()%></td>
			<td width="30%" nowrap="nowrap"><%=order.getCc_type()%></td>
		</tr>
<%	
 }}
   	else
   	{
%>	
</table>
<br/>
<b><i>No pending orders</i></b>
<%} %>	
</div>
</td>
</tr>
</table>
	
		
<%@include file="/html/scripts/pagingPendingScript.jsp"%>
</TABLE>
