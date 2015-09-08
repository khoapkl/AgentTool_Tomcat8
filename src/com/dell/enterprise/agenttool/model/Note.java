/*
 * FILENAME
 *     Note.java
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

package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hungnguyen
 **/
@SuppressWarnings("serial")
public class Note implements Serializable
{
    private String agentName;
    private String subject;
    private String noteType;
    private Timestamp timeOff;
    private int subjectId;
    private String topic;
    private String orderNumber;
    private String notes;
    private String orderBy;
    private String indexKey;
    private String shopper_id;
    private int agent_id;
    private Timestamp agent_id_exp;
    private String UserName;
    private int totalRow;
    

    /**
     * @return the totalRow
     */
    public int getTotalRow()
    {
        return totalRow;
    }

    /**
     * @param totalRow the totalRow to set
     */
    public void setTotalRow(int totalRow)
    {
        this.totalRow = totalRow;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return UserName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName)
    {
        UserName = userName;
    }

    /**
     * @return the agent_id_exp
     */
    public Timestamp getAgent_id_exp()
    {
        return agent_id_exp;
    }

    /**
     * @param agentIdExp the agent_id_exp to set
     */
    public void setAgent_id_exp(Timestamp agentIdExp)
    {
        agent_id_exp = agentIdExp;
    }

    /**
     * @return the shopper_id
     */
    public String getShopper_id()
    {
        return shopper_id;
    }

    /**
     * @param shopperId the shopper_id to set
     */
    public void setShopper_id(String shopperId)
    {
        shopper_id = shopperId;
    }

    /**
     * @return the agent_id
     */
    public int getAgent_id()
    {
        return agent_id;
    }

    /**
     * @param agentId the agent_id to set
     */
    public void setAgent_id(int agentId)
    {
        agent_id = agentId;
    }

    /**
     * @return the indexKey
     */
    public String getIndexKey()
    {
        return indexKey;
    }

    /**
     * @param indexKey the indexKey to set
     */
    public void setIndexKey(String indexKey)
    {
        this.indexKey = indexKey;
    }

    /**
     * @return the orderBy
     */
    public String getOrderBy()
    {
        return orderBy;
    }

    /**
     * @param orderBy the orderBy to set
     */
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }

    /**
     * @return the agentName
     */
    public String getAgentName()
    {
        return agentName;
    }

    /**
     * @param agentName
     *            the agentName to set
     */
    public void setAgentName(final String agentName)
    {
        this.agentName = agentName;
    }

    /**
     * @return the subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * @param subject
     *            the subject to set
     */
    public void setSubject(final String subject)
    {
        this.subject = subject;
    }

    /**
     * @return the noteType
     */
    public String getNoteType()
    {
        return noteType;
    }

    /**
     * @param noteType
     *            the noteType to set
     */
    public void setNoteType(final String noteType)
    {
        this.noteType = noteType;
    }

    /**
     * @return the timeOff
     */
    public Timestamp getTimeOff()
    {
        return timeOff;
    }

    /**
     * @param timeOff
     *            the timeOff to set
     */
    public void setTimeOff(final Timestamp timeOff)
    {
        this.timeOff = timeOff;
    }

    /**
     * @return the subjectId
     */
    public int getSubjectId()
    {
        return subjectId;
    }

    /**
     * @param subjectId
     *            the subjectId to set
     */
    public void setSubjectId(final int subjectId)
    {
        this.subjectId = subjectId;
    }

    /**
     * @return the topic
     */
    public String getTopic()
    {
        return topic;
    }

    /**
     * @param topic
     *            the topic to set
     */
    public void setTopic(final String topic)
    {
        this.topic = topic;
    }

    /**
     * @return the orderNumber
     */
    public String getOrderNumber()
    {
        return orderNumber;
    }

    /**
     * @param orderNumber
     *            the orderNumber to set
     */
    public void setOrderNumber(final String orderNumber)
    {
        this.orderNumber = (orderNumber == null) ? "" : orderNumber;
    }

    /**
     * @return the notes
     */
    public String getNotes()
    {
        return notes;
    }

    /**
     * @param notes
     *            the notes to set
     */
    public void setNotes(final String notes)
    {
        this.notes = notes;
    }

}
