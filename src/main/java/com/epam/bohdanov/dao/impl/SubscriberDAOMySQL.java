package com.epam.bohdanov.dao.impl;

import static com.epam.bohdanov.dao.Fields.SUBSCRIBER_EMAIL;
import static com.epam.bohdanov.dao.Fields.SUBSCRIBER_ID;
import static com.epam.bohdanov.dao.Fields.SUBSCRIBER_NAME;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.epam.bohdanov.controller.exception.DataException;
import com.epam.bohdanov.dao.interfaces.SubscriberDAO;
import com.epam.bohdanov.model.entity.Subscriber;

public class SubscriberDAOMySQL implements SubscriberDAO {
	private final String CREATE_SUBSCRIBER = "INSERT INTO `taskWeb`.`subscribers` (`name`, `email`) VALUES  (?, ?);";
	private final String GET_SUBSCRIBER_BY_ID = "SELECT * FROM `taskWeb`.`subscribers` where `subscriber_id`=?;";
	private final String GET_ALL_SUBSCRIBERS = "SELECT * FROM `taskWeb`.`subscribers`;";
	private final String GET_SUBSCRIBER_BY_EMAIL = "SELECT * FROM `taskWeb`.`subscribers` where `email`=?;";

	@Override
	public boolean addSubscriber(Subscriber subscriber, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(CREATE_SUBSCRIBER, Statement.RETURN_GENERATED_KEYS);
		prst.setString(1, subscriber.getName());
		prst.setString(2, subscriber.getEmail());
		if (prst.executeUpdate() > 0) {
			ResultSet rs = prst.getGeneratedKeys();
			if (rs.next()) {
				subscriber.setId(rs.getInt(1));
				rs.close();
				return true;
			}
			rs.close();
		}
		return false;
	}

	@Override
	public Subscriber getSubscriberByEmail(String email, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_SUBSCRIBER_BY_EMAIL);
		prst.setString(1, email);
		ResultSet rs = prst.executeQuery();
		Subscriber subscriber = null;
		if (rs.next()) {
			try {
				subscriber = extractSubscriber(rs);
			} catch (ParseException e) {
				new DataException(e);
			}
			rs.close();
		}
		return subscriber;
	}

	@Override
	public Subscriber getSubscriberById(Integer id, Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_SUBSCRIBER_BY_ID);
		prst.setInt(1, id);
		ResultSet rs = prst.executeQuery();
		Subscriber subscriber = null;
		if (rs.next()) {
			try {
				subscriber = extractSubscriber(rs);
			} catch (ParseException e) {
				new DataException(e);
			}
			rs.close();
		}
		return subscriber;
	}

	private Subscriber extractSubscriber(ResultSet rs) throws SQLException, ParseException {
		Subscriber user = new Subscriber();
		user.setId(rs.getInt(SUBSCRIBER_ID));
		user.setName(rs.getString(SUBSCRIBER_NAME));
		user.setEmail(rs.getString(SUBSCRIBER_EMAIL));
		return user;
	}

	@Override
	public List<Subscriber> getAllSubscribers(Connection connection) throws SQLException {
		PreparedStatement prst = connection.prepareStatement(GET_ALL_SUBSCRIBERS);
		ResultSet rs = prst.executeQuery();
		List<Subscriber> subscribers = new ArrayList<>();
		while (rs.next()) {
			try {
				subscribers.add(extractSubscriber(rs));
			} catch (ParseException e) {
				new DataException(e);
			}
		}
		rs.close();
		return subscribers;
	}
}
