<%@ page pageEncoding="utf-8"%>
<%@ include file="../component/CommonTop.jsp"%>

<title>修改密码</title>
<style>
.content { min-height: 600px; margin: 20px 0; padding: 40px 0; }
</style>

<script type="text/javascript">
    var htmlVal = { 
    		htmlUrl : "yjteachersettingaction" 
    	};
</script>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<a class="sub_menu_button " href="yjteachersettingaction?subMenuType=1" >用户信息</a>
		<a class="sub_menu_button font_bold bg_light_gray highlight" >修改密码</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div class="content simple_content_bg clearfix">
		<%@ include file="../component/UserEditPwd.jsp"%>
	</div>
</div>

<%@ include file="../component/CommonBottom.jsp"%>
