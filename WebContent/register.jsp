<%@page language="java" contentType="text/html; charset=TIS-620" pageEncoding="TIS-620"%>
<%@page import="com.extosoft.web.util.ETSConstant"%>
<%
	String errorMessage = (String) request.getAttribute(ETSConstant.SESSION_ERROR_MESSAGE);
	if(errorMessage == null){
		errorMessage = "";
	}
%>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="TIS-620">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Register | Extosoft Company</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- favicon
		============================================ -->
    <link rel="shortcut icon" type="image/x-icon" href="img/favicon.ico">
    <!-- Google Fonts
		============================================ -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:100,300,400,700,900" rel="stylesheet">
    <!-- Bootstrap CSS
		============================================ -->
    <link rel="stylesheet" href="css/bootstrap.min.css">
    <!-- Bootstrap CSS
		============================================ -->
    <link rel="stylesheet" href="css/font-awesome.min.css">
    <!-- owl.carousel CSS
		============================================ -->
    <link rel="stylesheet" href="css/owl.carousel.css">
    <link rel="stylesheet" href="css/owl.theme.css">
    <link rel="stylesheet" href="css/owl.transitions.css">
    <!-- animate CSS
		============================================ -->
    <link rel="stylesheet" href="css/animate.css">
    <!-- normalize CSS
		============================================ -->
    <link rel="stylesheet" href="css/normalize.css">
    <!-- meanmenu icon CSS
		============================================ -->
    <link rel="stylesheet" href="css/meanmenu.min.css">
    <!-- main CSS
		============================================ -->
    <link rel="stylesheet" href="css/main.css">
    <!-- educate icon CSS
		============================================ -->
    <link rel="stylesheet" href="css/educate-custon-icon.css">
    <!-- morrisjs CSS
		============================================ -->
    <link rel="stylesheet" href="css/morrisjs/morris.css">
    <!-- mCustomScrollbar CSS
		============================================ -->
    <link rel="stylesheet" href="css/scrollbar/jquery.mCustomScrollbar.min.css">
    <!-- metisMenu CSS
		============================================ -->
    <link rel="stylesheet" href="css/metisMenu/metisMenu.min.css">
    <link rel="stylesheet" href="css/metisMenu/metisMenu-vertical.css">
    <!-- calendar CSS
		============================================ -->
    <link rel="stylesheet" href="css/calendar/fullcalendar.min.css">
    <link rel="stylesheet" href="css/calendar/fullcalendar.print.min.css">
    <!-- modals CSS
		============================================ -->
    <link rel="stylesheet" href="css/modals.css">
    <!-- style CSS
		============================================ -->
    <link rel="stylesheet" href="style.css">
    <!-- responsive CSS
		============================================ -->
    <link rel="stylesheet" href="css/responsive.css">
    <!-- modernizr JS
		============================================ -->
    <script src="js/vendor/modernizr-2.8.3.min.js"></script>
    
    <script type="text/javascript">
		function showMessage(){
			$('#WarningModalalert').modal({
	            keyboard:true,
	            backdrop:'static',
	            show:true,
	            //remote:'../../ETS/pages/message.jsp'
	        });
	        return false;
		}
	</script>

</head>

<body>
    <!--[if lt IE 8]>
		<p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
	<![endif]-->
	<div class="error-pagewrap">
		<div class="error-page-int">
			<div class="text-center custom-login">
				<h3>Registration</h3>
				<p>This is the best extosoft company ever!</p>
			</div>
			<div class="content-error">
				<div class="hpanel">
                    <div class="panel-body">
                        <form action="./RegisterServlet" id="registerForm" method="post">
                            <div class="row">
                                <div class="form-group col-lg-12">
                                    <label>Username</label>
                                    <input type="text" name="username" id="username" class="form-control" value="admin">
                                </div>
                                <div class="form-group col-lg-6">
                                    <label>Password</label>
                                    <input type="password" name="password" id="password" class="form-control" value="1234">
                                </div>
                                <div class="form-group col-lg-6">
                                    <label>Repeat Password</label>
                                    <input type="password" name="repeatPassword" id="repeatPassword" class="form-control" value="1234">
                                </div>
                                <div class="form-group col-lg-6">
                                    <label>Email Address</label>
                                    <input type="text" name="emailAddress" id="emailAddress" class="form-control" value="administrator@gmail.com">
                                </div>
                                <div class="form-group col-lg-6">
                                    <label>Repeat Email Address</label>
                                    <input type="text" name="repeatEmailAddress" id="repeatEmailAddress" class="form-control" value="administrator@gmail.com">
                                </div>
                                <div class="checkbox col-lg-12">
                                    <input type="checkbox" name="ichecks" class="i-checks" value="1"> Please accept the terms.
                                </div>
                            
	                            <div class="text-center">
	                                <button class="btn btn-success loginbtn">Register</button>
	                            </div>
                            </div>
                        </form>
                        
                    </div>
                </div>
			</div>
			
			<!-- Modals Start-->
	        <div class="login-form-area edu-pd mg-b-15">
	            <div class="container-fluid">
	                <div class="row">
	                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
	                        <div id="WarningModalalert" class="modal modal-edu-general Customwidth-popup-WarningModal fade" role="dialog">
	                            <div class="modal-dialog">
	                                <div class="modal-content">
	                                    <div class="modal-close-area modal-close-df">
	                                        <a class="close" data-dismiss="modal" href="#"><i class="fa fa-close"></i></a>
	                                    </div>
	                                    <div class="modal-body">
	                                        <span class="educate-icon educate-warning modal-check-pro information-icon-pro"></span>
	                                        <h2>Message Box!</h2>
	                                        <p><%=errorMessage%></p>
	                                    </div>
	                                    <div class="modal-footer warning-md">
	                                        <a data-dismiss="modal" href="#">Close</a>
	                                    </div>
	                                </div>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	        <!-- Modals End-->
	        
	        <div class="footer-copyright-area">
	            <div class="container-fluid">
	                <div class="row">
	                    <div class="col-lg-12">
	                        <div class="footer-copy-right">
	                            <p>Copyright @ 2019. All rights reserved. Template by <a href="./LoginServlet">Extosoft Company</a></p>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
		</div>   
    </div>
    
    <!-- jquery
		============================================ -->
    <script src="js/vendor/jquery-1.12.4.min.js"></script>
    <!-- bootstrap JS
		============================================ -->
    <script src="js/bootstrap.min.js"></script>
    <!-- wow JS
		============================================ -->
    <script src="js/wow.min.js"></script>
    <!-- price-slider JS
		============================================ -->
    <script src="js/jquery-price-slider.js"></script>
    <!-- meanmenu JS
		============================================ -->
    <script src="js/jquery.meanmenu.js"></script>
    <!-- owl.carousel JS
		============================================ -->
    <script src="js/owl.carousel.min.js"></script>
    <!-- sticky JS
		============================================ -->
    <script src="js/jquery.sticky.js"></script>
    <!-- scrollUp JS
		============================================ -->
    <script src="js/jquery.scrollUp.min.js"></script>
    <!-- mCustomScrollbar JS
		============================================ -->
    <script src="js/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
    <script src="js/scrollbar/mCustomScrollbar-active.js"></script>
    <!-- metisMenu JS
		============================================ -->
    <script src="js/metisMenu/metisMenu.min.js"></script>
    <script src="js/metisMenu/metisMenu-active.js"></script>
    <!-- tab JS
		============================================ -->
    <script src="js/tab.js"></script>
    <!-- icheck JS
		============================================ -->
    <script src="js/icheck/icheck.min.js"></script>
    <script src="js/icheck/icheck-active.js"></script>
    <!-- plugins JS
		============================================ -->
    <script src="js/plugins.js"></script>
    <!-- main JS
		============================================ -->
    <script src="js/main.js"></script>
    <!-- tawk chat JS
		============================================ -->
    <!-- script src="js/tawk-chat.js"></script -->
    
    <script>
	$(document).ready(function(){
		var errorText = "<%=errorMessage%>";
		if(errorText != "" && errorText != null && errorText != "null"){
			showMessage();
		}
	});
	</script>

</body>

</html>