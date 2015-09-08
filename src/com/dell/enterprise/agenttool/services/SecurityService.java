package com.dell.enterprise.agenttool.services;

import java.io.UnsupportedEncodingException;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.dell.enterprise.agenttool.model.*;

import com.dell.enterprise.agenttool.DAO.AuthenticationDAO;
import com.dell.enterprise.agenttool.DAO.SecurityDAO;


public class SecurityService {
	
	public String encryptPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {

	    MessageDigest crypt = MessageDigest.getInstance("SHA-512");
	    crypt.reset();
	    crypt.update(password.getBytes("UTF-8"));

	    return new BigInteger(1, crypt.digest()).toString(16);
	}
	
	public boolean updateSecurity(int securityId, int lockoutTime, int lockoutCount, int expiryDays, int charNumber, int resetHistory , boolean isUppercase,boolean isNumber, boolean isSymbol )
	{
		 SecurityDAO dao = new SecurityDAO();
		 return dao.updateSecurity(securityId, lockoutTime, lockoutCount, expiryDays, charNumber, resetHistory , isUppercase, isNumber, isSymbol);
	}
	public boolean addSecurity(int securityId, int lockoutTime, int lockoutCount, int expiryDays, int charNumber, int resetHistory , boolean isUppercase,boolean isNumber, boolean isSymbol, String resetHistoryDate )
	{
		 SecurityDAO dao = new SecurityDAO();
		 return dao.addSecurity(securityId, lockoutTime,  lockoutCount, expiryDays, charNumber, resetHistory , isUppercase, isNumber, isSymbol, resetHistoryDate);
	}
	public Security getSecurity(int securityId)
	{
		 SecurityDAO dao = new SecurityDAO();
		 return dao.getSecurity(securityId);
	}
	
	public Date addDays(Date date, int days)
	{
		    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	        localCalendar.setTime(date);
	        localCalendar.add(Calendar.DATE, days);
	        Date returndate = localCalendar.getTime();
	        return returndate;
	}
	
	public Date addMonth(Date date, int month)
	{
		    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	        localCalendar.setTime(date);
	        localCalendar.add(Calendar.MONTH, month);
	        Date returndate = localCalendar.getTime();
	        return returndate;
	}
	
	public long subDays(Date date1, Date date2) throws ParseException
	{		
		    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			long d1=sdf.parse(this.formatDate(date1)).getTime();
			long d2=sdf.parse(this.formatDate(date2)).getTime();
		    long milisecond = Math.abs(d1-d2);
	        return milisecond/1000/60/60/24;
	}
	public Date addMinute(Date date, int minute)
	{
		    Calendar localCalendar = Calendar.getInstance(TimeZone.getDefault());
	        localCalendar.setTime(date);
	        localCalendar.add(Calendar.MINUTE, minute);
	        Date returndate = localCalendar.getTime();
	        return returndate;
	}
	public String formatDate(Date date)
	{
		    Calendar localCalendar = Calendar.getInstance();
	        localCalendar.setTime(date);
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        return sdf.format(localCalendar.getTime());       
	}
	
	 public boolean updateResetHistoryDate(int securityId, String resetHistoryDate)
	    {
	        SecurityDAO dao = new SecurityDAO();
	        return dao.updateResetHistoryDate(securityId, resetHistoryDate);
	    }
	 public boolean deleteSecurity()
	    {
	        SecurityDAO dao = new SecurityDAO();
	        return dao.deleteSecurity();
	    }
	

}
