<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>学校资源</title>
<style>
.main_content {float: left; width: 710px; padding: 10px 20px 0px 30px; }
#time {width: 200px; padding: 3px; font-size: 16px; border:1px solid #999;}
</style>

<div class="wrap">
	<div class="content clearfix">
		<div class="left_bar">
			<%@ include file="../component/SchemaCatalogue.jsp"%>
		</div>

		<div class="main_content">
            <div style="padding: 10px 0; ">
                <input id="time" class="Wdate right" type="text" name="birthdate" value="${actionBean.dateString }" readonly  onchange="htmlFn.selectDate(this.value)"/>
                <div class="clear"></div>
            </div>

            <div id="resource_list">
                <%@ include file="../component/ResourceListView.jsp"%>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<!-- 时间选择器 -->
<link rel="stylesheet" type="text/css" href="jqueryplugin/web-timepicker/css/jquery-ui.css" />
<link rel="stylesheet" type="text/css" href="jqueryplugin/web-timepicker/css/timepicker.css" />
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui.js"></script>
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui-slide.min.js"></script>
<script type="text/javascript" src="jqueryplugin/web-timepicker/js/jquery-ui-timepicker-addon.js"></script>
<script src="js/yjmaster/yjMasterResource.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
