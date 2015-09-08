/*
 * FILENAME
 *     Basket.java
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

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.OrderRow;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.services.ProductServices;
import com.dell.enterprise.agenttool.util.Constants;
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
 * @author vinhhq
 * 
 * @version $Id$
 **/
public class Basket extends DispatchAction
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.Basket");

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
                    forward = this.dispatchMethod(mapping, form, request, response, "actionBasket");
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

    public final ActionForward actionBasket(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.SHOW_BASKET;

        try
        {
            LOGGER.info("Action actionBasket");

            HttpSession sessions = request.getSession();
            ProductServices productServices = new ProductServices();
            BasketService basketService = new BasketService();
            Agent agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);

            Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
            String shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();

            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }

            String type = "";
            if (request.getParameter("type") != null)
            {
                type = request.getParameter("type");
            }

            if (type.equals("clear"))
            {
                String chks = "";
                String takens = "";

                if (request.getParameter("chks") != null)
                {
                    chks = request.getParameter("chks");
                }

                if (request.getParameter("takens") != null)
                {
                    takens = request.getParameter("takens");
                }

                productServices.orderDeleteItemBySku(shopper_id, chks, takens, true);
            }
            else if (type.equals("cancel"))
            {
                productServices.clearOrder(shopper_id, byAgent);
                request.setAttribute(Constants.ATTR_ORDER_CANCEL, 1);
            }

            //Check Held Order
            int countHeldOrder = basketService.countHeldOrder(shopper_id, byAgent);
            request.setAttribute(Constants.ATTR_COUNT_HELD_ORDER, countHeldOrder);

            //Load Basket
            OrderRow orderRow = basketService.getOrderRow(shopper_id, agent.getAgentId());
            List<EstoreBasketItem> listEstoreBasketItem = basketService.getBasketItems(shopper_id, byAgent);

            request.setAttribute(Constants.ATTR_ORDER_ROW, orderRow);
            request.setAttribute(Constants.ATTR_ESTORE_BASKET_ITEM, listEstoreBasketItem);

            if (isCustomer == null && !Constants.isNumber(shopper_id))
            {
                //get customer
                CustomerServices customerServices = new CustomerServices();
                Customer customer = customerServices.getCustomerByShopperID(shopper_id);

                request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                request.setAttribute(Constants.ATTR_CUSTOMER, customer);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
    }

    public final ActionForward exportExcel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.GLOBAL_EXPORT_EXCEL;

        try
        {
            LOGGER.info("Action actionBasket");

            HttpSession sessions = request.getSession();
            Agent agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);
            BasketService basketService = new BasketService();
            CheckoutService checkoutService = new CheckoutService();
            CustomerServices customerServices = new CustomerServices();

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
            String shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();

            //Load Customer
            Customer customer = customerServices.getCustomerByShopperID(shopper_id);
            //Load Basket
            List<EstoreBasketItem> listEstoreBasketItem = basketService.getBasketItems(shopper_id, byAgent);
            //Load Order Row
            OrderRow orderRow = basketService.getOrderRow(shopper_id, agent.getAgentId());

            //Create Workbook
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.autoSizeColumn((short) 2);

            //Create Style
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

            HSSFCellStyle cellStyleBold = wb.createCellStyle();
            cellStyleBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);
            cellStyleAlignRight.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

            HSSFCellStyle cellStyleBoldAlignRight = wb.createCellStyle();
            cellStyleBoldAlignRight.setFont(font);
            cellStyleBoldAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);
            
            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));
            styleCurrencyFormat.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

            //End Style 

            String strUnitPrice = "List Price";
            if (isCustomer != null)
                strUnitPrice = "Unit Price";

            //Create Row Header
            Row rowBasketHeader = sheet.createRow((short) 0);

            Cell cel2rowBasketHeader = rowBasketHeader.createCell((short) 1);
            cel2rowBasketHeader.setCellValue("Product Number");
            cel2rowBasketHeader.setCellStyle(cellStyleBold);

            Cell cel3rowBasketHeader = rowBasketHeader.createCell((short) 2);
            cel3rowBasketHeader.setCellValue("Product Description");
            cel3rowBasketHeader.setCellStyle(cellStyleBold);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 10));

            Cell cel10rowBasketHeader = rowBasketHeader.createCell((short) 11);
            cel10rowBasketHeader.setCellValue(strUnitPrice);
            cel10rowBasketHeader.setCellStyle(cellStyleBold);

            int idxRow = 1;
            Float totalPrice = new Float(0);
            for (EstoreBasketItem estoreBasketItem : listEstoreBasketItem)
            {
                Float listPrice = new Float(basketService.getListPrice(estoreBasketItem.getItem_sku()) * 100);
                if (isCustomer != null)
                {
                    List<Float> listValue = checkoutService.utilGetDiscount(customer, estoreBasketItem.getItem_sku(), listPrice, estoreBasketItem.getPlaced_price());
                    listPrice = listValue.get(1);
                }
                listPrice = listPrice / 100;
                totalPrice += listPrice;

                Row rowBasketItem = sheet.createRow((short) idxRow);

                Cell cel1rowBasket = rowBasketItem.createCell((short) 0);
                cel1rowBasket.setCellValue(idxRow);

                Cell cel2rowBasket = rowBasketItem.createCell((short) 1);
                cel2rowBasket.setCellValue(estoreBasketItem.getItem_sku());

                Cell cel3rowBasket = rowBasketItem.createCell((short) 2);
                cel3rowBasket.setCellValue(estoreBasketItem.getName());
                sheet.addMergedRegion(new CellRangeAddress(idxRow, idxRow, 2, 10));

                Cell cel10rowBasket = rowBasketItem.createCell((short) 11);
                cel10rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                cel10rowBasket.setCellStyle(styleCurrencyFormat);
                cel10rowBasket.setCellValue(listPrice.doubleValue());

                idxRow++;
            }
            idxRow++;

            //Create Row Total
            Row rowEstimatedSubTotal = sheet.createRow((short) idxRow);
            Cell cel1rowEstimatedSubTotal = rowEstimatedSubTotal.createCell((short) 7);
            cel1rowEstimatedSubTotal.setCellValue("Estimated Sub Total:");
            cel1rowEstimatedSubTotal.setCellStyle(cellStyleBoldAlignRight);

            sheet.addMergedRegion(new CellRangeAddress(idxRow, idxRow, 7, 10));

            Cell cel10rowBasket = rowEstimatedSubTotal.createCell((short) 11);
            //cel10rowBasket.setCellValue(Constants.FormatCurrency(totalPrice));
            //cel10rowBasket.setCellStyle(cellStyleAlignRight);
            cel10rowBasket.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cel10rowBasket.setCellStyle(styleCurrencyFormat);
            cel10rowBasket.setCellValue(totalPrice.doubleValue());

            sheet.addMergedRegion(new CellRangeAddress(idxRow, idxRow + 1, 11, 11));
            idxRow++;

            Row rowEstimatedSubTotalEnd = sheet.createRow((short) idxRow);
            Cell cel1rowEstimatedSubTotalEnd = rowEstimatedSubTotalEnd.createCell((short) 7);
            cel1rowEstimatedSubTotalEnd.setCellValue("(Excludes Shipping and Taxes)");
            cel1rowEstimatedSubTotalEnd.setCellStyle(cellStyleAlignRight);
            sheet.addMergedRegion(new CellRangeAddress(idxRow, idxRow, 7, 10));

            //End Create Row Total

            //Set Attribute
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", orderRow.getOrder_id() + "_BK_" + Calendar.getInstance().getTimeInMillis());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
    }
}
