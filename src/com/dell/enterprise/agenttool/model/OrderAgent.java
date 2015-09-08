/*
 * FILENAME
 *     OrderAgent.java
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

import java.math.BigDecimal;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrderAgent extends OrderDate
{
    private String agent;
    private int agentId;
    private int numUnit;
    private BigDecimal unitAsp;
    private int Id;

    /**
     * @return the agent
     */
    public String getAgent()
    {
        return agent;
    }

    /**
     * @param agent
     *            the agent to set
     */
    public void setAgent(String agent)
    {
        this.agent = agent;
    }

    /**
     * @return the agentId
     */
    public int getAgentId()
    {
        return agentId;
    }

    /**
     * @param agentId
     *            the agentId to set
     */
    public void setAgentId(int agentId)
    {
        this.agentId = agentId;
    }

    /**
     * @return the numUnit
     */
    public int getNumUnit()
    {
        return numUnit;
    }

    /**
     * @param numUnit
     *            the numUnit to set
     */
    public void setNumUnit(int numUnit)
    {
        this.numUnit = numUnit;
    }

    /**
     * @return the unitAsp
     */
    public BigDecimal getUnitAsp()
    {
        return unitAsp;
    }

    /**
     * @param unitAsp
     *            the unitAsp to set
     */
    public void setUnitAsp(BigDecimal unitAsp)
    {
        this.unitAsp = unitAsp;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return Id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id)
    {
        Id = id;
    }
}
