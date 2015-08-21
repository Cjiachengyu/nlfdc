<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 

<%@ include file="../component/CommonTop.jsp"%>

<title>大纲管理</title>
<link rel="stylesheet" type="text/css" href="css/yjadmin/yjAdminSchemaTextbookList.css?jscssimgversion=${actionBean.jsCssImgVersion}">

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" > 当前位置：大纲管理 </span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap" >
	<div id="content" class="clearfix">

        <div class="operation_div">
            <div class="right">
	        	<a class="local_button pink_button" id="admin_add_schema_button" href="javascript:void(0);">添加大纲</a>
            </div>
            <div class="clear"></div>
        </div>
	
		<div class="main_content" id="main_content">
			 <div class="schemaTextbook">
                <div class="schemaSelecter">
                   <span>大纲</span>
                   <select id="schema" class="schema" onchange="selectSchema(this.value)">
                         <option value="-1" selected>请选择大纲</option> 
                         <c:forEach var="schema" items="${actionBean.schemaList }">
                             <option value ="${schema.scmId }"  <c:if test="${actionBean.selectedSchemaId == schema.scmId }">selected</c:if> >
                             	${schema.scmName }
                             </option> 
                         </c:forEach>
                    </select>
                    <span>包含的课本：</span>
                </div>
                <div class="textbooks_in_schema" id="textbooks_in_schema">
                    <%@ include file="TextbooksInSchemaListView.jsp" %>
                </div>
            </div>
            
            <div class="textbookOutSchema">
                <div class="category_subject_selecter">
                    <span style=" position: relative; top: 5px;">可添加的课本：</span>
                    <div class="right">
                        <select id="subject" class="subject" onchange="getTextbookOutList()">
                             <option value="-1" selected>请选择科目</option> 
                             <c:forEach var="subj" items="${actionBean.subjectList }">
                                 <option value ="${subj.subjectId }" <c:if test="${actionBean.selectedSubjectId == subj.subjectId }">selected</c:if> >
                                     ${subj.subjectName }
                                 </option> 
                             </c:forEach>
                        </select>
                        <div class="clear" ></div>
                    </div>
                </div>
                <div class="textbooks_out_schema" id="textbooks_out_schema">
                    	<%@ include file="TextbooksOutSchemaListView.jsp" %>
                </div>
            </div>
            <div class="clear"></div>
        </div>
	</div>
</div>

<script>
var htmlVal = {
	htmlUrl : "yjadminschemaaction",
};
</script>

<script src="js/yjadmin/yjAdminSchemaTextbookList.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
