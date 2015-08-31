<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>查看通知</title>

<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<style>
.wrap_main { width: 1080px; background-color: white; margin: auto;}
.main_content { width: 90%; margin: auto; padding-top: 20px; }
.location { height: 24px; margin-top: 5px; }
.location a { color: #2c65a9; }
.location a:hover { color: red; }
.location_tip { height: 30px; width: 100%; margin: 0; background-color: #d0e2f9; }
</style>

<div class="wrap_main" >
	<div id="content" class="main_content">
			<div class="location">
					<img src="image/common/location.gif" style="width: 10px; height: 10px;">
					<span>当前位置：</span>
					<a href="index">首页</a>
					>
					<a href="##" id="location_second_menu">
						搜索	"${actionBean.searchText }"
					</a>
			</div>
			<div class="location_tip">
					<span style="color: #ad6d21; display: inline-block; margin: 5px 0px 5px 20px; font-size: 16px; ">
						搜索通知
					</span>
			</div>
			<div id="common_nofication_list">
				<%@ include file="../component/CommonNotificationListView.jsp" %>
			</div>
		</div>
</div>

<script type="text/javascript">

var htmlVal = {
	htmlUrl: "commonusersearch",
}
var htmlFn = {
		
	gotoPageCallback: function (result)
	{
		$("#common_nofication_list").html(result);
	}
}
</script>

<%@ include file="../component/CommonBottom.jsp"%>