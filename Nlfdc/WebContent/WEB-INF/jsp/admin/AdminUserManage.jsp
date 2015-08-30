<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonAdminTop.jsp" %>

<title>人员管理</title>
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
			<a class="blue_button right mar10" href="adminusermanageaction?turntoaddadminpage=">添加管理员</a>
			
			<div id="admin_list">
				<%@ include file="AdminListView.jsp" %>
			</div>
	</div>
</div>

<script>

var htmlVal = {
	htmlUrl: "adminusermanageaction",
}

var editingAdminId;

function disableAdmin(adminId)
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?disableadmin=",
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
		url : htmlVal.htmlUrl + "?enableadmin=",
		data : {
			adminId: adminId 
		},
		success : function(result) {
			isTimeOut(result);

			$("#admin_list").html(result);
		}
	});
}

function setFirstMenu(adminId) {
	editingAdminId = adminId;
	
	createBorderMaskLayer("set_admin_privilege_form", "设置管理员权限", getLoading(), 680, 400);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getsetadminmenuview=",
	    data : {adminId: adminId},
		success : function(result) {
			isTimeOut(result);

			$("#set_admin_privilege_form").html(result);
		}
	});
}

function save_menus_changes() {
	var firstMenuIds = "";
	var checkFirstMenuIds = $(".check_menu_input");
	for (var i = 0; i < checkFirstMenuIds.length; i++) {
		if (checkFirstMenuIds[i].checked) {
			firstMenuIds+= checkFirstMenuIds[i].value + ",";
		}
	}
	;
	if (firstMenuIds == "") {
		firstMenuIds = "nochecked";
	}
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?saveadminmenus=",
		data : {
			firstMenuIds : firstMenuIds,
			adminId : editingAdminId
		},
		success : function(result) {
			isTimeOut(result);

			if (result == "ok") {
				refreshAdminList();
				closeAllLayers();
			}
			else if (result == "error") {
				AlertDialogWithCallback("设置管理员权限出现错误！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
			else if (result == "dataError") {
				AlertDialogWithCallback("数据出现错误，设置管理员权限失败！", null, function() {
					window.location.href = htmlVal.htmlUrl;
				});
			}
		}
	});
}

function refreshAdminList()
{
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getuserlistview=",
		success : function(result) {
			$("#admin_list").html(result);
		}
	});
}

function addNewMaster() {
	
	createBorderMaskLayer("add_admin_form", "添加管理员", getLoading(), 500, 300);
	$.ajax({
		contentType : "application/x-www-form-urlencoded; charset=utf-8",
		type : "post",
		url : htmlVal.htmlUrl + "?getaddadminview=",
		success : function(result) {
			isTimeOut(result);

			$("#add_admin_form").html(result);
		}
	});
}
</script>

<%@ include file="../component/CommonAdminBottom.jsp"%>