<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>Admin</title>
    <meta name="description" content="Startups template">
    <meta name="keywords" content="Startups template">
    <link rel="shortcut icon" href="../img/favicon.ico">
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="../css/bootstrap.min.css" type="text/css" media="all" />
     <link rel="stylesheet" href="../css/style.css" type="text/css" media="all" />
    
    <!--[if lt IE 9]>
        <script src="../js/html5.js"></script>
        <script src="../js/respond.min.js"></script>
    <![endif]-->
</head>

<body>

    <nav class="navbar navbar-inverse">
        <ul class="nav navbar-nav"> 
      	    <li class="active"><a href="/taskWeb/admin/chat">Chat</a></li>
            <li><a href="/taskWeb/admin/subscribers">Subscribers</a></li>
        </ul>
    </nav>
    
    <div class="content">
    	<div class="col-sm-12">
	    	<div class="col-sm-6">
		    	<ul class="list-group chatList">
					<c:forEach var="chat" items="${chatLinks}" varStatus="loop">
						<a data-room-id="${chat.key.id}" data-href="${chat.value}"><li class="list-group-item ${chat.key.newMessage ? 'new' :'' }">
						  <!-- <span class="badge">14</span> -->
						  Chat with userID: ${chat.key.id}
						</li></a>
					</c:forEach>
				</ul>
	    	</div>
	    	<div class="col-sm-6 activeChatRoom">
	    	</div>
    	</div>
    </div>
    <!--[if lt IE 9]>
        <script type="text/javascript" src="../js/jquery-1.11.0.min.js?ver=1"></script>
    <![endif]-->  
    <!--[if (gte IE 9) | (!IE)]><!-->  
        <script type="text/javascript" src="../js/jquery-2.1.0.min.js?ver=1"></script>
    <!--<![endif]-->  
    
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
    <script type="text/javascript" src="../js/chat-admin.js"></script>
</body>
</html>