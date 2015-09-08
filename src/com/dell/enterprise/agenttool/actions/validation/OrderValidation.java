/*
 * FILENAME
 *     OrderValidation.java
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

package com.dell.enterprise.agenttool.actions.validation;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dell.enterprise.agenttool.model.OrderCriteria;

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
 * @author linhdo
 * 
 * @version $Id$
 **/
public class OrderValidation
{
    private static final Log LOG = LogFactory.getLog(OrderValidation.class);

    public static int isEmptyOrderRes(final List<String> searchOrder)
    {
        int result = 0;
        for (int i = 0; i < searchOrder.size(); i++)
        {
            if (!searchOrder.get(i).equals(""))
            {
                result = 1;
                break;
            }
        }
        return result;
    }

    public static boolean checkShopCriteria(final OrderCriteria criteria)
    {
        boolean valid = false;
        if (!criteria.getOrderId().equals("") || !criteria.getOrderType().equals("") || !criteria.getItemSku().equals("") || !criteria.getCustomerId().equals("") || !criteria.getSfname().equals("")
            || !criteria.getSlname().equals("") || !criteria.getScom().equals("") || !criteria.getSphone().equals("") || !criteria.getBfname().equals("") || !criteria.getBlname().equals("")
            || !criteria.getBcom().equals("") || !criteria.getBphone().equals(""))
            valid = true;
        return valid;
    }
}
