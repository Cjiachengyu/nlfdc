<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!-- 被包含页面，不需要设置title -->
<style type="text/css">
#admin_list { width: 100%; }
</style>

<div id="admin_list">
    <table class="light_blue_table" >
        <thead>
            <tr>
            	<th width="15%">次序</th>
                <th width="15%">姓名</th>
                <th width="15%">登录名</th>
                <th width="15%">密码</th>
                <th width="15%">负责模块</th>
                <th width="25%">操作</th>
            </tr>
        </thead>

        <tbody>
            <c:forEach var="admin" items="${actionBean.adminList}" varStatus="var">
                <tr height="40px">
                    <td> ${var.index + 1} </td>
                    <td>${admin.adminName }</td>
                    <td>${admin.loginName }</td>
                    <td>${admin.password }</td>
                    <td>
                    	<c:forEach var="adminMenu" items="${admin.adminMenuList }">
                    		${adminMenu.firstMenuName }
                    		<br>
                    	</c:forEach>
                    </td>
                    <td>
       					 <c:if test="${admin.isDeleted == 0 }">
       					 	<a href="javascript:disableAdmin(${admin.adminId })">禁用</a>
       					 </c:if>            
                    	 <c:if test="${admin.isDeleted == 1 }">
                    	 	<a style="color: gray;" href="javascript:enableAdmin(${admin.adminId })">恢复</a>
                    	 </c:if>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
