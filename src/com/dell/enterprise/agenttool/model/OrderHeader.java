/*
 * FILENAME
 *     OrderHeader.java
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
public class OrderHeader implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private String OrderNumber;
    private String OrderBarcode;
    private String shopper_id;
    private String BillingGrpID;
    private String PurchOrdNum;
    private String ApprovalNumber;
    private String Program;
    private String DataSource;
    private String ship_to_name;
    private String ship_to_title;
    private String ship_to_company;
    private String ship_to_address1;
    private String ship_to_address2;
    private String ship_to_address3;
    private String ship_to_city;
    private String ship_to_state;
    private String ship_to_postal;
    private String ship_to_county;
    private String ship_to_country;
    private String ship_to_fax;
    private String ship_to_phone;
    private String ship_to_phoneext;
    private String ship_to_email;
    private String ship_method;
    private String ship_terms;
    private String bill_to_name;
    private String bill_to_company;
    private String bill_to_address1;
    private String bill_to_address2;
    private String bill_to_city;
    private String bill_to_state;
    private String bill_to_postal;
    private String bill_to_country;
    private String bill_to_fax;
    private String bill_to_phone;
    private String bill_to_phoneext;
    private String bill_to_email;
    private BigDecimal oadjust_subtotal;
    private BigDecimal discount_total;
    private BigDecimal handling_total;
    private int tax_exempt;
    private String tax_exempt_number;
    private Timestamp tax_exempt_expire;
    private BigDecimal tax_total;
    private BigDecimal tax_included;
    private BigDecimal total_total;
    private BigDecimal warranty_total;
    
    private Float weight;

    private String payment_terms;
    private String cc_name;
    private String cc_number;
    private String cc_type;
    private int cc_expmonth;
    private int cc_expyear;
    private String tracking_number;
    private String orderStatus;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private Boolean Taxable;
    private String TaxExemptNum;
    private String TermCode;

    private Timestamp DateRequested;
    private Timestamp DatePrinted;
    private Timestamp DateCompleted;
    private Timestamp DateInvoiced;
    private Timestamp DateReturned;
    private BigDecimal shipping_total;
    private Timestamp rf_date;
    private BigDecimal freightTax;
    private float statetaxperc;
    private float countytaxperc;
    private float countytrantaxperc;
    private float citytaxperc;
    private float citytrantaxperc;
    private int agentIDEnter;  
    private String EditAgent;
    private String user_name;
    private String ip_address;
    private BigDecimal total_disc;   
    private BigDecimal total_list;   
    private String listingtype;
    private String avs_address;
    private String avs_zip;
    private String avs_country;
    private String venuetype;
    private Timestamp updateddate;
    private String cc_cvv2;
    private Boolean byAgent;    
    /**
     * @return the agentIDEnter
     */
    public int getAgentIDEnter()
    {
        return agentIDEnter;
    }

    /**
     * @param agentIDEnter the agentIDEnter to set
     */
    public void setAgentIDEnter(int agentIDEnter)
    {
        this.agentIDEnter = agentIDEnter;
    }

    /**
     * @return the editAgent
     */
    public String getEditAgent()
    {
        return EditAgent;
    }

    /**
     * @param editAgent the editAgent to set
     */
    public void setEditAgent(String editAgent)
    {
        EditAgent = editAgent;
    }

    /**
     * @return the user_name
     */
    public String getUser_name()
    {
        return user_name;
    }

    /**
     * @param userName the user_name to set
     */
    public void setUser_name(String userName)
    {
        user_name = userName;
    }

    /**
     * @return the ip_address
     */
    public String getIp_address()
    {
        return ip_address;
    }

    /**
     * @param ipAddress the ip_address to set
     */
    public void setIp_address(String ipAddress)
    {
        ip_address = ipAddress;
    }

    /**
     * @return the total_disc
     */
    public BigDecimal getTotal_disc()
    {
        return total_disc;
    }

    /**
     * @param totalDisc the total_disc to set
     */
    public void setTotal_disc(BigDecimal totalDisc)
    {
        total_disc = totalDisc;
    }

    /**
     * @return the total_list
     */
    public BigDecimal getTotal_list()
    {
        return total_list;
    }

    /**
     * @param totalList the total_list to set
     */
    public void setTotal_list(BigDecimal totalList)
    {
        total_list = totalList;
    }

    /**
     * @return the listingtype
     */
    public String getListingtype()
    {
        return listingtype;
    }

    /**
     * @param listingtype the listingtype to set
     */
    public void setListingtype(String listingtype)
    {
        this.listingtype = listingtype;
    }

    /**
     * @return the avs_address
     */
    public String getAvs_address()
    {
        return avs_address;
    }

    /**
     * @param avsAddress the avs_address to set
     */
    public void setAvs_address(String avsAddress)
    {
        avs_address = avsAddress;
    }

    /**
     * @return the avs_zip
     */
    public String getAvs_zip()
    {
        return avs_zip;
    }

    /**
     * @param avsZip the avs_zip to set
     */
    public void setAvs_zip(String avsZip)
    {
        avs_zip = avsZip;
    }

    /**
     * @return the avs_country
     */
    public String getAvs_country()
    {
        return avs_country;
    }

    /**
     * @param avsCountry the avs_country to set
     */
    public void setAvs_country(String avsCountry)
    {
        avs_country = avsCountry;
    }

    /**
     * @return the venuetype
     */
    public String getVenuetype()
    {
        return venuetype;
    }

    /**
     * @param venuetype the venuetype to set
     */
    public void setVenuetype(String venuetype)
    {
        this.venuetype = venuetype;
    }

    /**
     * @return the updateddate
     */
    public Timestamp getUpdateddate()
    {
        return updateddate;
    }

    /**
     * @param updateddate the updateddate to set
     */
    public void setUpdateddate(Timestamp updateddate)
    {
        this.updateddate = updateddate;
    }

    /**
     * @return the citytaxperc
     */
    public float getCitytaxperc()
    {
        return citytaxperc;
    }

    /**
     * @param citytaxperc the citytaxperc to set
     */
    public void setCitytaxperc(float citytaxperc)
    {
        this.citytaxperc = citytaxperc;
    }

    /**
     * @return the citytrantaxperc
     */
    public float getCitytrantaxperc()
    {
        return citytrantaxperc;
    }

    /**
     * @param citytrantaxperc the citytrantaxperc to set
     */
    public void setCitytrantaxperc(float citytrantaxperc)
    {
        this.citytrantaxperc = citytrantaxperc;
    }

    /**
     * @return the countytaxperc
     */
    public float getCountytaxperc()
    {
        return countytaxperc;
    }

    /**
     * @param countytaxperc the countytaxperc to set
     */
    public void setCountytaxperc(float countytaxperc)
    {
        this.countytaxperc = countytaxperc;
    }

    /**
     * @return the countytrantaxperc
     */
    public float getCountytrantaxperc()
    {
        return countytrantaxperc;
    }

    /**
     * @param countytrantaxperc the countytrantaxperc to set
     */
    public void setCountytrantaxperc(float countytrantaxperc)
    {
        this.countytrantaxperc = countytrantaxperc;
    }

    /**
     * @return the statetaxperc
     */
    public float getStatetaxperc()
    {
        return statetaxperc;
    }

    /**
     * @param statetaxperc the statetaxperc to set
     */
    public void setStatetaxperc(float statetaxperc)
    {
        this.statetaxperc = statetaxperc;
    }

    /**
     * @return the freightTax
     */
    public BigDecimal getFreightTax()
    {
        return freightTax;
    }

    /**
     * @param freightTax the freightTax to set
     */
    public void setFreightTax(BigDecimal freightTax)
    {
        this.freightTax = freightTax;
    }

    /**
     * @return the rf_date
     */
    public Timestamp getRf_date()
    {
        return rf_date;
    }

    /**
     * @param rfDate the rf_date to set
     */
    public void setRf_date(Timestamp rfDate)
    {
        rf_date = rfDate;
    }

    /**
     * @return the orderNumber
     */
    public String getOrderNumber()
    {
        return OrderNumber;
    }

    /**
     * @param orderNumber
     *            the orderNumber to set
     */
    public void setOrderNumber(String orderNumber)
    {
        OrderNumber = orderNumber;
    }

    /**
     * @return the orderBarcode
     */
    public String getOrderBarcode()
    {
        return OrderBarcode;
    }

    /**
     * @param orderBarcode
     *            the orderBarcode to set
     */
    public void setOrderBarcode(String orderBarcode)
    {
        OrderBarcode = orderBarcode;
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
     * @return the billingGrpID
     */
    public String getBillingGrpID()
    {
        return BillingGrpID;
    }

    /**
     * @param billingGrpID
     *            the billingGrpID to set
     */
    public void setBillingGrpID(String billingGrpID)
    {
        BillingGrpID = billingGrpID;
    }

    /**
     * @return the purchOrdNum
     */
    public String getPurchOrdNum()
    {
        return PurchOrdNum;
    }

    /**
     * @param purchOrdNum
     *            the purchOrdNum to set
     */
    public void setPurchOrdNum(String purchOrdNum)
    {
        PurchOrdNum = purchOrdNum;
    }

    /**
     * @return the approvalNumber
     */
    public String getApprovalNumber()
    {
        return ApprovalNumber;
    }

    /**
     * @param approvalNumber
     *            the approvalNumber to set
     */
    public void setApprovalNumber(String approvalNumber)
    {
        ApprovalNumber = approvalNumber;
    }

    /**
     * @return the program
     */
    public String getProgram()
    {
        return Program;
    }

    /**
     * @param program
     *            the program to set
     */
    public void setProgram(String program)
    {
        Program = program;
    }

    /**
     * @return the dataSource
     */
    public String getDataSource()
    {
        return DataSource;
    }

    /**
     * @param dataSource
     *            the dataSource to set
     */
    public void setDataSource(String dataSource)
    {
        DataSource = dataSource;
    }

    /**
     * @return the ship_to_name
     */
    public String getShip_to_name()
    {
        return ship_to_name;
    }

    /**
     * @param shipToName
     *            the ship_to_name to set
     */
    public void setShip_to_name(String shipToName)
    {
        ship_to_name = shipToName;
    }

    /**
     * @return the ship_to_title
     */
    public String getShip_to_title()
    {
        return ship_to_title;
    }

    /**
     * @param shipToTitle
     *            the ship_to_title to set
     */
    public void setShip_to_title(String shipToTitle)
    {
        ship_to_title = shipToTitle;
    }

    /**
     * @return the ship_to_company
     */
    public String getShip_to_company()
    {
        return ship_to_company;
    }

    /**
     * @param shipToCompany
     *            the ship_to_company to set
     */
    public void setShip_to_company(String shipToCompany)
    {
        ship_to_company = shipToCompany;
    }

    /**
     * @return the ship_to_address1
     */
    public String getShip_to_address1()
    {
        return ship_to_address1;
    }

    /**
     * @param shipToAddress1
     *            the ship_to_address1 to set
     */
    public void setShip_to_address1(String shipToAddress1)
    {
        ship_to_address1 = shipToAddress1;
    }

    /**
     * @return the ship_to_address2
     */
    public String getShip_to_address2()
    {
        return ship_to_address2;
    }

    /**
     * @param shipToAddress2
     *            the ship_to_address2 to set
     */
    public void setShip_to_address2(String shipToAddress2)
    {
        ship_to_address2 = shipToAddress2;
    }

    /**
     * @return the ship_to_address3
     */
    public String getShip_to_address3()
    {
        return ship_to_address3;
    }

    /**
     * @param shipToAddress3
     *            the ship_to_address3 to set
     */
    public void setShip_to_address3(String shipToAddress3)
    {
        ship_to_address3 = shipToAddress3;
    }

    /**
     * @return the ship_to_city
     */
    public String getShip_to_city()
    {
        return ship_to_city;
    }

    /**
     * @param shipToCity
     *            the ship_to_city to set
     */
    public void setShip_to_city(String shipToCity)
    {
        ship_to_city = shipToCity;
    }

    /**
     * @return the ship_to_state
     */
    public String getShip_to_state()
    {
        return ship_to_state;
    }

    /**
     * @param shipToState
     *            the ship_to_state to set
     */
    public void setShip_to_state(String shipToState)
    {
        ship_to_state = shipToState;
    }

    /**
     * @return the ship_to_postal
     */
    public String getShip_to_postal()
    {
        return ship_to_postal;
    }

    /**
     * @param shipToPostal
     *            the ship_to_postal to set
     */
    public void setShip_to_postal(String shipToPostal)
    {
        ship_to_postal = shipToPostal;
    }

    /**
     * @return the ship_to_county
     */
    public String getShip_to_county()
    {
        return ship_to_county;
    }

    /**
     * @param shipToCounty
     *            the ship_to_county to set
     */
    public void setShip_to_county(String shipToCounty)
    {
        ship_to_county = shipToCounty;
    }

    /**
     * @return the ship_to_country
     */
    public String getShip_to_country()
    {
        return ship_to_country;
    }

    /**
     * @param shipToCountry
     *            the ship_to_country to set
     */
    public void setShip_to_country(String shipToCountry)
    {
        ship_to_country = shipToCountry;
    }

    /**
     * @return the ship_to_fax
     */
    public String getShip_to_fax()
    {
        return ship_to_fax;
    }

    /**
     * @param shipToFax
     *            the ship_to_fax to set
     */
    public void setShip_to_fax(String shipToFax)
    {
        ship_to_fax = shipToFax;
    }

    /**
     * @return the ship_to_phone
     */
    public String getShip_to_phone()
    {
        return ship_to_phone;
    }

    /**
     * @param shipToPhone
     *            the ship_to_phone to set
     */
    public void setShip_to_phone(String shipToPhone)
    {
        ship_to_phone = shipToPhone;
    }

    /**
     * @return the ship_to_phoneext
     */
    public String getShip_to_phoneext()
    {
        return ship_to_phoneext;
    }

    /**
     * @param shipToPhoneext
     *            the ship_to_phoneext to set
     */
    public void setShip_to_phoneext(String shipToPhoneext)
    {
        ship_to_phoneext = shipToPhoneext;
    }

    /**
     * @return the ship_to_email
     */
    public String getShip_to_email()
    {
        return ship_to_email;
    }

    /**
     * @param shipToEmail
     *            the ship_to_email to set
     */
    public void setShip_to_email(String shipToEmail)
    {
        ship_to_email = shipToEmail;
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
     * @return the bill_to_name
     */
    public String getBill_to_name()
    {
        return bill_to_name;
    }

    /**
     * @param billToName
     *            the bill_to_name to set
     */
    public void setBill_to_name(String billToName)
    {
        bill_to_name = billToName;
    }

    /**
     * @return the bill_to_company
     */
    public String getBill_to_company()
    {
        return bill_to_company;
    }

    /**
     * @param billToCompany
     *            the bill_to_company to set
     */
    public void setBill_to_company(String billToCompany)
    {
        bill_to_company = billToCompany;
    }

    /**
     * @return the bill_to_address1
     */
    public String getBill_to_address1()
    {
        return bill_to_address1;
    }

    /**
     * @param billToAddress1
     *            the bill_to_address1 to set
     */
    public void setBill_to_address1(String billToAddress1)
    {
        bill_to_address1 = billToAddress1;
    }

    /**
     * @return the bill_to_address2
     */
    public String getBill_to_address2()
    {
        return bill_to_address2;
    }

    /**
     * @param billToAddress2
     *            the bill_to_address2 to set
     */
    public void setBill_to_address2(String billToAddress2)
    {
        bill_to_address2 = billToAddress2;
    }

    /**
     * @return the bill_to_city
     */
    public String getBill_to_city()
    {
        return bill_to_city;
    }

    /**
     * @param billToCity
     *            the bill_to_city to set
     */
    public void setBill_to_city(String billToCity)
    {
        bill_to_city = billToCity;
    }

    /**
     * @return the bill_to_state
     */
    public String getBill_to_state()
    {
        return bill_to_state;
    }

    /**
     * @param billToState
     *            the bill_to_state to set
     */
    public void setBill_to_state(String billToState)
    {
        bill_to_state = billToState;
    }

    /**
     * @return the bill_to_postal
     */
    public String getBill_to_postal()
    {
        return bill_to_postal;
    }

    /**
     * @param billToPostal
     *            the bill_to_postal to set
     */
    public void setBill_to_postal(String billToPostal)
    {
        bill_to_postal = billToPostal;
    }

    /**
     * @return the bill_to_country
     */
    public String getBill_to_country()
    {
        return bill_to_country;
    }

    /**
     * @param billToCountry
     *            the bill_to_country to set
     */
    public void setBill_to_country(String billToCountry)
    {
        bill_to_country = billToCountry;
    }

    /**
     * @return the bill_to_fax
     */
    public String getBill_to_fax()
    {
        return bill_to_fax;
    }

    /**
     * @param billToFax
     *            the bill_to_fax to set
     */
    public void setBill_to_fax(String billToFax)
    {
        bill_to_fax = billToFax;
    }

    /**
     * @return the bill_to_phone
     */
    public String getBill_to_phone()
    {
        return bill_to_phone;
    }

    /**
     * @param billToPhone
     *            the bill_to_phone to set
     */
    public void setBill_to_phone(String billToPhone)
    {
        bill_to_phone = billToPhone;
    }

    /**
     * @return the bill_to_phoneext
     */
    public String getBill_to_phoneext()
    {
        return bill_to_phoneext;
    }

    /**
     * @param billToPhoneext
     *            the bill_to_phoneext to set
     */
    public void setBill_to_phoneext(String billToPhoneext)
    {
        bill_to_phoneext = billToPhoneext;
    }

    /**
     * @return the bill_to_email
     */
    public String getBill_to_email()
    {
        return bill_to_email;
    }

    /**
     * @param billToEmail
     *            the bill_to_email to set
     */
    public void setBill_to_email(String billToEmail)
    {
        bill_to_email = billToEmail;
    }

    /**
     * @return the oadjust_subtotal
     */
    public BigDecimal getOadjust_subtotal()
    {
        return oadjust_subtotal;
    }

    /**
     * @param oadjustSubtotal
     *            the oadjust_subtotal to set
     */
    public void setOadjust_subtotal(BigDecimal oadjustSubtotal)
    {
        oadjust_subtotal = oadjustSubtotal;
    }

    /**
     * @return the discount_total
     */
    public BigDecimal getDiscount_total()
    {
        return discount_total;
    }

    /**
     * @param discountTotal
     *            the discount_total to set
     */
    public void setDiscount_total(BigDecimal discountTotal)
    {
        discount_total = discountTotal;
    }

    /**
     * @return the handling_total
     */
    public BigDecimal getHandling_total()
    {
        return handling_total;
    }

    /**
     * @param handlingTotal
     *            the handling_total to set
     */
    public void setHandling_total(BigDecimal handlingTotal)
    {
        handling_total = handlingTotal;
    }

    /**
     * @return the tax_exempt
     */
    public int getTax_exempt()
    {
        return tax_exempt;
    }

    /**
     * @param taxExempt
     *            the tax_exempt to set
     */
    public void setTax_exempt(int taxExempt)
    {
        tax_exempt = taxExempt;
    }

    /**
     * @return the tax_exempt_number
     */
    public String getTax_exempt_number()
    {
        return tax_exempt_number;
    }

    /**
     * @param taxExemptNumber
     *            the tax_exempt_number to set
     */
    public void setTax_exempt_number(String taxExemptNumber)
    {
        tax_exempt_number = taxExemptNumber;
    }

    /**
     * @return the tax_exempt_expire
     */
    public Timestamp getTax_exempt_expire()
    {
        return tax_exempt_expire;
    }

    /**
     * @param taxExemptExpire
     *            the tax_exempt_expire to set
     */
    public void setTax_exempt_expire(Timestamp taxExemptExpire)
    {
        tax_exempt_expire = taxExemptExpire;
    }

    /**
     * @return the tax_total
     */
    public BigDecimal getTax_total()
    {
        return tax_total;
    }

    /**
     * @param taxTotal
     *            the tax_total to set
     */
    public void setTax_total(BigDecimal taxTotal)
    {
        tax_total = taxTotal;
    }

    /**
     * @return the tax_included
     */
    public BigDecimal getTax_included()
    {
        return tax_included;
    }

    /**
     * @param taxIncluded
     *            the tax_included to set
     */
    public void setTax_included(BigDecimal taxIncluded)
    {
        tax_included = taxIncluded;
    }

    /**
     * @return the total_total
     */
    public BigDecimal getTotal_total()
    {
        return total_total;
    }

    /**
     * @param totalTotal
     *            the total_total to set
     */
    public void setTotal_total(BigDecimal totalTotal)
    {
        total_total = totalTotal;
    }

    /**
     * @return the warranty_total
     */
    public BigDecimal getWarranty_total()
    {
        return warranty_total;
    }

    /**
     * @param warrantyTotal
     *            the total_total to set
     */
    public void setWarranty_total(BigDecimal warrantyTotal)
    {
        warranty_total = warrantyTotal;
    }
    
    /**
     * @return the weight
     */
    public Float getWeight()
    {
        return weight;
    }

    /**
     * @param weight
     *            the weight to set
     */
    public void setWeight(Float weight)
    {
        this.weight = weight;
    }

    /**
     * @return the payment_terms
     */
    public String getPayment_terms()
    {
        return payment_terms;
    }

    /**
     * @param paymentTerms
     *            the payment_terms to set
     */
    public void setPayment_terms(String paymentTerms)
    {
        payment_terms = paymentTerms;
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
    public int getCc_expmonth()
    {
        return cc_expmonth;
    }

    /**
     * @param ccExpmonth
     *            the cc_expmonth to set
     */
    public void setCc_expmonth(int ccExpmonth)
    {
        cc_expmonth = ccExpmonth;
    }

    /**
     * @return the cc_expyear
     */
    public int getCc_expyear()
    {
        return cc_expyear;
    }

    /**
     * @param ccExpyear
     *            the cc_expyear to set
     */
    public void setCc_expyear(int ccExpyear)
    {
        cc_expyear = ccExpyear;
    }

    /**
     * @return the tracking_number
     */
    public String getTracking_number()
    {
        return tracking_number;
    }

    /**
     * @param trackingNumber
     *            the tracking_number to set
     */
    public void setTracking_number(String trackingNumber)
    {
        tracking_number = trackingNumber;
    }

    /**
     * @return the orderStatus
     */
    public String getOrderStatus()
    {
        return orderStatus;
    }

    /**
     * @param orderStatus
     *            the orderStatus to set
     */
    public void setOrderStatus(String orderStatus)
    {
        this.orderStatus = orderStatus;
    }

    /**
     * @return the createdDate
     */
    public Timestamp getCreatedDate()
    {
        return createdDate;
    }

    /**
     * @param createdDate
     *            the createdDate to set
     */
    public void setCreatedDate(Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     * @return the updatedDate
     */
    public Timestamp getUpdatedDate()
    {
        return updatedDate;
    }

    /**
     * @param updatedDate
     *            the updatedDate to set
     */
    public void setUpdatedDate(Timestamp updatedDate)
    {
        this.updatedDate = updatedDate;
    }

    /**
     * @return the taxable
     */
    public Boolean getTaxable()
    {
        return Taxable;
    }

    /**
     * @param taxable
     *            the taxable to set
     */
    public void setTaxable(Boolean taxable)
    {
        Taxable = taxable;
    }

    /**
     * @return the taxExemptNum
     */
    public String getTaxExemptNum()
    {
        return TaxExemptNum;
    }

    /**
     * @param taxExemptNum
     *            the taxExemptNum to set
     */
    public void setTaxExemptNum(String taxExemptNum)
    {
        TaxExemptNum = taxExemptNum;
    }

    /**
     * @return the termCode
     */
    public String getTermCode()
    {
        return TermCode;
    }

    /**
     * @param termCode
     *            the termCode to set
     */
    public void setTermCode(String termCode)
    {
        TermCode = termCode;
    }

    /**
     * @return the dateRequested
     */
    public Timestamp getDateRequested()
    {
        return DateRequested;
    }

    /**
     * @param dateRequested
     *            the dateRequested to set
     */
    public void setDateRequested(Timestamp dateRequested)
    {
        DateRequested = dateRequested;
    }

    /**
     * @return the datePrinted
     */
    public Timestamp getDatePrinted()
    {
        return DatePrinted;
    }

    /**
     * @param datePrinted
     *            the datePrinted to set
     */
    public void setDatePrinted(Timestamp datePrinted)
    {
        DatePrinted = datePrinted;
    }

    /**
     * @return the dateCompleted
     */
    public Timestamp getDateCompleted()
    {
        return DateCompleted;
    }

    /**
     * @param dateCompleted
     *            the dateCompleted to set
     */
    public void setDateCompleted(Timestamp dateCompleted)
    {
        DateCompleted = dateCompleted;
    }

    /**
     * @return the dateInvoiced
     */
    public Timestamp getDateInvoiced()
    {
        return DateInvoiced;
    }

    /**
     * @param dateInvoiced
     *            the dateInvoiced to set
     */
    public void setDateInvoiced(Timestamp dateInvoiced)
    {
        DateInvoiced = dateInvoiced;
    }

    /**
     * @return the dateReturned
     */
    public Timestamp getDateReturned()
    {
        return DateReturned;
    }

    /**
     * @param dateReturned
     *            the dateReturned to set
     */
    public void setDateReturned(Timestamp dateReturned)
    {
        DateReturned = dateReturned;
    }

    /**
     * @return the shipping_total
     */
    public BigDecimal getShipping_total()
    {
        return shipping_total;
    }

    /**
     * @param shippingTotal the shipping_total to set
     */
    public void setShipping_total(BigDecimal shippingTotal)
    {
        shipping_total = shippingTotal;
    }

    /**
     * @return the cc_cvv2
     */
    public String getCc_cvv2()
    {
        return cc_cvv2;
    }

    /**
     * @param ccCvv2 the cc_cvv2 to set
     */
    public void setCc_cvv2(String ccCvv2)
    {
        cc_cvv2 = ccCvv2;
    }

    /**
     * @param byAgent the byAgent to set
     */
    public void setByAgent(Boolean byAgent)
    {
        this.byAgent = byAgent;
    }

    /**
     * @return the byAgent
     */
    public Boolean getByAgent()
    {
        return byAgent;
    }

}
