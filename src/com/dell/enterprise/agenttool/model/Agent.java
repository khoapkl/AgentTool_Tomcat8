package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.TimerTask;

/**
 * @author hungnguyen
 * 
 * @version $Id$
 **/
public class Agent implements Serializable
{

    /**
     * <p>
     * TODO Describe what this data member models and how it's used.
     * </p>
     **/

    private static final long serialVersionUID = 1L;
    private int agentId;
    private String shopperId;
    private int shopperNumber;
    private String userName;
    private String password;
    private boolean admin;
    private boolean userType;
    private String userLevel;
    private boolean active;
    private String agentName;
    private String agentEmail;
    private int totalRow;
    private boolean report; 
    private int loginCount;
	private Timestamp passwordDate;
	private Timestamp loginTime;
    

	public Timestamp getPasswordDate() {
		return passwordDate;
	}

	public void setPasswordDate(Timestamp passwordDate) {
		this.passwordDate = passwordDate;
	}
    
    public Timestamp getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Timestamp loginTime) {
		this.loginTime = loginTime;
	}


    
    public int getLoginCount() {
		return loginCount;
	}

	public void setLoginCount(int loginCount) {
		this.loginCount = loginCount;
	}

	public boolean isReport() {
		return report;
	}

	public void setReport(boolean report) {
		this.report = report;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}

	/**
     * @return the shopperId
     */
    public String getShopperId()
    {
        return shopperId;
    }

    /**
     * @param shopperId the shopperId to set
     */
    public void setShopperId(String shopperId)
    {
        this.shopperId = shopperId;
    }

    /**
     * @return the shopperNumber
     */
    public int getShopperNumber()
    {
        return shopperNumber;
    }

    /**
     * @param shopperNumber the shopperNumber to set
     */
    public void setShopperNumber(int shopperNumber)
    {
        this.shopperNumber = shopperNumber;
    }

    /**
     * @return the agent_id
     */
    public int getAgentId()
    {
        return agentId;
    }

    /**
     * @param agentId
     *            the agent_id to set
     */
    public void setAgentId(final int agentId)
    {
        this.agentId = agentId;
    }

    /**
     * @return the userName
     */
    public String getUserName()
    {
        return userName;
    }

    /**
     * @param userName
     *            the userName to set
     */
    public void setUserName(final String userName)
    {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password)
    {
        this.password = password;
    }

    /**
     * @return the admin
     */
    public boolean isAdmin()
    {
        return admin;
    }

    /**
     * @param admin
     *            the admin to set
     */
    public void setAdmin(final boolean admin)
    {
        this.admin = admin;
    }

    /**
     * @return the userType
     */
    public boolean isUserType()
    {
        return userType;
    }

    /**
     * @param userType
     *            the userType to set
     */
    public void setUserType(final boolean userType)
    {
        this.userType = userType;
    }

    /**
     * @return the userLevel
     */
    public String getUserLevel()
    {
        return userLevel;
    }

    /**
     * @param userLevel
     *            the userLevel to set
     */
    public void setUserLevel(final String userLevel)
    {
        this.userLevel = userLevel;
    }

    /**
     * @return the active
     */
    public boolean isActive()
    {
        return active;
    }

    /**
     * @param active
     *            the active to set
     */
    public void setActive(final boolean active)
    {
        this.active = active;
    }

    /**
     * @return the agentName
     */
    public String getAgentName()
    {
        return agentName;
    }

    /**
     * @param agentName the agentName to set
     */
    public void setAgentName(String agentName)
    {
        this.agentName = agentName;
    }

    /**
     * @return the agentEmail
     */
    public String getAgentEmail()
    {
        return agentEmail;
    }

    /**
     * @param agentEmail the agentEmail to set
     */
    public void setAgentEmail(String agentEmail)
    {
        this.agentEmail = agentEmail;
    }

}
