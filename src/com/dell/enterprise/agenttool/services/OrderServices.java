/*
 * FILENAME
 *     OrderServices.java
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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.dell.enterprise.agenttool.DAO.OrderDAO;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.CreditReportOrder;
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
import com.dell.enterprise.agenttool.model.Summary;
import com.dell.enterprise.agenttool.model.SummaryInventoryReport;
import com.dell.enterprise.agenttool.model.SoldAssetReport;
//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * 
 * @author linhdo
 * 
 * @version $Id$
 **/
public class OrderServices
{
    private int totalRecord = 0;

    public List<Order> searchOrderCriteria(final int pageRecord, final List<String> searchCriteria) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.searchOrderCriteria(pageRecord, searchCriteria);
    }

    public List<OrderHeld> orderHelpCustomerMap(final int pageRecord, final String shopperNum) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<OrderHeld> listOrderHeld = dao.orderHeldCustomerMap(pageRecord, shopperNum);
        this.setTotalRecord(dao.getTotalRecord());
        return listOrderHeld;
    }

    public List<OrderHeld> orderHelpMap(final int pageRecord, Agent agent, String type) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<OrderHeld> listOrderHeld = dao.orderHeldMap(pageRecord, agent, type);
        this.setTotalRecord(dao.getTotalRecord());
        return listOrderHeld;
    }

    public BigDecimal totalCustomerOrderHeld(final String shopperNum) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.totalCustomerOrderHeld(shopperNum);
    }

    public BigDecimal totalOrderHeld(Agent agent, String type) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.totalOrderHeld(agent, type);
    }

    public int countHeldCustomerOrder(final String shopperNum) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countHeldCustomerOrder(shopperNum);
    }

    public int countHeldOrder(Agent agent, String type) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countHeldOrder(agent, type);
    }

    public List<OrderPending> listAllOrderPending(int page) throws Exception
    {

        OrderDAO dao = new OrderDAO();
        List<OrderPending> listOrder = dao.listAllOrderPending(page);
        this.setTotalRecord(dao.getTotalRecord());
        return listOrder;

    }

    public List<OrderPending> listAllOrderPendingExport() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.listAllOrderPendingExport();

    }

    public int totalShopper() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countAllShoppers();
    }

    public void updateDiscountAdjustment(final int percdiscount, int expirationDays) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        dao.updateDiscountAdjustment(percdiscount, expirationDays);

    }

    /*
     * public List<OrderShopper> listShopper(final int page) throws Exception {
     * OrderDAO dao = new OrderDAO(); return dao.listAllShoppers(page); }
     */
    public List<OrderShopper> searchOrdersByShopper(final int page, final OrderCriteria criteria) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<OrderShopper> list = dao.searchOrdersByShopper(page, criteria);
        this.setTotalRecord(dao.getTotalRecord());
        return list;

    }

    /*
     * public int totalShopperFilter(final OrderCriteria criteria) throws
     * Exception { OrderDAO dao = new OrderDAO(); return
     * dao.countShoppers(criteria); }
     */
    public List<CreditReportOrder> listSearchReportOrder(final String fromDay, final String toDay, final int selectedPage, final int pageSize) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<CreditReportOrder> listSearchReportOrder  = dao.searchCreditReport(fromDay, toDay,selectedPage,pageSize);
        this.setTotalRecord(dao.getTotalRecord());
        return listSearchReportOrder;
    }
    
    public List<SoldAssetReport> listSearchAssetReport(final String fromDay, final String toDay, final int selectedPage, final int pageSize, final String dateType) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<SoldAssetReport> listSearchAssetReport  = dao.searchAssetReport(fromDay, toDay,selectedPage,pageSize, dateType);
        this.setTotalRecord(dao.getTotalRecord());
        return listSearchAssetReport;
    }

    public String viewCreditReport(final String fromDay, final String toDay) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewCreditReport(fromDay, toDay);
    }
    
    public String viewAssetReport(final String fromDay, final String toDay) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewAssetReport(fromDay, toDay);
    }

    public List<SummaryInventoryReport> listSearchInventoryReport(final int selectedPage, final int pageSize) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<SummaryInventoryReport> listSearchInventoryReport  = dao.searchInventoryReport(selectedPage,pageSize);
        this.setTotalRecord(dao.getTotalRecord());
        return listSearchInventoryReport;
    }
    
    public String viewInventoryReport() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewInventoryReport();
    }
    
    public DiscountAdjustment listDiscountPercentage() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.listDiscountPercentage();
    }

    public Map<Integer, OrderDate> mapYear(final int year) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.mapYear(year);
    }

    public List<OrderSummary> getOrderSummray(final String catid, final String ostype, final String cosmetic, final String brandtype, final String model, final String date1, final String date2, final String proctype)
        throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getOrderSummray(catid, ostype, cosmetic, brandtype, model, date1, date2, proctype);
    }

    public List<Summary> showAgentReport(String catid, String ostype, String cosmetic, String brandtype, String model, String date1, String date2, String proctype) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getOrder4AgentSummray(catid, ostype, cosmetic, brandtype, model, date1, date2, proctype);
    }

    public Map<Integer, OrderDate> mapMonth(final int year, final int month) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.mapMonth(year, month);
    }

    public int totalOrderDay(final int year, final int month, final int day) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countOrderDay(year, month, day);
    }

    public List<Order> mapOrderDay(final int year, final int month, final int day, final int page) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<Order> listOrder = dao.listDay(year, month, day, page);
        this.setTotalRecord(dao.getTotalRecord());
        return listOrder;
    }

    public List<String> showProcessor(String brandType, String catType, String date1, String date2) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.showProcessor(brandType, catType, date1, date2);
    }

    public List<String> showModel(String brandtype, String catType, String date1, String date2, String procsellist)
    {
        OrderDAO dao = new OrderDAO();
        return dao.showModel(brandtype, catType, date1, date2, procsellist);
    }

    public OrderViewPending viewOrderViewPending1(String order_id)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrder1(order_id);
    }

    public OrderViewPending viewOrderViewPendingQuery(String AgentIDEnter)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrderQuery1(AgentIDEnter);
    }

    public OrderViewPending viewOrderViewPendingQuery2(String shipid)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrderQuery2(shipid);
    }

    public String viewOrderViewPendingQuery3(String orderId)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrderQuery3(orderId);
    }

    public String viewOrderViewPendingQuery4(String shopId)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrderQuery4(shopId);
    }

    public List<OrderViewPending> viewOrderViewPendingQuery5(String orderId)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrderQuery5(orderId);
    }

    public int countAgents(String date1, String date2) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countAgents(date1, date2);
    }

    public List<OrderAgent> getOrderByAgent(final String date1, final String date2) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getOrderByAgent(date1, date2);
    }

    public String viewPendingOrderQueryTitle(String orderId)
    {
        OrderDAO dao = new OrderDAO();
        return dao.viewPendingOrderQueryTitle(orderId);
    }

    public int countAgentDetail(String date1, String date2, String agentId) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countAgentDetail(date1, date2, agentId);
    }

    public List<OrderAgentDetail> getAgentDetails(final String date1, final String date2, String agentId, final int page) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<OrderAgentDetail> listOrderAgentDetail = dao.getAgentDetail(date1, date2, agentId, page);
        this.setTotalRecord(dao.getTotalRecord());
        return listOrderAgentDetail;
    }

    public void getAgentDetails(final String orderNumber) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        dao.updateOrderStatus(orderNumber);

    }

    public void updateOrderStatus(final String orderNumber) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        dao.updateOrderStatus(orderNumber);

    }

    public int countOrderbyShopper(final String shopperId) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countOrderByShopper(shopperId);
    }

    public List<Order> getOrderbyShopper(final String shopperId, final int page) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        List<Order> list = dao.getOrderByShopper(shopperId, page);
        this.setTotalRecord(dao.getTotalRecord());
        return list;
    }

    public List<OrderShopper> getShopperDetail(final String shopperId) throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getShopperDetail(shopperId);
    }

    public List<String> getVisSize() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getVisSize();
    }
    
    public List<String> getBrandtypePart() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandtypePart();
    }
    
    public List<String> getBrandServer() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandServer();
    }
    
    public List<String> getBrandDesktop() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandDesktop();
    }
    
    public List<String> getBrandWorkstation() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandWorkstation();
    }
    
    public List<String> getBrandLaptop() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandLaptop();
    }
    
    public List<String> getBrandTablet() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandTablet();
    }

    public List<String> getBrandWarranty() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getBrandWarranty();
    }
    
    //get cosmetic grade in table agent report
    public List<String> getCosmeticGrade() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getCosmeticGrade();
    }

    public List<String> getDockingStation() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.getDockingStation();
    }

    public int countOrderPending() throws Exception
    {
        OrderDAO dao = new OrderDAO();
        return dao.countOrderPending();
    }
    
    public boolean saveOrderClear(String ordernumber,String agentclear){
    	OrderDAO dao = new OrderDAO();
    	System.out.println(agentclear);
    	return dao.saveOrderClearLog(ordernumber, agentclear);
    }

    /**
     * @param totalRecord
     *            the totalRecord to set
     */
    public void setTotalRecord(int totalRecord)
    {
        this.totalRecord = totalRecord;
    }

    /**
     * @return the totalRecord
     */
    public int getTotalRecord()
    {
        return totalRecord;
    }
/*
    public int updateReportAdminUsers()
    {
        OrderDAO dao = new OrderDAO();
        return dao.updateReportAdminUsers();
    }
*/
}
