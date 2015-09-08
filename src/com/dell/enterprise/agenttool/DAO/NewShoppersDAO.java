/*
 * FILENAME
 *     NewShoppersByYearDAO.java
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

/**
 * @author hungnguyen
 **/
public class NewShoppersDAO
{

    private Connection conn;
    private PreparedStatement pstmt;
    private CallableStatement cstmt;
    private ResultSet rs;
    private static int newShoppersByDayCount = 0;

    /**
     * @param year
     *            year
     * @return Map<String, String>
     **/
    public Map<Integer, String> getNewShoppersByYear(final int year)
    {
        Map<Integer, String> newShoppersByYear = new HashMap<Integer, String>();

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            pstmt = conn.prepareStatement(daoUtils.getString("search.shopper.year"));
            pstmt.setInt(1, year);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                newShoppersByYear.put(rs.getInt(1), rs.getString(2));
            }
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
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return newShoppersByYear;
    }

    /**
     * @param year
     *            year
     * @param month
     *            month
     * @return Map<Integer, String>
     **/
    public Map<Integer, Integer> getNewShoppersByMonth(final int year, final int month)
    {
        Map<Integer, Integer> newShoppersByMonth = new HashMap<Integer, Integer>();

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            pstmt = conn.prepareStatement(daoUtils.getString("search.shopper.month"));
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                newShoppersByMonth.put(rs.getInt(1), rs.getInt(2));
            }
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
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return newShoppersByMonth;
    }

    /**
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @param page
     *            page
     * @return List<Shopper>
     **/
    public List<Shopper> getNewShoppersByDay(final int year, final int month, final int day, final int page)
    {
        List<Shopper> newShoppersByDay = new ArrayList<Shopper>();
        Shopper shopper;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            cstmt = conn.prepareCall(daoUtils.getString("search.shopper.day.paging.sp"));
            cstmt.setInt(1, page);
            cstmt.setInt(2, Constants.SHOPPER_LIST_RECORDS_PER_PAGE);
            cstmt.setInt(3, year);
            cstmt.setInt(4, month);
            cstmt.setInt(5, day);
            cstmt.registerOutParameter(6, java.sql.Types.INTEGER);

            rs = cstmt.executeQuery();

            while (rs.next())
            {
                shopper = new Shopper();
                shopper.setShipToFName(rs.getString(Constants.DB_FIELD_SHIP_FNAME));
                shopper.setShipToLName(rs.getString(Constants.DB_FIELD_SHIP_LNAME));
                shopper.setShipToCompany(rs.getString(Constants.DB_FIELD_SHIP_COMPANY));
                shopper.setShopperId(rs.getString(Constants.DB_FIELD_SHOPPER_ID));
                shopper.setCreatedDate(rs.getTimestamp(Constants.DB_FIELD_CREATED_DATE));

                newShoppersByDay.add(shopper);
            }

            newShoppersByDayCount = cstmt.getInt(6);
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
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return newShoppersByDay;
    }

    /**
     * @return int
     **/
    public int getNewShoppersByDayCount()
    {
        return newShoppersByDayCount;
    }

    /**
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @return List<Shopper>
     **/
    public int countNewShoppersByDay(final int year, final int month, final int day)
    {
        int total = 0;
        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            pstmt = conn.prepareStatement(daoUtils.getString("search.count.shopper.day"));
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            pstmt.setInt(3, day);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                total = rs.getInt(1);
            }
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
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return total;
    }
}
