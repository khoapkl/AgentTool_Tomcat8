package com.dell.enterprise.agenttool.DAO;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.SiteReferral;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;
import com.dell.enterprise.agenttool.util.DAOUtils;


public class CustomerDAO {
	
	 private static final Logger LOGGER = Logger.getLogger("com.dell.enterprise.agenttool.DAO.CustomerDAO");
	    
	    private Connection conn;
	    private Statement stmt;
	    private PreparedStatement pstmt;
	    private ResultSet rs;
	    private CallableStatement cst;
	    
	    public Customer getCustomerByShopperID(String newShopperID)
	    {
	    	Customer result = null;
	    	try
		    {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("get.shopper.byID");		         
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, newShopperID);	             
		         rs = pstmt.executeQuery();	  	            
		           
		         if (rs.next())
		         {	 
		        	 result = new Customer();
		        	 result.setShopper_id(rs.getString(Constants.DB_FIELD_SHOPPER_ID));
		        	 result.setShip_to_fname(rs.getString(Constants.DB_FIELD_SHIP_FNAME));
		        	 result.setShip_to_lname(rs.getString(Constants.DB_FIELD_SHIP_LNAME));	
		        	 result.setShip_to_country(rs.getString(Constants.DB_FIELD_SHIP_COUNTRY));
		        	 result.setShip_to_address1(rs.getString(Constants.DB_FIELD_SHIP_ADDRESS_1));
		        	 result.setShip_to_title(rs.getString(Constants.DB_FIELD_SHIP_TITLE));
		        	 result.setShip_to_address2(rs.getString(Constants.DB_FIELD_SHIP_ADDRESS_2));
		        	 result.setShip_to_phoneext(rs.getString(Constants.DB_FIELD_SHIP_PHONEEXT));
		        	 result.setShip_to_city(rs.getString(Constants.DB_FIELD_SHIP_CITY));
		        	 result.setShip_to_postal(rs.getString(Constants.DB_FIELD_SHIP_POSTAL));
		        	 result.setShip_to_phone(rs.getString("ship_to_phone"));
		        	 result.setShip_to_state(rs.getString("ship_to_state"));
		        	 result.setShip_to_company(rs.getString("ship_to_company"));
		        	 result.setShip_to_email(rs.getString("ship_to_email"));
		        	 
		        	 result.setAccount_type(rs.getString("account_type"));
		        			        	 
		        	 result.setBill_to_fname(rs.getString("bill_to_fname"));
		        	 result.setBill_to_lname(rs.getString("bill_to_lname"));
		        	 result.setBill_to_country(rs.getString(Constants.DB_FIELD_BILL_COUNTRY));
		        	 result.setBill_to_title(rs.getString(Constants.DB_FIELD_BILL_TITLE));
		        	 result.setBill_to_address1(rs.getString(Constants.DB_FIELD_BILL_ADDRESS_1));
		        	 result.setBill_to_address2(rs.getString(Constants.DB_FIELD_BILL_ADDRESS_2));
		        	 result.setBill_to_city(rs.getString(Constants.DB_FIELD_BILL_CITY));
		        	 result.setBill_to_state(rs.getString(Constants.DB_FIELD_BILL_STATE));
		        	 result.setBill_to_country(rs.getString(Constants.DB_FIELD_BILL_COUNTRY));
		        	 result.setBill_to_postal(rs.getString(Constants.DB_FIELD_BILL_POSTAL));
		        	 result.setBill_to_fax(rs.getString(Constants.DB_FIELD_BILL_FAX));
		        	 result.setBill_to_phoneext(rs.getString(Constants.DB_FIELD_BILL_PHONEEXT));
		        	 result.setBill_to_email(rs.getString(Constants.DB_FIELD_BILL_EMAIL));
		        	 result.setShip_to_email(rs.getString("ship_to_email"));
		        	 result.setBill_to_phone(rs.getString("bill_to_phone"));
		        	 result.setBill_to_company(rs.getString("bill_to_company"));
		        	 
		        	 
		        	 result.setTax_exempt_expire(rs.getDate(Constants.DB_FIELD_TAX_EXEMPT_EXPIRE));
		        	 result.setTax_exempt_number(rs.getString(Constants.DB_FIELD_TAX_EXEMPT_NUMBER));
		        	 result.setTax_exempt(rs.getInt(Constants.DB_FIELD_TAX_EXEMPT));
		        	 
		        	 result.setCreditexp((rs.getString(Constants.DB_FIELD_CREDIT_EXP)));
		        	 result.setCreditcomment(rs.getString(Constants.DB_FIELD_CREDIT_COMMENT)); 
		        	 result.setCreditavail(rs.getBigDecimal(Constants.DB_FIELD_CREDIT_AVAIL));  
		        	 result.setCreditline(rs.getBigDecimal(Constants.DB_FIELD_CREDIT_LINE)); 
		        	 
		        	 result.setSpecialty_discount(rs.getFloat(Constants.DB_FIELD_SPEC_DISCOUNT));		        	 
		        	 result.setLoginID(rs.getString(Constants.DB_FIELD_LOGIN_ID));
		        	 result.setPassword(rs.getString(Constants.DB_FIELD_PASSWORD));	     		
		        	 result.setHint(rs.getString(Constants.DB_FIELD_HINT));      
		        	 result.setAnswer(rs.getString(Constants.DB_FIELD_ANSWER));
		        	 result.setPromo_email(rs.getInt(Constants.DB_FIELD_PROMO_EMAIL));
		        	 result.setLoa(rs.getString(Constants.DB_FIELD_LOA));
		        	 
		        	 result.setLatperdiscount(rs.getInt(Constants.DB_FIELD_LAT_PER_DISCOUNT));
		        	 result.setLatamtdiscount(rs.getInt(Constants.DB_FIELD_LAT_AMT_DISCOUNT));
		        	 result.setLatexpdate(rs.getDate(Constants.DB_FIELD_LAT_EXP_DATE));
		        	 
		        	 result.setInsperdiscount(rs.getInt(Constants.DB_FIELD_INS_PER_DISCOUNT));
		        	 result.setInsamtdiscount(rs.getInt(Constants.DB_FIELD_INS_AMT_DISCOUNT));
		        	 result.setInsexpdate(rs.getDate(Constants.DB_FIELD_INS_EXP_DATE));
		        	 
		        	 result.setOptperdiscount(rs.getInt(Constants.DB_FIELD_OPT_PER_DISCOUNT));
		        	 result.setOptamtdiscount(rs.getInt(Constants.DB_FIELD_OPT_AMT_DISCOUNT));
		        	 result.setOptexpdate(rs.getDate(Constants.DB_FIELD_OPT_EXP_DATE)); 
		     		
		        	 result.setDimperdiscount(rs.getInt(Constants.DB_FIELD_DIM_PER_DISCOUNT));
		        	 result.setDimamtdiscount(rs.getInt(Constants.DB_FIELD_DIM_AMT_DISCOUNT));
		        	 result.setDimexpdate(rs.getDate(Constants.DB_FIELD_DIM_EXP_DATE));
		     		
		        	 result.setMonperdiscount(rs.getInt(Constants.DB_FIELD_MON_PER_DISCOUNT));
		        	 result.setMonamtdiscount(rs.getInt(Constants.DB_FIELD_MON_AMT_DISCOUNT));
		        	 result.setMonexpdate(rs.getDate(Constants.DB_FIELD_MON_EXP_DATE));
		        	 
		        	 result.setSerperdiscount(rs.getInt(Constants.DB_FIELD_SER_PER_DISCOUNT));
		        	 result.setSeramtdiscount(rs.getInt(Constants.DB_FIELD_SER_AMT_DISCOUNT));
		        	 result.setSerexpdate(rs.getDate(Constants.DB_FIELD_SER_EXP_DATE));
		        	 
		        	 result.setWorperdiscount(rs.getInt(Constants.DB_FIELD_WORK_PER_DISCOUNT));
		        	 result.setWoramtdiscount(rs.getInt(Constants.DB_FIELD_WORK_AMT_DISCOUNT));
		        	 result.setWorexpdate(rs.getDate(Constants.DB_FIELD_WORK_EXP_DATE));
		        	 //vinhhq get agent id of customer
		        	 result.setAgent_id(rs.getInt("agent_id"));
		         }
		     }
		     catch (Exception e)
		     {
		            LOGGER.warning("ERROR Execute DAO");
		            e.printStackTrace();
		     }finally{
		            try{	            
		            if(rs!=null) rs.close();
		            if(stmt!=null) stmt.close();
		            if(conn!=null) conn.close();
		            }catch(SQLException sqlE){}
		     }
		        
		     return result;
	    }
	    
	    public int getAgentIdByShopperID(String newShopperID)
		{
	    	int agent_id = 0;
	    	try{
	    		 LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("get.agentID.byshopperID");		         
		         pstmt = conn.prepareStatement(sql);
		         pstmt.setString(1, newShopperID);	             
		         rs = pstmt.executeQuery();	
		         if (rs.next())
		         {
		        	 agent_id = rs.getInt("agent_id");
		         }
		         
	    	}catch (Exception e) {
	    		 LOGGER.warning("ERROR Execute DAO");
		            e.printStackTrace();
			}finally{
				 try{	            
			            if(rs!=null) rs.close();
			            if(stmt!=null) stmt.close();
			            if(conn!=null) conn.close();
			            }catch(SQLException sqlE){}
			}
	    	return agent_id;
		}
	    
	    public Boolean performCheckout(String newShopperID,String oldShopperID,Boolean byAgent)
	    {
	    	 Boolean flag = false;
	         try
	         {
	             LOGGER.info("Execute CustomerDAO - Function performCheckout ");
	             DAOUtils daoUtil = DAOUtils.getInstance();
	             conn = daoUtil.getConnection();
	             conn.setAutoCommit(false);
	             
	             String sql1 = daoUtil.getString("customer.checkout.delete.basket");
	             if(byAgent)
	                 sql1 = daoUtil.getString("customer.checkout.delete.basket.agent");
	             
	             pstmt = conn.prepareStatement(sql1);
	             pstmt.setString(1,newShopperID);	             
	             pstmt.executeUpdate();
	             pstmt = null;
	             
	             String sql2 = daoUtil.getString("customer.checkout.update.inventory");
	             pstmt = conn.prepareStatement(sql2);
	             //pstmt.setString(1, Constants.STATUS_ITEM_INSTORE);
	             pstmt.setString(1, newShopperID);
	             pstmt.setBoolean(2, byAgent);
	             pstmt.executeUpdate();
	             pstmt = null;
	             
	             String sql3 = daoUtil.getString("customer.checkout.delete.basket.item");
	             pstmt = conn.prepareStatement(sql3);
	             pstmt.setString(1,newShopperID);	 
	             pstmt.setBoolean(2, byAgent);
	             pstmt.executeUpdate();
	             pstmt = null;
	             
	             String sql4 = daoUtil.getString("customer.checkout.update.basket");
	             if(byAgent)
	                 sql4 = daoUtil.getString("customer.checkout.update.basket.agent");
	             
	             pstmt = conn.prepareStatement(sql4);
	             pstmt.setString(1, newShopperID);
	             pstmt.setString(2, oldShopperID);
	             pstmt.executeUpdate();
	             pstmt = null;
	             
	             String sql5 = "";
	             if(newShopperID.equals(oldShopperID))
	             {
	            	 sql5 = daoUtil.getString("customer.checkout.update.basket.item");
	             }else{
	            	 sql5 = daoUtil.getString("customer.checkout.update.basket.item.update.price");
	             }
	             
	             pstmt = conn.prepareStatement(sql5);
	             pstmt.setString(1, newShopperID);
	             pstmt.setString(2, oldShopperID);
	             pstmt.setBoolean(3, byAgent);
	             pstmt.executeUpdate();
	             pstmt = null;
	        
	             flag = true;	                          
	         }
	         catch (Exception e)
	         {
	             LOGGER.warning("ERROR Execute DAO");
	             e.printStackTrace();
	             flag = false;	  
	         }
	         finally
	         {
	             try
	             {
	                 if (flag)
	                 {
	                     conn.commit();
	                 }
	                 else
	                 {
	                     conn.rollback();
	                 }
	                 conn.setAutoCommit(true);

	                 if (rs != null)
	                     rs.close();
	                 if (stmt != null)
	                     stmt.close();
	                 if (pstmt != null)
	                     pstmt.close();
	                 if (conn != null)
	                     conn.close();
	             }
	             catch (SQLException sqlE)
	             {
	                 sqlE.getStackTrace();
	             }
	         }
	         return flag;
	    }
	    
	    public Boolean performNonCheckout(String newShopperID,String oldShopperID, Boolean byAgent)
	    {
	    	 Boolean flag = false;
	         try
	         {	                	 
	             LOGGER.info("Execute CustomerDAO - Function performNonCheckout ");
	             DAOUtils daoUtil = DAOUtils.getInstance();
	             conn = daoUtil.getConnection();
	             conn.setAutoCommit(false);
     
	             String sql1 = daoUtil.getString("customer.checkout.delete.basket");
	             if (byAgent)
	                 sql1 = daoUtil.getString("customer.checkout.delete.basket.agent");   
	             pstmt = conn.prepareStatement(sql1);
	             pstmt.setString(1,newShopperID);	             

	             pstmt.executeUpdate();
	             pstmt = null;
	          	             
	             String sql2 = daoUtil.getString("customer.checkout.update.basket");
	             if(byAgent)
	                 sql2 = daoUtil.getString("customer.checkout.update.basket.agent");
	             pstmt = conn.prepareStatement(sql2);
	             pstmt.setString(1, newShopperID);
	             pstmt.setString(2, oldShopperID);
	             pstmt.executeUpdate();
	             pstmt = null;
	             
	                        
	             String sql5 = "";
	             if(newShopperID.equals(oldShopperID))
	             {
	            	 sql5 = daoUtil.getString("customer.checkout.update.basket.item");
	             }else{
	            	 sql5 = daoUtil.getString("customer.checkout.update.basket.item.update.price");
	             }
	             
	             pstmt = conn.prepareStatement(sql5);
	             pstmt.setString(1, newShopperID);
	             pstmt.setString(2, oldShopperID);
	             pstmt.setBoolean(3, byAgent);
	             
	             pstmt.executeUpdate();
	             pstmt = null;
	             
	             
	             
	            
	             flag = true;	             	             
	         }
	         catch (Exception e)
	         {
	             LOGGER.warning("ERROR Execute DAO");
	             e.printStackTrace();
	             flag = false;	
	         }
	         finally
	         {
	             try
	             {
	                 if (flag)
	                 {
	                     conn.commit();
	                 }
	                 else
	                 {
	                     conn.rollback();
	                 }
	                 conn.setAutoCommit(true);

	                 if (rs != null)
	                     rs.close();
	                 if (stmt != null)
	                     stmt.close();
	                 if (pstmt != null)
	                     pstmt.close();
	                 if (conn != null)
	                     conn.close();
	             }
	             catch (SQLException sqlE)
	             {
	                 sqlE.getStackTrace();
	             }
	         }
	         return flag;
	    }
	    
	    public Boolean updateOrderRowset(String newShopperID)
		{
	    	Boolean result = false;
	    	 try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		         String sql = daoUtil.getString("customer.checkout.get.order");	         
		         pstmt = conn.prepareStatement(sql);
	             pstmt.setString(1,newShopperID);	      
		         rs = pstmt.executeQuery(); 
     	    		
	     	    if (rs.next())
	     	    {
	     	    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     	    	
	     	    	sql = daoUtil.getString("customer.checkout.update.order");	
	     	    	PreparedStatement pstmt1 = conn.prepareStatement(sql);
	     	    	pstmt1.setString(1, dateFormat.format(new Date()));
	     	    	pstmt1.setString(2, newShopperID);
	     	    	result = (pstmt1.executeUpdate() > 0)?true:false;	  
	     	    	if (result)
	     	    		pstmt1.close();
	     	    }
	     	    else
	     	    {
	     	    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	     	    	
	     	    	sql = daoUtil.getString("customer.checkout.create.order");	
	     	    	PreparedStatement pstmt1 = conn.prepareStatement(sql);
	     	    	pstmt1.setString(1, newShopperID);
	     	    	pstmt1.setString(2, dateFormat.format(new Date()));
	     	    	pstmt1.setString(3, dateFormat.format(new Date()));
	     	    	result = (pstmt1.executeUpdate() > 0)?true:false;	
	     	    	if (result)
	     	    		pstmt1.close();
	     	    }
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
	    
	    public Map<String, String[]> getAllUserTypeA()
        {
        	Map<String,String[]> result = new HashMap<String,String[]>();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            
	            String sql = daoUtil.getString("get.admin.users.typeA");	         
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(sql.toString());	            
	           
	            while (rs.next())
	            {    	  
	            	String[] agent = new String[2];
	            	agent[0] = rs.getString("UserName");
	            	agent[1] = rs.getString("email");
	            		
	               	result.put(rs.getString("agent_id"),agent);		       
	            	//result.put(rs.getString("UserName"),rs.getString("email"));
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
        }
	    
	    public List<String> getSiteReferralSource()
	    {
	    	List<String> result = new ArrayList<String>();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            String sql = daoUtil.getString("get.siteReferral.source");
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(sql.toString());	            
	           
	            while (rs.next())
	            {  
	            	result.add(rs.getString("masterSource"));	            	
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
	    }        
        
        public Map<String, SiteReferral> getSiteReferralDescription()
        {
        	Map<String,SiteReferral> result = new HashMap<String,SiteReferral>();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            
	            String sql = daoUtil.getString("get.siteReferral.description");	         
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(sql.toString());	            
	           
	            while (rs.next())
	            {	           	
	            	SiteReferral tmp = new SiteReferral();
	            	tmp.setSourceID(rs.getString("sourceID"));
	            	tmp.setSourceDescription(rs.getString("sourceDescription"));
	            	tmp.setMasterSource(rs.getString("masterSource"));
	            	
	               	result.put(rs.getString("sourceID"),tmp);		       
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
        }
        
	    public List<String> getCountries()
	    {
	    	List<String> result = new ArrayList<String>();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            String sql = daoUtil.getString("get.country.all");
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(sql.toString());	            
	           
	            while (rs.next())
	            {	            	
	            	result.add(rs.getString("Country"));	            	
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
	    }
	    
	    public List<String> getStates()
	    {
	    	List<String> result = new ArrayList<String>();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            String sql = daoUtil.getString("get.state.all");
	            stmt = conn.createStatement();
	            rs = stmt.executeQuery(sql.toString());	            
	           
	            while (rs.next())
	            {	            	
	            	result.add(rs.getString("KeyWord"));	            	
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
	    }
	 	 	    
	    public Map<String,Customer> searchCustomer(int start, int end, String linkNumber, String shipFname, 
	    		String shipLname, String shipCompany, String shipPhone, String billCompany
	    		,String billFname, String billLname, String billPhone){
	    	
	    	//Old SQL query for paging in MS SQL 2005
	    	//search.customer.all = SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY ship_to_Fname ASC) AS rownum, contactInfo.*, (SELECT COUNT(*) FROM contactinfo 0) AS TotalRow FROM contactInfo 0) AS contactInfoRow WHERE  rownum >= ? AND rownum <= ?
	    	
	    	//
	    	
	    	
	    	Map<String,Customer> result = new HashMap<String,Customer>();
	    	
	        try
	        {
	            LOGGER.info("Execute DAO");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            
	            StringBuilder sql = new StringBuilder();
	           
	            
	            StringBuilder condition = new StringBuilder();
	           	            
	            boolean hadCondition = false;
	            
	           	if (!Constants.isEmpty(linkNumber))
	            {
	           		condition.append(Constants.SQL_CONDITION_WHERE);
	           		condition.append(daoUtil.getString("search.customer.linkNumber").replaceAll("0", linkNumber.trim()));
	           		hadCondition = true;
	           	}
	           	if (!Constants.isEmpty(shipFname))
	           	{
	           		if (hadCondition)
	           		{
	           			condition.append(Constants.SQL_CONDITION_AND);
	           			condition.append(daoUtil.getString("search.customer.shipFname").replaceAll("0", shipFname.trim()));
	           		}
	           		else
	           		{
	           			condition.append(Constants.SQL_CONDITION_WHERE);
	           			System.out.println("search.customer.shipFname:" + daoUtil.getString("search.customer.shipFname"));
	           			System.out.println("shipFname:" + shipFname.trim());
	           			condition.append(daoUtil.getString("search.customer.shipFname").replaceAll("0", shipFname.trim()));
	           		}
	           		hadCondition = true;
	           	}
	    	    if (!Constants.isEmpty(shipLname))
	    	    {
	    	       	if (hadCondition)
	           		{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.customer.shipLname").replaceAll("0", shipLname.trim()));
	           		}
	           		else
	           		{
	           			condition.append(Constants.SQL_CONDITION_WHERE);
	           			condition.append(daoUtil.getString("search.customer.shipLname").replaceAll("0", shipLname.trim()));
	           		}
	           		hadCondition = true;
	    	    }	    	    		 
	    	    if (!Constants.isEmpty(shipCompany))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.customer.shipCompany").replaceAll("0", shipCompany.trim()));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.customer.shipCompany").replaceAll("0", shipCompany.trim()));
	            	}
	            	hadCondition = true;
	    	    }	   
	    	    if (!Constants.isEmpty(shipPhone))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.customer.shipPhone").replaceAll("0", shipPhone.trim()));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.customer.shipPhone").replaceAll("0", shipPhone.trim()));
	            	}
	            	hadCondition = true;
	    	    }	   
	    	    if (!Constants.isEmpty(billCompany))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.customer.billCompany").replaceAll("0", billCompany.trim()));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.customer.billCompany").replaceAll("0", billCompany.trim()));
	            	}
	            	hadCondition = true;
	    	    }	  
	    	    if (!Constants.isEmpty(billFname))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.customer.billFname").replaceAll("0", billFname.trim()));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.customer.billFname").replaceAll("0", billFname.trim()));
	            	}
	            	hadCondition = true;
	    	    }	 
	    	    if (!Constants.isEmpty(billLname))
	    	    {
	    	       	if (hadCondition)
	            	{
	    	       		condition.append(Constants.SQL_CONDITION_AND);
	    	       		condition.append(daoUtil.getString("search.customer.billLname").replaceAll("0", billLname.trim()));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.customer.billLname").replaceAll("0", billLname.trim()));
	            	}
	            	hadCondition = true;
	    	    }	
	    	    if (!Constants.isEmpty(billPhone))
	    	    {
	    	     	if (hadCondition)
	            	{
	    	     		condition.append(Constants.SQL_CONDITION_AND);
	    	     		condition.append(daoUtil.getString("search.customer.billPhone").replaceAll("0", billPhone.trim()));
	            	}
	            	else
	            	{
	            		condition.append(Constants.SQL_CONDITION_WHERE);
	            		condition.append(daoUtil.getString("search.customer.billPhone").replaceAll("0", billPhone.trim()));
	            	}
	            	hadCondition = true;
	    	    }	
	    	    
	    	    //sql.append(daoUtil.getString("search.customer.all").replaceAll("0",condition.toString()));
	    	    
	    	    	    	   
	            //stmt = conn.createStatement();
	    	    //pstmt = conn.prepareStatement(sql.toString());
	    	    
	    	    //SELECT * FROM (SELECT TOP ? * FROM (SELECT TOP ? contactInfo.*, (SELECT COUNT(*) FROM contactinfo 0) AS TotalRow FROM contactInfo 0 ORDER BY ship_to_Fname ASC) AS contactInfoRow ORDER BY ship_to_Fname DESC) AS another ORDER BY ship_to_Fname ASC

	    	    //New parameter for paging	    	    
	    	    //start = Constants.SHOPPER_LIST_RECORDS_PER_PAGE;
	    	    
	    	    //Hot fix in case last page rows lesser than total row per page
	    	    //if (end%start > 0)
	    	    //	start = end%start;
	            //pstmt.setInt(1, start);
               // pstmt.setInt(2, end);
	           // rs = pstmt.executeQuery();
	    	    
	    	    cst = conn.prepareCall(daoUtil.getString("search.customer.all"));
	    	    
	    	    cst.setInt(1, start);
	            cst.setInt(2, end);         
	            cst.setString(3, "ship_to_Fname");	          
	            cst.setString(4, condition.toString());
	           
	            rs = cst.executeQuery();
	            
	           
	            while (rs.next())
	            {	            	
	                
	            	Customer tmpCustomer = new Customer();
	            	tmpCustomer.setLinkNumber(rs.getInt(Constants.DB_FIELD_LINK_NUMBER));
	            	tmpCustomer.setShopper_number(rs.getInt(Constants.DB_FIELD_SHOPPER_NUMBER));
	            	tmpCustomer.setShopper_id(rs.getString(Constants.DB_FIELD_SHOPPER_ID));
	            	tmpCustomer.setShip_to_fname(rs.getString(Constants.DB_FIELD_SHIP_FNAME));
	            	tmpCustomer.setShip_to_lname(rs.getString(Constants.DB_FIELD_SHIP_LNAME));
	            	tmpCustomer.setShip_to_company(rs.getString(Constants.DB_FIELD_SHIP_COMPANY));
	            	tmpCustomer.setShip_to_phone(rs.getString(Constants.DB_FIELD_SHIP_PHONE));
	            	tmpCustomer.setBill_to_company(rs.getString(Constants.DB_FIELD_BILL_COMPANY));
	            	tmpCustomer.setBill_to_fname(rs.getString(Constants.DB_FIELD_BILL_FNAME));
	            	tmpCustomer.setBill_to_lname(rs.getString(Constants.DB_FIELD_BILL_LNAME));
	            	tmpCustomer.setBill_to_phone(rs.getString(Constants.DB_FIELD_BILL_PHONE));
	            	tmpCustomer.setAgent_id(rs.getInt(Constants.DB_FIELD_AGENT_ID));
	            	tmpCustomer.setTotalRow(rs.getInt("TotalRow"));
	            	
	               	result.put(rs.getString(Constants.DB_FIELD_SHOPPER_ID),tmpCustomer);		       
	            }
	        }
	        catch (Exception e)
	        {
	            LOGGER.warning("ERROR Execute DAO");
	            e.printStackTrace();
	        }finally{
	            try{	            
	            if(rs!=null) rs.close();
	            if(stmt!=null) stmt.close();
	            if(conn!=null) conn.close();
	            }catch(SQLException sqlE)
	            {
	            	sqlE.getStackTrace();
	            }
	        }
	        
	        return result;
	    }
	    
	    public boolean addNewCustomer(Customer customer,Boolean usingManager,String section,String oldShopperID)
	    {
	    	 Boolean flag = false;
	         try
	         {
	            LOGGER.info("Execute customerDAO - Function updateCustomer ");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            conn.setAutoCommit(false);
	    	
	               	    		
	    	    String sql = daoUtil.getString("customer.checkout.add.shopper");	
	    	 pstmt = conn.prepareStatement(sql);
	    	 pstmt.setString(1, customer.getShopper_id());        
             pstmt.setString(2, customer.getBill_to_fname().replaceAll("'","''"));
             pstmt.setString(3, customer.getBill_to_lname().replaceAll("'","''"));
             pstmt.setString(4, customer.getBill_to_title().replaceAll("'","''"));
             
             pstmt.setString(5, customer.getBill_to_company().replaceAll("'","''"));
             pstmt.setString(6, customer.getBill_to_address1().replaceAll("'","''"));
             pstmt.setString(7, customer.getBill_to_address2().replaceAll("'","''"));
             pstmt.setString(8, customer.getBill_to_city().replaceAll("'","''"));
             
             pstmt.setString(9, customer.getBill_to_state().replaceAll("'","''"));
             pstmt.setString(10, customer.getBill_to_postal().replaceAll("'","''"));
             //pstmt.setString(11, customer.getBill_to_county().replaceAll("'","''"));
             pstmt.setString(11, "");
             
             pstmt.setString(12, customer.getBill_to_country().replaceAll("'","''"));
             pstmt.setString(13, customer.getBill_to_fax().replaceAll("'","''"));
             pstmt.setString(14, customer.getBill_to_phone().replaceAll("'","''"));
             
             pstmt.setString(15, customer.getBill_to_phoneext().replaceAll("'","''"));
             pstmt.setString(16, customer.getBill_to_email().replaceAll("'","''"));
             
             pstmt.setString(17, customer.getShip_to_fname().replaceAll("'","''"));
             pstmt.setString(18, customer.getShip_to_lname().replaceAll("'","''"));
             pstmt.setString(19, customer.getShip_to_title().replaceAll("'","''"));
             
             pstmt.setString(20, customer.getShip_to_company().replaceAll("'","''"));
             pstmt.setString(21, customer.getShip_to_address1().replaceAll("'","''"));
             pstmt.setString(22, customer.getShip_to_address2().replaceAll("'","''"));
             
             pstmt.setString(23, customer.getShip_to_city().replaceAll("'","''"));
             pstmt.setString(24, customer.getShip_to_state().replaceAll("'","''"));
             pstmt.setString(25, customer.getShip_to_postal().replaceAll("'","''"));
             //pstmt.setString(26, customer.getShip_to_county().replaceAll("'","''"));
             pstmt.setString(26, "");
             pstmt.setString(27, customer.getShip_to_country().replaceAll("'","''"));
             pstmt.setString(28, customer.getShip_to_fax().replaceAll("'","''"));
             pstmt.setString(29, customer.getShip_to_phone().replaceAll("'","''"));
             pstmt.setString(30, customer.getShip_to_phoneext().replaceAll("'","''"));
             pstmt.setString(31, customer.getShip_to_email().replaceAll("'","''"));
             pstmt.setInt(32, customer.getTax_exempt());
             pstmt.setString(33, customer.getTax_exempt_number().replaceAll("'","''"));     
             // Taxt exempt expire date in DB is not allow null, so we passing a 
             // default date into it 01/01/1900
             Timestamp defaultDate = Timestamp.valueOf(Converter.dateToTimeStamp(Converter.stringToDate("01/01/1900")));
             pstmt.setTimestamp(34, (customer.getTax_exempt_expire() == null)?defaultDate:Timestamp.valueOf(Converter.dateToTimeStamp(customer.getTax_exempt_expire())));
             
             pstmt.setString(35, customer.getLoginID().replaceAll("'","''"));
             pstmt.setString(36, customer.getPassword().replaceAll("'","''"));
             pstmt.setString(37, customer.getHint().replaceAll("'","''"));
        	 pstmt.setString(38, customer.getAnswer().replaceAll("'","''"));        	
         	 
             pstmt.setInt(39, customer.getPromo_email());
             pstmt.setInt(40, customer.getHeardAboutSiteFrom());
             pstmt.setString(41, customer.getEquip_use().replaceAll("'","''"));             
             pstmt.setInt(42, customer.getAgent_id());             
             pstmt.setTimestamp(43,(customer.getAgent_id_exp() == null)?null:Timestamp.valueOf(Converter.dateToTimeStamp(customer.getAgent_id_exp())));
             
             pstmt.setInt(44, customer.getLatperdiscount());
             pstmt.setInt(45, customer.getLatamtdiscount());
             pstmt.setTimestamp(46,(customer.getLatexpdate() == null)?null:Timestamp.valueOf(Converter.dateToTimeStamp(customer.getLatexpdate())));
             
             pstmt.setInt(47, customer.getInsperdiscount());
             pstmt.setInt(48, customer.getInsamtdiscount());
             pstmt.setTimestamp(49,(customer.getInsexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getInsexpdate())));
             
             pstmt.setInt(50, customer.getOptperdiscount());
             pstmt.setInt(51, customer.getOptamtdiscount());
             pstmt.setTimestamp(52,(customer.getOptexpdate() == null)?null:Timestamp.valueOf(Converter.dateToTimeStamp(customer.getOptexpdate())));
             
             pstmt.setInt(53, customer.getDimperdiscount());
             pstmt.setInt(54, customer.getDimamtdiscount());
             pstmt.setTimestamp(55,(customer.getDimexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getDimexpdate())));
                                      		 
             pstmt.setInt(56, customer.getMonperdiscount());
             pstmt.setInt(57, customer.getMonamtdiscount());
             pstmt.setTimestamp(58, (customer.getMonexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getMonexpdate())));
             
             pstmt.setInt(59, customer.getSerperdiscount());
             pstmt.setInt(60, customer.getSeramtdiscount());
             pstmt.setTimestamp(61, (customer.getSerexpdate() == null)?null:Timestamp.valueOf(Converter.dateToTimeStamp(customer.getSerexpdate())));
             
             pstmt.setInt(62, customer.getWorperdiscount());
             pstmt.setInt(63, customer.getWoramtdiscount());
             pstmt.setTimestamp(64,(customer.getWorexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getWorexpdate())));
             
             pstmt.setString(65, customer.getAccount_type().replaceAll("'","''"));
             pstmt.setString(66, customer.getLoa());
             
             
             int result1 = pstmt.executeUpdate();	
             if (result1 > 0)
	         {
	        	 String sql4 = daoUtil.getString("customer.checkout.update.basket");
	        	 pstmt = conn.prepareStatement(sql4);
	             pstmt.setString(1, customer.getShopper_id());
	             pstmt.setString(2, oldShopperID);
	             pstmt.executeUpdate();
	             pstmt = null;       
	             
	             String sql5 = daoUtil.getString("customer.checkout.update.basket.item");
	             pstmt = conn.prepareStatement(sql5);
	             pstmt.setString(1, customer.getShopper_id());
	             pstmt.setString(2, oldShopperID);
	             pstmt.setBoolean(3, usingManager);
	             
	             pstmt.executeUpdate();
	        	 
	             flag = true;
	         }	    
	         }
	         catch (Exception e)
	         {
	             LOGGER.warning("ERROR Execute DAO");
	             e.printStackTrace();
	         }
	         finally
	         {
	             try
	             {
	                 if (flag)
	                 {
	                     conn.commit();
	                 }
	                 else
	                 {
	                     conn.rollback();
	                 }
	                 conn.setAutoCommit(true);

	                 if (rs != null)
	                     rs.close();
	                 if (stmt != null)
	                     stmt.close();
	                 if (pstmt != null)
	                     pstmt.close();
	                 if (conn != null)
	                     conn.close();
	             }
	             catch (SQLException sqlE)
	             {
	                 sqlE.getStackTrace();
	             }
	         }
	         return flag;    
	    }	  
	    	   
	    
	    public boolean updateCustomer(Customer customer, boolean usingManager, String section)
	    {
	    	 Boolean flag = false;
	         try
	         {
	            LOGGER.info("Execute customerDAO - Function updateCustomer ");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            conn.setAutoCommit(false);
     
	            StringBuilder sql = new StringBuilder();
	            sql.append(daoUtil.getString("customer.checkout.update.shopper"));	            	 
	             
	         	if (usingManager) 	    		
	         		sql.append(daoUtil.getString("customer.checkout.update.manager"));
 	    		
 	    		if ((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout")))
 	    			sql.append(daoUtil.getString("customer.checkout.update.new1"));	    			
 	    		
 	    		if ((section.equalsIgnoreCase("new")) || (section.equalsIgnoreCase("new_checkout")) || section.equalsIgnoreCase("manage"))
 	    		{
 	    			sql.append(daoUtil.getString("customer.checkout.update.new2"));
 	    		}
 	    		
 	    		sql.append(daoUtil.getString("customer.checkout.update.end"));
 	    		 	    			    			
 	    		
	            pstmt = conn.prepareStatement(sql.toString());
	            pstmt.setString(1, customer.getBill_to_fname().replaceAll("'","''"));
	            pstmt.setString(2, customer.getBill_to_lname().replaceAll("'","''"));
	            pstmt.setString(3, customer.getBill_to_title().replaceAll("'","''"));
	            pstmt.setString(4, customer.getBill_to_company().replaceAll("'","''"));
	            pstmt.setString(5, customer.getBill_to_address1().replaceAll("'","''"));
	            pstmt.setString(6, customer.getBill_to_address2().replaceAll("'","''"));
	            pstmt.setString(7, customer.getBill_to_city().replaceAll("'","''"));
	            pstmt.setString(8, customer.getBill_to_state().replaceAll("'","''"));
	            pstmt.setString(9, customer.getBill_to_postal().replaceAll("'","''"));
	            //customer.getBill_to_county().replaceAll("'","''"   || County does not exist
	            //on GUI
	            pstmt.setString(10, null);
	            pstmt.setString(11, customer.getBill_to_country().replaceAll("'","''"));
	            pstmt.setString(12, customer.getBill_to_fax().replaceAll("'","''"));
	            pstmt.setString(13, customer.getBill_to_phone().replaceAll("'","''"));
	            pstmt.setString(14, customer.getBill_to_phoneext().replaceAll("'","''"));
	            pstmt.setString(15, customer.getBill_to_email().replaceAll("'","''"));
	            pstmt.setString(16, customer.getShip_to_fname().replaceAll("'","''"));
	            pstmt.setString(17, customer.getShip_to_lname().replaceAll("'","''"));
	            pstmt.setString(18, customer.getShip_to_title().replaceAll("'","''"));
	            pstmt.setString(19, customer.getShip_to_company().replaceAll("'","''"));
	            pstmt.setString(20, customer.getShip_to_address1().replaceAll("'","''"));
	            pstmt.setString(21, customer.getShip_to_address2().replaceAll("'","''"));
	            pstmt.setString(22, customer.getShip_to_city().replaceAll("'","''"));
	            pstmt.setString(23, customer.getShip_to_state().replaceAll("'","''"));
	            pstmt.setString(24, customer.getShip_to_postal().replaceAll("'","''"));
	            // customer.getShip_to_county().replaceAll("'","''") || County does not exist
	            //on GUI	           
	            pstmt.setString(25, null);
	            pstmt.setString(26, customer.getShip_to_country().replaceAll("'","''"));
	            // customer.getShip_to_fax().replaceAll("'","''") || Ship_to_fax does not exist
	            //on GUI
	            pstmt.setString(27, null);
	            pstmt.setString(28, customer.getShip_to_phone().replaceAll("'","''"));
	            pstmt.setString(29, customer.getShip_to_phoneext().replaceAll("'","''"));
	         // customer.getShip_to_email().replaceAll("'","''") || Ship_to_email does not exist
	            //on GUI
	            pstmt.setString(30, customer.getShip_to_email().replaceAll("'","''"));
	            int index = 31;
	            if (usingManager)
		    	{
	            	
	            	 pstmt.setInt(index++, customer.getTax_exempt());
	            	 pstmt.setString(index++, customer.getTax_exempt_number().replaceAll("'","''"));
	            	 
	            	 pstmt.setTimestamp(index++, (customer.getTax_exempt_expire() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getTax_exempt_expire())));
	            	 pstmt.setInt(index++, customer.getLatperdiscount());
	            	 pstmt.setInt(index++, customer.getLatamtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getLatexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getLatexpdate())));	            	 
	            	 pstmt.setInt(index++, customer.getInsperdiscount());
	            	 pstmt.setInt(index++, customer.getInsamtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getInsexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getInsexpdate())));	            	 
	            	 pstmt.setInt(index++, customer.getOptperdiscount());
	            	 pstmt.setInt(index++, customer.getOptamtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getOptexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getOptexpdate())));	            	 
	            	 pstmt.setInt(index++, customer.getDimperdiscount());
	            	 pstmt.setInt(index++, customer.getDimamtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getDimexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getDimexpdate())));	            	 
	            	 pstmt.setInt(index++, customer.getMonperdiscount());
	            	 pstmt.setInt(index++, customer.getMonamtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getMonexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getMonexpdate())));	            	 
	            	 pstmt.setInt(index++, customer.getSerperdiscount());
	            	 pstmt.setInt(index++, customer.getSeramtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getSerexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getSerexpdate())));	            	 
	            	 pstmt.setInt(index++, customer.getWorperdiscount());
	            	 pstmt.setInt(index++, customer.getWoramtdiscount());
	            	 pstmt.setTimestamp(index++, (customer.getWorexpdate() == null)?null: Timestamp.valueOf(Converter.dateToTimeStamp(customer.getWorexpdate())));	            	
	            	 pstmt.setString(index++, customer.getAccount_type().replaceAll("'","''"));
	            	 pstmt.setString(index++, customer.getLoa().replaceAll("'","''"));	    
	            	 pstmt.setBigDecimal(index++, customer.getCreditline());
	            	 pstmt.setString(index++, customer.getCreditexp().replaceAll("'","''"));
	            	 pstmt.setBigDecimal(index++, new BigDecimal(0.0));
	            	 pstmt.setString(index++, customer.getCreditcomment().replaceAll("'","''"));	            	           	 
		    	}
	            if (section.equalsIgnoreCase("new") || section.equalsIgnoreCase("new_checkout"))
	            	 pstmt.setString(index++, customer.getLoginID().replaceAll("'","''"));
	             
	            if ((section.equalsIgnoreCase("new")) || section.equalsIgnoreCase("new_checkout") || section.equalsIgnoreCase("manage"))
		    	{
	            	 pstmt.setString(index++, customer.getPassword().replaceAll("'","''"));
	            	 pstmt.setString(index++, customer.getHint().replaceAll("'","''"));
	            	 pstmt.setString(index++, customer.getAnswer().replaceAll("'","''"));		    			
		    	}
            	 
            	pstmt.setInt(index++, customer.getPromo_email());
            	pstmt.setString(index++, customer.getShopper_id());	 
            	 
	            int result1 = pstmt.executeUpdate();	            	        
	            if (result1 > 0)
	            {
	                 flag = true;
	            }	             
	         }
	         catch (Exception e)
	         {
	             LOGGER.warning("ERROR Execute DAO");
	             e.printStackTrace();
	         }
	         finally
	         {
	             try
	             {
	                 if (flag)
	                 {
	                     conn.commit();
	                 }
	                 else
	                 {
	                     conn.rollback();
	                 }
	                 conn.setAutoCommit(true);

	                 if (rs != null)
	                     rs.close();
	                 if (stmt != null)
	                     stmt.close();
	                 if (pstmt != null)
	                     pstmt.close();
	                 if (conn != null)
	                     conn.close();
	             }
	             catch (SQLException sqlE)
	             {
	                 sqlE.getStackTrace();
	             }
	         }
	         return flag;	         
	    }
	    
	    public List<String> verifyZipCode(String inpCity, String inpState, String inpPostal, String inpCounty,String inpName)
	    {
	    	 List<String> errors = new ArrayList<String>(); 
	        	 
	         try
	         {
	            LOGGER.info("Execute customerDAO - Function verifyZipCode ");
	            DAOUtils daoUtil = DAOUtils.getInstance();
	            conn = daoUtil.getConnection();
	            String sql = daoUtil.getString("customer.validation.zip1");
	    	    
	    	    pstmt = conn.prepareStatement(sql);
	    	    pstmt.setString(1, (inpPostal.length() > 5)?inpPostal.substring(0, 4):inpPostal);
	    	    rs = pstmt.executeQuery();	
	    	    boolean isError;
	    	    String stateFound = "";
             
	    	    if (rs.next())
	    	    {	           	
	    	    	isError = true;
	    	    	while (rs.next())
	    	    	{
	    				stateFound = rs.getString("state");
	    				if (rs.getString("city").equalsIgnoreCase(inpCity)) 
	    				{
	    					isError   = false;	    				
	    				}
	    				else if (rs.getString("city").contains(inpCity))
	    				{
	    					isError   = false;	    					 				
	    				}
	    	    	}
	    			
	    	    if (isError) 
	    		{
	    				
	    				sql = daoUtil.getString("customer.validation.zip2");
	    				pstmt = conn.prepareStatement(sql);
	    		    	pstmt.setString(1, inpCity);
	    		    	pstmt.setString(2, (inpPostal.length() > 5)?inpPostal.substring(0, 4):inpPostal);  	    	               
	    	             
	    	            rs = pstmt.executeQuery();	
	    	            
	    				if (rs.next())
	    				{
	    					isError   = false;	    						    					
	    				}
	    				if (isError)
	    				{	    					
	    					sql = daoUtil.getString("customer.validation.zip3");
	    					pstmt = conn.prepareStatement(sql);
		    		    	pstmt.setString(1, (inpPostal.length() > 5)?inpPostal.substring(0, 4):inpPostal);  
		    	             
		    	            rs = pstmt.executeQuery();	
		    	            
	    					String list_cities = "";
	    					while (rs.next())
	    					{
	    						if (list_cities.length() > 0) 
	    						{
	    							list_cities = list_cities + ", ";
	    						}
	    						list_cities = list_cities + rs.getString("city");	    						
	    					}

	    					if (isError) 
	    					{
	    						if (!stateFound.equalsIgnoreCase(inpState)) 
	    						{
	    							errors.add(inpName + " Zip Code (" + inpPostal + ") is not in the State - " + inpState);
	    						}
	    						errors.add(inpName + " City (" + inpCity + ") does not match " + inpName + " Zip Code (" + inpPostal + ").");
	    						if (list_cities.length() > 0)  
	    						{
	    							errors.add("For Zip Code (" + inpPostal + "), the following are possible matches: " + list_cities);
	    						}
	    					}
	    				}
	    		}     
	         }
	         else
	         {
	        	 errors.add("Unlisted " + inpName + " Zip Code.");
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
	 	            if(stmt!=null) stmt.close();
	 	            if (pstmt != null) pstmt.close();
	 	            if(conn!=null) conn.close();
	 	            }catch(SQLException sqlE)
	 	            {
	 	            	sqlE.getStackTrace();
	 	            }
	         }	        
	         
	         return errors;                 
	    		
	    }	  
	    
	    public Boolean isExistShopperLoginID(String loginID)
		{
	    	boolean result = false;
	    	
			try
		     {
		         LOGGER.info("Execute DAO");
		         DAOUtils daoUtil = DAOUtils.getInstance();
		         conn = daoUtil.getConnection();
		            
		        String sql = daoUtil.getString("shopper.check.exist.loginID");	         
		        pstmt = conn.prepareStatement(sql);            
	            pstmt.setString(1,loginID);
	                       
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
}
