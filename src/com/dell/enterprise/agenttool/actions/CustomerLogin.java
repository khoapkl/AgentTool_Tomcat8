package com.dell.enterprise.agenttool.actions;

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
import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.services.ProductServices;
import com.dell.enterprise.agenttool.util.Constants;

/**
 * @author hungnguyen
 * 
 * @version $Id$
 **/
public class CustomerLogin extends DispatchAction
{

    private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.actions.CustomerLogin");

    /**
     * {@inheritDoc}
     */
    public ActionForward execute(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response)
    {  LOGGER.info("thiendk");
        ActionForward forward;
        HttpSession session = request.getSession();

        try
        {
            String method = this.getMethodName(mapping, form, request, response, Constants.PARAMETER);

            if (method == null || "".equals(method))
            {
                if (session.getAttribute(Constants.AGENT_INFO) != null)
                {
                    forward = mapping.findForward(Constants.LOGIN_SUCCESS);
                }
                else
                {
                    forward = mapping.findForward(Constants.LOGIN_VIEW);
                }
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
     * @throws Exception
     *             when an exception occurs
     **/
    public final ActionForward login(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward;
        LOGGER.info("thiendk");

        try
        {
            HttpSession session = request.getSession();

            String userName = request.getParameter("userName");
            LOGGER.info("thiendk"+userName);
            String password = request.getParameter("password");
            if (!userName.equals("") && !password.equals(""))
            {
                AuthenticationService service = new AuthenticationService();
                Agent agent = service.login(userName, password);

                if (agent != null)
                {   
                    forward = Constants.LOGIN_SUCCESS;
                    session.setAttribute(Constants.AGENT_INFO, agent);
                    session.setAttribute(Constants.SHOPPER_ID, agent.getShopperId());
                    session.setAttribute(Constants.SHOPPER_NUMBER, agent.getShopperNumber());
                    session.setAttribute(Constants.IS_ADMIN, false);
                    session.setAttribute(Constants.IS_CUSTOMER, true);
                    session.removeAttribute(Constants.CUSTOMER_MANAGE);
                    session.setAttribute(Constants.USER_LEVEL, "A");

                    Cookie[] cookies = request.getCookies();
                    if(cookies!=null){
	                    for (int i = 0; i < cookies.length; i++)
	                    {
	                        if (cookies[i].getValue().equals(session.getId()))
	                        {
	                            Cookie myCookie = new Cookie("userLevel", "login.do");
	                            response.addCookie(myCookie);
	
	                        }
	                    }
                    }
                }
                else
                {
                    LOGGER.info("Invalid");
                    request.setAttribute("invalid", "");
                    forward = Constants.LOGIN_FAILURE;
                }
            }
            else
            {
                LOGGER.info("Invalid");
                request.setAttribute("invalid", "");
                forward = Constants.LOGIN_FAILURE;
            }
        }
        catch (NullPointerException e)
        {
            forward = Constants.LOGIN_VIEW;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
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
     * @throws Exception
     *             when an exception occurs
     **/
    public final ActionForward logout(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward;

        try
        {

            HttpSession session = request.getSession();

            /*
             * Vinhhq
             */
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
            if (session.getAttribute(Constants.SHOPPER_ID) != null)
            {
                String shopper_id = session.getAttribute(Constants.SHOPPER_ID).toString();
                ProductServices productServices = new ProductServices();
                productServices.clearOrder(shopper_id, byAgent);
            }

            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equals("userLevel"))
                {
                    cookies[i].setValue("");
                    response.addCookie(cookies[i]);
                }
            }

            session.removeAttribute(Constants.AGENT_INFO);
            session.removeAttribute(Constants.SHOPPER_ID);
            session.removeAttribute(Constants.SHOPPER_NUMBER);
            session.removeAttribute(Constants.IS_ADMIN);
            session.removeAttribute(Constants.IS_CUSTOMER);
            session.removeAttribute(Constants.USER_LEVEL);

            forward = Constants.LOGIN_VIEW;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
    }

}
