<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.OrderRow"%>
<%@page import="com.dell.enterprise.agenttool.model.EppPromotionRow"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter" %>
<%@page import="com.dell.enterprise.agenttool.util.Constants" %>
<%@page import="com.dell.enterprise.agenttool.model.EstoreBasketItem"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<div>

<%!
boolean rightToDisplayOptionFree(boolean isAdmin, String userLevel)
{
	boolean result = false;
	if (isAdmin)
		result = true;
	else
	{
		if (userLevel.equalsIgnoreCase("A"))
				result = true;
		else if (userLevel.equalsIgnoreCase("B"))
				result = false;
		else if (userLevel.equalsIgnoreCase("C"))
				result = false;					
	}

	return result;
}
%>

<%
Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);

List<EstoreBasketItem> basketItemCheck =  (List<EstoreBasketItem>) request.getAttribute(Constants.ATTR_ITEM_BASKET);
if (basketItemCheck == null || basketItemCheck.size() == 0 )
{
%>
<blockquote><font size="2" face="Arial, Helvetica"><strong>Your
cart is empty.</strong></font></blockquote>

<%    
}else{
%>

<%

boolean usingManager = (Converter.stringToBoolean(session.getAttribute(Constants.IS_ADMIN).toString()) || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")));

OrderRow orderRow =	(OrderRow)request.getAttribute(Constants.ATTR_ORDER_ROW);
Object[][] arrayShipping = (Object[][])request.getAttribute("ArrayShipping");
Customer customer = (Customer)request.getAttribute(Constants.ATTR_CUSTOMER);

String fontFace           = "Verdana, Helvetica, Sans-Serif";
String fontGeneric = "<font face=\"Arial, Helvetica\" size=\"2\">";
String fontResultsHead    = "<font FACE=\"Verdana, Helvetica, Sans-Serif\" size=\"2\">";

String fontResultsHeadEnd = "</font>";
String fontGenericRed     = "<font FACE=\"Verdana, Helvetica, Sans-Serif\" color=\"#FF0000\" size=\"2\">";
String fontHeader         = "<font FACE=\"Verdana, Helvetica, Sans-Serif\" size=\"3\"><b>";
String fontHeaderEnd      = "</b></font><br>";

String pageName = "Checkout - Shipping";
String strHeader = "images/checkout-shipping.gif";
String strMessage;
String strPostURL;
String shopperID = customer.getShopper_id();
int arrCount = 0;
	//targetPage = "order";
	//subPage    = "checkout";	
		
	
	
//set mscsOrderForm = UtilRunPrePlan(mscsShopperId)

String promotionCode = orderRow.getEpp_id();
int freeShipping = 0;
if (promotionCode != "")
{	
	EppPromotionRow eppPromotionRow = (EppPromotionRow)request.getAttribute(Constants.ATTR_EPP_PROMOTION);
    
  //  if rsEpp.eof then
    //    mscsOrderForm.[_Basket_Errors].Add (Application("MSCSMessageManager").GetMessage("pur_badepp"))
   // else
       // dfsPercent = rsEpp("dfs_p_contrib")
       // dfsDollar = rsEpp("dfs_d_contrib")
      //  corPercent = rsEpp("cor_p_contrib")
      //  corDollar = rsEpp("cor_d_contrib")
        freeShipping = eppPromotionRow.getFree_shipping();
   // end if
}

//checkoutURL = pageSURL("checkout.asp")

//nItemCount = mscsOrderForm.Items.Count

//strDownlevelURL = pageSURL("checkout2.asp")

//strPostURL      = request.getContextPath() + "/checkout2.do?method=submitCheckout";
%>

<%
if (pageName != "")
{
	String displayName =  "Agent Tool";
%>
	<SCRIPT LANGUAGE="JavaScript">
	document.title = "<%= displayName + " | " + pageName %>";
	</SCRIPT>
<%	
}
%>	
		<link rel="SHORTCUT ICON" href="images/dfs.ico">
			<link rel="stylesheet" href="dfsstyle.css" type="text/css">
				<SCRIPT LANGUAGE="JavaScript">
				function buildPath(action, shopperID, name, agent_level)
				{
					//checkout

					var redirect = 'shopper.do?method=prepareCheckout&shopper_new=' + shopperID + '&shopas=shopas&lg=lg&section=' + action +'&shopper_name=' + name + '&agent_level=' + agent_level;
					
					document.location = redirect;	
				}
				
				//Number Validation
				function isNumberKey(obj ,evt,allowDecimal)
				{							
					 var key;
					 var isCtrl = false;
					 var keychar;
					 var reg;
					  
					 if (window.event) {
					  key = evt.keyCode;
					  isCtrl = window.event.ctrlKey;
					 }
					 else if (evt.which) {
					  key = evt.which;
					  isCtrl = evt.ctrlKey;
					 }
					 
					 if (isNaN(key)) {
					  return true;
					 }
					 
					 keychar = String.fromCharCode(key);
					 
					 //check for backspace or delete, or if Ctrl was pressed
					 if (key == 8 || isCtrl) {
					  return true;
					 }

					 reg = /\d/;					 
					 var isFirstD = allowDecimal ? keychar == '.' && obj.value.indexOf('.') == -1 : false;
					 
					 return isFirstD || reg.test(keychar);
				}
				
function submitForm(arrCount) {	
    // must select one shipment method
		<% 	if (usingManager) { %>
		updShip(document.payinfo, arrCount);	
		if (document.payinfo.shipvia[arrCount].checked) 
				{
					
					if (check_shipterms(document.payinfo)) 
					{
						dialogOpen();
						document.payinfo.submit() ;
					}
				} 
				else 
				{
					dialogOpen();
					document.payinfo.submit() ;
				}
		<% } else { %>
			dialogOpen();
			document.payinfo.submit() ;
			<% } %>		
}

function check_shipterms(checkForm){
	<% if (usingManager) { %>	
	  if (price_check(checkForm.ship_cost.value,"Shipping Cost",checkForm.ship_cost)) 
	  {
	    	if ((checkForm.ship_terms.value == "Description of Other Shipping") || (checkForm.ship_terms.value == "")) {
	      		alert("Please Describe the Shipping Terms");
	      return false;
	  }
	    return true;
	  }
	  return false;
	<% } else %>
	  return true;
}

function price_check(adjPrice,fieldName,input_object){
	var bSigned = false;
	var sSigned = "(\\$?-?|-?\\$?)";
	var re = new RegExp("^"+((bSigned)?sSigned:"\\$?")+"((\\d{1,3})*(,?\\d{3})*\\.?\\d{1,100}|\\d{1,3}(,?\\d{3})*\\.?(\\d{1,100})?)$");
	
	if (re.test(adjPrice)){		
		input_object.value = formatCurrency(adjPrice);
		return true; } 
	else {
		alert('Invalid ' + fieldName + ' Entry.');
		input_object.focus();
		return false;
	}
}

function changeSelection(theForm) {
	  theForm.shipvia[0].checked=true;
}



<%
if (usingManager)
{
%>

function formatCurrency(num) {
	  num = num.toString().replace(/\$|\,/g,'');
	  if(isNaN(num)) return num; 

	  isNeg = false;
	  if(num < 0) {
	    num = num * -1;
	    isNeg = true;
	  }
	  cents = Math.floor((num*100+0.5)%100);
	  num = Math.floor((num*100+0.5)/100).toString();
	  if(cents == 0) cents = "0";
	  if(cents < 10) cents = "0" + cents;
	  for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	  num = num.substring(0,num.length-(4*i+3))+','+num.substring(num.length-(4*i+3));
	  num = "$" + num + "." + cents
	  if (isNeg) {
	    num = '-' + num;
	  }
	  return (num);
}

function updShip(theForm, arrCount) {
	if (theForm.shipvia[arrCount].checked) {
		var Item = theForm.ship_terms_vis.selectedIndex;
		theForm.ship_terms.value = theForm.ship_terms_vis[Item].value;	
	} else {
		theForm.ship_terms.value = "";
	}
}

function changeSelection(theForm)
{
	if (theForm.ship_terms.value == "")
	{
		theForm.shipvia[0].checked = true;
	}
	else
	{
		theForm.shipvia[1].checked = true;
	}
}
<% } %>
</SCRIPT>




		<form NAME="payinfo" ID="payinfo" ACTION="checkout2.do?method=submitCheckout" METHOD="POST" TARGET="_parent">
			<%= fontHeader %> Shipping Method<%= fontHeaderEnd %>
			<table WIDTH="100%" BORDER="0">
				<tr>
					<td><%= fontGeneric %>Please select a shipping method for ship to zip code
						<%= customer.getShip_to_postal() %>.</font>
					</td>
				</tr>
			</table>
			<br>
			<table WIDTH="100%" CELLPADDING="2" CELLSPACING="0" VALIGN="TOP" border="0">
				<tr>					
					<th colspan="2" ALIGN="LEFT">
					<%= fontResultsHead  %>Shipping Cost<%= fontResultsHeadEnd %>
					</th>
				</tr>
			</table>
			<% 
			StringBuilder tmp = new StringBuilder(); 
			
			
			boolean airborneOnly = true;
			
			tmp.append("<table>");
			
			try
			{
			for(int count = 0; count < 10;count++)
			{
			    boolean thisFreeShipping = false;
			    arrCount = (Integer.valueOf(arrayShipping[count][3].toString()) > arrCount)?Integer.valueOf(arrayShipping[count][3].toString()):arrCount;
			    			    
				if (!String.valueOf(arrayShipping[count][0]).equalsIgnoreCase(""))
				{
					//code old check && ((session.getAttribute(Constants.IS_CUSTOMER)==null) && !rightToDisplayOptionFree(agent.isAdmin(),agent.getUserLevel()))
					// code new  ((session.getAttribute(Constants.IS_CUSTOMER)!=null)
					// fix by TienDang							
					if (String.valueOf(arrayShipping[count][0]).equalsIgnoreCase("FREE Shipping") && (session.getAttribute(Constants.IS_CUSTOMER)!=null))
							continue;
					
					tmp.append("<tr><td>" + fontGeneric + " " + String.valueOf(arrayShipping[count][0]) + "</font></td>");
					tmp.append("\n" + "<td>" + fontGeneric);
		            if ((freeShipping == 1) && (String.valueOf(arrayShipping[count][2])).equalsIgnoreCase("UPS Ground"))  
		                thisFreeShipping = true;
		            else if ((freeShipping == 4) && (String.valueOf(arrayShipping[count][2])).equalsIgnoreCase("Airborne Next Day"))              
		                thisFreeShipping = true;
		            else if ((freeShipping == 5) && (String.valueOf(arrayShipping[count][2])).equalsIgnoreCase("Airborne 2nd Day"))             
		                thisFreeShipping = true;
		            
		            String aa = arrayShipping[count][1].toString();
		            if ((thisFreeShipping) || ((Float.valueOf(arrayShipping[count][1].toString())) <= 0))
		            	tmp.append("<font color=\"Red\">FREE</font></td>");
		            else 
		            {		            	
		            	tmp.append("<input type=\"hidden\" value=\"" + new Float(arrayShipping[count][1].toString()) + "\" name=\"" + String.valueOf(arrayShipping[count][0]) + "_cost\">");
		            	tmp.append(Constants.FormatCurrency(new Float(arrayShipping[count][1].toString())) + "</td>");
		            }
		            
		            tmp.append("<td>" + fontGeneric + "<input type=\"hidden\" value=\"" + count + "\" name=\"" + String.valueOf(arrayShipping[count][0]) + "\">");
		           		
		            if (count == 1) 
		            { 
						airborneOnly = false;
						tmp.append("<input style=\"width:170;\" width=\"250\" type=\"radio\" value=\"" + String.valueOf(arrayShipping[count][0]) + "\" checked name=\"shipvia\"");
		            }
		            else if ((count == 4) && airborneOnly)
		            	tmp.append("<input style=\"width:170;\" width=\"250\" type=\"radio\" value=\""+ String.valueOf(arrayShipping[count][0])+ "\" checked name=\"shipvia\"");
		          
		            else if (!Constants.isEmpty(orderRow.getShip_method()) && (Integer.valueOf(orderRow.getShip_method()) == count))
		            	tmp.append("<input style=\"width:170;\" width=\"250\" type=\"radio\" value=\""+ String.valueOf(arrayShipping[count][0])+ "\" checked name=\"shipvia\"");			           
		            else
		            	
		            	tmp.append("<input style=\"width:170;\" width=\"250\" type=\"radio\" value=\""+ String.valueOf(arrayShipping[count][0]) +"\" name=\"shipvia\""); 
		           					
		            tmp.append("></font></td></tr>");
				
			   }
			}
			
			  if ((session.getAttribute(Constants.IS_CUSTOMER)==null) && rightToDisplayOptionFree(agent.isAdmin(),agent.getUserLevel())) 
			  {

			//if (usingManager)
			//{		
				tmp.append("<tr><td valign=\"top\">" + fontGeneric + "OTHER Shipping</font></td><td>"+ fontGeneric);
				
				tmp.append("<input style=\"width:180;\" width=\"260\" type=\"hidden\" value=\"" + orderRow.getShip_terms() + "\" name=\"ship_terms\">");
				//tmp.append("<select style=\"width:180;\" width=\"260\" name=\"ship_terms_vis\" onchange=\"changeSelection(document.payinfo);\">");
				tmp.append("<select style=\"width:180;\" width=\"260\" name=\"ship_terms_vis\">");

				String selected = "";				
				if (orderRow.getShip_terms().equalsIgnoreCase(""))
					selected = "selected";				
				tmp.append("<OPTION VALUE=\"\"" + selected + " >");
				
				if (orderRow.getShip_terms().equalsIgnoreCase("Optional Ground / Item"))
					selected = "selected";
				else
					selected = "";
				tmp.append("<OPTION VALUE=\"Optional Ground / Item\"" + selected + " >Optional Ground / Item");
				
				if (orderRow.getShip_terms().equalsIgnoreCase("Optional Next Day / Item"))
					selected = "selected";
				else
					selected = "";
				tmp.append("<OPTION VALUE=\"Optional Next Day / Item\"" + selected + " >Optional Next Day / Item");
				
				if (orderRow.getShip_terms().equalsIgnoreCase("Optional 2 Days / Item"))
					selected = "selected";
				else
					selected = "";
				tmp.append("<OPTION VALUE=\"Optional 2 Days / Item\"" + selected + " >Optional 2 Days / Item");
				
				if (orderRow.getShip_terms().equalsIgnoreCase("Pallet Shipping / Item"))
					selected = "selected";
				else
					selected = "";
				tmp.append("<OPTION VALUE=\"Pallet Shipping / Item\"" + selected + " >Pallet Shipping / Item");
				
				if (orderRow.getShip_terms().equalsIgnoreCase("Will Call"))
					selected = "selected";
				else
					selected = "";
				tmp.append("<OPTION VALUE=\"Will Call\"" + selected + " >Will Call");
				
				tmp.append("</select>");

				tmp.append("<br>Description of Other Shipping</font></td>" + "<td>" + fontGeneric);
								
	 			if ((orderRow.getShip_terms().equalsIgnoreCase("Pallet Shipping / Item")) || (orderRow.getShip_terms().equalsIgnoreCase("Will Call")) || (orderRow.getShip_terms().equalsIgnoreCase("Optional Ground / Item")) || (orderRow.getShip_terms().equalsIgnoreCase("Optional Next Day / Item")) || (orderRow.getShip_terms().equalsIgnoreCase("Optional 2 Days / Item")))
	 			{
	 				tmp.append("<input style=\"width:170;\" width=\"250\" type=\"radio\" value=\"OTHER Shipping\" checked name=\"shipvia\"></font></td> <td>" + fontGeneric);
	 				tmp.append("<input type=\"hidden\" value=\"0\" name=\"OTHER Shipping\"><input style=\"text-align:right;\" size=\"8\" type=\"text\" value=\"" + orderRow.getShip_cost() + "\" name=\"ship_cost\" id=\"ship_cost\" onkeypress=\"return isNumberKey(this,event,true);\" onblur=\"this.value = formatCurrency(this.value)\">");
	 			}
	 			else
	 			{
	 				tmp.append("<input style=\"width:170;\" width=\"250\" type=\"radio\" value=\"OTHER Shipping\" name=\"shipvia\"></font></td> <td>" + fontGeneric);
	 				tmp.append("<input type=\"hidden\" value=\"0\" name=\"OTHER Shipping\"><input style=\"text-align:right;\" size=\"8\" type=\"text\" value=\"0\" name=\"ship_cost\" id=\"ship_cost\" onkeypress=\"return isNumberKey(this,event,true);\" onblur=\"this.value = formatCurrency(this.value)\">");
	 			}
				
				tmp.append("<br>Price</font></td></tr>");
			}
			
			tmp.append("</table>" + "\n");
			
			
			out.println(tmp.toString());
			} catch(Exception e)
			{
				System.out.println(e);
			}
			%>
			<br>
			<br>
			<table BORDER="0">
				<tr>
					<td align="right" valign="top"><a href="javascript:submitForm(<%=arrCount%>)"><img border="0" width="15" height="15" src="images/item.gif"></a></td>
					<td align="right"><a href="javascript:this.focus(); submitForm(<%=arrCount%>)">Continue Checkout</a></td>
				</tr>
			</table>
			<br>
			<%-- <table width="500" border="0">
				<tr>
					<td>
						<%= fontGeneric %>
						* Actual shipping charges will be provided during final checkout.You will have 
						the option to modify your shipping option or cancel your order.
					</td>
				</tr>
			</table> --%>
			<br>
			</td> 
			</tr>
		</form>

<%@include file="/html/scripts/dialogScript.jsp"%>					
		<% } %>
		
</div>

 
 