package com.dell.enterprise.agenttool.model;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author hungnguyen
 * 
 * @version $Id$
 **/
public class Security implements Serializable
{ 
	private int securityId;
	private int lockoutTime;
    private int lockoutCount;
    private int expiryDays;
    private int charNumber;
    private int resetHistory;
    private boolean isUppercase;
    private boolean isNumber;
    private boolean isSymbol;
    private Timestamp resetHistoryDate;
    
    public int getLockoutTime() {
		return lockoutTime;
	}
	public void setLockoutTime(int lockoutTime) {
		this.lockoutTime = lockoutTime;
	}
	public int getLockoutCount() {
		return lockoutCount;
	}
	public void setLockoutCount(int lockoutCount) {
		this.lockoutCount = lockoutCount;
	}
	public int getExpiryDays() {
		return expiryDays;
	}
	public void setExpiryDays(int expiryDays) {
		this.expiryDays = expiryDays;
	}
	public int getCharNumber() {
		return charNumber;
	}
	public void setCharNumber(int charNumber) {
		this.charNumber = charNumber;
	}
	public int getResetHistory() {
		return resetHistory;
	}
	public void setResetHistory(int resetHistory) {
		this.resetHistory = resetHistory;
	}
	public boolean isUppercase() {
		return isUppercase;
	}
	public void setUppercase(boolean isUppercase) {
		this.isUppercase = isUppercase;
	}
	public boolean isNumber() {
		return isNumber;
	}
	public void setNumber(boolean isNumber) {
		this.isNumber = isNumber;
	}
	public boolean isSymbol() {
		return isSymbol;
	}
	public void setSymbol(boolean isSymbol) {
		this.isSymbol = isSymbol;
	}

	public Timestamp getResetHistoryDate() {
		return resetHistoryDate;
	}
	public void setResetHistoryDate(Timestamp resetHistoryDate) {
		this.resetHistoryDate = resetHistoryDate;
	}
	public int getSecurityId() {
		return securityId;
	}
	public void setSecurityId(int securityId) {
		this.securityId = securityId;
	}
	
    
 
}

