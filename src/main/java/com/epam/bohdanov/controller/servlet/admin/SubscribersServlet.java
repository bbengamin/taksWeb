package com.epam.bohdanov.controller.servlet.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.antlr.stringtemplate.StringTemplate;
import org.apache.log4j.Logger;

import com.epam.bohdanov.controller.Path;
import com.epam.bohdanov.model.bean.EmailInfoBean;
import com.epam.bohdanov.model.entity.Subscriber;
import com.epam.bohdanov.service.SubscribersService;
import com.epam.bohdanov.utils.EMailHelper;
import com.epam.bohdanov.utils.JSONResponseHelper;

@WebServlet("/admin/subscribers")
public class SubscribersServlet extends HttpServlet {
	private static final long serialVersionUID = 871058478288689980L;

	private static final Logger LOG = Logger.getLogger(SubscribersServlet.class);
	private String appPath;
	private SubscribersService subscribersService;

	public void init(ServletConfig servletConfig) throws ServletException {
		LOG.trace("SubscribersServlet servlet start");
		ServletContext context = servletConfig.getServletContext();

		appPath = context.getRealPath("");
		System.out.println(appPath);

		subscribersService = (SubscribersService) context.getAttribute("subscribersService");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Subscriber> subscribers = subscribersService.getAllSubscribers();

		request.setAttribute("subscribers", subscribers);

		String forward = Path.SUBSCIBERS_LIST_JSP;
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		LOG.trace("Forward to : " + forward);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String text = request.getParameter("text");
		String[] subscribers = request.getParameterValues("subscribers");
		String stringTemplate = getTemplate("delivery.html");
		StringTemplate mail = new StringTemplate(stringTemplate);
		mail.setAttribute("emailText", text);

		EMailHelper emailHelper = new EMailHelper();
		EmailInfoBean emailBean = new EmailInfoBean();
		emailBean.setTemplate(mail.toString());
		emailBean.setSubject("Vinni and CO Delivery");
		emailBean.setTo(Arrays.asList(subscribers));

		emailHelper.send(emailBean);
		JSONResponseHelper responseHelper = new JSONResponseHelper(response);
		responseHelper.sendResponse("result", "success");
	}

	private String getTemplate(String filename) {
		String fullFileName = appPath + "WEB-INF\\jsp\\admin\\templates\\" + filename;
		String stringTemplate = null;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fullFileName)));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}

			stringTemplate = sb.toString();
		} catch (IOException e) {
			LOG.error("Cant open template", e);
		}

		return stringTemplate;
	}

}
