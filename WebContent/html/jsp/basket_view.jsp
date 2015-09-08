
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.ShopperViewBasket"%>
<%@page import="com.dell.enterprise.agenttool.model.DiscountAdjustment"%>

<%
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);

String shopperid= (String)request.getAttribute(Constants.SHOPPER_VIEW_BASKET_SHOPPER_ID);
ShopperViewReceipts  shopperViewReceipts=(ShopperViewReceipts)request.getAttribute(Constants.SHOPPER_INFO) ;
String orderId="";
DiscountAdjustment orders =(DiscountAdjustment) request.getAttribute(Constants.LIST_ORDER_LIST_DISCOUNT);
int expirationDate=orders.getExpirationdate();

if(request.getAttribute(Constants.SHOPPER_VIEW_BASKET_ORDER_ID)!=null)
{
orderId=(String) request.getAttribute(Constants.SHOPPER_VIEW_BASKET_ORDER_ID);
}

String typeCancel = "agent" ;
String typeFunction = "getViewBasketAgent" ;
if(request.getAttribute(Constants.ATTR_BASKET_ALL) != null)
{
    typeCancel = "";
    typeFunction = "getViewBasket" ;
}else
if(request.getAttribute(Constants.ATTR_CUSTOMER) != null)
{
    if((Boolean)request.getAttribute(Constants.ATTR_CUSTOMER))
    {
        typeCancel = "customer";
        typeFunction = "getViewBasketCustomer";
    }
}
%>

<script>
		 $('#pageTitle').html("Shopper - <%=shopperViewReceipts.getShip_to_fname()%> <%=shopperViewReceipts.getShip_to_lname()%> (<%=shopperViewReceipts.getShopper_number()%>)");
		 $('#topTitle').html("Shopper - <%=shopperViewReceipts.getShip_to_fname()%> <%=shopperViewReceipts.getShip_to_lname()%> (<%=shopperViewReceipts.getShopper_number()%>)");
</script>
&nbsp;

<%@page import="com.dell.enterprise.agenttool.model.ShopperViewReceipts"%><A
	HREF="shopper_manager.do?method=getShopperDetails&shopperId=<%=shopperid%>"><font
	color="red"><u>View Shopper</u></font> </A>
&nbsp;|&nbsp;
<a
	href="authenticate.do?method=shoppingAs&shopper_id=<%=shopperViewReceipts.getShopper_id() %>&shopper_name=<%=shopperViewReceipts.getShip_to_fname()%> <%=shopperViewReceipts.getShip_to_lname()%>"><font
	color="red"><u>Shop As Shopper</u></font></a>
	
<%if(userLevel!=null && !userLevel.equals("C")){ %>
&nbsp;|&nbsp;
<% if (shopperViewReceipts.getAgent_id() == 0) { %>
<a href="#" onclick="alert('This customer does not belong to Agent Sale Type');"><font
	color="red"><u>Checkout</u></font></a>
<% } else { %>
<a
	href="authenticate.do?method=checkoutAsShopper&shopper_id=<%=shopperViewReceipts.getShopper_id() %>&shopper_name=<%=shopperViewReceipts.getShip_to_fname()%> <%=shopperViewReceipts.getShip_to_lname()%>"><font
	color="red"><u>Checkout</u></font></a>
<%} %>
&nbsp;|&nbsp;
<a
	href="shopper_manager.do?method=<%= typeFunction %>&mscssid=<%= shopperid %>&cancel=cancel&typeCancel=<%=typeCancel%>"><font
	color="red"><u>Cancel Order</u></font></a>

<%} %>

<br>

<P>

<TABLE width="600">
	<tr>
		<TH VALIGN=BOTTOM ALIGN=LEFT COLSPAN=4>Cart Contents</TH>
	</tr>
	<% 
         	if (request.getAttribute(Constants.SHOPPER_VIEW_BASKET)!= null){         		   
         	   List<ShopperViewBasket> listShopperViewBasket=(List<ShopperViewBasket>)request.getAttribute(Constants.SHOPPER_VIEW_BASKET);
         	   if(listShopperViewBasket.size()>0){            	      
         	       %>
	<tr>
		<TD VALIGN=BOTTOM ALIGN=LEFT><b>Product<br>
		Number</b></TD>
		<TD VALIGN=BOTTOM ALIGN=LEFT><b>Product Name</b></TD>
		<TD VALIGN=BOTTOM ALIGN=right NOWRAP><b>Unit Price</b></TD>
		<TD VALIGN=BOTTOM ALIGN=right NOWRAP><b>Price/Mhz</b></TD>
		<tr>
			<tr>
				<TD COLSPAN=4 HEIGHT=1 BGCOLOR="black"></TD>
			</tr>
			<% for(ShopperViewBasket shopper : listShopperViewBasket) {  %>

			<tr>
				<TD VALIGN=TOP ALIGN=LEFT><%=shopper.getItem_sku() %></TD>
				<TD VALIGN=TOP ALIGN=LEFT><%=shopper.getName() %></TD>
				<TD VALIGN=TOP ALIGN=LEFT><%=Constants.FormatCurrency(shopper.getPlaced_price()/100) %></TD>

				<%if(shopper.getSpeed()>0){
   		            %>
				<td valign="top" align="right"><%=Constants.FormatMhz(shopper.getPlaced_price()/100/shopper.getSpeed()) %></td>

				<%      }
   		        else{
   		            %>
				<td valign="top" align="right">N/A</td>
			</tr>
			<% }       
            }
   		    %>
			<tr>
				<TD COLSPAN=4 HEIGHT=1 BGCOLOR="black"></TD>
			</tr>
			<%  }            		
         	   
            else{
                %>
			<tr>
				<TD></TD>
				<TD COLSPAN=2><I>Cart is empty</I></TD>
			</tr>
			<% }	}%>
		
</TABLE>

<TABLE width="600">
	<tr>
		<TH VALIGN=BOTTOM ALIGN=LEFT COLSPAN=3>Held Orders</TH>
	</tr>
	<%if(request.getAttribute(Constants.SHOPPER_VIEW_BASKET_DETAIL)!=null){
	    List<ShopperViewBasket> list= (List<ShopperViewBasket>) request.getAttribute(Constants.SHOPPER_VIEW_BASKET_DETAIL);	 	
	  	if(list.size()>0){	   
	        if(!orderId.equals("")){               
			  for(int i=1;i<=2;i++){
			      for(ShopperViewBasket viewResult : list) { 	   
			        if((i==1)&&(viewResult.getOrder_id().equals(orderId))){				          
	    %>
	    <tr>
	<TD colspan="2"><B>Order #</B> <%= viewResult.getOrder_id() %></TD>
	
	<%
	if(userLevel!=null && !userLevel.equals("C"))
	{ 
	%>
	
	<TD valign="bottom" align="right" colspan="2" >
		<a href="shopper_manager.do?method=<%= typeFunction %>&mscssid=<%= viewResult.getShopper_id() %>&held=<%=viewResult.getHeldOrder()%>&orderId=<%= viewResult.getOrder_id() %>">
			<font color="red"><u>Move To Cart</u></font>
		</a>
	</TD>
	 </tr>
	<%
	} 
	%>	
	<%if( viewResult.getUserHold() == null || viewResult.getUserHold().isEmpty()){ %>
	<tr>		
		<tr>
			<td colspan="2">Expiration Date 
			<%if (session.getAttribute(Constants.IS_CUSTOMER) != null ){ %>
			<%=viewResult.getExpirationdate() %> 
			<%
			}else
			{
			    %> <input type="text" class="datepicker"
				value="<%=viewResult.getExpirationdate() %>" readonly="readonly" />				
			<%if(userLevel!=null && !userLevel.equals("C")){ %>
			<input type="button" value="SET"
				onclick="setExpirationDay(this,'<%= viewResult.getOrder_id()%>','<%= shopperid %>','<%=expirationDate %>','<%=viewResult.getExpirationdate() %>');" />
			<%} %>
			</td>
		</tr>

		<%}}%>
		<tr>
			<TD VALIGN=BOTTOM ALIGN=LEFT><b>Product<br>
			Number</b></TD>
			<TD VALIGN=BOTTOM ALIGN=LEFT><b>Product Name</b></TD>
			<TD VALIGN=BOTTOM ALIGN=right NOWRAP><b>Unit Price</b></TD>
			<TD VALIGN=BOTTOM ALIGN=right NOWRAP><b>Price/Mhz</b></TD>
			<tr>
				<tr>
					<TD COLSPAN=9 HEIGHT=1 BGCOLOR="black"></TD>
				</tr>
				<%
	   			List<ShopperViewBasket> listInside=(List<ShopperViewBasket>)viewResult.getListShopperViewBasket();			
						for(ShopperViewBasket listinside : listInside){
							 
					   		%>
				<tr>
					<TD VALIGN=TOP ALIGN=LEFT><%=listinside.getItem_sku() %></TD>
					<TD VALIGN=TOP ALIGN=LEFT><%= listinside.getName() %></TD>
					<TD VALIGN=TOP ALIGN=RIGHT><%= Constants.FormatCurrency(listinside.getPlaced_price()/100) %></TD>
					<%if(listinside.getSpeed()>0){
					   		            %>
					<td valign="top" align="right"><%=Constants.FormatMhz(listinside.getPlaced_price()/100/listinside.getSpeed()) %></td>
							<% System.out.println("In page basket view : "+listinside.getSpeed()); %>
					<%      }
					   		        else{
					   		            %>
					<td valign="top" align="right">N/A</td>
				</tr>
				<% }       	
						             } %>
				<tr>
					<TD COLSPAN=4 HEIGHT=1 BGCOLOR="black"></TD>
				</tr>
				<tr>
					<TD COLSPAN=3><br>
					</TD>
				</tr>


				<%	   			
			        }}
			      for(ShopperViewBasket viewResult : list) { 	 
			       if(((i!=1)&&(!viewResult.getOrder_id().equals(orderId)))){               
		    %>
				<tr>
					<TD colspan="2"><B>Order #</B> <%= viewResult.getOrder_id() %></TD>
					<%
					if(userLevel!=null )
					{ 
					%>
					<td VALIGN=BOTTOM ALIGN=RIGHT colspan="2">
					<a
						href="shopper_manager.do?method=<%= typeFunction %>&mscssid=<%= viewResult.getShopper_id() %>&held=<%=viewResult.getHeldOrder()%>&orderId=<%= viewResult.getOrder_id() %>"><font
						color="red"><u>Move To Cart</u></font></a>
					</td>
					<%
					} 
					%>
				</tr>
				<%if(viewResult.getUserHold() == null ||viewResult.getUserHold().isEmpty()){ %>
				<tr>
					<td colspan="2">Expiration Date <%if (session.getAttribute(Constants.IS_CUSTOMER) != null){ %>
					<%=viewResult.getExpirationdate() %> <%}else{
			   %> <input type="text" class="datepicker"
						value="<%=viewResult.getExpirationdate() %>" readonly="readonly" />
				<%if(userLevel!=null && !userLevel.equals("B")){ %>
				<input
						type="button" value="SET"
						onclick="setExpirationDay(this,'<%= viewResult.getOrder_id()%>','<%= shopperid %>','<%=expirationDate %>','<%=viewResult.getExpirationdate() %>');" />
				<%} }%>
				</td>
				</tr>


				<% }%>
				<tr>

					<tr>
						<td VALIGN=BOTTOM ALIGN=LEFT><b>Product<br>
						Number</b></td>
						<td VALIGN=BOTTOM ALIGN=LEFT><b>Product Name</b></td>
						<td VALIGN=BOTTOM ALIGN=right NOWRAP><b>Unit Price</b></td>
						<td VALIGN=BOTTOM ALIGN=right NOWRAP><b>Price/Mhz</b></td>
						<tr>
							<tr>
								<td COLSPAN=9 HEIGHT=1 BGCOLOR="black"></td>
							</tr>
							<%
   			List<ShopperViewBasket> listInside=(List<ShopperViewBasket>)viewResult.getListShopperViewBasket();			
					for(ShopperViewBasket listinside : listInside){
					    
				   		%>
							<tr>
								<td VALIGN=TOP ALIGN=LEFT><%=listinside.getItem_sku() %></td>
								<td VALIGN=TOP ALIGN=LEFT><%= listinside.getName() %></td>
								<td VALIGN=TOP ALIGN=RIGHT><%= Constants.FormatCurrency(listinside.getPlaced_price()/100) %></td>
								<%if(listinside.getSpeed()>0){
					   		            %>
								<td valign="top" align="right"><%=Constants.FormatMhz(listinside.getPlaced_price()/100/listinside.getSpeed()) %></td>
										<% System.out.println("In page basket view : "+listinside.getSpeed()); %>
								<%      }
					   		        else{
					   		            %>
								<td valign="top" align="right">N/A</td>
							</tr>
							<% }       		
					             } %>
							<tr>
								<td COLSPAN=4 HEIGHT=1 BGCOLOR="black"></td>
							</tr>
							<tr>
								<td COLSPAN=3><br>
								</td>
							</tr>

							<%       }
			      
			}}}
	        else{
	            for(ShopperViewBasket viewResult : list) { 
	            %>
							<tr>
								<TD COLSPAN="2"><B>Order # </B><%= viewResult.getOrder_id() %></TD>
								<% 
								if(userLevel!=null )
								{ 
								%>
								<td VALIGN=BOTTOM ALIGN=RIGHT nowrap="nowrap" colspan="2">
									<a href="shopper_manager.do?method=<%= typeFunction %>&mscssid=<%=viewResult.getShopper_id() %>&held=<%=viewResult.getHeldOrder()%>&orderId=<%= viewResult.getOrder_id() %>">
										<font color="red"><u>Move To Cart</u></font>
									</a>									
								</td>
								<%
								} 
								%>
								<%if(viewResult.getUserHold() == null || viewResult.getUserHold().isEmpty()){ %>
								<tr>
									<td colspan="2">Expiration Date 
									<%if (session.getAttribute(Constants.IS_CUSTOMER) != null){ %>
									<%=	viewResult.getExpirationdate() %> <%}else{					    
					%> 				<input type="text" class="datepicker"
										value="<%=viewResult.getExpirationdate() %>"
										readonly="readonly" />
									<%if(userLevel!=null && !userLevel.equals("B")&& !userLevel.equals("C")){ %>
									<input type="button" value="SET"
										onclick="setExpirationDay(this,'<%= viewResult.getOrder_id()%>','<%=shopperid %>','<%=expirationDate %>','<%=viewResult.getExpirationdate() %>');" />
									<%} %>
									</td>
								</tr>

								<%  }}%>
								<tr>
									<tr>
										<td VALIGN=BOTTOM ALIGN=LEFT><b>Product<br>
										Number</b></td>
										<td VALIGN=BOTTOM ALIGN=LEFT><b>Product Name</b></td>
										<td VALIGN=BOTTOM ALIGN=right NOWRAP><b>Unit Price</b></td>
										<td VALIGN=BOTTOM ALIGN=right NOWRAP><b>Price/Mhz</b></td>
										<tr>
											<tr>
												<td COLSPAN=9 HEIGHT=1 BGCOLOR="black"></td>
											</tr>
											<%
	   			List<ShopperViewBasket> listInside=(List<ShopperViewBasket>)viewResult.getListShopperViewBasket();			
						for(ShopperViewBasket listinside : listInside){
						    
					   		%>
											<tr>
												<td VALIGN=TOP ALIGN=LEFT><%=listinside.getItem_sku() %></td>
												<td VALIGN=TOP ALIGN=LEFT><%= listinside.getName() %></td>
												<td VALIGN=TOP ALIGN=RIGHT><%= Constants.FormatCurrency(listinside.getPlaced_price()/100) %></td>
												<%if(listinside.getSpeed()>0){
					   		            %>
												<td valign="top" align="right"><%=Constants.FormatMhz(listinside.getPlaced_price()/100/listinside.getSpeed()) %></td>
														<% System.out.println("In page basket view : "+listinside.getSpeed()); %>
												<%      }
					   		        else{
					   		            %>
												<td valign="top" align="right">N/A</td>
											</tr>
											<% }       	
						             } %>
											<tr>
												<td COLSPAN=4 HEIGHT=1 BGCOLOR="black"></td>
											</tr>
											<tr>
												<td COLSPAN=3><br>
												</td>
											</tr>


											<%   }}        
	    
	  	} 
	else{
	    %>
											<tr>
												<td></td>
												<td COLSPAN=2><I>No Held Orders</I></td>
											</tr>
											<%	
         	  
	}}
		 %>
											<div id="results"></div>
</TABLE>

<%@include file="/html/scripts/calendarScript.jsp"%>
<%@include file="/html/scripts/basket_view_Script.jsp"%>