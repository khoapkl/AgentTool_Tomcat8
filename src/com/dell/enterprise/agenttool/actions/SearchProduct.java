/*
 * FILENAME
 *     SearchProduct.java
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

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.InventoryCategory;
import com.dell.enterprise.agenttool.model.Product;
import com.dell.enterprise.agenttool.model.ProductAttribute;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.services.InventoryCategoryService;
import com.dell.enterprise.agenttool.services.ProductServices;
import com.dell.enterprise.agenttool.util.Constants;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * TODO Add one-sentence summarising the class functionality here; this sentence
 * should only contain one full-stop.</p>
 * <p>
 * TODO Add detailed HTML description of class here, including the following,
 * where relevant:
 * <ul>
 *     <li>TODO Description of what the class does and how it is done.</li>
 *     <li>TODO Explanatory notes on usage, including code examples.</li>
 *     <li>TODO Notes on class dynamic behaviour e.g. is it thread-safe?</li>
 * </ul></p>
 * <p>
 * <h2>Responsibilities</h2></p>
 * <p>
 * <ul>
 *     <li>TODO Reponsibility #1.</li>
 *     <li>TODO Reponsibility #2.</li>
 *     <li>TODO Reponsibility #3.</li>
 *     <li>TODO Reponsibility #4.</li>
 * </ul></p>
 * <p>
 * <h2>Protected Interface</h2></p>
 * <p>
 * TODO Document the protected interface here, including the following:
 * <ul>
 *     <li>TODO Which aspects of the class's functionality can be extended.</li>
 *     <li>TODO How this extension is done, including usage examples.</li>
 * </ul></p>
 *
 * @see TODO Related Information
 *
 * @author Administrator
 *
 * @version $Id$
 **/
/**
 * @author vinhhq
 * 
 */
public class SearchProduct extends DispatchAction
{

    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.SearchProduct");
    
    
    public final ActionForward category(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        try
        {
            LOGGER.info("Execute Action Search Product");

            //Check Shopper login
            HttpSession sessions = request.getSession();
            Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
            
            if (sessions.getAttribute(Constants.SHOPPER_ID) == null)
            {
                forward = Constants.SESSION_TIMEOUT;
                Cookie[] cookies = request.getCookies();
                if (cookies != null)
                {
                    for (int i = 0; i < cookies.length; i++)
                    {
                        if (cookies[i].getName().equals("userLevel"))
                        {
                            if (cookies[i].getValue().equals("login.do"))
                                forward = Constants.CUSTOMER_SESSION_TIMEOUT;
                        }
                    }
                }
            }
            else
            {
                //check category
                int category_id = 0;
                String shopper_id = "";
                ProductServices productServices = new ProductServices();
                
                if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
                {
                    shopper_id = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                }
                
                if (request.getParameter("category_id") != null && !request.getParameter("category_id").isEmpty())
                {
                    LOGGER.info("Execute Action Search Product Category ");
                    InventoryCategoryService inventoryCategoryService = new InventoryCategoryService();
                    
                    //list drop-down where AGENT_STORE_AVAILABLE
                    String agentOrStore = "agent";
                    if(sessions.getAttribute(Constants.IS_CUSTOMER) != null){
                    	agentOrStore = "store";
                    }
                    category_id = Integer.parseInt(request.getParameter("category_id"));
                    InventoryCategory inventoryCategory = inventoryCategoryService.getInventoryCategory(category_id);
                    List<ProductAttribute> listProductAttribute = productServices.getProductAttributeByCategory(category_id, "");
                    List<String> listInventory = productServices.getInventoryByCategory(category_id);
                    Map<String, List<String>> mapValueSearch = productServices.getValueSearchForProductAttribute(listProductAttribute, category_id, agentOrStore);
                    List<String> listCosmetic = productServices.getCosmeticByCategory(category_id);
                    
                    
                    request.setAttribute(Constants.ATTR_CATEGORY_ID, category_id);
                    request.setAttribute(Constants.ATTR_INVENTORY_CATEGORY, inventoryCategory);
                    request.setAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE, listProductAttribute);
                    request.setAttribute(Constants.ATTR_LIST_INVENTORY, listInventory);
                    request.setAttribute(Constants.ATTR_LIST_COSMETIC, listCosmetic);
                    request.setAttribute(Constants.ATTR_MAP_VALUE_SEARCH, mapValueSearch);
                    
                    List<ProductAttribute> listProductAttributeSortBy = productServices.addSortBy(listProductAttribute);
                    request.setAttribute(Constants.ATTR_SORT_BY, listProductAttributeSortBy);
                    
                    if(isCustomer == null && !Constants.isNumber(shopper_id))
                    {
                        //get customer
                        CustomerServices customerServices = new CustomerServices();
                        Customer customer = customerServices.getCustomerByShopperID(shopper_id);
                        
                        request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                        request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                    }
                    
                    
                    forward = Constants.PRODUCT_SEARCH_PAGE;
                }
                else
                {
                    forward = "agenttools.search.defaultpage";
                }

                if (request.getParameter("search") != null)
                {
                    LOGGER.info("Execute Action Value Search Product");

                    category_id = Integer.parseInt(request.getParameter("category_id"));

                    

                    int currentPage = 1;
                    if (request.getParameter("page") != null)
                    {
                        currentPage = Integer.parseInt(request.getParameter("page").toString());
                    }

                    int rowOnPage = Constants.RECORDS_ON_PAGE;
                    if (request.getParameter("slRowOnpage") != null)
                    {
                        rowOnPage = Integer.parseInt(request.getParameter("slRowOnpage").toString());
                        sessions.setAttribute(Constants.ATTR_ROW_ON_PAGE, rowOnPage);
                    }
                    else if (sessions.getAttribute(Constants.ATTR_ROW_ON_PAGE) != null)
                    {
                        rowOnPage = Integer.parseInt(sessions.getAttribute(Constants.ATTR_ROW_ON_PAGE).toString());
                    }

                    String where_condition = productServices.buildQuerySearch(request);
                    String order_by_condition = productServices.buildQuerySortBy(request);
                    String columns = productServices.buildQueryColumn(request);
                    
          
                    //listProductAttribute using sort Filter Reslut 
                    List<ProductAttribute> listProductAttribute = productServices.getProductAttributeByCategory(category_id, "");
                    //listProductAttributeSortResult using sort Search Reslut 
                    List<ProductAttribute> listProductAttributeSortResult = productServices.getProductAttributeBySortResult(category_id, "");
                    
                    List<Product> listProduct = productServices.searchProduct(category_id,columns, where_condition, order_by_condition, currentPage, rowOnPage);
                    
                    Map<String, String> itemBaskets = productServices.getBasketKeyValue(shopper_id);

                    //create paging
                    int rowCount = productServices.getTotalRecord();
                    int totalPage = (rowCount / rowOnPage) + ((rowCount % rowOnPage) > 0 ? 1 : 0);
                    if (totalPage == 0)
                    {
                        currentPage = 0;
                    }

                    //Attribute Data
                    request.setAttribute(Constants.ATTR_CATEGORY_ID, category_id);
                    request.setAttribute(Constants.ATTR_SEARCH_PRODUCT_RESULT, listProduct);
                    request.setAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE, listProductAttribute);
                    request.setAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE_BY_SORT_RESULT, listProductAttributeSortResult);
                    request.setAttribute(Constants.ATTR_ITEM_BASKET, itemBaskets);

                    //Attribute Paging
                    request.setAttribute(Constants.ATTR_PRODUCT_ROW_COUNT, rowCount);
                    request.setAttribute(Constants.ATTR_PRODUCT_TOTAL_PAGE, totalPage);
                    request.setAttribute(Constants.ATTR_PRODUCT_CURRENT_PAGE, currentPage);
                    request.setAttribute(Constants.ATTR_ROW_ON_PAGE, rowOnPage);

                    forward = Constants.PRODUCT_SEARCH_RESULTS;
                    
                   
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

        return mapping.findForward(forward);
    }

    public final ActionForward updateBasketItem(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "";
        try
        {
            HttpSession sessions = request.getSession();
            Object isCustomer = sessions.getAttribute(Constants.IS_CUSTOMER);
            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }
            
            if (request.getParameter("update_card") != null && request.getParameter("chk") != null)
            {                
                if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
                {
                    String shopperId = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                    String skus = request.getParameter("chk");
                    String takens = request.getParameter("taken");
                    
                    
                    ProductServices productServices = new ProductServices();
                    BasketService basketService = new BasketService();
                    
                    if(sessions.getAttribute(Constants.AGENT_INFO) != null)
                    {
                        Agent agent = (Agent)sessions.getAttribute(Constants.AGENT_INFO);
                        basketService.getOrderRow(shopperId, agent.getAgentId());
                    }
                    
                    
                    Boolean result = true;
                    result = productServices.addOrder(shopperId, skus,byAgent);
                    productServices.orderDeleteItemBySku(shopperId, skus, takens, false);

                    if (result == false)
                    {
                        request.setAttribute(Constants.ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT, 2);
                    }
                    else
                    {
                        request.setAttribute(Constants.ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT, 1);
                    }
                }
                else
                {
                    request.setAttribute(Constants.ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT, 0);
                }
            }

            if (request.getParameter("clear_card") != null)
            {
                if (sessions.getAttribute(Constants.SHOPPER_ID) != null)
                {
                    String shopperId = sessions.getAttribute(Constants.SHOPPER_ID).toString();
                    ProductServices productServices = new ProductServices();
                    if (productServices.clearOrder(shopperId,byAgent))
                    {
                        request.setAttribute(Constants.ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT, 1);
                    }
                }
            }
            forward = Constants.PRODUCT_ADD_BASKET_RESULTS;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
        return mapping.findForward(forward);
    }

    public final ActionForward productDetail(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        LOGGER.info("Execute Action productDetail");

        String forward = Constants.PRODUCT_DETAIL_RESULTS;
        try
        {

            if (request.getParameter("item_sku") != null)
            {
                String item_sku = request.getParameter("item_sku").toString();
                ProductServices productServices = new ProductServices();

                int category_id = productServices.getCategoryIdBySku(item_sku);
                List<ProductAttribute> listProductAttributes = productServices.getProductAttributeByCategory(category_id, "attribute_number");
                Product product = productServices.getProductDetail(item_sku, listProductAttributes);

                request.setAttribute(Constants.ATTR_PRODUCT_DETAIL, product);
                request.setAttribute(Constants.ATTR_PRODUCT_DETAIL_ATTRIBUTE, listProductAttributes);
            }
            else
            {
                forward = Constants.ERROR_PAGE_VIEW;
            }

        }
        catch (Exception e)
        {
            LOGGER.info("Exception Execute Action productDetail");
            System.out.println(e.getMessage());
        }

        return mapping.findForward(forward);
    }

    public final ActionForward serviceTagSearch(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        LOGGER.info("Execute Action serviceTagSearch");

        String forward = Constants.PRODUCT_SEARCH_PAGE;

        try
        {
            //Check Shopper login
            HttpSession sessions = request.getSession();
            if (sessions.getAttribute(Constants.AGENT_INFO) == null)
            {
                forward = Constants.LOGIN_VIEW;
            }
            else
            {
                if (request.getParameter("item_sku") != null)
                {
                    ProductServices productServices = new ProductServices();
                    InventoryCategoryService inventoryCategoryService = new InventoryCategoryService();

                    String item_sku = request.getParameter("item_sku").toString().trim();
                    int category_id = productServices.getCategoryIdBySku(item_sku);

                    if (category_id == 0)
                    {
                        //get old category id
                        if (request.getParameter("category_id") != null)
                        {
                            category_id = Integer.parseInt(request.getParameter("category_id").toString());
                        }
                    }
                  //list drop-down where AGENT_STORE_AVAILABLE
                    String agentOrStore = "agent";
                    if(sessions.getAttribute(Constants.IS_CUSTOMER) != null){
                    	agentOrStore = "store";
                    }
                    InventoryCategory inventoryCategory = inventoryCategoryService.getInventoryCategory(category_id);
                    List<ProductAttribute> listProductAttribute = productServices.getProductAttributeByCategory(category_id, "");
                    List<String> listInventory = productServices.getInventoryByCategory(category_id);
                    Map<String, List<String>> mapValueSearch = productServices.getValueSearchForProductAttribute(listProductAttribute, category_id, agentOrStore);

                    request.setAttribute(Constants.ATTR_CATEGORY_ID, category_id);
                    request.setAttribute(Constants.ATTR_ITEM_SKU, item_sku);

                    request.setAttribute(Constants.ATTR_LIST_INVENTORY, listInventory);
                    request.setAttribute(Constants.ATTR_INVENTORY_CATEGORY, inventoryCategory);
                    request.setAttribute(Constants.ATTR_PRODUCT_ATTRIBUTE, listProductAttribute);
                    request.setAttribute(Constants.ATTR_MAP_VALUE_SEARCH, mapValueSearch);
                    
                    List<ProductAttribute> listProductAttributeSortBy = productServices.addSortBy(listProductAttribute);
                    request.setAttribute(Constants.ATTR_SORT_BY, listProductAttributeSortBy);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.info("Exception Execute Action serviceTagSearch");
            System.out.println(e.getMessage());
        }
        return mapping.findForward(forward);
    }

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
                forward = mapping.findForward(Constants.SESSION_TIMEOUT);
                Cookie[] cookies = request.getCookies();
                if(cookies != null)
                {
                    for (int i = 0; i < cookies.length; i++)
                    {
                        if (cookies[i].getName().equals("userLevel"))
                        {
                            if (cookies[i].getValue().equals("login.do"))
                                forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                        }
                    }
                }
            }
            else if (method == null || "".equals(method))
            {
                forward = mapping.findForward(Constants.PRODUCT_SEARCH_PAGE);
            }
            else
            {
                forward = this.dispatchMethod(mapping, form, request, response, method);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = mapping.findForward(Constants.ERROR_PAGE_VIEW);
        }

        return forward;
    }

}
