/*
 * FILENAME
 *     ShopperList.java
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.services.ShopperListService;
import com.dell.enterprise.agenttool.util.Constants;

/**
 * @author hungnguyen
 **/
public class ShopperList extends DispatchAction
{
    private static final int FIRST_PAGE = 1;
    private static final int PREV_PAGE = 2;
    private static final int NEXT_PAGE = 3;
    private static final int LAST_PAGE = 4;

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();

        try
        {
            String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);

            if (session.getAttribute(Constants.AGENT_INFO) == null)
            {
                forward = mapping.findForward(Constants.SESSION_TIMEOUT);
            }
            else if (method == null || "".equals(method))
            {
                forward = mapping.findForward(Constants.SHOPPER_LIST);
            }
            else
            {
                forward = this.dispatchMethod(mapping, form, request, response, method);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
        }

        return forward;
    }

    /**
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     **/
    public ActionForward getShoppers(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ShopperListService service = new ShopperListService();
        HttpSession session = request.getSession();

        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            Shopper searchCriteria = new Shopper();
            searchCriteria.setLinkNumber(request.getParameter("linknumber").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setShipToFName(request.getParameter("ship_to_fname").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setShipToLName(request.getParameter("ship_to_lname").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setShipToCompany(request.getParameter("ship_to_company").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setShipToPhone(request.getParameter("ship_to_phone").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setShipToEmail(request.getParameter("ship_to_email").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setBillToFName(request.getParameter("bill_to_fname").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setBillToLName(request.getParameter("bill_to_lname").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setBillToCompany(request.getParameter("bill_to_company").trim().replace("_", "\\_").replace("%", "\\%"));
            searchCriteria.setBillToPhone(request.getParameter("bill_to_phone").trim().replace("_", "\\_").replace("%", "\\%"));
            
            session.setAttribute(Constants.SHOPPER_SEARCH_CRITERIA, searchCriteria);
            List<Shopper> shopperList = service.getShoppers(1, searchCriteria);
            int shopperCount = service.getTotalRowCount();
            int pageCount = 0;
            int currentPage = 0;

            if (shopperCount > 0)
            {
                if (shopperCount < Constants.SHOPPER_LIST_RECORDS_PER_PAGE)
                {
                    pageCount = 1;
                }
                else
                {
                    pageCount = shopperCount / Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
                    pageCount = (shopperCount % Constants.SHOPPER_LIST_RECORDS_PER_PAGE > 0) ? pageCount + 1 : pageCount;
                }

                currentPage = 1;
            }

            session.setAttribute("shopper_list", shopperList);
            session.setAttribute("shopper_count", shopperCount);
            session.setAttribute("shopper_page_count", pageCount);
            session.setAttribute("shopper_current_page", currentPage);
            session.setAttribute("shopper_search_criteria", searchCriteria);
        }

        return mapping.findForward(Constants.SHOPPER_LIST_RESULTS);
    }

    /**
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     **/
    public ActionForward changePage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ShopperListService service = new ShopperListService();
        HttpSession session = request.getSession();

        int pageCount = (Integer) session.getAttribute("shopper_page_count");
        int currentPage = (Integer) session.getAttribute("shopper_current_page");
        int changeCase = Integer.valueOf(request.getParameter("changeCase"));
        Shopper searchCriteria = (Shopper) session.getAttribute("shopper_search_criteria");

        switch (changeCase)
        {
            case FIRST_PAGE:
                currentPage = 1;
                break;

            case PREV_PAGE:
                currentPage--;
                break;

            case NEXT_PAGE:
                currentPage++;
                break;

            case LAST_PAGE:
                currentPage = pageCount;
                break;

            default:
                break;
        }

        session.setAttribute("shopper_list", service.getShoppers(currentPage, searchCriteria));
        session.setAttribute("shopper_current_page", currentPage);

        return mapping.findForward(Constants.SHOPPER_LIST_RESULTS);
    }
    
    public final ActionForward backShopperForwardPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        request.setAttribute("backPage", true);
        return mapping.findForward(Constants.SHOPPER_LIST);
    }
    
    public final ActionForward backShopperPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.SHOPPER_LIST_RESULTS;
        HttpSession session = request.getSession();
        
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {                       
            request.setAttribute("shopper_list",  session.getAttribute("shopper_list"));
            request.setAttribute("shopper_count", session.getAttribute("shopper_count"));
            request.setAttribute("shopper_current_page", session.getAttribute("shopper_current_page"));
        }
        
        return mapping.findForward(forward);
    }
}
