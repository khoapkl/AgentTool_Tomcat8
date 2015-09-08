/*
 * FILENAME
 *     ShopperListService.java
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

package com.dell.enterprise.agenttool.services;

import java.util.List;

import com.dell.enterprise.agenttool.DAO.ShopperListDAO;
import com.dell.enterprise.agenttool.model.Shopper;

/**
 * @author hungnguyen
 **/
public class ShopperListService
{
    /**
     * @param pageNumber
     *            pageNumber
     * @param searchCriteria
     *            search criteria
     * @return list of shoppers
     **/
    public List<Shopper> getShoppers(final int pageNumber, final Shopper searchCriteria)
    {
        ShopperListDAO dao = new ShopperListDAO();

        return dao.getShoppers(pageNumber, searchCriteria);
    }

    /**
     * @return int
     **/
    public int getTotalRowCount()
    {
        ShopperListDAO dao = new ShopperListDAO();

        return dao.getTotalRowCount();
    }
}
