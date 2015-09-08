/*
 * FILENAME
 *     BasketDAO.java
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
import java.sql.SQLException; //import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.EppPromotionRow;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * TODO Add one-sentence summarising the class functionality here; this sentence
 * should only contain one full-stop.
 * </p>
 * <p>
 * TODO Add detailed HTML description of class here, including the following,
 * where relevant:
 * <ul>
 * <li>TODO Description of what the class does and how it is done.</li>
 * <li>TODO Explanatory notes on usage, including code examples.</li>
 * <li>TODO Notes on class dynamic behaviour e.g. is it thread-safe?</li>
 * </ul>
 * </p>
 * <p>
 * <h2>Responsibilities</h2>
 * </p>
 * <p>
 * <ul>
 * <li>TODO Reponsibility #1.</li>
 * <li>TODO Reponsibility #2.</li>
 * <li>TODO Reponsibility #3.</li>
 * <li>TODO Reponsibility #4.</li>
 * </ul>
 * </p>
 * <p>
 * <h2>Protected Interface</h2>
 * </p>
 * <p>
 * TODO Document the protected interface here, including the following:
 * <ul>
 * <li>TODO Which aspects of the class's functionality can be extended.</li>
 * <li>TODO How this extension is done, including usage examples.</li>
 * </ul>
 * </p>
 * 
 * @see TODO Related Information
 * 
 * @author vinhhq
 * 
 * @version $Id$
 **/
public class BasketDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.BasketDAO");
    private Connection conn;
    //    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * getOrderRow
     * 
     * @return OrderRow
     * 
     * @author vinhho
     * 
     */
    public OrderRow getOrderRow(String shopper_id, int agentID)
    {
        OrderRow orderRow = null;

        try
        {
            LOGGER.info("Execute BasketDAO : function getOrderRow");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.order.row");
            if (agentID > 0)
            {
                sql = daoUtil.getString("basket.order.row.agent");
            }
            else
            {
                sql = daoUtil.getString("basket.order.row");
            }
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                orderRow = new OrderRow();

                orderRow.setShopper_id(Constants.convertValueEmpty(rs.getString("shopper_id")));
                orderRow.setOrder_id(Constants.convertValueEmpty(rs.getString("order_id")));
                orderRow.setCreated(rs.getDate("created"));
                orderRow.setModified(rs.getDate("modified"));
                orderRow.setShip_method(Constants.convertValueEmpty(rs.getString("ship_method")));
                orderRow.setShip_terms(Constants.convertValueEmpty(rs.getString("ship_terms")));
                orderRow.setShip_cost(rs.getFloat("ship_cost"));
                orderRow.setCc_name(Constants.convertValueEmpty(rs.getString("cc_name")));
                orderRow.setCc_number(Constants.convertValueEmpty(rs.getString("cc_number")));
                orderRow.setCc_type(rs.getString("cc_type"));
                orderRow.setCc_expmonth(Constants.convertValueEmpty(rs.getString("cc_expmonth")));
                orderRow.setCc_expyear(Constants.convertValueEmpty(rs.getString("cc_expyear")));
                orderRow.setEpp_id(Constants.convertValueEmpty(rs.getString("epp_id")));
                orderRow.setDiscount_type(rs.getInt("discount_type"));
                orderRow.setDiscount_value(rs.getFloat("discount_value"));
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function getOrderRow");
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
                LOGGER.info("Error Execute BasketDAO : function getOrderRow - SQLException");
                sqlE.printStackTrace();
            }
        }

        return orderRow;
    }

    /**
     * addOrderRow
     * 
     * @return Boolean
     * 
     * @author vinhho
     * 
     */
    public Boolean addOrderRow(String shopper_id, int agentID)
    {
        Boolean Flag = false;
        int i = 0;
        String strAgentId = (agentID == 0) ? null : Integer.toString(agentID);

        Timestamp timestampCurrent = Timestamp.valueOf(Constants.dateNow(Constants.DATE_FORMAT_NOW));

        try
        {
            LOGGER.info("Execute OrderDAO : function addOrderRow");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.order.row.add");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            pstmt.setTimestamp(2, timestampCurrent);
            pstmt.setTimestamp(3, timestampCurrent);
            pstmt.setString(4, strAgentId);

            i = pstmt.executeUpdate();
            if (i > 0)
            {
                Flag = true;
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function addOrderRow");
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
                LOGGER.info("Error Execute BasketDAO : function addOrderRow - SQLException");
                sqlE.printStackTrace();
            }
        }
        return Flag;
    }

    public Boolean updateOrderRow(OrderRow orderRow)
    {
        Boolean Flag = false;
        int i = 0;

        Timestamp timestampCurrent = Timestamp.valueOf(Constants.dateNow(Constants.DATE_FORMAT_NOW));

        try
        {
            LOGGER.info("Execute OrderDAO : function addOrderRow");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.update.promotion.basket.item");

            pstmt = conn.prepareStatement(sql);

            pstmt.setTimestamp(2, timestampCurrent);
            pstmt.setTimestamp(3, timestampCurrent);

            pstmt.setString(1, orderRow.getShopper_id());
            pstmt.setString(1, orderRow.getOrder_id());
            i = pstmt.executeUpdate();
            if (i > 0)
            {
                Flag = true;
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function addOrderRow");
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
                LOGGER.info("Error Execute BasketDAO : function addOrderRow - SQLException");
                sqlE.printStackTrace();
            }
        }
        return Flag;
    }

    /**
     * UpdateOrderRowModified
     * 
     * @return Boolean
     * 
     * @author vinhho
     * 
     */
    public Boolean updateOrderRowModified(String shopper_id, int agentID)
    {
        Boolean flag = false;
        int i = 0;

        Timestamp timestampModified = Timestamp.valueOf(Constants.dateNow(Constants.DATE_FORMAT_NOW));

        try
        {
            LOGGER.info("Execute BasketDAO : function UpdateOrderRowModified");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.order.row.update");

            if (agentID > 0)
            {
                sql = sql.replaceAll("BY_AGENT", "AND userHold = '" + Integer.toString(agentID) + "' ");

            }
            else
            {
                sql = sql.replaceAll("BY_AGENT", "AND (userHold IS NULL OR userHold='')");
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, timestampModified);
            pstmt.setString(2, shopper_id);

            i = pstmt.executeUpdate();
            if (i > 0)
            {
                flag = true;
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function UpdateOrderRowModified");
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
                LOGGER.info("Error Execute BasketDAO : function UpdateOrderRowModified - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

    /**
     * getBasketItems
     * 
     * @return List<EstoreBasketItem>
     * 
     * @author vinhho
     * 
     */
    public List<EstoreBasketItem> getBasketItems(String shopper_id, Boolean byAgent)
    {
        List<EstoreBasketItem> listEstoreBasketItem = new ArrayList<EstoreBasketItem>();

        try
        {
            LOGGER.info("Execute BasketDAO : function getBasketItems");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.items");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            pstmt.setBoolean(2, byAgent);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                EstoreBasketItem estoreBasketItem = new EstoreBasketItem();

                estoreBasketItem.setMfg_part_number(rs.getString("mfg_part_number"));
                estoreBasketItem.setItem_sku(rs.getString("item_sku"));
                estoreBasketItem.setQuantity(rs.getInt("quantity"));
                estoreBasketItem.setItem_id(rs.getInt("item_id"));
                estoreBasketItem.setName(rs.getString("name"));
                estoreBasketItem.setShort_description(rs.getString("short_description"));
                estoreBasketItem.setPlaced_price(rs.getFloat("placed_price"));
                estoreBasketItem.setWeight(rs.getInt("weight"));

                listEstoreBasketItem.add(estoreBasketItem);
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function getBasketItems");
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
                LOGGER.info("Error Execute BasketDAO : function getBasketItems - SQLException");
                sqlE.printStackTrace();
            }
        }
        return listEstoreBasketItem;
    }

    public int getListPrice(String item_sku)
    {

        int listPrice = 0;

        try
        {
            LOGGER.info("Execute BasketDAO : function getListPrice");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.item.listPrice");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                if (Integer.valueOf(rs.getInt("modified_price")) > 0)
                {
                    listPrice = rs.getInt("modified_price");
                }
                else
                {
                    listPrice = rs.getInt("list_price");
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function getListPrice");
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
                LOGGER.info("Error Execute BasketDAO : function getListPrice - SQLException");
                sqlE.printStackTrace();
            }
        }
        return listPrice;
    }

    public int countHeldOrder(String shopper_id, Boolean byAgent)
    {
        int countHeldOrder = 0;
        try
        {
            LOGGER.info("Execute BasketDAO : function countHeldOrder");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.count.held.order");

            String strReplace = "";

            if (Constants.isNumber(shopper_id))
            {
                strReplace = " b.userHold = ? ";
            }
            else
            {
                if (byAgent)
                {
                    strReplace = "a.shopper_id = ? AND (userHold IS NOT NULL AND  userHold != '')";
                }
                else
                {
                    strReplace = "a.shopper_id = ? AND (userHold IS NULL OR  userHold = '')";
                }
            }
            sql = sql.replaceAll("WHERE_CONDITION", strReplace);


            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
        
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                countHeldOrder = rs.getInt("heldOrders");
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function countHeldOrder");
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
                LOGGER.info("Error Execute BasketDAO : function countHeldOrder - SQLException");
                sqlE.printStackTrace();
            }
        }
        return countHeldOrder;
    }

    public int updatePromotionCodeBasketItem(String epp_id, String shopper_id)
    {
        int flag = 0;
        Timestamp timestampModified = Timestamp.valueOf(Constants.dateNow(Constants.DATE_FORMAT_NOW));
        try
        {
            LOGGER.info("Execute BasketDAO : function updatePromotionCodeBasketItem");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.update.promotion.basket.item");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, epp_id);
            pstmt.setTimestamp(2, timestampModified);
            pstmt.setString(3, shopper_id);

            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function updatePromotionCodeBasketItem");
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
                LOGGER.info("Error Execute BasketDAO : function updatePromotionCodeBasketItem - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

    public int updateBasketDiscount(int discount_type, Float discount_amt, String shopper_id, String order_number)
    {
        int flag = 0;
        Timestamp timestampModified = Timestamp.valueOf(Constants.dateNow(Constants.DATE_FORMAT_NOW));
        try
        {
            LOGGER.info("Execute BasketDAO : function updatePromotionCodeBasketItem");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.update.discount.basket.item");

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, discount_type);
            pstmt.setTimestamp(2, timestampModified);
            pstmt.setFloat(3, discount_amt);
            pstmt.setString(4, shopper_id);
            pstmt.setString(5, order_number);

            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function updatePromotionCodeBasketItem");
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
                LOGGER.info("Error Execute BasketDAO : function updatePromotionCodeBasketItem - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }
    
    public EppPromotionRow getEppPromotionRow(String epp_id)
    {
        EppPromotionRow eppPromotionRow = new EppPromotionRow();
        try
        {
            LOGGER.info("Execute BasketDAO : function getEppPromotionRow");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("customer.checkout.get.eppPromotion");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, epp_id);

            rs = pstmt.executeQuery();

            if (rs.next())
            {
                eppPromotionRow.setEpp_id(rs.getString("epp_id"));
                eppPromotionRow.setDescription(rs.getString("description"));
                eppPromotionRow.setStart_date(rs.getDate("start_date"));
                eppPromotionRow.setEnd_date(rs.getDate("end_date"));
                eppPromotionRow.setDfs_p_contrib(rs.getInt("dfs_p_contrib"));
                eppPromotionRow.setDfs_d_contrib(rs.getInt("dfs_d_contrib"));
                eppPromotionRow.setCor_p_contrib(rs.getInt("cor_p_contrib"));
                eppPromotionRow.setCor_d_contrib(rs.getInt("cor_d_contrib"));
                eppPromotionRow.setFree_shipping(rs.getInt("free_shipping"));
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function getEppPromotionRow");
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
                LOGGER.info("Error Execute BasketDAO : function getEppPromotionRow - SQLException");
                sqlE.printStackTrace();
            }
        }
        return eppPromotionRow;
    }

    public Boolean cleanBasketItemExp()
    {
        Boolean flag = false;

        try
        {
            LOGGER.info("Execute BasketDAO : function cleanBasketItemExp");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql1 = daoUtil.getString("basket.inventory.exp");
            pstmt = conn.prepareStatement(sql1);
            int y = pstmt.executeUpdate();
            pstmt = null;

            String sql = daoUtil.getString("basket.items.exp");
            pstmt = conn.prepareStatement(sql);
            int x = pstmt.executeUpdate();

            if (x > 0 || y > 0)
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function cleanBasketItemExp");
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
                LOGGER.info("Error Execute BasketDAO : function cleanBasketItemExp - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

    public OrderRow getOrderRow(String shopper_id)
    {
        OrderRow orderRow = null;

        try
        {
            LOGGER.info("Execute BasketDAO : function getOrderRow");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("basket.order.row.held");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                orderRow = new OrderRow();

                orderRow.setShopper_id(Constants.convertValueEmpty(rs.getString("shopper_id")));
                orderRow.setOrder_id(Constants.convertValueEmpty(rs.getString("order_id")));
                orderRow.setCreated(rs.getDate("created"));
                orderRow.setModified(rs.getDate("modified"));
                orderRow.setShip_method(Constants.convertValueEmpty(rs.getString("ship_method")));
                orderRow.setShip_terms(Constants.convertValueEmpty(rs.getString("ship_terms")));
                orderRow.setShip_cost(rs.getFloat("ship_cost"));
                orderRow.setCc_name(Constants.convertValueEmpty(rs.getString("cc_name")));
                orderRow.setCc_number(Constants.convertValueEmpty(rs.getString("cc_number")));
                orderRow.setCc_type(rs.getString("cc_type"));
                orderRow.setCc_expmonth(Constants.convertValueEmpty(rs.getString("cc_expmonth")));
                orderRow.setCc_expyear(Constants.convertValueEmpty(rs.getString("cc_expyear")));
                orderRow.setEpp_id(Constants.convertValueEmpty(rs.getString("epp_id")));
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute BasketDAO : function getOrderRow");
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
                LOGGER.info("Error Execute BasketDAO : function getOrderRow - SQLException");
                sqlE.printStackTrace();
            }
        }

        return orderRow;
    }
}
