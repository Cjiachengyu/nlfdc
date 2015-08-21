<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style>
.simple_list_item_right a {height:100%; display: inline-block; margin: 0;}
</style>

<div class="resource_items">
    <c:forEach var="resource" items="${actionBean.createAsmRes}" varStatus="varSta">
        <div class="simple_list_item clearfix" id="create_asm_res_box2_${resource.resId }" >
            <div class="simple_list_item_left">
                <!-- 标题 -->
                <a href="javascript:viewResource(${resource.resId}, '${resource.resName}')" >
                    ${resource.resName} 
                </a>
                <br> 

                <!-- 小字 -->
                <span>
                    ${resource.simpleTimeString} 
                </span>
               
            </div>

            <!-- 右边 -->
            <div class="simple_list_item_right">
                <c:if test="${varSta.index != 0}">
	               	<a href="javascript:htmlFn.moveUp(${resource.resId});">上移</a>
                </c:if>
				<c:if test="${(varSta.index + 1) != fn:length(actionBean.createAsmRes)}">
	               	<a href="javascript:htmlFn.moveDown(${resource.resId});">下移</a>
				</c:if>
                <a href="javascript:htmlFn.removeFromAsm('${resource.resId }');" >删除</a>
            </div>

            <div class="clear"></div>
        </div>
    </c:forEach>
</div>

</fmt:bundle>