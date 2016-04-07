package com.epam.bohdanov.controller.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.bohdanov.model.entity.Subscriber;
import com.epam.bohdanov.service.SubscribersService;
import com.epam.bohdanov.utils.JSONResponseHelper;

@WebServlet("/subscribe")
public class AddSubscriberServlet extends HttpServlet {
	private static final long serialVersionUID = -2361066145882030712L;
	private static final Logger LOG = Logger.getLogger(AddSubscriberServlet.class);
	
	private SubscribersService subscribersService;

	public void init(ServletConfig servletConfig) throws ServletException {
		LOG.trace("AddSubscriber servlet start");
		ServletContext context = servletConfig.getServletContext();

		subscribersService = (SubscribersService) context.getAttribute("subscribersService");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		JSONResponseHelper responseHelper = new JSONResponseHelper(response);

		boolean result = false;

		if (name != null && email != null) {
			Subscriber subscriber = new Subscriber();
			subscriber.setEmail(email);
			subscriber.setName(name);
			result = subscribersService.addSubscriber(subscriber);
		}

		responseHelper.sendResponse("result", result);
	}

}
