package com.dell.enterprise.agenttool.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

public class AgentDAO {
	 private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.CustomerDAO");
	    
	    private Connection conn;
	    private Statement stmt;
	    private PreparedStatement pstmt;
	    private ResultSet rs;
	    private CallableStatement cst;
	    
	    public List<Agent> searchAgent(int start, int end,int agentId, String fullname, String username, String userLevel, String email, int isReport, int isAdmin, int isActive,
		        int userType)
		    {
	    	
	    	List<Agent> result = new ArrayList();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            
	            StringBuilder sql = new StringBuilder();	           
	            StringBuilder condition = new StringBuilder();
	           	            
	            boolean hadCondition = false;
	            
	           	if (agentId != Integer.MAX_VALUE)
	            {
	           		condition.append(Constants.SQL_CONDITION_WHERE);
	           		condition.append(daoUtil.getString("search.agent.agentId").replaceAll("0", String.valueOf(agentId)));
	           		hadCondition = true;
	           	}
	           	if (!Constants.isEmpty(fullname))
	           	{
	           		if (hadCondition)
	           		{
	           			condition.append(Constants.SQL_CONDITION_AND);
	           			condition.append(daoUtil.getString("search.agent.fullname").replaceAll("0", fullname.trim().replaceAll("'", "''")));
	           		}
	           		else
	           		{
	           			condition.append(Constants.SQL_CONDITION_WHERE);
	           			condition.append(daoUtil.getString("search.agent.fullname").replaceAll("0", fullname.trim().replaceAll("'", "''")));
	           		}
	           		hadCondition = true;
	           	}
	           	
	    	    if (!Constants.isEmpty(username))
	    	    {
	    	       	if (hadCondition)
	           		{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.agent.username").replaceAll("0", username.trim().replaceAll("'", "''")));
	           		}
	           		else
	           		{
	           			condition.append(Constants.SQL_CONDITION_WHERE);
	           			condition.append(daoUtil.getString("search.agent.username").replaceAll("0", username.trim().replaceAll("'", "''")));
	           		}
	           		hadCondition = true;
	    	    }	    	    		 
	    	    if (!Constants.isEmpty(userLevel))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.agent.userLevel").replaceAll("0", userLevel.trim().replaceAll("'", "''")));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.agent.userLevel").replaceAll("0", userLevel.trim().replaceAll("'", "''")));
	            	}
	            	hadCondition = true;
	    	    }	   
	    	    if (!Constants.isEmpty(email))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.agent.email").replaceAll("0", email.trim().replaceAll("'", "''")));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.agent.email").replaceAll("0", email.trim().replaceAll("'", "''")));
	            	}
	            	hadCondition = true;
	    	    }
	    	    if (isReport != Integer.MAX_VALUE)
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.agent.isReport").replaceAll("0",String.valueOf(isReport)));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.agent.isReport").replaceAll("0", String.valueOf(isReport)));
	            	}
	            	hadCondition = true;
	    	    }
	    	    if (isAdmin != Integer.MAX_VALUE)
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.agent.isAdmin").replaceAll("0", String.valueOf(isAdmin)));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.agent.isAdmin").replaceAll("0", String.valueOf(isAdmin)));
	            	}
	            	hadCondition = true;
	    	    }	
	    	    if (isActive != Integer.MAX_VALUE)
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.agent.isActive").replaceAll("0", String.valueOf(isActive)));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.agent.isActive").replaceAll("0", String.valueOf(isActive)));
	            	}
	            	hadCondition = true;
	    	    }	
	    	    if (userType != Integer.MAX_VALUE)
	    	    {
	    	     	if (hadCondition)
	            	{
	    	     		condition.append(Constants.SQL_CONDITION_AND);
	    	     		condition.append(daoUtil.getString("search.agent.userType").replaceAll("0",  String.valueOf(userType)));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.agent.userType").replaceAll("0",  String.valueOf(userType)));
	            	}
	            	hadCondition = true;
	    	    }	
	    	    	    	   
	    	    cst = conn.prepareCall(daoUtil.getString("search.agent.all"));
	    	    
	    	    cst.setInt(1, start);
	            cst.setInt(2, end);         
	            cst.setString(3, "UserName");	          
	            cst.setString(4, condition.toString());
	           
	            rs = cst.executeQuery();
	            
	           
	            while (rs.next())
	            {  
	            	Agent tmpAgent = new Agent();
	            	tmpAgent.setAgentId(rs.getInt("agent_id"));
		            tmpAgent.setAgentName(Constants.convertValueEmpty(rs.getString("fullname")));
		            tmpAgent.setUserName(Constants.convertValueEmpty(rs.getString("username")));
		            tmpAgent.setUserLevel(Constants.convertValueEmpty(rs.getString(Constants.USER_LEVEL)));
		            tmpAgent.setAgentEmail(Constants.convertValueEmpty(rs.getString("email")));
		            tmpAgent.setAdmin(rs.getBoolean("Admin"));
	            	tmpAgent.setActive(rs.getBoolean("Active"));
	            	tmpAgent.setUserType(rs.getBoolean("UserType"));
	            	tmpAgent.setReport(rs.getBoolean("Report"));
	            	tmpAgent.setLoginCount(rs.getInt("loginCount"));
	            	tmpAgent.setTotalRow(rs.getInt("TotalRow"));
	            	
	               	result.add(tmpAgent);		       
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(cst!=null) cst.close();	            
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
	}
	    
	public boolean updateAgent(int agentId, String mngUsername,String mngPassword,String mngEmail,String mngFullname, 
				String mngUserLevel,int isReport,int isAdmin,int isActive,int userType, int loginCount)
	{
	    	boolean result = false;
	    	
	    	 try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("search.agent.update");	         
		         pstmt = conn.prepareStatement(sql);
	             //pstmt.setString(1,mngUsername);
	             pstmt.setString(1,mngPassword.trim().replaceAll("'", "''"));	
	             pstmt.setInt(2,isAdmin);	
	             pstmt.setInt(3,userType);	
	             pstmt.setString(4,mngUserLevel);	
	             pstmt.setInt(5,isActive);	
	             pstmt.setString(6,mngEmail.trim().replaceAll("'", "''"));	
	             pstmt.setString(7,mngFullname.trim().replaceAll("'", "''"));	
	             pstmt.setInt(8,isReport);
	             pstmt.setInt(9,loginCount);
	             pstmt.setInt(10,agentId);
	             
		         result = pstmt.execute();     	    		
		     }
		     catch (Exception e)
		     {
		            LOGGER.warning("ERROR Execute DAO");
		            e.printStackTrace();
		            result = false;
		     }
		     finally
		     {
		            try{	            
		            if(rs!=null) rs.close();
		            if(pstmt!=null) pstmt.close();
		            if(stmt!=null) stmt.close();		           
		            if(conn!=null) conn.close();
		            }catch(SQLException sqlE)
		            {
		            	sqlE.getStackTrace();
		            }
		     }
		  
	    	return result;
	}
	
	public boolean addAgent(String mngUsername,String mngPassword,String mngEmail,String mngFullname, 
			String mngUserLevel,int isReport,int isAdmin,int isActive,int userType)
{
    	boolean result = false;
    	
    	 try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("search.agent.add");	         
	         pstmt = conn.prepareStatement(sql);
             pstmt.setString(1,mngUsername.trim().replaceAll("'", "''"));
             pstmt.setString(2,mngPassword.trim().replaceAll("'", "''"));	
             pstmt.setInt(3,isAdmin);	
             pstmt.setInt(4,userType);	
             pstmt.setString(5,mngUserLevel);	
             pstmt.setInt(6,isActive);	
             pstmt.setString(7,mngEmail.trim().replaceAll("'", "''"));	
             pstmt.setString(8,mngFullname.trim().replaceAll("'", "''"));	
             pstmt.setInt(9,isReport);	
             
	         result = pstmt.execute();     	    		
	     }
	     catch (Exception e)
	     {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	            result = false;
	     }
	     finally
	     {
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(pstmt!=null) pstmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	     }
	  
    	return result;
}
	
	public boolean deleteAgent(int agentId)
	{
		boolean result = false;
    	
		try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	        String sql = daoUtil.getString("search.agent.delete");	         
	        pstmt = conn.prepareStatement(sql);            
            pstmt.setInt(1,agentId);
                       
	        result = pstmt.execute();     	    		
	     }
	     catch (Exception e)
	     {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	            result = false;
	     }
	     finally
	     {
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(pstmt!=null) pstmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	     }
	  
	     return result;
	}
	
	public boolean isExistUser(String username)
	{
		boolean result = false;
    	
		try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	        String sql = daoUtil.getString("search.agent.check.exist");	         
	        pstmt = conn.prepareStatement(sql);            
            pstmt.setString(1,username.trim());
                       
	        rs = pstmt.executeQuery();    
	        if (rs.next())
	        	result = true;
	     }
	     catch (Exception e)
	     {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	            result = false;
	     }
	     finally
	     {
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(pstmt!=null) pstmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	     }
	  
	     return result;
	}
	
	public boolean isInUseAgent(int agentId)
	{
		boolean result = false;
    	
		try
	    {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         cst = conn.prepareCall(daoUtil.getString("search.agent.check.in.use"));	    	    
	    	 cst.setInt(1, agentId);
	        
	         rs = cst.executeQuery();
	       
	        if (rs.next() && (rs.getInt("result") != 0))
	        	result = true;
	     }
	     catch (Exception e)
	     {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	            result = false;
	     }
	     finally
	     {
	            try{	            
	            if(rs!=null) rs.close();
	            if(cst!=null) cst.close();	            
	            if(stmt!=null) stmt.close();
	            if(pstmt!=null) pstmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	     }
	  
	     return result;
	}
	
	 public Agent getAgent(int agentId)
	 {
	        Agent agent = null;

	        try
	        {
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();

	            String sql = daoUtil.getString("search.agent.get.byId");

	            pstmt = conn.prepareStatement(sql);
	            pstmt.setInt(1, agentId);	            
	            rs = pstmt.executeQuery();

	            if (rs.next())
	            {
	                agent = new Agent();
	                agent.setAgentId(rs.getInt("agent_id"));
	                agent.setUserName(rs.getString("Username").trim());
	                agent.setPassword(rs.getString("Password"));
	                agent.setAdmin(rs.getBoolean("Admin"));
	                agent.setUserType(rs.getBoolean("UserType"));
	                agent.setUserLevel(rs.getString("userLevel"));
	                agent.setActive(rs.getBoolean("active"));	           
	                agent.setAgentName(Constants.convertValueEmpty(rs.getString("fullname")));
	                agent.setAgentEmail(Constants.convertValueEmpty(rs.getString("email")));
	                agent.setReport(rs.getBoolean("report"));
	                agent.setLoginCount(rs.getInt("loginCount"));
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	        finally
	        {
	            try
	            {
	                if (rs != null)
	                    rs.close();
	                if (stmt != null)
	                    stmt.close();
	                if (pstmt != null)
	                    pstmt.close();
	                if (conn != null)
	                    conn.close();
	            }
	            catch (SQLException e)
	            {
	                e.printStackTrace();
	            }
	        }

	        return agent;
	    }

}
