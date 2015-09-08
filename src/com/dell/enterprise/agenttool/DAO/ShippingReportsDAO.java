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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import com.dell.enterprise.agenttool.model.Reports;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class ShippingReportsDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.ShippingReportsDAO");

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public List<Reports> searchShippingReports(String dayFrom, String dayTo)
    {
        List<Reports> listShippingReports = new ArrayList<Reports>();
        try
        {
            LOGGER.info("Execute ProductDAO : function searchShippingReports");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.shipping.report.bydate");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            dayFrom = dayFrom.replaceAll("/", "-");
            dayTo = dayTo.replaceAll("/", "-");
            String convertFromDay = "";
            String coverntToday = "";
            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
            dayTo += " 23:59:59";
            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
            java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, fromdateTimestamp);
            pstmt.setTimestamp(2, todateTimestamp);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                Reports report = new Reports();
                report.setItem_SKU(Constants.convertValueEmpty(rs.getString("item_SKU")));
                report.setCategory_id(Constants.convertValueEmpty(rs.getString("category_id")));
                report.setManufacturer_id(Constants.convertValueEmpty(rs.getString("manufacturer_id")));
                report.setMfg_part_number(Constants.convertValueEmpty(rs.getString("mfg_part_number")));
                report.setName(Constants.convertValueEmpty(rs.getString("name")));
                report.setImage_url(Constants.convertValueEmpty(rs.getString("image_url")));
                report.setShort_description(Constants.convertValueEmpty(rs.getString("short_description")));
                report.setLong_description(Constants.convertValueEmpty(rs.getString("long_description")));
                report.setWeight(Constants.convertValueEmpty(rs.getString("weight")));
                report.setDownload_filename(Constants.convertValueEmpty(rs.getString("download_filename")));
                report.setReceived_by(Constants.convertValueEmpty(rs.getString("received_by")));
                report.setReceived_date(Constants.convertValueEmpty(rs.getString("received_date")));
                report.setWarehouse_location(Constants.convertValueEmpty(rs.getString("warehouse_location")));
                report.setStatus(Constants.convertValueEmpty(rs.getString("status")));
                report.setStatus_date(Constants.convertValueEmpty(rs.getString("status_date")));
                report.setList_price(Constants.convertValueEmpty(rs.getString("list_price")));
                report.setModified_price(Constants.convertValueEmpty(rs.getString("modified_price")));
                report.setModified_date(Constants.convertValueEmpty(rs.getString("modified_date")));
                report.setWarranty_date(Constants.convertValueEmpty(rs.getString("warranty_date")));
                report.setFlagType(Constants.convertValueEmpty(rs.getString("flagType")));
                report.setLease_number(Constants.convertValueEmpty(rs.getString("lease_number")));
                report.setContract_number(Constants.convertValueEmpty(rs.getString("contract_number")));
                report.setMfg_SKU(Constants.convertValueEmpty(rs.getString("mfg_SKU")));
                report.setNotes(Constants.convertValueEmpty(rs.getString("notes")));
                report.setShip_via(Constants.convertValueEmpty(rs.getString("ship_via")));
                report.setAttribute01(Constants.convertValueEmpty(rs.getString("attribute01")));
                report.setAttribute02(Constants.convertValueEmpty(rs.getString("attribute02")));
                report.setAttribute03(Constants.convertValueEmpty(rs.getString("attribute03")));
                report.setAttribute04(Constants.convertValueEmpty(rs.getString("attribute04")));
                report.setAttribute05(Constants.convertValueEmpty(rs.getString("attribute05")));
                report.setAttribute06(Constants.convertValueEmpty(rs.getString("attribute06")));
                report.setAttribute07(Constants.convertValueEmpty(rs.getString("attribute07")));
                report.setAttribute08(Constants.convertValueEmpty(rs.getString("attribute08")));
                report.setAttribute09(Constants.convertValueEmpty(rs.getString("attribute09")));
                report.setAttribute10(Constants.convertValueEmpty(rs.getString("attribute10")));
                report.setAttribute11(Constants.convertValueEmpty(rs.getString("attribute11")));
                report.setAttribute12(Constants.convertValueEmpty(rs.getString("attribute12")));
                report.setAttribute13(Constants.convertValueEmpty(rs.getString("attribute13")));
                report.setAttribute14(Constants.convertValueEmpty(rs.getString("attribute14")));
                report.setAttribute15(Constants.convertValueEmpty(rs.getString("attribute15")));
                report.setAttribute16(Constants.convertValueEmpty(rs.getString("attribute16")));
                report.setAttribute17(Constants.convertValueEmpty(rs.getString("attribute17")));
                report.setAttribute18(Constants.convertValueEmpty(rs.getString("attribute18")));
                report.setAttribute19(Constants.convertValueEmpty(rs.getString("attribute19")));
                report.setAttribute20(Constants.convertValueEmpty(rs.getString("attribute20")));
                report.setAttribute21(Constants.convertValueEmpty(rs.getString("attribute21")));
                report.setAttribute22(Constants.convertValueEmpty(rs.getString("attribute22")));
                report.setAttribute23(Constants.convertValueEmpty(rs.getString("attribute23")));
                report.setAttribute24(Constants.convertValueEmpty(rs.getString("attribute24")));
                report.setAttribute25(Constants.convertValueEmpty(rs.getString("attribute25")));
                report.setAttribute26(Constants.convertValueEmpty(rs.getString("attribute26")));
                report.setAttribute27(Constants.convertValueEmpty(rs.getString("attribute27")));
                report.setAttribute28(Constants.convertValueEmpty(rs.getString("attribute28")));
                report.setAttribute29(Constants.convertValueEmpty(rs.getString("attribute29")));
                report.setAttribute30(Constants.convertValueEmpty(rs.getString("attribute30")));
                report.setAttribute31(Constants.convertValueEmpty(rs.getString("attribute31")));
                report.setAttribute32(Constants.convertValueEmpty(rs.getString("attribute32")));
                report.setAttribute33(Constants.convertValueEmpty(rs.getString("attribute33")));
                report.setAttribute34(Constants.convertValueEmpty(rs.getString("attribute34")));
                report.setAttribute35(Constants.convertValueEmpty(rs.getString("attribute35")));
                report.setAttribute36(Constants.convertValueEmpty(rs.getString("attribute36")));
                report.setAttribute37(Constants.convertValueEmpty(rs.getString("attribute37")));
                report.setAttribute38(Constants.convertValueEmpty(rs.getString("attribute38")));
                report.setAttribute39(Constants.convertValueEmpty(rs.getString("attribute39")));
                report.setAttribute40(Constants.convertValueEmpty(rs.getString("attribute40")));
                report.setOrder_number(Constants.convertValueEmpty(rs.getString("order_number")));
                report.setTracking_number(Constants.convertValueEmpty(rs.getString("tracking_number")));
                report.setShip_date(Constants.convertValueEmpty(rs.getString("ship_date")));
                report.setOrigin(Constants.convertValueEmpty(rs.getString("origin")));
                listShippingReports.add(report);
                LOGGER.info("Item Sku : " + report.getItem_SKU());
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
        return listShippingReports;
    }

}
