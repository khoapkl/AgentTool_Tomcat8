/*
 * FILENAME
 *     ProductDAO.java
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

package com.dell.enterprise.agenttool.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.dell.enterprise.agenttool.model.EppReport;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class EppReportDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.EppReportDAO");

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public List<EppReport> searchEppReports(String status, String month, String year)
    {
        List<EppReport> listEppReports = new ArrayList<EppReport>();
        try
        {
            LOGGER.info("Execute ProductDAO : function EppReportDAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            if (status.equals("promotion"))
            {
                String sql = daoUtil.getString("search.epp.report.bymonth.byyear.promotion");
                LOGGER.info("SQL : " + sql);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, month);
                pstmt.setString(2, year);
                rs = pstmt.executeQuery();

                while (rs.next())
                {

                    EppReport report = new EppReport();
                    report.setEpp_id(Constants.convertValueEmpty(rs.getString(1)));
                    report.setDescription(Constants.convertValueEmpty(rs.getString(2)));
                    report.setShopper_id(Constants.convertValueEmpty(rs.getString(3)));
                    report.setCreatedDate(Constants.convertValueEmpty(rs.getString(4)));
                    report.setOrderNumber(Constants.convertValueEmpty(rs.getString(5)));
                    report.setEst_subtotal(Constants.convertValueEmpty(rs.getString(6)));
                    report.setVolume_discount(Constants.convertValueEmpty(rs.getString(7)));
                    report.setDfs_discount(Constants.convertValueEmpty(rs.getString(8)));
                    report.setCor_discount(Constants.convertValueEmpty(rs.getString(9)));
                    report.setShipping_total(Constants.convertValueEmpty(rs.getString(10)));
                    report.setTotal_total(Constants.convertValueEmpty(rs.getString(11)));
                    listEppReports.add(report);
                    LOGGER.info("Item getEpp_id : " + report.getEpp_id());
                }
            }
            else
            {

                String sql = daoUtil.getString("search.epp.report.bymonth.byyear.asset");
                LOGGER.info("SQL : " + sql);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, month);
                pstmt.setString(2, year);
                pstmt.setString(3, month);
                pstmt.setString(4, year);
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    EppReport report = new EppReport();
                    report.setOrderNumber(Constants.convertValueEmpty(rs.getString(1)));
                    report.setItem_sku(Constants.convertValueEmpty(rs.getString(2)));
                    report.setName(Constants.convertValueEmpty(rs.getString(3)));
                    report.setProduct_list_price(Constants.convertValueEmpty(rs.getString(4)));

                    listEppReports.add(report);
                    LOGGER.info("Item getEpp_id : " + report.getEpp_id());
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                sqlE.printStackTrace();
            }
        }
        return listEppReports;
    }

}
