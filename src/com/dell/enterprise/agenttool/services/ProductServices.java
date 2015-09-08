/*
 * FILENAME
 *     ProductServices.java
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.dell.enterprise.agenttool.DAO.ProductDAO;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Product;
import com.dell.enterprise.agenttool.model.ProductAttribute;
import com.dell.enterprise.agenttool.util.Constants;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/*
 * Copyright (C) 2001-2003 Colin Bell
 * colbell@users.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
import java.util.ArrayList;
import java.util.List;
/**
 * String handling utilities.
 *
 * @author <A HREF="mailto:colbell@users.sourceforge.net">Colin Bell</A>
 */
class StringUtilities
{
  /**
   * Return <tt>true</tt> if the passed string is <tt>null</tt> or empty.
   *
   * @param str   String to be tested.
   *
   * @return  <tt>true</tt> if the passed string is <tt>null</tt> or empty.
   */
  public static boolean isEmpty(String str)
  {
    return str == null || str.length() == 0;
  }

  /**
   * Return whether the 2 passed strings are equal. This function
   * allows for <TT>null</TT> strings. If <TT>s1</TT> and <TT>s1</TT> are
   * both <TT>null</TT> they are considered equal.
   *
   * @param   str1  First string to check.
   * @param   str2  Second string to check.
   */
  public static boolean areStringsEqual(String str1, String str2)
  {
    if (str1 == null && str2 == null)
    {
      return true;
    }
    if (str1 != null)
    {
      return str1.equals(str2);
    }
    return str2.equals(str1);
  }

  /**
   * Clean the passed string. Replace whitespace characters with a single
   * space. If a <TT>null</TT> string passed return an empty string. E.G.
   * replace
   *
   * [pre]
   * \t\tselect\t* from\t\ttab01
   * [/pre]
   *
   * with
   *
   * [pre]
   * select * from tab01
   * [/pre]
   *
   * @param str String to be cleaned.
   *
   * @return  Cleaned string.
   */
  public static String cleanString(String str)
  {
    final StringBuffer buf = new StringBuffer(str.length());
    char prevCh = ' ';

    for (int i = 0, limit = str.length(); i < limit; ++i)
    {
      char ch = str.charAt(i);
      if (Character.isWhitespace(ch))
      {
        if (!Character.isWhitespace(prevCh))
        {
          buf.append(' ');
        }
      }
      else
      {
        buf.append(ch);
      }
      prevCh = ch;
    }

    return buf.toString();
  }

  /**
   * Return the number of occurences of a character in a string.
   *
   * @param str The string to check.
   * @param ch  The character check for.
   *
   * @return  The number of times <tt>ch</tt> occurs in <tt>str</tt>.
   */
  public static int countOccurences(String str, int ch)
  {
    if (isEmpty(str))
    {
      return 0;
    }

    int count = 0;
    int idx = -1;
    do
    {
      idx = str.indexOf(ch, ++idx);
      if (idx != -1)
      {
        ++count;
      }
    }
    while (idx != -1);
    return count;
  }

  /**
   * Split a string based on the given delimiter, but don't remove
   * empty elements.
   *
   * @param str     The string to be split.
   * @param delimiter Split string based on this delimiter.
   *
   * @return  Array of split strings. Guaranteeded to be not null.
   */
  public static String[] split(String str, char delimiter)
  {
    return split(str, delimiter, false);
  }

  /**
   * Split a string based on the given delimiter, optionally removing
   * empty elements.
   *
   * @param str     The string to be split.
   * @param delimiter Split string based on this delimiter.
   * @param removeEmpty If <tt>true</tt> then remove empty elements.
   *
   * @return  Array of split strings. Guaranteeded to be not null.
   */
  public static String[] split(String str, char delimiter,
                    boolean removeEmpty)
  {
    // Return empty list if source string is empty.
    final int len = (str == null) ? 0 : str.length();
    if (len == 0)
    {
      return new String[0];
    }

    final List<String> result = new ArrayList<String>();
    String elem = null;
    int i = 0, j = 0;
    while (j != -1 && j < len)
    {
      j = str.indexOf(delimiter,i);
      elem = (j != -1) ? str.substring(i, j) : str.substring(i);
      i = j + 1;
      if (!removeEmpty || !(elem == null || elem.length() == 0))
      {
        result.add(elem);
      }
    }
    return result.toArray(new String[result.size()]);
  }
    
    /**
     * Joins the specified parts separating each from one another with the 
     * specified delimiter.  If delim is null, then this merely returns the 
     * concatenation of all the parts.
     * 
     * @param parts the strings to be joined
     * @param delim the char(s) that should separate the parts in the result
     * @return a string representing the joined parts.
     */
    public static String join(String[] parts, String delim) {
        StringBuilder result = new StringBuilder();
        
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            result.append(part);
            if (delim != null && i < parts.length-1) {
                result.append(delim);
            }        
        }
        return result.toString();
    }
    
    public static String[] segment(String source, int maxSegmentSize) {
        ArrayList<String> tmp = new ArrayList<String>();
        if (source.length() <= maxSegmentSize) {
            return new String[] { source };
        }
        boolean done = false;
        int currBeginIdx = 0;
        int currEndIdx = maxSegmentSize;
        while (!done) {
            String segment = source.substring(currBeginIdx, currEndIdx);
            tmp.add(segment);
            if (currEndIdx >= source.length()) {
                done = true;
                continue;
            }
            currBeginIdx = currEndIdx;
            currEndIdx += maxSegmentSize;
            if (currEndIdx > source.length()) {
                currEndIdx = source.length();
            }
        }
        return tmp.toArray(new String[tmp.size()]);
    }
    
    public static int getTokenBeginIndex(String selectSQL, String token)
    {
       String lowerSel = selectSQL.toLowerCase();
       String lowerToken = token.toLowerCase().trim();

       int curPos = 0;
       int count = 0;
       while(-1 != curPos)
       {
          curPos = lowerSel.indexOf(lowerToken, curPos + lowerToken.length());

          if(-1 < curPos
                  && (0 == curPos || Character.isWhitespace(lowerSel.charAt(curPos-1)))
                  && (lowerSel.length() == curPos + lowerToken.length() || Character.isWhitespace(lowerSel.charAt(curPos + lowerToken.length())))
            )
          {
             return curPos;
          }
          // If we've loop through one time for each character in the string, 
          // then something must be wrong.  Get out!
          if (count++ > selectSQL.length()) {
              break;
          }
       }

       return curPos;
    }
    
    public static Byte[] getByteArray(byte[] bytes) {
        if (bytes == null || bytes.length == 0 ) {
            return new Byte[0];
        }
        Byte[] result = new Byte[bytes.length]; 
        for (int i = 0; i < bytes.length; i++) {
            result[i] = Byte.valueOf(bytes[i]);
        }

        return result;
    }
    
    /**
     * Chops off the very last character of the given string.
     * 
     * @param aString a string to chop
     * @return the specified string minus it's last character, or null for null
     *         or empty string for a string with length == 0|1.
     */
    public static String chop(String aString) {
        if (aString == null) {
            return null;
        }
        if (aString.length() == 0) {
            return "";
        }
        if (aString.length() == 1) {
            return "";
        }
        return aString.substring(0, aString.length()-1);
    }
    
    /**
     * Returns the platform-specific line separator, or "\n" if it is not defined for some reason.
     * 
     * @return the platform-specific line separator.
     */
    public static String getEolStr() {
     return System.getProperty("line.separator", "\n");
    }
}

public class ProductServices
{

    private int totalRecord = 0;
    public static String join(Iterable<? extends Object> elements, CharSequence separator) 
    {
        StringBuilder builder = new StringBuilder();

        if (elements != null)
        {
            Iterator<? extends Object> iter = elements.iterator();
            if(iter.hasNext())
            {
                builder.append( String.valueOf( iter.next() ) );
                while(iter.hasNext())
                {
                    builder
                        .append( separator )
                        .append( String.valueOf( iter.next() ) );
                }
            }
        }

        return builder.toString();
    }
    public List<Product> searchProduct(int category_id, String columns, String where_condition, String order_by_condition, int page, int rowOnPage)
    {
        ProductDAO dao = new ProductDAO();
        List<Product> listProduct = dao.searchProduct(category_id, columns, where_condition, order_by_condition, page, rowOnPage);

        this.setTotalRecord(dao.getTotalRow());

        return listProduct;
    }

    /*
     * public int countSearchProduct(int category_id, String where_condition,
     * String order_by_condition) { ProductDAO dao = new ProductDAO(); int count
     * = dao.countSearchProduct(category_id, where_condition,
     * order_by_condition); return count; }
     */
    public List<ProductAttribute> getProductAttributeByCategory(int category, String order_by)
    {
        ProductDAO dao = new ProductDAO();
        List<ProductAttribute> listProductAttribute = dao.getProductAttribute(category, order_by);
        return listProductAttribute;
    }
    
    public List<ProductAttribute> getProductAttributeBySortResult(int category, String order_by)
    {
        ProductDAO dao = new ProductDAO();
        List<ProductAttribute> listProductAttribute = dao.getProductAttributeBySortResult(category, order_by);
        return listProductAttribute;
    }

    public List<String> getValueSearch(String attribute, int category_id, String agentOStore)
    {

        ProductDAO dao = new ProductDAO();

        //String newOrderBy = " ORDER BY Convert(INT," + attribute + ") ";
        String newOrderBy = " ORDER BY replicate('0', 255 - len(" + attribute + ")) + " + attribute;
        String order_by = this.buildOrderByCategoryAttribute(newOrderBy, attribute, category_id);
        if (order_by.isEmpty())
        {
            order_by = " ORDER BY " + attribute + " ";
        }

        //List<String> valueSearch = dao.searchValueByAttribute(attribute, category_id, order_by, agentOStore);
        List<String> valueSearch = dao.searchValueByAttribute(attribute, category_id, order_by, agentOStore);

        return valueSearch;
    }

    public Map<String, List<String>> getValueSearchForProductAttribute(List<ProductAttribute> listProductAttribute, int category_id , String agentOStore)
    {
        Map<String, List<String>> mapValueSearch = new HashMap<String, List<String>>();

        for (ProductAttribute productAttribute : listProductAttribute)
        {
            mapValueSearch.put(productAttribute.getAttribute_number(), this.getValueSearch(productAttribute.getAttribute_number(), category_id, agentOStore));
        }

        return mapValueSearch;
    }

    public List<String> getInventoryByCategory(int category_id)
    {
        ProductDAO dao = new ProductDAO();
        List<String> inventory = dao.getInventoryByCategory(category_id);
        return inventory;
    }
    
    //implement request date 03/06/2012
    //get column image_url (is cosmetic grade)
    public List<String> getCosmeticByCategory(int category_id)
    {
        ProductDAO dao = new ProductDAO();
        List<String> Cosmetic = dao.getCosmeticByCategory(category_id);
        return Cosmetic;
    }

    public String buildQuerySortBy(HttpServletRequest request)
    {
        String orderby = "";
        String sortBy = "";

        //Filter - Sort By
        if (request.getParameter("category_id") != null && request.getParameter("sort_by") != null)
        {
            sortBy = request.getParameter("sort_by");
            String newOrderBy = " replicate('0', 255 - len(" + sortBy + ")) + " + sortBy;

            //String newOrderBy = " ORDER BY Convert(FLOAT,dbo.UDF_ParseAlphaChars(" + sortBy + "))";
            orderby = this.buildOrderByCategory(newOrderBy, request);

            //List Price
            if (sortBy.equals("list_price"))
            {
                orderby = " (CASE WHEN modified_price=0 THEN list_price ELSE modified_price END)";
            }
            
            if (sortBy.equals("days_at_mri"))
            {
            	orderby = " days_at_mri DESC";
            }
            
            if (orderby.isEmpty())
            {
                orderby = sortBy;
            }

        }
        return orderby;
    }

    public String buildQueryColumn(HttpServletRequest request)
    {
        HttpSession sessions = request.getSession();

        Agent agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);
        int agentID = agent.getAgentId();
        String byAgent = "";
        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        //if (agentID > 0)
        {
        	byAgent = byAgent.concat(" AND (userHold IS NULL OR userHold='')");
        	//byAgent = byAgent.concat(" AND userHold = '" + Integer.toString(agentID) + "' ");
        }
        
        //System.out.println("Customer : "+isCustomer);
        String columnAttr = "t1.item_sku, t1.name, t1.status, datediff(d,received_date,getdate()) as item_age, (CASE WHEN modified_price=0 THEN list_price ELSE modified_price END) AS list_price, t3.shopper_id, ";
        columnAttr = columnAttr.concat("(CASE WHEN t1.status LIKE '%-SOLD' THEN  (SELECT TOP 1 orderNumber FROM orderLine where item_sku = t1.item_sku)  ELSE (select TOP 1 order_id from estore_basket where shopper_id = t3.shopper_id "  + byAgent + ") END ) as order_id,");
        columnAttr = columnAttr.concat("t1.flagtype, item_id = NULL, t1.image_url"); 
        return columnAttr;
    }

    public String buildQuerySearch(HttpServletRequest request)
    {
        HttpSession sessions = request.getSession();
        
        Agent agent = (Agent) sessions.getAttribute(Constants.AGENT_INFO);
        String agentUser = agent.getUserName();

        String shopper_id = "";
        if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
        {
            shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
        }

        Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);

        int category_id = Integer.parseInt(request.getParameter("category_id"));

        String query = " (t1.category_id = " + category_id + ") ";

        String byAgent = "";
        if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
        {
            byAgent = byAgent + " t3.byAgent = 0 ";
        }
        else
        {
            byAgent = byAgent + " t3.byAgent = 1 ";
        }
        //System.out.println(byAgent);
        //Item Sku
        if (request.getParameter("item_sku") != null && !request.getParameter("item_sku").isEmpty())
        {
        	String item_sku = request.getParameter("item_sku").toString(); 
        	if (isCustomer != null && ((Boolean) isCustomer).booleanValue()){
	            query += " AND (t1.item_sku = '" + item_sku + "') AND ((t1.status='AGENT-STORE-PENDING' AND t3.byAgent = 0 AND t3.shopper_id='" + shopper_id + "') OR (t1.status!='AGENT-STORE-PENDING' AND t1.status LIKE 'AGENT-STORE-%')) " ;
	        }
        	else{
	            query += " AND (t1.item_sku = '" + item_sku + "') AND ((t1.status='AGENT-STORE-PENDING' AND t3.byAgent = 1 AND t3.shopper_id='" + shopper_id + "') OR (t1.status!='AGENT-STORE-PENDING')) " ;        		
        	}
        }
        else
        {
            String inventory = request.getParameter("only_available");
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue()) 
            {
                inventory = Constants.STATUS_ITEM_INSTORE_COT;
            }

            //Inventory
            if (inventory != null && !inventory.isEmpty())
            {
                if (inventory.equals("all"))
                {
                    query +=
                        " AND ((status IN (SELECT DISTINCT status FROM estore_inventory where category_id = " + category_id + ")) OR (shopper_id = '"
                            + shopper_id + "' AND " + byAgent + ")) ";
                }
                else if (inventory.equals("AGENT-AVAILABLE & HELD"))
                {
                    query += " AND ((status = '" + Constants.STATUS_ITEM_INSTORE + "') OR (status = 'AGENT-HELD') OR (shopper_id = '" + shopper_id + "')) ";
                }
                else if (inventory.equals("HELD-ITEMS"))
                {
                    query += " AND ((status LIKE 'HOL%') OR (shopper_id = '" + shopper_id + "')) ";
                }
                else if (inventory.equals("MGR-DISCRETION"))
                {
                    query += " AND ((status LIKE '%DISCRETION') OR (shopper_id = '" + shopper_id + "')) ";
                }
                else if (inventory.equalsIgnoreCase(Constants.STATUS_ITEM_INSTORE) || inventory.equalsIgnoreCase(Constants.STATUS_ITEM_INSTORE_COT))
                {
                    String statusPending = inventory.equals(Constants.STATUS_ITEM_INSTORE) ?  Constants.STATUS_ITEM_INSTORE_PENDING :  Constants.STATUS_ITEM_INSTORE_PENDING_COT ;
                    query += " AND ((status = '" + inventory + "') OR (shopper_id = '" + shopper_id + "' AND " + byAgent + " AND status = '" + statusPending + "')) ";
                }
                else if (inventory.equalsIgnoreCase("AGENT-" + agentUser + "-AVAILABLE"))
                {
                    String statusPending = "AGENT-" + agentUser.toUpperCase() + "-PENDING"; 
                    query += " AND ((status = '" + inventory + "') OR (shopper_id = '" + shopper_id + "' AND " + byAgent + " AND status = '" + statusPending + "')) ";
                }
                else
                {
                    query += " AND ((status = '" + inventory + "')) ";
                }
            }

            //  Aging Bucket
            if (request.getParameter("aging_bucket") != null)
            {
                String aging_bucket = request.getParameter("aging_bucket");
                if (aging_bucket.equals("30"))
                {
                    query += " AND (datediff(d,received_date,getdate()) <= 30)  ";
                }
                else if (aging_bucket.equals("3140"))
                {
                    query += " AND (datediff(d,received_date,getdate()) between 31 and 40)  ";
                }
                else if (aging_bucket.equals("4150"))
                {
                    query += " AND (datediff(d,received_date,getdate()) between 41 and 50)  ";
                }
                else if (aging_bucket.equals("5160"))
                {
                    query += " AND (datediff(d,received_date,getdate()) between 51 and 60)  ";
                }
                else if (aging_bucket.equals("6170"))
                {
                    query += " AND (datediff(d,received_date,getdate()) between 61 and 70)  ";
                }
                else if (aging_bucket.equals("7190"))
                {
                    query += " AND (datediff(d,received_date,getdate()) between 71 and 90)  ";
                }
                else if (aging_bucket.equals("91"))
                {
                    query += " AND (datediff(d,received_date,getdate()) >= 90)  ";
                }
                else
                {

                }
            }
            
            if(request.getParameter("cosmetic_grade") != null){
            	String cosmetic = request.getParameter("cosmetic_grade");
            	if(cosmetic.equals("all")){
            		query += " AND ((image_url in(SELECT DISTINCT image_url FROM estore_inventory where category_id = " + category_id + ")))";
            	}else{
            		String values[] = StringUtilities.split(cosmetic, ',');
                	String attr = StringUtilities.join(values, "\',\'");
            		query += " AND ((image_url in ('"+attr+"')))";
            	}
            	
            }

            //Search by Attribute
            ProductServices productService = new ProductServices();
            List<ProductAttribute> listProductAttribute = productService.getProductAttributeByCategory(category_id, "");

            for (int i = 0; i < listProductAttribute.size(); i++)
            {
                ProductAttribute productAttribute = (ProductAttribute) listProductAttribute.get(i);
                if (productAttribute.getIsSearchable() == true)
                {
                    if (null != request.getParameter(productAttribute.getAttribute_number()) && !request.getParameter(productAttribute.getAttribute_number()).isEmpty())
                    {
                        String valueAttribute = request.getParameter(productAttribute.getAttribute_number());
                        if (productAttribute.getReorderList() == 1 && request.getParameter("reorder_" + productAttribute.getAttribute_number()) != null)
                        {
                            //fix value search >= if value is Integer or String
                            if (Constants.isNumber(valueAttribute))
                            {
                                query = query.concat(" AND " + productAttribute.getAttribute_number() + " >= " + valueAttribute + "  ");
                            }
                            else
                            {
                            	String values[] = StringUtilities.split(valueAttribute, ',');
                            	String attr = StringUtilities.join(values, "\',\'");
                                query = query.concat(" AND " + productAttribute.getAttribute_number() + " in( '" + attr + "' ) ");
                            }
                            //query += " AND " + productAttribute.getAttribute_number() + " >= '" + valueAttribute + "'  ";
                        }
                        else
                        {
                        	String values[] = StringUtilities.split(valueAttribute, ',');
                        	String attr = StringUtilities.join(values, "\',\'");
                            query = query.concat(" AND " + productAttribute.getAttribute_number() + " in('" + attr + "') ");
                        }
                    }
                }
            }
        }
        //System.out.println("Query Build  :"+query);
        return query;
    }

    public Boolean addOrder(String shopper_id, String skus, Boolean byAgent)
    {
        Boolean flag = true;
        ProductDAO productDAO = new ProductDAO();
        String[] arrSku = skus.split(",");
       
        if (arrSku.length > 0)
        {
            for (String item_sku : arrSku)
            {
                Product product = productDAO.basketItemExists(item_sku);
                if (product == null)
                {
                    product = productDAO.getItemProduct(item_sku);
                    
                    productDAO.orderAddItem(shopper_id, product, byAgent);
                }
                else
                {
                    if (!product.getShopper_id().trim().equals(shopper_id))
                    {
                        flag = false;
                    }
                }
            }
        }
        return flag;
    }

    public Boolean clearOrder(String shopper_id, Boolean byAgent)
    {
        Boolean Flag = false;
        ProductDAO productDAO = new ProductDAO();
        Flag = productDAO.clearOrderItem(shopper_id, byAgent);
        return Flag;
    }

    public void orderDeleteItemBySku(String shopper_id, String chks, String takens, Boolean flag)
    {
        ProductDAO productDAO = new ProductDAO();
        String[] arrChks = chks.split(",");
        String[] arrTakens = takens.split(",");

        for (String taken : arrTakens)
        {
            if (itemInArray(arrChks, taken) == flag)
            {
                productDAO.deleteOrderItem(shopper_id, taken);
            }
        }
    }

    private Boolean itemInArray(String[] arr, String item)
    {
        Boolean Flag = false;
        for (String sku : arr)
        {
            if (sku.equals(item))
            {
                Flag = true;
            }
        }
        return Flag;
    }

    public Map<String, String> getBasketKeyValue(String shopper_id)
    {
        ProductDAO productDAO = new ProductDAO();
        Map<String, String> basket = productDAO.getBasketKeyValue(shopper_id);
        return basket;
    }

    public String getSoldTo(String status, String shopper_id, String item_sku)
    {
        String strReturn = "";

        ProductDAO productDAO = new ProductDAO();
        if (status.contains("PENDING"))
        {
            strReturn = productDAO.getNamePending(shopper_id);
        }
        else if (status.contains("HELD"))
        {
            strReturn = productDAO.getNameHold(shopper_id);
        }
        else if (status.contains("SOLD"))
        {
            strReturn = productDAO.getNameSold(item_sku);
        }

        if (strReturn.isEmpty())
        {
            strReturn = "N/A";
        }

        return strReturn;
    }

    public Product getProductDetail(String item_sku, List<ProductAttribute> listProductAttribute)
    {
        Product product = null;
        ProductDAO productDAO = new ProductDAO();
        product = productDAO.getProductDetail(item_sku, listProductAttribute);
        return product;
    }

    public Integer getCategoryIdBySku(String item_sku)
    {
        ProductDAO productDAO = new ProductDAO();
        return productDAO.getCategoryIdBySku(item_sku);
    }

    public String buildOrderByCategory(String newOrderBy, HttpServletRequest request)
    {
        String orderBy = "";
        String sortBy = request.getParameter("sort_by");
        int category_id = Integer.parseInt(request.getParameter("category_id").toString());

        //Laptop
        if (category_id == 11946 && (sortBy.equals("attribute13") || sortBy.equals("attribute15") || sortBy.equals("attribute18") || sortBy.equals("attribute25") || sortBy.equals("attribute33")))
        {
            orderBy = newOrderBy;
        }
        //Desktops
        else if (category_id == 11949 && (sortBy.equals("attribute13") || sortBy.equals("attribute15") || sortBy.equals("attribute18") || sortBy.equals("attribute25")))
        {
            orderBy = newOrderBy;
        }
        //Workstations 
        else if (category_id == 11947
            && (sortBy.equals("attribute13") || sortBy.equals("attribute15") || sortBy.equals("attribute18") || sortBy.equals("attribute25") || sortBy.equals("attribute33")))
        {
            orderBy = newOrderBy;
        }
        //Monitors
        else if (category_id == 11955 && (sortBy.equals("attribute03")))
        {
            orderBy = newOrderBy;
        }
        //Storage
        else if(category_id == 11961 && (sortBy.equals("attribute13") || sortBy.equals("attribute10") || sortBy.equals("attribute09"))){
        	orderBy = newOrderBy;
        	
        }
        //NetWorking
        else if(category_id == 11962 && (sortBy.equals("attribute13") || sortBy.equals("attribute10") || sortBy.equals("attribute09"))){
        	orderBy = newOrderBy;
        	
        }
        //Parts
        else if(category_id == 11963 && (sortBy.equals("attribute13") || sortBy.equals("attribute10") || sortBy.equals("attribute09"))){
        	orderBy = newOrderBy;
        	
        }
      //Servers
        else if(category_id == 11950 && (sortBy.equals("attribute13") || sortBy.equals("attribute10") || sortBy.equals("attribute09"))){
        	orderBy = newOrderBy;
        	
        }
        
        
        return orderBy;
    }

    public String buildOrderByCategoryAttribute(String newOrderBy, String attribute, int category_id)
    {
        String orderBy = "";
        String sortBy = attribute;

        //Laptop
        if (category_id == 11946 && (sortBy.equals("attribute13") || sortBy.equals("attribute15") || sortBy.equals("attribute18") || sortBy.equals("attribute25") || sortBy.equals("attribute33")))
        {
            orderBy = newOrderBy;
        }
        //Desktops
        else if (category_id == 11949 && (sortBy.equals("attribute13") || sortBy.equals("attribute15") || sortBy.equals("attribute18") || sortBy.equals("attribute25")))
        {
            orderBy = newOrderBy;
        }
        //Workstations 
        else if (category_id == 11947
            && (sortBy.equals("attribute13") || sortBy.equals("attribute15") || sortBy.equals("attribute18") || sortBy.equals("attribute25") || sortBy.equals("attribute33")))
        {
            orderBy = newOrderBy;
        }
        //Monitors
        else if (category_id == 11955 && (sortBy.equals("attribute03")))
        {
            orderBy = newOrderBy;
        }
        //Storage
        else if(category_id == 11961 && (sortBy.equals("attribute09") || sortBy.equals("attribute10") || sortBy.equals("attribute13") )){
        	orderBy = newOrderBy;
        }
        //Networking
        else if(category_id == 11962 && (sortBy.equals("attribute09") || sortBy.equals("attribute10") || sortBy.equals("attribute13") )){
        	orderBy = newOrderBy;
        }
        //Parts
        else if(category_id == 11963 && (sortBy.equals("attribute09") || sortBy.equals("attribute10") || sortBy.equals("attribute13") )){
        	orderBy = newOrderBy;
        }
      //Servers
        else if(category_id == 11950 && (sortBy.equals("attribute09") || sortBy.equals("attribute10") || sortBy.equals("attribute13") )){
        	orderBy = newOrderBy;
        }
        return orderBy;
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

    public List<ProductAttribute> addSortBy(List<ProductAttribute> listProductAttribute)
    {
        List<ProductAttribute>  listProductAttributeSB =   new ArrayList<ProductAttribute>(listProductAttribute);
        
        ProductAttribute a = new ProductAttribute();
        a.setAttribute_number("list_price");
        a.setAttribute_name("List Price");
        a.setIsSearchable(true);
        listProductAttributeSB.add(a);
        ProductAttribute b = new ProductAttribute();
        b.setAttribute_number("status");
        b.setAttribute_name("Status");
        b.setIsSearchable(true);
        listProductAttributeSB.add(b);
        
        Collections.sort(listProductAttributeSB, new Comparator<ProductAttribute>()
        {
            public int compare(ProductAttribute arg0, ProductAttribute arg1)
            {
                return arg0.getAttribute_name().compareToIgnoreCase(arg1.getAttribute_name());
            }
        });

        return listProductAttributeSB;
    }
}
