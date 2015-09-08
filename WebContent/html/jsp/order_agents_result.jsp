<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderAgent"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="java.math.BigDecimal"%>
<%
    List<OrderAgent> list = (List<OrderAgent>) request.getAttribute(Constants.ORDER_AGENT_DATE_RESULT);
	String date1 =(String) request.getAttribute(Constants.DATE1);
	String date2 =(String) request.getAttribute(Constants.DATE2);
%>
<% if (list.size() > 0)
    {
        Double finalTotal = 0.00;
        Double finalAsp = 0.00;
        Double finalDiscAvg = 0.00;
        BigDecimal finalMax = BigDecimal.valueOf(0);
        BigDecimal finalMin = BigDecimal.valueOf(0);
        BigDecimal finalDiscMax = BigDecimal.valueOf(0);
        BigDecimal finalDiscMin = BigDecimal.valueOf(0);
%>
<table width="100%" cellpadding="2" border="0" class="tablers main">
	<tbody>
	<tr >
	<td align="right" colspan="9" bgcolor="#ffffff"><a href="exportOrder.do?method=excelAgentOrders&date1=<%=date1%>&date2=<%=date2 %>"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
		<tr>
			<th valign="BOTTOM" align="CENTER" colspan="2" bgcolor="#ffffff"></th>
			<th valign="BOTTOM" align="CENTER" colspan="4" style="background-color: green;"><font
				face="Verdana, Helvetica, Sans-Serif" size="2" > Sale Price </font></th>
			<th valign="BOTTOM" align="CENTER" colspan="3" style="background-color:yellow;"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Discounts </font></th>
		</tr>
		<tr style="background-color:#CCCCCC">
			<th width="13%" valign="BOTTOM" align="RIGHT" bgcolor="#ffffff"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Agent </font></th>
			<th width="13%" valign="BOTTOM" align="RIGHT" bgcolor="#ffffff"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Num Unit </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Order Sub Total  $ </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Unit ASP $  </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Order Max $  </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Order Min $ </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Avg % </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Max % </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Min % </font></th>
		</tr>
		<%
			String style = "#CCCCCC";
		    for (OrderAgent agent : list)
		        {
		           finalTotal += agent.getTotalSum().doubleValue();
		           if(agent.getTotalMax().compareTo(finalMax)>0) finalMax =agent.getTotalMax();
		           finalMin = agent.getTotalMin();		           
		           if(agent.getDiscMax().compareTo(finalDiscMax)>0) finalDiscMax =agent.getDiscMax();
		           finalDiscMin = agent.getDiscMin();
		           finalAsp +=agent.getUnitAsp().doubleValue();
		           finalDiscAvg +=agent.getDiscAvg().doubleValue();
		           if(style.equals("#CCCCCC"))
		               style = "#99CCFF";
		           else 
		               style = "#CCCCCC";
		%>
		<tr style="background-color:<%= style %>">
			<td valign="TOP" align="RIGHT" bgcolor="#ffffff"><a
				href="<%=request.getContextPath()%>/order_db.do?method=detailAgent&agentId=<%=agent.getAgentId()%>&date1=<%=date1 %>&date2=<%=date2 %>&agentName=<%=agent.getAgent() %>"><%=agent.getAgent()%></a></td>
			<td valign="TOP" align="RIGHT" bgcolor="#ffffff"><%=agent.getNumUnit()%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(agent.getTotalSum())%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(agent.getUnitAsp())%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(agent.getTotalMax())%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(agent.getTotalMin())%></td>
			<td valign="TOP" align="RIGHT"><%=agent.getDiscAvg()%>%</td>
			<td valign="TOP" align="RIGHT"><%=agent.getDiscMax()%>%</td>
			<td valign="TOP" align="RIGHT"><%=agent.getDiscMin()%>%</td>
		</tr>
		<%
		    }
		 for (OrderAgent agent : list)
	        {
	         BigDecimal min = agent.getTotalMin();
	         if (finalMin.compareTo(min)>0)finalMin = min;
	         BigDecimal temp = agent.getDiscMin();
	         if (finalDiscMin.compareTo(temp)>0)finalDiscMin = temp;
	        }
		%>
		<tr>
			<th valign="TOP" align="RIGHT" colspan="2" bgcolor="#ffffff"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Column Totals
			</font></th>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=Converter.getMoney(BigDecimal.valueOf(finalTotal))%></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT">$<%=BigDecimal.valueOf(finalAsp/list.size()).setScale(2, BigDecimal.ROUND_HALF_EVEN)%></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=Converter.getMoney(finalMax) %></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=Converter.getMoney(finalMin) %></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=BigDecimal.valueOf(finalDiscAvg/list.size()).setScale(2, BigDecimal.ROUND_HALF_EVEN)%>%</td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=finalDiscMax %>%</td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=finalDiscMin %>%</td>


		</tr>
	</tbody>
</table>
<%
    }
    else
    {
%> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<b><i>No Order by Agent in this Time</i> </b> <%
     }
 %>
 <%@include file="/html/scripts/order_agents_resultScript.jsp"%>