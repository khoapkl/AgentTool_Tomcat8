<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collections"%>
<%@page import="com.dell.enterprise.agenttool.model.Product"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.ProductAttribute"%>
<%@page import="com.dell.enterprise.agenttool.services.ProductServices"%>
<%@page import="com.dell.enterprise.agenttool.model.InventoryCategory"%>
<%
	//ProductServices productServices = new ProductServices();

    int category_id = 0;
	String item_sku = "";
    List<ProductAttribute> listProductAttribute = null;
	InventoryCategory inventoryCategory = null ;
	List<String>  listInventory = null;
	List<String>  listCosmetic = null;
	Map<String,List<String>> mapValueSearch = null ; 
	int searchItemSku = 0;
	
    if (request.getAttribute(Constants.ATTR_CATEGORY_ID) != null)
    {
        category_id = Integer.parseInt(request.getAttribute(Constants.ATTR_CATEGORY_ID).toString());
    }
    
    if (request.getAttribute(Constants.ATTR_INVENTORY_CATEGORY)!=null){
        inventoryCategory = (InventoryCategory)request.getAttribute(Constants.ATTR_INVENTORY_CATEGORY);
    }
    
    if (request.getAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE) != null)
    {
        listProductAttribute = (List<ProductAttribute>) request.getAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE);
    }
    
    if (request.getAttribute(Constants.ATTR_ITEM_SKU) != null)
    {
         item_sku = request.getAttribute(Constants.ATTR_ITEM_SKU).toString();
         searchItemSku = 1 ;
    }
    
    if (request.getAttribute(Constants.ATTR_LIST_INVENTORY) != null)
    {
        listInventory = (List<String>)request.getAttribute(Constants.ATTR_LIST_INVENTORY);
    }
    
    if(request.getAttribute(Constants.ATTR_MAP_VALUE_SEARCH)!=null)
    {
       mapValueSearch  = (Map<String,List<String>>)request.getAttribute(Constants.ATTR_MAP_VALUE_SEARCH);
    }
    
    //BINHHT
    Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
%>
<!--Search by tag-->

<table width="100%" border="0">
	<tbody>
		<tr>
			<td width="300"><img alt="Feature"
				src="images/feature_search.gif"></td>
			<td>Service Tag Search 
			<input type="text" size="6" name="item_sku" id="item_sku" value="<%=item_sku %>" /> 
			<input type="submit" value="Go" name="btServiceTagSearch" id="btServiceTagSearch" />
			
			<input type="hidden" name="SearchItemSku" id="SearchItemSku" value="<%= searchItemSku %>" />

			</td>
		</tr>
	</tbody>
</table>

<!--Form search by toolbar-->
<div id="divSearchProductToolbar">
	<input type="Hidden" value="1" name="search" id="search"> 
	<%
     if (request.getAttribute(Constants.ATTR_CATEGORY_ID) != null)
     {
		%> 
		<input type="Hidden" value="<%=category_id%>" name="category_id" id="category_id"> 
		<%
     }
 	%>


<table width="100%" border="0">
	<tbody>
		<tr>
			<td width="100%" colspan="7">Filter the following items by
			selecting the drop-down boxes below. Clicking a checkbox places the
			item directly in your cart.</td>
		</tr>
	</tbody>
</table>

<!--Toolbar Search--> 
<%
	if(request.getAttribute(Constants.ATTR_INVENTORY_CATEGORY) != null)
	{
	    %>
	    <span style="font-size: 18px;font-weight: bold;"><%=inventoryCategory.getName()%></span>
	    <%
	}
%>
<input type="button" value="Filter" name="btFilterProduct" id="btFilterProduct">
<table cellspacing="2" cellpadding="1" border="0">
	<tbody>
		<tr>
			<%
			if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){
			%>
			<td valign="top" nowrap="nowrap" align="center">Inventory<br>
			<select name="only_available" id="only_available">
				<option selected value="all">All</option>
				<%
				    if (request.getAttribute(Constants.ATTR_LIST_INVENTORY) != null)
				    {
				    	//Added by HuyNVT
				        listInventory.add("AGENT-AVAILABLE & HELD");
				        Collections.sort(listInventory);
				        for (int i = 0 ; i < listInventory.size() ; i++)
				        {   
				            String vSelected = "";
				            if(Constants.STATUS_ITEM_INSTORE.equalsIgnoreCase(listInventory.get(i)))
				            {
				                vSelected = "selected";
				            }				
							%>
							
							<option value="<%=listInventory.get(i)%>" <%=vSelected%> ><%=listInventory.get(i)%></option>
							<%
				    	}
				        
				    }
				%>				
			</select></td>
			<%
			}
			%>
			<%
			if((isCustomer==null || !((Boolean)isCustomer).booleanValue()) & category_id != 11946 & category_id != 11947 & category_id != 11949){
			%>
			<td valign="top" nowrap="nowrap" align="center">Aging Bucket<br>
			<select name="aging_bucket" id="aging_bucket">
				<option selected value="">Ignore</option>
				<option value="30">&lt; 30 Days</option>
				<option value="3140">31-40 Days</option>
				<option value="4150">41-50 Days</option>
				<option value="5160">51-60 Days</option>
				<option value="6170">61-70 Days</option>
				<option value="7190">71-90 Days</option>
				<option value="91">&gt; 90 Days</option>
			</select></td>
			<%
			}
			%>
			
			<%
			if((isCustomer==null || !((Boolean)isCustomer).booleanValue())){
			%>
			<td valign="top" nowrap="nowrap" align="center">Cosmetic Grade
			<span name="mutiple-Cosmetic" style="cursor: pointer;" class="mutiple-Cosmetic" rel="cosmetic_grade">[ + ]</span>
			<br>
			<select name="cosmetic_grade" id="cosmetic_grade">
				<option selected value="all">All</option>
				<%
				    if (request.getAttribute(Constants.ATTR_LIST_COSMETIC) != null)
				    {
			    		listCosmetic = (List<String>)request.getAttribute(Constants.ATTR_LIST_COSMETIC);
				        Collections.sort(listCosmetic);
				        for (int i = 0 ; i < listCosmetic.size() ; i++)
				        {   				
							%>
							<option value="<%=listCosmetic.get(i)%>"><%=listCosmetic.get(i)%></option>
							<%
				    	}
				    }
				%>				
			</select></td>
			<%
			}
			%>
			
			
			<%
			    if (request.getAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE) != null)
			    {
			        for (ProductAttribute proAttribute : listProductAttribute)
			        {
			            List<String> valueSearch  = mapValueSearch.get(proAttribute.getAttribute_number());

						if((category_id == 11946 || category_id == 11947 || category_id == 11949) & proAttribute.getAttribute_name().equals("Chassis Color")){
							if(isCustomer==null || !((Boolean)isCustomer).booleanValue()){
							%>
								<td valign="top" nowrap="nowrap" align="center">Aging Bucket<br>
								<select name="aging_bucket" id="aging_bucket">
									<option selected value="">Ignore</option>
									<option value="30">&lt; 30 Days</option>
									<option value="3140">31-40 Days</option>
									<option value="4150">41-50 Days</option>
									<option value="5160">51-60 Days</option>
									<option value="6170">61-70 Days</option>
									<option value="7190">71-90 Days</option>
									<option value="91">&gt; 90 Days</option>
								</select></td>
							<%
							}
						}
			            if (proAttribute.getIsSearchable() == true && valueSearch.size() > 0)
			            {
							%>
							<td valign="top" nowrap="nowrap">
							<center>
							<%=proAttribute.getAttribute_name()%>
							<%
							    if (proAttribute.getReorderList() == 1)
							    {
							%> <span id="checkbox_<%=proAttribute.getAttribute_number()%>">>= <input type="checkbox"  name="reorder_<%=proAttribute.getAttribute_number()%>" id="reorder_<%=proAttribute.getAttribute_number()%>"  value="1"></span>
								<span name="checkMutiple" style="cursor: pointer;" class="check-multiple" rel="<%=proAttribute.getAttribute_number()%>">[ + ]</span>
							<br />
							<%
							    }else
							    {
							 %>
	 							<span name="checkMutiple" style="cursor: pointer;" class="check-multiple" rel="<%=proAttribute.getAttribute_number()%>">[ + ]</span>
	 
							 <br>
							 <%       
							    }
							%> 
							
							<select name="<%=proAttribute.getAttribute_number()%>" id="<%=proAttribute.getAttribute_number()%>" style="width: auto !important;min-width: 80px">
								<option value=""></option>
								<%
								for (String val : valueSearch)
								{
								%>
								<option value="<%=val%>"><%=val%></option>
								<%
								}
								%>
							</select> 
							</center>
			</td>
			<%
			   			}
			        }
			    }
			%>
			<td valign="top" nowrap="nowrap" align="center">Sort By<br>
			<select name="sort_by" id="sort_by">
				<option value="days_at_mri" >Aging Bucket</option>
				<%
			    if (request.getAttribute(Constants.ATTR_SORT_BY) != null)
			    {
			        List<ProductAttribute> listProductAttributeSortBy = (List<ProductAttribute>)request.getAttribute(Constants.ATTR_SORT_BY);
			        for (ProductAttribute proAttribute : listProductAttributeSortBy)
			        {
			            if(proAttribute.getAttribute_number().equals("status") && !(isCustomer==null || !((Boolean)isCustomer).booleanValue()))
			            {
			                continue;
			            }
			            if (proAttribute.getIsSearchable() == true)
			            {
			            	String selected = "";
			            	if(proAttribute.getAttribute_number().equals("list_price")) 
			            		selected = "selected";
							%>
							<option value="<%=proAttribute.getAttribute_number()%>" <%=selected %> ><%=proAttribute.getAttribute_name()%></option>
							<%
			    		}
			        }
			    }
				%>
			</select></td>
		</tr>
	</tbody>
</table>
</div>
<!--Paging tool bar and Result Product-->
<div id="divSearchProductResults">
	<%
	if(request.getAttribute(Constants.ATTR_ITEM_SKU) != null  && item_sku.isEmpty())
	{
	%>
		<I>No data available</I>
	<%   
	}else
	{
	%>
		<I>Please specify filter criteria</I>
	<%    
	}
	%>
	
</div>

<%@include file="/html/scripts/searchProductScript.jsp"%>
<%@include file="/html/scripts/dialogScript.jsp"%>

<%
if (request.getAttribute(Constants.ATTR_ITEM_SKU) != null && !item_sku.isEmpty())
{
    
%>
	<script type="text/javascript">
		var rowOnPage = 0 ;
		if(document.getElementById("slRowOnpage"))
		{
			rowOnPage = $("#slRowOnpage option:selected").val();
		}else
		{
			rowOnPage = <%= rowOnPage %>;
		}
		FilterProduct("<%= item_sku %>",1,rowOnPage);
	</script>
<%
}
%>

