/*
 * FILENAME
 *     OrderLine.java
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
import java.sql.Timestamp;

// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrderLine implements Serializable
{

    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private String orderNumber;
    private String shopper_id;
    private Timestamp created;
    private Timestamp modified;
    private int item_id;
    private String item_sku;
    private String description;
    private BigDecimal weight;
    private int quantity;
    private int qtyPicked;
    private int qtyShipped;
    private BigDecimal product_list_price;
    private BigDecimal iadjust_regularprice;
    private BigDecimal iadjust_currentprice;
    private BigDecimal oadjust_adjustedprice;
    private BigDecimal discounted_price;
    private String cosmetic_grade;
    private int category_id;

    /**
     * @return the orderNumber
     */
    public String getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * @param orderNumber
     *            the orderNumber to set
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
     * @param shopperId
     *            the shopper_id to set
     */
    public void setShopper_id(String shopperId)
    {
        shopper_id = shopperId;
    }

    /**
     * @return the created
     */
    public Timestamp getCreated()
    {
        return created;
    }

    /**
     * @param created
     *            the created to set
     */
    public void setCreated(Timestamp created)
    {
        this.created = created;
    }

    /**
     * @return the modified
     */
    public Timestamp getModified()
    {
        return modified;
    }

    /**
     * @param modified
     *            the modified to set
     */
    public void setModified(Timestamp modified)
    {
        this.modified = modified;
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
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the weight
     */
    public BigDecimal getWeight()
    {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(BigDecimal weight)
    {
        this.weight = weight;
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
     * @return the qtyPicked
     */
    public int getQtyPicked()
    {
        return qtyPicked;
    }

    /**
     * @param qtyPicked
     *            the qtyPicked to set
     */
    public void setQtyPicked(int qtyPicked)
    {
        this.qtyPicked = qtyPicked;
    }

    /**
     * @return the qtyShipped
     */
    public int getQtyShipped()
    {
        return qtyShipped;
    }

    /**
     * @param qtyShipped
     *            the qtyShipped to set
     */
    public void setQtyShipped(int qtyShipped)
    {
        this.qtyShipped = qtyShipped;
    }

    /**
     * @return the product_list_price
     */
    public BigDecimal getProduct_list_price()
    {
        return product_list_price;
    }

    /**
     * @param productListPrice
     *            the product_list_price to set
     */
    public void setProduct_list_price(BigDecimal productListPrice)
    {
        product_list_price = productListPrice;
    }

    /**
     * @return the iadjust_regularprice
     */
    public BigDecimal getIadjust_regularprice()
    {
        return iadjust_regularprice;
    }

    /**
     * @param iadjustRegularprice
     *            the iadjust_regularprice to set
     */
    public void setIadjust_regularprice(BigDecimal iadjustRegularprice)
    {
        iadjust_regularprice = iadjustRegularprice;
    }

    /**
     * @return the iadjust_currentprice
     */
    public BigDecimal getIadjust_currentprice()
    {
        return iadjust_currentprice;
    }

    /**
     * @param iadjustCurrentprice
     *            the iadjust_currentprice to set
     */
    public void setIadjust_currentprice(BigDecimal iadjustCurrentprice)
    {
        iadjust_currentprice = iadjustCurrentprice;
    }

    /**
     * @return the oadjust_adjustedprice
     */
    public BigDecimal getOadjust_adjustedprice()
    {
        return oadjust_adjustedprice;
    }

    /**
     * @param oadjustAdjustedprice
     *            the oadjust_adjustedprice to set
     */
    public void setOadjust_adjustedprice(BigDecimal oadjustAdjustedprice)
    {
        oadjust_adjustedprice = oadjustAdjustedprice;
    }

    /**
     * @return the discounted_price
     */
    public BigDecimal getDiscounted_price()
    {
        return discounted_price;
    }

    /**
     * @param discountedPrice
     *            the discounted_price to set
     */
    public void setDiscounted_price(BigDecimal discountedPrice)
    {
        discounted_price = discountedPrice;
    }

	public String getCosmetic_grade() {
		return cosmetic_grade;
	}

	public void setCosmetic_grade(String cosmetic_grade) {
		this.cosmetic_grade = cosmetic_grade;
	}

	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

}
