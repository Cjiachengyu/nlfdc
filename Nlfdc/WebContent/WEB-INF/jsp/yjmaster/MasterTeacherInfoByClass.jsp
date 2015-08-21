<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>
<title>老师使用情况</title>
<style>
.main_content {width: 100%; margin-bottom: 20px;}
.operation_div { margin: 15px 0 10px 0; }
.subjects { width: 150px; height: 30px; padding: 3px; font-size: 14px; }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
        <a class="sub_menu_button font_bold bg_light_gray highlight" >按班级查看</a>
        <a class="sub_menu_button " href="yjmasterteacheraction?checkType=2" >按老师查看</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="clearfix">
        <div class="operation_div">
            <div class="left">
                <%@ include file="SubjectSelector.jsp"%>
            </div>
            <div class="clear"></div>
        </div>
	
		<div class="main_content" id="main_content">
			<%@ include file="TeacherListViewByClass.jsp"%>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script src="js/yjmaster/yjMasterTeacherInfo.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
