<%@page import="com.dell.enterprise.agenttool.model.OrderHeader"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>
<%
    OrderHeader orderHeader = null;
	List<String> errorList = null;
	if(request.getAttribute(Constants.ATTR_LIST_ERROR)!= null)
	{
	    errorList = (List<String>)request.getAttribute(Constants.ATTR_LIST_ERROR);
	}
    if (request.getAttribute(Constants.ATTR_ORDER_RECEIPT) != null)
    {
        CheckoutService checkoutService = new CheckoutService();
        orderHeader = (OrderHeader) request.getAttribute(Constants.ATTR_ORDER_RECEIPT);
%>


<div style="padding-left: 20px"><img alt="Order Confirmation"
	src="images/order_confirmation.gif">
	
	<%
	if(request.getAttribute(Constants.ATTR_LIST_ERROR)!= null)
	{
	%>
	<ul style="color:red;">
	  	<%
	  		for(String val : errorList)
	  		{
  		    	%><li><%=val %></li><%
	  		}
	  	%>
	</ul>
	<%    
	}
	%>
	
<table width="100%">
	<tbody>
		<tr>
			<td><font size="2" face="Arial, Helvetica"> <b>Your
			order has been processed.</b> <br>
			<br>
			Please record your order number for tracking purposes. <br>
			<br>
			<table width="100%">
				<tbody>
					<tr>
						<th width="15%" valign="top" nowrap="" align="left"><font
							size="2" face="Arial, Helvetica"> Order Number: </font></th>
						<td width="85%"><font size="2" face="Arial, Helvetica"><%=orderHeader.getOrderNumber()%><br>
						</font></td>
					</tr>
					<tr>
						<th valign="top" nowrap="" align="left"><font size="2"
							face="Arial, Helvetica"> Order Date: </font></th>
						<td><font size="2" face="Arial, Helvetica"><%= Constants.formatDate(orderHeader.getCreatedDate(),"MM/dd/yyyy") %><br>
						</font></td>
					</tr>
					<tr>
						<th valign="top" nowrap="" align="left"><font size="2"
							face="Arial, Helvetica"> Order Total: </font></th>
						<td><font size="2" face="Arial, Helvetica"> <%=Constants.FormatCurrency(orderHeader.getTotal_total().floatValue())%>
						<br>
						</font></td>
					</tr>
					<tr>
						<th valign="top" nowrap="" align="left"><font size="2"
							face="Arial, Helvetica"> Ship To: </font></th>
						<td><font size="2" face="Arial, Helvetica"> <b> <%=orderHeader.getShip_to_name()%>
						</b><br>
						<%=orderHeader.getShip_to_company()%> <br>
						<%=orderHeader.getShip_to_address1()%><br>
						<%=orderHeader.getShip_to_city()%>,<%=orderHeader.getShip_to_state()%>
						<%=orderHeader.getShip_to_postal()%> <br>
						</font></td>
					</tr>
					<tr>
						<th valign="top" nowrap="" align="left"><font size="2"
							face="Arial, Helvetica"> Ship Via: </font></th>
						<td><font size="2" face="Arial, Helvetica"> <%= checkoutService.getShortShipping(orderHeader.getShip_method(),orderHeader.getShip_terms()) %>
						</font></td>
					</tr>
				</tbody>
			</table>
			<font size="2" face="Arial, Helvetica"> <br>

			</font></font></td>
		</tr>
	</tbody>
</table>
<br>
<font size="2" face="Arial, Helvetica"> <br>
You will be notified via E-mail once your order has shipped. You can
also check the status of your order by selecting "Order Tracking" from
the main Menu. An invoice/receipt will be mailed under separate cover to
the billing address. <br>
<br>
Thank you for your shopping with DFS Direct Sales Sales, your source for
quality off-lease Dell computers. Please visit us again soon. <br>
<br>
</font> <br>
<br>
</div>
<%
    }
%>