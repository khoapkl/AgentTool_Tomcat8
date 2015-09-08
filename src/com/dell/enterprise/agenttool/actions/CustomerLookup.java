package com.dell.enterprise.agenttool.actions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.util.Constants;

public class CustomerLookup extends DispatchAction {
	
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.CustomerLookup");

    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();
        Agent agent = (Agent)session.getAttribute(Constants.AGENT_INFO);
    	       	
    	if (agent != null)
    	{
    		 try{
    	                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);
    	                if (method == null || method.isEmpty())
    	                {
    	                    String addNew = request.getParameter("manage");
    	                    if(addNew!=null && addNew.equals("true"))
    	                        session.setAttribute(Constants.CUSTOMER_MANAGE,true);
    	                    else
    	                        session.removeAttribute(Constants.CUSTOMER_MANAGE);
    	                    forward = this.dispatchMethod(mapping, form, request, response, "viewSearchCustomer");
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
    	String forward = "agenttool.customerLookup.result";
    	
        try{
            HttpSession session = request.getSession();            
            if(session.getAttribute(Constants.IS_CUSTOMER)==null){
            	String resultParam = request.getParameter("results");
            	if ((resultParam != null) && (resultParam.equalsIgnoreCase("true")))
            	{
                LOGGER.info("Execute Action");
                CustomerServices service = new CustomerServices();
                
                int pageRecord = Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
                        
                   
                String linkNumber = request.getParameter(Constants.DB_FIELD_LINK_NUMBER);
                String shipFname = request.getParameter(Constants.DB_FIELD_SHIP_FNAME);
                String shipLname = request.getParameter(Constants.DB_FIELD_SHIP_LNAME);
                String shipCompany = request.getParameter(Constants.DB_FIELD_SHIP_COMPANY);
                String shipPhone = request.getParameter(Constants.DB_FIELD_SHIP_PHONE);
                String billCompany = request.getParameter(Constants.DB_FIELD_BILL_COMPANY);
                String billFname = request.getParameter(Constants.DB_FIELD_BILL_FNAME);
                String billLname = request.getParameter(Constants.DB_FIELD_BILL_LNAME);
                String billPhone = request.getParameter(Constants.DB_FIELD_BILL_PHONE);
        		                
                
                Map<String,Customer> customers = service.searchCustomer(1, pageRecord, linkNumber, shipFname, 
                		shipLname, shipCompany, shipPhone, billCompany, billFname, billLname, billPhone);
                
                int totalRecord = 0;
                if (!customers.isEmpty())
                {            	
                	for(Iterator i = customers.values().iterator(); i.hasNext();)
                    {
                		Customer customer = (Customer)i.next();
                		if (customer != null)
                		{
                			totalRecord = customer.getTotalRow();
                			break;
                		}
                    }
                }
    
                int numOfPage = Math.round(totalRecord / pageRecord);
                numOfPage = (totalRecord % pageRecord > 0) ? (numOfPage + 1) : numOfPage;
                                        
                session.setAttribute("totalRecord", totalRecord);
                session.setAttribute("numOfPage", numOfPage);
                session.setAttribute("noPage", 1);
                
                request.setAttribute("RESULT", customers);
            	}
            }
        }catch(Exception e){
        	forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }    
    
    public final ActionForward viewSearchCustomer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
    	HttpSession session = request.getSession();
    	
    	if(session.getAttribute(Constants.SHOPPER_ID) != null)
    	{
    		String shopperId = session.getAttribute(Constants.SHOPPER_ID).toString();
    		if (shopperId.toString().length() == 32)
        	{
    			CustomerServices service = new CustomerServices();
        		Customer customer = service.getCustomerByShopperID(shopperId);        		
        		request.setAttribute("CustomerName", customer.getShip_to_fname() + " " + customer.getShip_to_lname());
        	}    	    		
    		
            Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }
            
            CheckoutService checkoutService = new CheckoutService();
            List<EstoreBasketItem> basketItemCheck = checkoutService.getItemCheck(shopperId,byAgent);
            
            request.setAttribute(Constants.ATTR_ITEM_BASKET, basketItemCheck);
            if(isCustomer == null)
            {
                CustomerServices customerServices = new CustomerServices();
                Customer customer = customerServices.getCustomerByShopperID(shopperId);
                request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                request.setAttribute(Constants.ATTR_CUSTOMER, customer);
            }
    	}
    	
        return mapping.findForward("agenttool.customerLookup");
    }
       
    @SuppressWarnings("unchecked")
    public final ActionForward pagingMove(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "agenttool.customerLookup.result";
        CustomerServices service = new CustomerServices();
        HttpSession session = request.getSession();
        int pageRecord = Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
        
        try{
        	 int totalRecord = Integer.valueOf(request.getParameter("totalRecord"));
             int numOfPage = Integer.valueOf(request.getParameter("numOfPage"));
             int noPage = Integer.valueOf(request.getParameter("noPage"));
             int casePaging = Integer.valueOf(request.getParameter("casePage"));
             
             String linkNumber = request.getParameter(Constants.DB_FIELD_LINK_NUMBER);
             String shipFname = request.getParameter(Constants.DB_FIELD_SHIP_FNAME);
             String shipLname = request.getParameter(Constants.DB_FIELD_SHIP_LNAME);
             String shipCompany = request.getParameter(Constants.DB_FIELD_SHIP_COMPANY);
             String shipPhone = request.getParameter(Constants.DB_FIELD_SHIP_PHONE);
             String billCompany = request.getParameter(Constants.DB_FIELD_BILL_COMPANY);
             String billFname = request.getParameter(Constants.DB_FIELD_BILL_FNAME);
             String billLname = request.getParameter(Constants.DB_FIELD_BILL_LNAME);
             String billPhone = request.getParameter(Constants.DB_FIELD_BILL_PHONE);
     		
            
             
             if (casePaging == 3 && noPage < numOfPage)
             {
                 int lastPage = (pageRecord * (noPage + 1) > totalRecord) ? totalRecord : pageRecord * (noPage + 1);
               
                 Map<String,Customer> customers = service.searchCustomer(pageRecord * noPage + 1, lastPage, linkNumber, shipFname, 
                 		shipLname, shipCompany, shipPhone, billCompany, billFname, billLname, billPhone);
                 
                 request.setAttribute("RESULT", customers);
                 noPage += 1;
             }
             if (casePaging == 2 && noPage > 1)
             {
                 int firstPage = (noPage > 1) ? pageRecord * (noPage - 2) + 1 : 1;
                 Map<String, Customer> customers = service.searchCustomer(firstPage, (noPage - 1) * pageRecord, linkNumber, shipFname, 
                 		shipLname, shipCompany, shipPhone, billCompany, billFname, billLname, billPhone);
                 request.setAttribute("RESULT", customers);
                 noPage = (noPage > 1) ? noPage - 1 : 1;
             }
             if (casePaging == 4)
             {
                 noPage = numOfPage;
                 Map<String, Customer> customers = service.searchCustomer((noPage - 1) * pageRecord + 1, totalRecord, linkNumber, shipFname, 
                 		shipLname, shipCompany, shipPhone, billCompany, billFname, billLname, billPhone);
                 request.setAttribute("RESULT", customers);
             }
             if (casePaging == 1)
             {
                 noPage = 1;
                 Map<String, Customer> customers = service.searchCustomer(1, pageRecord, linkNumber, shipFname, 
                 		shipLname, shipCompany, shipPhone, billCompany, billFname, billLname, billPhone);
                 request.setAttribute("RESULT", customers);
             }
             session.setAttribute("totalRecord", totalRecord);
             session.setAttribute("numOfPage", numOfPage);
             session.setAttribute("noPage", noPage);       
        }catch(Exception e){
        	forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }
}
