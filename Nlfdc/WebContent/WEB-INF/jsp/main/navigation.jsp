<%@ page pageEncoding="utf-8" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>宁陵县房地产发展保障管理中心</title>

<style>
span, li, a { font-size: 12px; }
.line_box {display: inline-block; }
.clear { clear: both; }
.hide {display: none; }
.right { float: right; }
.gray { color: grey; }
.main_content { width: 1080px; height: 645px; margin: auto; margin-top: 5px; background-color: white; position: relative; padding-top: 10px; }
.wrap_box { width: 1060px; height: 635px; margin: 0px 10px 10px 10px; border: solid 1px gray; position: relative; }
.left_bar { display: inline-block; width: 25%; min-height: 620px; margin: 5px 10px 5px 0px; position: relative; }
.right_content { display: inline-block; width: 72%; min-height: 620px; margin: 5px 10px 5px 0px; position: absolute; top: 5px; }
.right_border_box { height: 610px; width: 16px; float: right; margin: 5px 0px; background-image: url(image/common/long_fenge_03.png);}
.left_link { height: 610px; width: 240px; float: left; margin: 5px 0px; }
.link { height: 24px; width: 200px; margin: auto; margin-top: 20px; border-bottom: solid 1px #c7c7c7; }
.location { height: 24px; padding-left: 20px; margin-top: 20px; }
.location_tip { height: 30px; width: 100%; margin: 0; background-color: #d0e2f9; }
.detail_link { height: 530px; width: 100%; padding-left: 20px; }
.second_menu { display: inline-block; min-width: 80px; }
.second_menu:hover { color: red; }
</style>

<div class="main_content">
	<div class="wrap_box">
		<div class="left_bar">
			<div class="left_link">
				<!-- 
				<h4 style="text-align: center; color: #3d6390;"></h4>
				 -->
				
				<div class="link">
					<img src="image/common/sanjiao_red_03.png" style="width: 6px; height: 6px;">
					<a href="index?websitenavigation">网站导航</a>
				</div>
			</div>
			<div class="right_border_box"></div>
		</div>
		<div class="right_content">
			<div class="location">
					<img src="image/common/location.gif" style="width: 10px; height: 10px;">
					<span>当前位置：</span>
					<a href="index">首页</a>
					>
					<a href="index?websitenavigation">网站导航</a>
			</div>
			<div class="location_tip">
					<span style="color: #ad6d21; display: inline-block; margin: 5px 0px 5px 20px; font-size: 16px; ">网站导航</span>
			</div>
			
			<div class="detail_link">
				<c:forEach var="firstMenu" items="${actionBean.menuSelector.firstMenuList }">
					<c:if test="${firstMenu.firstMenuId != 1 }">
						<h4 style="margin: 0; margin-top: 15px; ">${firstMenu.firstMenuName }:</h4>
						<c:forEach var="secondMenu" items="${firstMenu.secondMenuList }">
							<a class="second_menu" href="commonusernotificationmain?selectfirstandsecondmenu=&firstmenuid=${firstMenu.firstMenuId }&secondMenuId=${secondMenu.secondMenuId}">
								${secondMenu.secondMenuName }
							</a>
						</c:forEach>				
					</c:if>
				</c:forEach>
			</div>
		</div>
	</div>
</div>


<%@ include file="../component/CommonBottom.jsp"%>
