/*
 * FILENAME
 *     ShopperAs.java
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

public class ShopperAs implements Serializable
{
    private String shopperId;
    private String shopperName;

    /**
     * @return the shopperId
     */
    public String getShopperId()
    {
        return shopperId;
    }

    /**
     * @param shopperId
     *            the shopperId to set
     */
    public void setShopperId(String shopperId)
    {
        this.shopperId = shopperId;
    }

    /**
     * @return the shopperName
     */
    public String getShopperName()
    {
        return shopperName;
    }

    /**
     * @param shopperName
     *            the shopperName to set
     */
    public void setShopperName(String shopperName)
    {
        this.shopperName = shopperName;
    }
}
