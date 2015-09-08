/*
 * FILENAME
 *     OrderManagerExport.java
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
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Order;
import com.dell.enterprise.agenttool.model.OrderAgent;
import com.dell.enterprise.agenttool.model.OrderAgentDetail;
import com.dell.enterprise.agenttool.model.OrderCriteria;
import com.dell.enterprise.agenttool.model.OrderDate;
import com.dell.enterprise.agenttool.model.OrderHeld;
import com.dell.enterprise.agenttool.model.OrderShopper;
import com.dell.enterprise.agenttool.model.OrderSummary;
import com.dell.enterprise.agenttool.model.Summary;
import com.dell.enterprise.agenttool.services.OrderServices;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;
import com.sun.xml.internal.bind.v2.TODO;

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
public class OrderManagerExport extends DispatchAction

{
    private int pageRecord = Constants.PAGE_ORDER_RECORD;
    private int orderRecord = Constants.PAGE_RECORD;

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();

        try
        {
            String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);

            if (session.getAttribute(Constants.AGENT_INFO) == null)
            {
                forward = mapping.findForward(Constants.SESSION_TIMEOUT);
                Cookie[] cookies = request.getCookies();
                for (int i = 0; i < cookies.length; i++)
                {
                    if (cookies[i].getName().equals("userLevel"))
                    {
                        if (cookies[i].getValue().equals("login.do"))
                            forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                    }
                }

            }
            else if (method == null || "".equals(method))
            {
                forward = mapping.findForward(Constants.ORDER_MANAGEMENT);
            }
            else
            {
                forward = this.dispatchMethod(mapping, form, request, response, method);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
        }

        return forward;
    }

    public ActionForward excelAllOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

        	HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();

            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));
                        
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);

            
            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);
            
            HttpSession session = request.getSession();

            OrderServices service = new OrderServices();
            List<String> searchOrder = (List<String>) session.getAttribute(Constants.ORDER_CRITERIA);
            List<Order> mapOrder = service.searchOrderCriteria(1, searchOrder);
            List<Order> listOrder = new ArrayList<Order>();
            int totalRecord = 0;
            if (!mapOrder.isEmpty())
            {
                Order order = mapOrder.get(0);
                totalRecord = order.getTotalRow();
                int numOfPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
                for (int i = 1; i <= numOfPage; i++)
                {
                    listOrder.addAll(service.searchOrderCriteria(i, searchOrder));
                }
            }

            //            String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);
            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("Order Lookup");
            row0.getCell(0).setCellStyle(cellStyleBold);

            Row row2 = sheet.createRow((short) 3);
            row2.createCell(0).setCellValue("");
            row2.createCell(1).setCellValue("Order Number");
            row2.getCell(1).setCellStyle(cellStyleBold);
            row2.createCell(2).setCellValue(searchOrder.get(0));
            row2.createCell(3).setCellValue("Sort Order");
            row2.getCell(3).setCellStyle(cellStyleBold);
            String sortOrder = "Order Date";
            if (searchOrder.get(1).equals("1"))
                sortOrder = "Ship To Name";
            if (searchOrder.get(1).equals("2"))
                sortOrder = "Items Sold";
            if (searchOrder.get(1).equals("3"))
                sortOrder = "Discount";
            if (searchOrder.get(1).equals("4"))
                sortOrder = "Total";

            row2.createCell(4).setCellValue(sortOrder);
            row2.createCell(5).setCellValue("Service Tag");
            row2.getCell(5).setCellStyle(cellStyleBold);
            row2.createCell(6).setCellValue(searchOrder.get(2));
            row2.createCell(7).setCellValue("Listing Number");
            row2.getCell(7).setCellStyle(cellStyleBold);
            row2.createCell(8).setCellValue(searchOrder.get(3));

            Row row3 = sheet.createRow((short) 4);
            row3.createCell(0).setCellValue("");
            row3.createCell(1).setCellValue("Customer Number");
            row3.getCell(1).setCellStyle(cellStyleBold);
            row3.createCell(2).setCellValue(searchOrder.get(4));
            row3.createCell(3).setCellValue("Order Type");
            row3.getCell(3).setCellStyle(cellStyleBold);
            String typeOrder = "All";
            if (searchOrder.get(5).equals("1"))
                typeOrder = "Store";
            if (searchOrder.get(5).equals("2"))
                typeOrder = "Agent";
            if (searchOrder.get(5).equals("3"))
                typeOrder = "Auction";
            if (searchOrder.get(5).equals("4"))
                sortOrder = "Ebay";

            row3.createCell(4).setCellValue(typeOrder);
            row3.createCell(5).setCellValue("Email Address");
            row3.getCell(5).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue(searchOrder.get(6));

            Row row4 = sheet.createRow((short) 5);
            row4.createCell(0).setCellValue("Shipping");
            row4.getCell(0).setCellStyle(cellStyleBold);
            row4.createCell(1).setCellValue("First Name");
            row4.getCell(1).setCellStyle(cellStyleBold);
            row4.createCell(2).setCellValue(searchOrder.get(7));
            row4.createCell(3).setCellValue("Last Name");
            row4.getCell(3).setCellStyle(cellStyleBold);
            row4.createCell(4).setCellValue(searchOrder.get(8));
            row4.createCell(5).setCellValue("Company");
            row4.getCell(5).setCellStyle(cellStyleBold);
            row4.createCell(6).setCellValue(searchOrder.get(9));
            row4.createCell(7).setCellValue("Phone");
            row4.getCell(7).setCellStyle(cellStyleBold);
            row4.createCell(8).setCellValue(searchOrder.get(10));

            Row row5 = sheet.createRow((short) 6);
            row5.createCell(0).setCellValue("Billing");
            row5.getCell(0).setCellStyle(cellStyleBold);
            row5.createCell(1).setCellValue("First Name");
            row5.getCell(1).setCellStyle(cellStyleBold);
            row5.createCell(2).setCellValue(searchOrder.get(11));
            row5.createCell(3).setCellValue("Last Name");
            row5.getCell(3).setCellStyle(cellStyleBold);
            row5.createCell(4).setCellValue(searchOrder.get(12));
            row5.createCell(5).setCellValue("Company");
            row5.getCell(5).setCellStyle(cellStyleBold);
            row5.createCell(6).setCellValue(searchOrder.get(13));
            row5.createCell(7).setCellValue("Phone");
            row5.getCell(7).setCellStyle(cellStyleBold);
            row5.createCell(8).setCellValue(searchOrder.get(14));

            Row row7 = sheet.createRow((short) 7);
            row7.createCell(10).setCellValue("Notebooks");
            row7.getCell(10).setCellStyle(cellStyleBold);
            row7.createCell(12).setCellValue("Desktops");
            row7.getCell(12).setCellStyle(cellStyleBold);
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 10, 11));
            sheet.addMergedRegion(new CellRangeAddress(7, 7, 12, 13));

            Row row8 = sheet.createRow((short) 9);
            row8.createCell(0).setCellValue("Order");
            row8.getCell(0).setCellStyle(cellStyleBold);
            row8.createCell(1).setCellValue("Date");
            row8.getCell(1).setCellStyle(cellStyleBold);  
            row8.createCell(2).setCellValue("Ship To Name");
            row8.getCell(2).setCellStyle(cellStyleBold);
            row8.createCell(3).setCellValue("Status");
            row8.getCell(3).setCellStyle(cellStyleBold);
            row8.createCell(4).setCellValue("Sale Type");
            row8.getCell(4).setCellStyle(cellStyleBold);
            row8.createCell(5).setCellValue("Items");
            row8.getCell(5).setCellStyle(cellStyleBold);
            row8.createCell(6).setCellValue("Discount");
            row8.getCell(6).setCellStyle(cellStyleBold);
            row8.createCell(7).setCellValue("System Total");
            row8.getCell(7).setCellStyle(cellStyleBold);
            row8.createCell(8).setCellValue("Freight");
            row8.getCell(8).setCellStyle(cellStyleBold);
            row8.createCell(9).setCellValue("Total");
            row8.getCell(9).setCellStyle(cellStyleBold);
            row8.createCell(10).setCellValue("ASP");
            row8.getCell(10).setCellStyle(cellStyleBold);
            row8.createCell(11).setCellValue("$/Mhz");
            row8.getCell(11).setCellStyle(cellStyleBold);
            row8.createCell(12).setCellValue("ASP");
            row8.getCell(12).setCellStyle(cellStyleBold);
            row8.createCell(13).setCellValue("$/Mhz");
            row8.getCell(13).setCellStyle(cellStyleBold);
            int rowIdx = 10;
            if (listOrder.size() > 0)
            {

                for (Order order : listOrder)
                {
                    Row row = sheet.createRow((short) rowIdx);
                    row.createCell(0).setCellValue(order.getOrderId());
                    row.createCell(1).setCellValue(order.getDayOrder());
                    String company = (order.getShip_to_company() != null && !order.getShip_to_company().isEmpty()) ? " (" + order.getShip_to_company() + ")" : "";
                    row.createCell(2).setCellValue(order.getShip_to_name() + company);
                    row.createCell(3).setCellValue(order.getStatus());
                    row.createCell(4).setCellValue(order.getOrderType());
                    row.createCell(5).setCellValue(order.getItem());
                    
                    row.createCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);  
                    row.getCell(6).setCellStyle(stylePercentFormat);
                    row.getCell(6).setCellValue(order.getDiscount()/100.0);

                    row.createCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(7).setCellStyle(styleCurrencyFormat);
                    row.getCell(7).setCellValue(Double.parseDouble(order.getOadjust_total().toString()));
                    
                    
                    row.createCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(8).setCellStyle(styleCurrencyFormat);
                    row.getCell(8).setCellValue(Double.parseDouble(order.getFreight_total().toString()));
                    
                    row.createCell(9).setCellType(HSSFCell.CELL_TYPE_NUMERIC);                    
                    row.getCell(9).setCellStyle(styleCurrencyFormat);
                    row.getCell(9).setCellValue(Double.parseDouble(order.getTotal_total().toString()));
                    
                    row.createCell(10).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(10).setCellStyle(styleCurrencyFormat);
                    row.getCell(10).setCellValue(Double.parseDouble(order.getNote().getAsp().toString()));  
                    
                    row.createCell(11).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(11).setCellStyle(styleCurrencyFormat);
                    row.getCell(11).setCellValue(Double.parseDouble(order.getNote().getMhz().toString()));
                   
                    row.createCell(12).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(12).setCellStyle(styleCurrencyFormat);
                    row.getCell(12).setCellValue(Double.parseDouble(order.getDesk().getAsp().toString()));

                    row.createCell(13).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(13).setCellStyle(styleCurrencyFormat);
                    row.getCell(13).setCellValue(Double.parseDouble(order.getDesk().getMhz().toString()));
                    
                    rowIdx++;

                }
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "OrderLookup");

        }

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelHeldOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {
            String typeHold = request.getParameter(Constants.TYPE_HOLD_ORDER);
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            CellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFDataFormat df = wb.createDataFormat();
            HSSFCellStyle styleCurrency3DecimalFormat = null;
            styleCurrency3DecimalFormat = wb.createCellStyle();
            styleCurrency3DecimalFormat.setDataFormat(df.getFormat("$#,##0.000"));
            
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            HttpSession session = request.getSession();
            OrderServices service = new OrderServices();
            Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
            if (session.getAttribute(Constants.IS_CUSTOMER) == null)
            {
                //                Row row0 = sheet.createRow((short) 0);
                //                row0.createCell(0).setCellValue("Current User: " + session.getAttribute(Constants.SHOPPER_NAME).toString());
                Row row1 = sheet.createRow((short) 1);
                if (typeHold.equals("CUSTOMER"))
                    row1.createCell(0).setCellValue("Agent store-Held Orders");
                else
                    row1.createCell(0).setCellValue("Agent-Held Orders");

                row1.getCell(0).setCellStyle(cellStyleBold);

                Row row2 = sheet.createRow((short) 3);
                //String totalHeld = Converter.getMoney(service.totalOrderHeld(agent, typeHold));
                row2.createCell(0).setCellValue("Order Total");
                row2.getCell(0).setCellStyle(cellStyleBold);
                
                row2.createCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row2.getCell(1).setCellStyle(styleCurrencyFormat);
                row2.getCell(1).setCellValue(service.totalOrderHeld(agent, typeHold).doubleValue());

                Row row3 = sheet.createRow((short) 4);
                row3.createCell(0).setCellValue("Order Number");
                row3.getCell(0).setCellStyle(cellStyleBold);
                row3.createCell(1).setCellValue("Modify Date");
                row3.getCell(1).setCellStyle(cellStyleBold);
                row3.createCell(2).setCellValue("Ship To Name");
                row3.getCell(2).setCellStyle(cellStyleBold);
                row3.createCell(3).setCellValue("Items Held");
                row3.getCell(3).setCellStyle(cellStyleBold);
                row3.createCell(4).setCellValue("Order Total");
                row3.getCell(4).setCellStyle(cellStyleBold);
                row3.createCell(5).setCellValue("System Total");
                row3.getCell(5).setCellStyle(cellStyleBold);
                row3.createCell(6).setCellValue("ASP");
                row3.getCell(6).setCellStyle(cellStyleBold);
                row3.createCell(7).setCellValue("Avg $/Mhz");
                row3.getCell(7).setCellStyle(cellStyleBold);
                row3.createCell(8).setCellValue("Avg Mhz");
                row3.getCell(8).setCellStyle(cellStyleBold);
                row3.createCell(9).setCellValue("Agent");
                row3.getCell(9).setCellStyle(cellStyleBold);
                row3.createCell(10).setCellValue("Discount");
                row3.getCell(10).setCellStyle(cellStyleBold);
                int rowIdx = 5;
                List<OrderHeld> orderHeldMap = new ArrayList<OrderHeld>();
                service.orderHelpMap(1, agent, typeHold);
                int totalRecord = service.getTotalRecord();
                int numOfPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
                for (int i = 1; i <= numOfPage; i++)
                {
                    orderHeldMap.addAll(service.orderHelpMap(i, agent, typeHold));
                }
                if (orderHeldMap.size() > 0)
                {
                    for (OrderHeld order : orderHeldMap)
                    {
                        Row row = sheet.createRow((short) rowIdx);
                        row.createCell(0).setCellValue(order.getOrderId());
                        row.createCell(1).setCellValue(order.getDayOrder());
                        row.createCell(2).setCellValue(order.getShip_to_name());
                        
                        row.createCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        row.getCell(3).setCellValue(order.getItem());
                        
                        row.createCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        row.getCell(4).setCellStyle(styleCurrencyFormat);
                        row.getCell(4).setCellValue(Double.parseDouble(order.getTotal_total().divide(BigDecimal.valueOf(100)).toString()));
                        
                        if (order.getAvgMhz().getTotal_price() != BigDecimal.valueOf(0)){
	                        row.createCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	                        row.getCell(5).setCellStyle(styleCurrencyFormat);
	                        row.getCell(5).setCellValue(Double.parseDouble(order.getAvgMhz().getTotal_price().toString()));
	                        System.out.println("Total-Price : "+order.getAvgMhz().getTotal_price());
                        }
                        else {
                            row.createCell(5).setCellValue("N/A");
                            row.getCell(5).setCellType(HSSFCell.CELL_TYPE_STRING);
                            row.getCell(5).setCellStyle(cellStyleAlignRight);
                        }
                       
                        row.createCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        row.getCell(6).setCellStyle(styleCurrencyFormat);
                        row.getCell(6).setCellValue(Double.parseDouble(order.getAvgMhz().getTotal_price().divide(BigDecimal.valueOf(order.getItem()), 2).toString()));
                        
                        if(order.getAvgMhz().getAvgmhz().equalsIgnoreCase("N/A"))
                        {
                        	row.createCell(7).setCellValue(order.getAvgMhz().getAvgmhz());
                            row.getCell(7).setCellType(HSSFCell.CELL_TYPE_STRING);
                            row.getCell(7).setCellStyle(cellStyleAlignRight);
                            
                        }else
                        {
                        	row.createCell(7).setCellValue(Double.parseDouble(order.getAvgMhz().getAvgmhz().substring(1)));
	                        row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	                        row.getCell(7).setCellStyle(styleCurrency3DecimalFormat);
                        }

                        if (order.getAvgMhz().getUnit_mhz() > 0){
	                        row.createCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	                        row.getCell(8).setCellValue(Math.round(order.getAvgMhz().getSpeed_total() / order.getAvgMhz().getUnit_mhz()));
                        }
                        else{ 
                        	row.createCell(8).setCellValue("N/A");
                        	row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
                        	row.getCell(8).setCellStyle(cellStyleAlignRight);
                        }

                        row.createCell(9).setCellValue(order.getUser_hold());

                        row.createCell(10).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        row.getCell(10).setCellStyle(stylePercentFormat);
                        row.getCell(10).setCellValue(Converter.getHeldDiscount(order.getPlace_price(), order.getModi_price())/100.0);
                        
                        rowIdx++;
                    }
                }
            }
            if (typeHold.equalsIgnoreCase("customer"))
                typeHold = "Agent_Store";
            else
                typeHold = "Agent";
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", typeHold + "_" + "Held_Orders");
        }
        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelShopperOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);
            
            HSSFCellStyle cellStyleBoldAlignRight = wb.createCellStyle();
            cellStyleBoldAlignRight.setFont(font);
            cellStyleBoldAlignRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
                     
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));


            HttpSession session = request.getSession();

            OrderServices service = new OrderServices();
            OrderCriteria orderCriteria = (OrderCriteria) session.getAttribute(Constants.ORDER_SHOP_CRITERIA);
            List<OrderShopper> listShoppers = new ArrayList<OrderShopper>();
            service.searchOrdersByShopper(1, orderCriteria);
            int totalRecord = service.getTotalRecord();
            int numOfPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
            for (int i = 1; i <= numOfPage; i++)
            {
                listShoppers.addAll(service.searchOrdersByShopper(i, orderCriteria));
            }
            //            String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);
            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("Order By Shopper");
            row0.getCell(0).setCellStyle(cellStyleBold);
            Row row2 = sheet.createRow((short) 3);
            row2.createCell(0).setCellValue("");
            row2.createCell(1).setCellValue("Order Number");
            row2.getCell(1).setCellStyle(cellStyleBold);
            row2.createCell(2).setCellValue(orderCriteria.getOrderId());
            row2.createCell(3).setCellValue("Sort Order");
            row2.getCell(3).setCellStyle(cellStyleBold);
            String sortOrder = "Ship to Name";
            if (orderCriteria.getOrderType().equals("1"))
                sortOrder = "Orders";
            if (orderCriteria.getOrderType().equals("2"))
                sortOrder = "Total";
            row2.createCell(4).setCellValue(sortOrder);

            row2.createCell(5).setCellValue("Item Sku");
            row2.getCell(5).setCellStyle(cellStyleBold);
            row2.createCell(6).setCellValue(orderCriteria.getItemSku());

            Row row3 = sheet.createRow((short) 4);
            row3.createCell(0).setCellValue("");
            row3.createCell(1).setCellValue("Customer Number");
            row3.getCell(1).setCellStyle(cellStyleBold);

            Row row4 = sheet.createRow((short) 5);
            row4.createCell(0).setCellValue("Shipping");
            row4.getCell(0).setCellStyle(cellStyleBold);
            row4.createCell(1).setCellValue("First Name");
            row4.getCell(1).setCellStyle(cellStyleBold);
            row4.createCell(2).setCellValue(orderCriteria.getSfname());
            row4.createCell(3).setCellValue("Last Name");
            row4.getCell(3).setCellStyle(cellStyleBold);
            row4.createCell(4).setCellValue(orderCriteria.getSlname());
            row4.createCell(5).setCellValue("Company");
            row4.getCell(5).setCellStyle(cellStyleBold);
            row4.createCell(6).setCellValue(orderCriteria.getScom());
            row4.createCell(7).setCellValue("Phone");
            row4.getCell(7).setCellStyle(cellStyleBold);
            row4.createCell(8).setCellValue(orderCriteria.getSphone());

            Row row5 = sheet.createRow((short) 6);
            row5.createCell(0).setCellValue("Billing");
            row5.getCell(0).setCellStyle(cellStyleBold);
            row5.createCell(1).setCellValue("First Name");
            row5.getCell(1).setCellStyle(cellStyleBold);
            row5.createCell(2).setCellValue(orderCriteria.getBfname());
            row5.createCell(3).setCellValue("Last Name");
            row5.getCell(3).setCellStyle(cellStyleBold);
            row5.createCell(4).setCellValue(orderCriteria.getBlname());
            row5.createCell(5).setCellValue("Company");
            row5.getCell(5).setCellStyle(cellStyleBold);
            row5.createCell(6).setCellValue(orderCriteria.getBcom());
            row5.createCell(7).setCellValue("Phone");
            row5.getCell(7).setCellStyle(cellStyleBold);
            row5.createCell(8).setCellValue(orderCriteria.getBphone());

            Row row8 = sheet.createRow((short) 8);
            row8.createCell(0).setCellValue("Shopper Name");
            row8.getCell(0).setCellStyle(cellStyleBold);

            row8.createCell(1).setCellValue("Number Orders");
            row8.getCell(1).setCellStyle(cellStyleBoldAlignRight);
            
            row8.createCell(2).setCellValue("Total $");
            row8.getCell(2).setCellStyle(cellStyleBoldAlignRight);
            
            row8.createCell(3).setCellValue("Avg $");
            row8.getCell(3).setCellStyle(cellStyleBoldAlignRight);
            
            row8.createCell(4).setCellValue("Max $");
            row8.getCell(4).setCellStyle(cellStyleBoldAlignRight);
            
            row8.createCell(5).setCellValue("Min $");
            row8.getCell(5).setCellStyle(cellStyleBoldAlignRight);
            
            int rowIdx = 9;
            if (listShoppers.size() > 0)
            {
                for (OrderShopper order : listShoppers)
                {
                    Row row = sheet.createRow((short) rowIdx);
                    String vName = Constants.convertValueEmpty(order.getShip_to_name()) + " " + Constants.convertValueEmpty(order.getShip_to_company());
                    row.createCell(0).setCellValue(vName.trim());
                    
                    row.createCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(1).setCellValue(order.getNumOrder());
                    
                    
                    row.createCell(2).setCellValue(Double.parseDouble(order.getTotal().toString()));
                    //row.getCell(2).setCellStyle(cellStyleAlignRight);
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(2).setCellStyle(styleCurrencyFormat);

                    
                    row.createCell(3).setCellValue(Double.parseDouble(order.getAvg().toString()));
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(3).setCellStyle(styleCurrencyFormat);
                    //row.getCell(3).setCellStyle(cellStyleAlignRight);
                    
                    
                    row.createCell(4).setCellValue(Double.parseDouble(order.getMax().toString()));
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(4).setCellStyle(styleCurrencyFormat);
                    //row.getCell(4).setCellStyle(cellStyleAlignRight);
                    
                    row.createCell(5).setCellValue(Double.parseDouble(order.getMin().toString()));
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(5).setCellStyle(styleCurrencyFormat);
                    //row.getCell(5).setCellStyle(cellStyleAlignRight);
                    rowIdx++;

                }
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "OrderByShopper");
        }

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelYearOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle cellStyleBoldAlignRight = wb.createCellStyle();
            cellStyleBoldAlignRight.setFont(font);
            cellStyleBoldAlignRight.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFDataFormat df = wb.createDataFormat();
            HSSFCellStyle styleCurrency3DecimalFormat = null;
            styleCurrency3DecimalFormat = wb.createCellStyle();
            styleCurrency3DecimalFormat.setDataFormat(df.getFormat("$#,##0.000"));
            
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));


            HttpSession session = request.getSession();

            int year = Constants.I_YEAR();
            if (request.getParameter(Constants.ORDER_YEAR_PARAM) != null)
            {
                year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
            }
            OrderServices service = new OrderServices();
            Map<Integer, OrderDate> mapYear = service.mapYear(year);

            Double finalTotal = 0.00;
            Double finalAvg = 0.00;
            Double finalDiscAvg = 0.00;
            BigDecimal finalMax = BigDecimal.valueOf(0);
            BigDecimal finalMin = BigDecimal.valueOf(0);
            BigDecimal finalDiscMax = BigDecimal.valueOf(0);
            BigDecimal finalDiscMin = BigDecimal.valueOf(0);

            //            String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);
            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("New Order By Year: " + year);
            row0.getCell(0).setCellStyle(cellStyleBold);
            Row row2 = sheet.createRow((short) 3);
            row2.createCell(0).setCellValue("");
            row2.createCell(1).setCellValue("");
            row2.createCell(2).setCellValue("Sale Price");
            row2.getCell(2).setCellStyle(cellStyleBold);
            row2.createCell(6).setCellValue("Discounts");
            row2.getCell(6).setCellStyle(cellStyleBold);
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 5));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 8));

            Row row3 = sheet.createRow((short) 4);
            row3.createCell(0).setCellValue("Month");
            row3.getCell(0).setCellStyle(cellStyleBold);
            row3.createCell(1).setCellValue("Number Orders");
            row3.getCell(1).setCellStyle(cellStyleBold);
            
            row3.createCell(2).setCellValue("Total $");
            row3.getCell(2).setCellStyle(cellStyleBold);
            row3.createCell(3).setCellValue("Avg $");
            row3.getCell(3).setCellStyle(cellStyleBold);
            row3.createCell(4).setCellValue("Max $");
            row3.getCell(4).setCellStyle(cellStyleBold);
            row3.createCell(5).setCellValue("Min $");
            row3.getCell(5).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue("Avg %");
            row3.getCell(6).setCellStyle(cellStyleBold);
            row3.createCell(7).setCellValue("Max %");
            row3.getCell(7).setCellStyle(cellStyleBold);
            row3.createCell(8).setCellValue("Min %");
            row3.getCell(8).setCellStyle(cellStyleBold);

            int rowIdx = 5;
            int count = 0;
            for (Iterator<OrderDate> iterator = mapYear.values().iterator(); iterator.hasNext();)
            {
                OrderDate orderDate = iterator.next();
                finalTotal += orderDate.getTotalSum().doubleValue();
                if (orderDate.getTotalMax().compareTo(finalMax) > 0)
                    finalMax = orderDate.getTotalMax();
                finalMin = orderDate.getTotalMin();
                if (orderDate.getDiscMax().compareTo(finalDiscMax) > 0)
                    finalDiscMax = orderDate.getDiscMax();
                finalDiscMin = orderDate.getDiscMin();
                finalAvg += orderDate.getTotalAvg().doubleValue();
                finalDiscAvg += orderDate.getDiscAvg().doubleValue();

                Row row = sheet.createRow((short) rowIdx);
                row.createCell(0).setCellValue(orderDate.getMonth());
                
                row.createCell(1).setCellValue(orderDate.getNumOrder());
                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                
                row.createCell(2).setCellValue(Double.parseDouble(orderDate.getTotalSum().toString()));
                row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(2).setCellStyle(styleCurrencyFormat);
                //row.getCell(2).setCellStyle(cellStyleAlignRight);
                
                row.createCell(3).setCellValue(Double.parseDouble(orderDate.getTotalAvg().toString()));
                row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(3).setCellStyle(styleCurrencyFormat);
                //row.getCell(3).setCellStyle(cellStyleAlignRight);
                
                row.createCell(4).setCellValue(Double.parseDouble(orderDate.getTotalMax().toString()));
                row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(4).setCellStyle(styleCurrencyFormat);
                //row.getCell(4).setCellStyle(cellStyleAlignRight);
                
                row.createCell(5).setCellValue(Double.parseDouble(orderDate.getTotalMin().toString()));
                row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(5).setCellStyle(styleCurrencyFormat);
                //row.getCell(5).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(6).setCellValue(orderDate.getDiscAvg() + "%");
                row.createCell(6).setCellValue(Double.parseDouble(orderDate.getDiscAvg().toString())/100.0);
                row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(6).setCellStyle(stylePercentFormat);
                //row.getCell(6).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(7).setCellValue(orderDate.getDiscMax() + "%");
                row.createCell(7).setCellValue(Double.parseDouble(orderDate.getDiscMax().toString())/100.0);
                row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(7).setCellStyle(stylePercentFormat);
                //row.getCell(7).setCellStyle(cellStyleAlignRight);
                
                row.createCell(8).setCellValue(Double.parseDouble(orderDate.getDiscMin().toString())/100.0);
                row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(8).setCellStyle(stylePercentFormat);
                //row.getCell(8).setCellStyle(cellStyleAlignRight);
                
                rowIdx++;
                count++;
            }
            for (Iterator<OrderDate> iterator = mapYear.values().iterator(); iterator.hasNext();)
            {
                OrderDate orderDate = iterator.next();
                BigDecimal min = orderDate.getTotalMin();
                if (finalMin.compareTo(min) > 0)
                    finalMin = min;
                BigDecimal temp = orderDate.getDiscMin();
                if (finalDiscMin.compareTo(temp) > 0)
                    finalDiscMin = temp;
            }
            Row row = sheet.createRow((short) rowIdx);
            row.createCell(0).setCellValue("");
            row.createCell(1).setCellValue("Column Totals");
            row.getCell(1).setCellStyle(cellStyleBold);
            
            row.createCell(2).setCellValue(finalTotal);
            row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(2).setCellStyle(styleCurrencyFormat);
            //row.getCell(2).setCellStyle(cellStyleAlignRight);
            
            row.createCell(3).setCellValue(finalAvg / count);
            row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(3).setCellStyle(styleCurrencyFormat);
            //row.getCell(3).setCellStyle(cellStyleAlignRight);
            
            row.createCell(4).setCellValue(finalMax.doubleValue());
            row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(4).setCellStyle(styleCurrencyFormat);
            //row.getCell(4).setCellStyle(cellStyleAlignRight);
            
            row.createCell(5).setCellValue(finalMin.doubleValue());
            row.getCell(5).setCellStyle(styleCurrencyFormat);
            row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            //row.getCell(4).setCellStyle(styleCurrencyFormat);
            
            //row.createCell(6).setCellValue(BigDecimal.valueOf(finalDiscAvg / count).setScale(2, BigDecimal.ROUND_HALF_EVEN) + "%");
            row.createCell(6).setCellValue(BigDecimal.valueOf(finalDiscAvg / count).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() /100);
            //row.createCell(6).setCellValue(finalDiscAvg/count/100.0);
            row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(6).setCellStyle(stylePercentFormat);
            //row.getCell(6).setCellStyle(cellStyleAlignRight);
            
            row.createCell(7).setCellValue(Double.parseDouble(finalDiscMax.toString())/100.0);
            row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(7).setCellStyle(stylePercentFormat);
            //row.getCell(7).setCellStyle(cellStyleAlignRight);
            
            row.createCell(8).setCellValue(Double.parseDouble(finalDiscMin.toString())/100.0);
            row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(8).setCellStyle(stylePercentFormat);
            //row.getCell(8).setCellStyle(cellStyleAlignRight);
            
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "NewOrderByYear" + year);
        }

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelMonthOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
           
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
            HttpSession session = request.getSession();

            int year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
            int month = Integer.valueOf(request.getParameter(Constants.ORDER_MONTH_PARAM));
            OrderServices service = new OrderServices();
            Map<Integer, OrderDate> mapMonth = service.mapMonth(year, month);

            Double finalTotal = 0.00;
            Double finalAvg = 0.00;
            Double finalDiscAvg = 0.00;
            BigDecimal finalMax = BigDecimal.valueOf(0);
            BigDecimal finalMin = BigDecimal.valueOf(0);
            BigDecimal finalDiscMax = BigDecimal.valueOf(0);
            BigDecimal finalDiscMin = BigDecimal.valueOf(0);

            //            String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);
            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("New Order By Month: " + month + " / " + year);
            row0.getCell(0).setCellStyle(cellStyleBold);
            Row row2 = sheet.createRow((short) 3);
            row2.createCell(0).setCellValue("");
            row2.createCell(1).setCellValue("");
            row2.createCell(2).setCellValue("Sale Price");
            row2.getCell(2).setCellStyle(cellStyleBold);
            row2.createCell(6).setCellValue("Discounts");
            row2.getCell(6).setCellStyle(cellStyleBold);
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 2, 5));
            sheet.addMergedRegion(new CellRangeAddress(3, 3, 6, 8));

            Row row3 = sheet.createRow((short) 4);
            row3.createCell(0).setCellValue("Day");
            row3.getCell(0).setCellStyle(cellStyleBold);
            row3.createCell(1).setCellValue("Number Orders");
            row3.getCell(1).setCellStyle(cellStyleBold);
            row3.createCell(2).setCellValue("Total $");
            row3.getCell(2).setCellStyle(cellStyleBold);
            row3.createCell(3).setCellValue("Avg $");
            row3.getCell(3).setCellStyle(cellStyleBold);
            row3.createCell(4).setCellValue("Max $");
            row3.getCell(4).setCellStyle(cellStyleBold);
            row3.createCell(5).setCellValue("Min $");
            row3.getCell(5).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue("Avg %");
            row3.getCell(6).setCellStyle(cellStyleBold);
            row3.createCell(7).setCellValue("Max %");
            row3.getCell(7).setCellStyle(cellStyleBold);
            row3.createCell(8).setCellValue("Min %");
            row3.getCell(8).setCellStyle(cellStyleBold);

            int rowIdx = 5;
            int count = 0;
            for (Iterator<OrderDate> iterator = mapMonth.values().iterator(); iterator.hasNext();)
            {
                OrderDate orderDate = iterator.next();
                finalTotal += orderDate.getTotalSum().doubleValue();
                if (orderDate.getTotalMax().compareTo(finalMax) > 0)
                    finalMax = orderDate.getTotalMax();
                finalMin = orderDate.getTotalMin();
                if (orderDate.getDiscMax().compareTo(finalDiscMax) > 0)
                    finalDiscMax = orderDate.getDiscMax();
                finalDiscMin = orderDate.getDiscMin();
                finalAvg += orderDate.getTotalAvg().doubleValue();
                finalDiscAvg += orderDate.getDiscAvg().doubleValue();

                Row row = sheet.createRow((short) rowIdx);
                row.createCell(0).setCellValue(orderDate.getDayNum());
                
                row.createCell(1).setCellValue(orderDate.getNumOrder());
                row.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                
                //row.createCell(2).setCellValue(Converter.getMoney(orderDate.getTotalSum()));
                row.createCell(2).setCellValue(orderDate.getTotalSum().doubleValue());
                //row.createCell(2).setCellValue(Double.parseDouble(orderDate.getTotalSum().toString()) );
                row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(2).setCellStyle(styleCurrencyFormat);                
                //row.getCell(2).setCellStyle(cellStyleAlignRight);
                
                
                //row.createCell(3).setCellValue(Converter.getMoney(orderDate.getTotalAvg()));
                row.createCell(3).setCellValue(orderDate.getTotalAvg().doubleValue());
                //row.createCell(3).setCellValue(Double.parseDouble(orderDate.getTotalAvg().toString() ) );
                row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(3).setCellStyle(styleCurrencyFormat);
                //row.getCell(3).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(4).setCellValue(Converter.getMoney(orderDate.getTotalMax()));
                row.createCell(4).setCellValue(orderDate.getTotalMax().doubleValue());
                //row.createCell(4).setCellValue(Double.parseDouble(orderDate.getTotalMax().toString()));
                row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(4).setCellStyle(styleCurrencyFormat);
                //row.getCell(4).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(5).setCellValue(Converter.getMoney(orderDate.getTotalMin()));
                row.createCell(5).setCellValue(orderDate.getTotalMin().doubleValue());
                //row.createCell(5).setCellValue(Double.parseDouble(orderDate.getTotalMin().toString()));
                row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(5).setCellStyle(styleCurrencyFormat);
                //row.getCell(5).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(6).setCellValue(orderDate.getDiscAvg() + "%");
                row.createCell(6).setCellValue(orderDate.getDiscAvg().doubleValue()/100.0);
                //row.createCell(6).setCellValue(Double.parseDouble(orderDate.getDiscAvg().toString())/100.0 );
                row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(6).setCellStyle(stylePercentFormat);
                //row.getCell(6).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(7).setCellValue(orderDate.getDiscMax() + "%");
                row.createCell(7).setCellValue(orderDate.getDiscMax().doubleValue()/100.0);
                //row.createCell(7).setCellValue(Double.parseDouble(orderDate.getDiscMax().toString())/100.0);
                row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(7).setCellStyle(stylePercentFormat);
                //row.getCell(7).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(8).setCellValue(orderDate.getDiscMin() + "%");
                row.createCell(8).setCellValue(orderDate.getDiscMin().doubleValue()/100.0);
                //row.createCell(8).setCellValue(Double.parseDouble(orderDate.getDiscMin().toString())/100.0 );
                row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(8).setCellStyle(stylePercentFormat);
                //row.getCell(8).setCellStyle(cellStyleAlignRight);
                
                rowIdx++;
                count++;
            }
            for (Iterator<OrderDate> iterator = mapMonth.values().iterator(); iterator.hasNext();)
            {
                OrderDate orderDate = iterator.next();
                BigDecimal min = orderDate.getTotalMin();
                if (finalMin.compareTo(min) > 0)
                    finalMin = min;
                BigDecimal temp = orderDate.getDiscMin();
                if (finalDiscMin.compareTo(temp) > 0)
                    finalDiscMin = temp;
            }
            Row row = sheet.createRow((short) rowIdx);
            row.createCell(0).setCellValue("");
            row.createCell(1).setCellValue("Column Totals");
            row.getCell(1).setCellStyle(cellStyleBold);
            
            //row.createCell(2).setCellValue(Converter.getMoney(BigDecimal.valueOf(finalTotal)));
            row.createCell(2).setCellValue(finalTotal);
            row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(2).setCellStyle(styleCurrencyFormat);
            //row.getCell(2).setCellStyle(cellStyleAlignRight);
            
            //row.createCell(3).setCellValue("$" + BigDecimal.valueOf(finalAvg / count).setScale(2, BigDecimal.ROUND_HALF_EVEN));
            row.createCell(3).setCellValue(finalAvg/count);
            row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(3).setCellStyle(styleCurrencyFormat);
            //row.getCell(3).setCellStyle(cellStyleAlignRight);
            
            //row.createCell(4).setCellValue(Converter.getMoney(finalMax));
            row.createCell(4).setCellValue(finalMax.doubleValue());
            row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(4).setCellStyle(styleCurrencyFormat);
            //row.getCell(4).setCellStyle(cellStyleAlignRight);
            
            row.createCell(5).setCellValue(finalMin.doubleValue());
            row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(5).setCellStyle(styleCurrencyFormat);
            //row.getCell(5).setCellStyle(cellStyleAlignRight);
            
            row.createCell(6).setCellValue(BigDecimal.valueOf(finalDiscAvg / count).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue()/100.0);
            //row.createCell(6).setCellValue(finalDiscAvg/count/100.0);
            row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(6).setCellStyle(stylePercentFormat);
            
            
            row.createCell(7).setCellValue(Double.parseDouble(finalDiscMax.toString())/100.0 );
            row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(7).setCellStyle(stylePercentFormat);
//            row.getCell(7).setCellStyle(cellStyleAlignRight);
            
            
            //row.createCell(8).setCellValue(finalDiscMin + "%");
            row.createCell(8).setCellValue(Double.parseDouble(finalDiscMin.toString())/100.0);
            row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            row.getCell(8).setCellStyle(stylePercentFormat);
            //row.getCell(8).setCellStyle(cellStyleAlignRight);

            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "NewOrderByMonth" + month + "_" + year);
        }

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelDayOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {
        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0%"));
            

            int year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
            int month = Integer.valueOf(request.getParameter(Constants.ORDER_MONTH_PARAM));
            int day = Integer.valueOf(request.getParameter(Constants.ORDER_DAY_PARAM));

            OrderServices service = new OrderServices();
            service.mapOrderDay(year, month, day, 1);
            int totalDay = service.getTotalRecord();

            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("New Order By Day: " + month + " / " + day + " / " + year);
            row0.getCell(0).setCellStyle(cellStyleBold);
            Row row3 = sheet.createRow((short) 3);
            row3.createCell(0).setCellValue("Order");
            row3.getCell(0).setCellStyle(cellStyleBold);
            row3.createCell(1).setCellValue("Time");
            row3.getCell(1).setCellStyle(cellStyleBold);
            row3.createCell(2).setCellValue("Ship To Name");
            row3.getCell(2).setCellStyle(cellStyleBold);
            row3.createCell(3).setCellValue("Sale Type");
            row3.getCell(3).setCellStyle(cellStyleBold);
            row3.createCell(4).setCellValue("Items");
            row3.getCell(4).setCellStyle(cellStyleBold);
            row3.createCell(5).setCellValue("Discount");
            row3.getCell(5).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue("Total");
            row3.getCell(6).setCellStyle(cellStyleBold);

            List<Order> mapDay = new ArrayList<Order>();
            int numOfPage = (totalDay % orderRecord > 0) ? (totalDay / orderRecord + 1) : (totalDay / orderRecord);
            for (int i = 1; i <= numOfPage; i++)
            {
                mapDay.addAll(service.mapOrderDay(year, month, day, i));
            }
            if (mapDay.size() > 0)
            {
                int rowIdx = 4;
                for (Order order : mapDay)
                {
                    Row row = sheet.createRow((short) rowIdx);
                    row.createCell(0).setCellValue(order.getOrderId());
                    
                    row.createCell(1).setCellValue(order.getTime());
                    
                    row.createCell(2).setCellValue(order.getShip_to_name());
                    
                    row.createCell(3).setCellValue(order.getOrderType());
                    
                    row.createCell(4).setCellValue(order.getItem());
                    
                    //row.createCell(5).setCellValue(order.getDiscount() + "%");
                    Integer dis=(Integer)order.getDiscount();
                    row.createCell(5).setCellValue(dis.doubleValue()/100.0);
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(5).setCellStyle(stylePercentFormat);
                    //row.getCell(5).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(6).setCellValue(Converter.getMoney(order.getTotal_total()));
                    row.createCell(6).setCellValue(order.getTotal_total().doubleValue());
                    row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(6).setCellStyle(styleCurrencyFormat);
                    //row.getCell(6).setCellStyle(cellStyleAlignRight);
                    
                    rowIdx++;
                }
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "NewOrderByDay" + day + "_" + month + "_" + year);
        }
        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelAgentOrders(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));

            HttpSession session = request.getSession();

            String date1 = (String) request.getParameter(Constants.DATE1);
            String date2 = (String) request.getParameter(Constants.DATE2);
            OrderServices service = new OrderServices();

            //            String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);
            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("Orders by Agents");
            row0.getCell(0).setCellStyle(cellStyleBold);
            Row row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("From date");
            row2.getCell(0).setCellStyle(cellStyleBold);
            row2.createCell(1).setCellValue(date1);
            row2.createCell(2).setCellValue("To date");
            row2.getCell(2).setCellStyle(cellStyleBold);
            row2.createCell(3).setCellValue(date2.substring(0, 10));

            Row row3 = sheet.createRow((short) 4);
            row3.createCell(0).setCellValue("");
            row3.createCell(1).setCellValue("");
            row3.createCell(2).setCellValue("Sale Price");
            row3.getCell(2).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue("Discounts");
            row3.getCell(6).setCellStyle(cellStyleBold);
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 2, 5));
            sheet.addMergedRegion(new CellRangeAddress(4, 4, 6, 8));

            Row row4 = sheet.createRow((short) 5);
            row4.createCell(0).setCellValue("Agent");
            row4.getCell(0).setCellStyle(cellStyleBold);
            row4.createCell(1).setCellValue("Number Unit");
            row4.getCell(1).setCellStyle(cellStyleBold);
            row4.createCell(2).setCellValue("Order Sub Total $");
            row4.getCell(2).setCellStyle(cellStyleBold);
            row4.createCell(3).setCellValue("Unit ASP $");
            row4.getCell(3).setCellStyle(cellStyleBold);
            row4.createCell(4).setCellValue("Order Max $");
            row4.getCell(4).setCellStyle(cellStyleBold);
            row4.createCell(5).setCellValue("Order Min $");
            row4.getCell(5).setCellStyle(cellStyleBold);
            row4.createCell(6).setCellValue("Avg %");
            row4.getCell(6).setCellStyle(cellStyleBold);
            row4.createCell(7).setCellValue("Max %");
            row4.getCell(7).setCellStyle(cellStyleBold);
            row4.createCell(8).setCellValue("Min %");
            row4.getCell(8).setCellStyle(cellStyleBold);

            int rowIdx = 6;
            //  int totalRecord = service.countAgents(date1, date2);
            List<OrderAgent> listAgents = service.getOrderByAgent(date1, date2);

            if (null != listAgents && !listAgents.isEmpty())
            {
                Double finalTotal = 0.00;
                Double finalAsp = 0.00;
                Double finalDiscAvg = 0.00;
                BigDecimal finalMax = BigDecimal.valueOf(0);
                BigDecimal finalMin = BigDecimal.valueOf(0);
                BigDecimal finalDiscMax = BigDecimal.valueOf(0);
                BigDecimal finalDiscMin = BigDecimal.valueOf(0);
                for (OrderAgent agent : listAgents)
                {
                    finalTotal += agent.getTotalSum().doubleValue();
                    if (agent.getTotalMax().compareTo(finalMax) > 0)
                        finalMax = agent.getTotalMax();
                    finalMin = agent.getTotalMin();
                    if (agent.getDiscMax().compareTo(finalDiscMax) > 0)
                        finalDiscMax = agent.getDiscMax();
                    finalDiscMin = agent.getDiscMin();
                    finalAsp += agent.getUnitAsp().doubleValue();
                    finalDiscAvg += agent.getDiscAvg().doubleValue();

                    Row row = sheet.createRow((short) rowIdx);
                    row.createCell(0).setCellValue(agent.getAgent());
                    
                    row.createCell(1).setCellValue(agent.getNumUnit());
                    
                    //row.createCell(2).setCellValue(Converter.getMoney(agent.getTotalSum()));
                    row.createCell(2).setCellValue(agent.getTotalSum().doubleValue());
                    row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(2).setCellStyle(styleCurrencyFormat);
                    //row.getCell(2).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(3).setCellValue(Converter.getMoney(agent.getUnitAsp()));
                    row.createCell(3).setCellValue(agent.getUnitAsp().doubleValue());
                    row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(3).setCellStyle(styleCurrencyFormat);
                    //row.getCell(3).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(4).setCellValue(Converter.getMoney(agent.getTotalMax()));
                    row.createCell(4).setCellValue(agent.getTotalMax().doubleValue());
                    row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(4).setCellStyle(styleCurrencyFormat);
                    //row.getCell(4).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(5).setCellValue(Converter.getMoney(agent.getTotalMin()));
                    row.createCell(5).setCellValue(agent.getTotalMin().doubleValue());
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(5).setCellStyle(styleCurrencyFormat);
                    //row.getCell(5).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(6).setCellValue(agent.getDiscAvg() + "%");
                    row.createCell(6).setCellValue(agent.getDiscAvg().doubleValue()/100.0);
                    row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(6).setCellStyle(stylePercentFormat);
                    //row.getCell(6).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(7).setCellValue(agent.getDiscMax() + "%");
                    row.createCell(7).setCellValue(agent.getDiscMax().doubleValue()/100.0);
                    row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(7).setCellStyle(stylePercentFormat);
                    //row.getCell(7).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(8).setCellValue(agent.getDiscMin() + "%");
                    row.createCell(8).setCellValue(agent.getDiscMin().doubleValue()/100.0);
                    row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(8).setCellStyle(stylePercentFormat);
                    //row.getCell(8).setCellStyle(cellStyleAlignRight);
                    
                    rowIdx++;

                }
                for (OrderAgent agent : listAgents)
                {
                    BigDecimal min = agent.getTotalMin();
                    if (finalMin.compareTo(min) > 0)
                        finalMin = min;
                    BigDecimal temp = agent.getDiscMin();
                    if (finalDiscMin.compareTo(temp) > 0)
                        finalDiscMin = temp;
                }
                Row row = sheet.createRow((short) rowIdx);
                row.createCell(0).setCellValue("");
                
                row.createCell(1).setCellValue("Column Totals");
                row.getCell(1).setCellStyle(cellStyleBold);
                
                //row.createCell(2).setCellValue(Converter.getMoney(BigDecimal.valueOf(finalTotal)));
                row.createCell(2).setCellValue(finalTotal);
                row.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(2).setCellStyle(styleCurrencyFormat);
                //row.getCell(2).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(3).setCellValue("$" + BigDecimal.valueOf(finalAsp / listAgents.size()).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                row.createCell(3).setCellValue(finalAsp/listAgents.size());
                row.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(3).setCellStyle(styleCurrencyFormat);
                //row.getCell(3).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(4).setCellValue(Converter.getMoney(finalMax));
                row.createCell(4).setCellValue(finalMax.doubleValue());
                row.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(4).setCellStyle(styleCurrencyFormat);
                //row.getCell(4).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(5).setCellValue(Converter.getMoney(finalMin));
                row.createCell(5).setCellValue(finalMin.doubleValue());
                row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(5).setCellStyle(styleCurrencyFormat);
                //row.getCell(5).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(6).setCellValue(BigDecimal.valueOf(finalDiscAvg / listAgents.size()).setScale(2, BigDecimal.ROUND_HALF_EVEN) + "%");
                //row.createCell(6).setCellValue(finalDiscAvg/listAgents.size()/100.0);
                row.createCell(6).setCellValue(BigDecimal.valueOf(finalDiscAvg / listAgents.size()).setScale(2, BigDecimal.ROUND_HALF_EVEN).doubleValue()/100.0);
                row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(6).setCellStyle(stylePercentFormat);
                //row.getCell(6).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(7).setCellValue(finalDiscMax + "%");
                row.createCell(7).setCellValue(finalDiscMax.doubleValue()/100.0);
                row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(7).setCellStyle(stylePercentFormat);
                //row.getCell(7).setCellStyle(cellStyleAlignRight);
                
                //row.createCell(8).setCellValue(finalDiscMin + "%");
                row.createCell(8).setCellValue(finalDiscMin.doubleValue()/100.0);
                row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                row.getCell(8).setCellStyle(stylePercentFormat);
                //row.getCell(8).setCellStyle(cellStyleAlignRight);
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "OrdersByAgents" + date1 + "_" + date2.substring(0, 10));
        }

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelAgentDetail(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {
        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFCellStyle stylePercentFormat = null;
            stylePercentFormat = wb.createCellStyle();
            stylePercentFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00%"));
            
            HttpSession session = request.getSession();

            OrderServices service = new OrderServices();
            String date1 = (String) request.getParameter(Constants.DATE1);
            String date2 = (String) request.getParameter(Constants.DATE2);
            String agentId = (String) request.getParameter(Constants.ORDER_AGENT_ID);
            String agentName = (String) request.getParameter(Constants.ORDER_AGENT_NAME);
            System.out.println("date : "+date1 +","+date2);
            service.getAgentDetails(date1, date2, agentId, 1);
            int totalRecord = service.getTotalRecord();
            List<OrderAgentDetail> listOrders = new ArrayList<OrderAgentDetail>();

            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("Orders By Agent: " + agentName + " From " + date1 + " To " + date2.substring(0, 10));
            row0.getCell(0).setCellStyle(cellStyleBold);
            Row row3 = sheet.createRow((short) 3);
            row3.createCell(0).setCellValue("Order ID");
            row3.getCell(0).setCellStyle(cellStyleBold);
            row3.createCell(1).setCellValue("Date");
            row3.getCell(1).setCellStyle(cellStyleBold);
            row3.createCell(2).setCellValue("Ship To Name");
            row3.getCell(2).setCellStyle(cellStyleBold);
            row3.createCell(3).setCellValue("Ship To Company");
            row3.getCell(3).setCellStyle(cellStyleBold);
            /*
            row3.createCell(4).setCellValue("Sale Type");
            row3.getCell(4).setCellStyle(cellStyleBold);
            row3.createCell(5).setCellValue("Items Sold");
            row3.getCell(5).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue("Discount %");
            row3.getCell(6).setCellStyle(cellStyleBold);
            row3.createCell(7).setCellValue("Discount Amt");
            row3.getCell(7).setCellStyle(cellStyleBold);
            row3.createCell(8).setCellValue("Avg $/Mhz");
            row3.getCell(8).setCellStyle(cellStyleBold);
            row3.createCell(9).setCellValue("Avg mhz");
            row3.getCell(9).setCellStyle(cellStyleBold);
            row3.createCell(10).setCellValue("Order Total");
            row3.getCell(10).setCellStyle(cellStyleBold);
			*/
            row3.createCell(4).setCellValue("Items Sold");
            row3.getCell(4).setCellStyle(cellStyleBold);
            row3.createCell(5).setCellValue("Discount %");
            row3.getCell(5).setCellStyle(cellStyleBold);
            row3.createCell(6).setCellValue("Discount Amt");
            row3.getCell(6).setCellStyle(cellStyleBold);
            row3.createCell(7).setCellValue("Avg $/Mhz");
            row3.getCell(7).setCellStyle(cellStyleBold);
            row3.createCell(8).setCellValue("Avg mhz");
            row3.getCell(8).setCellStyle(cellStyleBold);
            row3.createCell(9).setCellValue("Order Total");
            row3.getCell(9).setCellStyle(cellStyleBold);

            int numOfPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
            for (int i = 1; i <= numOfPage; i++)
            {
                listOrders.addAll(service.getAgentDetails(date1, date2, agentId, i));
            }
            if (listOrders.size() > 0)
            {
                int rowIdx = 4;
                for (OrderAgentDetail detail : listOrders)
                {
                    String avg_mhz = (detail.getAvgMhz().getTotal_price() != BigDecimal.valueOf(0)) ? Converter.getMoney(detail.getAvgMhz().getTotal_price()) : "N/A";
                    String average_mhz = (detail.getAvgMhz().getUnit_mhz() > 0) ? (String.valueOf(Math.round(detail.getAvgMhz().getSpeed_total() / detail.getAvgMhz().getUnit_mhz()))) : "N/A";
                    Row row = sheet.createRow((short) rowIdx);
                    row.createCell(0).setCellValue(detail.getOrderId());
                    
                    row.createCell(1).setCellValue(detail.getDayOrder());
                    row.createCell(2).setCellValue(detail.getShip_to_name());
                    row.createCell(3).setCellValue(detail.getShip_to_company());
                    //row.createCell(4).setCellValue(detail.getAgentName());
                    row.createCell(4).setCellValue(detail.getItem());
                    
                    //row.createCell(5).setCellValue(detail.getDiscount() + "%");
                    row.createCell(5).setCellValue(detail.getDiscount()/100.0);
                    row.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(5).setCellStyle(stylePercentFormat);
                    //row.getCell(5).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(6).setCellValue(Converter.getMoney(detail.getTotalDisc()));
                    row.createCell(6).setCellValue(detail.getTotalDisc().doubleValue());
                    row.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(6).setCellStyle(styleCurrencyFormat);
                    //row.getCell(6).setCellStyle(cellStyleAlignRight);
                    
                    //row.createCell(7).setCellValue(avg_mhz);
	                // row.createCell(7).setCellValue(Double.parseDouble(avg_mhz.substring(1)) );
                    row.createCell(7).setCellValue(Double.parseDouble(avg_mhz.replace(",", "").substring(1)) );
	                row.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	                row.getCell(7).setCellStyle(styleCurrencyFormat);
	                    //row.getCell(7).setCellStyle(cellStyleAlignRight);
	                    //System.out.println("avg_mhz : "+avg_mhz.replace(",", "").substring(1));
                    
                    
                    if(average_mhz.equalsIgnoreCase("N/A")){
                    	row.createCell(8).setCellValue(average_mhz);
                        row.getCell(8).setCellType(HSSFCell.CELL_TYPE_STRING);
                        row.getCell(8).setCellStyle(cellStyleAlignRight);
                    }else{
	                    row.createCell(8).setCellValue(Double.parseDouble(average_mhz));
	                    row.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
	                    //row.getCell(8).setCellStyle(cellStyleAlignRight);
                    }
                    
                    //row.createCell(9).setCellValue(Converter.getMoney(detail.getTotal_total()));
                    row.createCell(9).setCellValue(detail.getTotal_total().doubleValue());
                    row.getCell(9).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    row.getCell(9).setCellStyle(styleCurrencyFormat);
                    //row.getCell(9).setCellStyle(cellStyleAlignRight);
                    
                    rowIdx++;
                }
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "OrdersBy" + agentName + "_" + date1 + "_" + date2.substring(0, 10));
        }
        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward excelSumaryReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {
        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);
            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            HSSFCellStyle styleCurrency0DecimalFormat  = null;
            styleCurrency0DecimalFormat = wb.createCellStyle();
            styleCurrency0DecimalFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0_);($#,##0)"));
            
            HSSFDataFormat df = wb.createDataFormat();
            HSSFCellStyle styleCurrency3DecimalFormat = null;
            styleCurrency3DecimalFormat = wb.createCellStyle();
            styleCurrency3DecimalFormat.setDataFormat(df.getFormat("$#,##0.000"));

            HttpSession session = request.getSession();

            //            String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);
            OrderServices service = new OrderServices();
            String catid = request.getParameter(Constants.CATID);
            String ostype = request.getParameter(Constants.OSTYPE);
            String cosmetic = request.getParameter(Constants.COSMETIC);
            String brandtype = request.getParameter(Constants.BRANDTYPE);
            String model = request.getParameter(Constants.MODEL);
            String date1 = request.getParameter(Constants.DATE1);
            String date2 = request.getParameter(Constants.DATE2) + " 23:59:59";
            String proctype = request.getParameter(Constants.PROCTYPE);
            OrderServices services = new OrderServices();
            List<OrderSummary> list = services.getOrderSummray(catid, ostype, cosmetic, brandtype, model, date1, date2, proctype);
            List<Summary> listAgent = services.showAgentReport(catid, ostype , cosmetic, brandtype, model, date1, date2, proctype);

            //            Row row1 = sheet.createRow((short) 0);
            //            row1.createCell(0).setCellValue("Current User: " + shopperName);
            Row row0 = sheet.createRow((short) 1);
            row0.createCell(0).setCellValue("Orders Summary Report From " + date1 + " To " + date2.substring(0, 10));
            row0.getCell(0).setCellStyle(cellStyleBold);
            String type = "All";
            if (ostype.equals("win"))
                type = "Yes";
            else if (ostype.equals("no"))
                type = "No";
            Row row2 = sheet.createRow((short) 2);
            row2.createCell(0).setCellValue("Operating System: " + type);
            
            Row rowCosmetic = sheet.createRow((short) 3);
            row2.createCell(3).setCellValue("Cosmetic Grade: " + cosmetic);
            
            List<String> category = new ArrayList<String>();
            if (catid.contains("11946"))
                category.add("Notebooks");
            if (catid.contains("11949"))
                category.add("Desktops");
            if (catid.contains("11947"))
                category.add("Workstations");
            if (catid.contains("11950"))
                category.add("Servers");
            if (catid.contains("11955"))
                category.add("Monitors");
            if (catid.contains("11958"))
                category.add("Docking Stations");
            if (catid.contains("11956"))
                category.add("Projectors");
            if (catid.contains("11957"))
                category.add("Printers");
            if (catid.contains("11959"))
                category.add("Memories");
            if (catid.contains("11960"))
                category.add("Batteries");
            String[] brand = brandtype.split(",");
            for (int i = 0; i < brand.length; i++)
            {
                if (brand[i].contains("'"))
                    brand[i] = brand[i].trim().substring(1, brand[i].trim().length() - 1);
            }
            String[] process = proctype.split(",");
            for (int i = 0; i < process.length; i++)
            {
                if (process[i].contains("'"))
                    process[i] = process[i].trim().substring(1, process[i].trim().length() - 1);
            }
            String[] modelL = model.split(",");
            for (int i = 0; i < modelL.length; i++)
            {
                if (modelL[i].contains("'"))
                    modelL[i] = modelL[i].trim().substring(1, modelL[i].trim().length() - 1);
            }
            int rowMax = 0;
            rowMax = category.size();
            rowMax = (rowMax > brand.length) ? rowMax : brand.length;
            rowMax = (rowMax > process.length) ? rowMax : process.length;
            rowMax = (rowMax > modelL.length) ? rowMax : modelL.length;

            Row row3 = sheet.createRow((short) 3);
            row3.createCell(0).setCellValue("Category");
            row3.getCell(0).setCellStyle(cellStyleBold);
            row3.createCell(1).setCellValue("Brand Type");
            row3.getCell(1).setCellStyle(cellStyleBold);
            row3.createCell(2).setCellValue("Processor Type");
            row3.getCell(2).setCellStyle(cellStyleBold);
            row3.createCell(3).setCellValue("Model Number");
            row3.getCell(3).setCellStyle(cellStyleBold);
            int rowCon = 4;
            for (int i = 0; i < rowMax; i++)
            {
                Row row = sheet.createRow((short) rowCon);
                if (i < category.size())
                    row.createCell(0).setCellValue(category.get(i));
                if (i < brand.length)
                    row.createCell(1).setCellValue(brand[i]);
                if (i < process.length)
                    row.createCell(2).setCellValue(process[i]);
                if (i < modelL.length)
                    row.createCell(3).setCellValue(modelL[i]);
                rowCon++;
            }
            int rowIdx = 0;
            if (list.size() > 0)
            {
                Row row4 = sheet.createRow((short) rowCon);
                row4.createCell(0).setCellValue("");
                row4.createCell(1).setCellValue("Agent");
                row4.getCell(1).setCellStyle(cellStyleBold);
                row4.createCell(2).setCellValue("Store");
                row4.getCell(2).setCellStyle(cellStyleBold);
                row4.createCell(3).setCellValue("Dell Auction");
                row4.getCell(3).setCellStyle(cellStyleBold);
                row4.createCell(4).setCellValue("Ebay");
                row4.getCell(4).setCellStyle(cellStyleBold);
                row4.createCell(5).setCellValue("Agent Store");
                row4.getCell(5).setCellStyle(cellStyleBold);
                row4.createCell(6).setCellValue("Retail");
                row4.getCell(6).setCellStyle(cellStyleBold);
                rowIdx = rowCon + 1;

                Summary summary = new Summary();
                summary.setSales(BigDecimal.valueOf(0));
                summary.setUnits(0);
                summary.setMhz(0);
                summary.setAsp(BigDecimal.valueOf(0));
                summary.setSalesMhz(BigDecimal.valueOf(0));
                summary.setOrders(0);

                Row row5 = sheet.createRow((short) rowIdx);
                row5.createCell(0).setCellValue("Revenue");
                row5.getCell(0).setCellStyle(cellStyleBold);
                Row row6 = sheet.createRow((short) rowIdx + 1);
                row6.createCell(0).setCellValue("Units");
                row6.getCell(0).setCellStyle(cellStyleBold);
                Row row7 = sheet.createRow((short) rowIdx + 2);
                row7.createCell(0).setCellValue("ASP");
                row7.getCell(0).setCellStyle(cellStyleBold);
                Row row8 = sheet.createRow((short) rowIdx + 3);
                row8.createCell(0).setCellValue("$/Mhz");
                row8.getCell(0).setCellStyle(cellStyleBold);
                Row row9 = sheet.createRow((short) rowIdx + 4);
                row9.createCell(0).setCellValue("Orders");
                row9.getCell(0).setCellStyle(cellStyleBold);
                for (OrderSummary orderSummary : list)
                {
                    if (orderSummary.getAgent() == null)
                    {
                        orderSummary.setAgent(summary);
                    }
                    if (orderSummary.getStore() == null)
                    {
                        orderSummary.setStore(summary);
                    }
                    if (orderSummary.getAuction() == null)
                    {
                        orderSummary.setAuction(summary);
                    }
                    if (orderSummary.getEbay() == null)
                    {
                        orderSummary.setEbay(summary);
                    }
                    if (orderSummary.getCustomer() == null)
                    {
                        orderSummary.setCustomer(summary);
                    }

                    BigDecimal sales =
                        orderSummary.getAgent().getSales().add(orderSummary.getStore().getSales()).add(orderSummary.getAuction().getSales()).add(orderSummary.getEbay().getSales()).add(
                            orderSummary.getCustomer().getSales());
                    int unit =
                        orderSummary.getAgent().getUnits() + orderSummary.getStore().getUnits() + orderSummary.getAuction().getUnits() + orderSummary.getEbay().getUnits()
                            + orderSummary.getCustomer().getUnits();
                    int mhz =
                        orderSummary.getAgent().getMhz() + orderSummary.getStore().getMhz() + orderSummary.getAuction().getMhz() + orderSummary.getEbay().getMhz()
                            + orderSummary.getCustomer().getMhz();
                    BigDecimal asp = BigDecimal.valueOf(0);
                    BigDecimal salesMhz = BigDecimal.valueOf(0);
                    if (unit > 0 && sales != null)
                    {

                        asp = unit == 0 ? new BigDecimal(0) : sales.divide(BigDecimal.valueOf(unit), 0, RoundingMode.HALF_EVEN);
                    }
                    if (mhz > 0 && sales != null)
                    {
                        salesMhz = mhz == 0 ? new BigDecimal(0) : sales.divide(BigDecimal.valueOf(mhz), 3, RoundingMode.HALF_EVEN);
                    }
                    Cell cel1Row5 = row5.createCell((short) 1);
                    cel1Row5.setCellValue(orderSummary.getAgent().getSales().doubleValue());
                    cel1Row5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel1Row5.setCellStyle(styleCurrencyFormat);
                    //cel1Row5.setCellStyle(cellStyleAlignRight);

                    Cell cel2Row5 = row5.createCell((short) 2);
                    cel2Row5.setCellValue(orderSummary.getStore().getSales().doubleValue());
                    cel2Row5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel2Row5.setCellStyle(styleCurrencyFormat);
                    //cel2Row5.setCellStyle(cellStyleAlignRight);

                    Cell cel3Row5 = row5.createCell((short) 3);
                    cel3Row5.setCellValue(orderSummary.getAuction().getSales().doubleValue());
                    cel3Row5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel3Row5.setCellStyle(styleCurrencyFormat);
                    //cel3Row5.setCellStyle(cellStyleAlignRight);

                    Cell cel4Row5 = row5.createCell((short) 4);
                    cel4Row5.setCellValue(orderSummary.getEbay().getSales().doubleValue());
                    cel4Row5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel4Row5.setCellStyle(styleCurrencyFormat);
                    //cel4Row5.setCellStyle(cellStyleAlignRight);

                    Cell cel5Row5 = row5.createCell((short) 5);
                    //cel5Row5.setCellValue(Converter.getMoney(orderSummary.getCustomer().getSales()));
                    cel5Row5.setCellValue(orderSummary.getCustomer().getSales().doubleValue());
                    cel5Row5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel5Row5.setCellStyle(styleCurrencyFormat);
                    //cel5Row5.setCellStyle(cellStyleAlignRight);

                    Cell cel6Row5 = row5.createCell((short) 6);
                    cel6Row5.setCellValue(sales.doubleValue());
                    cel6Row5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel6Row5.setCellStyle(styleCurrencyFormat);
                    //cel6Row5.setCellStyle(cellStyleAlignRight);

                    Cell cel1Row6 = row6.createCell(1);
                    cel1Row6.setCellValue(orderSummary.getAgent().getUnits());
                    cel1Row6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel1Row6.setCellStyle(cellStyleAlignRight);

                    Cell cel2Row6 = row6.createCell(2);
                    cel2Row6.setCellValue(orderSummary.getStore().getUnits());
                    cel2Row6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel2Row6.setCellStyle(cellStyleAlignRight);

                    Cell cel3Row6 = row6.createCell(3);
                    cel3Row6.setCellValue(orderSummary.getAuction().getUnits());
                    cel3Row6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel3Row6.setCellStyle(cellStyleAlignRight);

                    Cell cel4Row6 = row6.createCell(4);
                    cel4Row6.setCellValue(orderSummary.getEbay().getUnits());
                    cel4Row6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel4Row6.setCellStyle(cellStyleAlignRight);

                    Cell cel5Row6 = row6.createCell(5);
                    cel5Row6.setCellValue(orderSummary.getCustomer().getUnits());
                    cel5Row6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel5Row6.setCellStyle(cellStyleAlignRight);

                    Cell cel6Row6 = row6.createCell(6);
                    cel6Row6.setCellValue(unit);
                    cel6Row6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //cel6Row6.setCellStyle(cellStyleAlignRight);

                    Cell cel1Row7 = row7.createCell(1);
                    //cel1Row7.setCellValue(Converter.getMoneyNonePoint(orderSummary.getAgent().getAsp()));
                    cel1Row7.setCellValue(orderSummary.getAgent().getAsp().doubleValue());
                    cel1Row7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel1Row7.setCellStyle(styleCurrency0DecimalFormat);
                    //cel1Row7.setCellStyle(cellStyleAlignRight);

                    Cell cel2Row7 = row7.createCell(2);
                    cel2Row7.setCellValue(orderSummary.getStore().getAsp().doubleValue());
                    cel2Row7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel2Row7.setCellStyle(styleCurrency0DecimalFormat);
                    //cel2Row7.setCellStyle(cellStyleAlignRight);

                    Cell cel3Row7 = row7.createCell(3);
                    cel3Row7.setCellValue(orderSummary.getAuction().getAsp().doubleValue());
                    cel3Row7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel3Row7.setCellStyle(styleCurrency0DecimalFormat);
                    //cel3Row7.setCellStyle(cellStyleAlignRight);

                    Cell cel4Row7 = row7.createCell(4);
                    cel4Row7.setCellValue(orderSummary.getEbay().getAsp().doubleValue());
                    cel4Row7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel4Row7.setCellStyle(styleCurrency0DecimalFormat);
                    //cel4Row7.setCellStyle(cellStyleAlignRight);

                    Cell cel5Row7 = row7.createCell(5);
                    cel5Row7.setCellValue(orderSummary.getCustomer().getAsp().doubleValue());
                    cel5Row7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    cel5Row7.setCellStyle(styleCurrency0DecimalFormat);
                    //cel5Row7.setCellStyle(cellStyleAlignRight);

                    row7.createCell(6).setCellValue(asp.doubleValue());
                    row7.getCell(6).setCellStyle(styleCurrency0DecimalFormat);
                    row7.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    

                    row8.createCell(1).setCellValue(orderSummary.getAgent().getSalesMhz().doubleValue());
                    row8.getCell(1).setCellStyle(styleCurrency3DecimalFormat);
                    row8.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row8.getCell(1).setCellStyle(cellStyleAlignRight);

                    row8.createCell(2).setCellValue(orderSummary.getStore().getSalesMhz().doubleValue());
                    row8.getCell(2).setCellStyle(styleCurrency3DecimalFormat);
                    row8.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row8.getCell(2).setCellStyle(cellStyleAlignRight);

                    row8.createCell(3).setCellValue(orderSummary.getAuction().getSalesMhz().doubleValue());
                    row8.getCell(3).setCellStyle(styleCurrency3DecimalFormat);
                    row8.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row8.getCell(3).setCellStyle(cellStyleAlignRight);

                    row8.createCell(4).setCellValue(orderSummary.getEbay().getSalesMhz().doubleValue());
                    row8.getCell(4).setCellStyle(styleCurrency3DecimalFormat);
                    row8.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row8.getCell(4).setCellStyle(cellStyleAlignRight);

                    row8.createCell(5).setCellValue(orderSummary.getCustomer().getSalesMhz().doubleValue());
                    row8.getCell(5).setCellStyle(styleCurrency3DecimalFormat);
                    row8.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row8.getCell(5).setCellStyle(cellStyleAlignRight);                   

                    row8.createCell(6).setCellValue(salesMhz.doubleValue());
                    row8.getCell(6).setCellStyle(styleCurrency3DecimalFormat);
                    row8.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row8.getCell(6).setCellStyle(cellStyleAlignRight);

                    row9.createCell(1).setCellValue(orderSummary.getAgent().getOrders());
                    row9.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row9.getCell(1).setCellStyle(cellStyleAlignRight);

                    row9.createCell(2).setCellValue(orderSummary.getStore().getOrders());
                    row9.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row9.getCell(2).setCellStyle(cellStyleAlignRight);

                    row9.createCell(3).setCellValue(orderSummary.getAuction().getOrders());
                    row9.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row9.getCell(3).setCellStyle(cellStyleAlignRight);

                    row9.createCell(4).setCellValue(orderSummary.getEbay().getOrders());
                    row9.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row9.getCell(4).setCellStyle(cellStyleAlignRight);

                    row9.createCell(5).setCellValue(orderSummary.getCustomer().getOrders());
                    row9.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row9.getCell(5).setCellStyle(cellStyleAlignRight);

                    row9.createCell(6).setCellValue(
                        orderSummary.getAgent().getOrders() + orderSummary.getStore().getOrders() + orderSummary.getAuction().getOrders() + orderSummary.getEbay().getOrders()
                            + orderSummary.getCustomer().getOrders());
                    row9.getCell(6).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row9.getCell(6).setCellStyle(cellStyleAlignRight);

                    break;
                }
            }
            if (listAgent.size() > 0)
            {
                rowIdx = rowIdx + 5;
                int unit = 0;
                BigDecimal sales = BigDecimal.valueOf(0);
                BigDecimal asp = BigDecimal.valueOf(0);
                BigDecimal salesMhz = BigDecimal.valueOf(0);
                int mhz = 0;
                String name = "Unknown Agent";

                Row row10 = sheet.createRow((short) rowIdx);
                row10.createCell(0).setCellValue("");
                int numAg = 1;
                for (Summary summary1 : listAgent)
                {
                    name = (summary1.getAgentName() == null) ? name : summary1.getAgentName();
                    row10.createCell(numAg).setCellValue(name);
                    //row10.getCell(numAg).setCellStyle(cellStyleAlignRight);
                    numAg++;
                }
                row10.createCell(numAg).setCellValue("Agent");
                row10.getCell(numAg).setCellStyle(cellStyleBold);
                Row row11 = sheet.createRow((short) rowIdx + 1);
                row11.createCell(0).setCellValue("Revenue");
                row11.getCell(0).setCellStyle(cellStyleBold);
                Row row12 = sheet.createRow((short) rowIdx + 2);
                row12.createCell(0).setCellValue("Units");
                row12.getCell(0).setCellStyle(cellStyleBold);
                Row row13 = sheet.createRow((short) rowIdx + 3);
                row13.createCell(0).setCellValue("ASP");
                row13.getCell(0).setCellStyle(cellStyleBold);
                Row row14 = sheet.createRow((short) rowIdx + 4);
                row14.createCell(0).setCellValue("$/Mhz");
                row14.getCell(0).setCellStyle(cellStyleBold);

                numAg = 1;
                for (Summary summary1 : listAgent)
                {
                	//row11.createCell(numAg).setCellValue(Converter.getMoney(summary1.getSales()));
                	row11.createCell(numAg).setCellValue(summary1.getSales().doubleValue());
                    row11.getCell(numAg).setCellStyle(styleCurrencyFormat);
                    row11.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row11.getCell(numAg).setCellStyle(cellStyleAlignRight);
                    sales = sales.add(summary1.getSales()).setScale(3, RoundingMode.HALF_EVEN);
                    numAg++;
                }
                row11.createCell(numAg).setCellValue(sales.doubleValue());
                row11.getCell(numAg).setCellStyle(styleCurrencyFormat);
                row11.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                //row11.getCell(numAg).setCellStyle(cellStyleAlignRight);

                numAg = 1;
                for (Summary summary1 : listAgent)
                {
                    row12.createCell(numAg).setCellValue(summary1.getUnits());
                    row12.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row12.getCell(numAg).setCellStyle(cellStyleAlignRight);
                    unit += summary1.getUnits();
                    numAg++;
                }
                row12.createCell(numAg).setCellValue(unit);
                row12.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                //row12.getCell(numAg).setCellStyle(cellStyleAlignRight);

                numAg = 1;
                for (Summary summary1 : listAgent)
                {
                    row13.createCell(numAg)
                        .setCellValue(
                            (summary1.getUnits() > 0) ? summary1.getSales().divide(BigDecimal.valueOf(summary1.getUnits()), 0, RoundingMode.HALF_EVEN).doubleValue() :0.000);
                    //Converter.getMoneyNonePoint((summary1.getUnits() > 0) ? summary1.getSales().divide(BigDecimal.valueOf(summary1.getUnits()), 0, RoundingMode.HALF_EVEN) : BigDecimal                                .valueOf(0)));
                    row13.getCell(numAg).setCellStyle(styleCurrency0DecimalFormat);
                    row13.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row13.getCell(numAg).setCellStyle(cellStyleAlignRight);
                    asp = (unit == 0) ? new BigDecimal(0) : sales.divide(BigDecimal.valueOf(unit), 0, RoundingMode.HALF_EVEN);
                    numAg++;
                }
                row13.createCell(numAg).setCellValue(asp.doubleValue());
                row13.getCell(numAg).setCellStyle(styleCurrency0DecimalFormat);
                row13.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                //row13.getCell(numAg).setCellStyle(cellStyleAlignRight);

                numAg = 1;
                for (Summary summary1 : listAgent)
                {
                    row14.createCell(numAg).setCellValue(
                        ((summary1.getMhz() > 0) ? summary1.getSales().divide(BigDecimal.valueOf(summary1.getMhz()), 3, RoundingMode.HALF_EVEN).doubleValue() : 0.000));
                    //"$" + ((summary1.getMhz() > 0) ? summary1.getSales().divide(BigDecimal.valueOf(summary1.getMhz()), 3, RoundingMode.HALF_EVEN) : BigDecimal.valueOf(0)));
                    row14.getCell(numAg).setCellStyle(styleCurrency3DecimalFormat);
                    row14.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //row14.getCell(numAg).setCellStyle(cellStyleAlignRight);
                    mhz += summary1.getMhz();
                    salesMhz = (mhz == 0) ? new BigDecimal(0) : sales.divide(BigDecimal.valueOf(mhz), 3, RoundingMode.HALF_EVEN);
                    numAg++;
                }
                row14.createCell(numAg).setCellValue( salesMhz.doubleValue());
                row14.getCell(numAg).setCellStyle(styleCurrency3DecimalFormat);
                row14.getCell(numAg).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                //row14.getCell(numAg).setCellStyle(cellStyleAlignRight);
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "OrderSummaryReport");
        }
        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward searchCustomerOrderExport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {      
    	String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

            OrderServices service = new OrderServices();
            HttpSession session = request.getSession();
            List<String> searchOrder = new ArrayList<String>();
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add(String.valueOf(session.getAttribute(Constants.SHOPPER_NUMBER)));
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("");
            searchOrder.add("201");

            List<Order> mapOrder = service.searchOrderCriteria(0, searchOrder);

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(12);

            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cellStyleWrapTextBold = wb.createCellStyle();
            cellStyleWrapTextBold.setFont(font);

            HSSFCellStyle cellStyleWrapTextBoldAlignRight = wb.createCellStyle();
            cellStyleWrapTextBoldAlignRight.setFont(font);
            cellStyleWrapTextBoldAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));

            Row row00 = sheet.createRow((short) 1);
            Cell cel0Row00 = row00.createCell((short) 0);
            cel0Row00.setCellValue("Previous Orders");
            cel0Row00.setCellStyle(cellStyleWrapTextBold);

            Row row1 = sheet.createRow((short) 3);
            Cell cel0Row1 = row1.createCell((short) 0);
            cel0Row1.setCellValue("#");
            cel0Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel1Row1 = row1.createCell((short) 1);
            cel1Row1.setCellValue("Order");
            cel1Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel2Row1 = row1.createCell((short) 2);
            cel2Row1.setCellValue("Date");
            cel2Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel3Row1 = row1.createCell((short) 3);
            cel3Row1.setCellValue("Ship To Name");
            cel3Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel6Row1 = row1.createCell((short) 4);
            cel6Row1.setCellValue("Items");
            cel6Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel10Row1 = row1.createCell((short) 5);
            cel10Row1.setCellValue("Total");
            cel10Row1.setCellStyle(cellStyleWrapTextBoldAlignRight);

            if (!mapOrder.isEmpty())
            {
                int rowIdx = 5;
                int count = 1;
                for (Order order : mapOrder)
                {
                    Row rowBasket = sheet.createRow((short) rowIdx);
                    rowBasket.createCell(0).setCellValue(count);
                    rowBasket.createCell(1).setCellValue(order.getOrderId());
                    rowBasket.createCell(2).setCellValue(order.getDayOrder());
                    rowBasket.createCell(3).setCellValue(order.getShip_to_name());
                    rowBasket.createCell(4).setCellValue(order.getItem());
                    
                    rowBasket.createCell(5).setCellValue(order.getTotal_total().doubleValue());
                    rowBasket.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    rowBasket.getCell(5).setCellStyle(styleCurrencyFormat);
                    //rowBasket.getCell(5).setCellStyle(cellStyleAlignRight);
                    rowIdx++;
                    count++;
                }
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "PreviousOrders");

        }
        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public final ActionForward excelOrderHeldCustomerMap(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;

        try
        {
            OrderServices service = new OrderServices();
            HttpSession session = request.getSession();
            List<OrderHeld> orderHeldMap = service.orderHelpCustomerMap(0, String.valueOf(session.getAttribute(Constants.SHOPPER_ID)));
            String totalHeld = Converter.getMoney(service.totalCustomerOrderHeld(String.valueOf(session.getAttribute(Constants.SHOPPER_ID))));

            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(11);

            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle cellStyleWrapTextBold = wb.createCellStyle();
            cellStyleWrapTextBold.setFont(font);

            HSSFCellStyle cellStyleWrapTextBoldAlignRight = wb.createCellStyle();
            cellStyleWrapTextBoldAlignRight.setFont(font);
            cellStyleWrapTextBoldAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            
            Row row000 = sheet.createRow((short) 0);
            Cell cel0row000 = row000.createCell((short) 0);
            cel0row000.setCellValue("Held Orders");
            cel0row000.setCellStyle(cellStyleWrapTextBold);

            Row row00 = sheet.createRow((short) 2);
            Cell cel0Row00 = row00.createCell((short) 0);
            cel0Row00.setCellValue("Order Total:" + totalHeld);
            cel0Row00.setCellStyle(cellStyleWrapTextBold);

            Row row1 = sheet.createRow((short) 3);
            Cell cel0Row1 = row1.createCell((short) 0);
            cel0Row1.setCellValue("#");
            cel0Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel1Row1 = row1.createCell((short) 1);
            cel1Row1.setCellValue("Order");
            cel1Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel2Row1 = row1.createCell((short) 2);
            cel2Row1.setCellValue("Modified");
            cel2Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel3Row1 = row1.createCell((short) 3);
            cel3Row1.setCellValue("Ship To Name");
            cel3Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel4Row1 = row1.createCell((short) 4);
            cel4Row1.setCellValue(" Items Held  ");
            cel4Row1.setCellStyle(cellStyleWrapTextBold);

            Cell cel5Row1 = row1.createCell((short) 5);
            cel5Row1.setCellValue("Order Total#");
            cel5Row1.setCellStyle(cellStyleWrapTextBoldAlignRight);

            if (orderHeldMap.size() > 0)
            {
                int rowIdx = 4;

                for (OrderHeld orderHeld : orderHeldMap)
                {
                    Row rowBasket = sheet.createRow((short) rowIdx);

                    rowBasket.createCell(0).setCellValue(orderHeld.getId());
                    rowBasket.createCell(1).setCellValue(orderHeld.getOrderId());
                    rowBasket.createCell(2).setCellValue(orderHeld.getDayOrder());
                    rowBasket.createCell(3).setCellValue(orderHeld.getShip_to_name());
                    rowBasket.createCell(4).setCellValue(orderHeld.getItem());
                    
                    //rowBasket.createCell(5).setCellValue(Converter.getMoney(orderHeld.getTotal_total().divide(BigDecimal.valueOf(100))));
                    //rowBasket.getCell(5).setCellStyle(cellStyleAlignRight);
                    rowBasket.createCell(5).setCellValue(orderHeld.getTotal_total().doubleValue()/100.0);
                    rowBasket.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    rowBasket.getCell(5).setCellStyle(styleCurrencyFormat);
                    rowIdx++;

                }

            }

            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "HeldOrders");

            return mapping.findForward(forward);
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        return mapping.findForward(forward);
    }

}
