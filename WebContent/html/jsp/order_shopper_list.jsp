<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.List"%>

<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>

<%

    ShopperViewReceipts shopperViewReceipts = null;
    shopperViewReceipts = (ShopperViewReceipts) request.getAttribute(Constants.SHOPPER_INFO);
   
%>
<%@page import="com.dell.enterprise.agenttool.model.ShopperViewReceipts"%>
<script>
		 $('#pageTitle').html("Shopper - <%=shopperViewReceipts.getShip_to_fname()%> <%=shopperViewReceipts.getShip_to_lname()%> (<%=shopperViewReceipts.getShopper_number()%>)");
		 $('#topTitle').html("Shopper - <%=shopperViewReceipts.getShip_to_fname()%> <%=shopperViewReceipts.getShip_to_lname()%> (<%=shopperViewReceipts.getShopper_number()%>)");
</script>

<A
	HREF="shopper_manager.do?method=getShopperDetails&shopperId=<%=shopperViewReceipts.getShopper_id()%>"><font
	color="red"><u>View Shopper</u></font> </A>
<br></br>
<div id="note_result">
<%
	List<ShopperViewReceipts> list = (List<ShopperViewReceipts>) request.getAttribute(Constants.SHOPPER_VIEW_RECEIPTS);
	int totalRecord =(Integer) request.getAttribute(Constants.ORDER_TOTAL);
	DateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
	int pageRecord = 40;
	if(totalRecord > pageRecord){
	  int  numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
	  int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
<table cellspacing="3" border="0" class="main">
        <tbody><tr>
            <td bgcolor="lightgrey" align="center"><b>
                <nobr><span id="totalRecord"><%=totalRecord %></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp; Page: <span id="noPage"><%=noPage %></span> of <span id="maxPage"><%=numOfPage %></span></nobr>
            </b></td>
        </tr>
        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingGetViewReceipts","note_result");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingGetViewReceipts","note_result");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingGetViewReceipts","note_result");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingGetViewReceipts","note_result");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingGetViewReceipts","note_result");' />
                 </div>
            </td>
        </tr>
  </tbody></table>
 <%} %> 


<table>
	
 <%	
 if(list.size()>0){
     %>
 		  <TR>
				<TD ALIGN=RIGHT VALIGN=BOTTOM><B> # </B></TD>
				<TD ALIGN=CENTER VALIGN=BOTTOM><B> Order ID </B></TD>
				<TD ALIGN=RIGHT VALIGN=BOTTOM><B> Date / Time </B></TD>
				<TD ALIGN=RIGHT VALIGN=BOTTOM><B> Total </B></TD>
			</TR>					     
 <%
		
	for (Iterator<ShopperViewReceipts> iterator = list.iterator(); iterator.hasNext();) {
	    ShopperViewReceipts listView = iterator.next();
	    float total = Float.parseFloat(listView.getTotal_total());
%>
	<tr>
		<TD VALIGN=TOP ALIGN=CENTER> <%=listView.getRowNumber() %> </TD>
		<TD VALIGN=TOP ALIGN=CENTER><a
			href="order_db.do?method=orderViewPending&order_id=<%=listView.getOrderNumber()%>"><font
			color="red"><u><%=listView.getOrderNumber()%></u></font></a></TD>
		<TD VALIGN=TOP ALIGN=RIGHT><%=listView.getCreatedDate()%></TD>
		<TD VALIGN=TOP ALIGN=RIGHT><%=Constants.FormatCurrency(total)%></TD>
		

	</tr>
<%
	}
%>
	
		
		<%	 }
		    else
		    {
		        out.println("<tr><td><i>No data available</i></td></tr>");
		    }	%>
</table>
</div>



<script>

function movePaging(casePage,method,divResult) {
	var numOfPage = $('#maxPage').text();
	var totalRecord = $('#totalRecord').text();
	var noPage = $('#noPage').text();
	$.ajax( {
		url : 'shopper_manager.do',
		type : 'post',
		cache : false,
		data : {
			method : method,
			casePage : casePage,
			mscssid : '<%=shopperViewReceipts.getShopper_id()%>',
			totalRecord : totalRecord,
			noPage : noPage
		},
		dataType : 'html',
		async : true,
		success : function(html) {
			var data = html.replace(/[\r\n]+/g, "");
			if (data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && !isCustommer)	{
				document.location = "<%=request.getContextPath()%>/authenticate.do";
			}
			else if(data.toString().indexOf("<input type=\"submit\" value=\"Login\" />") != -1 && isCustommer){ 
				document.location = "<%=request.getContextPath()%>/login.do";
			}
			else{
				$('#'+divResult).html(data);
				}				
		},
		error : function() {
			alert("Error execute in search process!");
		}
	});
};
$('document').ready(function() {
	var pageNo = $('#noPage').text();
	var numOfPage = $('#maxPage').text();
	if (pageNo == 1) {
		$('#firstPage').attr('disabled', 'disabled');
		$('#prePage').attr('disabled', 'disabled');
	} else {
		$('#firstPage').removeAttr('disabled');
		$('#prePage').removeAttr('disabled');
	}
	if(pageNo == numOfPage){
		$('#lastPage').attr('disabled', 'disabled');
		$('#nextPage').attr('disabled', 'disabled');
	}
	else{
		$('#lastPage').removeAttr('disabled');
		$('#nextPage').removeAttr('disabled');
		}
});

</script>	

