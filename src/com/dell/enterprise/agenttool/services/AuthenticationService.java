package com.dell.enterprise.agenttool.services;

import com.dell.enterprise.agenttool.DAO.AgentDAO;
import com.dell.enterprise.agenttool.DAO.AuthenticationDAO;
import com.dell.enterprise.agenttool.model.Agent;

/**
 * @author hungnguyen
 * 
 * @version $Id$
 **/
public class AuthenticationService
{
    /**
     * @param userName
     *            self-explained
     * @param password
     *            self-explained
     * @return Agent
     **/
    public Agent authenticate(final String userName, final String password)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        Agent agent = dao.authenticate(userName, password);

        return agent;
    }
    
    /**
     * @param userName
     *            self-explained
     * @param password
     *            self-explained
     * @return Agent
     **/
    public Agent login(final String userName, final String password)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        Agent agent = dao.login(userName, password);

        return agent;
    }
    
    public Agent getAgentByAgentId(int agent_id)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        Agent agent = dao.getAgentByAgentId(agent_id);
        return agent;
    }
    public Agent getAgentByName(String userName)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        Agent agent = dao.getAgentByName(userName);
        return agent;
    }
    public boolean updateLoginCount(String userName, int loginCount)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        return dao.updateLoginCount(userName, loginCount);
    }
    public boolean updateLoginTime(String userName, String loginTime)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        return dao.updateLoginTime(userName, loginTime);
    }
    public boolean updatePasswordDate(String userName, String passwordDate)
    {
        AuthenticationDAO dao = new AuthenticationDAO();
        return dao.updatePasswordDate(userName, passwordDate);
    }
    public boolean updatePasswordAgent(String mngUsername,String mngPassword )
	{
    	 AuthenticationDAO dao = new AuthenticationDAO();
		 return dao.updatePasswordAgent(mngUsername,mngPassword);
	}
    
}
