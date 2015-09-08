<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.model.Note"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.ShopperViewReceipts"%>
<%
Boolean isAdmin =(Boolean)session.getAttribute(Constants.IS_ADMIN);
String userLevel =(String)session.getAttribute(Constants.USER_LEVEL);
Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
	Shopper shopper = (Shopper) request.getAttribute(Constants.SHOPPER_INFO);
	int receipts = Integer.valueOf(request.getAttribute(Constants.SHOPPER_RECEIPTS).toString());
	DateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
	
%>
<script>
		 $('#pageTitle').html("Shopper - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
		 $('#topTitle').html("Shopper - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
</script>
&nbsp;
<A
	HREF="shopper_manager.do?method=getShopperDetails&shopperId=<%=shopper.getShopperId() %>">Refresh
View</A>
&nbsp;|&nbsp;
<%
if(userLevel!=null ){%>

<A
	HREF="shopper_manager.do?method=getViewReceipts&shopperId=<%=shopper.getShopperId()%>">View
Receipts (<%=receipts%>)</A>
&nbsp;|&nbsp;
<%} %>
<%
if(userLevel!=null && !userLevel.equals("C")){	
%>

<A
	HREF="shopper_manager.do?method=getViewBasket&mscssid=<%=shopper.getShopperId()%>">View
Cart</A>

<%if((session.getAttribute(Constants.IS_CUSTOMER) == null)){ %>
&nbsp;|&nbsp;
<A
	HREF="shopper_manager.do?method=viewNotes&mscssid=<%=shopper.getShopperId()%>">View
Notes</A>
&nbsp;|&nbsp;
<a href="javascript:void(0)"
	onClick="openWarrenty('shopper_manager.do?method=addNote&mscssid=<%=shopper.getShopperId()%>');"
	onmouseOver="window.status='Click for warranty information.'; return true"
	onmouseOut="window.status=''; return true" CLASS="nounderline"><font
	color="red"><u>Add Note</u></font></a>
&nbsp;|&nbsp;
<%}} %>
<%
if((session.getAttribute(Constants.IS_CUSTOMER) == null)&& userLevel!=null && !userLevel.equals("C") && !userLevel.equals("B")){
%>
<a
	href="authenticate.do?method=editAsShopper&shopper_id=<%=shopper.getShopperId() %>&shopper_name=<%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%>">Edit
Shopper</a>


&nbsp;|&nbsp;
<%  
	if ((Boolean) session.getAttribute(Constants.IS_ADMIN)) {
	    if ("A".equalsIgnoreCase(session.getAttribute(Constants.USER_LEVEL).toString())) {
%>
<a
	href="shopper_manager.do?method=editAgentExpiration&mscssid=<%=shopper.getShopperId()%>">Change Agent</a>
&nbsp;|&nbsp;
<%
	    }}
	}
%>

<%if((session.getAttribute(Constants.IS_CUSTOMER) == null)&& userLevel!=null && !userLevel.equals("C")){ %>
<a
	href="authenticate.do?method=shoppingAs&shopper_id=<%=shopper.getShopperId() %>&shopper_name=<%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%>">Shop
As Shopper</a>
&nbsp;|&nbsp;
<% if (shopper.getAgent_id() == 0) { %>
<a href="#" onclick="alert('This customer does not belong to Agent Sale Type');">Checkout</a>
<% } else {%>
<a
	href="authenticate.do?method=checkoutAsShopper&shopper_id=<%=shopper.getShopperId() %>&shopper_name=<%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%>">Checkout</a>
<%} %>
<%} %>
<BR>
<input type="button" id="shopperBackButton" value="<<Back" />
<TABLE width="100%" border="0">
	<tr>
		<td width="50%" valign="top">
		<TABLE width="100%" border"0">
			<tr>
				<td width="20%"></td>
				<td width="80%"></td>
			</tr>
			<tr>
				<td colspan="2"><b><u>Shipping Information</u></b></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Name : </b></td>
				<td valign="top"><b> <%=shopper.getShipToFName() + " " + shopper.getShipToLName() %>
				</b></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Company : </b></td>
				<td valign="top"><%=shopper.getShipToCompany() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Address Line 1 :
				</b></td>
				<td valign="top"><%=shopper.getShipToAddress1() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Address Line 2 :
				</b></td>
				<td valign="top"><%=shopper.getShipToAddress2() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> City, State ZIP :
				</b></td>
				<td valign="top"><%=shopper.getShipToCity() + ", " + shopper.getShipToState() + "&nbsp;" + shopper.getShipToPostal() %>
				</td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Country : </b></td>
				<td valign="top"><%=shopper.getShipToCountry() %></td>
			</tr>			
			<tr>
				<td valign="top" align="right" nowrap><b> Phone : </b></td>
				<td valign="top"><%=shopper.getShipToPhone() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap></td>
				<td valign="top"></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Email : </b></td>
				<td valign="top"><%=shopper.getShipToEmail() %></td>
			</tr>
		</TABLE>
		</td>
		<td width="50%" valign="top">
		<TABLE width="100%" border="0" >
			<tr>
				<td width="20%"></td>
				<td width="80%"></td>
			</tr>
			<tr>
				<td colspan="2"><b><u>Billing Information</u></b></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Name : </b></td>
				<td valign="top"><b> <%=shopper.getBillToFName() + " " + shopper.getBillToLName() %>
				</b></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Company : </b></td>
				<td valign="top"><%=shopper.getBillToCompany() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Address Line 1 :
				</b></td>
				<td valign="top"><%=shopper.getBillToAddress1() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Address Line 2 :
				</b></td>
				<td valign="top"><%=shopper.getBillToAddress2() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> City, State ZIP :
				</b></td>
				<td valign="top"><%=shopper.getBillToCity() + ", " + shopper.getBillToState() + "&nbsp;" + shopper.getBillToPostal() %>
				</td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Country : </b></td>
				<td valign="top"><%=shopper.getBillToCountry() %></td>
			</tr>			
			<tr>
				<td valign="top" align="right" nowrap><b>Phone : </b></td>
				<td valign="top"><%=shopper.getBillToPhone() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap>
				<% if (!Constants.isEmpty(shopper.getBillToFax())) { %>
				<b> Fax : </b>
				<% } %>
				</td>
				<td valign="top"><%=shopper.getBillToFax() %></td>
			</tr>
			<tr>
				<td valign="top" align="right" nowrap><b> Email : </b></td>
				<td valign="top"><%=shopper.getBillToEmail() %></td>
			</tr>
		</TABLE>
		</td>
	</tr>
	<%
	
	Agent agent = new Agent();
	if(session.getAttribute(Constants.AGENT_INFO) != null)
	{
	    agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
	}

	if(agent.isAdmin() || (!agent.isAdmin() && (agent.getUserLevel() != null && !agent.getUserLevel().equals("C")) && isCustomer == null ))
	{
	%>
		<tr>
		<td colspan="2"><b><u>Notes</u></b></td>
	</tr>
	<tr>
		<td colspan="2">
		<div id="note_result">
<%

	    

	List<Note> notes = (List<Note>) request.getAttribute(Constants.SHOPPER_NOTES);
	if(null != notes && !notes.isEmpty()){
	int totalRecord =Integer.valueOf(request.getAttribute(Constants.ORDER_TOTAL).toString());
	int pageRecord = 5;
	if(totalRecord > pageRecord){
	int  numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
	int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
		<table cellspacing="3" border="0" class="main">
			<tbody>
				<tr>
					<td bgcolor="lightgrey" align="center"><b> <nobr><span
						id="totalRecord"><%=totalRecord %></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp;
					Page: <span id="noPage"><%=noPage %></span> of <span id="maxPage"><%=numOfPage %></span></nobr>
					</b></td>
				</tr>
				<tr>
					<td>
					<div id="pagingDiv"><input type="Button"
						value="   &lt;&lt;   " id="firstPage"
						onclick='movePaging("FIRST","movingNotes","note_result");' /> <input
						type="Button" value="   &lt;    " id="prePage"
						onclick='movePaging("PREV","movingNotes","note_result");' /> <input
						type="Button" value="    &gt;   " id="nextPage"
						onclick='movePaging("NEXT","movingNotes","note_result");' /> <input
						type="Button" value="   &gt;&gt;   " id="lastPage"
						onclick='movePaging("LAST","movingNotes","note_result");' /> <input
						type="Button" value=" Requery "
						onclick='movePaging("FIRST", "movingNotes", "note_result");'/></div>
												
					</td>					
				</tr>		
			</tbody>
		</table>
		<%} %>
		<table>
		
			<%	
		
	for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
	    Note note = iterator.next();
%>
			<tr>
				<TD VALIGN=TOP ALIGN=CENTER>&#149;</TD>
				<TD VALIGN=TOP><%=df.format(note.getTimeOff()) %> [ <%=note.getAgentName() %>
				<%
		if (note.getSubjectId() == 0) {
		    String topic = note.getTopic();
		    if (!"".equals(topic) && topic != null)
%> : <%=topic %> 
<%
		} else {
%> : <%=note.getSubject() %> - <%=note.getNoteType() %> <%
		}
%> (<%=note.getOrderNumber() %>) ] <%=note.getNotes() %></TD>
			</tr>
			<%
	}}
%>
		</table>
		</div>
		

		</td>
	</tr>
	<%
	}
%>
</TABLE>
<%@include file="/html/scripts/pagingNoteScript.jsp"%>
<script LANGUAGE="JavaScript">
	$(document).ready(function(){
		<%		
		if (request.getParameter("from") != null) {
			if (request.getParameter("from").toString().equals("order_result")) {
		%>
		$('#shopperBackButton').click(function() {
			javascript:document.location.href='<%=request.getContextPath()%>/order_db.do?method=backOrderForwardPage';
		});
		<%		
			} else if (request.getParameter("from").toString().equals("shopper_result")) {
		%>
		$('#shopperBackButton').click(function() {
			javascript:document.location.href='<%=request.getContextPath()%>/shopper_list.do?method=backShopperForwardPage';
		});
		<%	
			}else if (request.getParameter("from").toString().equals("orderAgent")) {
		%>
		$('#shopperBackButton').click(function() {
			javascript:document.location.href='<%=request.getContextPath()%>/order_db.do?method=detailAgent&back=true';
		});
		<%	
			}else if (request.getParameter("from").toString().equals("orderByShopper")) {
		%>
		$('#shopperBackButton').click(function() {
			javascript:document.location.href='<%=request.getContextPath()%>/order_db.do?method=backShopperForwardPage&backPage=true';
		});
		<%	
			}
		} else {
		%>
		$('#shopperBackButton').css('display', 'none');
		<%
		}
		%>
	});
	//
	function openWarrenty(URL) {
		var newWIN;
		day = new Date();
		id = day.getTime();
		eval("page"
				+ id
				+ "=window.open(URL,'"
				+ id
				+ "','status=1,toolbar=0,scrollbars=1,location=0,statusbars=1,menubar=0,resizable=1,width=540,height=440');");
	}
</script>
