<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Date"%>
<%@page import="com.dell.enterprise.agenttool.model.SiteReferral"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.util.Converter" %>
<%@page import="com.dell.enterprise.agenttool.util.Constants" %>
<%@page import="com.dell.enterprise.agenttool.services.CheckoutService"%>
<%@page import="com.dell.enterprise.agenttool.model.EstoreBasketItem"%>

<div>

<style type="text/css"> 
.disabled 
{ 
 background-color: #CCC; 
} 

input.readOnlyTextBox
{
    background-color: #F2F2F2 !important;
    color: #C6C6C6;
    border-color: #ddd;
}
</style> 

<%
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);

String requiredFlag1_start = "<B><strong> ";
String requiredFlag1_end = "</strong></B>";
String fontGeneric = "<font face=\"Arial, Helvetica\" size=\"2\">";

String bill_to_fname = "";
String bill_to_lname = "";
String bill_to_phone = "";
String bill_to_company = "";
String bill_to_title = "";
String bill_to_address1 = "";
String bill_to_address2 = "";
String bill_to_city = "";
String bill_to_state = "";
String bill_to_country = "";
String bill_to_postal = "";
String bill_to_fax = "";
String bill_to_phoneext = "";
String bill_to_email = "";
String credit_comment = "";
String credit_available = "";
String credit_line = "";
String credit_expire = "";

String ship_to_state = "";
String ship_to_phone = "";
String ship_to_lname = "";
String ship_to_fname = "";
String ship_to_company = "";
String ship_to_country = "";
String ship_to_address1 = "";
String ship_to_title = "";
String ship_to_phoneext = "";
String ship_to_address2 = "";
String ship_to_city = "";
String ship_to_postal = "";
String ship_to_email = "";
String tax_exempt_expire = "";
String tax_exempt_number = "";  
Integer tax_exempt = 0; 
Integer promo_email = 0;

String loginID = "";
String hint = "";
String password = "";
String answer = "";

int latperdiscount = 0; 
int latamtdiscount = 0; 
String latexpdate = "";
int insperdiscount = 0; 
int insamtdiscount = 0; 
String insexpdate = "";
int optperdiscount = 0; 
int optamtdiscount = 0; 
String optexpdate = "";
int dimperdiscount = 0; 
int dimamtdiscount = 0; 
String dimexpdate = "";
int monperdiscount = 0; 
int monamtdiscount = 0; 
String monexpdate = "";
int serperdiscount = 0; 
int seramtdiscount = 0; 
String serexpdate = "";
int workperdiscount = 0; 
int workamtdiscount = 0; 
String workexpdate = "";
int Storagediscount = 0; 
int Stotamtdiscount = 0; 
String Storxpdate = "";

Agent agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
String agent_level = "";
if (agent != null)
	 agent_level = (agent.getUserLevel() == null)?"":agent.getUserLevel();

Boolean usingManager = false;

if (session.getAttribute(Constants.IS_ADMIN) != null)
 		usingManager = (Converter.stringToBoolean(session.getAttribute(Constants.IS_ADMIN).toString()) || agent_level.equalsIgnoreCase("A"));

String section = (request.getAttribute("section") == null)?"":request.getAttribute("section").toString();
Customer customer = (Customer)request.getAttribute("CUSTOMER_CHECKOUT");
String account_type = "";
if (customer != null)
{
	account_type = customer.getAccount_type();
	bill_to_fname = customer.getBill_to_fname();
	bill_to_lname = customer.getBill_to_lname();
	bill_to_phone = customer.getBill_to_phone();
	bill_to_company = customer.getBill_to_company();
	bill_to_title = customer.getBill_to_title();
	bill_to_address1 = customer.getBill_to_address1();
	bill_to_address2 = customer.getBill_to_address2();
	bill_to_city = customer.getBill_to_city();
	bill_to_state = customer.getBill_to_state();
	bill_to_country = customer.getBill_to_country();
	bill_to_postal = customer.getBill_to_postal();

	bill_to_fax = customer.getBill_to_fax();
	bill_to_phoneext = customer.getBill_to_phoneext();
	bill_to_email = customer.getBill_to_email();
 	credit_comment = customer.getCreditcomment();
 	credit_available =  Converter.getMoney(customer.getCreditavail());
 	credit_line = Converter.getMoney(customer.getCreditline());
 	credit_expire = customer.getCreditexp();

 	ship_to_phone = customer.getShip_to_phone();
 	ship_to_lname = customer.getShip_to_lname();
 	ship_to_fname = customer.getShip_to_fname();
 	ship_to_company = customer.getShip_to_company();
 	ship_to_country = customer.getShip_to_country();
 	ship_to_state = customer.getShip_to_state();
 	ship_to_email = customer.getShip_to_email();
 	ship_to_address1 = customer.getShip_to_address1();
 	ship_to_title = customer.getShip_to_title();
 	ship_to_phoneext = customer.getShip_to_phoneext();
 	ship_to_address2 = customer.getShip_to_address2();
 	ship_to_city = customer.getShip_to_city();
 	ship_to_postal = customer.getShip_to_postal();
 	tax_exempt_expire = (customer.getTax_exempt_expire() == null)?"":Converter.dateDBToDate(customer.getTax_exempt_expire());
// 	String aa = customer.getTax_exempt_expire().toLocaleString();
 	//String aa1 = customer.getTax_exempt_expire().toGMTString();
 	tax_exempt_number = customer.getTax_exempt_number();  
 	tax_exempt = customer.getTax_exempt(); 
 	promo_email = customer.getPromo_email();

 	loginID = customer.getLoginID();
 	hint = customer.getHint();
 	password = customer.getPassword();
 	answer = customer.getAnswer();
 	
 	latperdiscount =  customer.getLatperdiscount();
 	latamtdiscount  = customer.getLatamtdiscount();
 	latexpdate     = reformatDateTime(customer.getLatexpdate());
 	insperdiscount  = customer.getInsperdiscount();
 	insamtdiscount  = customer.getInsamtdiscount();
 	insexpdate      = reformatDateTime(customer.getInsexpdate());
 	optperdiscount  = customer.getOptperdiscount();
 	optamtdiscount  = customer.getOptamtdiscount();
 	optexpdate      = reformatDateTime(customer.getOptexpdate());
 	dimperdiscount  = customer.getDimperdiscount();
 	dimamtdiscount  = customer.getDimamtdiscount();
 	dimexpdate      = reformatDateTime(customer.getDimexpdate());
 	monperdiscount  = customer.getMonperdiscount();
 	monamtdiscount  = customer.getMonamtdiscount();
 	monexpdate      = reformatDateTime(customer.getMonexpdate());
 	serperdiscount  = customer.getSerperdiscount();
 	seramtdiscount  = customer.getSeramtdiscount();
 	serexpdate      = reformatDateTime(customer.getSerexpdate());
 	workperdiscount = customer.getWorperdiscount();
 	workamtdiscount = customer.getWoramtdiscount();
 	workexpdate     = reformatDateTime(customer.getWorexpdate());
}

String mscsShopperId = "mscsShopperId";
String fontResults = "<font face=\"Arial, Helvetica\" size=\"1\">";
String fontResultsEnd = "</font>";
String fontGenericSmallRed = "<font face=\"Arial, Helvetica\" size=\"1\" color=\"red\">";

session = request.getSession();

CheckoutService checkoutService = new CheckoutService();
session.setAttribute("maxDiscount",checkoutService.getMaxDiscount());
//Ko biet, thay code cu hard-code
session.setAttribute("maxDiscountAmt",2000);


List<EstoreBasketItem> basketItemCheck =  (List<EstoreBasketItem>) request.getAttribute(Constants.ATTR_ITEM_BASKET);
Boolean checkShopper = (request.getAttribute(Constants.IS_SHOPPER) == null)?false:(Boolean)request.getAttribute(Constants.IS_SHOPPER);
Boolean isCreatingCustomer = ((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout"))); 
Boolean isEditCustomer =   ((request.getParameter("manage") != null) && request.getParameter("manage").equals("true"));

if ((!checkShopper) && (basketItemCheck == null || basketItemCheck.size() == 0) && !isCreatingCustomer && !isEditCustomer)
{
%>
<br/>
<br/>
<img src="images/your_cart.gif" alt="Customer Lookup">
<blockquote>
	<font size="2" face="Arial, Helvetica">
		<strong>Your cart is empty.</strong>
	</font>
</blockquote>
<%    
}else
{
%>



<%!

	boolean rightToEditBillInfo(boolean isAdmin, String userLevel)
	{
		boolean result = false;
		if (isAdmin)
			result = true;
		else
		{
			if (userLevel.equalsIgnoreCase("A"))
					result = true;
			else if (userLevel.equalsIgnoreCase("B"))
					result = true;
			else if (userLevel.equalsIgnoreCase("C"))
					result = false;					
		}
	
		return result;
	}

	boolean rightToEditDiscountInfo(boolean isAdmin, String userLevel)
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
	
	boolean rightToEditUserInfo(boolean isAdmin, String userLevel)
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

	String rtrim(String str) {
		if (str == null)
			return null;
		if (str.length() < 1)
			return "";
		int i = (str.length() - 1);
		while ((i >= 0) && (str.charAt(i) == ' '))
			i--;

		return str.substring(0, i + 1);
	}

	String calculateExpiredDate(int numberOfDay) {
		String DATE_FORMAT = "MM/dd/yyyy";
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				DATE_FORMAT);
		java.util.Calendar c1 = java.util.Calendar.getInstance();
		c1.add(java.util.Calendar.DATE, numberOfDay);

		return sdf.format(c1.getTime());
	}

	int compareDate(String date1, Date date2) {
		java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			// Get Date 1
			d1 = df.parse(date1);

			// Get Date 2
			d2 = df.parse(df.format(date2));
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}

		return d1.compareTo(d2);
		/*   String relation;
		if (d1.equals(d2))
		   relation = "the same date as";
		else if (d1.before(d2))
		   relation = "before";
		else
		   relation = "after";
		 */
	}
	
	String reformatDateTime(Date date) {
		String result = "";
		if (date != null)
		{
			try {
				java.text.DateFormat df = new java.text.SimpleDateFormat(
						"MM/dd/yyyy");		
				result = df.format(date);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	String reformatDateTime(String date) {
		String result = "";
		try {
			java.text.DateFormat df = new java.text.SimpleDateFormat(
					"MM/dd/yyyy");
			java.util.Date today = df.parse(date);
			result = df.format(today);
		} catch (java.text.ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	String discountTable(String category, String family, int perDiscount,
			int amtDiscount, String expDate, int index, String agent_level) {
		StringBuilder htmlCode = new StringBuilder();
		htmlCode.append("<TR><td WIDTH = \"15%\">&nbsp;</td>");
		String fontGeneric = "<font face=\"Arial, Helvetica\" size=\"2\">";
		
		if (agent_level.equalsIgnoreCase("A")) 
		{
			htmlCode.append("<td>" + fontGeneric);
			htmlCode.append("\n");
			htmlCode.append("<input type=\"checkbox\" name=\""+ family.substring(0, 3).toLowerCase() + "_pd\" "
			    			+ "id=\""+ family.substring(0, 3).toLowerCase() + "_pd\"");
			htmlCode.append(" value=\"" + family.substring(0, 3).toLowerCase() + "\"");

			if ((expDate != "") && (compareDate(expDate, new Date()) >= 0)) 
			{
				htmlCode.append(" checked");
				htmlCode.append(" onClick=\"DefaultDate(document.NewUser, " + index + "),enableDiscountItem('"+family.substring(0, 3).toLowerCase()+"')\">");
				htmlCode.append("\n");
				if (family != category)
					htmlCode.append("<label for=discount>" + family + " "+ category + "</FONT></td>");
				else
					htmlCode.append("<label for=discount>" + family	+ "</FONT></td>");
				
				String strPerDiscount = "";
				if(perDiscount != 0)
				    strPerDiscount = Integer.toString(perDiscount);
			
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"2\" STYLE=\"WIDTH:20\" "
				    +"NAME=\""+ family.substring(0, 3).toLowerCase()+ "perdiscount\" "
				    +"id=\""+ family.substring(0, 3).toLowerCase()+ "perdiscount\" "
				    +"VALUE=\""
					+ strPerDiscount
					+ "\"></td>");
				
		
				String strAmtDiscount = "";
				if (amtDiscount != 0)
				    strAmtDiscount = Integer.toString(amtDiscount);
				
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"3\" STYLE=\"WIDTH:28\" "
				    	+ "NAME=\"" + family.substring(0, 3).toLowerCase()+ "amtdiscount\" "
				    	+ "id=\"" + family.substring(0, 3).toLowerCase()+ "amtdiscount\" "
				    	+ " VALUE=\""+ strAmtDiscount
						+ "\"></td>");

				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"10\" STYLE=\"WIDTH:70\"" 
				    			+ "NAME=\""	+ family.substring(0, 3).toLowerCase()+ "expdate\""
				    			+ "id=\""	+ family.substring(0, 3).toLowerCase()+ "expdate\""
				    			+ "VALUE=\""+ reformatDateTime(expDate) + "\" readonly></td>");
				htmlCode.append("<td WIDTH = \"25%\">&nbsp;</td>");
			} else {
				htmlCode.append(" onClick=\"DefaultDate(document.NewUser, " + index + "),enableDiscountItem('"+family.substring(0, 3).toLowerCase()+"')\">");
				if (family != category)
					htmlCode.append("<label for=discount>" + family + " "+ category + "</FONT></td>");
				else
					htmlCode.append("<label for=discount>" + family + "</FONT></td>");

				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT disabled=\"disabled\" TYPE=\"TEXT\" MAXLENGTH=\"2\" STYLE=\"WIDTH:20\" " 
				    			+ "id=\"" + family.substring(0, 3).toLowerCase()+ "perdiscount\""
				    			+ "NAME=\"" + family.substring(0, 3).toLowerCase()+ "perdiscount\"></td>");
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT disabled=\"disabled\" TYPE=\"TEXT\" MAXLENGTH=\"3\" STYLE=\"WIDTH:28\" "
				    			+ "id=\""	+ family.substring(0, 3).toLowerCase()+ "amtdiscount\""
				    			+ "NAME=\""	+ family.substring(0, 3).toLowerCase()+ "amtdiscount\" ></td>");
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"10\" STYLE=\"WIDTH:70\"" 
				    			+ "id=\""+ family.substring(0, 3).toLowerCase() + "expdate\" "
				    			+ "NAME=\""+ family.substring(0, 3).toLowerCase() + "expdate\" readonly></td>");
				htmlCode.append("<td WIDTH = \"25%\">&nbsp;</td>");
			}
		} else {
			htmlCode.append("<td>" + fontGeneric + "<input type=\"hidden\" name=\""+ family.substring(0, 3).toLowerCase() + "_pd\"");
			htmlCode.append(" value=\"" + family.substring(0, 3).toLowerCase() + "dis\"");

			if ((expDate != "") && (compareDate(expDate, new Date()) >= 0)) {
				htmlCode.append(" checked");
				htmlCode.append(" onClick=\"DefaultDate(document.NewUser, " + index + ")\"");
				if (family != category)
					htmlCode.append("ID=\"Checkbox1\">	<label for=discount>" + family + " " + category + "</FONT></td>");
				else
					htmlCode.append("<label for=discount>" + family + "</FONT></td>");
				if (perDiscount != 0)
					htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"2\" STYLE=\"WIDTH:20\" NAME=\""
									+ family.substring(0, 3).toLowerCase()
									+ "perdiscount\" VALUE=\""
									+ perDiscount
									+ "\" ID=\"Text1\" readonly></td>");
				else
					htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"2\" STYLE=\"WIDTH:20\" NAME=\""
									+ family.substring(0, 3).toLowerCase()
									+ "perdiscount\" ID=\"Text2\" readonly></td>");
				if (amtDiscount != 0)
					htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"3\" STYLE=\"WIDTH:28\" NAME=\""
									+ family.substring(0, 3).toLowerCase()
									+ "amtdiscount\" VALUE=\""
									+ amtDiscount
									+ "\" ID=\"Text3\" readonly></td>");
				else
					htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"3\" STYLE=\"WIDTH:28\" NAME=\""
									+ family.substring(0, 3).toLowerCase()
									+ "amtdiscount\" ID=\"Text4\" readonly></td>");
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"10\" STYLE=\"WIDTH:70\" NAME=\""
								+ family.substring(0, 3).toLowerCase()
								+ "expdate\" VALUE=\""
								+ reformatDateTime(expDate)
								+ "\" ID=\"Text5\" readonly></td>");
				htmlCode.append("<td WIDTH = \"25%\">&nbsp;</td>");
			} else {
				htmlCode.append(" onClick=\"DefaultDate(document.NewUser, "
						+ index + ")\">");
				if (family != category)
					htmlCode.append("<label for=discount>" + family + " " + category + "</FONT></td>");
				else
					htmlCode.append("<label for=discount>" + family + "</FONT></td>");

				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"2\" STYLE=\"WIDTH:20\" NAME=\""
								+ family.substring(0, 3).toLowerCase()
								+ "perdiscount\" ID=\"Text6\" readonly></td>");
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"3\" STYLE=\"WIDTH:28\" NAME=\""
								+ family.substring(0, 3).toLowerCase()
								+ "amtdiscount\" ID=\"Text7\" readonly></td>");
				htmlCode.append("<td ALIGN=\"CENTER\"><INPUT TYPE=\"TEXT\" MAXLENGTH=\"10\" STYLE=\"WIDTH:70\" NAME=\""
								+ family.substring(0, 3).toLowerCase()
								+ "expdate\" ID=\"Text8\" readonly></td>");
				htmlCode.append("<td WIDTH = \"25%\">&nbsp;</td>");
			}
		}
		htmlCode.append("</TR>");
		return htmlCode.toString();
	}
%>



<%!String pageSURL(String page) {
		return page;
	}

	String baseSURL(String page) {
		return page + "?method=checkout";
	}%>

<%
 // strLG = request.querystring("lg")
  //fromPage = request.servervariables("http_referer")
 // toolsPage = application("tools_url") & "/" & application("tools_app") & "/shopper-db/shopper_edit.asp"

  /*IF (  (INSTR(fromPage, rootSURL&"login")    = 0) AND _ 
	(INSTR(fromPage, rootSURL&"checkout") = 0) AND _
        (INSTR(fromPage, rootSURL&"hint_lookup.asp")  = 0) AND _
        (INSTR(fromPage, rootSURL&"cust_lookup.asp")  = 0) AND _
        (INSTR(fromPage, toolsPage)           = 0)) OR _
     (  (INSTR(fromPage, rootSURL&"login") > 0)       AND _
	(NOT strLG="lg" AND NOT (section = "new" OR section = "new_checkout"))) OR _
     (  (INSTR(fromPage, rootSURL&"cust_lookup.asp") > 0)   AND _
	(NOT strLG="lg" AND NOT (section = "checkout" OR section = "new_checkout"))) OR _
     (  (INSTR(fromPage, toolsPage) > 0)    AND _
	(NOT request.querystring("editas")="editas")) OR _
     (  (INSTR(fromPage, rootSURL&"hint_lookup.asp") > 0) AND _
	(NOT request.form("access")="granTed" AND NOT (section = "manage"))) THEN
	IF not section="new_register" then
		IF section="" OR IsNull(section) OR IsEmpty(section) THEN
			response.redirect(baseSURL("login.asp") & "section=manage")
		ELSE
			response.redirect(baseSURL("login.asp") & "section=tracking")
		END IF
	END IF
  END IF*/

//	targetPage = "order"
	//subPage    = "eaccount"

String pageName;
String strHeader;
String strMessage;
String strPostURL;
Boolean boolEdit;
//String checkUpdate;

	if ((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout"))) 
	{
		
		pageName   = "New Customer Setup";
		strHeader  = "images/new_customer_setup.gif";
		strMessage = "";
		boolEdit = false;
		//strPostURL = pageSURL("xt_shopper_new.asp");
		%>
		
 		<script type="text/javascript">
		$('document').ready(function() {
			$('.divCheck').hide();
		});
		</script>
		<%
		
		strPostURL = request.getContextPath() + "/shopper.do?method=addNewCustomer";
	}
	else if (section.equalsIgnoreCase("new_register"))
	{
		pageName   = "New User Registration";
		strHeader  = "images/new_user_registration.gif";
		strMessage = "<table width=\"60%\"><tr><td><font size=\"2\">Complete the information below to register on the DFS Direct Sales web site.  Registration is simple and free.  Benefits of registration include periodic e-mails about sales specials, special events, and faster checkout when you are ready to purchase.</font></td></tr></table>";
		boolEdit = false;
		//strPostURL = pageSURL("xt_shopper_new.asp");
		strPostURL = pageSURL("xt_shopper_new.asp");
	}
	else
	{
		pageName   = "Customer Information";
		if (!usingManager)
		{
			strMessage = "<table width=\"50%\"><tr><td><font size=\"2\">Below is our record of your billing, shipping, and payment information.  Please verify and make any necessary changes.</font></td></tr></table>";
			strHeader  = "images/customer_information.gif";
		}
		else
		{
	 		strMessage = "";
			strHeader  = "images/customer_information.gif";
		}
		boolEdit = true;
		 /* if(session.getAttribute("editShopper") == "editShopper"){
			
	 		<script type="text/javascript">
			$('document').ready(function() {
				$('.divCheck').hide();
			});
			</script>
			
		}  */
 		/* <SCRIPT LANGUAGE="JavaScript" type="text/javascript">
		$('document').ready(function() {
			$("#BillingInformationTable :input").attr('disabled','true');
			$("#ShippingTable :input").attr('disabled','true');
			
			$('#changeProfile').change(function (){
				if($('#changeProfile').is(':checked')){
					$("#BillingInformationTable :input").removeAttr('disabled');
					$('#ShippingTable :input').removeAttr('disabled');
					
				}else{
					$("#BillingInformationTable :input").attr('disabled','true');
					$("#ShippingTable :input").attr('disabled','true');
				}
				
			});
		});
		</SCRIPT> */ 
		
		//strPostURL = baseSURL("xt_shopper_update.asp");
		strPostURL = request.getContextPath() +  baseSURL("/shopper.do");
		
		
		/* checkUpdate = request.getAttribute("check").toString();
		System.out.println(checkUpdate); */
		// + mscsPage.URLShopperArgs();

		/*REM Run the basic plan
		Dim mscsOrderForm
		Dim nItemCount
		
		call UtilGetShopper(mscsOrderForm, mscsShopperId)
		call UtilGetDefaults(mscsOrderForm)

		
		specialty_discount = mscsOrderForm.specialty_discount
		tax_exempt         = mscsOrderForm.tax_exempt
		tax_exempt_number  = mscsOrderForm.tax_exempt_number
		tax_exempt_expire  = mscsOrderForm.tax_exempt_expire

		cc_type            = mscsOrderForm.cc_type
		cc_name            = mscsOrderForm.cc_name
		cc_number          = mscsOrderForm.cc_number
		cc_expmonth        = mscsOrderForm.cc_expmonth
		cc_expyear         = mscsOrderForm.cc_expyear

		loginID      = mscsOrderForm.loginID
		password     = mscsOrderForm.password
		hint         = mscsOrderForm.hint
		answer       = mscsOrderForm.answer

		promo_email  = mscsOrderForm.promo_email
	
		credit_line		   = mscsOrderForm.creditline
		credit_expire	   = mscsOrderForm.creditexp
		credit_available   = mscsOrderForm.creditavail
		credit_comment	   = mscsOrderForm.creditcomment*/
	}
%>

<%
if (pageName != "")
{
	
%>
	<SCRIPT LANGUAGE="JavaScript">
	document.title = "<%= pageName %>";
	$('#btnContinue').attr('disabled', false);
	
	</SCRIPT>
<%	
}
%>		

<%
if(session.getAttribute(Constants.IS_CUSTOMER)!=null)
{
	
%>
	<SCRIPT LANGUAGE="JavaScript">
	$('document').ready(function() {
	 	//alert('aaa');
		//$('#changeProfile').detach();
		$('.divCheck').detach();
	});
	</SCRIPT>
<%	
}
%>
		
	<TABLE WIDTH="50%" CELLSPACING="0" CELLPADDING="3" BORDER="0" ALIGN="LEFT">
		<TR>
			<TD>
				<%= strMessage %>
				
				<FORM METHOD=POST ACTION="<%= strPostURL %>" NAME="NewUser" ID="NewUser">
				<% request.setAttribute("section",section);  %>				
<!-- START OF SHOPPER FORM -->
<!-- START OF SHOPPER FORM -->
<!-- START OF SHOPPER FORM -->
<!-- START OF SHOPPER FORM onSubmit="return check_Form(document.NewUser)" -->					
				
			<img src="images/spacer.gif" width="100%" height="1" border="0">
			
			<BR>
			<img src="<%=strHeader%>" alt="<%=pageName%>">

			
<div id="error_results">

</div>



<% 
		if ((strHeader.equalsIgnoreCase("images/checkout.gif")) && (!usingManager))
		{
%>


<table width="460" BORDER="0">
	<tr>
		<td valign="bottom"><%= fontGenericSmallRed %>DFS is committed to fighting online fraud by providing you with secure payment processing, dynamic user identity confirmation (AVS & CSC) and working with law enforcement.</font> 
		</td>
	</tr>
	
</table>
<%
		}
%>
<BR/><div class="divCheck"><input type="checkbox" name="changeProfile" id="changeProfile"><label><%=requiredFlag1_start%> Save Customer Profile <%=requiredFlag1_end%></label></input></div>
	
	
<TABLE id="BillingInformationTable" WIDTH="760" CELLSPACING="0" CELLPADDING="3" BORDER="0">

<TR>
<TD COLSPAN="5" ALIGN="LEFT">
<img src = "images/billing_information-small.gif" BORDER="0">
<table CELLSPACING="0" CELLPADDING="0" BORDER="0" width="750" ALIGN="LEFT">
<tr>
<td>
<%= fontGeneric %><%=requiredFlag1_start%>Bold fields are required. <%=requiredFlag1_end%></FONT>
</td>


<% if (usingManager && (section.equalsIgnoreCase("new") || section.equalsIgnoreCase("new_checkout") || section.equalsIgnoreCase("new_register")))
{
	session.setAttribute("loa","N");
%>
<td ALIGN=LEFT>
<%= fontGeneric %><a href="#" onclick="blankInfo(document.NewUser)">Enter Blank Data</a></td>
<% }%>
</tr>


</table>

</TH>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP"  NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> First Name <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="bill_to_fname" ID="bill_to_fname" VALUE="<%=bill_to_fname%>" onblur="check_len(this, 40)"></TD>
<TD WIDTH="8">&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Last Name <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="bill_to_lname" ID="bill_to_lname" VALUE="<%=bill_to_lname%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %> Title </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185"  NAME="bill_to_title" MAXLENGTH="50" ID="bill_to_title" VALUE="<%=bill_to_title%>"></TD>
<TD WIDTH="8">&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %> Company </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="100" NAME="bill_to_company" ID="bill_to_company" VALUE="<%=bill_to_company%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Address 1 <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><INPUT TYPE="TEXT" SIZE="28" MAXLENGTH="50" NAME="bill_to_address1" ID="bill_to_address1" VALUE="<%=bill_to_address1%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %> Address 2</TD>
<TD COLSPAN="4"><INPUT TYPE="TEXT" SIZE="28" MAXLENGTH="50" NAME="bill_to_address2" ID="bill_to_address2" VALUE="<%=bill_to_address2%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> City <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="bill_to_city" ID="bill_to_city" VALUE="<%=bill_to_city%>" onblur="check_len(this, 40)"></TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> State <%=requiredFlag1_end%> </TD>
<TD><%= fontGeneric %><SELECT NAME="bill_to_state" ID="bill_to_state">
<%

if (request.getAttribute("STATES") != null)
{
	out.println("<OPTION VALUE=\"\">");
	List<String> states = (List<String>)request.getAttribute("STATES");
	StringBuilder tmp = new StringBuilder(); 
     
	for(int i=0;i<states.size();i++)
    {			
		if (bill_to_state.equalsIgnoreCase(states.get(i)))
			tmp.append("<option value=\""+states.get(i)+"\" selected>"+states.get(i)+"</option>");				
		else
			tmp.append("<option value=\""+states.get(i)+"\">"+states.get(i)+"</option>");	
			
    }
	
	out.println(tmp + "</OPTION>");
}
%>
</SELECT></FONT></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Country <%=requiredFlag1_end%> </TD>
<TD><%= fontGeneric %><SELECT NAME="bill_to_country" ID="bill_to_country">
<% 
if (request.getAttribute("COUNTRIES") != null)
{
	out.println("<OPTION VALUE=\"\">");
	List<String> countries = (List<String>)request.getAttribute("COUNTRIES");
	StringBuilder tmp = new StringBuilder(); 
     
	for(int i=0;i<countries.size();i++)
    {		
		if (bill_to_country.equalsIgnoreCase(countries.get(i)))
			tmp.append("<option value=\""+countries.get(i)+"\" selected>"+countries.get(i)+"</option>");	
		else
			tmp.append("<option value=\""+countries.get(i)+"\">"+countries.get(i)+"</option>");		
    }
	
	out.println(tmp + "</OPTION>");
}

%></SELECT></FONT></TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Zip <%=requiredFlag1_end%> </TD>
<TD ALIGN="LEFT"> <INPUT TYPE="TEXT" SIZE="10" MAXLENGTH="5" NAME="bill_to_postal" ID="bill_to_postal" VALUE="<%=bill_to_postal%>"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Phone <%=requiredFlag1_end%> </TD>
<TD NOWRAP>
(<INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="3" NAME="bill_to_phone_area" ID="bill_to_phone_area" VALUE="<%= (bill_to_phone.length() > 2)?bill_to_phone.substring(0,3):"" %>">)
<INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="3" NAME="bill_to_phone_exch" ID="bill_to_phone_exch" VALUE="<%= (bill_to_phone.length() > 5)?bill_to_phone.substring(3,6):"" %>">-
<%
if (bill_to_phone.length() > 3)
{	
%><INPUT TYPE="TEXT" SIZE="8" MAXLENGTH="4" NAME="bill_to_phone" ID="bill_to_phone" VALUE="<%
	String tmp = rtrim(bill_to_phone);
	if (tmp.length() == 10)
	out.println(tmp.substring(tmp.length() - 4,tmp.length())); %>" onblur="check_len(this, 10)"><%
}
else
{
%><INPUT TYPE="TEXT" SIZE="8" MAXLENGTH="4" NAME="bill_to_phone" ID="bill_to_phone" VALUE="<%= bill_to_phone %>" onblur="check_len(this, 10)"><%
}

%>
</TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Ext </TD>
<TD><INPUT TYPE="TEXT" SIZE="10" MAXLENGTH="10" NAME="bill_to_phoneext" ID="bill_to_phoneext" VALUE="<%=bill_to_phoneext%>"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Fax </TD>
<TD NOWRAP>
(<INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="3" NAME="bill_to_fax_area" ID="bill_to_fax_area" VALUE="<%= (bill_to_fax.length() > 2)?bill_to_fax.substring(0,3):"" %>">)
<INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="3" NAME="bill_to_fax_exch" ID="bill_to_fax_exch" VALUE="<%= (bill_to_fax.length() > 5)?bill_to_fax.substring(3,6):"" %>">-<%
if (bill_to_fax.length() > 3)
{
%><INPUT TYPE="TEXT" SIZE="8" MAXLENGTH="4" NAME="bill_to_fax" ID="bill_to_fax" VALUE="<%
	String tmp = rtrim(bill_to_fax);
	if (tmp.length() == 10)
	out.println(tmp.substring(tmp.length() - 4,tmp.length()));
%>">
<%
}
else
{
%>
<INPUT TYPE="TEXT" SIZE="8" MAXLENGTH="4" NAME="bill_to_fax" ID="bill_to_fax" VALUE="<%= bill_to_fax %>">
<%
}
%>
</TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> E-mail <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="60" NAME="bill_to_email" ID="bill_to_email" VALUE="<%=bill_to_email%>"></TD>
</TR>

<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>
</TABLE>
<TABLE width="100%" CELLSPACING="0" CELLPADDING="3" BORDER="0">
<%
if ((session.getAttribute(Constants.IS_CUSTOMER)==null) && rightToEditDiscountInfo(agent.isAdmin(),agent.getUserLevel())) {
%>
<TR>
<TD COLSPAN="5">
<img src = "images/discount_information-small.gif" BORDER="0">
</TH>
</TR>
<TR><TD COLSPAN=5>
<TABLE WIDTH="100%" CELLSPACING="0" CELLPADDING="3" BORDER="0">
<TR><td WIDTH = "15%">&nbsp;</td>
<td VALIGN="BOTTOM"><%= fontGeneric %>&nbsp;<b>Product Category<br><img src="images/dot_black.gif" width=100% height=1></b></FONT></td>
<td VALIGN="BOTTOM" ALIGN="CENTER"><%= fontGeneric %><b>Discount %<br><img src="images/dot_black.gif" width=100% height=1></b></FONT></td>
<td VALIGN="BOTTOM" ALIGN="CENTER"><%= fontGeneric %><b>Discount $<br><img src="images/dot_black.gif" width=100% height=1></b></FONT></td>
<td VALIGN="BOTTOM" ALIGN="CENTER"><%= fontGeneric %><b>Expiration<br>(MM/DD/YYYY)<br><img src="images/dot_black.gif" width=100% height=1></b></FONT></td>
<td WIDTH = "15%">&nbsp;</td>
</TR>

<%
if (agent_level != "")
{
	session.setAttribute("agent_level", agent_level);	
	
}
	
 //String perDiscount, String amtDiscount, String expDate, String index,String agent_level)

out.println(discountTable("Notebooks","Latitude",latperdiscount,latamtdiscount,latexpdate,0,agent_level));
out.println(discountTable("Notebooks","Inspiron",insperdiscount,insamtdiscount,insexpdate,1,agent_level));
out.println(discountTable("Desktops","Optiplex",optperdiscount,optamtdiscount,optexpdate,2,agent_level));
out.println(discountTable("Desktops","Dimension",dimperdiscount,dimamtdiscount,dimexpdate,3,agent_level));
out.println(discountTable("Monitors","Monitors",monperdiscount,monamtdiscount,monexpdate,4,agent_level));
out.println(discountTable("Servers","Servers",serperdiscount,seramtdiscount,serexpdate,5,agent_level));
out.println(discountTable("Workstations","Workstations",workperdiscount,workamtdiscount,workexpdate,6,agent_level));
//out.println(discountTable("Storage","D531",serperdiscount,seramtdiscount,serexpdate,8,agent_level));
//CALL PDTable ("Notebooks", "Latitude", Latperdiscount, Latamtdiscount, Latexpdate, 0)
//CALL PDTable ("Notebooks", "Inspiron", Insperdiscount, Insamtdiscount, Insexpdate, 1)
//CALL PDTable ("Desktops", "Optiplex", Optperdiscount, Optamtdiscount, Optexpdate, 2)
//CALL PDTable ("Desktops", "Dimension", Dimperdiscount, Dimamtdiscount, Dimexpdate, 3)
//CALL PDTable ("Monitors", "Monitors", Monperdiscount, Monamtdiscount, Monexpdate, 4)
//CALL PDTable ("Servers", "Servers", Serperdiscount, Seramtdiscount, Serexpdate, 5)
//CALL PDTable ("Workstations", "Workstations", Workperdiscount, Workamtdiscount, Workexpdate, 6)
%>

</TABLE>
</TD></TR>
<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Tax Exempt:</TD>
<TD NOWRAP>
<% if (agent_level.equalsIgnoreCase("A")) { %>
<input type="radio" name="tax_exempt" id="tax_exempt" value="1" <% if (tax_exempt == 1) out.println(" checked"); %> ID="Radio1" onclick="resetTax(1);"><%= fontGeneric %>YES
<input type="radio" name="tax_exempt" id="tax_exempt" value="0" <% if (tax_exempt == 0) out.println(" checked"); %> ID="Radio2"  onclick="resetTax(0);"><%= fontGeneric %>NO
<% } else { %>
<% if (tax_exempt == 1) { %>
<input type="hidden" name="tax_exempt" value="1" checked ID="Hidden1"><%= fontGeneric %>YES
<input type="hidden" name="tax_exempt" value="0" ID="Hidden2"><%= fontGeneric %>
<% } else { %>
<input type="hidden" name="tax_exempt" value="1" ID="Hidden3"><%= fontGeneric %>
<input type="hidden" name="tax_exempt" value="0" checked ID="Hidden4"><%= fontGeneric %>NO
<% } %>
<% } %>
</TD>
<TD ALIGN="LEFT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Letter of Authorization (LOA)</TD>
<TD colspan="2"><%= fontGeneric %>
<input type="radio" name="loa" value="1" 
<% 
Boolean flagLoa = false;
if (((customer != null) && customer.getLoa().equalsIgnoreCase("Y")) || ((session.getAttribute("loa") != null) && session.getAttribute("loa").toString().equalsIgnoreCase("Y")))
{
    out.println(" checked");
    flagLoa = true;
}
%> ><%= fontGeneric %>YES
<input type="radio" name="loa" value="0" <% 
if (((customer != null) && customer.getLoa().equalsIgnoreCase("N")) || ((session.getAttribute("loa") != null) && session.getAttribute("loa").toString().equalsIgnoreCase("N")) || flagLoa == false)
{
    out.println(" checked");   
}
	 
%>><%= fontGeneric %>NO
<% // session.setAttribute("loa","N"); %>
</TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Tax Exempt#:</TD>
<% if (agent_level.equalsIgnoreCase("A")) { %>
<TD><INPUT TYPE="TEXT" SIZE="25" MAXLENGTH="20" NAME="tax_exempt_number" ID="tax_exempt_number" VALUE="<%= tax_exempt_number %>"></TD>
<% } else { %>
<TD><INPUT TYPE="TEXT" SIZE="25" MAXLENGTH="20" NAME="tax_exempt_number" ID="tax_exempt_number" VALUE="<%= tax_exempt_number %>" readonly></TD>
<% } %>
<TD>Type of Account:</TD>
<TD align="left" colspan="2">
<%
String select_r = new String();
String select_c = new String();
String select_e = new String();
String select_i = new String();
String select_g = new String();
String select_f = new String();
String select_n = new String();
if (account_type.equalsIgnoreCase("R"))
	select_r = "selected";
else if (account_type.equalsIgnoreCase("C"))
	select_c = "selected";
else if (account_type.equalsIgnoreCase("E")) 
	select_e = "selected";
else if (account_type.equalsIgnoreCase("I")) 
	select_i = "selected";
else if (account_type.equalsIgnoreCase("G")) 
	select_g = "selected";
else if (account_type.equalsIgnoreCase("F")) 
	select_f = "selected";
else
	select_n = "selected";

//session.setAttribute("account_type","");
%>
<select name="account_type">
<option value = "" <%=select_n%>>Select account type...</option>
<option value = "R" <%=select_r%>>Reseller</option>
<option value = "C" <%=select_c%>>Commercial</option>
<option value = "E" <%=select_e%>>Education</option>
<option value = "I" <%=select_i%>>Consumer/Individual</option>
<option value = "G" <%=select_g%>>Government</option>
<option value = "F" <%=select_f%>>Faith/Religious Organization</option>
</select>
</TD>
</TR>
<tr>
<TD ALIGN="right" VALIGN="TOP" NOWRAP><%= fontGeneric %>Exp.Date:</TD>
<% if (agent_level.equalsIgnoreCase("A")) { %>
<TD align="left" colspan="2"><INPUT TYPE="TEXT" SIZE=25 MAXLENGTH=235 Class="disabled" NAME="tax_exempt_expire" ID="tax_exempt_expire" VALUE="<%= tax_exempt_expire %>" readonly></TD>
<% } else { %>
<TD align="left" colspan="2"><INPUT TYPE="TEXT" SIZE=25 MAXLENGTH=235 Class="disabled" NAME="tax_exempt_expire" VALUE="<%= tax_exempt_expire %>" readonly></TD>
<% } %>
</tr>
<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>
<% } %>

</Table>
<table name="ShippingTable" id="ShippingTable">
<TR>
<TD COLSPAN="5" NOWRAP>
<IMG SRC="images/shipping_information-small.gif" border="0"><br>
<%= fontGeneric %><INPUT TYPE="BUTTON" ONCLICK="copyBilling(this.form)" VALUE="Copy Bill To"></FONT>
&nbsp;<%= fontResults %>(Click here if billing and shipping are the same.)<%= fontResultsEnd %>
</TH>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> First Name <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="ship_to_fname" ID="ship_to_fname" VALUE="<%=ship_to_fname%>" onblur="check_len(this, 40)"></TD>
<TD WIDTH="8">&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Last Name <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="ship_to_lname" ID="ship_to_lname" VALUE="<%=ship_to_lname%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Title </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="ship_to_title" VALUE="<%=ship_to_title%>"></TD>
<TD WIDTH="8">&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %> Company </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="100" NAME="ship_to_company" VALUE="<%=ship_to_company%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Address <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" SIZE="28" MAXLENGTH="50" NAME="ship_to_address1" VALUE="<%=ship_to_address1%>" onblur="check_len(this, 40)"></TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> E-mail <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="60" NAME="ship_to_email" VALUE="<%=ship_to_email%>"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP"><%= fontGeneric %> Address 2</TD>
<TD COLSPAN="4"><INPUT TYPE="TEXT" SIZE="28" MAXLENGTH="50" NAME="ship_to_address2" VALUE="<%=ship_to_address2%>" onblur="check_len(this, 40)"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> City <%=requiredFlag1_end%> </TD>
<TD><INPUT TYPE="TEXT" STYLE="WIDTH:185" MAXLENGTH="50" NAME="ship_to_city" VALUE="<%=ship_to_city%>" onblur="check_len(this, 40)"></TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> State <%=requiredFlag1_end%> </TD>
<TD><%= fontGeneric %><SELECT NAME="ship_to_state">
<% 
if (request.getAttribute("STATES") != null)
{
	out.println("<OPTION VALUE=\"\">");
	List<String> states = (List<String>)request.getAttribute("STATES");
	StringBuilder tmp = new StringBuilder();
	      
	for(int i=0;i<states.size();i++)
    {		
		if (ship_to_state.equalsIgnoreCase(states.get(i)))
			tmp.append("<option value=\""+states.get(i)+"\" selected>"+states.get(i)+"</option>");				
		else
			tmp.append("<option value=\""+states.get(i)+"\">"+states.get(i)+"</option>");				
    }
	
	out.println(tmp + "</OPTION>");
	
} 
%></SELECT></FONT></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Country <%=requiredFlag1_end%> </TD>
<TD><%= fontGeneric %><SELECT NAME="ship_to_country">
<% 
if (request.getAttribute("COUNTRIES") != null)
{
	out.println("<OPTION VALUE=\"\">");
	List<String> countries = (List<String>)request.getAttribute("COUNTRIES");
	StringBuilder tmp = new StringBuilder(); 
     
	for(int i=0;i<countries.size();i++)
    {		
		if (ship_to_country.equalsIgnoreCase(countries.get(i)))
			tmp.append("<option value=\""+countries.get(i)+"\" selected>"+countries.get(i)+"</option>");				
		else
			tmp.append("<option value=\""+countries.get(i)+"\">"+countries.get(i)+"</option>");
    }
	
	out.println(tmp + "</OPTION>");
} 
%></SELECT></FONT></TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Zip <%=requiredFlag1_end%> </TD>
<TD ALIGN="LEFT"> <INPUT TYPE="TEXT" SIZE="10" MAXLENGTH="5" NAME="ship_to_postal" VALUE="<%=ship_to_postal%>"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Phone <%=requiredFlag1_end%> </TD>
<TD NOWRAP>
(<INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="3" NAME="ship_to_phone_area" VALUE="<%= (ship_to_phone.length() > 2)?ship_to_phone.substring(0,3):"" %>">)
<INPUT TYPE="TEXT" SIZE="2" MAXLENGTH="3" NAME="ship_to_phone_exch" VALUE="<%=  (ship_to_phone.length() > 5)?ship_to_phone.substring(3,6):"" %>">-
<%
if (ship_to_phone.length() > 3)
{	
%><INPUT TYPE="TEXT" SIZE="8" MAXLENGTH="4" NAME="ship_to_phone" VALUE="<%
	String tmp = rtrim(ship_to_phone);
	if (tmp.length() == 10)
	out.println(tmp.substring(tmp.length() - 4,tmp.length())); %>" onblur="check_len(this, 10)"><%
}
else
{
%><INPUT TYPE="TEXT" SIZE="8" MAXLENGTH="4" NAME="ship_to_phone" VALUE="<%= ship_to_phone %>" onblur="check_len(this, 10)"><%
}

%>	
</TD>
<TD>&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %>Ext </TD>
<TD><INPUT TYPE="TEXT" SIZE="10" MAXLENGTH="10" NAME="ship_to_phoneext" VALUE="<%=ship_to_phoneext%>"></TD>
</TR>
<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>


<!-- User Name Setup area. Commented. No longer use -->

<% 

if ((session.getAttribute(Constants.IS_CUSTOMER)==null) && rightToEditUserInfo(agent.isAdmin(),agent.getUserLevel())) {

if (section.equalsIgnoreCase("new") || section.equalsIgnoreCase("new_checkout") || section.equalsIgnoreCase("new_register") || section.equalsIgnoreCase("manage") || (request.getParameter("manage") != null)) { 
%>
<TR>
<TD COLSPAN="5">
<IMG SRC="images/user_name_setup-small.gif" border="0"><br>
</TD>
</TR>
<% if (section.equalsIgnoreCase("manage") || (request.getParameter("manage") != null)) { %>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Username <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><%= fontGeneric %><%=loginID%></font></TD>
</TR>
<% } else {%>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Username <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><INPUT TYPE="TEXT" SIZE="25" MAXLENGTH="20" NAME="loginID" VALUE="<%=loginID%>"></TD>
</TR>
<% } %>

<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Password <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><%= fontResults %>(Password must be between 6 and 20 characters) <%= fontResultsEnd %> <BR> <INPUT TYPE="password" SIZE=25 MAXLENGTH=20 NAME="pwd1" VALUE="<%=password%>"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Confirm<BR>Password <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><INPUT TYPE="password" SIZE="25" MAXLENGTH="20" NAME="pwd2" VALUE="<%=password%>"></TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Hint <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><INPUT TYPE="TEXT" SIZE="25" MAXLENGTH="50" NAME="hint" VALUE="<%=hint%>">
<br><%= fontResults %>Ex. Mother's maiden name?<%= fontResultsEnd %>
</TD>
</TR>
<TR>
<TD ALIGN="RIGHT" VALIGN="TOP" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Answer to Hint <%=requiredFlag1_end%> </TD>
<TD COLSPAN="4"><INPUT TYPE="TEXT" SIZE="25" MAXLENGTH="50" NAME="answer" VALUE="<%=answer%>">
<br><%= fontResults %>Ex. Smith<%= fontResultsEnd %>
</TD>
</TR>
<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>

<% } } %>


<% if (section.equalsIgnoreCase("new") || section.equalsIgnoreCase("new_checkout") || section.equalsIgnoreCase("new_register")) { %>
<TR>
<TD COLSPAN="5">
<img src = "images/user_profile.gif" width="475" height="32" BORDER="0">
</TR>
<TR>
<td COLSPAN="5"><%= fontGeneric %><%=requiredFlag1_start%>The following information is used by DFS to enhance our service<br> capabilities.
This information is not sold to 3rd Party entities. <%=requiredFlag1_end%></FONT>
</td>
</tr>
<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>
<TR>
<TD COLSPAN="5"><%= fontGeneric %>
This equipment will primarily be used for:</font></TD>
</TR>
<TR>
<TD COLSPAN="5"> <%= fontGeneric %>
<INPUT type="radio" name="equip_use" value="Business / Commercial Use">
Business / Commercial Use </font>
</TD>
</TR>
<TR>
<TD COLSPAN="5"> <%= fontGeneric %>
<INPUT type="radio" name="equip_use" value="Personal / Home Use">
Personal / Home Use </font>
</TD>
</TR>
<TR>
<TD COLSPAN="5"> <%= fontGeneric %>
<INPUT type="radio" name="equip_use" value="Educational Use">
Educational Use </font>
</TD>
</TR>

<TR><TD COLSPAN="5">&nbsp; <BR></TD></TR>
<% } %>
<TR>
<TD colspan="5">
<TABLE width="90%"  CELLSPACING="0" CELLPADDING="3" BORDER="0">
<TBODY>
<% if ((section.equalsIgnoreCase("new") || section.equalsIgnoreCase("new_checkout") || section.equalsIgnoreCase("new_register")) && usingManager) { %>
<TR>
<TD ALIGN="LEFT" VALIGN="CENTER" NOWRAP Width="20%"><%= fontGeneric %><%=requiredFlag1_start%> Select Agent <%=requiredFlag1_end%> </TD>
<TD Width="20%"><%= fontGeneric %><SELECT NAME="agent_ID" ID="agent_ID" onchange="changeAgentSelection();">

<script language="javascript">
var agentNameEmail = new Array();
</script>

<% 
StringBuilder htmlCode = new StringBuilder();
StringBuilder javascriptCode = new StringBuilder();
//session.setAttribute("agent_level","A");
if (agent_level.equalsIgnoreCase("A"))
{
	htmlCode.append("<OPTION VALUE=0>Select One</OPTION>");
	if (request.getAttribute("AllAdminUser") != null)
	{		
		javascriptCode.append("<script language=\"javascript\">");
		Map<String,String[]> users = (Map<String,String[]>)request.getAttribute("AllAdminUser");
		 for (String key : users.keySet()) 
		 {
			 	htmlCode.append("<option value=\"");
				htmlCode.append(key);
				htmlCode.append("\">");
				htmlCode.append(((String[])users.get(key))[0]);				
				javascriptCode.append("agentNameEmail["+key+"] = \"" + ((String[])users.get(key))[1] +"\";");
				htmlCode.append("</option>");	            
	     }		
		 javascriptCode.append("</script>");
	}
}
else
{
	htmlCode.append("<option value=\"");
	htmlCode.append(session.getAttribute("agent_id"));
	htmlCode.append("\">");
	htmlCode.append(session.getAttribute("agent_name"));
	htmlCode.append("</option>");
}
htmlCode.append(javascriptCode);
out.println(htmlCode.toString());


%></SELECT></FONT></TD>

<TD width="5%">&nbsp; </TD>
<TD ALIGN="LEFT" VALIGN="CENTER" NOWRAP><%= fontGeneric %><%=requiredFlag1_start%> Email: <%=requiredFlag1_end%> </TD>
<TD ALIGN="LEFT" VALIGN="CENTER" NOWRAP> 
  <%= fontGeneric %> <div id="lblEmail"> <% //(agent != null)?agent.getAgentEmail():"" %> </div> </FONT>
  
</TD>

<TD width="5%">&nbsp; </TD>
<TD ALIGN="RIGHT" VALIGN="CENTER">&nbsp;</TD>
<TD ALIGN="LEFT"> 
&nbsp;
</TD>
</TR>
<% } 
else 
{
	Agent agentObject = (Agent)request.getAttribute("AgentObject");
	%>
	<TR>
<TD COLSPAN="8" NOWRAP>
<IMG SRC="images/agent_information.jpg" border="0"><br>
</TR>
<TR>

	<TR>
	<TD width="4%">&nbsp; </TD>
	<TD ALIGN="LEFT" VALIGN="CENTER" NOWRAP width="10%"><%= fontGeneric %><%=requiredFlag1_start%> Agent Name: <%=requiredFlag1_end%> </TD>
	<TD ALIGN="LEFT" VALIGN="CENTER"><%= fontGeneric %>  <%= (agentObject != null)?agentObject.getUserName():"" %> </FONT></TD>
<TD width="15%">&nbsp; </TD>
<td width="15%">&nbsp; </td>
<td width="17%">&nbsp; </td>
<TD ALIGN="LEFT" VALIGN="CENTER" NOWRAP width="10%"><%= fontGeneric %><%=requiredFlag1_start%> Agent Email: <%=requiredFlag1_end%> </TD>
<TD ALIGN="LEFT" VALIGN="CENTER">
<%= fontGeneric %><div id="lblEmail">  <%= (agentObject != null && agentObject.getAgentEmail() != null && !agentObject.getAgentEmail().isEmpty())?agentObject.getAgentEmail():"" %> </div></FONT>
</TD>
</TR>
<% }
%>
</TBODY>
</TABLE> 
</TD>
</TR>

</TABLE>

</TD>
</TR>
<%
//&& !userLevel.equals("B")
if(userLevel!=null && !userLevel.equals("C") ){
%>
<TR>
<% 
Boolean isCustomerManage = (request.getParameter("manage") != null) && (Boolean.valueOf(request.getParameter("manage")) == true);
Boolean isEditAsShopper = (request.getParameter("method") != null) && request.getParameter("method").equalsIgnoreCase("editAsShopper");

if (!section.equalsIgnoreCase("new_checkout") && ((!isCustomerManage && !isEditAsShopper) && (customer.getAgent_id() == 0))) { %>
<TD ALIGN="LEFT"><INPUT TYPE="Button" BORDER="0" NAME="Continue" VALUE="Continue" ID="btnContinue" onclick="alert('This customer does not belong to Agent Sale Type');"></TD>
<% } else { %>
<TD ALIGN="LEFT"><INPUT TYPE="Button" BORDER="0" NAME="Continue" VALUE="Continue" ID="btnContinue" onclick="check_Form(document.NewUser,<%=session.getAttribute(Constants.CUSTOMER_MANAGE)%>);"></TD>
<% } %>
</TR>
<%} %>
</TABLE>	
					
					
<!-- END OF SHOPPER FORM -->
<!-- END OF SHOPPER FORM -->
<!-- END OF SHOPPER FORM -->
<!-- END OF SHOPPER FORM -->

					<INPUT TYPE=HIDDEN BORDER="0" NAME="section" VALUE="<%=section%>"> 
					<INPUT TYPE=HIDDEN BORDER="0" NAME="hidManage" VALUE="<%=request.getParameter("manage")%>"> 
					<INPUT TYPE=HIDDEN BORDER="0" NAME="booledit" VALUE="<%=boolEdit%>">
					<INPUT TYPE=HIDDEN BORDER="0" NAME="shopper_new" VALUE="<%= (customer != null)?customer.getShopper_id():"" %>">

	
				</FORM>
			</TD>
		</TR>
	</TABLE>	
<%    
}
%>
</div>
<%@include file="/html/scripts/dialogScript.jsp"%>
<%@include file="/html/scripts/calendarScript.jsp"%>
<%@include file="/html/scripts/shopperScript.jsp"%>
