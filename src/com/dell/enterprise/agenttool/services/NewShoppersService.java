/*
 * FILENAME
 *     NewShoppersByYearService.java
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
import java.util.Map;

import com.dell.enterprise.agenttool.DAO.NewShoppersDAO;
import com.dell.enterprise.agenttool.model.Shopper;

/**
 * @author hungnguyen
 **/
public class NewShoppersService
{

    /**
     * @param year
     *            year
     * @return Map<String, String>
     **/
    public Map<Integer, String> getNewShoppersByYear(final int year)
    {
        NewShoppersDAO dao = new NewShoppersDAO();

        return dao.getNewShoppersByYear(year);
    }

    /**
     * @param year
     *            year
     * @param month
     *            month
     * @return Map<Integer, String>
     **/
    public Map<Integer, Integer> getNewShoppersByMonth(final int year, final int month)
    {
        NewShoppersDAO dao = new NewShoppersDAO();

        return dao.getNewShoppersByMonth(year, month);
    }

    /**
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @param page
     *            page
     * @return List<Shopper>
     **/
    public List<Shopper> getNewShoppersByDay(final int year, final int month, final int day, final int page)
    {
        NewShoppersDAO dao = new NewShoppersDAO();

        return dao.getNewShoppersByDay(year, month, day, page);
    }

    /**
     * @return int
     **/
    public int getNewShoppersByDayCount()
    {
        NewShoppersDAO dao = new NewShoppersDAO();

        return dao.getNewShoppersByDayCount();
    }

    /**
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @return List<Shopper>
     **/
    public int countNewShoppersByDay(final int year, final int month, final int day)
    {
        NewShoppersDAO dao = new NewShoppersDAO();

        return dao.countNewShoppersByDay(year, month, day);
    }

}
