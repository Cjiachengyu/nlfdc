<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>

<title>查看通知</title>
<style>
.main_content {width: 100%; margin-bottom: 20px;}
.notification {width: 100%; min-height: 500px; text-align: center; padding: 20px 0px 10px 0px;}
.notification_content { text-align: justify; width: 80%; margin: auto; margin-top: 30px; }
.notification_info {text-align: left; padding: 10px; border-bottom: solid 1px #ccc; }
.notification_info span {display: inline-block; font-size: 12px; margin: 0 30px; color: #ccc; }
.operation_div {width: 80%; margin: auto;  }
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span>
		<c:if test="${sessionScope.realUser.userRole == 10}">
			<span class="pad10 inline font_small" >当前位置： <a href="yjmasternotification">通知 </a> &gt; 查看通知 </span>
		</c:if>
		<c:if test="${sessionScope.realUser.userRole == 9 }">
			<span class="pad10 inline font_small" >当前位置： <a href="yjteachernotification">通知 </a> &gt; 查看通知 </span>
		</c:if>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div id="content" class="clearfix">
		<div class="notification_info">
			<span >${actionBean.viewIngNotification.createTimeString }</span>
			<span >${actionBean.viewIngNotificationInfo.creatorName }</span>
			<span >${actionBean.viewIngNotificationInfo.notifyTargetString }</span>
			<span >已阅读${actionBean.viewIngNotificationInfo.readedCount }人</span>
			<span >共通知${actionBean.viewIngNotificationInfo.receiveCount }人</span>
			<div class="clear"></div>
		</div>
		<div class="notification">
			<h3>${actionBean.viewIngNotification.title }</h3>
			<div class="notification_content">
				${actionBean.viewIngNotification.content }
			</div>
		</div>
		<div class="operation_div">
	        <a class="right pink_button mar10" href="javascript:history.back();">返回</a>
	        <div class="clear"></div>
		</div>
	</div>
</div>

<%@ include file="../component/CommonBottom.jsp"%>
