<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>查看通知</title>

<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<style>
.wrap_main { width: 1080px; background-color: white; margin: auto;}
.main_content {float: left; width: 740px; margin-top: 15px; padding: 10px 20px 0px 30px; min-height: 670px; }
.left_bar2 { float: left; width: 246px; min-height: 610px; overflow-x: hidden; margin: 20px 0; }
.right_border_box { height: 610px; width: 16px; float: left; margin: 5px 0px; background-image: url(image/common/long_fenge_03.png);}
.left_link { height: 610px; width: 242px; margin: 5px 0px; }
.link { height: 24px; width: 200px; margin: auto; margin-top: 20px; border-bottom: solid 1px #c7c7c7; }
.second_menu_link { color: #2c65a9; }
.second_menu_link:hover { color: red; }
.location { height: 24px; margin-top: 5px; }
.location a { color: #2c65a9; }
.location a:hover { color: red; }
.location_tip { height: 30px; width: 100%; margin: 0; background-color: #d0e2f9; }
</style>

<div class="wrap_main" >
	<div id="content" class="clearfix">
		<div class="left_bar2">
			<div class="left_link">
				<h4 style="text-align: left; padding-left: 20px; color: #3d6390;">${actionBean.menuSelector.currentFirstMenu.firstMenuName }</h4>
				
				<c:forEach var="secondMenu" items="${actionBean.menuSelector.currentFirstMenu.secondMenuList }">
					<div class="link">
						<img src="image/common/sanjiao_red_03.png" style="width: 6px; height: 6px;">
						<a class="second_menu_link" href="javascript:selectSecondMenu(${secondMenu.secondMenuId }, '${secondMenu.secondMenuName }')">
							${secondMenu.secondMenuName }
						</a>
					</div>
				</c:forEach>
			</div>
		</div>	
		<div class="right_border_box"></div>
		
		<div class="main_content">
			<div class="location">
					<img src="image/common/location.gif" style="width: 10px; height: 10px;">
					<span>当前位置：</span>
					<a href="index">首页</a>
					>
					<a href="commonusernotificationmain?handlefirstmenu=&firstmenuid=${actionBean.menuSelector.currentFirstMenu.firstMenuId }">
						${actionBean.menuSelector.currentFirstMenu.firstMenuName }
					</a>
					>
					<a href="##" id="location_second_menu">
						${actionBean.menuSelector.currentSecondMenu.secondMenuName }
					</a>
			</div>
			<div class="location_tip">
					<span style="color: #ad6d21; display: inline-block; margin: 5px 0px 5px 20px; font-size: 16px; ">
						${actionBean.menuSelector.currentFirstMenu.firstMenuName }
					</span>
			</div>
			<div id="admin_nofication_list">
				<%@ include file="../component/AdminNotificationListView.jsp" %>
			</div>
		</div>
	</div>
</div>

<script>

function selectSecondMenu(secondMenuId, secondMenuName)
{
	$("#location_second_menu").html(secondMenuName);
	
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?selectsecondmenu=",
		data : {
			secondMenuId: secondMenuId
		},
		success : function(result) {
			isTimeOut(result);
			
			if (result != "not_change")
			{
				htmlFn.selectSecondMenuCallback(result);
			}
		}
	});

}

var htmlVal = {
	    htmlUrl: "commonusernotificationmain"
	};

	var htmlFn = {
			
		selectFirstMenuCallback: function(result)
		{
			$("#admin_nofication_list").html(result);
		},
		
		selectSecondMenuCallback: function(result)
		{
			$("#admin_nofication_list").html(result);
		},
		
	}
</script>
<%@ include file="../component/CommonBottom.jsp"%>