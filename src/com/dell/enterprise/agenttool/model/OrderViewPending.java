/*
 * FILENAME
 *     OrderRow.java
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
public class OrderViewPending
{
    private String order_id;
    private String orderStatus;
    private String createdDate;
    private String bill_to_name;
    private String bill_to_company;
    private String bill_to_address1;
    private String bill_to_address2;
    private String bill_to_city;
    private String bill_to_country;
    private String bill_to_phone;
    private String bill_to_phoneext;
    private String bill_to_fax;
    private String cc_type;
    private String cc_name;
    private String cc_number;
    private String cc_expmonth;
    private String cc_expyear;
    private String ApprovalNumber;
    private String payment_terms;
    private String shopper_id;
    private String item_sku;
    private String bill_to_state;
    private String bill_to_postal;
    private String ship_to_name;
    private String ship_to_title;
    private String ship_to_company;
    private String ship_to_address1;
    private String ship_to_address2;
    private String ship_to_city;
    private String ship_to_state;
    private String ship_to_postal;
    private String ship_to_country;   
    private String ship_to_phone;    
    private String product_list_price;   
    private String epp_id;    
    private String oadjust_subtotal;   
    private String discount_total;  
    private String est_subtotal;  
    private String shipping_discount;  
    private String shipping_total;
    private String handling_total;
    private String tax_total;  
    private String freighttax;  
    private String total_total;  
    private String shopper_number;  
    private String listing;  
    private String discounted_price;  
    private String agentName;
    private Short byAgent;
    private String cosmetic_grade;
    private String ordered_qty;
    
    
    public String getOrderedQty()
    {
    	return ordered_qty;
    }
    
    public void setOrderedQty(String orderQty)
    {
    	ordered_qty = orderQty;
    }
    
    /**
     * @return the discounted_price
     */
    public String getDiscounted_price()
    {
        return discounted_price;
    }

    /**
     * @param discountedPrice the discounted_price to set
     */
    public void setDiscounted_price(String discountedPrice)
    {
        discounted_price = discountedPrice;
    }

    /**
     * @return the listing
     */
    public String getListing()
    {
        return listing;
    }

    /**
     * @param listing the listing to set
     */
    public void setListing(String listing)
    {
        this.listing = listing;
    }

    /**
     * @return the shopper_number
     */
    public String getShopper_number()
    {
        return shopper_number;
    }

    /**
     * @param shopperNumber the shopper_number to set
     */
    public void setShopper_number(String shopperNumber)
    {
        shopper_number = shopperNumber;
    }

    /**
     * @return the total_total
     */
    public String getTotal_total()
    {
        return total_total;
    }

    /**
     * @param totalTotal the total_total to set
     */
    public void setTotal_total(String totalTotal)
    {
        total_total = totalTotal;
    }

    /**
     * @return the freighttax
     */
    public String getFreighttax()
    {
        return freighttax;
    }

    /**
     * @param freighttax the freighttax to set
     */
    public void setFreighttax(String freighttax)
    {
        this.freighttax = freighttax;
    }

    /**
     * @return the tax_total
     */
    public String getTax_total()
    {
        return tax_total;
    }

    /**
     * @param taxTotal the tax_total to set
     */
    public void setTax_total(String taxTotal)
    {
        tax_total = taxTotal;
    }

    /**
     * @return the shipping_total
     */
    public String getShipping_total()
    {
        return shipping_total;
    }

    /**
     * @param shippingTotal the shipping_total to set
     */
    public void setShipping_total(String shippingTotal)
    {
        shipping_total = shippingTotal;
    }

    /**
     * @return the shipping_discount
     */
    public String getShipping_discount()
    {
        return shipping_discount;
    }

    /**
     * @param shippingDiscount the shipping_discount to set
     */
    public void setShipping_discount(String shippingDiscount)
    {
        shipping_discount = shippingDiscount;
    }

    /**
     * @return the est_subtotal
     */
    public String getEst_subtotal()
    {
        return est_subtotal;
    }

    /**
     * @param estSubtotal the est_subtotal to set
     */
    public void setEst_subtotal(String estSubtotal)
    {
        est_subtotal = estSubtotal;
    }

    /**
     * @return the epp_id
     */
    public String getEpp_id()
    {
        return epp_id;
    }

    /**
     * @param eppId the epp_id to set
     */
    public void setEpp_id(String eppId)
    {
        epp_id = eppId;
    }

    /**
     * @return the oadjust_subtotal
     */
    public String getOadjust_subtotal()
    {
        return oadjust_subtotal;
    }

    /**
     * @param oadjustSubtotal the oadjust_subtotal to set
     */
    public void setOadjust_subtotal(String oadjustSubtotal)
    {
        oadjust_subtotal = oadjustSubtotal;
    }

    /**
     * @return the discount_total
     */
    public String getDiscount_total()
    {
        return discount_total;
    }

    /**
     * @param discountTotal the discount_total to set
     */
    public void setDiscount_total(String discountTotal)
    {
        discount_total = discountTotal;
    }

    /**
     * @return the product_list_price
     */
    public String getProduct_list_price()
    {
        return product_list_price;
    }

    /**
     * @param productListPrice the product_list_price to set
     */
    public void setProduct_list_price(String productListPrice)
    {
        product_list_price = productListPrice;
    }

    /**
     * @return the ship_to_phone
     */
    public String getShip_to_phone()
    {
        return ship_to_phone;
    }

    /**
     * @param shipToPhone the ship_to_phone to set
     */
    public void setShip_to_phone(String shipToPhone)
    {
        ship_to_phone = shipToPhone;
    }

    private String ship_to_phoneext;    
    
    
    
    /**
     * @return the ship_to_company
     */
    public String getShip_to_company()
    {
        return ship_to_company;
    }

    /**
     * @param shipToCompany the ship_to_company to set
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
     * @param shipToAddress1 the ship_to_address1 to set
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
     * @param shipToAddress2 the ship_to_address2 to set
     */
    public void setShip_to_address2(String shipToAddress2)
    {
        ship_to_address2 = shipToAddress2;
    }

    /**
     * @return the ship_to_city
     */
    public String getShip_to_city()
    {
        return ship_to_city;
    }

    /**
     * @param shipToCity the ship_to_city to set
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
     * @param shipToState the ship_to_state to set
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
     * @param shipToPostal the ship_to_postal to set
     */
    public void setShip_to_postal(String shipToPostal)
    {
        ship_to_postal = shipToPostal;
    }

    /**
     * @return the ship_to_country
     */
    public String getShip_to_country()
    {
        return ship_to_country;
    }

    /**
     * @param shipToCountry the ship_to_country to set
     */
    public void setShip_to_country(String shipToCountry)
    {
        ship_to_country = shipToCountry;
    }

    /**
     * @return the ship_to_phoneext
     */
    public String getShip_to_phoneext()
    {
        return ship_to_phoneext;
    }

    /**
     * @param shipToPhoneext the ship_to_phoneext to set
     */
    public void setShip_to_phoneext(String shipToPhoneext)
    {
        ship_to_phoneext = shipToPhoneext;
    }

    private String AgentIDEnter;   
    private String agent_id;
    private String UserName;
    private String ship_method;
    private String ship_terms;
    private String description;
    private String tracking_number ;
    private String AccountType ;
    private String FraudDetail ;
    
    
 
    

    /**
     * @return the accountType
     */
    public String getAccountType()
    {
        return AccountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType)
    {
        AccountType = accountType;
    }

    /**
     * @return the fraudDetail
     */
    public String getFraudDetail()
    {
        return FraudDetail;
    }

    /**
     * @param fraudDetail the fraudDetail to set
     */
    public void setFraudDetail(String fraudDetail)
    {
        FraudDetail = fraudDetail;
    }

    /**
     * @return the tracking_number
     */
    public String getTracking_number()
    {
        return tracking_number;
    }

    /**
     * @param trackingNumber the tracking_number to set
     */
    public void setTracking_number(String trackingNumber)
    {
        tracking_number = trackingNumber;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the ship_method
     */
    public String getShip_method()
    {
        return ship_method;
    }

    /**
     * @param shipMethod the ship_method to set
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
     * @param shipTerms the ship_terms to set
     */
    public void setShip_terms(String shipTerms)
    {
        ship_terms = shipTerms;
    }

    /**
     * @return the agent_id
     */
    public String getAgent_id()
    {
        return agent_id;
    }

    /**
     * @param agentId the agent_id to set
     */
    public void setAgent_id(String agentId)
    {
        agent_id = agentId;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return UserName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        UserName = userName;
    }

    /**
     * @return the agentIDEnter
     */
    public String getAgentIDEnter()
    {
        return AgentIDEnter;
    }

    /**
     * @param agentIDEnter the agentIDEnter to set
     */
    public void setAgentIDEnter(String agentIDEnter)
    {
        AgentIDEnter = agentIDEnter;
    }

    /**
     * @return the ship_to_title
     */
    public String getShip_to_title()
    {
        return ship_to_title;
    }

    /**
     * @param shipToTitle the ship_to_title to set
     */
    public void setShip_to_title(String shipToTitle)
    {
        ship_to_title = shipToTitle;
    }

    /**
     * @return the ship_to_name
     */
    public String getShip_to_name()
    {
        return ship_to_name;
    }

    /**
     * @param shipToName the ship_to_name to set
     */
    public void setShip_to_name(String shipToName)
    {
        ship_to_name = shipToName;
    }

    /**
     * @return the bill_to_state
     */
    public String getBill_to_state()
    {
        return bill_to_state;
    }

    /**
     * @param billToState the bill_to_state to set
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
     * @param billToPostal the bill_to_postal to set
     */
    public void setBill_to_postal(String billToPostal)
    {
        bill_to_postal = billToPostal;
    }

    /**
     * @return the order_id
     */
    public String getOrder_id()
    {
        return order_id;
    }

    /**
     * @param orderId
     *            the order_id to set
     */
    public void setOrder_id(String orderId)
    {
        order_id = orderId;
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
     * @return the cc_expmonth
     */
    public String getCc_expmonth()
    {
        return cc_expmonth;
    }

    /**
     * @param ccExpmonth
     *            the cc_expmonth to set
     */
    public void setCc_expmonth(String ccExpmonth)
    {
        cc_expmonth = ccExpmonth;
    }

    /**
     * @return the cc_expyear
     */
    public String getCc_expyear()
    {
        return cc_expyear;
    }

    /**
     * @param ccExpyear
     *            the cc_expyear to set
     */
    public void setCc_expyear(String ccExpyear)
    {
        cc_expyear = ccExpyear;
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

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public Short getByAgent() {
		return byAgent;
	}

	public void setByAgent(Short byAgent) {
		this.byAgent = byAgent;
	}

	public void setHandling_total(String handling_total) {
		this.handling_total = handling_total;
	}

	public String getHandling_total() {
		return handling_total;
	}

	public String getCosmetic_grade() {
		return cosmetic_grade;
	}

	public void setCosmetic_grade(String cosmetic_grade) {
		this.cosmetic_grade = cosmetic_grade;
	}

}
