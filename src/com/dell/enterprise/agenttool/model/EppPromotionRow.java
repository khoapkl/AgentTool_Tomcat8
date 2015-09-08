package com.dell.enterprise.agenttool.model;

import java.io.Serializable;
import java.sql.Date;

public class EppPromotionRow implements Serializable {
	private String epp_id;
	private String description;
	private Date start_date;
	private Date end_date;
	private int dfs_p_contrib;
	private int dfs_d_contrib;
	private int cor_p_contrib;
	private int cor_d_contrib;
	private int free_shipping;
	
	public String getEpp_id() {
		return epp_id;
	}
	public void setEpp_id(String eppId) {
		epp_id = eppId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getStart_date() {
		return start_date;
	}
	public void setStart_date(Date startDate) {
		start_date = startDate;
	}
	public Date getEnd_date() {
		return end_date;
	}
	public void setEnd_date(Date endDate) {
		end_date = endDate;
	}
	public int getDfs_p_contrib() {
		return dfs_p_contrib;
	}
	public void setDfs_p_contrib(int dfsPContrib) {
		dfs_p_contrib = dfsPContrib;
	}
	public int getDfs_d_contrib() {
		return dfs_d_contrib;
	}
	public void setDfs_d_contrib(int dfsDContrib) {
		dfs_d_contrib = dfsDContrib;
	}
	public int getCor_p_contrib() {
		return cor_p_contrib;
	}
	public void setCor_p_contrib(int corPContrib) {
		cor_p_contrib = corPContrib;
	}
	public int getCor_d_contrib() {
		return cor_d_contrib;
	}
	public void setCor_d_contrib(int corDContrib) {
		cor_d_contrib = corDContrib;
	}
	public int getFree_shipping() {
		return free_shipping;
	}
	public void setFree_shipping(int freeShipping) {
		free_shipping = freeShipping;
	}

}
