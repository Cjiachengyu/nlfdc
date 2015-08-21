<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>
<title>创建任务1</title>

<link href="css/yjteacher/yjTeacherCreateAsm1.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<script>
var htmlVal = { 
		htmlUrl: "yjteacreateasmaction",
		limitFileSize: 20971520,
		limitFileSizeString: "20M",
	};
</script>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<div class="create_step create_step_1" >
			<a class="create_step_btn create_step_btn_prev_disable left"></a>
			<a class="create_step_btn create_step_btn_next right" href="yjteacreateasmaction?gotostep=&step=2"></a>
		</div>
	</div>
</div>

<div class="wrap">
	<div class="clearfix">
		<div class="left_bar">
			<%@ include file="../component/SchemaCatalogue.jsp"%>
		</div>

		<div class="main_content">
			
			<div class="operation_div relative">
				 <a <c:if test="${actionBean.resFromType == 3}">class="res_from_type_button res_from_type_selected"</c:if> style="left: 10px;"
        			<c:if test="${actionBean.resFromType != 3}">class="res_from_type_button" href="yjteacreateasmaction?switchresfromtype&resfromtype=3"</c:if>  >系统资源</a>
       			 <a <c:if test="${actionBean.resFromType == 2}">class="res_from_type_button res_from_type_selected"</c:if> style="left: 130px;"
        			<c:if test="${actionBean.resFromType != 2}">class="res_from_type_button" href="yjteacreateasmaction?switchresfromtype&resfromtype=2"</c:if> >园本资源</a>
        		 <a <c:if test="${actionBean.resFromType == 1}">class="res_from_type_button res_from_type_selected"</c:if> style="left: 250px;" 
        			<c:if test="${actionBean.resFromType != 1}">class="res_from_type_button" href="yjteacreateasmaction?switchresfromtype&resfromtype=1" </c:if> >个人资源</a>
        		
        		<div class="right upload_res_box" >
        			<c:if test="${actionBean.resFromType == 1 }">
	        			 <%@ include file="../component/UploadResourceBtn.jsp"%>
        			</c:if>
        		</div>	
        		
			</div>
			
            <div id="resource_list">
                <%@ include file="../component/ResourceListView.jsp"%>
            </div>
            
            <div class="operation_div2 relative">
            	<a class="pink_button right" href="yjteacreateasmaction?gotostep=&step=2" > 下一步</a>
            	<a class="pink_button right" style="margin-right: 10px;" href="javascript:htmlFn.clearChoosedResList()" > 清空已选</a>
            </div>
            
		</div>
		<div class="clear"></div>
	</div>
</div>

<script src="js/yjteacher/yjTeacherCreateAsm1.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
