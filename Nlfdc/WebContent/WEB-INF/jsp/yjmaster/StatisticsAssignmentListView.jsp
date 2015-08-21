<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style type="text/css">
a { text-decoration: none; color: #2b8ccd; }
.asmName { background: #eee; } 
</style>

<div>
    <div>
	  <table class="light_pink_table" >
	      <thead >
	         <tr >
	             <th width="30%">作业名称</th>
	             <th width="10%">总人数</th>
	             <th width="10%">已提交</th>
				 <!-- 
	             <th width="10%">提交率</th>
				  -->
	             <th width="10%">已批改</th>
	             <!-- 
	             <th width="10%">批改率</th>
	              -->
	             <th width="13%">发布者</th>
	             <th width="13%">发布班级</th>
	             <th width="14%">发布日期</th>
	         </tr>
	      </thead>

	      <tbody>
	         <c:forEach var="teaAsm" items="${actionBean.statisticsAssignmentList}" varStatus="var">

	         	<c:forEach var="pubCls" items="${teaAsm.publishedClass }" varStatus="var2" >
		           <tr height="40px">
		           	  <c:if test="${var2.index == 0}">
		              	<td  class="asmName" rowSpan="${fn:length(teaAsm.publishedClass) }">${teaAsm.asmName }</td>
		              </c:if>
		              <td >${pubCls.studentCount }</td>
		              <td >${pubCls.submittedCount }</td>
		              <!-- 
		              <td >
		              	 <c:if test="${pubCls.submitRatio == 100.0}">
		              	 	100
		              	 </c:if>
		              	 <c:if test="${pubCls.submitRatio == 0.0}">
		              	 	0
		              	 </c:if>
		              	 <c:if test="${pubCls.submitRatio != 100.0 and pubCls.submitRatio != 0.0}">
		              	 	${pubCls.submitRatio }
		              	 </c:if>%
		              </td>
		               -->
		              <td >${pubCls.correctedCount }</td>
		              <!-- 
		              <td >
		              	 <c:if test="${pubCls.correctRatio == 100.0}">
		              	 	100
		              	 </c:if>
		              	 <c:if test="${pubCls.correctRatio == 0.0}">
		              	 	0
		              	 </c:if>
		              	 <c:if test="${pubCls.correctRatio != 100.0 and pubCls.correctRatio != 0.0}">
		              	 	${pubCls.correctRatio }
		              	 </c:if>%
		              </td>
		               -->
		              <td >${teaAsm.creatorName }</td>
		              <td >${pubCls.clsName }</td>
		              <td >${pubCls.createTimeString }</td>
		           </tr>
	         	</c:forEach>
	         </c:forEach>
	      </tbody>
	     </table>
	</div>
	
	<div>
		<%@ include file="../component/PagingBar.jsp"%>
	</div>
</div>

</fmt:bundle>