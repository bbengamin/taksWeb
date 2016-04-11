<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>Subscribers</title>
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
<div class='back'>
	<nav class="navbar navbar-inverse admin-nav">
        <ul class="nav navbar-nav"> 
           <li><a href="/taskWeb/admin/chat">Chat</a></li>
            <li class="active"><a href="/taskWeb/admin/subscribers">Subscribers</a></li>
        </ul>
    </nav>
<div class="admin-wrapp">
  <div class="admin-content">
    <div class="content">
   	<h2 class="center">Subscribers</h2>
   	 <form id="sub_form" class="form-horizontal">
	    	<div class="col-sm-6">
		    	<div class="col-sm-12 subs">
		    	
					<c:forEach var="subscriber" items="${subscribers}" varStatus="loop">
						 <div class="form-group">
							<input type="checkbox" value='${subscriber.email}' name='subscribers' id="sub${subscriber.id}" class="form-control sub-box">
							<label for="sub${subscriber.id}" class="control-label">${subscriber.name}</label>
						  </div>
					</c:forEach>
				
		    	</div>
	    	</div>
	    	<div class="col-sm-6 email-text">
	    		<textarea name='text' id='emailText'></textarea>
	    		<span class='loading'>Loading...</span>
	    		<button type="button" id='send_d' class="btn btn-primary active delivery">Send delivery</button>
	    	</div>
    	</form>
    </div>
    </div>
    </div>
</div>
<!-- <footer id="footer" class="footer light">
        <div class="copyright">&copy; 2016 EPAM Systems. Design by Ihor Bohdanov</div>
</footer> -->
    <!--[if lt IE 9]>
        <script type="text/javascript" src="../js/jquery-1.11.0.min.js?ver=1"></script>
    <![endif]-->  
    <!--[if (gte IE 9) | (!IE)]><!-->  
        <script type="text/javascript" src="../js/jquery-2.1.0.min.js?ver=1"></script>
    <!--<![endif]-->  
    
    <script type="text/javascript" src="../js/bootstrap.min.js"></script>
     <script src="../js/ckeditor/ckeditor.js"></script>
    <script>
	    CKEDITOR.editorConfig = function( config ) {
	    	config.language = 'ru';
	    	config.height = 300;
	    	config.toolbarCanCollapse = true;
	    };
	    CKEDITOR.replace( 'emailText' );
	    
	    $('#send_d').click(function(){
	    	 for ( instance in CKEDITOR.instances ){
    	        CKEDITOR.instances[instance].updateElement();
	    	 }
	    	
	    	 if(($('#sub_form input[type="checkbox"]:checked').length == 0) || $('#sub_form textarea').val() == ""){
	    		 alert("Виберите подписчиков и заполните текст письма");
	    		 return;
	    	 }
	    	$('.loading').show();
	    	$.ajax({
				type : "POST",
				url : "/taskWeb/admin/subscribers",
				data : $('#sub_form input[type="checkbox"]:checked, textarea'),
				dataType : "json",
				success : function() {
					$('#sub_form')[0].reset();
					for ( instance in CKEDITOR.instances ){
						CKEDITOR.instances[instance].setData('');
         		    }
					$('.loading').hide();
					alert("Рассылка отправлена!");
				}
			});
	    });
    </script>
</body>
</html>