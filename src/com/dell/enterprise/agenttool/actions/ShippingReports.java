/*
 * FILENAME
 *     ShippingReports.java
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

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Reports;
import com.dell.enterprise.agenttool.services.ReportServices;
import com.dell.enterprise.agenttool.util.Constants;

/**
 * @author hungnguyen,thuynguyen
 **/
public class ShippingReports extends DispatchAction
{

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();

        if (session.getAttribute(Constants.AGENT_INFO) == null)
        {
            forward = mapping.findForward(Constants.SESSION_TIMEOUT);
        }
        else
        {
            try
            {
                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);

                if (method == null || "".equals(method))
                {
                    forward = mapping.findForward(Constants.SHIPPING_REPORTS_VIEW);
                }
                else
                {
                    forward = this.dispatchMethod(mapping, form, request, response, method);
                }
            }
            catch (Exception e)
            {
                forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
            }
        }

        return forward;
    }

    public final ActionForward searchShippingReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        try
        {
            HttpSession session = request.getSession();
           /* String userLevel = (String) session.getAttribute(Constants.USER_LEVEL);
            if (session.getAttribute(Constants.IS_CUSTOMER)==null && userLevel!=null && !userLevel.equals("C"))*/
            if (session.getAttribute(Constants.IS_CUSTOMER)==null )
            {
            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);
            String check = request.getParameter("output_type_rdo");

            List<Reports> searchShippingReport = null;
            ReportServices reportSearvices = new ReportServices();
            // List<String> paramList = new ArrayList<String>();    
            searchShippingReport = reportSearvices.searchShippingReports(datepickerFrom, datepickerTo);
            request.setAttribute(Constants.SEARCH_RESULT_BY_REPORT_SHIPPING, searchShippingReport);
            request.setAttribute(Constants.DATAPICKER_FROM, datepickerFrom);
            request.setAttribute(Constants.DATAPICKER_TO, datepickerTo);
            request.setAttribute("valueCheck", check);
            forward = Constants.SEARCH_RESULT_BY_REPORT_SHIPPING;
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public ActionForward exportExcel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {
            HttpSession session = request.getSession();
            //String userLevel = (String) session.getAttribute(Constants.USER_LEVEL);
            if (session.getAttribute(Constants.IS_CUSTOMER)==null )
            {
            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);
            List<Reports> searchShippingReport = null;
            ReportServices reportSearvices = new ReportServices();
            // List<String> paramList = new ArrayList<String>();    
            searchShippingReport = reportSearvices.searchShippingReports(datepickerFrom, datepickerTo);

            Workbook wb = new HSSFWorkbook();
            Sheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(15);          

            CellStyle cellStyleBold = wb.createCellStyle();
            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellStyleBold.setFont(font);

            CellStyle cs = wb.createCellStyle();
            cs.setWrapText(true);

            Row row0 = sheet.createRow((short) 0);
            row0.createCell(0).setCellValue("Shipping Report");
            Row row2 = sheet.createRow((short) 2);
            //  Cell cellrow2 = row2.createCell((short) 0);
            row2.createCell(0).setCellValue("item_SKU");
            row2.createCell(1).setCellValue("category_id");
            row2.createCell(2).setCellValue("manufacturer_id");
            row2.createCell(3).setCellValue("mfg_part_number");
            row2.createCell(4).setCellValue("name");
            row2.createCell(5).setCellValue("image_url");
            row2.createCell(6).setCellValue("short_description");
            row2.createCell(7).setCellValue("long_description");
            row2.createCell(8).setCellValue("weight");
            row2.createCell(9).setCellValue("download_filename");
            row2.createCell(10).setCellValue("received_by");
            row2.createCell(11).setCellValue("received_date");
            row2.createCell(12).setCellValue("warehouse_location");
            row2.createCell(13).setCellValue("status");
            row2.createCell(14).setCellValue("status_date");
            row2.createCell(15).setCellValue("list_price");
            row2.createCell(16).setCellValue("modified_price");
            row2.createCell(17).setCellValue("modified_date");
            row2.createCell(18).setCellValue("warranty_date");
            row2.createCell(19).setCellValue("flagType");
            row2.createCell(20).setCellValue("lease_number");
            row2.createCell(21).setCellValue("contract_number");
            row2.createCell(22).setCellValue("mfg_SKU");
            row2.createCell(23).setCellValue("notes");
            row2.createCell(24).setCellValue("ship_via");
            row2.createCell(25).setCellValue("attribute01");
            row2.createCell(26).setCellValue("attribute02");
            row2.createCell(27).setCellValue("attribute03");
            row2.createCell(28).setCellValue("attribute04");
            row2.createCell(29).setCellValue("attribute05");
            row2.createCell(30).setCellValue("attribute06");
            row2.createCell(31).setCellValue("attribute07");
            row2.createCell(32).setCellValue("attribute08");
            row2.createCell(33).setCellValue("attribute09");
            row2.createCell(34).setCellValue("attribute10");
            row2.createCell(35).setCellValue("attribute11");
            row2.createCell(36).setCellValue("attribute12");
            row2.createCell(37).setCellValue("attribute13");
            row2.createCell(38).setCellValue("attribute14");
            row2.createCell(39).setCellValue("attribute15");
            row2.createCell(40).setCellValue("attribute16");
            row2.createCell(41).setCellValue("attribute17");
            row2.createCell(42).setCellValue("attribute18");
            row2.createCell(43).setCellValue("attribute19");
            row2.createCell(44).setCellValue("attribute20");
            row2.createCell(45).setCellValue("attribute21");
            row2.createCell(46).setCellValue("attribute22");
            row2.createCell(47).setCellValue("attribute23");
            row2.createCell(48).setCellValue("attribute24");
            row2.createCell(49).setCellValue("attribute25");
            row2.createCell(50).setCellValue("attribute26");
            row2.createCell(51).setCellValue("attribute27");
            row2.createCell(52).setCellValue("attribute28");
            row2.createCell(53).setCellValue("attribute29");
            row2.createCell(54).setCellValue("attribute30");
            row2.createCell(55).setCellValue("attribute31");
            row2.createCell(56).setCellValue("attribute32");
            row2.createCell(57).setCellValue("attribute33");
            row2.createCell(58).setCellValue("attribute34");
            row2.createCell(59).setCellValue("attribute35");
            row2.createCell(60).setCellValue("attribute36");
            row2.createCell(61).setCellValue("attribute37");
            row2.createCell(62).setCellValue("attribute38");
            row2.createCell(63).setCellValue("attribute39");
            row2.createCell(64).setCellValue("attribute40");
            row2.createCell(65).setCellValue("order_number");
            row2.createCell(66).setCellValue("tracking_number");
            row2.createCell(67).setCellValue("ship_date");
            row2.createCell(68).setCellValue("origin");
            int rowIdx = 3;
            if (searchShippingReport.size() > 0)
            {
                for (Reports report : searchShippingReport)
                {
                    Row rowBasket = sheet.createRow((short) rowIdx);
                    rowBasket.createCell(0).setCellValue(report.getItem_SKU());
                    rowBasket.createCell(1).setCellValue(report.getCategory_id());
                    rowBasket.createCell(2).setCellValue(report.getManufacturer_id());
                    rowBasket.createCell(3).setCellValue(report.getMfg_part_number());
                    rowBasket.createCell(4).setCellValue(report.getName());
                    rowBasket.createCell(5).setCellValue(report.getImage_url());
                    rowBasket.createCell(6).setCellValue(report.getShort_description());
                    rowBasket.createCell(7).setCellValue(report.getLong_description());
                    rowBasket.createCell(8).setCellValue(report.getWeight());
                    rowBasket.createCell(9).setCellValue(report.getDownload_filename());
                    rowBasket.createCell(10).setCellValue(report.getReceived_by());
                    rowBasket.createCell(11).setCellValue(report.getReceived_date());
                    rowBasket.createCell(12).setCellValue(report.getWarehouse_location());
                    rowBasket.createCell(13).setCellValue(report.getStatus());
                    rowBasket.createCell(14).setCellValue(report.getStatus_date());
                    rowBasket.createCell(15).setCellValue(report.getList_price());
                    rowBasket.createCell(16).setCellValue(report.getModified_price());
                    rowBasket.createCell(17).setCellValue(report.getModified_date());
                    rowBasket.createCell(18).setCellValue(report.getWarranty_date());
                    rowBasket.createCell(19).setCellValue(report.getFlagType());
                    rowBasket.createCell(20).setCellValue(report.getLease_number());
                    rowBasket.createCell(21).setCellValue(report.getContract_number());
                    rowBasket.createCell(22).setCellValue(report.getMfg_SKU());
                    rowBasket.createCell(23).setCellValue(report.getNotes());
                    rowBasket.createCell(24).setCellValue(report.getShip_via());
                    rowBasket.createCell(25).setCellValue(report.getAttribute01());
                    rowBasket.createCell(26).setCellValue(report.getAttribute02());
                    rowBasket.createCell(27).setCellValue(report.getAttribute03());
                    rowBasket.createCell(28).setCellValue(report.getAttribute04());
                    rowBasket.createCell(29).setCellValue(report.getAttribute05());
                    rowBasket.createCell(30).setCellValue(report.getAttribute06());
                    rowBasket.createCell(31).setCellValue(report.getAttribute07());
                    rowBasket.createCell(32).setCellValue(report.getAttribute08());
                    rowBasket.createCell(33).setCellValue(report.getAttribute09());
                    rowBasket.createCell(34).setCellValue(report.getAttribute10());
                    rowBasket.createCell(35).setCellValue(report.getAttribute11());
                    rowBasket.createCell(36).setCellValue(report.getAttribute12());
                    rowBasket.createCell(37).setCellValue(report.getAttribute13());
                    rowBasket.createCell(38).setCellValue(report.getAttribute14());
                    rowBasket.createCell(39).setCellValue(report.getAttribute15());
                    rowBasket.createCell(40).setCellValue(report.getAttribute16());
                    rowBasket.createCell(41).setCellValue(report.getAttribute17());
                    rowBasket.createCell(42).setCellValue(report.getAttribute18());
                    rowBasket.createCell(43).setCellValue(report.getAttribute19());
                    rowBasket.createCell(44).setCellValue(report.getAttribute20());
                    rowBasket.createCell(45).setCellValue(report.getAttribute21());
                    rowBasket.createCell(46).setCellValue(report.getAttribute22());
                    rowBasket.createCell(47).setCellValue(report.getAttribute23());
                    rowBasket.createCell(48).setCellValue(report.getAttribute24());
                    rowBasket.createCell(49).setCellValue(report.getAttribute25());
                    rowBasket.createCell(50).setCellValue(report.getAttribute26());
                    rowBasket.createCell(51).setCellValue(report.getAttribute27());
                    rowBasket.createCell(52).setCellValue(report.getAttribute28());
                    rowBasket.createCell(53).setCellValue(report.getAttribute29());
                    rowBasket.createCell(54).setCellValue(report.getAttribute30());
                    rowBasket.createCell(55).setCellValue(report.getAttribute31());
                    rowBasket.createCell(56).setCellValue(report.getAttribute32());
                    rowBasket.createCell(57).setCellValue(report.getAttribute33());
                    rowBasket.createCell(58).setCellValue(report.getAttribute34());
                    rowBasket.createCell(59).setCellValue(report.getAttribute35());
                    rowBasket.createCell(60).setCellValue(report.getAttribute36());
                    rowBasket.createCell(61).setCellValue(report.getAttribute37());
                    rowBasket.createCell(62).setCellValue(report.getAttribute38());
                    rowBasket.createCell(63).setCellValue(report.getAttribute39());
                    rowBasket.createCell(64).setCellValue(report.getAttribute40());
                    rowBasket.createCell(65).setCellValue(report.getOrder_number());
                    rowBasket.createCell(66).setCellValue(report.getTracking_number());
                    rowBasket.createCell(67).setCellValue(report.getShip_date());
                    rowBasket.createCell(68).setCellValue(report.getOrigin());
                    rowIdx++;
                }
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "ShippingReport");
        }}

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

}
