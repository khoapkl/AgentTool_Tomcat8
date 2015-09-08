package com.dell.enterprise.agenttool.actions;

import java.util.List;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EppPromotionRow;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;

public class Checkout2 extends DispatchAction
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.Checkout2");
    private String url;

    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);

        if (agent != null)
        {
            try
            {
                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);
                if (method == null || method.isEmpty())
                {
                    forward = this.dispatchMethod(mapping, form, request, response, "showCheckout");
                }
                else
                {
                    forward = this.dispatchMethod(mapping, form, request, response, method);
                }
            }
            catch (Exception e)
            {
                forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
            }
        }
        else
        {
            forward = mapping.findForward(Constants.SESSION_TIMEOUT);
            Cookie[] cookies = request.getCookies();
            if (cookies != null)
            {
                for (int i = 0; i < cookies.length; i++)
                {
                    if (cookies[i].getName().equals("userLevel"))
                    {
                        if (cookies[i].getValue().equals("login.do"))
                            forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                    }
                }
            }
        }

        return forward;
    }

    public final ActionForward showCheckout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession sessions = request.getSession();

        String forward = "agenttools.checkout2.show";
        try
        {
            LOGGER.info("Execute Action");
            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {

                CustomerServices customerServices = new CustomerServices();
                BasketService basketService = new BasketService();

                String shopperId = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
                Agent agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);

                Customer customer = customerServices.getCustomerByShopperID(shopperId);
                OrderRow orderRow = basketService.getOrderRow(shopperId, agent.getAgentId());

                //boolean result = service.updateOrderRowset(shopperId);		    			
                //request.setAttribute("CustomerName", customer.getShip_to_fname() + " " + customer.getShip_to_lname());

                Boolean byAgent = true;
                if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
                {
                    byAgent = false;
                }
                else
                {
                    byAgent = true;
                }
                boolean usingManager = Converter.stringToBoolean(sessions.getAttribute(Constants.IS_ADMIN).toString());

                List<EstoreBasketItem> listEstoreBasketItem = basketService.getBasketItems(shopperId, byAgent);

                CheckoutService checkoutService = new CheckoutService();
                Object[][] arrayShipping = checkoutService.getShippingPrice(usingManager, listEstoreBasketItem, customer.getShip_to_postal());
                EppPromotionRow eppPromotionRow = basketService.getEppPromotionRow(orderRow.getEpp_id());

                List<EstoreBasketItem> basketItemCheck = checkoutService.getItemCheck(shopperId, byAgent);

                request.setAttribute("ArrayShipping", arrayShipping);
                request.setAttribute(Constants.ATTR_ORDER_ROW, orderRow);
                request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                request.setAttribute(Constants.ATTR_EPP_PROMOTION, eppPromotionRow);
                request.setAttribute(Constants.ATTR_ITEM_BASKET, basketItemCheck);

                if (isCustomer == null)
                {
                    request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                }
            }

        }
        catch (Exception e)
        {
            forward = Constants.ERROR_PAGE_VIEW;
        }
        this.setUrl(forward);
        return mapping.findForward(forward);
    }

    public final ActionForward submitCheckout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        Agent agent = null;
        
        try
        {
        	        	
            if (session.getAttribute(Constants.AGENT_INFO) != null)
            {
                agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
            }
            else
            {
                agent = new Agent();
            }

            String shopperId = session.getAttribute(Constants.SHOPPER_ID).toString();

            String price = (request.getParameter("ship_cost") == null) ? "0" : request.getParameter("ship_cost").toString();
            price = Constants.removeChar(Constants.removeChar(price, '$'), ',');

            Float ship_cost = (request.getParameter("ship_cost") == null) ? 0 : Float.valueOf(price);
            String ship_terms = (request.getParameter("ship_terms") == null) ? "0" : request.getParameter("ship_terms").toString();
            String ship_method = request.getParameter("shipvia").toString();
            String ship_method_id = request.getParameter(ship_method);

            //
            //Temporary adding
            //
            ship_method = ship_method + "_cost";
            if (request.getParameter(ship_method) != null)
            {
                price = request.getParameter(ship_method).toString();
                ship_cost = (request.getParameter(ship_method) == null) ? 0 : Float.valueOf(price);
            }

            /////
            ////
            CheckoutService checkoutService = new CheckoutService();
            checkoutService.orderUpdateShipping(shopperId, ship_method_id, ship_terms, ship_cost,agent.getAgentId());
        }
        catch (Exception e)
        {
            // forward = Constants.ERROR_PAGE_VIEW;
            LOGGER.info("Action submitCheckout Exception");
            e.printStackTrace();
        }
        //Checkout3
        Checkout3 checkout3 = new Checkout3();
        checkout3.showCheckout(mapping, form, request, response);
        // forward = checkout3.getUrl();
        return mapping.findForward(checkout3.getUrl());
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }
}
