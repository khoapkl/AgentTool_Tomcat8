/*
 * FILENAME
 *     ShopperListDAO.java
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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

/**
 * @author hungnguyen
 **/
public class ShopperListDAO
{
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private CallableStatement cstmt;
    private ResultSet rs;
    private static int totalRowCount = 0;

    /**
     * @param pageNumber
     *            pageNumber
     * @param searchCriteria
     *            searchCriteria
     * @param searchCriteria
     * @return list of shoppers
     **/
    public List<Shopper> getShoppers(final int pageNumber, final Shopper searchCriteria)
    {
        List<Shopper> shopperList = new ArrayList<Shopper>();
        Shopper shopper;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            cstmt = conn.prepareCall(daoUtils.getString("search.shopper.paging.sp"));
            cstmt.setInt(1, pageNumber);
            cstmt.setInt(2, Constants.SHOPPER_LIST_RECORDS_PER_PAGE);
            cstmt.setString(3, createFilter(searchCriteria));
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

            rs = cstmt.executeQuery();

            while (rs.next())
            {
                shopper = new Shopper();
                shopper.setShopperId(rs.getString(Constants.DB_FIELD_SHOPPER_ID));
                shopper.setShopperNumber(rs.getInt(Constants.DB_FIELD_SHOPPER_NUMBER));
                shopper.setLinkNumber(rs.getString(Constants.DB_FIELD_LINK_NUMBER));
                shopper.setShipToFName(rs.getString(Constants.DB_FIELD_SHIP_FNAME));
                shopper.setShipToLName(rs.getString(Constants.DB_FIELD_SHIP_LNAME));
                shopper.setShipToCompany(rs.getString(Constants.DB_FIELD_SHIP_COMPANY));
                shopper.setShipToPhone(rs.getString(Constants.DB_FIELD_SHIP_PHONE));
                shopper.setBillToFName(rs.getString(Constants.DB_FIELD_BILL_FNAME));
                shopper.setBillToLName(rs.getString(Constants.DB_FIELD_BILL_LNAME));
                shopper.setBillToCompany(rs.getString(Constants.DB_FIELD_BILL_COMPANY));
                shopper.setBillToPhone(rs.getString(Constants.DB_FIELD_BILL_PHONE));

                shopperList.add(shopper);
            }

            totalRowCount = cstmt.getInt(4);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return shopperList;
    }

    /**
     * @param searchCriteria
     *            searchCriteria
     * @return String
     **/
    public String createFilter(final Shopper searchCriteria)
    {
        String sql = "shopper_id IS NOT NULL";

        String linkNumber = searchCriteria.getLinkNumber();
        String shipToFName = searchCriteria.getShipToFName();
        String shipToLName = searchCriteria.getShipToLName();
        String shipToCompany = searchCriteria.getShipToCompany();
        String shipToPhone = searchCriteria.getShipToPhone();
        String shipToEmail = searchCriteria.getShipToEmail();
        String billToFName = searchCriteria.getBillToFName();
        String billToLName = searchCriteria.getBillToLName();
        String billToCompany = searchCriteria.getBillToCompany();
        String billToPhone = searchCriteria.getBillToPhone();

        if (linkNumber != null && !"".equals(linkNumber))
        {
            sql += " AND " + Constants.DB_FIELD_SHOPPER_NUMBER + " LIKE '%" + linkNumber + "%' escape '\\'";
        }

        if (shipToFName != null && !"".equals(shipToFName))
        {
            sql += " AND " + Constants.DB_FIELD_SHIP_FNAME + " LIKE '%" + shipToFName + "%' escape '\\'";
        }

        if (shipToLName != null && !"".equals(shipToLName))
        {
            sql += " AND " + Constants.DB_FIELD_SHIP_LNAME + " LIKE '%" + shipToLName + "%' escape '\\'";
        }

        if (shipToCompany != null && !"".equals(shipToCompany))
        {
            sql += " AND " + Constants.DB_FIELD_SHIP_COMPANY + " LIKE '%" + shipToCompany + "%' escape '\\'";
        }

        if (shipToPhone != null && !"".equals(shipToPhone))
        {
            sql += " AND " + Constants.DB_FIELD_SHIP_PHONE + " LIKE '%" + shipToPhone + "%' escape '\\'";
        }

        if (shipToEmail != null && !"".equals(shipToEmail))
        {
            sql += " AND " + Constants.DB_FIELD_SHIP_EMAIL + " LIKE '%" + shipToEmail + "%' escape '\\'";
        }

        if (billToFName != null && !"".equals(billToFName))
        {
            sql += " AND " + Constants.DB_FIELD_BILL_FNAME + " LIKE '%" + billToFName + "%' escape '\\'";
        }

        if (billToLName != null && !"".equals(billToLName))
        {
            sql += " AND " + Constants.DB_FIELD_BILL_LNAME + " LIKE '%" + billToLName + "%' escape '\\'";
        }

        if (billToCompany != null && !"".equals(billToCompany))
        {
            sql += " AND " + Constants.DB_FIELD_BILL_COMPANY + " LIKE '%" + billToCompany + "%' escape '\\'";
        }

        if (billToPhone != null && !"".equals(billToPhone))
        {
            sql += " AND " + Constants.DB_FIELD_BILL_PHONE + " LIKE '%" + billToPhone + "%' escape '\\'";
        }

        return sql;
    }

    /**
     * @return int
     **/
    public int getTotalRowCount()
    {
        return totalRowCount;
    }
}
