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
 * @author thanhphan
 * 
 * @version $Id$
 **/
public class SummaryInventoryReport implements Serializable
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
    private int category_id;
    private String mfg_part_number;
    private String short_description;
    private int item_count;
    private int list_price;
    private int list_price40;
    private int list_price50;
    private int list_price60;
    private String item_status;
   
    
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
    
    /**
     * @return the sum_amount
     */
    public int getcategory_id()
    {
        return category_id;
    }
    /**
     * @param sumAmount the sum_amount to set
     */
    public void setcategory_id(int category_ID)
    {
    	category_id = category_ID;
    }
    /**
     * @return the contact_name
     */
    public String getmfg_part_number()
    {
        return mfg_part_number;
    }
    /**
     * @param contactName the contact_name to set
     */
    public void setmfg_part_number(String Mfg_Part_Number)
    {
    	mfg_part_number = Mfg_Part_Number;
    }
    /**
     * @return the ordernumber
     */
    public String getshort_description()
    {
        return short_description;
    }
    /**
     * @param ordernumber the ordernumber to set
     */
    public void setshort_description(String Short_Description)
    {
        this.short_description = Short_Description;
    }
    /**
     * @return the item_sku
     */
    
    public int getitem_count()
    {
        return item_count;
    }
    /**
     * @param itemSku the item_sku to set
     */
    public void setitem_count(int Item_Count)
    {
        item_count = Item_Count;
    }
    /**
     * @return the account
     */
    
    public int getList_price()
    {
        return list_price;
    }
    public void setList_price(int List_price)
    {
    	list_price = List_price;
    }
    
    public int getList_price40()
    {
        return list_price40;
    }
    public void setList_price40(int List_price40)
    {
    	list_price40 = List_price40;
    }
    
    public int getList_price50()
    {
        return list_price50;
    }
    public void setList_price50(int List_price50)
    {
    	list_price50 = List_price50;
    }
    
    public int getList_price60()
    {
        return list_price60;
    }
    public void setList_price60(int List_price60)
    {
    	list_price60 = List_price60;
    }
    
    public String getitem_status()
    {
        return item_status;
    }
    /**
     * @param account the account to set
     */
    public void setitem_status(String Item_Status)
    {
        this.item_status = Item_Status;
    }
    /**
 
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
}
