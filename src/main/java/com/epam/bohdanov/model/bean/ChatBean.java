package com.epam.bohdanov.model.bean;

import java.util.ArrayList;
import java.util.List;

import javax.websocket.Session;

public class ChatBean {
	private String id;
	private List<MessageBean> messages;
	private boolean active;
	private boolean newMessage;
	private Session adminSession;

	public ChatBean(String id) {
		super();
		this.id = id;
		active = true;
		messages = new ArrayList<>();
		newMessage = false;
	}

	public boolean isNewMessage() {
		return newMessage;
	}

	public void setNewMessage(boolean newMessage) {
		this.newMessage = newMessage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Session getAdminSession() {
		return adminSession;
	}

	public void setAdminSession(Session adminSession) {
		this.adminSession = adminSession;
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
