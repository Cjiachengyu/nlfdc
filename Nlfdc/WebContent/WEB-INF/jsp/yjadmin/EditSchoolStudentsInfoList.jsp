<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 

<div>
	<table class="light_gray_table3">
		<c:if test="${actionBean.studentsInEditingSchoolList != null && fn:length(actionBean.studentsInEditingSchoolList) != 0}">
			<thead style="text-align: left; ">
				<tr>
					<th width="10%">姓名</th>
					<th width="25%">ID</th>
					<th width="25%">登录名</th>
					<th width="30%">邮箱</th>
					<!-- 
					<th width="20%">密码</th>
					 -->
					<th width="10%">操作</th>
				</tr>
			</thead>
		
            <tbody>
                <c:forEach var="user" items="${actionBean.studentsInEditingSchoolList }" >
				<tr>
					<td>${user.userName }</td>
					<td>${user.loginId }</td>
					<td>${user.loginName }</td>
					<td>${user.email }</td>
					<!-- 
					<td>${user.password }</td>
					 -->
					<td> <a href="javascript:deleteStudent(${user.userId })">删除</a> </td>
				</tr>
                </c:forEach>
            </tbody>
		</c:if>

		<c:if test="${actionBean.studentsInEditingSchoolList == null || fn:length(actionBean.studentsInEditingSchoolList) == 0 }">
			<h4 align="center">该学校没有家长！</h4>
		</c:if>
	</table>
</div>
