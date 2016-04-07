package com.epam.bohdanov.controller.servlet.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.bohdanov.controller.Path;
import com.epam.bohdanov.model.entity.Subscriber;
import com.epam.bohdanov.service.SubscribersService;

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
		
	/*	InputStream fileTemplate = new ;
        String stringTemplate = EmailUtil.convertTemplate(fileTemplate);
        StringTemplate mail = new StringTemplate(stringTemplate);
        mail.setAttribute(TemplateConstants.TRAINING_TITLE, trainingRecordBean.getTitle());
        mail.setAttribute(TemplateConstants.TRAINING_ID, trainingRecordBean.getId());
        mail.setAttribute(TemplateConstants.TRAINING_START_DATE,  EmailUtil.formatDateTime(trainingRecordBean.getStartDate()));
        mail.setAttribute(TemplateConstants.TRAINING_LOCATION, trainingRecordBean.getLocation());
        mail.setAttribute(TemplateConstants.TRAINER_NAME, trainingRecordBean.getCoach());
        mail.setAttribute(TemplateConstants.IMAGES_HOST, imagesHost);
        return mail.toString();*/

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
