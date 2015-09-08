/*
 * FILENAME
 *     ShopperManagerDAO.java
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

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Note;
import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.model.ShopperViewBasket;
import com.dell.enterprise.agenttool.model.ShopperViewReceipts;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

/**
 * @author thuynguyen
 **/
public class ShopperManagerDAO
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.ShopperManagerDAO");
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private CallableStatement cstmt;
    private int totalRecord = 5;

    /**
     * @param shopperId
     *            shopperId
     * @return Shopper
     **/
    public Shopper getShopperDetails(final String shopperId)
    {
        Shopper shopper = null;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            String sql = daoUtils.getString("get.shopper.details");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                shopper = new Shopper();

                shopper.setShopperId(shopperId);
                shopper.setShipToFName(rs.getString(Constants.DB_FIELD_SHIP_FNAME));
                shopper.setShipToLName(rs.getString(Constants.DB_FIELD_SHIP_LNAME));
                shopper.setShipToCompany(rs.getString(Constants.DB_FIELD_SHIP_COMPANY));
                shopper.setShipToAddress1(rs.getString(Constants.DB_FIELD_SHIP_ADDRESS_1));
                shopper.setShipToAddress2(rs.getString(Constants.DB_FIELD_SHIP_ADDRESS_2));
                shopper.setShipToCity(rs.getString(Constants.DB_FIELD_SHIP_CITY));
                shopper.setShipToState(rs.getString(Constants.DB_FIELD_SHIP_STATE));
                shopper.setShipToPostal(rs.getString(Constants.DB_FIELD_SHIP_POSTAL));
                shopper.setShipToCounty(rs.getString(Constants.DB_FIELD_SHIP_COUNTY));
                shopper.setShipToCountry(rs.getString(Constants.DB_FIELD_SHIP_COUNTRY));
                shopper.setShipToFax(rs.getString(Constants.DB_FIELD_SHIP_FAX));
                shopper.setShipToPhone(rs.getString(Constants.DB_FIELD_SHIP_PHONE));
                shopper.setShipToEmail(rs.getString(Constants.DB_FIELD_SHIP_EMAIL));

                shopper.setBillToFName(rs.getString(Constants.DB_FIELD_BILL_FNAME));
                shopper.setBillToLName(rs.getString(Constants.DB_FIELD_BILL_LNAME));
                shopper.setBillToCompany(rs.getString(Constants.DB_FIELD_BILL_COMPANY));
                shopper.setBillToAddress1(rs.getString(Constants.DB_FIELD_BILL_ADDRESS_1));
                shopper.setBillToAddress2(rs.getString(Constants.DB_FIELD_BILL_ADDRESS_2));
                shopper.setBillToCity(rs.getString(Constants.DB_FIELD_BILL_CITY));
                shopper.setBillToState(rs.getString(Constants.DB_FIELD_BILL_STATE));
                shopper.setBillToPostal(rs.getString(Constants.DB_FIELD_BILL_POSTAL));
                shopper.setBillToCounty(rs.getString(Constants.DB_FIELD_BILL_COUNTY));
                shopper.setBillToCountry(rs.getString(Constants.DB_FIELD_BILL_COUNTRY));
                shopper.setBillToFax(rs.getString(Constants.DB_FIELD_BILL_FAX));
                shopper.setBillToPhone(rs.getString(Constants.DB_FIELD_BILL_PHONE));
                shopper.setBillToEmail(rs.getString(Constants.DB_FIELD_BILL_EMAIL));

                shopper.setShopperNumber(rs.getInt(Constants.DB_FIELD_SHOPPER_NUMBER));
                shopper.setCreditLine(rs.getDouble(Constants.DB_FIELD_CREDIT_LINE));
                shopper.setCreditExp(rs.getString(Constants.DB_FIELD_CREDIT_EXP));
                shopper.setCreditAvail(rs.getDouble(Constants.DB_FIELD_CREDIT_AVAIL));
                shopper.setCreditComment(rs.getString(Constants.DB_FIELD_CREDIT_COMMENT));
                
                //Fix, add agent id information
                shopper.setAgent_id(rs.getInt("agent_id"));
            }
        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return shopper;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return int
     **/
    public int getShopperReceipts(final String shopperId)
    {
        int receipts = 0;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            String sql = daoUtils.getString("get.shopper.receipts");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                receipts = rs.getInt(Constants.SHOPPER_RECEIPTS);
            }
        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return receipts;
    }
    public int getShopperReceipts_AgentStore(final String shopperId)
    {
        int receipts = 0;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            String sql = daoUtils.getString("get.shopper.receipts_agentstore");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                receipts = rs.getInt(Constants.SHOPPER_RECEIPTS);
            }
        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return receipts;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<Note> getShopperNotes(final String shopperId)
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            String sql = daoUtils.getString("get.shopper.notes");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                note = new Note();

                note.setAgentName(rs.getString(Constants.SHOPPER_NOTES_AGENT_NAME));
                note.setSubject(rs.getString(Constants.SHOPPER_NOTES_SUBJECT));
                note.setNoteType(rs.getString(Constants.SHOPPER_NOTES_NOTE_TYPE));
                note.setNotes(rs.getString(Constants.SHOPPER_NOTES));
                note.setTimeOff(rs.getTimestamp(Constants.SHOPPER_NOTES_TIME_OFF));
                note.setSubjectId(rs.getInt(Constants.SHOPPER_NOTES_SUBJECT_ID));
                note.setTopic(rs.getString(Constants.SHOPPER_NOTES_TOPIC));
                note.setOrderNumber(rs.getString(Constants.SHOPPER_NOTES_ORDER_NUMBER));

                notes.add(note);
            }

        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return notes;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<Note> getShopperNotes(final String shopperId, int page)
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            String sql = daoUtils.getString("get.shopper.notes");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                note = new Note();

                note.setAgentName(rs.getString(Constants.SHOPPER_NOTES_AGENT_NAME));
                note.setSubject(rs.getString(Constants.SHOPPER_NOTES_SUBJECT));
                note.setNoteType(rs.getString(Constants.SHOPPER_NOTES_NOTE_TYPE));
                note.setNotes(rs.getString(Constants.SHOPPER_NOTES));
                note.setTimeOff(rs.getTimestamp(Constants.SHOPPER_NOTES_TIME_OFF));
                note.setSubjectId(rs.getInt(Constants.SHOPPER_NOTES_SUBJECT_ID));
                note.setTopic(rs.getString(Constants.SHOPPER_NOTES_TOPIC));
                note.setOrderNumber(rs.getString(Constants.SHOPPER_NOTES_ORDER_NUMBER));

                notes.add(note);
            }

        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return notes;
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
     * @author thuynguyen
     * 
     */
    public List<Note> searchNotes(final String shopper_id, int page) throws Exception
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;
        try
        {
            //    LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("get.shopper.move.notes");
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, shopper_id);
            cstmt.setInt(2, page);
            cstmt.setInt(3, 5);
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                note = new Note();
                note.setAgentName(rs.getString(Constants.SHOPPER_NOTES_AGENT_NAME));
                note.setSubject(rs.getString(Constants.SHOPPER_NOTES_SUBJECT));
                note.setNoteType(rs.getString(Constants.SHOPPER_NOTES_NOTE_TYPE));
                note.setNotes(rs.getString(Constants.SHOPPER_NOTES));
                note.setTimeOff(rs.getTimestamp(Constants.SHOPPER_NOTES_TIME_OFF));
                note.setSubjectId(rs.getInt(Constants.SHOPPER_NOTES_SUBJECT_ID));
                note.setTopic(rs.getString(Constants.SHOPPER_NOTES_TOPIC));
                note.setOrderNumber(rs.getString(Constants.SHOPPER_NOTES_ORDER_NUMBER));

                notes.add(note);
            }
            this.setTotalRecord(cstmt.getInt(4));
        }

        catch (Exception e)
        {
            // LOGGER.warning("ERROR Execute DAO");
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
                // LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return notes;
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
    public List<Note> searchMoreNotes(final String shopper_id, int page) throws Exception
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;
        try
        {
            //    LOGGER.info("Execute DAO");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql = daoUtil.getString("get.shopper.move.notes");
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, shopper_id);
            cstmt.setInt(2, page);
            cstmt.setInt(3, 10);
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                note = new Note();
                note.setAgentName(rs.getString(Constants.SHOPPER_NOTES_AGENT_NAME));
                note.setSubject(rs.getString(Constants.SHOPPER_NOTES_SUBJECT));
                note.setNoteType(rs.getString(Constants.SHOPPER_NOTES_NOTE_TYPE));
                note.setNotes(rs.getString(Constants.SHOPPER_NOTES));
                note.setTimeOff(rs.getTimestamp(Constants.SHOPPER_NOTES_TIME_OFF));
                note.setSubjectId(rs.getInt(Constants.SHOPPER_NOTES_SUBJECT_ID));
                note.setTopic(rs.getString(Constants.SHOPPER_NOTES_TOPIC));
                note.setOrderNumber(rs.getString(Constants.SHOPPER_NOTES_ORDER_NUMBER));
                notes.add(note);
            }
            this.setTotalRecord(cstmt.getInt(4));

        }

        catch (Exception e)
        {
            // LOGGER.warning("ERROR Execute DAO");
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
                // LOGGER.info("SQL error");
                sqlE.printStackTrace();
            }
        }

        return notes;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return int
     **/
    public int getSizeNotes(final String shopperId)
    {
        int receipts = 0;

        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            String sql = daoUtils.getString("get.shopper.size.notes");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                receipts = rs.getInt(1);
            }
        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return receipts;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return ShopperViewReceipts
     **/
    public ShopperViewReceipts getViewReceipts(final String shopperId)
    {
        //    List<ShopperViewBasket> viewBaskets = new ArrayList<ShopperViewBasket>();
        ShopperViewReceipts view = null;
        try
        {

            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();
            String sql = daoUtils.getString("shopper.view.receipts");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {

                view = new ShopperViewReceipts();
                view.setShip_to_fname(rs.getString("ship_to_fname"));
                view.setShip_to_lname(rs.getString("ship_to_lname"));
                view.setShopper_number(rs.getString("shopper_number"));
                view.setShopper_id(rs.getString("shopper_id"));
                view.setAgent_id(rs.getInt("agent_id"));

            }
        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return view;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<ShopperViewReceipts>
     **/
    public List<ShopperViewReceipts> getViewTotal(final String shopperId, int page)
    {
        List<ShopperViewReceipts> viewReceipts = new ArrayList<ShopperViewReceipts>();
        ShopperViewReceipts view;
        try
        {

            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();
            String sql = daoUtils.getString("shopper.view.receipts.viewtotal");
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, shopperId);
            cstmt.setInt(2, page);
            cstmt.setInt(3, 40);
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                view = new ShopperViewReceipts();
                view.setRowNumber(rs.getInt("RowNumber"));
                view.setCreatedDate(Constants.convertValueEmpty(rs.getString("createdDate")));
                view.setTotal_total(Constants.convertValueEmpty(rs.getString("total_total")));
                view.setOrderNumber(Constants.convertValueEmpty(rs.getString("OrderNumber")));
                viewReceipts.add(view);

            }
            this.setTotalRecord(cstmt.getInt(4));
        }
        catch (Exception e)
        {

        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return viewReceipts;
    }
    /**
     * @param shopperId
     *            shopperId
     * @return List<ShopperViewReceipts>
     **/
    public List<ShopperViewReceipts> getViewTotal_AgentStore(final String shopperId, int page)
    {
        List<ShopperViewReceipts> viewReceipts = new ArrayList<ShopperViewReceipts>();
        ShopperViewReceipts view;
        try
        {

            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();
            String sql = daoUtils.getString("shopper.view.receipts.viewtotal.agentstore");
            cstmt = conn.prepareCall(sql);
            cstmt.setString(1, shopperId);
            cstmt.setInt(2, page);
            cstmt.setInt(3, 40);
            cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
            rs = cstmt.executeQuery();
            while (rs.next())
            {
                view = new ShopperViewReceipts();
                view.setRowNumber(rs.getInt("RowNumber"));
                view.setCreatedDate(Constants.convertValueEmpty(rs.getString("createdDate")));
                view.setTotal_total(Constants.convertValueEmpty(rs.getString("total_total")));
                view.setOrderNumber(Constants.convertValueEmpty(rs.getString("OrderNumber")));
                viewReceipts.add(view);

            }
            this.setTotalRecord(cstmt.getInt(4));
        }
        catch (Exception e)
        {

        }
        finally
        {
            try
            {
                if (rs != null)
                    rs.close();
                if (cstmt != null)
                    cstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return viewReceipts;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<ShopperViewBasket>
     **/

    public List<ShopperViewBasket> getViewBasket(final String shopperId, Boolean byAgent, Agent agent)
    {
        List<ShopperViewBasket> viewBaskets = null;
        ShopperViewBasket view = null;

        try
        {
            LOGGER.info("Execute ShopperManagerDAO getViewBasket");

            DAOUtils daoUtils = DAOUtils.getInstance();

            viewBaskets = new ArrayList<ShopperViewBasket>();
            if (byAgent == true)
            {
                conn = daoUtils.getConnection();
                String sql = daoUtils.getString("shopper.view.basket.agent");
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, shopperId);
                pstmt.setBoolean(2, byAgent);
                pstmt.setInt(3, agent.getAgentId());
                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    view = new ShopperViewBasket();
                    view.setItem_sku(Constants.convertValueEmpty(rs.getString("item_sku")));
                    view.setName(Constants.convertValueEmpty(rs.getString("name")));
                    view.setItem_id(rs.getInt("item_id"));
                    view.setPlaced_price(rs.getFloat("placed_price"));
                    view.setCategory_id(rs.getInt("category_id"));
                    view.setAttribute05(Constants.convertValueEmpty(rs.getString("attribute13")));
                    view.setAttribute06(Constants.convertValueEmpty(rs.getString("attribute06")));
                    view.setAttribute18(Constants.convertValueEmpty(rs.getString("attribute18")));
                    view.setAttribute09(Constants.convertValueEmpty(rs.getString("attribute09")));
                    System.out.println(" getViewBasket Attribute 09 :"+view.getAttribute09());
                    
                    if (view.getCategory_id() == 11946 || view.getCategory_id() == 11947 ||  view.getCategory_id() == 11949)
                    {

                        view.setSpeed(Float.parseFloat(view.getAttribute05()));

                    }
                    /*else if (view.getCategory_id() == 11949)
                    {
                        view.setSpeed(Float.parseFloat(view.getAttribute06()));
                    }*/

                    else if (view.getCategory_id() == 11950)
                    {
                        view.setSpeed(Float.parseFloat(view.getAttribute09()));
                    }
                    
                    //else if (view.getCategory_id() == 11961 || view.getCategory_id() == 11962 || view.getCategory_id() == 11963)
                    //{
                    //    view.setSpeed(Float.parseFloat(view.getAttribute09()));
                    //}
                                        

                    else
                    {
                        view.setSpeed(new Float(0));
                    }


                    viewBaskets.add(view);

                }

            }
            else
            {
                conn = daoUtils.getConnection();
                String sql = daoUtils.getString("shopper.view.basket");
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, shopperId);
                pstmt.setBoolean(2, byAgent);
                rs = pstmt.executeQuery();

                while (rs.next())
                {
                    view = new ShopperViewBasket();
                    view.setItem_sku(Constants.convertValueEmpty(rs.getString("item_sku")));
                    view.setName(Constants.convertValueEmpty(rs.getString("name")));
                    view.setItem_id(rs.getInt("item_id"));
                    view.setPlaced_price(rs.getFloat("placed_price"));
                    view.setCategory_id(rs.getInt("category_id"));
                    view.setAttribute05(Constants.convertValueEmpty(rs.getString("attribute13")));
                    view.setAttribute06(Constants.convertValueEmpty(rs.getString("attribute06")));
                    view.setAttribute18(Constants.convertValueEmpty(rs.getString("attribute18")));
                    view.setAttribute09(Constants.convertValueEmpty(rs.getString("attribute09")));

                    if (view.getCategory_id() == 11946 || view.getCategory_id() == 11947 || view.getCategory_id() == 11949)
                    {

                        view.setSpeed(Float.parseFloat(view.getAttribute05()));

                    }
                    /*else if (view.getCategory_id() == 11949)
                    {
                        view.setSpeed(Float.parseFloat(view.getAttribute06()));
                    }*/

                    else if (view.getCategory_id() == 11950)
                    {
                        view.setSpeed(Float.parseFloat(view.getAttribute09()));
                    }

                    else if (view.getCategory_id() == 11961 || view.getCategory_id() == 11962 || view.getCategory_id() == 11963)
                    {
                        view.setSpeed(Float.parseFloat(rs.getString("attribute09")));
                    }
                    
                    else
                    {
                        view.setSpeed(new Float(0));
                    }

                    viewBaskets.add(view);

                }

            }
        }
        catch (Exception e)
        {
            LOGGER.info("Execute ShopperManagerDAO getViewBasket - Exception ");
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return viewBaskets;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<ShopperViewBasket> getHeldOrderGeneral(final String shopperId, Agent agent, String type)
    {
        List<ShopperViewBasket> viewBaskets = new ArrayList<ShopperViewBasket>();

        try
        {

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = "";
            if (agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("shopper.view.basket.heldordergeneral");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);

            }
            else if (!agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.view.basket.agent.general");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);
                pstmt.setString(2, String.valueOf(agent.getAgentId()));

            }
            else
            {
                query = daoUtil.getString("sql.order.view.basket.customer.notadmin");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);

            }

            System.out.println("GET list query " + query);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ShopperViewBasket view = new ShopperViewBasket();
                view.setHeldOrder(Constants.convertValueEmpty(rs.getString("held_order")));
                view.setOrder_id(Constants.convertValueEmpty(rs.getString("order_id")));
                view.setExpirationdate(Constants.convertValueEmpty(rs.getString("exp_date")));
                view.setUserHold(Constants.convertValueEmpty(rs.getString("userHold")));
                viewBaskets.add(view);
            }

        }
        catch (Exception e)
        {
            //  System.out.println(e.getMessage());
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return viewBaskets;
        //1return viewBaskets;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<ShopperViewBasket> getHeldOrderGeneralCustomer(final String shopperId)
    {
        List<ShopperViewBasket> viewBaskets = new ArrayList<ShopperViewBasket>();

        try
        {

            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();
            String sql = daoUtils.getString("shopper.view.basket.heldordergeneral.customer");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopperId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ShopperViewBasket view = new ShopperViewBasket();
                view.setHeldOrder(Constants.convertValueEmpty(rs.getString("held_order")));
                view.setOrder_id(Constants.convertValueEmpty(rs.getString("order_id")));
                view.setExpirationdate(Constants.convertValueEmpty(rs.getString("exp_date")));
                view.setUserHold(Constants.convertValueEmpty(rs.getString("userHold")));
                viewBaskets.add(view);
            }

        }
        catch (Exception e)
        {

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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return viewBaskets;
        //1return viewBaskets;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<ShopperViewBasket> getHeldOrderGeneralAgent(final String shopperId, Agent agent, String type)
    {
        List<ShopperViewBasket> viewBaskets = new ArrayList<ShopperViewBasket>();

        try
        {

            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String query = "";
            if (agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.view.basket.agent.admin");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);

            }
            else if (agent.isAdmin() && type.equals("CUSTOMER"))
            {
                query = daoUtil.getString("sql.order.view.basket.admin.customer");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);

            }
            else if (!agent.isAdmin() && type.equals("AGENT"))
            {
                query = daoUtil.getString("sql.order.view.basket.agent");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);
                pstmt.setString(2, String.valueOf(agent.getAgentId()));

            }
            else if (!agent.isAdmin() && type.equals("CUSTOMER"))
            {
                query = daoUtil.getString("sql.order.view.basket.customer.notadmin");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);

            }
            else
            {
                query = daoUtil.getString("shopper.view.basket.heldordergeneral");
                pstmt = conn.prepareStatement(query);
                pstmt.setString(1, shopperId);
                // pstmt.setString(2, String.valueOf(agent.getAgentId()));

            }
            System.out.println("GET list query " + query);

            rs = pstmt.executeQuery();
            while (rs.next())
            {
                ShopperViewBasket view = new ShopperViewBasket();
                view.setHeldOrder(Constants.convertValueEmpty(rs.getString("held_order")));
                view.setOrder_id(Constants.convertValueEmpty(rs.getString("order_id")));
                view.setExpirationdate(Constants.convertValueEmpty(rs.getString("exp_date")));
                view.setUserHold(Constants.convertValueEmpty(rs.getString("userHold")));

                viewBaskets.add(view);
            }

        }
        catch (Exception e)
        {
            //  System.out.println(e.getMessage());
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return viewBaskets;
        //1return viewBaskets;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<ShopperViewBasket>
     **/
    public ShopperViewBasket getHeldOrderGeneralSecond(final String shopperId, final ShopperViewBasket shopper)
    {

        ShopperViewBasket view = null;
        try
        {
            DAOUtils daoUtils = DAOUtils.getInstance();
            conn = daoUtils.getConnection();

            view = new ShopperViewBasket();
            List<ShopperViewBasket> resultListInside = new ArrayList<ShopperViewBasket>();
            view.setOrder_id(shopper.getOrder_id());
            view.setHeldOrder(shopper.getHeldOrder());
            view.setShopper_id(shopperId);
            view.setExpirationdate(shopper.getExpirationdate());
            view.setUserHold(Constants.convertValueEmpty(shopper.getUserHold()));
            String dateExpiration = "";
            String[] cutDate = shopper.getExpirationdate().split(" ");
            String resultDateExpiration = cutDate[0].replaceAll("-", "/");
            dateExpiration = dateExpiration.concat(resultDateExpiration.substring(5, 10).concat("/").concat(resultDateExpiration.substring(0, 4)));

            view.setExpirationdate(dateExpiration);

            String sql = daoUtils.getString("shopper.view.basket.heldordergeneraldetail");
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, shopper.getHeldOrder());
            rs = pstmt.executeQuery();

            while (rs.next())
            {
                ShopperViewBasket viewInside = new ShopperViewBasket();
                viewInside.setItem_sku(Constants.convertValueEmpty(rs.getString("item_sku")));
                viewInside.setName(Constants.convertValueEmpty(rs.getString("name")));
                viewInside.setItem_id(rs.getInt("item_id"));
                viewInside.setPlaced_price(rs.getFloat("placed_price"));
                viewInside.setCategory_id(rs.getInt("category_id"));
                viewInside.setAttribute05(Constants.convertValueEmpty(rs.getString("attribute13")));
                viewInside.setAttribute06(Constants.convertValueEmpty(rs.getString("attribute06")));
                viewInside.setAttribute18(Constants.convertValueEmpty(rs.getString("attribute18")));
                viewInside.setAttribute09(Constants.convertValueEmpty(rs.getString("attribute09")));
                System.out.println("Attribute 09 : "+viewInside.getAttribute09());
                if (viewInside.getCategory_id() == 11946 || viewInside.getCategory_id() == 11947 || viewInside.getCategory_id() == 11949)
                {

                    viewInside.setSpeed(Float.parseFloat(viewInside.getAttribute05()));

                }
/*                else if (viewInside.getCategory_id() == 11949)
                {

                    viewInside.setSpeed(Float.parseFloat(viewInside.getAttribute06()));
                }*/

                else if (viewInside.getCategory_id() == 11950)
                {
                    viewInside.setSpeed(Float.parseFloat(viewInside.getAttribute09()));
                }
                
                //else if (viewInside.getCategory_id() == 11961 || viewInside.getCategory_id() == 11963 || viewInside.getCategory_id() == 11962)
                //{
                //    viewInside.setSpeed(Float.parseFloat(rs.getString("attribute09")));
                //}
                
                else
                {
                    viewInside.setSpeed(new Float(0));
                }


                resultListInside.add(viewInside);
                System.out.println("Speed : "+viewInside.getSpeed());
            }
            view.setListShopperViewBasket(resultListInside);
            //resultList.add(view);
        }
        catch (Exception e)
        {
            System.out.println("Exception - ShopperManagerDAO - getHeldOrderGeneralSecond");
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return view;
    }

    public Boolean performHeld(String mscsShopperID, String held_order, int byAgent, Agent agent)
    {
        Boolean flag = false;
        try
        {

            //Logger.info("Execute CustomerDAO - Function performCheckout ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            String sql1 = daoUtil.getString("shopper.view.basket.held");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, held_order);
            int a = pstmt.executeUpdate();
            pstmt = null;
            int x = 0;
            int y = 0;
            if (byAgent == 1)
            {

                String sql3 = daoUtil.getString("shopper.view.basket.held2.agent");
                pstmt = conn.prepareStatement(sql3);
                pstmt.setString(1, mscsShopperID);
                pstmt.executeUpdate();
                pstmt = null;

                String sql4 = daoUtil.getString("shopper.view.basket.held3.agent");
                pstmt = conn.prepareStatement(sql4);
                pstmt.setInt(1, agent.getAgentId());
                pstmt.setString(2, mscsShopperID);
                pstmt.setString(3, held_order);
                x = pstmt.executeUpdate();
                pstmt = null;

                String sql2 = daoUtil.getString("shopper.view.basket.held1.agent");
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, mscsShopperID);
                pstmt.setString(2, held_order);
                y = pstmt.executeUpdate();
                pstmt = null;
            }
            else
            {
                String sql3 = daoUtil.getString("shopper.view.basket.held2");
                pstmt = conn.prepareStatement(sql3);
                pstmt.setString(1, mscsShopperID);
                pstmt.executeUpdate();
                pstmt = null;

                String sql4 = daoUtil.getString("shopper.view.basket.held3.customer");
                pstmt = conn.prepareStatement(sql4);
                pstmt.setString(1, mscsShopperID);
                pstmt.setString(2, held_order);
                x = pstmt.executeUpdate();
                pstmt = null;

                String sql2 = daoUtil.getString("shopper.view.basket.held1.customer");
                pstmt = conn.prepareStatement(sql2);
                pstmt.setString(1, mscsShopperID);
                pstmt.setString(2, held_order);
                y = pstmt.executeUpdate();
                pstmt = null;
            }

            String sql5 = daoUtil.getString("shopper.view.basket.held4");
            pstmt = conn.prepareStatement(sql5);
            pstmt.setString(1, held_order);
            int z = pstmt.executeUpdate();
            pstmt = null;

            if (a > 0 && x > 0 && y > 0 && z > 0)
            {
                flag = true;
            }
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            flag = false;
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

    public Boolean performCancel(String mscsShopperID)
    {
        Boolean flag = false;
        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            conn.setAutoCommit(false);

            String sql1 = daoUtil.getString("shopper.view.basket.cancel");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, mscsShopperID);
            pstmt.executeUpdate();
            pstmt = null;

            String sql2 = daoUtil.getString("shopper.view.basket.cancel1");
            pstmt = conn.prepareStatement(sql2);
            pstmt.setString(1, mscsShopperID);
            pstmt.executeUpdate();
            pstmt = null;

            flag = true;
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            flag = false;
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

    public Boolean setExpiration(String expirationDate, String orderId, String shopperid)
    {
        Boolean flag = false;
        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.view.basket.update.expriration.date");
            pstmt = conn.prepareStatement(sql1);
            expirationDate = expirationDate.replaceAll("/", "-");
            String dayExpri = "";
            dayExpri = dayExpri.concat(expirationDate.substring(6, 10).concat("-").concat(expirationDate.substring(0, 5)));

            java.sql.Timestamp fromdateTimestamp = Timestamp.valueOf(dayExpri + " 23:59:59");
            pstmt.setTimestamp(1, fromdateTimestamp);
            pstmt.setString(2, shopperid);
            pstmt.setString(3, orderId);
            pstmt.executeUpdate();

            flag = true;
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            flag = false;
        }
        finally
        {
            try
            {

                if (pstmt != null)
                    pstmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public String selectExpirationDateBefore(String orderId, String shopperid)
    {
        String expirationDateBefore = "";
        String resultDateBefore = "";
        try
        {

            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.view.basket.select.expriration.date");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopperid);
            pstmt.setString(2, orderId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                expirationDateBefore = Constants.convertValueEmpty(rs.getString(1));

            }
            String[] expirationDateBeforeResult = expirationDateBefore.split(" ");
            expirationDateBefore = expirationDateBeforeResult[0];
            expirationDateBefore = expirationDateBefore.replaceAll("-", "/");
            resultDateBefore = resultDateBefore.concat(expirationDateBefore.substring(5, 10).concat("/").concat(expirationDateBefore.substring(0, 4)));
            return resultDateBefore;
            //   flag = true;                             
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return resultDateBefore;
    }

    public String selectCreatedDate(String orderId, String shopperid)
    {
        String createdDateBefore = "";
        String resultDateBefore = "";
        try
        {

            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.view.basket.select.created.date");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, shopperid);
            pstmt.setString(2, orderId);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                createdDateBefore = Constants.convertValueEmpty(rs.getString(1));

            }
            String[] expirationDateBeforeResult = createdDateBefore.split(" ");
            createdDateBefore = expirationDateBeforeResult[0];
            createdDateBefore = createdDateBefore.replaceAll("-", "/");
            resultDateBefore = resultDateBefore.concat(createdDateBefore.substring(5, 10).concat("/").concat(createdDateBefore.substring(0, 4)));

            return resultDateBefore;
            //   flag = true;                             
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return resultDateBefore;
    }

    public List<Note> selectSubjectType()
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;

        try
        {

            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.select.subject.type");
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                note = new Note();
                note.setSubject(rs.getString("subject"));
                note.setOrderBy(rs.getString("orderBy"));
                notes.add(note);
            }

        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return notes;
    }

    public List<Note> selectGroupSubject()
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;

        try
        {

            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.select.group.subject");
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                note = new Note();
                note.setSubject(rs.getString("subject"));
                note.setNoteType(rs.getString("noteType"));
                note.setIndexKey(rs.getString("indexKey"));
                notes.add(note);
            }

        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return notes;
    }

    public String selectindexKey(String subject, String noteType)
    {
        // List<Note> notes = new ArrayList<Note>();

        String indexKey = "";
        try
        {

            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.select.index.key");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, subject);
            pstmt.setString(2, noteType);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                indexKey = rs.getString("indexKey");
            }

        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return indexKey;
    }

    public Boolean actionAddNote(Note note)
    {
        Boolean flag = false;
        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shoppert.insert.note");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, note.getShopper_id());
            pstmt.setInt(2, note.getAgent_id());
            pstmt.setTimestamp(3, note.getTimeOff());
            pstmt.setString(4, note.getTopic());
            pstmt.setString(5, note.getNotes());
            pstmt.setString(6, note.getOrderNumber());
            pstmt.setInt(7, Integer.valueOf(note.getIndexKey()));
            pstmt.executeUpdate();

            flag = true;
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            flag = false;
        }
        finally
        {
            try
            {

                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public Boolean insertAttempt(String fldOrder, String UserName)
    {
        Boolean flag = false;
        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shoppert.insert.attempt");
            pstmt = conn.prepareStatement(sql1);

            pstmt.setString(1, fldOrder);
            pstmt.setString(2, UserName);
            pstmt.executeUpdate();

            flag = true;
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            flag = false;
        }
        finally
        {
            try
            {

                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return flag;
    }

    public Note selectAgentIdInfo(String mscssid)
    {
        //  List<Note> notes = new ArrayList<Note>();
        Note note = null;

        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.select.agent.id.edit.expiration");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, mscssid);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                note = new Note();
                note.setAgent_id(rs.getInt("agent_id"));
                note.setAgent_id_exp(rs.getTimestamp("agent_id_exp"));
                //  notes.add(note); 
            }
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return note;
    }

    public List<Note> selectAdminInfo()
    {
        List<Note> notes = new ArrayList<Note>();
        Note note;

        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.select.edit.expiration.admin.user");
            pstmt = conn.prepareStatement(sql1);
            rs = pstmt.executeQuery();
            while (rs.next())
            {
                note = new Note();
                note.setAgent_id(rs.getInt("agent_id"));
                note.setUserName(rs.getString("UserName"));
                notes.add(note);
            }
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            // flag = false;    
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
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return notes;
    }

    public Boolean insertShopperCommission(String agent_ID, String shopperId)
    {
        Boolean flag = false;
        try
        {
            //Logger.info("Execute CustomerDAO - Function performCheckout ");            
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();
            String sql1 = daoUtil.getString("shopper.update.expiration");
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, agent_ID);
            pstmt.setString(2, shopperId);
            pstmt.executeUpdate();
            flag = true;
        }
        catch (Exception e)
        {
            // Logger.warning("ERROR Execute DAO");
            e.printStackTrace();
            flag = false;
        }
        finally
        {
            try
            {

                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
                if (conn != null)
                    conn.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
        return flag;
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
            //   LOGGER.info("Execute DAO Count");
            stmt = con.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if (res.next())
            {
                totalRecord = res.getInt(1);
            }
        }
        catch (Exception e)
        {
            //  LOGGER.warning("ERROR Execute DAO");
            e.printStackTrace();
        }
        return totalRecord;
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

}
