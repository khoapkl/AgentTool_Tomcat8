<%@page import="java.util.Map"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.dell.enterprise.agenttool.model.Customer"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>

<%
Map<String, Customer> customers = (Map<String, Customer>)request.getAttribute("RESULT");
if ((customers != null) && (customers.size() > 0)){
		
	
	int totalRecord =(Integer) session.getAttribute("totalRecord");
	int numPage = (Integer) session.getAttribute("numOfPage");
	int noPage = (Integer) session.getAttribute("noPage");
	Set entries = customers.entrySet();
	String record = (totalRecord > 1) ? "Records" : "Record";
	
	Agent agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
	String agent_level = "";
	if (agent != null)
		 agent_level = agent.getUserLevel();		

%>	
<table BORDER=0 CELLSPACING=0 CELLPADDING=2 width="100%">
        <tbody>
        <tr>
            <td bgcolor="lightgrey" nowrap="nowrap"><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging(1);'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging(2);'/>

                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging(3);'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging(4);'/>                  
                    <input type="Hidden" value="1" name="ListAbsolutePage"/>
                    <input type="Hidden" value="" name="mscssid"/>
                 </div>
            </td>
            <td width="100%" bgcolor="lightgrey" align="left" style="padding-left: 10px">
             <b>
            <nobr><span id="totalRecord"><%=totalRecord %></span>&nbsp;<span><%=record %></span>&nbsp;&nbsp;&nbsp;&nbsp; Page:&nbsp;<span id="noPage"><%=noPage%></span> of <span id="numPage"><%=numPage %></span></nobr>
            </b>
            </td>
        </tr>
 </tbody></table>
	
	
	<TABLE BORDER=0 CELLPADDING=2 WIDTH="100%"><tr>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Customer# </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB" colspan="3"><font face="Arial, Helvetica" size="2"> Shipping</font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB" colspan="3"><font face="Arial, Helvetica" size="2"> Billing</font></TH>
	</tr>
	<tr>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB">&nbsp;</TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Name </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Company </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Phone </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Name </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Company </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Phone </font></TH>
	</tr>
	
	<%
	
    

	for(Iterator i = customers.values().iterator(); i.hasNext();)
    {
		Customer customer = (Customer)i.next();           	
    %>
    	<tr>   	
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=(customer.getShopper_number() == 0)?"":customer.getShopper_number()%> </font></TD>
      	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2">
      	<%
      	Boolean customerManage = false ;
      	if(session.getAttribute(Constants.CUSTOMER_MANAGE) != null)
      	{
      	    customerManage = (Boolean)session.getAttribute(Constants.CUSTOMER_MANAGE);    
      	}
      	if ((customerManage == false) && (customer.getAgent_id() == 0)) {      		
      	%>
      	<A HREF="#" onclick="alert('This customer does not belong to Agent Sale Type');"><%=customer.getShip_to_fname() + " " + customer.getShip_to_lname()%></A>
      	<% } else { %>
      	<A HREF="#" onclick="buildPath('checkout','<%=customer.getShopper_id()%>','<%=customer.getShip_to_fname().trim().replace("'","\\'") + " " + customer.getShip_to_lname().trim().replace("'","\\'")%>','<%=agent_level%>',<%=session.getAttribute(Constants.CUSTOMER_MANAGE)%>)"><%=customer.getShip_to_fname() + " " + customer.getShip_to_lname()%></A>
      	<% } %>
      	</font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=customer.getShip_to_company() + "&nbsp;"%></font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=customer.getShip_to_phone() + "&nbsp;"%></font></TD>

    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=customer.getBill_to_fname() + "&nbsp;" + customer.getBill_to_lname()%></font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=customer.getBill_to_company() + "&nbsp;"%></font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=customer.getBill_to_phone() + "&nbsp;"%></font></TD>
    	</tr>   
    <%    
    }
    %>
    </TABLE>
<%	
}
else
{
%>
<table BORDER=0 CELLSPACING=0 CELLPADDING=2 width="100%">
        <tbody>
        <tr>
        <td width="35%"><font FACE="Arial, Helvetica" size="2"><b>No record found.</b></font></td>
        <td width="30%"></td>
        <td width="35%"></td>
        </tr>
        </tbody>
</table>
<%
}
%>

<%@include file="/html/scripts/cust_resultsScript.jsp"%>