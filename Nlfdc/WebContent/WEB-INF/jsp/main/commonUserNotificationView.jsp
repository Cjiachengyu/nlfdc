<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>查看通知</title>

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
.small_text { font-size: small; }
.normal_text { font-size: medium; }
.big_text { font-size: large; }
a {color: #2c65a9} 
</style>

<div class="wrap_main" >
	<div id="content" class="clearfix">
		<div class="left_bar2">
			<div class="left_link">
				<h4 style="text-align: left; padding-left: 20px; color: #3d6390;">${actionBean.menuSelector.currentFirstMenu.firstMenuName }</h4>
				
				<c:forEach var="secondMenu" items="${actionBean.menuSelector.currentFirstMenu.secondMenuList }">
					<div class="link">
						<img src="image/common/sanjiao_red_03.png" style="width: 6px; height: 6px;">
						<a class="second_menu_link" href="commonusernotificationmain?selectfirstandsecondmenu=&firstmenuid=${actionBean.menuSelector.currentFirstMenu.firstMenuId }&secondMenuId=${secondMenu.secondMenuId}">
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
					<a href="commonusernotificationmain?selectfirstandsecondmenu=&firstmenuid=${actionBean.menuSelector.currentFirstMenu.firstMenuId }&secondMenuId=${actionBean.menuSelector.currentSecondMenu.secondMenuId}" id="location_second_menu">
						${actionBean.menuSelector.currentSecondMenu.secondMenuName }
					</a>
			</div>
			<div class="location_tip">
					<span style="color: #ad6d21; display: inline-block; margin: 5px 0px 5px 20px; font-size: 16px; ">
						${actionBean.menuSelector.currentFirstMenu.firstMenuName }
					</span>
			</div>
			<div style="padding-bottom: 60px;">
				<h2 style="text-align: center;">${actionBean.viewIngNotification.title }</h2 >
				<span style="display: inline-block; width: 100%; text-align: center; ">发布时间： ${actionBean.viewIngNotification.createTimeString }
				&nbsp;|&nbsp;
				字号：【<a href="javascript:setFontSize(3)">大</a>&nbsp;<a href="javascript:setFontSize(2)">中</a>&nbsp;<a href="javascript:setFontSize(1)">小</a>】
				</span>
				<hr>
				<pre id="notification_content" class="normal_text">
					${actionBean.viewIngNotification.content }
				</pre>
				<!-- 
				 -->
				<hr style="margin-top: 100px; ">
				<div class="bottom_operation_div">
					<a href="#top" class="right">【返回顶部】</a>
					<a href="javascript:closeWindow();" class="right">【关闭窗口】</a>
					<a class="right">【打印本页】</a>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
function setFontSize(index)
{
	if (index == 1)
	{
		$("#notification_content").attr("class", "small_text");
	}
	else if (index == 2)
	{
		$("#notification_content").attr("class", "normal_text");
	}
	else if (index == 3)
	{
		$("#notification_content").attr("class", "big_text");
	}
}

function closeWindow()
{
	window.location.href="about:config";
	window.close(); 
}

</script>
<%@ include file="../component/CommonBottom.jsp"%>