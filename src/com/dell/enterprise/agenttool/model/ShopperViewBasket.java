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
import java.util.List;


/**
 * @author thuynguyen
 **/
@SuppressWarnings("serial")
public class ShopperViewBasket implements Serializable
{
    
    private String item_sku;
    private String name;
    private int item_id;
    private float placed_price;
    private int category_id;
    private String attribute05;
    private String attribute06;
    private String attribute18;
    private String attribute09;
    private Float speed;
    private String heldOrder;
    private String shopper_id;
    private String order_id;
    private String expirationdate;
    private String userHold;
    private List<ShopperViewBasket> listShopperViewBasket;
    
        
    /**
     * @return the userHold
     */
    public String getUserHold()
    {
        return userHold;
    }
    /**
     * @param userHold the userHold to set
     */
    public void setUserHold(String userHold)
    {
        this.userHold = userHold;
    }
    /**
     * @return the expirationdate
     */
    public String getExpirationdate()
    {
        return expirationdate;
    }
    /**
     * @param expirationdate the expirationdate to set
     */
    public void setExpirationdate(String expirationdate)
    {
        this.expirationdate = expirationdate;
    }
    /**
     * @return the listShopperViewBasket
     */
    public List<ShopperViewBasket> getListShopperViewBasket()
    {
        return listShopperViewBasket;
    }
    /**
     * @param listShopperViewBasket the listShopperViewBasket to set
     */
    public void setListShopperViewBasket(List<ShopperViewBasket> listShopperViewBasket)
    {
        this.listShopperViewBasket = listShopperViewBasket;
    }
    /**
     * @return the order_id
     */
    public String getOrder_id()
    {
        return order_id;
    }
    /**
     * @param orderId the order_id to set
     */
    public void setOrder_id(String orderId)
    {
        order_id = orderId;
    }
    /**
     * @return the heldOrder
     */
    public String getHeldOrder()
    {
        return heldOrder;
    }
    /**
     * @param heldOrder the heldOrder to set
     */
    public void setHeldOrder(String heldOrder)
    {
        this.heldOrder = heldOrder;
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
     * @return the speed
     */
    public Float getSpeed()
    {
        return speed;
    }
    /**
     * @param speed the speed to set
     */
    public void setSpeed(Float speed)
    {
        this.speed = speed;
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
     * @return the name
     */
    public String getName()
    {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * @return the item_id
     */
    public int getItem_id()
    {
        return item_id;
    }
    /**
     * @param itemId the item_id to set
     */
    public void setItem_id(int itemId)
    {
        item_id = itemId;
    }
    /**
     * @return the placed_price
     */
    public float getPlaced_price()
    {
        return placed_price;
    }
    /**
     * @param placedPrice the placed_price to set
     */
    public void setPlaced_price(float placedPrice)
    {
        placed_price = placedPrice;
    }
    /**
     * @return the category_id
     */
    public int getCategory_id()
    {
        return category_id;
    }
    /**
     * @param categoryId the category_id to set
     */
    public void setCategory_id(int categoryId)
    {
        category_id = categoryId;
    }
    /**
     * @return the attribute05
     */
    public String getAttribute05()
    {
        return attribute05;
    }
    /**
     * @param attribute05 the attribute05 to set
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
     * @param attribute06 the attribute06 to set
     */
    public void setAttribute06(String attribute06)
    {
        this.attribute06 = attribute06;
    }
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
	public String getAttribute09() {
		return attribute09;
	}
	public void setAttribute09(String attribute09) {
		this.attribute09 = attribute09;
	}
 
   
    

    
    
}
