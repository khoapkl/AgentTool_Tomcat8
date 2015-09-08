/*
 * FILENAME
 *     BasketService.java
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

package com.dell.enterprise.agenttool.services;

import java.util.List;

import com.dell.enterprise.agenttool.DAO.BasketDAO;
import com.dell.enterprise.agenttool.model.EppPromotionRow;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;

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
public class BasketService
{
    public OrderRow getOrderRow(String shopper_id, int agentID)
    {
        OrderRow orderRow = null;
        BasketDAO basketDAO = new BasketDAO();
        orderRow = basketDAO.getOrderRow(shopper_id, agentID);
        //System.out.println("AgentID : " + agentID);
        if (orderRow == null)
        {
            basketDAO.addOrderRow(shopper_id, agentID);
        }
        else
        {
            basketDAO.updateOrderRowModified(shopper_id, agentID);
        }

        //Reload Data
        orderRow = basketDAO.getOrderRow(shopper_id, agentID);
        return orderRow;

    }

    public OrderRow getOrderRow(String shopper_id)
    {
        BasketDAO basketDAO = new BasketDAO();
        OrderRow orderRow = basketDAO.getOrderRow(shopper_id);
        return orderRow;
    }

    public List<EstoreBasketItem> getBasketItems(String shopper_id, Boolean byAgent)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.getBasketItems(shopper_id, byAgent);
    }

    public int getListPrice(String item_sku)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.getListPrice(item_sku);
    }

    public int countHeldOrder(String shopper_id, Boolean byAgent)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.countHeldOrder(shopper_id, byAgent);
    }

    public OrderRow getOrder(String shopper_id, int agentID)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.getOrderRow(shopper_id, agentID);
    }

    public int updatePromotionCodeBasketItem(String epp_id, String shopper_id)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.updatePromotionCodeBasketItem(epp_id, shopper_id);
    }

    public int updateBasketDiscount(int discount_type, Float discount_value, String shopper_id, String order_number)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.updateBasketDiscount(discount_type, discount_value, shopper_id, order_number);
    }
    
    //HuyNVT
    public EppPromotionRow getEppPromotionRow(String epp_id)
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.getEppPromotionRow(epp_id);
    }

    public Boolean cleanBasketItemExp()
    {
        BasketDAO basketDAO = new BasketDAO();
        return basketDAO.cleanBasketItemExp();
    }

}
