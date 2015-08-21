<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>已发通知</title>
<style>
.main_content {width: 100%; min-height: 600px; padding: 10px 20px 0px 30px; }
#time {width: 200px; padding: 3px; font-size: 16px; border:1px solid #999;}
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
        <a class="sub_menu_button font_bold bg_light_gray highlight" >已发通知</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
		<div class="main_content">
            <div >
                <a class="pink_button right mar10" href="javascript:htmlFn.turnToPublishPage();">发布通知</a>
                <input id="time" class="Wdate right mar10" style="height: 24px;" type="text" name="birthdate" value="${actionBean.dateString }"
                	 readonly  onchange="htmlFn.selectDate(this.value, 0)"/>
                <div class="clear"></div>
            </div>

            <div id="notification_list">
            	<%@ include file="../component/NotificationListView.jsp"%>
			</div>
		</div>
		<div class="clear"></div>
</div>

<script type="text/javascript">
var htmlVal = {
		htmlUrl : "yjmasternotification",
	};
</script>

<!-- 时间选择器 -->
<link rel="stylesheet" type="text/css" href="jqueryplugin/web-timepicker/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="jqueryplugin/web-timepicker/css/timepicker.css" />
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui.js"></script>
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui-slide.min.js"></script>
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui-timepicker-addon.js"></script>

<script src="js/component/yjCommonNotification.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
