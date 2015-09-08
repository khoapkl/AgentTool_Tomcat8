package com.dell.enterprise.agenttool.model;

import java.io.Serializable;

public class ProductAttribute implements Serializable
{

    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private int attribute_id;
    private int category_id;
    private String attribute_number;
    private String attribute_name;
    private String units;
    private Boolean isVisible;
    private Boolean isSearchable;
    private int importLocation;
    private String importColumn;
    private int sortOrder;
    private int reorderList;

    /**
     * @return the attribute_id
     */
    public int getAttribute_id()
    {
        return attribute_id;
    }

    /**
     * @param attributeId
     *            the attribute_id to set
     */
    public void setAttribute_id(int attributeId)
    {
        attribute_id = attributeId;
    }

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
     * @return the attribute_number
     */
    public String getAttribute_number()
    {
        return attribute_number;
    }

    /**
     * @param attributeNumber
     *            the attribute_number to set
     */
    public void setAttribute_number(String attributeNumber)
    {
        attribute_number = attributeNumber;
    }

    /**
     * @return the attribute_name
     */
    public String getAttribute_name()
    {
        return attribute_name;
    }

    /**
     * @param attributeName
     *            the attribute_name to set
     */
    public void setAttribute_name(String attributeName)
    {
        attribute_name = attributeName;
    }

    /**
     * @return the units
     */
    public String getUnits()
    {
        return units;
    }

    /**
     * @param units
     *            the units to set
     */
    public void setUnits(String units)
    {
        this.units = units;
    }

    /**
     * @return the isVisible
     */
    public Boolean getIsVisible()
    {
        return isVisible;
    }

    /**
     * @param isVisible
     *            the isVisible to set
     */
    public void setIsVisible(Boolean isVisible)
    {
        this.isVisible = isVisible;
    }

    /**
     * @return the isSearchable
     */
    public Boolean getIsSearchable()
    {
        return isSearchable;
    }

    /**
     * @param isSearchable
     *            the isSearchable to set
     */
    public void setIsSearchable(Boolean isSearchable)
    {
        this.isSearchable = isSearchable;
    }

    /**
     * @return the importLocation
     */
    public int getImportLocation()
    {
        return importLocation;
    }

    /**
     * @param importLocation
     *            the importLocation to set
     */
    public void setImportLocation(int importLocation)
    {
        this.importLocation = importLocation;
    }

    /**
     * @return the importColumn
     */
    public String getImportColumn()
    {
        return importColumn;
    }

    /**
     * @param importColumn
     *            the importColumn to set
     */
    public void setImportColumn(String importColumn)
    {
        this.importColumn = importColumn;
    }

    /**
     * @return the sortOrder
     */
    public int getSortOrder()
    {
        return sortOrder;
    }

    /**
     * @param sortOrder
     *            the sortOrder to set
     */
    public void setSortOrder(int sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    /**
     * @return the reorderList
     */
    public int getReorderList()
    {
        return reorderList;
    }

    /**
     * @param reorderList
     *            the reorderList to set
     */
    public void setReorderList(int reorderList)
    {
        this.reorderList = reorderList;
    }

}
