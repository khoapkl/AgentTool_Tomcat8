package com.dell.enterprise.agenttool.services;

import java.util.List;
import java.util.Map;

import com.dell.enterprise.agenttool.DAO.CustomerDAO;
import com.dell.enterprise.agenttool.model.Customer;
import com.dell.enterprise.agenttool.model.SiteReferral;

public class CustomerServices {
	
		public Map<String, Customer> searchCustomer(int start, int end,String linkNumber, String shipFname, String shipLname, String shipCompany, String shipPhone, String billCompany, String billFname, String billLname,
	        String billPhone)
	    {
	        CustomerDAO dao = new CustomerDAO();
	        Map<String, Customer> customers = dao.searchCustomer(start, end, linkNumber, shipFname, shipLname, shipCompany, shipPhone, billCompany, billFname, billLname, billPhone);
	        return customers;
	    }
		
		public Customer getCustomerByShopperID(String newShopperID)
		{
			 CustomerDAO dao = new CustomerDAO();
			 Customer customer = dao.getCustomerByShopperID(newShopperID);
		     return customer;
		}
		
		public int getAgentIdByShopperID(String newShopperID)
		{
			 CustomerDAO dao = new CustomerDAO();
			 int agent_Id = dao.getAgentIdByShopperID(newShopperID);
		     return agent_Id;
		}
		
		public Boolean performCheckout(String newShopperID,String oldShopperID,Boolean byAgent)
		{
			CustomerDAO dao = new CustomerDAO();
			Boolean result = dao.performCheckout(newShopperID,oldShopperID,byAgent);
		    return result;
		}
		
		public Boolean updateCustomer(Customer customer,Boolean usingManager,String section)
		{
			CustomerDAO dao = new CustomerDAO();
			Boolean result = dao.updateCustomer(customer,usingManager,section);
		    return result;
		}
		
		public Boolean addNewCustomer(Customer customer,Boolean usingManager,String section,String oldShopperID)
		{
			CustomerDAO dao = new CustomerDAO();
			Boolean result = dao.addNewCustomer(customer,usingManager,section,oldShopperID);
		    return result;
		}
		
		
		public Boolean performNonCheckout(String newShopperID,String oldShopperID, Boolean byAgent)
		{
			CustomerDAO dao = new CustomerDAO();
			Boolean result = dao.performNonCheckout(newShopperID,oldShopperID,byAgent);
		    return result;
		}	
		
		/*public Boolean updateOrderRowset(String newShopperID)
		{
			CustomerDAO dao = new CustomerDAO();
			Boolean result = dao.updateOrderRowset(newShopperID);
		    return result;
		}*/

	    public List<String> getStates()
	    {
	        CustomerDAO dao = new CustomerDAO();
	        List<String> states = dao.getStates();
	        return states;
	    }

	    public List<String> getCountries()
	    {
	        CustomerDAO dao = new CustomerDAO();
	        List<String> countries = dao.getCountries();
	        return countries;
	    }
	    
	    public List<String> getSiteReferralSource()
	    {
	    	CustomerDAO dao = new CustomerDAO();
	        List<String> siteReferralSource = dao.getSiteReferralSource();
	        return siteReferralSource;
	    }    
	    
	    public Map<String, SiteReferral> getSiteReferralDescription()
	    {
	    	CustomerDAO dao = new CustomerDAO();
	    	Map<String, SiteReferral> siteReferralDescription = dao.getSiteReferralDescription();
	        return siteReferralDescription;
	    }
	    
	    public Map<String, String[]> getAllUserTypeA()
	    {    
	    	 CustomerDAO dao = new CustomerDAO();
	    	 Map<String, String[]> users = dao.getAllUserTypeA();
	         return users;
	    }
	    
	    public List<String> verifyZipCode(String inpCity, String inpState, String inpPostal, String inpCounty,String inpName)
	    {
	    	 CustomerDAO dao = new CustomerDAO();
	    	 List<String> errors = dao.verifyZipCode(inpCity,  inpState,  inpPostal,  inpCounty, inpName);
	         return errors;
	    }
	    
	    public Boolean isExistShopperLoginID(String loginID)
		{
			CustomerDAO dao = new CustomerDAO();
			Boolean result = dao.isExistShopperLoginID(loginID);
		    return result;
		}	    
}
