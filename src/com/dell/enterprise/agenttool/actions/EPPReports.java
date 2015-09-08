/*
 * FILENAME
 *     EPPReports.java
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
import com.dell.enterprise.agenttool.model.EppReport;
import com.dell.enterprise.agenttool.services.ReportServices;
import com.dell.enterprise.agenttool.util.Constants;

/**
 * @author hungnguyen, thuynguyen
 **/
public class EPPReports extends DispatchAction
{

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();

        if (session.getAttribute(Constants.AGENT_INFO) == null)
        {
            forward = mapping.findForward(Constants.SESSION_TIMEOUT);
        }
        else
        {
            try
            {
                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);

                if (method == null || "".equals(method))
                {
                    forward = mapping.findForward(Constants.EPP_REPORTS_VIEW);
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

        return forward;
    }

    public final ActionForward searchEppReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        try
        {
            HttpSession session = request.getSession();
          //  String userLevel = (String) session.getAttribute(Constants.USER_LEVEL);
            if (session.getAttribute(Constants.IS_CUSTOMER)==null  )
            {
           
            String status = request.getParameter("report_type_rdo").toString();
            String output_type = request.getParameter("output_type_rdo").toString();
            String month = request.getParameter("report_month_select").toString();
            String year = request.getParameter("report_year_select").toString();
            int convertMonth = Integer.parseInt(month);
            if (convertMonth < 10)
            {
                month = "0" + month;
            }

            List<EppReport> searchEppReport = null;
            ReportServices reportSearvices = new ReportServices();
            searchEppReport = reportSearvices.searchEppReports(status, month, year);
            request.setAttribute(Constants.SEARCH_RESULT_BY_REPORT_EPP, searchEppReport);
            request.setAttribute("output_type", output_type);
            request.setAttribute("status", status);
            forward = Constants.SEARCH_RESULT_BY_REPORT_EPP;
            }

        }
        catch (Exception e)
        {
            // TODO: handle exception
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }
}
