<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div style="text-align: center; width:100%;">
	<c:if test="${actionBean.fileUrl != ''}">
		<img src="${actionBean.fileUrl}" style="max-width: 96%; margin-top: 20px; " 
		<c:if test="${actionBean.isMobile == 1 }">
			onclick="javascript:history.back();" 
		</c:if>
		/>
	</c:if>
	<c:if test="${actionBean.fileUrl == ''}">
		<h2>出现错误，加载资源失败！</h2>
	</c:if>
</div>
