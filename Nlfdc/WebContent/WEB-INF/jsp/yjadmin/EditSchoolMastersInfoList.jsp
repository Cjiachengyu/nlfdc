<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<div>
	<table class="light_gray_table3">
		<c:if test="${actionBean.mastersInEditingSchoolList != null && fn:length(actionBean.mastersInEditingSchoolList) != 0}">
			<thead>
				<tr>
					<th width="15%">姓名</th>
					<th width="25%">登录Id</th>
					<th width="30%">Email</th>
					<!-- 
					<th width="20%">密码</th>
					 -->
					<th width="10%">操作</th>
				</tr>
			</thead>
		
			<tbody>
                <c:forEach var="user" items="${actionBean.mastersInEditingSchoolList }" >
				<tr>
					<td>${user.userName }</td>
					<td>${user.loginId }</td>
					<td>${user.email }</td>
					<!-- 
					<td>${user.password }</td>
					 -->
					<td> <a href="javascript:deleteMaster(${user.userId })">删除</a> </td>
				</tr>
                </c:forEach>
			</tbody>
		</c:if>

		<c:if test="${actionBean.mastersInEditingSchoolList == null || fn:length(actionBean.mastersInEditingSchoolList) == 0 }">
			<h4 align="center">该学校没有校长！</h4>
		</c:if>
	</table>
</div>
