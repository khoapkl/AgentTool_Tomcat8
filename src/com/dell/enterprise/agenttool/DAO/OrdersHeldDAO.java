/*
 * FILENAME
 *     OrderHeldDAO.java
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

import com.dell.enterprise.agenttool.model.OrdersHeld;
import com.dell.enterprise.agenttool.util.DAOUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrdersHeldDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.OrderHeldDAO");

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public List<OrdersHeld> getOrdersHeld()
    {
        List<OrdersHeld> listOrdersHeld = new ArrayList<OrdersHeld>();
        try
        {
            LOGGER.info("Execute DAO - Function getOrdersHeld");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("order.hold.get");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                OrdersHeld ordersHeld = new OrdersHeld();
                ordersHeld.setShopper_id(rs.getString("shopper_id"));
                ordersHeld.setHeld_order(rs.getString("held_order"));
                ordersHeld.setExp_date(rs.getTimestamp("exp_date"));
                ordersHeld.setExp_days(rs.getInt("exp_days"));
                if(rs.getString("userHold") == null || rs.getString("userHold").isEmpty())
                {
                    ordersHeld.setByAgent(false);
                }else
                {
                    ordersHeld.setByAgent(true);
                }
                listOrdersHeld.add(ordersHeld);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute OrderHeldDAO - Function getOrdersHeld");
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
            catch (SQLException sqlE)
            {
                sqlE.getStackTrace();
            }
        }
        return listOrdersHeld;
    }

    public Boolean deleteOrderHeld(OrdersHeld ordersHeld)
    {
        Boolean Flag = false;

        try
        {
            LOGGER.info("Execute DAO - Function deleteOrderHeld");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            //update status for estore_inventory
            String sql = daoUtil.getString("order.hold.inventory.update.status");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ordersHeld.getHeld_order());
            int a = pstmt.executeUpdate();
            pstmt = null;

            String sql1 = daoUtil.getString("order.hold.estore.basket.delete");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, ordersHeld.getHeld_order());
            int b = pstmt.executeUpdate();
            pstmt = null;

            String sql2 = daoUtil.getString("order.hold.estore.basket.item.delete");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, ordersHeld.getHeld_order());
            int c = pstmt.executeUpdate();
            pstmt = null;

            String sql3 = daoUtil.getString("order.hold.delete");
            pstmt = conn.prepareStatement(sql3);
            pstmt.setString(1, ordersHeld.getHeld_order());
            int d = pstmt.executeUpdate();
            pstmt = null;

            if (a > 0 && b > 0 && c > 0 && d > 0)
            {
                Flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute OrderHeldDAO - Function deleteOrderHeld");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (Flag)
                {
                    conn.commit();
                }
                else
                {
                    conn.rollback();
                }
                conn.setAutoCommit(true);

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
                sqlE.getStackTrace();
            }
        }

        return Flag;
    }
}
