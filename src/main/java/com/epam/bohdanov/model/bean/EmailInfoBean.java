package com.epam.bohdanov.model.bean;

import java.util.ArrayList;
import java.util.List;

public class EmailInfoBean {
	private List<String> to = new ArrayList<String>();
	private String subject;
	private String template;

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
}
