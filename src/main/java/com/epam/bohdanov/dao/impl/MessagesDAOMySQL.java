package com.epam.bohdanov.dao.impl;

import static com.epam.bohdanov.dao.Fields.MESSAGE_DIALOG_ID;
import static com.epam.bohdanov.dao.Fields.MESSAGE_FROM;
import static com.epam.bohdanov.dao.Fields.MESSAGE_ID;
import static com.epam.bohdanov.dao.Fields.MESSAGE_TEXT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.epam.bohdanov.controller.exception.DataException;
import com.epam.bohdanov.dao.interfaces.MessagesDAO;
import com.epam.bohdanov.model.bean.MessageBean;
import com.epam.bohdanov.model.bean.Person;

public class MessagesDAOMySQL implements MessagesDAO {
	private final String CREATE_MESSAGE = "INSERT INTO `taskWeb`.`messages` (`dialog_id`, `text`, `from`) VALUES  (?, ?, ?);";
	private final String GET_MESSAGE_BY_ID = "SELECT * FROM `taskWeb`.`messages` where `message_id`=?;";
	private final String GET_MESSAGES_BY_DIALOG_ID = "SELECT * FROM `taskWeb`.`messages` where `dialog_id`=?;";

	@Override
	public boolean addMessages(List<MessageBean> messages, int dialogId, Connection connection) throws SQLException {
		boolean result = true;
		for (MessageBean messageBean : messages) {
			PreparedStatement prst = connection.prepareStatement(CREATE_MESSAGE, Statement.RETURN_GENERATED_KEYS);

			prst.setInt(1, dialogId);
			prst.setString(2, messageBean.getMessage());
			prst.setString(3, messageBean.getFrom().toString().toUpperCase());

			if (prst.executeUpdate() > 0) {
				ResultSet rs = prst.getGeneratedKeys();
				if (rs.next()) {
					messageBean.setId(rs.getInt(1));
					rs.close();
				}
				rs.close();
			} else {
				result = false;
			}
		}
		return result;
	}

	@Override
	public boolean addMessage(MessageBean message, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(CREATE_MESSAGE, Statement.RETURN_GENERATED_KEYS);

		prst.setInt(1, message.getDialogId());
		prst.setString(2, message.getMessage());
		prst.setString(3, message.getFrom().toString().toUpperCase());

		if (prst.executeUpdate() > 0) {
			ResultSet rs = prst.getGeneratedKeys();
			if (rs.next()) {
				message.setId(rs.getInt(1));
				rs.close();
				return true;
			}
			rs.close();
		}
		return false;
	}

	@Override
	public List<MessageBean> getMessagesByDialogId(int dialogId, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_MESSAGES_BY_DIALOG_ID);
		prst.setInt(1, dialogId);
		ResultSet rs = prst.executeQuery();
		List<MessageBean> messages = new ArrayList<>();
		while (rs.next()) {
			try {
				messages.add(extractMessage(rs));
			} catch (ParseException e) {
				new DataException(e);
			}
		}
		rs.close();
		return messages;
	}

	@Override
	public MessageBean getMessageById(int messageId, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_MESSAGE_BY_ID);
		prst.setInt(1, messageId);
		ResultSet rs = prst.executeQuery();
		MessageBean messageBean = null;
		if (rs.next()) {
			try {
				messageBean = extractMessage(rs);
			} catch (ParseException e) {
				new DataException(e);
			}
			rs.close();
		}
		return messageBean;

	}

	private MessageBean extractMessage(ResultSet rs) throws SQLException, ParseException {
		MessageBean message = new MessageBean();
		message.setId(rs.getInt(MESSAGE_ID));
		message.setMessage(rs.getString(MESSAGE_TEXT));
		message.setFrom(Person.valueOf(rs.getString(MESSAGE_FROM)));
		message.setDialogId(rs.getInt(MESSAGE_DIALOG_ID));
		return message;
	}

}
