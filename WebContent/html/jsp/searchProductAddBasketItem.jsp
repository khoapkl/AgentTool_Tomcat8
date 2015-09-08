<%@page import="com.dell.enterprise.agenttool.util.Constants"%>
<%
    if (request.getAttribute(Constants.ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT) != null)
    {
        int result = Integer.parseInt(request.getAttribute(Constants.ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT).toString());
        if (result == 1)
        {
            out.print("ok");
        }
        else if (result == 2)
        {
            out.print("another");
        }
    }
%>