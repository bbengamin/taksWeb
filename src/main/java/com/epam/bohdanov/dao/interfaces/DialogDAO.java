package com.epam.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.bohdanov.model.bean.ChatBean;

public interface DialogDAO {
	public boolean addDialog(ChatBean dialog, Connection connection) throws SQLException;

	public List<ChatBean> getAllDialogs(Connection connection) throws SQLException;

	public ChatBean getDialogById(int id, Connection connection) throws SQLException;
}
