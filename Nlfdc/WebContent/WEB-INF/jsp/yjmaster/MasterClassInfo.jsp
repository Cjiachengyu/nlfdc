<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>班级信息</title>
<style>
.main_content {width: 100%; margin-bottom: 20px;}
.add_class { width: 100%; }
.add { width: 120px; height: 18px; margin: 10px 0; border-radius: 5px; padding: 4px; }
.operation_div { margin: 15px 0 10px 0; display: block;}
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" >当前位置：班级情况 </span>
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
			<%@ include file="ClassListView.jsp"%>
		</div>

        <div style="float: right; margin-bottom: 20px; ">
        	<a class="add pink_button" href="javascript:addClass();">添加班级</a>
        </div>

		<div class="clear"></div>
	</div>
</div>

<script src="js/yjmaster/yjMasterClassInfo.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
