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
public class EstoreBasketItem
{
    private String mfg_part_number;
    private String item_sku;
    private int quantity;
    private int item_id;
    private String name;
    private String short_description;
    private Float list_price;
    private Float placed_price;
    private int weight;
    private String attribute05;
    private String attribute06;
    private String attribute18;    
    private int category_id;
    private Float speed;
    private Float screenSize;
    private String attribute12;
    private String attribute09;
    private String attribute10;
    private String attribute13;
    private String image_url;
    

    /**
     * @return the attribute18
     */
    public String getAttribute18()
    {
        return attribute18;
    }

    /**
     * @param attribute18 the attribute18 to set
     */
    public void setAttribute18(String attribute18)
    {
        this.attribute18 = attribute18;
    }

    /**
     * @return the mfg_part_number
     */
    public String getMfg_part_number()
    {
        return mfg_part_number;
    }

    /**
     * @param mfgPartNumber
     *            the mfg_part_number to set
     */
    public void setMfg_part_number(String mfgPartNumber)
    {
        mfg_part_number = mfgPartNumber;
    }

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
     * @return the quantity
     */
    public int getQuantity()
    {
        return quantity;
    }

    /**
     * @param quantity
     *            the quantity to set
     */
    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    /**
     * @return the item_id
     */
    public int getItem_id()
    {
        return item_id;
    }

    /**
     * @param itemId
     *            the item_id to set
     */
    public void setItem_id(int itemId)
    {
        item_id = itemId;
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
    public String getShort_description()
    {
        return short_description;
    }

    /**
     * @param shortDescription
     *            the short_description to set
     */
    public void setShort_description(String shortDescription)
    {
        short_description = shortDescription;
    }

    /**
     * @return the placed_price
     */
    public Float getPlaced_price()
    {
        return placed_price;
    }

    /**
     * @param placedPrice
     *            the placed_price to set
     */
    public void setPlaced_price(Float placedPrice)
    {
        placed_price = placedPrice;
    }

    /**
     * @return the weight
     */
    public int getWeight()
    {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(int weight)
    {
        this.weight = weight;
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
     * @return the attribute05
     */
    public String getAttribute05()
    {
        return attribute05;
    }

    /**
     * @param attribute05
     *            the attribute05 to set
     */
    public void setAttribute05(String attribute05)
    {
        this.attribute05 = attribute05;
    }

    /**
     * @return the attribute06
     */
    public String getAttribute06()
    {
        return attribute06;
    }

    /**
     * @param attribute06
     *            the attribute06 to set
     */
    public void setAttribute06(String attribute06)
    {
        this.attribute06 = attribute06;
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

    /**
     * @return the speed
     */
    public Float getSpeed()
    {
        return speed;
    }

    /**
     * @param speed
     *            the speed to set
     */
    public void setSpeed(Float speed)
    {
        this.speed = speed;
    }

	public void setScreenSize(Float screenSize) {
		this.screenSize = screenSize;
	}

	public Float getScreenSize() {
		return screenSize;
	}

	public String getAttribute12() {
		return attribute12;
	}

	public void setAttribute12(String attribute12) {
		this.attribute12 = attribute12;
	}

	public String getAttribute09() {
		return attribute09;
	}

	public void setAttribute09(String attribute09) {
		this.attribute09 = attribute09;
	}

	public String getAttribute10() {
		return attribute10;
	}

	public void setAttribute10(String attribute10) {
		this.attribute10 = attribute10;
	}

	public String getAttribute13() {
		return attribute13;
	}

	public void setAttribute13(String attribute13) {
		this.attribute13 = attribute13;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
}
