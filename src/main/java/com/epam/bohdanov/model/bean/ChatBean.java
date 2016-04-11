package com.epam.bohdanov.model.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.Session;

import com.epam.bohdanov.model.entity.Entity;

public class ChatBean extends Entity {
	private List<MessageBean> messages;
	private boolean active;
	private boolean newMessage;
	private Session adminSession;
	private Session userSession;

	private Date date;

	public ChatBean() {
		super();
		active = true;
		messages = new ArrayList<>();
		newMessage = true;
		date = new Date();
	}

	public ChatBean(Session userSession) {
		this.userSession = userSession;
		active = true;
		messages = new ArrayList<>();
		newMessage = true;
		date = new Date();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isNewMessage() {
		return newMessage;
	}

	public void setNewMessage(boolean newMessage) {
		this.newMessage = newMessage;
	}

	public Session getAdminSession() {
		return adminSession;
	}

	public void setAdminSession(Session adminSession) {
		this.adminSession = adminSession;
	}

	public Session getUserSession() {
		return userSession;
	}

	public void setUserSession(Session userSession) {
		this.userSession = userSession;
	}

	public List<MessageBean> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageBean> messages) {
		this.messages = messages;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
