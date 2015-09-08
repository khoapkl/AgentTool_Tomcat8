/*
 * FILENAME
 *     Converter.java
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

import java.math.BigDecimal;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.sun.xml.internal.bind.v2.TODO;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * 
 * @see TODO Related Information
 * 
 * @author linhdo
 * 
 * @version $Id$
 **/
public class Converter
{
    /**
     * Method getDateString.
     * 
     * @param dt
     *            Date
     * @return String
     * @author linhdo
     */
    public static String getDateString(final Date dt)
    {
        String s = "";
        DateFormat formatter;
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        s = formatter.format(dt);
        return s;
    }

    /**
     * Method isEmpty.
     * 
     * @param obj
     *            String
     * @return boolean
     */
    public static boolean isEmpty(final String obj)
    {
        return null == obj || "".equals(obj.trim());
    }

    /**
     * Method getString.
     * 
     * @param str
     *            String
     * @return String
     * @author linhdo
     */
    public static String getString(String str)
    {
        if (isEmpty(str))
            str = "";
        return str;
    }

    /**
     * Method getMoney.
     * 
     * @param price
     *            BigDecimal
     * @return String
     * @author linhdo
     */
    public static String getMoney(final BigDecimal price)
    {
        String s = "";
        if (price != null)
        {
            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
            double doublePayment = price.doubleValue();
            s = n.format(doublePayment);
        }
        else
            s = "0.00";
        return s;
   }


    /**
     * Method getMoney.
     * 
     * @param price
     *            BigDecimal
     * @return String
     * @author linhdo
     */
    public static String getMoneyNonePoint(final BigDecimal price)
    {
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        double doublePayment = price.doubleValue();
        String s = n.format(doublePayment);
        s = s.substring(0, s.length() - 3);
        return s;
    }

    /**
     * Method getHeldDiscount.
     * 
     * @param plprice
     *            Integer
     * @param moprice
     *            Integer
     * @return Integer
     * @author linhdo
     */
    public static int getHeldDiscount(final int plprice, final int moprice)
    {
        int percent = 0;
        double temp = 0;
        if (moprice > 0)
            temp = (moprice - (plprice / 100.0)) / moprice * 100;
        percent = (int) Math.round(temp);
        return percent;
    }

    /**
     * Method getDiscount.
     * 
     * @param discount
     *            discount
     * @return Integer
     * @author linhdo
     */
    public static int getDiscount(final BigDecimal discount)
    {
        int percent = 0;
        double temp = discount.doubleValue();
        percent = (int) Math.round(temp);
        return percent;
    }

    /**
     * Method getTimeString.
     * 
     * @param dt
     *            Date
     * @return String
     * @author linhdo
     */
    public static String getTimeString(final Time dt)
    {
        String s = "";
        DateFormat formatter;
        formatter = new SimpleDateFormat("h:mm a");
        s = formatter.format(dt);
        return s;
    }

    /**
     * Method stringToBoolean.
     * 
     * @author huyngo
     * @param str
     *            str
     * @return boolean
     */
    public static boolean stringToBoolean(final String str)
    {
        boolean i = false;
        try
        {
            i = Boolean.parseBoolean(str.trim());
        }
        catch (Exception nfe)
        {
            System.out.println("Exception: " + nfe.getMessage());
        }
        return i;
    }

    /**
     * Method stringToInt.
     * 
     * @author huyngo
     * @param str
     *            str
     * @return int
     */
    public static int stringToInt(final String str)
    {
        int i = 0;
        if (str != null)
        {
            try
            {
                i = Integer.parseInt(str.trim());
            }
            catch (Exception nfe)
            {
                System.out.println("Exception: " + nfe.getMessage());
                i = Integer.MAX_VALUE;
            }
        }
        return i;
    }

    /**
     * Method stringToDate.
     * 
     * @author huyngo
     * @param str
     *            str
     * @return Date
     */
    public static Date stringToDate(final String str)
    {
        DateFormat formatter;
        Date date = null;
        if (str != null)
        {
            try
            {
                formatter = new SimpleDateFormat("MM/dd/yyyy");
                date = (Date) formatter.parse(str);
            }
            catch (Exception nfe)
            {
                System.out.println("Exception: " + nfe.getMessage());
            }
        }
        return date;
    }

    public static String dateDBToDate(final Date str)
    {
        DateFormat formatter;
        String date = null;
        if (str != null)
        {
            try
            {
                formatter = new SimpleDateFormat("MM/dd/yyyy");
                date = formatter.format(str);

            }
            catch (Exception nfe)
            {
                System.out.println("Exception: " + nfe.getMessage());
            }
        }
        return date;
    }

    /**
     * Method getTimeString.
     * 
     * @param str
     *            Date
     * @return String
     * @author linhdo
     */
    public static String dateToTimeStamp(final Date str)
    {
        DateFormat formatter;
        String date = null;
        if (str != null)
        {
            try
            {
                formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = formatter.format(str);
            }
            catch (Exception nfe)
            {
                System.out.println("Exception: " + nfe.getMessage());
            }
        }
        return date;
    }

    /**
     * Method stringToBigDecimal.
     * 
     * @author huyngo
     * @param str
     *            str
     * @return BigDecimal
     */
    public static BigDecimal stringToBigDecimal(final String str)
    {
        BigDecimal i = null;
        try
        {
            i = new BigDecimal(str.trim());
        }
        catch (Exception nfe)
        {
            System.out.println("Exception: " + nfe.getMessage());
        }
        return i;
    }

    /**
     * Method getSaleType.
     * 
     * @param dt
     *            Date
     * @return String
     * @author linhdo
     */
    public static String getSaleType(String agentId)
    {
        String str = "";
        if (agentId.equals("") || agentId.equals("0"))
            str = "Store";
        else if (agentId.equals("62") || agentId.equals("-1"))
            str = "Auction";
        else if (agentId.equals("63") || agentId.equals("188"))
            str = "eBay";
        else
            str = ("Agent");
        return str;
    }

    /**
     * Method Integer to String.
     * 
     * @param dt
     *            Date
     * @return String
     * @author linhdo
     */
    public static String stringOfNumber(final int number)
    {
        String s = "";
        if (number != 0)
            s = String.valueOf(number);
        else
            s = "";
        return s;
    }

    public static String changeRequest(String srt)
    {
        String str = "";
        str = (Integer.valueOf(srt) >= 10) ? srt : "0" + srt;
        return str;
    }
    
    
    public static boolean isEqualOrGreatThanToday(Date date) 
    {
        Calendar today = Calendar.getInstance();
        today.setTime(new Date());
        
        Calendar otherday = Calendar.getInstance();
        otherday.setTime(date);
        
        return (otherday.get(Calendar.YEAR) == today.get(Calendar.YEAR)
            && otherday.get(Calendar.MONTH) == today.get(Calendar.MONTH)
            && otherday.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) || otherday.after(today);
    }
}
