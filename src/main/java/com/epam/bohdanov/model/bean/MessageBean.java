package com.epam.bohdanov.model.bean;

import com.epam.bohdanov.model.entity.Entity;

public class MessageBean extends Entity {
	private Person from;
	private String message;
	private int dialogId;

	public MessageBean() {
		super();
	}

	public MessageBean(Person from, String message) {
		super();
		this.from = from;
		this.message = message;
	}

	public Person getFrom() {
		return from;
	}

	public void setFrom(Person from) {
		this.from = from;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getDialogId() {
		return dialogId;
	}

	public void setDialogId(int dialogId) {
		this.dialogId = dialogId;
	}

}
