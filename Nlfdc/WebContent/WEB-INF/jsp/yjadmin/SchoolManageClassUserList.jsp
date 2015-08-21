<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<!-- 弹出框-显示班级用户信息，不需要添加title -->
<style>
.userInfo { width: 100%; height:600px; overflow-y: scroll;}
.userInfo_table { width: 98%; margin-left:10px; border-collapse: collapse; }
.body_tr { height:30px; border: solid 1px #e5e5e5; }
</style>
<div class="userInfo">
	<table class="light_blue_table">
		<c:if test="${actionBean.userOfClassList != null && fn:length(actionBean.userOfClassList) != 0}">
			<thead class="thaed" style="text-align: left; background: #3598db; ">
				<tr height="30px">
					<th width="10%">角色</th>
					<th width="15%">姓名</th>
					<th width="15%">ID</th>
					<th width="15%">登录名</th>
					<th width="30%">邮箱</th>
					<th width="15%">密码</th>
				</tr>
			</thead>
		
			<c:forEach var="user" items="${actionBean.userOfClassList }" >
				<tr class="body_tr" 
					<c:if test="${user.userRole == 9}">style="background: #eaeaea; "</c:if>
					<c:if test="${user.userRole == 8}">style="background: #cccccc; "</c:if>
					>
					<td>
						<c:if test="${user.userRole == 9}">老师</c:if>
						<c:if test="${user.userRole == 8}">家长</c:if>
					</td>
					<td>${user.userName }</td>
					<td>${user.loginId }</td>
					<td>${user.loginName }</td>
					<td>${user.email }</td>
					<td>${user.password }</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${actionBean.userOfClassList == null || fn:length(actionBean.userOfClassList) == 0 }">
			<h2 align="center" style="margin-top: 100px; ">该班级没有用户！</h2>
		</c:if>
	</table>
</div>
<input type="hidden" id="viewClassName" value="${actionBean.viewingClassName }">

<script type="text/javascript">
$(function(){
	$("#border_masker_id").html($("#viewClassName").val());
});
</script>
