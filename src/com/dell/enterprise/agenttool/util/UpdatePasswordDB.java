package com.dell.enterprise.agenttool.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.omg.CORBA.PUBLIC_MEMBER;

import sun.print.resources.serviceui;

import com.dell.enterprise.agenttool.model.Agent;
import com.dell.enterprise.agenttool.services.SecurityService;


public class UpdatePasswordDB {

	/**
	 * @param args
	 */
	private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private CallableStatement cst;
    public ArrayList<Agent> getAllAgent()
	 {      
    		ArrayList<Agent> agents = new ArrayList<Agent>();

	        try
	        {
	        	conn = connectDB();
		        String sql = "SELECT * FROM adminUsers where username = 'bstrawn'";	  
	            pstmt = conn.prepareStatement(sql);	            
	            rs = pstmt.executeQuery();
	            
	            while (rs.next())
	            {  
	                Agent agent = new Agent();
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
	                agents.add(agent);
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

	        return agents;
	    }
    
    public boolean updatePasswordAgent(String userName, String password){
    	boolean result = false;
    	
    	 try
	     {
	        
	         conn = connectDB();
	         
	         String sql = "UPDATE adminUsers SET password = ? WHERE userName = ?";	         
	         pstmt = conn.prepareStatement(sql);
	         
             pstmt.setString(1, password);	
             pstmt.setString(2, userName);

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
    
    
	 public void updatePassword()
	    {
	    	try{
	    		
	    		ArrayList<Agent> agents = new ArrayList<Agent>();
	    		agents = getAllAgent();
	    		SecurityService securityservice = new SecurityService();
	    		
	    		for (Agent agent : agents) {
	    			updatePasswordAgent(agent.getUserName(), securityservice.encryptPassword(agent.getPassword()));
	    			
	    		}
		         
	 		}catch (Exception e) {
				// TODO: handle exception
	 			 
			}
	    }
	 
	public Connection connectDB() throws ClassNotFoundException {
		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
		Connection conn1 = null;
		try {
			conn1 = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;user=sa;password=mr1server45;database=Fulfill_DFS");		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return conn1;
		
	}
	public static void main(String[] args) {
		UpdatePasswordDB updatePassword = new UpdatePasswordDB();
        try {
			updatePassword.updatePassword();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("connect fail");
		}
	}

}
