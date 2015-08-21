<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8" autoFlush="true" buffer="300kb" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>

<html >
<head>

<style type="text/css">
span, li, a {font-size: 12px;}
a {text-decoration: none; }
body {margin: 0; background: url(image/commonTop/body_1px_02.png); }
.wrap { width: 1280px; margin: auto; background-image: url(image/commonTop/logo_05.png); }
.wrap2 { width: 1080px; margin: auto; }
.box_title {width: 100%; height: 37px; position: relative; }
.back { width: 100%; height: 214px; }
.head_links { width: 370px; height: 34px; float: right; margin: 0 100px; border: none; 
	background-image: url(image/commonTop/main_web.png);}
.head_links a { display: inline-block; height: 33px; width: 100px; text-align: center; color: white; margin-top: 8px;}
.head_links a:hover {color: red; }
.head_links span { border-right: solid 1px white; margin-top: 8px; }
.mar_left_20 { margin-left: 20px; }
.mar_right_20 { margin-right: 20px; }
.lead_links { width: 100%; height: 40px; background-color: #276ABB; border-radius: 5px; }
.lead_links a { display: inline-block; width: 9%; height:37px; margin: 0; padding-top: 12px; text-align:center; color: white;}
.lead_links a:hover {color: red; }
.tip_box { width: 100%; height: 40px; background-color: #458FCD; position: relative; }
.tip_box span { display: inline-block; color: white; margin: 12px 20px; margin-left: 30px; margin-right: 0; }
.content { width: 80%; min-height: 600px; }
.search_btn {float: right; display: inline-block; background-color: white; width: 60px; height:24px; margin: 8px; padding: 4px 0; text-align: center; }
.search_text { float: right; margin-top: 8px; height: 18px; }
</style>

</head>

<div class="wrap">
	<div class="box_title">
		<div class="head_links">
		 	<a class="mar_left_20" href="javascript:void(0)">设为首页</a>
		 	<span></span>
		 	<a href="javascript:void(0)">加入收藏</a>
		 	<span></span>
		 	<a class="mar_right_20" href="javascript:void(0)">网站导航</a>
		</div>
	</div>
	<div class="back">
	</div>
</div>

<div class="wrap2">
	<div class="lead_links">
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[0].firstMenuId }">
			${actionBean.allFirstMenu[0].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[1].firstMenuId }">
			${actionBean.allFirstMenu[1].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[2].firstMenuId }">
			${actionBean.allFirstMenu[2].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[3].firstMenuId }">
			${actionBean.allFirstMenu[3].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[4].firstMenuId }">
			${actionBean.allFirstMenu[4].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[5].firstMenuId }">
			<!-- 网上备案不需要二级页面，直接链接到外部系统，此处待修改 -->
			${actionBean.allFirstMenu[5].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[6].firstMenuId }">
			${actionBean.allFirstMenu[6].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[7].firstMenuId }">
			${actionBean.allFirstMenu[7].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[8].firstMenuId }">
			${actionBean.allFirstMenu[8].firstMenuName }
		</a>
		<a href="handlemenuaction?handlefirstmenu=&firstmenuid=${actionBean.allFirstMenu[9].firstMenuId }">
			${actionBean.allFirstMenu[9].firstMenuName }
		</a>
	</div>
	
	<div class="tip_box">
		<input type="button" id="search_btn" class="search_btn" value="搜索">		
		<input type="text" id="search_text" class="search_text">
	</div>
</div>

