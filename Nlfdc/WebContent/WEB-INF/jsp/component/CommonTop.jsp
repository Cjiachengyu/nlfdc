<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8" autoFlush="true" buffer="300kb" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"  %>

<link href="image/common/blank.ico" type="image/x-icon" rel="shortcut icon" /> 

<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<head>

<style type="text/css">
span, li, a {font-size: 12px;}
a {text-decoration: none; }
body {margin: 0; background: white; }
.wrap1 { width: 1080px; margin: auto; background-image: url(image/commonTop/tempTop2.gif); }
.wrap2 { width: 1080px; margin: auto; }
.box_title {width: 100%; height: 37px; position: relative; }
.back { width: 100%; height: 214px; }
.head_links { width: 370px; height: 32px; float: right; margin: 0 100px; border: none; 
	background-image: url(image/commonTop/main_web.png);}
.head_links a { display: inline-block; height: 33px; width: 100px; text-align: center; color: white; margin-top: 8px;}
.head_links a:hover {color: red; }
.head_links span { border-right: solid 1px white; margin-top: 8px; }
.mar_left_20 { margin-left: 20px; }
.mar_right_20 { margin-right: 20px; }
.lead_links { width: 100%; height: 40px; background-color: #CC3300; border-bottom: solid 2px gray; position: relative; }
.selected_first_menu { border-bottom: solid 2px white; background-color: white; float: left; width: 9%; height:20px; padding: 10px 0px; 
			text-align:center; color: black; font-size: 14px; font-weight: bolder; }
.not_selected_frist_menu {float: left; width: 9%; height:20px; padding: 10px 0px; text-align:center; color: white; font-size: 14px; }
.lead_second_links {width: 100%; height: 50px; background-color: white; border-bottom: solid 2px gray;  }
.lead_second_links a { display: inline-block; width: 120px; color: black; text-align: center; margin: 18px 0 10px 0px; border-right: solid 1px black; }
.lead_second_links a:hover { color: red; }
.tip_box { width: 100%; height: 36px; background-color: #eaeaea; position: relative; }
.tip_box span { display: inline-block; color: black; margin: 12px 20px; margin-left: 30px; margin-right: 0; }
.content { width: 80%; min-height: 600px; }
.search_btn {float: right; display: inline-block; background-color: white; width: 60px; height:24px; margin: 8px; text-align: center; }
.search_text { float: right; margin-top: 8px; height: 18px; }
</style>

</head>

<div class="wrap1">
	<div class="box_title">
		<div class="head_links">
		 	<a class="mar_left_20" href="javascript:void(0);" onclick="SetHome(this,'');" >设为首页</a>
		 	<span></span>
		 	<a href="javascript:void(0);" onclick="AddFavorite('脚本之家','http://www.baidu.com')">加入收藏</a>
		 	<span></span>
		 	<a class="mar_right_20" href="index?websitenavigation">网站导航</a>
		</div>
	</div>
	<div class="back">
	</div>
</div>

<div class="wrap2">
	<div class="lead_links">
		<c:forEach var="firstMenu" items="${actionBean.menuSelector.firstMenuList }" varStatus="status">
			<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${firstMenu.firstMenuId }"
				id="first_menu_links_${firstMenu.firstMenuId }"
				<c:if test="${status.index == 0 }"> class="selected_first_menu"</c:if>
				<c:if test="${status.index != 0 }"> class="not_selected_frist_menu"</c:if>
				 onmouseover="selectFirstMenu(${firstMenu.firstMenuId })"
			>
				${firstMenu.firstMenuName }
			</a>
		</c:forEach>
	</div>
	<div class="lead_second_links">
		<c:forEach var="firstMenu" items="${actionBean.menuSelector.firstMenuList }" >
			<div id="second_menu_box_${firstMenu.firstMenuId }" class="hide">
				<c:forEach var="secondMenu" items="${firstMenu.secondMenuList }" >
					<a href="commonusernotificationmain?selectfirstandsecondmenu=&firstmenuid=${firstMenu.firstMenuId }&secondMenuId=${secondMenu.secondMenuId}" >
					${secondMenu.secondMenuName }</a>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
	
	<div class="tip_box">
		<span>欢迎访问宁陵县房地产管理局</span>
		<span id="date_info"></span>
		<input type="button" id="search_btn" class="search_btn" value="搜索" onclick="search()">		
		<input type="text" id="search_text" class="search_text" onkeydown="checkEnter(event)">
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
var month = date.getMonth() + 1;
var day = date.getDate();
var weekDay = date.getDay();

var weekDayStr = ["星期日","星期一","星期二","星期三","星期四","星期五","星期六"];

var result = "今天是:"+year+"年"+month+"月"+day+"日 "+weekDayStr[weekDay];

$("#date_info").html(result);


function SetHome(obj,url){
	  try{
	    obj.style.behavior='url(#default#homepage)';
	    obj.setHomePage(url);
	  }catch(e){
	    if(window.netscape){
	     try{
	       netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
	     }catch(e){
	       alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
	     }
	    }else{
	    alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【"+url+"】设置为首页。");
	    }
	 }
	}
	  
	//收藏本站 www.jb51.net
function AddFavorite(title, url) {
	 try {
	   window.external.addFavorite(url, title);
	 }
	catch (e) {
	   try {
	    window.sidebar.addPanel(title, url, "");
	  }
	   catch (e) {
	     alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请进入新网站后使用Ctrl+D进行添加");
	   }
	 }
}

function checkEnter(e)
{
	if (e.which == 13) {
		search();
	}	
}

function search()
{
	var searchText = $("#search_text").val();
	if (searchText == "")
	{
		$("#search_text").focus();
		return false;
	}
	window.location.href = "commonusersearch?searchnotification=&searchText="+searchText;
}

function selectFirstMenu(firstMenuId)
{
	$("#first_menu_links_"+firstMenuId).siblings().removeClass("selected_first_menu").attr("class", "not_selected_frist_menu");
	$("#first_menu_links_"+firstMenuId).attr("class", "selected_first_menu");
	$("#second_menu_box_"+firstMenuId).siblings().hide();
	$("#second_menu_box_"+firstMenuId).show();
}
</script>