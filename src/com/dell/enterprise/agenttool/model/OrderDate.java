/*
 * FILENAME
 *     OrderDate.java
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

import com.sun.xml.internal.bind.v2.TODO;

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
public class OrderDate implements Serializable
{
    private String month;
    private int monthNum;
    private int numOrder;
    private BigDecimal totalSum;
    private BigDecimal totalAvg;
    private BigDecimal totalMax;
    private BigDecimal totalMin;
    private BigDecimal discAvg;
    private BigDecimal discMax;
    private BigDecimal discMin;
    private int dayNum;

    /**
     * @return the month
     */
    public String getMonth()
    {
        return month;
    }

    /**
     * @param month
     *            the month to set
     */
    public void setMonth(String month)
    {
        this.month = month;
    }

    /**
     * @return the monthNum
     */
    public int getMonthNum()
    {
        return monthNum;
    }

    /**
     * @param monthNum
     *            the monthNum to set
     */
    public void setMonthNum(int monthNum)
    {
        this.monthNum = monthNum;
    }

    /**
     * @return the numOrder
     */
    public int getNumOrder()
    {
        return numOrder;
    }

    /**
     * @param numOrder
     *            the numOrder to set
     */
    public void setNumOrder(int numOrder)
    {
        this.numOrder = numOrder;
    }

    /**
     * @return the totalSum
     */
    public BigDecimal getTotalSum()
    {
        return totalSum;
    }

    /**
     * @param totalSum
     *            the totalSum to set
     */
    public void setTotalSum(BigDecimal totalSum)
    {
        this.totalSum = totalSum;
    }

    /**
     * @return the totalAvg
     */
    public BigDecimal getTotalAvg()
    {
        return totalAvg;
    }

    /**
     * @param totalAvg
     *            the totalAvg to set
     */
    public void setTotalAvg(BigDecimal totalAvg)
    {
        this.totalAvg = totalAvg;
    }

    /**
     * @return the totalMax
     */
    public BigDecimal getTotalMax()
    {
        return totalMax;
    }

    /**
     * @param totalMax
     *            the totalMax to set
     */
    public void setTotalMax(BigDecimal totalMax)
    {
        this.totalMax = totalMax;
    }

    /**
     * @return the totalMin
     */
    public BigDecimal getTotalMin()
    {
        return totalMin;
    }

    /**
     * @param totalMin
     *            the totalMin to set
     */
    public void setTotalMin(BigDecimal totalMin)
    {
        this.totalMin = totalMin;
    }

    /**
     * @return the discAvg
     */
    public BigDecimal getDiscAvg()
    {
        return discAvg = discAvg.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * @param discAvg
     *            the discAvg to set
     */
    public void setDiscAvg(BigDecimal discAvg)
    {
        this.discAvg = discAvg;
    }

    /**
     * @return the discMax
     */
    public BigDecimal getDiscMax()
    {
        return discMax = discMax.setScale(2);
    }

    /**
     * @param discMax
     *            the discMax to set
     */
    public void setDiscMax(BigDecimal discMax)
    {
        this.discMax = discMax;
    }

    /**
     * @return the discMin
     */
    public BigDecimal getDiscMin()
    {
        return discMin = discMin.setScale(2);
    }

    /**
     * @param discMin
     *            the discMin to set
     */
    public void setDiscMin(BigDecimal discMin)
    {
        this.discMin = discMin;
    }

    /**
     * @return the dayNum
     */
    public int getDayNum()
    {
        return dayNum;
    }

    /**
     * @param dayNum
     *            the dayNum to set
     */
    public void setDayNum(int dayNum)
    {
        this.dayNum = dayNum;
    }
}
