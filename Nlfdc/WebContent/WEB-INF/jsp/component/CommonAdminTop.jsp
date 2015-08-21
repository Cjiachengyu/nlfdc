<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html style="background: white;">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
<link href="image/common/fenghuang.ico" type="image/x-icon" rel="shortcut icon" /> 

<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<link href="css/component/commonTop.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
    
<!--[if lt IE 9]><style>.shadow { border: solid 1px lightgray; }</style><![endif]-->
</head>
<body>

<c:if test="${sessionScope.loginUser != null}">
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<div id="update_broswer_tip" style="display: none;">
    <div style=" width: 100%; height: 50px; text-align: center; color: gray; line-height: 3em; position: relative; " >
		<h3 style="height: 50px; ">您的浏览器太旧了,请&nbsp<a href="http://browsehappy.com/" target="_blank">升级浏览器</a>&nbsp以便正常使用该网站。</h3>	
		<a style="position: absolute; right: 10px; bottom: -12px;" onclick="closeTip()"><img style="height: 15px; width: 15px;" src="image/common/closelabel.gif"></a>			
	</div>
</div>

<div class="top_root" >
	<!-- 用户信息设置，语言 -->
	<div style="height: 40px; background-color: #323436; ">
		<div class="wrap">
            <div >
            	<img style="  display: inline-block; width: 86px; height: 25px; float: left; margin: 8px; " src="image/yj/yj_logo.png">
            </div>

			<div class="right top_gray mar10">
				<!-- 用户名显示 -->
                <span class="top_font top_gray mar5">${sessionScope.realUser.userName}</span>

                <span class="top_sep" ></span>
				
				<!-- 用户操作 -->
				<div class="top_user_setting_icon"></div>
				<c:if test="${sessionScope.realUser.userRole == 0}">
					<a class="top_font top_gray mar5" href="yjteachersettingaction">帐户设置</a>
				</c:if>
				<c:if test="${sessionScope.realUser.userRole == 1}">
					<a class="top_font top_gray mar5" href="yjmastersettingaction">帐户设置</a>
				</c:if>
                
                <span class="top_sep" ></span>

                <!-- 退出 -->
                <a class="top_font top_highlight mar5" href="loginaction?signoutwithcookie">[退出]</a>
                
                <span style="margin-left: 5px; ">&nbsp;</span>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 校徽校训，主菜单操作 -->
	<div class="top_bg" >
		<div class="wrap relative" style="height: 130px;">
			<!-- 学校信息 -->
			<div style="padding: 20px 0 0 10px;">
				<img class="left white" style="border-radius: 50%;" 
						<c:if test="${sessionScope.school != null && sessionScope.school.schoolIcon != null && sessionScope.school.schoolIcon != ''}"> src="${sessionScope.school.schoolIconUrl}" </c:if>
						<c:if test="${sessionScope.school == null || sessionScope.school.schoolIcon == null || sessionScope.school.schoolIcon == ''}"> src="image/yj/fenghuangsmalllogo.png" </c:if>
						width="90" height="90">
				<div class="left" style="margin: 12px 20px;">
					<p class="school_header_color font_big font_yahei" style="padding: 0 0 5px 0">
                        <c:if test="${sessionScope.school != null && sessionScope.school.schoolName != null }">
                            ${sessionScope.school.schoolName }
                        </c:if>
					</p>
					<p class="school_header_color font_yahei">
						<c:if test="${sessionScope.school != null && sessionScope.school.schoolMotto != null }">
							${sessionScope.school.schoolMotto }
						</c:if>
					</p>
				</div>
				<div class="clear"></div>
			</div>

			<!-- 主菜单 -->
			<div class="absolute" style="right: 10px; bottom: 0px;">
				
				<!-- 幼教家长 -->
				<c:if test="${sessionScope.realUser.userRole == 8}">
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="##" >我的作业</a>
						<c:if test="${sessionScope.currentMemuOperation == '81'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '81'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
				</c:if>
				
				<!-- 幼教老师 -->
				<c:if test="${sessionScope.realUser.userRole == 9}">
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjteaasmaction" >我的任务</a>
						<c:if test="${sessionScope.currentMemuOperation == '91'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '91'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjteachernotification" >通知</a>
						<c:if test="${sessionScope.currentMemuOperation == '92'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '92'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjteares" >我的资源</a>
						<c:if test="${sessionScope.currentMemuOperation == '93'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '93'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
				</c:if>
				
				<!-- 幼教校长 -->
				<c:if test="${sessionScope.realUser.userRole == 10}">
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjmasterclassaction" >班级情况</a>
						<c:if test="${sessionScope.currentMemuOperation == '101'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '101'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjmasterteacheraction" >教师情况</a>
						<c:if test="${sessionScope.currentMemuOperation == '102'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '102'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjmasterresourceaction" >园本资源</a>
						<c:if test="${sessionScope.currentMemuOperation == '103'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '103'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjmasternotification" >通知</a>
						<c:if test="${sessionScope.currentMemuOperation == '104'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '104'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjmasterproblemmanageaction" >常见问题</a>
						<c:if test="${sessionScope.currentMemuOperation == '105'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '105'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
				</c:if>
				
				<!-- 幼教编辑 -->
				<c:if test="${sessionScope.realUser.userRole == 11}">
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjeditorresourcemanageaction" >资源中心</a>
						<c:if test="${sessionScope.currentMemuOperation == '111'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '111'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjadmintextbookaction" >课本管理</a>
						<c:if test="${sessionScope.currentMemuOperation == '112'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '112'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
				</c:if>
				
				<!-- 管理员操作 -->
				<c:if test="${sessionScope.realUser.userRole == 12}">
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjadminproblemmanageaction" >常见问题</a>
						<c:if test="${sessionScope.currentMemuOperation == '121'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '121'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjadminschoollistaction" >园所管理</a>
						<c:if test="${sessionScope.currentMemuOperation == '122'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '122'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjadminschemaaction" >大纲管理</a>
						<c:if test="${sessionScope.currentMemuOperation == '123'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '123'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjadmintextbookaction" >课本管理</a>
						<c:if test="${sessionScope.currentMemuOperation == '124'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '124'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
					<div class="top_main_menu_div">
						<a class="top_main_menu" href="yjeditorresourcemanageaction" >资源中心</a>
						<c:if test="${sessionScope.currentMemuOperation == '125'}"> <div class="current_main_menu" ></div> </c:if>
						<c:if test="${sessionScope.currentMemuOperation != '125'}"> <div class="inactive_main_menu" ></div> </c:if>
					</div>
				</c:if>
				
			</div>
		</div>
    </div>  
</div>

</fmt:bundle>
</c:if>

<div style="display: none;">
    <div id="lang" style="display: none;">${sessionScope.lang}</div>
    <div id="user_id" style="display: none;">${sessionScope.user.userId}</div>
    <div id="user_role" style="display: none;">${sessionScope.user.userRole}</div>
</div>

<script src='js/jquery-1.7.1.min.js' type='text/javascript' ></script>

<link rel="stylesheet" href="jqueryplugin/zebra_dialog/zebra_dialog.css" type="text/css" />
<script src="jqueryplugin/zebra_dialog/jquery.zebra_dialog.js" type="text/javascript"></script>

<script src='js/common/global.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
<script src="js/common/js.message.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
