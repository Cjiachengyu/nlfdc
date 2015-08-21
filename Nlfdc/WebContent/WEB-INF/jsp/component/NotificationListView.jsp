<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<style>

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
                <a href="javascript:viewNotification(${noti.notificationId }, 0)" >${noti.title}</a>
                <br>
                <span> 发布时间：${noti.createTimeString} </span>
              
            </div>

            <div class="simple_list_item_right">
                    <a href="javascript:deleteNotification('${noti.notificationId }');">删除</a>
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
