package com.epam.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.bohdanov.model.bean.MessageBean;

public interface MessagesDAO {
	public boolean addMessages(List<MessageBean> messages, int dialogId, Connection connection) throws SQLException;
	
	public boolean addMessage(MessageBean message, Connection connection) throws SQLException;

	public List<MessageBean> getMessagesByDialogId(int dialogId, Connection connection) throws SQLException;
	
	public MessageBean getMessageById(int messageId, Connection connection) throws SQLException;
}
