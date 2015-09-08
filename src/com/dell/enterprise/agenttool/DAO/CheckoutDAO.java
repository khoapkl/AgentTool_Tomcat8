/*
 * FILENAME
 *     CheckoutDAO.java
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

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.Avg_mhz;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.WarrantyPartList;
import com.dell.enterprise.agenttool.model.OrderHeader;
import com.dell.enterprise.agenttool.model.PayMethods;
import com.dell.enterprise.agenttool.model.TaxTables;
import com.dell.enterprise.agenttool.model.OrderLine;
import com.dell.enterprise.agenttool.util.Converter;
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
public class CheckoutDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.CheckoutDAO");
    private Connection conn;
    //private Statement stmt;
    private PreparedStatement pstmt;
    private PreparedStatement pstmt1;
    private ResultSet rs;

    public List<EstoreBasketItem> getItemCheck(String shopper_id, Boolean byAgent)
    {
        List<EstoreBasketItem> listEstoreBasketItem = new ArrayList<EstoreBasketItem>();
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getItemCheck");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.get.item.check");

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
                estoreBasketItem.setList_price(rs.getFloat("list_price") * 100);
                estoreBasketItem.setPlaced_price(rs.getFloat("placed_price"));
                estoreBasketItem.setWeight(rs.getInt("weight"));
                estoreBasketItem.setAttribute05(rs.getString("attribute05"));
                estoreBasketItem.setAttribute06(rs.getString("attribute06"));
                estoreBasketItem.setCategory_id(rs.getInt("category_id"));
                estoreBasketItem.setAttribute12(rs.getString("attribute12"));
                
                estoreBasketItem.setAttribute09(rs.getString("attribute09"));
                estoreBasketItem.setAttribute10(rs.getString("attribute10"));
                estoreBasketItem.setAttribute13(rs.getString("attribute13"));
                estoreBasketItem.setImage_url(rs.getString("image_url"));
                
                //estoreBasketItem.setAttribute12("4");
                //attribute03
                if (estoreBasketItem.getCategory_id() == 11946 || estoreBasketItem.getCategory_id() == 11947 || estoreBasketItem.getCategory_id() == 11949)
                {
                    estoreBasketItem.setSpeed(Float.parseFloat(estoreBasketItem.getAttribute13()));
                }
                /*else if (estoreBasketItem.getCategory_id() == 11949)
                {
                    estoreBasketItem.setSpeed(Float.parseFloat(estoreBasketItem.getAttribute06()));
                }*/
                else
                {
                    estoreBasketItem.setSpeed(new Float(0));
                }
                
                if(estoreBasketItem.getCategory_id() == 11946)
                {
                	estoreBasketItem.setScreenSize(Float.parseFloat(estoreBasketItem.getAttribute06()));
                }
                else if(estoreBasketItem.getCategory_id() == 11955)
                {
                	estoreBasketItem.setScreenSize(Float.parseFloat(rs.getString("attribute03")));
                }
                else if(estoreBasketItem.getCategory_id() == 11950)
                {
                	estoreBasketItem.setSpeed(Float.parseFloat(rs.getString("attribute09")));
                }

                
                //System.out.println("data  data :"+estoreBasketItem.getAttribute12());
                listEstoreBasketItem.add(estoreBasketItem);
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

        return listEstoreBasketItem;
    }

    
    public List<WarrantyPartList> getWarrantyList(int warrantyCat1, int warrantyCat2)
    {
        List<WarrantyPartList> listWarrantyPart = new ArrayList<WarrantyPartList>();
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getItemCheck");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.get.warranty.list");

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, warrantyCat1);
            pstmt.setInt(2, warrantyCat2);
            
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                WarrantyPartList warrantyParts = new WarrantyPartList();

                warrantyParts.setItem_sku(rs.getString("partnumber"));                
                warrantyParts.setName(rs.getString("description"));
                warrantyParts.setMfg_sku(rs.getString("mfg_sku"));
                warrantyParts.setList_price(rs.getFloat("price") * 100);
                warrantyParts.setCategory_id(rs.getInt("category_id"));
                
                listWarrantyPart.add(warrantyParts);
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
                LOGGER.info("Error Execute CheckoutDAO : function listWarranty - SQLException");
                sqlE.printStackTrace();
            }
        }

        return listWarrantyPart;
    }

    
    
    public String getPromoByCat(int category_id)
    {
        String nameFamily = "";

        String rsName = "Family";
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getPromoByCat");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.get.promo.by.cat");

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category_id);

            rs = pstmt.executeQuery();

            if (category_id == 11955 || category_id == 11950 || category_id == 11947)
            {
                rsName = "name";
            }

            if (rs.next())
            {
                nameFamily = rs.getString(rsName).substring(0, 3).toUpperCase();
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getPromoByCat");
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
                LOGGER.info("Error Execute CheckoutDAO : function getPromoByCat - SQLException");
                sqlE.printStackTrace();
            }
        }

        return nameFamily;
    }

    public String getCatIDFamily(String item_sku)
    {
        String nameFamily = "";
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getPromoByCat");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.get.CatIDFamily");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                nameFamily = rs.getString("attribute02").substring(0, 3).toUpperCase();
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getPromoByCat");
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
                LOGGER.info("Error Execute CheckoutDAO : function getPromoByCat - SQLException");
                sqlE.printStackTrace();
            }
        }
        return nameFamily;
    }

    public int getMaxDiscount()
    {
        int maxDiscount = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getMaxDiscount");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.get.max.discount");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                maxDiscount = rs.getInt("percdiscount");
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getMaxDiscount");
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
                LOGGER.info("Error Execute CheckoutDAO : function getMaxDiscount - SQLException");
                sqlE.printStackTrace();
            }
        }
        return maxDiscount;
    }

    public int getPromotionContribByCode(String promotionCode)
    {
        int contrib = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getMaxDiscount");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.contrib.promotion.code");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, promotionCode);

            rs = pstmt.executeQuery();

            if (rs.next())
            {
                contrib = rs.getInt("dfs_p_contrib");
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getMaxDiscount");
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
                LOGGER.info("Error Execute CheckoutDAO : function getMaxDiscount - SQLException");
                sqlE.printStackTrace();
            }
        }
        return contrib;
    }

    public void setDiscountItem(int contrib, String order_id)
    {
        try
        {
            LOGGER.info("Execute CheckoutDAO : function setDiscountItem");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.discount.item");

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, contrib);
            pstmt.setString(2, order_id);

            pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function setDiscountItem");
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
                LOGGER.info("Error Execute CheckoutDAO : function setDiscountItem - SQLException");
                sqlE.printStackTrace();
            }
        }
    }

    public int setOrderLineItemDiscount(Float newPrice, String shopper_id, String item_sku)
    {
        int flag = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function setOrderLineItemDiscount");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.order.line.item.discount");

            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, newPrice);
            pstmt.setString(2, shopper_id);
            pstmt.setString(3, item_sku);

            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function setOrderLineItemDiscount");
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
                LOGGER.info("Error Execute CheckoutDAO : function setOrderLineItemDiscount - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;

    }
    
    public int setOrderLineAddWarranty(Float newPrice, String shopper_id, String item_sku, int qty, int category_id, Float orignalprice, Boolean byAgent)
    {
        int flag = 0;
        String partDescription = "";
        try
        {
            LOGGER.info("Execute CheckoutDAO : function setOrderLineAddWarranty");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            
            //Get warranty info
            String sql1 = daoUtil.getString("product.basket.warranty.query");
            pstmt1 = conn.prepareStatement(sql1);
            pstmt1.setString(1, item_sku);
            rs = pstmt1.executeQuery();
            if (rs.next())
            {
            	String sql = daoUtil.getString("product.basket.item.add");
                partDescription = rs.getString("description");
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, shopper_id);
                pstmt.setString(2, partDescription);
                pstmt.setString(3, item_sku);
                pstmt.setInt(4, qty);
                pstmt.setFloat(5, orignalprice);
                pstmt.setString(6, partDescription);
                pstmt.setString(7, partDescription);
                pstmt.setInt(8, 0);
                pstmt.setBoolean(9, byAgent);
                pstmt.setInt(10, rs.getInt("category_id"));

                flag = pstmt.executeUpdate();
            }
            pstmt1.close();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function setOrderLineAddWarranty");
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
                LOGGER.info("Error Execute CheckoutDAO : function setOrderLineAddWarranty - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;

    }
    
    public int setOrderLineUpdateWarranty(Float newPrice, String shopper_id, String item_sku, int qty, Boolean isAgent)
    {
        int flag = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function setOrderLineUpdateWarranty");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.order.line.item.update.warranty");

            pstmt = conn.prepareStatement(sql);
            pstmt.setFloat(1, newPrice);
            pstmt.setInt(2, qty);
            pstmt.setString(3, shopper_id);
            pstmt.setString(4, item_sku);
            pstmt.setBoolean(5, isAgent);
            
            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function setOrderLineUpdateWarranty");
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
                LOGGER.info("Error Execute CheckoutDAO : function setOrderLineAddWarranty - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;

    }
    
    public int deleteWarrantyItem(String shopper_id, String item_sku, Boolean isAgent)
    {
        int flag = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function setOrderLineItemDiscount");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.order.line.item.del_warranty");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            pstmt.setString(2, item_sku);
            pstmt.setBoolean(3, isAgent);

            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function deleteWarrantyItem");
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
                LOGGER.info("Error Execute CheckoutDAO : function deleteWarrantyItem - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;

    }

    public Object[][] getShippingPrice(boolean usingManager, List<EstoreBasketItem> listEstoreBasketItem, String shipPostal)
    {
        Object[][] arrayShipping = new Object[10][4];

        for (int count = 0; count < 10; count++)
        {
            arrayShipping[count][0] = "";
            arrayShipping[count][1] = 0;
            arrayShipping[count][2] = "";
            arrayShipping[count][3] = 0;
        }

        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            for (int i = 0; i < listEstoreBasketItem.size(); i++)
            {
                EstoreBasketItem estoreBasketItem = (EstoreBasketItem) listEstoreBasketItem.get(i);

                String sqlWeight;
                LOGGER.info("Execute CheckoutDAO - Function getShippingPrice ");

                try
                {
                    ///
                    /// Temporary add for by passing table ShipCostFree
                    ///
                    sqlWeight = daoUtil.getString("customer.checkout2.ShippingWeight.Admin");

                    ///
                    ///
                    ///

                    pstmt = conn.prepareStatement(sqlWeight);
                    pstmt.setString(1, shipPostal);
                    pstmt.setInt(2, estoreBasketItem.getWeight());
                    
                    //	             pstmt = null;
                    rs = pstmt.executeQuery();
                    int tmp = 1;
                    while (rs.next())
                    {
                        int shipOption = rs.getInt("shipid");
                        arrayShipping[shipOption][0] = rs.getString("carrier") + " " + rs.getString("service");
                        if (!usingManager)
                        {
                        	arrayShipping[shipOption][1] = rs.getInt("freeCtrl") * (Double.valueOf(arrayShipping[shipOption][1].toString()) + rs.getDouble("Price"));
                        }
                        else
                        {
                        	arrayShipping[shipOption][1] = Double.valueOf(arrayShipping[shipOption][1].toString()) + rs.getDouble("Price");
                        }
                        arrayShipping[shipOption][2] = rs.getString("carrier") + " " + rs.getString("service");
                        arrayShipping[shipOption][3] = tmp;
                        tmp++;
                    }
                }
                catch (Exception e)
                {
                    LOGGER.warning("ERROR Execute DAO");
                    e.printStackTrace();
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
        return arrayShipping;
    }

    public int estoreBasketUpdate(String shopper_id, String ship_method, String ship_terms, Float ship_cost, int agentId)
    {
        int flag = 0;
        ship_terms = ship_terms.isEmpty() ? null : ship_terms;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function estoreBasketUpdate");

            LOGGER.info("Execute CheckoutDAO : function estoreBasketUpdate - SHIP COST : " + ship_cost);

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("estore_basket.update");
            if (agentId > 0)
                sql = daoUtil.getString("estore_basket.update.agent");

            pstmt = conn.prepareStatement(sql);
            if (Integer.parseInt(ship_method) == 6)
            {
                pstmt.setString(1, ship_method);
                pstmt.setString(2, null);
                pstmt.setFloat(3, 0);
                pstmt.setString(4, shopper_id);
            }
            else
            {
                pstmt.setString(1, ship_method);
                pstmt.setString(2, ship_terms);
                pstmt.setFloat(3, ship_cost);
                pstmt.setString(4, shopper_id);
            }

            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function estoreBasketUpdate");
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
                LOGGER.info("Error Execute CheckoutDAO : function estoreBasketUpdate - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

    public int contactInfoUpdate(String shopper_id, String ship_method)
    {
        int flag = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function contactInfoUpdate");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("contactInfo.update");

            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, ship_method);
            pstmt.setString(2, shopper_id);

            flag = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function contactInfoUpdate");
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
                LOGGER.info("Error Execute CheckoutDAO : function contactInfoUpdate - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

    public String shortShipping(String ship_method)
    {
        String description = "";

        try
        {
            LOGGER.info("Execute CheckoutDAO : function shortShipping");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("shortShipping.get");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ship_method);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                description = rs.getString("description");
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function shortShipping");
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
                LOGGER.info("Error Execute CheckoutDAO : function shortShipping - SQLException");
                sqlE.printStackTrace();
            }
        }
        return description;
    }

    /*
     * Author Vinhhq
     */
    public List<PayMethods> getPayMethods()
    {
        List<PayMethods> listPayMethods = new ArrayList<PayMethods>();

        try
        {
            LOGGER.info("Execute CheckoutDAO : function getPayMethods");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout3.payMethods");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                PayMethods payMethods = new PayMethods();

                payMethods.setCode(rs.getInt("code"));
                payMethods.setName(rs.getString("name"));
                payMethods.setIsCreditCard(rs.getInt("isCreditCard"));

                listPayMethods.add(payMethods);

            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getPayMethods");
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
                LOGGER.info("Error Execute CheckoutDAO : function getPayMethods - SQLException");
                sqlE.printStackTrace();
            }
        }

        return listPayMethods;
    }

    /*
     * 
     * */
    public OrderHeader getOrderReceipt(String shopper_id, String order_id)
    {
        OrderHeader orderHeader = null;

        try
        {
            LOGGER.info("Execute CheckoutDAO : function getOrderReceipt");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout3.getOrderReceipt");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            pstmt.setString(2, order_id);

            rs = pstmt.executeQuery();

            if (rs.next())
            {
                orderHeader = new OrderHeader();

                orderHeader.setOrderNumber(rs.getString("OrderNumber"));
                orderHeader.setCreatedDate(rs.getTimestamp("createdDate"));
                orderHeader.setUpdatedDate(rs.getTimestamp("updatedDate"));
                orderHeader.setOrderStatus(rs.getString("orderStatus"));

                orderHeader.setShip_to_name(rs.getString("ship_to_name"));
                orderHeader.setShip_to_company(rs.getString("ship_to_company"));
                orderHeader.setShip_to_address1(rs.getString("ship_to_address1"));
                orderHeader.setShip_to_address2(rs.getString("ship_to_address2"));
                orderHeader.setShip_to_city(rs.getString("ship_to_city"));
                orderHeader.setShip_to_state(rs.getString("ship_to_state"));
                orderHeader.setShip_to_postal(rs.getString("ship_to_postal"));
                orderHeader.setShip_to_country(rs.getString("ship_to_country"));
                orderHeader.setShip_to_fax(rs.getString("ship_to_fax"));
                orderHeader.setShip_to_phone(rs.getString("ship_to_phone"));
                orderHeader.setShip_to_email(rs.getString("ship_to_email"));

                orderHeader.setShip_method(rs.getString("ship_method"));
                orderHeader.setShip_terms(rs.getString("ship_terms"));
                orderHeader.setTracking_number(rs.getString("tracking_number"));

                orderHeader.setBill_to_name(rs.getString("bill_to_name"));
                orderHeader.setBill_to_company(rs.getString("bill_to_company"));
                orderHeader.setBill_to_address1(rs.getString("bill_to_address1"));
                orderHeader.setBill_to_address2(rs.getString("bill_to_address2"));
                orderHeader.setBill_to_city(rs.getString("bill_to_city"));
                orderHeader.setBill_to_state(rs.getString("bill_to_state"));
                orderHeader.setBill_to_postal(rs.getString("bill_to_postal"));
                orderHeader.setBill_to_country(rs.getString("bill_to_country"));
                orderHeader.setBill_to_fax(rs.getString("bill_to_fax"));
                orderHeader.setBill_to_phone(rs.getString("bill_to_phone"));
                orderHeader.setBill_to_email(rs.getString("bill_to_email"));

                orderHeader.setTax_exempt(rs.getInt("tax_exempt"));
                orderHeader.setTax_exempt_number(rs.getString("tax_exempt_number"));
                orderHeader.setTax_exempt_expire(rs.getTimestamp("tax_exempt_expire"));

                orderHeader.setOadjust_subtotal(rs.getBigDecimal("oadjust_subtotal"));
                orderHeader.setDiscount_total(rs.getBigDecimal("discount_total"));
                orderHeader.setShipping_total(rs.getBigDecimal("shipping_total"));
                orderHeader.setHandling_total(rs.getBigDecimal("handling_total"));
                orderHeader.setTax_total(rs.getBigDecimal("tax_total"));
                orderHeader.setTax_included(rs.getBigDecimal("tax_included"));

                orderHeader.setCc_name(rs.getString("cc_name"));
                orderHeader.setCc_type(rs.getString("cc_type"));
                orderHeader.setCc_expmonth(rs.getInt("cc_expmonth"));
                orderHeader.setCc_expyear(rs.getInt("cc_expyear"));
                orderHeader.setCc_number(rs.getString("cc_number"));
                orderHeader.setTotal_total(rs.getBigDecimal("total_total"));

                if (orderHeader.getCc_number().length() > 4)
                {
                    orderHeader.setCc_number("XXXXXXXXXX" + orderHeader.getCc_number().substring(orderHeader.getCc_number().length() - 5));
                }
                else
                {
                    orderHeader.setPayment_terms(rs.getString("payment_terms"));
                    orderHeader.setTotal_total(rs.getBigDecimal("total_total"));
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getOrderReceipt");
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
                LOGGER.info("Error Execute CheckoutDAO : function getOrderReceipt - SQLException");
                sqlE.printStackTrace();
            }
        }

        return orderHeader;
    }

    public TaxTables getTaxTables(String zip, String city)
    {
        TaxTables taxTables = null;

        try
        {
            LOGGER.info("Execute CheckoutDAO : function getTaxTables");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout3.getTaxTables");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, zip);
            pstmt.setString(2, city);

            rs = pstmt.executeQuery();
            taxTables = new TaxTables();
            if (rs.next())
            {
                taxTables.setStateTax(rs.getDouble("STATEUTAX"));
                taxTables.setCountyTax(rs.getDouble("CNTUTAX"));
                taxTables.setCountyTransTax(rs.getDouble("CNTLCLUTAX"));
                taxTables.setCityTax(rs.getDouble("CTYUTAX"));
                taxTables.setCityTranTax(rs.getDouble("CTYLCLUTAX"));
                taxTables.setCombuTax(rs.getDouble("COMBUTAX"));
            }else
            {
                taxTables.setStateTax(new Double(0));
                taxTables.setCountyTax(new Double(0));
                taxTables.setCountyTransTax(new Double(0));
                taxTables.setCityTax(new Double(0));
                taxTables.setCityTranTax(new Double(0));
                taxTables.setCombuTax(new Double(0));
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getTaxTables");
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
                LOGGER.info("Error Execute CheckoutDAO : function getTaxTables - SQLException");
                sqlE.printStackTrace();
            }
        }

        return taxTables;
    }

    public Boolean setOrderHeader(OrderHeader orderHeader)
    {
        Boolean flag = false;
        try
        {

            LOGGER.info("Execute CheckoutDAO : function setOrderHeader");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("checkout.insert.orderHeader");
            pstmt = conn.prepareStatement(sql);
            //  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            pstmt.setString(1, orderHeader.getOrderNumber());
            pstmt.setString(2, orderHeader.getShopper_id());
            //   pstmt.setString(3, dateFormat.format(new Date()));
            pstmt.setString(3, orderHeader.getShip_to_name());
            pstmt.setString(4, orderHeader.getShip_to_company());
            pstmt.setString(5, orderHeader.getShip_to_address1());
            pstmt.setString(6, orderHeader.getShip_to_address2());
            pstmt.setString(7, orderHeader.getShip_to_city());
            pstmt.setString(8, orderHeader.getShip_to_state());
            pstmt.setString(9, orderHeader.getShip_to_postal());
            pstmt.setString(10, orderHeader.getShip_to_country());
            pstmt.setString(11, orderHeader.getShip_to_phone());
            pstmt.setString(12, orderHeader.getShip_to_email());
            
            if (orderHeader.getShip_terms().equalsIgnoreCase("Optional Ground / Item"))
            	pstmt.setString(13, "1");
            else if (orderHeader.getShip_terms().equalsIgnoreCase("Optional Next Day / Item"))
            	pstmt.setString(13, "4");
            else if (orderHeader.getShip_terms().equalsIgnoreCase("Optional 2 Days / Item"))
            	pstmt.setString(13, "5");
            else
            	pstmt.setString(13, orderHeader.getShip_method());
            
            pstmt.setString(14, orderHeader.getBill_to_name());
            pstmt.setString(15, orderHeader.getBill_to_company());
            pstmt.setString(16, orderHeader.getBill_to_address1());
            pstmt.setString(17, orderHeader.getBill_to_address2());
            pstmt.setString(18, orderHeader.getBill_to_city());
            pstmt.setString(19, orderHeader.getBill_to_state());
            pstmt.setString(20, orderHeader.getBill_to_postal());
            pstmt.setString(21, orderHeader.getBill_to_country());
            pstmt.setString(22, orderHeader.getBill_to_phone());
            pstmt.setString(23, orderHeader.getBill_to_email());

            pstmt.setFloat(24, orderHeader.getOadjust_subtotal().floatValue());
            pstmt.setFloat(25, orderHeader.getHandling_total().floatValue());
            pstmt.setFloat(26, orderHeader.getFreightTax().floatValue());
            pstmt.setFloat(27, orderHeader.getShipping_total().floatValue());
            pstmt.setFloat(28, orderHeader.getTax_total().floatValue());
            pstmt.setFloat(29, orderHeader.getTotal_total().floatValue());

            pstmt.setString(30, orderHeader.getApprovalNumber());
            pstmt.setString(31, orderHeader.getCc_name());
            if (orderHeader.getCc_number().length() > 12)
            {
                pstmt.setString(32, "XXXXXXXXXXXXXXXX");
            }
            else
            {
                pstmt.setString(32, orderHeader.getCc_number());
            }

            //pstmt.setInt(33, orderHeader.getCc_expmonth());
            //pstmt.setInt(34, orderHeader.getCc_expyear());
            pstmt.setInt(33, 1);
            pstmt.setInt(34, 1);
            pstmt.setString(35, orderHeader.getPayment_terms());
            pstmt.setString(59, orderHeader.getPayment_terms());
            pstmt.setString(36, orderHeader.getCc_type());

            //pstmt.setBoolean(37, orderHeader.getTaxable());
            pstmt.setBoolean(37, true);

            pstmt.setString(38, orderHeader.getOrderStatus());

            pstmt.setFloat(39, orderHeader.getStatetaxperc());
            pstmt.setFloat(40, orderHeader.getCountytaxperc());
            pstmt.setFloat(41, orderHeader.getCountytrantaxperc());
            pstmt.setFloat(42, orderHeader.getCitytaxperc());
            pstmt.setFloat(43, orderHeader.getCitytrantaxperc());

            pstmt.setInt(44, orderHeader.getAgentIDEnter());
            pstmt.setString(45, orderHeader.getDataSource());
            pstmt.setString(46, orderHeader.getEditAgent());
            pstmt.setString(47, orderHeader.getUser_name());
            pstmt.setString(48, orderHeader.getIp_address());

            pstmt.setFloat(49, orderHeader.getTotal_disc().floatValue());
            pstmt.setFloat(50, orderHeader.getDiscount_total().floatValue());
            pstmt.setFloat(51, orderHeader.getTotal_list().floatValue());

            pstmt.setString(52, orderHeader.getListingtype());
            pstmt.setString(53, orderHeader.getAvs_address());
            pstmt.setString(54, orderHeader.getAvs_zip());
            pstmt.setString(55, orderHeader.getAvs_country());
            pstmt.setString(56, orderHeader.getVenuetype());
            pstmt.setString(57, orderHeader.getShip_terms());
            pstmt.setBoolean(58, orderHeader.getByAgent());
            pstmt.setFloat(60, orderHeader.getWarranty_total().floatValue());

            int i = pstmt.executeUpdate();
            if (i > 0)
            {
                flag = true;
            }

        }
        catch (Exception ex)
        {
            LOGGER.info("Error Execute CheckoutDAO : function setOrderHeader");
            ex.printStackTrace();
            flag = false;
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
                LOGGER.info("Error Execute CheckoutDAO : function setOrderHeader - SQLException");
                sqlE.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    public Boolean setOrderLine(OrderLine orderLine)
    {
        Boolean flag = false;
        String str_cosmetic = "";
        try
        {
            LOGGER.info("Execute CheckoutDAO : function setOrderLine");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("checkout.insert.orderline");
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, orderLine.getOrderNumber());
            pstmt.setString(2, orderLine.getShopper_id());
            //    pstmt.setString(3, dateFormat.format(new Date())); 
            //    pstmt.setString(4, dateFormat.format(new Date())); 
            pstmt.setInt(3, orderLine.getItem_id());
            pstmt.setString(4, orderLine.getItem_sku());
            pstmt.setString(5, orderLine.getDescription());
            pstmt.setBigDecimal(6, orderLine.getWeight());
            pstmt.setInt(7, orderLine.getQuantity());
            pstmt.setInt(8, orderLine.getQtyPicked());
            pstmt.setInt(9, orderLine.getQtyShipped());
            pstmt.setBigDecimal(10, orderLine.getProduct_list_price().divide(new BigDecimal(100)));
            pstmt.setBigDecimal(11, orderLine.getIadjust_regularprice());
            pstmt.setBigDecimal(12, orderLine.getIadjust_currentprice());
            pstmt.setBigDecimal(13, orderLine.getOadjust_adjustedprice());
            pstmt.setBigDecimal(14, orderLine.getDiscounted_price());
            if(orderLine.getCategory_id() == 11946 || orderLine.getCategory_id() == 11947 || orderLine.getCategory_id() == 11949){
            	str_cosmetic = orderLine.getCosmetic_grade().toString();
            }
            pstmt.setString(15, str_cosmetic);
            
            int i = pstmt.executeUpdate();
            if (i > 0)
            {
                flag = true;
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function setOrderLine");
            e.printStackTrace();
            flag = false;
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
                flag = false;
                LOGGER.info("Error Execute CheckoutDAO : function setOrderLine - SQLException");
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

    public Boolean clearBasket(String shopper_id, Boolean byAgent)
    {
        Boolean Flag = false;

        try
        {
            LOGGER.info("Execute ProducDAO - Function ClearOrderItem ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            //Delete from estore_basket         
            String sql3 = daoUtil.getString("product.basket.delete");
            if (byAgent)
                sql3 = daoUtil.getString("product.basket.delete.agent");
            pstmt = conn.prepareStatement(sql3);
            pstmt.setString(1, shopper_id);
            pstmt.executeUpdate();
            pstmt = null;

            //Update status  for store inventory
            String sql1 = daoUtil.getString("product.basket.item.sold");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopper_id);
            pstmt.setBoolean(2, byAgent);
            int x = pstmt.executeUpdate();
            pstmt = null;

            //Delete from estore_basket_item
            String sql2 = daoUtil.getString("product.basket.item.all.delete");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, shopper_id);
            pstmt.setBoolean(2, byAgent);
            int y = pstmt.executeUpdate();

            if (x > 0 && y > 0)
            {
                Flag = true;
            }
            //Flag = true;
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function ClearOrderItem");
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

    public Boolean setOrdersHeld(String oldShopper_id, String newShopper_id, Timestamp expirationHoldDate)
    {
        Boolean flag = false;

        try
        {
            LOGGER.info("Execute ProducDAO - Function setOrdersHeld ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);
            //Add orders Held
            String sql1 = daoUtil.getString("checkout3.ordersHeld.set");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, oldShopper_id);
            pstmt.setString(2, newShopper_id);
            pstmt.setTimestamp(3, expirationHoldDate);

            int x = pstmt.executeUpdate();

            if (x > 0)
            {
                flag = true;
            }
            //Flag = true;
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function setOrdersHeld");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (flag)
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
        return flag;
    }

    public Boolean setBasketsHeld(String oldShopper_id, String newShopper_id, String agent_id)
    {
        Boolean flag = false;
        Boolean byAgent = false;
        if (!agent_id.isEmpty())
            byAgent = true;
        agent_id = agent_id.isEmpty() ? null : agent_id;
        try
        {
            LOGGER.info("Execute ProducDAO - Function setBasketsHeld ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            //Add orders Held
            String sql1 = daoUtil.getString("checkout3.ordersHeld.inventory.set");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, oldShopper_id);
            pstmt.setBoolean(2, byAgent);
            int x = pstmt.executeUpdate();
            pstmt = null;

            String sql2 = daoUtil.getString("checkout3.ordersHeld.basket.set");
            if (byAgent)
                sql2 = daoUtil.getString("checkout3.ordersHeld.basket.set.agent");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, newShopper_id);
            pstmt.setString(2, agent_id);
            pstmt.setString(3, oldShopper_id);
            int y = pstmt.executeUpdate();

            String sql3 = daoUtil.getString("checkout3.ordersHeld.basketItem.set");
            pstmt = conn.prepareStatement(sql3);
            pstmt.setString(1, newShopper_id);
            pstmt.setString(2, oldShopper_id);
            pstmt.setBoolean(3, byAgent);
            int z = pstmt.executeUpdate();

            if (x > 0 && y > 0 && z > 0)
            {
                flag = true;
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function setBasketsHeld");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (flag)
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

        return flag;
    }

    //cancel held order by TienDang date : 6/01/2012
    public Boolean setBasketsCancel(String orderNumber)
    {
        Boolean flag = false;
        
        try
        {
            LOGGER.info("Execute ProducDAO - Function setBasketsHeld ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);
            int order = Integer.parseInt(orderNumber);
            
            //get held order
            String sql1 = daoUtil.getString("checkout3.cancelHeld.getOrder");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setInt(1, order);
            ResultSet rs_held = pstmt.executeQuery();
            String held_order = null;
            while (rs_held.next()) {
            	held_order = rs_held.getString("shopper_id");	
			}
            
            if(this.updateItemSku(held_order)){
            	String removeOrderHeld = daoUtil.getString("checkout3.cancelHeld.deleteOrdersHeld");
            	pstmt = conn.prepareStatement(removeOrderHeld);
                pstmt.setString(1, held_order);
                pstmt.executeUpdate();
                
                String removeBasketItem = daoUtil.getString("checkout3.cancelHeld.deleteBasketItem");
            	pstmt = conn.prepareStatement(removeBasketItem);
                pstmt.setString(1, held_order);
                pstmt.executeUpdate();
                
                String removeEstoreBasket = daoUtil.getString("checkout3.cancelHeld.deleteEstoreBasket");
            	pstmt = conn.prepareStatement(removeEstoreBasket);
                pstmt.setInt(1, order);
                pstmt.executeUpdate();
                
                String removeAvgMhz = daoUtil.getString("checkout3.cancelHeld.deleteAvgMhz");
            	pstmt = conn.prepareStatement(removeAvgMhz);
                pstmt.setInt(1, order);
                pstmt.executeUpdate();
                
                flag = true;
            }
            
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function setBasketsHeld");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (flag)
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

        return flag;
    }

    
    public boolean updateItemSku(String held_order){
    	boolean result = false;
    	try{
    		LOGGER.info("Execute ProducDAO - Function setBasketsHeld ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            
            String sql = daoUtil.getString("checkout3.cancelHeld.getListSku");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, held_order);
            ResultSet rs_sku = pstmt.executeQuery();
            
            String  lstSku = "(";
            
            while (rs_sku.next()) {
				lstSku += "'"+rs_sku.getString("item_sku")+"',";
			}
            lstSku = lstSku.substring(0, lstSku.length()-1);
            lstSku +=")";
            
            //System.out.println(lstSku);
            
            pstmt = conn.prepareStatement("update estore_inventory set status='AGENT-STORE-AVAILABLE' where item_sku in "+ lstSku);
			int i = pstmt.executeUpdate();
			
			if(i>0){
				result = true;
			}
            
    	}catch (Exception e) {
    		LOGGER.warning("ERROR Execute DAO - Function setBasketsHeld");
            e.printStackTrace();
		}
    	return result;
    }
    
    public int getCheckCat(String shopper_id,Boolean byAgent)
    {
        //checkout.select.quantity.get
        int cat = 0;

        try
        {
            LOGGER.info("Execute ProducDAO - Function getCheckCat ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            //Add orders Held
            String sql1 = daoUtil.getString("checkout.select.quantity.get");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopper_id);
            pstmt.setBoolean(2, byAgent);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                cat = rs.getInt("cat");
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function getCheckCat");
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

        return cat;
    }
    
    public int getQtyCat(String shopper_id,int category_id)
    {
        //checkout.select.quantity.get
        int cat = 0;

        try
        {
            LOGGER.info("Execute ProducDAO - Function getCheckCat ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            //Add orders Held
            String sql1 = daoUtil.getString("checkout.select.quantity.get.category");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopper_id);
            pstmt.setInt(2, category_id);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                cat = rs.getInt("catqty");
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function getCheckCat");
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

        return cat;
    }
    
    public int getHoldDays()
    {
        int maxDiscount = 0;
        try
        {
            LOGGER.info("Execute CheckoutDAO : function getHoldDays");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout.get.max.discount");

            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                maxDiscount = rs.getInt("holddays");
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Error Execute CheckoutDAO : function getHoldDays");
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
                LOGGER.info("Error Execute CheckoutDAO : function getHoldDays - SQLException");
                sqlE.printStackTrace();
            }
        }
        return maxDiscount;
    }

    public Boolean deleteOrders(String order_id, String shopper_id)
    {
        Boolean flag = false;

        try
        {
            LOGGER.info("Execute CheckoutDAO - Function deleteOrders ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql1 = daoUtil.getString("checkout3.delete.order.header");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, order_id);
            pstmt.setString(2, shopper_id);

            int x = pstmt.executeUpdate();
            pstmt = null;

            String sql2 = daoUtil.getString("checkout3.delete.order.header.line");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, order_id);
            pstmt.setString(2, shopper_id);

            int y = pstmt.executeUpdate();

            if (x > 0 || y > 0)
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute CheckoutDAO - Function deleteOrders");
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

        return flag;
    }

    public Boolean addAvgMhz(Avg_mhz avgMhz)
    {
        Boolean flag = false;

        try
        {
            LOGGER.info("Execute CheckoutDAO - Function addAvgMhz ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql1 = daoUtil.getString("checkout3.add.avg.mhz");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, avgMhz.getOrder_number());
            pstmt.setString(2, avgMhz.getAvgmhz());
            pstmt.setInt(3, avgMhz.getUnit_mhz());
            pstmt.setInt(4, avgMhz.getSpeed_total());
            pstmt.setFloat(5, avgMhz.getTotal_price().floatValue());

            int x = pstmt.executeUpdate();

            if (x > 0)
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute CheckoutDAO - Function deleteOrders");
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

        return flag;
    }

    public Boolean deleteAvgMhz(String orderNumber)
    {
        Boolean flag = false;

        try
        {
            LOGGER.info("Execute CheckoutDAO - Function deleteAvgMhz ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql1 = daoUtil.getString("checkout3.delete.avg.mhz");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, orderNumber);

            int x = pstmt.executeUpdate();

            if (x >= 0)
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute CheckoutDAO - Function deleteAvgMhz");
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

        return flag;
    }

    public boolean checkStateInShippingTax(String state)
    {
        boolean chk = false;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.state.byshippingtax");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, state);

            rs = pstmt.executeQuery();

            if (rs != null && rs.next())
            {
                chk = true;
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
        return chk;
    }

    public Boolean addAuthLog(Map<String, String> objResponse)
    {
        boolean flag = false;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("checkout3.add.authLog");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(objResponse.get("OrderId")));

            if (objResponse.get("PaymentType").equalsIgnoreCase("Credit"))
            {

                pstmt.setString(2, objResponse.containsKey("AUTHCODE") ? objResponse.get("AUTHCODE") : "DENIED");
                pstmt.setString(3, objResponse.containsKey("UrlResponse") ? objResponse.get("UrlResponse") : "");
                pstmt.setString(4, "");
                pstmt.setString(5, "");
                pstmt.setString(6, "");
                pstmt.setString(7, "");
                pstmt.setString(8, "");
                pstmt.setString(9, "");
                pstmt.setString(10, "");
                pstmt.setString(11, "");
                pstmt.setString(12, "");
                pstmt.setString(13, "");
                pstmt.setString(14, "");
                pstmt.setString(15, "");
                pstmt.setString(16, "");
                pstmt.setString(17, objResponse.containsKey("AUTHCODE") ? objResponse.get("AUTHCODE") : "");
                pstmt.setString(18, objResponse.containsKey("PNREF") ? objResponse.get("PNREF") : "");

                if (objResponse.containsKey("RESPMSG") && objResponse.get("RESPMSG").equals("Approved"))
                    pstmt.setString(19, "success");
                else
                    pstmt.setString(19, "");

                pstmt.setString(20, "");
                pstmt.setString(21, "");
            }
            else
            {
                pstmt.setString(2, "FAITH");
                pstmt.setString(3, "");
                pstmt.setString(4, "");
                pstmt.setString(5, "");
                pstmt.setString(6, "");
                pstmt.setString(7, "");
                pstmt.setString(8, "");
                pstmt.setString(9, "");
                pstmt.setString(10, "");
                pstmt.setString(11, "");
                pstmt.setString(12, "");
                pstmt.setString(13, "");
                pstmt.setString(14, "");
                pstmt.setString(15, "");
                pstmt.setString(16, "");
                pstmt.setString(17, "");
                pstmt.setString(18, "");
                pstmt.setString(19, "");
                pstmt.setString(20, "");
                pstmt.setString(21, "");
            }

            if (pstmt.executeUpdate() > 0)
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute CheckoutDAO - function addAuthLog");
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
                sqlE.printStackTrace();
            }
        }
        return flag;
    }

}
