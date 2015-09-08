<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter"%>
<%@page import="com.dell.enterprise.agenttool.DAO.CheckoutDAO"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.EstoreBasketItem"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderRow"%>
<%@page import="com.dell.enterprise.agenttool.model.EppPromotionRow"%>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>
<%@page import="com.dell.enterprise.agenttool.model.PayMethods"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderHeader"%>
<%@page import="com.dell.enterprise.agenttool.model.TaxTables"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Timestamp"%>


<%
	boolean b = false;
    List<EstoreBasketItem> basketItemCheck = null;
    Customer customer = null;
    OrderRow orderRow = null;
    EppPromotionRow eppPromotionRow = null;
    CheckoutService checkoutService = new CheckoutService();
    OrderHeader orderReceipt = null; 
    TaxTables taxTables = null;
    int holdDays = 0 ;
    Calendar cal = Calendar.getInstance();
    
    /* if (request.getAttribute(Constants.ATTR_CUSTOMER) != null)
    {
        customer = (Customer) request.getAttribute(Constants.ATTR_CUSTOMER);
        
    } */
    
    if (session.getAttribute("CUSTOMER_CHECKOUT_SESSION") != null)
    {
        customer = (Customer) session.getAttribute("CUSTOMER_CHECKOUT_SESSION");
    }else{
    	customer = (Customer) request.getAttribute(Constants.ATTR_CUSTOMER);
    }

    if (request.getAttribute(Constants.ATTR_ORDER_ROW) != null)
    {
        orderRow = (OrderRow) request.getAttribute(Constants.ATTR_ORDER_ROW);
    }

    if (request.getAttribute(Constants.ATTR_EPP_PROMOTION) != null)
    {
        eppPromotionRow = (EppPromotionRow) request.getAttribute(Constants.ATTR_EPP_PROMOTION);
    }
	
    if (request.getAttribute(Constants.ATTR_ORDER_RECEIPT) != null)
    {
        orderReceipt = (OrderHeader) request.getAttribute(Constants.ATTR_ORDER_RECEIPT);
    }else
    {
        orderReceipt =  new OrderHeader();
    }
    
    if (request.getAttribute(Constants.ATTR_TAX_TABLES) != null)
    {
        taxTables = (TaxTables)request.getAttribute(Constants.ATTR_TAX_TABLES);
    }
    
	//Check Tax Exempt of customer
    if(customer.getTax_exempt() == 1 
        && (customer.getTax_exempt_number().length() > 0) 
        && Converter.isEqualOrGreatThanToday(customer.getTax_exempt_expire())
        )
    {
        taxTables = new TaxTables();
    }
    
    if (request.getAttribute(Constants.ATTR_HOLD_DAYS) != null)
    {
        holdDays = Integer.parseInt(request.getAttribute(Constants.ATTR_HOLD_DAYS).toString());
        holdDays = holdDays - 1 ;
        cal.add(Calendar.DATE, holdDays);
    }
    
    Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
    
    Float stsubtotal_amt = new Float(0);
    Float stsum = new Float(0);
    Float lidisc_amnt = new Float(0);
    Float totalMhz = new Float(0);
    Float totalPriceMhz = new Float(0);
    Float warrantyTotal = new Float(0);
    int maxCheckout = 0;
    int itemWarranty = 0;
    Float percentTDis = new Float(0);
	
    Float shipCost = new Float(0);
    Float taxCost = new Float(0);
    Float orderSubtotal = new Float(0);
    Float orderSubtotal1 = new Float(0);
    Float orderTotal = new Float(0);
    Float listPrice = new Float(0);
    Float moneyByMhz = new Float(0);
    Float taxShip = new Float(0);
    Float taxOrder = new Float(0);
    int unitMhz = 0 ;
    Float Fee = new Float(0);
    String accountType = customer.getAccount_type();
	String shipToState = customer.getShip_to_state(); 
	int taxExempt = customer.getTax_exempt();
    
    if (request.getAttribute(Constants.ATTR_ITEM_BASKET) != null)
    {
        basketItemCheck = (List<EstoreBasketItem>) request.getAttribute(Constants.ATTR_ITEM_BASKET);
        
        for (EstoreBasketItem estoreBasketItem : basketItemCheck)
        {
            //stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getList_price();
            //stsum = stsum + estoreBasketItem.getPlaced_price();
            if (estoreBasketItem.getItem_sku().contains("WARRANTY")){
            	stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getPlaced_price();
           	 	warrantyTotal = warrantyTotal + (estoreBasketItem.getPlaced_price());
           	 	itemWarranty = itemWarranty + 1;
            }
            else
            	stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getList_price();
            
            stsum = stsum + estoreBasketItem.getPlaced_price();
            
            if(estoreBasketItem.getSpeed().floatValue() > 0)
            {
                totalMhz = totalMhz + estoreBasketItem.getSpeed();    
                moneyByMhz = moneyByMhz + estoreBasketItem.getPlaced_price().floatValue();
                unitMhz ++ ;
            }
        }
        totalPriceMhz = (totalMhz.floatValue() == 0 ) ? 0 :  (moneyByMhz / 100) / totalMhz;
        lidisc_amnt = (stsubtotal_amt - stsum)/100;
        percentTDis = lidisc_amnt / ((stsubtotal_amt - warrantyTotal) / 100);
        
        
      	       
        boolean chkShipTax = checkoutService.checkStateInShippingTax(customer.getShip_to_state());
        
        listPrice = stsubtotal_amt/100;
        orderSubtotal =  stsum/100;
        
        shipCost =  orderRow.getShip_cost();
        if(orderRow.getShip_method().equalsIgnoreCase("0") && (orderRow.getShip_terms().equalsIgnoreCase("Pallet Shipping / Item") || orderRow.getShip_terms().equalsIgnoreCase("Optional Ground / Item") || orderRow.getShip_terms().equalsIgnoreCase("Optional Next Day / Item") || orderRow.getShip_terms().equalsIgnoreCase("Optional 2 Days / Item")))
		{
        	shipCost = shipCost * (basketItemCheck.size() - itemWarranty);
		}
        
        orderSubtotal1 = orderSubtotal + shipCost;
        /*
        taxShip = shipCost.floatValue() / 100 * taxTables.getCombuTax().floatValue() ;
        taxOrder = orderSubtotal / 100 * taxTables.getCombuTax().floatValue() ;
        taxCost = taxShip + taxOrder ;
        */
        if(chkShipTax){
        	taxCost = (orderSubtotal + shipCost.floatValue()) * taxTables.getCombuTax().floatValue();
        }else{
        	taxCost = orderSubtotal * taxTables.getCombuTax().floatValue();
        }
        
        orderTotal =  orderSubtotal + shipCost + taxCost;
        
        
		
    }
  	//Get error List
    List<String> errorList = null;
    if(request.getAttribute(Constants.ATTR_LIST_ERROR)!= null)
    {
        errorList = (List<String>)request.getAttribute(Constants.ATTR_LIST_ERROR);
    }
%>



<%@page import="java.util.ArrayList"%><div style="padding-left: 10px">
	<img alt="Checkout - Payment" src="images/final_checkout.gif"> 
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

    if (basketItemCheck != null && customer != null && orderRow != null && eppPromotionRow != null)
    {
%>
<form id="Payment" name="Payment" onsubmit="return check_Payment(document.Payment)" 
					method="POST"
					action="checkout3.do?method=submitOrder"
					name="Payment">
					
<table width="760" cellspacing="0" cellpadding="3" border="0">
	<tbody>
		<tr>
			<td><font size="2" face="Arial, Helvetica"> Please verify
			we have your correct billing and shipping information. </font><br>
			<font size="2" face="Arial, Helvetica" color="#ff0000">(Important:
			Billing Address must match exactly as it appears on credit card
			statement used for this purchase) </font><br>
			<b>Order Number:</b> <%=orderRow.getOrder_id()%></td>
		</tr>
	</tbody>
</table>


<%
//get url edit customer
Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
String url = "shopper.do?method=prepareCheckout&shopper_new="+customer.getShopper_id()+"&shopas=shopas&lg=lg&section=checkout&shopper_name="+customer.getBill_to_fname() + " " + customer.getBill_to_lname()+"&agent_level="+agent.getUserLevel() ;
%>
<!--Information customer-->
<table width="760" cellspacing="0" cellpadding="2" border="1">
	<tbody>
		<tr>
			<th width="50%" bgcolor="#dbdbdb" align="LEFT"><font size="2"
				face="Arial, Helvetica">Billing Address &nbsp;&nbsp;<a
				href="<%=url + "#bill_to_fname" %>">Edit</a></font>
			</th>
			<th width="50%" bgcolor="#dbdbdb" align="LEFT"><font size="2"
				face="Arial, Helvetica">Shipping Address &nbsp;&nbsp;<a
				href="<%=url + "#ship_to_fname"%>">Edit</a></font>
			</th>
		</tr>
		<tr>
			<td valign="TOP">
				<font size="2" face="Arial, Helvetica">
				<%=Constants.collapseRowBR("<b>" + customer.getBill_to_fname() + " " + customer.getBill_to_lname() + "</b>", "")%>
				<%=Constants.collapseRowBR(customer.getBill_to_title(), "")%> 
				<%=Constants.collapseRowBR(customer.getBill_to_company(), "")%>
				<%=Constants.collapseRowBR(customer.getBill_to_address1(), "")%> 
				<%=Constants.collapseRowBR(customer.getBill_to_address2(), "")%>
				<%=Constants.collapseRowBR(customer.getBill_to_city() + ", " + customer.getBill_to_state() + ", " + customer.getBill_to_postal(), "")%>
				<%=Constants.collapseRowBR(customer.getBill_to_country(), "")%> 
				<%=Constants.collapseRowBR(customer.getBill_to_phone(), "TEL #:")%>
				<%=Constants.collapseRowBR(customer.getBill_to_phoneext(), "ext:")%>
				<%=Constants.collapseRowBR(customer.getBill_to_fax(), "FAX #:")%> 
				</font>
			</td>

			<td valign="TOP"><font size="2" face="Arial, Helvetica">
				<%=Constants.collapseRowBR("<b>" + customer.getShip_to_fname() + " " + customer.getShip_to_lname() + "</b>", "")%>
				<%=Constants.collapseRowBR(customer.getShip_to_title(), "")%> 
				<%=Constants.collapseRowBR(customer.getShip_to_company(), "")%>
				<%=Constants.collapseRowBR(customer.getShip_to_address1(), "")%> 
				<%=Constants.collapseRowBR(customer.getShip_to_address2(), "")%>
				<%=Constants.collapseRowBR(customer.getShip_to_city() + ", " + customer.getShip_to_state() + ", " + customer.getShip_to_postal(), "")%>
				<%=Constants.collapseRowBR(customer.getShip_to_country(), "")%> 
				<%=Constants.collapseRowBR(customer.getShip_to_phone(), "TEL #:")%>
				<%=Constants.collapseRowBR(customer.getShip_to_phoneext(), "ext:")%>
			</font></td>
		</tr>
	</tbody>
</table>

<%/*
	for (EstoreBasketItem estoreBasketItem : basketItemCheck)
	{
		if(estoreBasketItem.getCategory_id() == 11946 || estoreBasketItem.getCategory_id() == 11955 )
		{
			if(estoreBasketItem.getScreenSize() > 15 ){
				Fee = Fee + 8;
			}
			else if(estoreBasketItem.getScreenSize() < 15 )
			{
				Fee = Fee + 6;
			}
		}	 
	 }*/	
%> 

<!--Display Product-->
<table width="760" cellspacing="0" cellpadding="2" border="0">
	<tbody>
		<tr>
			<td nowrap="nowrap" colspan="2"><font size="3" style=""><b><i>
			Order Totals</i></b></font></td>
		</tr>
		<tr>
			<th>&nbsp;</th>
			<th align="LEFT">
				<font size="2" face="Arial, Helvetica">Product Number</font>
			</th>
			<th align="LEFT">
				<font size="2" face="Arial, Helvetica">Chassis Color</font>
			</th>
			<th>&nbsp;</th>
			<th align="LEFT">
				<font size="2" face="Arial, Helvetica">Product Description</font>
			</th>
			<%
			if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
			{
			%>
			<th>&nbsp;</th>
			<th align="RIGHT">
				<font size="2" face="Arial, Helvetica">Price</font>
			</th>
			
			<th>&nbsp;</th>
			<th align="RIGHT">
				<font size="2" face="Arial, Helvetica">Discount Amount</font>
			</th>
			<%
			}
			if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
			{
			%>
			<th>&nbsp;</th>
			<th align="RIGHT">
				<font size="2" face="Arial, Helvetica">$/Mhz</font>
			</th>
			<%
			}
			%>
			<th>&nbsp;</th>
			
			<th align="RIGHT">
				<% 
				if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
				{
				%>
				<font size="2" face="Arial, Helvetica">Discounted Price</font>
				<%    
				}else
				{
				%>
				<font size="2" face="Arial, Helvetica">Price</font>
				<%    
				}
				%>
			</th >
			<!-- TienDang -->	
			<th align="RIGHT">
				<%
					if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0)
					{
					%>
					<font size="2" face="Arial, Helvetica">Environmental Fee</font>
					<%	
					}
				%>
			</th>
			
		</tr>

		<%
		    	int count_idx = 1;
		        for (EstoreBasketItem estoreBasketItem : basketItemCheck)
		        {
		            
		%>

		<tr>

			<td align="LEFT">
				<font size="1" face="Arial, Helvetica"><b><%=count_idx%></b></font>
			</td>
			<td>
				<font size="1" face="Arial, Helvetica"><%=estoreBasketItem.getItem_sku()%></font>
			</td>
		
			<%if(estoreBasketItem.getCategory_id()==11946 || estoreBasketItem.getCategory_id()==11947 || estoreBasketItem.getCategory_id()==11949){%>
				<td>
					<font size="1" face="Arial, Helvetica"><%=estoreBasketItem.getAttribute05()%></font>
				</td>
			<%}else{%>
				<td>
					<font size="1" face="Arial, Helvetica"></font>
				</td>
			<%} %>
		
			<td>&nbsp;</td>
			<td align="LEFT">
				<font size="1" face="Arial, Helvetica"><%=estoreBasketItem.getName()%> </font>
			</td>
			
			<%
			if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
			{
			%>
			<td>&nbsp;</td>
			<%if (estoreBasketItem.getCategory_id() == 11940 || estoreBasketItem.getCategory_id() == 11941){ %>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica">
					<%=Constants.FormatCurrency(estoreBasketItem.getPlaced_price()/100) %>
				</font>
			</td>
			<td>&nbsp;</td>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica">
					<%=Constants.FormatCurrency(new Float((estoreBasketItem.getPlaced_price() - estoreBasketItem.getPlaced_price()) / 100))%>
				</font>
			</td>
			<%}else {%>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica">
					<%=Constants.FormatCurrency(estoreBasketItem.getList_price()/100) %>
				</font>
			</td>
			<td>&nbsp;</td>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica">
					<%=Constants.FormatCurrency(new Float((estoreBasketItem.getList_price() - estoreBasketItem.getPlaced_price()) / 100))%>
				</font>
			</td>
			<%} %>
			<% 
			}
			%>
			<%
 			if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) 
			{
			    String money_mhz = "";
				if(estoreBasketItem.getSpeed().floatValue() == new Float(0)) 
				{
				    money_mhz = "0" ;
				}else
				{
				    money_mhz = Constants.FormatMhz((estoreBasketItem.getPlaced_price() / 100) / estoreBasketItem.getSpeed());
				}
			%>
			<td>&nbsp;</td>
			<td align="RIGHT"><font size="1" face="Arial, Helvetica">
				$<%=money_mhz %></font>
			</td>
			<%
			}
			%>
			<td>&nbsp;</td>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica">
					<%=Constants.FormatCurrency(new Float(estoreBasketItem.getPlaced_price() / 100))%>
				</font>
			</td>
			
			<td align="RIGHT">
				<%
				if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0)
				{
					float att12 =0;
					float valAtt12=1;
					boolean bl;
							if(estoreBasketItem.getCategory_id() == 11946)
							{
									
								if(estoreBasketItem.getScreenSize() >= 35 )
								{
									Fee = Fee + 5;
								%>
								<font size="1" face="Arial, Helvetica">
									<%= Constants.FormatCurrency(Float.valueOf("5")) %> 
								</font>
								<%	
								}	
								else if(estoreBasketItem.getScreenSize() >= 15 )
								{
										Fee = Fee + 4;
									%>
									<font size="1" face="Arial, Helvetica">
										<%= Constants.FormatCurrency(Float.valueOf("4")) %> 
									</font>
									<%	
								}
								else if(estoreBasketItem.getScreenSize() < 15 )
								{
										Fee = Fee + 3;
										%>
											<font size="1" face="Arial, Helvetica">
												<%= Constants.FormatCurrency(Float.valueOf("3")) %>
											</font>
										<%
								}
							}
							if( estoreBasketItem.getCategory_id() == 11955 )
							{
								try{
									att12 = Float.parseFloat(estoreBasketItem.getAttribute12());
									bl = true;	
									//System.out.println("attribute12 : "+att12);
								}catch(Exception e){
									bl = false;
								}
								
								if (bl==true){
									if(att12>0)
									{
										valAtt12 = att12;
									}
									//System.out.println("value  : "+valAtt12);
								}else {
									valAtt12 = 1;
									//System.out.println("Test :"+att12);
								}
			
								if(estoreBasketItem.getScreenSize() >= 35 )
								{
									Fee = Fee +( 5 * valAtt12);
								%>
								<font size="1" face="Arial, Helvetica">
									<%= Constants.FormatCurrency(Float.valueOf("5")) %> * <%=valAtt12 %>
								</font>
								<%	
								}
								else if(estoreBasketItem.getScreenSize() >= 15 )
								{
									Fee = Fee +( 4 * valAtt12);
								%>
								<font size="1" face="Arial, Helvetica">
									<%= Constants.FormatCurrency(Float.valueOf("4")) %> * <%=valAtt12 %>
								</font>
								<%	
								}
								else if(estoreBasketItem.getScreenSize() < 15 )
								{
									Fee = Fee + (3 * valAtt12);
									%>
											<font size="1" face="Arial, Helvetica">
												<%= Constants.FormatCurrency(Float.valueOf("3")) %> * <%=valAtt12 %>
											</font>
									<%
								}
							}
				}
				%>
			</td>
		</tr>
		<%
		    	count_idx++;
		        }
		%>

		<tr height="1px">
			<td colspan="5">
			</td>
			<td>
			</td>
			<%
			if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
			{
			%>
			<td valign="TOP" align="RIGHT">
				<img width="60%" height="1px" src="images/dot_black.gif">
			</td>
			<td height="1">
			</td>
			
			<td valign="TOP" align="RIGHT">
				<img width="100%" height="1px" src="images/dot_black.gif">
			</td>
			<td height="1"></td>
			<%
			}
			if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) 
			{
			%>
			<td valign="TOP" align="RIGHT">
				<img width="100%" height="1px" src="images/dot_black.gif">
			</td>
			<td height="1"></td>
			<%
			} 
			%>
			<td valign="TOP" align="RIGHT">
				<img width="100%" height="1px" src="images/dot_black.gif">
			</td>
			<%
			if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0){
			%>
			<td valign="TOP" align="RIGHT">
				<img width="100%" height="1px" src="images/dot_black.gif">
			</td>
			<%
			}
			%>
		</tr>

		<tr>
		<td>
		</td>
			<td colspan="5">&nbsp;</td>
			<%
			if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
			{
			%>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica"><%=Constants.FormatCurrency(listPrice)%></font>
			</td>
			<td>&nbsp;</td>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica"><%=Constants.FormatCurrency(lidisc_amnt)%></font>
			</td>
			<td>&nbsp;</td>
			<%}
			if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) 
			{
			%>
			<td align="right">
				<font size="1" face="Arial, Helvetica">$<%=Constants.FormatMhz(totalPriceMhz)%></font>
			</td>
			
			<td>&nbsp;</td>
			<%
			} 
			%>
			<td align="RIGHT">
				<font size="1" face="Arial, Helvetica"><%=Constants.FormatCurrency(orderSubtotal)%></font>
			</td>
			
			<%
			if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0){
			%>
			<td align="RIGHT">
				
				<font size="1" face="Arial, Helvetica"><%=Constants.FormatCurrency(Fee)%></font>
			</td>
			<%
			}
			%>
			
		</tr>

	</tbody>
</table>





<table width="760" cellspacing="0" cellpadding="4" border="0">
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td width="25%" align="right" valign="TOP" >
			<table width="100%" cellspacing="0" cellpadding="2" border="2" >
				<tbody>
					<tr>
						<th width="40%" align="RIGHT"><font size="2"
							face="Arial, Helvetica">Order Subtotal:</font></th>
						<td width="65%" align="RIGHT"><font size="2"
							face="Arial, Helvetica"><%=Constants.FormatCurrency(orderSubtotal)%>
						</font></td>
					</tr>


					<!-- Display tiered discount-->

					<!-- Display volume discount-->

					<tr>
						<th nowrap="nowrap" align="RIGHT"><font size="2"
							face="Arial, Helvetica"> <a href="checkout2.do">Edit</a>
						Shipping & Handling: <br>
						<%=checkoutService.getShortShipping(orderRow.getShip_method(),orderRow.getShip_terms()) %>
						</font></th>
						<td align="RIGHT">
						<font size="2" face="Arial, Helvetica">
						<%
							//get price ship cost
							if(Constants.isNumber(orderRow.getShip_method()))
							{
							    if(Integer.parseInt(orderRow.getShip_method()) == 6 )
								{
								    out.println("<font color='red'>FREE</font>");
								}else
								{
								   out.println(Constants.FormatCurrency(shipCost));
								}
							}
							
							%> 
						</font>						</td>
					</tr>

					<tr>
						<th align="RIGHT">
							<font size="2" face="Arial, Helvetica"> Order Subtotal: </font>						</th>
						<td align="RIGHT"><font size="2" face="Arial, Helvetica">
						<%=  Constants.FormatCurrency(orderSubtotal1)  %>
						 </font></td>
					</tr>
					<tr>
						<th align="RIGHT"><font size="2" face="Arial, Helvetica">Tax:
						</font></th>
						<td align="RIGHT"><font size="2" face="Arial, Helvetica">
						<%= Constants.FormatCurrency(taxCost)	%>
						</font></td>
					</tr>
					<%
					if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0)
					{
					%>
					<tr>
						<th align="RIGHT"><font size="2" face="Arial, Helvetica">Environmental Fee:
						</font></th>
						<td align="RIGHT"><font size="2" face="Arial, Helvetica">
						<%= Constants.FormatCurrency(Fee) %>
						</font></td>
					</tr>
					<%
					}
					%>	
					<tr>
						<th align="RIGHT"><font size="2" face="Arial, Helvetica">Order
						Total:</font></th>
						<td align="RIGHT"><font size="2" face="Arial, Helvetica">
						
						<b><%= Constants.FormatCurrency(orderTotal + Fee) %></b>
			
						</font></td>
					</tr>
				</tbody>
			</table>			</td>
		</tr>
		<tr>
		  <td>&nbsp;</td>
	      <td width="25%" align="right" valign="top"  >
	      	<b> <a href="javascript:void(0)" onclick="openwindow('checkout3.do?method=exportExcel');">Export to excel</a></b> 
	      </td>
	  </tr>

		<tr>
			<td width="75%" valign="TOP" colspan="2"><br>

		
				<font size="3" face="Arial, Helvetica">
				<b>Payment Information</b>				</font>
				<br>
			<p>
			<table border="0">
				<tbody>
					<tr>
						<td>
						<table border="0">
							<tbody>
								<tr>
									<th align="RIGHT"><font size="2" face="Arial, Helvetica">Payment
									Method:</font></th>
									<td colspan="2">
										<font size="2" face="Arial, Helvetica">
											<select name="cc_type" id="cc_type">
												<option value="">Choose Payment Method</option>
												<% 
												
												String isCreditCard = "";
												if(request.getAttribute(Constants.ATTR_PAY_METHODS)!=null){
												    List<PayMethods> listPayMethods  = (List<PayMethods>)request.getAttribute(Constants.ATTR_PAY_METHODS);
												    for(PayMethods payMethods : listPayMethods)
												    {
												        isCreditCard += payMethods.getIsCreditCard() + ",";
												        if(isCustomer != null && payMethods.getIsCreditCard() == 0)
												        {
												            continue ;   
												        }
												        String strSelected = "";
												        if(orderReceipt.getCc_type() != null && orderReceipt.getCc_type().equals(payMethods.getName()))
												        {
												            strSelected = "selected" ;
												        }
												        %>
														<option value="<%=payMethods.getName()%>" <%=strSelected %> > <%= payMethods.getName() %></option>
														<%
												    }
												}
												%>
											</select> 
										</font> 
										<input type="hidden" name="payment_type_selected" id="payment_type_selected"  value="0"> 
										<input type="hidden" name="payment_type" id="payment_type" value="-1,<%=isCreditCard %>,-1" >							
										
										<input type="hidden" name="ListPricetotal" id="ListPricetotal" value="<%=listPrice%>" >
										<input type="hidden" name="OrderSubtotal" id="OrderSubtotal" value="<%=orderSubtotal%>" >
										<input type="hidden" name="ShipCost" id="ShipCost" value="<%=shipCost%>" >
										<input type="hidden" name="TaxCost" id="TaxCost" value="<%=taxCost%>" >
										<input type="hidden" name="OrderTotal" id="OrderTotal" value="<%=orderTotal + Fee%>" >
										<input type="hidden" name="OrderNumber" id="OrderNumber" value="<%=orderRow.getOrder_id()%>" >
										<input type="hidden" name="Shopper_id" id="Shopper_id" value="<%=customer.getShopper_id()%>" >
										<input type="hidden" name="ZipCode" id="ZipCode" value="<%=customer.getShip_to_postal()%>" >
										<input type="hidden" name="AvgMhz" id=AvgMhz" value="<%= "$" + Constants.FormatMhz(totalPriceMhz) %>" >
										<input type="hidden" name="UnitMhz" id="UnitMhz" value="<%= unitMhz %>" >
										<input type="hidden" name="SpeedTotal" id="SpeedTotal" value="<%= totalMhz %>" >
										<input type="hidden" name="Fee" id="Fee" value="<%= Fee %>" >
										<input type="hidden" name="WarrantyTotal" id="WarrantyTotal" value="<%= warrantyTotal / 100 %>" >
										<%-- <input type="hidden" name="CUSTOMER_CHECKOUT_SESSION" id="CUSTOMER_CHECKOUT_SESSION" value="<%= customer %>" > --%>
									</td>
								</tr>

								<tr>
									<th align="RIGHT"><font size="2" face="Arial, Helvetica">Payment Information:</font></th>
									<td colspan="2">
										<input type="text" size="20" value="<%= (orderReceipt.getPayment_terms()!= null && orderReceipt.getPayment_terms().equals("Credit")) ?  "" : (request.getAttribute("TMP_PAYMENT_INFO") != null)?request.getAttribute("TMP_PAYMENT_INFO"):Constants.convertValueEmpty(orderReceipt.getPayment_terms())%>" maxlength="20" name="terms" id="terms">
									</td>
								</tr>

								<tr>
									<th align="RIGHT"><font size="2" face="Arial, Helvetica">Name
									On Card:</font></th>
									<td colspan="2">
										<input type="text" size="20" value="<%= Constants.convertValueEmpty(orderReceipt.getCc_name()) %>" maxlength="60" name="cc_name" id="cc_name">
									</td>
								</tr>
								<tr>
									<th align="RIGHT">
										<font size="2" face="Arial, Helvetica">Card	Number:</font></th>
									<td colspan="2">
										<input type="text" size="20" value="<%= Constants.convertValueEmpty(orderReceipt.getCc_number()) %>" maxlength="19" name="_cc_number" id="_cc_number">									</td>
								</tr>
								<tr>
									<th align="RIGHT"><font size="2" face="Arial, Helvetica">CSC:</font></th>
									<td>
										<input type="text" size="2" value="<%= (request.getAttribute("TMP_CSC") == null)?"":request.getAttribute("TMP_CSC") %>" maxlength="4" name="_cc_cvv2" id="_cc_cvv2">									</td>
								<td align="left">
										<font size="1" color="#0000ff"><b>
										<a onclick=showRemote(); href="#">CSC Number Help</a></b>									
										</font>									
									</td>
								</tr>
								<tr>
									<th align="RIGHT"><font size="2" face="Arial, Helvetica">Expiration:</font></th>
									<td>
										<font size="2" face="Arial, Helvetica"> <%= Constants.getOptionMonth(orderReceipt.getCc_expmonth()) %></font>
									</td>
									<td>
										<font size="2" face="Arial, Helvetica"> <%= Constants.getOptionYear(orderReceipt.getCc_expyear())%></font>
									</td>
								</tr>
							</tbody>
						</table>						</td>
						<td valign="bottom"></td>
					</tr>
				</tbody>
			</table>
			</p>			</td>
		</tr>
	</tbody>
</table>
<br>
<input type="hidden" id="iCus" value="<%=isCustomer %>">
<%
if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
{
%>
<font size="3" color="#fe0000">
	<b>EXPORT COMPLIANCE:</b>
</font>
<br>
<br>
<font size="2" face="Arial, Helvetica">Dell Financial Services L.P. considers compliance with Export Compliance policy and<br>
procedures very important. Remember, it is each sales associate's responsibility to<br>
identify export intent, end user and ultimate destination. Prior to submitting sale make<br>
sure to ask the customer the following:</font>

 <table width="90%" cellspacing="1" cellpadding="1" border="0">
              <tbody><tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left">1. </td>
                <td width="99%" valign="top" align="left"><font size="2">Does the customer plan to export this equipment?</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left"></td>
                <td width="99%" valign="top" align="left"><font size="2">&nbsp;a) If so, what country will the equipment be exported to?</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left">2.</td>
                <td width="99%" valign="top" align="left"><font size="2">Will the equipment be used for the following (Domestic or International):</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left"></td>
                <td width="99%" valign="top" align="left"><font size="2">&nbsp;a) Military</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left"></td>
                <td width="99%" valign="top" align="left"><font size="2">&nbsp;b) Organizations or companies involved in Chemical or Biological activities</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left"></td>
                <td width="99%" valign="top" align="left"><font size="2">&nbsp;c) Mining operations</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left"></td>
                <td width="99%" valign="top" align="left"><font size="2">&nbsp;d) Fertilizer manufacturing facilities</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left"></td>
                <td width="99%" valign="top" align="left"><font size="2">&nbsp;e) Pharmaceutical labs</font></td>
              </tr>
              <tr> 
                <td width="20" valign="top" align="left"></td>
                <td width="15" valign="top" align="left">3.</td>
                <td width="99%" valign="top" align="left"><font size="2">Is the equipment being shipped to a freight forwarder, hotel, or airport?</font></td>
              </tr>
           </tbody></table>
<br>
<table width="760">
<tbody><tr>
	<td><font size="2" face="Arial, Helvetica">
If you answered yes to any of the above, place the order on hold and forward the information to DFS Export Compliance, <a href="mailto:us_dfs_eol_export_compliance@dell.com">us_dfs_eol_export_compliance@dell.com</a>, for review.
You will receive notification once the order can be released.</font></td>
</tr>
</tbody></table>
<%
}
else
{
%>
<table width="760">
<tbody><tr>
	<tr></tr><td><input type="checkbox" id="chk"> <font size="2" face="Arial, Helvetica" color="#0085C5">  I agree to the terms and conditions of sale</font></input> </td></tr>
	<tr><td></td></tr>
	<td><font size="2" face="Arial, Helvetica">
Check this box to indicate that you have read and agree to be bound by the <a href="http://www.dfsdirectsales.com/dfsdirectsales/ctl641/sitecontent/TermsAndConditions/TermsAndConditions">Terms and Conditions of Sale</a>, then click "Submit Order". Additionally, by clicking on the "Submit Order" button, I represent that this purchase is not intended for export. If purchases are intended for export, please contact Dell Financial Services LLC at 1.866.417.3355 and speak with a sales agent.
</font></td>
</tr>
</tbody></table>
<%
}
%>
<br>


<!--Expiration hold date-->
<input type="hidden" id="expCurrentDate" name="expCurrentDate" value ="<%= Constants.formatDate(new Timestamp(Calendar.getInstance().getTime().getTime()), "MM/dd/yyyy") %>" />
<input type="hidden" id="expHoldDateSystem" name="expHoldDateSystem" value ="<%= Constants.formatDate(new Timestamp(cal.getTime().getTime()), "MM/dd/yyyy") %>" />

<%				    
if(request.getAttribute(Constants.ATTR_HOLD_DAYS) != null && isCustomer == null)
{
%>
	<input type="hidden" id="expHoldDate" name="expHoldDate" value ="<%= Constants.formatDate(new Timestamp(cal.getTime().getTime()), "MM/dd/yyyy") %>" />
<%    
}else
if(request.getAttribute(Constants.ATTR_HOLD_DAYS) != null && isCustomer != null)
{
%>
	<b>Hold expires after</b> : <%= Constants.formatDate(new Timestamp(cal.getTime().getTime()), "MM/dd/yyyy") %>
	<input type="hidden" id="expHoldDate" name="expHoldDate" value ="<%= Constants.formatDate(new Timestamp(cal.getTime().getTime()), "MM/dd/yyyy") %>" />
<%
}	
%>
<table width="500" cellspacing="0" cellpadding="0" border="0" >
    <tbody><tr>
        <td width="25%" valign="TOP" align="CENTER">
			<font size="2" face="Arial, Helvetica">
				<a href="javascript:submitHeld();"><img width="15" height="15" border="0" src="images/item.gif">&nbsp;
				Hold Order
				</a>
			</font>
        </td>
        <td width="25%" valign="TOP" align="CENTER">
			<font size="2" face="Arial, Helvetica">
				<a href="javascript:submitPayment();">
					<img width="15" height="15" border="0" src="images/item.gif"  >&nbsp;
					Submit Order
				</a>
			</font>
			
        </td>
        <td width="25%" valign="TOP" align="CENTER">
		
			<font size="2" face="Arial, Helvetica">
				<a href="showBasket.do?method=actionBasket&type=cancel" onclick="dialogOpen();"><img width="15" height="15" border="0" src="images/item.gif">&nbsp;
				Cancel Order
				</a>
			</font>
		
        </td>
    </tr>
</tbody></table>

</form>
<%@include file="/html/scripts/checkout3Script.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>
	<%
    }
    else
    {
	%>
	<blockquote>
		<font size="2" face="Arial, Helvetica">
			<strong>Your cart is empty.</strong>
		</font>
	</blockquote>

	<%
    }
	%>
</div>


