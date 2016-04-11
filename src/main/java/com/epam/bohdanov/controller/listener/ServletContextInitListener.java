package com.epam.bohdanov.controller.listener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.epam.bohdanov.dao.impl.DialogDAOMySQL;
import com.epam.bohdanov.dao.impl.MessagesDAOMySQL;
import com.epam.bohdanov.dao.impl.SubscriberDAOMySQL;
import com.epam.bohdanov.dao.interfaces.DialogDAO;
import com.epam.bohdanov.dao.interfaces.MessagesDAO;
import com.epam.bohdanov.dao.interfaces.SubscriberDAO;
import com.epam.bohdanov.model.bean.ChatBean;
import com.epam.bohdanov.service.DialogService;
import com.epam.bohdanov.service.SubscribersService;
import com.epam.bohdanov.service.transaction.JdbcTransactionManager;
import com.epam.bohdanov.service.transaction.TransactionManager;
import com.epam.bohdanov.utils.DBUtils;

public class ServletContextInitListener implements ServletContextListener {
	private static final Logger LOG = Logger.getLogger(ServletContextInitListener.class);

	public void contextDestroyed(ServletContextEvent arg0) {
	}

	public void contextInitialized(ServletContextEvent arg0) {
		ServletContext context = arg0.getServletContext();
		initLog4J(context);
		initChat(context);

		DataSource dataSource = null;
		try {
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			dataSource = (DataSource) envContext.lookup("jdbc/taskWeb");
		} catch (NamingException e) {
			LOG.error("Cant get DataSource", e);
		}

		SubscriberDAO subscriberDAO = new SubscriberDAOMySQL();
		DialogDAO dialogDAO = new DialogDAOMySQL();
		MessagesDAO messagesDAO = new MessagesDAOMySQL();
		TransactionManager transactionManager = new JdbcTransactionManager(new DBUtils(dataSource));
		SubscribersService subscribersService = new SubscribersService(transactionManager, subscriberDAO);
		DialogService dialogService = new DialogService(transactionManager, dialogDAO, messagesDAO);

		context.setAttribute("subscribersService", subscribersService);
		context.setAttribute("dialogService", dialogService);
	}

	private void initChat(ServletContext context) {
		Map<String, Session> userChatSessions = Collections.synchronizedMap(new HashMap<>());
		Map<String, ChatBean> chatRooms = new HashMap<>();
		context.setAttribute("userChatSessions", userChatSessions);
		context.setAttribute("chatRooms", chatRooms);
	}

	/**
	 * Initializes log4j framework.
	 * 
	 * @param servletContext
	 */
	private void initLog4J(ServletContext servletContext) {
		log("Log4J initialization started");
		try {
			PropertyConfigurator.configure(servletContext.getRealPath("/WEB-INF/log4j.properties"));
			LOG.debug("Log4j has been initialized");
		} catch (Exception ex) {
			log("Cannot configure Log4j");
			ex.printStackTrace();
		}
		log("Log4J initialization finished");
	}

	private void log(String msg) {
		System.out.println("[ContextListener] " + msg);
	}
}
