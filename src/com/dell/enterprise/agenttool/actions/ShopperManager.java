/*
 * FILENAME
 *     ShopperManagement.java
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

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.DiscountAdjustment;
import com.dell.enterprise.agenttool.model.Note;
import com.dell.enterprise.agenttool.model.Order;
import com.dell.enterprise.agenttool.model.OrderHeld;
import com.dell.enterprise.agenttool.model.Shopper;
import com.dell.enterprise.agenttool.model.ShopperViewBasket;
import com.dell.enterprise.agenttool.model.ShopperViewReceipts;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.OrderServices;
import com.dell.enterprise.agenttool.services.ShopperManagerService;
import com.dell.enterprise.agenttool.util.Constants;

/**
 * @author hungnguyen, thuynguyen
 **/
public class ShopperManager extends DispatchAction
{

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
                Cookie[] cookies = request.getCookies();
                for (int i = 0; i < cookies.length; i++)
                {
                    if (cookies[i].getName().equals("userLevel"))
                    {
                        if (cookies[i].getValue().equals("login.do"))
                            forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                    }
                }
                forward = mapping.findForward(Constants.SESSION_TIMEOUT);
            }
            else if (method == null || "".equals(method))
            {
                forward = mapping.findForward(Constants.SHOPPER_MANAGEMENT);
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

        return forward;
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
    public ActionForward getShopperDetails(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW;
        HttpSession session = request.getSession();
        Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
        try
        {
            String shopperId = request.getParameter("shopperId");
            if (shopperId == null)
            {
                shopperId = (String) session.getAttribute("shopperAdd_id");
            }
            ShopperManagerService service = new ShopperManagerService();

            Shopper shopper = service.getShopperDetails(shopperId);
            
            int receipts ;
            if (isCustomer!=null && ((Boolean) isCustomer).booleanValue()){
            	receipts= service.getShopperReceipts_AgentStore(shopperId);	
            }
            else {
            	receipts = service.getShopperReceipts(shopperId);
            }
            	
            List<Note> notes1 = service.searchNotes(shopperId, 1);
            int totalRecord = service.getTotalRecord();
            request.setAttribute(Constants.SHOPPER_ID, shopperId);
            request.setAttribute(Constants.SHOPPER_INFO, shopper);
            request.setAttribute(Constants.SHOPPER_NOTES, notes1);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);

            request.setAttribute(Constants.SHOPPER_RECEIPTS, receipts);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }
        session.removeAttribute("getAttribute");
        return mapping.findForward(forwardName);
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
    public ActionForward getViewReceipts(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_RECEIPTS;

        try
        {
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            Object isCustomer = request.getSession().getAttribute(Constants.IS_CUSTOMER);
            if (userLevel != null)
            {
                String shopperId = request.getParameter("shopperId");
                ShopperManagerService service = new ShopperManagerService();
                ShopperViewReceipts informationViewBasket= service.getViewReceipts(shopperId);
                
                List<ShopperViewReceipts> listViewBack;
                if (isCustomer!=null && ((Boolean) isCustomer).booleanValue()){
                	
                	listViewBack = service.getViewTotal_AgentStore(shopperId, 1);
                }
                else {
                	
                	listViewBack = service.getViewTotal(shopperId, 1);
                }
                int totalRecord = service.getTotalRecord();
                request.setAttribute(Constants.SHOPPER_INFO, informationViewBasket);
                request.setAttribute(Constants.SHOPPER_VIEW_RECEIPTS, listViewBack);
                request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
                request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);

                forwardName = Constants.SHOPPER_VIEW_RECEIPTS;

            }

        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward movingGetViewReceipts(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_RECEIPTS_PAGING;

        try
        {
            ShopperManagerService service = new ShopperManagerService();
            String mscssid = request.getParameter("mscssid");
            // Shopper shopper = service.getShopperDetails(mscssid);

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
                noPage = (totalRecord % 40 > 0) ? (totalRecord / 40 + 1) : (totalRecord / 40);
            }
            ShopperViewReceipts informationViewBasket = service.getViewReceipts(mscssid);
            List<ShopperViewReceipts> listViewBack = service.getViewTotal(mscssid, noPage);
            request.setAttribute(Constants.SHOPPER_INFO, informationViewBasket);
            request.setAttribute(Constants.SHOPPER_VIEW_RECEIPTS, listViewBack);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
            forwardName = Constants.SHOPPER_VIEW_RECEIPTS_PAGING;

        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward getViewBasketCustomer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_BASKET;

        try
        {
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            if (userLevel != null && !userLevel.equals("C"))
            {
                HttpSession session = request.getSession();
                String mscsShopperID = request.getParameter("mscssid");
                String held_order = request.getParameter("held");
                String cancel_order = request.getParameter("cancel");
                String orderId = request.getParameter("orderId");
                ShopperManagerService service = new ShopperManagerService();
                ShopperViewReceipts informationViewBasket = service.getViewReceipts(mscsShopperID);
                Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
                
                Boolean byAgent = false;
                if (agent.getAgentId() > 0)
                {
                    byAgent = true;
                }
                else
                {
                    byAgent = false;
                }
              
                if (mscsShopperID != null && held_order != null && agent != null)
                {
                    int status = 0;
                    if (byAgent)
                        status = 1 ; 
                    else
                        status = 0 ;
                    Boolean flag = service.performHeld(mscsShopperID, held_order,status , agent);
                    if (flag && orderId != null)
                    {
                        CheckoutService checkoutService = new CheckoutService();
                        checkoutService.deleteAvgMhz(orderId);
                    }
                }

                if (cancel_order != null)
                {
                    service.performCancel(mscsShopperID);
                }

                

                List<ShopperViewBasket> listViewBacketDetail = null;
                List<ShopperViewBasket> listViewBacket = service.getViewBasket(mscsShopperID, byAgent, agent);
                List<ShopperViewBasket> listViewBacketGeneral = null;

                listViewBacketGeneral = service.getViewBasketGeneralAgent(mscsShopperID, agent, "CUSTOMER");

                if (listViewBacketGeneral != null)
                {
                    listViewBacketDetail = service.getViewBasketGeneralSecond(mscsShopperID, listViewBacketGeneral);
                }

                OrderServices discountAdjustment = new OrderServices();
                DiscountAdjustment listDiscountAdjustment = discountAdjustment.listDiscountPercentage();

                request.setAttribute(Constants.LIST_ORDER_LIST_DISCOUNT, listDiscountAdjustment);
                request.setAttribute(Constants.SHOPPER_INFO, informationViewBasket);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET, listViewBacket);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET_DETAIL, listViewBacketDetail);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET_SHOPPER_ID, mscsShopperID);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET_ORDER_ID, orderId);
                request.setAttribute(Constants.ATTR_CUSTOMER, true);
                forwardName = Constants.SHOPPER_VIEW_BASKET;
            }
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward getViewBasketAgent(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_BASKET;

        try
        {
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            if (userLevel != null && !userLevel.equals("C"))
            {
                HttpSession session = request.getSession();
                //  String shopperId = request.getParameter("shopperId");      
                String mscsShopperID = request.getParameter("mscssid");
                String held_order = request.getParameter("held");
                String cancel_order = request.getParameter("cancel");
                String orderId = request.getParameter("orderId");
                ShopperManagerService service = new ShopperManagerService();
                Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
                if (session.getAttribute(Constants.IS_CUSTOMER) == null)
                {

                    ShopperViewReceipts informationViewBasket = service.getViewReceipts(mscsShopperID);
                    if (mscsShopperID != null && held_order != null && agent != null)
                    {
                        Boolean flag = service.performHeld(mscsShopperID, held_order, 1, agent);
                        if (flag && orderId != null)
                        {
                            CheckoutService checkoutService = new CheckoutService();
                            checkoutService.deleteAvgMhz(orderId);
                        }
                    }
                    if (cancel_order != null)
                    {
                        service.performCancel(mscsShopperID);
                    }

                    List<ShopperViewBasket> listViewBacketDetail = null;
                    List<ShopperViewBasket> listViewBacket = service.getViewBasket(mscsShopperID, true, agent);
                    List<ShopperViewBasket> listViewBacketGeneral = null;

                    listViewBacketGeneral = service.getViewBasketGeneralAgent(mscsShopperID, agent, "AGENT");

                    if (listViewBacketGeneral != null)
                    {
                        listViewBacketDetail = service.getViewBasketGeneralSecond(mscsShopperID, listViewBacketGeneral);
                    }

                    DiscountAdjustment listDiscountAdjustment = null;
                    OrderServices discountAdjustment = new OrderServices();
                    listDiscountAdjustment = discountAdjustment.listDiscountPercentage();
                    request.setAttribute(Constants.LIST_ORDER_LIST_DISCOUNT, listDiscountAdjustment);

                    request.setAttribute(Constants.SHOPPER_INFO, informationViewBasket);
                    request.setAttribute(Constants.SHOPPER_VIEW_BASKET, listViewBacket);
                    request.setAttribute(Constants.SHOPPER_VIEW_BASKET_DETAIL, listViewBacketDetail);
                    request.setAttribute(Constants.SHOPPER_VIEW_BASKET_SHOPPER_ID, mscsShopperID);
                    request.setAttribute(Constants.SHOPPER_VIEW_BASKET_ORDER_ID, orderId);

                }
            }
            // forwardName = Constants.SHOPPER_VIEW_BASKET;
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward getViewBasket(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_BASKET;
        try
        {
            HttpSession session = request.getSession();
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            if (userLevel != null && !userLevel.equals("C"))
            {
                String mscsShopperID = null;
                if (request.getParameter("mscssid") != null)
                    mscsShopperID = request.getParameter("mscssid").toString();
                String held_order = null;
                if (request.getParameter("held") != null)
                    held_order = request.getParameter("held").toString();
                String cancel_order = null;
                if (request.getParameter("cancel") != null)
                    cancel_order = request.getParameter("cancel").toString();
                String orderId = null;
                if (request.getParameter("orderId") != null)
                    orderId = request.getParameter("orderId").toString();

                ShopperManagerService service = new ShopperManagerService();
                ShopperViewReceipts informationViewBasket = service.getViewReceipts(mscsShopperID);
                Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);

                String statusCustomer = "";
                int addByAgent = 0;
                Boolean byAgent = false;
                if (session.getAttribute(Constants.IS_CUSTOMER) == null)
                {
                    statusCustomer = "AGENT";
                    addByAgent = 1;
                    byAgent = true;
                }
                else if (agent != null)
                {
                    statusCustomer = "CUSTOMER";
                    addByAgent = 0;
                    byAgent = false;
                }

                if (mscsShopperID != null && held_order != null && agent != null)
                {
                    Boolean flag = service.performHeld(mscsShopperID, held_order, addByAgent, agent);
                    if (flag && orderId != null)
                    {
                        System.out.println("OrderID Move to basket : " + orderId);
                        CheckoutService checkoutService = new CheckoutService();
                        checkoutService.deleteAvgMhz(orderId);
                    }
                }

                if (cancel_order != null)
                {
                    service.performCancel(mscsShopperID);
                }

                List<ShopperViewBasket> listViewBacketDetail = null;
                List<ShopperViewBasket> listViewBacket = service.getViewBasket(mscsShopperID, byAgent, agent);
                List<ShopperViewBasket> listViewBacketGeneral = null;

                listViewBacketGeneral = service.getViewBasketGeneral(mscsShopperID, agent, statusCustomer);

                if (listViewBacketGeneral != null)
                {
                    listViewBacketDetail = service.getViewBasketGeneralSecond(mscsShopperID, listViewBacketGeneral);
                }

                DiscountAdjustment listDiscountAdjustment = null;
                OrderServices discountAdjustment = new OrderServices();
                listDiscountAdjustment = discountAdjustment.listDiscountPercentage();
                request.setAttribute(Constants.LIST_ORDER_LIST_DISCOUNT, listDiscountAdjustment);

                request.setAttribute(Constants.SHOPPER_INFO, informationViewBasket);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET, listViewBacket);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET_DETAIL, listViewBacketDetail);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET_SHOPPER_ID, mscsShopperID);
                request.setAttribute(Constants.SHOPPER_VIEW_BASKET_ORDER_ID, orderId);
                request.setAttribute(Constants.ATTR_BASKET_ALL, true);
                forwardName = Constants.SHOPPER_VIEW_BASKET;

            }
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward setExpirationDate(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_SET_EXPRIRATION_DATE;

        try
        {

            //  String shopperId = request.getParameter("shopperId");      
            String expirationDate = request.getParameter("expirationDate");
            String orderId = request.getParameter("orderId");
            String shopperid = request.getParameter("shopperid");

            ShopperManagerService service = new ShopperManagerService();
            String createdDate = service.selectCreatedDate(orderId, shopperid);
            // SimpleDateFormat dateFormat = new SimpleDateFormat("mm/dd/yyyy");
            Date theDateNew = new Date(expirationDate);
            Date theDateOld = new Date(createdDate);
            Date now = new Date(Constants.getCurrentDate());
            /*
             * fix
             */
            if ((theDateNew.after(theDateOld)) && ((theDateNew.after(now)) || ((theDateNew.equals(now)))))
            {
                if (service.setExpiration(expirationDate, orderId, shopperid))
                {
                    request.setAttribute(Constants.SHOPPER_SET_EXPRIRATION_DATE, "true");
                }
                else
                {
                    request.setAttribute(Constants.SHOPPER_SET_EXPRIRATION_DATE, "false");
                }
            }
            else
            {
                request.setAttribute(Constants.SHOPPER_SET_EXPRIRATION_DATE, "before");

            }

            request.setAttribute(Constants.SHOPPER_GET_CREATE_DATE, createdDate);
            forwardName = Constants.SHOPPER_SET_EXPRIRATION_DATE;
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward addNote(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_ADD_NOTES;

        try
        {
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            if (userLevel != null && !userLevel.equals("C"))
            {
                ShopperManagerService service = new ShopperManagerService();
                String mscssid = request.getParameter("mscssid");
                String item_sku = request.getParameter("item_sku");
                String order_id = request.getParameter("order_id");
                List<Note> listSubjectType = service.selectSubjectType();
                List<Note> listGroupSubject = service.selectGroupSubject();
                Shopper shopper = service.getShopperDetails(mscssid);
                request.setAttribute(Constants.SHOPPER_INFO, shopper);
                request.setAttribute(Constants.SHOPPER_ADD_NOTES_LIST_SUBJECT_TYPE, listSubjectType);
                request.setAttribute(Constants.SHOPPER_ADD_NOTES_LIST_GROUP_SUBJECT, listGroupSubject);
                request.setAttribute(Constants.ITEM_SKU, item_sku);
                request.setAttribute(Constants.ORDER_ID_PENDING, order_id);
                request.setAttribute(Constants.SHOPPER_ID, mscssid);
                forwardName = Constants.SHOPPER_ADD_NOTES;
            }
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward actionAddNote(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_ADD_NOTES_SUCCESS;

        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
        try
        {
            String topicPre = request.getParameter("topicPre");
            String subjectType = request.getParameter("subjectType");
            String fldWhen = request.getParameter("fldWhen");
            String fldTopic = request.getParameter("fldTopic");
            String attempt = Constants.convertValueEmpty(request.getParameter("attempt"));
            String mscssid = request.getParameter("mscssid");
            String fldNotes = request.getParameter("fldNotes");
            String fldOrder = request.getParameter("fldOrder");
            int agent_id = agent.getAgentId();
            String userName = agent.getUserName();
            ShopperManagerService service = new ShopperManagerService();
            String indexKey = (String) service.selectindexKey(subjectType, topicPre);
            Note note = new Note();
            note.setShopper_id(mscssid);
            note.setAgent_id(agent_id);
            note.setTimeOff(Timestamp.valueOf(fldWhen));
            note.setTopic(fldTopic);
            note.setNotes(fldNotes);
            note.setOrderNumber(Constants.convertValueEmpty(fldOrder));
            note.setIndexKey(indexKey);
            service.actionAddNote(note);
            session.setAttribute("shopperAdd_id", mscssid);
            if (attempt.equals("on"))
            {
                service.insertAttempt(note.getOrderNumber(), userName);
            }

            forwardName = Constants.SHOPPER_ADD_NOTES_SUCCESS;
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward viewNotes(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_NOTES;

        try
        {
            String userLevel = (String) request.getSession().getAttribute(Constants.USER_LEVEL);
            if (userLevel != null && !userLevel.equals("C"))
            {
                ShopperManagerService service = new ShopperManagerService();
                String mscssid = request.getParameter("mscssid");
                Shopper shopper = service.getShopperDetails(mscssid);
                List<Note> notes = service.searchMoreNotes(mscssid, 1);
                int totalRecord = service.getTotalRecord();
                request.setAttribute(Constants.SHOPPER_ID, mscssid);
                request.setAttribute(Constants.SHOPPER_INFO, shopper);
                request.setAttribute(Constants.SHOPPER_NOTES, notes);
                request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
                request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);

                forwardName = Constants.SHOPPER_VIEW_NOTES;
            }
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward movingNotes(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_VIEW_NOTES;

        try
        {
            ShopperManagerService service = new ShopperManagerService();
            String mscssid = request.getParameter("mscssid");
            // Shopper shopper = service.getShopperDetails(mscssid);

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
                noPage = (totalRecord % 5 > 0) ? (totalRecord / 5 + 1) : (totalRecord / 5);
            }

            List<Note> notes = service.searchNotes(mscssid, noPage);
            //   request.setAttribute(Constants.SHOPPER_ID, mscssid);
            //   request.setAttribute(Constants.SHOPPER_INFO, shopper);            
            request.setAttribute(Constants.SHOPPER_NOTES, notes);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
            forwardName = Constants.SHOPPER_PAGE_FORWARD_NOTES;
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward movingMoreNotes(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_PAGE_FORWARD_MORE_NOTES;

        try
        {
            ShopperManagerService service = new ShopperManagerService();
            String mscssid = request.getParameter("mscssid");
            // Shopper shopper = service.getShopperDetails(mscssid);

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
                noPage = (totalRecord % 10 > 0) ? (totalRecord / 10 + 1) : (totalRecord / 10);
            }

            List<Note> notes = service.searchMoreNotes(mscssid, noPage);
            //   request.setAttribute(Constants.SHOPPER_ID, mscssid);
            //   request.setAttribute(Constants.SHOPPER_INFO, shopper);            
            request.setAttribute(Constants.SHOPPER_NOTES, notes);
            request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
            request.setAttribute(Constants.ORDER_NUMBER_PAGE, noPage);
            forwardName = Constants.SHOPPER_PAGE_FORWARD_MORE_NOTES;
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward editAgentExpiration(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        String forwardName = Constants.SHOPPER_EDIT_AGENT_EXPIRATION;
        HttpSession session = request.getSession();
        try
        {
            if ((Boolean) session.getAttribute(Constants.IS_ADMIN))
            {
                if ("A".equalsIgnoreCase(session.getAttribute(Constants.USER_LEVEL).toString()))
                {
                    ShopperManagerService service = new ShopperManagerService();
                    String mscssid = request.getParameter("mscssid");
                    Shopper shopper = service.getShopperDetails(mscssid);
                    List<Note> listAdminInfo = (List<Note>) service.selectAdminInfo();
                    Note listAgentInfo = (Note) service.selectAgentIdInfo(mscssid);

                    request.setAttribute(Constants.SHOPPER_ID, mscssid);
                    request.setAttribute(Constants.SHOPPER_INFO, shopper);
                    request.setAttribute(Constants.SHOPPER_LIST_ADMIN_INFO, listAdminInfo);
                    request.setAttribute(Constants.SHOPPER_LIST_AGENT_INFO, listAgentInfo);

                    forwardName = Constants.SHOPPER_EDIT_AGENT_EXPIRATION;
                }
            }
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forwardName);
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
    public ActionForward updateAgentExpiration(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {

        String forwardName = Constants.SHOPPER_VIEW;
        HttpSession session = request.getSession();
        Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);
        try
        {
            if ((Boolean) session.getAttribute(Constants.IS_ADMIN))
            {
                if ("A".equalsIgnoreCase(session.getAttribute(Constants.USER_LEVEL).toString()))
                {
                    String shopperId = request.getParameter("shopperId");
                    String statusMode = request.getParameter("mode");
                    String agent_ID = request.getParameter("agent_ID");
                    // String agent_id_exp = request.getParameter("agent_id_exp");

                    if (shopperId == null)
                    {
                        shopperId = (String) session.getAttribute("shopperAdd_id");
                    }
                    ShopperManagerService service = new ShopperManagerService();
                    if ((statusMode.equals("new")) || (statusMode.equals("update")))
                    {
                        service.insertShopperCommission(agent_ID, shopperId);
                    }

                    Shopper shopper = service.getShopperDetails(shopperId);
                    int totalRecord = service.getSizeNotes(shopperId);
                    List<Note> notes = service.searchNotes(shopperId, 1);
                    int receipts;
                    if (isCustomer!=null && ((Boolean) isCustomer).booleanValue()){
                    	receipts = service.getShopperReceipts_AgentStore(shopperId);
                    }
                    else{
                    	receipts = service.getShopperReceipts(shopperId);
                    }
                    //int receipts = service.getShopperReceipts(shopperId);
                    request.setAttribute(Constants.SHOPPER_INFO, shopper);
                    request.setAttribute(Constants.SHOPPER_NOTES, notes);
                    request.setAttribute(Constants.SHOPPER_RECEIPTS, receipts);
                    request.setAttribute(Constants.ORDER_TOTAL, totalRecord);
                    request.setAttribute(Constants.ORDER_NUMBER_PAGE, 1);
                    forwardName = Constants.SHOPPER_VIEW;
                }
            }
        }
        catch (Exception e)
        {
            forwardName = Constants.ERROR_PAGE_VIEW;
        }
        session.removeAttribute("getAttribute");
        return mapping.findForward(forwardName);
    }

}
