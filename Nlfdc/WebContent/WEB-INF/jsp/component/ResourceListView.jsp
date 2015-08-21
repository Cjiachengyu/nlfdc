<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<div class="resource_items">
    <c:forEach var="resource" items="${actionBean.resourceList}" varStatus="var">
        <div id="create_asm_res_box_${resource.resId }"
	        <c:if test="${resource.isAddedToAsm == true}">class="simple_list_item clearfix selected_res_box"</c:if>
    	    <c:if test="${resource.isAddedToAsm == false}">class="simple_list_item clearfix"</c:if>
        >
            <div class="simple_list_item_left" style="max-width: 60%;">
                <!-- 标题 -->
				
                <c:if test="${resource.resType == 1}">
	                <a href="userresourceitemaction?gotoresourceitem&resId=${resource.resId}" >${resource.resName}</a>
                </c:if>
                <c:if test="${resource.resType != 1}">
	                <a href="javascript:viewResource(${resource.resId}, '${resource.resName}')" >${resource.resName}</a>
                </c:if>
                <br> 

                <!-- 小字 -->
                <c:if test="${sessionScope.realUser.userRole != 9}">
                <span>
                    ${resource.simpleTimeString} 
                </span>
                </c:if>
				<c:if test="${sessionScope.realUser.userRole == 9}">
				<span>
					${resource.simpleTimeString }
					<c:if test="${actionBean.resFromType == 2}">
						&nbsp; &nbsp; 发布者：${resource.creatorName }
					</c:if>
					<c:if test="${actionBean.resFromType == 2 or actionBean.resFromType == 3}">
						&nbsp; &nbsp; 使用：${resource.yjResUseCount }
					</c:if>
				</span>
				</c:if>
				
				<!-- 管理员（编辑、园长）端的园本资源增加上传者与使用次数的信息 -->
				<c:if test="${sessionScope.realUser.userRole == 10 or sessionScope.realUser.userRole == 11 or sessionScope.realUser.userRole == 12}">
					<c:if test="${resource.fromType == 1 }">
					<span>
						&nbsp; &nbsp; 发布者：${resource.creatorName }
						&nbsp; &nbsp; 使用：${resource.yjResUseCount }
					</span>
					</c:if>
				</c:if>
				
            </div>

            <!-- 右边 -->
            <div class="simple_list_item_right">
            
               <!-- 校长 -->
               <c:if test="${sessionScope.realUser.userRole == 10}"> 
							<div id="operation_div_${resource.resId }" class="inside">
									<a href="javascript:deleteResource('${resource.resId }');">删除</a>
									<a href="javascript:editResName('${resource.resId }')">编辑</a>
									<a href="yjeditorresourcemanageaction?yjdownloadresfile=&resId=${resource.resId }">下载</a>
									<c:if test="${resource.canMoveUp == true }">
										<a href="javascript:moveUp(${var.index})">上移</a>
									</c:if>
								 	<c:if test="${resource.canMoveDown == true }">
								    	<a href="javascript:moveDown(${var.index})">下移</a>
								    </c:if>
							</div>
			    </c:if>
			    
				 <!-- 编辑员 -->
                 <c:if test="${sessionScope.realUser.userRole == 11 and (actionBean.showType == 5 or actionBean.showType == 6)}"> 
					     	<div id="operation_div_${resource.resId }" class="inside">
					     		<a href="javascript:editResName('${resource.resId }')">编辑</a>
					     		<c:if test="${resource.canMoveUp == true }">
						     		<a href="javascript:moveUp(${var.index})">上移</a>
					     		</c:if>
					     		<c:if test="${resource.canMoveDown == true }">
					     			<a href="javascript:moveDown(${var.index})">下移</a>
					     		</c:if>
					     	</div>
                </c:if>

			   <!-- 管理员 -->
               <c:if test="${sessionScope.realUser.userRole == 12 and (actionBean.showType == 5 or actionBean.showType == 6)}"> 
							<div id="operation_div_${resource.resId }" class="inside">
									<a href="javascript:deleteResource('${resource.resId }');">删除</a>
									<a href="javascript:editResName('${resource.resId }')">编辑</a>
									<a href="yjeditorresourcemanageaction?yjdownloadresfile=&resId=${resource.resId }">下载</a>
									<c:if test="${resource.canMoveUp == true }">
										<a href="javascript:moveUp(${var.index})">上移</a>
									</c:if>
								 	<c:if test="${resource.canMoveDown == true }">
								    	<a href="javascript:moveDown(${var.index})">下移</a>
								    </c:if>
							</div>
			    </c:if>

                
                <!-- 幼教老师 -->
                <c:if test="${sessionScope.realUser.userRole == 9 && actionBean.showType == 0 }">
                	<c:if test="${resource.isAddedToAsm == false}">
	                	<a  class="que_oper_btn_with_icon add_que_button" id="operate_que_button_${resource.resId }" href="javascript:htmlFn.addResToAsm(${resource.resId});">添加</a>
                	</c:if> 
					<c:if test="${resource.isAddedToAsm == true}">
			       		<a class="que_oper_btn_with_icon remove_que_button"	id="operate_que_button_${resource.resId }" href="javascript:htmlFn.removeResFromAsm(${resource.resId});">移除</a>
					</c:if>
                </c:if>

                <c:if test="${sessionScope.realUser.userRole == 9 && actionBean.showType == 1 }">
                	<a href="javascript:createClassifySchemaMaskLayer('${resource.resId }');" >分类</a>
                	<a href="javascript:editResName('${resource.resId }')">编辑</a>
                	<a href="yjteares?yjdownloadresfile=&resId=${resource.resId }">下载</a>
                    <a href="javascript:deleteResource('${resource.resId }');" >删除</a>
                </c:if>
            </div>

            <div class="clear"></div>
        </div>
    </c:forEach>
</div>
<script src="js/component/resourceListView.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<div>
    <%@ include file="../component/PagingBar.jsp"%>
</div>


</fmt:bundle>