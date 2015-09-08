/*
 * FILENAME
 *     StartPage.java
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

import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.util.Constants;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//
public class StartPage extends Action
{

    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.StartPage");

    /**
     * {@inheritDoc}
     */
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();

        try
        {
            if (session.getAttribute(Constants.AGENT_INFO) == null)
            {
                forward = mapping.findForward(Constants.SESSION_TIMEOUT);
                Cookie[] cookies = request.getCookies();
                for (int i = 0; i < cookies.length; i++)
                {
                    if (cookies[i].getName().equals("userLevel"))
                    {
                        if (cookies[i].getValue().equals("login.do"))
                            forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                    }
                }

            }
            else
            {
                forward = mapping.findForward("agenttools.defaultpage");
            }
            
            //vinhhq
            if(session.getAttribute(Constants.SHOPPER_ID) != null)
            {
                Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
                String shopper_id = session.getAttribute(Constants.SHOPPER_ID).toString();
                
                if(isCustomer == null && !Constants.isNumber(shopper_id))
                {
                    //get customer
                    CustomerServices customerServices = new CustomerServices();
                    Customer customer = customerServices.getCustomerByShopperID(shopper_id);
                    
                    request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                    request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                }
            }
            
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
        }

        return forward;
    }
}