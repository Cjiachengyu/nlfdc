<%@ page pageEncoding="utf-8" %>
<%@ include file="../component/CommonTopMobile.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link href="css/yjteacher/mobileYjTeacherCreateAsm.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<script>
    var htmlVal = {htmlUrl: "yjteacreateasmaction"};
</script>

<div>
    <div class="page_title">
        <span>共有<font color="#FAB2B0" id="create_asm_res_num">${fn:length(actionBean.createAsmRes)}</font>个资源</span>
    </div>

    <div id="asm_res_list" >
        <%@ include file="YjTeacherCreateAsmResListView.jsp" %>
    </div>
    <div class="clear"></div>
</div>

<div>
    <div class="container create_step">
        <div class="row">
            <a class="col-xs-4 step_block step_btn" href="yjteacreateasmaction?gotostep=&step=1">上一步</a>
            <span class="col-xs-4 step_block step_info">第二步：调整资源</span>
            <a class="col-xs-4 step_block step_btn" href="yjteacreateasmaction?gotostep=&step=3">下一步</a>
        </div>
    </div>
</div>

<script charset="utf-8" src="js/yjteacher/yjTeacherCreateAsm2.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
