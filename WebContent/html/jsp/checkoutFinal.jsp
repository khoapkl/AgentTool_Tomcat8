<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderHeader"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>
<%@page import="java.util.List"%>
<%
    Customer customer = null;
    OrderHeader orderHeader = null;
    String shopper_id = "";
    String order_id = "";
    CheckoutService checkoutService = new CheckoutService();
    if (request.getAttribute(Constants.ATTR_CHECKOUT_RESULTS) != null)
    {
        if (request.getAttribute(Constants.ATTR_CUSTOMER) != null)
        {
            customer = (Customer) request.getAttribute(Constants.ATTR_CUSTOMER);
        }
        if (request.getAttribute(Constants.ATTR_ORDER_RECEIPT) != null)
        {
            orderHeader = (OrderHeader) request.getAttribute(Constants.ATTR_ORDER_RECEIPT);
        }
        
        if(request.getAttribute(Constants.ATTR_SHOPPER_ID) != null)
        {
            shopper_id = request.getAttribute(Constants.ATTR_SHOPPER_ID).toString();
        }
        
        if(request.getAttribute(Constants.ATTR_ORDER_ID) != null)
        {
            order_id = request.getAttribute(Constants.ATTR_ORDER_ID).toString();
        }
%>


<div style="padding-left: 20px"><img alt="Order Confirmation"
	src="images/order_confirmation.gif">

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
						<td><font size="2" face="Arial, Helvetica"><%=Constants.dateNow("MM/dd/yyyy")%><br>
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
<br />
<input type="SUBMIT" border="0"
	onmouseout="window.status=''; return true"
	onmouseover="window.status='Click for warranty information.'; return true"
	onclick="openwindow('checkout3.do?method=printOrder&order_id=<%=order_id %>&shopper_id=<%= shopper_id %>')"
	value="Print Screen" name="Print Screen"> <br />
<br />
<input type="SUBMIT" border="0" value="Return to Main Page" id="btReturn" name="btReturn"
	name="Continue Shopping"></div>

<%
    }
    else
    {
        out.println("Please checkout againt !");
    }
%>

<script type="text/javascript">
	$("#btReturn").click(function(){
			window.location = 'doStartPage.do';
	});
</script>
