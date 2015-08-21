<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../component/CommonTop.jsp" %>

<title>课本管理</title>

<style>
.local_button { width: 120px; margin: 0 20px; }
.operation_div { margin: 15px 0 10px 0; display: block; border-bottom: solid 2px #999999; padding-bottom: 13px; }
.category_selecter_box { display: inline-block; margin: 10px; width: 225px; height: 35px; float: left; }
.selecter { width: 225px; height: 35px; font-size: 14px;}
.book_list { min-height: 700px; margin-bottom: 20px; display: block;}
</style>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" > 当前位置：课本管理 </span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap" >
	<div class="clearfix">
		 <div class="operation_div">
            <div class="right">
	             <a class="local_button pink_button" id="admin_add_textbook_button" href="javascript:void(0);">添加课本</a>
            </div>
            <div class="clear"></div>
        </div>
    	
    	<div class="main_content" id="main_content">
    	
			<div class="category_selecter_box">
				<select id="schema_selecter" class="selecter" onchange="reselect()">
                         <option value="0" selected>请选择大纲</option> 
                         <c:forEach var="schema" items="${actionBean.schemaList }">
                             <option value ="${schema.scmId }"  <c:if test="${actionBean.selectedSchemaId == schema.scmId }">selected</c:if> >
                             	${schema.scmName }
                             </option> 
                         </c:forEach>
                </select>
			</div>
			
			<div class="category_selecter_box">
				<select id="subject_selecter" class="selecter" onchange="reselect()">
                         <option value="0" selected>请选择科目</option> 
                         <c:forEach var="subject" items="${actionBean.subjectList }">
                             <option value ="${subject.subjectId }"  <c:if test="${actionBean.selectedSubjectId == subject.subjectId }">selected</c:if> >
                             	${subject.subjectName }
                             </option> 
                         </c:forEach>
                </select>
			</div>
    	</div>
	</div>
	
	<div id="textbook_list" class="book_list">
			<%@ include file="../yjadmin/YjTextbookListView.jsp" %>
	</div>
</div>

<script>
var htmlVal = {
		htmlUrl: "yjadmintextbookaction",
		htmlOutUrl: "yjadmineditbookaction",
}
</script>

<script src="js/yjadmin/yjAdminManageTextbook.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
