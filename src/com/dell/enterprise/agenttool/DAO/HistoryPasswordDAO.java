package com.dell.enterprise.agenttool.DAO;

import java.sql.SQLException;

import com.dell.enterprise.agenttool.model.Security;
import com.dell.enterprise.agenttool.util.DAOUtils;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class HistoryPasswordDAO {
	    private Connection conn;
	    private Statement stmt;
	    private PreparedStatement pstmt;
	    private ResultSet rs;
	    private CallableStatement cst;
	
	public boolean addHistoryPassword(String userName, String password)
	{
		boolean result = false;
		    	
		    	 try
			     {
			       
			         DAOUtils daoUtil = DAOUtils.getInstance();
			         conn = daoUtil.getConnection();
			            
			         String sql = daoUtil.getString("history.password.add");	         
			         pstmt = conn.prepareStatement(sql);
			         pstmt.setString(1, userName);
		             pstmt.setString(2, password);		
			         result = pstmt.execute();
			     }
			     catch (Exception e)
			     {
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
		 
	public boolean getHistoryPassword(String userName, String password)
	{
		 try
	     {
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("history.password.get");	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1,userName);
	         pstmt.setString(2,password); 
	         rs = pstmt.executeQuery();

	            if (rs.next())
	            {
	            	return true;
     
	            }
	     }
	     catch (Exception e)
	     { 
	            e.printStackTrace();
	            return false;
	          
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
	     return false;
		
	}
	public boolean deleteHistoryPassword()
	{
		 try
	     {
	         DAOUtils daoUtil = DAOUtils.getInstance();
	         conn = daoUtil.getConnection();
	            
	         String sql = daoUtil.getString("history.password.delete");	         
	         pstmt = conn.prepareStatement(sql);
	         pstmt.executeUpdate();
	     }
	     catch (Exception e)
	     { 
	            e.printStackTrace();
	            return false;
	          
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
	     return false;
		
	}
}
