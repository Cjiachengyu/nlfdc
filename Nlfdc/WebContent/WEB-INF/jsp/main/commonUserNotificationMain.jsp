<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>查看通知</title>

<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<style>
.wrap_main { width: 1080px; background-color: white; }
.main_content {float: left; width: 710px; margin-top: 15px; padding: 10px 20px 0px 30px; min-height: 670px; }
</style>
<script type="text/javascript">
var htmlVal = {
    htmlUrl: "commonusernotificationmain"
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

<div class="wrap_main" >
	<div id="content" class="clearfix">
		<div class="left_bar">
			<%@ include file="../component/MenuSelector.jsp"%>
		</div>	
		
		<div class="main_content">
			<div id="admin_nofication_list">
				<%@ include file="../component/AdminNotificationListView.jsp" %>
			</div>
		</div>
	</div>
</div>

<%@ include file="../component/CommonBottom.jsp"%>