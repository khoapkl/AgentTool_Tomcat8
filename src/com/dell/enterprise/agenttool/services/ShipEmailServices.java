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

import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;

import com.dell.enterprise.agenttool.DAO.EmailShipDAO;
import com.dell.enterprise.agenttool.model.EmailShipNotify;
import com.dell.enterprise.agenttool.model.ShippedItem;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.MailUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class ShipEmailServices
{
    public List<EmailShipNotify> getShipEmailQueue()
    {
        EmailShipDAO emailShipDAO = new EmailShipDAO();
        return emailShipDAO.getShipEmailQueue();
    }

    public Boolean UpdateEmailFlag(String orderNumber)
    {
    	EmailShipDAO emailShipDAO = new EmailShipDAO();
        return emailShipDAO.UpdateEmailFlag(orderNumber);
    }
    
    public List<ShippedItem> getShippedItems(String order_number)
    {
        EmailShipDAO emailShippedDAO = new EmailShipDAO();
        return emailShippedDAO.getShippedItems(order_number);
    }
    
    public static boolean isValidEmailAddress(String email) {
    	   boolean result = true;
    	   try {
    	      InternetAddress emailAddr = new InternetAddress(email);
    	      emailAddr.validate();
    	   } catch (AddressException ex) {
    	      result = false;
    	   }
    	   return result;
    	}
    
	public Boolean sendMailShipNotify(EmailShipNotify emailNotify)
    {
        Boolean flag = true;
        SimpleDateFormat shipdate_format = new SimpleDateFormat("MM-dd-yyyy");
        Date today = new Date();
        String shippedDate = "";
        String emailAddress = "";
        String shippedName = "";
        
        try
        {
            MailUtils mailUtils = MailUtils.getInstance();

            List<ShippedItem> listShippedItems = getShippedItems(emailNotify.getOrderNumber());

            String items = "";
            
            if (emailNotify.getShippedDate() == null) {
            	shippedDate = shipdate_format.format(today);
            }
            else {
            	shippedDate = shipdate_format.format(emailNotify.getShippedDate());
            }
            
            if (isValidEmailAddress(emailNotify.getShipToEmail())){
            	emailAddress = emailNotify.getShipToEmail().trim();
            }
            else {
            	emailAddress = "tphan@magrabbit.com";
            }
            
            if ((emailNotify.getShipToName() == null) || (emailNotify.getShipToName().trim().length() == 0)){
            	shippedName = "Customer";
            }
            else {
            	shippedName = emailNotify.getShipToName();
            }

            for (ShippedItem emailShippedItem : listShippedItems)
            {
                items =
                    items.concat("<tr align=\"center\">").concat("<td align=\"center\">" + emailShippedItem.getShipmentID() + "</td>").concat(
                        "<td>" + emailShippedItem.getItemSku() + "</td>").concat("<td align=\"left\">" + emailShippedItem.getTrackingNumber() + "</td>").concat("<td align=\"left\">" + emailShippedItem.getItemName() + "</td>").concat("</tr>");
            }

            //Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailUtils.getHost());
            email.setSmtpPort(mailUtils.getSmtp_port());
            email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
            email.setTLS(true);
            //send email to agent
            email.setFrom(mailUtils.getFrom());

            email.addTo(emailAddress, shippedName);
            email.setSubject("Dell Financial Services Shipment Confirmation");

            String htmlContent =
                "<div style=\"font-size:12px;font-family:Courier New\">" + "Dear "
                    + shippedName
                    + ","
                    + "<BR/>"
                    + "Thank you for your recent order from Dell Financial Services.  Your order (#"
                    + emailNotify.getOrderNumber()
                    + ") was shipped on " + shippedDate + ". <BR/>"
                    + "<BR/>The tracking number/s and reference number for the shipment is listed below. You may check the status of your order by following the shipment status link below once the order has been placed with the carrier for shipment." + "<BR/>"
                    + "<BR/>---------------------------------<BR/>"
                    + "<BR/>" + "ORDER INFORMATION" + "<BR/>" + "Order #: " + emailNotify.getOrderNumber() + "<BR/>" + "Order Items: (" + listShippedItems.size() + "):" + "<BR/>"
                    + "---------------------------------<BR/>"
                    + "<table style=\"font-size:11px;font-family:Courier New\">" + "<tr> <td colspan=\"3\"> <hr width=\"100%\" style=\"border-top: 1px dashed #000000;color:#FFFFFF\" /><td> </tr>"
                    + "<tr>" + "<th>Package</th>" + "<th>Service Tag</th>" + "     <th>Tracking Number</th>" + "     <th>Description</th>" + "</tr>"
                    + "<tr> <td colspan=\"4\"> <hr width=\"100%\" style=\"border-top: 1px dashed #000000;color:#FFFFFF\" /><td> </tr>" + items
                    + "<tr> <td colspan=\"4\"> <hr width=\"100%\" style=\"border-top: 1px dashed #000000;color:#FFFFFF\" /><td> </tr>" + "</table>"
                    + "Shipment Status Link: http://www.fedex.com/fedextrack/?action=track&cntry_code=us" + "<BR/>"
                    + "<BR/>" + "Your order is being shipped to:" + "<BR/>" + "<BR/>"
                    + Constants.collapseRowBR("<b>" + shippedName + "</b>", "") + Constants.collapseRowBR(emailNotify.getShipToCompany(), "")
                    + Constants.collapseRowBR(emailNotify.getShipToAddress1(), "") + Constants.collapseRowBR(emailNotify.getShipToAddress2(), "")
                    + Constants.collapseRowBR(emailNotify.getShipToCity() + ", " + emailNotify.getShipToState() + ", " + emailNotify.getShipToPostal(), "")
                    + Constants.collapseRowBR(emailNotify.getShipToPhone(),"")
                    + "<BR/>" + "Notes:" + "<BR/>"
                    + "-Transit times will vary based on location and the ship method selected" + "<BR/>"
                    + "-Estimated Transit Times" + "<BR/>"
                    + "     o  Ground = 5 to 7 business days from " + shippedDate + "<BR/>"
                    + "     o  2nd Day Air = 2 business days from " + shippedDate + "<BR/>"
                    + "     o  Next Day Air = 1 business day from " + shippedDate + "<BR/>"
                    + "-If your order has not arrived within the estimated delivery times, please feel free to contact us for help researching your delivery." + "<BR/>" + "<BR/>"
                    + "Again, thank you for your order with Dell Financial Services." + "<BR/>" + "<BR/>" + "Sincerely," + "<BR/>" + emailNotify.getAgentName() + "<BR/>" + "Email: " + emailNotify.getAgentEmail()
                    + "</div>";
            // set the html message
            email.setHtmlMsg(htmlContent);

            // send the email
            email.send();
        }
        catch (Exception e)
        {
            flag = false;
            e.printStackTrace();
            // TODO: handle exception
        }
        return flag;
    }
}
