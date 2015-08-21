<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>作业回收站</title>
<style>
.main_content {width: 900px; padding: 10px 20px 0px 30px; margin: auto; min-height: 700px; }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
        <a class="sub_menu_button " href="yjteaasmaction" >任务列表</a>
	    <a class="sub_menu_button font_bold bg_light_gray highlight" >已删除</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="clearfix">
		<div class="main_content" id="assignment_list">
			<%@ include file="AsmListView.jsp"%>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript">
    var htmlVal = { 
    		htmlUrl: "yjteacherassignmentrecycleaction", 
    	};
</script>
<script src="js/yjteacher/yjTeacherAsmRecycle.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>

