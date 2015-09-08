package com.dell.enterprise.agenttool.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.DAO.ProductDAO;
import com.dell.enterprise.agenttool.actions.validation.ShopperCheckoutValidation;
import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.EppPromotionRow;
import com.dell.enterprise.agenttool.model.EstoreBasketItem;
import com.dell.enterprise.agenttool.model.Product;
import com.dell.enterprise.agenttool.model.SiteReferral;
import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.services.BasketService;
import com.dell.enterprise.agenttool.services.CheckoutService;
import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;
import com.dell.enterprise.agenttool.util.MailUtils;

public class Shopper extends DispatchAction
{
    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.Shopper");

    public final ActionForward validateCustomer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "agenttool.shopper.errors.result";
        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
        boolean usingManager =
            (Converter.stringToBoolean(session.getAttribute(Constants.IS_ADMIN).toString()) || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")));
        String agent_level = "";
        if (session.getAttribute("agent_level") != null)
            agent_level = session.getAttribute("agent_level").toString();
        List<String> errors = new ArrayList<String>();
        String section = request.getParameter("section");

        if (!ShopperCheckoutValidation.check_hasValue(request.getParameter(Constants.DB_FIELD_BILL_FNAME), "TEXT"))
        {
            errors.add("You must give your billing first name.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter(Constants.DB_FIELD_BILL_LNAME), "TEXT"))
        {
            errors.add("You must give your billing last name.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter(Constants.DB_FIELD_BILL_ADDRESS_1), "TEXT"))
        {
            errors.add("You must give your billing address.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_city"), "TEXT"))
        {
            errors.add("You must give your billing city.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_phone_area"), "PHONE3"))
        {
            errors.add("Your billing area code must be three digits. Ex: (XXX) xxx-xxx");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_phone_exch"), "PHONE3"))
        {
            errors.add("Your billing phone number exchange must be three digits. Ex:  (xxx) XXX-xxx");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_phone"), "PHONE4"))
        {
            errors.add("Your billing phone number must end with four digits. Ex: (xxx) xxx-XXXX");
        }       
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("equip_use"), "RADIO"))
        {
            if ((section.equalsIgnoreCase("new_checkout")) || (section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_register")))
                errors.add("Please tell us how you intend to use DFS Direct Sales equipment.");
        }
        else if (((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout")) || (section.equalsIgnoreCase("new_register"))) && usingManager)
        {
            if (request.getParameter("agent_ID").toString().equalsIgnoreCase("0"))
            {
                errors.add("Incomplete Agent. Please select an Agent before submitting.");
            }           
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_country"), "SELECT"))
        {
            errors.add("You must give your country.");
        }
        else if (ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_country"), "SELECT"))
        {
            String CountrySelected = request.getParameter("bill_to_country");
            if (CountrySelected.equalsIgnoreCase("US"))
            {
                if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_state"), "SELECT"))
                {
                    errors.add("You must give your state.");
                }
                if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_postal"), "SELECT"))
                {
                    errors.add("You must give your zip.");
                }
            }
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_country"), "SELECT"))
        {
            errors.add("You must give your country.");
        }
        else if (!ShopperCheckoutValidation.isEmailAddress(request.getParameter("bill_to_email")))
        {
            errors.add("You must enter a valid Billing E-mail contact address.");
        }
        else if (!ShopperCheckoutValidation.isEmailAddress(request.getParameter("ship_to_email")))
        {
            errors.add("You must enter a valid Shipping E-mail contact address.");
        }

        if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_fname"), "TEXT"))
        {
            errors.add("You must give your shipping first name.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_lname"), "TEXT"))
        {
            errors.add("You must give your shipping last name.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_address1"), "TEXT"))
        {
            errors.add("You must give your shipping address.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_city"), "TEXT"))
        {
            errors.add("You must give your shipping city.");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_phone_area"), "PHONE3"))
        {
            errors.add("Your shipping area code must be three digits. Ex: (XXX) xxx-xxx");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_phone_exch"), "PHONE3"))
        {
            errors.add("Your shipping phone number exchange must be three digits. Ex: (xxx) XXX-xxx");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_phone"), "PHONE4"))
        {
            errors.add("Your shipping phone number must end with four digits. Ex: (xxx) xxx-XXXX");
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("bill_to_country"), "SELECT"))
        {
            errors.add("You must give your country.");
        }
        else if (ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_country"), "SELECT"))
        {
            String CountrySelected = request.getParameter("ship_to_country");
            if (CountrySelected.equalsIgnoreCase("US"))
            {
                if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_state"), "SELECT"))
                {
                    errors.add("You must give your shipping state.");
                }
                if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_postal"), "SELECT"))
                {
                    errors.add("You must give your shipping zip.");
                }
            }
        }
        else if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("ship_to_country"), "SELECT"))
        {
            errors.add("You must give your shipping country.");
        }

        if (usingManager || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")))
        {
            String[] tmpArray =
                {
                    "lat_pd",
                    "latamtdiscount",
                    "latperdiscount",
                    "latexpdate",
                    "Latitude Notebooks",
                    "ins_pd",
                    "insamtdiscount",
                    "insperdiscount",
                    "insexpdate",
                    "Inspiron Notebooks",
                    "opt_pd",
                    "optamtdiscount",
                    "optperdiscount",
                    "optexpdate",
                    "Optiplex Desktops",
                    "dim_pd",
                    "dimamtdiscount",
                    "dimperdiscount",
                    "dimexpdate",
                    "Dimension Desktops",
                    "mon_pd",
                    "monamtdiscount",
                    "monperdiscount",
                    "monexpdate",
                    "Monitors",
                    "ser_pd",
                    "seramtdiscount",
                    "serperdiscount",
                    "serexpdate",
                    "Servers",
                    "wor_pd",
                    "woramtdiscount",
                    "worperdiscount",
                    "worexpdate",
                    "Workstations"
                };
            Object theChkBox;
            Object theAmtField;
            Object thePerField;
            Object theExpField;
            String theProduct = "";
            int maxDiscountAmt = Converter.stringToInt(session.getAttribute("maxDiscountAmt").toString());
            int maxDiscount = Converter.stringToInt(session.getAttribute("maxDiscount").toString());

            for (int j = 0; j < tmpArray.length; j += 5)
            {
                theChkBox = request.getParameter(tmpArray[j]);
                theAmtField = request.getParameter(tmpArray[j + 1]);
                thePerField = request.getParameter(tmpArray[j + 2]);
                theExpField = request.getParameter(tmpArray[j + 3]);
                theProduct = tmpArray[j + 4];
                List<String> errorsTmp =
                    ShopperCheckoutValidation.checkValidDiscount(usingManager, agent_level, theChkBox, theAmtField, thePerField, theExpField, theProduct, maxDiscountAmt, maxDiscount);
                if ((errorsTmp != null) && (errorsTmp.size() > 0))
                {
                    for (int i = 0; i < errorsTmp.size(); i++)
                        errors.add(errorsTmp.get(i));
                }
            }

            if (ShopperCheckoutValidation.check_hasValue(request.getParameter("tax_exempt"), "RADIO"))
            {
                if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("tax_exempt_number"), "TEXT"))
                {
                    errors.add("You must specifify a tax exemption Number.");
                }
                if (!ShopperCheckoutValidation.check_hasValue(request.getParameter("tax_exempt_expire"), "TEXT"))
                {
                    errors.add("You must specifify a tax exemption Expiration Date.");
                }
            }
            //Check exist username
            boolean isExist = ShopperCheckoutValidation.isExistShopperLoginID(request.getParameter("loginID"));
            if ((request.getParameter("loginID") != null) && isExist)
            {
                errors.add("User Name Setup area: Username already exists.");
            }
        }

        if ((section.equalsIgnoreCase("new") || (section.equalsIgnoreCase("new_checkout") || (section.equalsIgnoreCase("new_register")))) && usingManager)
        {
            if (request.getParameter("agent_ID").equalsIgnoreCase("0"))
            {
                    errors.add("Please select an Agent before submitting.");
            }           
        }

        if (!ShopperCheckoutValidation.checkLen(request.getParameter("bill_to_city"), 1, 50))
            errors.add("Bill To City must be a string between 1 and 50 characters.");

        if (!ShopperCheckoutValidation.checkLen(request.getParameter("bill_to_country"), 1, 50))
            errors.add("Bill To Country must be a string between 1 and 50 characters.");

        if (!ShopperCheckoutValidation.checkLen(request.getParameter("bill_to_postal"), 1, 50))
            errors.add("Bill To Zip must be a string between 5 and 15 characters.");

        String bill_to_country = request.getParameter("bill_to_country");
        if (bill_to_country.equalsIgnoreCase("USA") || bill_to_country.equalsIgnoreCase("US"))
        {
            if (!ShopperCheckoutValidation.checkLen(request.getParameter("bill_to_state"), 2, 2))
                errors.add("Bill To State must be 2 characters.");
        }

        if (!ShopperCheckoutValidation.checkLen(request.getParameter("ship_to_city"), 1, 50))
            errors.add("Ship To City must be a string between 1 and 50 characters.");

        if (!ShopperCheckoutValidation.checkLen(request.getParameter("ship_to_country"), 1, 50))
            errors.add("Ship To Country must be a string between 1 and 50 characters.");

        if (!ShopperCheckoutValidation.checkLen(request.getParameter("ship_to_postal"), 1, 50))
            errors.add("Ship To Zip must be a string between 5 and 15 characters.");

        String ship_to_country = request.getParameter("ship_to_country");
        if (ship_to_country.equalsIgnoreCase("USA") || ship_to_country.equalsIgnoreCase("US"))
        {
            if (!ShopperCheckoutValidation.checkLen(request.getParameter("ship_to_state"), 2, 2))
                errors.add("Ship To State must be 2 characters.");
        }

        //Validate postal
        List<String> errors1 =
            ShopperCheckoutValidation.verifyZipCode(request.getParameter("bill_to_city"), request.getParameter("bill_to_state"), request.getParameter("bill_to_postal"), request
                .getParameter("bill_to_county"), "Billing");
        for (int i = 0; i < errors1.size(); i++)
            errors.add(errors1.get(i));

        //Validate postal
        errors1 =
            ShopperCheckoutValidation.verifyZipCode(request.getParameter("ship_to_city"), request.getParameter("ship_to_state"), request.getParameter("ship_to_postal"), request
                .getParameter("ship_to_county"), "Shipping");
        for (int i = 0; i < errors1.size(); i++)
            errors.add(errors1.get(i));

        if (errors.size() > 0)
            request.setAttribute("errorsList", errors);

        return mapping.findForward(forward);
    }

    public final ActionForward new_checkout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "agenttool.shopper";
        request.setAttribute("section", "new_checkout");

        /*
         * CustomerServices service = new CustomerServices(); List<String>
         * states = service.getStates(); request.setAttribute("STATES", states);
         * 
         * List<String> countries = service.getCountries();
         * request.setAttribute("COUNTRIES", countries);
         * 
         * List<String> siteReferralSource = service.getSiteReferralSource();
         * request.setAttribute("SiteReferralSource", siteReferralSource);
         * 
         * Map<String, SiteReferral> siteReferralDescription =
         * service.getSiteReferralDescription();
         * request.setAttribute("SiteReferralDescription",
         * siteReferralDescription);
         * 
         * Map<String, String> allAdminUser = service.getAllUserTypeA();
         * request.setAttribute("AllAdminUser", allAdminUser);
         */

        return mapping.findForward(forward);
    }

    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {
        ActionForward forward;
        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);

        if (agent != null)
        {
            CustomerServices service = new CustomerServices();
            List<String> states = service.getStates();
            request.setAttribute("STATES", states);

            List<String> countries = service.getCountries();
            request.setAttribute("COUNTRIES", countries);

            //List<String> siteReferralSource = service.getSiteReferralSource();
            //request.setAttribute("SiteReferralSource", siteReferralSource);

            Map<String, SiteReferral> siteReferralDescription = service.getSiteReferralDescription();
            request.setAttribute("SiteReferralDescription", siteReferralDescription);

            Map<String, String[]> allAdminUser = service.getAllUserTypeA();
            request.setAttribute("AllAdminUser", allAdminUser);

            try
            {
                String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);
                if (method == null || method.isEmpty())
                {
                    String addNew = request.getParameter("manage");
                    if (addNew != null && addNew.equals("true"))
                        session.setAttribute(Constants.CUSTOMER_MANAGE, true);
                    else
                        session.removeAttribute(Constants.CUSTOMER_MANAGE);
                    forward = this.dispatchMethod(mapping, form, request, response, "viewShopper");
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
        }
        else
        {
            forward = mapping.findForward(Constants.SESSION_TIMEOUT);
            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equals("userLevel"))
                {
                    if (cookies[i].getValue().equals("login.do"))
                        forward = mapping.findForward(Constants.CUSTOMER_SESSION_TIMEOUT);
                }
            }
        }

        return forward;
    }

    public final ActionForward prepareCheckout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "agenttool.shopper";
        try
        {
            LOGGER.info("Execute Action");
            HttpSession session = request.getSession();
            CustomerServices service = new CustomerServices();
            List<String> states = service.getStates();
            request.setAttribute("STATES", states);

            List<String> countries = service.getCountries();
            request.setAttribute("COUNTRIES", countries);

            //List<String> siteReferralSource = service.getSiteReferralSource();
            //request.setAttribute("SiteReferralSource", siteReferralSource);

            Map<String, SiteReferral> siteReferralDescription = service.getSiteReferralDescription();
            request.setAttribute("SiteReferralDescription", siteReferralDescription);

            Map<String, String[]> allAdminUser = service.getAllUserTypeA();
            request.setAttribute("AllAdminUser", allAdminUser);

            String shopper_new = "";
            if(request.getParameter("shopper_new") != null)
            {
                shopper_new = request.getParameter("shopper_new");
            }
            
            
            String section = request.getParameter("section");
            //String shopas = request.getParameter("shopas");
            //String shopper_name = request.getParameter("shopper_name");
            Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);

            Customer customer = null;
            Object isCustomer = session.getAttribute(Constants.IS_CUSTOMER);

            Boolean byAgent = true;
            if (isCustomer != null && ((Boolean) isCustomer).booleanValue())
            {
                byAgent = false;
            }
            else
            {
                byAgent = true;
            }
            
            Boolean isShopper = false ;
            
            if (session.getAttribute(Constants.SHOPPER_ID) != null)
            {
                if (!shopper_new.equals(session.getAttribute(Constants.SHOPPER_ID).toString()))
                {
                    if (session.getAttribute(Constants.IS_SHOPPER) != null)
                    {
                        isShopper = (Boolean)session.getAttribute(Constants.IS_SHOPPER);
                        request.setAttribute(Constants.IS_SHOPPER, session.getAttribute(Constants.IS_SHOPPER));
                        session.removeAttribute(Constants.IS_SHOPPER);
                    }
                }
            }

            if (agent != null)
            {
                //String agent_level = agent.getUserLevel();
                boolean result = false;
                if ((shopper_new != null) && !Constants.isEmpty(shopper_new))
                {
                    LOGGER.info("Execute Action");

                    String oldShopperID = (session.getAttribute(Constants.SHOPPER_ID) == null) ? "" : session.getAttribute(Constants.SHOPPER_ID).toString();
                    String newShopperID = shopper_new;
                    //check existence of shopperId
                    customer = service.getCustomerByShopperID(newShopperID);
                    request.setAttribute("CUSTOMER_CHECKOUT", customer);
                    
                    if (customer != null)
                    {
                    
                        AuthenticationService authenticateService = new AuthenticationService();
                        Agent agentObject = authenticateService.getAgentByAgentId(customer.getAgent_id());
                        request.setAttribute("AgentObject", agentObject);
                    
                        newShopperID = customer.getShopper_id();

                        if (!newShopperID.equalsIgnoreCase(oldShopperID))
                        {
                            if (section.equalsIgnoreCase("checkout"))
                            {
                                result = service.performCheckout(newShopperID, oldShopperID, byAgent);
                            }
                            else
                            {
                                result = service.performNonCheckout(newShopperID, oldShopperID, byAgent);
                            }

                            session.setAttribute(Constants.SHOPPER_ID, newShopperID);
                        }

                        CheckoutService checkoutService = new CheckoutService();           
                        List<EstoreBasketItem> basketItemCheck = checkoutService.getItemCheck(newShopperID, byAgent);
                        request.setAttribute(Constants.ATTR_ITEM_BASKET, basketItemCheck);
                   
                    }
                }
                else
                {
                    /* fix edit Shopper by linhdo */
                    customer = service.getCustomerByShopperID(session.getAttribute(Constants.SHOPPER_ID).toString());
                    request.setAttribute("CUSTOMER_CHECKOUT", customer);
                    request.setAttribute("section", "manage");
                    
                    AuthenticationService authenticateService = new AuthenticationService();
                    Agent agentObject = authenticateService.getAgentByAgentId(customer.getAgent_id());
                    request.setAttribute("AgentObject", agentObject);

                }
            

                //add code by vinhh
                if (isCustomer == null)
                {
                    request.setAttribute(Constants.ATTR_CHECKOUT_SHOP_AS, true);
                    request.setAttribute(Constants.ATTR_CUSTOMER, customer);
                }
                //end add code
                //request.setAttribute("editShopper", "editShopper");
            }
            else
            {
                forward = Constants.LOGIN_VIEW;
            }

        }
        catch (Exception e)
        {
            System.out.println("Exception prepareCheckout");
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }
        return mapping.findForward(forward);
    }

    public final ActionForward checkout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "agenttool.shopper";
        HttpSession session = request.getSession();
        Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);
        String isManage = request.getParameter("hidManage");
        System.out.println(isManage);
        if (agent != null)
        {
            try
            {

                LOGGER.info("Execute Action checkout");

                String section = "checkout";
                CustomerServices service = new CustomerServices();
                Customer customer = new Customer();
                String shopper_new = request.getParameter("shopper_new");
                boolean usingManager =
                    (Converter.stringToBoolean(session.getAttribute(Constants.IS_ADMIN).toString()) || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")));

                if ((shopper_new != null) && !Constants.isEmpty(shopper_new))
                {

                    String phone = request.getParameter("bill_to_phone");
                    String phone1 = request.getParameter("bill_to_phone_exch");
                    String phone2 = request.getParameter("bill_to_phone_area");

                    if (phone != null)
                    {
                        if (phone.length() > 4)
                            customer.setBill_to_phone(phone);
                        else
                            customer.setBill_to_phone(phone2 + phone1 + phone);
                    }

                    phone = request.getParameter("ship_to_phone");
                    phone1 = request.getParameter("ship_to_phone_exch");
                    phone2 = request.getParameter("ship_to_phone_area");

                    if (phone != null)
                    {
                        if (phone.length() > 4)
                            customer.setShip_to_phone(phone);
                        else
                            customer.setShip_to_phone(phone2 + phone1 + phone);
                    }
                    
                    String fax = request.getParameter("bill_to_fax");
                    String fax1 = request.getParameter("bill_to_fax_exch");
                    String fax2 = request.getParameter("bill_to_fax_area");

                    if (fax != null)
                    {
                        if (fax.length() > 4)
                        {
                            customer.setBill_to_fax(fax);
                            customer.setShip_to_fax(fax);
                        }
                        else
                        {
                            customer.setBill_to_fax(fax2 + fax1 + fax);
                            customer.setShip_to_fax(fax2 + fax1 + fax);
                        }
                    }

                    customer.setShopper_id(shopper_new);
                    customer.setBill_to_fname(request.getParameter(Constants.DB_FIELD_BILL_FNAME));
                    customer.setBill_to_lname(request.getParameter("bill_to_lname"));
                    customer.setBill_to_title(request.getParameter("bill_to_title"));
                    customer.setBill_to_company(request.getParameter("bill_to_company"));
                    customer.setBill_to_address1(request.getParameter("bill_to_address1"));
                    customer.setBill_to_address2(request.getParameter("bill_to_address2"));
                    customer.setBill_to_city(request.getParameter("bill_to_city"));
                    customer.setBill_to_state(request.getParameter("bill_to_state"));
                    customer.setBill_to_postal(request.getParameter("bill_to_postal"));
                    customer.setBill_to_county(request.getParameter("bill_to_county"));
                    customer.setBill_to_country(request.getParameter("bill_to_country"));
                    //customer.setBill_to_fax(request.getParameter("bill_to_fax"));
                    //customer.setBill_to_phone(request.getParameter("bill_to_phone"));
                    customer.setBill_to_phoneext(request.getParameter("bill_to_phoneext"));
                    customer.setBill_to_email(request.getParameter("bill_to_email"));
                    customer.setShip_to_fname(request.getParameter("ship_to_fname"));
                    customer.setShip_to_lname(request.getParameter("ship_to_lname"));
                    customer.setShip_to_title(request.getParameter("ship_to_title"));
                    customer.setShip_to_company(request.getParameter("ship_to_company"));
                    customer.setShip_to_address1(request.getParameter("ship_to_address1"));
                    customer.setShip_to_address2(request.getParameter("ship_to_address2"));
                    customer.setShip_to_city(request.getParameter("ship_to_city"));
                    customer.setShip_to_postal(request.getParameter("ship_to_postal"));
                    customer.setShip_to_county(request.getParameter("ship_to_county"));
                    customer.setShip_to_email(request.getParameter("ship_to_email"));
                    customer.setShip_to_state(request.getParameter("ship_to_state"));
                    customer.setShip_to_country(request.getParameter("ship_to_country"));
                    //customer.setShip_to_phone(request.getParameter("ship_to_phone"));
                    customer.setShip_to_phoneext(request.getParameter("ship_to_phoneext"));
                    customer.setShip_to_email(request.getParameter("ship_to_email"));
                    customer.setShip_to_fax(request.getParameter("ship_to_fax"));
                    

                    if (usingManager || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")))
                    {
                        //Hard code -  DB not allow null
                        String tax_exp_date = request.getParameter("tax_exempt_expire");
                        tax_exp_date = ((tax_exp_date != null) && !tax_exp_date.equalsIgnoreCase("")) ? tax_exp_date : "01/01/1900";

                        customer.setTax_exempt(Converter.stringToInt(request.getParameter("tax_exempt")));
                        customer.setTax_exempt_number(request.getParameter("tax_exempt_number"));
                        customer.setTax_exempt_expire(Converter.stringToDate(tax_exp_date));

                        if (request.getParameter("lat_pd") != null)
                        {
                            int latperdiscount = Converter.stringToInt(request.getParameter("latperdiscount"));
                            int latamtdiscount = Converter.stringToInt(request.getParameter("latamtdiscount"));
                            customer.setLatperdiscount((latperdiscount == Integer.MAX_VALUE) ? 0 : latperdiscount);
                            customer.setLatamtdiscount((latamtdiscount == Integer.MAX_VALUE) ? 0 : latamtdiscount);
                            customer.setLatexpdate(Converter.stringToDate(request.getParameter("latexpdate")));
                        }

                        if (request.getParameter("ins_pd") != null)
                        {
                            int insperdiscount = Converter.stringToInt(request.getParameter("insperdiscount"));
                            int insamtdiscount = Converter.stringToInt(request.getParameter("insamtdiscount"));
                            customer.setInsperdiscount((insperdiscount == Integer.MAX_VALUE) ? 0 : insperdiscount);
                            customer.setInsamtdiscount((insamtdiscount == Integer.MAX_VALUE) ? 0 : insamtdiscount);
                            customer.setInsexpdate(Converter.stringToDate(request.getParameter("insexpdate")));
                        }

                        if (request.getParameter("opt_pd") != null)
                        {
                            int optperdiscount = Converter.stringToInt(request.getParameter("optperdiscount"));
                            int optamtdiscount = Converter.stringToInt(request.getParameter("optamtdiscount"));
                            customer.setOptperdiscount((optperdiscount == Integer.MAX_VALUE) ? 0 : optperdiscount);
                            customer.setOptamtdiscount((optamtdiscount == Integer.MAX_VALUE) ? 0 : optamtdiscount);
                            customer.setOptexpdate(Converter.stringToDate(request.getParameter("optexpdate")));
                        }

                        if (request.getParameter("dim_pd") != null)
                        {
                            int dimperdiscount = Converter.stringToInt(request.getParameter("dimperdiscount"));
                            customer.setDimperdiscount((dimperdiscount == Integer.MAX_VALUE) ? 0 : dimperdiscount);
                            int dimamtdiscount = Converter.stringToInt(request.getParameter("dimamtdiscount"));
                            customer.setDimamtdiscount((dimamtdiscount == Integer.MAX_VALUE) ? 0 : dimamtdiscount);
                            customer.setDimexpdate(Converter.stringToDate(request.getParameter("dimexpdate")));
                        }

                        if (request.getParameter("mon_pd") != null)
                        {
                            int monperdiscount = Converter.stringToInt(request.getParameter("monperdiscount"));
                            customer.setMonperdiscount((monperdiscount == Integer.MAX_VALUE) ? 0 : monperdiscount);
                            int monamtdiscount = Converter.stringToInt(request.getParameter("monamtdiscount"));
                            customer.setMonamtdiscount((monamtdiscount == Integer.MAX_VALUE) ? 0 : monamtdiscount);
                            customer.setMonexpdate(Converter.stringToDate(request.getParameter("monexpdate")));
                        }

                        if (request.getParameter("ser_pd") != null)
                        {
                            int serperdiscount = Converter.stringToInt(request.getParameter("serperdiscount"));
                            customer.setSerperdiscount((serperdiscount == Integer.MAX_VALUE) ? 0 : serperdiscount);
                            int seramtdiscount = Converter.stringToInt(request.getParameter("seramtdiscount"));
                            customer.setSeramtdiscount((seramtdiscount == Integer.MAX_VALUE) ? 0 : seramtdiscount);
                            customer.setSerexpdate(Converter.stringToDate(request.getParameter("serexpdate")));
                        }

                        if (request.getParameter("wor_pd") != null)
                        {
                            int worperdiscount = Converter.stringToInt(request.getParameter("worperdiscount"));
                            customer.setWorperdiscount((worperdiscount == Integer.MAX_VALUE) ? 0 : worperdiscount);
                            int woramtdiscount = Converter.stringToInt(request.getParameter("woramtdiscount"));
                            customer.setWoramtdiscount((woramtdiscount == Integer.MAX_VALUE) ? 0 : woramtdiscount);
                            customer.setWorexpdate(Converter.stringToDate(request.getParameter("worexpdate")));
                        }

                        if (request.getParameter("loa") != null)
                            customer.setLoa(request.getParameter("loa").toString().equalsIgnoreCase("0") ? "N" : "Y");
                        else
                            customer.setLoa("N");
                        
                        customer.setAccount_type(request.getParameter("account_type"));

                        BigDecimal credit_line = (request.getParameter("credit_line") == null || request.getParameter("credit_line").isEmpty()) ?   new BigDecimal(0) :  Converter.stringToBigDecimal(Constants.removeChar(request.getParameter("credit_line"), '$'));
                        customer.setCreditline((credit_line == null) ? BigDecimal.ZERO : credit_line);
                        customer.setCreditexp(request.getParameter("credit_expire"));
                        //BigDecimal credit_available = Converter.stringToBigDecimal(Constants.removeChar(request.getParameter("credit_available"), '$'));
                       // customer.setCreditavail((credit_available == null) ? BigDecimal.ZERO : credit_available);
                        //customer.setCreditcomment(request.getParameter("credit_comment"));

                    }

                    if ((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout")))
                        customer.setLoginID(request.getParameter("loginID"));

                    if ((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout")) || (section.equalsIgnoreCase("manage")))
                    {
                        customer.setPassword(request.getParameter("pwd1"));
                        customer.setHint(request.getParameter("hint"));
                        customer.setAnswer(request.getParameter("answer"));
                    }

                    // if (request.getParameter("promo_email") != null)
                    //      customer.setPromo_email(Converter.stringToInt(request.getParameter("promo_email").toString()));
                    //   else
                    customer.setPromo_email(0);
                    
                    String strCheckUpdate = (String) request.getParameter("changeProfile");
                    //System.out.println("strCheckUpdate  : " + strCheckUpdate);
                    //Boolean boolCheckUpdate = Boolean.parseBoolean(strCheckUpdate);
                    if(session.getAttribute(Constants.IS_CUSTOMER)!=null)
                    {
                    	Boolean result = service.updateCustomer(customer, usingManager, section);
                        if (result)
                        {
                            request.setAttribute("CUSTOMER_CHECKOUT", customer);
                            //getCheckout(form, request);
                            session.removeAttribute("CUSTOMER_CHECKOUT_SESSION");
    	                    session.setAttribute("CUSTOMER_CHECKOUT_SESSION", customer);
                            forward = Constants.SHOW_CHECKOUT;
                        }
                    }else{
	                    //check box is checked then update new  data
	                    if(strCheckUpdate != "" && strCheckUpdate!= null){
	                    	
	                        service.updateCustomer(customer, usingManager, section);
	                    }
	                    request.setAttribute("CUSTOMER_CHECKOUT", customer);
	                    session.removeAttribute("CUSTOMER_CHECKOUT_SESSION");
	                    session.setAttribute("CUSTOMER_CHECKOUT_SESSION", customer);
	                    forward = Constants.SHOW_CHECKOUT;
                    }
                    
                    /*if(strCheckUpdate != "" && strCheckUpdate!= null){
                    	
                        service.updateCustomer(customer, usingManager, section);
                    }
                    request.setAttribute("CUSTOMER_CHECKOUT", customer);
                    session.removeAttribute("CUSTOMER_CHECKOUT_SESSION");
                    session.setAttribute("CUSTOMER_CHECKOUT_SESSION", customer);
                    forward = Constants.SHOW_CHECKOUT;*/
                    
                    //code old fix update contacinfo comment by tien
                    /*Boolean result = service.updateCustomer(customer, usingManager, section);
                    if (result)
                    {
                        request.setAttribute("CUSTOMER_CHECKOUT", customer);
                        //getCheckout(form, request);
                        forward = Constants.SHOW_CHECKOUT;
                    }*/

                    /* Linh fix manage */

                    if (((session.getAttribute(Constants.CUSTOMER_MANAGE) != null) && ((Boolean) session.getAttribute(Constants.CUSTOMER_MANAGE))) || isManage.equalsIgnoreCase("true"))
                    {
                        customer.setPassword(request.getParameter("pwd1"));
                        customer.setHint(request.getParameter("hint"));
                        customer.setAnswer(request.getParameter("answer"));

                        section = "manage";
                        if ((request.getParameter("pwd1") != null) && request.getParameter("pwd1").equals(""))
                        {
                            isManage = "has";
                        }
                        service.updateCustomer(customer, usingManager, section);
                        
                        
                    	
                    }

                    /* End */

                }

            }
            catch (Exception e)
            {
                forward = Constants.ERROR_PAGE_VIEW;
                System.out.println("Error checkout in Customer");
                e.printStackTrace();
            }
        }
        else
        {
            forward = Constants.LOGIN_VIEW;
        }

        if ((session.getAttribute(Constants.CUSTOMER_MANAGE) != null) || isManage.equalsIgnoreCase("true"))
        {
        	if (!forward.equalsIgnoreCase(Constants.ERROR_PAGE_VIEW))
        	{
        		//session.removeAttribute(Constants.CUSTOMER_MANAGE);
        		//author : fix by vinhhq 
        		Authentication authentication = new Authentication();                		
        		//return authentication.shopAsAgent(mapping, form, request, (HttpServletResponse)session.getAttribute("A"));
        		
        		//fix by huyngo
        		return authentication.redirectAfterUpdateCustomer(mapping, form, request, response);
        		//return mapping.findForward(Constants.LOGIN_SUCCESS);
        	}
        }
        else
        {
            Checkout checkout = new Checkout();
            return checkout.showCheckout(mapping, form, request, response);
        }
        
        return mapping.findForward(forward);
    }

    public final ActionForward addNewCustomer(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward = "agenttool.shopper";
        HttpSession session = request.getSession();
        try
        {

            LOGGER.info("Execute Action");
            CustomerServices service = new CustomerServices();
            Customer customer = new Customer();
            String shopper_new = Constants.generateID(Constants.ID_LENGTH);
            Agent agent = (Agent) session.getAttribute(Constants.AGENT_INFO);

            boolean usingManager =
                (Converter.stringToBoolean(session.getAttribute(Constants.IS_ADMIN).toString()) || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")));
            String section = "new_checkout";

            if ((shopper_new != null) && !Constants.isEmpty(shopper_new))
            {

                String phone = request.getParameter(Constants.DB_FIELD_BILL_PHONE);
                String phone1 = request.getParameter("bill_to_phone_exch");
                String phone2 = request.getParameter("bill_to_phone_area");

                if (phone != null)
                {
                    if (phone.length() > 4)
                        customer.setBill_to_phone(phone);
                    else
                        customer.setBill_to_phone(phone2 + phone1 + phone);
                }

                phone = request.getParameter("ship_to_phone");
                phone1 = request.getParameter("ship_to_phone_exch");
                phone2 = request.getParameter("ship_to_phone_area");

                if (phone != null)
                {
                    if (phone.length() > 4)
                        customer.setShip_to_phone(phone);
                    else
                        customer.setShip_to_phone(phone2 + phone1 + phone);
                }

                String fax = request.getParameter("bill_to_fax");
                String fax1 = request.getParameter("bill_to_fax_exch");
                String fax2 = request.getParameter("bill_to_fax_area");

                if (fax != null)
                {
                    if (fax.length() > 4)
                    {
                        customer.setBill_to_fax(fax);
                        customer.setShip_to_fax(fax);
                    }
                    else
                    {
                        customer.setBill_to_fax(fax2 + fax1 + fax);
                        customer.setShip_to_fax(fax2 + fax1 + fax);
                    }
                }

                customer.setShopper_id(shopper_new);
                customer.setBill_to_fname(request.getParameter(Constants.DB_FIELD_BILL_FNAME));
                customer.setBill_to_lname(request.getParameter(Constants.DB_FIELD_BILL_LNAME));
                customer.setBill_to_title(request.getParameter(Constants.DB_FIELD_BILL_TITLE));
                customer.setBill_to_company(request.getParameter(Constants.DB_FIELD_BILL_COMPANY));
                customer.setBill_to_address1(request.getParameter(Constants.DB_FIELD_BILL_ADDRESS_1));
                customer.setBill_to_address2(request.getParameter(Constants.DB_FIELD_BILL_ADDRESS_2));
                customer.setBill_to_city(request.getParameter("bill_to_city"));

                if (request.getParameter("howdidyouhear") != null)
                    customer.setHeardAboutSiteFrom(Integer.parseInt(request.getParameter("howdidyouhear")));
                customer.setEquip_use(request.getParameter("equip_use"));
                customer.setAgent_id(Integer.parseInt(request.getParameter("agent_ID")));

                customer.setAgent_id_exp(null);

                customer.setBill_to_state(request.getParameter("bill_to_state"));
                customer.setBill_to_postal(request.getParameter("bill_to_postal"));
                customer.setBill_to_county(request.getParameter("bill_to_county"));
                customer.setBill_to_country(request.getParameter("bill_to_country"));
                //customer.setBill_to_fax(request.getParameter("bill_to_fax"));
                //customer.setBill_to_phone(request.getParameter("bill_to_phone"));
                customer.setBill_to_phoneext(request.getParameter("bill_to_phoneext"));
                customer.setBill_to_email(request.getParameter("bill_to_email"));
                customer.setShip_to_fname(request.getParameter("ship_to_fname"));
                customer.setShip_to_lname(request.getParameter("ship_to_lname"));
                customer.setShip_to_title(request.getParameter("ship_to_title"));
                customer.setShip_to_company(request.getParameter("ship_to_company"));
                customer.setShip_to_address1(request.getParameter("ship_to_address1"));
                customer.setShip_to_address2(request.getParameter("ship_to_address2"));
                customer.setShip_to_city(request.getParameter("ship_to_city"));
                customer.setShip_to_postal(request.getParameter("ship_to_postal"));
                customer.setShip_to_county(request.getParameter("ship_to_county"));

                customer.setShip_to_state(request.getParameter("ship_to_state"));
                customer.setShip_to_country(request.getParameter("ship_to_country"));
                //customer.setShip_to_phone(request.getParameter("ship_to_phone"));
                customer.setShip_to_phoneext(request.getParameter("ship_to_phoneext"));
                customer.setShip_to_email(request.getParameter("ship_to_email"));
                //customer.setShip_to_fax(request.getParameter("ship_to_fax"));

                if (usingManager || ((agent != null) && (agent.getUserLevel() != null) && agent.getUserLevel().equalsIgnoreCase("A")))
                {
                    customer.setTax_exempt(Converter.stringToInt(request.getParameter("tax_exempt")));
                    customer.setTax_exempt_number(request.getParameter("tax_exempt_number"));
                    customer.setTax_exempt_expire(Converter.stringToDate(request.getParameter("tax_exempt_expire")));

                    if (request.getParameter("lat_pd") != null)
                    {
                        int latperdiscount = Converter.stringToInt(request.getParameter("latperdiscount"));
                        int latamtdiscount = Converter.stringToInt(request.getParameter("latamtdiscount"));
                        customer.setLatperdiscount((latperdiscount == Integer.MAX_VALUE) ? 0 : latperdiscount);
                        customer.setLatamtdiscount((latamtdiscount == Integer.MAX_VALUE) ? 0 : latamtdiscount);
                        customer.setLatexpdate(Converter.stringToDate(request.getParameter("latexpdate")));
                    }

                    if (request.getParameter("ins_pd") != null)
                    {
                        int insperdiscount = Converter.stringToInt(request.getParameter("insperdiscount"));
                        int insamtdiscount = Converter.stringToInt(request.getParameter("insamtdiscount"));
                        customer.setInsperdiscount((insperdiscount == Integer.MAX_VALUE) ? 0 : insperdiscount);
                        customer.setInsamtdiscount((insamtdiscount == Integer.MAX_VALUE) ? 0 : insamtdiscount);
                        customer.setInsexpdate(Converter.stringToDate(request.getParameter("insexpdate")));
                    }

                    if (request.getParameter("opt_pd") != null)
                    {
                        int optperdiscount = Converter.stringToInt(request.getParameter("optperdiscount"));
                        int optamtdiscount = Converter.stringToInt(request.getParameter("optamtdiscount"));
                        customer.setOptperdiscount((optperdiscount == Integer.MAX_VALUE) ? 0 : optperdiscount);
                        customer.setOptamtdiscount((optamtdiscount == Integer.MAX_VALUE) ? 0 : optamtdiscount);
                        customer.setOptexpdate(Converter.stringToDate(request.getParameter("optexpdate")));
                    }

                    if (request.getParameter("dim_pd") != null)
                    {
                        int dimperdiscount = Converter.stringToInt(request.getParameter("dimperdiscount"));
                        customer.setDimperdiscount((dimperdiscount == Integer.MAX_VALUE) ? 0 : dimperdiscount);
                        int dimamtdiscount = Converter.stringToInt(request.getParameter("dimamtdiscount"));
                        customer.setDimamtdiscount((dimamtdiscount == Integer.MAX_VALUE) ? 0 : dimamtdiscount);
                        customer.setDimexpdate(Converter.stringToDate(request.getParameter("dimexpdate")));
                    }

                    if (request.getParameter("mon_pd") != null)
                    {
                        int monperdiscount = Converter.stringToInt(request.getParameter("monperdiscount"));
                        customer.setMonperdiscount((monperdiscount == Integer.MAX_VALUE) ? 0 : monperdiscount);
                        int monamtdiscount = Converter.stringToInt(request.getParameter("monamtdiscount"));
                        customer.setMonamtdiscount((monamtdiscount == Integer.MAX_VALUE) ? 0 : monamtdiscount);
                        customer.setMonexpdate(Converter.stringToDate(request.getParameter("monexpdate")));
                    }

                    if (request.getParameter("ser_pd") != null)
                    {
                        int serperdiscount = Converter.stringToInt(request.getParameter("serperdiscount"));
                        customer.setSerperdiscount((serperdiscount == Integer.MAX_VALUE) ? 0 : serperdiscount);
                        int seramtdiscount = Converter.stringToInt(request.getParameter("seramtdiscount"));
                        customer.setSeramtdiscount((seramtdiscount == Integer.MAX_VALUE) ? 0 : seramtdiscount);
                        customer.setSerexpdate(Converter.stringToDate(request.getParameter("serexpdate")));
                    }

                    if (request.getParameter("wor_pd") != null)
                    {
                        int worperdiscount = Converter.stringToInt(request.getParameter("worperdiscount"));
                        customer.setWorperdiscount((worperdiscount == Integer.MAX_VALUE) ? 0 : worperdiscount);
                        int woramtdiscount = Converter.stringToInt(request.getParameter("woramtdiscount"));
                        customer.setWoramtdiscount((woramtdiscount == Integer.MAX_VALUE) ? 0 : woramtdiscount);
                        customer.setWorexpdate(Converter.stringToDate(request.getParameter("worexpdate")));
                    }

                    if (request.getParameter("loa") != null)
                        customer.setLoa(request.getParameter("loa").toString().equalsIgnoreCase("0") ? "N" : "Y");
                    else
                        customer.setLoa("N");

                    if (usingManager)
                    {
                        BigDecimal credit_line = Converter.stringToBigDecimal(request.getParameter("credit_line"));
                        customer.setCreditline((credit_line == null) ? BigDecimal.ZERO : credit_line);
                        customer.setCreditexp(request.getParameter("credit_expire"));
                        //BigDecimal credit_available = Converter.stringToBigDecimal(request.getParameter("credit_available"));
                        //customer.setCreditavail((credit_available == null) ? BigDecimal.ZERO : credit_available);
                        //customer.setCreditcomment(request.getParameter("credit_comment"));
                    }
                    customer.setAccount_type(request.getParameter("account_type"));

                }
                else
                {
                    customer.setTax_exempt(0);
                    customer.setTax_exempt_number("");
                    customer.setTax_exempt_expire(null);

                    customer.setLatperdiscount(0);
                    customer.setLatamtdiscount(0);
                    customer.setLatexpdate(null);

                    customer.setInsperdiscount(0);
                    customer.setInsamtdiscount(0);
                    customer.setInsexpdate(null);

                    customer.setOptperdiscount(0);
                    customer.setOptamtdiscount(0);
                    customer.setOptexpdate(null);

                    customer.setDimperdiscount(0);
                    customer.setDimamtdiscount(0);
                    customer.setDimexpdate(null);

                    customer.setMonperdiscount(0);
                    customer.setMonamtdiscount(0);
                    customer.setMonexpdate(null);

                    customer.setSerperdiscount(0);
                    customer.setSeramtdiscount(0);
                    customer.setSerexpdate(null);

                    customer.setWorperdiscount(0);
                    customer.setWoramtdiscount(0);
                    customer.setWorexpdate(null);

                    customer.setAgent_id(0);
                    customer.setAgent_id_exp(null);
                }

                customer.setLoginID(request.getParameter("loginID"));
                customer.setPassword(request.getParameter("pwd1"));
                customer.setHint(request.getParameter("hint"));
                customer.setAnswer(request.getParameter("answer"));

                //customer.setPromo_email((request.getParameter("promo_email") == null) ? 0 : Converter.stringToInt(request.getParameter("promo_email").toString()));

                customer.setPromo_email(0);

                Boolean result =
                    service.addNewCustomer(customer, usingManager, section, (session.getAttribute(Constants.SHOPPER_ID) == null) ? "" : session.getAttribute(Constants.SHOPPER_ID).toString());
                if (result)
                {
                    //#vinhhq remove send mail for create customer
                    //sendMailNewRegister(customer);
                    request.setAttribute("CUSTOMER_CHECKOUT", customer);
					session.removeAttribute("CUSTOMER_CHECKOUT_SESSION");
                    //request.setAttribute("CUSTOMER_CHECKOUT_SESSION", customer);
                    forward = "agenttools.checkout.show";

                    //#vinhhq 
                    session.setAttribute(Constants.SHOPPER_ID, customer.getShopper_id());
                    /*request.removeAttribute(Constants.ATTR_CUSTOMER);
                    request.setAttribute(Constants.ATTR_CUSTOMER, customer);*/
                }
            }

        }
        catch (Exception e)
        {
            forward = Constants.ERROR_PAGE_VIEW;
        }

        if (session.getAttribute(Constants.CUSTOMER_MANAGE) != null)
        {
            request.setAttribute(Constants.ATTR_CREATE_CUSTOMER, true);
            return mapping.findForward("agenttool.shopper.cust_lookup");
        }
        else
        {
            Checkout checkout = new Checkout();
            ActionForward caf =checkout.showCheckout(mapping, form, request, response); 
            return caf;
        }
    }

    private void sendMailNewRegister(Customer customer) throws Exception
    {
        MailUtils mailUtils = MailUtils.getInstance();

        // Create the email message
        HtmlEmail email = new HtmlEmail();
        email.setHostName(mailUtils.getHost());
        email.setSmtpPort(mailUtils.getSmtp_port());
        email.setAuthenticator(new DefaultAuthenticator(mailUtils.getAuthen_username(), mailUtils.getAuthen_password()));
        email.setTLS(true);
        //send email to customer
        email.setFrom(mailUtils.getFrom());
        email.addTo(customer.getBill_to_email(), customer.getBill_to_fname() + " " + customer.getBill_to_lname());

        email.setSubject("New Customer Registration");

        String htmlContent =
            "<html>" + "<div>Hello " + customer.getBill_to_fname() + " " + customer.getBill_to_lname() + ",<br>" + "<br> Welcome to our Customer Tools system.<br><br>"
                + "Use the username and password you provided to sign in at Customer Tools site.<br><br>"
                + "This e-mail message contains important information about your account. Please save or print a copy so you can refer to it later.<br>" + " Registration Date:   "
                + Constants.getCurrentDate() + "<br/>" + " Customer name:       " + customer.getBill_to_fname() + " " + customer.getBill_to_lname() + "<br/>" + " Company name:        "
                + customer.getBill_to_company() + "<br/>" + " Username:            " + customer.getLoginID() + "<br/>" + " Password:            " + customer.getPassword() + "<br/>"
                + " Hint:                " + customer.getHint() + "<br/>" + " Answer:              " + customer.getAnswer() + "<br/>"
                + "If you have questions regarding the Privacy Statement, send an e-mail message to <a href=\"mailto:supportAgentTool@magrabbit.com\">supportAgentTool@magrabbit.com</a>.<br>"
                + "<br>To request more help, contact Customer Support at <a href=\"http://www.magrabbit.com/contactUs\" target=\"_blank\">http://www.magrabbit.com/contactUs</a>.<br>"
                + "<br>Thank you for using Customer Tools !<br><br>AgentTools Team<br><br>NOTE:<br>"
                + "Please do not reply to this message, which was sent from an unmonitored e-mail address. Mail sent to this address cannot be answered.<br>" + "<br></div>" + "</html>";

        // set the html message
        email.setHtmlMsg(htmlContent);

        // send the email
        email.send();
    }
    
    public ActionForward addWarranty(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
    	HttpSession sessions = request.getSession();
        Boolean flag = true;
        ProductDAO productDAO = new ProductDAO();
        String shopperId = sessions.getAttribute(Constants.SHOPPER_ID).toString();
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
    	
    	return mapping.findForward("agenttools.checkout.show");
    }
}
