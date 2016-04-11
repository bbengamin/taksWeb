package com.epam.bohdanov.controller.servlet.chat;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.epam.bohdanov.model.bean.ChatBean;
import com.epam.bohdanov.model.bean.MessageBean;
import com.epam.bohdanov.model.bean.Person;
import com.epam.bohdanov.service.DialogService;

@ServerEndpoint(value = "/chat", configurator = ChatHandlerConfigurator.class)
public class ChatHandler {
	private static final Logger LOG = Logger.getLogger(ChatHandler.class);
	private HttpSession httpSession;
	private Map<String, ChatBean> chatRooms;
	private JSONParser parser = new JSONParser();

	@SuppressWarnings("unchecked")
	@OnOpen
	public void onOpen(Session userSession, EndpointConfig config) {
		this.httpSession = (HttpSession) config.getUserProperties().get("httpSession");
		ServletContext context = httpSession.getServletContext();
		chatRooms = (Map<String, ChatBean>) context.getAttribute("chatRooms");

		if (config.getUserProperties().get("mainAdmin") != null) {
			context.setAttribute("mainAdmin", userSession);
		} else if (config.getUserProperties().get("roomID") != null) {
			String chatId = (String) config.getUserProperties().get("roomID");
			if (chatRooms.containsKey(chatId)) {
				cleanAdminSessions(chatRooms);
				chatRooms.get(chatId).setAdminSession(userSession);
			}
		} else {
			if (!chatRooms.containsKey(userSession.getId())) {
				ChatBean dialog = new ChatBean(userSession);
				chatRooms.put(userSession.getId(), dialog);
			}
			LOG.debug("New session - " + userSession.getId());
		}
	}

	@OnClose
	public void onClose(Session userSession) {
		if (chatRooms.containsKey(userSession.getId())) {
			ChatBean dialog = chatRooms.get(userSession.getId());
			dialog.setActive(false);
			/*Session adminSession = dialog.getAdminSession();*/

			DialogService dialogService = (DialogService) httpSession.getServletContext().getAttribute("dialogService");
			dialogService.addDialog(dialog);
			
			
			Session adminSession = (Session) httpSession.getServletContext().getAttribute("mainAdmin");
			if (adminSession != null) {
				JSONObject response = new JSONObject();
				response.put("destroy", userSession.getId());
				response.put("newID", dialog.getId());
				adminSession.getAsyncRemote().sendText(response.toJSONString());
			}
		}
		chatRooms.remove(userSession.getId());
		LOG.debug("Removed session - " + userSession.getId());
	}

	@OnMessage
	public void onMessage(String message, Session userSession) {

		LOG.debug("Message Received - " + message);
		try {
			JSONObject jsonMessage = (JSONObject) parser.parse(message);
			MessageBean messageBean = new MessageBean(Person.USER, (String) jsonMessage.get("message"));
			ChatBean chatBean = chatRooms.get(userSession.getId());
			chatBean.getMessages().add(messageBean);

			chatBean.setNewMessage(true);
			if (chatBean.getAdminSession() != null) {
				chatBean.getAdminSession().getAsyncRemote().sendText(message);
			}
			Session adminSession = (Session) httpSession.getServletContext().getAttribute("mainAdmin");
			if (adminSession != null) {
				adminSession.getAsyncRemote().sendText(userSession.getId());
			}
		} catch (ParseException e) {
			LOG.error(e.getMessage());
		}
	}

	private void cleanAdminSessions(Map<String, ChatBean> dialogs) {
		for (ChatBean dialog : dialogs.values()) {
			dialog.setAdminSession(null);
		}
	}
}