/*
 * FILENAME
 *     ProductServices.java
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

package com.dell.enterprise.agenttool.services;

import java.util.List;
import com.dell.enterprise.agenttool.DAO.EppReportDAO;
import com.dell.enterprise.agenttool.DAO.ShippingReportsDAO;
import com.dell.enterprise.agenttool.model.EppReport;
import com.dell.enterprise.agenttool.model.Reports;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class ReportServices
{

    public List<Reports> searchShippingReports(String fromDay, String toDay)
    {
        ShippingReportsDAO dao = new ShippingReportsDAO();
        List<Reports> listProduct = dao.searchShippingReports(fromDay, toDay);
        return listProduct;
    }

    public List<EppReport> searchEppReports(String status, String month, String year)
    {

        EppReportDAO dao = new EppReportDAO();
        List<EppReport> listProduct = dao.searchEppReports(status, month, year);
        return listProduct;

    }

}
