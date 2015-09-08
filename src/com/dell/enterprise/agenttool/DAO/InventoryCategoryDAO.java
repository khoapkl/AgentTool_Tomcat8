/*
 * FILENAME
 *     InventoryCategoryDAO.java
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
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.InventoryCategory;
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
public class InventoryCategoryDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.InventoryCategoryDAO");

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public InventoryCategory getInventoryCategory(int category_id)
    {
        InventoryCategory inventoryCategory = null;
        try
        {
            LOGGER.info("Execute InventoryCategoryDAO - Function getInventoryCategory ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("inventory.category.get");
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, category_id);

            rs = pstmt.executeQuery();
            if (rs.next())
            {
                inventoryCategory = new InventoryCategory();
                inventoryCategory.setCategory_id(rs.getInt(1));
                inventoryCategory.setParent_id(rs.getInt(2));
                inventoryCategory.setName(rs.getString(3));
                inventoryCategory.setDescription(rs.getString(4));
                inventoryCategory.setImageURL(rs.getString(5));
                inventoryCategory.setData_changed(rs.getDate(6));
                inventoryCategory.setShow_short(rs.getString(7));
                inventoryCategory.setShow_short_count(rs.getInt(8));

                Map<String, String> mapAttributes = new HashMap<String, String>();
                for (int i = 1; i <= 40; i++)
                {
                    String strAttribute = "attribute";
                    if (i < 10)
                    {
                        strAttribute += "0";
                    }
                    mapAttributes.put("attribute" + i, rs.getString(strAttribute + i));
                }
                inventoryCategory.setMapAttributes(mapAttributes);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute InventoryCategoryDAO - Function getInventoryCategory ");
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
        return inventoryCategory;
    }
}
