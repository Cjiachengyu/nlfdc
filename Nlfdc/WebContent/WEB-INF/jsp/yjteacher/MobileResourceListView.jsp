<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<style>
.simple_list_item_right a {width: 100%; display: inline-block; margin: 0;}
</style>

<div class="resource_items">
    <c:forEach var="resource" items="${actionBean.resourceList}">
        <div  id="create_asm_res_box_${resource.resId }"
	        <c:if test="${resource.isAddedToAsm == true}">class="simple_list_item clearfix selected_res_box"</c:if>
    	    <c:if test="${resource.isAddedToAsm == false}">class="simple_list_item clearfix"</c:if>
        >
            <div class="simple_list_item_left">
                <!-- 标题 -->
				<!--预览有问题，暂时取消预览     --还原
                <a href="javascript:void(0)" >
				-->
                <a href="javascript:viewResource(${resource.resId}, '${resource.resName}')" >
                        ${resource.resName}
                </a>
                <br>

                <!-- 小字 -->
                <span>
                     ${resource.simpleTimeString}
                     <c:if test="${actionBean.resFromType == 2}">
						&nbsp; &nbsp; 发布者：${resource.creatorName }
					</c:if>
                     <c:if test="${actionBean.resFromType != 1}">
						&nbsp; &nbsp; 使用：${resource.yjResUseCount }
                     </c:if>
                </span>
            </div>

            <!-- 右边 -->
            <div class="simple_list_item_right">

                <!-- 幼教老师 -->
                <c:if test="${resource.isAddedToAsm == false}"> 
                <a id="operate_que_button_${resource.resId }"
                        href="javascript:htmlFn.addResToAsm(${resource.resId});"
                >添加</a>
                </c:if>

				<c:if test="${resource.isAddedToAsm == true}">
                <a id="operate_que_button_${resource.resId }"  href="javascript:htmlFn.removeResFromAsm(${resource.resId});"
                >移除</a>
                </c:if>
            </div>

            <div class="clear"></div>
        </div>
    </c:forEach>
</div>

<div>
    <%@ include file="../component/PagingBar.jsp" %>
</div>

<script>
$(function(){
	$("#current_page_tip").html("");
});
</script>