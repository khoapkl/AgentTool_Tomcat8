package com.dell.enterprise.agenttool.actions;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.dell.enterprise.agenttool.services.SecurityService;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.model.*;


public class SecuritySetup extends DispatchAction {
	
	 private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.SecuritySetup");

	 public final ActionForward securityEdit(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {	
	    	String forward = "";
	    	SecurityService securityservice = new SecurityService();
	    	Security security= null ;
	 		try{	 

	             int lockoutCount =Integer.parseInt(request.getParameter("lockoutCount"));
	             int lockoutTime = Integer.parseInt(request.getParameter("lockoutTime"));
	             int expiryDays = Integer.parseInt(request.getParameter("expiryDays"));
	             int charNumber = Integer.parseInt(request.getParameter("charNumber"));
	             int resetHistory = Integer.parseInt(request.getParameter("resetHistory"));
	             boolean isUppercase = false;
	             boolean isNumber = false;
	             boolean isSymbol = false;
	            if (request.getParameter("isUppercase") != null) 
	             {  
	            	 isUppercase = true;  
	             }
	            if (request.getParameter("isNumber") != null) 
	             {  
	            	isNumber = true;  
	             }
	            if (request.getParameter("isSymbol") != null) 
	             {  
	            	isSymbol = true;  
	             }
	             
	             security = securityservice.getSecurity(1);
	             securityservice.updateSecurity(1, lockoutTime, lockoutCount, expiryDays, charNumber, resetHistory , isUppercase, isNumber, isSymbol );
	             request.setAttribute("messageSuces", "Security Settings saved successfully.");
	             forward = Constants.SECURITY_SETTING_VIEW;
	 			
	 		}catch (Exception e) {
				// TODO: handle exception
	 			 /*request.setAttribute("messageError", "Security Settings cannot be saved. Pleasetry again");  */
	             forward = Constants.SECURITY_SETTING_VIEW;
			}
	 		security = securityservice.getSecurity(1);
	 		request.setAttribute("security",security);
	 		return mapping.findForward(forward);
         	
	    }
	 
	 
	 public final ActionForward securitySetup(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {	
	    	String forward = "";
	    	SecurityService securityservice = new SecurityService();
	    	Security security = null ;
	 		try{
	 			security = securityservice.getSecurity(1);
	 			request.setAttribute("security",security);
	 			forward = Constants.SECURITY_SETTING_VIEW;
	 		}catch (Exception e) {
				// TODO: handle exception
	 			forward = "agenttool.agentSetup";
			}
      	return mapping.findForward(forward);
	    }
	 

}
