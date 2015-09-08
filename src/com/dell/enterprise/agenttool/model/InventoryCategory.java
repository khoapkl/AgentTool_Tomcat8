/*
 * FILENAME
 *     InventoryCategory.java
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
import java.util.Date;
import java.util.Map;

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
public class InventoryCategory implements Serializable
{
    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private int category_id;
    private int parent_id;
    private String name;
    private String description;
    private String imageURL;
    private Date data_changed;
    private String show_short;
    private int show_short_count;
    private Map<String, String> mapAttributes;

    /**
     * @return the category_id
     */
    public int getCategory_id()
    {
        return category_id;
    }

    /**
     * @param categoryId
     *            the category_id to set
     */
    public void setCategory_id(int categoryId)
    {
        category_id = categoryId;
    }

    /**
     * @return the parent_id
     */
    public int getParent_id()
    {
        return parent_id;
    }

    /**
     * @param parentId
     *            the parent_id to set
     */
    public void setParent_id(int parentId)
    {
        parent_id = parentId;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the imageURL
     */
    public String getImageURL()
    {
        return imageURL;
    }

    /**
     * @param imageURL
     *            the imageURL to set
     */
    public void setImageURL(String imageURL)
    {
        this.imageURL = imageURL;
    }

    /**
     * @return the data_changed
     */
    public Date getData_changed()
    {
        return data_changed;
    }

    /**
     * @param dataChanged
     *            the data_changed to set
     */
    public void setData_changed(Date dataChanged)
    {
        data_changed = dataChanged;
    }

    /**
     * @return the show_short
     */
    public String getShow_short()
    {
        return show_short;
    }

    /**
     * @param showShort
     *            the show_short to set
     */
    public void setShow_short(String showShort)
    {
        show_short = showShort;
    }

    /**
     * @return the show_short_count
     */
    public int getShow_short_count()
    {
        return show_short_count;
    }

    /**
     * @param showShortCount
     *            the show_short_count to set
     */
    public void setShow_short_count(int showShortCount)
    {
        show_short_count = showShortCount;
    }

    /**
     * @return the mapAttributes
     */
    public Map<String, String> getMapAttributes()
    {
        return mapAttributes;
    }

    /**
     * @param mapAttributes
     *            the mapAttributes to set
     */
    public void setMapAttributes(Map<String, String> mapAttributes)
    {
        this.mapAttributes = mapAttributes;
    }

}
