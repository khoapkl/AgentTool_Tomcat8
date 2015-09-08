/**
 * 
 */
package com.dell.enterprise.agenttool.actions;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.DAO.ProductDAO;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.CreditReportOrder;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.DiscountAdjustment;
import com.dell.enterprise.agenttool.model.Order;
import com.dell.enterprise.agenttool.model.OrderAgent;
import com.dell.enterprise.agenttool.model.OrderAgentDetail;
import com.dell.enterprise.agenttool.model.OrderCriteria;
import com.dell.enterprise.agenttool.model.OrderDate;
import com.dell.enterprise.agenttool.model.OrderHeld;
import com.dell.enterprise.agenttool.model.OrderPending;
import com.dell.enterprise.agenttool.model.OrderShopper;
import com.dell.enterprise.agenttool.model.OrderSummary;
import com.dell.enterprise.agenttool.model.OrderViewPending;
import com.dell.enterprise.agenttool.model.Product;
import com.dell.enterprise.agenttool.model.Summary;
import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.services.OrderServices;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;
import com.dell.enterprise.agenttool.model.SummaryInventoryReport;
import com.dell.enterprise.agenttool.model.SoldAssetReport;

/**
 * @author linhdo
 * 
 */
public class OrderManager extends DispatchAction

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

    public final ActionForward searchCustomerOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_CUSTOMER_RESULT;
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
        searchOrder.add("");

        List<Order> mapOrder = service.searchOrderCriteria(1, searchOrder);
        int totalRecord = 0;
        if (!mapOrder.isEmpty())
        {
            Order order = mapOrder.get(0);
            totalRecord = order.getTotalRow();
        }
        session.setAttribute(Constants.ORDER_CUSTOMER_CRITERIA, searchOrder);
        request.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        return mapping.findForward(forward);
    }

    @SuppressWarnings("unchecked")
    public final ActionForward movingCustomerOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_CUSTOMER_RESULT;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        List<String> searchOrder = (List<String>) session.getAttribute(Constants.ORDER_CUSTOMER_CRITERIA);

        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        List<Order> mapOrder = service.searchOrderCriteria(noPage, searchOrder);

        request.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        return mapping.findForward(forward);
    }

    public final ActionForward searchOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_RESULT;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            List<String> searchOrder = new ArrayList<String>();
            searchOrder.add(request.getParameter(Constants.ORDER_NUMBER).trim());
            searchOrder.add(request.getParameter(Constants.ORDER_SORT));
            searchOrder.add(request.getParameter(Constants.ITEM_SKU).trim());
            searchOrder.add(request.getParameter(Constants.LISTING_NUMBER).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_LINK_NUMBER).trim());
            System.out.println("Filter : --------- " + request.getParameter(Constants.FILTER_TYPE));
            searchOrder.add(request.getParameter(Constants.FILTER_TYPE));
            searchOrder.add(request.getParameter(Constants.SHIP_TO_EMAIL).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_SHIP_FNAME).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_SHIP_LNAME).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_SHIP_COMPANY).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_SHIP_PHONE).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_BILL_FNAME).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_BILL_LNAME).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_BILL_COMPANY).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_BILL_PHONE).trim());
            searchOrder.add(request.getParameter(Constants.DB_FIELD_BILL_PHONE).trim());
            searchOrder.add("");
            List<Order> mapOrder = service.searchOrderCriteria(1, searchOrder);
            int totalRecord = 0;
            if (!mapOrder.isEmpty())
            {
                Order order = mapOrder.get(0);
                totalRecord = order.getTotalRow();
            }

            System.out.println(totalRecord);

            session.setAttribute(Constants.ORDER_CRITERIA, searchOrder);
            session.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
            session.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            session.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);

            request.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        }
        return mapping.findForward(forward);
    }

    public final ActionForward backOrderForwardPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        request.setAttribute("backPage", true);
        return mapping.findForward("agenttools.order_list");
    }

    public final ActionForward backOrderPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_RESULT;
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            request.setAttribute(Constants.ORDER_LIST_RESULT, session.getAttribute(Constants.ORDER_LIST_RESULT));
            request.setAttribute(Constants.ORDER_TOTAL, session.getAttribute(Constants.ORDER_TOTAL));
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, session.getAttribute(Constants.ORDER_NUMBER_PAGE));
        }
        return mapping.findForward(forward);
    }

    @SuppressWarnings("unchecked")
    public final ActionForward pagingMove(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_RESULT;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        List<String> searchOrder = (List<String>) session.getAttribute(Constants.ORDER_CRITERIA);
        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }
        List<Order> mapOrder = service.searchOrderCriteria(noPage, searchOrder);

        session.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        session.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);

        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        return mapping.findForward(forward);
    }

    public final ActionForward heldCustomerOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_HELD_CUSTOMER_FOR;
        String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
        if (userLevel != null && !userLevel.equals("C"))
        {
            OrderServices service = new OrderServices();
            HttpSession session = request.getSession();
            List<OrderHeld> orderHeldMap = service.orderHelpCustomerMap(1, String.valueOf(session.getAttribute(Constants.SHOPPER_ID)));

            String totalHeld = Converter.getMoney(service.totalCustomerOrderHeld(String.valueOf(session.getAttribute(Constants.SHOPPER_ID))));

            //int totalRecord = service.countHeldCustomerOrder(String.valueOf(session.getAttribute(Constants.SHOPPER_ID)));
            int totalRecord = service.getTotalRecord();

            session.setAttribute(Constants.ORDER_HELD_TOTAL, totalHeld);
            request.setAttribute(Constants.ORDER_HELD_RESULT, orderHeldMap);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        }
        return mapping.findForward(forward);
    }

    public ActionForward cancelHeldOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
    	CheckoutService service = new CheckoutService();
    	String orderNumber = request.getParameter("orderNumber");
    	service.setOrdersCancel(orderNumber);
    	OrderManager manager = new OrderManager(); 
    	return manager.heldCustomerOrder(mapping,form,request,response);
    }
    
    public final ActionForward heldCustomerPaging(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_HELD_CUSTOMER_FOR_SECOND;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();

        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }
        List<OrderHeld> orderHeldMap = service.orderHelpCustomerMap(noPage, String.valueOf(session.getAttribute(Constants.SHOPPER_ID)));
        request.setAttribute(Constants.ORDER_HELD_RESULT, orderHeldMap);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        return mapping.findForward(forward);
    }

    public final ActionForward heldOrderByAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_HELD_AGENT;
        String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
        if (userLevel != null && !userLevel.equals("C"))
        {
            OrderServices service = new OrderServices();
            HttpSession session = request.getSession();
            Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
            if (session.getAttribute(Constants.IS_CUSTOMER) == null)
            {
                List<OrderHeld> orderHeldMap = service.orderHelpMap(1, agent, "AGENT");
                String totalHeld = Converter.getMoney(service.totalOrderHeld(agent, "AGENT"));
                int totalRecord = service.getTotalRecord();

                session.setAttribute(Constants.ORDER_HELD_TOTAL, totalHeld);
                request.setAttribute(Constants.ORDER_HELD_RESULT, orderHeldMap);
                request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
                request.setAttribute(Constants.ORDER_TOTAL, totalRecord);

                System.out.println(totalRecord);
            }
        }
        return mapping.findForward(forward);
    }

    public final ActionForward movingHeldByAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_HELD_AGENT_RESULT;
        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
        OrderServices service = new OrderServices();
        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        List<OrderHeld> orderHeldMap = service.orderHelpMap(noPage, agent, "AGENT");

        request.setAttribute(Constants.ORDER_HELD_RESULT, orderHeldMap);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        return mapping.findForward(forward);
    }

    public final ActionForward heldOrderByCustomer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_HELD_FOR;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
        if (userLevel != null && !userLevel.equals("C"))
        {
            Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
            if (session.getAttribute(Constants.IS_CUSTOMER) == null)
            {
                List<OrderHeld> orderHeldMap = service.orderHelpMap(1, agent, "CUSTOMER");
                String totalHeld = Converter.getMoney(service.totalOrderHeld(agent, "CUSTOMER"));
                //int totalRecord = service.countHeldOrder(agent, "CUSTOMER");
                int totalRecord = service.getTotalRecord();
                //System.out.println(totalRecord);
                session.setAttribute(Constants.ORDER_HELD_TOTAL, totalHeld);
                request.setAttribute(Constants.ORDER_HELD_RESULT, orderHeldMap);
                request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
                request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            }
        }
        return mapping.findForward(forward);
    }

    public final ActionForward movingHeldByCustomer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_HELD_RESULT_FOR;
        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
        OrderServices service = new OrderServices();
        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        List<OrderHeld> orderHeldMap = service.orderHelpMap(noPage, agent, "CUSTOMER");

        request.setAttribute(Constants.ORDER_HELD_RESULT, orderHeldMap);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        return mapping.findForward(forward);
    }

    public final ActionForward allOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {

        return mapping.findForward(Constants.ORDER_FORWARD);
    }

    public final ActionForward shopOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {

        return mapping.findForward(Constants.ORDER_SHOP_FORWARD);
    }

    public final ActionForward listAllOrderPending(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            List<OrderPending> listAllOrderPending = null;
            OrderServices listOrderPendingServies = new OrderServices();
            listAllOrderPending = listOrderPendingServies.listAllOrderPending(1);
            int totalRecord = listOrderPendingServies.getTotalRecord();
            request.setAttribute(Constants.LIST_ORDER_PENDING_FORWARD, listAllOrderPending);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        }
        return mapping.findForward(Constants.LIST_ORDER_PENDING_FORWARD);
    }

    public ActionForward exportExcelAllpending(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_SHIPPING_REPORT;
        try
        {

        	HSSFWorkbook wb = new HSSFWorkbook();
        	HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);

            HSSFFont font = (HSSFFont) wb.createFont();
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            
            HSSFCellStyle cellStyleWrapTextBold = wb.createCellStyle();
            cellStyleWrapTextBold.setFont(font);

            HSSFCellStyle cellStyleAlignRight = wb.createCellStyle();
            cellStyleAlignRight.setAlignment(CellStyle.ALIGN_RIGHT);

            HSSFCellStyle styleCurrencyFormat = null;
            styleCurrencyFormat = wb.createCellStyle();
            styleCurrencyFormat.setDataFormat(HSSFDataFormat.getBuiltinFormat("$#,##0.00_);($#,##0.00)"));

            HttpSession session = request.getSession();

            if (session.getAttribute(Constants.IS_CUSTOMER) == null)
            {
                List<OrderPending> listAllOrderPending = null;
                OrderServices listOrderPendingServies = new OrderServices();
                listAllOrderPending = listOrderPendingServies.listAllOrderPendingExport();
                //                String shopperName = (String) session.getAttribute(Constants.SHOPPER_NAME);

                //                Row row0 = sheet.createRow((short) 0);
                //                Cell cel0Row0 = row0.createCell((short) 0);
                //                cel0Row0.setCellValue("Current User: " + shopperName);
                //                cel0Row0.setCellStyle(cellStyleWrapTextBold);

                Row row1 = sheet.createRow((short) 1);
                Cell cel0Row1 = row1.createCell((short) 0);
                cel0Row1.setCellValue("All Pending Orders");
                cel0Row1.setCellStyle(cellStyleWrapTextBold);

                Row row2 = sheet.createRow((short) 3);
                Cell cel0Row2 = row2.createCell((short) 0);
                cel0Row2.setCellValue("Order");
                cel0Row2.setCellStyle(cellStyleWrapTextBold);

                Cell cel1Row2 = row2.createCell((short) 1);
                cel1Row2.setCellValue("Ship To Name");
                cel1Row2.setCellStyle(cellStyleWrapTextBold);

                Cell cel2Row2 = row2.createCell((short) 2);
                cel2Row2.setCellValue("Total");
                cel2Row2.setCellStyle(cellStyleWrapTextBold);

                Cell cel3Row2 = row2.createCell((short) 3);
                cel3Row2.setCellValue("Agent");
                cel3Row2.setCellStyle(cellStyleWrapTextBold);

                Cell cel4Row2 = row2.createCell((short) 4);
                cel4Row2.setCellValue("Term");
                cel4Row2.setCellStyle(cellStyleWrapTextBold);

                int rowIdx = 4;
                DecimalFormat df = new DecimalFormat("0.00");
                if (listAllOrderPending.size() > 0)
                {
                    for (OrderPending report : listAllOrderPending)
                    {
                        Row rowBasket = sheet.createRow((short) rowIdx);
                        rowBasket.createCell(0).setCellValue(Double.parseDouble(report.getOrdernumber()) );
                        rowBasket.getCell(0).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        
                        rowBasket.createCell(1).setCellValue(report.getShip_to_name());
                       
                        //rowBasket.createCell(2).setCellValue(Constants.FormatCurrency(new Float(report.getTotal_total_pending())));
                        //rowBasket.createCell(2).setCellValue(Double.parseDouble(report.getTotal_total_pending()));
                        //System.out.println(" Data : "+df.format(new Float(report.getTotal_total_pending())));
                        rowBasket.createCell(2).setCellValue(Double.valueOf(df.format(new Float(report.getTotal_total_pending()))));
                        rowBasket.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                        rowBasket.getCell(2).setCellStyle(styleCurrencyFormat);
                        //rowBasket.getCell(2).setCellStyle(cellStyleAlignRight);
                        
                        rowBasket.createCell(3).setCellValue(report.getUsername());
                        rowBasket.createCell(4).setCellValue(report.getCc_type());
                        rowIdx++;

                    }
                }
                request.setAttribute("ExcelContent", wb);
                request.setAttribute("ExcelName", "ListAllPending");

            }

        }

        catch (Exception e)
        {
            //    LOGGER.info("Action exportExcel - Checkout 3  - Exception");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    /**
     * @param mapping
     *            ActionMapping
     * @param form
     *            ActionForm
     * @param request
     *            HttpServletRequest
     * @param response
     *            HttpServletResponse
     * @return ActionForward
     **/
    public ActionForward movingListAllOrderPending(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.LIST_ORDER_PENDING_MORE_FORWARD;

        try
        {
            OrderServices service = new OrderServices();
            int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
            int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
            String caseMove = request.getParameter(Constants.MOVING_PAGING);
            if (caseMove.equals(Constants.MOVING_FIRST))
            {
                noPage = 1;
            }
            if (caseMove.equals(Constants.MOVING_PREV))
            {
                noPage = noPage - 1;
            }
            if (caseMove.equals(Constants.MOVING_NEXT))
            {
                noPage = noPage + 1;
            }
            if (caseMove.equals(Constants.MOVING_LAST))
            {
                noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
            }

            List<OrderPending> notes = service.listAllOrderPending(noPage);
            request.setAttribute(Constants.LIST_ORDER_PENDING_FORWARD, notes);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);

        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
    }

    public final ActionForward searchShopOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_SHOP_RESULT_FOR;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            List<OrderShopper> listShoppers = new ArrayList<OrderShopper>();
            int totalRow = 0;

            OrderCriteria orderCriteria = new OrderCriteria();
            orderCriteria.setOrderId(request.getParameter(Constants.ORDER_NUMBER));

            orderCriteria.setOrderType(request.getParameter(Constants.ORDER_SORT));
            orderCriteria.setItemSku(request.getParameter(Constants.ITEM_SKU).trim());

            orderCriteria.setCustomerId(request.getParameter(Constants.DB_FIELD_LINK_NUMBER).trim());

            orderCriteria.setSfname(request.getParameter(Constants.DB_FIELD_SHIP_FNAME).trim());
            orderCriteria.setSlname(request.getParameter(Constants.DB_FIELD_SHIP_LNAME).trim());
            orderCriteria.setScom(request.getParameter(Constants.DB_FIELD_SHIP_COMPANY).trim());
            orderCriteria.setSphone(request.getParameter(Constants.DB_FIELD_SHIP_PHONE).trim());
            orderCriteria.setBfname(request.getParameter(Constants.DB_FIELD_BILL_FNAME).trim());
            orderCriteria.setBlname(request.getParameter(Constants.DB_FIELD_BILL_LNAME).trim());
            orderCriteria.setBcom(request.getParameter(Constants.DB_FIELD_BILL_COMPANY).trim());
            orderCriteria.setBphone(request.getParameter(Constants.DB_FIELD_BILL_PHONE).trim());

            listShoppers = service.searchOrdersByShopper(1, orderCriteria);
            totalRow = service.getTotalRecord();
            session.setAttribute(Constants.ORDER_SHOP_CRITERIA, orderCriteria);
            session.setAttribute(Constants.ORDER_SHOP_RESULT, listShoppers);
            session.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
            session.setAttribute(Constants.ORDER_TOTAL, totalRow);

            request.setAttribute(Constants.ORDER_SHOP_RESULT, listShoppers);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
            request.setAttribute(Constants.ORDER_TOTAL, totalRow);
        }
        return mapping.findForward(forward);
    }

    public final ActionForward backShopperForwardPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        request.setAttribute("backPage", true);
        return mapping.findForward(Constants.ORDER_SHOP_FORWARD);
    }

    public final ActionForward backShopperPage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_SHOP_RESULT_FOR;
        HttpSession session = request.getSession();

        request.setAttribute(Constants.ORDER_SHOP_RESULT, session.getAttribute(Constants.ORDER_SHOP_RESULT));
        request.setAttribute(Constants.ORDER_TOTAL, session.getAttribute(Constants.ORDER_TOTAL));
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, session.getAttribute(Constants.ORDER_NUMBER_PAGE));

        return mapping.findForward(forward);
    }

    public final ActionForward movingShopper(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_SHOP_RESULT_FOR;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        List<OrderShopper> listShoppers = new ArrayList<OrderShopper>();
        OrderCriteria orderCriteria = (OrderCriteria) session.getAttribute(Constants.ORDER_SHOP_CRITERIA);
        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        listShoppers = service.searchOrdersByShopper(noPage, orderCriteria);
        session.setAttribute(Constants.ORDER_SHOP_RESULT, listShoppers);
        session.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        session.setAttribute(Constants.ORDER_TOTAL, totalRecord);

        request.setAttribute(Constants.ORDER_SHOP_RESULT, listShoppers);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        return mapping.findForward(forward);
    }

    public final ActionForward updateDiscountAdjustment(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String percdiscount = request.getParameter(Constants.PERCDISCOUNT);
        String expirationDays = request.getParameter(Constants.EXPIRATION_DAYS);
        int CovPercdiscount = Integer.parseInt(percdiscount);
        int CovExpirationDays = Integer.parseInt(expirationDays);
        OrderServices discountAdjustment = new OrderServices();
        discountAdjustment.updateDiscountAdjustment(CovPercdiscount, CovExpirationDays);
        return null;
    }

    public final ActionForward viewCreditReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String formatDateToDisplay = dateFormat.format(date);
            request.setAttribute(Constants.DATE_VIEW_CREDIT_REPORT, formatDateToDisplay);
        }
        return mapping.findForward(Constants.VIEW_CREDIT_REPORT);
    }
    
    public final ActionForward viewInventoryReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String formatDateToDisplay = dateFormat.format(date);
            request.setAttribute(Constants.DATE_VIEW_CREDIT_REPORT, formatDateToDisplay);
        }
        return mapping.findForward(Constants.VIEW_SUMMARY_INVENTORY_REPORT);
    }
    
    public final ActionForward viewSoldAssetReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String formatDateToDisplay = dateFormat.format(date);
            request.setAttribute(Constants.DATE_VIEW_CREDIT_REPORT, formatDateToDisplay);
        }
        return mapping.findForward(Constants.VIEW_ASSET_REPORT);
    }
    
    public final ActionForward searchSummaryInventoryReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            List<SummaryInventoryReport> listInventoryReport = null;
//            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
//            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);

            int currentPage = 1;
            if (request.getParameter("page") != null)
            {
                currentPage = Integer.parseInt(request.getParameter("page").toString());
            }
            int rowOnPage = Constants.RECORDS_ON_PAGE;
   
            
            OrderServices searchSummaryInventoryReport = new OrderServices();
            listInventoryReport = searchSummaryInventoryReport.listSearchInventoryReport(currentPage, rowOnPage);
            
            int rowCount = searchSummaryInventoryReport.getTotalRecord();
            int totalPage = (rowCount / rowOnPage) + ((rowCount % rowOnPage) > 0 ? 1 : 0);
            currentPage =  (totalPage == 0) ?  0 : currentPage;
            
            String total = searchSummaryInventoryReport.viewInventoryReport();
            request.setAttribute(Constants.LIST_SEARCH_SUMMARY_INVENTORY_REPORT, listInventoryReport);
            request.setAttribute(Constants.VIEW_TOTAL_CREDIT_REPORT_ORDER, total);
//            request.setAttribute(Constants.DATAPICKER_FROM, datepickerFrom);
//            request.setAttribute(Constants.DATAPICKER_TO, datepickerTo);
            
            //Attribute Paging
            request.setAttribute(Constants.ATTR_PRODUCT_ROW_COUNT, rowCount);
            request.setAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE, totalPage);
            request.setAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE, currentPage);
            request.setAttribute(Constants.ATTR_ROW_ON_PAGE, rowOnPage);
            
        }
        return mapping.findForward(Constants.LIST_SEARCH_SUMMARY_INVENTORY_REPORT);
    }
    
    public final ActionForward searchAssetReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            List<SoldAssetReport> listAssetReport = null;
            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);
            String dateType = request.getParameter(Constants.ASSETDATETYPE);

            int currentPage = 1;
            if (request.getParameter("page") != null)
            {
                currentPage = Integer.parseInt(request.getParameter("page").toString());
            }
            int rowOnPage = Constants.RECORDS_ON_PAGE;
   
            
            OrderServices searchAssetReport = new OrderServices();
            listAssetReport = searchAssetReport.listSearchAssetReport(datepickerFrom, datepickerTo, currentPage, rowOnPage, dateType);
            
            int rowCount = searchAssetReport.getTotalRecord();
            int totalPage = (rowCount / rowOnPage) + ((rowCount % rowOnPage) > 0 ? 1 : 0);
            currentPage =  (totalPage == 0) ?  0 : currentPage;
            
            //String total = searchAssetReport.viewAssetReport(datepickerFrom, datepickerTo);
            request.setAttribute(Constants.LIST_SEARCH_ASSET_REPORT, listAssetReport);
            //request.setAttribute(Constants.VIEW_TOTAL_CREDIT_REPORT_ORDER, total);
            request.setAttribute(Constants.DATAPICKER_FROM, datepickerFrom);
            request.setAttribute(Constants.DATAPICKER_TO, datepickerTo);
            request.setAttribute(Constants.ASSETDATETYPE, dateType);
            
            //Attribute Paging
            request.setAttribute(Constants.ATTR_PRODUCT_ROW_COUNT, rowCount);
            request.setAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE, totalPage);
            request.setAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE, currentPage);
            request.setAttribute(Constants.ATTR_ROW_ON_PAGE, rowOnPage);
            
        }
        return mapping.findForward(Constants.LIST_SEARCH_ASSET_REPORT);
    }

    public final ActionForward searchCreditReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            List<CreditReportOrder> listCreditReportOrder = null;
            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);

            int currentPage = 1;
            if (request.getParameter("page") != null)
            {
                currentPage = Integer.parseInt(request.getParameter("page").toString());
            }
            int rowOnPage = Constants.RECORDS_ON_PAGE;
   
            
            OrderServices searchCreditReport = new OrderServices();
            listCreditReportOrder = searchCreditReport.listSearchReportOrder(datepickerFrom, datepickerTo, currentPage, rowOnPage);
            
            int rowCount = searchCreditReport.getTotalRecord();
            int totalPage = (rowCount / rowOnPage) + ((rowCount % rowOnPage) > 0 ? 1 : 0);
            currentPage =  (totalPage == 0) ?  0 : currentPage;
            
            String total = searchCreditReport.viewCreditReport(datepickerFrom, datepickerTo);
            request.setAttribute(Constants.LIST_SEARCH_CREDIT_REPORT_ORDER, listCreditReportOrder);
            request.setAttribute(Constants.VIEW_TOTAL_CREDIT_REPORT_ORDER, total);
            request.setAttribute(Constants.DATAPICKER_FROM, datepickerFrom);
            request.setAttribute(Constants.DATAPICKER_TO, datepickerTo);
            
            //Attribute Paging
            request.setAttribute(Constants.ATTR_PRODUCT_ROW_COUNT, rowCount);
            request.setAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE, totalPage);
            request.setAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE, currentPage);
            request.setAttribute(Constants.ATTR_ROW_ON_PAGE, rowOnPage);
            
        }
        return mapping.findForward(Constants.LIST_SEARCH_CREDIT_REPORT_ORDER);
    }

    public final ActionForward listDiscountPercentage(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        if (session.getAttribute(Constants.IS_CUSTOMER) == null)
        {
            DiscountAdjustment listDiscountAdjustment = null;
            OrderServices discountAdjustment = new OrderServices();
            listDiscountAdjustment = discountAdjustment.listDiscountPercentage();
            request.setAttribute(Constants.LIST_ORDER_LIST_DISCOUNT, listDiscountAdjustment);
        }
        return mapping.findForward(Constants.LIST_ORDER_LIST_DISCOUNT);
    }

    public final ActionForward yearOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        int year = Constants.I_YEAR();
        OrderServices service = new OrderServices();
        if (request.getParameter(Constants.ORDER_YEAR_PARAM) != null)
        {
            year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
            forward = Constants.ORDER_YEAR_FORWARD;
        }
        else
            forward = Constants.ORDER_DATE_FORWARD;

        Map<Integer, OrderDate> mapYear = service.mapYear(year);
        request.setAttribute(Constants.ORDER_YEAR_PARAM, year);
        request.setAttribute(Constants.ORDER_YEAR_RESULT, mapYear);
        return mapping.findForward(forward);
    }

    public final ActionForward agentReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        OrderServices services = new OrderServices();
        List<String> listVis = services.getVisSize();
        List<String> listDocking = services.getDockingStation();
        List<String> listPart = services.getBrandtypePart();
        List<String> listserver = services.getBrandServer();
        List<String> listLaptop = services.getBrandLaptop();
        List<String> listTablet = services.getBrandTablet();
        List<String> listDesktop = services.getBrandDesktop();
        List<String> listWorkstation = services.getBrandWorkstation();
        List<String> listWarranty = services.getBrandWarranty();
        List<String> listCosmetic = services.getCosmeticGrade();
        request.setAttribute(Constants.ORDER_AGENT_REPORT_VIS, listVis);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandPart, listPart);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_DOCKING, listDocking);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandServer, listserver);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandLaptop, listLaptop);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandTablet, listTablet);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandDesktop, listDesktop);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandWorkstation, listWorkstation);
        request.setAttribute(Constants.ORDER_AGENT_REPORT_BrandWarranty, listWarranty);
        //get data column cometic grade in table agent_report
        request.setAttribute(Constants.ORDER_AGENT_REPORT_COSMETIC, listCosmetic);
        return mapping.findForward(Constants.FORWARD_AGENT_REPORT);
    }

    public final ActionForward showProcessor(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        OrderServices services = new OrderServices();
        String brandType = request.getParameter(Constants.BRANDTYPE);
        String catType = request.getParameter(Constants.CATTYPE);
        String date1 = request.getParameter(Constants.DATE1);
        String date2 = request.getParameter(Constants.DATE2) + " 23:59:59";
        // catType = catType.substring(1, catType.length() - 1);
        if (brandType == null)
        {
            brandType = "N/A";
        }
        //  brandType = brandType.substring(1, brandType.length() - 1);

        List<String> list = services.showProcessor(brandType, catType, date1, date2);
        request.setAttribute(Constants.SHOW_PROCESSOR_RESULT, list);
        return mapping.findForward(Constants.SHOW_PROCESSOR);
    }

    public final ActionForward showModel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        OrderServices services = new OrderServices();
        String brandType = request.getParameter(Constants.BRANDTYPE);
        String catType = request.getParameter(Constants.CATTYPE);
        String date1 = request.getParameter(Constants.DATE1);
        String date2 = request.getParameter(Constants.DATE2) + " 23:59:59";
        String procsellist = request.getParameter(Constants.PROSELL_LIST);

        List<String> list = services.showModel(brandType, catType, date1, date2, procsellist);
        request.setAttribute(Constants.SHOW_MODEL_RESULT, list);
        return mapping.findForward(Constants.SHOW_MODEL);
    }

    public final ActionForward showSummaryReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        // String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
        // if (userLevel != null && !userLevel.equals("C"))
        {
            String catid = request.getParameter(Constants.CATID);
            String ostype = request.getParameter(Constants.OSTYPE);
            String cosmetic = request.getParameter(Constants.COSMETIC);
            String brandtype = request.getParameter(Constants.BRANDTYPE);
            String model = request.getParameter(Constants.MODEL);
            String date1 = request.getParameter(Constants.DATE1);
            String date2 = request.getParameter(Constants.DATE2) + " 23:59:59";
            String proctype = request.getParameter(Constants.PROCTYPE);
            OrderServices services = new OrderServices();
            //services.updateReportAdminUsers();
            List<OrderSummary> list = services.getOrderSummray(catid, ostype, cosmetic, brandtype, model, date1, date2, proctype);
            List<Summary> listAgent = services.showAgentReport(catid, ostype, cosmetic, brandtype, model, date1, date2, proctype);
            request.setAttribute(Constants.ORDER_REPORT_SUMMARY_RESULT, list);
            request.setAttribute(Constants.ORDER_REPORT_SUMMARY_AGENT_RESULT, listAgent);
            request.setAttribute(Constants.CATID, catid);
            request.setAttribute(Constants.OSTYPE, ostype);
            request.setAttribute(Constants.COSMETIC, cosmetic);
            request.setAttribute(Constants.BRANDTYPE, brandtype);
            request.setAttribute(Constants.MODEL, model);
            request.setAttribute(Constants.DATE1, date1);
            request.setAttribute(Constants.DATE2, date2.substring(0, 10));
            request.setAttribute(Constants.PROCTYPE, proctype);
        }
        return mapping.findForward(Constants.SHOW_REPORT);
    }

    public final ActionForward monthOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_MONTH_FORWARD;
        int year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
        int month = Integer.valueOf(request.getParameter(Constants.ORDER_MONTH_PARAM));
        OrderServices service = new OrderServices();
        Map<Integer, OrderDate> mapMonth = service.mapMonth(year, month);
        request.setAttribute(Constants.ORDER_YEAR_PARAM, year);
        request.setAttribute(Constants.ORDER_MONTH_PARAM, month);
        request.setAttribute(Constants.ORDER_MONTH_RESULT, mapMonth);
        return mapping.findForward(forward);
    }

    public final ActionForward dayOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DAY_FORWARD;
        int year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
        int day = Integer.valueOf(request.getParameter(Constants.ORDER_DAY_PARAM));
        int month = Integer.valueOf(request.getParameter(Constants.ORDER_MONTH_PARAM).trim());

        OrderServices service = new OrderServices();
        //int totalDay = service.totalOrderDay(year, month, day);
        List<Order> mapDay = service.mapOrderDay(year, month, day, 1);
        int totalDay = service.getTotalRecord();

        request.setAttribute(Constants.ORDER_DAY_RESULT, mapDay);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        request.setAttribute(Constants.ORDER_TOTAL, totalDay);
        request.setAttribute(Constants.ORDER_YEAR_PARAM, year);
        request.setAttribute(Constants.ORDER_MONTH_PARAM, month);
        request.setAttribute(Constants.ORDER_DAY_PARAM, day);
        return mapping.findForward(forward);
    }

    public final ActionForward movingDay(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DAY_FORWARD;
        OrderServices service = new OrderServices();

        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        int year = Integer.valueOf(request.getParameter(Constants.ORDER_YEAR_PARAM));
        int day = Integer.valueOf(request.getParameter(Constants.ORDER_DAY_PARAM).trim());
        int month = Integer.valueOf(request.getParameter(Constants.ORDER_MONTH_PARAM).trim());
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        List<Order> mapDay = service.mapOrderDay(year, month, day, noPage);

        request.setAttribute(Constants.ORDER_DAY_RESULT, mapDay);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_YEAR_PARAM, year);
        request.setAttribute(Constants.ORDER_MONTH_PARAM, month);
        request.setAttribute(Constants.ORDER_DAY_PARAM, day);
        return mapping.findForward(forward);
    }

    public final ActionForward orderViewPending(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        //String strStatus="";
        try
        {

            forward = Constants.ORDER_VIEW_PENDING;
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            if (userLevel != null)
            {
                String order_id = request.getParameter("order_id");
                OrderServices order = new OrderServices();
                OrderViewPending listOrderViewPending = order.viewOrderViewPending1(order_id);
                String AgentIDEnter = listOrderViewPending.getAgentIDEnter();
                OrderViewPending listOverViewPendingQuery1 = order.viewOrderViewPendingQuery(AgentIDEnter);
                String TitleOrderNumber = order.viewPendingOrderQueryTitle(order_id);
                List<OrderViewPending> listFollowOrderNumber = new ArrayList<OrderViewPending>();
                listFollowOrderNumber = order.viewOrderViewPendingQuery5(order_id);
                String FraudDetail = "";
                String showAccountType = "";
                FraudDetail = Constants.convertValueEmpty(order.viewOrderViewPendingQuery3(order_id));
                String shop_id = listOrderViewPending.getShopper_id();
                String account_type = order.viewOrderViewPendingQuery4(shop_id);
                //strStatus=listOrderViewPending.getOrderStatus();
                if (account_type != null)
                {
                    if (account_type.equals("R"))
                    {
                        showAccountType = "Reseller";
                    }
                    else if (account_type.equals("C"))
                    {
                        showAccountType = "Commercial";
                    }
                    else if (account_type.equals("E"))
                    {
                        showAccountType = "Education";
                    }
                    else if (account_type.equals("I"))
                    {
                        showAccountType = "Consumer/Individual";
                    }
                    else if (account_type.equals("G"))
                    {
                        showAccountType = "Government";
                    }
                    else if (account_type.equals("F"))
                    {
                        showAccountType = "Faith/Religious Organization";
                    }
                    else
                    {
                        showAccountType = "Not Available";

                    }

                }

                String shortShipping = "";
                int shipmethod = Integer.parseInt(listOrderViewPending.getShip_method());
                if (shipmethod == 0)
                {
                    shortShipping = "Other: " + listOrderViewPending.getShip_terms();
                }
                else
                {
                    OrderViewPending shortOrderViewPending = (OrderViewPending) order.viewOrderViewPendingQuery2(listOrderViewPending.getShip_method());
                    shortShipping = shortOrderViewPending.getDescription();

                }
                
                
                request.setAttribute(Constants.ATTR_ORDER_VIEW_PENDING_AGENT_IDENTER, listOverViewPendingQuery1);
                request.setAttribute(Constants.ATTR_ORDER_VIEW_PENDING, listOrderViewPending);
                request.setAttribute(Constants.SHORT_SHIPPING, shortShipping);
                request.setAttribute(Constants.SHOW_ACCOUNT_TYPE, showAccountType);
                request.setAttribute(Constants.LIST_FOLLOW_ORDER_NUMBER, listFollowOrderNumber);
                request.setAttribute(Constants.FRAUD_DETAIL, FraudDetail);
                request.setAttribute(Constants.TITLE_ORDER_NUMBER, TitleOrderNumber);
                request.setAttribute(Constants.ORDER_ID_PENDING, order_id);

                if (listOrderViewPending.getShopper_id() != null && !listOrderViewPending.getShopper_id().isEmpty())
                {
                	
                    CustomerServices customerServices = new CustomerServices();
                    Customer customer = customerServices.getCustomerByShopperID(listOrderViewPending.getShopper_id());
                    /* Do comment
                    if (listOrderViewPending.getAgentIDEnter() != null && Constants.OTHER_SALE_TYPE.contains(";" + listOrderViewPending.getAgentIDEnter() + ";"))
                    {
                        AuthenticationService authenticationService = new AuthenticationService();
                        Agent agent = authenticationService.getAgentByAgentId(Integer.parseInt(listOrderViewPending.getAgentIDEnter()));
                        request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);

                    }
                    else if (customer.getAgent_id() > 0)
                    {
                        AuthenticationService authenticationService = new AuthenticationService();
                        Agent agent = authenticationService.getAgentByAgentId(customer.getAgent_id());
                        request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);

                    }
					*/
                    
                    if (listOrderViewPending.getByAgent() !=null && listOrderViewPending.getByAgent()==0)//orders come from COT
                    {
                        AuthenticationService authenticationService = new AuthenticationService();
                        Agent agent = authenticationService.getAgentByAgentId(customer.getAgent_id());
                        request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);
                    }
                    else if (listOrderViewPending.getAgentIDEnter()!=null)
	                {
                    	AuthenticationService authenticationService = new AuthenticationService();
	                    Agent agent = authenticationService.getAgentByAgentId(Integer.parseInt(listOrderViewPending.getAgentIDEnter()));
	                    request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);
	                }
                    
                    request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                }
            }
            Object isCustomer = request.getAttribute(Constants.IS_CUSTOMER);
            String ordernumber = request.getParameter("order_id");
            String agentclear = request.getParameter("agentname");
            boolean b = false ;
            if(isCustomer==null || !((Boolean)isCustomer).booleanValue() ){
            	if(agentclear == null || agentclear =="") {
            		
                	System.out.println(b);
            	}else{
            		OrderServices order = new OrderServices();
            		System.out.println(ordernumber);
                	b = order.saveOrderClear(ordernumber, agentclear);
                	
            	}
                
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
        
        return mapping.findForward(forward);
    }

    public final ActionForward agentOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        request.setAttribute("backPage", request.getParameter("backPage"));
        return mapping.findForward(Constants.ORDER_AGENT_FORWARD);
    }

    public final ActionForward getBackAgentbyDate(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();

        request.setAttribute(Constants.ORDER_AGENT_DATE_RESULT, session.getAttribute(Constants.ORDER_AGENT_DATE_RESULT));
        request.setAttribute(Constants.DATE1, session.getAttribute(Constants.DATE1));
        request.setAttribute(Constants.DATE2, session.getAttribute(Constants.DATE2));

        return mapping.findForward(Constants.ORDER_AGENT_DATE_FORWARD);
    }

    public final ActionForward getAgentbyDate(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();

        OrderServices service = new OrderServices();
        String date1 = request.getParameter(Constants.DATE1);
        String date2 = request.getParameter(Constants.DATE2);
        date2 = date2 + " 23:59:59";
        int totalRecord = service.countAgents(date1, date2);
        List<OrderAgent> listAgents = new ArrayList<OrderAgent>();
        listAgents = service.getOrderByAgent(date1, date2);

        request.setAttribute(Constants.ORDER_AGENT_DATE_RESULT, listAgents);
        request.setAttribute(Constants.DATE1, date1);
        request.setAttribute(Constants.DATE2, date2);

        session.setAttribute(Constants.ORDER_AGENT_DATE_RESULT, listAgents);
        session.setAttribute(Constants.DATE1, date1);
        session.setAttribute(Constants.DATE2, date2);

        return mapping.findForward(Constants.ORDER_AGENT_DATE_FORWARD);
    }

    public final ActionForward detailAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();

        OrderServices service = new OrderServices();
        List<OrderAgentDetail> listOrders = new ArrayList<OrderAgentDetail>();

        String date1 = (String) request.getParameter(Constants.DATE1);
        String date2 = (String) request.getParameter(Constants.DATE2);

        String agentId = (String) request.getParameter(Constants.ORDER_AGENT_ID);
        String agentName = (String) request.getParameter(Constants.ORDER_AGENT_NAME);

        int totalRecord = 0;

        if (request.getParameter("back") != null)
        {
            agentId = (String) session.getAttribute(Constants.ORDER_AGENT_ID);
            date1 = (String) session.getAttribute(Constants.DATE1);
            date2 = (String) session.getAttribute(Constants.DATE2);
            agentName = (String) session.getAttribute(Constants.ORDER_AGENT_NAME);
            listOrders = (List<OrderAgentDetail>) session.getAttribute(Constants.ORDER_AGENT_ORDER_RESULT);
            totalRecord = (Integer) session.getAttribute(Constants.ORDER_TOTAL);
        }
        else
        {

            listOrders = service.getAgentDetails(date1, date2, agentId, 1);
            totalRecord = service.getTotalRecord();

            session.setAttribute(Constants.ORDER_AGENT_ID, agentId);
            session.setAttribute(Constants.DATE1, date1);
            session.setAttribute(Constants.DATE2, date2);
            session.setAttribute(Constants.ORDER_AGENT_NAME, agentName);
            session.setAttribute(Constants.ORDER_AGENT_ORDER_RESULT, listOrders);
            session.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            session.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        }

        request.setAttribute(Constants.ORDER_AGENT_ID, agentId);
        request.setAttribute(Constants.DATE1, date1);
        request.setAttribute(Constants.DATE2, date2);
        request.setAttribute(Constants.ORDER_AGENT_NAME, agentName);
        request.setAttribute(Constants.ORDER_AGENT_ORDER_RESULT, listOrders);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);

        return mapping.findForward(Constants.ORDER_AGENT_ORDER_FORWARD);
    }

    public final ActionForward movingAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();

        OrderServices service = new OrderServices();
        String date1 = (String) request.getParameter(Constants.DATE1);
        String date2 = (String) request.getParameter(Constants.DATE2);
        String agentId = (String) request.getParameter(Constants.ORDER_AGENT_ID);
        String agentName = (String) request.getParameter(Constants.ORDER_AGENT_NAME);
        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        List<OrderAgentDetail> listOrders = new ArrayList<OrderAgentDetail>();

        listOrders = service.getAgentDetails(date1, date2, agentId, noPage);
        request.setAttribute(Constants.DATE1, date1);
        request.setAttribute(Constants.DATE2, date2);
        request.setAttribute(Constants.ORDER_AGENT_ID, agentId);
        request.setAttribute(Constants.ORDER_AGENT_NAME, agentName);
        request.setAttribute(Constants.ORDER_AGENT_ORDER_RESULT, listOrders);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);

        session.setAttribute(Constants.ORDER_AGENT_ID, agentId);
        session.setAttribute(Constants.DATE1, date1);
        session.setAttribute(Constants.DATE2, date2);
        session.setAttribute(Constants.ORDER_AGENT_NAME, agentName);
        session.setAttribute(Constants.ORDER_AGENT_ORDER_RESULT, listOrders);
        session.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        session.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);

        return mapping.findForward(Constants.ORDER_AGENT_ORDER_FORWARD_PAGING);
    }

    public final ActionForward updateOrderStatus(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {

        String orderNumber = request.getParameter("orderNumber");
        OrderServices service = new OrderServices();
        service.updateOrderStatus(orderNumber);
        return null;
    }

    public final ActionForward getShopperOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String shopperId = request.getParameter("shopperId");
        HttpSession session = request.getSession();
        OrderServices service = new OrderServices();
        List<OrderShopper> listShopperDetail = service.getShopperDetail(shopperId);
        List<Order> list = service.getOrderbyShopper(shopperId, 1);
        int totalRecord = service.getTotalRecord();
        session.setAttribute(Constants.ORDER_SHOPPER_ID, shopperId);
        request.setAttribute(Constants.ORDER_BY_SHOPPER_RESULT, list);
        session.setAttribute(Constants.ORDER_SHOPPER_DETAIL, listShopperDetail);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        return mapping.findForward(Constants.ORDER_BY_SHOPPER_FORWARD);
    }

    public final ActionForward movingShopperOrder(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        OrderServices service = new OrderServices();
        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String shopperId = (String) session.getAttribute(Constants.ORDER_SHOPPER_ID);
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }
        List<Order> list = service.getOrderbyShopper(shopperId, noPage);

        request.setAttribute(Constants.ORDER_BY_SHOPPER_RESULT, list);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        return mapping.findForward(Constants.ORDER_BY_SHOPPER_AJAX_FORWARD);
    }

    public ActionForward exportExcelCreditReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_CREDIT_REPORT_ORDER;
        HttpSession session = request.getSession();
        //   HSSFCellStyle styleCurrencyFormat = null;

        try
        {
            List<CreditReportOrder> listCreditReportOrder = null;
            OrderServices searchCreditReport = new OrderServices();
            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);
            listCreditReportOrder = searchCreditReport.listSearchReportOrder(datepickerFrom, datepickerTo,0,0);
            String total = "";
            float totalTotal = 0;
            total = searchCreditReport.viewCreditReport(datepickerFrom, datepickerTo);
            if (total != null)
            {
                totalTotal = Float.parseFloat(total);
            }
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);

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

            Row row1 = sheet.createRow((short) 1);
            Cell cel0Row1 = row1.createCell((short) 0);
            cel0Row1.setCellValue("Credit Report");
            cel0Row1.setCellStyle(cellStyleWrapTextBold);

            Row row3 = sheet.createRow((short) 3);
            Cell cel0Row3 = row3.createCell((short) 0);
            cel0Row3.setCellValue("Total Credit:" + Constants.FormatCurrency(totalTotal));
            cel0Row3.setCellStyle(cellStyleWrapTextBold);

            Row row4 = sheet.createRow((short) 4);
            Cell cel0Row4 = row4.createCell((short) 0);
            cel0Row4.setCellValue("Name");
            cel0Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel1Row4 = row4.createCell((short) 1);
            cel1Row4.setCellValue("Order Number");
            cel1Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel2Row4 = row4.createCell((short) 2);
            cel2Row4.setCellValue("Service Tag");
            cel2Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel3Row4 = row4.createCell((short) 3);
            cel3Row4.setCellValue("Channel");
            cel3Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel4Row4 = row4.createCell((short) 4);
            cel4Row4.setCellValue("Ship_to_State");
            cel4Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel5Row4 = row4.createCell((short) 5);
            cel5Row4.setCellValue("Transaction Type");
            cel5Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel6Row4 = row4.createCell((short) 6);
            cel6Row4.setCellValue("Credit Date");
            cel6Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel7Row4 = row4.createCell((short) 7);
            cel7Row4.setCellValue("Ordered Date");
            cel7Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);

            Cell cel8Row4 = row4.createCell((short) 8);
            cel8Row4.setCellValue("Amount Credit");
            cel8Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);

            Cell cel9Row4 = row4.createCell((short) 9);
            cel9Row4.setCellValue("Sales Price");
            cel9Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);
            
            Cell cel10Row4 = row4.createCell((short) 10);
            cel10Row4.setCellValue("Mfg Part Number");
            cel10Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);
            
            Cell cel11Row4 = row4.createCell((short) 11);
            cel11Row4.setCellValue("Cosmetic");
            cel11Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);
            
            Cell cel12Row4 = row4.createCell((short) 12);
            cel12Row4.setCellValue("Short Description");
            cel12Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);
            
            Cell cel13Row4 = row4.createCell((short) 13);
            cel13Row4.setCellValue("Category ID");
            cel13Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel14Row4 = row4.createCell((short) 14);
            cel14Row4.setCellValue("Tax Amt");
            cel14Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel15Row4 = row4.createCell((short) 15);
            cel15Row4.setCellValue("Enviro Fee");
            cel15Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel16Row4 = row4.createCell((short) 16);
            cel16Row4.setCellValue("Freight");
            cel16Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel17Row4 = row4.createCell((short) 17);
            cel17Row4.setCellValue("Discount");
            cel17Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel18Row4 = row4.createCell((short) 18);
            cel18Row4.setCellValue("Total Order");
            cel18Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel19Row4 = row4.createCell((short) 19);
            cel19Row4.setCellValue("Bill Email");
            cel19Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel20Row4 = row4.createCell((short) 20);
            cel20Row4.setCellValue("Ship Email");
            cel20Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel21Row4 = row4.createCell((short) 21);
            cel21Row4.setCellValue("Credit Type");
            cel21Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel22Row4 = row4.createCell((short) 22);
            cel22Row4.setCellValue("Total Items");
            cel22Row4.setCellStyle(cellStyleWrapTextBold);

            if (listCreditReportOrder.size() > 0)
            {
                int rowIdx = 5;
                for (CreditReportOrder order : listCreditReportOrder)
                {
                    String createdate = order.getCredit_date().substring(0, 10);
                    createdate = createdate.replaceAll("-", "/");
                    String year = createdate.substring(0, 4);
                    String month = createdate.substring(5, 7);
                    String day = createdate.substring(8, 10);
                    String dateView = month + "/" + day + "/" + year;
                    
                    String ordereddate = order.getOrdered_date().substring(0, 10);
                    ordereddate = ordereddate.replaceAll("-", "/");
                    String or_year = ordereddate.substring(0, 4);
                    String or_month = ordereddate.substring(5, 7);
                    String or_day = ordereddate.substring(8, 10);
                    String or_dateView = or_month + "/" + or_day + "/" + or_year;
                    
                    
                    Row rowBasket = sheet.createRow((short) rowIdx);
                    
                    rowBasket.createCell(0).setCellValue(order.getContact_name());
                    
                    //rowBasket.createCell(1).setCellValue(order.getOrdernumber());
                    rowBasket.createCell(1).setCellValue(Double.parseDouble(order.getOrdernumber()));
                    rowBasket.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);                    
                    rowBasket.createCell(2).setCellValue(order.getItem_sku());                    
                    rowBasket.createCell(3).setCellValue(order.getAccount());                    
                    rowBasket.createCell(4).setCellValue(order.getShip_to_state());                    
                    rowBasket.createCell(5).setCellValue(order.getReason());                    
                    rowBasket.createCell(6).setCellValue(dateView);
                    rowBasket.createCell(7).setCellValue(or_dateView);
                    
                    //rowBasket.createCell(6).setCellValue(Constants.FormatCurrency(new Float(order.getAmount())));
                    rowBasket.createCell(8).setCellValue(Double.parseDouble(order.getAmount()));
                    rowBasket.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    rowBasket.getCell(8).setCellStyle(styleCurrencyFormat);
                    //rowBasket.getCell(6).setCellStyle(cellStyleAlignRight);
                    
                    rowBasket.createCell(9).setCellValue(Double.parseDouble(order.getSales_price())/100);
                    rowBasket.getCell(9).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    rowBasket.getCell(9).setCellStyle(styleCurrencyFormat);
                    
                    rowBasket.createCell(10).setCellValue(order.getMfg_part_number1());
                    rowBasket.createCell(11).setCellValue(order.getCosmetic1());
                    rowBasket.createCell(12).setCellValue(order.getShort_description1());
                    
                    String statusCat2 = "" + order.getCat2();
                    if (statusCat2.equals(""))
                    {
                        rowBasket.createCell(13).setCellValue(order.getCat2());
                    }
                    else
                    {
                        rowBasket.createCell(13).setCellValue(order.getCat1());
                    }
                    
                    rowBasket.createCell(14).setCellValue(Double.parseDouble(order.getTax_amt()));
                    rowBasket.createCell(15).setCellValue(Double.parseDouble(order.getRF_amt()));
                    rowBasket.createCell(16).setCellValue(Double.parseDouble(order.getShip_amt()));
                    rowBasket.createCell(17).setCellValue(Double.parseDouble(order.getDiscount_amt()));
                    rowBasket.createCell(18).setCellValue(Double.parseDouble(order.getTotal_amt()));
                    rowBasket.createCell(19).setCellValue(order.getBill_email());
                    rowBasket.createCell(20).setCellValue(order.getShip_email());
                    rowBasket.createCell(21).setCellValue(order.getCC_type());
                    rowBasket.createCell(22).setCellValue(Double.parseDouble(order.getTotal_item()));

                    rowIdx++;
                }
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                sheet.autoSizeColumn(9);
                sheet.autoSizeColumn(10);
                sheet.autoSizeColumn(11);
                sheet.autoSizeColumn(12);
                sheet.autoSizeColumn(13);
                sheet.autoSizeColumn(14);
                sheet.autoSizeColumn(15);
                sheet.autoSizeColumn(16);
                sheet.autoSizeColumn(17);
                sheet.autoSizeColumn(18);
                sheet.autoSizeColumn(19);
                sheet.autoSizeColumn(20);
                sheet.autoSizeColumn(21);
                sheet.autoSizeColumn(22);
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "CreditReport");
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        return mapping.findForward(forward);
    }
    
    public ActionForward exportExcelAssetReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_ASSET_REPORT_ORDER;
        //HttpSession session = request.getSession();
        //   HSSFCellStyle styleCurrencyFormat = null;

        try
        {
            List<SoldAssetReport> listAssetReportOrder = null;
            OrderServices searchAssetReport = new OrderServices();
            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);
            String dateTypeRpt = request.getParameter(Constants.ASSETDATETYPE);
            listAssetReportOrder = searchAssetReport.listSearchAssetReport(datepickerFrom, datepickerTo,0,0,dateTypeRpt);
            //String total = "";
            //float totalTotal = 0;
            //total = searchAssetReport.viewCreditReport(datepickerFrom, datepickerTo);
            //if (total != null)
            //{
            //    totalTotal = Float.parseFloat(total);
            //}
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);

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

            Row row0 = sheet.createRow((short) 0);
            Cell cel0Row0 = row0.createCell((short) 0);
            cel0Row0.setCellValue("Sold Asset Report");
            cel0Row0.setCellStyle(cellStyleWrapTextBold);
            
            Row row1 = sheet.createRow((short) 1);
            Cell cel0Row1 = row1.createCell((short) 0);
            if (dateTypeRpt.trim().equals("exporteddate")){
            	cel0Row1.setCellValue("Report By OOR Export Date");
            } else
            {
            	cel0Row1.setCellValue("Report By Order Date");
            }
            cel0Row1.setCellStyle(cellStyleWrapTextBold);
            
            Row row2 = sheet.createRow((short) 2);
            Cell cel0Row2 = row2.createCell((short) 0);
            cel0Row2.setCellValue("Date Range:");
            cel0Row2.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel1Row2 = row2.createCell((short) 1);
            cel1Row2.setCellValue(datepickerFrom);
            cel1Row2.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel2Row2 = row2.createCell((short) 2);
            cel2Row2.setCellValue("To");
            cel2Row2.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel3Row2 = row2.createCell((short) 3);
            cel3Row2.setCellValue(datepickerTo);
            cel3Row2.setCellStyle(cellStyleWrapTextBold);
            
          
            //Row row3 = sheet.createRow((short) 3);
            //Cell cel0Row3 = row3.createCell((short) 0);
            //cel0Row3.setCellValue("Total Credit:" + Constants.FormatCurrency(totalTotal));
            //cel0Row3.setCellStyle(cellStyleWrapTextBold);

            Row row4 = sheet.createRow((short) 4);
            Cell cel0Row4 = row4.createCell((short) 0);
            cel0Row4.setCellValue("OOR Export Date");
            cel0Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel1Row4 = row4.createCell((short) 1);
            cel1Row4.setCellValue("Serial Number");
            cel1Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel2Row4 = row4.createCell((short) 2);
            cel2Row4.setCellValue("Item Status");
            cel2Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel3Row4 = row4.createCell((short) 3);
            cel3Row4.setCellValue("Status Change Date");
            cel3Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel4Row4 = row4.createCell((short) 4);
            cel4Row4.setCellValue("Order Number");
            cel4Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel5Row4 = row4.createCell((short) 5);
            cel5Row4.setCellValue("Order Date");
            cel5Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel6Row4 = row4.createCell((short) 6);
            cel6Row4.setCellValue("Order Total");
            cel6Row4.setCellStyle(cellStyleWrapTextBold);

            Cell cel7Row4 = row4.createCell((short) 7);
            cel7Row4.setCellValue("Order Tax");
            cel7Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);

            Cell cel8Row4 = row4.createCell((short) 8);
            cel8Row4.setCellValue("Order Shipping Cost");
            cel8Row4.setCellStyle(cellStyleWrapTextBoldAlignRight);
            
            Cell cel9Row4 = row4.createCell((short) 9);
            cel9Row4.setCellValue("Sold Price");
            cel9Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel10Row4 = row4.createCell((short) 10);
            cel10Row4.setCellValue("Modifed Price");
            cel10Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel11Row4 = row4.createCell((short) 11);
            cel11Row4.setCellValue("Category Number");
            cel11Row4.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel12Row4 = row4.createCell((short) 12);
            cel12Row4.setCellValue("Description");
            cel12Row4.setCellStyle(cellStyleWrapTextBold);

            if (listAssetReportOrder.size() > 0)
            {
                int rowIdx = 5;
                for (SoldAssetReport order : listAssetReportOrder)
                {
                    String createdate = order.getOrder_date().substring(0, 10);
                    createdate = createdate.replaceAll("-", "/");
                    String year = createdate.substring(0, 4);
                    String month = createdate.substring(5, 7);
                    String day = createdate.substring(8, 10);
                    String dateView = month + "/" + day + "/" + year;
                    Row rowBasket = sheet.createRow((short) rowIdx);
                    
                    rowBasket.createCell(0).setCellValue(order.getExported_date());
                    
                    rowBasket.createCell(1).setCellValue(order.getItem_sku());
                    //rowBasket.createCell(1).setCellValue(Double.parseDouble(order.getOrder_date()));
                    //rowBasket.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    rowBasket.createCell(2).setCellValue(order.getItem_status());
                    rowBasket.createCell(3).setCellValue(order.getItem_status_date());
                    
                    rowBasket.createCell(4).setCellValue(order.getOrder_number());
                    
                    rowBasket.createCell(5).setCellValue(order.getOrder_date());
                    
                    rowBasket.createCell(6).setCellValue(Double.parseDouble(order.getOrder_total()));
                    rowBasket.getCell(6).setCellStyle(styleCurrencyFormat);
                    
                    rowBasket.createCell(7).setCellValue(Double.parseDouble(order.getTax_total()));
                    rowBasket.getCell(7).setCellStyle(styleCurrencyFormat);
                    
                    rowBasket.createCell(8).setCellValue(Double.parseDouble(order.getShip_total()));
                    rowBasket.getCell(8).setCellStyle(styleCurrencyFormat);
                    
                    rowBasket.createCell(9).setCellValue(Double.parseDouble(order.getSold_price()));
                    rowBasket.getCell(9).setCellStyle(styleCurrencyFormat);
                    
                    rowBasket.createCell(10).setCellValue(Double.parseDouble(order.getModified_price()));
                    rowBasket.getCell(10).setCellStyle(styleCurrencyFormat);
                    
                    rowBasket.createCell(11).setCellValue(order.getCategory_id());
                    rowBasket.createCell(12).setCellValue(order.getDescription());
                    
                    //rowBasket.createCell(6).setCellValue(Constants.FormatCurrency(new Float(order.getAmount())));
                    //rowBasket.createCell(7).setCellValue(Double.parseDouble(order.getOrder_date()));
                    //rowBasket.getCell(7).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //rowBasket.getCell(7).setCellStyle(styleCurrencyFormat);
                    //rowBasket.getCell(6).setCellStyle(cellStyleAlignRight);
                    
                    //rowBasket.createCell(8).setCellValue(Double.parseDouble(order.getOrder_date())/100);
                    //rowBasket.getCell(8).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    //rowBasket.getCell(8).setCellStyle(styleCurrencyFormat);
                    

                    rowIdx++;
                }
                sheet.autoSizeColumn(0);
                sheet.autoSizeColumn(1);
                sheet.autoSizeColumn(2);
                sheet.autoSizeColumn(3);
                sheet.autoSizeColumn(4);
                sheet.autoSizeColumn(5);
                sheet.autoSizeColumn(6);
                sheet.autoSizeColumn(7);
                sheet.autoSizeColumn(8);
                sheet.autoSizeColumn(9);
                sheet.autoSizeColumn(10);
                sheet.autoSizeColumn(11);
            }
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "Sold Asset Report");
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        return mapping.findForward(forward);
    }

    public ActionForward exportExcelInventoryReport(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forward = Constants.SHOW_PRINT_INVENTORY_REPORT_ORDER;

        try
        {
            List<SummaryInventoryReport> listInventoryReportOrder = null;
            OrderServices searchInventoryReport = new OrderServices();
//            String datepickerFrom = request.getParameter(Constants.DATAPICKER_FROM);
//            String datepickerTo = request.getParameter(Constants.DATAPICKER_TO);
            listInventoryReportOrder = searchInventoryReport.listSearchInventoryReport(0,0);
          
            HSSFWorkbook wb = new HSSFWorkbook();
            HSSFSheet sheet = wb.createSheet();
            sheet.setDefaultColumnWidth(17);

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


            Row row0 = sheet.createRow((short) 0);

            Cell cel2Row0 = row0.createCell((short) 0);
            cel2Row0.setCellValue("Short Description");
            cel2Row0.setCellStyle(cellStyleWrapTextBold);

            Cell cel3Row0 = row0.createCell((short) 1);
            cel3Row0.setCellValue("Count");
            cel3Row0.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel4Row0 = row0.createCell((short) 2);
            cel4Row0.setCellValue("List Price");
            cel4Row0.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel5Row0 = row0.createCell((short) 3);
            cel5Row0.setCellValue("40% of List");
            cel5Row0.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel6Row0 = row0.createCell((short) 4);
            cel6Row0.setCellValue("50% of List");
            cel6Row0.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel7Row0 = row0.createCell((short) 5);
            cel7Row0.setCellValue("60% of List");
            cel7Row0.setCellStyle(cellStyleWrapTextBold);
            
            Cell cel8Row0 = row0.createCell((short) 6);
            cel8Row0.setCellValue("Item Status");
            cel8Row0.setCellStyle(cellStyleWrapTextBold);
            

            if (listInventoryReportOrder.size() > 0)
            {
                int rowIdx = 1;
                for (SummaryInventoryReport order : listInventoryReportOrder)
                {
                    Row rowBasket = sheet.createRow((short) rowIdx);
                    
                    rowBasket.createCell(0).setCellValue(order.getshort_description());
                    sheet.autoSizeColumn(0);
                    
                    rowBasket.createCell(1).setCellValue(order.getitem_count());
                    rowBasket.getCell(1).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    sheet.autoSizeColumn(1);
                    
                    rowBasket.createCell(2).setCellValue(order.getList_price());
                    rowBasket.getCell(2).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    sheet.autoSizeColumn(2);
                    
                    rowBasket.createCell(3).setCellValue(order.getList_price40());
                    rowBasket.getCell(3).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    sheet.autoSizeColumn(3);
                    
                    rowBasket.createCell(4).setCellValue(order.getList_price50());
                    rowBasket.getCell(4).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    sheet.autoSizeColumn(4);
                    
                    rowBasket.createCell(5).setCellValue(order.getList_price60());
                    rowBasket.getCell(5).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
                    sheet.autoSizeColumn(5);
                    
                    rowBasket.createCell(6).setCellValue(order.getitem_status());
                    sheet.autoSizeColumn(6);
                    rowIdx++;
                }
            }
            
            request.setAttribute("ExcelContent", wb);
            request.setAttribute("ExcelName", "InventoryReport");
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        return mapping.findForward(forward);
    }
    
    public final ActionForward orderViewPendingSearch(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        try
        {
            forward = Constants.ORDER_VIEW_PENDING_SEARCH;
            String order_id = request.getParameter("order_id");
            OrderServices order = new OrderServices();
            OrderViewPending listOrderViewPending = order.viewOrderViewPending1(order_id);
            String AgentIDEnter = listOrderViewPending.getAgentIDEnter();
            OrderViewPending listOverViewPendingQuery1 = order.viewOrderViewPendingQuery(AgentIDEnter);
            String TitleOrderNumber = order.viewPendingOrderQueryTitle(order_id);
            List<OrderViewPending> listFollowOrderNumber = new ArrayList<OrderViewPending>();
            listFollowOrderNumber = order.viewOrderViewPendingQuery5(order_id);
            String FraudDetail = "";
            String showAccountType = "";
            FraudDetail = Constants.convertValueEmpty(order.viewOrderViewPendingQuery3(order_id));
            String shop_id = listOrderViewPending.getShopper_id();
            String account_type = order.viewOrderViewPendingQuery4(shop_id);
            if (account_type != null)
            {
                if (account_type.equals("R"))
                {
                    showAccountType = "Reseller";
                }
                else if (account_type.equals("C"))
                {
                    showAccountType = "Commercial";
                }
                else if (account_type.equals("E"))
                {
                    showAccountType = "Education";
                }
                else if (account_type.equals("I"))
                {
                    showAccountType = "Consumer/Individual";
                }
                else if (account_type.equals("G"))
                {
                    showAccountType = "Government";
                }
                else if (account_type.equals("F"))
                {
                    showAccountType = "Faith/Religious Organization";
                }
                else
                {
                    showAccountType = "Not Available";
                }

            }
            String shortShipping = "";
            int shipmethod = Integer.parseInt(listOrderViewPending.getShip_method());
            if (shipmethod == 0)
            {
                shortShipping = "Other: " + listOrderViewPending.getShip_terms();
            }
            else
            {
                OrderViewPending shortOrderViewPending = (OrderViewPending) order.viewOrderViewPendingQuery2(listOrderViewPending.getShip_method());
                shortShipping = shortOrderViewPending.getDescription();

            }

            request.setAttribute(Constants.ATTR_ORDER_VIEW_PENDING_AGENT_IDENTER, listOverViewPendingQuery1);
            request.setAttribute(Constants.ATTR_ORDER_VIEW_PENDING, listOrderViewPending);
            request.setAttribute(Constants.SHORT_SHIPPING, shortShipping);
            request.setAttribute(Constants.SHOW_ACCOUNT_TYPE, showAccountType);
            request.setAttribute(Constants.LIST_FOLLOW_ORDER_NUMBER, listFollowOrderNumber);
            request.setAttribute(Constants.FRAUD_DETAIL, FraudDetail);
            request.setAttribute(Constants.TITLE_ORDER_NUMBER, TitleOrderNumber);
            request.setAttribute(Constants.ORDER_ID_PENDING, order_id);

            if (listOrderViewPending.getShopper_id() != null && !listOrderViewPending.getShopper_id().isEmpty())
            {
                CustomerServices customerServices = new CustomerServices();
                Customer customer = customerServices.getCustomerByShopperID(listOrderViewPending.getShopper_id());
                //System.out.println(listOrderViewPending.getAgentIDEnter());
                /* Do comment 00:42 14/May
                if (listOrderViewPending.getAgentIDEnter() != null && Constants.OTHER_SALE_TYPE.contains(";" + listOrderViewPending.getAgentIDEnter() + ";"))
                {
                    AuthenticationService authenticationService = new AuthenticationService();
                    Agent agent = authenticationService.getAgentByAgentId(Integer.parseInt(listOrderViewPending.getAgentIDEnter()));
                    request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);

                }
                else if (customer.getAgent_id() > 0)
                {
                    AuthenticationService authenticationService = new AuthenticationService();
                    Agent agent = authenticationService.getAgentByAgentId(customer.getAgent_id());
                    request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);
                }
                */
                //Do add 00:43 15/May
                  
                if (listOrderViewPending.getByAgent() !=null && listOrderViewPending.getByAgent()==0)//orders come from COT
                {
                    AuthenticationService authenticationService = new AuthenticationService();
                    Agent agent = authenticationService.getAgentByAgentId(customer.getAgent_id());
                    request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);              	
                }
                else if (listOrderViewPending.getAgentIDEnter()!=null)
                {
                	
	                AuthenticationService authenticationService = new AuthenticationService();
	                Agent agent = authenticationService.getAgentByAgentId(Integer.parseInt(listOrderViewPending.getAgentIDEnter()));
	                request.setAttribute(Constants.ATTR_AGENT_OF_CUSTOMER, agent);
                }
                
              //Do add 00:43 15/May
                
                request.setAttribute(Constants.ATTR_CUSTOMER, customer);
            }
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        return mapping.findForward(forward);
    }

    public final ActionForward orderViewPendingSearchExportExcel(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
        throws Exception
    {
        String forward = "";

        try
        {
            forward = Constants.ORDER_VIEW_PENDING_SEARCH;

            String order_id = request.getParameter("order_id");
            OrderServices order = new OrderServices();
            OrderViewPending listOrderViewPending = order.viewOrderViewPending1(order_id);
            String AgentIDEnter = listOrderViewPending.getAgentIDEnter();
            OrderViewPending listOverViewPendingQuery1 = order.viewOrderViewPendingQuery(AgentIDEnter);
            String TitleOrderNumber = order.viewPendingOrderQueryTitle(order_id);
            List<OrderViewPending> listFollowOrderNumber = new ArrayList<OrderViewPending>();
            listFollowOrderNumber = order.viewOrderViewPendingQuery5(order_id);
            String FraudDetail = "";
            String showAccountType = "";
            FraudDetail = Constants.convertValueEmpty(order.viewOrderViewPendingQuery3(order_id));
            String shop_id = listOrderViewPending.getShopper_id();
            String account_type = order.viewOrderViewPendingQuery4(shop_id);
            if (account_type != null)
            {
                if (account_type.equals("R"))
                {
                    showAccountType = "Reseller";
                }
                else if (account_type.equals("C"))
                {
                    showAccountType = "Commercial";
                }
                else if (account_type.equals("E"))
                {
                    showAccountType = "Education";
                }
                else if (account_type.equals("I"))
                {
                    showAccountType = "Consumer/Individual";
                }
                else if (account_type.equals("G"))
                {
                    showAccountType = "Government";
                }
                else if (account_type.equals("F"))
                {
                    showAccountType = "Faith/Religious Organization";
                }
                else
                {
                    showAccountType = "Not Available";

                }

            }
            String shortShipping = "";
            int shipmethod = Integer.parseInt(listOrderViewPending.getShip_method());
            if (shipmethod == 0)
            {
                shortShipping = "Other: " + listOrderViewPending.getShip_terms();
            }
            else
            {
                OrderViewPending shortOrderViewPending = (OrderViewPending) order.viewOrderViewPendingQuery2(listOrderViewPending.getShip_method());
                shortShipping = shortOrderViewPending.getDescription();

            }

            if (listOrderViewPending != null)
            {
                String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
                int i = Integer.parseInt(listOrderViewPending.getAgentIDEnter());
                if (userLevel != null && !userLevel.equals("C"))
                {
                    Workbook wb = new HSSFWorkbook();
                    Sheet sheet = wb.createSheet();
                    sheet.setDefaultColumnWidth(17);
                    CellStyle cellStyleBold = wb.createCellStyle();
                    HSSFFont font = (HSSFFont) wb.createFont();
                    font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
                    cellStyleBold.setFont(font);

                    CellStyle cs = wb.createCellStyle();
                    cs.setWrapText(true);
                    //  CellStyle style = wb.createCellStyle();    

                }

            }

            request.setAttribute(Constants.ATTR_ORDER_VIEW_PENDING_AGENT_IDENTER, listOverViewPendingQuery1);
            request.setAttribute(Constants.ATTR_ORDER_VIEW_PENDING, listOrderViewPending);
            request.setAttribute(Constants.SHORT_SHIPPING, shortShipping);
            request.setAttribute(Constants.SHOW_ACCOUNT_TYPE, showAccountType);
            request.setAttribute(Constants.LIST_FOLLOW_ORDER_NUMBER, listFollowOrderNumber);
            request.setAttribute(Constants.FRAUD_DETAIL, FraudDetail);
            request.setAttribute(Constants.TITLE_ORDER_NUMBER, TitleOrderNumber);
            request.setAttribute(Constants.ORDER_ID_PENDING, order_id);
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }

        return mapping.findForward(forward);
    }

    public final ActionForward searchCustomerOrderOutsite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        HttpSession session = request.getSession();
        String forward = Constants.ORDER_DB_CUSTOMER_RESULT_OUTSITE;
        if (session.getAttribute(Constants.IS_CUSTOMER) != null)
        {
            OrderServices service = new OrderServices();
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
            searchOrder.add(Constants.AGENT_STORE_ID);

            List<Order> mapOrder = service.searchOrderCriteria(1, searchOrder);
            int totalRecord = 0;
            if (!mapOrder.isEmpty())
            {
                Order order = mapOrder.get(0);
                totalRecord = order.getTotalRow();
            }
            session.setAttribute(Constants.ORDER_CUSTOMER_CRITERIA, searchOrder);
            request.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        }
        return mapping.findForward(forward);
    }

    @SuppressWarnings("unchecked")
    public final ActionForward movingCustomerOrderOutsite(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = Constants.ORDER_DB_CUSTOMER_RESULT_OUTSITE_PAGGING;
        OrderServices service = new OrderServices();
        HttpSession session = request.getSession();
        List<String> searchOrder = (List<String>) session.getAttribute(Constants.ORDER_CUSTOMER_CRITERIA);

        int noPage = Integer.valueOf(request.getParameter(Constants.ORDER_NUMBER_PAGE));
        int totalRecord = Integer.valueOf(request.getParameter(Constants.ORDER_TOTAL));
        String caseMove = request.getParameter(Constants.MOVING_PAGING);
        if (caseMove.equals(Constants.MOVING_FIRST))
        {
            noPage = 1;
        }
        if (caseMove.equals(Constants.MOVING_PREV))
        {
            noPage = noPage - 1;
        }
        if (caseMove.equals(Constants.MOVING_NEXT))
        {
            noPage = noPage + 1;
        }
        if (caseMove.equals(Constants.MOVING_LAST))
        {
            noPage = (totalRecord % orderRecord > 0) ? (totalRecord / orderRecord + 1) : (totalRecord / orderRecord);
        }

        List<Order> mapOrder = service.searchOrderCriteria(noPage, searchOrder);

        request.setAttribute(Constants.ORDER_LIST_RESULT, mapOrder);
        request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
        request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
        return mapping.findForward(forward);
    }
}
