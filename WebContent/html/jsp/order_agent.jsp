<div id="agentDateDetail">
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderAgentDetail"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="java.math.BigDecimal"%>
<br/>
<input type="button" value="<<Back" onclick="document.location.href='<%=request.getContextPath() %>/order_db.do?method=agentOrder&backPage=true'"/>
<br/>
<br/>
<%
    List<OrderAgentDetail> list = (List<OrderAgentDetail>) request.getAttribute(Constants.ORDER_AGENT_ORDER_RESULT);
    int totalRecord = (Integer) request.getAttribute(Constants.ORDER_TOTAL);
	String date1 =(String) request.getAttribute(Constants.DATE1);
	String date2 =(String) request.getAttribute(Constants.DATE2);
	String agentId =(String)request.getAttribute(Constants.ORDER_AGENT_ID);
	String agentName = (String)request.getAttribute(Constants.ORDER_AGENT_NAME);
    int pageRecord = Constants.PAGE_RECORD;
    if (totalRecord >pageRecord)
    {
        int numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
        int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
<table cellspacing="3" border="0" class="main">
	<tbody>
		<tr>
			<td bgcolor="lightgrey" align="center"><b> <nobr><span
				id="totalRecord"><%=totalRecord%></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp;
			Page: <span id="noPage"><%=noPage%></span> of <span id="maxPage"><%=numOfPage%></span></nobr>
			</b></td>
		</tr>
		<tr>
			<td>
			<div id="pagingDiv"><input type="Button" value="   &lt;&lt;   "
				id="firstPage"
				onclick='movePagingAgent("FIRST","movingAgent","agentDateDetail","<%=date1 %>","<%=date2 %>","<%=agentId %>","<%=agentName %>");' />
			<input type="Button" value="   &lt;    " id="prePage"
				onclick='movePagingAgent("PREV","movingAgent","agentDateDetail","<%=date1 %>","<%=date2 %>","<%=agentId %>","<%=agentName %>");' />
			<input type="Button" value="    &gt;   " id="nextPage"
				onclick='movePagingAgent("NEXT","movingAgent","agentDateDetail","<%=date1 %>","<%=date2 %>","<%=agentId %>","<%=agentName %>");' />
			<input type="Button" value="   &gt;&gt;   " id="lastPage"
				onclick='movePagingAgent("LAST","movingAgent","agentDateDetail","<%=date1 %>","<%=date2 %>","<%=agentId %>","<%=agentName %>");' />
			<input type="Button" value=" Requery "
				onclick='movePagingAgent("FIRST","movingAgent","agentDateDetail","<%=date1 %>","<%=date2 %>","<%=agentId %>","<%=agentName %>");' />
			</div>
			</td>
		</tr>
	</tbody>
</table>
<%
    }
    if (list.size() > 0)
    {
%>

<form action="#" method="POST" id="order_agent">
<table id="OrderTable" width="90%" cellpadding="2" border="0" class="tablers main">
	<tbody>
	<tr>
	<td align="right" colspan="12" bgcolor="#ffffff"><a href="exportOrder.do?method=excelAgentDetail&date1=<%=date1%>&date2=<%=date2 %>&agentId=<%=agentId %>&agentName=<%=agentName %>"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
	
		<tr style="background-color:  #0099FF">
			<td valign="BOTTOM" align="LEFT"><b> # </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Order ID </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Date </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Ship To Name </b></td>
			<td valign="BOTTOM" align="LEFT"><b> Ship To Company </b></td>
			
			
			
			<td valign="BOTTOM" align="RIGHT"><b> Items Sold </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Discount% </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Discount Amt </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Avg $/Mhz </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Avg Mhz </b></td>
			<td valign="BOTTOM" align="RIGHT"><b> Order Total </b></td>
		</tr>
		<%
			String style = "#CCCCCC";
		    for (OrderAgentDetail detail : list)
		    {
		        style = style.equals("#CCCCCC") ? "#99CCFF" : "#CCCCCC" ;
		        String avg_mhz = (detail.getAvgMhz().getTotal_price() != BigDecimal.valueOf(0))? Converter.getMoney(detail.getAvgMhz().getTotal_price()): "N/A";
		        String average_mhz = (detail.getAvgMhz().getUnit_mhz() >0)?(String.valueOf(Math.round(detail.getAvgMhz().getSpeed_total()/detail.getAvgMhz().getUnit_mhz()))):"N/A";
		%>
		<tr style="background-color:<%= style %>">
			<td valign="TOP" align="CENTER"><%=detail.getId() %></td>

			<td valign="TOP" align="LEFT"><a href="order_db.do?method=orderViewPendingSearch&order_id=<%=detail.getOrderId() %>&from=orderAgent"><%=detail.getOrderId() %></a></td>
			<td valign="TOP" align="LEFT"><%=detail.getDayOrder() %></td>
			<td valign="TOP" align="LEFT"><a
				href="shopper_manager.do?method=getShopperDetails&shopperId=<%=detail.getShopId()%>&from=orderAgent"><%=detail.getShip_to_name() %></a></td>
			<td valign="TOP" align="LEFT"><%=detail.getShip_to_company() %></td>
			
			<td valign="TOP" align="RIGHT"><%=detail.getItem() %></td>
			<td valign="TOP" align="RIGHT"><%=detail.getDiscount()%>%</td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(detail.getTotalDisc()) %></td>

			<td width="80" valign="TOP" align="RIGHT"><%=avg_mhz %></td>
			<td width="80" valign="TOP" align="RIGHT"><%=average_mhz %></td>
			<td width="80" valign="TOP" align="RIGHT"><%=Converter.getMoney(detail.getTotal_total()) %></td>
		</tr>

	</tbody>
	<%
	    }
	%>
</table>
</form>
<%
    }
    else
    {
%>
&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
<b><i>No Order by Agent</i> </b>
<%
    }
%>
<script>
$(document).ready(function() 
{
	$('#pageTitle').text("Orders by Agents: <%=agentName %>");
	$('#topTitle').text("Orders by Agents");
	var pageNo = $('#noPage').text();
	var numOfPage = $('#maxPage').text();
	if (pageNo == 1) {
		$('#firstPage').attr('disabled', 'disabled');
		$('#prePage').attr('disabled', 'disabled');
	} else {
		$('#firstPage').removeAttr('disabled');
		$('#prePage').removeAttr('disabled');
	}
	if(pageNo == numOfPage){
		$('#lastPage').attr('disabled', 'disabled');
		$('#nextPage').attr('disabled', 'disabled');
	}
	else{
		$('#lastPage').removeAttr('disabled');
		$('#nextPage').removeAttr('disabled');
		}
	$('#btAllAgent').click(function(){
		$('#agentDate').show();
		$('#agentDateDetail').html("");
		});
});
</script>
 <%@include file="/html/scripts/order_agents_resultScript.jsp"%>
 <%@include file="/html/scripts/dialogScript.jsp"%>
 </div>