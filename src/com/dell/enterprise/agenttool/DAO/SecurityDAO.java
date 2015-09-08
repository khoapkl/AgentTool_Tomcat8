package com.dell.enterprise.agenttool.DAO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;


import com.dell.enterprise.agenttool.model.*;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.DAOUtils;

public class SecurityDAO {
	 private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.CustomerDAO");
	    
	    private Connection conn;
	    private Statement stmt;
	    private PreparedStatement pstmt;
	    private ResultSet rs;
	    private CallableStatement cst;
	    
	public boolean updateSecurity(int securityId, int lockoutTime, int lockoutCount, int expiryDays, int charNumber, int resetHistory , boolean isUppercase,boolean isNumber, boolean isSymbol )
	{
	    	boolean result = false;
	    	
	    	 try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("security.update");	         
		         pstmt = conn.prepareStatement(sql);
		        
	             pstmt.setLong(1,lockoutTime);	
	             pstmt.setInt(2,lockoutCount);	
	             pstmt.setInt(3, expiryDays);
	             pstmt.setInt(4, charNumber);
	             pstmt.setInt(5, resetHistory);
	             pstmt.setBoolean(6, isUppercase);
	             pstmt.setBoolean(7, isNumber);
	             pstmt.setBoolean(8, isSymbol);
	             pstmt.setInt(9, securityId);

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
	public boolean addSecurity(int securityId, int lockoutTime, int lockoutCount, int expiryDays, int charNumber, int resetHistory , boolean isUppercase,boolean isNumber, boolean isSymbol, String resetHistoryDate)
	{
	    	boolean result = false;
	    	
	    	 try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("security.add");	         
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setInt(1,securityId);
	             pstmt.setLong(2,lockoutTime);	
	             pstmt.setInt(3,lockoutCount);	
	             pstmt.setInt(4, expiryDays);
	             pstmt.setInt(5, charNumber);
	             pstmt.setInt(6, resetHistory);
	             pstmt.setBoolean(7, isUppercase);
	             pstmt.setBoolean(8, isNumber);
	             pstmt.setBoolean(9, isSymbol);
	             pstmt.setString(10, resetHistoryDate);
	             
	            	
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
	public Security getSecurity(int securityId)
	{		
			Security security = null;
	    	 try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("security.get");	         
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setInt(1,securityId);  	
		         rs = pstmt.executeQuery();

		            if (rs.next())
		            {
		            	security = new Security();
		            	security.setSecurityId(rs.getInt("securityId"));
		            	security.setLockoutTime(rs.getInt("lockoutTime"));
		            	security.setLockoutCount(rs.getInt("lockoutCount"));
		            	security.setExpiryDays(rs.getInt("expiryDays"));
		            	security.setCharNumber(rs.getInt("charNumber"));
		            	security.setResetHistory(rs.getInt("resetHistory"));
		            	security.setUppercase(rs.getBoolean("isUppercase"));
		            	security.setNumber(rs.getBoolean("isNumber"));
		            	security.setSymbol(rs.getBoolean("isSymbol"));
		            	security.setResetHistoryDate(rs.getTimestamp("resetHistoryDate"));
		            }
		     }
		     catch (Exception e)
		     {
		            LOGGER.warning("ERROR Execute DAO");
		            e.printStackTrace();
		          
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
		  
	    	return security;
	}


    public boolean updateResetHistoryDate(int securityId, String resetHistoryDate){
    	boolean result = false;
    	
    	 try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("security.update.updatehistorydate");	         
	         pstmt = conn.prepareStatement(sql);
	        	
             pstmt.setString(1, resetHistoryDate);
             pstmt.setInt(2, securityId);

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

    public boolean deleteSecurity(){
    	boolean result = false;
    	
    	 try
	     {
	         LOGGER.info("Execute DAO");
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("security.update.deletesecurity");	         
	         pstmt = conn.prepareStatement(sql);

	         pstmt.executeUpdate();
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
