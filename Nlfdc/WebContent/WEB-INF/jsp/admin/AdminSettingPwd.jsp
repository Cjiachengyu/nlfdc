<%@ page pageEncoding="utf-8"%>
<%@ include file="../component/CommonAdminTop.jsp"%>
<title>设置密码</title>
<style>
#content { min-height: 700px; margin: 20px 0; padding: 40px 0; }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<a class="sub_menu_button font_bold bg_light_gray highlight" >修改密码</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="simple_content_bg clearfix">
		<%@ include file="../component/UserEditPwd.jsp"%>
	</div>
</div>

<script type="text/javascript">
var htmlVal = { htmlUrl: "adminsettingaction" };
</script>

<%@ include file="../component/CommonBottom.jsp"%>
