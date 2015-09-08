<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderRow"%>
<%@page import="com.dell.enterprise.agenttool.model.EstoreBasketItem"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.services.BasketService"%>
<%@page import="com.dell.enterprise.agenttool.services.CustomerServices"%>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>
<%@page import="java.util.ArrayList"%>
<%
    BasketService basketService = new BasketService();
	CheckoutService checkoutService = new CheckoutService();
    OrderRow orderRow = null;
    List<EstoreBasketItem> listEstoreBasketItem = null;
    Customer customer = null;
	String shopper_id = "";
    if (request.getAttribute(Constants.ATTR_ORDER_ROW) != null)
    {
        orderRow = (OrderRow) request.getAttribute(Constants.ATTR_ORDER_ROW);
    }

    if (request.getAttribute(Constants.ATTR_ESTORE_BASKET_ITEM) != null)
    {
        listEstoreBasketItem = (List<EstoreBasketItem>) request.getAttribute(Constants.ATTR_ESTORE_BASKET_ITEM);
    }
    
    if (session.getAttribute(Constants.SHOPPER_ID) != null)
    {
        shopper_id = session.getAttribute(Constants.SHOPPER_ID).toString();
    }
    
    Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
    
 
	if(isCustomer != null)
	{
	    CustomerServices customerServices = new CustomerServices();
		customer = customerServices.getCustomerByShopperID(shopper_id);
	}
%>





<br />
<img alt="Your Cart" src="images/your_cart.gif">
<br />
<%
    if (listEstoreBasketItem.size() == 0)
    {
%>
<blockquote><font size="2" face="Arial, Helvetica"><strong>Your cart is empty.</strong></font></blockquote>
<%
    }
    else
    {
%>
<table width="100%">
	<tbody>
		<tr>
			<td><font size="2" face="Arial, Helvetica">Due to the
			unique nature of each product, items selected will only remain in
			your shopping cart for 1 hour.
			</font>
			</td>
		</tr>
		<tr>
			<td><font color="#ff0000"><b> NOTE: Prices listed on
			this page are list prices before applicable discounts which will be
			applied during your checkout process. </b>
			</font>
			</td>
		</tr>
	</tbody>
</table>
<BR/>
<form id="form1" name="form1" action="showBasket.do" method="POST">
<table id="table1" width="80%" border="0">
	<tbody>
		<tr>
			<td>

			<table width="70%" border="0">
				<tbody>
					<tr>
						<th>&nbsp;</th>
						<th align="LEFT">
							<font size="2" face="Arial, Helvetica">Product<br>Number</font>						</th>
						<th>&nbsp;</th>
						<th valign="BOTTOM" align="CENTER">
							<font size="2" face="Arial, Helvetica"> Product Description</font>						</th>
						<th>&nbsp;</th>
						<th align="right" nowrap="nowrap">
							<font size="2" face="Arial, Helvetica">
							<%
							if(isCustomer != null)
							{
								out.print("Unit Price");
							}else
							{
							    out.print("List Price");
							}
							%>
							</font>						</th>
						<th>&nbsp;</th>
						<th>&nbsp;</th>
					</tr>
					<tr>
						<td colspan="8">
						<hr size="1" noshade="noshade" color="#bebebe">						</td>
					</tr>

						<%

					    int idx = 1;
						Float totalPrice = new Float(0);
						Float listPrice = new Float(0);
				        for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
				        {
				        	if (estoreBasketItem.getItem_sku().contains("WARRANTY"))
				        	{
				        		listPrice = estoreBasketItem.getPlaced_price();
				        	}
				        	else
				        	{
					            listPrice = new Float(basketService.getListPrice(estoreBasketItem.getItem_sku()) * 100);
					            //Float currentPrice = new Float(basketService.getListPrice(estoreBasketItem.getItem_sku()));
					            if(isCustomer != null)
					        	{
					                List<Float> listValue = checkoutService.utilGetDiscount(customer,estoreBasketItem.getItem_sku(),listPrice,estoreBasketItem.getPlaced_price());
					                listPrice = listValue.get(1);
					        	}
				        	}
				            listPrice = listPrice /100;
				            totalPrice += listPrice;
				            
						%>
						<tr>
							<td width="20" align="CENTER">
								<font size="1" face="Arial, Helvetica"><%= idx %></font>							</td>
							<td width="20" align="left">
								<font size="1" face="Arial, Helvetica"><%=estoreBasketItem.getItem_sku()%></font>							</td>
							<td>&nbsp;</td>
							<td align="LEFT">
								<font size="1" face="Arial, Helvetica"><%=estoreBasketItem.getName()%> </font>							</td>
							<td>&nbsp;</td>
							<td align="RIGHT"><font size="1" face="Arial, Helvetica"><%= Constants.FormatCurrency(listPrice) %></font></td>
							<td>&nbsp;</td>
							<td>
								<input type="checkbox" value="<%=estoreBasketItem.getItem_sku()%>" name="chk" id="chk">
								<input type="hidden" value="<%=estoreBasketItem.getItem_sku()%>" name="taken" id="taken">							</td>
						</tr>
						<%
							idx++;
					    }
						%>

					<tr>
						<td colspan="8">
						<hr size="1" noshade="noshade" color="#bebebe">						</td>
					</tr>
					<tr>
						<td align="right">
						</td>
						<td align="right">&nbsp;</td>
						<td align="right">&nbsp;</td>
						<td align="right">
						<strong><font size="2" face="Arial, Helvetica">Estimated Sub Total:</font></strong><br>
							<font size="1" face="Arial, Helvetica"> (Excludes Shipping and Taxes)</font>
						</td>
						<td align="right">&nbsp;</td>
						<td align="right"><font size="2" face="Arial, Helvetica">
								<%= Constants.FormatCurrency(totalPrice) %>							</font>	</td>
						<td align="right" valign="top" colspan="2">
												</td>
					</tr>
					<tr>
						<td colspan="8">&nbsp;</td>
					</tr>


					<tr>
						<td align="RIGHT" colspan="8">
							<input type="button" value="Remove Select Items" name="btRemove" id="btRemove">						
						</td>
					</tr>
					<tr>
						<td align="right" colspan="8">
							<b> <a href="javascript:void(0)" onclick="openwindow('showBasket.do?method=exportExcel');">Export to excel</a></b>
						</td>
					</tr>
				</tbody>
			</table>

			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</tbody>
</table>

	<input type="hidden" value="actionBasket" id="method" name="method">
	<input type="hidden" id="chks" name="chks"> 
	<input type="hidden" id="takens" name="takens"> 
	<input type="hidden" value="clear" id="type" name="type">
</form>



<table width="500px">

	<tr>
		<td><font size="2" face="Arial, Helvetica"> 
				<a href="doStartPage.do"> <img width="15" height="15" border="0" src="images/item.gif"> Continue Shopping </a> 
			</font>
		</td>
		<%
		if(isCustomer!=null && ((Boolean)isCustomer).booleanValue()){
		%>
		<td>
			<a href="shopper.do?method=prepareCheckout&shopper_new=<%=session.getAttribute(Constants.SHOPPER_ID) %>&section=checkout">
				<img width="15" height="15" border="0" src="images/item.gif"> Proceed to Checkout </a>
		</td>
		<%
		}else
		{
		%>		
		<td>
			<a href="cust_lookup.do"><img width="15" height="15" border="0" src="images/item.gif"> Proceed to Customer Lookup </a>
		</td>
		<%
		}
		%>

		<td><font size="2" face="Arial, Helvetica"> 
			<a href="showBasket.do?method=actionBasket&type=cancel">
				<img width="15" height="15" border="0" src="images/item.gif">Cancel	Order
			</a>
			</font>
		</td>
	</tr>

</table>

<%@include file="/html/scripts/viewBasketScript.jsp"%>
<%
    }

    if (request.getAttribute(Constants.ATTR_ORDER_CANCEL) != null)
    {
%>
	<font size="3" face="Arial, Helvetica">
		<b><font color="#ff0000"> Your order has been cancelled. </font></b>
	</font>
	<BR/>
<%
    }
    if (session.getAttribute(Constants.AGENT_INFO) != null && request.getAttribute(Constants.ATTR_COUNT_HELD_ORDER) != null)
    {
        int countHeldOrder = Integer.parseInt(request.getAttribute(Constants.ATTR_COUNT_HELD_ORDER).toString());
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
%>
	You have <%= countHeldOrder %> held orders. 
<%	
    }
%>



