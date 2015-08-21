<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<style>
.highlight_text { color: red; }
</style>

<!-- 被包含页面，不需要设置title -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<div class="notification_items">
    <c:forEach var="noti" items="${actionBean.notificationList}">
        <div class="simple_list_item clearfix">
        	<div class="simple_list_item_left mar5">
        		<c:if test="${noti.notifyTarget == '1'}"><img style="width: 36px;" src="image/yj/all_user.png"> </c:if>
        		<!-- 
        		<c:if test="${noti.notifyTarget != '1'}"><span style="display: inline-block; width: 27px;" ></span> </c:if>
        		 -->
                <c:if test="${noti.notifyTarget == '2'}"><img style="width: 36px;" src="image/yj/all_teacher.png"> </c:if>
                <c:if test="${noti.notifyTarget == '3'}"><img style="width: 36px;" src="image/yj/all_parent.png"> </c:if>
                <c:if test="${noti.notifyTarget != '1' and noti.notifyTarget != '2' and noti.notifyTarget != '3'}"><img style="width: 36px;" src="image/yj/some_cls.png"> </c:if>
        	</div>
            <div class="simple_list_item_left">
                <a id="notification_${noti.notificationId  }" href="javascript:viewNotification(${noti.notificationId }, 1)" >${noti.title}</a>
                <br>
                <span> 发布时间：${noti.createTimeString} </span>
				<c:if test="${noti.isReaded == 0}">
					<span id="not_read_tip_${noti.notificationId  }" class="font_bold" style="color: #c93232;">未读</span>
				</c:if>              
            </div>

            <div class="simple_list_item_right">
            	<c:if test="${noti.isReaded == 0}">
                    <a id="operation_${noti.notificationId  }" href="javascript:setNotificationReaded('${noti.notificationId }');">设为已读</a>
                </c:if>
            </div>
			
            <div class="clear"></div>
        </div>
    </c:forEach>
</div>
<script src="js/component/notificationListView.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<div>
    <%@ include file="../component/PagingBar.jsp"%>
</div>

</fmt:bundle>
