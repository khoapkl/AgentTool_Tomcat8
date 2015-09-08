/*
 * FILENAME
 *     Constants.java
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
 *     Copyright (C) 2010 DELL. All rights reserved.
 *     This software is the confidential and proprietary information of
 *     DELL ("Confidential Information"). You shall not
 *     disclose such Confidential Information and shall use it only in
 *     accordance with the terms of the licence agreement you entered into
 *     with DELL.
 */

package com.dell.enterprise.agenttool.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * <p>
 * Constants for App.
 * </p>
 * 
 * @author BINHHT
 * 
 * @version $Id$
 **/
public final class Constants
{

    /**
     * <p>
     * Constructor.
     * </p>
     * 
     **/
    private Constants()
    {
        super();
    }

    //Added by HuyNVT
    public static boolean isEmpty(final String str)
    {
        if (str == null || str.trim().length() == 0)
            return true;
        else
            return false;
    }

    public static Boolean isNumber(final String str)
    {
        Boolean flag = true;
        try
        {
            Integer.parseInt(str.trim());
        }
        catch (Exception nfe)
        {
            flag = false;
        }
        return flag;
    }

    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    public static String dateNow(final String dateFormat)
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }

    public static String formatDate(final Timestamp timestamp, final String dateFormat)
    {
        //Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(timestamp.getTime());
    }

    public static String FormatCurrency(Float value)
    {
        DecimalFormat df = new DecimalFormat("#,##,##,##0.00");
        return "$" + df.format(value);
    }
    
    public static String FormatMhz(Float value)
    {
        if (value.floatValue() == 0)
        {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#####0.000");
        return df.format(value);
    }

    public static Float FloatRound(Float r)
    {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(r);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_UP);
        r = bd.floatValue();
        // output is 3.15
        return r;
    }
    
    public static Float FloatRoundPending(Float r)
    {
        int decimalPlace = 2;
        BigDecimal bd = new BigDecimal(r);
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_EVEN);
        r = bd.floatValue();
        // output is 3.15
        return r;
    }

    /////////

    public static final String ENVIRONMENT = "agenttool.environment";
    public static final String ENVIRONMENT_DEV = "DEV";

    public static final String STATUS_ITEM_UNLISTED = "UNLISTED";
    public static final String STATUS_ITEM_UNPRICED = "UNPRICED";
    public static final String STATUS_ITEM_AUCTION = "AUCTION-AVAILABLE";
    public static final String STATUS_ITEM_STORE = "STORE-AVAILABLE";
    public static final String STATUS_ITEM_INSTORE = "AGENT-AVAILABLE";
    public static final String STATUS_ITEM_INSTORE_PENDING = "AGENT-PENDING";
    public static final String STATUS_ITEM_INSTORE_COT = "AGENT-STORE-AVAILABLE";
    public static final String STATUS_ITEM_INSTORE_PENDING_COT = "AGENT-STORE-PENDING";

    public static final String STATUS_ITEM_SOLD_STORE = "STORE-SOLD";
    public static final String STATUS_ITEM_SOLD_AGENT = "DIRECT-SOLD";
    public static final String STATUS_ITEM_SOLD_AUCTION = "AUCTION-SOLD";

    public static final String STATUS_ITEM_EBAY_AUCTION_INSTORE = "EBAYAUCTION-AVAILABLE";
    public static final String STATUS_ITEM_EBAY_FIXED_INSTORE = "EBAYFIXED-AVAILABLE";
    public static final String STATUS_ITEM_EBAY_INSTORE = "EBAYAUCTION-SOLD";
    public static final String STATUS_ITEM_EBAY_FIXED = "EBAYFIXED-SOLD";
    public static final String STATUS_ITEM_EBAY_DAILY_INSTORE = "EBAYDAILYDEAL-AVAILABLE";
    public static final String STATUS_ITEM_EBAY_DAILY = "EBAYDAILYDEAL-SOLD";
    public static final String STATUS_ITEM_SHIPPER = "SHIPPED";

    public static final String STATUS_ORDER_SOLD_HELD = "HOLD";
    public static final String STATUS_ORDER_SOLD_CLEAR = "CLEAR";
    public static final String STATUS_ORDER_SOLD_PAYMENT = "HOLD-PAYMENT";

    public static final String ATTR_PRODUCT_ATTRIBUTE = "attr_prodcut_attribute";
    public static final String ATTR_PRODUCT_ATTRIBUTE_BY_SORT_RESULT = "attr_prodcut_attribute_by_sort_result";
    public static final String ATTR_CATEGORY_ID = "attr_category_id";
    public static final String ATTR_SORT_BY = "attr_sort_by";
    public static final String ATTR_INVENTORY_CATEGORY = "attr_inventory_category";
    public static final String ATTR_LIST_INVENTORY = "attr_list_inventory";
    public static final String ATTR_LIST_COSMETIC = "attr_list_cosmetic";
    public static final String ATTR_MAP_VALUE_SEARCH = "attr_map_value_search";
    public static final String ATTR_SEARCH_PRODUCT_RESULT = "attr_search_product_result";
    public static final String ATTR_PRODUCT_ADD_CLEAR_BASKET_RESULT = "attr_product_add_clear_basket_result";
    public static final String ATTR_PRODUCT_DETAIL = "attr_product_detail";
    public static final String ATTR_PRODUCT_DETAIL_ATTRIBUTE = "attr_product_detail_attribute";
    public static final String ATTR_PRODUCT_ROW_COUNT = "attr_product_row_count";
    public static final String ATTR_PRODUCT_TOTAL_PAGE = "attr_product_total_page";
    public static final String ATTR_PRODUCT_CURRENT_PAGE = "attr_product_current_page";
    public static final String ATTR_ITEM_SKU = "attr_item_sku";
    public static final String ATTR_ITEM_BASKET = "attr_item_basket";
    public static final String ATTR_LIST_WARRANTY = "attr_list_warranty";
    public static final String ATTR_ORDER_ROW = "attr_order_row";
    public static final String ATTR_ESTORE_BASKET_ITEM = "attr_estore_basket_item";
    public static final String ATTR_ORDER_CANCEL = "attr_order_cancel";
    public static final String ATTR_ROW_ON_PAGE = "attr_row_on_page";
    public static final String ATTR_COUNT_HELD_ORDER = "attr_count_held_order";
    public static final String ATTR_CUSTOMER = "attr_customer";

    public static final String ATTR_MAX_DISCOUNT = "attr_max_discount";
    public static final String ATTR_EPP_PROMOTION = "attr_epp_promotion";
    public static final String ATTR_ORDER_RECEIPT = "attr_order_receipt";
    public static final String ATTR_PAY_METHODS = "attr_pay_methods";
    public static final String ATTR_TAX_TABLES = "attr_tax_tables";
    public static final String ATTR_CHECKOUT_RESULTS = "attr_checkout_results";
    public static final String ATTR_ORDER_ID = "attr_order_id";
    public static final String ATTR_SHOPPER_ID = "attr_shopper_id";
    public static final String ATTR_QTY_NB = "attr_qty_nb";
    public static final String ATTR_QTY_DT = "attr_qty_dt";
    public static final String ATTR_CHECK_CAT = "attr_check_cat";
    public static final String ATTR_HOLD_DAYS = "attr_hold_days";
    public static final String ATTR_LIST_ERROR = "attr_list_error";
    public static final String ATTR_CREATE_CUSTOMER = "attr_create_customer";
    //////////////Added by HuyNVT /////////////
    public static final String SQL_CONDITION_WHERE = " WHERE ";
    public static final String SQL_CONDITION_AND = " AND ";
    public static final String DB_FIELD_LINK_NUMBER = "linknumber";

    public static final String DB_FIELD_SHIP_FNAME = "ship_to_fname";
    public static final String DB_FIELD_SHIP_LNAME = "ship_to_lname";
    public static final String DB_FIELD_SHIP_COMPANY = "ship_to_company";
    public static final String DB_FIELD_SHIP_PHONE = "ship_to_phone";
    public static final String DB_FIELD_SHIP_ADDRESS_1 = "ship_to_address1";
    public static final String DB_FIELD_SHIP_ADDRESS_2 = "ship_to_address2";
    public static final String DB_FIELD_SHIP_COUNTY = "ship_to_county";
    public static final String DB_FIELD_SHIP_COUNTRY = "ship_to_country";
    public static final String DB_FIELD_SHIP_FAX = "ship_to_fax";
    public static final String DB_FIELD_SHIP_EMAIL = "ship_to_email";
    public static final String DB_FIELD_SHIP_POSTAL = "ship_to_postal";
    public static final String DB_FIELD_SHIP_CITY = "ship_to_city";
    public static final String DB_FIELD_SHIP_STATE = "ship_to_state";
    public static final String DB_FIELD_AGENT_ID = "agent_id";

    public static final String DB_FIELD_BILL_COMPANY = "bill_to_company";
    public static final String DB_FIELD_BILL_FNAME = "bill_to_fname";
    public static final String DB_FIELD_BILL_LNAME = "bill_to_lname";
    public static final String DB_FIELD_BILL_PHONE = "bill_to_phone";
    public static final String DB_FIELD_BILL_ADDRESS_1 = "bill_to_address1";
    public static final String DB_FIELD_BILL_ADDRESS_2 = "bill_to_address2";
    public static final String DB_FIELD_BILL_COUNTY = "bill_to_county";
    public static final String DB_FIELD_BILL_COUNTRY = "bill_to_country";
    public static final String DB_FIELD_BILL_FAX = "bill_to_fax";
    public static final String DB_FIELD_BILL_EMAIL = "bill_to_email";
    public static final String DB_FIELD_BILL_POSTAL = "bill_to_postal";
    public static final String DB_FIELD_BILL_CITY = "bill_to_city";
    public static final String DB_FIELD_BILL_STATE = "bill_to_state";

    public static final String DB_FIELD_CREDIT_LINE = "creditline";
    public static final String DB_FIELD_CREDIT_EXP = "creditexp";
    public static final String DB_FIELD_CREDIT_AVAIL = "creditavail";
    public static final String DB_FIELD_CREDIT_COMMENT = "creditcomment";

    public static final String DB_FIELD_SHOPPER_ID = "shopper_id";
    public static final String DB_FIELD_SHOPPER_NUMBER = "shopper_number";
    public static final String DB_FIELD_CREATED_DATE = "createdDate";
    //////////////////////////////////////////

    public static final String ERROR_PAGE_VIEW = "error.page.view";
    public static final String LOGIN_VIEW = "agenttool.login.view";
    public static final String LOGIN = "agenttool.login";
    public static final String LOGIN_FAILURE = "agenttool.login.failure";
    public static final String LOGIN_SUCCESS = "agenttool.login.success";
    public static final String PARAMETER = "method";
    public static final String AGENT_INFO = "AgentInfo";
    public static final String OLD_SHOPPER_ID = "old_shopper_id";
    public static final String SHOPPER_ID = "shopper_id";
    public static final String SHOPPER_NAME = "shopper_name";
    public static final String OLD_SHOPPER_NAME = "old_shopper_name";
    public static final String SHOPPER_NUMBER = "shopper_number";
    public static final String IS_ADMIN = "isAdmin";
    public static final String IS_CUSTOMER = "isCustomer";
    public static final String SHOPPING_ID = "shopping_id";
    public static final String USER_LEVEL = "userLevel";
    public static final String CUSTOMER_MANAGE = "customer.manage";
    public static final String AGENT_NAME = "agent.userName";
    public static final String AGENT_PASSWORD = "agent.password";
    public static final String UPDATE_PASSWORD_VIEW = "agenttool.updatePassword";
    public static final String CHANGE_PASSWORD_VIEW = "agenttool.changePassword";
    public static final String SECURITY_SETTING_VIEW = "agenttool.securitySetup";
    
    

    public static final String SHOPPER_YEAR_VIEW = "agenttool.shopper_year.view";
    public static final String SHOPPER_YEAR_RESULTS = "agenttool.shopper_year.results";
    public static final String SHIPPING_REPORTS_VIEW = "agenttool.ship_report.view";
    public static final String SHOW_PRINT_SHIPPING_REPORT = "agenttool.ship_report.export.exel";

    public static final String SESSION_TIMEOUT = "agenttool.session_timeout";
    public static final String CUSTOMER_SESSION_TIMEOUT = "agenttool.customer.login";
    public static final String EPP_REPORTS_VIEW = "agenttool.shopper_epp_reports.view";
    public static final String SHOPPER_MANAGEMENT = "agenttool.shopper_manager";
    public static final String SHOPPER_LIST = "agenttools.shopper_list";
    public static final String SHOPPER_LIST_RESULTS = "agenttools.shopper_list.results";
    public static final String SHOPPER_MONTH_VIEW = "agenttool.shopper_month.view";
    public static final String SHOPPER_DAY_VIEW = "agenttool.shopper_day.view";
    public static final String SHOPPER_DAY_VIEW_RESULT = "agenttool.shopper_day.view.result";
    public static final String SHOPPER_VIEW = "agenttool.shopper_view";
    public static final String SHOPPER_RECEIPTS = "receipts";
    public static final String SHOPPER_INFO = "shopper_info";
    public static final String SHOPPER_NOTES = "notes";
    public static final String SHOPPER_NOTES_NOTE_TYPE = "noteType";
    public static final String SHOPPER_NOTES_AGENT_NAME = "agent_name";
    public static final String SHOPPER_NOTES_SUBJECT = "subject";
    public static final String SHOPPER_NOTES_TIME_OFF = "timeoff";
    public static final String SHOPPER_NOTES_SUBJECT_ID = "subjectId";
    public static final String SHOPPER_NOTES_TOPIC = "topic";
    public static final String SHOPPER_NOTES_ORDER_NUMBER = "orderNumber";
    public static final String SHOPPER_VIEW_RECEIPTS = "agenttools.shopper.view.receipts";
    public static final String SHOPPER_VIEW_RECEIPTS_PAGING = "agenttools.shopper.view.receipts.paging";
    public static final String SHOPPER_VIEW_BASKET = "agenttools.shopper.view.basket";
    public static final String SHOPPER_VIEW_BASKET_DETAIL = "agenttools.shopper.view.basket.detail";
    public static final String SHOPPER_VIEW_BASKET_SHOPPER_ID = "shopper id";
    public static final String SHOPPER_VIEW_BASKET_ORDER_ID = "order id";
    public static final String SHOPPER_SET_EXPRIRATION_DATE = "agenttools.shopper.set.expriration.date";
    public static final String SHOPPER_GET_CREATE_DATE = "agenttools.shopper.get.create.date";
    public static final String SHOPPER_ADD_NOTES = "agenttools.shopper.add.note";
    public static final String SHOPPER_ADD_NOTES_LIST_SUBJECT_TYPE = "list Subject Type";
    public static final String SHOPPER_ADD_NOTES_LIST_GROUP_SUBJECT = "list Group Subject";
    public static final String SHOPPER_VIEW_NOTES = "agenttools.shopper.view.note";
    public static final String SHOPPER_EDIT_AGENT_EXPIRATION = "agenttools.shopper.edit.expiration";
    public static final String SHOPPER_SEARCH_CRITERIA = "shopperSearchCriteria";
    public static final String AGENT_STORE_ID = "201";

    public static final String[] MONTHS = {
        "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"
    };

    public static final String PRODUCT_SEARCH_PAGE = "agenttool.search.page";
    public static final String PRODUCT_SEARCH_RESULTS = "agenttool.search.results";
    public static final String PRODUCT_ADD_BASKET_RESULTS = "agenttool.search.add.basket.results";
    public static final String PRODUCT_DETAIL_RESULTS = "agenttool.product.detail.show";

    /*
     * Report Shipping
     */
    public static final int RECORDS_ON_PAGE = 50;
    public static final int PAGE_ORDER_RECORD = 40;
    public static final int PAGE_RECORD = 40;
    public static final int PAGE_RECORD_LIST = 40;
    public static final int SHOPPER_LIST_RECORDS_PER_PAGE = 10;
    public static final String URL_METHOD = "method";
    public static final String FROMDATE_SHIPPING_REPORT = "fromDateShipping";
    public static final String TODATE_SHIPPING_REPORT = "toDateShipping";
    public static final String SEARCH_RESULT_BY_REPORT_SHIPPING = "agenttool.ship_report.result";

    /*
     * Order Constants
     */
    public static final String ORDER_DB_RESULT = "agenttools.order_db.result";
    public static final String ORDER_DB_CUSTOMER_RESULT = "agenttools.order_db.customer.result";
    public static final String ORDER_CUSTOMER_RESULT = "agenttools.order_db.customer";
    public static final String ORDER_DB_CUSTOMER_RESULT_OUTSITE = "agenttools.order_db.customer.outsite";
    public static final String ORDER_DB_CUSTOMER_RESULT_OUTSITE_PAGGING = "agenttools.order_db.customer.outsite.page";
    public static final String SHIP_TO_EMAIL = "ship_to_email";
    public static final String FILTER_TYPE = "filter_type";
    public static final String ORDER_NUMBER = "ordernumber";
    public static final String ORDER_SORT = "sort_order";
    public static final String ITEM_SKU = "itemsku";
    public static final String LISTING_NUMBER = "listing";
    public static final String ORDER_CRITERIA = "searchOrder";
    public static final String ORDER_CUSTOMER_CRITERIA = "customerOrder";
    public static final String ORDER_LIST_RESULT = "orderList";
    public static final String ORDER_TOTAL = "totalRecord";
    public static final String ORDER_NUMBER_PAGE = "noPage";
    public static final String MOVING_PAGING = "casePage";
    public static final String MOVING_NEXT = "NEXT";
    public static final String MOVING_LAST = "LAST";
    public static final String MOVING_PREV = "PREV";
    public static final String MOVING_FIRST = "FIRST";
    public static final String ORDER_DB_HELD_FOR = "agenttools.order_db.held";
    public static final String ORDER_DB_HELD_CUSTOMER_FOR = "agenttools.order_db.held.customer";
    public static final String ORDER_DB_HELD_CUSTOMER_FOR_SECOND = "agenttools.order_db.held.customer.second";
    public static final String ORDER_HELD_RESULT = "orderHeldMap";
    public static final String ORDER_HELD_TOTAL = "totalHeld";
    public static final String ORDER_FORWARD = "agenttools.order_db";
    public static final String LIST_ORDER_PENDING_FORWARD = "agenttools.list_order_pending_db";
    public static final String ORDER_MANAGEMENT = "agenttools.order_manager";
    public static final String ORDER_SHOP_FORWARD = "agenttools.order_db.shopper";
    public static final String ORDER_SHOP_RESULT = "shopperList";
    public static final String ORDER_SHOP_CRITERIA = "shopCriteria";
    public static final String ORDER_SHOP_RESULT_FOR = "agenttools.order_db.shopper.result";
    public static final String ORDER_DATE_FORWARD = "agenttools.order_db.date";
    public static final String ORDER_YEAR_FORWARD = "agenttools.order_db.date.year";
    public static final String ORDER_MONTH_FORWARD = "agenttools.order_db.date.month";
    public static final String ORDER_DAY_FORWARD = "agenttools.order_db.date.day";
    public static final String ORDER_YEAR_PARAM = "iyear";
    public static final String ORDER_MONTH_PARAM = "imonth";
    public static final String ORDER_DAY_PARAM = "iday";
    public static final String ORDER_YEAR_RESULT = "mapYear";
    public static final String ORDER_MONTH_RESULT = "mapMonth";
    public static final String ORDER_DAY_RESULT = "mapDay";
    public static final String ORDER_AGENT_FORWARD = "agenttools.order_db.agents";
    public static final String ORDER_AGENT_DATE_FORWARD = "agenttools.order_db.agents.result";
    public static final String ORDER_AGENT_DATE_RESULT = "agentList";
    public static final String ORDER_AGENT_ORDER_FORWARD = "agenttools.agents.order";
    public static final String ORDER_AGENT_ORDER_RESULT = "agenttools.agents.order.result";
    public static final String ORDER_AGENT_ID = "agentId";
    public static final String ORDER_AGENT_NAME = "agentName";
    public static final String ORDER_ORDER_VIEW_FORWARD = "agenttools.order.view";
    public static final String ORDER_SHOPPER_ID = "shopperId";
    public static final String ORDER_SHOPPER_DETAIL = "shopperDetail";
    public static final String ORDER_BY_SHOPPER_RESULT = "listOrder.byShopper";
    public static final String ORDER_BY_SHOPPER_FORWARD = "agenttools.agents.order.shopper";
    public static final String ORDER_AGENT_REPORT_VIS = "visSize";
    public static final String ORDER_AGENT_REPORT_BrandPart = "BrandPart";
    public static final String ORDER_AGENT_REPORT_BrandServer = "BrandServer";
    public static final String ORDER_AGENT_REPORT_BrandLaptop = "BrandLaptop";
    public static final String ORDER_AGENT_REPORT_BrandTablet = "BrandTablet";
    public static final String ORDER_AGENT_REPORT_BrandDesktop = "BrandDesktop";
    public static final String ORDER_AGENT_REPORT_BrandWorkstation = "BrandWorkstation";
    public static final String ORDER_AGENT_REPORT_BrandWarranty = "BrandWarranty";
    public static final String ORDER_AGENT_REPORT_COSMETIC = "agent_report_cosmetic";
    public static final String ORDER_AGENT_REPORT_DOCKING = "dockingStation";
    public static final String ORDER_REPORT_SUMMARY_RESULT = "reportList";
    public static final String ORDER_REPORT_SUMMARY_AGENT_RESULT = "reportAgentList";
    public static final String ORDER_BY_SHOPPER_AJAX_FORWARD = "agenttools.agents.order.shopper.ajax";
    public static final String LIST_ORDER_PENDING_MORE_FORWARD = "agenttools.list_order_pending_db.more";
    public static final String ORDER_AGENT_ORDER_FORWARD_PAGING = "agenttools.agents.order.paging";
    public static final int I_YEAR()
    {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.YEAR);
    }

    public static final String SEARCH_RESULT_BY_REPORT_EPP = "agenttool.epp_report.result";
    public static final String EPP_ID = "epp_id";
    public static final String DESCRIPTION = "description";
    public static final String CREATEDATE = "createdDate";
    public static final String ORDERNUMBER = "orderNumber";
    public static final String EST_SUBTOTAL = "est_subtotal";
    public static final String VOLUME_DISCOUNT = "volume_discount";
    public static final String DFS_DISCOUNT = "dfs_discount";
    public static final String COR_DISCOUNT = "cor_discount";
    public static final String SHIPPING_TOTAL = "shipping_total";
    public static final String TOTAL_TOTAL = "total_total";
    public static final String ORDER_DB_HELD_RESULT_FOR = "agenttools.order_db.held.result";
    public static final String ORDER_DB_HELD_AGENT = "agenttools.order_db.held.agent";
    public static final String ORDER_DB_HELD_AGENT_RESULT = "agenttools.order_db.held.agent.result";
    public static final String LIST_ORDER_LIST_DISCOUNT = "agenttools.order_db.list.discount";
    public static final String UPDATE_ORDER_LIST_DISCOUNT = "agenttools.order_db.update.discount";
    public static final String LIST_SEARCH_CREDIT_REPORT_ORDER = "agenttools.search.creadit.report.order";
    public static final String VIEW_CREDIT_REPORT = "agenttools.view.creadit.report.order";
    public static final String VIEW_TOTAL_CREDIT_REPORT_ORDER = "agenttools.view.total.creadit.report.order";
    public static final String LIST_SEARCH_SUMMARY_INVENTORY_REPORT = "agenttools.search.inventory.report.order";
    public static final String VIEW_SUMMARY_INVENTORY_REPORT = "agenttools.view.inventory.report.order";
    public static final String VIEW_ASSET_REPORT = "agenttools.view.asset.report.order";
    public static final String LIST_SEARCH_ASSET_REPORT = "agenttools.search.asset.report.order";
    public static final String SHOW_PRINT_INVENTORY_REPORT_ORDER = "agenttools.export.exel.inventory.report.order";
    public static final String SHOW_PRINT_CREDIT_REPORT_ORDER = "agenttools.export.exel.creadit.report.order";
    public static final String SHOW_PRINT_ASSET_REPORT_ORDER = "agenttools.export.exel.asset.report.order";

    /*
     * BASKET Author Vinhhq
     */
    public static final String SHOW_BASKET = "agenttools.basket.show";

    public static final String DB_FIELD_BILL_TITLE = "bill_to_title";
    public static final String DB_FIELD_SHIP_TITLE = "ship_to_title";
    public static final String DB_FIELD_BILL_PHONEEXT = "bill_to_phoneext";
    public static final String DB_FIELD_SHIP_PHONEEXT = "ship_to_phoneext";
    public static final String DB_FIELD_TAX_EXEMPT_EXPIRE = "tax_exempt_expire";
    public static final String DB_FIELD_TAX_EXEMPT_NUMBER = "tax_exempt_number";

    public static final String PERCDISCOUNT = "percdiscount";
    public static final String DATAPICKER_FROM = "datepickerFrom";
    public static final String DATAPICKER_TO = "datepickerTo";
    public static final String ASSETDATETYPE = "datetype";
    public static final String FORWARD_AGENT_REPORT = "agenttools.forward.agent.report";
    public static final String SHOW_PROCESSOR = "agenttools.show.processor.agent.report";
    public static final String SHOW_PROCESSOR_RESULT = "listProcessor";
    public static final String SHOW_MODEL = "agenttools.show.model.agent.report";
    public static final String SHOW_MODEL_RESULT = "listModel";
    public static final String DB_FIELD_SPEC_DISCOUNT = "specialty_discount";
    public static final String DB_FIELD_TAX_EXEMPT = "tax_exempt";
    public static final String DB_FIELD_LOGIN_ID = "loginID";
    public static final String DB_FIELD_PASSWORD = "password";
    public static final String DB_FIELD_HINT = "hint";
    public static final String DB_FIELD_ANSWER = "answer";
    public static final String DB_FIELD_PROMO_EMAIL = "promo_email";
    public static final String DB_FIELD_LOA = "loa";
    public static final String DB_FIELD_LAT_PER_DISCOUNT = "latperdiscount";
    public static final String DB_FIELD_LAT_AMT_DISCOUNT = "latamtdiscount";
    public static final String DB_FIELD_LAT_EXP_DATE = "latexpdate";

    public static final String DB_FIELD_INS_PER_DISCOUNT = "insperdiscount";
    public static final String DB_FIELD_INS_AMT_DISCOUNT = "insamtdiscount";
    public static final String DB_FIELD_INS_EXP_DATE = "insexpdate";

    public static final String DB_FIELD_OPT_PER_DISCOUNT = "optperdiscount";
    public static final String DB_FIELD_OPT_AMT_DISCOUNT = "optamtdiscount";
    public static final String DB_FIELD_OPT_EXP_DATE = "optexpdate";

    public static final String DB_FIELD_DIM_PER_DISCOUNT = "dimperdiscount";
    public static final String DB_FIELD_DIM_AMT_DISCOUNT = "dimamtdiscount";
    public static final String DB_FIELD_DIM_EXP_DATE = "dimexpdate";

    public static final String DB_FIELD_MON_PER_DISCOUNT = "monperdiscount";
    public static final String DB_FIELD_MON_AMT_DISCOUNT = "monamtdiscount";
    public static final String DB_FIELD_MON_EXP_DATE = "monexpdate";

    public static final String DB_FIELD_SER_PER_DISCOUNT = "serperdiscount";
    public static final String DB_FIELD_SER_AMT_DISCOUNT = "seramtdiscount";
    public static final String DB_FIELD_SER_EXP_DATE = "serexpdate";

    public static final String DB_FIELD_WORK_PER_DISCOUNT = "worperdiscount";
    public static final String DB_FIELD_WORK_AMT_DISCOUNT = "woramtdiscount";
    public static final String DB_FIELD_WORK_EXP_DATE = "worexpdate";
    public static final String SHOW_REPORT = "agenttools.show.agent.report.order";
    public static final String CATID = "catid";
    public static final String OSTYPE = "ostype";
    public static final String COSMETIC = "cosmetic";
    public static final String BRANDTYPE = "brandType";
    public static final String CATTYPE = "catType";
    public static final String MODEL = "model";
    public static final String DATE1 = "date1";
    public static final String DATE2 = "date2";
    public static final String PROCTYPE = "proctype";
    public static final String DATE_VIEW_CREDIT_REPORT = "date";
    public static final String DATE_VIEW_AGENT_REPORT = "date";
    public static final String PROSELL_LIST = "procsellist";
    public static final String ORDER_VIEW_PENDING = "agenttools.order.pending";
    public static final String ATTR_ORDER_VIEW_PENDING = "attr_agenttools.order.pending";
    public static final String ATTR_ORDER_VIEW_PENDING_AGENT_IDENTER = "attr_agenttools.order.pending.identer";
    public static final String ATTR_TEST = "test";
    public static final String SHORT_SHIPPING = "short_shipping";
    public static final String SHOW_ACCOUNT_TYPE = "show Account Type";
    public static final String LIST_FOLLOW_ORDER_NUMBER = "list follow ordernumber";

    public static final String FRAUD_DETAIL = "fraud detail";
    public static final String TITLE_ORDER_NUMBER = "Title Order Number";
    public static final String ORDER_ID_PENDING = "order id pending";
    public static final String ORDER_AGENT_ORDER_PRODUCT_NUMBER = "order.agenttool.view.product.number";
    public static final String ORDER_VIEW_PENDING_SEARCH = "agenttools.order.pending.search";
    public static final String CHECK_TRACK_NUMBER = "Tracking_number";
    public static final String EXPIRATION_DAYS = "expirationdate";
    public static final String SHOPPER_ADD_NOTES_SUCCESS = "shopper.add.note.success";
    public static final String SHOPPER_LIST_ADMIN_INFO = "shopper.list.admin.info";
    public static final String SHOPPER_LIST_AGENT_INFO = "shopper.list.agent.info";
    public static final String SHOPPER_PAGE_FORWARD_NOTES = "shopper.paging.agent.info.notes";
    public static final String SHOPPER_PAGE_FORWARD_MORE_NOTES = "shopper.paging.agent.info.more.notes";

    /*
     * Checkout Author Vinhhq
     */
    public static final String SHOW_CHECKOUT = "agenttools.checkout.show";
    public static final String SHOW_CHECKOUT_2 = "agenttools.checkout2.show";
    public static final String SHOW_CHECKOUT_3 = "agenttools.checkout3.show";
    public static final String SHOW_CHECKOUT_FINAL = "agenttools.checkout.final.show";
    public static final String SHOW_PRINT_ORDER = "agenttools.print.order.show";

    public static final String SHOW_CSC = "agenttools.csc.show";

    public static final String LINK_FOOTER_DEFAULT = "agenttools.link.footer.default";
    public static final String LINK_FOOTER_DEFAULT1 = "agenttools.link.footer.default1";
    public static final String LINK_FOOTER_DEFAULT2 = "agenttools.link.footer.default2";

    public static final String GLOBAL_EXPORT_EXCEL = "agenttool.ship_report.export.exel";
    public static final String TYPE_HOLD_ORDER = "typehold";
    public static final int ID_LENGTH = 32;

    public static final String IS_SHOPPER = "isShopper";
    public static final String IS_CUSTOMER_NEW = "isCustomerNew";
    public static final int[] ARR_ROW_ON_PAGE = {
        10, 20, 30, 40, 50, 75, 100, 125, 150, 175, 200
    };
    public static final String ATTR_CHECKOUT_SHOP_AS = "attr_checkout_shop_as";
    public static final String ATTR_AGENT_OF_CUSTOMER = "attr_agent_of_customer";
    
    public static final String OTHER_SALE_TYPE = ";-1;0;62;63;188;192;";
    public static final String ATTR_BASKET_ALL = "attr_basket_all";
    public static String generateID(int tmpLength)
    {
        StringBuilder tmpGUID = new StringBuilder();
        String strValid = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random rand = new Random();
        int num;
        for (int tmpCounter = 0; tmpCounter < tmpLength; tmpCounter++)
        {
            num = rand.nextInt((tmpLength - 1));
            tmpGUID.append(strValid.substring(num, (num + 1)));
        }

        return tmpGUID.toString();
    }

    public static String getCurrentDate()
    {
        String s = "";
        DateFormat formatter;
        formatter = new SimpleDateFormat("MM/dd/yyyy");
        Date date = new Date();
        s = formatter.format(date);
        return s;
    }

    public static String convertValueEmpty(String textInput)
    {
        if (null == textInput || textInput.isEmpty())
        {
            return "";
        }
        else
            return textInput;
    }

    public static String collapseRowBR(String val, String prefix)
    {
        String strReturn = "";
        if (val != null && val.isEmpty() == false)
        {
            strReturn = prefix + val + "<BR/>";
            strReturn = strReturn.trim();
        }
        return strReturn;
    }

    public static String collapseRowNotBR(String val, String prefix)
    {
        String strReturn = "";
        if (null != val && val.isEmpty() == false)
        {
            strReturn = prefix + val + ",";
            strReturn = strReturn.trim();
        }
        return strReturn;
    }

    public static String collapseRowNotBRNotPrefix(String val, String prefix)
    {
        String strReturn = "";
        if (null != val && val.isEmpty() == false)
        {
            strReturn = prefix + val + "<BR/>";
            strReturn = strReturn.trim();
        }

        return strReturn;
    }

    public static String collapseRowN(String val, String prefix)
    {
        String strReturn = "";
        if (val != null && !val.trim().isEmpty())
        {
            strReturn = prefix + val + "\n";
        }
        return strReturn;
    }

    public static String getOptionMonth(int vMonth)
    {
        String optionMonth = "";

        String[] strMonths = new String[] {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };

        for (int i = 0; i < strMonths.length; i++)
        {
            String strSelected = "";
            if (vMonth == (i + 1))
            {
                strSelected = "selected";
            }
            optionMonth = optionMonth.concat("<option value='" + (i + 1) + "' " + strSelected + ">" + strMonths[i] + "</option>");
        }

        optionMonth = "<select name='_cc_expmonth'>" + optionMonth + "</select>";

        return optionMonth;
    }

    public static String getOptionYear(int vYear)
    {
        Calendar cal = Calendar.getInstance();
        String optionYear = "";

        int year = cal.get(Calendar.YEAR);

        optionYear += "<option value='" + year + "'>" + year + "</option>";
        for (int i = 1; i <= 8; i++)
        {
            String strSelected = "";
            if (vYear == (year + i))
            {
                strSelected = "selected";
            }
            optionYear = optionYear.concat("<option value='" + (year + i) + "' " + strSelected + " >" + (year + i) + "</option>");
        }

        optionYear = "<select name='_cc_expyear'>" + optionYear + "</select>";

        return optionYear;
    }

    public static boolean checkFloatNumber(String sNumber)
    {
        try
        {
            Float.parseFloat(sNumber);
            return true;
        }
        catch (Exception e)
        {
            // TODO: handle exception
            return false;
        }
    }

    public static String removeChar(String s, char c)
    {
        String r = "";
        for (int i = 0; i < s.length(); i++)
        {
            if (s.charAt(i) != c)
                r += s.charAt(i);
        }
        return r;
    }

    public static String currentFullDate()
    {
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_NOW);
        String dateNow = formatter.format(currentDate.getTime());

        return dateNow;

    }

    public static boolean isIntNumber(String num)
    {
        try
        {
            Integer.parseInt(num);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }
    public static String getMonth(int month) 
    {
        return new DateFormatSymbols().getShortMonths()[month-1];
    }
}
