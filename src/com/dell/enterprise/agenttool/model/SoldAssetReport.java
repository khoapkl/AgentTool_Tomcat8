/*
 * FILENAME
 *     Desktop.java
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
import java.sql.Time;
import java.util.Date;
import java.util.Map;

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
 * @author thuynguyen
 * 
 * @version $Id$
 **/
public class SoldAssetReport implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.</p>
     **/
    
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private Long id;
    private String exported_date;
    private String item_sku;
    private String item_status;
    private String item_status_date;
    private String order_number;
    private String order_date;
    private String order_total;
    private String tax_total;
    private String ship_total;
    private String sold_price;
    private String modified_price;
    private String category_id;
    private String description;
   
    /**
     * @return the sum_amount
     */
    public String getExported_date()
    {
        return exported_date;
    }
    /**
     * @param sumAmount the sum_amount to set
     */
    public void setExported_date(String exporteddate)
    {
    	exported_date = exporteddate;
    }
    /**
     * @return the contact_name
     */
    public String getItem_status()
    {
        return item_status;
    }
    /**
     * @param contactName the contact_name to set
     */
    public void setItem_status(String itemstatus)
    {
    	item_status = itemstatus;
    }
    /**
     * @return the ordernumber
     */
    public String getItem_status_date()
    {
        return item_status_date;
    }
    /**
     * @param ordernumber the ordernumber to set
     */
    public void setItem_status_date(String itemstatusdate)
    {
        this.item_status_date = itemstatusdate;
    }
    /**
     * @return the item_sku
     */
    public String getItem_sku()
    {
        return item_sku;
    }
    /**
     * @param itemSku the item_sku to set
     */
    public void setItem_sku(String itemSku)
    {
        item_sku = itemSku;
    }
    /**
     * @return the account
     */
    public String getOrder_number()
    {
        return order_number;
    }
    /**
     * @param account the account to set
     */
    public void setOrder_number(String ordernumber)
    {
        this.order_number = ordernumber;
    }
    /**
     * @return the reason
     */
    public String getOrder_date()
    {
        return order_date;
    }
    /**
     * @param reason the reason to set
     */
    public void setOrder_date(String orderdate)
    {
        this.order_date = orderdate;
    }
    /**
     * @return the credit_date
     */
    public String getOrder_total()
    {
        return order_total;
    }
    /**
     * @param creditDate the credit_date to set
     */
    public void setOrder_total(String ordertotal)
    {
        order_total = ordertotal;
    }
    /**
     * @return the amount
     */
    public String getTax_total()
    {
        return tax_total;
    }
    /**
     * @param amount the amount to set
     */
    public void setTax_total(String taxtotal)
    {
        this.tax_total = taxtotal;
    }
    /**
     * @return the cat2
     */
    public String getShip_total()
    {
        return ship_total;
    }
    /**
     * @param cat2 the cat2 to set
     */
    public void setShip_total(String shiptotal)
    {
        this.ship_total = shiptotal;
    }
    /**
     * @return the cat1
     */
    public String getSold_price()
    {
        return sold_price;
    }
    /**
     * @param cat1 the cat1 to set
     */
    public void setSold_price(String soldprice)
    {
        this.sold_price = soldprice;
    }
    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    /**
     * @param id the id to set
     */
    public void setId(Long id)
    {
        this.id = id;
    }
    /**
     * @return the id
     */
    public Long getId()
    {
        return id;
    }
    
    
    
	public String getModified_price() {
		return modified_price;
	}
	public void setModified_price(String modifiedprice) {
		this.modified_price = modifiedprice;
	}
	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String categoryid) {
		this.category_id = categoryid;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String itemDescription) {
		this.description = itemDescription;
	}
}
