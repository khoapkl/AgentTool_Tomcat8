/*
 * FILENAME
 *     Shopper.java
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


/**
 * @author thuynguyen
 **/
@SuppressWarnings("serial")
public class ShopperViewReceipts implements Serializable
{
    
    private String ship_to_fname;
    private String ship_to_lname;
    private String shopper_number;
    private String createdDate;
    private String total_total;
    private String shopper_id;
    private String orderNumber;
    private int rowNumber;
    private int agent_id;
   
    
    
    public int getAgent_id() {
		return agent_id;
	}
	public void setAgent_id(int agentId) {
		agent_id = agentId;
	}
	/**
     * @return the rowNumber
     */
    public int getRowNumber()
    {
        return rowNumber;
    }
    /**
     * @param rowNumber the rowNumber to set
     */
    public void setRowNumber(int rowNumber)
    {
        this.rowNumber = rowNumber;
    }
    /**
     * @return the orderNumber
     */
    public String getOrderNumber()
    {
        return orderNumber;
    }
    /**
     * @param orderNumber the orderNumber to set
     */
    public void setOrderNumber(String orderNumber)
    {
        this.orderNumber = orderNumber;
    }
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
     * @return the ship_to_fname
     */
    public String getShip_to_fname()
    {
        return ship_to_fname;
    }
    /**
     * @param shipToFname the ship_to_fname to set
     */
    public void setShip_to_fname(String shipToFname)
    {
        ship_to_fname = shipToFname;
    }
    /**
     * @return the ship_to_lname
     */
    public String getShip_to_lname()
    {
        return ship_to_lname;
    }
    /**
     * @param shipToLname the ship_to_lname to set
     */
    public void setShip_to_lname(String shipToLname)
    {
        ship_to_lname = shipToLname;
    }
    /**
     * @return the shopper_number
     */
    public String getShopper_number()
    {
        return shopper_number;
    }
    /**
     * @param shopperNumber the shopper_number to set
     */
    public void setShopper_number(String shopperNumber)
    {
        shopper_number = shopperNumber;
    }
    /**
     * @return the createdDate
     */
    public String getCreatedDate()
    {
        return createdDate;
    }
    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }
    /**
     * @return the total_total
     */
    public String getTotal_total()
    {
        return total_total;
    }
    /**
     * @param totalTotal the total_total to set
     */
    public void setTotal_total(String totalTotal)
    {
        total_total = totalTotal;
    }
    
    
    
}
