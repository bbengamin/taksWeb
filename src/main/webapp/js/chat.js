// Websocket Endpoint url
var host = window.location.host;
var path = window.location.pathname;
var webCtx = path.substring(0, path.indexOf('/', 1));
var endPointURL = "ws://" + window.location.host + webCtx + "/chat";

var chatClient = null;

function connect () {
    chatClient = new WebSocket(endPointURL);
    chatClient.onmessage = function(event){
    	var text = event.data;
    	var newMessage = "<div class='message text-right'><span class='text'>" + text + "</span><span class='from'> :WE</span></div>";
        $('.message-wrapper').append(newMessage);
        $('.message-wrapper').scrollTop($('.message-wrapper')[0].scrollHeight)
    };
}

function disconnect () {
    chatClient.close();
}

function sendMessage() {
	var message = $('#your-message').val().trim();
	if(message.length <= 0){
		return false;
	}
	
	if(chatClient == null){
		connect();
	}
    if (message !== "") {
        var jsonObj = {"message" : message};
        waitForSocketConnection(chatClient, function(){
        	chatClient.send(JSON.stringify(jsonObj));
        });
        $('#your-message').val("");
    }
    var newMessage = "<div class='message text-left'><span class='from'>YOU:</span><span class='text'>" + message + "</span></div>"
    $('.message-wrapper').append(newMessage);
    $('.message-wrapper').scrollTop($('.message-wrapper')[0].scrollHeight);
    $('#your-message').focus();
}

function waitForSocketConnection(socket, callback){
    setTimeout(
        function () {
            if (socket.readyState === 1) {
                console.log("Connection is made")
                if(callback != null){
                    callback();
                }
                return;

            } else {
                console.log("wait for connection...")
                waitForSocketConnection(socket, callback);
            }

        }, 5); 
}

$( "#your-message" ).keypress(function( event ) {
	  if ( event.which == 13 ) {
	     event.preventDefault();
	     sendMessage();
	  }
	});
$( window ).unload(function() {
	disconnect();
});

