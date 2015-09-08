/*
 * FILENAME
 *     avg_mhz.java
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
 * @author linhdo
 * 
 * @version $Id$
 **/
public class Avg_mhz implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.</p>
     **/
    
    private static final long serialVersionUID = 1L;
    private String order_number;
    private String avgmhz;
    private int unit_mhz;
    private int speed_total;
    private BigDecimal total_price;
    private String average_mhz;

    /**
     * @return the order_number
     */
    public String getOrder_number()
    {
        return order_number;
    }

    /**
     * @param orderNumber
     *            the order_number to set
     */
    public void setOrder_number(String orderNumber)
    {
        order_number = orderNumber;
    }

    /**
     * @return the avgmhz
     */
    public String getAvgmhz()
    {
        return avgmhz;
    }

    /**
     * @param avgmhz
     *            the avgmhz to set
     */
    public void setAvgmhz(String avgmhz)
    {
        this.avgmhz = avgmhz;
    }

    /**
     * @return the unit_mhz
     */
    public int getUnit_mhz()
    {
        return unit_mhz;
    }

    /**
     * @param unitMhz
     *            the unit_mhz to set
     */
    public void setUnit_mhz(int unitMhz)
    {
        unit_mhz = unitMhz;
    }

    /**
     * @return the speed_total
     */
    public int getSpeed_total()
    {
        return speed_total;
    }

    /**
     * @param speedTotal
     *            the speed_total to set
     */
    public void setSpeed_total(int speedTotal)
    {
        speed_total = speedTotal;
    }

    /**
     * @return the total_price
     */
    public BigDecimal getTotal_price()
    {
        return total_price;
    }

    /**
     * @param totalPrice
     *            the total_price to set
     */
    public void setTotal_price(BigDecimal totalPrice)
    {
        total_price = totalPrice;
    }

    /**
     * @return the average_mhz
     */
    public String getAverage_mhz()
    {
        return average_mhz;
    }

    /**
     * @param averageMhz
     *            the average_mhz to set
     */
    public void setAverage_mhz(String averageMhz)
    {
        average_mhz = averageMhz;
    }

}
