package com.dell.enterprise.agenttool.actions.validation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dell.enterprise.agenttool.services.CustomerServices;
import com.dell.enterprise.agenttool.util.Constants;
import com.dell.enterprise.agenttool.util.Converter;

public class ShopperCheckoutValidation {
	
	 private static final Log LOG = LogFactory.getLog(ShopperCheckoutValidation.class);

	 public static int isEmptyOrderRes(final List<String> searchOrder)
	    {
	        int result = 0;
	        for (int i = 0; i < searchOrder.size(); i++)
	        {
	            if (!searchOrder.get(i).equals(""))
	            {
	                result = 1;
	                break;
	            }
	        }
	        return result;
	    }
	 
	 public static boolean isEmailAddress(String emailAddress)
	 {
		 if (Constants.isEmpty(emailAddress))
			 return false;
		 else
		 {
			 Pattern email = Pattern.compile("^\\S+@\\S+$");

			 Matcher fit = email.matcher(emailAddress);
			 if (fit.matches()) {
				 return true;
			 } else {
				 return false;
			 }      
		 }
	 }
	 
	 public static boolean check_hasValue(Object obj,String obj_type) 
	 {
		  if (obj_type == "TEXT") 
		  {
			if (obj != null)
			{
				    if (Constants.isEmpty(obj.toString())) 
				   	      return false;
				    else
				    	  return true;		   
			}
			else
			{
				return false;
			}
		  }
		  else if (obj_type == "NUMBER") 
		  {			  
			  if (obj != null)
			  {
				 if (!Constants.isNumber(obj.toString())) 
				 {
				      return false;
				 } 
				 else 
				 {
				      return true;
				 }		   
			  }
			  else
			  {
				return false;
			  }
		  } 		 
		  
		  else if (obj_type == "PHONE3") 
		  {			  
			  if (obj != null)
			  {
				 if (!Constants.isNumber(obj.toString())) 
				 {
				      return false;
				 } 
				 else if (obj.toString().length() != 3)
				 {
					 return false;
				 }
				 else
				 {					  
				      return true;
				 }		   
			  }
			  else
			  {
				return false;
			  }
		  } 
		  
		  else if (obj_type == "PHONE4") {
			  
			  if (obj != null)
			  {
				 if (!Constants.isNumber(obj.toString())) 
				 {
				      return false;
				 } 
				 else if (obj.toString().length() != 4)
				 {
					 return false;
				 }
				 else
				 {					  
				      return true;
				 }		   
			  }
			  else
			  {
				return false;
			  }
		  }
		  else if (obj_type == "SELECT" || obj_type == "RADIO" || obj_type == "CHECKBOX") 
		  {
			    
			    if (obj == null)
			      	return false;
			    else if (obj.toString().equalsIgnoreCase("0"))
			      	return false;
			    else
			    	return true;

		  } 
		  else if (obj_type == "SINGLE_VALUE_RADIO" || obj_type == "SINGLE_VALUE_CHECKBOX") 
		  {
			  	if (obj == null)
			      	return false;
			  	else if (obj.toString().equalsIgnoreCase("0"))
				      	return false;
			    else
			      	return true;      

		  } 		
		  
		  return false;
	}
	 
	 public static List<String> checkValidDiscount(boolean usingManager, String agent_level,Object theChkBox,Object theAmtField,Object thePerField,Object theExpField,String theProduct, int maxDiscountAmt, int maxDiscount)
	 {	
		 List<String> errors = new ArrayList<String>();  
		 
		 String tmp;
		 boolean disabled;
		 if (theChkBox != null)
		 {
			tmp = theChkBox.toString();			
			if (tmp.length() > 3)
			{
				tmp = tmp.substring(tmp.length()-3, tmp.length());
				disabled = (tmp.equalsIgnoreCase("dis"))?true:false;
			}
			else
				disabled = false;
		 }
		 else
			 disabled = true;
		 
			if (!disabled) 
		 	{								
		 		if (theExpField.toString()=="") 
		 		{	
		 			//theExpField.focus();
		 			errors.add("Incomplete Product Discount. You must specify a " + theProduct + " Expiration Date."); 		
		 		}
		 		else if (thePerField.toString()=="") 
		 		{	
		 			if (theAmtField.toString()=="")
		 			{		
		 				errors.add("Incomplete Product Discount. You must specify a " + theProduct + "  percent or dollar discount.");
		 				//thePerField.focus();		 				
		 			}
		 			else
		 			{
		 				if (Converter.stringToInt(theAmtField.toString()) > maxDiscountAmt)
		 					
		 				{
		 					errors.add("Invalid Product Discount. The " +theProduct+ " Discount $ exceeds the maximum allowable discount of $" +maxDiscountAmt+ ".");
		 					//theAmtField.focus();		 				
		 				}
		 				else if (Converter.stringToInt(theAmtField.toString()) < 0)
		 				{
		 					errors.add("Invalid Product Discount. The " +theProduct+ " Discount $ must be a value greater than 0.");
		 					//theAmtField.focus();		 					
		 				}
		 			}
		 		}
		 		else if (theAmtField.toString()=="")
		 		{
		 			if (Converter.stringToInt(thePerField.toString()) > maxDiscount)
		 			{
		 				errors.add("Invalid Product Discount. The " +theProduct+ " Discount % exceeds the maximum allowable discount of " + maxDiscount + "%.");
		 				//thePerField.focus();		 				
		 			}
		 			else if (Converter.stringToInt(thePerField.toString()) < 0)
		 			{
		 				errors.add("Invalid Product Discount. The " +theProduct+ " Discount % must be a value greater than 0.");
		 				//thePerField.focus();		 				
		 			}
		 		}
		 		else
		 		{
		 			errors.add("Invalid Product Discount. You may only specify a '" +theProduct+ " percent or dollar discount.");
		 				//thePerField.focus();		 				
		 		}		 	
		     }
		 	/*else
		 	{		
		 		if (usingManager || agent_level.equalsIgnoreCase("A")) {
		 			
		 			
		 				if (theExpField.toString() != "")
		 				{
		 					errors.add("Invalid Product Discount. You have entered data without checking the box!");
		 					//theChkBox.focus();		 					
		 				}
		 				else if (theAmtField.toString() != "")
		 				{
		 					errors.add("Invalid Product Discount. You have entered data without checking the box!");
		 					//theChkBox.focus();		 					
		 				}
		 				else if (thePerField.toString() != "")
		 				{
		 					errors.add("Invalid Product Discount. You have entered data without checking the box!");
		 					//theChkBox.focus();		 					
		 				}
		 		  
		 			
		 		 } else { 
		 			if (theExpField.toString() != "")
		 			{
		 				errors.add("Invalid Product Discount. You have entered data without checking the box!");
		 				//theChkBox.focus();		 			
		 			}
		 			else if (theAmtField.toString() != "")
		 			{
		 				errors.add("Invalid Product Discount. You have entered data without checking the box!");
		 				//theChkBox.focus();
		 			}
		 			else if (thePerField.toString() != "")
		 			{
		 				errors.add("Invalid Product Discount. You have entered data without checking the box!");
		 				//theChkBox.focus();
		 			}
		 		 } 
		 	}*/
		 
		  
		 return errors;
	 }		
	 
	 public static boolean checkLen(String str, int min, int max)
	 {
		 boolean result = false;
		 if ((str.length() >= min) && (str.length() <= max))
			 result = true;
		 
		 return result;
	 }
	 
	 public static List<String> verifyZipCode(String inpCity, String inpState, String inpPostal, String inpCounty,String inpName)
	 {	 
		 CustomerServices service = new CustomerServices();		 
		 return service.verifyZipCode(inpCity, inpState, inpPostal, inpCounty, inpName);
	 }
	 
	 public static Boolean isExistShopperLoginID(String loginID)
	 {
		 CustomerServices service = new CustomerServices();		 
		 return service.isExistShopperLoginID(loginID);
	 }
}
