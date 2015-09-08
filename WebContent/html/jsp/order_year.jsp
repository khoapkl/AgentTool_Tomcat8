<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Map"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderDate"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
    Map<Integer, OrderDate> mapYear = (Map<Integer, OrderDate>) request.getAttribute(Constants.ORDER_YEAR_RESULT);
    String year =String.valueOf(request.getAttribute(Constants.ORDER_YEAR_PARAM));
    if (mapYear.size() > 0)
    {
        Double finalTotal = 0.00;
        Double finalAvg = 0.00;
        Double finalDiscAvg = 0.00;
        BigDecimal finalMax = BigDecimal.valueOf(0);
        BigDecimal finalMin = BigDecimal.valueOf(0);
        BigDecimal finalDiscMax = BigDecimal.valueOf(0);
        BigDecimal finalDiscMin = BigDecimal.valueOf(0);
        int count = 0;
%>

<table width="100%" cellpadding="2" border="0" class="tablers main">
	<tbody>
   <tr>
	<td align="right" colspan="9" bgcolor="#ffffff"><a href="exportOrder.do?method=excelYearOrder&iyear=<%=year %>"><font color="red"><u>EXPORT TO EXCEL</u></font></a></td>
	</tr>
		<tr>
			<th valign="BOTTOM" align="CENTER" colspan="2"  bgcolor="#ffffff"></th>
			<th valign="BOTTOM" bgcolor="green" align="CENTER" colspan="4"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Sale Price </font></th>
			<th valign="BOTTOM" bgcolor="yellow" align="CENTER" colspan="3"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Discounts </font></th>
		</tr>
		<tr style="background-color:#CCCCCC">
			<th width="13%" valign="BOTTOM" align="RIGHT"  bgcolor="#ffffff"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Month </font></th>
			<th width="13%" valign="BOTTOM" align="RIGHT"  bgcolor="#ffffff"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Num Orders </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Total $ </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Avg $ </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Max $ </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Min $ </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Avg % </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Max % </font></th>
			<th valign="BOTTOM" align="RIGHT"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Min % </font></th>
		</tr>
		<%
			String style = "#CCCCCC";
		    for (Iterator<OrderDate> iterator = mapYear.values().iterator(); iterator.hasNext();)
		        {
		           OrderDate orderDate = iterator.next();
		           finalTotal += orderDate.getTotalSum().doubleValue();
		           if(orderDate.getTotalMax().compareTo(finalMax)>0) finalMax =orderDate.getTotalMax();
		           finalMin = orderDate.getTotalMin();
		           if(orderDate.getDiscMax().compareTo(finalDiscMax)>0) finalDiscMax =orderDate.getDiscMax();
		           finalDiscMin = orderDate.getDiscMin();
		           finalAvg +=orderDate.getTotalAvg().doubleValue();
		           finalDiscAvg +=orderDate.getDiscAvg().doubleValue();
		           count ++;
		           
		           if(style.equals("#CCCCCC"))
		               style = "#99CCFF";
		           else 
		               style = "#CCCCCC";
		%>
		<tr style="background-color:<%= style %>">
			<td valign="TOP" align="RIGHT" bgcolor="#ffffff"><a
				href="#" onclick='getOrderByMonth("<%=year %>","<%=orderDate.getMonthNum()%>");'><%=orderDate.getMonth()%></a></td>
			<td valign="TOP" align="RIGHT" bgcolor="#ffffff"><%=orderDate.getNumOrder()%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(orderDate.getTotalSum())%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(orderDate.getTotalAvg())%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(orderDate.getTotalMax())%></td>
			<td valign="TOP" align="RIGHT"><%=Converter.getMoney(orderDate.getTotalMin())%></td>
			<td valign="TOP" align="RIGHT"><%=orderDate.getDiscAvg()%>%</td>
			<td valign="TOP" align="RIGHT"><%=orderDate.getDiscMax()%>%</td>
			<td valign="TOP" align="RIGHT"><%=orderDate.getDiscMin()%>%</td>
		</tr>
		<%
		    }
		 for (Iterator<OrderDate> iterator = mapYear.values().iterator(); iterator.hasNext();)
	        {
	         OrderDate orderDate = iterator.next();
	         BigDecimal min = orderDate.getTotalMin();
	         if (finalMin.compareTo(min)>0)finalMin = min;
	         BigDecimal temp = orderDate.getDiscMin();
	         if (finalDiscMin.compareTo(temp)>0)finalDiscMin = temp;
	        }
		%>
		<tr>
			<th valign="TOP" align="RIGHT" colspan="2" bgcolor="#ffffff"><font
				face="Verdana, Helvetica, Sans-Serif" size="2"> Column Totals
			</font></th>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=Converter.getMoney(BigDecimal.valueOf(finalTotal))%></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT">$<%=BigDecimal.valueOf(finalAvg/count).setScale(2, BigDecimal.ROUND_HALF_EVEN)%></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=Converter.getMoney(finalMax) %></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=Converter.getMoney(finalMin) %></td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=BigDecimal.valueOf(finalDiscAvg/count).setScale(2, BigDecimal.ROUND_HALF_EVEN)%>%</td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=finalDiscMax %>%</td>
			<td valign="TOP" bgcolor="#0099FF" align="RIGHT"><%=finalDiscMin %>%</td>


		</tr>
	</tbody>
</table>
<%
    }
    else
    {
%> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<b><i>No Order in this year</i> </b> <%
     }
 %>
