package com.epam.bohdanov.controller.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import com.epam.bohdanov.controller.Path;
import com.epam.bohdanov.model.bean.ChatBean;
import com.epam.bohdanov.model.bean.MessageBean;
import com.epam.bohdanov.model.bean.Person;

@WebServlet("/admin/chat")
public class ChatServlet extends HttpServlet {
	private static final String QUERY_PARAM_CHAT_ID = "?chatID=";
	private static final Logger LOG = Logger.getLogger(ChatServlet.class);
	private Map<String, Session> userChatSessions;
	private Map<String, ChatBean> chatRooms;

	public void init(ServletConfig servletConfig) throws ServletException {
		LOG.trace("Chat servlet start");
		ServletContext context = servletConfig.getServletContext();
		userChatSessions = (Map<String, Session>) context.getAttribute("userChatSessions");
		chatRooms = (Map<String, ChatBean>) context.getAttribute("chatRooms");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chatID = request.getParameter("chatID");
		if (chatID != null) {
			ChatBean chatBean = chatRooms.get(chatID);
			chatBean.setNewMessage(false);
			request.setAttribute("chatRoom", chatBean);

			String forward = Path.CHAT_WINDOW_JSP;
			RequestDispatcher rd = request.getRequestDispatcher(forward);
			LOG.trace("Forward to : " + forward);
			rd.forward(request, response);
			return;
		}
		Map<ChatBean, String> links = getChatLinks();
		request.setAttribute("chatLinks", links);

		String forward = Path.CHAT_LIST_JSP;
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		LOG.trace("Forward to : " + forward);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chatID = request.getParameter("chatID");
		String message = request.getParameter("message");
		response.setContentType("application/json, charset=UTF-8");
		PrintWriter out = response.getWriter();
		JSONObject responseJSON = new JSONObject();

		if (chatID != null && message != null) {
			Session userSession = userChatSessions.get(chatID);
			userSession.getAsyncRemote().sendText(message);

			MessageBean messageBean = new MessageBean(Person.SYSTEM, message);
			chatRooms.get(chatID).getMessages().add(messageBean);

			responseJSON.put("result", "success");
		} else {
			responseJSON.put("result", "fail");
		}
		String json = responseJSON.toString();
		out.write(json);
		out.close();
	}

	private Map<ChatBean, String> getChatLinks() {
		Map<ChatBean, String> links = new HashMap<>();
		for (Entry<String, ChatBean> entry : chatRooms.entrySet()) {
			links.put(entry.getValue(), Path.CHAT_SERVLET + QUERY_PARAM_CHAT_ID + entry.getKey());
		}
		return links;
	}
}
