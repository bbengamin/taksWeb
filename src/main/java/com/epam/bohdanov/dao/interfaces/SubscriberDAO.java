package com.epam.bohdanov.dao.interfaces;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.bohdanov.model.entity.Subscriber;

public interface SubscriberDAO {
	public boolean addSubscriber(Subscriber subscriber, Connection connection) throws SQLException;
	
	public Subscriber getSubscriberByEmail(String email, Connection connection) throws SQLException;
	
	public Subscriber getSubscriberById(Integer id, Connection connection) throws SQLException;
	
	public List<Subscriber> getAllSubscribers(Connection connection) throws SQLException;
}
