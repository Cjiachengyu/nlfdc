<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style type="text/css">
#schema_classify_wrapper {width: 100%; filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#51FFFFFF, endColorstr=#51FFFFFF); }
#schema_classify_wrapper select {width: 90%; margin: 6px 5%; padding: 3px; border: 1px solid #999; font-size: 16px;}
.classify_ok_btn { width: 90%; margin: 15px 5%; display: block; padding: 5px 0; }
</style>

<br>
<%@ include file="ClassifySelector.jsp" %>

<div>
	<a href="javascript:do_classify_schema();" class="pink_button classify_ok_btn" >确定</a>
</div>

<script src="js/component/schemaClassify.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

