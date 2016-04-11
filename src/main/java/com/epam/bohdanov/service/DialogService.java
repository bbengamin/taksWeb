package com.epam.bohdanov.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.bohdanov.dao.interfaces.DialogDAO;
import com.epam.bohdanov.dao.interfaces.MessagesDAO;
import com.epam.bohdanov.model.bean.ChatBean;
import com.epam.bohdanov.service.transaction.TransactionManager;
import com.epam.bohdanov.service.transaction.TransactionOperation;

public class DialogService {

	private TransactionManager transactionManager;
	private DialogDAO dialogDAO;
	private MessagesDAO messagesDAO;

	public DialogService(TransactionManager transactionManager, DialogDAO dialogDAO, MessagesDAO messagesDAO) {
		super();
		this.transactionManager = transactionManager;
		this.dialogDAO = dialogDAO;
		this.messagesDAO = messagesDAO;
	}

	public boolean addDialog(ChatBean dialog) {
		return transactionManager.<Boolean> execute(new TransactionOperation<Boolean>() {
			public Boolean execute(Connection connection) throws SQLException {
				if (dialogDAO.addDialog(dialog, connection)) {
					if (messagesDAO.addMessages(dialog.getMessages(), dialog.getId(), connection)) {
						return true;
					}
				}
				return false;
			}
		});
	}

	public List<ChatBean> getAllDialogs() {
		return transactionManager.<List<ChatBean>> execute(new TransactionOperation<List<ChatBean>>() {
			public List<ChatBean> execute(Connection connection) throws SQLException {
				List<ChatBean> dialogs = dialogDAO.getAllDialogs(connection);
				for (ChatBean chatBean : dialogs) {
					chatBean.setMessages(messagesDAO.getMessagesByDialogId(chatBean.getId(), connection));
				}
				return dialogs;
			}
		});
	}

	public ChatBean getDialogById(int id) {
		return transactionManager.<ChatBean> execute(new TransactionOperation<ChatBean>() {
			public ChatBean execute(Connection connection) throws SQLException {
				ChatBean dialog = dialogDAO.getDialogById(id, connection);
				dialog.setMessages(messagesDAO.getMessagesByDialogId(id, connection));
				return dialog;
			}
		});
	}

}
