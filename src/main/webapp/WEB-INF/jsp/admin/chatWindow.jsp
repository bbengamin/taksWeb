<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="charRoom">
    <div class="chat">
        <div class="message-wrapper">
			<div class='message text-left'><span class='from'>WE:</span><span class='text'>Hi! How are you?</span></div>						
        	<c:forEach var="message" items="${chatRoom.messages}" varStatus="loop">
				<c:choose>
					<c:when test="${message.from == 'USER'}">
            			<div class="message text-right"><span class="text">${message.message} </span><span class="from"> :USER</span></div>
					</c:when>
					<c:otherwise>
						<div class='message text-left'><span class='from'>WE:</span><span class='text'> ${message.message}</span></div>						
					</c:otherwise>
				</c:choose>
			</c:forEach>
        </div>
    </div>
    <c:if test="${chatRoom.active}">
	    <div class="writer" onload="connect();" onunload="disconnect();">
	        <textarea data-id="${chatId}" id='your-message' onkeypress="keypressHandler()" placeholder="Write you message here..."></textarea>
	        <div class="vert-line"></div>
	        <div class="writer-button " onclick="sendMessage();"></div>
	    </div>
	    <script>
		    chatClient = new WebSocket(endPointURL + "?roomID=${chatId}");
			chatClient.onmessage = function(event){
				console.log(event.data);
				var text = response.message;
				var newMessage = "<div class='message text-right'><span class='text'>" + text + "</span><span class='from'> :USER</span></div>";
				$('.message-wrapper').append(newMessage);
				/* var response = JSON.parse(event.data);
				if(response.message){
					var text = response.message;
					var newMessage = "<div class='message text-right'><span class='text'>" + text + "</span><span class='from'> :USER</span></div>";
					$('.message-wrapper').append(newMessage);
				}else{
					var newid = response.newID;					
					var destroy = response.destroy;
					
					$('textarea[data-id="' + destroy + '"]').parent().detach();
					$('a[data-room-id="' + destroy + '"]').detach();
					if($('.activeList li').length <= 0){
						$('.activeList').append('<span id="empty">No such dialogs</span>');
					}
					$('.historyList').append(
							"<a data-room-id="
							+ newid
							+ " data-href='chat?chatID="
							+ newid
							+ "&hist=true\'><li class='list-group-item'> Chat with userID: "
							+ newid + "</li></a>");
				} */
			 };
	    </script>
	</c:if>
</div>