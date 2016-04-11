<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <!--[if IE]><meta http-equiv="X-UA-Compatible" content="IE=edge"><![endif]-->
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" />
    <title>Home</title>
    <meta name="description" content="Startups template">
    <meta name="keywords" content="Startups template">
    <link rel="shortcut icon" href="img/favicon.ico">
    <link rel="apple-touch-icon" href="img/apple-touch-icon.jpg">
    <link rel="apple-touch-icon" sizes="72x72" href="img/apple-touch-icon-72x72.jpg">
    <link rel="apple-touch-icon" sizes="114x114" href="img/apple-touch-icon-114x114.jpg">
    <link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" media="all" />
    <link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" media="all" />
    <link rel="stylesheet" href="css/font-lineicons.css" type="text/css" media="all" />
    <link rel="stylesheet" href="css/animate.css" type="text/css" media="all" />
    <link rel="stylesheet" href="css/toastr.min.css" type="text/css" media="all" />
    <link rel="stylesheet" href="css/style.css" type="text/css" media="all" />

    <!--[if lt IE 9]>
        <script src="js/html5.js"></script>
        <script src="js/respond.min.js"></script>
    <![endif]-->
</head>

<body id="landing-page">
	
	<div class='alert-mes'>
		<span class='alert-mes-text icon icon-alerts-18'>Спасибо! Теперь вы подписаны на новости о нашем проекте!</span>
	</div>
	
    <!-- Preloader -->
    <div id="mask">
        <div id="loader"></div>
    </div>

    <header>
        <nav class="navigation navigation-header">
            <div class="container">
                <div class="navigation-brand">
                    <div class="brand-logo">
						<a href="/taskWeb" class="logo">Винни Пух и KO</a>
						<span class="sr-only">Винни Пух и все,. все все</span>
                    </div>
                    <button class="navigation-toggle visible-xs" type="button" data-toggle="dropdown" data-target=".navigation-navbar">
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                      <span class="icon-bar"></span>
                    </button>
                </div>
                <div class="navigation-navbar">
                    <ul class="navigation-bar navigation-bar-left">
                        <li class="active"><a href="#hero">Домой</a></li>
                        <li><a href="#about">О нас</a></li>
                        <li><a href="#process">Почему мы</a></li>
                        <li><a href="#support">Поддержка</a></li>
                    </ul>
                  <!--   <ul class="navigation-bar navigation-bar-right">
                        <li><a href="register.html">Login</a></li>
                        <li class="featured"><a href="register.html">Sign up</a></li>
                    </ul>   -->
                </div>
            </div>
        </nav>
    </header>

	<div id="hero" class="static-header register-version light clearfix">

        <div class="text-heading">
            <h1 class="animated hiding" data-animation="bounceInDown" data-delay="0">Винни Пух &mdash; <span class="highlight">бах бах</span> и в продакшн!</h1>
            <p class="animated hiding" data-animation="fadeInDown" data-delay="500">Я тучка, тучка, тучка,<br>
                                                                                    Я вовсе не медведь.<br>
                                                                                    Ах, как приятно тучке<br>
                                                                                    По небу лететь!<br>
                                                                                    &copy; Винни Пух</p>
        </div>

		<div class="container">
			<div class="signup-wrapper animated hiding" data-animation="bounceInUp" data-delay="0">
				<div class="row">
						<form class="form-inline form-register form-register-small" method="post">
							<div class="form-group">
								<input size="30" type="text" class="form-control required" name="name" required id="fullname" placeholder="Ваше имя">
							</div>

							<div class="form-group">
								<input size="45" type="email" class="form-control required email" required name="email" id="email" placeholder="Почта">
							</div>
							<div class="form-group submit-wrap">
								<input type="hidden" name="small-form"/>
								<button type="submit" class="btn btn-primary btn-md">Присоеденится к нам</button>
							</div>
						</form>
				</div>
			</div>
		</div>
		<!-- <a data-to="#about" class="arrow-down"></a> -->
    </div>

    <a id="showHere"></a>

	<section id="about" class="section dark">
        <div class="container">
            <h2>О НАШЕЙ <span class="highlight">КОМАНДЕ</span></h2>
            <div class="row">
                <div class="animated hiding" data-animation="fadeInLeft">
                    <img src="img/people/18_7p_orig.png" class="img-responsive centred-img" alt="process 3" />
                </div>
                <div class="col-sm-12">
                <p class="text-team">Программирование у нас в ДНК. С 1993 года мы помогаем мировым лидерам придумывать, проектировать, разрабатывать и внедрять программное обеспечение, которое изменяет мир. Сегодня мы уже больше, чем просто разработчики. Мы эксперты, которые помогают вывести ваш бизнес на новый уровень.</p>
                </div>
            </div>
            <!-- <a data-to="#process" class="arrow-down"></a> -->


			</div>
        </div>
    </section>

    <hr class="no-margin" />

    <section id="process" class="section dark">
        <div class="container">
            <div class="section-content row">


                <div class="col-sm-6 animated hiding" data-animation="fadeInLeft">
                    <img src="img/people/creative.gif" class="img-responsive" alt="process 3" />
                </div>
                <div class="col-sm-6 animated hiding" data-animation="fadeInRight">
					<br/><br/>
                    <article>
                        <h3>КРЕАТИВНЫЕ <span class="highlight">РЕШЕНИЯ</span></h3>
                        <div class="sub-title">Lorem ipsum dolor sit atmet sit dolor greand fdanrh<br/> sdfs sit atmet sit dolor greand fdanrh sdfs</div>
                        <p>In his igitur partibus duabus nihil erat, quod Zeno commuta rest gestiret. Sed virtutem ipsam inchoavit, nihil ampliusuma. Scien tiam pollicentur, quam non erat mirum sapientiae lorem cupido patria esse cariorem. Quae qui non vident, nihilamane umquam magnum ac cognitione.</p>
                    </article>
                </div>
                <hr class="clearfix" />

                <div class="col-sm-6 pull-right animated hiding" data-animation="fadeInRight">
                    <img src="img/people/teamwork.png" class="img-responsive" alt="process 2" />
                </div>
                <div class="col-sm-6 animated hiding" data-animation="fadeInLeft">
                    <br/><br/>
                    <article>
                        <h3>РАБОТА В <span class="highlight">КОМАНДЕ</span></h3>
                        <div class="sub-title">Lorem ipsum dolor sit atmet sit dolor greand fdanrh<br/> sdfs sit atmet sit dolor greand fdanrh sdfs</div>
                        <p>In his igitur partibus duabus nihil erat, quod Zeno commuta rest gestiret. Sed virtutem ipsam inchoavit, nihil ampliusuma. Scien tiam pollicentur, quam non erat mirum sapientiae lorem cupido patria esse cariorem. Quae qui non vident, nihilamane umquam magnum ac cognitione.</p>
                    </article>
                </div>
                <hr class="clearfix" />

                <div class="col-sm-6 animated hiding" data-animation="fadeInLeft">
                    <img src="img/people/help.jpg" class="img-responsive" alt="process 3" />
                </div>
                <div class="col-sm-6 animated hiding" data-animation="fadeInLeft">
                    <br/><br/>
                    <article>
                        <h3><span class="highlight">БЫСТРАЯ</span> ПОМОЩЬ</h3>
                        <div class="sub-title">Lorem ipsum dolor sit atmet sit dolor greand fdanrh<br/> sdfs sit atmet sit dolor greand fdanrh sdfs</div>
                        <p>In his igitur partibus duabus nihil erat, quod Zeno commuta rest gestiret. Sed virtutem ipsam inchoavit, nihil ampliusuma. Scien tiam pollicentur, quam non erat mirum sapientiae lorem cupido patria esse cariorem. Quae qui non vident, nihilamane umquam magnum ac cognitione.</p>
                    </article>
                </div>

            </div>
        </div>
    </section>

     <section id="support" class="section">
     <h2>ПООБЩАТЬСЯ С <span class="highlight">НАМИ</span></h2>
        <div class="container">
            <div class="chat">
                <div class="message-wrapper">
                    <div class="message text-right"><span class="text">Hi! How are you? </span><span class="from"> :WE</span></div>
                </div>
            </div>
            <div class="writer" onunload="disconnect();">
                <textarea id='your-message' placeholder="Write you message here..."></textarea>
                <div class="vert-line"></div>
                <div class="writer-button icon icon-office-05" onclick="sendMessage();"></div>
            </div>
        </div>
    </section>

    <footer id="footer" class="footer light">
        <div class="copyright">&copy; 2016 EPAM Systems. Design by Ihor Bohdanov</div>
    </footer>

    <div class="back-to-top"><i class="fa fa-angle-up fa-3x"></i></div>

    <!--[if lt IE 9]>
        <script type="text/javascript" src="js/jquery-1.11.0.min.js?ver=1"></script>
    <![endif]-->
    <!--[if (gte IE 9) | (!IE)]><!-->
        <script type="text/javascript" src="js/jquery-2.1.0.min.js?ver=1"></script>
    <!--<![endif]-->

    <script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js/jquery.flexslider-min.js"></script>
    <script type="text/javascript" src="js/jquery.nav.js"></script>
    <script type="text/javascript" src="js/jquery.appear.js"></script>
    <script type="text/javascript" src="js/jquery.plugin.js"></script>
    <script type="text/javascript" src="js/jquery.countdown.js"></script>
    <script type="text/javascript" src="js/waypoints.min.js"></script>
    <script type="text/javascript" src="js/waypoints-sticky.min.js"></script>
    <script type="text/javascript" src="js/jquery.validate.js"></script>
    <script type="text/javascript" src="js/toastr.min.js"></script>
    <script type="text/javascript" src="js/headhesive.min.js"></script>
    <script type="text/javascript" src="js/scripts.js"></script>
    <script type="text/javascript" src="js/chat.js"></script>
</body>
</html>