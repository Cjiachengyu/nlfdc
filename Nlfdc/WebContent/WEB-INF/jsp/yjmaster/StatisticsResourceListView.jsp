<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 被包含页面，不需要设置title -->
<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style type="text/css">
#teacher_assignmnet_list { width: 100%; }
.teacher_assignmnet_items{ width: 100%; }
a { text-decoration: none; color: #2b8ccd; }
.asmName { background: #eee; } 
</style>

<div id="teacher_assignmnet_list">
    <div class="teacher_assignmnet_items" >
        <table class="light_pink_table" >
            <thead >
                <tr>
                    <th width="35%">资源名称</th>
                    <th width="15%">发布者</th>
                    <th width="30%">发布时间</th>
                    <th width="20%">使用次数</th>
                </tr>
            </thead>
	  
            <tbody>
                <c:forEach var="res" items="${actionBean.resourceList}" varStatus="var">
                        <tr height="40px">
                            <td class="asmName">${res.resName }</td>
                            <td>${res.creatorName }</td>
                            <td>${res.simpleTimeString }</td>
                            <td>${res.yjResUseCount }</td>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
	</div>
	
	<div>
		<%@ include file="../component/PagingBar.jsp"%>
	</div>
</div>

</fmt:bundle>