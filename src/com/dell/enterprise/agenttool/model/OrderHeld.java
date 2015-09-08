/*
 * FILENAME
 *     OrderHeld.java
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

import java.math.BigDecimal;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * TODO Add one-sentence summarising the class functionality here; this sentence
 * should only contain one full-stop.</p>
 * <p>
 * TODO Add detailed HTML description of class here, including the following,
 * where relevant:
 * <ul>
 *     <li>TODO Description of what the class does and how it is done.</li>
 *     <li>TODO Explanatory notes on usage, including code examples.</li>
 *     <li>TODO Notes on class dynamic behaviour e.g. is it thread-safe?</li>
 * </ul></p>
 * <p>
 * <h2>Responsibilities</h2></p>
 * <p>
 * <ul>
 *     <li>TODO Reponsibility #1.</li>
 *     <li>TODO Reponsibility #2.</li>
 *     <li>TODO Reponsibility #3.</li>
 *     <li>TODO Reponsibility #4.</li>
 * </ul></p>
 * <p>
 * <h2>Protected Interface</h2></p>
 * <p>
 * TODO Document the protected interface here, including the following:
 * <ul>
 *     <li>TODO Which aspects of the class's functionality can be extended.</li>
 *     <li>TODO How this extension is done, including usage examples.</li>
 * </ul></p>
 *
 * @see TODO Related Information
 *
 * @author linhdo
 *
 * @version $Id$
 **/
public class OrderHeld extends Order
{
    private String held_order;
    private String user_hold;
    private int place_price;
    private int modi_price;
    private Avg_mhz avgMhz;
    /**
     * @return the place_price
     */
    public int getPlace_price()
    {
        return place_price;
    }

    /**
     * @param placePrice the place_price to set
     */
    public void setPlace_price(int placePrice)
    {
        place_price = placePrice;
    }

    /**
     * @return the modi_price
     */
    public int getModi_price()
    {
        return modi_price;
    }

    /**
     * @param modiPrice the modi_price to set
     */
    public void setModi_price(int modiPrice)
    {
        modi_price = modiPrice;
    }

    /**
     * @return the user_hold
     */
    public String getUser_hold()
    {
        return user_hold;
    }

    /**
     * @param userHold the user_hold to set
     */
    public void setUser_hold(String userHold)
    {
        user_hold = userHold;
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
     * @return the avgMhz
     */
    public Avg_mhz getAvgMhz()
    {
        return avgMhz;
    }

    /**
     * @param avgMhz the avgMhz to set
     */
    public void setAvgMhz(Avg_mhz avgMhz)
    {
        this.avgMhz = avgMhz;
    }
    
}
