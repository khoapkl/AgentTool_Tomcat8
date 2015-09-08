<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.model.Product"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.ProductAttribute"%>
<%@page import="com.dell.enterprise.agenttool.services.ProductServices"%>
<%@page import="com.dell.enterprise.agenttool.services.CustomerServices"%>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>

<%
    int categoryId = 0;
	
    CheckoutService checkoutService = new CheckoutService();
    ProductServices productService = new ProductServices();
    List<ProductAttribute> listProductAttribute = null;
    Map<String, String> itemBaskets = null;
    List<Product> productResutls = null;
    Agent agent = null;
    String shopperId = "";
    
    int rowCount = 0;
    int totalPage = 0;
    int currentPage = 0;
	int rowOnPage = 0 ;
	
	
	if(session.getAttribute(Constants.AGENT_INFO) != null)
	{
	    agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
	}
	
    if (request.getAttribute(Constants.ATTR_CATEGORY_ID) != null)
    {
        categoryId = Integer.parseInt(request.getAttribute(Constants.ATTR_CATEGORY_ID).toString());
        productResutls = (List<Product>) request.getAttribute(Constants.ATTR_SEARCH_PRODUCT_RESULT);
        //listProductAttribute = (List<ProductAttribute>) request.getAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE);
        listProductAttribute = (List<ProductAttribute>) request.getAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE_BY_SORT_RESULT);
        itemBaskets = (Map<String, String>) request.getAttribute(Constants.ATTR_ITEM_BASKET);
    }

    if (request.getAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE) != null && request.getAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE) != null)
    {
        rowCount = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_ROW_COUNT).toString());
        totalPage = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE).toString());
        currentPage = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE).toString());
    }
	
    if(request.getAttribute(Constants.ATTR_ROW_ON_PAGE) != null)
    {
        rowOnPage = (Integer)request.getAttribute(Constants.ATTR_ROW_ON_PAGE) ;
    }
    
    if (session.getAttribute(Constants.SHOPPER_ID) != null)
    {
        shopperId = session.getAttribute(Constants.SHOPPER_ID).toString();
    }
    
    Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
    Customer customer = null;
	if(isCustomer != null)
	{
	    CustomerServices customerServices = new CustomerServices();
		customer = customerServices.getCustomerByShopperID(shopperId);
	}
    
    
    if (request.getAttribute(Constants.ATTR_SEARCH_PRODUCT_RESULT) != null && currentPage > 0)
    {
        if (request.getAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE) != null && request.getAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE) != null)
        {
%>

<table width="100%" cellspacing="0" cellpadding="2" border="0">
	<tbody>
		<tr>
			<td nowrap="nowrap" bgcolor="lightgrey" align="left">
				<input type="button" value="   &lt;&lt;   " id="btFistPage" name="btFistPage"> 
				<input type="button" value="   &lt;    "    id="btPreviousPage" name="btPreviousPage"> 
				<input type="button" value="    &gt;   " id="btNextPage" name="btNextPage">
				<input type="button" value="   &gt;&gt;   " id="btLastPage" name="btLastPage"> 
				<input type="hidden" value="<%= currentPage %>" id="currentPage" name="currentPage">
			</td>
			<th width="100%" bgcolor="lightgrey" align="LEFT"><nobr>&nbsp;&nbsp;&nbsp;<%=rowCount%>
			Records &nbsp;&nbsp;&nbsp;&nbsp; Page: <%=currentPage%> of <%=totalPage%></nobr>
				&nbsp;&nbsp;&nbsp;&nbsp;Page size: 
				<select name="slRowOnpage" id="slRowOnpage" style="width:80px">
					<%
					String strSelected = "";
					for(int valRowOnPage :  Constants.ARR_ROW_ON_PAGE)
					{
					    if(valRowOnPage == rowOnPage)
					    {
					        strSelected = "selected" ; 
					    }else
					    {
					        strSelected = "" ;					        
					    }
					    %>
					    <option <%=strSelected%> value="<%= valRowOnPage %>"><%= valRowOnPage %></option>
					    <%
					}
					%>
				</select>
			</th>
		</tr>
	</tbody>
</table>
<script type="text/javascript">
	var rowOnPage = $("#slRowOnpage option:selected").val(); 
	<%
	if (currentPage == 1)
	{
	%>
		$("#btFistPage,#btPreviousPage").css("color","lightgrey");
	<%
	}
	else
	{
	%>
	$("#btFistPage").click(function()
	{
		FilterProductPaging(1,rowOnPage);
	});
	$("#btPreviousPage").click(function()
	{
		FilterProductPaging(<%=(currentPage - 1)%>,rowOnPage);
	});
	<%
	}
	if (currentPage == totalPage)
	{
	%>
		$("#btNextPage,#btLastPage").css("color","lightgrey");
	<%
	}
	else
	{
	%>
	$("#btNextPage").click(function()
	{
		FilterProductPaging(<%=(currentPage + 1)%>,rowOnPage);
	});
	$("#btLastPage").click(function()
	{
		FilterProductPaging(<%=(totalPage)%>,rowOnPage);
	});
	<%
	}
	%>
</script>
<%
}
//Check Pesmission for Agent
if(agent.isAdmin() || (isCustomer == null &&  !agent.isAdmin() && !agent.getUserLevel().equals("C")) || isCustomer != null)
{
%>
<input type="button" value="Update Cart" name="btUpdateCard" id="btUpdateCard" />
<input type="button" value="Clear Items" name="btClearItem" id="btClearItem" />
<input type="button" value="Select All" name="btSelectAll" id="btSelectAll" />
<input type="button" value="De-Select All" name="btDeSelectAll" id="btDeSelectAll" />
<%    
}
%>

<table>
	<tbody>
		<tr>
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			<%if(categoryId != 11946 & categoryId != 11947 & categoryId != 11949){
				%>
					<td valign="top" align="center" nowrap="nowrap">
						<strong>Product<BR>Number</strong>
					</td>
					
					<td valign="top" align="center" nowrap="nowrap"><strong>Status</strong></td>
					<td valign="top" align="center" nowrap="nowrap"><strong>Cosmetic Grade</strong></td>
					<% if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){%>
						<td valign="top" align="center" nowrap="nowrap"><strong>Days @ MRI</strong></td>
					 <% }%>
					<td valign="top" align="center" nowrap="nowrap"><strong>Order #</strong></td>
					<td valign="top" align="center" nowrap="nowrap"><strong>Sold To</strong></td>
					<td valign="top" align="center" nowrap="nowrap"><strong>Unit Price</strong></td>
				<%	
			}else{%>
				<td valign="top" align="center" nowrap="nowrap"><strong>Status</strong></td>
				<%if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){%>
					<td valign="top" align="center" nowrap="nowrap"><strong>Cosmetic Grade</strong></td>	
				<%}	
			}
			%>
			
			<%
		    int colspan = 8;
	        for (ProductAttribute proAttribute : listProductAttribute)
	        {
	        	if((categoryId == 11946 || categoryId == 11947 || categoryId == 11949) & proAttribute.getAttribute_name().equals("WLAN")){
	            	%>
					<td valign="top" align="center" nowrap="nowrap"><strong>Unit Price</strong></td>
					<%if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){%>
						<td valign="top" align="center" nowrap="nowrap"><strong>Days @ MRI</strong></td>
					<%} %>
					<td valign="top" align="center" nowrap="nowrap"><strong>Order #</strong></td>
					<td valign="top" align="center" nowrap="nowrap"><strong>Sold To</strong></td>
					<td valign="top" align="center" nowrap="nowrap">
						<strong>Product<BR>Number</strong>
					</td>
	            <% }
	        	
	            if (proAttribute.getIsVisible() == false)
	            {
	                continue;
	            }
	            colspan++;
	            
			%>
			<td valign="top" align="center" nowrap="nowrap"><strong><%=proAttribute.getAttribute_name()%></strong></td>
			<%
			}
			%>

		</tr>
		<tr>
			<td colspan="<%=colspan%>">
			<hr width="100%" size="1 COLOR=" noshade="TRUE" align="left">
			</td>
		</tr>
		<%
	        productResutls = (List<Product>) request.getAttribute(Constants.ATTR_SEARCH_PRODUCT_RESULT);
	        for (Product product : productResutls)
	        {
	            Map<String, String> mapAtrr = product.getAttributes();
	            String checked = "";
		%>
		<tr>
			<td align="center"><%= product.getIdx() %></td>
			<td align="center">
			<%
			    if (product.getStatus().equalsIgnoreCase(Constants.STATUS_ITEM_INSTORE) || 
			        product.getStatus().equalsIgnoreCase(Constants.STATUS_ITEM_INSTORE_PENDING) ||
			    	product.getStatus().equalsIgnoreCase(Constants.STATUS_ITEM_INSTORE_COT) ||
			    	product.getStatus().equalsIgnoreCase(Constants.STATUS_ITEM_INSTORE_PENDING_COT) ||
			    	product.getStatus().equalsIgnoreCase("AGENT-" + agent.getUserName() + "-AVAILABLE") ||
			    	product.getStatus().equalsIgnoreCase("AGENT-" + agent.getUserName() + "-PENDING")
			    )
			    {
			        if(agent.isAdmin() || (isCustomer == null &&  !agent.isAdmin() && !agent.getUserLevel().equals("C")) || isCustomer != null)
			        {
						if (itemBaskets.get(product.getItem_sku()) != null)
						{
						    checked = "checked";
						}
						%>
						<input type="checkbox" name="chk" id="chk" value="<%=product.getItem_sku()%>" <%=checked%> /> 
						<%
						
						if (!checked.isEmpty())
				        {
						%> 
						<input type="hidden" name="taken" id="taken" value="<%=product.getItem_sku()%>"> 
						<%
				    	}
			        }
			    }
			 %>
			</td>
			<%if(categoryId != 11946 & categoryId != 11947 & categoryId != 11949){
				%>
				
				<td align="center" nowrap="nowrap">
					<a href="javascript:void(0)"
					onclick="openwindow('searchProduct.do?method=productDetail&item_sku=<%=product.getItem_sku()%>');"
					onmouseOver="window.status='Click for warranty information.'; return true"
					onmouseOut="window.status=''; return true"> <%=product.getItem_sku()%>
					</a>
				</td>
				<td align="center" nowrap="nowrap"><%=product.getStatus().toUpperCase()%></td>
				<td align="center" nowrap="nowrap"><%=product.getCosmeticgrade()%></td>
				<%if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){%>
					<td align="center" nowrap="nowrap"><%=product.getItem_age()%></td>
					<%} %>
				<td align="center" nowrap="nowrap"><%=product.getOrder_id()%></td>
				<td align="center" nowrap="nowrap"><%=productService.getSoldTo(product.getStatus(), product.getShopper_id(), product.getItem_sku())%></td>
				
				<%
					Float listPrice = product.getList_price();
				 	if(isCustomer != null)
		        	{
		                List<Float> listValue = checkoutService.utilGetDiscount(customer,product.getItem_sku(),listPrice*100,listPrice*100);
		                listPrice = listValue.get(1)/100;
		        	}
				%>
				<td align="center" nowrap="nowrap"><%=Constants.FormatCurrency(listPrice)%></td>
				
			<%}else{%>
			
				<td align="center" nowrap="nowrap"><%=product.getStatus().toUpperCase()%></td>
					<%if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){%>
						<td align="center" nowrap="nowrap"><%=product.getCosmeticgrade()%></td>		
					<%}
					} %>
			<%
			    for (ProductAttribute proAttribute : listProductAttribute)
			    {
		                if (proAttribute.getIsVisible() == false)
		                {
		                    continue;
		                }
		                if((categoryId == 11946 || categoryId == 11947 || categoryId == 11949) & proAttribute.getAttribute_name().equals("WLAN")){%>
		                	
							<%
								Float listPrice = product.getList_price();
							 	if(isCustomer != null)
					        	{
					                List<Float> listValue = checkoutService.utilGetDiscount(customer,product.getItem_sku(),listPrice*100,listPrice*100);
					                listPrice = listValue.get(1)/100;
					        	}
							%>
							
							<td align="center" nowrap="nowrap"><%=Constants.FormatCurrency(listPrice)%></td>
							<%if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){%>
								<td align="center" nowrap="nowrap"><%=product.getItem_age()%></td>
								<%} %>
							<td align="center" nowrap="nowrap"><%=product.getOrder_id()%></td>
							<td align="center" nowrap="nowrap"><%=productService.getSoldTo(product.getStatus(), product.getShopper_id(), product.getItem_sku())%></td>
							
							<td align="center" nowrap="nowrap">
								<a href="javascript:void(0)"
								onclick="openwindow('searchProduct.do?method=productDetail&item_sku=<%=product.getItem_sku()%>');"
								onmouseOver="window.status='Click for warranty information.'; return true"
								onmouseOut="window.status=''; return true"> <%=product.getItem_sku()%>
								</a>
							</td>
							
		                <%}
			%>
			<td align="center" nowrap="nowrap"><%=mapAtrr.get(proAttribute.getAttribute_number())%></td>
			<%
			    }
			%>
		</tr>
		<%
		}
		%>

	</tbody>
</table>
<%@include file="/html/scripts/searchProductResultsScript.jsp"%>
<%
    }
    else
    {
        out.println("<i>No data available</i>");
    }
%>