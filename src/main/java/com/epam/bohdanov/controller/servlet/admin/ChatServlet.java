package com.epam.bohdanov.controller.servlet.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

import com.epam.bohdanov.controller.Path;
import com.epam.bohdanov.model.bean.ChatBean;
import com.epam.bohdanov.model.bean.MessageBean;
import com.epam.bohdanov.model.bean.Person;
import com.epam.bohdanov.service.DialogService;
import com.epam.bohdanov.utils.JSONResponseHelper;

@WebServlet("/admin/chat")
public class ChatServlet extends HttpServlet {
	private static final String HIST_TRUE = "&hist=true";
	private static final long serialVersionUID = 411230504322723993L;
	private static final String QUERY_PARAM_CHAT_ID = "?chatID=";
	private static final Logger LOG = Logger.getLogger(ChatServlet.class);
	private Map<String, ChatBean> chatRooms;
	private DialogService dialogService;

	@SuppressWarnings("unchecked")
	public void init(ServletConfig servletConfig) throws ServletException {
		LOG.trace("Chat servlet start");
		ServletContext context = servletConfig.getServletContext();
		chatRooms = (Map<String, ChatBean>) context.getAttribute("chatRooms");
		dialogService = (DialogService) context.getAttribute("dialogService");
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chatID = request.getParameter("chatID");
		String history = request.getParameter("hist");
		if (chatID != null) {
			ChatBean chatBean = null;
			if (history != null) {
				chatBean = dialogService.getDialogById(Integer.parseInt(chatID));
			} else {
				chatBean = chatRooms.get(chatID);
			}
			chatBean.setNewMessage(false);
			request.setAttribute("chatRoom", chatBean);
			request.setAttribute("chatId", chatID);

			String forward = Path.CHAT_WINDOW_JSP;
			RequestDispatcher rd = request.getRequestDispatcher(forward);
			LOG.trace("Forward to : " + forward);
			rd.forward(request, response);
			return;
		}
		Map<ChatBean, String> links = getChatLinks();
		List<ChatBean> chatHistory = dialogService.getAllDialogs();
		Map<ChatBean, String> linksHistory = getChatHistoryLinks(chatHistory);
		request.setAttribute("chatLinks", links);
		request.setAttribute("chatHistoryLinks", linksHistory);

		String forward = Path.CHAT_LIST_JSP;
		RequestDispatcher rd = request.getRequestDispatcher(forward);
		LOG.trace("Forward to : " + forward);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chatID = request.getParameter("chatID");
		String message = request.getParameter("message");

		JSONResponseHelper responseHelper = new JSONResponseHelper(response);

		String result = "fail";
		if (chatID != null && message != null) {
			Session userSession = chatRooms.get(chatID).getUserSession();
			userSession.getAsyncRemote().sendText(message);

			MessageBean messageBean = new MessageBean(Person.SYSTEM, message);
			chatRooms.get(chatID).getMessages().add(messageBean);

			result = "success";
		}
		responseHelper.sendResponse("result", result);
	}

	private Map<ChatBean, String> getChatLinks() {
		Map<ChatBean, String> links = new HashMap<>();
		for (Entry<String, ChatBean> entry : chatRooms.entrySet()) {
			links.put(entry.getValue(), Path.CHAT_SERVLET + QUERY_PARAM_CHAT_ID + entry.getKey());
		}
		return links;
	}

	private Map<ChatBean, String> getChatHistoryLinks(List<ChatBean> chatHistory) {
		Map<ChatBean, String> links = new HashMap<>();
		for (ChatBean dialog : chatHistory) {
			links.put(dialog, Path.CHAT_SERVLET + QUERY_PARAM_CHAT_ID + dialog.getId() + HIST_TRUE);
		}
		return links;
	}
}
