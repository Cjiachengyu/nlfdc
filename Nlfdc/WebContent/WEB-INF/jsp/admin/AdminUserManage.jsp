<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonAdminTop.jsp" %>

<title>通知管理</title>
<style>
.main_content {float: left; width: 710px; margin-top: 15px; padding: 10px 20px 0px 30px; min-height: 750px; }
</style>
<script type="text/javascript">
var htmlVal = {
    htmlUrl: "adminusermanageaction"
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
	
}
</script>

<div class="bg_white simple_shadow">
    <div class="wrap">
        <span class="current_location"></span> 
	    <span class="pad10 inline font_small"> 当前位置：人员管理</span>
        <div class="clear"></div>
    </div>
</div>

<div class="wrap" >
	<div id="content" class="clearfix">
			<a class="blue_button right mar10" href="adminnotificationmanageaction?turntopublishpage=">发布通知</a>
			
			<div id="admin_list">
				<%@ include file="AdminListView.jsp" %>
			</div>
	</div>
</div>

<script>
	
function disableAdmin(adminId)
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url :"adminusermanageaction?disableadmin=",
		data : {
			adminId: adminId 
		},
		success : function(result) {
			isTimeOut(result);

			$("#admin_list").html(result);
		}
	});
}

function enableAdmin(adminId)
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url :"adminusermanageaction?enableadmin=",
		data : {
			adminId: adminId 
		},
		success : function(result) {
			isTimeOut(result);

			$("#admin_list").html(result);
		}
	});
}
</script>

<%@ include file="../component/CommonAdminBottom.jsp"%>