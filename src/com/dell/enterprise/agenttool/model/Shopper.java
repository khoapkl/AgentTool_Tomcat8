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
import java.sql.Timestamp;

/**
 * @author hungnguyen
 **/
@SuppressWarnings("serial")
public class Shopper implements Serializable
{
    private String shopperId;
    private int shopperNumber;
    private String linkNumber;

    private String shipToFName;
    private String shipToLName;
    private String shipToCompany;
    private String shipToAddress1;
    private String shipToAddress2;
    private String shipToCity;
    private String shipToState;
    private String shipToPostal;
    private String shipToCounty;
    private String shipToCountry;
    private String shipToFax;
    private String shipToPhone;
    private String shipToEmail;

    private String billToFName;
    private String billToLName;
    private String billToCompany;
    private String billToAddress1;
    private String billToAddress2;
    private String billToCity;
    private String billToState;
    private String billToPostal;
    private String billToCounty;
    private String billToCountry;
    private String billToFax;
    private String billToPhone;
    private String billToEmail;

    private double creditLine;
    private String creditExp;
    private double creditAvail;
    private String creditComment;

    private Timestamp createdDate;
    private	int agent_id;
    
    public int getAgent_id() {
		return agent_id;
	}

	public void setAgent_id(int agentId) {
		agent_id = agentId;
	}

	/**
     * @return the shopperId
     */
    public String getShopperId()
    {
        return shopperId;
    }

    /**
     * @param shopperId
     *            the shopperId to set
     */
    public void setShopperId(final String shopperId)
    {
        this.shopperId = (shopperId == null) ? "" : shopperId;
    }

    /**
     * @return the shopperNumber
     */
    public int getShopperNumber()
    {
        return shopperNumber;
    }

    /**
     * @param shopperNumber
     *            the shopperNumber to set
     */
    public void setShopperNumber(final int shopperNumber)
    {
        this.shopperNumber = shopperNumber;
    }

    /**
     * @return the linkNumber
     */
    public String getLinkNumber()
    {
        return linkNumber;
    }

    /**
     * @param linkNumber
     *            the linkNumber to set
     */
    public void setLinkNumber(final String linkNumber)
    {
        this.linkNumber = (linkNumber == null) ? "" : linkNumber;
    }

    /**
     * @return the shipToFName
     */
    public String getShipToFName()
    {
        return shipToFName;
    }

    /**
     * @param shipToFName
     *            the shipToFName to set
     */
    public void setShipToFName(final String shipToFName)
    {
        this.shipToFName = (shipToFName == null) ? "" : shipToFName;
    }

    /**
     * @return the shipToLName
     */
    public String getShipToLName()
    {
        return shipToLName;
    }

    /**
     * @param shipToLName
     *            the shipToLName to set
     */
    public void setShipToLName(final String shipToLName)
    {
        this.shipToLName = (shipToLName == null) ? "" : shipToLName;
    }

    /**
     * @return the shipToCompany
     */
    public String getShipToCompany()
    {
        return shipToCompany;
    }

    /**
     * @param shipToCompany
     *            the shipToCompany to set
     */
    public void setShipToCompany(final String shipToCompany)
    {
        this.shipToCompany = (shipToCompany == null) ? "" : shipToCompany;
    }

    /**
     * @return the shipToPhone
     */
    public String getShipToPhone()
    {
        return shipToPhone;
    }

    /**
     * @param shipToPhone
     *            the shipToPhone to set
     */
    public void setShipToPhone(final String shipToPhone)
    {
        this.shipToPhone = (shipToPhone == null) ? "" : shipToPhone;
    }

    /**
     * @return the billToFName
     */
    public String getBillToFName()
    {
        return billToFName;
    }

    /**
     * @param billToFName
     *            the billToFName to set
     */
    public void setBillToFName(final String billToFName)
    {
        this.billToFName = (billToFName == null) ? "" : billToFName;
    }

    /**
     * @return the billToLName
     */
    public String getBillToLName()
    {
        return billToLName;
    }

    /**
     * @param billToLName
     *            the billToLName to set
     */
    public void setBillToLName(final String billToLName)
    {
        this.billToLName = (billToLName == null) ? "" : billToLName;
    }

    /**
     * @return the billToCompany
     */
    public String getBillToCompany()
    {
        return billToCompany;
    }

    /**
     * @param billToCompany
     *            the billToCompany to set
     */
    public void setBillToCompany(final String billToCompany)
    {
        this.billToCompany = (billToCompany == null) ? "" : billToCompany;
    }

    /**
     * @return the billToPhone
     */
    public String getBillToPhone()
    {
        return billToPhone;
    }

    /**
     * @param billToPhone
     *            the billToPhone to set
     */
    public void setBillToPhone(final String billToPhone)
    {
        this.billToPhone = (billToPhone == null) ? "" : billToPhone;
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
    public void setCreatedDate(final Timestamp createdDate)
    {
        this.createdDate = createdDate;
    }

    /**
     * @return the shipToCity
     */
    public String getShipToCity()
    {
        return shipToCity;
    }

    /**
     * @param shipToCity
     *            the shipToCity to set
     */
    public void setShipToCity(final String shipToCity)
    {
        this.shipToCity = (shipToCity == null) ? "" : shipToCity;
    }

    /**
     * @return the shipToState
     */
    public String getShipToState()
    {
        return shipToState;
    }

    /**
     * @param shipToState
     *            the shipToState to set
     */
    public void setShipToState(final String shipToState)
    {
        this.shipToState = (shipToState == null) ? "" : shipToState;
    }

    /**
     * @return the shipToPostal
     */
    public String getShipToPostal()
    {
        return shipToPostal;
    }

    /**
     * @param shipToPostal
     *            the shipToPostal to set
     */
    public void setShipToPostal(final String shipToPostal)
    {
        this.shipToPostal = (shipToPostal == null) ? "" : shipToPostal;
    }

    /**
     * @return the shipToCounty
     */
    public String getShipToCounty()
    {
        return shipToCounty;
    }

    /**
     * @param shipToCounty
     *            the shipToCounty to set
     */
    public void setShipToCounty(final String shipToCounty)
    {
        this.shipToCounty = (shipToCounty == null) ? "" : shipToCounty;
    }

    /**
     * @return the shipToCountry
     */
    public String getShipToCountry()
    {
        return shipToCountry;
    }

    /**
     * @param shipToCountry
     *            the shipToCountry to set
     */
    public void setShipToCountry(final String shipToCountry)
    {
        this.shipToCountry = (shipToCountry == null) ? "" : shipToCountry;
    }

    /**
     * @return the shipToFax
     */
    public String getShipToFax()
    {
        return shipToFax;
    }

    /**
     * @param shipToFax
     *            the shipToFax to set
     */
    public void setShipToFax(final String shipToFax)
    {
        this.shipToFax = (shipToFax == null) ? "" : shipToFax;
    }

    /**
     * @return the shipToEmail
     */
    public String getShipToEmail()
    {
        return shipToEmail;
    }

    /**
     * @param shipToEmail
     *            the shipToEmail to set
     */
    public void setShipToEmail(final String shipToEmail)
    {
        this.shipToEmail = (shipToEmail == null) ? "" : shipToEmail;
    }

    /**
     * @return the billToCity
     */
    public String getBillToCity()
    {
        return billToCity;
    }

    /**
     * @param billToCity
     *            the billToCity to set
     */
    public void setBillToCity(final String billToCity)
    {
        this.billToCity = (billToCity == null) ? "" : billToCity;
    }

    /**
     * @return the billToState
     */
    public String getBillToState()
    {
        return billToState;
    }

    /**
     * @param billToState
     *            the billToState to set
     */
    public void setBillToState(final String billToState)
    {
        this.billToState = (billToState == null) ? "" : billToState;
    }

    /**
     * @return the billToPostal
     */
    public String getBillToPostal()
    {
        return billToPostal;
    }

    /**
     * @param billToPostal
     *            the billToPostal to set
     */
    public void setBillToPostal(final String billToPostal)
    {
        this.billToPostal = (billToPostal == null) ? "" : billToPostal;
    }

    /**
     * @return the billToCounty
     */
    public String getBillToCounty()
    {
        return billToCounty;
    }

    /**
     * @param billToCounty
     *            the billToCounty to set
     */
    public void setBillToCounty(final String billToCounty)
    {
        this.billToCounty = (billToCounty == null) ? "" : billToCounty;
    }

    /**
     * @return the billToCountry
     */
    public String getBillToCountry()
    {
        return billToCountry;
    }

    /**
     * @param billToCountry
     *            the billToCountry to set
     */
    public void setBillToCountry(final String billToCountry)
    {
        this.billToCountry = (billToCountry == null) ? "" : billToCountry;
    }

    /**
     * @return the billToFax
     */
    public String getBillToFax()
    {
        return billToFax;
    }

    /**
     * @param billToFax
     *            the billToFax to set
     */
    public void setBillToFax(final String billToFax)
    {
        this.billToFax = (billToFax == null) ? "" : billToFax;
    }

    /**
     * @return the billToEmail
     */
    public String getBillToEmail()
    {
        return billToEmail;
    }

    /**
     * @param billToEmail
     *            the billToEmail to set
     */
    public void setBillToEmail(final String billToEmail)
    {
        this.billToEmail = (billToEmail == null) ? "" : billToEmail;
    }

    /**
     * @return the creditLine
     */
    public double getCreditLine()
    {
        return creditLine;
    }

    /**
     * @param creditLine
     *            the creditLine to set
     */
    public void setCreditLine(final double creditLine)
    {
        this.creditLine = creditLine;
    }

    /**
     * @return the creditExp
     */
    public String getCreditExp()
    {
        return creditExp;
    }

    /**
     * @param creditExp
     *            the creditExp to set
     */
    public void setCreditExp(final String creditExp)
    {
        this.creditExp = (creditExp == null) ? "" : creditExp;
    }

    /**
     * @return the creditAvail
     */
    public double getCreditAvail()
    {
        return creditAvail;
    }

    /**
     * @param creditAvail
     *            the creditAvail to set
     */
    public void setCreditAvail(final double creditAvail)
    {
        this.creditAvail = creditAvail;
    }

    /**
     * @return the creditComment
     */
    public String getCreditComment()
    {
        return creditComment;
    }

    /**
     * @param creditComment
     *            the creditComment to set
     */
    public void setCreditComment(final String creditComment)
    {
        this.creditComment = (creditComment == null) ? "" : creditComment;
    }

    /**
     * @return the shipToAddress1
     */
    public String getShipToAddress1()
    {
        return shipToAddress1;
    }

    /**
     * @param shipToAddress1
     *            the shipToAddress1 to set
     */
    public void setShipToAddress1(final String shipToAddress1)
    {
        this.shipToAddress1 = (shipToAddress1 == null) ? "" : shipToAddress1;
    }

    /**
     * @return the shipToAddress2
     */
    public String getShipToAddress2()
    {
        return shipToAddress2;
    }

    /**
     * @param shipToAddress2
     *            the shipToAddress2 to set
     */
    public void setShipToAddress2(final String shipToAddress2)
    {
        this.shipToAddress2 = (shipToAddress2 == null) ? "" : shipToAddress2;
    }

    /**
     * @return the billToAddress1
     */
    public String getBillToAddress1()
    {
        return billToAddress1;
    }

    /**
     * @param billToAddress1
     *            the billToAddress1 to set
     */
    public void setBillToAddress1(final String billToAddress1)
    {
        this.billToAddress1 = (billToAddress1 == null) ? "" : billToAddress1;
    }

    /**
     * @return the billToAddress2
     */
    public String getBillToAddress2()
    {
        return billToAddress2;
    }

    /**
     * @param billToAddress2
     *            the billToAddress2 to set
     */
    public void setBillToAddress2(final String billToAddress2)
    {
        this.billToAddress2 = (billToAddress2 == null) ? "" : billToAddress2;
    }
}
