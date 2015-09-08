package com.dell.enterprise.agenttool.services;

import com.dell.enterprise.agenttool.DAO.HistoryPasswordDAO;

public class HistoryPasswordService {
	
	public boolean addHistoryPassword(String userName, String password)
	{
		 HistoryPasswordDAO dao = new HistoryPasswordDAO();
		 return dao.addHistoryPassword(userName, password);
	}
	public boolean getHistoryPassword(String userName, String password)
	{
		 HistoryPasswordDAO dao = new HistoryPasswordDAO();
		 return dao.getHistoryPassword(userName, password);
	}
	public boolean deleteHistoryPassword()
	{
		 HistoryPasswordDAO dao = new HistoryPasswordDAO();
		 return dao.deleteHistoryPassword();
	}
}
