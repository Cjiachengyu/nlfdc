<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>已发通知</title>
<style>
.main_content {width: 100%; min-height: 600px; margin-top: 20px; }
#time {width: 200px; padding: 3px; font-size: 16px; border:1px solid #999;}
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span>
		<c:if test="${sessionScope.realUser.userRole == 9}">
			<span class="pad10 inline font_small" >当前位置： <a href="yjteachernotification">通知 </a> &gt; 发布通知 </span>
		</c:if>
		<c:if test="${sessionScope.realUser.userRole == 10}">
			<span class="pad10 inline font_small" >当前位置： <a href="yjmasternotification">通知 </a> &gt; 发布通知 </span>
		</c:if>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
		<div class="main_content">
            <%@ include file="../component/NotificationPublish.jsp"%>
		</div>
		<div class="clear"></div>
</div>

<c:if test="${sessionScope.realUser.userRole == 9}">
<script type="text/javascript">
var htmlVal = {
		htmlUrl: "yjteachernotification",
}
</script>
</c:if>

<c:if test="${sessionScope.realUser.userRole == 10}">
<script type="text/javascript">
var htmlVal = {
		htmlUrl: "yjmasternotification",
}
</script>
</c:if>

<%@ include file="../component/CommonBottom.jsp"%>
