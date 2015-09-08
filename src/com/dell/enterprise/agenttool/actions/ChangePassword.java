package com.dell.enterprise.agenttool.actions;

import java.util.Date;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.dell.enterprise.agenttool.services.AuthenticationService;
import com.dell.enterprise.agenttool.services.HistoryPasswordService;
import com.dell.enterprise.agenttool.services.PwdValidator;
import com.dell.enterprise.agenttool.services.SecurityService;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.model.*;

public class ChangePassword extends DispatchAction {

	private static final Logger LOGGER = Logger
			.getLogger("com.dell.enterprise.agenttool.actions.SecuritySetup");

	public ActionForward editPassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String foward = Constants.CHANGE_PASSWORD_VIEW;
		String expire = request.getParameter("expire");
		if (expire != null && expire.trim() != "") {
			SecurityService securityservice = new SecurityService();
			Security security = securityservice.getSecurity(1);
			request.setAttribute("security", security);
			request.setAttribute("expire", expire);
		}

		return mapping.findForward(foward);

	}

	public ActionForward updatePassword(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AuthenticationService service = new AuthenticationService();
		HistoryPasswordService historyService = new HistoryPasswordService();
		AuthenticationService serviceAgent = new AuthenticationService();
		SecurityService securityservice = new SecurityService();
		String forward = Constants.UPDATE_PASSWORD_VIEW;
		
		String expire = request.getParameter("expire");
		if (expire != null && expire.trim() != "") {
			request.setAttribute("expire", expire);
		}
		
		try {
			Security security = securityservice.getSecurity(1);
			if (security == null) {
				security = new Security();
				security.setExpiryDays(30);
				security.setCharNumber(8);
				security.setUppercase(false);
				security.setSymbol(false);
				security.setNumber(false);
				security.setLockoutCount(5);
				security.setLockoutTime(15);
				security.setResetHistory(12);
			}
			request.setAttribute("security", security);
			String userName = (String) request.getParameter("userName");
			String oldPassword = (String) request.getParameter("oldPassword");
			String password = (String) request.getParameter("password");
			String rePassword = (String) request.getParameter("rePassword");
			String tmppassword = securityservice.encryptPassword(oldPassword);
			Agent agent = service.authenticate(userName, tmppassword);
			String PASSWORD_PATTERN = "(";
			PASSWORD_PATTERN += security.isNumber() ? "(?=.*\\d)" : "";
			PASSWORD_PATTERN += security.isSymbol() ? "(?=.*[@`/#-$;:?%~!^&*|<>().,{}\\[\\]\"+ '_])" : "";
			PASSWORD_PATTERN += security.isUppercase() ? "(?=.*[A-Z])" : "";
			PASSWORD_PATTERN += ".{" + security.getCharNumber() + ",})";
			PwdValidator pwdValidator = new PwdValidator(PASSWORD_PATTERN);
			request.setAttribute(Constants.AGENT_NAME, userName);
			if (!userName.equals("") && !oldPassword.equals("")
					&& !password.equals("") && !rePassword.equals("")) {
				if (agent == null) {;
					request.setAttribute("message", "incorrectpassword");
					return mapping.findForward(forward);
				} else {
					request.setAttribute(Constants.AGENT_PASSWORD, oldPassword);
					if (!password.equals(rePassword)) {
						request.setAttribute("message", "confirmpassword");
						return mapping.findForward(forward);
					} else {
						if (!pwdValidator.validate(password)) {
							request.setAttribute("message", "sercuritypassword");
							return mapping
									.findForward(forward);
						} else if (historyService.getHistoryPassword(userName,
								securityservice.encryptPassword(password))) {
							request.setAttribute("message", "reusepassword");
							return mapping
									.findForward(forward);
						}
					}

				}

				String mngPassword = securityservice.encryptPassword(password);
				historyService.addHistoryPassword(userName, mngPassword);
				serviceAgent.updatePasswordAgent(userName, mngPassword);
				Date currentTime = new Date();
				serviceAgent.updateLoginCount(userName, 0);
				serviceAgent.updateLoginTime(userName,
						securityservice.formatDate(currentTime));
				serviceAgent.updatePasswordDate(userName,
						securityservice.formatDate(currentTime));
				forward = Constants.LOGIN;
				return mapping.findForward(forward);
			} else {
				request.setAttribute("message", "emptypassword");
				return mapping.findForward(forward);

			}

		} catch (Exception e) {
			// TODO: handle exception
			return mapping.findForward(forward);
		}

	}

}
