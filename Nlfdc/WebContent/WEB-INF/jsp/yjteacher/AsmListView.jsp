<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<style>
.need_correct_count { position: absolute; top: 0; right: 0; padding: 0 1px; background-color: #ED1C24; max-width: 28px; min-width: 16px; height: 14px; 
	line-height: 14px; font-size: 12px; font-weight: normal; text-align: center; color: white; border-radius: 14px; list-style-type:none;}
.need_correct_box { position: relative; float: right; display: inline-block; }
.need_correct_box:hover { background: #eaeaea; border-radius: 14px;  }
</style>

<!-- 被包含页面，不需要设置title -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<div class="assignment_items">
    <c:forEach var="asm" items="${actionBean.assignmentList}">
        <div class="simple_list_item clearfix">
        	<div class="simple_list_item_left mar5">
                <c:if test="${asm.asmType == 4 }"><img style="width: 36px;" src="image/yj/yj_asm_type4.png"> </c:if>
                <c:if test="${asm.asmType == 5 }"><img style="width: 36px;" src="image/yj/yj_asm_type5.png"> </c:if>
                <c:if test="${asm.asmType == 6 }"><img style="width: 36px;" src="image/yj/yj_asm_type6.png"> </c:if>
        	</div>
            <div class="simple_list_item_left">
                <a 
                	<c:if test="${asm.isDeleted == 0}">href="yjteacherassignmentinfoaction?asmId=${asm.asmId}"</c:if>
                	<c:if test="${asm.isDeleted == 1}">href="##"</c:if>
                >${asm.asmName}</a>
                <br>
                <span> 创建：${asm.createTimeString} </span>
                <span > 
                	<img src="image/yj/time_icon.png" style="height: 14px; " >
				    <c:if test="${asm.finishTime > 0 }">
		    	    	<font color="green">截止：${asm.finishTimeString}</font>
		    	    </c:if> 
				    <c:if test="${asm.finishTime == 0 }">
				    	<font color="green">截止：未限制</font>
				    </c:if>
                </span>
				
                <c:if test="${actionBean.isDeleted == 0 }">
                     <c:if test="${asm.hasPublishedAny == true and asm.isPreReleased == false}"> 
                        <span> 已发布 </span>
                     </c:if> 
                     <c:if test="${asm.hasPublishedAny == true and asm.isPreReleased == true}"> 
                        <span class="font_bold" style="color: #c93232;"> 预发布 </span>
                     </c:if> 
	
                     <c:if test="${asm.hasPublishedAny == false}"> 
                        <span class="font_bold" style="color: #c93232;"> 未发布 </span>
                     </c:if> 
                 </c:if>
            </div>

            <div class="simple_list_item_right">
                <c:if test="${actionBean.isDeleted == 0 }">
                    <a href="javascript:htmlFn.deleteAssignment('${asm.asmId }');">删除</a>
                </c:if>
                <c:if test="${actionBean.isDeleted == 1 }">
                    <a href="javascript:htmlFn.restoreAssignment('${asm.asmId }');">还原</a>
                </c:if>
            </div>
			
			<c:if test="${asm.needCorrectedcount != 0 and asm.isDeleted == 0}">
 			<a style="" class="need_correct_box" href="yjteacherassignmentinfoaction?asmId=${asm.asmId}">
			<div class="simple_list_item_right" >
					<span class="mar10">待批改</span>
					<li class="need_correct_count" >${asm.needCorrectedcount }</li>
			</div>
			<div class="clear"></div>
 			</a>	
			</c:if>
			
            <div class="clear"></div>
        </div>
    </c:forEach>
</div>

<div>
    <%@ include file="../component/PagingBar.jsp"%>
</div>

</fmt:bundle>
