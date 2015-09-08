/*
 * FILENAME
 *     Summary.java
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

public class Summary implements Serializable
{
    private BigDecimal sales;
    private BigDecimal salesMhz;
    private int units;
    private BigDecimal asp;
    private int mhz;
    private int orders;
    private String agentName;

    /**
     * @return the asp
     */
    public BigDecimal getAsp()
    {
        return asp;
    }

    /**
     * @param asp
     *            the asp to set
     */
    public void setAsp(BigDecimal asp)
    {
        this.asp = asp;
    }

    /**
     * @return the sales
     */
    public BigDecimal getSales()
    {
        return sales;
    }

    /**
     * @param sales
     *            the sales to set
     */
    public void setSales(BigDecimal sales)
    {
        this.sales = sales;
    }

    /**
     * @return the salesMhz
     */
    public BigDecimal getSalesMhz()
    {
        return salesMhz;
    }

    /**
     * @param salesMhz
     *            the salesMhz to set
     */
    public void setSalesMhz(BigDecimal salesMhz)
    {
        this.salesMhz = salesMhz;
    }

    /**
     * @return the units
     */
    public int getUnits()
    {
        return units;
    }

    /**
     * @param units
     *            the units to set
     */
    public void setUnits(int units)
    {
        this.units = units;
    }

    /**
     * @return the mhz
     */
    public int getMhz()
    {
        return mhz;
    }

    /**
     * @param mhz
     *            the mhz to set
     */
    public void setMhz(int mhz)
    {
        this.mhz = mhz;
    }

    /**
     * @return the orders
     */
    public int getOrders()
    {
        return orders;
    }

    /**
     * @param orders
     *            the orders to set
     */
    public void setOrders(int orders)
    {
        this.orders = orders;
    }

    /**
     * @return the agentName
     */
    public String getAgentName()
    {
        return agentName;
    }

    /**
     * @param agentName
     *            the agentName to set
     */
    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }
}
