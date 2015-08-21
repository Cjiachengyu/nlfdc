<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>任务列表</title>
<style>
.main_content {float: left; width: 710px; padding: 10px 20px 0px 30px; }
#assignment_list { margin-top: 10px;}
.local_button { margin: 20px 20px 5px 20px;}
</style>

<script>
    var htmlVal = { htmlUrl: "yjteaasmaction", };
    
    $(function(){
    	judgeBrowserVersion();
    });
</script>

<div class="bg_white simple_shadow">
    <div class="wrap">
        <a class="sub_menu_button font_bold bg_light_gray highlight" >任务列表</a>
        <a class="sub_menu_button " href="yjteacherassignmentrecycleaction" >已删除</a>
        <div class="clear"></div>
    </div>
</div>

<div class="wrap">
	<div class="content clearfix">
		<div class="left_bar">
			<%@ include file="../component/SchemaCatalogue.jsp"%>
		</div>

		<a class="pink_button local_button right" href="yjteacreateasmaction">创建任务</a>
		<div class="main_content" id="assignment_list">
			<%@ include file="AsmListView.jsp"%>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script src="js/yjteacher/yjTeacherAssignmentMain.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
