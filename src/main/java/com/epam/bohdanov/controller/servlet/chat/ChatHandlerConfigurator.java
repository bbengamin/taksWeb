package com.epam.bohdanov.controller.servlet.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;

/**
 * ChatServerEndPointConfigurator
 * 
 * @author Jiji_Sasidharan
 */
public class ChatHandlerConfigurator extends Configurator {

	private static ChatHandler chatServer = new ChatHandler();

	@Override
	public void modifyHandshake(ServerEndpointConfig config, HandshakeRequest request, HandshakeResponse response) {
		HttpSession httpSession = (HttpSession) request.getHttpSession();
		config.getUserProperties().put("httpSession", httpSession);
		if(request.getParameterMap().get("roomID") != null){
			config.getUserProperties().put("roomID", request.getParameterMap().get("roomID").get(0));
		}
		if(request.getParameterMap().get("mainAdmin") != null){
			config.getUserProperties().put("mainAdmin", request.getParameterMap().get("mainAdmin").get(0));
		}
	}

	@Override
	public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
		return (T) chatServer;
	}
}