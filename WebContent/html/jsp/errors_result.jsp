<%@page import="java.util.List"%>

<%
List<String> errors = (List<String>)request.getAttribute("errorsList");

if (errors != null)
{
	StringBuilder html = new StringBuilder();
	html.append("<UL>");
	for(int i=0;i<errors.size();i++)
	{
		html.append("<LI>");
		html.append("<font face=\"Arial, Helvetica\" size=\"2\" color=\"red\"><b>");
		html.append(errors.get(i).toString());
		html.append("</b></font>");
		html.append("</LI>");
	}
	html.append("</UL>");
	
	out.println(html);
}
else
{		
	out.println("<script language='javascript'>dialogOpen(); $('#NewUser').submit();</script>");
}
%>

