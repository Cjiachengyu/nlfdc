<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html style="background: white;">
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />

<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<link href="css/component/commonTop.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
    
<!--[if lt IE 9]><style>.shadow { border: solid 1px lightgray; }</style><![endif]-->
</head>
<body>

<div class="top_root" >
	<!-- 用户信息设置，语言 -->
	<div style="height: 40px; background-color: #323436; ">
		<div class="wrap">

			<div class="right top_gray mar10">
				<!-- 用户名显示 -->
                <span class="top_font top_gray mar5">${sessionScope.admin.adminName }</span>

                <span class="top_sep" ></span>
				
				<!-- 用户操作 -->
				<div class="top_user_setting_icon"></div>
				<a class="top_font top_gray mar5" href="adminsettingaction">帐户设置</a>
                
                <span class="top_sep" ></span>

                <!-- 退出 -->
                <a class="top_font top_highlight mar5" href="loginaction?yjsignout">[退出]</a>
                
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
				<div class="left" style="margin: 12px 20px;">
					<p class="school_header_color font_big font_yahei" style="padding: 0 0 5px 0">
                           	宁陵县房地产发展保障管理中心
					</p>
				</div>
				<div class="clear"></div>
			</div>

			<!-- 主菜单 -->
			<div class="absolute" style="right: 10px; bottom: -8px;">
				
				<!-- 幼教家长 -->
				<div class="top_main_menu_div">
					<a class="top_main_menu" href="adminnotificationmanageaction" >通知管理</a>
					<c:if test="${sessionScope.currentMemuOperation == '01'}"> <div class="current_main_menu" ></div> </c:if>
					<c:if test="${sessionScope.currentMemuOperation != '01'}"> <div class="inactive_main_menu" ></div> </c:if>
				</div>
				<c:if test="${sessionScope.admin.adminRole == 0}">
					<a class="top_main_menu" href="##" >人员管理</a>
					<c:if test="${sessionScope.currentMemuOperation == '02'}"> <div class="current_main_menu" ></div> </c:if>
					<c:if test="${sessionScope.currentMemuOperation != '02'}"> <div class="inactive_main_menu" ></div> </c:if>
				</c:if>
				
			
			</div>
		</div>
    </div>  
</div>


<div style="display: none;">
    <div id="lang" style="display: none;">${sessionScope.lang}</div>
    <div id="user_id" style="display: none;">${sessionScope.admin.adminId }</div>
    <div id="user_role" style="display: none;">${sessionScope.admin.adminRole }</div>
</div>

<script src='js/jquery-1.7.1.min.js' type='text/javascript' ></script>

<link rel="stylesheet" href="jqueryplugin/zebra_dialog/zebra_dialog.css" type="text/css" />
<script src="jqueryplugin/zebra_dialog/jquery.zebra_dialog.js" type="text/javascript"></script>

<script src='js/common/global.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
<script src="js/common/js.message.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
