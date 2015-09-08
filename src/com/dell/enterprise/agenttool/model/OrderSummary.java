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

import com.sun.xml.internal.bind.v2.TODO;

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
public class OrderSummary implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;

    private Summary Agent;
    private Summary Store;
    private Summary Auction;
    private Summary Ebay;
    private Summary Customer;

    /**
     * @return the agent
     */
    public Summary getAgent()
    {
        return Agent;
    }

    /**
     * @param agent
     *            the agent to set
     */
    public void setAgent(Summary agent)
    {
        Agent = agent;
    }

    /**
     * @return the store
     */
    public Summary getStore()
    {
        return Store;
    }

    /**
     * @param store
     *            the store to set
     */
    public void setStore(Summary store)
    {
        Store = store;
    }

    /**
     * @return the auction
     */
    public Summary getAuction()
    {
        return Auction;
    }

    /**
     * @param auction
     *            the auction to set
     */
    public void setAuction(Summary auction)
    {
        Auction = auction;
    }

    /**
     * @return the ebay
     */
    public Summary getEbay()
    {
        return Ebay;
    }

    /**
     * @param ebay
     *            the ebay to set
     */
    public void setEbay(Summary ebay)
    {
        Ebay = ebay;
    }

    /**
     * @return the customer
     */
    public Summary getCustomer()
    {
        return Customer;
    }

    /**
     * @param customer
     *            the customer to set
     */
    public void setCustomer(Summary customer)
    {
        Customer = customer;
    }

}
