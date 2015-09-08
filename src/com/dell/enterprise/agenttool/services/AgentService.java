package com.dell.enterprise.agenttool.services;

import java.util.List;
import com.dell.enterprise.agenttool.DAO.AgentDAO;
import com.dell.enterprise.agenttool.model.Agent;

public class AgentService {

	public List<Agent> searchAgent(int start, int end,int agentId, String fullname, String username, String userLevel, String email, int isReport, int isAdmin, int isActive,
	        int userType)
	    {
	        AgentDAO dao = new AgentDAO();
	        List<Agent> agents = dao.searchAgent(start, end, agentId, fullname, username, userLevel, email, isReport, isAdmin, isActive, userType);
	        return agents;
	    }
	
	public boolean addAgent(String mngUsername,String mngPassword,String mngEmail,String mngFullname, 
			String mngUserLevel,int isReport,int isAdmin,int isActive,int userType)
	{
		 AgentDAO dao = new AgentDAO();
		 return dao.addAgent(mngUsername, mngPassword, mngEmail, mngFullname, 
         		mngUserLevel, isReport, isAdmin, isActive, userType);
	}
	
	public boolean updateAgent(int agentId, String mngUsername,String mngPassword,String mngEmail,String mngFullname, 
			String mngUserLevel,int isReport,int isAdmin,int isActive,int userType, int loginCount)
	{
		 AgentDAO dao = new AgentDAO();
		 return dao.updateAgent(agentId, mngUsername, mngPassword, mngEmail, mngFullname, 
         		mngUserLevel, isReport, isAdmin, isActive, userType, loginCount);
	}
	
	public boolean deleteAgent(int agentId)
	{
		 AgentDAO dao = new AgentDAO();
		 return dao.deleteAgent(agentId);
	}
	
	public boolean isExistUser(String username)
	{
		 AgentDAO dao = new AgentDAO();
		 return dao.isExistUser(username);
	}
	
	public boolean isInUseAgent(int agentId)
	{
		 AgentDAO dao = new AgentDAO();
		 return dao.isInUseAgent(agentId);
	}	
	
	public Agent getAgent(int agentId)
	{
		 AgentDAO dao = new AgentDAO();
		 return dao.getAgent(agentId);
	}
	
	
	
}
