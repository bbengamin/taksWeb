package com.epam.bohdanov.model.bean;

public class MessageBean {
	private Person from;
	private String message;

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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MessageBean [from=");
		builder.append(from);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}

}
