<%@page import="com.dell.enterprise.agenttool.model.OrderSummary"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="com.dell.enterprise.agenttool.model.Summary"%>
<%
List<OrderSummary> listOrder=(List<OrderSummary>) request.getAttribute(Constants.ORDER_REPORT_SUMMARY_RESULT);
List<Summary> listAgent=(List<Summary>) request.getAttribute(Constants.ORDER_REPORT_SUMMARY_AGENT_RESULT);
if(listOrder != null && !listOrder.isEmpty()){ 
    Summary summary = new Summary();
    summary.setSales(BigDecimal.valueOf(0));
    summary.setUnits(0);
    summary.setMhz(0);
    summary.setAsp(BigDecimal.valueOf(0));
    summary.setSalesMhz(BigDecimal.valueOf(0));
    summary.setOrders(0);
    
    String catid =request.getAttribute(Constants.CATID).toString();
    String ostype = request.getAttribute(Constants.OSTYPE).toString();
    String brandtype = request.getAttribute(Constants.BRANDTYPE).toString();
    String model = request.getAttribute(Constants.MODEL).toString();
    String cosmetic = request.getAttribute(Constants.COSMETIC).toString();
    String date1 = request.getAttribute(Constants.DATE1).toString();
    String date2 = request.getAttribute(Constants.DATE2).toString();
    String proctype = request.getAttribute(Constants.PROCTYPE).toString();

%>
<form action="exportOrder.do?method=excelSumaryReport" id="excelSumary" method="post">
<table width="100%" class="tablers main">
		<tbody>
	<tr>
	<td align="right" colspan="7" bgcolor="#ffffff"><a href="javascript:void(0)" id="exportId"><font color="red"><u>EXPORT TO EXCEL</u></font></a>
	<input type="hidden" name="catid" value="<%=catid %>"/>
	<input type="hidden" name="ostype" value="<%=ostype %>"/>
	<input type="hidden" name="brandType" value="<%=brandtype %>"/>
	<input type="hidden" name="model" value="<%=model %>"/>
	<input type="hidden" name="date1" value="<%=date1 %>"/>
	<input type="hidden" name="date2" value="<%=date2 %>"/>
	<input type="hidden" name="proctype" value="<%=proctype %>"/>
	<input type="hidden" name="cosmetic" value="<%=cosmetic %>"/>
	</tr>		
<%for (OrderSummary orderSummary : listOrder){ 
   		if( orderSummary.getAgent() == null){
   		    orderSummary.setAgent(summary);
   		}
   		if( orderSummary.getStore() == null){
   		    orderSummary.setStore(summary);
   		}
   		if( orderSummary.getAuction() == null){
   		    orderSummary.setAuction(summary);
   		}
   		if( orderSummary.getEbay() == null){
   		    orderSummary.setEbay(summary);
   		}
   		if( orderSummary.getCustomer() == null){
   		    orderSummary.setCustomer(summary);
   		}
   		BigDecimal sales = orderSummary.getAgent().getSales().add(orderSummary.getStore().getSales()).add(orderSummary.getAuction().getSales()).add(orderSummary.getEbay().getSales()).add(orderSummary.getCustomer().getSales()).setScale(3,RoundingMode.HALF_EVEN);
   		int unit = orderSummary.getAgent().getUnits() +orderSummary.getStore().getUnits()+orderSummary.getAuction().getUnits()+orderSummary.getEbay().getUnits()+orderSummary.getCustomer().getUnits();
   		int mhz = orderSummary.getAgent().getMhz() +orderSummary.getStore().getMhz()+orderSummary.getAuction().getMhz()+orderSummary.getEbay().getMhz()+orderSummary.getCustomer().getMhz();
   		BigDecimal asp = BigDecimal.valueOf(0);
   		BigDecimal salesMhz = BigDecimal.valueOf(0);
   		if(unit>0 && sales != null){
   		    asp = sales.divide(BigDecimal.valueOf(unit),0,RoundingMode.HALF_EVEN);
   		}
   		if(mhz>0 && sales != null){
   			 salesMhz = sales.divide(BigDecimal.valueOf(mhz),3,RoundingMode.HALF_EVEN);
   		}
%>			
		<tr style="background-color:  #0099FF">
			<th  height="25" align="LEFT"><font size="2"></font></th>
			<th  height="25" align="center"><font size="2"> Agent </font></th>
			<th  height="25" align="center"><font size="2"> Store </font></th>
			<th  height="25" align="center"><font size="2"> Dell Auction </font></th>
			<th  height="25" align="center"><font size="2"> Ebay </font></th>
			<th  height="25" align="center"><font size="2"> Agent Store </font></th>
			<th  height="25" align="center"><font size="2"> Retail </font></th>
			
		</tr>	
		<tr style="background-color:#CCCCCC">
			<td>Revenue</td>
			<td align="right"><%=Converter.getMoney(orderSummary.getAgent().getSales()) %></td>		
			<td align="right"><%=Converter.getMoney(orderSummary.getStore().getSales()) %></td>
			<td align="right"><%=Converter.getMoney(orderSummary.getAuction().getSales()) %></td>
			<td align="right"><%=Converter.getMoney(orderSummary.getEbay().getSales()) %></td>
			<td align="right"><%=Converter.getMoney(orderSummary.getCustomer().getSales()) %></td>
			<td align="right"><%=Converter.getMoney(sales)%></td>
		</tr>		
		<tr style="background-color:#99CCFF">
			<td>Units</td>
			<td align="right"><%=orderSummary.getAgent().getUnits()%></td>
			<td align="right"><%=orderSummary.getStore().getUnits() %></td>
			<td align="right"><%=orderSummary.getAuction().getUnits() %></td>
			<td align="right"><%=orderSummary.getEbay().getUnits() %></td>
			<td align="right"><%=orderSummary.getCustomer().getUnits() %></td>
			<td align="right"><%=unit%></td>
		</tr>		
		<tr style="background-color:#CCCCCC">
			<td>ASP</td>
			
				<td align="right"><%=Converter.getMoneyNonePoint(orderSummary.getAgent().getAsp()) %></td>
				<td align="right"><%=Converter.getMoneyNonePoint(orderSummary.getStore().getAsp()) %></td>
				<td align="right"><%=Converter.getMoneyNonePoint(orderSummary.getAuction().getAsp()) %></td>			
				<td align="right"><%=Converter.getMoneyNonePoint(orderSummary.getEbay().getAsp()) %></td>
				<td align="right"><%=Converter.getMoneyNonePoint(orderSummary.getCustomer().getAsp()) %></td>				
				<td align="right"><%=Converter.getMoneyNonePoint(asp) %></td>
			
		</tr>
		<tr style="background-color:#99CCFF">
			<td>$/Mhz</td>			
				<td align="right">$<%=orderSummary.getAgent().getSalesMhz() %></td>			
				<td align="right">$<%=orderSummary.getStore().getSalesMhz() %></td>			
				<td align="right">$<%=orderSummary.getAuction().getSalesMhz() %></td>			
				<td align="right">$<%=orderSummary.getEbay().getSalesMhz() %></td>
				<td align="right">$<%=orderSummary.getCustomer().getSalesMhz() %></td>			
				<td align="right">$<%=salesMhz%></td>
		</tr>
		<tr style="background-color:#CCCCCC">
			<td>Orders</td>
			<td align="right"><%=orderSummary.getAgent().getOrders()%></td>
			<td align="right"><%=orderSummary.getStore().getOrders() %></td>
			<td align="right"><%=orderSummary.getAuction().getOrders() %></td>
			<td align="right"><%=orderSummary.getEbay().getOrders() %></td>
			<td align="right"><%=orderSummary.getCustomer().getOrders() %></td>
			<td align="right"><%=orderSummary.getAgent().getOrders() +orderSummary.getStore().getOrders()+orderSummary.getAuction().getOrders()+orderSummary.getEbay().getOrders()+orderSummary.getCustomer().getOrders()%></td>
		</tr>
<!-- 		<tr>
			<td>MHZ</td>
			<td align="right"><%=orderSummary.getAgent().getMhz()%></td>
			<td align="right"><%=orderSummary.getStore().getMhz() %></td>
			<td align="right"><%=orderSummary.getAuction().getMhz() %></td>
			<td align="right"><%=orderSummary.getEbay().getMhz()%></td>
			<td align="right"><%=orderSummary.getCustomer().getMhz()%></td>
			<td align="right"><%=mhz%></td>
		</tr>
 -->
		<%
		break;} %>
</tbody></table>	</form>
<%}else{ %>
<b><i>No data available </i></b>
<%
} if(listAgent != null && !listAgent.isEmpty())
{ 
    int unit = 0;
    BigDecimal sales = BigDecimal.valueOf(0);
    BigDecimal asp = BigDecimal.valueOf(0);
    int mhz = 0;
    BigDecimal salesMhz = BigDecimal.valueOf(0);
    String name = "Unknown Agent" ;
    %>
 <br/><br/>   
<table width="100%" class="tablers main">
		<tbody><tr style="background-color:  #0099FF">
			<th width="10%" height="25" align="LEFT"><font size="2"></font></th>
			<%
			for(Summary summary : listAgent)
			{ 
			%>
			<th height="25" align="center"><font size="2"><%= (summary.getAgentName()== null)? name :summary.getAgentName()%></font></th>
			<%	
			} 
			%>
			<th width="10%" height="25"  align="center"><font size="2">Agent</font></th>
		</tr>
		<tr style="background-color:#CCCCCC">
			<td>Revenue</td>
			<%for(Summary summary : listAgent){ %>
			<td  align="right"><%=Converter.getMoney(summary.getSales()) %></td>
			<%
			sales = sales.add(summary.getSales());
			} %>
			<td  align="right"><%=Converter.getMoney(sales.setScale(2,RoundingMode.HALF_EVEN)) %></td>
		</tr>
		<tr style="background-color:#99CCFF">
			<td>Units</td>
			<%for(Summary summary : listAgent){ %>
			<td  align="right"><%=summary.getUnits() %></td>
			<%
			unit += summary.getUnits();
			} %>
			<td  align="right"><%=unit %></td>
		</tr>
		<tr style="background-color:#CCCCCC">
			<td>ASP</td>			
			<%for(Summary summary : listAgent){ %>
			<td  align="right"><%=Converter.getMoneyNonePoint((summary.getUnits() >0)?summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()),0,RoundingMode.HALF_EVEN):BigDecimal.valueOf(0)) %></td>
				<%
				asp =(unit >0)? sales.divide(BigDecimal.valueOf(unit),0,RoundingMode.HALF_EVEN):asp;
				} %>
			<td  align="right"><%=Converter.getMoneyNonePoint(asp) %></td>
			
		</tr>
		<tr style="background-color:#99CCFF">
			<td>$/Mhz</td>
			<%for(Summary summary : listAgent){ %>
			<td  align="right">$<%=(summary.getMhz()> 0)? summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()),3,RoundingMode.HALF_EVEN):BigDecimal.valueOf(0)%></td>
			<%
			mhz += summary.getMhz();
			salesMhz =(mhz>0)? sales.divide(BigDecimal.valueOf(mhz),3,RoundingMode.HALF_EVEN):salesMhz;			
			} %>
				<td  align="right">$<%=salesMhz %></td>
			
		</tr>
<!--		
		<tr>
			<td>Mhz</td>
			<%for(Summary summary : listAgent){ %>
			<td  align="right">$<%=summary.getMhz()%></td>
			<%
			} %>
				<td  align="right">$<%=mhz %></td>
			
		</tr>
-->		
</tbody></table>
<%
}else
{
//if (listOrder!=null && listAgent!=null && listOrder.size()>0 && listAgent.size() == 0)
%>
<br/>
<b><i>No data available for Agent</i></b>
<%} %>
<script>
$('#exportId').click(function() {
	$('#excelSumary').submit();
});
</script>
