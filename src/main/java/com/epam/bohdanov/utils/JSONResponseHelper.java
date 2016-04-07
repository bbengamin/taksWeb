package com.epam.bohdanov.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public class JSONResponseHelper {
	private HttpServletResponse response;

	public JSONResponseHelper(HttpServletResponse response) {
		super();
		this.response = response;
	}

	public void sendResponse(Object key, Object value) throws IOException {
		if (!response.isCommitted()) {
			response.setContentType("application/json, charset=UTF-8");

			PrintWriter out = response.getWriter();
			JSONObject responseJSON = new JSONObject();

			responseJSON.put(key, value);

			String json = responseJSON.toString();
			out.write(json);
			out.close();
		}
	}
}
