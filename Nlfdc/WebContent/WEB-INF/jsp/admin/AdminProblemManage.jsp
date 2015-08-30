<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonAdminTop.jsp" %>

<title>常见问题</title>

<script type="text/javascript">
var htmlVal = {
    htmlUrl: "adminusermanageaction"
};

</script>

<div class="bg_white simple_shadow">
    <div class="wrap">
        <span class="current_location"></span> 
	    <span class="pad10 inline font_small"> 当前位置：<a href="adminusermanageaction">人员管理 </a>&gt; 添加管理员</span>
        <div class="clear"></div>
    </div>
</div>

<div class="wrap" >
	<div id="content" class="clearfix">
	     <%@ include file="YjAddEditor.jsp" %>
	</div>
</div>

<%@ include file="../component/CommonBottom.jsp"%>