<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonAdminTop.jsp"%>

<title>发布通知</title>
<style>
.main_content {float: left; width: 750px; margin-top: 15px; padding: 10px 0px 0px 10px; min-height: 750px; }
.notification { width: 100%; }
.notification_title_box { width: 93%; margin: 10px; margin: auto; }
.notification_tip1 { width: 93%; display: block; margin: auto; margin-bottom: 5px; font-size: 12px; color: red; }
.notification_tip { display: block; margin-bottom: 5px; font-size: 12px; color: red; }
.notification_title { width: 97%; height: 40px; padding: 2px 10px; font-size: 18px; }
.notification_content_box { width: 93%; margin: 10px; margin: auto; }
.content_box { width: 100%;}
.operation_div {width: 93%; margin: auto; margin-top: 20px; margin-bottom: 20px; } 
.operation_div a { width: 200px; }
.simple_shine {padding: 1px 10px; font-size: 13px; background: white; border: 1px solid rgb(190, 190, 190);	cursor: pointer; text-align: center; transition: box-shadow 0.5s ease-out;}
.simple_shine:hover {box-shadow: 0px 0px 15px #0099ff; }
.insert_file { float: right; margin: 4px; }
.insert_audio {	margin-right: 24px; }
.input_file { position: absolute; top: 0; left: 0; width: 158px; height: 32px; filter: alpha(opacity:0); opacity: 0;  }
</style>

<link href="jqueryplugin/umeditor/themes/default/css/umeditor.min.css?jscssimgversion=${actionBean.jsCssImgVersion}" type="text/css" rel="stylesheet">

<div id="content_editor_bar_append" style="display:none;" >
        <form id='content_image_form' enctype='multipart/form-data' method='post' target='iframe' class="relative right" >
        <a id='content_edit_insert_image' class='simple_shine insert_file' href='##'>插入图片</a>
        <input type='file' id='content_image' class="input_file" name='image' onchange='htmlFn.uploadImage("content")' /> </form>
</div>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span>
		<span class="pad10 inline font_small" >当前位置： <a href="adminnotificationmanageaction">通知管理 </a> &gt; 发布通知 </span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="clearfix">
		<div class="left_bar">
			<%@ include file="../component/MenuSelector.jsp"%>
		</div>	
		
		<div class="main_content">
			<div class="notification">
				<div class="notification_title_box">
					<span class="notification_tip">标题：</span>
					<input type="text" id="notification_title" class="notification_title" maxLength="50">
				</div>
				<div class="notification_content_box">
					<span class="notification_tip">内容：</span>
					<div class="notification_content" id="notification_content">
						<script type="text/plain" contentEditable="true" id="content_edit_div" class="content_box" ></script>
					</div>
				</div>
			</div>
		
			<div class="operation_div">
	       		<a class="right blue_button" id="publish_notification_btn" href="javascript:htmlFn.doPublishNotification();">发布通知</a>
	        	<div class="clear"></div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
var htmlVal = {
		htmlUrl: "adminnotificationmanageaction",
}
</script>
<script src="jqueryplugin/umeditor/umeditor.config.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="jqueryplugin/umeditor/umeditor.min.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="jqueryplugin/umeditor/lang/zh-cn/zh-cn.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<script src="js/common/jquery.form.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<script src="js/component/adminNotificationPublish.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
