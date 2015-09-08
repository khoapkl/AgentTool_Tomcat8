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
public class EppReport implements Serializable
{

    private String description;
    private String shopper_id;
    private String createdDate;
    private String orderNumber;
    private String est_subtotal;
    private String volume_discount;
    private String dfs_discount;
    private String cor_discount;
    private String shipping_total;
    private String total_total;

    private String item_sku;
    private String name;
    private String product_list_price;

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    /**
     * table estore_epp_order
     */

    private String epp_id;

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
     * @return the createdDate
     */
    public String getCreatedDate()
    {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(String createdDate)
    {
        this.createdDate = createdDate;
    }

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
     * @return the est_subtotal
     */
    public String getEst_subtotal()
    {
        return est_subtotal;
    }

    /**
     * @param estSubtotal
     *            the est_subtotal to set
     */
    public void setEst_subtotal(String estSubtotal)
    {
        est_subtotal = estSubtotal;
    }

    /**
     * @return the volume_discount
     */
    public String getVolume_discount()
    {
        return volume_discount;
    }

    /**
     * @param volumeDiscount
     *            the volume_discount to set
     */
    public void setVolume_discount(String volumeDiscount)
    {
        volume_discount = volumeDiscount;
    }

    /**
     * @return the dfs_discount
     */
    public String getDfs_discount()
    {
        return dfs_discount;
    }

    /**
     * @param dfsDiscount
     *            the dfs_discount to set
     */
    public void setDfs_discount(String dfsDiscount)
    {
        dfs_discount = dfsDiscount;
    }

    /**
     * @return the cor_discount
     */
    public String getCor_discount()
    {
        return cor_discount;
    }

    /**
     * @param corDiscount
     *            the cor_discount to set
     */
    public void setCor_discount(String corDiscount)
    {
        cor_discount = corDiscount;
    }

    /**
     * @return the shipping_total
     */
    public String getShipping_total()
    {
        return shipping_total;
    }

    /**
     * @param shippingTotal
     *            the shipping_total to set
     */
    public void setShipping_total(String shippingTotal)
    {
        shipping_total = shippingTotal;
    }

    /**
     * @return the total_total
     */
    public String getTotal_total()
    {
        return total_total;
    }

    /**
     * @param totalTotal
     *            the total_total to set
     */
    public void setTotal_total(String totalTotal)
    {
        total_total = totalTotal;
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
     * @return the product_list_price
     */
    public String getProduct_list_price()
    {
        return product_list_price;
    }

    /**
     * @param productListPrice
     *            the product_list_price to set
     */
    public void setProduct_list_price(String productListPrice)
    {
        product_list_price = productListPrice;
    }

    /**
     * @return the serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }

}
