/*
 *     ShopperManagerService.java
 * FILENAME
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

import java.util.ArrayList;
import java.util.List;

import com.dell.enterprise.agenttool.DAO.ShopperManagerDAO;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Note;
import com.dell.enterprise.agenttool.model.OrderShopper;
import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.model.ShopperViewBasket;
import com.dell.enterprise.agenttool.model.ShopperViewReceipts;

/**
 * @author hungnguyen, thuynguyen
 **/
public class ShopperManagerService
{
    private int totalRecord = 0;

    /**
     * @param shopperId
     *            shopperId
     * @return Shopper
     **/
    public Shopper getShopperDetails(final String shopperId)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getShopperDetails(shopperId);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return int
     **/
    public int getShopperReceipts(final String shopperId )
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getShopperReceipts(shopperId);
    }
    /**
     * @param shopperId
     *            shopperId
     * @return int
     **/
    public int getShopperReceipts_AgentStore(final String shopperId )
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getShopperReceipts_AgentStore(shopperId);
    }


    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<Note> getShopperNotes(final String shopperId)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.getShopperNotes(shopperId);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public int getSizeNotes(final String shopperId)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.getSizeNotes(shopperId);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<Note> getShopperNotes(final String shopperId, int page)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.getShopperNotes(shopperId, page);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<Note> searchNotes(final String shopperId, int page) throws Exception
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        List<Note> list = dao.searchNotes(shopperId, page);
        this.setTotalRecord(dao.getTotalRecord());
        return list;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return List<Note>
     **/
    public List<Note> searchMoreNotes(final String shopperId, int page) throws Exception
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        List<Note> list = dao.searchMoreNotes(shopperId, page);
        this.setTotalRecord(dao.getTotalRecord());
        return list;
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public ShopperViewReceipts getViewReceipts(final String shopperId)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getViewReceipts(shopperId);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<ShopperViewReceipts> getViewTotal(final String shopperId, int page)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        List<ShopperViewReceipts> list=dao.getViewTotal(shopperId, page);
        this.setTotalRecord(dao.getTotalRecord());
        return list;
    }
    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<ShopperViewReceipts> getViewTotal_AgentStore(final String shopperId, int page)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        List<ShopperViewReceipts> list=dao.getViewTotal_AgentStore(shopperId, page);
        this.setTotalRecord(dao.getTotalRecord());
        return list;
    }
    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/

    public List<ShopperViewBasket> getViewBasket(final String shopperId,Boolean byAgent,Agent agent)

    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.getViewBasket(shopperId,byAgent,agent);

    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<ShopperViewBasket> getViewBasketGeneral(final String shopperId, Agent agent, String type)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getHeldOrderGeneral(shopperId, agent, type);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<ShopperViewBasket> getViewBasketGeneralCustomer(final String shopperId)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getHeldOrderGeneralCustomer(shopperId);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<ShopperViewBasket> getViewBasketGeneralAgent(final String shopperId, Agent agent, String type)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();

        return dao.getHeldOrderGeneralAgent(shopperId, agent, type);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<ShopperViewBasket> getViewBasketGeneralSecond(final String shopperId, final List<ShopperViewBasket> list)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        List<ShopperViewBasket> listShopperViewBasket = new ArrayList<ShopperViewBasket>();
        for (ShopperViewBasket shopperViewBasket : list)
        {
            listShopperViewBasket.add(dao.getHeldOrderGeneralSecond(shopperId, shopperViewBasket));
        }

        return listShopperViewBasket;
    }

    /**
     * @param mscsShopperID
     *            , held_order
     * 
     * @return Boolean
     **/
    public Boolean performHeld(final String mscsShopperID, String held_order, int status, Agent agent)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        Boolean check = dao.performHeld(mscsShopperID, held_order, status, agent);
        return check;
    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public Boolean performCancel(final String mscsShopperID)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        //Boolean check=false;
        try
        {
            dao.performCancel(mscsShopperID);
            return true;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return false;
        }

    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public Boolean setExpiration(final String expirationDate, String orderId, String shopperid)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        //Boolean check=false;
        try
        {
            dao.setExpiration(expirationDate, orderId, shopperid);
            return true;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return false;
        }

    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public String selectExpirationDateBefore(final String orderId, String shopperid)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        //Boolean check=false;
        try
        {
            String ExpirationDateBefore = dao.selectExpirationDateBefore(orderId, shopperid);
            return ExpirationDateBefore;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return "";
        }

    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public String selectCreatedDate(final String orderId, String shopperid)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        //Boolean check=false;
        try
        {
            String DateBefore = dao.selectCreatedDate(orderId, shopperid);
            return DateBefore;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return "";
        }

    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<Note> selectSubjectType()
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.selectSubjectType();
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<Note> selectGroupSubject()
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.selectGroupSubject();
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public String selectindexKey(String subject, String noteType)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.selectindexKey(subject, noteType);
    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public Boolean actionAddNote(final Note note)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        //Boolean check=false;
        try
        {
            dao.actionAddNote(note);
            return true;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return false;
        }

    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public Boolean insertAttempt(final String fldOrder, String UserName)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        //Boolean check=false;
        try
        {
            dao.insertAttempt(fldOrder, UserName);
            return true;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return false;
        }

    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public Note selectAgentIdInfo(String mscssid)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.selectAgentIdInfo(mscssid);
    }

    /**
     * @param shopperId
     *            shopperId
     * @return <ShopperViewBasket>
     **/
    public List<Note> selectAdminInfo()
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
        return dao.selectAdminInfo();
    }

    /**
     * @param mscsShopperID
     * 
     * @return Boolean
     **/
    public Boolean insertShopperCommission(final String agent_ID, String shopperId)
    {
        ShopperManagerDAO dao = new ShopperManagerDAO();
     
            dao.insertShopperCommission(agent_ID,  shopperId);
            return null;
      

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
