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

import com.dell.enterprise.agenttool.model.EmailShipNotify;
import com.dell.enterprise.agenttool.model.ShippedItem;
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

public class EmailShipDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.OrderHeldDAO");

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public List<EmailShipNotify> getShipEmailQueue()
    {
        List<EmailShipNotify> listOrdersEmailNotify = new ArrayList<EmailShipNotify>();
        try
        {
            LOGGER.info("Execute DAO - Function getOrdersHeld");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("email.ship.notification");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
            	EmailShipNotify shipEmail = new EmailShipNotify();
            	shipEmail.setOrderNumber(rs.getString("ordernumber"));
            	shipEmail.setShippedDate(rs.getTimestamp("datecompleted"));
            	shipEmail.setShipToCompany(rs.getString("ship_to_company"));
            	shipEmail.setShipToAddress1(rs.getString("ship_to_address1"));
            	shipEmail.setShipToAddress2(rs.getString("ship_to_address2"));
            	shipEmail.setShipToCity(rs.getString("ship_to_city"));
            	shipEmail.setShipToState(rs.getString("ship_to_state"));
            	shipEmail.setShipToPostal(rs.getString("ship_to_postal"));
            	shipEmail.setShipToPhone(rs.getString("ship_to_phone"));
            	shipEmail.setShipToEmail(rs.getString("ship_to_email"));
            	shipEmail.setShipToName(rs.getString("ship_to_name"));
            	shipEmail.setAgentName(rs.getString("fullname"));
            	shipEmail.setAgentEmail(rs.getString("email"));
                
            	listOrdersEmailNotify.add(shipEmail);
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
        return listOrdersEmailNotify;
    }

    public Boolean UpdateEmailFlag(String orderNumber)
    {
        Boolean Flag = false;

        try
        {
            LOGGER.info("Execute DAO - Function Update Shipping Email Flag");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            //update status for estore_inventory
            String sql = "UPDATE orderheader set email = 1 WHERE ordernumber = '" + orderNumber + "'";
            LOGGER.warning(sql);
            pstmt = conn.prepareStatement(sql);
            int a = pstmt.executeUpdate();
            pstmt = null;
            
            if (a > 0) Flag = true;

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute OrderHeldDAO - Function Update Shipping Email Flag");
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
    
    public List<ShippedItem> getShippedItems(String order_number)
    {
        List<ShippedItem> listShippedItems = new ArrayList<ShippedItem>();
        try
        {
            LOGGER.info("Execute ShippedDAO : function getShippedItems");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("email.ship.notificgtion.items");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, order_number);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
            	ShippedItem shippedItems = new ShippedItem();

                
            	shippedItems.setItemSku(rs.getString("item_sku"));
            	shippedItems.setItemName(rs.getString("description"));
            	shippedItems.setShipmentID(order_number);
            	//shippedItems.setShipmentID(rs.getString("shipment_id"));
            	if (rs.getString("tracking_number") == null)
            	{
            		shippedItems.setTrackingNumber("N/A");
            	}
            	else {
            		shippedItems.setTrackingNumber(rs.getString("tracking_number"));
            	}
                
           //System.out.println("data  data :"+estoreBasketItem.getAttribute12());
            	listShippedItems.add(shippedItems);
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getItemCheck");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                {
                    rs.close();
                }
                if (pstmt != null)
                {
                    pstmt.close();
                }
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("Error Execute CheckoutDAO : function getItemCheck - SQLException");
                sqlE.printStackTrace();
            }
        }

        return listShippedItems;
    }
}
