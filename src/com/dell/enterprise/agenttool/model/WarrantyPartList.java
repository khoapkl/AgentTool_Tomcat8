/*
 * FILENAME
 *     EstoreBasketItem.java
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
public class WarrantyPartList
{
    
    private String item_sku;
    private String name;
    private String mfg_sku;
    private Float list_price;
    private int category_id;
    

    /**
     * @return the item_sku
     */
    public String getItem_sku()
    {
        return item_sku;
    }

    /**
     * @param itemSku
     *            the item_sku to set
     */
    public void setItem_sku(String itemSku)
    {
        item_sku = itemSku;
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
     * @return the short_description
     */
    public String getMfg_sku()
    {
        return mfg_sku;
    }

    /**
     * @param shortDescription
     *            the short_description to set
     */
    public void setMfg_sku(String mfgsku)
    {
        mfg_sku = mfgsku;
    }
    
    /**
     * @return the list_price
     */
    public Float getList_price()
    {
        return list_price;
    }

    /**
     * @param listPrice
     *            the list_price to set
     */
    public void setList_price(Float listPrice)
    {
        list_price = listPrice;
    }

    /**
     * @return the category_id
     */
    public int getCategory_id()
    {
        return category_id;
    }

    /**
     * @param categoryId
     *            the category_id to set
     */
    public void setCategory_id(int categoryId)
    {
        category_id = categoryId;
    }
}
