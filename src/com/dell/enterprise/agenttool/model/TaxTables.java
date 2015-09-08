/*
 * FILENAME
 *     TaxTables.java
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

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class TaxTables implements Serializable
{

    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;

    private Double stateTax = new Double(0);
    private Double countyTax = new Double(0);
    private Double countyTransTax = new Double(0);
    private Double cityTax = new Double(0);
    private Double cityTranTax = new Double(0);
    private Double combuTax = new Double(0);

    /**
     * @return the stateTax
     */
    public Double getStateTax()
    {
        return stateTax;
    }

    /**
     * @param stateTax
     *            the stateTax to set
     */
    public void setStateTax(Double stateTax)
    {
        this.stateTax = stateTax;
    }

    /**
     * @return the countyTax
     */
    public Double getCountyTax()
    {
        return countyTax;
    }

    /**
     * @param countyTax
     *            the countyTax to set
     */
    public void setCountyTax(Double countyTax)
    {
        this.countyTax = countyTax;
    }

    /**
     * @return the countyTransTax
     */
    public Double getCountyTransTax()
    {
        return countyTransTax;
    }

    /**
     * @param countyTransTax
     *            the countyTransTax to set
     */
    public void setCountyTransTax(Double countyTransTax)
    {
        this.countyTransTax = countyTransTax;
    }

    /**
     * @return the cityTax
     */
    public Double getCityTax()
    {
        return cityTax;
    }

    /**
     * @param cityTax
     *            the cityTax to set
     */
    public void setCityTax(Double cityTax)
    {
        this.cityTax = cityTax;
    }

    /**
     * @return the cityTranTax
     */
    public Double getCityTranTax()
    {
        return cityTranTax;
    }

    /**
     * @param cityTranTax
     *            the cityTranTax to set
     */
    public void setCityTranTax(Double cityTranTax)
    {
        this.cityTranTax = cityTranTax;
    }

    /**
     * @return the combuTax
     */
    public Double getCombuTax()
    {
        return combuTax;
    }

    /**
     * @param combuTax
     *            the combuTax to set
     */
    public void setCombuTax(Double combuTax)
    {
        this.combuTax = combuTax;
    }
}
