/*
 * FILENAME
 *     PayMethods.java
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

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class PayMethods
{
    private int code;
    private String name;
    private int isCreditCard;

    /**
     * @return the code
     */
    public int getCode()
    {
        return code;
    }

    /**
     * @param code
     *            the code to set
     */
    public void setCode(int code)
    {
        this.code = code;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the isCreditCard
     */
    public int getIsCreditCard()
    {
        return isCreditCard;
    }

    /**
     * @param isCreditCard
     *            the isCreditCard to set
     */
    public void setIsCreditCard(int isCreditCard)
    {
        this.isCreditCard = isCreditCard;
    }
}
