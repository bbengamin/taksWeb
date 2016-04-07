// Websocket Endpoint url
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + "/chat";

var mainAdminConnection = null;

$(document)
		.ready(
				function() {
					mainAdminConnection = new WebSocket(endPointURL
							+ "?mainAdmin=true");
					mainAdminConnection.onmessage = function(event) {
						var text = event.data;
						if ($('.chatList a[data-room-id="' + text + '"]').length > 0) {
							$('.chatList a[data-room-id="' + text + '"] li')
									.addClass("new");
						} else {
							$('.chatList')
									.append(
											"<a data-room-id="
													+ text
													+ " data-href='chat?chatID="
													+ text
													+ "\'><li class='list-group-item new'> Chat with userID: "
													+ text + "</li></a>");
							
						}
					};
				});

$(window).unload(function() {
	mainAdminConnection.close();
});

function connect() {
	chatClient = new WebSocket(endPointURL);
	chatClient.onmessage = function(event) {
		var text = JSON.parse(event.data).message;
		var newMessage = "<div class='message text-right'><span class='text'>"
				+ text + "</span><span class='from'> :USER</span></div>";
		$('.message-wrapper').append(newMessage);
	};
}

function disconnect() {
	if (chatClient != null) {
		chatClient.close();
	}
}

var chatClient = null;

function keypressHandler() {
	if (event.which == 13) {
		event.preventDefault();
		sendMessage();
	}
}
function sendMessage() {
	var message = $('#your-message').val().trim();
	var chatID = $('#your-message').attr("data-id");
	if (message !== "") {
		$.ajax({
			type : "POST",
			url : window.location.href,
			data : "chatID=" + chatID + "&message=" + message,
			dataType : "json",
			success : function() {
				$('#your-message').val("");
				var newMessage = "<div class='message text-left'><span class='from'>WE:</span><span class='text'>"
						+ message + "</span></div>"
				$('.message-wrapper').append(newMessage);
				$('#your-message').focus();
				$('.message-wrapper').scrollTop(
				$('.message-wrapper')[0].scrollHeight);
				$(".chatList a[data-room-id='" + chatID + "']").find('li').removeClass("new");
			}
		});
	}
}

$(".activeChatRoom #your-message").keypress(function(event) {
	if (event.which == 13) {
		event.preventDefault();
		sendMessage();
	}
});

$(".chatList").on("click", "a", function() {
	disconnect();
	var href = $(this).attr('data-href');
	linkToRefresh = $(this).attr('data-href');
	var room_id = $(this).attr('data-room-id');
	$(this).find('li').removeClass("new");
	$(".activeChatRoom").load(href, function() {
		$('.message-wrapper').scrollTop($('.message-wrapper')[0].scrollHeight);
	});
});