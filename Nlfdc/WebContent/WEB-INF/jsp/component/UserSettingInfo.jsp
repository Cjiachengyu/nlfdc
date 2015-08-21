<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.input_text { width: 230px; height: 25px; font-size: 17px; margin-right: 20px; position: relative; top: -1px; padding: 0 5px;}
.tip1  { display: none; color: #FF4400; font-size: 12px; }
.tip2  { display: none; color: #FF4400; font-size: 12px; }
.near { margin: 0; padding: 0; margin-left: 200px; }
</style>

<div>
    <div class="col_1" >
        <span > 姓名： </span>
    </div>
    <div class="col_2">
        <span>
            ${sessionScope.realUser.userName }
        </span>
    </div>
</div>
<div>
    <div class="col_1" >
        <span > 学号： </span>
    </div>
    <div class="col_2">
        <span>
            ${sessionScope.realUser.loginId }
        </span>
    </div>
</div>
<div>
    <div class="col_1" >
        <span > 登录名： </span>
    </div>
    <div class="col_2" style="margin: 5px; ">
    	<input type="hidden" id="old_loginName" value="${sessionScope.realUser.loginName }">
		<input type='text' class="input_text " id='loginName' maxlength="32" value='${sessionScope.realUser.loginName }' />
        <a class="pink_button" style="width: 100px; padding: 2px;" href="javascript:changeLoginName();">修改</a>
        <div>
            <span class="tip1" >*登录名为字母或数字的组合，且不以数字开头，最大长度为30</span>
        </div>
    </div>
</div>
<div>
    <div class="col_1" >
        <span > Email： </span>
    </div>
    <div class="col_2">
        <span>
            <c:if test="${sessionScope.realUser.email == null || sessionScope.realUser.email == ''}">(无)</c:if>
            ${sessionScope.realUser.email }
        </span>
    </div>
    <!-- 
    <div class="col_2" style="margin: 5px; ">
    	<input type="hidden" id="old_email" value="${sessionScope.realUser.email }">
		<input type='text' class="input_text " id='email' maxlength="32"  value='${sessionScope.realUser.email }' />
        <a class="pink_button" href="javascript:changeEmail();">修改</a>
        <div>
            <span class="tip2">*email必须是有效的邮箱地址</span>
        </div>
    </div>
     -->
</div>

<script type="text/javascript" src="js/component/userSettingInfo.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
