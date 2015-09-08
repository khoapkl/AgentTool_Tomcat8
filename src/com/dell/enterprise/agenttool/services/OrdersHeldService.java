/*
 * FILENAME
 *     OrdersHeldService.java
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

import java.sql.Timestamp;
import java.util.List;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import com.dell.enterprise.agenttool.DAO.OrdersHeldDAO;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.model.OrdersHeld;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.MailUtils;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class OrdersHeldService
{
    public List<OrdersHeld> getOrdersHeld()
    {
        OrdersHeldDAO OrdersHeldDAO = new OrdersHeldDAO();
        return OrdersHeldDAO.getOrdersHeld();
    }

    public Boolean deleteOrderHeld(OrdersHeld ordersHeld)
    {
        OrdersHeldDAO OrdersHeldDAO = new OrdersHeldDAO();
        return OrdersHeldDAO.deleteOrderHeld(ordersHeld);
    }

    public Boolean sendMailOrderHeld(OrdersHeld ordersHeld)
    {
        Boolean flag = true;

        try
        {
            CustomerServices customerServices = new CustomerServices();
            BasketService basketService = new BasketService();

            OrderRow orderRow = basketService.getOrderRow(ordersHeld.getHeld_order());
            Customer customer = customerServices.getCustomerByShopperID(ordersHeld.getShopper_id());

            if (orderRow != null && customer != null && customer.getBill_to_email() != null && !customer.getBill_to_email().isEmpty())
            {
                MailUtils mailUtils = MailUtils.getInstance();
                //Create the email message
                HtmlEmail email = new HtmlEmail();
                email.setHostName(mailUtils.getHost());
                email.setSmtpPort(mailUtils.getSmtp_port());
                email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
                email.setTLS(true);
                //send email to agent
                email.setFrom(mailUtils.getFrom());
                email.addTo(customer.getBill_to_email(), customer.getBill_to_fname() + " " + customer.getBill_to_lname());

                email.setSubject("Order Hold Expires");

                String htmlContent = "";
                htmlContent = htmlContent.concat("Dear " + customer.getBill_to_fname() + " " + customer.getBill_to_lname() + ", <BR/>");
                htmlContent = htmlContent.concat("<BR/>");
                htmlContent = htmlContent.concat("We would like to inform to you that, your Hold Order will expired and removed on <Exp Date>.<BR/>");
                htmlContent = htmlContent.concat("Please look at detail below:<BR/>");
                htmlContent = htmlContent.concat("Held Order #: " + orderRow.getOrder_id() + "<BR/>");
                htmlContent = htmlContent.concat("Created Date: " + Constants.formatDate(new Timestamp(orderRow.getCreated().getTime()), "MM-dd-yyyy") + "<BR/>");
                htmlContent = htmlContent.concat("Exp Date: " + Constants.formatDate(ordersHeld.getExp_date(), "MM-dd-yyyy") + "<BR/>");
                htmlContent = htmlContent.concat("<BR/>");
                htmlContent = htmlContent.concat("Please contact to us to process this Hold Order.<BR/>");
                // set the html message
                email.setHtmlMsg(htmlContent);
                // send the email
                email.send();
            }
        }
        catch (Exception e)
        {
            flag = false;
            System.out.println("Error - Class OrdersHeldService - sendMailOrderHeld");
            e.printStackTrace();
            // TODO: handle exception
        }
        return flag;
    }

}
