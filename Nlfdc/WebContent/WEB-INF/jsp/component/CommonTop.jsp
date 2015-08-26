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
.tip_box { width: 100%; height: 36px; background-color: #458FCD; position: relative; }
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
		<c:forEach var="firstMenu" items="${actionBean.menuSelector.firstMenuList }">
			<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${firstMenu.firstMenuId }">
				${firstMenu.firstMenuName }
			</a>
		</c:forEach>
	</div>
	
	<div class="tip_box">
		<span>欢迎访问宁陵县房地产发展保障管理中心！</span>
		<span id="date_info"></span>
		<input type="button" id="search_btn" class="search_btn" value="搜索">		
		<input type="text" id="search_text" class="search_text">
	</div>
</div>

<script src='js/jquery-1.7.1.min.js' type='text/javascript' ></script>
<link rel="stylesheet" href="jqueryplugin/zebra_dialog/zebra_dialog.css" type="text/css" />
<script src="jqueryplugin/zebra_dialog/jquery.zebra_dialog.js" type="text/javascript"></script>

<script src='js/common/global.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
<script src="js/common/js.message.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<script type="text/javascript">
var date = new Date();
var year = date.getFullYear();
var month = date.getMonth();
var day = date.getDate();
var weekDay = date.getDay();

var weekDayStr = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];

var result = "今天是:"+year+"年"+month+"月"+day+"日 "+weekDayStr[weekDay];

$("#date_info").html(result);

</script>