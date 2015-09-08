/*
Constants.FormatCurrency * FILENAME
 *     Checkout3.java
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

package com.dell.enterprise.agenttool.actions;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Avg_mhz;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EppPromotionRow;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.OrderHeader;
import com.dell.enterprise.agenttool.model.OrderHeld;
import com.dell.enterprise.agenttool.model.OrderLine;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.model.PayMethods;
import com.dell.enterprise.agenttool.model.TaxTables;
import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.services.OrderServices;
import com.dell.enterprise.agenttool.services.ProductServices;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;
import com.dell.enterprise.agenttool.util.MailUtils;

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
 * @author vinhhq
 * 
 * @version $Id$
 **/
public class Checkout3 extends DispatchAction
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.Checkout3");

    private String url;

    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;

        try
        {
            HttpSession sessions = request.getSession();
            if (sessions.getAttribute(Constants.SHOPPER_ID) == null)
            {
                forward = mapping.findForward(Constants.SESSION_TIMEOUT);
                Cookie[] cookies = request.getCookies();
                if (cookies != null)
                {
                    for (int i = 0; i < cookies.length; i++)
                    {
                        if (cookies[i].getName().equals("userLevel"))
                        {
                            if (cookies[i].getValue().equals("login.do"))
                                forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                        }
                    }
                }
            }
            else
            {
                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);
                if (method == null || method.isEmpty())
                {
                    forward = this.dispatchMethod(mapping, form, request, response, "showCheckout");
                }
                else
                {
                    forward = this.dispatchMethod(mapping, form, request, response, method);
                }
            }
        }
        catch (Exception e)
        {
            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
        }

        return forward;
    }

    public ActionForward showCheckout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_CHECKOUT_3;

        try
        {
            LOGGER.info("Action showCheckout - Checkout 3");

            HttpSession sessions = request.getSession();
            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                String shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
                Agent agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);
                Boolean byAgent = true;
                if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
                {
                    byAgent = false;
                }
                else
                {
                    byAgent = true;
                }

                if (!Constants.isNumber(shopper_id))
                {

                    /*
                     * Load data for checkout
                     */
                    //get estore_basket_item
                    CheckoutService checkoutService = new CheckoutService();
                    List<EstoreBasketItem> basketItemCheck = checkoutService.getItemCheck(shopper_id, byAgent);

                    //get customer
                    CustomerServices customerServices = new CustomerServices();
                    Customer customer = customerServices.getCustomerByShopperID(shopper_id);

                    //Get order
                    BasketService basketService = new BasketService();
                    OrderRow orderRow = basketService.getOrder(shopper_id, agent.getAgentId());

                    //LOGGER.info("Action showCheckout Debug zip : " + customer.getShip_to_postal());
                    //get taxtables
                    TaxTables taxTables = checkoutService.getTaxTables(customer.getShip_to_postal(), customer.getShip_to_city());

                    //Get EppPromotion 
                    EppPromotionRow eppPromotionRow = basketService.getEppPromotionRow(orderRow.getEpp_id());

                    //getOrderReceipt
                    //OrderHeader orderReceipt = checkoutService.getOrderReceipt(shopper_id, orderRow.getOrder_id());
                    //request.setAttribute(Constants.ATTR_ORDER_RECEIPT, orderReceipt);

                    //get List Payment Method
                    List<PayMethods> listPayMethods = checkoutService.getPayMethods();

                    //get hold days 
                    int holdDays = checkoutService.getHoldDays();

                    request.setAttribute(Constants.ATTR_ITEM_BASKET, basketItemCheck);
                    request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                    request.setAttribute(Constants.ATTR_ORDER_ROW, orderRow);
                    request.setAttribute(Constants.ATTR_EPP_PROMOTION, eppPromotionRow);
                    request.setAttribute(Constants.ATTR_PAY_METHODS, listPayMethods);
                    request.setAttribute(Constants.ATTR_TAX_TABLES, taxTables);
                    request.setAttribute(Constants.ATTR_HOLD_DAYS, holdDays);

                    if (isCustomer == null)
                    {
                        request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                    }
                }
            }
            setUrl(forward);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
    }

    /**
     * @param url
     *            the url to set
     */
    public void setUrl(String url)
    {
        this.url = url;
    }

    /**
     * @return the url
     */
    public String getUrl()
    {
        return url;
    }

    public ActionForward submitOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_CHECKOUT_FINAL;
        HttpSession session = request.getSession();
        Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
        List<String> errorList = new ArrayList<String>();
        Boolean byAgent = true;
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
            byAgent = false;
        }
        else
        {
            byAgent = true;
        }

        try
        {
            request.setAttribute("TMP_CSC", request.getParameter("_cc_cvv2"));
            request.setAttribute("TMP_PAYMENT_INFO", request.getParameter("terms"));

            LOGGER.info("Action submitOrder - Checkout 3");

            String order_id = "";
            String shopper_id = "";
            String avs = "N/A";
            String zip = "N/A";
            String csc = "N/A";
            
            List<OrderLine> listOrderLine = null;
            OrderHeader orderHeader = null;
            CheckoutService checkoutService = new CheckoutService();

            CustomerServices customerServices = new CustomerServices();

            if (request.getParameter("OrderNumber") != null)
            {
                order_id = request.getParameter("OrderNumber").toString();
            }

            if (request.getParameter("Shopper_id") != null)
            {
                shopper_id = request.getParameter("Shopper_id").toString();
            }

            orderHeader = getOrderHeader(form, request);
            	
            listOrderLine = getOrderLine(form, request);
            Avg_mhz avgMhz = getAvgMhz(form, request);

            Boolean flag = true;
            Map<String, String> objResponse = null;
            if (orderHeader.getPayment_terms().equals("Credit"))
            {
                objResponse = checkoutService.verifyCard(orderHeader);
                objResponse.put("PaymentType", "Credit");
                /*
                if ((objResponse.containsKey("RESPMSG") && objResponse.get("RESPMSG").equals("Approved")) && (objResponse.containsKey("AUTHCODE") && objResponse.get("AUTHCODE").length() == 6)
                    && (objResponse.containsKey("AVSADDR") && objResponse.get("AVSADDR").equals("Y")) && (objResponse.containsKey("AVSZIP") && objResponse.get("AVSZIP").equals("Y"))
                    && (objResponse.containsKey("CVV2MATCH") && objResponse.get("CVV2MATCH").equals("Y")))
                */
                if ((objResponse.containsKey("RESPMSG") && objResponse.get("RESPMSG").equals("Approved")) && (objResponse.containsKey("AUTHCODE") && objResponse.get("AUTHCODE").length() == 6)) 
                {
                	orderHeader.setApprovalNumber(objResponse.get("AUTHCODE"));
                    flag = true;
                }
                else
                {
                    flag = false;
                    String mess = objResponse.get("RESPMSG").toString();
                    
                    if (objResponse.get("AVSADDR") != null) avs = objResponse.get("AVSADDR");
                    if (objResponse.get("AVSZIP") != null) zip = objResponse.get("AVSZIP");
                    if (objResponse.get("CVV2MATCH") != null) csc = objResponse.get("CVV2MATCH");
                    
                    if (mess.contains("expiration date"))
                        mess = "Invalid expiration date: " + Constants.getMonth(orderHeader.getCc_expmonth()) + " " + orderHeader.getCc_expyear() + ".";
                    mess = mess + "<br>" + "<b>AVS Address:</b> " + avs + "&nbsp;&nbsp;&nbsp;<b>AVS ZIP:</b> " + zip + "&nbsp;&nbsp;&nbsp;<b>CSC:</b> " + csc;
                    errorList.add(mess);
                }
            }
            else
            {
                objResponse = new HashMap<String, String>();
                objResponse.put("PaymentType", "Other");
                objResponse.put("OrderId", orderHeader.getOrderNumber());
            }
            if (objResponse != null)
                checkoutService.addAuthLog(objResponse);

            if (flag)
                flag = checkoutService.setOrderHeader(orderHeader);

            if (flag)
                flag = checkoutService.setOrderLine(listOrderLine);

            if (flag)
                flag = checkoutService.addAvgMhz(avgMhz);

            if (!flag)
            {
                if (errorList.size() == 0)
                    errorList.add("Error, system can't finish order. Please contact administrator.");

                checkoutService.deleteOrders(order_id, shopper_id);
                request.setAttribute(Constants.ATTR_ORDER_RECEIPT, orderHeader);
                showCheckout(mapping, form, request, response);
                forward = this.getUrl();
            }
            else
            {
                //Send Mail for Customer
                //if (isCustomer != null)
                //{
                //    if (sendMailNotify(orderHeader, request))
                //        errorList.add("Error, system can't send mail for Agent. Please contact administrator.");

                //}

                //Send Mail for Agent

                if (!sendMailNotifyAgent(orderHeader, request))
                	errorList.add("Error, system can't send mail for Customer. Please contact administrator.");

                //Clear basket 
                checkoutService.clearBasket(shopper_id, byAgent);

                Customer customer = customerServices.getCustomerByShopperID(shopper_id);
                //set Result
                request.setAttribute(Constants.ATTR_CHECKOUT_RESULTS, 1);
                request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                request.setAttribute(Constants.ATTR_ORDER_RECEIPT, orderHeader);

                request.setAttribute(Constants.ATTR_ORDER_ID, order_id);
                request.setAttribute(Constants.ATTR_SHOPPER_ID, shopper_id);

                //if Agent set session SHOPPER_ID
                if ((isCustomer == null || !((Boolean) isCustomer).booleanValue()))
                {
                    Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
                    session.setAttribute(Constants.SHOPPER_ID, agent.getAgentId());
                    //if(session.getAttribute(Constants.IS_CUSTOMER) != null)
                    //session.removeAttribute(Constants.IS_CUSTOMER);
                    if (session.getAttribute(Constants.IS_SHOPPER) != null)
                        session.removeAttribute(Constants.IS_SHOPPER);
                }
            }

            if (errorList.size() > 0)
            {
                request.setAttribute(Constants.ATTR_LIST_ERROR, errorList);
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Action submitOrder - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
    }

    public OrderHeader getOrderHeader(ActionForm form, HttpServletRequest request)
    {
        HttpSession sessions = request.getSession();
        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        Agent agent = null;
        if (sessions.getAttribute(Constants.AGENT_INFO) != null)
        {
            agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);
        }
        else
        {
            agent = new Agent();
        }

        Boolean byAgent = true;
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
            byAgent = false;
        }
        else
        {
            byAgent = true;
        }

        String[] paymentType = null;
        int paymentTypeSelect = 0;
        String order_id = "";
        String shopper_id = "";

        Float listPricetotal = new Float(0);
        Float orderSubtotal = new Float(0);
        //        Float shipCost = new Float(0);
        //        Float taxCost = new Float(0);
        Float orderTotal = new Float(0);
        Float Fee = new Float(0);
        Float warranty_Total = new Float(0);

        String cc_type = "";
        String cc_name = "";
        String cc_number = "";
        String cc_cvv2 = "";
        String cc_expmonth = "";
        String cc_expyear = "";
        String payment_terms = "";

        Customer customer = null;
        TaxTables taxTables = null;
        int agent_id = 0;
        
        CustomerServices customerServices = new CustomerServices();
        CheckoutService checkoutService = new CheckoutService();
        BasketService basketService = new BasketService();

        if (request.getParameter("cc_name") != null)
        {
            cc_name = request.getParameter("cc_name").toString();
        }
        if (request.getParameter("_cc_number") != null)
        {
            cc_number = request.getParameter("_cc_number").toString();
        }
        if (request.getParameter("_cc_cvv2") != null)
        {
            cc_cvv2 = request.getParameter("_cc_cvv2").toString();
        }
        if (request.getParameter("_cc_expmonth") != null)
        {
            cc_expmonth = request.getParameter("_cc_expmonth").toString();
        }
        if (request.getParameter("_cc_expyear") != null)
        {
            cc_expyear = request.getParameter("_cc_expyear").toString();
        }

        if (request.getParameter("payment_type") != null)
        {
            String paymentTemp = request.getParameter("payment_type").toString();
            String[] arrayS = paymentTemp.split(",");
            String[] arrayD = new String[6];

            //Copy Array
            System.arraycopy(arrayS, 1, arrayD, 0, 6);
            paymentType = arrayD;

        }

        if (request.getParameter("payment_type_selected") != null)
        {
            paymentTypeSelect = Integer.parseInt(request.getParameter("payment_type_selected").toString());
        }

        if (request.getParameter("cc_type") != null)
        {
            cc_type = request.getParameter("cc_type").toString();
        }

        if (paymentType != null && paymentType.length == 6)
        {
            if (Integer.parseInt(paymentType[paymentTypeSelect]) == 1)
            {
                payment_terms = "Credit";

                //                if (cc_number.length() >= 12)
                //                {
                //                    cc_number = "XXXXXXXXXXXX" + cc_number.substring(12);
                //                }
            }
            else
            {
                if (request.getParameter("terms") != null)
                {
                    payment_terms = request.getParameter("terms").toString();
                }
                cc_name = "";
                cc_number = "";
                cc_expmonth = "01";
                cc_expyear = "1900";
                cc_number = "";
            }
        }

        if (request.getParameter("OrderNumber") != null)
        {
            order_id = request.getParameter("OrderNumber").toString();
        }

        if (request.getParameter("Shopper_id") != null)
        {
            shopper_id = request.getParameter("Shopper_id").toString();
            //code get information customer old
            //customer = customerServices.getCustomerByShopperID(shopper_id);
            agent_id = customerServices.getAgentIdByShopperID(shopper_id);
            //code get information customer new by tiendang
            try {
            	customer = (Customer) sessions.getAttribute("CUSTOMER_CHECKOUT_SESSION");
            } catch(Exception e) {
            } finally {
            	if(customer == null) { 
            		customer = customerServices.getCustomerByShopperID(shopper_id);
            	}
            }
            taxTables = checkoutService.getTaxTables(customer.getShip_to_postal(), customer.getShip_to_city());
        }
        else
        {
            customer = new Customer();
            taxTables = new TaxTables();
        }

        //Check Tax Exempt of customer
        if (customer.getTax_exempt() == 1 && (customer.getTax_exempt_number().length() > 0) && Converter.isEqualOrGreatThanToday(customer.getTax_exempt_expire()))//&& customer.getAccount_type().equalsIgnoreCase("R")
        {
            taxTables = new TaxTables();
        }

        //ListPricetotal
        if (request.getParameter("ListPricetotal") != null)
        {
            listPricetotal = Float.parseFloat(request.getParameter("ListPricetotal").toString());
        }
        if (request.getParameter("OrderSubtotal") != null)
        {
            orderSubtotal = Float.parseFloat(request.getParameter("OrderSubtotal").toString());
        }
        
        if (request.getParameter("OrderTotal") != null)
        {
            orderTotal = Float.parseFloat(request.getParameter("OrderTotal").toString());
            //System.out.println("Value Total ::"+orderTotal);
        }
        if (request.getParameter("Fee") != null)
        {
            Fee = Float.parseFloat(request.getParameter("Fee").toString());
        }
        
        if (request.getParameter("WarrantyTotal") != null)
        {
        	warranty_Total = Float.parseFloat(request.getParameter("WarrantyTotal").toString());
        }

        OrderRow orderRow = basketService.getOrder(shopper_id, agent.getAgentId());

        OrderHeader orderHeader = new OrderHeader();

        orderHeader.setOrderNumber(order_id);
        orderHeader.setShopper_id(shopper_id);

        if (customer != null)
        {
            orderHeader.setShip_to_name(customer.getShip_to_fname() + " " + customer.getShip_to_lname());
        }

        orderHeader.setShip_to_company(customer.getShip_to_company());
        orderHeader.setShip_to_address1(customer.getShip_to_address1());
        orderHeader.setShip_to_address2(customer.getShip_to_address2());
        orderHeader.setShip_to_city(customer.getShip_to_city());
        orderHeader.setShip_to_state(customer.getShip_to_state());
        orderHeader.setShip_to_postal(customer.getShip_to_postal());
        orderHeader.setShip_to_country("USA");
        orderHeader.setShip_to_phone(customer.getShip_to_phone());
        orderHeader.setShip_to_email(customer.getShip_to_email());

        orderHeader.setShip_method(orderRow.getShip_method());
        orderHeader.setShip_terms(orderRow.getShip_terms());

        orderHeader.setBill_to_name(customer.getBill_to_fname() + " " + customer.getBill_to_lname());
        orderHeader.setBill_to_company(customer.getBill_to_company());
        orderHeader.setBill_to_address1(customer.getBill_to_address1());
        orderHeader.setBill_to_address2(customer.getBill_to_address2());
        orderHeader.setBill_to_city(customer.getBill_to_city());
        orderHeader.setBill_to_state(customer.getBill_to_state());
        orderHeader.setBill_to_postal(customer.getBill_to_postal());
        orderHeader.setBill_to_country("USA");
        orderHeader.setBill_to_phone(customer.getBill_to_phone());
        orderHeader.setBill_to_email(customer.getBill_to_email());
        
        orderHeader.setOadjust_subtotal(new BigDecimal(orderSubtotal));
        orderHeader.setHandling_total(new BigDecimal(Fee));

        if (request.getParameter("ShipCost") != null)
        {
            orderRow.setShip_cost(new Float(request.getParameter("ShipCost").toString()));
        }

        if (checkoutService.checkStateInShippingTax(customer.getShip_to_state()))
        {
            orderHeader.setFreightTax(new BigDecimal(orderRow.getShip_cost() * taxTables.getCombuTax()));
        }
        else
        {
            orderHeader.setFreightTax(new BigDecimal(0));
        }

        orderHeader.setShipping_total(new BigDecimal(orderRow.getShip_cost()));

        orderHeader.setTax_total(new BigDecimal(orderSubtotal * taxTables.getCombuTax() + orderHeader.getFreightTax().floatValue()));
        orderHeader.setTotal_total(new BigDecimal(orderHeader.getOadjust_subtotal().floatValue() + orderHeader.getTax_total().floatValue() + orderHeader.getShipping_total().floatValue() + orderHeader.getHandling_total().floatValue() ));

        orderHeader.setApprovalNumber("");//123454
        
        orderHeader.setCc_name(cc_name);
        orderHeader.setCc_number(cc_number);
        orderHeader.setCc_expmonth(Integer.parseInt(cc_expmonth));
        orderHeader.setCc_expyear(Integer.parseInt(cc_expyear));
        orderHeader.setPayment_terms(payment_terms);
        orderHeader.setCc_type(cc_type);
        orderHeader.setTaxable(true);
        orderHeader.setOrderStatus("PENDING");
        orderHeader.setStatetaxperc(taxTables.getStateTax().floatValue() * 100);
        orderHeader.setCountytaxperc(taxTables.getCountyTax().floatValue() * 100);
        orderHeader.setCountytrantaxperc(taxTables.getCountyTransTax().floatValue() * 100);
        orderHeader.setCitytaxperc(taxTables.getCityTax().floatValue() * 100);
        orderHeader.setCitytrantaxperc(taxTables.getCityTranTax().floatValue()* 100);

        orderHeader.setAgentIDEnter(agent.getAgentId());
        //Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
        	orderHeader.setAgentIDEnter(Integer.parseInt(Constants.AGENT_STORE_ID)); //for COT use case
        }
        else
        {
        	orderHeader.setAgentIDEnter(agent_id);//Get agent_id of customers, Mr. Thanh demand      	
        }
        
        orderHeader.setDataSource("Direct");
        orderHeader.setEditAgent(Integer.toString(agent.getAgentId()));
        orderHeader.setUser_name(agent.getUserName());
        orderHeader.setIp_address(request.getRemoteAddr());
        orderHeader.setTotal_disc(new BigDecimal(listPricetotal - orderSubtotal));
        orderHeader.setDiscount_total(new BigDecimal(listPricetotal - orderSubtotal));
        orderHeader.setWarranty_total(new BigDecimal(warranty_Total));
        orderHeader.setTotal_list(new BigDecimal(listPricetotal));
        orderHeader.setListingtype("Agent");
        orderHeader.setAvs_address("Y");
        orderHeader.setAvs_zip("Y");
        orderHeader.setAvs_country("Y");
        orderHeader.setVenuetype("MRI");

        orderHeader.setCc_cvv2(cc_cvv2);
        orderHeader.setByAgent(byAgent);
        //sessions.removeAttribute("CUSTOMER_CHECKOUT_SESSION");
        return orderHeader;
    }

    public List<OrderLine> getOrderLine(ActionForm form, HttpServletRequest request)
    {
        HttpSession sessions = request.getSession();
        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        Boolean byAgent = true;
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
            byAgent = false;
        }
        else
        {
            byAgent = true;
        }

        String order_id = "";
        String shopper_id = "";
        if (request.getParameter("OrderNumber") != null)
        {
            order_id = request.getParameter("OrderNumber").toString();
        }

        if (request.getParameter("Shopper_id") != null)
        {
            shopper_id = request.getParameter("Shopper_id").toString();
        }
        CheckoutService checkoutService = new CheckoutService();

        List<EstoreBasketItem> listEstoreBasketItem = checkoutService.getItemCheck(shopper_id, byAgent);
        List<OrderLine> listOrderLine = new ArrayList<OrderLine>();
        int idx = 1;
        for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
        {
            OrderLine orderLine = new OrderLine();
            orderLine.setOrderNumber(order_id);
            orderLine.setShopper_id(shopper_id);
            orderLine.setItem_id(idx);
            orderLine.setItem_sku(estoreBasketItem.getItem_sku());
            orderLine.setDescription(estoreBasketItem.getName());
            orderLine.setWeight(new BigDecimal(estoreBasketItem.getWeight()));
            orderLine.setQuantity(estoreBasketItem.getQuantity());
            orderLine.setQtyPicked(0);
            orderLine.setQtyShipped(0);
            orderLine.setProduct_list_price(new BigDecimal(estoreBasketItem.getList_price()));

            orderLine.setIadjust_regularprice(new BigDecimal(estoreBasketItem.getPlaced_price()));
            orderLine.setIadjust_currentprice(new BigDecimal(estoreBasketItem.getPlaced_price()));
            orderLine.setOadjust_adjustedprice(new BigDecimal(estoreBasketItem.getPlaced_price()));
            orderLine.setDiscounted_price(new BigDecimal(estoreBasketItem.getPlaced_price()));
            orderLine.setCosmetic_grade(estoreBasketItem.getImage_url());
            orderLine.setCategory_id(estoreBasketItem.getCategory_id());
            
            listOrderLine.add(orderLine);
            idx++;
        }
        return listOrderLine;
    }

    public ActionForward cancelOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.LOGIN_SUCCESS;
        HttpSession sessions = request.getSession();
        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        Boolean byAgent = true;
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
            byAgent = false;
        }
        else
        {
            byAgent = true;
        }
        try
        {
            LOGGER.info("Action cancelOrder - Checkout 3");
            String shopper_id = "";

            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                ProductServices productServices = new ProductServices();
                productServices.clearOrder(shopper_id, byAgent);
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Action cancelOrder - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }
 
    
    @SuppressWarnings("deprecation")
    public ActionForward holdOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        LOGGER.info("Action heldOrder - Checkout 3");
        String forward = Constants.LOGIN_SUCCESS;
        HttpSession sessions = request.getSession();
        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        List<String> errorList = new ArrayList<String>();

        try
        {

            String oldShopper_id = "";
            String newShopper_id = Constants.generateID(Constants.ID_LENGTH);
            String agent_id = "";
            Timestamp expirationHoldDate = null;

            Agent agent = null;
            CheckoutService checkoutService = new CheckoutService();

            if (sessions.getAttribute(Constants.AGENT_INFO) != null)
            {
                agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);

                if (agent.getAgentId() == 0)
                {
                    agent_id = "";
                }
                else
                {
                    agent_id = Integer.toString(agent.getAgentId());
                }
            }
            else
            {
                agent = new Agent();
            }

            if (request.getParameter("expHoldDate") != null)
            {
                if (agent.getAgentId() > 0)
                {
                    expirationHoldDate = new Timestamp(new Date("01/01/1970").getTime());
                }
                else
                {
                    expirationHoldDate = new Timestamp(new Date(request.getParameter("expHoldDate").toString()).getTime());
                }
            }

            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                oldShopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                if (Constants.isNumber(oldShopper_id))
                {
                    return mapping.findForward(forward);
                }

                OrderHeader orderHeader = getOrderHeader(form, request);
                Avg_mhz avgMhz = getAvgMhz(form, request);

                Boolean flag = checkoutService.setOrdersHeld(oldShopper_id, newShopper_id, expirationHoldDate, agent_id);
                if (flag)
                    flag = checkoutService.addAvgMhz(avgMhz);
                if (flag)
                {
                    // Send Mail for Agent 
                    if (isCustomer != null)
                    {
                        sendMailNotifyHold(orderHeader, expirationHoldDate, request);
                    }

                    //if Agent set session SHOPPER_ID
                    if (isCustomer == null || !((Boolean) isCustomer).booleanValue())
                    {
                        sessions.setAttribute(Constants.SHOPPER_ID, agent.getAgentId());
                        //                        if(sessions.getAttribute(Constants.IS_CUSTOMER) != null)
                        //                            sessions.removeAttribute(Constants.IS_CUSTOMER);
                        if (sessions.getAttribute(Constants.IS_SHOPPER) != null)
                            sessions.removeAttribute(Constants.IS_SHOPPER);
                    }
                }
                else
                {
                    //Failed 
                    errorList.add("Error held order. Please contact administrator.");
                    showCheckout(mapping, form, request, response);
                    forward = this.getUrl();
                }
            }
            else
            {
                forward = Constants.LOGIN_VIEW;
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Action cancelOrder - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        if (errorList.size() > 0)
            request.setAttribute(Constants.ATTR_LIST_ERROR, errorList);

        return mapping.findForward(forward);
    }

    public ActionForward showCSCNumberHelp(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        LOGGER.info("Action showCSCNumberHelp - Checkout 3");
        String forward = Constants.SHOW_CSC;
        return mapping.findForward(forward);
    }

    public ActionForward printOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_ORDER;
        try
        {
            LOGGER.info("Action printOrder - Checkout 3");

            String order_id = "";
            String shopper_id = "";
            OrderHeader orderHeader = null;
            CheckoutService checkoutService = new CheckoutService();

            if (request.getParameter("order_id") != null)
            {
                order_id = request.getParameter("order_id").toString();
            }

            if (request.getParameter("shopper_id") != null)
            {
                shopper_id = request.getParameter("shopper_id").toString();
            }

            orderHeader = checkoutService.getOrderReceipt(shopper_id, order_id);
            request.setAttribute(Constants.ATTR_ORDER_RECEIPT, orderHeader);
        }
        catch (Exception e)
        {
            LOGGER.info("Action printOrder - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    private Boolean sendMailNotify(OrderHeader orderHeader, HttpServletRequest request)
    {
        Boolean flag = true;

        try
        {
            HttpSession session = request.getSession();
            MailUtils mailUtils = MailUtils.getInstance();
            Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);

            //Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailUtils.getHost());
            email.setSmtpPort(mailUtils.getSmtp_port());
            email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
            email.setTLS(true);
            //send email to agent
            email.setFrom(mailUtils.getFrom());

            Agent agent = null;
            if (isCustomer == null || !((Boolean) isCustomer).booleanValue())
            {
                agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
            }
            else
            {
                AuthenticationService authenticationService = new AuthenticationService();
                CustomerServices customerServices = new CustomerServices();
                String shopper_id = session.getAttribute(Constants.SHOPPER_ID).toString();

                Customer customer = customerServices.getCustomerByShopperID(shopper_id);
                int agent_id = customer.getAgent_id();
                agent = authenticationService.getAgentByAgentId(agent_id);
            }

            email.addTo(agent.getAgentEmail(), agent.getAgentName());
            email.setSubject("New Customer Order");

            String htmlContent =
                "<html>" + " Date: " + Constants.getCurrentDate() + "<br/>" + " Customer name: " + orderHeader.getBill_to_name() + "<br/>" + " Company name: " + orderHeader.getBill_to_company()
                    + "<br/>" + " Order #" + orderHeader.getOrderNumber() + "<br/>" + " Order total: " + Constants.FormatCurrency(orderHeader.getTotal_total().floatValue()) + "</html>";
            // set the html message
            email.setHtmlMsg(htmlContent);

            // send the email
            email.send();
        }
        catch (Exception e)
        {
            flag = false;
            LOGGER.info("sendMailNotify  - Exception");
            e.printStackTrace();
            // TODO: handle exception
        }
        return flag;
    }

    private Boolean sendMailNotifyHold(OrderHeader orderHeader, Timestamp expirationHoldDate, HttpServletRequest request) throws Exception
    {
        Boolean flag = true;
        try
        {

            HttpSession session = request.getSession();
            MailUtils mailUtils = MailUtils.getInstance();
            Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);

            // Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailUtils.getHost());
            email.setSmtpPort(mailUtils.getSmtp_port());
            email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
            email.setTLS(true);
            //send email to agent
            email.setFrom(mailUtils.getFrom());
            if (isCustomer == null || !((Boolean) isCustomer).booleanValue())
            {
                Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
                email.addTo(agent.getAgentEmail(), agent.getAgentName());
            }
            else
            {
                AuthenticationService authenticationService = new AuthenticationService();
                CustomerServices customerServices = new CustomerServices();
                String shopper_id = session.getAttribute(Constants.SHOPPER_ID).toString();

                Customer customer = customerServices.getCustomerByShopperID(shopper_id);
                int agent_id = customer.getAgent_id();
                Agent agent = authenticationService.getAgentByAgentId(agent_id);

                email.addTo(agent.getAgentEmail(), agent.getAgentName());
            }

            email.setSubject("New Customer Quote");

            String htmlContent =
                "<html>" + " Date: " + Constants.getCurrentDate() + "<br/>" + " Customer name: " + orderHeader.getBill_to_name() + "<br/>" + " Company name: " + orderHeader.getBill_to_company()
                    + "<br/>" + " Order #" + orderHeader.getOrderNumber() + "<br/>" + " Order total: " + Constants.FormatCurrency(orderHeader.getTotal_total().floatValue()) + "<br/>"
                    + " Order cancelation date: " + Constants.formatDate(expirationHoldDate, "MM/dd/yyyy") + "</html>";
            // set the html message
            email.setHtmlMsg(htmlContent);

            // send the email
            email.send();
        }
        catch (Exception e)
        {

            flag = false;
            LOGGER.info("sendMailNotifyHold  - Exception");
            e.printStackTrace();
            // TODO: handle exception

        }
        return flag;
    }

    public ActionForward exportExcel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = "agenttools.checkout3.exportExcel";
        try
        {
            HttpSession sessions = request.getSession();

            Agent agent = null;
            if (sessions.getAttribute(Constants.AGENT_INFO) != null)
            {
                agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);
            }
            else
            {
                agent = new Agent();
            }
            Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }

            LOGGER.info("Action exportExcel - Checkout 3");
            
            String shopper_id = "";

            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
            }

            //shopper_id = "H2W28PCFGPM4E76N5E20020221085427";

            //get estore_basket_item
            CheckoutService checkoutService = new CheckoutService();
            List<EstoreBasketItem> listEstoreBasketItem = checkoutService.getItemCheck(shopper_id, byAgent);

            //get customer
            CustomerServices customerServices = new CustomerServices();
            //Customer customer = customerServices.getCustomerByShopperID(shopper_id);
            
            Customer customer = (Customer) sessions.getAttribute("CUSTOMER_CHECKOUT_SESSION");
            	
            if(customer ==  null){
            	//CustomerServices customerServices = new CustomerServices();
                customer = customerServices.getCustomerByShopperID(shopper_id);
            }
            //Get order
            BasketService basketService = new BasketService();
            OrderRow orderRow = basketService.getOrder(shopper_id, agent.getAgentId());

            //get taxtables
            TaxTables taxTables = checkoutService.getTaxTables(customer.getShip_to_postal(), customer.getShip_to_city());
            
            Float stsubtotal_amt = new Float(0);
            Float stsum = new Float(0);
            Float lidisc_amnt = new Float(0);
            Float totalMhz = new Float(0);
            Float totalPriceMhz = new Float(0);
            Float warrantyTotal = new Float(0);
            Float percentTDis = new Float(0);
            Float shipCost = new Float(0);
            Float taxCost = new Float(0);
            Float orderSubtotal = new Float(0);
            Float orderSubtotal1 = new Float(0);
            Float orderTotal = new Float(0);
            Float moneyByMhz = new Float(0);
            Float taxShip = new Float(0);
            Float taxOrder = new Float(0);
            int unitMhz = 0 ;
            Float Fee = new Float(0);
            String accountType = customer.getAccount_type();
        	String shipToState = customer.getShip_to_state(); 
        	int taxExempt = customer.getTax_exempt();
            
            
            for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
            {
                //stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getList_price();
                
                if (estoreBasketItem.getItem_sku().contains("WARRANTY")){
                	stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getPlaced_price();
               	 	warrantyTotal = warrantyTotal + estoreBasketItem.getPlaced_price();
                }
                else
                	stsubtotal_amt = stsubtotal_amt + estoreBasketItem.getList_price();
                
                stsum = stsum + estoreBasketItem.getPlaced_price();
                
                if(estoreBasketItem.getSpeed().floatValue() > 0)
                {
                    totalMhz = totalMhz + estoreBasketItem.getSpeed();    
                    moneyByMhz = moneyByMhz + estoreBasketItem.getPlaced_price().floatValue();
                    unitMhz ++ ;
                }
            }
            totalPriceMhz = (totalMhz.floatValue() == 0 ) ? 0 :  (moneyByMhz / 100) / totalMhz;
            lidisc_amnt = (stsubtotal_amt - stsum)/100;
            percentTDis = lidisc_amnt / ((stsubtotal_amt - warrantyTotal) / 100);
            
            
            //Check Tax Exempt of customer
            if (customer.getTax_exempt() == 1 && (customer.getTax_exempt_number().length() > 0) && Converter.isEqualOrGreatThanToday(customer.getTax_exempt_expire()))
            {
                taxTables = new TaxTables();
            }

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.autoSizeColumn((short) 2);

            //Create Style
            //Create font Bold
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleWrapText = wb.createCellStyle();
            cellStyleWrapText.setWrapText(true);

            HSSFCellStyle cellStyleWrapTextBold = wb.createCellStyle();
            cellStyleWrapTextBold.setFont(font);
            cellStyleWrapText.setWrapText(true);

            HSSFCellStyle cellStyleWrapTextBoldTotal = wb.createCellStyle();
            cellStyleWrapTextBoldTotal.setFont(font);
            cellStyleWrapTextBoldTotal.setWrapText(true);
            cellStyleWrapTextBoldTotal.setBorderBottom((short) 1);
            cellStyleWrapTextBoldTotal.setBorderLeft((short) 1);
            cellStyleWrapTextBoldTotal.setBorderRight((short) 1);
            cellStyleWrapTextBoldTotal.setBorderTop((short) 1);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFDataFormat format = wb.createDataFormat();
            HSSFCellStyle styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(format.getFormat("$#,##0.00"));
            
            HSSFDataFormat df1 = wb.createDataFormat();
            HSSFCellStyle styleCurrency3DecimalFormat = null;
            styleCurrency3DecimalFormat = wb.createCellStyle();
            styleCurrency3DecimalFormat.setDataFormat(df1.getFormat("$#,##0.000"));
            //End Create Style

            //Row 0
            Row row0 = sheet.createRow((short) 0);
            Cell cellRow0 = row0.createCell((short) 0);
            cellRow0.setCellValue("Order Number: " + orderRow.getOrder_id());

            //Row 1
            Row row1 = sheet.createRow((short) 1);
            Cell cell0Row1 = row1.createCell((short) 0);
            cell0Row1.setCellValue("Billing Address");

            Cell cel6Row1 = row1.createCell((short) 6);
            cel6Row1.setCellValue("Shipping Address ");

            //Row 2
            Row row2 = sheet.createRow((short) 2);

            Cell cell0Row2 = row2.createCell((short) 0);

            String valueLeft =
                Constants.collapseRowN(customer.getBill_to_fname() + " " + customer.getBill_to_lname(), "") + Constants.collapseRowN(customer.getBill_to_title(), "")
                    + Constants.collapseRowN(customer.getBill_to_company(), "") + Constants.collapseRowN(customer.getBill_to_address1(), "")
                    + Constants.collapseRowN(customer.getBill_to_address2(), "")
                    + Constants.collapseRowN(customer.getBill_to_city() + ", " + customer.getBill_to_state() + ", " + customer.getBill_to_postal(), "")
                    + Constants.collapseRowN(customer.getBill_to_country(), "") + Constants.collapseRowN(customer.getBill_to_phone(), "TEL #:")
                    + Constants.collapseRowN(customer.getBill_to_phoneext(), "ext:") + Constants.collapseRowN(customer.getBill_to_fax(), "FAX #:");

            cell0Row2.setCellValue(valueLeft);
            cell0Row2.setCellStyle(cellStyleWrapText);

            Cell cel6Row2 = row2.createCell((short) 6);
            String valueRight =
                Constants.collapseRowN(customer.getShip_to_fname() + " " + customer.getShip_to_lname(), "") + Constants.collapseRowN(customer.getShip_to_title(), "")
                    + Constants.collapseRowN(customer.getShip_to_company(), "") + Constants.collapseRowN(customer.getShip_to_address1(), "")
                    + Constants.collapseRowN(customer.getShip_to_address2(), "")
                    + Constants.collapseRowN(customer.getShip_to_city() + ", " + customer.getShip_to_state() + ", " + customer.getShip_to_postal(), "")
                    + Constants.collapseRowN(customer.getShip_to_country(), "") + Constants.collapseRowN(customer.getShip_to_phone(), "TEL #:")
                    + Constants.collapseRowN(customer.getShip_to_phoneext(), "ext:");
            cel6Row2.setCellValue(valueRight);
            cel6Row2.setCellStyle(cellStyleWrapText);

            int heightRow = 0;
            if (valueLeft.split("\n").length > valueRight.split("\n").length)
            {
                heightRow = valueLeft.split("\n").length;
            }
            else
            {
                heightRow = valueRight.split("\n").length;
            }

            //increase row height to accomodate two lines of text
            row2.setHeightInPoints(((heightRow + 1) * sheet.getDefaultRowHeightInPoints()));

            int colIdx = 12;
            if(isCustomer != null )
                colIdx++;
            
            Row row3 = sheet.createRow((short) 4);
            Cell cel1Row3 = row3.createCell((short) 0);
            cel1Row3.setCellValue("Order Totals");
            cel1Row3.setCellStyle(cellStyleBold);

            Row rowBasketHeader = sheet.createRow((short) 5);
            Cell cel1rowBasketHeader = rowBasketHeader.createCell((short) 0);
            cel1rowBasketHeader.setCellValue("");

            Cell cel2rowBasketHeader = rowBasketHeader.createCell((short) 1);
            cel2rowBasketHeader.setCellValue("Product Number");
            cel2rowBasketHeader.setCellStyle(cellStyleBold);
            //cel2rowBasketHeader.setCellStyle(cellStyleWrapTextBold);

            Cell celChassisColor = rowBasketHeader.createCell(2);
            celChassisColor.setCellValue("Chassis Color");
            celChassisColor.setCellStyle(cellStyleBold);
            
            Cell cel3rowBasketHeader = rowBasketHeader.createCell((short) 3);
            cel3rowBasketHeader.setCellValue("Product Description");
            cel3rowBasketHeader.setCellStyle(cellStyleBold);

            
            if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
            {
                Cell cel10rowBasketHeader = rowBasketHeader.createCell((short) colIdx);
                cel10rowBasketHeader.setCellValue("Unit Price");
                cel10rowBasketHeader.setCellStyle(cellStyleBold);
                colIdx++;

                Cell cel11rowBasketHeader = rowBasketHeader.createCell((short) colIdx);
                cel11rowBasketHeader.setCellValue("Discount Amount");
                cel11rowBasketHeader.setCellStyle(cellStyleBold);
                colIdx++;
   
            }
            
            if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) 
            {
                Cell cel12rowBasketHeader = rowBasketHeader.createCell((short) colIdx);
                cel12rowBasketHeader.setCellValue("$/Mhz");
                cel12rowBasketHeader.setCellStyle(cellStyleBold);
                colIdx++;    
            }
            
            Cell cel13rowBasketHeader = rowBasketHeader.createCell((short) colIdx);
            if (isCustomer == null && lidisc_amnt.floatValue() != 0)
                cel13rowBasketHeader.setCellValue("Discounted Price");
            else
                cel13rowBasketHeader.setCellValue("Unit Price");
            cel13rowBasketHeader.setCellStyle(cellStyleBold);
            colIdx++;
            
            if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0){
            	Cell cel14rowBasketHeader = rowBasketHeader.createCell((short) colIdx);
                cel14rowBasketHeader.setCellValue("Environmental Fee");
                cel14rowBasketHeader.setCellStyle(cellStyleBold);
                colIdx++;
            }

            
            int rowIdx = 7;
            int idx = 1;

            for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
            {
                int colIdxN = 12;
                if(isCustomer != null)
                    colIdxN++;
               
                String moneyMhz = "";
                moneyMhz = (estoreBasketItem.getSpeed().floatValue() > 0) ? "$" + Constants.FormatMhz(estoreBasketItem.getPlaced_price() / estoreBasketItem.getSpeed() / 100) : "$0";

                Row rowBasket = sheet.createRow((short) rowIdx);

                Cell cel1rowBasket = rowBasket.createCell((short) 0);
                cel1rowBasket.setCellValue(idx);

                Cell cel2rowBasket = rowBasket.createCell((short) 1);
                cel2rowBasket.setCellValue(estoreBasketItem.getItem_sku());

                if(estoreBasketItem.getCategory_id()==11946 || estoreBasketItem.getCategory_id()==11947 || estoreBasketItem.getCategory_id()==11949){
	                Cell cel2Color = rowBasket.createCell((short) 2);
	                cel2Color.setCellValue(estoreBasketItem.getAttribute05());
                }
                
                Cell cel3rowBasket = rowBasket.createCell((short) 3);
                cel3rowBasket.setCellValue(estoreBasketItem.getName());
                
                //hide column $/MHZ
                if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
                {
                    Cell cel10rowBasket = rowBasket.createCell((short) colIdxN);
                    //cel10rowBasket.setCellValue(Constants.FormatCurrency(new Float(estoreBasketItem.getList_price() / 100)));
                    cel10rowBasket.setCellValue(estoreBasketItem.getList_price().doubleValue() / 100.0);
                    cel10rowBasket.setCellStyle(styleCurrencyFormat);
                    cel10rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    colIdxN++;

                    Cell cel11rowBasket = rowBasket.createCell((short) colIdxN);
                    //cel11rowBasket.setCellValue(Constants.FormatCurrency(new Float((estoreBasketItem.getList_price() - estoreBasketItem.getPlaced_price()) / 100)));
                    cel11rowBasket.setCellValue((estoreBasketItem.getList_price().doubleValue() - estoreBasketItem.getPlaced_price().doubleValue()) / 100.0);
                    cel11rowBasket.setCellStyle(styleCurrencyFormat);
                    cel11rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    colIdxN++;
                }
                
                if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) 
                {
                    Cell cel12rowBasket = rowBasket.createCell((short) colIdxN);
                    cel12rowBasket.setCellValue(Double.parseDouble(moneyMhz.substring(1).toString()));
                    cel12rowBasket.setCellStyle(styleCurrency3DecimalFormat);
                    cel12rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel12rowBasket.setCellStyle(cellStyleAlignRight);
                    colIdxN++;  
    
                }
                Cell cel13rowBasket = rowBasket.createCell((short) colIdxN);
                //cel13rowBasket.setCellValue(Constants.FormatCurrency(estoreBasketItem.getPlaced_price() / 100));
                cel13rowBasket.setCellValue(estoreBasketItem.getPlaced_price().doubleValue() / 100);
                cel13rowBasket.setCellStyle(styleCurrencyFormat);
                cel13rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                colIdxN++;
                // TienDang
                if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0){
                	float att12 =0;
					float valAtt12=1;
					boolean bl;
					double dtemp;
                	if(estoreBasketItem.getCategory_id() == 11946)
					{
						if(estoreBasketItem.getScreenSize() > 15 )
						{
							Fee = Fee + 8;
							Cell cel14rowBasket = rowBasket.createCell((short) colIdxN);
							cel14rowBasket.setCellValue(Double.valueOf("8"));
		                    cel14rowBasket.setCellStyle(styleCurrencyFormat);
		                    cel14rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}else if(estoreBasketItem.getScreenSize() < 15){
							Fee = Fee + 6;
							Cell cel14rowBasket = rowBasket.createCell((short) colIdxN);
							cel14rowBasket.setCellValue(Double.valueOf("6"));
		                    cel14rowBasket.setCellStyle(styleCurrencyFormat);
		                    cel14rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
					}
                	if(estoreBasketItem.getCategory_id() == 11955 )
					{
                		try{
							att12 = Float.parseFloat(estoreBasketItem.getAttribute12());
							bl = true;	
							//System.out.println("attribute12 : "+att12);
						}catch(Exception e){
							bl = false;
						}
						
						if (bl==true){
							if(att12>0)
							{
								valAtt12 = att12;
							}
							//System.out.println("value  : "+valAtt12);
						}else {
							valAtt12 = 1;
							//System.out.println("Test :"+att12);
						}
						if(estoreBasketItem.getScreenSize() > 15 )
						{
							Fee = Fee + (8 * valAtt12);
							dtemp = 8 * valAtt12;
							Cell cel14rowBasket = rowBasket.createCell((short) colIdxN);
							cel14rowBasket.setCellValue(Double.valueOf(dtemp));
		                    cel14rowBasket.setCellStyle(styleCurrencyFormat);
		                    cel14rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}else if(estoreBasketItem.getScreenSize() < 15){
							Fee = Fee + (6 * valAtt12);
							dtemp = 6 * valAtt12;
							Cell cel14rowBasket = rowBasket.createCell((short) colIdxN);
							cel14rowBasket.setCellValue(Double.valueOf(dtemp));
		                    cel14rowBasket.setCellStyle(styleCurrencyFormat);
		                    cel14rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						}
					}
                	
                }
                
                rowIdx++;
                idx++;
            }
            totalPriceMhz = (totalMhz.floatValue() > 0) ? (moneyByMhz / 100) / totalMhz : 0;
            lidisc_amnt = (stsubtotal_amt - stsum) / 100;
            orderSubtotal = stsum / 100;
            stsubtotal_amt = stsubtotal_amt / 100;

            shipCost = orderRow.getShip_cost();
            if (orderRow.getShip_method().equalsIgnoreCase("0") && orderRow.getShip_terms().equalsIgnoreCase("Pallet Shipping / Item"))
            {
                shipCost = shipCost * listEstoreBasketItem.size();
            }

            orderSubtotal1 = orderSubtotal + shipCost;

            if (checkoutService.checkStateInShippingTax(customer.getShip_to_state()))
            {
                taxShip = shipCost.floatValue() * taxTables.getCombuTax().floatValue();
            }
            else
            {
                taxShip = new Float(0);
            }

            taxOrder = orderSubtotal * taxTables.getCombuTax().floatValue();
            taxCost = taxShip + taxOrder;
            orderTotal = orderSubtotal + shipCost + taxCost ;

            //Calculate Total
            int colIdxN = 12;
            if(isCustomer != null)
                colIdxN ++ ;
            Row rowTotal = sheet.createRow((short) rowIdx);
            //Set total order
            
            if(lidisc_amnt.floatValue() != 0 && (isCustomer==null || !((Boolean)isCustomer).booleanValue()))
            {
                Cell cel10rowTotal1 = rowTotal.createCell((short) colIdxN);
                //cel10rowTotal1.setCellValue(Constants.FormatCurrency(stsubtotal_amt));
                cel10rowTotal1.setCellValue(stsubtotal_amt.doubleValue());
                //cel10rowTotal1.setCellStyle(cellStyleAlignRight);
                cel10rowTotal1.setCellStyle(styleCurrencyFormat);
                cel10rowTotal1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                colIdxN++;
                
                Cell cel11rowTotal1 = rowTotal.createCell((short) colIdxN);
                cel11rowTotal1.setCellValue(lidisc_amnt.doubleValue());
                cel11rowTotal1.setCellStyle(styleCurrencyFormat);
                cel11rowTotal1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                colIdxN++;
                
               
               
            }
            
            if(isCustomer==null || !((Boolean)isCustomer).booleanValue()) 
            {
                //Set total MHZ
                Cell cel12rowTotal1 = rowTotal.createCell((short) colIdxN);
                cel12rowTotal1.setCellValue(totalPriceMhz.doubleValue());
                cel12rowTotal1.setCellStyle(styleCurrency3DecimalFormat);
                cel12rowTotal1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                //cel12rowTotal1.setCellStyle(cellStyleAlignRight);
                colIdxN++;
            }
            
            Cell cel13rowTotal1 = rowTotal.createCell((short) colIdxN);
            cel13rowTotal1.setCellValue(orderSubtotal.doubleValue());
            cel13rowTotal1.setCellStyle(styleCurrencyFormat);
            cel13rowTotal1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            colIdxN++;
            //cel13rowTotal1.setCellStyle(cellStyleAlignRight);
            
            //total Environmental Fee (TienDang)
            if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0)
            {
	            Cell celTotalFee = rowTotal.createCell((short) colIdxN);
	            celTotalFee.setCellValue(Fee.doubleValue());
	            celTotalFee.setCellStyle(styleCurrencyFormat);
	            celTotalFee.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	            colIdxN++;
            }
            
            rowIdx++;
            int colSub = 0;
            if(isCustomer == null && lidisc_amnt.floatValue() != 0)
                colSub = 12 ;
            else
                colSub = 10;
            
            Row rowOrderSubtotal = sheet.createRow((short) rowIdx);
            Cell cel8rowOrderSubtotal = rowOrderSubtotal.createCell((short) colSub);
            cel8rowOrderSubtotal.setCellValue("Order Subtotal:");
            cel8rowOrderSubtotal.setCellStyle(cellStyleBold);

            Cell cel10rowOrderSubtotal = rowOrderSubtotal.createCell((short) colSub + 2);
            cel10rowOrderSubtotal.setCellValue(orderSubtotal.doubleValue());
            cel10rowOrderSubtotal.setCellStyle(styleCurrencyFormat);
            cel10rowOrderSubtotal.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            //cel10rowOrderSubtotal.setCellStyle(cellStyleAlignRight);

            Row rowShipping = sheet.createRow((short) rowIdx + 1);
            Cell cel8rowShipping = rowShipping.createCell((short) colSub);
            cel8rowShipping.setCellValue("Shipping: " + checkoutService.getShortShipping(orderRow.getShip_method(), orderRow.getShip_terms()));
            cel8rowShipping.setCellStyle(cellStyleBold);

            String strShip = "";
            if (Constants.isNumber(orderRow.getShip_method()))
            {
                if (Integer.parseInt(orderRow.getShip_method()) == 6)
                {
                    strShip = "FREE";
                }
//                else
//                {
//                    strShip = Constants.FormatCurrency(shipCost);
//                }
            }

            Cell cel10rowShipping = rowShipping.createCell((short) colSub + 2);
            if (strShip=="FREE"){
            	cel10rowShipping.setCellStyle(cellStyleAlignRight);
            	cel10rowShipping.setCellValue(strShip);
            }
            else{
            	//cel10rowShipping.setCellValue(Double.parseDouble(strShip.substring(1).replace(",", "")));
            	cel10rowShipping.setCellValue(shipCost.doubleValue());
            	cel10rowShipping.setCellStyle(styleCurrencyFormat);
                cel10rowShipping.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            }
            
            
            Row rowOrderSubtotal1 = sheet.createRow((short) rowIdx + 2);
            Cell cel8rowOrderSubtotal1 = rowOrderSubtotal1.createCell((short) colSub);
            cel8rowOrderSubtotal1.setCellValue("Order Subtotal:");
            cel8rowOrderSubtotal1.setCellStyle(cellStyleBold);

            Cell cel10rowOrderSubtotal1 = rowOrderSubtotal1.createCell((short) colSub + 2);
            cel10rowOrderSubtotal1.setCellValue(orderSubtotal1.doubleValue());
            cel10rowOrderSubtotal1.setCellStyle(styleCurrencyFormat);
            cel10rowOrderSubtotal1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            //cel10rowOrderSubtotal1.setCellStyle(cellStyleAlignRight);

            Row rowTax = sheet.createRow((short) rowIdx + 3);
            Cell cel8rowTax = rowTax.createCell((short) colSub);
            cel8rowTax.setCellValue("Tax:");
            cel8rowTax.setCellStyle(cellStyleBold);

            Cell cel10rowTax = rowTax.createCell((short) colSub + 2);
            cel10rowTax.setCellValue(taxCost.doubleValue());
            cel10rowTax.setCellStyle(styleCurrencyFormat);
            cel10rowTax.setCellType(HSSFCell.CELL_TYPE_NUMERIC);      
           // cel10rowTax.setCellStyle(cellStyleAlignRight);
            
            //create Cell Environmental Fee (TienDang)
            if(!accountType.equalsIgnoreCase("R") && shipToState.equalsIgnoreCase("CA") && taxExempt == 0)
            {
	            Row rowEnvironmentalFee = sheet.createRow((short) rowIdx + 4);
	            Cell celEnvironmental = rowEnvironmentalFee.createCell((short) colSub);
	            celEnvironmental.setCellValue("Environmental Fee:");
	            celEnvironmental.setCellStyle(cellStyleBold);
	            
	            Cell cel10rowEnvironmental = rowEnvironmentalFee.createCell((short) colSub + 2);
	            cel10rowEnvironmental.setCellValue(Fee.doubleValue());
	            cel10rowEnvironmental.setCellStyle(styleCurrencyFormat);
	            cel10rowEnvironmental.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	            
	            // create cell Order Total
	            Row rowOrderTotal = sheet.createRow((short) rowIdx + 5);
            	Cell cel9rowOrderTotal = rowOrderTotal.createCell((short) colSub);
                cel9rowOrderTotal.setCellValue("Order Total:");
                cel9rowOrderTotal.setCellStyle(cellStyleBold);

                Cell cel10rowOrderTotal = rowOrderTotal.createCell((short) colSub + 3);
                cel10rowOrderTotal.setCellValue(orderTotal.doubleValue() + Fee.doubleValue());
                cel10rowOrderTotal.setCellStyle(styleCurrencyFormat);
                cel10rowOrderTotal.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                
            }else{
            	
            	Row rowOrderTotal = sheet.createRow((short) rowIdx + 4);
            	Cell cel9rowOrderTotal = rowOrderTotal.createCell((short) colSub);
                cel9rowOrderTotal.setCellValue("Order Total:");
                cel9rowOrderTotal.setCellStyle(cellStyleBold);

                Cell cel10rowOrderTotal = rowOrderTotal.createCell((short) colSub + 2);
                cel10rowOrderTotal.setCellValue(orderTotal.doubleValue() + Fee.doubleValue());
                cel10rowOrderTotal.setCellStyle(styleCurrencyFormat);
                cel10rowOrderTotal.setCellType(HSSFCell.CELL_TYPE_NUMERIC);   
            }
            //cel10rowOrderTotal.setCellStyle(cellStyleAlignRight);

            //Start Merge Cell
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

            sheet.addMergedRegion(new CellRangeAddress(1, //first row (0-based)
                1, 0, //first column (0-based)
                5));

            sheet.addMergedRegion(new CellRangeAddress(1, //first row (0-based)
                1, 6, //first column (0-based)
                11));

            sheet.addMergedRegion(new CellRangeAddress(2, //first row (0-based)
                2, 0, //first column (0-based)
                5));

            sheet.addMergedRegion(new CellRangeAddress(2, //first row (0-based)
                2, 6, //first column (0-based)
                11));

            sheet.addMergedRegion(new CellRangeAddress(3, //first row (0-based)
                3, 0, //first column (0-based)
                2));

            int colIdxMeg = 9;
            if(isCustomer != null)
                colIdxMeg++;

            sheet.addMergedRegion(new CellRangeAddress(4, //first row (0-based)
                4, 3, //first column (0-based)
                colIdxMeg));

            for (int i = 5; i < listEstoreBasketItem.size() + 5; i++)
            {
                sheet.addMergedRegion(new CellRangeAddress(i, //first row (0-based)
                    i, 3, colIdxMeg));
            }
            
            sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, 10, 11));
            sheet.addMergedRegion(new CellRangeAddress(rowIdx, rowIdx, colSub + 2, colSub + 3));
            
            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 1, rowIdx + 1, colSub, colSub + 1));
            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 1, rowIdx + 1, colSub + 2, colSub + 3));

            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 2, rowIdx + 2, colSub, colSub + 1));
            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 2, rowIdx + 2, colSub + 2, colSub + 3));

            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 3, rowIdx + 3, colSub, colSub + 1));
            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 3, rowIdx + 3, colSub + 2, colSub + 3));
            
            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 4, rowIdx + 4, colSub, colSub + 1));
            sheet.addMergedRegion(new CellRangeAddress(rowIdx + 4, rowIdx + 4, colSub + 2, colSub + 3));
            //End Merge Cell

            //Set Attribute
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", orderRow.getOrder_id() + "_" + Calendar.getInstance().getTimeInMillis());
        }
        catch (Exception e)
        {
            LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    private Boolean sendMailNotifyCustomer(OrderHeader orderHeader, HttpServletRequest request)
    {
        Boolean flag = true;

        try
        {
            HttpSession sessions = request.getSession();
            Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }

            MailUtils mailUtils = MailUtils.getInstance();
            String shopper_id = "";

            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
            }

            CheckoutService checkoutService = new CheckoutService();
            List<EstoreBasketItem> listEstoreBasketItem = checkoutService.getItemCheck(shopper_id, byAgent);

            String items = "";

            for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
            {
                items =
                    items.concat("<tr align=\"center\">").concat("<td align=\"center\">" + estoreBasketItem.getItem_sku() + "</td>").concat(
                        "<td>" + Constants.FormatCurrency(estoreBasketItem.getPlaced_price() / 100) + "</td>").concat("<td align=\"left\">" + estoreBasketItem.getName() + "</td>").concat("</tr>");
            }

            //Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailUtils.getHost());
            email.setSmtpPort(mailUtils.getSmtp_port());
            email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
            email.setTLS(true);
            //send email to agent
            email.setFrom(mailUtils.getFrom());

            email.addTo(orderHeader.getBill_to_email(), orderHeader.getBill_to_name());
            email.setSubject("New Customer Order");

            String htmlContent =
                "<div style=\"font-size:12px;font-family:Courier New\">" + "Dear "
                    + orderHeader.getBill_to_name()
                    + ","
                    + "<BR/>"
                    + "Thank you for your purchase from Dell Financial Services.   We appreciate your business.   Your order #"
                    + orderHeader.getOrderNumber()
                    + " has been received and is currently being processed.    Your complete order information is listed below.   If you have any questions about your order, please contact us at 1.800.891.8595 or email us at us_dfsdirectsales_cs@dell.com."
                    + "<BR/>" + "ORDER INFORMATION" + "<BR/>" + "Order #: " + orderHeader.getOrderNumber() + "<BR/>" + "Order Items (" + listEstoreBasketItem.size() + "):" + "<BR/>"

                    + "<table style=\"font-size:11px;font-family:Courier New\">" + "<tr> <td colspan=\"3\"> <hr width=\"100%\" style=\"border-top: 1px dashed #000000;color:#FFFFFF\" /><td> </tr>"
                    + "<tr>" + "<th>Service Tag</th>" + "     <th>Price</th>" + "     <th>Title</th>" + "</tr>"
                    + "<tr> <td colspan=\"3\"> <hr width=\"100%\" style=\"border-top: 1px dashed #000000;color:#FFFFFF\" /><td> </tr>" + items
                    + "<tr> <td colspan=\"3\"> <hr width=\"100%\" style=\"border-top: 1px dashed #000000;color:#FFFFFF\" /><td> </tr>" + "</table>"

                    + "<BR/>" + "Subtotal:    " + Constants.FormatCurrency(orderHeader.getOadjust_subtotal().floatValue()) + "<BR/>" + "Shipping:    "
                    + Constants.FormatCurrency(orderHeader.getShipping_total().floatValue()) + "<BR/>" + "Tax:         " + Constants.FormatCurrency(orderHeader.getTax_total().floatValue()) + "<BR/>"
                    + "Grand Total: " + Constants.FormatCurrency(orderHeader.getTotal_total().floatValue()) + "<BR/>" + "<BR/>" + "Your order is being shipped to:" + "<BR/>" + "<BR/>"
                    + Constants.collapseRowBR("<b>" + orderHeader.getBill_to_name() + "</b>", "") + Constants.collapseRowBR(orderHeader.getBill_to_company(), "")
                    + Constants.collapseRowBR(orderHeader.getBill_to_address1(), "") + Constants.collapseRowBR(orderHeader.getBill_to_address2(), "")
                    + Constants.collapseRowBR(orderHeader.getBill_to_city() + ", " + orderHeader.getBill_to_state() + ", " + orderHeader.getBill_to_postal(), "")
                    + Constants.collapseRowBR(orderHeader.getBill_to_country(), "") + Constants.collapseRowBR(orderHeader.getBill_to_phone(), "TEL #:")
                    + Constants.collapseRowBR(orderHeader.getBill_to_phoneext(), "ext:") + Constants.collapseRowBR(orderHeader.getBill_to_fax(), "FAX #:") + "<BR/>" + "Notes:" + "<BR/>"
                    + "-Total order processing time is estimated to be 2 - 3 business days after payment approval." + "<BR/>"
                    + "-You will receive a Ship Confirmation email after the order has been fully processed and shipped." + "<BR/>"
                    + "-Transit times will vary, based on location and the ship method that was selected." + "<BR/>"
                    + "-If your shipping address is incorrect or if you need to update your shipping information, please contact customer service at 1-800-891-8595." + "<BR/>" + "<BR/>" + "<BR/>"
                    + "Again, thank you for shopping DFSDirectSales.com." + "<BR/>" + "<BR/>" + "Sincerely," + "<BR/>" + "Dell Financial Services" + "<BR/>" + "www.dfsdirectsales.com" + "<BR/>"
                    + "1.800.891.8595  " + "</div>";
            // set the html message
            email.setHtmlMsg(htmlContent);

            // send the email
            email.send();
        }
        catch (Exception e)
        {
            flag = false;
            LOGGER.info("sendMailNotify  - Exception");
            e.printStackTrace();
            // TODO: handle exception
        }
        return flag;
    }
    
    private Boolean sendMailNotifyAgent(OrderHeader orderHeader, HttpServletRequest request)
    {
        Boolean flag = true;
        String ship_via = "";
        String payment_type = "";

        try
        {
            HttpSession sessions = request.getSession();
            Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }

            MailUtils mailUtils = MailUtils.getInstance();
            String shopper_id = "";

            if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
            {
                shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
            }

            //Create the email message
            HtmlEmail email = new HtmlEmail();
            email.setHostName(mailUtils.getHost());
            email.setSmtpPort(mailUtils.getSmtp_port());
            email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
            email.setTLS(true);
            //send email to agent
            email.setFrom(mailUtils.getFrom());
            
            if (orderHeader.getCc_type().equalsIgnoreCase("Net 15 Days") || orderHeader.getCc_type().equalsIgnoreCase("Net 30 Days"))
            	payment_type = "Net Days";
            else if (orderHeader.getCc_type().equalsIgnoreCase("Visa") || orderHeader.getCc_type().equalsIgnoreCase("MasterCard") || orderHeader.getCc_type().equalsIgnoreCase("American Express"))
            	payment_type = "Credit Card";
            else 
            	payment_type = orderHeader.getCc_type();

            //email.addTo("ustphan@yahoo.com", "DFS JDE Account");
            email.addTo("DFS_EOL_Sales_Support@dell.com", "DFS EOF Sales Support");
            email.addTo("Sarah_Lieurance@dell.com", "Sarah Lieurance");
            email.addTo("Amy_Calhoun@dell.com", "Amy Calhoun");
            email.addTo("Tara_Holman@dell.com", "Tara Holman");
            if (orderHeader.getAgentIDEnter() == 75)
            	email.addTo("Brenda_Strawn@dell.com", "Brenda Strawn");
            else if (orderHeader.getAgentIDEnter() == 166)
            	email.addTo("lisa_rolls@dell.com", "Lisa Rolls");
            
            email.setSubject(" Order Release Rqst - " + orderHeader.getOrderNumber() + ", " + orderHeader.getCc_type() + ", " + orderHeader.getBill_to_company());

            if (orderHeader.getShip_method().equalsIgnoreCase("1"))
            	ship_via = "Ground";
            else if (orderHeader.getShip_method().equalsIgnoreCase("4"))
            	ship_via = "Next Day";
            else if (orderHeader.getShip_method().equalsIgnoreCase("5"))
            	ship_via = "2nd Day";
            else if (orderHeader.getShip_terms().equalsIgnoreCase("Optional Ground / Item"))
            	ship_via = "Optional Ground";
            else if (orderHeader.getShip_terms().equalsIgnoreCase("Optional Next Day / Item"))
            	ship_via = "Optional Next Day";
            else if (orderHeader.getShip_terms().equalsIgnoreCase("Optional 2 Days / Item"))
            	ship_via = "Optional 2 Days";
            else
            	ship_via = orderHeader.getShip_terms();
            
            String htmlContent =
                "<div style=\"font-size:12px;font-family:Courier New\">"
                    + "Order Number: " + orderHeader.getOrderNumber()
                    + "<BR/>"
                    + "Payment Type: " + payment_type
                    + "<BR/>"
                    + "Bill To Name: " + orderHeader.getBill_to_name()
                    + "<BR/>"
                    + "Bill To Company: " + orderHeader.getBill_to_company()
                    + "<BR/>"
                    + "Ship Via: " + ship_via
                    + "<BR/>"
                    + "Payment Method: " + orderHeader.getCc_type()
                    + "<BR/>"
                    + "Payment Information: " + orderHeader.getPayment_terms()
                    + "<BR/>"
                    + "Order Total: " + Constants.FormatCurrency(orderHeader.getTotal_total().floatValue())
                    + "</div>";
            // set the html message
            email.setHtmlMsg(htmlContent);

            // send the email
            email.send();
        }
        catch (Exception e)
        {
            flag = false;
            LOGGER.info("sendMailNotify  - Exception");
            e.printStackTrace();
            // TODO: handle exception
        }
        return flag;
    }

    public Avg_mhz getAvgMhz(ActionForm form, HttpServletRequest request)
    {
        Avg_mhz avg_Mhz = new Avg_mhz();

        String orderNumber = "";
        String avgMhz = "";
        int unitMhz = 0;
        int speedTotal = 0;
        Float totalPrice = new Float(0);

        if (request.getParameter("OrderNumber") != null)
        {
            orderNumber = request.getParameter("OrderNumber").toString();
        }

        if (request.getParameter("AvgMhz") != null)
        {
            avgMhz = request.getParameter("AvgMhz").toString();
        }

        if (request.getParameter("UnitMhz") != null)
        {
            unitMhz = Integer.parseInt(request.getParameter("UnitMhz").toString());
        }

        if (request.getParameter("OrderSubtotal") != null)
        {
            totalPrice = Float.parseFloat(request.getParameter("OrderSubtotal").toString());
        }

        if (unitMhz == 0)
        {
            avgMhz = "N/A";
            speedTotal = 0;
            totalPrice = (float) 0;
        }
        else
        {
            if (request.getParameter("SpeedTotal") != null)
            {
                speedTotal = new Float(request.getParameter("SpeedTotal").toString()).intValue();
            }
        }

        avg_Mhz.setOrder_number(orderNumber);
        avg_Mhz.setAvgmhz(avgMhz);
        avg_Mhz.setUnit_mhz(unitMhz);
        avg_Mhz.setSpeed_total(speedTotal);
        avg_Mhz.setTotal_price(BigDecimal.valueOf(totalPrice.doubleValue()));

        return avg_Mhz;
    }
}
