<%@page language="java" contentType="text/html; charset=TIS-620" pageEncoding="TIS-620"%>
<%@page import="com.extosoft.web.util.ETSConstant"%>
<%@page import="com.extosoft.web.bean.StudentResponse"%>
<%

	String userLogin = "";
	if(request.getSession().getAttribute(ETSConstant.SESSION_USER_LOGIN) != null){
		userLogin = (String) request.getSession().getAttribute(ETSConstant.SESSION_USER_LOGIN);
	}
	if(userLogin == null){
		userLogin = "";
	}

	String errorMessage = "";
	if(request.getAttribute(ETSConstant.SESSION_ERROR_MESSAGE) != null){
		errorMessage = (String) request.getAttribute(ETSConstant.SESSION_ERROR_MESSAGE);
	}
	
	if(errorMessage == null){
		errorMessage = "";
	}
	
	StudentResponse tmpStdResp = null;
	if(request.getSession().getAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_TMP) != null){
		tmpStdResp = (StudentResponse)request.getSession().getAttribute(ETSConstant.SESSION_DATA_ETS_RESULT_TMP);
	}
	
	
%>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="TIS-620">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Add Student | Extosoft Company</title>
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
    <!-- forms CSS
		============================================ -->
    <link rel="stylesheet" href="css/form/all-type-forms.css">
    <!-- dropzone CSS
		============================================ -->
    <link rel="stylesheet" href="css/dropzone/dropzone.css">
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
    <!-- Start Left menu area -->
    <div class="left-sidebar-pro">
        <nav id="sidebar" class="">
            <div class="sidebar-header">
                <a href="index.html"><img class="main-logo" src="img/logo/logo.png" alt="" /></a>
                <strong><a href="index.html"><img src="img/logo/logosn.png" alt="" /></a></strong>
            </div>
            <div class="left-custom-menu-adp-wrap comment-scrollbar">
                <nav class="sidebar-nav left-sidebar-menu-pro">
                    <ul class="metismenu" id="menu1">
                    	<li class="active">
                            <a class="has-arrow" href="./AllStudentsServlet" aria-expanded="false"><span class="educate-icon educate-student icon-wrap"></span> <span class="mini-click-non">Students</span></a>
                            <ul class="submenu-angle" aria-expanded="false">
                                <li><a title="All Students" href="./AllStudentsServlet"><span class="mini-sub-pro">All Students</span></a></li>
                                <li><a title="Add Student" href="./AddStudentServlet"><span class="mini-sub-pro">Add Student</span></a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </nav>
    </div>
    <!-- End Left menu area -->
    <!-- Start Welcome area -->
    <div class="all-content-wrapper">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                    <div class="logo-pro">
                        <a href="index.html"><img class="main-logo" src="img/logo/logo.png" alt="" /></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="header-advance-area">
            <div class="header-top-area">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="header-top-wraper">
                                <div class="row">
                                    <div class="col-lg-1 col-md-0 col-sm-1 col-xs-12">
                                        <div class="menu-switcher-pro">
                                            <button type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
													<i class="educate-icon educate-nav"></i>
												</button>
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-md-7 col-sm-6 col-xs-12">
                                        <div class="header-top-menu tabl-d-n">
                                            <ul class="nav navbar-nav mai-top-nav">
                                                <li class="nav-item"><a href="#" class="nav-link">Home</a>
                                                </li>
                                                
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-lg-5 col-md-5 col-sm-12 col-xs-12">
                                        <div class="header-right-info">
                                            <ul class="nav navbar-nav mai-top-nav header-right-menu">
                                                
                                                <li class="nav-item">
                                                    <a href="#" data-toggle="dropdown" role="button" aria-expanded="false" class="nav-link dropdown-toggle">
															<img src="img/product/pro4.jpg" alt="" />
															<span class="admin-name"><%=userLogin%></span>
															<i class="fa fa-angle-down edu-icon edu-down-arrow"></i>
														</a>
                                                    <ul role="menu" class="dropdown-header-top author-log dropdown-menu animated zoomIn">
                                                        <li><a href="./LoginServlet?mode=logout"><span class="edu-icon edu-locked author-log-ic"></span>Log Out</a>
                                                        </li>
                                                    </ul>
                                                </li>
                                                
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Mobile Menu start -->
            <div class="mobile-menu-area">
                <div class="container">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="mobile-menu">
                                <nav id="dropdown">
                                    <ul class="mobile-menu-nav">
                                        <li><a data-toggle="collapse" data-target="#demopro" href="#">Students <span class="admin-project-icon edu-icon edu-down-arrow"></span></a>
                                            <ul id="demopro" class="collapse dropdown-header-top">
                                                <li><a href="all-students.html">All Students</a>
                                                </li>
                                                <li><a href="add-student.html">Add Student</a>
                                                </li>
                                                <li><a href="edit-student.html">Edit Student</a>
                                                </li>
                                                <li><a href="student-profile.html">Student Profile</a>
                                                </li>
                                            </ul>
                                        </li>
                                    </ul>
                                </nav>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Mobile Menu end -->
            <div class="breadcome-area">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                            <div class="breadcome-list single-page-breadcome">
                                <div class="row">
                                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                        <div class="breadcome-heading">
                                            
                                        </div>
                                    </div>
                                    <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                        <ul class="breadcome-menu">
                                            <li><a href="#">Home</a> <span class="bread-slash">/</span>
                                            </li>
                                            <li><span class="bread-blod">Add Student</span>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Single pro tab review Start-->
        <div class="single-pro-review-area mt-t-30 mg-b-15">
            <div class="container-fluid">
            	<form action="./AddStudentServlet" name="formStudent" id="formStudent" method="post" enctype="multipart/form-data">
                <div class="row">
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="product-payment-inner-st">
                            <ul id="myTabedu1" class="tab-review-design">
                                <li class="active"><a href="#description">Basic Information</a></li>
                                <li><a href="#reviews"> Account Information</a></li>
                                <li><a href="#INFORMATION">Social Information</a></li>
                            </ul>
                            <div id="myTabContent" class="tab-content custom-product-edit">
                                
                                <div class="product-tab-list tab-pane fade active in" id="description">
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                            <div class="review-content-section">
                                                <div id="dropzone1" class="pro-ad">
                                                    
                                                        <div class="row">
                                                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                                                <div class="form-group">
                                                                    <input name="fullname" type="text" class="form-control" placeholder="Full Name" value="<%=(tmpStdResp != null && tmpStdResp.getFullName() != null && !"".equals(tmpStdResp.getFullName()))? tmpStdResp.getFullName() : ""%>">
                                                                </div>
                                                                <div class="form-group">
                                                                    <input name="address" type="text" class="form-control" placeholder="Address" value="<%=(tmpStdResp != null && tmpStdResp.getAddress() != null && !"".equals(tmpStdResp.getAddress()))? tmpStdResp.getAddress() : ""%>">
                                                                </div>
                                                                <div class="form-group">
                                                                    <input name="mobileno" type="text" class="form-control" placeholder="Mobile no." value="<%=(tmpStdResp != null && tmpStdResp.getMobileNo() != null && !"".equals(tmpStdResp.getMobileNo()))? tmpStdResp.getMobileNo() : ""%>">
                                                                </div>
                                                                <div class="form-group">
                                                                    <input name="finish" id="finish" type="text" class="form-control" placeholder="Date of Birth" value="<%=(tmpStdResp != null && tmpStdResp.getDateOfBirth() != null && !"".equals(tmpStdResp.getDateOfBirth()))? tmpStdResp.getDateOfBirth() : ""%>">
                                                                </div>
                                                                <div class="form-group">
                                                                    <input name="postcode" type="text" class="form-control" placeholder="Postcode" value="<%=(tmpStdResp != null && tmpStdResp.getPostCode() != null && !"".equals(tmpStdResp.getPostCode()))? tmpStdResp.getPostCode() : ""%>">
                                                                </div>
                                                                <div class="form-group alert-up-pd">
																	<div class="btn-group images-cropper-pro">
																		<label title="Upload image file" for="inputImage" class="btn btn-primary img-cropper-cp">
																			<input type="file" accept="image/*" name="file" id="inputImage"> Upload Image
																		</label>
																	</div>
                                                                </div>
                                                            </div>
                                                            <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                                            	<div class="form-group">
                                                                    <input name="department" type="text" class="form-control" placeholder="Department" value="<%=(tmpStdResp != null && tmpStdResp.getDepartment() != null && !"".equals(tmpStdResp.getDepartment()))? tmpStdResp.getDepartment() : ""%>">
                                                                </div>
                                                                <div class="form-group">
                                                                    <input name="age" type="number" class="form-control" placeholder="Age." value="<%=(tmpStdResp != null && tmpStdResp.getAge() > 0)? tmpStdResp.getAge() : ""%>">
                                                                </div>
                                                                <div class="form-group res-mg-t-15">
                                                                    <textarea name="description" placeholder="Description"><%=(tmpStdResp != null && tmpStdResp.getDescription() != null && !"".equals(tmpStdResp.getDescription()))? tmpStdResp.getDescription() : ""%></textarea>
                                                                </div>
                                                                <div class="form-group">
                                                                    <select name="gender" class="form-control">
																		<option value="none" selected="" disabled="">Select Gender</option>
																		<option value="M" <%if("M".equals((tmpStdResp != null && tmpStdResp.getGender() != null && !"".equals(tmpStdResp.getGender()))? tmpStdResp.getGender() : "")){out.print("selected");}%>>Male</option>
																		<option value="F" <%if("F".equals((tmpStdResp != null && tmpStdResp.getGender() != null && !"".equals(tmpStdResp.getGender()))? tmpStdResp.getGender() : "")){out.print("selected");}%>>Female</option>
																	</select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <select name="country" class="form-control">
																			<option value="none" selected="" disabled="">Select Country</option>
																			<option value="Thailand" <%if("Thailand".equals((tmpStdResp != null && tmpStdResp.getCountry() != null && !"".equals(tmpStdResp.getCountry()))? tmpStdResp.getCountry() : "")){out.print("selected");}%>>Thailand</option>
																	</select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <select name="city" class="form-control">
																			<option value="none" selected="" disabled="">Select City</option>
																			<option value="Bangkok" <%if("Bangkok".equals((tmpStdResp != null && tmpStdResp.getCity() != null && !"".equals(tmpStdResp.getCity()))? tmpStdResp.getCity() : "")){out.print("selected");}%>>Bangkok</option>
																	</select>
                                                                </div>
                                                                <div class="form-group">
                                                                    <input name="websiteURL" type="text" class="form-control" placeholder="Website URL" value="<%=(tmpStdResp != null && tmpStdResp.getWebsiteURL() != null && !"".equals(tmpStdResp.getWebsiteURL()))? tmpStdResp.getWebsiteURL() : ""%>">
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-lg-12">
                                                                <div class="payment-adress">
                                                                    <button type="submit" class="btn btn-primary waves-effect waves-light">Submit</button>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="product-tab-list tab-pane fade" id="reviews">
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                            <div class="review-content-section">
                                                <div class="row">
                                                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                                        <div class="devit-card-custom">
                                                            <div class="form-group">
                                                                <input type="text" name="email" class="form-control" placeholder="Email" value="<%=(tmpStdResp != null && tmpStdResp.getEmail() != null && !"".equals(tmpStdResp.getEmail()))? tmpStdResp.getEmail() : ""%>">
                                                            </div>
                                                            <div class="form-group">
                                                                <input type="text" name="phone" class="form-control" placeholder="Phone" value="<%=(tmpStdResp != null && tmpStdResp.getPhone() != null && !"".equals(tmpStdResp.getPhone()))? tmpStdResp.getPhone() : ""%>">
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="product-tab-list tab-pane fade" id="INFORMATION">
                                    <div class="row">
                                        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                                            <div class="review-content-section">
												<div class="row">
													<div class="col-lg-12">
														<div class="devit-card-custom">
															<div class="form-group">
																<input type="text" name="facebookURL" class="form-control" placeholder="Facebook URL" value="<%=(tmpStdResp != null && tmpStdResp.getFacebookURL() != null && !"".equals(tmpStdResp.getFacebookURL()))? tmpStdResp.getFacebookURL() : ""%>">
															</div>
															<div class="form-group">
																<input type="text" name="twitterURL" class="form-control" placeholder="Twitter URL" value="<%=(tmpStdResp != null && tmpStdResp.getTwitterURL() != null && !"".equals(tmpStdResp.getTwitterURL()))? tmpStdResp.getTwitterURL() : ""%>">
															</div>
															<div class="form-group">
																<input type="text" name="youtubeURL" class="form-control" placeholder="Youtube URL" value="<%=(tmpStdResp != null && tmpStdResp.getTwitterURL() != null && !"".equals(tmpStdResp.getTwitterURL()))? tmpStdResp.getYoutubeURL() : ""%>">
															</div>
														</div>
													</div>
												</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                </form>
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
                                       <p><b><%=errorMessage%></b></p>
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
                    <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
                        <div class="footer-copy-right">
                            <p>Copyright @ 2019. All rights reserved. Template by <a href="./LoginServlet">Extosoft Company</a></p>
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
    <!-- morrisjs JS
		============================================ -->
    <script src="js/sparkline/jquery.sparkline.min.js"></script>
    <script src="js/sparkline/jquery.charts-sparkline.js"></script>
    <!-- calendar JS
		============================================ -->
    <script src="js/calendar/moment.min.js"></script>
    <script src="js/calendar/fullcalendar.min.js"></script>
    <script src="js/calendar/fullcalendar-active.js"></script>
    <!-- maskedinput JS
		============================================ -->
    <script src="js/jquery.maskedinput.min.js"></script>
    <script src="js/masking-active.js"></script>
    <!-- datepicker JS
		============================================ -->
    <script src="js/datepicker/jquery-ui.min.js"></script>
    <script src="js/datepicker/datepicker-active.js"></script>
    <!-- form validate JS
		============================================ -->
    <script src="js/form-validation/jquery.form.min.js"></script>
    <script src="js/form-validation/jquery.validate.min.js"></script>
    <script src="js/form-validation/form-active.js"></script>
    <!-- dropzone JS
		============================================ -->
    <script src="js/dropzone/dropzone.js"></script>
    <!-- tab JS
		============================================ -->
    <script src="js/tab.js"></script>
    <!-- plugins JS
		============================================ -->
    <script src="js/plugins.js"></script>
    <!-- main JS
		============================================ -->
    <script src="js/main.js"></script>
    <!-- tawk chat JS
		============================================ -->
    <!--script src="js/tawk-chat.js"></script-->
    
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