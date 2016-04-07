package com.epam.bohdanov.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.epam.bohdanov.dao.interfaces.SubscriberDAO;
import com.epam.bohdanov.model.entity.Subscriber;
import com.epam.bohdanov.service.transaction.TransactionManager;
import com.epam.bohdanov.service.transaction.TransactionOperation;

public class SubscribersService {

	private TransactionManager transactionManager;
	private SubscriberDAO subscriberDAO;

	public SubscribersService(TransactionManager transactionManager, SubscriberDAO subscriberDAO) {
		super();
		this.transactionManager = transactionManager;
		this.subscriberDAO = subscriberDAO;
	}

	public boolean addSubscriber(Subscriber subscriber) {
		return transactionManager.<Boolean> execute(new TransactionOperation<Boolean>() {
			public Boolean execute(Connection connection) throws SQLException {
				boolean result = false;
				if (subscriberDAO.getSubscriberByEmail(subscriber.getEmail(), connection) == null) {
					result = subscriberDAO.addSubscriber(subscriber, connection);
				}
				return result;
			}
		});
	}

	public List<Subscriber> getAllSubscribers() {
		return transactionManager.<List<Subscriber>> execute(new TransactionOperation<List<Subscriber>>() {
			public List<Subscriber> execute(Connection connection) throws SQLException {
				return subscriberDAO.getAllSubscribers(connection);
			}
		});
	}
}
