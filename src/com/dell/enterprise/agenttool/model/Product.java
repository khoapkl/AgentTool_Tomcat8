package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.util.Map;

public class Product implements Serializable
{

    /**
     * <p>
     * TODO Describe what this data member models and how it's used.</p>
     **/
    
    private static final long serialVersionUID = 1L;
    private String item_sku;
    private int category_id;
    private String mfg_part_number;
    private String short_description ;
    private String name;
    private String status;
    private int item_age;
    private Float list_price;
    private String shopper_id;
    private String order_id;
    private String flagtype;
    private Map<String, String> attributes;
    private int weight;
    private int idx;
    private String cosmeticgrade; 
    
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
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }
    /**
     * @return the item_age
     */
    public int getItem_age()
    {
        return item_age;
    }
    /**
     * @param itemAge the item_age to set
     */
    public void setItem_age(int itemAge)
    {
        item_age = itemAge;
    }
    /**
     * @return the list_price
     */
    public Float getList_price()
    {
        return list_price;
    }
    /**
     * @param listPrice the list_price to set
     */
    public void setList_price(Float listPrice)
    {
        list_price = listPrice;
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
     * @return the flagtype
     */
    public String getFlagtype()
    {
        return flagtype;
    }
    /**
     * @param flagtype the flagtype to set
     */
    public void setFlagtype(String flagtype)
    {
        this.flagtype = flagtype;
    }
    /**
     * @return the attributes
     */
    public Map<String, String> getAttributes()
    {
        return attributes;
    }
    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, String> attributes)
    {
        this.attributes = attributes;
    }
    /**
     * @return the mfg_part_number
     */
    public String getMfg_part_number()
    {
        return mfg_part_number;
    }
    /**
     * @param mfgPartNumber the mfg_part_number to set
     */
    public void setMfg_part_number(String mfgPartNumber)
    {
        mfg_part_number = mfgPartNumber;
    }
    /**
     * @return the short_description
     */
    public String getShort_description()
    {
        return short_description;
    }
    /**
     * @param shortDescription the short_description to set
     */
    public void setShort_description(String shortDescription)
    {
        short_description = shortDescription;
    }
    /**
     * @return the weight
     */
    public int getWeight()
    {
        return weight;
    }
    /**
     * @param weight the weight to set
     */
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
    /**
     * @return the idx
     */
    public int getIdx()
    {
        return idx;
    }
    /**
     * @param idx the idx to set
     */
    public void setIdx(int idx)
    {
        this.idx = idx;
    }
	public String getCosmeticgrade() {
		return cosmeticgrade;
	}
	public void setCosmeticgrade(String cosmeticgrade) {
		this.cosmeticgrade = cosmeticgrade;
	}

}
