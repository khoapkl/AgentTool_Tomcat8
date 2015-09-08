<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%@page import="com.dell.enterprise.agenttool.model.Agent"%>

<%
List<Agent> agents = (List<Agent>)request.getAttribute("RESULT");
if ((agents != null) && (agents.size() > 0)){
		
	
	int totalRecord =(Integer) session.getAttribute("totalRecord");
	int numPage = (Integer) session.getAttribute("numOfPage");
	int noPage = (Integer) session.getAttribute("noPage");	
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
	
	
	<TABLE BORDER=0 CELLPADDING=2 WIDTH="100%">
	<tr>	
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Agent ID </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Username </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Email </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Full Name </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Is Admin </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> User Level </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Active </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Is Report </font></TH>
	<TH ALIGN=LEFT VALIGN=BOTTOM BGCOLOR="#DBDBDB"><font face="Arial, Helvetica" size="2"> Action </font></TH>
	</tr>
	
	<%
	
    

	for(int i = 0;i< agents.size();i++)
    {
		Agent currentAgent = agents.get(i);           	
    %>
    	<tr id="row<%=currentAgent.getAgentId()%>">    	
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=currentAgent.getAgentId()%> </font></TD>
      	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2">
     	 <%=currentAgent.getUserName()%>
      	</font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=currentAgent.getAgentEmail() + "&nbsp;"%></font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=currentAgent.getAgentName() + "&nbsp;"%></font></TD>

    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2">
    	<%
    		
    		if(currentAgent.isAdmin())
    			out.println("YES");
    		else	
    		    out.println("NO");
    	%>
    	</font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2"><%=currentAgent.getUserLevel() + "&nbsp;"%></font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2">
    	<%
    		
    		if(currentAgent.isActive())
    			out.println("YES");
    		else	
    		    out.println("NO");
    	%>
    	
    	</font></TD>
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2">
    	<%
    		if(currentAgent.isReport())
    			out.println("YES");
    		else	
    		    out.println("NO");
    	%>
    	</font></TD>    	
    	<TD VALIGN=TOP ALIGN=LEFT><font face="Arial, Helvetica" size="2">    
    	<% if (currentAgent.getAgentId() == agent.getAgentId()){ %>
    	<A HREF="#" onclick="editAgent(<%=currentAgent.getAgentId()%>)">Edit</A>
    	<% } else { %>
    	<A HREF="#" onclick="editAgent(<%=currentAgent.getAgentId()%>)">Edit</A>&nbsp;|&nbsp;
    	<A HREF="#" onclick="if (confirm('Are you sure you want to delete this agent ?')) checkForAgentInUse('<%=currentAgent.getAgentId()%>')">Delete</A>
    	<% } %>
    	</font></TD>
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

<%@include file="/html/scripts/agent_resultsScript.jsp"%>