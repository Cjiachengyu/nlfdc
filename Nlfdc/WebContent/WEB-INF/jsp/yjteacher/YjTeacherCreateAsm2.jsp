<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<title>创建任务2</title>

<link href="css/yjteacher/yjTeacherCreateAsm2.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<script>
   	var htmlVal = { htmlUrl: "yjteacreateasmaction", };
</script>   

<div class="bg_white simple_shadow create_step_bg">
	<div class="wrap">
		<div class="create_step create_step_2" >
			<a class="create_step_btn create_step_btn_prev left" href="yjteacreateasmaction?gotostep=&step=1" ></a>
			<a class="create_step_btn create_step_btn_next right" href="yjteacreateasmaction?gotostep=&step=3"></a>
		</div>
	</div>
</div>
<div class="wrap">
	<div class="content relative clearfix">
       	<div class="res_list_box">
			<h4 class="msg_title">共有 &nbsp<font color="#FAB2B0" id="create_asm_res_num">${fn:length(actionBean.createAsmRes) }</font>&nbsp个资源</h4>

			<div id="asm_res_list" class="asm_res_list" >
				<%@ include file="YjTeacherCreateAsmResListView.jsp" %>
			</div>
	    </div>

		<div class="operation_div">
			<a class="pink_button right marR20" href="yjteacreateasmaction?gotostep=&step=3">下一步</a>
			<a class="pink_button right marR20" href="yjteacreateasmaction?gotostep=&step=1">上一步</a>
			<div class="clear"></div>
		</div>
	</div>
</div>

<script charset="utf-8" src="js/yjteacher/yjTeacherCreateAsm2.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
