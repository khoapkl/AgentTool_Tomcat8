/*
 * FILENAME
 *     OrdersHeld.java
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
import java.sql.Timestamp;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrdersHeld implements Serializable
{

    /**
     * <p>
     * TODO Describe what this data member models and how it's used.</p>
     **/
    
    private static final long serialVersionUID = 1L;
    private String shopper_id;
    private String held_order;
    private Timestamp exp_date;
    private int exp_days;
    private Boolean byAgent;
    /**
     * @return the shopper_id
     */
    public String getShopper_id()
    {
        return shopper_id;
    }
    /**
     * @param shopperId the shopper_id to set
     */
    public void setShopper_id(String shopperId)
    {
        shopper_id = shopperId;
    }
    /**
     * @return the held_order
     */
    public String getHeld_order()
    {
        return held_order;
    }
    /**
     * @param heldOrder the held_order to set
     */
    public void setHeld_order(String heldOrder)
    {
        held_order = heldOrder;
    }
    /**
     * @return the exp_date
     */
    public Timestamp getExp_date()
    {
        return exp_date;
    }
    /**
     * @param expDate the exp_date to set
     */
    public void setExp_date(Timestamp expDate)
    {
        exp_date = expDate;
    }
    /**
     * @return the exp_days
     */
    public int getExp_days()
    {
        return exp_days;
    }
    /**
     * @param expDays the exp_days to set
     */
    public void setExp_days(int expDays)
    {
        exp_days = expDays;
    }
    /**
     * @param byAgent the byAgent to set
     */
    public void setByAgent(Boolean byAgent)
    {
        this.byAgent = byAgent;
    }
    /**
     * @return the byAgent
     */
    public Boolean getByAgent()
    {
        return byAgent;
    }

}
