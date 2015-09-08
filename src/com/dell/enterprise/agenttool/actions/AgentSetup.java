package com.dell.enterprise.agenttool.actions;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.services.AgentService;
import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.services.SecurityService;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;

public class AgentSetup extends DispatchAction {
	
	 private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.AgentSetup");

	 public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
	 {
	        ActionForward forward;
	        HttpSession session = request.getSession();
	        Agent agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
	    	       	
	    	if ((agent != null) && (agent.isAdmin() || agent.getUserLevel().equalsIgnoreCase("A")))
	    	{
	    		 try{
	    	                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);
	    	                if (method == null || method.isEmpty())
	    	                {	
	    	                    
	    	                    request.setAttribute(Constants.IS_ADMIN, true);
	    	                    forward =  mapping.findForward("agenttool.agentSetup");
	    	                }
	    	                else
	    	                {
	    	                    forward = this.dispatchMethod(mapping, form, request, response, method);
	    	                }
	    	        }catch (Exception e)
	    	        {
	    	            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
	    	        }    		
	    	}
	    	else
	    	{
	    		forward = mapping.findForward(Constants.LOGIN_VIEW);
	    	}
	           
	        return forward;
	    }

	    public final ActionForward search(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agentSetup.result";
	    	
	        try{
	            HttpSession session = request.getSession();            
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	                
	                int pageRecord = Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
	                  			            
	                int agentId = Constants.convertValueEmpty(request.getParameter(Constants.ORDER_AGENT_ID)).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter(Constants.ORDER_AGENT_ID));
	                String fullname = request.getParameter("fullname");
	                String username = request.getParameter("username");
	                String userLevel = request.getParameter(Constants.USER_LEVEL);
	                String email = request.getParameter("email");
	                int isReport = Constants.convertValueEmpty(request.getParameter("isReport")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("isReport"));	                
	                int isAdmin = Constants.convertValueEmpty(request.getParameter("isAdmin")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("isAdmin")); 
	                int isActive = Constants.convertValueEmpty(request.getParameter("isActive")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("isActive")); 
	                int userType = Constants.convertValueEmpty(request.getParameter("userType")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("userType")); 
	          
	                List<Agent> agents = service.searchAgent(1, pageRecord, agentId, fullname, 
	                		username, userLevel, email, isReport, isAdmin, isActive, userType);
	                
	                int totalRecord = 0;
	                if (!agents.isEmpty())
	                {            	
	                	for(int i = 0;i < agents.size();i++)
	                    {	                		
	                		if (agents.get(i) != null)
	                		{
	                			totalRecord = agents.get(i).getTotalRow();
	                			break;
	                		}
	                    }
	                }
	    
	                int numOfPage = Math.round(totalRecord / pageRecord);
	                numOfPage = (totalRecord % pageRecord > 0) ? (numOfPage + 1) : numOfPage;
	                                        
	                session.setAttribute("totalRecord", totalRecord);
	                session.setAttribute("numOfPage", numOfPage);
	                session.setAttribute("noPage", 1);
	                
	                request.setAttribute("RESULT", agents);
	            	
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }
	        return mapping.findForward(forward);
	    }  
	    
	    public final ActionForward pagingMove(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	        String forward = "agenttool.agentSetup.result";
	        AgentService service = new AgentService();
	        HttpSession session = request.getSession();
	        int pageRecord = Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
	        
	        try{
	        	 int totalRecord = Integer.valueOf(request.getParameter("totalRecord"));
	             int numOfPage = Integer.valueOf(request.getParameter("numOfPage"));
	             int noPage = Integer.valueOf(request.getParameter("noPage"));
	             int casePaging = Integer.valueOf(request.getParameter("casePage"));
	             
	             int agentId = Constants.convertValueEmpty(request.getParameter(Constants.ORDER_AGENT_ID)).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter(Constants.ORDER_AGENT_ID));
	             String fullname = request.getParameter("fullname");
	             String username = request.getParameter("username");
	             String userLevel = request.getParameter(Constants.USER_LEVEL);
	             String email = request.getParameter("email");
	             int isReport = Constants.convertValueEmpty(request.getParameter("isReport")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("isReport"));	                
	             int isAdmin = Constants.convertValueEmpty(request.getParameter("isAdmin")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("isAdmin")); 
	             int isActive = Constants.convertValueEmpty(request.getParameter("isActive")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("isActive")); 
	             int userType = Constants.convertValueEmpty(request.getParameter("userType")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("userType")); 
	            
	             
	             if (casePaging == 3 && noPage < numOfPage)
	             {
	                 int lastPage = (pageRecord * (noPage + 1) > totalRecord) ? totalRecord : pageRecord * (noPage + 1);
	               
	                 List<Agent> agents = service.searchAgent(pageRecord * noPage + 1, lastPage, agentId, fullname, 
		                		username, userLevel, email, isReport, isAdmin, isActive, userType);
	                 
	                 request.setAttribute("RESULT", agents);
	                 noPage += 1;
	             }
	             if (casePaging == 2 && noPage > 1)
	             {
	                 int firstPage = (noPage > 1) ? pageRecord * (noPage - 2) + 1 : 1;
	                 List<Agent> agents = service.searchAgent(firstPage, (noPage - 1) * pageRecord, agentId, fullname, 
		                		username, userLevel, email, isReport, isAdmin, isActive, userType);
	                 request.setAttribute("RESULT", agents);
	                 noPage = (noPage > 1) ? noPage - 1 : 1;
	             }
	             if (casePaging == 4)
	             {
	                 noPage = numOfPage;
	                 List<Agent> agents = service.searchAgent((noPage - 1) * pageRecord + 1, totalRecord, agentId, fullname, 
		                		username, userLevel, email, isReport, isAdmin, isActive, userType);
	                 request.setAttribute("RESULT", agents);
	             }
	             if (casePaging == 1)
	             {
	                 noPage = 1;
	                 List<Agent> agents = service.searchAgent(1, pageRecord, agentId, fullname, 
		                		username, userLevel, email, isReport, isAdmin, isActive, userType);
	                 request.setAttribute("RESULT", agents);
	             }
	             session.setAttribute("totalRecord", totalRecord);
	             session.setAttribute("numOfPage", numOfPage);
	             session.setAttribute("noPage", noPage);       
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }
	        return mapping.findForward(forward);
	    }
	    
	    public final ActionForward addAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agentSetup.manage";
	    	 boolean result = false;
	        try{
	            HttpSession session = request.getSession();            
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	                SecurityService security = new SecurityService();
	           
	                String mngUsername = request.getParameter("mngUsername");
	                LOGGER.info("Execute addAgent Action user:" + mngUsername);
	                String mngEmail = request.getParameter("mngEmail");
	                String mngUserLevel = request.getParameter("mngUserLevel");
	                String tmpPassword = request.getParameter("mngPassword");
	                String mngFullname = request.getParameter("mngFullname");
	                
	                int isReport = Constants.convertValueEmpty(request.getParameter("mngIsReport")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngIsReport"));	                
	                int isAdmin = Constants.convertValueEmpty(request.getParameter("mngIsAdmin")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngIsAdmin")); 
	                int isActive = Constants.convertValueEmpty(request.getParameter("mngIsActive")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngIsActive")); 
	                int userType = Constants.convertValueEmpty(request.getParameter("mngUserType")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngUserType")); 
	                
	                String mngPassword= security.encryptPassword(tmpPassword);  	
	           
	                result = service.addAgent(mngUsername, mngPassword, mngEmail, mngFullname, 
	                		mngUserLevel, isReport, isAdmin, isActive, userType);
	                AuthenticationService serviceAgent = new AuthenticationService();
	                SecurityService securityservice = new SecurityService();
	                Date currentTime = new Date();
	                serviceAgent.updateLoginCount(mngUsername, 0);
	                serviceAgent.updateLoginTime(mngUsername,
							securityservice.formatDate(currentTime));
	                serviceAgent.updatePasswordDate(mngUsername, securityservice.formatDate(currentTime));
	                
	                
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }
	        if (result)
	        	return null;
	        else
	        	return mapping.findForward(forward);
	    }  
	    
	    public final ActionForward deleteAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agentSetup.manage";
	    	boolean result = false;
	        try{
	            HttpSession session = request.getSession();            
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	           
	                int agentId =  Constants.convertValueEmpty(request.getParameter("agentId")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("agentId"));
	               
	                result = service.deleteAgent(agentId);	             	     	            	
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }
	        if (result)
	        	return null;
	        else
	        	return mapping.findForward(forward);
	    }  
	    
	    public final ActionForward checkExistUser(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agentSetup.manage";
	    	boolean result = false;
	        try{
	            HttpSession session = request.getSession();            
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	           
	                String mngUsername = request.getParameter("mngUsername");
	                
	                result = service.isExistUser(mngUsername);	
	                
	                if (result)	                
	                	request.setAttribute("EXIST_USER", true);
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }	    
	        	return mapping.findForward(forward);
	    }
	    
	    public final ActionForward checkInUseAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agent.new";
	    	boolean result = false;
	        try{
	            HttpSession session = request.getSession();            
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	           
	                int mngAgentId = Converter.stringToInt(request.getParameter("agentId"));	                
	                result = service.isInUseAgent(mngAgentId);	
	                
	                if (result)	                
	                	request.setAttribute("AGENT_IN_USE", true);
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }	    
	        	return mapping.findForward(forward);
	    }  
	    
	    public final ActionForward getAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agent.edit";
	    	
	        try{
	            HttpSession session = request.getSession();            
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	           
	                int agentId =  Constants.convertValueEmpty(request.getParameter("agentId")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("agentId"));
	               
	                Agent agent = service.getAgent(agentId);	
	                if (agent != null)
	                	request.setAttribute("EDIT_AGENT", agent);
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }	       
	        
	        return mapping.findForward(forward);
	    }  
	    
	    public final ActionForward newAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agent.new";   
	        
	        return mapping.findForward(forward);
	    } 
	    
	    public final ActionForward updateAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
	    {
	    	String forward = "agenttool.agentSetup.manage";
	    	 boolean result = false;
	    	 
	        try{
	            HttpSession session = request.getSession();  
	            SecurityService security = new SecurityService();
	            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
	            	
	                LOGGER.info("Execute Action");
	                AgentService service = new AgentService();
	           
	                String mngUsername = request.getParameter("mngUsername");
	                String mngEmail = request.getParameter("mngEmail");
	                String mngUserLevel = request.getParameter("mngUserLevel");
	                String tmpPassword = request.getParameter("mngPassword");
	                String mngFullname = request.getParameter("mngFullname");
	                String mngPassword= security.encryptPassword(tmpPassword); 
	                
	                int isReport = Constants.convertValueEmpty(request.getParameter("mngIsReport")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngIsReport"));	                
	                int isAdmin = Constants.convertValueEmpty(request.getParameter("mngIsAdmin")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngIsAdmin")); 
	                int isActive = Constants.convertValueEmpty(request.getParameter("mngIsActive")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngIsActive")); 
	                int userType = Constants.convertValueEmpty(request.getParameter("mngUserType")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngUserType")); 
	                int agentId = Constants.convertValueEmpty(request.getParameter("mngAgentId")).isEmpty()?Integer.MAX_VALUE:Integer.valueOf(request.getParameter("mngAgentId")); 
	                int agentLoginCount = Integer.valueOf(request.getParameter("mngLoginCount"));
	  	          
	                result = service.updateAgent(agentId,mngUsername, mngPassword, mngEmail, mngFullname, 
	                		mngUserLevel, isReport, isAdmin, isActive, userType, agentLoginCount);	             	     	            	
	            }
	        }catch(Exception e){
	        	forward = Constants.ERROR_PAGE_VIEW;
	        }
	        if (result)
	        	return null;
	        else
	        	return mapping.findForward(forward);
	    }  
}
