<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonAdminTop.jsp" %>

<title>查看通知</title>

<style>
.wrap_main { width: 1080px; background-color: white; margin: auto;}
.main_content {float: left; width: 740px; margin-top: 15px; padding: 10px 20px 0px 30px; min-height: 670px; }
a {color: #2c65a9} 
.notification { width: 80%; margin: auto; margin-top: 20px; min-height: 600px; }
.back_btn { float: right; margin-right: 10%; }
</style>

<div class="wrap_main" >
	<div id="content" class="clearfix">
			<div class="notification">
				<h2 style="text-align: center; font-size: 18px; margin: 10px; ">${actionBean.viewIngNotification.title }</h2 >
				<span style="display: inline-block; width: 100%; text-align: center; ">发布时间： ${actionBean.viewIngNotification.createTimeString }
				&nbsp;|&nbsp;
				字号：【<a href="javascript:setFontSize(3)">大</a>&nbsp;<a href="javascript:setFontSize(2)">中</a>&nbsp;<a href="javascript:setFontSize(1)">小</a>】
				</span>
				<hr>
				<pre id="notification_content" class="normal_text">
					${actionBean.viewIngNotification.content }
				</pre>
			</div>
			
			<a class="blue_button back_btn" href="javascript:history.back();">返回</a>
	</div>
</div>

<script type="text/javascript">
function setFontSize(index)
{
	if (index == 1)
	{
		$("#notification_content").attr("class", "small_text");
	}
	else if (index == 2)
	{
		$("#notification_content").attr("class", "normal_text");
	}
	else if (index == 3)
	{
		$("#notification_content").attr("class", "big_text");
	}
}
</script>
<%@ include file="../component/CommonBottom.jsp"%>