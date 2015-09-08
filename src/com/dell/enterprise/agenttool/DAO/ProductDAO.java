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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.mail.Session;

import com.dell.enterprise.agenttool.model.Product;
import com.dell.enterprise.agenttool.model.ProductAttribute;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class ProductDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.ProductDAO");

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private CallableStatement cst;

    public List<ProductAttribute> getProductAttribute(int category, String order_by)
    {
        List<ProductAttribute> listProductAttribute = new ArrayList<ProductAttribute>();
        try
        {
            LOGGER.info("Execute DAO - Function getProductAttribute");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.attribute.bycategory");

            if (order_by.trim().isEmpty())
            {
                order_by = "sortOrder";
            }
            sql = sql.replaceAll("ORDER_BY", order_by);

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category);

            rs = pstmt.executeQuery();

	            while (rs.next())
	            {
	                ProductAttribute productAttribute = new ProductAttribute();
	                productAttribute.setAttribute_id(rs.getInt(1));
	                productAttribute.setCategory_id(rs.getInt(2));
	                productAttribute.setAttribute_number(rs.getString(3));
	                productAttribute.setAttribute_name(rs.getString(4));
	                productAttribute.setUnits(rs.getString(5));
	                productAttribute.setIsVisible(rs.getBoolean(6));
	                productAttribute.setIsSearchable(rs.getBoolean(7));
	                productAttribute.setImportLocation(rs.getInt(8));
	                productAttribute.setImportColumn(rs.getString(9));
	                productAttribute.setSortOrder(rs.getInt(10));
	                productAttribute.setReorderList(rs.getInt(11));
	
	                listProductAttribute.add(productAttribute);
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

        return listProductAttribute;
    }

    private int totalRow = 0;

    
    public List<ProductAttribute> getProductAttributeBySortResult(int category, String order_by)
    {
        List<ProductAttribute> listProductAttribute = new ArrayList<ProductAttribute>();
        try
        {
            LOGGER.info("Execute DAO - Function getProductAttribute");

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql ;
            if((category == 11946 || category == 11947 || category == 11949 )){
            	sql = daoUtil.getString("search.product.attribute.bycategory.sortResult");
            
            }else{
            	
            	sql = daoUtil.getString("search.product.attribute.bycategory");
            	if (order_by.trim().isEmpty())
                {
                    order_by = "sortOrder";
                }
                sql = sql.replaceAll("ORDER_BY", order_by);
            }
            

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category);

            rs = pstmt.executeQuery();

	            while (rs.next())
	            {
	                ProductAttribute productAttribute = new ProductAttribute();
	                productAttribute.setAttribute_id(rs.getInt(1));
	                productAttribute.setCategory_id(rs.getInt(2));
	                productAttribute.setAttribute_number(rs.getString(3));
	                productAttribute.setAttribute_name(rs.getString(4));
	                productAttribute.setUnits(rs.getString(5));
	                productAttribute.setIsVisible(rs.getBoolean(6));
	                productAttribute.setIsSearchable(rs.getBoolean(7));
	                productAttribute.setImportLocation(rs.getInt(8));
	                productAttribute.setImportColumn(rs.getString(9));
	                productAttribute.setSortOrder(rs.getInt(10));
	                productAttribute.setReorderList(rs.getInt(11));
	
	                listProductAttribute.add(productAttribute);
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

        return listProductAttribute;
    }
    
    public List<Product> searchProduct(int category_id, String columns, String where_condition, String order_by_condition, int page, int rowOnPage)
    {

        List<Product> listProduct = new ArrayList<Product>();

        String columnAttr = columns;
        // "t1.item_sku, t1.name, t1.status, datediff(d,received_date,getdate()) as item_age, (CASE WHEN modified_price=0 THEN list_price ELSE modified_price END) AS list_price, t3.shopper_id, (select order_id from estore_basket where shopper_id = t3.shopper_id) as order_id, t1.flagtype, item_id = NULL";
        List<ProductAttribute> listProductAttribute = this.getProductAttributeBySortResult(category_id, "");

        for (int i = 0; i < listProductAttribute.size(); i++)
        {
            ProductAttribute productAttribute = (ProductAttribute) listProductAttribute.get(i);
            if (!columnAttr.contains(productAttribute.getAttribute_number()))
            {
                columnAttr = columnAttr.concat(",").concat(productAttribute.getAttribute_number());
            }
        }
        order_by_condition = order_by_condition.isEmpty() ? "status" : order_by_condition;
        try
        {
            LOGGER.info("Execute ProductDAO : function searchProduct");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.bycategory.sp");

            cst = conn.prepareCall(sql);
            cst.setString(1, columnAttr);
            cst.setString(2, order_by_condition);
            cst.setInt(3, page);
            cst.setInt(4, rowOnPage);
            cst.setString(5, where_condition);
            cst.registerOutParameter(6, java.sql.Types.INTEGER);

            //            System.out.println(columnAttr);
            //            System.out.println(order_by_condition);
            //            System.out.println(page);
            //            System.out.println(rowOnPage);
            //            System.out.println(where_condition);

            rs = cst.executeQuery();

            while (rs.next())
            {
                Product pro = new Product();
                pro.setItem_sku(rs.getString(1));
                pro.setName(rs.getString(2));
                pro.setStatus(rs.getString(3));
                pro.setItem_age(rs.getInt(4));
                pro.setList_price(rs.getFloat(5));

                if (rs.getString(6) != null && !rs.getString(6).isEmpty())
                    pro.setShopper_id(rs.getString(6));
                else
                    pro.setShopper_id("N/A");

                if (rs.getString(7) != null && !rs.getString(7).isEmpty())
                    pro.setOrder_id(rs.getString(7));
                else

                	pro.setOrder_id("N/A");
                pro.setFlagtype(rs.getString(8));
                pro.setIdx(rs.getInt("RowNumber"));
                pro.setCosmeticgrade(rs.getString("image_url"));
                
                Map<String, String> mapAttribute = new HashMap<String, String>();
                if(category_id == 11961 || category_id == 11962 || category_id == 11963 || category_id == 11950 ){
                	for (int i = 0; i < listProductAttribute.size(); i++)
                    {

                        ProductAttribute proAttribute = (ProductAttribute) listProductAttribute.get(i);
                        if (proAttribute.getIsVisible() == false)
                        {
                            continue;
                        }

                        if (proAttribute.getAttribute_number().equals("attribute09"))
                        {
                            if (Constants.isEmpty(rs.getString(proAttribute.getAttribute_number())))
                                mapAttribute.put(proAttribute.getAttribute_number(), "");
                            else
                                mapAttribute.put(proAttribute.getAttribute_number(), rs.getString(proAttribute.getAttribute_number()) + " MHZ");
                        }
                        else if (proAttribute.getAttribute_number().equals("attribute10"))
                        {
                            if (Constants.isEmpty(rs.getString(proAttribute.getAttribute_number())))
                            {
                                mapAttribute.put(proAttribute.getAttribute_number(), "");
                            }
                            else
                            {
                                mapAttribute.put(proAttribute.getAttribute_number(), rs.getString(proAttribute.getAttribute_number()) + " MB");
                            }

                        }
                        else if (proAttribute.getAttribute_number().equals("attribute13"))
                        {
                            if (Constants.isEmpty(rs.getString(proAttribute.getAttribute_number())))
                            {
                                mapAttribute.put(proAttribute.getAttribute_number(), "");
                            }
                            else
                            {
                                mapAttribute.put(proAttribute.getAttribute_number(), rs.getString(proAttribute.getAttribute_number()) + " GB");
                            }
                        }
                        else
                        {
                            mapAttribute.put(proAttribute.getAttribute_number(), Constants.convertValueEmpty(rs.getString(proAttribute.getAttribute_number())));
                        }
                    }
                }else
                	if(category_id == 11946 || category_id == 11947 || category_id == 11949)
                {
                for (int i = 0; i < listProductAttribute.size(); i++)
                {

                    ProductAttribute proAttribute = (ProductAttribute) listProductAttribute.get(i);
                    if (proAttribute.getIsVisible() == false)
                    {
                        continue;
                    }

                    if (proAttribute.getAttribute_number().equals("attribute13"))
                    {
                        if (Constants.isEmpty(rs.getString(proAttribute.getAttribute_number())))
                            mapAttribute.put(proAttribute.getAttribute_number(), "");
                        else
                            mapAttribute.put(proAttribute.getAttribute_number(), rs.getString(proAttribute.getAttribute_number()) + " MHZ");
                    }
                    else if (proAttribute.getAttribute_number().equals("attribute15"))
                    {
                        if (Constants.isEmpty(rs.getString(proAttribute.getAttribute_number())))
                        {
                            mapAttribute.put(proAttribute.getAttribute_number(), "");
                        }
                        else
                        {
                            mapAttribute.put(proAttribute.getAttribute_number(), rs.getString(proAttribute.getAttribute_number()) + " MB");
                        }

                    }
                    else if (proAttribute.getAttribute_number().equals("attribute18"))
                    {
                        if (Constants.isEmpty(rs.getString(proAttribute.getAttribute_number())))
                        {
                            mapAttribute.put(proAttribute.getAttribute_number(), "");
                        }
                        else
                        {
                            mapAttribute.put(proAttribute.getAttribute_number(), rs.getString(proAttribute.getAttribute_number()) + " MB");
                        }
                    }
                    else
                    {
                        mapAttribute.put(proAttribute.getAttribute_number(), Constants.convertValueEmpty(rs.getString(proAttribute.getAttribute_number())));
                    }
                }
                }else{
                	for (int i = 0; i < listProductAttribute.size(); i++)
                    {

                        ProductAttribute proAttribute = (ProductAttribute) listProductAttribute.get(i);
                        if (proAttribute.getIsVisible() == false)
                        {
                            continue;
                        }
                        mapAttribute.put(proAttribute.getAttribute_number(), Constants.convertValueEmpty(rs.getString(proAttribute.getAttribute_number())));
                    }
                	
                }
                

                pro.setAttributes(mapAttribute);
                listProduct.add(pro);
            }

            totalRow = cst.getInt(6);
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO : function searchProduct");
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
                if (cst != null)
                    cst.close();
            }
            catch (SQLException sqlE)
            {
                sqlE.printStackTrace();
            }
        }
        return listProduct;
    }

    public int countSearchProduct(int category_id, String where_condition, String order_by_condition)
    {
        int row_count = 0;
        try
        {
            LOGGER.info("Execute ProductDAO : function CountSearchProduct");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.bycategory.count");
            //Replay where condition
            sql = sql.replaceAll("WHERE_CONDITION", where_condition);

            LOGGER.info("SQL Search Product Count : " + sql);

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category_id);

            rs = pstmt.executeQuery();

            if (rs.next())
            {
                row_count = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            LOGGER.info("ERROR Execute ProductDAO : function CountSearchProduct");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                //                if (stmt != null)
                //                    stmt.close();
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
        //LOGGER.info("ERROR Execute ProductDAO : function CountSearchProduct - Row Count : " + row_count);
        return row_count;
    }

    public List<String> searchValueByAttribute(String attribute, int category_id, String order_by, String agentOStore)
    {
        List<String> searchValue = new ArrayList<String>();

        try
        {
            LOGGER.info("Execute DAO searchValueByAttribute");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql ="";
            if(agentOStore.equals("agent")){
            	sql = daoUtil.getString("search.product.attribute.searchvalue.bycategory");
            }else{
            	sql = daoUtil.getString("search.product.attribute.searchvalue.bycategory2");
            }
            //String sql = daoUtil.getString("search.product.attribute.searchvalue.bycategory");
            sql = sql.replace("ATTRIBUTE_ID", attribute);
            sql = sql.replace("ORDER_BY", order_by);

            LOGGER.info("Execute DAO searchValueByAttribute : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category_id);
            //Condition item in store , item auction , item unlisted
            //			pstmt.setString(2, Constants.STATUS_ITEM_INSTORE);
            //			pstmt.setString(3, Constants.STATUS_ITEM_AUCTION);
            //			pstmt.setString(4, Constants.STATUS_ITEM_UNLISTED);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                searchValue.add(rs.getString(attribute));
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
            }
        }

        return searchValue;
    }

    public List<String> getInventoryByCategory(int category_id)
    {
        List<String> InventoryStatus = new ArrayList<String>();

        try
        {
            LOGGER.info("Execute DAO - Function getInventoryByCategory");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.inventory.bycategory");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category_id);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (rs.getString(1).contains("PENDING"))
                {
                    continue;
                }
                InventoryStatus.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function getInventoryByCategory");
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
                sqlE.getStackTrace();
            }
        }

        return InventoryStatus;
    }
    
    public List<String> getCosmeticByCategory(int category_id)
    {
        List<String> Cosmetic = new ArrayList<String>();

        try
        {
            LOGGER.info("Execute DAO - Function getCosmeticByCategory");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.cosmetic.bycategory");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category_id);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                Cosmetic.add(rs.getString(1));
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function getInventoryByCategory");
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
                sqlE.getStackTrace();
            }
        }

        return Cosmetic;
    }

    public Product getItemProduct(String item_sku)
    {
        Product product = null;
        try
        {
            LOGGER.info("Execute DAO - Function getItemProduct");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.item.inventory");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                product = new Product();
                product.setCategory_id(rs.getInt("category"));
                product.setItem_sku(rs.getString("item_sku"));
                product.setName(rs.getString("name"));
                product.setShort_description(rs.getString("short_description"));
                product.setMfg_part_number(rs.getString("mfg_part_number"));
                product.setList_price(rs.getFloat("list_price"));
                product.setFlagtype(rs.getString("flagType"));
                product.setWeight(rs.getInt("weight"));
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function getItemProduct");
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
                sqlE.getStackTrace();
            }
        }
        return product;
    }

    public Boolean orderAddItem(String shopper_id, Product product, Boolean byAgent)
    {
        Boolean Flag = false;
        try
        {
            LOGGER.info("Execute ProducDAO - Function BasketItemExists ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            String sql1 = daoUtil.getString("product.basket.item.add");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopper_id);
            pstmt.setString(2, product.getMfg_part_number());
            pstmt.setString(3, product.getItem_sku());
            pstmt.setInt(4, 1);
            pstmt.setFloat(5, product.getList_price() * 100);
            pstmt.setString(6, product.getName());
            pstmt.setString(7, product.getShort_description());
            pstmt.setInt(8, product.getWeight());
            pstmt.setBoolean(9, byAgent);
            pstmt.setInt(10, product.getCategory_id());

            int x = pstmt.executeUpdate();
            pstmt = null;

            String sql2 = daoUtil.getString("product.inventory.item.updateStatus");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, product.getItem_sku());
            int y = pstmt.executeUpdate();

            if (x > 0 && y > 0)
            {
                Flag = true;
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
    
    public Boolean orderAddWarrantyItem(String shopper_id, Product product, Boolean byAgent)
    {
        Boolean Flag = false;
        try
        {
            LOGGER.info("Execute ProducDAO - Function BasketItemExists ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            String sql1 = daoUtil.getString("product.basket.item.add");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopper_id);
            pstmt.setString(2, product.getMfg_part_number());
            pstmt.setString(3, product.getItem_sku());
            pstmt.setInt(4, 1);
            pstmt.setFloat(5, product.getList_price() * 100);
            //pstmt.setFloat(5, 0);
            pstmt.setString(6, product.getName());
            pstmt.setString(7, product.getShort_description());
            pstmt.setInt(8, product.getWeight());
            pstmt.setBoolean(9, byAgent);
            pstmt.setInt(10, product.getCategory_id());

            int x = pstmt.executeUpdate();
            pstmt = null;

            /**
            String sql2 = daoUtil.getString("product.inventory.item.updateStatus");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, product.getItem_sku());
            int y = pstmt.executeUpdate();

            if (x > 0 && y > 0)
            {
                Flag = true;
            }
            **/
            
            if (x > 0) Flag = true;
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


    public Product basketItemExists(String item_sku)
    {
        //Boolean Flag = false;
        Product product = null;
        try
        {
            LOGGER.info("Execute ProducDAO - Function BasketItemExists ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("product.basket.item.exists");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                //Flag = true;

                product = new Product();
                product.setShopper_id(rs.getString("shopper_id"));
                product.setItem_sku(rs.getString("item_sku"));
                product.setName(rs.getString("name"));
                product.setShort_description(rs.getString("short_description"));
                product.setMfg_part_number(rs.getString("mfg_part_number"));
                product.setList_price(rs.getFloat("placed_price"));
                //product.setFlagtype(rs.getString("flagType"));
                product.setWeight(rs.getInt("weight"));
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
                sqlE.getStackTrace();
            }
        }

        return product;
    }
    
    public Product basketWarrantyItemExists(String item_sku, String shopper_id)
    {
        //Boolean Flag = false;
        Product product = null;
        try
        {
            LOGGER.info("Execute ProducDAO - Function BasketItemExists ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("product.basket.warranty.item.exists");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);
            pstmt.setString(2, shopper_id);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                //Flag = true;

                product = new Product();
                product.setShopper_id(rs.getString("shopper_id"));
                product.setItem_sku(rs.getString("item_sku"));
                product.setName(rs.getString("name"));
                product.setShort_description(rs.getString("short_description"));
                product.setMfg_part_number(rs.getString("mfg_part_number"));
                product.setList_price(rs.getFloat("placed_price"));
                //product.setFlagtype(rs.getString("flagType"));
                product.setWeight(rs.getInt("weight"));
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
                sqlE.getStackTrace();
            }
        }

        return product;
    }

    public Boolean clearOrderItem(String shopper_id, Boolean byAgent)
    {
        Boolean Flag = false;

        try
        {
            LOGGER.info("Execute ProducDAO - Function ClearOrderItem ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            //Delete from estore_basket    
            /*
             * String sql3 = daoUtil.getString("product.basket.delete"); pstmt =
             * conn.prepareStatement(sql3); pstmt.setString(1, shopper_id);
             * 
             * pstmt.executeUpdate(); pstmt = null;
             */

            //UPDATE STATUS FOR Store_inventory
            String sql1 = daoUtil.getString("product.basket.item.clear");
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

    public Boolean deleteOrderItem(String shopper_id, String sku)
    {
        Boolean Flag = false;
        int x = 0;

        try
        {
            LOGGER.info("Execute ProducDAO - Function DeleteOrderItem ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);
            
            if (!sku.contains("WARRANTY")){
	            String sql1 = daoUtil.getString("product.inventory.item.update.bySku");
	            pstmt = conn.prepareStatement(sql1);
	            pstmt.setString(1, sku);
	
	            x = pstmt.executeUpdate();
            }
            else {
            	x = 1;
            }
            pstmt = null;

            String sql2 = daoUtil.getString("product.basket.item.delete");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, shopper_id);
            pstmt.setString(2, sku);
            int y = pstmt.executeUpdate();

            if (x > 0 && y > 0)
            {
                Flag = true;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO - Function DeleteOrderItem");
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

    public Map<String, String> getBasketKeyValue(String shopper_id)
    {
        Map<String, String> basketKeyValue = new HashMap<String, String>();

        try
        {
            LOGGER.info("Execute ProducDAO - Function getBasketKeyValue ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql1 = daoUtil.getString("product.basket.item.all");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopper_id);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                basketKeyValue.put(rs.getString("item_sku"), rs.getString("item_sku"));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute ProducDAO - Function getBasketKeyValue");
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
                sqlE.getStackTrace();
            }
        }
        return basketKeyValue;
    }

    public String getNamePending(String shopper_id)
    {
        String strReturn = "";
        String sql = "";

        try
        {
            LOGGER.info("Execute ProducDAO - Function getNamePending ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            if (Constants.isNumber(shopper_id))
            {
                sql = daoUtil.getString("search.product.getNamePending_1");
                //LOGGER.info("Execute ProducDAO - Function getNamePending - SQL : search.product.getNamePending_1");
            }
            else
            {
                sql = daoUtil.getString("search.product.getNamePending_2");
                //LOGGER.info("Execute ProducDAO - Function getNamePending - SQL : search.product.getNamePending_2");
            }

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                strReturn = rs.getString(1);
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute ProducDAO - Function getNamePending");
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
                sqlE.getStackTrace();
            }
        }

        return strReturn;
    }

    public String getNameHold(String shopper_id)
    {
        String strReturn = "";

        try
        {
            LOGGER.info("Execute ProducDAO - Function getNameHold ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.getNameHold_1");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper_id);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                String new_shopper_id = rs.getString(1);

                pstmt = null;
                rs = null;

                String sql1 = daoUtil.getString("search.product.getNameHold_2");
                pstmt = conn.prepareStatement(sql1);
                pstmt.setString(1, new_shopper_id);
                rs = pstmt.executeQuery();
                if (rs.next())
                {
                    strReturn = rs.getString(1);
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute ProducDAO - Function getNameHold");
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
                sqlE.getStackTrace();
            }
        }
        return strReturn;
    }

    public String getNameSold(String item_sku)
    {
        String strReturn = "";
        try
        {
            LOGGER.info("Execute ProducDAO - Function getNameSold ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.product.getNameSold_1");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                String order_number = rs.getString(1);

                pstmt = null;
                rs = null;

                String sql1 = daoUtil.getString("search.product.getNameSold_2");
                pstmt = conn.prepareStatement(sql1);
                pstmt.setString(1, order_number);
                rs = pstmt.executeQuery();
                if (rs.next())
                {
                    strReturn = rs.getString(1);
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute ProducDAO - Function getNameSold");
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
                sqlE.getStackTrace();
            }
        }
        return strReturn;
    }

    public Product getProductDetail(String item_sku, List<ProductAttribute> listProductAttribute)
    {
        Product product = new Product();

        try
        {
            LOGGER.info("Execute ProducDAO - Function getProductDetail ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("product.detail");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                product.setItem_sku(rs.getString("item_sku"));
                product.setCategory_id(rs.getInt("category_id"));
                Map<String, String> mapAttribute = new HashMap<String, String>();
                for (int i = 0; i < listProductAttribute.size(); i++)
                {
                    ProductAttribute productAttribute = (ProductAttribute) listProductAttribute.get(i);

                    if (rs.getString(productAttribute.getAttribute_number()) == null)
                    {
                        mapAttribute.put(productAttribute.getAttribute_number(), "");
                    }
                    else
                    {
                        mapAttribute.put(productAttribute.getAttribute_number(), rs.getString(productAttribute.getAttribute_number()));
                    }
                }
                product.setAttributes(mapAttribute);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute ProducDAO - Function getProductDetail");
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
                sqlE.getStackTrace();
            }
        }
        return product;
    }

    public Integer getCategoryIdBySku(String item_sku)
    {
        int category_id = 0;
        try
        {
            LOGGER.info("Execute ProducDAO - Function getCategoryIdBySku ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("product.getCategoryId");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item_sku);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                category_id = rs.getInt(1);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute ProducDAO - Function getCategoryIdBySku");
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
                sqlE.getStackTrace();
            }
        }
        return category_id;
    }

    /**
     * @param totalRow
     *            the totalRow to set
     */
    public void setTotalRow(int totalRow)
    {
        this.totalRow = totalRow;
    }

    /**
     * @return the totalRow
     */
    public int getTotalRow()
    {
        return totalRow;
    }

}
