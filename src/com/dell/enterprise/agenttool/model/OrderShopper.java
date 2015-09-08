/*
 * FILENAME
 *     OrderShopper.java
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

package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.math.BigDecimal;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrderShopper implements Serializable
{
    private  String shopId;
    private int numOrder;
    private BigDecimal total;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private  String ship_to_name;
    private  String ship_to_company;
    private int id;
    /**
     * @return the shopId
     */
    public String getShopId()
    {
        return shopId;
    }
    /**
     * @param shopId the shopId to set
     */
    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }
    /**
     * @return the numOrder
     */
    public int getNumOrder()
    {
        return numOrder;
    }
    /**
     * @param numOrder the numOrder to set
     */
    public void setNumOrder(int numOrder)
    {
        this.numOrder = numOrder;
    }
    /**
     * @return the total
     */
    public BigDecimal getTotal()
    {
        return total;
    }
    /**
     * @param total the total to set
     */
    public void setTotal(BigDecimal total)
    {
        this.total = total;
    }
    /**
     * @return the avg
     */
    public BigDecimal getAvg()
    {
        return avg;
    }
    /**
     * @param avg the avg to set
     */
    public void setAvg(BigDecimal avg)
    {
        this.avg = avg;
    }
    /**
     * @return the max
     */
    public BigDecimal getMax()
    {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(BigDecimal max)
    {
        this.max = max;
    }
    /**
     * @return the min
     */
    public BigDecimal getMin()
    {
        return min;
    }
    /**
     * @param min the min to set
     */
    public void setMin(BigDecimal min)
    {
        this.min = min;
    }
    /**
     * @return the ship_to_name
     */
    public String getShip_to_name()
    {
        return ship_to_name;
    }
    /**
     * @param shipToName the ship_to_name to set
     */
    public void setShip_to_name(String shipToName)
    {
        ship_to_name = shipToName;
    }
    /**
     * @return the ship_to_company
     */
    public String getShip_to_company()
    {
        return ship_to_company;
    }
    /**
     * @param shipToCompany the ship_to_company to set
     */
    public void setShip_to_company(String shipToCompany)
    {
        ship_to_company = shipToCompany;
    }
    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    } 
}
