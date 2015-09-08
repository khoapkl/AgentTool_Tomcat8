package com.dell.enterprise.agenttool.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.util.Constants;
public class LinkFooter extends DispatchAction
{

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
    public final ActionForward linkfooter1(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward;

        try
        {            
            forward = Constants.LINK_FOOTER_DEFAULT;
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
    public final ActionForward linkfooter2(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward;

        try
        {            
            forward = Constants.LINK_FOOTER_DEFAULT1;
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
    public final ActionForward linkfooter3(final ActionMapping mapping, final ActionForm form, final HttpServletRequest request, final HttpServletResponse response) throws Exception
    {
        String forward;

        try
        {                  
            forward = Constants.LINK_FOOTER_DEFAULT2;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            forward = Constants.ERROR_PAGE_VIEW;
        }

        return mapping.findForward(forward);
    }
}
    