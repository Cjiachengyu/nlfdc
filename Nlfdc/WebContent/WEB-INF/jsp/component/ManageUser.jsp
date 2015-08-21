<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 

<link href="css/component/commonManage.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div class="operation_div">
    <div class="left margin-left30">
        <h4 style="margin-bottom: 10px;">用户找回密码</h4>
         <input type="text" id="email" placeholder="登录名/邮箱/学号" onkeypress="enterkey(event)">
         <a class="pink_button" style="margin: 0 10px;" href="javascript:findPassword();">查找密码</a>
         <span id="tip"></span>
         <div class="userInfo">
            <table class="userInfo_table">
                <thead class="thaed" style="text-align: left; background: #FAB2B0; ">
                    <tr height="30px">
                        <td width="10%">角色</td>
                        <td width="10%">姓名</td>
                        <td width="20%">Email</td>
                        <td width="20%">学号</td>
                        <td width="20%">登录名</td>
                        <td width="10%">密码</td>
                        <td width="10%">操作</td>
                    </tr>
                </thead>
            
                <tr class="body_tr">
                    <td id="user_roleString"></td>
                    <td id="user_name"></td>
                    <td id="user_email"></td>
                    <td id="user_loginId"></td>
                    <td id="user_loginName"></td>
                    <td ><input id="user_password" maxlength="32" disabled></td>
                    <td ><a href="javascript:resetPassword()" id="reset_password">重置密码</a></td>
                </tr>
            </table>
        </div>
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
</div>
<div class="clear"></div>

<script src="js/common/commonManage.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
