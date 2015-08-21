<%@ page pageEncoding="utf-8" %>
<%@ include file="../component/CommonTopMobile.jsp" %>

<link href="css/yjteacher/mobileYjTeacherNotification.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet"/>

<style>
.switch_notification_type_box { position: fixed; top: 0; width: 100%; text-align: center; }
.container { width: 100%; }
</style>

<input type="hidden" id="show_type" value="${actionBean.showType }">
<div class="switch_notification_type_box">
	 <img id="switch_type_img" src="image/common/check_off.png">
</div>

<div class="container">
	<div id="get_notification_list" class="notification_list">
		
	</div>
	
	<div id="send_notification_list" class="notification_list">
		
	</div>
</div>


<script>
    var htmlVal = {
    		htmlUrl: "yjteachermobilenotification"
    	};

    
    $(function(){
		var showType = $("#show_type").val()
		if (showType == 'get')
		{
			$("#switch_type_img").attr("src", "image/common/check_on.png");
			$("#switch_type_img").attr("onclick", "switchType('send')");
			$("#get_notification_list").show();
			$("#send_notification_list").hide();
		}
		else
		{
			$("#switch_type_img").attr("src", "image/common/check_off.png");
			$("#switch_type_img").attr("onclick", "switchType('get')");
			$("#get_notification_list").hide();
			$("#send_notification_list").show();
		}
		
    });
</script>

<script src="js/yjteacher/mobileYjTeacherNotification.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
