/*
 * FILENAME
 *     OrderHeld.java
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


public class EmailShipNotify implements Serializable
{
	private static final long serialVersionUID = 1L;
    private String order_number;
    private Timestamp date_complete;
    private String ship_to_company;
    private String ship_to_address1;
    private String ship_to_address2;
    private String ship_to_city;
    private String ship_to_postal;
    private String ship_to_state;
    private String ship_to_phone;
    private String ship_to_email;
    private String ship_to_name;
    private String agent_name;
    private String agent_email;
    
    
    public String getOrderNumber()
    {
        return order_number;
    }

    
    public void setOrderNumber(String orderNumber)
    {
    	order_number = orderNumber;
    }

    
    public Timestamp getShippedDate()
    {
        return date_complete;
    }

    
    public void setShippedDate(Timestamp shippedDate)
    {
    	date_complete = shippedDate;
    }
    
    public String getShipToCompany()
    {
        return ship_to_company;
    }

    
    public void setShipToCompany(String shipToCompany)
    {
        ship_to_company = shipToCompany;
    }
    
    
    public String getShipToAddress1()
    {
        return ship_to_address1;
    }

    
    public void setShipToAddress1(String shipToAddress1)
    {
        ship_to_address1 = shipToAddress1;
    }
    
    
    public String getShipToAddress2()
    {
        return ship_to_address2;
    }

    
    public void setShipToAddress2(String shipToAddress2)
    {
        ship_to_address2 = shipToAddress2;
    }
    
    
    public String getShipToCity()
    {
        return ship_to_city;
    }

    
    public void setShipToCity(String shipToCity)
    {
        ship_to_city = shipToCity;
    }
    
    
    public String getShipToPostal()
    {
        return ship_to_postal;
    }

    
    public void setShipToPostal(String shipToPostal)
    {
        ship_to_postal = shipToPostal;
    }
    
    public String getShipToState()
    {
        return ship_to_state;
    }

    
    public void setShipToState(String shipToState)
    {
        ship_to_state = shipToState;
    }
    
    public String getShipToPhone()
    {
        return ship_to_phone;
    }

    
    public void setShipToPhone(String shipToPhone)
    {
        ship_to_phone = shipToPhone;
    }
    
    public String getShipToEmail()
    {
        return ship_to_email;
    }

    
    public void setShipToEmail(String shipToEmail)
    {
        ship_to_email = shipToEmail;
    }
    
    public String getShipToName()
    {
        return ship_to_name;
    }

    
    public void setShipToName(String shipToName)
    {
        ship_to_name = shipToName;
    }
    
    public String getAgentName()
    {
        return agent_name;
    }

    
    public void setAgentName(String agentName)
    {
        agent_name = agentName;
    }
    
    public String getAgentEmail()
    {
        return agent_email;
    }

    
    public void setAgentEmail(String agentEmail)
    {
        agent_email = agentEmail;
    }
    
}
