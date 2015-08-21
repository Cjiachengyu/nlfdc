<%@ page pageEncoding="utf-8"%>

<style>
.col_1 { display: inline-block; width: 150px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 400px; height: 30px; margin: 10px; } 
.input_text { width: 250px; height: 30px; font-size: 17px; }
</style>

<div id="user_edit_pwd" class="clearfix">
	<div class="mar20 pad20">
		<div>
			<span class="col_1"> 请输入原密码 ：</span>
			<div class="col_2">
				<input type='password' class="input_text " id='old_pwd' name='oldPassword' maxlength="32"/>
			</div>
		</div>
		<div>
			<span class="col_1"> 请输入新密码 ： </span>
			<div class="col_2">
				<input type='password' class="input_text " id='new_pwd' name='newPassword' maxlength="32"/>
			</div>
		</div>
		<div>
			<span class="col_1"> 请再输入新密码 ： </span>
			<div class="col_2">
				<input type='password' class="input_text " id='new_pwd_again' name='newPasswordAgain' maxlength="32"/>
			</div>
		</div>
		
		<div>
			<span class="col_1"></span>
			<div class="col_2">
				<a class="pink_button" style="padding: 5px 30px; width: 195px;" href="javascript:doChangeUserPassword();">修改密码</a>
			</div>
		</div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript" src="js/component/userEditPassword.js?jscssimgversion=${actionBean.jsCssImgVersion}" ></script>
