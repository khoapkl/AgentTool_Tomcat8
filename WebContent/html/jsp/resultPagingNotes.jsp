<%@page import="java.util.Iterator"%>
<%@page import="com.dell.enterprise.agenttool.model.Note"%>
<%@page import="java.util.List"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%
	List<Note> notes = (List<Note>) request.getAttribute(Constants.SHOPPER_NOTES);
	int totalRecord =(Integer) request.getAttribute(Constants.ORDER_TOTAL);
	DateFormat df = new SimpleDateFormat("M/d/yyyy h:mm:ss a");
	int pageRecord = 5;
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
                    <input type="Button" value="   &lt;&lt;   " id="firstPage" onclick='movePaging("FIRST","movingNotes","note_result");'/>
                    <input type="Button" value="   &lt;    " id="prePage" onclick='movePaging("PREV","movingNotes","note_result");'/>
                    <input type="Button" value="    &gt;   " id="nextPage" onclick='movePaging("NEXT","movingNotes","note_result");'/>
                    <input type="Button" value="   &gt;&gt;   " id="lastPage" onclick='movePaging("LAST","movingNotes","note_result");'/>
                    <input type="Button" value=" Requery " onclick='movePaging("FIRST","movingNotes","note_result");' />
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
<script>

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