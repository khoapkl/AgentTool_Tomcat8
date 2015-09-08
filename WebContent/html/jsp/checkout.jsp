<%@page import="java.io.Console"%>
<%@page import="com.dell.enterprise.agenttool.model.EstoreBasketItem"%>
<%@page import="com.dell.enterprise.agenttool.model.WarrantyPartList"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderRow"%>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>
<%@page import="java.util.List"%>
<%@page import="java.math.BigDecimal"%>
<BR />
<BR />
<img alt="Checkout" src="images/checkout.gif">
<BR />
<%
    List<EstoreBasketItem> basketItemCheck = null;
	List<WarrantyPartList> listPartsWarranty = null;
    Customer customer = null;
    OrderRow orderRow = null;
    
    
    
    Float stsubtotal_amt = new Float(0);
    Float stsum = new Float(0);
    Float lidisc_amnt = new Float(0);
    Float totalMhz = new Float(0);
    Float totalPriceMhz = new Float(0);
    Float warrantyTotal = new Float(0);
    //Float newWarrantyDT = new Float(0);
    //Float newWarrantyNB = new Float(0);
    String price_list = "";
    String sku_list = "";
    String sku_disc_list = "";
    String sku_mhz_list = ""; 
    
    int maxCheckout = 0 ;
    Float percentTDis = new Float(0);
    int checkCat = 0 ;
    int qtydesktop = 0;
    int qtynotebook = 0;
    Boolean warrantyNB = false;
    Boolean warrantyDT = false;
    String showWarranty = "";
    
    CheckoutService checkoutService = new CheckoutService(); 
    
    if(request.getAttribute(Constants.ATTR_MAX_DISCOUNT) != null)
    {
        maxCheckout = Integer.parseInt(request.getAttribute(Constants.ATTR_MAX_DISCOUNT).toString());
    }
    
    if(request.getAttribute(Constants.ATTR_CUSTOMER) != null)
    {
        customer = (Customer) request.getAttribute(Constants.ATTR_CUSTOMER);
    }
    
    if(request.getAttribute(Constants.ATTR_ORDER_ROW) != null)
    {
        orderRow = (OrderRow) request.getAttribute(Constants.ATTR_ORDER_ROW);   
    }
    
    if(request.getAttribute(Constants.ATTR_CHECK_CAT) != null)
	{
	    checkCat = Integer.parseInt(request.getAttribute(Constants.ATTR_CHECK_CAT).toString());
	}
    
    if(request.getAttribute(Constants.ATTR_QTY_NB) != null)
	{
    	qtynotebook = Integer.parseInt(request.getAttribute(Constants.ATTR_QTY_NB).toString());
	}
    
    if(request.getAttribute(Constants.ATTR_QTY_DT) != null)
	{
    	qtydesktop = Integer.parseInt(request.getAttribute(Constants.ATTR_QTY_DT).toString());
	}
    
    //newWarrantyNB = (float)qtynotebook * 49;
    //newWarrantyDT = (float)qtydesktop * 39;
    
    Float moneyByMhz = new Float(0);
    if (request.getAttribute(Constants.ATTR_ITEM_BASKET) != null)
    {
        basketItemCheck = (List<EstoreBasketItem>) request.getAttribute(Constants.ATTR_ITEM_BASKET);
        listPartsWarranty = (List<WarrantyPartList>) request.getAttribute(Constants.ATTR_LIST_WARRANTY);
        
        for(EstoreBasketItem  estoreBasketItem : basketItemCheck)
		{
             List<Float> listValue = checkoutService.utilGetDiscount(customer,estoreBasketItem.getItem_sku(),estoreBasketItem.getList_price(),estoreBasketItem.getPlaced_price());
             
             if (estoreBasketItem.getItem_sku().contains("WARRANTY")){
            	 warrantyTotal = warrantyTotal + listValue.get(1);
            	 stsubtotal_amt = stsubtotal_amt + listValue.get(1);
             }
             else
             {
            	 stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getList_price();
                 
             }
             stsum = stsum + listValue.get(1);
             
             
             if(estoreBasketItem.getSpeed().floatValue() > new Float(0))
             {
                 totalMhz = totalMhz + estoreBasketItem.getSpeed();               
                 moneyByMhz = moneyByMhz + listValue.get(1);
             }
             
            price_list = price_list + (estoreBasketItem.getList_price()/100) + "," ;
     		sku_list = sku_list + "LIDiscount" + estoreBasketItem.getItem_sku() + "," ;
     		sku_disc_list = sku_disc_list + "DiscountAmt" + estoreBasketItem.getItem_sku() + ",";
     		sku_mhz_list = sku_mhz_list + "LIMhz" + estoreBasketItem.getItem_sku() + ",";
     		
		}
        if(totalMhz.floatValue() == new Float(0))
        {
            totalPriceMhz = new Float(0);
        }else{
            totalPriceMhz = (moneyByMhz / 100) / totalMhz;    
        }
        lidisc_amnt = stsubtotal_amt - stsum; 
        percentTDis = lidisc_amnt/((stsubtotal_amt - warrantyTotal)/100);
        
    }
    
    Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
    if(isCustomer != null)
    {
        stsubtotal_amt = stsum;
    }
    
    Boolean nbmultip = false;
    Boolean dtmultip = false;
    int warrantyqty = 0;
    if(request.getAttribute(Constants.ATTR_CHECK_CAT) != null)
	{
        if(checkCat == 1 || checkCat==3)
            nbmultip = true;
        if(checkCat == 2 || checkCat==3)
            dtmultip = true;
	}	
    
    if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) {
    	showWarranty = "Text";
    }
    else
    	showWarranty = "hidden";
    
%>


<% 

if (basketItemCheck == null || basketItemCheck.size() == 0 )
{
%>
	<blockquote>
		<font size="2" face="Arial, Helvetica">
			<strong>Your cart is empty.</strong>
		</font>
	</blockquote>
<%    
}else
{
%>

<form NAME="payinfo" ID="payinfo" method="POST" action="checkout.do?method=submitCheckout">
<table>

	<%
	if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
	{
		String disPercent = "";
		if(percentTDis.floatValue() > 0)
		{
		    disPercent = Constants.FloatRound(percentTDis).toString();		    
		}
	%>
	<tr>
		<td>
			 <table border="0" width="1190" cellspacing="0" cellpadding="2" border="0">
				 <tr>
					<td valign="middle" align="right" width="80%"><font color="#0085C5">
						<b>Adjust Discount</b></font> 
					</td>
					<%if (orderRow.getDiscount_type() == 1) {%>
					<td>
						<select name="massdiscount" id="massdiscount" onchange="this.form.adj_discount.focus()">
													<option selected="selected" value="1">None</option>
													<option value="2">Percentage</option>
													<option value="3">Unit Price</option>
													<option value="4">Price per Mhz</option>
												</select>
					</td>
					<td>
						<input type="TEXT" value=""	size="9" value="" name="adj_discount" style="text-align: right;" onfocus="this.form.adj_discount.select()">
					</td>	
					<%} else if (orderRow.getDiscount_type() == 2) {%>
					<td>
						<select name="massdiscount" id="massdiscount" onchange="this.form.adj_discount.focus()">
													<option value="1">None</option>
													<option selected="selected" value="2">Percentage</option>
													<option value="3">Unit Price</option>
													<option value="4">Price per Mhz</option>
												</select>
					</td>
					<td>
						<input type="TEXT" value="<%=disPercent%>"	size="9" value="" name="adj_discount" style="text-align: right;" onfocus="this.form.adj_discount.select()">
					</td>	
					<%} else if (orderRow.getDiscount_type() == 3) {%>
					<td>
						<select name="massdiscount" id="massdiscount" onchange="this.form.adj_discount.focus()">
													<option value="1">None</option>
													<option value="2">Percentage</option>
													<option selected="selected" value="3">Unit Price</option>
													<option value="4">Price per Mhz</option>
												</select>
					</td>
					<td>
						<input type="TEXT" value="<%=orderRow.getDiscount_value()%>" size="9" value="" name="adj_discount" style="text-align: right;" onfocus="this.form.adj_discount.select()">
					</td>	
					<%} else if (orderRow.getDiscount_type() == 4) {%>
					<td>
						<select name="massdiscount" id="massdiscount" onchange="this.form.adj_discount.focus()">
													<option value="1">None</option>
													<option value="2">Percentage</option>
													<option value="3">Unit Price</option>
													<option selected="selected" value="4">Price per Mhz</option>
												</select>
					</td>
					<td>
						<input type="TEXT" value="<%=orderRow.getDiscount_value()%>" size="9" value="" name="adj_discount" style="text-align: right;" onfocus="this.form.adj_discount.select()">
					</td>	
					<%} else {%>
					<td>
						<select name="massdiscount" id="massdiscount" onchange="this.form.adj_discount.focus()">
													<option selected="selected" value="1">None</option>
													<option value="2">Percentage</option>
													<option value="3">Unit Price</option>
													<option value="4">Price per Mhz</option>
												</select>
					</td>
					<td>
						<input type="TEXT" value="" size="9" value="" name="adj_discount" style="text-align: right;" onfocus="this.form.adj_discount.select()">
					</td>	
						<%}%>
					<td>
						<input type="button" value="Apply" name="disapply" onclick="mass_cal_discount_option(this.form, this.form.adj_discount.value)">
					</td>
				</tr>
				<tr>
					<td valign="middle" align="right" colspan="2"><b>Average Price/Mhz:&nbsp;</b></td>
					<td>
						<input type="TEXT" readonly="readonly" size="9"
							value="<%= Constants.FormatMhz(totalPriceMhz) %>"
							name="avg_price_mhz" style="text-align: right;">
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<%
	}
	%>

	<tr>
		<td>


		<table width="1200" cellspacing="0" cellpadding="2" border="0">
			<tbody>
				<tr>
					<th>&nbsp;</th>
					<th align="LEFT"><font size="2" face="Arial, Helvetica">Product
					Number </font></th>
					<th width="1">&nbsp;</th>
					<th align="LEFT"><font size="2" face="Arial, Helvetica">Product Description </font></th>
					<th width="1">&nbsp;</th>
					<th align="RIGHT">
						<font size="2" face="Arial, Helvetica">Unit	Price </font>
					</th>
					<th align="RIGHT">
						<font size="2" face="Arial, Helvetica">Qty </font>
					</th>
				<%
				if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
				{
				%>
					<th>&nbsp;</th>
					<th align="RIGHT">
						<font size="2" face="Arial, Helvetica">Discount	Amount</font>
					</th>
					<th>&nbsp;</th>					
					<th align="RIGHT">
						<font size="2" face="Arial, Helvetica">Extended Price</font>
					</th>					
					<th>&nbsp;</th>
					<th align="LEFT">
						<font size="2" face="Arial, Helvetica">Price/Mhz</font>
					</th>
				<%
				} else {
				%>
				<th>&nbsp;</th>					
					<th align="RIGHT">
						<font size="2" face="Arial, Helvetica">Extended Price</font>
					</th>
				<%} %>
				</tr>

				<%
		if (basketItemCheck != null)
		{
		    int count_idx = 1 ;
			for(EstoreBasketItem  estoreBasketItem : basketItemCheck)
			{
			    Float listPrice = estoreBasketItem.getList_price();
			    List<Float> listValue = checkoutService.utilGetDiscount(customer,estoreBasketItem.getItem_sku(),new Float(estoreBasketItem.getList_price()),new Float(estoreBasketItem.getPlaced_price()));
			    //if(isCustomer != null)
			    //{
			    //    listPrice = listValue.get(1);    
			    //}
			    
			    if (estoreBasketItem.getCategory_id() == 11940) warrantyNB = true;
			    else if (estoreBasketItem.getCategory_id() == 11941) warrantyDT = true;
		%>

				<tr>
					<td align="LEFT">
						<font size="1" face="Arial, Helvetica"><b><%= count_idx %></b></font>
					</td>
					<td>
						<font size="1" face="Arial, Helvetica"><%= estoreBasketItem.getItem_sku() %></font>
					</td>
					<td>
						<font size="1" face="Arial, Helvetica">&nbsp;</font>
					</td>
					<td align="LEFT">
						<font size="1" face="Arial, Helvetica"><%= estoreBasketItem.getName() %></font>
					</td>
					<td>
						<font size="1" face="Arial, Helvetica">&nbsp;</font>
					</td>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica"><%= Constants.FormatCurrency(Float.valueOf(listPrice/100)) %></font>
					</td>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica">
							<input type="text" onfocus="this.blur();"
								<%if (estoreBasketItem.getCategory_id() == 11940) warrantyqty = qtynotebook; else if (estoreBasketItem.getCategory_id() == 11941) warrantyqty = qtydesktop; else warrantyqty = 1; %>
							value="<%=warrantyqty%>"
							size="3" name="qty<%=estoreBasketItem.getItem_sku() %>"
							style="text-align: right;">
						</font>
					</td>
					<%
					if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
					{  
					%>
					<td><font size="1" face="Arial, Helvetica">&nbsp;</font></td>
					<%if ((estoreBasketItem.getCategory_id() != 11940 && estoreBasketItem.getCategory_id() != 11941)) { %>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica">
							<input type="<%=showWarranty %>" onfocus="this.blur();"
							value="<%=Constants.FormatCurrency((estoreBasketItem.getList_price() - listValue.get(1))/100 )%>"
							size="9" name="DiscountAmt<%=estoreBasketItem.getItem_sku() %>"
							style="text-align: right;">
						</font>
					</td>
					<td>&nbsp;</td>
					<td align="RIGHT">
						<input type="<%=showWarranty %>" onchange="calc_discount(this.form, this.form.LIDiscount<%=estoreBasketItem.getItem_sku() %>, <%=count_idx %>, this.form.LIDiscount<%=estoreBasketItem.getItem_sku() %>.value, <%= estoreBasketItem.getList_price()/100 %>, this.form.DiscountAmt<%=estoreBasketItem.getItem_sku() %>, this.form.LIMhz<%=estoreBasketItem.getItem_sku() %>, this.form.LIMhz<%=estoreBasketItem.getItem_sku() %>hid.value)"
						value="<%= Constants.FormatCurrency(listValue.get(1)/100) %>"
						size="9" name="LIDiscount<%=estoreBasketItem.getItem_sku() %>"
						style="text-align: right;">
					</td>
					<% } else {%>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica">
							<input type="<%=showWarranty %>" onfocus="this.blur();"
							value="<%=0%>"
							size="9" name="DiscountAmt<%=estoreBasketItem.getItem_sku() %>"
							style="text-align: right;">
						</font>
					</td>
					<td>&nbsp;</td>
					<td align="RIGHT">
						<input type="<%=showWarranty %>" onfocus="this.blur();"
						value="<%= Constants.FormatCurrency(estoreBasketItem.getList_price()/100 * warrantyqty) %>"
						size="9" name="LIDiscount<%=estoreBasketItem.getItem_sku() %>"
						style="text-align: right;">
					</td>
					<%} %>
					<td>&nbsp;</td>
					
						<%
						String valFormatMhz =  (estoreBasketItem.getSpeed().intValue() == 0) ? "0" : Constants.FormatMhz((listValue.get(1) /100) / estoreBasketItem.getSpeed());
						System.out.println("List Values: "+listValue.get(1));
						System.out.println("Speed: "+estoreBasketItem.getSpeed());
						%>
						
						<td>
							<input type="<%=showWarranty %>" readonly="readonly" value="<%= valFormatMhz %>" name="LIMhz<%=estoreBasketItem.getItem_sku()%>" size="5">
						</td>
					<%
					}else{
				    %>
				    <td>&nbsp;</td>
					<td align="RIGHT">
						<input type="text" onfocus="this.blur();"
						value="<%= Constants.FormatCurrency(estoreBasketItem.getList_price()/100 * warrantyqty) %>"
						size="9" name="LIDiscount<%=estoreBasketItem.getItem_sku() %>"
						style="text-align: right;">
					</td>
				    <!-- 
				    <td>
				    	<input type="hidden" value="<%= Constants.FormatCurrency(listValue.get(1)/100) %>"	name="LIDiscount<%=estoreBasketItem.getItem_sku() %>" >
				    </td>
				     -->
				    <%
					}
					%>
					<td>
						<input type="hidden" value="<%=estoreBasketItem.getSpeed()%>" name="LIMhz<%=estoreBasketItem.getItem_sku()%>hid">
					</td>
					<%if (estoreBasketItem.getCategory_id() == 11940 || estoreBasketItem.getCategory_id() == 11941) 
					{ %>
					<td>
						<input type="checkbox" name="<%=estoreBasketItem.getItem_sku()%>buy" checked="checked">
					</td>
					<td><font size="1" face="Arial, Helvetica">Uncheck to Remove</font></td>
					<%} else {%>
					<td></td> 
					<%} %>
				</tr>
				<%
				count_idx ++ ;
			}
			//Manually add warranty line
			%>
			<% 
			for (WarrantyPartList listWarrantyParts : listPartsWarranty)
			{
				if (listWarrantyParts.getCategory_id() == 11940) warrantyqty = qtynotebook; else if (listWarrantyParts.getCategory_id() == 11941) warrantyqty = qtydesktop; else warrantyqty = 1;
			%>
				<tr>
					<td align="LEFT">
						<font size="1" face="Arial, Helvetica"><b>+/-</b></font>
					</td>
					<td>
						<font size="1" face="Arial, Helvetica"><%=listWarrantyParts.getItem_sku() %></font>
					</td>
					<td>
						<font size="1" face="Arial, Helvetica">&nbsp;</font>
					</td>
					<td align="LEFT">
						<font size="1" face="Arial, Helvetica"><%=listWarrantyParts.getName() %></font>
					</td>
					<td>
						<font size="1" face="Arial, Helvetica">&nbsp;</font>
					</td>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica"><%=Constants.FormatCurrency(listWarrantyParts.getList_price()/100)%></font>
					</td>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica">
							<input type="TEXT" onfocus="this.blur();"
							value="<%=warrantyqty%>"
							size="3" name="qty<%=listWarrantyParts.getItem_sku() %>"
							style="text-align: right;">
						</font>
					</td>
					
					<td><font size="1" face="Arial, Helvetica">&nbsp;</font></td>
					<%
					if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
					{  
					%>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica">
							<input type="<%=showWarranty %>" onfocus="this.blur();"
							value="0" size="9" name="DiscountAmt<%=listWarrantyParts.getItem_sku() %>"
							style="text-align: right;">
						</font>
					</td>
					<td>&nbsp;</td>
					<td align="RIGHT">
						<input type="<%=showWarranty %>" onfocus="this.blur();"
						value="<%=Constants.FormatCurrency(listWarrantyParts.getList_price() * warrantyqty / 100)%>"
						size="9" name="LIDiscount<%=listWarrantyParts.getItem_sku() %>"
						style="text-align: right;">
					</td>
					<%} else { %>
					<td align="RIGHT">
						<input type="text" onfocus="this.blur();"
						value="<%=Constants.FormatCurrency(listWarrantyParts.getList_price() * warrantyqty / 100) %>"
						size="9" name="LIDiscount<%=listWarrantyParts.getItem_sku() %>"
						style="text-align: right;">
					</td>
					<%} %>
					<td>&nbsp;</td>
					<td><input type="<%=showWarranty %>" readonly="readonly" value="0" name="LIMhz<%=listWarrantyParts.getItem_sku()%>" size="5"></td>
					<td>
						<input type="checkbox" name="<%=listWarrantyParts.getItem_sku() %>new">
					</td>
					<td><font size="1" face="Arial, Helvetica">Check to Add</font></td>
				</tr>
				<%} %>
				
				<%if ((!warrantyNB) && (qtynotebook > 0)) {%>
				
			<%
			}
			%>
			<%if ((!warrantyDT) && (qtydesktop > 0)) {%>
			
				<%} %>
				
				<%if ((!warrantyNB) && (qtynotebook > 0)) {%>
				
				<%} %>
			<%
			//End warranty line
		}
		%>
			
				<tr height="1px">
					<td valign="top" colspan="5" align="right">
						&nbsp;
					</td>
					<td valign="TOP" align="RIGHT">
						<img width="100%" height="1" src="images/dot_black.gif" alt="">
					</td>
					<%
					if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
					{
					%>
					
					<td valign="TOP" align="RIGHT">
						<img width="100%" height="1" src="images/dot_black.gif" alt="">
					</td>
					<td>
						&nbsp;
					</td>
					<td valign="TOP" align="RIGHT">
						<img width="100%" height="1" src="images/dot_black.gif" alt="">
					</td>
					<td>
						&nbsp;
					</td>
					<td valign="TOP" align="LEFT">
						<img width="90%" height="1" src="images/dot_black.gif" alt="">
					</td>
					<td>
						&nbsp;
					</td>
					<td valign="TOP" align="LEFT">
						<img width="90%" height="1" src="images/dot_black.gif" alt="">
					</td>
					<%
					}
					%>	
				</tr>

				<tr>
					<td colspan="5">&nbsp;</td>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica"><%= Constants.FormatCurrency(stsubtotal_amt/100) %></font>
					</td>
					<td>&nbsp;</td>
					<%
					if(isCustomer==null || !((Boolean)isCustomer).booleanValue())
					{
					%>
					<td>&nbsp;</td>
					<td align="RIGHT"><font size="1" face="Arial, Helvetica"><input
						type="TEXT" onfocus="this.blur();"
						value="<%= Constants.FormatCurrency(lidisc_amnt/100)%>"
						name="Disc_TotCol" size="9" style="text-align: right;"></font></td>
					
					<td>&nbsp;</td>
					<td align="RIGHT">
						<font size="1" face="Arial, Helvetica">
							<input type="TEXT" onfocus="this.blur();" value="<%= Constants.FormatCurrency(stsum/100) %>" name="Ext_TotCol" size="9" style="text-align: right;">
						</font>
					</td>

					<td>&nbsp;</td>
					
					<td align="LEFT"><font size="1" face="Arial, Helvetica"><input
						type="TEXT" onfocus="this.blur();"
						value="<%= Constants.FormatMhz(totalPriceMhz) %>" name="AvgMhz"
						size="5" style="text-align: left;"></font></td>
					<%
					} 
					%>			

				</tr>
			</tbody>
		</table>
		<input type="hidden" value="CheckPromotionCode" name="mode"> <input
			type="hidden" value="0" name="Volume_Discount"> <input
			type="hidden" value="<%= price_list %>" name="price_list"> <input
			type="hidden" value="<%= sku_list %>" name="sku_list"> <input
			type="hidden" value="<%= sku_disc_list %>" name="sku_disc_list">
		<input type="hidden" value="<%= sku_mhz_list %>" name="sku_mhz_list">
		<input type="hidden" value="<%= basketItemCheck.size() %>" name="row"> 
		<input type="hidden" value="<%=stsubtotal_amt/100 %>" name="sum"> <input
			type="hidden" value="<%=stsubtotal_amt/100 %>" name="subtotal">
		<input type="hidden" value="<%=maxCheckout%>" name="MaxDiscount">
		<input type="hidden" value="<%=qtydesktop %>" name="warrantyDT">
		<input type="hidden" value="<%=qtynotebook %>" name="warrantyNB">

		</td>

	</tr>
</table>


<table width="1000" cellspacing="0" cellpadding="2" border="0">
	<tbody>
		<tr>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
			<td valign="BOTTOM" align="right">
			<table width="778" cellspacing="0" cellpadding="1" border="0">

				<tbody>
					<tr>
						<th align="RIGHT">
							<font size="2" face="Arial, Helvetica">Subtotal:</font>
						</th>
						<td width="10" align="RIGHT"><font size="2"
							face="Arial, Helvetica"> <input type="TEXT"
							onfocus="this.blur();"
							value="<%= Constants.FormatCurrency(stsum/100) %>"
							name="Est_SubTot" size="9" style="text-align: right;"></font></td>
					</tr>
					<tr>
						<td>
						<input type="hidden" value="$0.00" name="VOLUME" id="VOLUME" />
						<input type="Hidden" value="$0.00" name="LIDISCOUNT"/>
						</td>
					</tr>
					<tr>
						<th align="RIGHT">
							<font size="2" face="Arial, Helvetica">Estimated Total:</font>
						</th>
						<td align="RIGHT">
							<font size="2" face="Arial, Helvetica">
								<input type="text" onfocus="this.blur();" value="<%= Constants.FormatCurrency(stsum/100) %>" size="9" name="ETOTAL" style="text-align: right;">
							</font>
						</td>
					</tr>
					<%
					if(isCustomer == null)
					{
					%>		
					<tr>
						<td align="RIGHT">
							<img width="20%" height="1"	src="images/dot_black.gif" alt="">
						</td>
						<td>
						</td>
					</tr>

					<tr>
						<th nowrap="nowrap" align="RIGHT">
							<font size="2" face="Arial, Helvetica"> Total Disc. $</font>
						</th>
						<td align="RIGHT">
							<font size="2" face="Arial, Helvetica">
								<input type="TEXT" onfocus="this.blur();" value="<%= Constants.FormatCurrency(lidisc_amnt/100)%>" maxlength="9" size="9" name="ttldisc" style="text-align: right;">
							</font>
						</td>
					</tr>
					<tr>
						<th nowrap="nowrap" align="RIGHT">
							<font size="2" face="Arial, Helvetica"> Total Disc. %</font>
						</th>
						<td align="RIGHT">
							<font size="2" face="Arial, Helvetica"> 
							<input type="TEXT" onfocus="this.blur();" value="<%=Constants.FloatRound(percentTDis)%>%" maxlength="9" size="9" name="ttldiscp" style="text-align: right;"></font>
						</td>
					</tr>
					<%    
					}else
					{
					%>
					<tr>
						<td height="1px">
							<input type="hidden" value="<%= Constants.FloatRound(percentTDis)%>" name="ttldiscp" id="ttldiscp" />
						</td>
					</tr>
					<%
					}
					%>	
				</tbody>
			</table>
			</td>
		</tr>
		<tr>
			<td align="left"></td>
			<td align="right">
			<p>&nbsp;

			<table width="500" cellspacing="0" cellpadding="2" border="0" align="right">

				<tbody>
					<%
					if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){
					%>
<!--Vinhhq comment code Promotion Code				-->
<!--					<tr>
						<td width="80%" align="right">
							<font size="2" face="Arial," elvetica=""><b>Promotion Code </b></font>
						</td>
						<td align="right">
							<input 	type="text"
									onchange="javascript:document.payinfo.promotion_code_tf.value = document.payinfo.promotion_code_tf.value.toUpperCase();"
									value="" 
									name="promotion_code_tf" 
									maxlength="8" 
									size="9" >
						</td>
					</tr>
-->
					<%
					} 
					
					if(nbmultip || dtmultip)
					{
					%>
					<tr>
						<td align="right">
						
							<font size="2" face="Arial,"><b><u>MultipPack Options:</u></b></font>
						
						</td>
						<td align="right"></td>
					</tr>
					<%    
					}
					
					if(request.getAttribute(Constants.ATTR_CHECK_CAT) != null)
					{
					    if(checkCat == 1 || checkCat==3)
					    {
					    	%>
					    	<tr>
								<td align="right">
									<font size="2" face="Arial,">Notebooks</font></td>
								<td align="right">
								<select name="nbmultip" id="nbmultip">
									<!-- <option selected="selected" value="0">Select Qty...</option> -->
									<option value="1">1</option>
									<!-- <option value="5">5</option>
									<option value="48">48</option> -->
								</select>
								</td>
							</tr>
					    	<%    
					    }
					    else
					    {
					        %>
					         <tr>
								<td colspan="2"><input type="hidden" id="nbmultip" name="nbmultip" value="2"></td>
                            </tr>	
					        <%
					    }
					    
					    if(checkCat == 2 || checkCat==3)
					    {
					        %>
					        <tr>
								<td align="right">
									<font size="2" face="Arial">Desktops</font></td>
								<td align="right">
								<select name="dtmultip" id="dtmultip">
									<option value="1">1</option>
									<!-- <option selected="selected" value="0">Select Qty...</option>
									<option value="10">10</option> -->
								</select>
								</td>
							</tr>
					        <%
					    }else
					    {
							%>
							 <tr>
								<td colspan="2"><input type="hidden" id="dtmultip" name="dtmultip" value="2"></td>
							</tr>
							<%					        
					    }
					}else{
					    %>
						 <tr>
							<td colspan="2"><input type="hidden" id="dtmultip" name="dtmultip" value="2"></td>
						</tr>
						<%		
					}
				%>

				</tbody>
			</table>
			</p>
			</td>
		</tr>
		<tr>
			<td colspan="2">

			<table width="500" cellspacing="0" cellpadding="2" border="0" align="right">
				<tbody>
					<tr>
						<td align="right" colspan="2"><a
							href="javascript:submitForm();"><img width="15" height="15"
							border="0" src="images/item.gif" alt=""></a>&nbsp;&nbsp;<a
							href="javascript:submitForm();">Continue Checkout</a></td>
						<td>&nbsp;</td>

					</tr>
				</tbody>
			</table>
			</td>
		</tr>
	</tbody>
</table>
</form>

<%@include file="/html/scripts/checkoutScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>

<%    
}
%>
