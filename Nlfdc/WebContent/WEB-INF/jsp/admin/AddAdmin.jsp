<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 被包含页面，不需要添加title  -->

<style>
.col_1 { display: inline-block; width: 200px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 400px; height: 30px; margin: 10px; } 
.col_3 { display: inline-block; width: 200px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: left;} 
.input_text { width: 400px; height: 30px; font-size: 17px; }
.loginname_check_passed { border: solid 1px green;}
.loginname_check_failed { border: solid 1px red;}
.admin_menus { width: 58%; height: 125px; margin: auto; overflow-y: auto; } 
.check_menu { float: left; width: 120px; margin: 10px 10px 5px 40px; }
.none { display: none; }
</style>


<div class="clearfix">
	<div class="mar20">

		<div class="admin_menus">
			<c:forEach var="firstMenu" items="${actionBean.allFirstMenuList }" >
			<c:if test="${firstMenu.firstMenuId != 1 }">
			<div class="check_menu" >
            	<input id='check_menu_${firstMenu.firstMenuId }' class="check_menu_input" 
                type='checkbox' value='${firstMenu.firstMenuId }' />
            	<label for='check_menu_${firstMenu.firstMenuId }'> ${firstMenu.firstMenuName } </label>
			</div>
			</c:if>
			</c:forEach>
			<div class="clear"></div>
		</div>
	
		<div>
		<span class="col_1">姓名：</span>
		<div class="col_2">
	       		<input type='text' class="input_text" id="admin_name" maxlength="16" onchange="check('admin_name',3)" />
				<span id="msg3" class="none"><font size="1" color="red">*长度达到最大值</font></span>
        </div>
		</div>
			
		<div>
			<span class="col_1">登录名：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="login_name" maxlength="16" onchange="check('login_name',1)" />
				<span id="msg1" class="none"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
	        <span id="check_loginname_exist" class="col_3"></span>
		</div>
		
		<div>
		<span class="col_1">密码：</span>
		<div class="col_2">
				<input type='text' class="input_text" id="password" maxlength="16" onchange="check('password',2)" />
				<span id="msg2" class="none"><font size="1" color="red">*长度达到最大值</font></span>
         </div>
    	</div>
		
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="blue_button" id="button" style="width: 196px;" href="javascript:doAddNewSystemUser();">确定添加</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

 
<script type="text/javascript" src="js/admin/addAdmin.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

