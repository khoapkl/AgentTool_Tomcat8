/*
 * FILENAME
 *     CheckoutService.java
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.dell.enterprise.agenttool.DAO.CheckoutDAO;
import com.dell.enterprise.agenttool.model.Avg_mhz;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.WarrantyPartList;
import com.dell.enterprise.agenttool.model.OrderHeader;
import com.dell.enterprise.agenttool.model.OrderLine;
import com.dell.enterprise.agenttool.model.PayMethods;
import com.dell.enterprise.agenttool.model.TaxTables;
import com.dell.enterprise.agenttool.util.Constants;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class CheckoutService
{
	private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.services.CheckoutService");
    public List<EstoreBasketItem> getItemCheck(String shopper_id, Boolean byAgent)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getItemCheck(shopper_id, byAgent);
    }

    public List<WarrantyPartList> getWarrantyPartList(int warrantyCat1, int warrantyCat2)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getWarrantyList(warrantyCat1, warrantyCat2);
    }
    
    public List<Float> utilGetDiscount(Customer customer, String item_sku, Float orig_price, Float place_price)
    {
        Float discount = new Float(0);
        Float adjPrice = new Float(0);

        List<Float> returnValue = new ArrayList<Float>();

        ProductServices productServices = new ProductServices();
        int category_id = productServices.getCategoryIdBySku(item_sku);

        CheckoutDAO checkoutDAO = new CheckoutDAO();
        String family = checkoutDAO.getCatIDFamily(item_sku);

        if (category_id == 11955 || category_id == 11950 || category_id == 11947)
        {
            family = checkoutDAO.getPromoByCat(category_id);
        }
        if (orig_price.floatValue() == place_price.floatValue())
        {
            if (family.equals("LAT"))
            {
                if (customer.getLatexpdate() != null && customer.getLatexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getLatperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getLatperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getLatamtdiscount() > 0)
                    {
                        discount = (float) (customer.getLatamtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }

                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else if (family.equals("INS"))
            {
                if (customer.getInsexpdate() != null && customer.getInsexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getInsperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getInsperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getInsamtdiscount() > 0)
                    {
                        discount = (float) (customer.getInsamtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }
                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else if (family.equals("OPT"))
            {

                if (customer.getOptexpdate() != null && customer.getOptexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getOptperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getOptperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getOptamtdiscount() > 0)
                    {
                        discount = (float) (customer.getOptamtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }
                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else if (family.equals("DIM"))
            {
                if (customer.getDimexpdate() != null && customer.getDimexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getDimperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getDimperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getDimamtdiscount() > 0)
                    {
                        discount = (float) (customer.getDimamtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }
                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else if (family.equals("MON"))
            {
                if (customer.getMonexpdate() != null && customer.getMonexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getMonperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getMonperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getMonamtdiscount() > 0)
                    {
                        discount = (float) (customer.getMonamtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }
                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else if (family.equals("SER"))
            {
                if (customer.getSerexpdate() != null && customer.getSerexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getSerperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getSerperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getSeramtdiscount() > 0)
                    {
                        discount = (float) (customer.getSeramtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }
                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else if (family.equals("WOR"))
            {
                if (customer.getWorexpdate() != null && customer.getWorexpdate().after(Calendar.getInstance().getTime()))
                {
                    if (customer.getWorperdiscount() > 0)
                    {
                        discount = (orig_price / 100) * customer.getWorperdiscount();
                        adjPrice = orig_price - discount;
                    }
                    else if (customer.getWoramtdiscount() > 0)
                    {
                        discount = (float) (customer.getWoramtdiscount() * 100);
                        adjPrice = orig_price - discount;
                    }
                    else
                    {
                        adjPrice = orig_price;
                    }
                }
                else
                {
                    adjPrice = orig_price;
                }
            }
            else
            {
                adjPrice = orig_price;
            }
        }
        else
        {
            adjPrice = place_price;
            discount = orig_price - place_price;
        }

        returnValue.add(discount);
        returnValue.add(adjPrice);

        return returnValue;
    }

    public int getMaxDiscount()
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getMaxDiscount();
    }

    public int getPromotionContribByCode(String promotionCode)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getPromotionContribByCode(promotionCode);
    }

    public void setDiscountItem(int contrib, String order_id)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        checkoutDAO.setDiscountItem(contrib, order_id);
    }

    /*
     * Author Vinhhq
     */
    public void setOrderLineItemDiscounts(String shopper_id, HttpServletRequest request)
    {
        BasketService basketService = new BasketService();
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        HttpSession sessions = request.getSession();
        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        Boolean byAgent = true;
        int warrantyQty = 0;
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
            byAgent = false;
        }
        else
        {
            byAgent = true;
        }
        List<EstoreBasketItem> listEstoreBasketItem = basketService.getBasketItems(shopper_id, byAgent);

        for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
        {
            String item_sku = estoreBasketItem.getItem_sku();
            
            //LOGGER.info("Error Execute CheckoutService" + request.getParameter(item_sku + "buy").toString());
            System.out.println("Warranty: " + request.getParameter(item_sku + "buy"));
            if (request.getParameter(item_sku + "buy") == null && item_sku.trim().contains("WARRANTY"))
            {	
            	System.out.println("Delete warranty: " + item_sku);
            	checkoutDAO.deleteWarrantyItem(shopper_id, item_sku, byAgent);
            }
            else if (item_sku.trim().contains("WARRANTY"))
            {
            	if (request.getParameter(item_sku + "buy").toString().contains("on"))
            	{
	            	System.out.println("Update warranty: " + item_sku);
	            	Float newPrice = Float.parseFloat(request.getParameter("LIDiscount" + item_sku).toString().substring(1).replace(",","")) * 100;
	            	int qty = (int) Float.parseFloat(request.getParameter("qty" + item_sku).toString());
	            	newPrice = Constants.FloatRound(newPrice);
	                System.out.println(qty);
	            	checkoutDAO.setOrderLineUpdateWarranty(newPrice, shopper_id, item_sku, qty, byAgent);
            	}
            }
            else
            {
            	Float newPrice = Float.parseFloat(request.getParameter("LIDiscount" + item_sku).toString().substring(1).replace(",","")) * 100;
                newPrice = Constants.FloatRound(newPrice);
                checkoutDAO.setOrderLineItemDiscount(newPrice, shopper_id, item_sku);
            }
            	
        }
        
        if ((!request.getParameter("sku_list").contains("WARRANTY_DT")) && ((int) Float.parseFloat(request.getParameter("warrantyDT").toString()) > 0)){
        	if (request.getParameter("WARRANTY_DTnew") != null)
        	{
	        	if (request.getParameter("WARRANTY_DTnew").toString().contains("on")){
		        	System.out.println(request.getParameter("LIDiscountWARRANTY_DT").toString());
		        	Float newPrice = Float.parseFloat(request.getParameter("LIDiscountWARRANTY_DT").toString().substring(1).replace(",",""))  * 100;
		        	warrantyQty = (int) Float.parseFloat(request.getParameter("qtyWARRANTY_DT").toString());
		        	
		            newPrice = Constants.FloatRound(newPrice);
		            
		            checkoutDAO.setOrderLineAddWarranty(newPrice, shopper_id, "WARRANTY_DT", warrantyQty, 11941, newPrice, byAgent);
		        }
        	}
        }
        
        if ((!request.getParameter("sku_list").contains("WARRANTY_NB")) && ((int) Float.parseFloat(request.getParameter("warrantyNB").toString()) > 0)){
        	if (request.getParameter("WARRANTY_NBnew") != null)
        	{
	        	if (request.getParameter("WARRANTY_NBnew").toString().contains("on")){
		        	Float newPrice = Float.parseFloat(request.getParameter("LIDiscountWARRANTY_NB").toString().substring(1).replace(",",""))  * 100;
		        	warrantyQty = (int) Float.parseFloat(request.getParameter("qtyWARRANTY_NB").toString());
		        	
		        	//newPrice = Constants.FloatRound(newPrice);
		            newPrice = Constants.FloatRound(newPrice);
		            //System.out.println(newPrice);
		            checkoutDAO.setOrderLineAddWarranty(newPrice, shopper_id, "WARRANTY_NB", warrantyQty, 11940, newPrice, byAgent);
		        }
        	}
        }
    }

    public Object[][] getShippingPrice(boolean usingManager, List<EstoreBasketItem> listEstoreBasketItem, String shipPostal)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getShippingPrice(usingManager, listEstoreBasketItem, shipPostal);
    }

    /*
     * Author Vinhhq
     */
    public void orderUpdateShipping(String shopper_id, String ship_method, String ship_terms, Float ship_cost, int agentId)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        checkoutDAO.estoreBasketUpdate(shopper_id, ship_method, ship_terms, ship_cost, agentId);
        checkoutDAO.contactInfoUpdate(shopper_id, ship_method);
    }

    public String getShortShipping(String ship_method, String ship_terms)
    {
        String shortShipping = "";
        if (Constants.isNumber(ship_method))
        {
            if (Integer.parseInt(ship_method) == 0)
            {
                shortShipping = "Other: " + ship_terms;
            }
            else
            {
                CheckoutDAO checkoutDAO = new CheckoutDAO();
                shortShipping = checkoutDAO.shortShipping(ship_method);
            }
        }

        return shortShipping;
    }

    public List<PayMethods> getPayMethods()
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getPayMethods();
    }

    public OrderHeader getOrderReceipt(String shopper_id, String order_id)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getOrderReceipt(shopper_id, order_id);
    }

    public TaxTables getTaxTables(String zip, String city)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getTaxTables(zip, city);
    }

    public Boolean setOrderHeader(OrderHeader orderHeader)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.setOrderHeader(orderHeader);
    }

    public Boolean setOrderLine(List<OrderLine> listOrderLine)
    {
        Boolean flag = true;
        CheckoutDAO checkoutDAO = new CheckoutDAO();

        for (OrderLine orderLine : listOrderLine)
        {
            flag = checkoutDAO.setOrderLine(orderLine);
        }

        return flag;
    }

    public Boolean clearBasket(String shopper_id, Boolean byAgent)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.clearBasket(shopper_id, byAgent);
    }

    public Boolean setOrdersHeld(String oldShopper_id, String newShopper_id, Timestamp expirationHoldDate, String agent_id)
    {
        Boolean flag = false;
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        Boolean x = checkoutDAO.setOrdersHeld(oldShopper_id, newShopper_id, expirationHoldDate);
        Boolean y = checkoutDAO.setBasketsHeld(oldShopper_id, newShopper_id, agent_id);
        if (x == true && y == true)
        {
            flag = true;
        }
        return flag;
    }

    public Boolean setOrdersCancel(String orderNumber)
    {
    	CheckoutDAO checkoutDAO = new CheckoutDAO();
    	return checkoutDAO.setBasketsCancel(orderNumber);
    }
    
    public int getCheckCat(String shopper_id, Boolean byAgent)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getCheckCat(shopper_id,byAgent);
    }
    
    public int getCatQty(String shopper_id, int category_id)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getQtyCat(shopper_id,category_id);
    }

    public int getHoldDays()
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.getHoldDays();
    }

    public Boolean deleteOrders(String order_id, String shopper_id)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.deleteOrders(order_id, shopper_id);
    }

    public Boolean addAvgMhz(Avg_mhz avgMhz)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        if (checkoutDAO.deleteAvgMhz(avgMhz.getOrder_number()))
        {
            return checkoutDAO.addAvgMhz(avgMhz);
        }
        return false;
    }

    public Boolean deleteAvgMhz(String orderNumber)
    {
        CheckoutDAO checkoutDAO = new CheckoutDAO();
        return checkoutDAO.deleteAvgMhz(orderNumber);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> verifyCard(OrderHeader orderHeader)
    {

    	Map<String, String> objResponse = new HashMap<String, String>();
    	String GatewayHost = "https://pilot-payflowpro.paypal.com";

        String strExpDate = "&EXPDATE=";
        if (orderHeader.getCc_expmonth() < 10)
        {
            strExpDate = strExpDate.concat("0" + Integer.toString(orderHeader.getCc_expmonth()));
        }
        else
        {
            strExpDate = strExpDate.concat(Integer.toString(orderHeader.getCc_expmonth()));
        }
        strExpDate = strExpDate.concat(Integer.toString(orderHeader.getCc_expyear()).substring(2));

        String parmList = "";
        parmList = parmList + "TENDER=C";
        parmList = parmList + "&PWD=vncard1";
        parmList = parmList + "&USER=vncard";
        parmList = parmList + "&VENDOR=mrifordfsdirect";
        parmList = parmList + "&PARTNER=Verisign";
        parmList = parmList + "&ACCT=" + orderHeader.getCc_number();
        parmList = parmList + strExpDate;
        parmList = parmList + "&CVV2=" + orderHeader.getCc_cvv2();
        parmList = parmList + "&AMT=" + Constants.FloatRound(orderHeader.getTotal_total().floatValue());
        parmList = parmList + "&COMMENT1=" + orderHeader.getOrderNumber();
        parmList = parmList + "&COMMENT2=Agent Order";
        parmList = parmList + "&TRXTYPE=S";
        parmList = parmList + "&CITY=" + orderHeader.getBill_to_city();
        parmList = parmList + "&STATE=" + orderHeader.getBill_to_state();
        parmList = parmList + "&STREET=" + orderHeader.getBill_to_address1();
        parmList = parmList + "&ZIP=" + orderHeader.getBill_to_postal();

        try
        {
            // Send the request
            URL url = new URL(GatewayHost);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "text/namevalue");
            conn.setRequestProperty("X-VPS-Timeout", "30");
            conn.setRequestProperty("X-VPS-Request-ID", Constants.generateID(32));

            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            //write parameters
            writer.write(parmList);
            writer.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null)
            {
                answer.append(line);
            }
            writer.close();
            reader.close();

            //Output the response

            if (answer != null && answer.length() > 0)
            {
                String[] tempArrStr = answer.toString().split("&");
                for (String tempStr : tempArrStr)
                {
                    String[] elements = tempStr.split("=");
                    objResponse.put(elements[0], elements[1]);
                }
                objResponse.put("UrlResponse", answer.toString());
                objResponse.put("OrderId", orderHeader.getOrderNumber());
            }

        }
        catch (Exception e)
        {
            System.out.println("General System. Function verify card error.");
            e.printStackTrace();
        }

        return objResponse;
    }

    public Boolean checkStateInShippingTax(String state)
    {
        CheckoutDAO CheckoutDAO = new CheckoutDAO();
        return CheckoutDAO.checkStateInShippingTax(state);
    }

    public Boolean addAuthLog(Map<String, String> objResponse)
    {
        if (!objResponse.isEmpty())
        {
            CheckoutDAO CheckoutDAO = new CheckoutDAO();
            return CheckoutDAO.addAuthLog(objResponse);
        }
        else
            return false;
    }
}
