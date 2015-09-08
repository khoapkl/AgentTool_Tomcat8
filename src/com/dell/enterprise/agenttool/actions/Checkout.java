/*
 * FILENAME
 *     Checkout.java
 *
 * FILE LOCATION
 *     $Source$
 *
 * VERSION
 *     $Id$
 *         @version       $Revision$
 *         Check-Out Tag: $Name$
 *         Locked By:     $Lockers$
 *
 * FORMATTING NOTES
 *     * Lines should be limited to 120 characters.
 *     * Files should contain no tabs.
 *     * Indent code using four-character increments.
 *
 * COPYRIGHT
 *     Copyright (C) 2010 HEB. All rights reserved.
 *     This software is the confidential and proprietary information of
 *     HEB ("Confidential Information"). You shall not
 *     disclose such Confidential Information and shall use it only in
 *     accordance with the terms of the licence agreement you entered into
 *     with HEB.
 */

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

import com.dell.enterprise.agenttool.DAO.ProductDAO;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.WarrantyPartList;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.model.Product;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.util.Constants;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * TODO Add one-sentence summarising the class functionality here; this sentence
 * should only contain one full-stop.
 * </p>
 * <p>
 * TODO Add detailed HTML description of class here, including the following,
 * where relevant:
 * <ul>
 * <li>TODO Description of what the class does and how it is done.</li>
 * <li>TODO Explanatory notes on usage, including code examples.</li>
 * <li>TODO Notes on class dynamic behaviour e.g. is it thread-safe?</li>
 * </ul>
 * </p>
 * <p>
 * <h2>Responsibilities</h2>
 * </p>
 * <p>
 * <ul>
 * <li>TODO Reponsibility #1.</li>
 * <li>TODO Reponsibility #2.</li>
 * <li>TODO Reponsibility #3.</li>
 * <li>TODO Reponsibility #4.</li>
 * </ul>
 * </p>
 * <p>
 * <h2>Protected Interface</h2>
 * </p>
 * <p>
 * TODO Document the protected interface here, including the following:
 * <ul>
 * <li>TODO Which aspects of the class's functionality can be extended.</li>
 * <li>TODO How this extension is done, including usage examples.</li>
 * </ul>
 * </p>
 * 
 * @see TODO Related Information
 * 
 * @author vinhhq
 * 
 * @version $Id$
 **/
public class Checkout extends DispatchAction
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.Checkout");

    private String url;

    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;

        try
        {
            HttpSession sessions = request.getSession();
            if (sessions.getAttribute(Constants.AGENT_INFO) == null)
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
            else
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
        }
        catch (Exception e)
        {
            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
        }

        return forward;
    }

    public final ActionForward showCheckout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.SHOW_CHECKOUT;

        try
        {
            LOGGER.info("Action showCheckout");

            HttpSession sessions = request.getSession();
            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
            	
                String shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
                Agent agent = (Agent)sessions.getAttribute(Constants.AGENT_INFO);
                Boolean byAgent = true;
                if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
                {
                    byAgent = false;
                }
                else
                {
                    byAgent = true;
                }
                
                //LOGGER.info("Action showCheckout shopper_id : " + shopper_id);
                if (!Constants.isNumber(shopper_id))
                {
                    /*
                     * Load data for checkout
                     */
                    CheckoutService checkoutService = new CheckoutService();
                    List<EstoreBasketItem> basketItemCheck = checkoutService.getItemCheck(shopper_id,byAgent);
                    
                    //get customer
                    CustomerServices customerServices = new CustomerServices();
                    Customer customer = customerServices.getCustomerByShopperID(shopper_id);
                    //set is session because method no pass page Customer information

                    //Get order
                    BasketService basketService = new BasketService();
                    OrderRow orderRow = basketService.getOrderRow(shopper_id,agent.getAgentId());

                    //Get Max Discount
                    int maxDiscount = checkoutService.getMaxDiscount();
                    
                    //Get Check category by Shopper
                    int checkCat = checkoutService.getCheckCat(shopper_id,byAgent);
                    
                    //Get qty of notebook
                    int qtyNBCat = checkoutService.getCatQty(shopper_id, 11946);
                    
                  //Get qty of desktop
                    int qtyDTCat = checkoutService.getCatQty(shopper_id, 11949);
                    
                    //check if warranty already existed in basket
                    Boolean existWarrantyNB = false;
                    Boolean existWarrantyDT = false;
                    for(EstoreBasketItem  estoreBasketItemTemp : basketItemCheck)
        			{
                    	if (estoreBasketItemTemp.getCategory_id() == 11940){
                    		existWarrantyNB = true;
                    	}
                    	if (estoreBasketItemTemp.getCategory_id() == 11941){
                    		existWarrantyDT = true;
                    	}
        			}
                    
                  //get warranty list
                    List<WarrantyPartList> listWarrantyPart = null;
                    if (qtyNBCat > 0 && qtyDTCat > 0 && !existWarrantyNB && !existWarrantyDT){
                    	listWarrantyPart = checkoutService.getWarrantyPartList(11946, 11949);
                    }
                    else if (qtyNBCat > 0 && !existWarrantyNB){
                    	listWarrantyPart = checkoutService.getWarrantyPartList(11946, 1);
                    }
                    else if (qtyDTCat > 0 && !existWarrantyDT){
                    	listWarrantyPart = checkoutService.getWarrantyPartList(11949, 1);
                    }
                    else {
                        listWarrantyPart = checkoutService.getWarrantyPartList(1,2);
                    }
                    
                    request.setAttribute(Constants.ATTR_ITEM_BASKET, basketItemCheck);
                    request.setAttribute(Constants.ATTR_LIST_WARRANTY, listWarrantyPart);
                    request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                    request.setAttribute(Constants.ATTR_ORDER_ROW, orderRow);
                    request.setAttribute(Constants.ATTR_MAX_DISCOUNT, maxDiscount);
                    request.setAttribute(Constants.ATTR_CHECK_CAT, checkCat);
                    request.setAttribute(Constants.ATTR_QTY_NB, qtyNBCat);
                    request.setAttribute(Constants.ATTR_QTY_DT, qtyDTCat);
                    
                    if(isCustomer == null)
                    {
                        request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                    }
                }
            }
            setUrl(forward);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        
        ActionForward af = mapping.findForward(forward);
        return af;
    }

    public final ActionForward submitCheckout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        //String forward = Constants.SHOW_CHECKOUT_2;
        try
        {
            LOGGER.info("Action submitCheckout");

            HttpSession sessions = request.getSession();
            CheckoutService checkoutService = new CheckoutService();
            BasketService basketService = new BasketService();

            //String promotionCode = "";
            String shopper_id = "";
            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
            }

            if (request.getParameter("promotion_code_tf") != null && !request.getParameter("promotion_code_tf").isEmpty())
            {
                //promotionCode = request.getParameter("promotion_code_tf");

//                int promotionContrib = checkoutService.getPromotionContribByCode(promotionCode);
//                if (promotionContrib > 0)
//                {
//                    Set Promotion code for estore_basket
//                    basketService.updatePromotionCodeBasketItem(promotionCode, shopper_id);
//                    set Discount value for discount_item
//                    OrderRow orderRow = basketService.getOrder(shopper_id);
//                    checkoutService.setDiscountItem(promotionContrib, orderRow.getOrder_id());
//
//                }
            }
            else
            {
            	//set Discounted Price for each item basket
                checkoutService.setOrderLineItemDiscounts(shopper_id, request);
                //Set Promotion code null for estore_basket
                basketService.updatePromotionCodeBasketItem("", shopper_id);
                Agent agent = (Agent)sessions.getAttribute(Constants.AGENT_INFO);
                OrderRow orderRow = basketService.getOrder(shopper_id, agent.getAgentId());
                basketService.updateBasketDiscount(Integer.parseInt(request.getParameter("massdiscount")), Float.parseFloat(request.getParameter("adj_discount")), shopper_id, orderRow.getOrder_id());
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Action submitCheckout Exception");
            e.printStackTrace();
            //forward = Constants.ERROR_PAGE_VIEW;
        }

        //Checkout2
        Checkout2 checkout2 = new Checkout2();
        checkout2.showCheckout(mapping, form, request, response);

        return mapping.findForward(checkout2.getUrl());
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getUrl()
    {
        return url;
    }
}
