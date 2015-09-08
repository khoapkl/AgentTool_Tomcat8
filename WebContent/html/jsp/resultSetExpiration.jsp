<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.dell.enterprise.agenttool.util.Constants"%>

<%
String result=(String) request.getAttribute(Constants.SHOPPER_SET_EXPRIRATION_DATE);
String createDay=(String) request.getAttribute(Constants.SHOPPER_GET_CREATE_DATE);
String currentday=Constants.getCurrentDate();
String today=Constants.getCurrentDate();
	
if(result.equals("true"))
{
    out.print("Set expiration date is successful.");
}
else if(result.equals("before"))
{
    out.print("The created date is "+createDay + ".\n"+"The new Exp Date should be after today "+today +".");
}
else 
{
    out.print("Set expiration date is error.");
}
%>