<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 

<%@ include file="../component/CommonTop.jsp"%>

<title>班级管理</title>
<style>
.main_content {width: 100%; margin-bottom: 20px;}
.local_button { width: 120px; margin: 0 20px; }
.operation_div { margin: 15px 0 10px 0; display: block;}
.subjects { width: 150px; height: 30px; padding: 3px; font-size: 14px; }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" >当前位置：<a href="yjadminschoollistaction">园所管理</a> &gt; 班级管理：${actionBean.editingSchool.schoolName }</span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="clearfix">

        <div class="operation_div">
            <div class="right">
                    <a class="local_button pink_button" href="javascript:addClass()">添加班级</a>
            </div>
            <div class="right">
                    <a class="local_button pink_button" href="yjadminschoollistaction">返回</a>
            </div>
            <div class="clear"></div>
        </div>
		
		<div class="main_content" id="main_content">
			<%@ include file="YjSchoolClassListView.jsp"%>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript">
var htmlVal = {
		htmlUrl : "yjadminclassaction",
		htmlOutUrl: "yjadmineditcls",
	}
</script>

<script type="text/javascript" src="js/yjadmin/yjAdminSchoolManageClass.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
