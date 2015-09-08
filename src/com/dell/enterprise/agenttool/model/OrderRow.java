/*
 * FILENAME
 *     OrderRow.java
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
import java.util.Date;

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
 * @author vinhhq
 * 
 * @version $Id$
 **/
public class OrderRow implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private String shopper_id;
    private String order_id;
    private Date created;
    private Date modified;
    private String ship_method;
    private String cc_name;
    private String cc_number;
    private String cc_type;
    private String cc_expmonth;
    private String cc_expyear;
    private String epp_id;
    private String ship_terms;
    private Float ship_cost;
    private int discount_type;
    private Float discount_value;

    /**
     * @return the shopper_id
     */
    public String getShopper_id()
    {
        return shopper_id;
    }

    /**
     * @param shopperId
     *            the shopper_id to set
     */
    public void setShopper_id(String shopperId)
    {
        shopper_id = shopperId;
    }

    /**
     * @return the order_id
     */
    public String getOrder_id()
    {
        return order_id;
    }

    /**
     * @param orderId
     *            the order_id to set
     */
    public void setOrder_id(String orderId)
    {
        order_id = orderId;
    }

    /**
     * @return the created
     */
    public Date getCreated()
    {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(Date created)
    {
        this.created = created;
    }

    /**
     * @return the modified
     */
    public Date getModified()
    {
        return modified;
    }

    /**
     * @param modified
     *            the modified to set
     */
    public void setModified(Date modified)
    {
        this.modified = modified;
    }

    /**
     * @return the ship_method
     */
    public String getShip_method()
    {
        return ship_method;
    }

    /**
     * @param shipMethod
     *            the ship_method to set
     */
    public void setShip_method(String shipMethod)
    {
        ship_method = shipMethod;
    }

    /**
     * @return the cc_name
     */
    public String getCc_name()
    {
        return cc_name;
    }

    /**
     * @param ccName
     *            the cc_name to set
     */
    public void setCc_name(String ccName)
    {
        cc_name = ccName;
    }

    /**
     * @return the cc_number
     */
    public String getCc_number()
    {
        return cc_number;
    }

    /**
     * @param ccNumber
     *            the cc_number to set
     */
    public void setCc_number(String ccNumber)
    {
        cc_number = ccNumber;
    }

    /**
     * @return the cc_type
     */
    public String getCc_type()
    {
        return cc_type;
    }

    /**
     * @param ccType
     *            the cc_type to set
     */
    public void setCc_type(String ccType)
    {
        cc_type = ccType;
    }

    /**
     * @return the cc_expmonth
     */
    public String getCc_expmonth()
    {
        return cc_expmonth;
    }

    /**
     * @param ccExpmonth
     *            the cc_expmonth to set
     */
    public void setCc_expmonth(String ccExpmonth)
    {
        cc_expmonth = ccExpmonth;
    }

    /**
     * @return the cc_expyear
     */
    public String getCc_expyear()
    {
        return cc_expyear;
    }

    /**
     * @param ccExpyear
     *            the cc_expyear to set
     */
    public void setCc_expyear(String ccExpyear)
    {
        cc_expyear = ccExpyear;
    }

    /**
     * @return the epp_id
     */
    public String getEpp_id()
    {
        return epp_id;
    }

    /**
     * @param eppId
     *            the epp_id to set
     */
    public void setEpp_id(String eppId)
    {
        epp_id = eppId;
    }

    /**
     * @return the ship_terms
     */
    public String getShip_terms()
    {
        return ship_terms;
    }

    /**
     * @param shipTerms
     *            the ship_terms to set
     */
    public void setShip_terms(String shipTerms)
    {
        ship_terms = shipTerms;
    }

    /**
     * @return the ship_cost
     */
    public Float getShip_cost()
    {
        return ship_cost;
    }

    /**
     * @param shipCost
     *            the ship_cost to set
     */
    public void setShip_cost(Float shipCost)
    {
        ship_cost = shipCost;
    }
    
    public int getDiscount_type()
    {
        return discount_type;
    }
    
    public void setDiscount_type(int discountType)
    {
        discount_type = discountType;
    }
    
    public Float getDiscount_value()
    {
        return discount_value;
    }
    
    public void setDiscount_value(Float discountValue)
    {
    	discount_value = discountValue;
    }
}
