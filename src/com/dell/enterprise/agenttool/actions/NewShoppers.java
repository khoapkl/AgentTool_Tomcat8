/*
 * FILENAME
 *     NewShoppersByYear.java
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
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.services.NewShoppersService;
import com.dell.enterprise.agenttool.util.Constants;

/**
 * @author hungnguyen
 **/
public class NewShoppers extends DispatchAction
{

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

                forward = mapping.findForward(Constants.SHOPPER_MANAGEMENT);
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
    public ActionForward getNewShoppersByYear(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        NewShoppersService service = new NewShoppersService();
        String forward = "";
        int year = Constants.I_YEAR();
        if (request.getParameter("year") != null && request.getParameter("type") == null)
        {
            year = Integer.valueOf(request.getParameter("year"));
            forward = Constants.SHOPPER_YEAR_RESULTS;
        }
        else if (request.getParameter("year") != null && request.getParameter("type") != null)
        {
            year = Integer.valueOf(request.getParameter("year"));
            forward = Constants.SHOPPER_YEAR_VIEW;
        }
        else
            forward = Constants.SHOPPER_YEAR_VIEW;
        Map<Integer, String> newShoppers = service.getNewShoppersByYear(year);
        request.setAttribute("iyear", year);
        request.setAttribute("new_shoppers_by_year", newShoppers);

        return mapping.findForward(forward);
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
    public ActionForward getNewShoppersByMonth(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        NewShoppersService service = new NewShoppersService();

        int year = Integer.valueOf(request.getParameter("year"));
        int month = Integer.valueOf(request.getParameter("month"));

        Map<Integer, Integer> newShoppersByMonth = service.getNewShoppersByMonth(year, month);

        request.setAttribute("new_shoppers_by_month", newShoppersByMonth);

        return mapping.findForward(Constants.SHOPPER_MONTH_VIEW);
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
    public ActionForward getNewShoppersByDay(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        NewShoppersService service = new NewShoppersService();

        int year = Integer.valueOf(request.getParameter("year"));
        int month = Integer.valueOf(request.getParameter("month"));
        int day = Integer.valueOf(request.getParameter("day"));
        List<Shopper> newShoppersByDay = service.getNewShoppersByDay(year, month, day, 1);
        int totalRecord = service.getNewShoppersByDayCount();

        request.setAttribute("new_shoppers_by_day", newShoppersByDay);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        return mapping.findForward(Constants.SHOPPER_DAY_VIEW);
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
    public ActionForward movingNewShoppersByDay(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        NewShoppersService service = new NewShoppersService();

        int year = Integer.valueOf(request.getParameter("year"));
        int month = Integer.valueOf(request.getParameter("month"));
        int day = Integer.valueOf(request.getParameter("day"));

        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);

        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = Integer.valueOf(request.getParameter("numOfPage"));
        }

        List<Shopper> newShoppersByDay = service.getNewShoppersByDay(year, month, day, noPage);

        request.setAttribute("new_shoppers_by_day", newShoppersByDay);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);

        return mapping.findForward(Constants.SHOPPER_DAY_VIEW_RESULT);
    }
}
