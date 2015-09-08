/*
 * FILENAME
 *     OrderDAO.java
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

package com.dell.enterprise.agenttool.DAO;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Avg_mhz;
import com.dell.enterprise.agenttool.model.CreditReportOrder;
import com.dell.enterprise.agenttool.model.Desktop;
import com.dell.enterprise.agenttool.model.DiscountAdjustment;
import com.dell.enterprise.agenttool.model.NoteBook;
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
import com.dell.enterprise.agenttool.services.AgentService;
import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;
import com.dell.enterprise.agenttool.util.DAOUtils;
import com.dell.enterprise.agenttool.util.OrderDAOImpl;
import com.sun.xml.internal.bind.v2.TODO;
import com.dell.enterprise.agenttool.model.SummaryInventoryReport;
import com.dell.enterprise.agenttool.model.SoldAssetReport;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * 
 * @see TODO Related Information
 * 
 * @author linhdo
 * 
 * @version $Id$
 **/
public class OrderDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.OrderDAO");
    private Connection conn;
    private Statement stmt;
    private CallableStatement cstmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private int record = Constants.PAGE_RECORD;
    private int totalRecord = 0;

    /**
     * 
     * <p>
     * Get ASP MHZ OF NOTEBOOK
     * </p>
     * 
     * @throws Exception
     * 
     * @return NoteBook
     * 
     * @author linhdo
     * 
     */
    private NoteBook getASP_Mhz_NB(final Connection con, final int orderNumber)
    {
        NoteBook noteBook = new NoteBook(0, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            String sql1 = daoUtil.getString("sql.getAsp_mhz.fromOrder1");
            String sql2 = daoUtil.getString("sql.getAsp_mhz.fromOrder2");

            PreparedStatement pstm = con.prepareStatement(sql1);
            pstm.setInt(1, orderNumber);
            ResultSet res = pstm.executeQuery();

            if (res.next())
            {
                if (res.getInt(1) > 0)
                {
                    noteBook = new NoteBook(res.getInt(1), res.getBigDecimal(2), res.getBigDecimal(3));
                }
                else
                {
                    pstm = con.prepareStatement(sql2);
                    pstm.setInt(1, orderNumber);
                    res = pstm.executeQuery();
                    if (res.next())
                    {
                        if (res.getInt(1) > 0)
                        {
                            noteBook = new NoteBook(res.getInt(1), res.getBigDecimal(2), res.getBigDecimal(3));
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return noteBook;
    }

    /**
     * 
     * <p>
     * Get ASP MHZ OF DESKTOP
     * </p>
     * 
     * @throws Exception
     * 
     * @return DESKTOP
     * 
     * @author linhdo
     * 
     */

    private Desktop getASP_Mhz_DT(final Connection con, final int orderNumber)
    {
        Desktop desktop = new Desktop(0, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            String sql3 = daoUtil.getString("sql.getAsp_mhz.fromOrder3");
            String sql4 = daoUtil.getString("sql.getAsp_mhz.fromOrder4");

            PreparedStatement pstm = con.prepareStatement(sql3);
            pstm.setInt(1, orderNumber);
            ResultSet res = pstm.executeQuery();

            if (res.next())
            {
                if (res.getInt(1) > 0)
                {
                    desktop = new Desktop(res.getInt(1), res.getBigDecimal(2), res.getBigDecimal(3));
                }
                else
                {
                    pstm = con.prepareStatement(sql4);
                    pstm.setInt(1, orderNumber);
                    res = pstm.executeQuery();
                    if (res.next())
                    {
                        if (res.getInt(1) > 0)
                        {
                            desktop = new Desktop(res.getInt(1), res.getBigDecimal(2), res.getBigDecimal(3));
                        }
                    }
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return desktop;
    }

    /**
     * <p>
     * Get ASP MHZ OF DESKTOP
     * </p>
     * 
     * @throws Exception
     * 
     * @return DESKTOP
     * 
     * @author linhdo
     * 
     */
    private String getAgentName(final Connection con, final String shopperId, final short byAgent)
    {
        String agentName = "";
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            String sql = "";
            if (byAgent == 1)
            {
                sql = daoUtil.getString("sql.getAgentName.fromShopperID");
                PreparedStatement pstm = con.prepareStatement(sql);
                pstm.setString(1, shopperId);
                ResultSet res = pstm.executeQuery();

                if (res.next())
                {
                    agentName = res.getString(1);
                }
                else
                {
                    agentName = "";
                }
            }
            else
            {
                agentName = "Agent Store";
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return agentName;
    }

    /**
     * 
     * <p>
     * Count Total Record Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return Integer totalRecord
     * 
     * @author linhdo
     * 
     */
    private int total_Row(final Connection con, final String sql) throws Exception
    {
        int totalRecord = 0;
        try
        {
            LOGGER.info("Execute DAO Count");
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if (res.next())
            {
                totalRecord = res.getInt(1);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return totalRecord;
    }

    /**
     * 
     * <p>
     * Count Total Record Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return Integer totalRecord
     * 
     * @author linhdo
     * 
     */
    private int total_Row2(final Connection con, final String sql) throws Exception
    {
        int totalRecord = 0;
        try
        {
            LOGGER.info("Execute DAO Count");
            stmt = con.createStatement();
            LOGGER.info(sql);
            ResultSet res = stmt.executeQuery(sql);
            while (res.next())
            {
                totalRecord += 1;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return totalRecord;
    }

    /**
     * <p>
     * Search All Order
     * </p>
     * 
     * @param page
     *            page
     * @param criteria
     *            criteria
     * @throws Exception
     *             Exception
     * @return Map
     * 
     * @author linhdo
     * 
     */
    public List<Order> searchOrderCriteria(final int page, final List<String> criteria) throws Exception
    {
        List<Order> mapOrder = new ArrayList<Order>();
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String queryOrder = OrderDAOImpl.filterOrderQueryPage(criteria);
            String queryWhere = OrderDAOImpl.filterOrderQuery(queryOrder, criteria);
            System.out.println(queryOrder);
            System.out.println(queryWhere);
            
            String query = daoUtil.getString("sql.orderAll.paging.sp");
            
            cstmt = conn.prepareCall(query);
            
            cstmt.setString(1, queryOrder);
            cstmt.setInt(2, page);
            cstmt.setInt(3, record);
            cstmt.setString(4, queryWhere);
            
            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
            
            rs = cstmt.executeQuery();
            
            
            while (rs.next())
            {
                Order order = new Order();

                order.setId(rs.getInt(1));
                order.setOrderId(rs.getInt(2));

                NoteBook noteBook = new NoteBook(0, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
                noteBook = getASP_Mhz_NB(conn, order.getOrderId());
                order.setNote(noteBook);

                Desktop desktop = new Desktop(0, BigDecimal.valueOf(0), BigDecimal.valueOf(0));
                desktop = getASP_Mhz_DT(conn, order.getOrderId());
                order.setDesk(desktop);

                order.setShopNum(rs.getInt(4));
                order.setShip_to_name(rs.getString(5));

                order.setShip_to_company(rs.getString(6)); //Some case it Null
                order.setStatus(rs.getString(7));
                order.setShopId(rs.getString(8));
                order.setDayOrder(Converter.getDateString(rs.getDate(9)));
                order.setTotal_total(rs.getBigDecimal(10).setScale(2, RoundingMode.HALF_EVEN));
                order.setOadjust_total(rs.getBigDecimal(11).setScale(2, RoundingMode.HALF_EVEN));
                order.setFreight_total(rs.getBigDecimal(12).setScale(2, RoundingMode.HALF_EVEN));
                order.setDiscount(rs.getInt(13));
                order.setItem(rs.getInt(14));

                order.setAgentId(rs.getString(3));
                order.setByAgent(rs.getShort(15));

                String agentName = "";
                
                //DO comment 14/May
                /* 
                if (order.getAgentId() != null && Integer.parseInt(order.getAgentId()) > 0 && Constants.OTHER_SALE_TYPE.contains(";" + order.getAgentId() + ";"))
                {
                    AgentService agentService = new AgentService();
                    Agent agent = agentService.getAgent(Integer.parseInt(order.getAgentId()));
                    agentName = agent.getUserName();
                }
                else
                {
                    agentName = getAgentName(conn, order.getShopId(), order.getByAgent());
                }
				*/
                //DO add 14/May
                if (order.getByAgent()==1)
                {
	                //if (order.getAgentId() != null && Integer.parseInt(order.getAgentId()) > 0 )
                	if (order.getAgentId() != null)
	                {
	                	AgentService agentService = new AgentService();
	                	Agent agent = agentService.getAgent(Integer.parseInt(order.getAgentId()));
	                	agentName = agent.getUserName();
	                }
	                else
	                {
	                	agentName = "Unknown Sale Type";
	                }
                }
                else 
                {
                    agentName = "Agent Store";
                }
                
                //End of DO add 14/May
                
                order.setOrderType(agentName);
              
                mapOrder.add(order);
            }
            if (null != mapOrder && !mapOrder.isEmpty())
                mapOrder.get(0).setTotalRow(cstmt.getInt(5));

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return mapOrder;
    }

    /**
     * <p>
     * Search All Order Held
     * </p>
     * 
     * @param page
     *            page
     * @throws Exception
     *             Exception
     * @return Map
     * 
     * @author linhdo
     * 
     */
    public List<OrderHeld> orderHeldCustomerMap(final int page, final String shopperNum) throws Exception
    {
        List<OrderHeld> orderHeldMap = new ArrayList<OrderHeld>();
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            if (page > 0)
            {
                String query = daoUtil.getString("sql.order.customer.held.sp");
                cstmt = conn.prepareCall(query);
                cstmt.setString(1, shopperNum);
                cstmt.setInt(2, page);
                cstmt.setInt(3, record);
                cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

                rs = cstmt.executeQuery();

                while (rs.next())
                {
                    OrderHeld orderHeld = new OrderHeld();
                    orderHeld.setId(rs.getInt(1));
                    orderHeld.setShopId(rs.getString(2));
                    orderHeld.setHeld_order(rs.getString(3));
                    orderHeld.setOrderId(rs.getInt(4));
                    orderHeld.setDayOrder(Converter.getDateString(rs.getDate(5)));
                    orderHeld.setShip_to_name(rs.getString(6));
                    orderHeld.setShip_to_company(rs.getString(7));
                    orderHeld.setUser_hold(rs.getString(8));

                    Object[] object = totalItemShop(conn, orderHeld.getHeld_order());
                    orderHeld.setItem((Integer) object[0]);
                    orderHeld.setTotal_total((BigDecimal) object[1]);

                    Integer[] price = heldDiscount(conn, orderHeld.getHeld_order());
                    orderHeld.setPlace_price(price[0]);
                    orderHeld.setModi_price(price[1]);

                    Map<String, Avg_mhz> heldAvg_mhzMap = heldAvg_mhz(conn, String.valueOf(orderHeld.getOrderId()));
                    orderHeld.setAvgMhz(heldAvg_mhzMap.get(String.valueOf(orderHeld.getOrderId())));

                    orderHeldMap.add(orderHeld);

                }
                this.setTotalRecord(cstmt.getInt(4));

            }
            else
            {
                String query = daoUtil.getString("sql.order.customer.held.export");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperNum);

                LOGGER.info(query);
                rs = pstmt.executeQuery();
                int id = 1;
                while (rs.next())
                {
                    OrderHeld orderHeld = new OrderHeld();
                    orderHeld.setId(id);
                    orderHeld.setShopId(rs.getString(1));
                    orderHeld.setHeld_order(rs.getString(2));
                    orderHeld.setOrderId(rs.getInt(3));
                    orderHeld.setDayOrder(Converter.getDateString(rs.getDate(4)));
                    orderHeld.setShip_to_name(rs.getString(5));
                    orderHeld.setShip_to_company(rs.getString(6));
                    orderHeld.setUser_hold(rs.getString(7));

                    Object[] object = totalItemShop(conn, orderHeld.getHeld_order());
                    orderHeld.setItem((Integer) object[0]);
                    orderHeld.setTotal_total((BigDecimal) object[1]);

                    Integer[] price = heldDiscount(conn, orderHeld.getHeld_order());
                    orderHeld.setPlace_price(price[0]);
                    orderHeld.setModi_price(price[1]);

                    Map<String, Avg_mhz> heldAvg_mhzMap = heldAvg_mhz(conn, String.valueOf(orderHeld.getOrderId()));
                    orderHeld.setAvgMhz(heldAvg_mhzMap.get(String.valueOf(orderHeld.getOrderId())));

                    orderHeldMap.add(orderHeld);
                    id++;
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return orderHeldMap;
    }

    /**
     * <p>
     * Search All Order Held
     * </p>
     * 
     * @param page
     *            page
     * @throws Exception
     *             Exception
     * @return Map
     * 
     * @author linhdo
     * 
     */
    public List<OrderHeld> orderHeldMap(final int page, Agent agent, String type) throws Exception
    {
        List<OrderHeld> orderHeldMap = new ArrayList<OrderHeld>();
        try
        {
            LOGGER.info("Execute OrderDAO - function orderHeldMap");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String query = daoUtil.getString("sql.order.held.sp");
            cstmt = conn.prepareCall(query);
            cstmt.setInt(1, page);
            cstmt.setInt(2, record);

            if (agent.isAdmin() && (type.equals("AGENT") || type.equals("CUSTOMER")))
            {
                System.out.println("A");
                cstmt.setString(3, type);
                cstmt.setString(4, "");
            }
            else if (!agent.isAdmin() && type.equals("AGENT"))
            {
                System.out.println("B");
                cstmt.setString(3, "BYAGENT");
                cstmt.setString(4, Integer.toString(agent.getAgentId()));
            }
            else
            {
                System.out.println("C");
                cstmt.setString(3, "BYCUSTOMER");
                cstmt.setString(4, Integer.toString(agent.getAgentId()));
            }

            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                OrderHeld orderHeld = new OrderHeld();
                orderHeld.setId(rs.getInt(1));
                orderHeld.setShopId(rs.getString(2));
                orderHeld.setHeld_order(rs.getString(3));
                orderHeld.setOrderId(rs.getInt(4));
                orderHeld.setDayOrder(Converter.getDateString(rs.getDate(5)));
                orderHeld.setShip_to_name(rs.getString(6));
                orderHeld.setShip_to_company(rs.getString(7));
                orderHeld.setUser_hold((rs.getString(8) == null) ? "" : rs.getString(8));

                Object[] object = totalItemShop(conn, orderHeld.getHeld_order());
                orderHeld.setItem((Integer) object[0]);
                orderHeld.setTotal_total((BigDecimal) object[1]);

                Integer[] price = heldDiscount(conn, orderHeld.getHeld_order());
                orderHeld.setPlace_price(price[0]);
                orderHeld.setModi_price(price[1]);

                Map<String, Avg_mhz> heldAvg_mhzMap = heldAvg_mhz(conn, String.valueOf(orderHeld.getOrderId()));
                orderHeld.setAvgMhz(heldAvg_mhzMap.get(String.valueOf(orderHeld.getOrderId())));

                orderHeldMap.add(orderHeld);
            }
            this.setTotalRecord(cstmt.getInt(5));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return orderHeldMap;
    }

    /**
     * <p>
     * Count All Order Held
     * </p>
     * 
     * @return BigDecimal
     * 
     * @author linhdo
     * 
     */
    public BigDecimal totalCustomerOrderHeld(final String shopperNum) throws Exception
    {
        BigDecimal total = BigDecimal.valueOf(0);
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String queryTotal = daoUtil.getString("sql.order.customer.total.held");
            pstmt = conn.prepareStatement(queryTotal);
            pstmt.setString(1, shopperNum);
            rs = pstmt.executeQuery();
            if (rs.next())
                total = rs.getBigDecimal(1);

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        total = (total != null) ? total : BigDecimal.valueOf(0);
        return total.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_EVEN);
    }

    /**
     * <p>
     * Count All Order Held
     * </p>
     * 
     * @return BigDecimal
     * 
     * @author linhdo
     * 
     */
    public BigDecimal totalOrderHeld(Agent agent, String type) throws Exception
    {
        BigDecimal total = BigDecimal.valueOf(0);
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String query = "";
            if (agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.total.held.admin.agent");
                pstmt = conn.prepareStatement(query);

            }
            else if (agent.isAdmin() && type.equals("CUSTOMER"))
            {
                query = daoUtil.getString("sql.order.total.held.admin.customer");
                pstmt = conn.prepareStatement(query);

            }
            else if (!agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.total.held.agent");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, String.valueOf(agent.getAgentId()));
            }
            else
            {
                query = daoUtil.getString("sql.order.total.held.customer");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, String.valueOf(agent.getAgentId()));

            }

            System.out.println("Total query " + query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                total = rs.getBigDecimal(1);
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        total = (total == null) ? BigDecimal.valueOf(0) : total;
        return total.divide(BigDecimal.valueOf(100));
    }

    /**
     * <p>
     * Get Total Item buy order held
     * </p>
     * 
     * @param conn
     * @param held_order
     * @return Object[]
     * @author linhdo
     **/
    private Object[] totalItemShop(final Connection con, final String held_order)
    {
        Object[] object = new Object[2];
        try
        {
            LOGGER.info("GET ITEM n TOTAL 4 HELD ORDER");
            DAOUtils daoUtil = DAOUtils.getInstance();
            String query = daoUtil.getString("sql.order.held.totalsys");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, held_order);
            ResultSet res = pstmt.executeQuery();
            if (res.next())
            {
                object[0] = res.getInt(2);
                object[1] = res.getBigDecimal(3);
            }
            else
            {
                object[0] = 0;
                object[1] = BigDecimal.valueOf(0);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }

        return object;
    }

    /**
     * <p>
     * Get discount for Order Held
     * </p>
     * 
     * @param conn
     * @param held_order
     * @return Integer[]
     * @author linhdo
     **/
    private Integer[] heldDiscount(final Connection con, final String held_order)
    {
        Integer[] price = new Integer[2];
        try
        {
            LOGGER.info("GET DISCOUNT 4 HELD ORDER");
            DAOUtils daoUtil = DAOUtils.getInstance();
            String query = daoUtil.getString("sql.order.held.discount");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, held_order);
            ResultSet res = pstmt.executeQuery();
            if (res.next())
            {
                price[0] = res.getInt(2);
                price[1] = res.getInt(3);
            }
            else
            {
                price[0] = 0;
                price[1] = 0;
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }

        return price;
    }

    /**
     * <p>
     * Get Map Held Avg
     * </p>
     * 
     * @param conn
     * @param orderId
     * @return Map
     * @author linhdo
     **/
    private Map<String, Avg_mhz> heldAvg_mhz(final Connection con, final String orderId)
    {
        Map<String, Avg_mhz> heldAvg_mhzMap = new HashMap<String, Avg_mhz>();
        try
        {
            LOGGER.info("GET AVG_MHZ 4 HELD ORDER");
            DAOUtils daoUtil = DAOUtils.getInstance();
            String query = daoUtil.getString("sql.order.held.avghmz");
            pstmt = con.prepareStatement(query);
            pstmt.setString(1, orderId);
            LOGGER.info(query);
            ResultSet res = pstmt.executeQuery();
            if (res.next())
            {
                Avg_mhz avgMhz = new Avg_mhz();
                avgMhz.setOrder_number(res.getString(1));
                avgMhz.setAvgmhz(res.getString(2));
                avgMhz.setUnit_mhz(res.getInt(3));
                avgMhz.setSpeed_total(res.getInt(4));
                avgMhz.setTotal_price(res.getBigDecimal(5));
                heldAvg_mhzMap.put(avgMhz.getOrder_number(), avgMhz);
            }
            else
            {
                Avg_mhz avgMhz = new Avg_mhz();
                avgMhz.setOrder_number(orderId);
                avgMhz.setAvgmhz("N/A");
                avgMhz.setTotal_price(BigDecimal.valueOf(0));
                avgMhz.setUnit_mhz(0);
                avgMhz.setSpeed_total(0);
                heldAvg_mhzMap.put(avgMhz.getOrder_number(), avgMhz);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }

        return heldAvg_mhzMap;
    }

    /**
     * 
     * <p>
     * Count Total Record Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return Integer totalRecord
     * 
     * @author linhdo
     * 
     */
    public int countHeldCustomerOrder(final String shopperNum) throws Exception
    {
        int totalRecord = 0;
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String queryTotal = daoUtil.getString("sql.order.customer.held.count");
            pstmt = conn.prepareStatement(queryTotal);
            pstmt.setString(1, shopperNum);
            rs = pstmt.executeQuery();
            if (rs.next())
                totalRecord = rs.getInt(1);

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return totalRecord;
    }

    /**
     * 
     * <p>
     * Count Total Record Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return Integer totalRecord
     * 
     * @author linhdo
     * 
     */
    public int countHeldOrder(Agent agent, String type) throws Exception
    {
        int totalRecord = 0;
        try
        {
            LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = "";
            if (agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.held.agent.admin.count");
                pstmt = conn.prepareStatement(query);

            }
            else if (agent.isAdmin() && type.equals("CUSTOMER"))
            {
                query = daoUtil.getString("sql.order.held.agent.admin.customer.count");
                pstmt = conn.prepareStatement(query);

            }
            else if (!agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.held.agent.count");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, String.valueOf(agent.getAgentId()));
            }
            else
            {
                query = daoUtil.getString("sql.order.held.agent.customer.count");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, String.valueOf(agent.getAgentId()));
            }
            rs = pstmt.executeQuery();
            if (rs.next())
                totalRecord = rs.getInt(1);

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return totalRecord;
    }

    /**
     * 
     * <p>
     * Search pending Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public List<OrderPending> listAllOrderPendingExport()
    {
        List<OrderPending> listOrderPending = new ArrayList<OrderPending>();
        try
        {
            LOGGER.info("Execute ProductDAO : function listAllOrderPending");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.order.pending.export");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                OrderPending orderPending = new OrderPending();
                orderPending.setOrdernumber(rs.getString(1));
                orderPending.setShip_to_name(rs.getString(2));
                orderPending.setTotal_total_pending(rs.getString(3));
                orderPending.setUsername(rs.getString(4));
                orderPending.setCc_type(rs.getString(5));

                listOrderPending.add(orderPending);
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listOrderPending;
    }

    /**
     * 
     * <p>
     * Search pending Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public List<OrderPending> listAllOrderPending(int page)
    {
        List<OrderPending> listOrderPending = new ArrayList<OrderPending>();
        try
        {
            LOGGER.info("Execute ProductDAO : function listAllOrderPending");

            DAOUtils daoUtil = DAOUtils.getInstance();
            String sql = daoUtil.getString("search.order.pending");
            conn = daoUtil.getConnection();
            LOGGER.info("SQL : " + sql);
            cstmt = conn.prepareCall(sql);
            cstmt.setInt(1, page);
            cstmt.setInt(2, 40);
            cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                OrderPending orderPending = new OrderPending();
                orderPending.setOrdernumber(rs.getString(2));
                orderPending.setShip_to_name(rs.getString(3));
                orderPending.setTotal_total_pending(rs.getString(4));
                orderPending.setUsername(rs.getString(5));
                orderPending.setCc_type(rs.getString(6));
                listOrderPending.add(orderPending);
                LOGGER.info("Item Sku : " + orderPending.getOrdernumber());
            }
            this.setTotalRecord(cstmt.getInt(3));

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listOrderPending;
    }

    /**
     * <p>
     * Get List Shopper Order
     * </p>
     * 
     * @param page
     * @return List
     * @author linhdo
     **/
    public List<OrderShopper> searchOrdersByShopper(final int page, final OrderCriteria criteria)
    {
        List<OrderShopper> list = new ArrayList<OrderShopper>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.shopper.sp");
            String queryOrder = OrderDAOImpl.filterOrderShopper(criteria);
            String queryWhere = OrderDAOImpl.filterWhereShopper(criteria);
            cstmt = conn.prepareCall(query);
            cstmt.setString(1, queryOrder);
            cstmt.setInt(2, page);
            cstmt.setInt(3, record);
            cstmt.setString(4, queryWhere);
            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);

            rs = cstmt.executeQuery();
            while (rs.next())
            {
                OrderShopper orderShopper = new OrderShopper();
                orderShopper.setId(rs.getInt(1));
                orderShopper.setShopId(rs.getString(2));
                orderShopper.setShip_to_name(rs.getString(3));
                orderShopper.setNumOrder(rs.getInt(4));
                orderShopper.setTotal(rs.getBigDecimal(5));
                orderShopper.setAvg(rs.getBigDecimal(6));
                orderShopper.setMax(rs.getBigDecimal(7));
                orderShopper.setMin(rs.getBigDecimal(8));
                list.add(orderShopper);
            }
            this.setTotalRecord(cstmt.getInt(5));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    /**
     * <p>
     * Count Shopper
     * </p>
     * 
     * @return Integer
     * @throws Exception
     * @author linhdo
     **/
    public int countAllShoppers() throws Exception
    {
        int totalShopper = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.shopper.count");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next())
                totalShopper = rs.getInt(1);
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return totalShopper;
    }

    /**
     * 
     * <p>
     * Search pending Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public DiscountAdjustment listDiscountPercentage()
    {
        DiscountAdjustment discountAdjustment = null;
        try
        {
            LOGGER.info("Execute ProductDAO : function listDiscountPercentage");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("list.order.discount");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                discountAdjustment = new DiscountAdjustment();
                discountAdjustment.setPercdiscount(rs.getInt(1));
                discountAdjustment.setExpirationdate(rs.getInt(2));
                LOGGER.info("Discount Adjustment : " + discountAdjustment.getPercdiscount());
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return discountAdjustment;
    }

    /**
     * 
     * <p>
     * Search pending Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return void
     * 
     * @author thuynguyen
     * 
     */
    public Boolean updateDiscountAdjustment(final int percdiscount, int expirationDays)
    {

        Boolean flag = false;
        try
        {
            LOGGER.info("Execute OrderDAO : function updateDiscountAdjustment");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            String sql = daoUtil.getString("update.order.discount");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, percdiscount);
            pstmt.setInt(2, expirationDays);
            int a = pstmt.executeUpdate();
            pstmt = null;

            if (a > 0)
            {
                String sql1 = daoUtil.getString("update.order.discount.latperdiscount");
                LOGGER.info("SQL : " + sql1);
                pstmt = conn.prepareStatement(sql1);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                String sql2 = daoUtil.getString("update.order.discount.insperdiscount");
                LOGGER.info("SQL : " + sql2);
                pstmt = conn.prepareStatement(sql2);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                String sql3 = daoUtil.getString("update.order.discount.optperdiscount");
                LOGGER.info("SQL : " + sql3);
                pstmt = conn.prepareStatement(sql3);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                String sql4 = daoUtil.getString("update.order.discount.dimperdiscount");
                LOGGER.info("SQL : " + sql4);
                pstmt = conn.prepareStatement(sql4);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                String sql5 = daoUtil.getString("update.order.discount.monperdiscount");
                LOGGER.info("SQL : " + sql5);
                pstmt = conn.prepareStatement(sql5);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                String sql6 = daoUtil.getString("update.order.discount.serperdiscount");
                LOGGER.info("SQL : " + sql6);
                pstmt = conn.prepareStatement(sql6);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                String sql7 = daoUtil.getString("update.order.discount.worperdiscount");
                LOGGER.info("SQL : " + sql7);
                pstmt = conn.prepareStatement(sql7);
                pstmt.setInt(1, percdiscount);
                pstmt.setInt(2, percdiscount);
                pstmt.executeUpdate();
                pstmt = null;

                flag = true;
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Order DAO - function: Update Discount Adjustment");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (flag)
                {
                    conn.commit();
                }
                else
                {
                    conn.rollback();
                }
                conn.setAutoCommit(true);

                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                sqlE.getStackTrace();
            }
        }
        return flag;

    }

    /**
     * <p>
     * Count Shopper with criteria
     * </p>
     * 
     * @param criteria
     * @return Integer
     * @throws Exception
     * @author linhdo
     **/
    /*
     * public int countShoppers(final OrderCriteria criteria) throws Exception {
     * int totalShopper = 0; try { DAOUtils daoUtil = DAOUtils.getInstance();
     * conn = daoUtil.getConnection(); String query =
     * OrderDAOImpl.filterCountShopper(criteria); System.out.println(query);
     * stmt = conn.createStatement(); rs = stmt.executeQuery(query); if
     * (rs.next()) totalShopper = rs.getInt(1); } catch (Exception e) {
     * LOGGER.warning("ERROR Execute DAO"); e.printStackTrace(); } finally { try
     * { if (rs != null) rs.close(); if (stmt != null) stmt.close(); if (conn !=
     * null) conn.close(); } catch (SQLException sqlE) {
     * LOGGER.info("SQL error"); sqlE.printStackTrace(); } } return
     * totalShopper; }
     */
    /**
     * <p>
     * Get List Shoppers Order With Criteria
     * </p>
     * 
     * @param page
     * @param criteria
     * @return List
     * @author linhdo
     **/
    /*
     * public List<OrderShopper> listShoppers(final int page, final
     * OrderCriteria criteria) { List<OrderShopper> list = new
     * ArrayList<OrderShopper>(); try { DAOUtils daoUtil =
     * DAOUtils.getInstance(); conn = daoUtil.getConnection(); String query =
     * OrderDAOImpl.filterShopperQuery(criteria); pstmt =
     * conn.prepareStatement(query); pstmt.setInt(1, (page * record - record +
     * 1)); pstmt.setInt(2, page * record); System.out.print(query); rs =
     * pstmt.executeQuery(); int id = page * record - record + 1; while
     * (rs.next()) { OrderShopper orderShopper = new OrderShopper();
     * orderShopper.setId(id); orderShopper.setShip_to_name(rs.getString(2));
     * orderShopper.setShip_to_company(rs.getString(3));
     * orderShopper.setShopId(rs.getString(4));
     * orderShopper.setNumOrder(rs.getInt(5));
     * orderShopper.setTotal(rs.getBigDecimal(6));
     * orderShopper.setAvg(rs.getBigDecimal(7));
     * orderShopper.setMax(rs.getBigDecimal(8));
     * orderShopper.setMin(rs.getBigDecimal(9)); list.add(orderShopper); id++; }
     * } catch (Exception e) { LOGGER.warning("ERROR Execute DAO");
     * e.printStackTrace(); } finally { try { if (rs != null) rs.close(); if
     * (stmt != null) stmt.close(); if (conn != null) conn.close(); } catch
     * (SQLException sqlE) { LOGGER.info("SQL error"); sqlE.printStackTrace(); }
     * } return list; }
     */
    /**
     * 
     * <p>
     * Search Credit Report
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public List<CreditReportOrder> searchCreditReport(String dayFrom, String dayTo, int selectedPage, int pageSize)
    {
        List<CreditReportOrder> listCreditReportOrder = new ArrayList<CreditReportOrder>();
        try
        {
            LOGGER.info("Execute ProductDAO : function searchCreditReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.credit.report.order.sp");
            LOGGER.info("SQL : " + sql);
            dayFrom = dayFrom.replaceAll("/", "-");
            dayTo = dayTo.replaceAll("/", "-");
            String convertFromDay = "";
            String coverntToday = "";
            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
            dayTo += " 23:59:59";
            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
            java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);


            cstmt = conn.prepareCall(sql);
            cstmt.setTimestamp(1, fromdateTimestamp);
            cstmt.setTimestamp(2, todateTimestamp);
            cstmt.setInt(3, selectedPage);
            cstmt.setInt(4, pageSize);
            cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
            
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                CreditReportOrder creditReportOrder = new CreditReportOrder();
                creditReportOrder.setContact_name(Constants.convertValueEmpty(rs.getString(1)));
                creditReportOrder.setOrdernumber(Constants.convertValueEmpty(rs.getString(2)));
                creditReportOrder.setItem_sku(Constants.convertValueEmpty(rs.getString(3)));
                creditReportOrder.setAccount(Constants.convertValueEmpty(rs.getString(4)));
                creditReportOrder.setReason(Constants.convertValueEmpty(rs.getString(5)));
                creditReportOrder.setCredit_date(Constants.convertValueEmpty(rs.getString(6)));
                creditReportOrder.setAmount(Constants.convertValueEmpty(rs.getString(7)));
                
                if (Constants.convertValueEmpty(rs.getString(8)) != "")
                {
                	creditReportOrder.setCat1(Constants.convertValueEmpty(rs.getString(8)));
                	creditReportOrder.setMfg_part_number1(Constants.convertValueEmpty(rs.getString(12)));
                	creditReportOrder.setCosmetic1(Constants.convertValueEmpty(rs.getString(14)));
                	creditReportOrder.setShort_description1(Constants.convertValueEmpty(rs.getString(16)));
                }
                else
                {
                	creditReportOrder.setCat1(Constants.convertValueEmpty((rs.getString(9))));
                	creditReportOrder.setMfg_part_number1(Constants.convertValueEmpty(rs.getString(13)));
                	creditReportOrder.setCosmetic1(Constants.convertValueEmpty(rs.getString(15)));
                	creditReportOrder.setShort_description1(Constants.convertValueEmpty(rs.getString(17)));
                }
                
                creditReportOrder.setSales_price(Constants.convertValueEmpty((rs.getString(10))));
                creditReportOrder.setShip_to_state(Constants.convertValueEmpty((rs.getString(11))));
                creditReportOrder.setOrdered_date(Constants.convertValueEmpty((rs.getString(18))));
                
                creditReportOrder.setTax_amt(Constants.convertValueEmpty((rs.getString(19))));
                creditReportOrder.setRF_amt(Constants.convertValueEmpty((rs.getString(20))));
                creditReportOrder.setShip_amt(Constants.convertValueEmpty((rs.getString(21))));
                creditReportOrder.setDiscount_amt(Constants.convertValueEmpty((rs.getString(22))));
                creditReportOrder.setTotal_amt(Constants.convertValueEmpty((rs.getString(23))));
                creditReportOrder.setBill_email(Constants.convertValueEmpty((rs.getString(24))));
                creditReportOrder.setShip_email(Constants.convertValueEmpty((rs.getString(25))));
                creditReportOrder.setCC_type(Constants.convertValueEmpty((rs.getString(26))));
                creditReportOrder.setTotal_item(Constants.convertValueEmpty((rs.getString(27))));
                
                creditReportOrder.setId(rs.getLong(28));
                
                listCreditReportOrder.add(creditReportOrder);
                LOGGER.info("Discount Adjustment : " + creditReportOrder.getContact_name());
            }
            this.setTotalRecord(cstmt.getInt(5));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listCreditReportOrder;
    }
    
    public List<SoldAssetReport> searchAssetReport(String dayFrom, String dayTo, int selectedPage, int pageSize, String dateType)
    {
        List<SoldAssetReport> listAssetReport = new ArrayList<SoldAssetReport>();
        try
        {
            LOGGER.info("Execute ProductDAO : function searchAssetReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.asset.report.order.sp");
            LOGGER.info("SQL : " + sql);
            dayFrom = dayFrom.replaceAll("/", "-");
            dayTo = dayTo.replaceAll("/", "-");
            String convertFromDay = "";
            String coverntToday = "";
            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
            dayTo += " 23:59:59";
            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
            java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);


            cstmt = conn.prepareCall(sql);
            cstmt.setTimestamp(1, fromdateTimestamp);
            cstmt.setTimestamp(2, todateTimestamp);
            cstmt.setInt(3, selectedPage);
            cstmt.setInt(4, pageSize);
            cstmt.setString(5, dateType);
            cstmt.registerOutParameter(6, java.sql.Types.INTEGER);
            
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                SoldAssetReport soldAssetdReport = new SoldAssetReport();
                soldAssetdReport.setExported_date(Constants.convertValueEmpty(rs.getString(2)));
                soldAssetdReport.setItem_status(Constants.convertValueEmpty(rs.getString(4)));
                soldAssetdReport.setItem_sku(Constants.convertValueEmpty(rs.getString(3)));
                soldAssetdReport.setItem_status_date(Constants.convertValueEmpty(rs.getString(5)));
                soldAssetdReport.setOrder_number(Constants.convertValueEmpty(rs.getString(6)));
                soldAssetdReport.setOrder_date(Constants.convertValueEmpty(rs.getString(7)));
                soldAssetdReport.setOrder_total(Constants.convertValueEmpty(rs.getString(8)));
                
                //if (Constants.convertValueEmpty(rs.getString(8)) != "")
                //	soldAssetdReport.setCat1(Constants.convertValueEmpty(rs.getString(8)));
                //else
                //	soldAssetdReport.setCat1(Constants.convertValueEmpty((rs.getString(9))));
                soldAssetdReport.setTax_total(Constants.convertValueEmpty((rs.getString(9))));
                soldAssetdReport.setShip_total(Constants.convertValueEmpty((rs.getString(10))));
                soldAssetdReport.setSold_price(Constants.convertValueEmpty((rs.getString(11))));
                soldAssetdReport.setModified_price(Constants.convertValueEmpty((rs.getString(12))));
                soldAssetdReport.setCategory_id(Constants.convertValueEmpty((rs.getString(13))));
                soldAssetdReport.setDescription(Constants.convertValueEmpty((rs.getString(14))));
                soldAssetdReport.setId(rs.getLong(1));
                
                
                listAssetReport.add(soldAssetdReport);
                //LOGGER.info("Discount Adjustment : " + soldAssetdReport.getContact_name());
            }
            this.setTotalRecord(cstmt.getInt(6));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listAssetReport;
    }

    /**
     * 
     * <p>
     * Search Credit Report
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public String viewCreditReport(String dayFrom, String dayTo)
    {
        String total = "";
        try
        {
            LOGGER.info("Execute ProductDAO : function searchCreditReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("view.credit.report.order");
            LOGGER.info("SQL : " + sql);
            dayFrom = dayFrom.replaceAll("/", "-");
            dayTo = dayTo.replaceAll("/", "-");
            String convertFromDay = "";
            String coverntToday = "";
            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
            dayTo += " 23:59:59";
            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
            java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, fromdateTimestamp);
            pstmt.setTimestamp(2, todateTimestamp);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                total = rs.getString(1);

            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return total;
    }
    
    public String viewAssetReport(String dayFrom, String dayTo)
    {
        String total = "";
        try
        {
            LOGGER.info("Execute ProductDAO : function searchCreditReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("view.credit.report.order");
            LOGGER.info("SQL : " + sql);
            dayFrom = dayFrom.replaceAll("/", "-");
            dayTo = dayTo.replaceAll("/", "-");
            String convertFromDay = "";
            String coverntToday = "";
            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
            dayTo += " 23:59:59";
            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
            java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);
            pstmt = conn.prepareStatement(sql);
            pstmt.setTimestamp(1, fromdateTimestamp);
            pstmt.setTimestamp(2, todateTimestamp);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                total = rs.getString(1);

            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return total;
    }
    
    public List<SummaryInventoryReport> searchInventoryReport(int selectedPage, int pageSize)
    {
        List<SummaryInventoryReport> listInventoryReport = new ArrayList<SummaryInventoryReport>();
        try
        {
            LOGGER.info("Execute ProductDAO : function searchCreditReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.inventory.report.order.sp");
            LOGGER.info("SQL : " + sql);
//            dayFrom = dayFrom.replaceAll("/", "-");
//            dayTo = dayTo.replaceAll("/", "-");
//            String convertFromDay = "";
//            String coverntToday = "";
//            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
//            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
//            dayTo += " 23:59:59";
//            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
//            java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);


            cstmt = conn.prepareCall(sql);
//            cstmt.setTimestamp(1, fromdateTimestamp);
//            cstmt.setTimestamp(2, todateTimestamp);
            cstmt.setInt(1, selectedPage);
            cstmt.setInt(2, pageSize);
            cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
            
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                SummaryInventoryReport summaryInventoryReport = new SummaryInventoryReport();
                summaryInventoryReport.setcategory_id((rs.getInt(1)));
                summaryInventoryReport.setmfg_part_number(Constants.convertValueEmpty(rs.getString(2)));
                summaryInventoryReport.setshort_description(Constants.convertValueEmpty(rs.getString(3)));
                summaryInventoryReport.setitem_count((rs.getInt(4)));
                summaryInventoryReport.setList_price((rs.getInt(5)));
                summaryInventoryReport.setList_price40((rs.getInt(6)));
                summaryInventoryReport.setList_price50((rs.getInt(7)));
                summaryInventoryReport.setList_price60((rs.getInt(8)));
                summaryInventoryReport.setitem_status(Constants.convertValueEmpty(rs.getString(9)));
                summaryInventoryReport.setId(rs.getLong(10));
                
                listInventoryReport.add(summaryInventoryReport);
                //LOGGER.info("Discount Adjustment : " + summaryInventoryReport.getContact_name());
            }
            
            this.setTotalRecord(cstmt.getInt(3));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listInventoryReport;
    }
    
    public String viewInventoryReport()
    {
        String total = "";
        try
        {
            LOGGER.info("Execute ProductDAO : function searchCreditReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("view.inventory.report.order");
            LOGGER.info("SQL : " + sql);
//            dayFrom = dayFrom.replaceAll("/", "-");
//            dayTo = dayTo.replaceAll("/", "-");
//            String convertFromDay = "";
//            String coverntToday = "";
//            dayFrom = convertFromDay.concat(dayFrom.substring(6, 10).concat("-").concat(dayFrom.substring(0, 5)));
//            dayTo = coverntToday.concat(dayTo.substring(6, 10).concat("-").concat(dayTo.substring(0, 5)));
//            dayTo += " 23:59:59";
            //java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayFrom + " 00:00:00");
            //java.sql.Timestamp todateTimestamp = Timestamp.valueOf(dayTo);
            pstmt = conn.prepareStatement(sql);
            //pstmt.setTimestamp(1, fromdateTimestamp);
            //pstmt.setTimestamp(2, todateTimestamp);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                total = rs.getString(1);

            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return total;
    }

    /**
     * <p>
     * Get Map Total Order by Month of year
     * </p>
     * 
     * @param year
     *            year
     * @return Map
     * @throws Exception
     *             e
     * @author linhdo
     **/
    public Map<Integer, OrderDate> mapYear(final int year) throws Exception
    {
        SortedMap<Integer, OrderDate> mapYear = new TreeMap<Integer, OrderDate>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.date.year");

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, year);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                OrderDate orderDate = new OrderDate();
                orderDate.setMonth(Constants.convertValueEmpty(rs.getString(1)));
                orderDate.setMonthNum(rs.getInt(2));
                orderDate.setNumOrder(rs.getInt(3));
                orderDate.setTotalSum(rs.getBigDecimal(4));
                orderDate.setTotalAvg(rs.getBigDecimal(5));
                orderDate.setTotalMax(rs.getBigDecimal(6));
                orderDate.setTotalMin(rs.getBigDecimal(7));
                orderDate.setDiscAvg(rs.getBigDecimal(8));
                orderDate.setDiscMax(rs.getBigDecimal(9));
                orderDate.setDiscMin(rs.getBigDecimal(10));
                mapYear.put(orderDate.getMonthNum(), orderDate);
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return mapYear;
    }

    /**
     * <p>
     * Get Map Total Order by Month of year
     * </p>
     * 
     * @param year
     *            year
     * @return Map
     * @throws Exception
     *             e
     * @author linhdo
     **/
    public Map<Integer, OrderDate> mapMonth(final int year, final int month) throws Exception
    {
        SortedMap<Integer, OrderDate> mapMonth = new TreeMap<Integer, OrderDate>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.date.month");

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                OrderDate orderDate = new OrderDate();
                orderDate.setDayNum(rs.getInt(1));
                orderDate.setNumOrder(rs.getInt(2));
                orderDate.setTotalSum(rs.getBigDecimal(3));
                orderDate.setTotalAvg(rs.getBigDecimal(4));
                orderDate.setTotalMax(rs.getBigDecimal(5));
                orderDate.setTotalMin(rs.getBigDecimal(6));
                orderDate.setDiscAvg(rs.getBigDecimal(7));
                orderDate.setDiscMax(rs.getBigDecimal(8));
                orderDate.setDiscMin(rs.getBigDecimal(9));
                mapMonth.put(orderDate.getDayNum(), orderDate);
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return mapMonth;
    }

    /**
     * 
     * <p>
     * Search Credit Report
     * </p>
     * 
     * @param catid
     *            catid
     * @param ostype
     *            ostype
     * @param brandtype
     *            brandtype
     * @param model
     *            model
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @param proctype
     *            proctype
     * @return List list
     * 
     * @author linhdo
     * 
     */
    public List<OrderSummary> getOrderSummray(String catid, String ostype, String cosmetic, String brandtype, String model, String date1, String date2, String proctype) throws Exception
    {
        List<OrderSummary> list = new ArrayList<OrderSummary>();
        try
        {
        	DAOUtils daoUtil = DAOUtils.getInstance();
        	conn = daoUtil.getConnection();
        	//Get agents that have report=1
        	String agentIDs = daoUtil.getString("sql.order.summary.report.agent.displayed");
        	pstmt = conn.prepareStatement(agentIDs);
            rs = pstmt.executeQuery();  
            agentIDs="-1";
            while (rs.next())
            {
            	agentIDs=agentIDs + "," + rs.getInt(1);
            }
            if (rs != null)
                rs.close();
            if (pstmt != null)
                pstmt.close();
        	// 
         
           //get cosmetic 
            if(cosmetic.equals("all")){
            	cosmetic = "SELECT DISTINCT (cosmetic_grade) FROM agent_report";
            }else{
            	cosmetic = "'" + cosmetic + "'";
            }
            
           // int category_id = Integer.parseInt(catid);
            String query1 = daoUtil.getString("sql.order.summary.report.model.protype1");
            query1=query1.replaceAll("AGENTIDS", agentIDs);
            
            String query2 = daoUtil.getString("sql.order.summary.report.model.protype2");
            
            String filter1 = daoUtil.getString("sql.order.summary.report.filter1");
            filter1=filter1.replaceAll("AGENTIDS", agentIDs);
            
            String filter2 = daoUtil.getString("sql.order.summary.report.filter2");

            String query = "";
            String filter = "";
            if (model.contains("All") && proctype.contains("All"))
            {
                query = query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+") " + query2;
                filter =
                    filter1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+") " + "AND ( ordereddate BETWEEN '" + date1 + "' AND '" + date2
                        + "' )";
            }
            else if (model.contains("All") && !proctype.contains("All"))
            {
                query = query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ")  AND cosmetic_grade in("+cosmetic+")  AND processor_type IN  ( " + proctype + ") " + query2;
                filter =
                    filter1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ")  AND cosmetic_grade in("+cosmetic+")  AND model IN ( " + model + ") " + "AND ( ordereddate BETWEEN '"
                        + date1 + "' AND '" + date2 + "' )";
                System.out.println("Query : "+query);
                System.out.println("Filter : "+filter);
            }
            else if (!model.contains("All") && proctype.contains("All"))
            {
                query = query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ")  AND cosmetic_grade in("+cosmetic+")  AND model IN ( " + model + ") " + query2;
                filter =
                    filter1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  AND processor_type IN  ( " + proctype + ") "
                        + "AND ( ordereddate BETWEEN '" + date1 + "' AND '" + date2 + "' )";
            }
            else
            {
            	// with case category 11961,11961,11963 search level which is Brand Type
            	if(catid.contains("11961") || catid.contains("11962") || catid.contains("11963") || catid.contains("11964") || catid.contains("11940") || catid.contains("11941")){
            		query =
                            query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ")  AND cosmetic_grade in("+cosmetic+")  " + query2;
                        filter =
                            filter1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+") "
                                 + "AND ( ordereddate BETWEEN '" + date1 + "' AND '" + date2 + "' )";
            	}else{
                
            		query =
                    query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  AND model IN ( " + model + ") AND processor_type IN  ( "
                        + proctype + ") " + query2;
                
            		filter =
                    filter1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  AND model IN ( " + model + ") AND processor_type IN  ( "
                        + proctype + ") " + "AND ( ordereddate BETWEEN '" + date1 + "' AND '" + date2 + "' )";
            	}
            }

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            System.out.println("Order Summary:\n " + query);
            rs = pstmt.executeQuery();
            OrderSummary orderSummary = new OrderSummary();

            while (rs.next())
            {
                Summary summary = new Summary();
                if (rs.getString(5).trim().equals("MRI"))
                {
                    summary.setSales(rs.getBigDecimal(1));
                    summary.setUnits(rs.getInt(2));
                    summary.setMhz(rs.getInt(3));
                    summary.setOrders(rs.getInt(4));
                    orderSummary.setAgent(summary);
                }
                else if (rs.getString(5).trim().equals("Catalog Store"))
                {
                    summary.setSales(rs.getBigDecimal(1));
                    summary.setUnits(rs.getInt(2));
                    summary.setMhz(rs.getInt(3));
                    summary.setOrders(rs.getInt(4));
                    orderSummary.setStore(summary);
                }
                else if (rs.getString(5).trim().equals("edeal Auctions"))
                {
                    summary.setSales(rs.getBigDecimal(1));
                    summary.setUnits(rs.getInt(2));
                    summary.setMhz(rs.getInt(3));
                    summary.setOrders(rs.getInt(4));
                    orderSummary.setAuction(summary);
                }
                else if ((rs.getString(5).trim().equals("eBay Marketplace")) || (rs.getString(5).trim().equals("eBayAuction")) || (rs.getString(5).trim().equals("eBayFixed"))
                    || (rs.getString(5).trim().equals("eBayDailyDeal")))
                {
                    if (orderSummary.getEbay() != null)
                    {
                        summary.setSales(orderSummary.getEbay().getSales().add(rs.getBigDecimal(1)));
                        summary.setUnits(orderSummary.getEbay().getUnits() + rs.getInt(2));
                        summary.setMhz(orderSummary.getEbay().getMhz() + rs.getInt(3));
                        summary.setOrders(orderSummary.getEbay().getOrders() + rs.getInt(4));
                    }
                    else
                    {
                        summary.setSales(rs.getBigDecimal(1));
                        summary.setUnits(rs.getInt(2));
                        summary.setMhz(rs.getInt(3));
                        summary.setOrders(rs.getInt(4));
                    }
                    orderSummary.setEbay(summary);
                }
                else if (rs.getString(5).trim().equals("Agent Store"))
                {
                    summary.setSales(rs.getBigDecimal(1));
                    summary.setUnits(rs.getInt(2));
                    summary.setMhz(rs.getInt(3));
                    summary.setOrders(rs.getInt(4));
                    orderSummary.setCustomer(summary);
                }
                list.add(orderSummary);
            }
            /* For Docking Station only */
            OrderSummary dockingSummary = new OrderSummary();
            if (catid.contains("11958") && (brandtype.contains("DOCKING")))
            {
                String removeDock = brandtype.replace("DOCKING", "");
                String queryDocking = query1 + " category_id IN ( '11958' ) AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + removeDock + ")" + query2;
                dockingSummary = getSumarryDocking(conn, queryDocking, date1, date2);
            }

            /* For Monitor only */
            int[] orders = new int[5];
            BigDecimal[][] saleUnit = new BigDecimal[5][2];
            for (int i = 0; i < 5; i++)
            {
                for (int j = 0; j < 2; j++)
                {
                    saleUnit[i][j] = BigDecimal.valueOf(0);
                }
            }
            int[] filterOrder = orders;
            BigDecimal[][] filterSaleUnit = saleUnit;
            if (catid.contains("11955") && (!brandtype.contains("VIS")))
            {
                String queryMonitor = daoUtil.getString("sql.order.summary.report.monitor1");
                queryMonitor=queryMonitor.replaceAll("AGENTIDS", agentIDs);
                filterOrder = getOrderSummray(conn, queryMonitor, date1, date2, filter);

                String querySalesUnit = daoUtil.getString("sql.order.summary.report.sales.unit");
                querySalesUnit=querySalesUnit.replaceAll("AGENTIDS", agentIDs);
                filterSaleUnit = getSalesUnit(conn, querySalesUnit, date1, date2);
            }
            else if (catid.contains("11955") && (brandtype.contains("VIS")))
            {
                String removeVis = brandtype.replace("VIS ", "");
                String monitor1 = daoUtil.getString("sql.order.summary.report.monitor2a");
                monitor1=monitor1.replaceAll("AGENTIDS", agentIDs);
                String monitor2 = daoUtil.getString("sql.order.summary.report.monitor2b");
                String monitor = monitor1 + " brandtype IN (" + removeVis + ") " + monitor2;
                filterOrder = getOrderSummray(conn, monitor, date1, date2, filter);

                String querySalesUnit = daoUtil.getString("sql.order.summary.report.sales.unit1");
                querySalesUnit=querySalesUnit.replaceAll("AGENTIDS", agentIDs);
                querySalesUnit += " brandtype IN (" + removeVis + ") " + daoUtil.getString("sql.order.summary.report.sales.unit2");
                filterSaleUnit = getSalesUnit(conn, querySalesUnit, date1, date2);
                //check null value of filterSaleUnit before add
            }
            /* END */
            /* For non_system_cat */
            String nonSysCat = catid;
            nonSysCat = nonSysCat.replace("11940", "");
            nonSysCat = nonSysCat.replace("11941", "");
            nonSysCat = nonSysCat.replace("11946", "");
            nonSysCat = nonSysCat.replace("11947", "");
            nonSysCat = nonSysCat.replace("11949", "");
            nonSysCat = nonSysCat.replace("11950", "");
            nonSysCat = nonSysCat.replace("11955", "");
            nonSysCat = nonSysCat.replace("11961", "");
            nonSysCat = nonSysCat.replace("11962", "");
            nonSysCat = nonSysCat.replace("11963", "");
            nonSysCat = nonSysCat.replace("11964", "");
            nonSysCat = nonSysCat.replace("11958", "");
            
            int[] nonSysOrder = orders;
            BigDecimal[][] nonSaleUnit = saleUnit;

            if (nonSysCat.contains("119"))
            {
                String queryNon = daoUtil.getString("sql.order.summary.report.nonsyscat.order1");
                queryNon=queryNon.replaceAll("AGENTIDS", agentIDs);
                queryNon += " category_id IN (" + nonSysCat + ") " + daoUtil.getString("sql.order.summary.report.nonsyscat.order2");

                nonSysOrder = getOrderSummray(conn, queryNon, date1, date2, filter);

                String querySaleNon = daoUtil.getString("sql.order.summary.report.nonsyscat.sales.unit1");
                querySaleNon=querySaleNon.replaceAll("AGENTIDS", agentIDs);
                querySaleNon += " category_id IN ( " + nonSysCat + ") " + daoUtil.getString("sql.order.summary.report.nonsyscat.sales.unit2");

                nonSaleUnit = getSalesUnit(conn, querySaleNon, date1, date2);

            }
            /* END */
            //SUMMARY ORDER
            OrderSummary summaryAll = new OrderSummary();
            Boolean hasValue = false;
            for (OrderSummary sum : list)
            {
                hasValue = true;
                if (sum.getAgent() != null)
                {
                    Summary summary = new Summary();
                    summary.setSales(sum.getAgent().getSales().add((BigDecimal) filterSaleUnit[0][0]).add((BigDecimal) nonSaleUnit[0][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(sum.getAgent().getUnits() + filterSaleUnit[0][1].intValue() + nonSaleUnit[0][1].intValue());
                    summary.setMhz(sum.getAgent().getMhz());
                    summary.setOrders(sum.getAgent().getOrders() + filterOrder[0] + nonSysOrder[0]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getAgent())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getAgent().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getAgent().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getAgent().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getAgent().getOrders());
                        }

                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }

                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    summaryAll.setAgent(summary);
                }
                else
                {
                    Summary summary = new Summary();
                    summary.setSales((BigDecimal) filterSaleUnit[0][0].add((BigDecimal) nonSaleUnit[0][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(filterSaleUnit[0][1].intValue() + nonSaleUnit[0][1].intValue());
                    summary.setMhz(0);
                    summary.setOrders(filterOrder[0] + nonSysOrder[0]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getAgent())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getAgent().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getAgent().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getAgent().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getAgent().getOrders());
                        }

                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }

                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    summaryAll.setAgent(summary);
                }
                if (sum.getStore() != null)
                {
                    Summary summary = new Summary();
                    summary.setSales(sum.getStore().getSales().add((BigDecimal) filterSaleUnit[1][0]).add((BigDecimal) nonSaleUnit[1][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(sum.getStore().getUnits() + filterSaleUnit[1][1].intValue() + nonSaleUnit[1][1].intValue());
                    summary.setMhz(sum.getStore().getMhz());
                    summary.setOrders(sum.getStore().getOrders() + filterOrder[1] + nonSysOrder[1]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getStore())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getStore().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getStore().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getStore().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getStore().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setStore(summary);
                }
                else
                {
                    Summary summary = new Summary();
                    summary.setSales((BigDecimal) filterSaleUnit[1][0].add((BigDecimal) nonSaleUnit[1][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(filterSaleUnit[1][1].intValue() + nonSaleUnit[1][1].intValue());
                    summary.setMhz(0);
                    summary.setOrders(filterOrder[1] + nonSysOrder[1]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getStore())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getStore().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getStore().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getStore().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getStore().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setStore(summary);

                }
                if (sum.getAuction() != null)
                {
                    Summary summary = new Summary();
                    summary.setSales(sum.getAuction().getSales().add((BigDecimal) filterSaleUnit[2][0]).add((BigDecimal) nonSaleUnit[2][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(sum.getAuction().getUnits() + filterSaleUnit[2][1].intValue() + nonSaleUnit[2][1].intValue());
                    summary.setMhz(sum.getAuction().getMhz());
                    summary.setOrders(sum.getAuction().getOrders() + filterOrder[2] + nonSysOrder[2]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getAuction())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getAuction().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getAuction().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getAuction().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getAuction().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setAuction(summary);
                }
                else
                {
                    Summary summary = new Summary();
                    summary.setSales((BigDecimal) filterSaleUnit[2][0].add((BigDecimal) nonSaleUnit[2][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(filterSaleUnit[2][1].intValue() + nonSaleUnit[2][1].intValue());
                    summary.setMhz(0);
                    summary.setOrders(filterOrder[2] + nonSysOrder[2]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getAuction())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getAuction().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getAuction().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getAuction().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getAuction().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setAuction(summary);
                }
                if (sum.getEbay() != null)
                {
                    Summary summary = new Summary();
                    summary.setSales(sum.getEbay().getSales().add((BigDecimal) filterSaleUnit[3][0]).add((BigDecimal) nonSaleUnit[3][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(sum.getEbay().getUnits() + filterSaleUnit[3][1].intValue() + nonSaleUnit[3][1].intValue());
                    summary.setMhz(sum.getEbay().getMhz());
                    summary.setOrders(sum.getEbay().getOrders() + filterOrder[3] + nonSysOrder[3]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getEbay())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getEbay().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getEbay().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getEbay().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getEbay().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setEbay(summary);
                }
                else
                {
                    Summary summary = new Summary();
                    summary.setSales((BigDecimal) filterSaleUnit[3][0].add((BigDecimal) nonSaleUnit[3][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(filterSaleUnit[3][1].intValue() + nonSaleUnit[3][1].intValue());
                    summary.setMhz(0);
                    summary.setOrders(filterOrder[3] + nonSysOrder[3]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getEbay())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getEbay().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getEbay().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getEbay().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getEbay().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setEbay(summary);
                }
                if (sum.getCustomer() != null)
                {
                    Summary summary = new Summary();
                    summary.setSales(sum.getCustomer().getSales().add((BigDecimal) filterSaleUnit[4][0]).add((BigDecimal) nonSaleUnit[4][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(sum.getCustomer().getUnits() + filterSaleUnit[4][1].intValue() + nonSaleUnit[4][1].intValue());
                    summary.setMhz(sum.getCustomer().getMhz());
                    summary.setOrders(sum.getCustomer().getOrders() + filterOrder[4] + nonSysOrder[4]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getCustomer())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getCustomer().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getCustomer().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getCustomer().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getCustomer().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setCustomer(summary);
                }
                else
                {
                    Summary summary = new Summary();
                    summary.setSales((BigDecimal) filterSaleUnit[4][0].add((BigDecimal) nonSaleUnit[4][0]).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(filterSaleUnit[4][1].intValue() + nonSaleUnit[4][1].intValue());
                    summary.setMhz(0);
                    summary.setOrders(filterOrder[4] + nonSysOrder[4]);
                    if (null != dockingSummary)
                    {
                        if (null != dockingSummary.getCustomer())
                        {
                            summary.setSales(summary.getSales().add(dockingSummary.getCustomer().getSales()));
                            summary.setUnits(summary.getUnits() + dockingSummary.getCustomer().getUnits());
                            summary.setMhz(summary.getMhz() + dockingSummary.getCustomer().getMhz());
                            summary.setOrders(summary.getOrders() + dockingSummary.getCustomer().getOrders());
                        }

                    }
                    if (summary.getUnits() > 0 && summary.getSales() != null)
                    {
                        summary.setAsp(summary.getSales().divide(BigDecimal.valueOf(summary.getUnits()), 0, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setAsp(BigDecimal.valueOf(0));
                    }
                    if (summary.getMhz() > 0 && (summary.getSales() != null))
                    {
                        summary.setSalesMhz(summary.getSales().divide(BigDecimal.valueOf(summary.getMhz()), 3, RoundingMode.HALF_EVEN));
                    }
                    else
                    {
                        summary.setSalesMhz(BigDecimal.valueOf(0));
                    }
                    summaryAll.setCustomer(summary);
                }
            }
            if (hasValue)
            {
                list.clear();
                list.add(summaryAll);
            }
            else
            {
                Summary sumAgent = new Summary();
                sumAgent.setSales((BigDecimal) filterSaleUnit[0][0].add((BigDecimal) nonSaleUnit[0][0]).setScale(3, RoundingMode.HALF_EVEN));
                sumAgent.setUnits(filterSaleUnit[0][1].intValue() + nonSaleUnit[0][1].intValue());
                sumAgent.setMhz(0);
                sumAgent.setOrders(filterOrder[0] + nonSysOrder[0]);
                if (null != dockingSummary)
                {
                    if (null != dockingSummary.getAgent())
                    {
                        sumAgent.setSales(sumAgent.getSales().add(dockingSummary.getAgent().getSales()));
                        sumAgent.setUnits(sumAgent.getUnits() + dockingSummary.getAgent().getUnits());
                        sumAgent.setMhz(sumAgent.getMhz() + dockingSummary.getAgent().getMhz());
                        sumAgent.setOrders(sumAgent.getOrders() + dockingSummary.getAgent().getOrders());
                    }

                }
                if (sumAgent.getMhz() > 0 && (sumAgent.getSales() != null))
                {
                    sumAgent.setSalesMhz(sumAgent.getSales().divide(BigDecimal.valueOf(sumAgent.getMhz()), 3, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumAgent.setSalesMhz(BigDecimal.valueOf(0));
                }

                if (sumAgent.getUnits() > 0 && sumAgent.getSales() != null)
                {
                    sumAgent.setAsp(sumAgent.getSales().divide(BigDecimal.valueOf(sumAgent.getUnits()), 0, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumAgent.setAsp(BigDecimal.valueOf(0));
                }
                summaryAll.setAgent(sumAgent);

                Summary sumStore = new Summary();
                sumStore.setSales((BigDecimal) filterSaleUnit[1][0].add((BigDecimal) nonSaleUnit[1][0]).setScale(3, RoundingMode.HALF_EVEN));
                sumStore.setUnits(filterSaleUnit[1][1].intValue() + nonSaleUnit[1][1].intValue());
                sumStore.setMhz(0);
                sumStore.setOrders(filterOrder[1] + nonSysOrder[1]);
                if (null != dockingSummary)
                {
                    if (null != dockingSummary.getStore())
                    {
                        sumStore.setSales(sumStore.getSales().add(dockingSummary.getStore().getSales()));
                        sumStore.setUnits(sumStore.getUnits() + dockingSummary.getStore().getUnits());
                        sumStore.setMhz(sumStore.getMhz() + dockingSummary.getStore().getMhz());
                        sumStore.setOrders(sumStore.getOrders() + dockingSummary.getStore().getOrders());
                    }

                }
                if (sumStore.getUnits() > 0 && sumStore.getSales() != null)
                {
                    sumStore.setAsp(sumStore.getSales().divide(BigDecimal.valueOf(sumStore.getUnits()), 0, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumStore.setAsp(BigDecimal.valueOf(0));
                }
                if (sumStore.getMhz() > 0 && (sumStore.getSales() != null))
                {
                    sumStore.setSalesMhz(sumStore.getSales().divide(BigDecimal.valueOf(sumStore.getMhz()), 3, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumStore.setSalesMhz(BigDecimal.valueOf(0));
                }
                summaryAll.setStore(sumStore);

                Summary sumAuction = new Summary();
                sumAuction.setSales((BigDecimal) filterSaleUnit[2][0].add((BigDecimal) nonSaleUnit[2][0]).setScale(3, RoundingMode.HALF_EVEN));
                sumAuction.setUnits(filterSaleUnit[2][1].intValue() + nonSaleUnit[2][1].intValue());
                sumAuction.setMhz(0);
                sumAuction.setOrders(filterOrder[2] + nonSysOrder[2]);
                if (null != dockingSummary)
                {
                    if (null != dockingSummary.getAuction())
                    {
                        sumAuction.setSales(sumAuction.getSales().add(dockingSummary.getAuction().getSales()));
                        sumAuction.setUnits(sumAuction.getUnits() + dockingSummary.getAuction().getUnits());
                        sumAuction.setMhz(sumAuction.getMhz() + dockingSummary.getAuction().getMhz());
                        sumAuction.setOrders(sumAuction.getOrders() + dockingSummary.getAuction().getOrders());
                    }

                }
                if (sumAuction.getUnits() > 0 && sumAuction.getSales() != null)
                {
                    sumAuction.setAsp(sumAuction.getSales().divide(BigDecimal.valueOf(sumAuction.getUnits()), 0, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumAuction.setAsp(BigDecimal.valueOf(0));
                }
                if (sumAuction.getMhz() > 0 && (sumAuction.getSales() != null))
                {
                    sumAuction.setSalesMhz(sumAuction.getSales().divide(BigDecimal.valueOf(sumAuction.getMhz()), 3, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumAuction.setSalesMhz(BigDecimal.valueOf(0));
                }
                summaryAll.setAuction(sumAuction);

                Summary sumEbay = new Summary();
                sumEbay.setSales((BigDecimal) filterSaleUnit[3][0].add((BigDecimal) nonSaleUnit[3][0]).setScale(3, RoundingMode.HALF_EVEN));
                sumEbay.setUnits(filterSaleUnit[3][1].intValue() + nonSaleUnit[3][1].intValue());
                sumEbay.setMhz(0);
                sumEbay.setOrders(filterOrder[3] + nonSysOrder[3]);
                if (null != dockingSummary)
                {
                    if (null != dockingSummary.getEbay())
                    {
                        sumEbay.setSales(sumEbay.getSales().add(dockingSummary.getEbay().getSales()));
                        sumEbay.setUnits(sumEbay.getUnits() + dockingSummary.getEbay().getUnits());
                        sumEbay.setMhz(sumEbay.getMhz() + dockingSummary.getEbay().getMhz());
                        sumEbay.setOrders(sumEbay.getOrders() + dockingSummary.getEbay().getOrders());
                    }

                }
                if (sumEbay.getUnits() > 0 && sumEbay.getSales() != null)
                {
                    sumEbay.setAsp(sumEbay.getSales().divide(BigDecimal.valueOf(sumEbay.getUnits()), 0, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumEbay.setAsp(BigDecimal.valueOf(0));
                }
                if (sumEbay.getMhz() > 0 && (sumEbay.getSales() != null))
                {
                    sumEbay.setSalesMhz(sumEbay.getSales().divide(BigDecimal.valueOf(sumEbay.getMhz()), 3, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumEbay.setSalesMhz(BigDecimal.valueOf(0));
                }
                summaryAll.setEbay(sumEbay);

                Summary sumCustomer = new Summary();
                sumCustomer.setSales((BigDecimal) filterSaleUnit[4][0].add((BigDecimal) nonSaleUnit[4][0]).setScale(3, RoundingMode.HALF_EVEN));
                sumCustomer.setUnits(filterSaleUnit[4][1].intValue() + nonSaleUnit[4][1].intValue());
                sumCustomer.setMhz(0);
                sumCustomer.setOrders(filterOrder[4] + nonSysOrder[4]);
                if (null != dockingSummary)
                {
                    if (null != dockingSummary.getCustomer())
                    {
                        sumCustomer.setSales(sumCustomer.getSales().add(dockingSummary.getCustomer().getSales()));
                        sumCustomer.setUnits(sumCustomer.getUnits() + dockingSummary.getCustomer().getUnits());
                        sumCustomer.setMhz(sumCustomer.getMhz() + dockingSummary.getCustomer().getMhz());
                        sumCustomer.setOrders(sumCustomer.getOrders() + dockingSummary.getCustomer().getOrders());
                    }

                }
                if (sumCustomer.getUnits() > 0 && sumCustomer.getSales() != null)
                {
                    sumCustomer.setAsp(sumCustomer.getSales().divide(BigDecimal.valueOf(sumCustomer.getUnits()), 0, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumCustomer.setAsp(BigDecimal.valueOf(0));
                }
                if (sumCustomer.getMhz() > 0 && (sumCustomer.getSales() != null))
                {
                    sumCustomer.setSalesMhz(sumCustomer.getSales().divide(BigDecimal.valueOf(sumCustomer.getMhz()), 3, RoundingMode.HALF_EVEN));
                }
                else
                {
                    sumCustomer.setSalesMhz(BigDecimal.valueOf(0));
                }
                summaryAll.setCustomer(sumCustomer);

                list.clear();
                list.add(summaryAll);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 
     * <p>
     * Get Sales and Unit for Monitor
     * </p>
     * 
     * @param query
     *            query
     * @param con
     *            con
     * @param filter
     *            filter
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return Object[][] object
     * 
     * @author linhdo
     * 
     */
    private BigDecimal[][] getSalesUnit(Connection con, String query, String date1, String date2) throws Exception
    {
        BigDecimal[][] filterSaleUnit = new BigDecimal[5][2];
        try
        {
           // DAOUtils daoUtil = DAOUtils.getInstance();
          //  con = daoUtil.getConnection();
            System.out.println("SALE UNIT --" + query);
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, date1);
            pstm.setString(2, date2);
            ResultSet res = pstm.executeQuery();
            while (res.next())
            {
                if (res.getString(3).trim().equals("MRI"))
                {
                    filterSaleUnit[0][0] = res.getBigDecimal(1);
                    filterSaleUnit[0][1] = res.getBigDecimal(2);
                }
                else if (res.getString(3).trim().equals("Catalog Store"))
                {
                    filterSaleUnit[1][0] = res.getBigDecimal(1);
                    filterSaleUnit[1][1] = res.getBigDecimal(2);
                }
                else if (res.getString(3).trim().equals("edeal Auctions"))
                {
                    filterSaleUnit[2][0] = res.getBigDecimal(1);
                    filterSaleUnit[2][1] = res.getBigDecimal(2);
                }
                else if ((res.getString(3).trim().equals("eBay Marketplace")) || (res.getString(3).trim().equals("eBayAuction")) || (res.getString(3).trim().equals("eBayFixed"))
                    || (res.getString(3).trim().equals("eBayDailyDeal")))
                {
                    if (filterSaleUnit[3][0] != null && filterSaleUnit[3][1] != null)
                    {
                        filterSaleUnit[3][0] = filterSaleUnit[3][0].add(res.getBigDecimal(1));
                        filterSaleUnit[3][1] = filterSaleUnit[3][1].add(res.getBigDecimal(2));
                    }
                    else
                    {
                        filterSaleUnit[3][0] = res.getBigDecimal(1);
                        filterSaleUnit[3][1] = res.getBigDecimal(2);
                    }
                }
                else if (res.getString(3).trim().equals("Agent Store"))
                {
                    filterSaleUnit[4][0] = res.getBigDecimal(1);
                    filterSaleUnit[4][1] = res.getBigDecimal(2);
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            System.out.println("ERROR Execute DAO");
            e.printStackTrace();
        }
        filterSaleUnit[0][0] = (filterSaleUnit[0][0] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[0][0];
        filterSaleUnit[1][0] = (filterSaleUnit[1][0] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[1][0];
        filterSaleUnit[2][0] = (filterSaleUnit[2][0] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[2][0];
        filterSaleUnit[3][0] = (filterSaleUnit[3][0] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[3][0];
        filterSaleUnit[4][0] = (filterSaleUnit[4][0] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[4][0];

        filterSaleUnit[0][1] = (filterSaleUnit[0][1] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[0][1];
        filterSaleUnit[1][1] = (filterSaleUnit[1][1] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[1][1];
        filterSaleUnit[2][1] = (filterSaleUnit[2][1] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[2][1];
        filterSaleUnit[3][1] = (filterSaleUnit[3][1] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[3][1];
        filterSaleUnit[4][1] = (filterSaleUnit[4][1] == null) ? BigDecimal.valueOf(0) : filterSaleUnit[4][1];

        return filterSaleUnit;
    }

    /**
     * 
     * <p>
     * Get Order for Monitor
     * </p>
     * 
     * @param query
     *            query
     * @param con
     *            con
     * @param filter
     *            filter
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return int[] int
     * 
     * @author linhdo
     * 
     */
    private int[] getOrderSummray(Connection con, String query, String date1, String date2, String filter) throws Exception
    {
        int[] filterOrder = new int[5];
        try
        {
            System.out.println("Filter " + filter);
            query = query + " NOT IN (" + filter + ") GROUP BY channel";
          //  DAOUtils daoUtil = DAOUtils.getInstance();
          //  con = daoUtil.getConnection();
            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, date1);
            pstm.setString(2, date2);
            System.out.println("Order after Filter : " + query);
            ResultSet res = pstm.executeQuery();
            while (res.next())
            {
                if (res.getString(2).trim().equals("MRI"))
                {
                    filterOrder[0] = (res.getInt(1));
                }
                else if (res.getString(2).trim().equals("Catalog Store"))
                {

                    filterOrder[1] = (res.getInt(1));
                }
                else if (res.getString(2).trim().equals("edeal Auctions"))
                {
                    filterOrder[2] = (res.getInt(1));
                }
                else if ((res.getString(2).trim().equals("eBay Marketplace")) || (res.getString(2).trim().equals("eBayAuction")) || (res.getString(2).trim().equals("eBayFixed"))
                    || (res.getString(2).trim().equals("eBayDailyDeal")))
                {
                    filterOrder[3] += (res.getInt(1));
                }
                else if ((res.getString(2).trim().equals("Agent Store")))
                {
                    filterOrder[4] = (res.getInt(1));
                }
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            System.out.println("ERROR Execute DAO");
            e.printStackTrace();
        }
        return filterOrder;
    }

    /**
     * <p>
     * Get Map Total Order by Day
     * </p>
     * 
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @param page
     *            page
     * @return Map
     * @throws Exception
     *             e
     * @author linhdo
     **/
    public List<Order> listDay(final int year, final int month, final int day, final int page) throws Exception
    {
        List<Order> listDay = new ArrayList<Order>();
        
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.date.day.sp");

            cstmt = conn.prepareCall(query);
            cstmt.setInt(1, year);
            cstmt.setInt(2, month);
            cstmt.setInt(3, day);
            cstmt.setInt(4, page);
            cstmt.setInt(5, record);
            cstmt.registerOutParameter(6, java.sql.Types.INTEGER);

            rs = cstmt.executeQuery();
            while (rs.next())
            {
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setOrderId(rs.getInt(2));
                order.setAgentId(rs.getString(3));
                order.setShip_to_name(rs.getString(4));
                order.setShopId(rs.getString(5));
                order.setTime(Converter.getTimeString(rs.getTime(6)));
                order.setTotal_total(rs.getBigDecimal(7));
                order.setDiscount(Converter.getDiscount(rs.getBigDecimal(8)));
                order.setItem(rs.getInt(9));
                order.setByAgent(rs.getShort(10));
                //Do modify
                if (order.getByAgent()==0)
                {
                  	 order.setOrderType("Agent Store");
                }
                else 
                {
                	if (order.getAgentId() != null)
                	{                	
                 	 AgentService agentService = new AgentService();
                     Agent agent = agentService.getAgent(Integer.parseInt(order.getAgentId()));
                     order.setOrderType(agent.getUserName());
                	}
                    else
                    {
                     	order.setOrderType("Unknown Sale Type");
                    }
                }

                listDay.add(order);
            }
            //set total record
            this.setTotalRecord(cstmt.getInt(6));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listDay;
    }

    /**
     * <p>
     * Count order in Day
     * </p>
     * 
     * @param year
     *            year
     * @param month
     *            month
     * @param day
     *            day
     * @return Map
     * @throws Exception
     *             e
     * @author linhdo
     **/
    public int countOrderDay(final int year, final int month, final int day) throws Exception
    {
        int numOrder = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.date.day.count");

            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, year);
            pstmt.setInt(2, month);
            pstmt.setInt(3, day);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            if (rs.next())
            {
                numOrder = rs.getInt(1);
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return numOrder;
    }

    /**
     * 
     * <p>
     * Get Processor Type
     * </p>
     * 
     * @param brandType
     *            brandType
     * @param catType
     *            catType
     * @param date1
     *            date1
     * @param date2
     *            date2
     * @return List
     * 
     * @author linhdo
     * 
     */
    public List<String> showProcessor(String brandType, String catType, String date1, String date2) throws Exception
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query1 = daoUtil.getString("search.processor.agent.report.order1");
            String query2 = daoUtil.getString("search.processor.agent.report.order2");
            String query = query1 + " brandtype in (" + brandType + ") AND category_id IN (" + catType + ") " + query2;
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            System.out.println("SHOW PROCESSOR : " + query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                if (null != rs.getString(1) && !rs.getString(1).equals(""))
                    list.add(rs.getString(1));
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 
     * <p>
     * show Model Agent Report
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public List<String> showModel(String brandtype, String catType, String date1, String date2, String procsellist)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            if (procsellist.contains("All"))
            {
                String query1 = daoUtil.getString("search.model1a.agent.report.order");
                String query2 = daoUtil.getString("search.model1b.agent.report.order");
                String query = query1 + " brandtype in (" + brandtype + ") AND category_id IN (" + catType + ") " + query2;

                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, date1);
                pstmt.setString(2, date2);
                System.out.println("SHOW MODEL " + query);
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    if (null != rs.getString(1) && !rs.getString(1).equals(""))
                        list.add(rs.getString(1));
                }
            }
            else
            {
                String query1 = daoUtil.getString("search.model2a.agent.report.order");
                String query2 = daoUtil.getString("search.model2b.agent.report.order");
                String query = query1 + " brandtype in (" + brandtype + ") AND category_id IN (" + catType + ") AND processor_type IN (" + procsellist + ") " + query2;
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, date1);
                pstmt.setString(2, date2);
                System.out.println(query);
                rs = pstmt.executeQuery();
                while (rs.next())
                {
                    if (null != rs.getString(1) && !rs.getString(1).equals(""))
                        list.add(rs.getString(1));
                }
            }
        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 
     * <p>
     * view Pending Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public OrderViewPending viewPendingOrder1(String order_id)
    {
        OrderViewPending viewPendingOrder = null;
        try
        {

            LOGGER.info("Execute ProductDAO : function viewPendingOrder");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("search.view1.pending.order");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, order_id);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                viewPendingOrder = new OrderViewPending();
                // OrderViewPending viewPendingOrder = new OrderViewPending();

                viewPendingOrder.setOrder_id(order_id);
                viewPendingOrder.setEpp_id(rs.getString("epp_id"));
                viewPendingOrder.setOadjust_subtotal(rs.getString("oadjust_subtotal"));
                viewPendingOrder.setDiscount_total(rs.getString("discount_total"));
                viewPendingOrder.setShipping_discount(Constants.convertValueEmpty(rs.getString("shipping_discount")));
                viewPendingOrder.setShipping_total(Constants.convertValueEmpty(rs.getString("shipping_total")));
                viewPendingOrder.setTax_total(Constants.convertValueEmpty(rs.getString("tax_total")));
                viewPendingOrder.setFreighttax(rs.getString("freighttax"));
                viewPendingOrder.setTotal_total(rs.getString("total_total"));
                viewPendingOrder.setEst_subtotal(rs.getString("est_subtotal"));
                viewPendingOrder.setOrderStatus(rs.getString("orderStatus"));
                viewPendingOrder.setCreatedDate(Constants.convertValueEmpty(rs.getString("createdDate")));
                viewPendingOrder.setBill_to_name(Constants.convertValueEmpty(rs.getString("bill_to_name")));
                viewPendingOrder.setBill_to_company(Constants.convertValueEmpty(rs.getString("bill_to_company")));
                viewPendingOrder.setBill_to_address1(Constants.convertValueEmpty(rs.getString("bill_to_address1")));
                viewPendingOrder.setBill_to_address2(Constants.convertValueEmpty(rs.getString("bill_to_address2")));
                viewPendingOrder.setBill_to_address1(Constants.convertValueEmpty(rs.getString("bill_to_address1")));
                viewPendingOrder.setBill_to_city(Constants.convertValueEmpty(rs.getString("bill_to_city")));
                viewPendingOrder.setBill_to_state(Constants.convertValueEmpty(rs.getString("bill_to_state")));
                viewPendingOrder.setBill_to_postal(Constants.convertValueEmpty(rs.getString("bill_to_postal")));
                viewPendingOrder.setBill_to_country(Constants.convertValueEmpty(rs.getString("bill_to_country")));
                viewPendingOrder.setBill_to_phone(Constants.convertValueEmpty(rs.getString("bill_to_phone")));
                viewPendingOrder.setBill_to_phoneext(Constants.convertValueEmpty(rs.getString("bill_to_phoneext")));
                viewPendingOrder.setBill_to_fax(Constants.convertValueEmpty(rs.getString("bill_to_fax")));

                viewPendingOrder.setShip_to_name(Constants.convertValueEmpty(rs.getString("ship_to_name")));
                viewPendingOrder.setShip_to_title(Constants.convertValueEmpty(rs.getString("ship_to_title")));
                viewPendingOrder.setShip_to_company(Constants.convertValueEmpty(rs.getString("ship_to_company")));
                viewPendingOrder.setShip_to_address1(Constants.convertValueEmpty(rs.getString("ship_to_address1")));
                viewPendingOrder.setShip_to_address2(Constants.convertValueEmpty(rs.getString("ship_to_address2")));
                viewPendingOrder.setShip_to_city(Constants.convertValueEmpty(rs.getString("ship_to_city")));
                viewPendingOrder.setShip_to_state(Constants.convertValueEmpty(rs.getString("ship_to_state")));
                viewPendingOrder.setShip_to_postal(Constants.convertValueEmpty(rs.getString("ship_to_postal")));
                viewPendingOrder.setShip_to_country(Constants.convertValueEmpty(rs.getString("ship_to_country")));
                viewPendingOrder.setShip_to_phone(Constants.convertValueEmpty(rs.getString("ship_to_phone")));
                viewPendingOrder.setShip_to_phoneext(Constants.convertValueEmpty(rs.getString("ship_to_phoneext")));
                viewPendingOrder.setCc_type(Constants.convertValueEmpty(rs.getString("cc_type")));
                viewPendingOrder.setCc_name(Constants.convertValueEmpty(rs.getString("cc_name")));
                viewPendingOrder.setCc_expmonth(rs.getString("cc_expmonth"));
                viewPendingOrder.setCc_expyear(rs.getString("cc_expyear"));
                viewPendingOrder.setCc_number(Constants.convertValueEmpty(rs.getString("cc_number")));
                viewPendingOrder.setApprovalNumber(Constants.convertValueEmpty(rs.getString("ApprovalNumber")));
                viewPendingOrder.setShopper_id(rs.getString(10));
                viewPendingOrder.setAgentIDEnter(rs.getString("AgentIDEnter"));
                viewPendingOrder.setShip_method(Constants.convertValueEmpty(rs.getString("ship_method")));
                viewPendingOrder.setShip_terms(Constants.convertValueEmpty(rs.getString("ship_terms")));
                viewPendingOrder.setPayment_terms(Constants.convertValueEmpty(rs.getString("payment_terms")));
                viewPendingOrder.setByAgent(rs.getShort("byAgent"));
                viewPendingOrder.setTracking_number(rs.getString("tracking_number"));
                viewPendingOrder.setListing(rs.getString("listing"));
                viewPendingOrder.setHandling_total(rs.getString("handling_total"));
                
                

                String agentName = getAgentName(conn, viewPendingOrder.getShopper_id(), viewPendingOrder.getByAgent());
                viewPendingOrder.setAgentName(agentName);
                //  listviewPendingOrder.add(viewPendingOrder);
                // LOGGER.info("Item Sku : " + orderPending.getOrdernumber());

            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return viewPendingOrder;
    }

    /**
     * 
     * <p>
     * view Pending Order
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public OrderViewPending viewPendingOrderQuery1(String AgentIDEnter)
    {
        OrderViewPending viewPendingOrder = null;
        try
        {

            LOGGER.info("Execute ProductDAO : function viewPendingOrder");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.view3.pending.order");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, AgentIDEnter);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                viewPendingOrder = new OrderViewPending();
                // OrderViewPending viewPendingOrder = new OrderViewPending();                   
                viewPendingOrder.setUserName(rs.getString("UserName"));
                //  listviewPendingOrder.add(viewPendingOrder);
                // LOGGER.info("Item Sku : " + orderPending.getOrdernumber());
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return viewPendingOrder;
    }

    /**
     * 
     * <p>
     * view Pending Order Query2
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public OrderViewPending viewPendingOrderQuery2(String shipid)
    {
        OrderViewPending viewPendingOrder = null;
        try
        {
            System.out.println("viewPendingOrderQuery1");
            LOGGER.info("Execute ProductDAO : function viewPendingOrder");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.view4.pending.order");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shipid);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                viewPendingOrder = new OrderViewPending();
                // OrderViewPending viewPendingOrder = new OrderViewPending();                   
                viewPendingOrder.setDescription(Constants.convertValueEmpty(rs.getString(1)));
                System.out.println("setDescription" + rs.getString(1));
                //  listviewPendingOrder.add(viewPendingOrder);
                // LOGGER.info("Item Sku : " + orderPending.getOrdernumber());
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return viewPendingOrder;
    }

    /**
     * 
     * <p>
     * view Pending Order Query2
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public String viewPendingOrderQuery3(String orderId)
    {
        String viewPendingOrder = "";
        try
        {

            LOGGER.info("Execute ProductDAO : function viewPendingOrderQuery3");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.view5.pending.order");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, orderId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                //   viewPendingOrder = new OrderViewPending();
                // OrderViewPending viewPendingOrder = new OrderViewPending();                   
                viewPendingOrder = rs.getString(4);

                //  listviewPendingOrder.add(viewPendingOrder);
                // LOGGER.info("Item Sku : " + orderPending.getOrdernumber());
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return viewPendingOrder;
    }

    /**
     * 
     * <p>
     * view Pending Order Query2
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public String viewPendingOrderQuery4(String shopId)
    {
        String viewPendingOrder = "";
        try
        {

            LOGGER.info("Execute ProductDAO : function viewPendingOrderQuery3");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.view6.pending.order");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                //   viewPendingOrder = new OrderViewPending();
                // OrderViewPending viewPendingOrder = new OrderViewPending();                   
                viewPendingOrder = rs.getString(1);

                //  listviewPendingOrder.add(viewPendingOrder);
                // LOGGER.info("Item Sku : " + orderPending.getOrdernumber());
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return viewPendingOrder;
    }

    /**
     * 
     * <p>
     * show Model Agent Report
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public List<OrderViewPending> viewPendingOrderQuery5(String OrderNumber)
    {
        List<OrderViewPending> listviewPendingOrder = new ArrayList<OrderViewPending>();
        try
        {

            LOGGER.info("Execute ProductDAO : function showProcessorAgentReport");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.view2.pending.order");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, OrderNumber);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                OrderViewPending viewPendingOrder = new OrderViewPending();
                viewPendingOrder.setItem_sku(Constants.convertValueEmpty(rs.getString("item_sku")));
                viewPendingOrder.setDescription(Constants.convertValueEmpty(rs.getString("description")));
                viewPendingOrder.setProduct_list_price(Constants.convertValueEmpty(rs.getString("product_list_price")));
                viewPendingOrder.setDiscounted_price(Constants.convertValueEmpty(rs.getString("discounted_price")));
                viewPendingOrder.setTracking_number(Constants.convertValueEmpty(rs.getString("tracking_number")));
                viewPendingOrder.setCosmetic_grade(Constants.convertValueEmpty(rs.getString("cosmetic_grade")));
                viewPendingOrder.setOrderedQty(Constants.convertValueEmpty(rs.getString("quantity")));
                
                listviewPendingOrder.add(viewPendingOrder);
                // LOGGER.info("Item Sku : " + orderPending.getOrdernumber());
            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listviewPendingOrder;
    }

    /**
     * <p>
     * Search All Order by Agents
     * </p>
     * 
     * @param date1
     * @param date2
     * @param page
     *            page
     * @throws Exception
     *             Exception
     * @return List list
     * @author linhdo
     * 
     */
    public List<OrderAgent> getOrderByAgent(final String date1, final String date2) throws Exception
    {
        List<OrderAgent> listAgents = new ArrayList<OrderAgent>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agents.all");

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            int id = 1;
            while (rs.next())
            {
                OrderAgent agent = new OrderAgent();
                agent.setId(id);
                agent.setAgent(rs.getString(1));
                agent.setAgentId(rs.getInt(2));
                agent.setNumUnit(rs.getInt(3));
                agent.setTotalSum(rs.getBigDecimal(4));
                agent.setUnitAsp(rs.getBigDecimal(5));
                agent.setTotalMax(rs.getBigDecimal(6));
                agent.setTotalMin(rs.getBigDecimal(7));
                agent.setDiscAvg(rs.getBigDecimal(8));
                agent.setDiscMax(rs.getBigDecimal(9));
                agent.setDiscMin(rs.getBigDecimal(10));
                listAgents.add(agent);
                id++;
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listAgents;
    }

    /**
     * <p>
     * Count Agents
     * </p>
     * 
     * @param date1
     * @param date2
     * @return Integer
     * @throws Exception
     * @author linhdo
     **/
    public int countAgents(String date1, String date2) throws Exception
    {
        int total = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agents.count");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            if (rs.next())
                total = rs.getInt(1);
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return total;
    }

    /**
     * 
     * <p>
     * view Pending Order Query2
     * </p>
     * 
     * @throws Exception
     * 
     * @return List
     * 
     * @author thuynguyen
     * 
     */
    public String viewPendingOrderQueryTitle(String order_id)
    {
        String viewPendingOrderQueryTitle = "";
        try
        {

            LOGGER.info("Execute ProductDAO : function viewPendingOrderQueryTitle");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("search.view7.pending.order");
            LOGGER.info("SQL : " + sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, order_id);
            rs = pstmt.executeQuery();
            while (rs.next())
            {

                viewPendingOrderQueryTitle = rs.getString(1);

            }

        }

        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return viewPendingOrderQueryTitle;
    }

    /**
     * <p>
     * Count Order by Agents in Time
     * </p>
     * 
     * @param date1
     * @param date2
     * @param agentId
     * @return Integer
     * @throws Exception
     * @author linhdo
     **/
    public int countAgentDetail(String date1, String date2, String agentId) throws Exception
    {
        int total = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agents.detail.count");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            pstmt.setString(3, agentId);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            if (rs.next())
                total = rs.getInt(1);
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return total;
    }

    /**
     * <p>
     * Count Order by Agents in Time
     * </p>
     * 
     * @param date1
     * @param date2
     * @param agentId
     * @return Integer
     * @throws Exception
     * @author linhdo
     **/

    public List<OrderAgentDetail> getAgentDetail(String date1, String date2, String agentId, int page) throws Exception
    {
        List<OrderAgentDetail> listOrder = new ArrayList<OrderAgentDetail>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agents.detail.sp");

            cstmt = conn.prepareCall(query);

            cstmt.setString(1, date1);
            cstmt.setString(2, date2);
            cstmt.setString(3, agentId);
            cstmt.setInt(4, page);
            cstmt.setInt(5, record);
            cstmt.registerOutParameter(6, java.sql.Types.INTEGER);

            rs = cstmt.executeQuery();
            while (rs.next())
            {
                OrderAgentDetail agentDetail = new OrderAgentDetail();
                agentDetail.setId(rs.getInt(1));
                agentDetail.setOrderId(rs.getInt(2));
                agentDetail.setAgentId(rs.getString(3));
                agentDetail.setShip_to_name(rs.getString(4));
                agentDetail.setShip_to_company(rs.getString(5));
                agentDetail.setShopId(rs.getString(6));
                agentDetail.setDayOrder(Converter.getDateString(rs.getDate(7)));
                agentDetail.setTotal_total(rs.getBigDecimal(8));
                agentDetail.setDiscount(rs.getInt(9));
                agentDetail.setTotalDisc(rs.getBigDecimal(10));
                agentDetail.setItem(rs.getInt(11));
                agentDetail.setByAgent(rs.getShort(12));
                agentDetail.setAgentName(Constants.convertValueEmpty(rs.getString(13)));
                Map<String, Avg_mhz> avg_mhzMap = heldAvg_mhz(conn, String.valueOf(agentDetail.getOrderId()));
                agentDetail.setAvgMhz(avg_mhzMap.get(String.valueOf(agentDetail.getOrderId())));

                listOrder.add(agentDetail);
            }

            this.setTotalRecord(cstmt.getInt(6));
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return listOrder;
    }

    /**
     * <p>
     * update Order Status
     * </p>
     * 
     * @param orderNumber
     * @return void
     * @throws Exception
     * @author thuynguyen
     **/
    public void updateOrderStatus(String orderNumber) throws Exception
    {

        try
        {

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("search.view9.pending.order");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, orderNumber);
            LOGGER.info(query);
            pstmt.executeUpdate();

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

    }

    /**
     * <p>
     * Count Orders By Shopper
     * </p>
     * 
     * @param shopperId
     *            shopperId
     * 
     * @return Integer
     * @throws Exception
     * @author linhdo
     **/
    public int countOrderByShopper(String shopperId) throws Exception
    {
        int total = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.shopper.list.count");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, shopperId);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            if (rs.next())
                total = rs.getInt(1);
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return total;
    }

    /**
     * <p>
     * Get Shopper Detail
     * </p>
     * 
     * @param shopperId
     *            shopperId
     * 
     * @return List
     * @throws Exception
     * @author linhdo
     **/
    public List<OrderShopper> getShopperDetail(String shopperId) throws Exception
    {
        List<OrderShopper> list = new ArrayList<OrderShopper>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.shopper.detail");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, shopperId);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                OrderShopper orderShopper = new OrderShopper();
                String fname = rs.getString(1);
                String lname = rs.getString(2);
                orderShopper.setShip_to_name(fname + " " + lname);
                orderShopper.setShopId(rs.getString(3));
                list.add(orderShopper);
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    /**
     * <p>
     * Search All Orders by Shopper
     * </p>
     * 
     * @param shopperId
     *            shopperId
     * 
     * @param page
     *            page
     * @throws Exception
     *             Exception
     * @return List list
     * @author linhdo
     * 
     */
    public List<Order> getOrderByShopper(final String shopperId, final int page) throws Exception
    {
        List<Order> list = new ArrayList<Order>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.shopper.list.sp");

            cstmt = conn.prepareCall(query);
            cstmt.setInt(1, page);
            cstmt.setInt(2, record);
            cstmt.setString(3, shopperId);
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);

            rs = cstmt.executeQuery();
            while (rs.next())
            {
                Order order = new Order();
                order.setId(rs.getInt(1));
                order.setOrderId(rs.getInt("OrderNumber"));
                order.setDayOrder(Converter.getDateString(rs.getDate("createdDate")));
                order.setTime(Converter.getTimeString((rs.getTime("createdDate"))));
                order.setTotal_total(rs.getBigDecimal("total_total"));
                list.add(order);
            }
            this.setTotalRecord(cstmt.getInt(4));

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getVisSize()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.monitersize");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add("VIS " + rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getBrandtypePart()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandPartNetWorking");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }


    public List<String> getBrandServer()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandServer");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }
    
    public List<String> getBrandLaptop()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandLaptop");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }
    
    public List<String> getBrandTablet()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandTablet");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }
    
    public List<String> getBrandDesktop()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandDesktop");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }
    
    public List<String> getBrandWorkstation()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandWorkstation");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }
    
    public List<String> getBrandWarranty()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.BrandWarranty");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    
    public List<String> getCosmeticGrade()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.CosmeticGrade");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }
    
    public List<Summary> getOrder4AgentSummray(String catid, String ostype, String cosmetic, String brandtype, String model, String date1, String date2, String proctype) throws Exception
    {
        List<Summary> listAgent = new ArrayList<Summary>();
        try
        {
        	if(cosmetic.equals("all")){
            	cosmetic = "SELECT DISTINCT (cosmetic_grade) FROM agent_report";
            }else{
            	cosmetic = "'" + cosmetic + "'";
            }
        	
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query1 = daoUtil.getString("sql.order.summary.report.agent.model.protype1");
            String query2 = daoUtil.getString("sql.order.summary.report.agent.model.protype2");
            String query = "";
            if (model.contains("All") && proctype.contains("All"))
            {
                query = query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%'AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  " + query2;
            }
            else if (model.contains("All") && !proctype.contains("All"))
            {
                query = query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%'AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  AND processor_type IN  ( " + proctype + ") " + query2;
            }
            else if (!model.contains("All") && proctype.contains("All"))
            {
                query = query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%'AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  AND model IN ( " + model + ") " + query2;
            }
            else
            {
            	//with case category 11961,11961,11963 search level which is Brand Type
            	if(catid.contains("11961") || catid.contains("11962") || catid.contains("11963") || catid.contains("11940") || catid.contains("11941")){
            		query =
                            query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  " + query2;
                        
            	}else{
                
            		query =
                    query1 + " category_id IN ( " + catid + " )AND ostype LIKE '" + ostype + "%'AND brandtype IN ( " + brandtype + ") AND cosmetic_grade in("+cosmetic+")  AND model IN ( " + model + ") AND processor_type IN  ( "
                        + proctype + ") " + query2;
            	}
            }

            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, date1);
            pstmt.setString(2, date2);
            System.out.println("\nAgent Summary: " + query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                Summary summary = new Summary();
                summary.setSales(rs.getBigDecimal(1).setScale(3, RoundingMode.HALF_EVEN));
                summary.setUnits(rs.getInt(2));
                summary.setMhz(rs.getInt(3));
                summary.setAgentName(rs.getString(4));

                listAgent.add(summary);
            }

            List<Summary> listDockingAgent = new ArrayList<Summary>();
            /* For Docking Station */
            if (catid.contains("11958") && brandtype.contains("DOCKING"))
            {
            	brandtype = brandtype.replace("DOCKING", "");
                String queryDocking = query1 + " category_id IN ( '11958' ) AND ostype LIKE '" + ostype + "%' AND brandtype IN ( " + brandtype + ")" + query2;
                listDockingAgent = getSumarryDockingAgent(conn, queryDocking, date1, date2);
                if (listDockingAgent != null && !listDockingAgent.isEmpty())
                {
                    listAgent.addAll(listDockingAgent);
                }
            }
            List<Summary> listMonitor = new ArrayList<Summary>();
            /* For Monitor only */
            if (catid.contains("11955") && (!brandtype.contains("VIS")))
            {
                String querySalesUnit = daoUtil.getString("sql.order.summary.report.agent.monitor1");
                listMonitor = getSalesUnit4Agent(conn, querySalesUnit, date1, date2);
            }
            else if (catid.contains("11955") && (brandtype.contains("VIS")))
            {
                String removeVis = brandtype.replace("VIS ", "");
                String querySalesUnit = daoUtil.getString("sql.order.summary.report.agent.monitor2a");
                querySalesUnit += " brandtype IN (" + removeVis + ") " + daoUtil.getString("sql.order.summary.report.agent.monitor2b");
                listMonitor = getSalesUnit4Agent(conn, querySalesUnit, date1, date2);
                //check null value of filterSaleUnit before add
            }
            if (listMonitor != null && !listMonitor.isEmpty())
            {
                listAgent.addAll(listMonitor);
            }

            /* END */

            /* For non_system_cat */
            String nonSysCat = catid;
            nonSysCat = nonSysCat.replace("11946", "");
            nonSysCat = nonSysCat.replace("11947", "");
            nonSysCat = nonSysCat.replace("11949", "");
            nonSysCat = nonSysCat.replace("11950", "");
            nonSysCat = nonSysCat.replace("11955", "");
            nonSysCat = nonSysCat.replace("11958", "");
            nonSysCat = nonSysCat.replace("11961", "");
            nonSysCat = nonSysCat.replace("11962", "");
            nonSysCat = nonSysCat.replace("11963", "");
            List<Summary> listRest = new ArrayList<Summary>();
            if (nonSysCat.contains("119"))
            {

                String querySaleNon = daoUtil.getString("sql.order.summary.report.agent.nonsyscat1");
                querySaleNon += " category_id IN ( " + nonSysCat + ") " + daoUtil.getString("sql.order.summary.report.agent.monitor2b");
                listRest = getSalesUnit4Agent(conn, querySaleNon, date1, date2);
            }
            if (listRest != null && !listRest.isEmpty())
            {
                listAgent.addAll(listRest);
            }
            //add All Agent Name

            listAgent.addAll(getNameAgent(conn));

            /* END */
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return coverList(listAgent);
    }

    private List<Summary> getSalesUnit4Agent(Connection con, String query, String date1, String date2) throws Exception
    {
        List<Summary> list = new ArrayList<Summary>();
        try
        {
           // DAOUtils daoUtil = DAOUtils.getInstance();
          //  con = daoUtil.getConnection();

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, date1);
            pstm.setString(2, date2);
            System.out.println("Sale Unit " + query);
            ResultSet res = pstm.executeQuery();
            while (res.next())
            {
                Summary summary = new Summary();
                summary.setSales(res.getBigDecimal(1).setScale(2, RoundingMode.HALF_EVEN));
                summary.setUnits(res.getInt(2));
                summary.setMhz(res.getInt(3));
                summary.setAgentName(res.getString(4));
                list.add(summary);
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return list;
    }

    private List<Summary> coverList(List<Summary> listAgent)
    {
        List<Summary> list = new ArrayList<Summary>();
        Summary summary;
        for (int i = 0; i < listAgent.size(); i++)
        {
            BigDecimal sales = listAgent.get(i).getSales();
            int unit = listAgent.get(i).getUnits();
            int mhz = listAgent.get(i).getMhz();

            String currentAgentName = listAgent.get(i).getAgentName();
            summary = new Summary();
            boolean flag = true;

            for (int j = i + 1; j < listAgent.size(); j++)
            {
                if (listAgent.get(j).getAgentName().equals(listAgent.get(i).getAgentName()))
                {
                    sales = sales.add(listAgent.get(j).getSales().setScale(2, RoundingMode.HALF_EVEN));
                    unit += listAgent.get(j).getUnits();
                    mhz += listAgent.get(j).getMhz();

                }
            }

            for (Iterator<Summary> iterator = list.iterator(); iterator.hasNext();)
            {
                Summary summary2 = (Summary) iterator.next();
                if (summary2.getAgentName().equals(currentAgentName))
                {
                    flag = false;
                }
            }

            if (flag)
            {
                summary.setAgentName(currentAgentName);
                summary.setUnits(unit);
                summary.setSales(sales);
                summary.setMhz(mhz);
                list.add(summary);
            }

        }
        return list;
    }

    /**
     * <p>
     * Count Shopper
     * </p>
     * 
     * @return Integer
     * @throws Exception
     * @author thuynguyen
     **/
    public int countOrderPending() throws Exception
    {
        int totalShopper = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("search.count.all.pending.order");
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            if (rs.next())
                totalShopper = rs.getInt(1);
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return totalShopper;
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

    public List<String> getDockingStation()
    {
        List<String> list = new ArrayList<String>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("sql.order.agent.report.dockingStation");
            pstmt = conn.prepareStatement(query);
            LOGGER.info(query);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                list.add(rs.getString(1));
            }

        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return list;
    }

    private OrderSummary getSumarryDocking(Connection con, String query, String date1, String date2) throws Exception
    {
        OrderSummary dockingSummary = new OrderSummary();
        try
        {
          //  DAOUtils daoUtil = DAOUtils.getInstance();
         //   con = daoUtil.getConnection();
            Summary summ = new Summary();
            summ.setSales(BigDecimal.valueOf(0));
            summ.setUnits(0);
            summ.setMhz(0);
            summ.setAsp(BigDecimal.valueOf(0));
            summ.setSalesMhz(BigDecimal.valueOf(0));
            summ.setOrders(0);

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, date1);
            pstm.setString(2, date2);
            System.out.println("DOCKING STATION " + query);
            ResultSet res = pstm.executeQuery();
            while (res.next())
            {
                
                if (res.getString(5).trim().equals("MRI"))
                {
                	Summary summary = new Summary();
                	summary.setSales(res.getBigDecimal(1).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(res.getInt(2));
                    summary.setMhz(res.getInt(3));
                    summary.setOrders(res.getInt(4));
                    dockingSummary.setAgent(summary);
                }
                else if (res.getString(5).trim().equals("Catalog Store"))
                {
                	Summary summary = new Summary();
                	summary.setSales(res.getBigDecimal(1).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(res.getInt(2));
                    summary.setMhz(res.getInt(3));
                    summary.setOrders(res.getInt(4));
                    dockingSummary.setStore(summary);
                }
                else if (res.getString(5).trim().equals("edeal Auctions"))
                {
                	Summary summary = new Summary();
                	summary.setSales(res.getBigDecimal(1).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(res.getInt(2));
                    summary.setMhz(res.getInt(3));
                    summary.setOrders(res.getInt(4));
                    dockingSummary.setAuction(summary);
                }
                else if ((res.getString(5).trim().equals("eBay Marketplace")) || (res.getString(5).trim().equals("eBayAuction")) || (res.getString(5).trim().equals("eBayFixed"))
                    || (res.getString(5).trim().equals("eBayDailyDeal")))
                {
                	Summary summary = new Summary();
                	if (dockingSummary.getEbay() != null)
                    {
                        summary.setSales(dockingSummary.getEbay().getSales().add(res.getBigDecimal(1)));
                        summary.setUnits(dockingSummary.getEbay().getUnits() + res.getInt(2));
                        summary.setMhz(dockingSummary.getEbay().getMhz() + res.getInt(3));
                        summary.setOrders(dockingSummary.getEbay().getOrders() + res.getInt(4));
                    }
                    else
                    {
                        summary.setSales(res.getBigDecimal(1));
                        summary.setUnits(res.getInt(2));
                        summary.setMhz(res.getInt(3));
                        summary.setOrders(res.getInt(4));
                    }
                    dockingSummary.setEbay(summary);
                }
                else if (res.getString(5).trim().equals("Agent Store"))
                {
                	Summary summary = new Summary();
                	summary.setSales(res.getBigDecimal(1).setScale(3, RoundingMode.HALF_EVEN));
                    summary.setUnits(res.getInt(2));
                    summary.setMhz(res.getInt(3));
                    summary.setOrders(res.getInt(4));
                    dockingSummary.setCustomer(summary);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return dockingSummary;
    }

    private List<Summary> getSumarryDockingAgent(Connection con, String query, String date1, String date2) throws Exception
    {
        List<Summary> listDockingAgent = new ArrayList<Summary>();
        try
        {
          //  DAOUtils daoUtil = DAOUtils.getInstance();
           // con = daoUtil.getConnection();

            PreparedStatement pstm = con.prepareStatement(query);
            pstm.setString(1, date1);
            pstm.setString(2, date2);
            System.out.println("DOCKING STATION AGENT " + query);
            ResultSet res = pstm.executeQuery();
            while (res.next())
            {
                Summary summary = new Summary();
                summary.setSales(res.getBigDecimal(1).setScale(3, RoundingMode.HALF_EVEN));
                summary.setUnits(res.getInt(2));
                summary.setMhz(res.getInt(3));
                summary.setAgentName(res.getString(4));
                listDockingAgent.add(summary);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return listDockingAgent;
    }

    private List<Summary> getNameAgent(Connection con)
    {
        List<Summary> list = new ArrayList<Summary>();
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
         //   con = daoUtil.getConnection();
            String query = daoUtil.getString("sql.summary.report.agent.name");
            System.out.println("DOCKING STATION AGENT " + query);
            PreparedStatement pstm = con.prepareStatement(query);
            LOGGER.info("AGENT NAME " + query);
            ResultSet res = pstm.executeQuery();
            while (res.next())
            {
                Summary summary = new Summary();
                summary.setSales(BigDecimal.valueOf(0));
                summary.setUnits(0);
                summary.setMhz(0);
                summary.setAgentName(res.getString(1));
                list.add(summary);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveOrderClearLog(String ordernumber,String agentclear){
    	boolean b = false;
    	try{
    		DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("order.clear.log");
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, ordernumber);
            pstmt.setString(2, agentclear);
            LOGGER.info(query);
            System.out.println("before execute");
            pstmt.execute();
            System.out.println("after execute");
            
    		b=true;
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return b;
    }
 /*   
    public int updateReportAdminUsers()
    {
        int i = 0;
        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = daoUtil.getString("order.agent.report.update.AdminUsers");
            pstmt = conn.prepareStatement(query);
            i = pstmt.executeUpdate();
        }
        catch (Exception e)
        {
            LOGGER.warning("ERROR Execute OrderDAO cleanAdminUsers");
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException sqlE)
            {
                LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }
        return i;
    }
*/
}
