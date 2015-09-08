package com.dell.enterprise.agenttool.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.util.DAOUtils;

/**
 * @author hungnguyen
 * 
 * @version $Id$
 **/
public class AuthenticationDAO
{   private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.AuthenticationDAO");

    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     * @param userName
     *            self-explained
     * @param password
     *            self-explained
     * @return an Agent object containing information about the user
     **/
    public Agent authenticate(final String userName, final String password)
    {
        Agent agent = null;

        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("authenticate.agent");
            LOGGER.info("thiendk"+sql);
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                agent = new Agent();
                agent.setAgentId(rs.getInt("agent_id"));
                agent.setUserName(userName);
                agent.setPassword(password);
                agent.setAdmin(rs.getBoolean("Admin"));
                agent.setUserType(rs.getBoolean("UserType"));
                agent.setUserLevel(rs.getString("userLevel"));
                agent.setActive(rs.getBoolean("active"));
                //vinhhq
                agent.setAgentName(rs.getString("fullname"));
                agent.setAgentEmail(rs.getString("email"));
                agent.setLoginCount(rs.getInt("loginCount"));
                agent.setLoginTime(rs.getTimestamp("loginTime"));
                agent.setPasswordDate(rs.getTimestamp("passwordDate"));
                
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
                if (pstmt != null)
                    pstmt.close();                
                if (stmt != null)
                    stmt.close();
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
    
    /**
     * @param userName
     *            self-explained
     * @param password
     *            self-explained
     * @return an Agent object containing information about the user
     **/
    public Agent login(final String userName, final String password)
    {
        Agent agent = null;

        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("authenticate.customer");
           
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();

            if (rs.next())
            {
                agent = new Agent();
                agent.setAgentId(0);
                agent.setShopperId(rs.getString("shopper_id"));
                agent.setShopperNumber(rs.getInt("shopper_number"));
                agent.setUserName(userName);
                agent.setPassword(password);
                agent.setAdmin(false);
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
                if (pstmt != null)
                    pstmt.close();
                if (stmt != null)
                    stmt.close();
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
    
    
    public Agent getAgentByAgentId(int agent_id)
    {
        Agent agent = null;

        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("agent.get");

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, agent_id);

            rs = pstmt.executeQuery();


            if (rs.next())
            {
                agent = new Agent();
                agent.setAgentId(rs.getInt("agent_id"));
                agent.setUserName(rs.getString("UserName"));
                agent.setAdmin(rs.getBoolean("Admin"));
                agent.setUserType(rs.getBoolean("UserType"));
                agent.setUserLevel(rs.getString("userLevel"));
                agent.setActive(rs.getBoolean("active"));
                //vinhhq
                agent.setAgentName(rs.getString("fullname"));
                agent.setAgentEmail(rs.getString("email"));
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
                if (rs != null)
                    rs.close();
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
    public Agent getAgentByName(String userName)
    {
        Agent agent = null;

        try
        {
            DAOUtils daoUtil = DAOUtils.getInstance();
            conn = daoUtil.getConnection();

            String sql = daoUtil.getString("agent.getname");

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, userName);

            rs = pstmt.executeQuery();


            if (rs.next())
            {
                agent = new Agent();
                agent.setAgentId(rs.getInt("agent_id"));
                agent.setUserName(rs.getString("UserName"));
                agent.setAdmin(rs.getBoolean("Admin"));
                agent.setUserType(rs.getBoolean("UserType"));
                agent.setUserLevel(rs.getString("userLevel"));
                agent.setActive(rs.getBoolean("active"));
                //vinhhq
                agent.setAgentName(rs.getString("fullname"));
                agent.setAgentEmail(rs.getString("email"));
                agent.setLoginCount(rs.getInt("loginCount"));
                agent.setLoginTime(rs.getTimestamp("loginTime"));
                agent.setPasswordDate(rs.getTimestamp("passwordDate"));
                
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
                if (rs != null)
                    rs.close();
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
    
    public boolean updateLoginCount(String userName, int loginCount){
	    	boolean result = false;
	    	
	    	 try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("agent.update.logincount");	         
		         pstmt = conn.prepareStatement(sql);
		        	
	             pstmt.setInt(1, loginCount);	
	             pstmt.setString(2, userName);

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
    
    public boolean updateLoginTime(String userName, String loginTime){
    	boolean result = false;
    	
    	 try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("agent.update.logintime");	         
	         pstmt = conn.prepareStatement(sql);
	        	
             pstmt.setString(1, loginTime);	
             pstmt.setString(2, userName);

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
    public boolean updatePasswordDate(String userName, String passwordDate){
    	boolean result = false;
    	
    	 try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("agent.update.passworddate");	         
	         pstmt = conn.prepareStatement(sql);
	        	
             pstmt.setString(1, passwordDate);	
             pstmt.setString(2, userName);

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
    public boolean updatePasswordAgent(String userName, String password){
    	boolean result = false;
    	
    	 try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("agent.update.passwordagent");	         
	         pstmt = conn.prepareStatement(sql);
	        	
             pstmt.setString(1, password);	
             pstmt.setString(2, userName);

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
}
