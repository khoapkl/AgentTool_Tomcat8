/*
 * FILENAME
 *     OrderCriteria.java
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
public class OrderCriteria implements Serializable
{
    private String orderId;
    private String orderType;
    private String itemSku;
    private String customerId;
    private String sfname;
    private String slname;
    private String scom;
    private String sphone;
    private String bfname;
    private String blname;
    private String bcom;
    private String bphone;
    /**
     * @return the orderId
     */
    public String getOrderId()
    {
        return orderId;
    }
    /**
     * @param orderId the orderId to set
     */
    public void setOrderId(final String orderId)
    {
        this.orderId = orderId;
    }
    /**
     * @return the orderType
     */
    public String getOrderType()
    {
        return orderType;
    }
    /**
     * @param orderType the orderType to set
     */
    public void setOrderType(final String orderType)
    {
        this.orderType = orderType;
    }
    /**
     * @return the itemSku
     */
    public String getItemSku()
    {
        return itemSku;
    }
    /**
     * @param itemSku the itemSku to set
     */
    public void setItemSku(final String itemSku)
    {
        this.itemSku = itemSku;
    }
    /**
     * @return the customerId
     */
    public String getCustomerId()
    {
        return customerId;
    }
    /**
     * @param customerId the customerId to set
     */
    public void setCustomerId(final String customerId)
    {
        this.customerId = customerId;
    }
    /**
     * @return the ship_to_fname
     */
    /**
     * @return the sfname
     */
    public String getSfname()
    {
        return sfname;
    }
    /**
     * @param sfname the sfname to set
     */
    public void setSfname(final String sfname)
    {
        this.sfname = sfname;
    }
    /**
     * @return the slname
     */
    public String getSlname()
    {
        return slname;
    }
    /**
     * @param slname the slname to set
     */
    public void setSlname(final String slname)
    {
        this.slname = slname;
    }
    /**
     * @return the scom
     */
    public String getScom()
    {
        return scom;
    }
    /**
     * @param scom the scom to set
     */
    public void setScom(final String scom)
    {
        this.scom = scom;
    }
    /**
     * @return the sphone
     */
    public String getSphone()
    {
        return sphone;
    }
    /**
     * @param sphone the sphone to set
     */
    public void setSphone(final String sphone)
    {
        this.sphone = sphone;
    }
    /**
     * @return the bfname
     */
    public String getBfname()
    {
        return bfname;
    }
    /**
     * @param bfname the bfname to set
     */
    public void setBfname(final String bfname)
    {
        this.bfname = bfname;
    }
    /**
     * @return the blname
     */
    public String getBlname()
    {
        return blname;
    }
    /**
     * @param blname the blname to set
     */
    public void setBlname(final String blname)
    {
        this.blname = blname;
    }
    /**
     * @return the bcom
     */
    public String getBcom()
    {
        return bcom;
    }
    /**
     * @param bcom the bcom to set
     */
    public void setBcom(final String bcom)
    {
        this.bcom = bcom;
    }
    /**
     * @return the bphone
     */
    public String getBphone()
    {
        return bphone;
    }
    /**
     * @param bphone the bphone to set
     */
    public void setBphone(final String bphone)
    {
        this.bphone = bphone;
    }
   
    
}
