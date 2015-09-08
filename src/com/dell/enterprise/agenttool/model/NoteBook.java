/*
 * FILENAME
 *     NoteBook.java
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
import java.math.RoundingMode;

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
public class NoteBook implements Serializable
{
    private int total_unit;
    private BigDecimal total_price;
    private int total_mhz;
    private BigDecimal asp;
    private BigDecimal mhz;

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
     * @return the mhz
     */
    public BigDecimal getMhz()
    {
        return mhz;
    }

    /**
     * @param mhz
     *            the mhz to set
     */
    public void setMhz(BigDecimal mhz)
    {
        this.mhz = mhz;
    }

    /**
     * @return the total_unit
     */
    public int getTotal_unit()
    {
        return total_unit;
    }

    /**
     * @param totalUnit
     *            the total_unit to set
     */
    public void setTotal_unit(int totalUnit)
    {
        total_unit = totalUnit;
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
     * @return the total_mhz
     */
    public int getTotal_mhz()
    {
        return total_mhz;
    }

    /**
     * @param totalMhz
     *            the total_mhz to set
     */
    public void setTotal_mhz(int totalMhz)
    {
        total_mhz = totalMhz;
    }

    public NoteBook(int total_unit, BigDecimal total_price, BigDecimal total_mhz)
    {
        if (total_unit > 0)
        {
            this.asp = total_price.divide(BigDecimal.valueOf(total_unit), 0, RoundingMode.HALF_EVEN);
        }
        else
        {
            this.asp = BigDecimal.valueOf(0);
        }
        if (total_mhz.compareTo(BigDecimal.valueOf(0)) > 0)
        {
            this.mhz = total_price.divide(total_mhz, 3, RoundingMode.HALF_EVEN);
        }
        else
        {
            this.mhz = BigDecimal.valueOf(0);
        }

    }

}
