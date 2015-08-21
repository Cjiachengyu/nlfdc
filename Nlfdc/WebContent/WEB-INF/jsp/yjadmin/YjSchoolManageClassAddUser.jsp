<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 弹出框-添加用户，不需要添加title   -->
<style>
.col_1 { display: inline-block; width: 100px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 300px; height: 30px; margin: 10px; } 
.input_text { width: 296px; height: 30px; font-size:17px; }
.input_area { width: 300px; height: 200px; font-size: 14px; overflow-y: scroll; }
.username_textarea {display: inline-block; width:300px; height: 200px;  margin: 10px;  }
.tip { display: inline-block; width:300px; height: 100px;  margin: 10px; background: #eaeaea; border-radius: 5px; }
li { font-size: 12px; color: red; font-famlily: serif; }
.none { display: none; }
</style>

<div class="clearfix">
	<div class="mar20">
		
		<div>
		<span class="col_1">角色：</span>
		<div class="col_2" style="width:200px;">
		<input type="radio" id="student_role" name="add_user_role" value="8" checked="checked" />
	    	<label for="student_role">家长</label>
	    <input type="radio" id="teacher_role" name="add_user_role" value="9" />
	    	<label for="teacher_role">老师</label>
        </div>
		</div>
		
		<div>
			<span class="col_1">密码：</span>
			<div class="col_2">
				<input type='password' class="input_text" id="password" maxlength="32" oninput="check2(this.value)" onpropertychange="check2(this.value)" placeholder="不超过32个字符"/>
				<span id="msg2" class="none"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
		</div>
		
		<div>
			<span class="col_1">提示：</span>
			<div class="tip">
				<li>
            		每行写一个姓名，开头结尾不要留空格，最后一行结束不要回车！
        		</li>
        		  <span style="font-famlily: serif; font-size: 12px;">例：</span>
        <pre style="font-famlily: serif; font-size: 12px;">
陈浩
王淼
        </pre>
			</div>
		</div>
		
		<div>
			<span class="col_1">姓名：</span>
			<div class="username_textarea">
				<textarea class="input_area" id="userName"  oninput="check1(this.value)" onpropertychange="check1(this.value)" ></textarea>
			</div>
		</div>
		
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" id="add_user_button" style="width: 296px;display: none" href="javascript:doAddNewUser();">添加用户</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript" src="js/yjadmin/yjAdminSchoolManageClassAddUser.js?jscssimgversion=${actionBean.jsCssImgVersion}" ></script>
