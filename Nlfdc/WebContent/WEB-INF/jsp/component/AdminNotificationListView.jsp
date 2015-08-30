<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.highlight_text { color: red; }
.notification_items { width: 100%; }
.notification { width: 100%; height: 35px; position: relative; border-bottom: solid 1px #eaeaea; }
.col_1 { display: inline-block; width: 70%; margin-top: 10px; }
.col_2 { display: inline-block; width: 10%; float: right; margin-top: 10px; text-align: right; }
.col_3 { display: inline-block; width: 15%; float: right; margin-top: 10px; text-align: center; }
.notification_link {color: blue; text-decoration: none; }
.notification_link:hover {color: red; text-decoration: none; }
.operation_link {color: black; }
.operation_link:hover {color: red; }
</style>

<div class="notification_items">
    <c:forEach var="noti" items="${actionBean.notificationList}">
		<div class="notification">
			<span class="col_1">[${noti.secondMenuName }]&nbsp;
			<a class="notification_link" href="adminnotificationmanageaction?viewnotification=&notificationId=${noti.notificationId}">${noti.title }</a></span>
			<c:if test="${sessionScope.admin != null }">
			<span class="col_2">
					<c:if test="${noti.isDeleted == 0}">
						<a class="operation_link" href="javascript:deleteNotification(${noti.notificationId })" >删除</a>
					</c:if>
					<c:if test="${noti.isDeleted == 1}">
						<a class="operation_link" href="javascript:unDeleteNotification(${noti.notificationId })" >还原</a>
					</c:if>
			</span>
			</c:if>
			<span class="col_3">${noti.createTimeString }</span>
		</div>
    </c:forEach>
		
</div>
<script src="js/component/adminNotificationListView.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<div>
    <%@ include file="../component/PagingBar.jsp"%>
</div>
