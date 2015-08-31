<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonAdminTop.jsp" %>

<title>通知管理</title>
<style>
.main_content {float: left; width: 710px; margin-top: 15px; padding: 10px 20px 0px 30px; min-height: 750px; }
</style>
<script type="text/javascript">
var htmlVal = {
    htmlUrl: "adminnotificationmanageaction"
};

var htmlFn = {
		
	selectFirstMenuCallback: function(result)
	{
		$("#admin_nofication_list").html(result);
	},
	
	selectSecondMenuCallback: function(result)
	{
		$("#admin_nofication_list").html(result);
	},

	gotoPageCallback: function (result)
	{
		$("#admin_nofication_list").html(result);
	}
}
</script>

<div class="bg_white simple_shadow">
    <div class="wrap">
        <span class="current_location"></span> 
	    <span class="pad10 inline font_small"> 当前位置：通知管理</span>
        <div class="clear"></div>
    </div>
</div>

<div class="wrap" >
	<div id="content" class="clearfix">
		<div class="left_bar">
			<%@ include file="../component/MenuSelector.jsp"%>
		</div>	
		
		<div class="main_content">
			<a class="blue_button right mar10" href="adminnotificationmanageaction?turntopublishpage=">发布通知</a>
			<div class="clear"></div>
			<div id="admin_nofication_list">
				<%@ include file="../component/AdminNotificationListView.jsp" %>
			</div>
		</div>
	</div>
</div>

<%@ include file="../component/CommonAdminBottom.jsp"%>