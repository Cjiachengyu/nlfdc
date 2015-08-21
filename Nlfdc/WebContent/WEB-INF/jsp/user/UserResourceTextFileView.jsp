<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@include file="../component/CommonTopSimpleOnlyJs.jsp"%>

<div id="file_content_preview" >
	<c:if test="${actionBean.fileUrl != ''}">
		<a id="file_url" href="${actionBean.fileUrl}" target="_blank"></a>
	</c:if>
	<c:if test="${actionBean.fileUrl == ''}">
		<h2>出现错误，加载资源失败！</h2>
	</c:if>
</div>


<script>
$(function(){
	$("#file_url")[0].click();
	closeAllLayers();
});

</script>
