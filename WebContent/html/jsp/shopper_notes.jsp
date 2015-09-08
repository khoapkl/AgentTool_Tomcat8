
<%@page import="com.dell.enterprise.agenttool.model.Note"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.dell.enterprise.agenttool.model.Shopper"%>
<%
    Shopper shopper = (Shopper) request.getAttribute(Constants.SHOPPER_INFO);
    String mscssid = (String) request.getAttribute(Constants.SHOPPER_ID);
    DateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
%>

<script>
		 $('#pageTitle').html("Shopper - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
		 $('#topTitle').html("Shopper - <%=shopper.getShipToFName()%> <%=shopper.getShipToLName()%> (<%=shopper.getShopperNumber()%>)");
</script>

<A HREF="shopper_manager.do?method=getShopperDetails&shopperId=<%=mscssid%>"><font color="red"><u>View Shopper</u></font> </A>&nbsp;|&nbsp;
<a href="javascript:void(0)" onClick="openWarrenty('shopper_manager.do?method=addNote&mscssid=<%=mscssid%>');" onmouseOver="window.status='Click for warranty information.'; return true" onmouseOut="window.status=''; return true" CLASS="nounderline"><font color="red"><u>Add Note</u></font></a>

<table>
	<tr>
		<td colspan="2">
<div id="note_result"><%
    List<Note> notes = (List<Note>) request.getAttribute(Constants.SHOPPER_NOTES);
    int totalRecord = (Integer) request.getAttribute(Constants.ORDER_TOTAL);
    int pageRecord = 10;
    if (totalRecord > pageRecord)
    {
        int numOfPage = (totalRecord % pageRecord > 0) ? (totalRecord / pageRecord + 1) : (totalRecord / pageRecord);
        int noPage = (Integer) request.getAttribute(Constants.ORDER_NUMBER_PAGE);
%>
<table cellspacing="3" border="0" class="main">
        <tbody><tr>
            <td bgcolor="lightgrey" align="center"><b>
                <nobr><span id="totalRecord"><%=totalRecord%></span>&nbsp;Records&nbsp;&nbsp;&nbsp;&nbsp; Page: <span id="noPage"><%=noPage%></span> of <span id="maxPage"><%=numOfPage%></span></nobr>
            </b></td>
        </tr>
        <tr>
            <td><div id="pagingDiv">
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingMoreNotes","note_result");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingMoreNotes","note_result");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingMoreNotes","note_result");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingMoreNotes","note_result");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingMoreNotes","note_result");' />
                 </div>
            </td>
        </tr>
  </tbody></table>
 <%} %> 
		<table>
	
 <%	
		
	for (Iterator<Note> iterator = notes.iterator(); iterator.hasNext();) {
	    Note note = iterator.next();
%>
	<tr>
		<TD VALIGN=TOP ALIGN=CENTER> &#149; </TD>
		<TD VALIGN=TOP><%=df.format(note.getTimeOff()) %> [ <%=note.getAgentName() %>
<%
		if (note.getSubjectId() == 0) {
		    String topic = note.getTopic();
		    if (!"".equals(topic) && topic != null)
%>
 : <%=topic %>
<%
		} else {
%>
 : <%=note.getSubject() %> - <%=note.getNoteType() %>
<%
		}
%>
 (<%=note.getOrderNumber() %>) ] <%=note.getNotes() %>
		</TD> 
	</tr>
<%
	}
%>
		</table>
</div>		
		</td>
	</tr>
</TABLE>

<%@include file="/html/scripts/pagingNoteScript.jsp"%>
<script LANGUAGE="JavaScript">
<!-- //
function openWarrenty(URL) {
    var newWIN;
    day = new Date();
    id = day.getTime();
    eval("page"+id+"=window.open(URL,'"+id+"','status=1,toolbar=0,scrollbars=1,location=0,statusbars=1,menubar=0,resizable=1,width=540,height=440');");
}
</script> 	