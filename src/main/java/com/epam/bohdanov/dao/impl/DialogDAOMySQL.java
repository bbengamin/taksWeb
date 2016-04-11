package com.epam.bohdanov.dao.impl;

import static com.epam.bohdanov.dao.Fields.DIALOG_DATE;
import static com.epam.bohdanov.dao.Fields.DIALOG_ID;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.epam.bohdanov.controller.exception.DataException;
import com.epam.bohdanov.dao.interfaces.DialogDAO;
import com.epam.bohdanov.model.bean.ChatBean;

public class DialogDAOMySQL implements DialogDAO {
	private final String CREATE_DIALOG = "INSERT INTO `taskWeb`.`dialogs` (`date`) VALUES  (?);";
	private final String GET_ALL_DIALOGS = "SELECT * FROM `taskWeb`.`dialogs`;";
	private final String GET_DIALOG_BY_ID = "SELECT * FROM `taskWeb`.`dialogs` where `dialog_id`=?;";

	@Override
	public boolean addDialog(ChatBean dialog, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(CREATE_DIALOG, Statement.RETURN_GENERATED_KEYS);
		prst.setDate(1, new java.sql.Date(dialog.getDate().getTime()));
		if (prst.executeUpdate() > 0) {
			ResultSet rs = prst.getGeneratedKeys();
			if (rs.next()) {
				dialog.setId(rs.getInt(1));
				rs.close();
				return true;
			}
			rs.close();
		}
		return false;
	}

	@Override
	public List<ChatBean> getAllDialogs(Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_ALL_DIALOGS);

		ResultSet rs = prst.executeQuery();
		List<ChatBean> dialogs = new ArrayList<>();
		while (rs.next()) {
			try {
				dialogs.add(extractDialog(rs));
			} catch (ParseException e) {
				new DataException(e);
			}
		}
		rs.close();
		return dialogs;
	}

	@Override
	public ChatBean getDialogById(int id, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_DIALOG_BY_ID);
		prst.setInt(1, id);
		ResultSet rs = prst.executeQuery();
		ChatBean dialog = null;
		if (rs.next()) {
			try {
				dialog = extractDialog(rs);
			} catch (ParseException e) {
				new DataException(e);
			}
			rs.close();
		}
		return dialog;
	}

	private ChatBean extractDialog(ResultSet rs) throws SQLException, ParseException {
		ChatBean chatBean = new ChatBean();

		chatBean.setActive(false);
		chatBean.setId(rs.getInt(DIALOG_ID));
		chatBean.setDate(rs.getDate(DIALOG_DATE));

		return chatBean;
	}

}
