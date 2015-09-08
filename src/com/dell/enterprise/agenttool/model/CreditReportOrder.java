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
public class CreditReportOrder implements Serializable
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
    private String contact_name;
    private String ordernumber;
    private String item_sku;
    private String account;
    private String reason;
    private String credit_date;
    private String amount;
    private String cat2;
    private String cat1;
    private String sum_amount;
    private String sales_price;
    private String ship_to_state;
    private String mfg_part_number1;
    private String mfg_part_number2;
    private String cosmetic1;
    private String cosmetic2;
    private String short_description1;
    private String short_description2;
    private String ordered_date;
    private String tax_amt;
    private String rf_amt;
    private String ship_amt;
    private String discount_amt;
    private String total_amt;
    private String bill_email;
    private String ship_email;
    private String cc_type;
    private String total_item;
   
    /**
     * @return the sum_amount
     */
    public String getSum_amount()
    {
        return sum_amount;
    }
    /**
     * @param sumAmount the sum_amount to set
     */
    public void setSum_amount(String sumAmount)
    {
        sum_amount = sumAmount;
    }
    /**
     * @return the contact_name
     */
    public String getContact_name()
    {
        return contact_name;
    }
    /**
     * @param contactName the contact_name to set
     */
    public void setContact_name(String contactName)
    {
        contact_name = contactName;
    }
    /**
     * @return the ordernumber
     */
    public String getOrdernumber()
    {
        return ordernumber;
    }
    /**
     * @param ordernumber the ordernumber to set
     */
    public void setOrdernumber(String ordernumber)
    {
        this.ordernumber = ordernumber;
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
    public String getAccount()
    {
        return account;
    }
    /**
     * @param account the account to set
     */
    public void setAccount(String account)
    {
        this.account = account;
    }
    /**
     * @return the reason
     */
    public String getReason()
    {
        return reason;
    }
    /**
     * @param reason the reason to set
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }
    /**
     * @return the credit_date
     */
    public String getCredit_date()
    {
        return credit_date;
    }
    /**
     * @param creditDate the credit_date to set
     */
    public void setCredit_date(String creditDate)
    {
        credit_date = creditDate;
    }
    /**
     * @return the amount
     */
    public String getAmount()
    {
        return amount;
    }
    /**
     * @param amount the amount to set
     */
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    /**
     * @return the cat2
     */
    public String getCat2()
    {
        return cat2;
    }
    /**
     * @param cat2 the cat2 to set
     */
    public void setCat2(String cat2)
    {
        this.cat2 = cat2;
    }
    /**
     * @return the cat1
     */
    public String getCat1()
    {
        return cat1;
    }
    /**
     * @param cat1 the cat1 to set
     */
    public void setCat1(String cat1)
    {
        this.cat1 = cat1;
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
	public String getSales_price() {
		return sales_price;
	}
	public void setSales_price(String sales_price) {
		this.sales_price = sales_price;
	}
	public String getShip_to_state() {
		return ship_to_state;
	}
	public void setShip_to_state(String ship_to_state) {
		this.ship_to_state = ship_to_state;
	}
	
	public String getMfg_part_number1()
	{
		return mfg_part_number1;
	}
	
	public void setMfg_part_number1(String Mfg_part_number1)
	{
		this.mfg_part_number1 = Mfg_part_number1;
	}
	
	public String getMfg_part_number2()
	{
		return mfg_part_number2;
	}
	
	public void setMfg_part_number2(String Mfg_part_number2)
	{
		this.mfg_part_number2 = Mfg_part_number2;
	}
	
	public String getCosmetic1()
	{
		return cosmetic1;
	}
	public void setCosmetic1(String Cosmetic1)
	{
		this.cosmetic1 = Cosmetic1;
	}
	
	public String getCosmetic2()
	{
		return cosmetic2;
	}
	public void setCosmetic2(String Cosmetic2)
	{
		this.cosmetic1 = Cosmetic2;
	}
	
	public String getShort_description1()
	{
		return short_description1;
	}
	public void setShort_description1(String Short_description1)
	{
		this.short_description1 = Short_description1;
	}
	
	public String getShort_description2()
	{
		return short_description2;
	}
	public void setShort_description2(String Short_description2)
	{
		this.short_description2 = Short_description2;
	}
	
	public String getOrdered_date()
	{
		return ordered_date;
	}
	public void setOrdered_date(String Ordered_date)
	{
		this.ordered_date = Ordered_date;
	}
	
	public String getTax_amt() {
		return tax_amt;
	}
	public void setTax_amt(String Tax_amt){
		this.tax_amt = Tax_amt;
	}
	
	public String getRF_amt(){
		return rf_amt;
	}
	public void setRF_amt(String RF_amt){
		this.rf_amt = RF_amt;
	}
	
	public String getShip_amt(){
		return ship_amt;
	}
	public void setShip_amt(String Ship_amt){
		this.ship_amt = Ship_amt;
	}
	
	public String getDiscount_amt(){
		return discount_amt;
	}
	public void setDiscount_amt(String Discount_amt){
		this.discount_amt = Discount_amt;
	}
	
	public String getTotal_amt(){
		return total_amt;
	}
	public void setTotal_amt(String Total_amt){
		this.total_amt = Total_amt;
	}
	
	public String getBill_email(){
		return bill_email;
	}
	public void setBill_email(String Bill_email){
		this.bill_email = Bill_email;
	}
	
	public String getShip_email(){
		return ship_email;
	}
	public void setShip_email(String Ship_email){
		this.ship_email = Ship_email;
	}
	
	public String getCC_type(){
		return cc_type;
	}
	public void setCC_type(String CC_type){
		this.cc_type = CC_type;
	}
	
	public String getTotal_item(){
		return total_item;
	}
	public void setTotal_item(String Total_item){
		this.total_item = Total_item;
	}
	
}
