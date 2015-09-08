/*
 * FILENAME
 *     OrderDAOImpl.java
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

package com.dell.enterprise.agenttool.util;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dell.enterprise.agenttool.model.OrderCriteria;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrderDAOImpl
{
    private static final Log log = LogFactory.getLog(OrderDAOImpl.class);
    private static final String AND = " and ";
    private static final String SPACE = " ";

    public static String filterOrderQuery(final String queryOne, final List<String> orderSearch)
    {
        String query = "";
        try
        {
            query = query.concat("(T1.shopper_id IS NOT NULL)");

            if (!orderSearch.get(0).equals(""))//ordernumber
                query = query + AND + "T1.orderNumber = '" + orderSearch.get(0) + "' ";

            if (!orderSearch.get(2).equals(""))//itemsku
                query = query + AND + "T1.orderNumber in (select orderNumber from orderline where item_sku like '%" + orderSearch.get(2).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\') ";

            if (!orderSearch.get(3).equals(""))//listing
                query = query + AND + "T1.listing like '%" + orderSearch.get(3).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(4).equals(""))//linknumber
                query = query + AND + "T3.shopper_number like '%" + orderSearch.get(4).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!orderSearch.get(6).equals(""))//ship_to_email
                query = query + AND + "T1.ship_to_email like '%" + orderSearch.get(6).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(7).equals(""))//ship_to_fname
                query = query + AND + "T1.ship_to_name like '%" + orderSearch.get(7).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(8).equals(""))//ship_to_lname
                query = query + AND + "T1.ship_to_name like '%" + orderSearch.get(8).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(9).equals(""))//ship_to_company
                query = query + AND + "T1.ship_to_company like '%" + orderSearch.get(9).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(10).equals(""))//ship_to_phone
                query = query + AND + "T1.ship_to_phone like '%" + orderSearch.get(10).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(11).equals(""))//bill_to_fname
                query = query + AND + "T1.bill_to_name like '%" + orderSearch.get(11).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(12).equals(""))//bill_to_lname
                query = query + AND + "T1.bill_to_name like '%" + orderSearch.get(12).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(13).equals(""))//bill_to_company
                query = query + AND + "T1.bill_to_company like '%" + orderSearch.get(13).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";

            if (!orderSearch.get(14).equals(""))//bill_to_phone
                query = query + AND + "T1.bill_to_phone like '%" + orderSearch.get(14).replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!orderSearch.get(15).equals(""))//Agent Store case
            	query = query + AND + "T1.AgentIdEnter=201 ";
            if (!orderSearch.get(5).equals(""))
            {
            	switch (Integer.valueOf(orderSearch.get(5)))
                {
                	//store
                    case 1:
                        query = query + AND + " (AgentIDEnter = '0' OR AgentIDEnter = '') ";
                        break;

                    //Agent
                    case 2:
                        query =
                            query
                                + AND
                                + " not ((AgentIDEnter is null) OR (AgentIDEnter = '-1') OR (AgentIDEnter = '') OR (AgentIDEnter = '0') OR (AgentIDEnter = '62') OR (AgentIDEnter = '63') OR (AgentIDEnter = '188') OR (AgentIDEnter = '204') OR (AgentIDEnter = '" + Constants.AGENT_STORE_ID + "')) ";
                        break;
                        
                    //Auction
                    case 3:
                        query = query + AND + " (AgentIDEnter = '62' or AgentIDEnter = '-1') ";
                        break;
                        
                    //eBay
                    case 4:
                    	query = query + AND + " (AgentIDEnter = '63' OR AgentIDEnter = '188') ";
                        break;
                        //eBay
                    case 5:
                        query = query + AND + " (AgentIDEnter = '" + Constants.AGENT_STORE_ID + "') ";                        
                        break;
                    //canada
                    case 6:
                    	query = query + AND + " (AgentIDEnter = '204') ";
                        break;
                        //canada
                    default:
                        break;
                }
            }
        }
        catch (Exception e)
        {
            log.info("CAN NOT BULIB QUERY STRING");
            e.printStackTrace();
        }

        return query;
    }

    public static String filterOrderQueryPage(final List<String> orderSearch)
    {
        String query = "";
        try
        {
            if (!orderSearch.get(1).equals(""))
            {
                switch (Integer.valueOf(orderSearch.get(1)))
                //order_type
                {
                    case 1:
                        query = " ship_to_name  ";
                        break;

                    case 2:
                        query = " COUNT(T2.OrderNumber) DESC  ";
                        break;
                    case 3:
                        query = " (T1.total_disc / T1.total_list * 100) DESC  ";
                        break;
                    case 4:
                        query = " total_total DESC ";
                        break;
                    default:
                        break;

                }
            }
            else
            {
                query = " T1.createdDate DESC  ";
            }
        }
        catch (Exception e)
        {
            log.info("CAN NOT BULIB QUERY STRING");
            e.printStackTrace();
        }

        return query;
    }

    public static String filterWhereShopper(final OrderCriteria criteria)
    {
        String query = " (T1.shopper_id IS NOT NULL) ";
        try
        {
            if (!criteria.getOrderId().equals(""))
                query += " and t1.orderNumber = '" + criteria.getOrderId() + "' ";
            if (!criteria.getCustomerId().equals(""))
                query += " and t2.shopper_number like '%" + criteria.getCustomerId().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getItemSku().equals(""))
                query += " and t1.orderNumber in (select orderNumber from orderline where item_sku like '%" + criteria.getItemSku().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\') ";
            if (!criteria.getBfname().equals(""))
                query += " and t1.bill_to_name like '%" + criteria.getBfname().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getBlname().equals(""))
                query += " and t1.bill_to_name like '%" + criteria.getBlname().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getBcom().equals(""))
                query += " and t1.bill_to_company like '%" + criteria.getBcom().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getBphone().equals(""))
                query += " and t1.bill_to_phone like '%" + criteria.getBphone().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getSfname().equals(""))
                query += " and t1.ship_to_name like '%" + criteria.getSfname().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getSlname().equals(""))
                query += " and t1.ship_to_name like '%" + criteria.getSlname().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getScom().equals(""))
                query += " and t1.ship_to_company like '%" + criteria.getScom().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
            if (!criteria.getSphone().equals(""))
                query += " and t1.ship_to_phone like '%" + criteria.getSphone().replace("_", "\\_").replace("%", "\\%") + "%' escape '\\' ";
        }
        catch (Exception e)
        {
            log.info("CAN NOT BULIB QUERY STRING");
            e.printStackTrace();
        }
        return query;
    }

    public static String filterOrderShopper(final OrderCriteria criteria)
    {
        String query = "";
        try
        {
            if (!criteria.getOrderType().equals(""))
            {
                switch (Integer.valueOf(criteria.getOrderType()))
                {
                    case 1:
                        query += " count(t1.OrderNumber) DESC ";
                        break;
                    case 2:
                        query += " sum(t1.total_total) DESC ";
                        break;
                    default:
                        query += " T2.ship_to_fname ";
                        break;

                }
            }
            else
            {
                query += " T2.ship_to_fname ";
            }
        }
        catch (Exception e)
        {
            log.info("CAN NOT BULIB QUERY STRING");
            e.printStackTrace();
        }

        return query;
    }
}
