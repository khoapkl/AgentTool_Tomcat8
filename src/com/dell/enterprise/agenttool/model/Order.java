/**
 * 
 */
package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author linhdo
 * 
 */
public class Order implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private int id;
    private int orderId;
    private String dayOrder;
    private String ship_to_name;
    private int listing;
    private String orderType;
    private int item;
    private int discount;
    private BigDecimal total_total;
    private BigDecimal freight_total;
    private BigDecimal oadjust_total;
    private NoteBook note;
    private Desktop desk;
    private String agentId;
    private String ship_to_company;
    private String shopId;
    private int shopNum;
    private int totalRow;
    private String time;
    private String status;
    private short byAgent;

    /**
     * @return the totalRow
     */
    public int getTotalRow()
    {
        return totalRow;
    }

    /**
     * @param totalRow
     *            the totalRow to set
     */
    public void setTotalRow(int totalRow)
    {
        this.totalRow = totalRow;
    }

    /**
     * @return the shopNum
     */
    public int getShopNum()
    {
        return shopNum;
    }

    /**
     * @param shopNum
     *            the shopNum to set
     */
    public void setShopNum(int shopNum)
    {
        this.shopNum = shopNum;
    }

    /**
     * @return the shopId
     */
    public String getShopId()
    {
        return shopId;
    }

    /**
     * @param shopId
     *            the shopId to set
     */
    public void setShopId(String shopId)
    {
        this.shopId = shopId;
    }

    /**
     * @return the ship_to_company
     */
    public String getShip_to_company()
    {
        return ship_to_company;
    }

    /**
     * @param shipToCompany
     *            the ship_to_company to set
     */
    public void setShip_to_company(String shipToCompany)
    {
        ship_to_company = shipToCompany;
    }

    /**
     * @return the agentId
     */
    public String getAgentId()
    {
        return agentId;
    }

    /**
     * @param agentId
     *            the agentId to set
     */
    public void setAgentId(String agentId)
    {
        this.agentId = agentId;
    }

    /**
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * @return the orderId
     */
    public int getOrderId()
    {
        return orderId;
    }

    /**
     * @param orderId
     *            the orderId to set
     */
    public void setOrderId(int orderId)
    {
        this.orderId = orderId;
    }

    /**
     * @return the ship_to_name
     */
    public String getShip_to_name()
    {
        return ship_to_name;
    }

    /**
     * @param shipToName
     *            the ship_to_name to set
     */
    public void setShip_to_name(String shipToName)
    {
        ship_to_name = shipToName;
    }

    /**
     * @return the listing
     */
    public int getListing()
    {
        return listing;
    }

    /**
     * @param listing
     *            the listing to set
     */
    public void setListing(int listing)
    {
        this.listing = listing;
    }

    /**
     * @return the orderType
     */
    public String getOrderType()
    {
        return orderType;
    }

    /**
     * @param orderType
     *            the orderType to set
     */
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    /**
     * @return the item
     */
    public int getItem()
    {
        return item;
    }

    /**
     * @param item
     *            the item to set
     */
    public void setItem(int item)
    {
        this.item = item;
    }

    /**
     * @return the discount
     */
    public int getDiscount()
    {
        return discount;
    }

    /**
     * @param discount
     *            the discount to set
     */
    public void setDiscount(int discount)
    {
        this.discount = discount;
    }

    /**
     * @return the total_total
     */
    public BigDecimal getTotal_total()
    {
        return total_total;
    }

    /**
     * @param totalTotal
     *            the total_total to set
     */
    public void setTotal_total(BigDecimal totalTotal)
    {
        total_total = totalTotal;
    }

    /**
     * @return the freight_total
     */
    public BigDecimal getFreight_total()
    {
        return freight_total;
    }

    /**
     * @param freightTotal
     *            the freight_total to set
     */
    public void setFreight_total(BigDecimal freightTotal)
    {
        freight_total = freightTotal;
    }

    /**
     * @return the oadjust_total
     */
    public BigDecimal getOadjust_total()
    {
        return oadjust_total;
    }

    /**
     * @param oadjustTotal
     *            the oadjust_total to set
     */
    public void setOadjust_total(BigDecimal oadjustTotal)
    {
        oadjust_total = oadjustTotal;
    }

    /**
     * @return the note
     */
    public NoteBook getNote()
    {
        return note;
    }

    /**
     * @param note
     *            the note to set
     */
    public void setNote(NoteBook note)
    {
        this.note = note;
    }

    /**
     * @return the desk
     */
    public Desktop getDesk()
    {
        return desk;
    }

    /**
     * @param desk
     *            the desk to set
     */
    public void setDesk(Desktop desk)
    {
        this.desk = desk;
    }

    /**
     * @param dayOrder
     *            the dayOrder to set
     */
    public void setDayOrder(String dayOrder)
    {
        this.dayOrder = dayOrder;
    }

    /**
     * @return the dayOrder
     */
    public String getDayOrder()
    {
        return dayOrder;
    }

    /**
     * @return the time
     */
    public String getTime()
    {
        return time;
    }

    /**
     * @param time
     *            the time to set
     */
    public void setTime(String time)
    {
        this.time = time;
    }

    /**
     * @return the status
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * @param status
     *            the status to set
     */
    public void setStatus(String status)
    {
        this.status = status;
    }

    /**
     * @return the byAgent
     */
    public short getByAgent()
    {
        return byAgent;
    }

    /**
     * @param byAgent
     *            the byAgent to set
     */
    public void setByAgent(short byAgent)
    {
        this.byAgent = byAgent;
    }
}
