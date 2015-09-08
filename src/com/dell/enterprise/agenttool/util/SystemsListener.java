/*
 * FILENAME
 *     CleanHoldOrdersListener.java
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;
import java.util.Calendar;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.dell.enterprise.agenttool.model.OrdersHeld;
import com.dell.enterprise.agenttool.model.EmailShipNotify;
import com.dell.enterprise.agenttool.model.Security;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.HistoryPasswordService;
import com.dell.enterprise.agenttool.services.OrdersHeldService;
import com.dell.enterprise.agenttool.services.ShipEmailServices;
import com.dell.enterprise.agenttool.services.SecurityService;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class SystemsListener implements ServletContextListener
{

    Timer timer = new Timer();
    public static final int SECOND = 1000;
    public static final int MINUTE = SECOND * 60;
    public static final int HOUR = MINUTE * 60;
    public static final int DAY = HOUR * 24;

    @Override
    public void contextDestroyed(ServletContextEvent sce)
    {
        // TODO Auto-generated method stub
        timer.cancel();
        System.out.println("ServletContextListener destroyed");
    }

    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        // TODO Auto-generated method stub
    	//addSecuritySetting addSecurity = new addSecuritySetting();
    	//addSecurity.addSecurity();
        ToDoTaskCleanOrderHeld toDoTaskCleanOrderHeld = new ToDoTaskCleanOrderHeld(sce.getServletContext());
        ToDoTaskCleanBasket toDoTaskCleanBasket = new ToDoTaskCleanBasket(sce.getServletContext());
        ToDoTaskCleanHistoryPassword historyPassword =  new ToDoTaskCleanHistoryPassword(sce.getServletContext());
        ToDoTaskShipEmailNotification toDoTaskShipEmail = new ToDoTaskShipEmailNotification(sce.getServletContext());

        timer.schedule(toDoTaskCleanOrderHeld, 0, 45 * MINUTE);
        timer.schedule(toDoTaskCleanBasket, 0, 1 * MINUTE);
        timer.schedule(historyPassword, 0, 1 * MINUTE);
        timer.schedule(toDoTaskShipEmail, 0, 2 * MINUTE);
        System.out.println("ServletContextListener started");	
        
        /*call to update password Agent of database current*/
        /*end update password Agent of database current*/
        

    }
}

class ToDoTaskCleanOrderHeld extends TimerTask
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.util.ToDoTask");
    @SuppressWarnings("unused")
    private ServletContext context = null;

    public ToDoTaskCleanOrderHeld(ServletContext context)
    {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public void run()
    {
        Date currentDate = new Date();
        LOGGER.info("Execute ToDoTask - run - " + currentDate.toString() + "- Hour " + currentDate.getHours());

        //Orders Hold
        OrdersHeldService ordersHeldService = new OrdersHeldService();
        List<OrdersHeld> listOrdersHeld = ordersHeldService.getOrdersHeld();

        for (OrdersHeld ordersHeld : listOrdersHeld)
        {   
        	LOGGER.info("Delete Order Hold - OrdersHeld ID : " + ordersHeld.getExp_days()+currentDate.getHours());
            if (ordersHeld.getExp_days() == 1)
            {
                //Send Mail in 1AM
               // if (currentDate.getHours() == 23)
              //  {
                    //Send email for customer expires
                    //ordersHeldService.sendMailOrderHeld(ordersHeld);
                //}
            }
            else
            {
                LOGGER.info("Delete Order Hold - OrdersHeld ID : " + ordersHeld.getHeld_order());
                //Delete Order Hold
                ordersHeldService.deleteOrderHeld(ordersHeld);
            }
        }
    }
}


class ToDoTaskShipEmailNotification extends TimerTask
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.util.ToDoTask");
    @SuppressWarnings("unused")
    private ServletContext context = null;

    public ToDoTaskShipEmailNotification(ServletContext context)
    {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public void run()
    {
        Date currentDate = new Date();
        Calendar run_hour = Calendar.getInstance();
        
        LOGGER.info("Execute Shipping Email Notification - run - " + currentDate.toString() + "- Hour " + run_hour.get(Calendar.HOUR_OF_DAY) + " Minutes: " + run_hour.get(Calendar.MINUTE));

        //Orders Hold
        ShipEmailServices ordersShipEmailService = new ShipEmailServices();
        List<EmailShipNotify> listOrdersShipEmail = ordersShipEmailService.getShipEmailQueue();

        for (EmailShipNotify ordersShipEmail : listOrdersShipEmail)
        {   
            
        	//Send Mail in 1AM
        	if (run_hour.get(Calendar.HOUR_OF_DAY) > 20)
        	{
        		//Send shipping notification email
        		ordersShipEmailService.sendMailShipNotify(ordersShipEmail);
        		ordersShipEmailService.UpdateEmailFlag(ordersShipEmail.getOrderNumber());
        	}
        }
    }
}

class ToDoTaskCleanBasket extends TimerTask
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.util.ToDoTaskCleanBasket");
    @SuppressWarnings("unused")
    private ServletContext context = null;

    public ToDoTaskCleanBasket(ServletContext context)
    {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public void run()
    {
        Date currentDate = new Date();
        int minutes = currentDate.getMinutes();
        int hours = currentDate.getHours();
        LOGGER.info("Execute ToDoTaskCleanBasket - run - " + currentDate.toString() + "- Hour : " + hours + " - Minute : " + minutes);

        BasketService basketService = new BasketService();
        basketService.cleanBasketItemExp();
    }
}
class ToDoTaskCleanHistoryPassword extends TimerTask
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.util.ToDoTaskCleanHistoryPassword");
    @SuppressWarnings("unused")
    private ServletContext context = null;

    public ToDoTaskCleanHistoryPassword(ServletContext context)
    {
        this.context = context;
    }

    @SuppressWarnings("deprecation")
    public void run()
    {   
        Date currentDate = new Date();
        int minutes = currentDate.getMinutes();
        int hours = currentDate.getHours();
    	SecurityService securityservice = new SecurityService();
    	Security security= null ;
    	security = securityservice.getSecurity(1);
    	Date resetHistoryDate = security.getResetHistoryDate();

        if (securityservice.addMonth(resetHistoryDate,
				security.getResetHistory()).compareTo(currentDate) == -1) {
        HistoryPasswordService historyService = new HistoryPasswordService();
        Date currentTime = new Date();
        String resetHistorycurrent = securityservice.formatDate(currentTime);
        
        historyService.deleteHistoryPassword();
        securityservice.updateResetHistoryDate(1, resetHistorycurrent);
        
        }
    }
}

class addSecuritySetting 
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.util.addSecuritySetting");
    public void addSecurity()
    {
    	SecurityService securityservice = new SecurityService();
    	Security security= null ;
 		try{	 

             int lockoutCount = 5;
             int lockoutTime = 15;
             int expiryDays = 30;
             int charNumber = 8;
             int resetHistory = 12;
             boolean isUppercase = false;
             boolean isNumber = false;
             boolean isSymbol = false;
             
             Date currentTime = new Date();
             String resetHistoryDate = securityservice.formatDate(currentTime);
             securityservice.deleteSecurity();
        	 securityservice.addSecurity(1, lockoutTime, lockoutCount, expiryDays, charNumber, resetHistory , isUppercase, isNumber, isSymbol , resetHistoryDate);
	         
           
 		}catch (Exception e) {
			// TODO: handle exception
 			 
		}
 		
    }
}