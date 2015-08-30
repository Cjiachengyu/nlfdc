<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 弹出框-添加校长，不需要添加title   -->
<style>
.col_1 { display: inline-block; width: 100px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 200px; height: 30px; margin: 10px; } 
.input_text { width: 200px; height: 30px; font-size: 17px; }
.admin_menus { height: 225px; padding: 30px 40px 10px 40px; overflow-y: auto; } 
.check_menu { float: left; width: 140px; margin: 5px 10px 5px 40px; }
.none { display: none; }
</style>

<div class="clearfix">
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

	<div class="mar20">
		 
		 <div>
			<span class="col_1">姓名：</span>
			<div class="col_2">
	       		<input type='text' class="input_text" id="userName" maxlength="16" oninput="check3(this.value)" onpropertychange="check3(this.value)" placeholder="不超过16个字符"/>
				<span id="add_admin_msg3" class="none"><font size="1" color="red">*长度达到最大值</font></span>
        	</div>
		</div>
	
	 	<div>
			<span class="col_1">登录名：</span>
			<div class="col_2">
	       		<input type='text' class="input_text" id="userName" maxlength="16" oninput="check1(this.value)" onpropertychange="check1(this.value)" placeholder="不超过16个字符"/>
				<span id="add_admin_msg1" class="none"><font size="1" color="red">*长度达到最大值</font></span>
        	</div>
		</div>	
		
		<div>
			<span class="col_1">密码：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="password" maxlength="16" oninput="check2(this.value)" onpropertychange="check2(this.value)" placeholder="不超过32个字符"/>
				<span id="add_admin_msg2" class="none"><font size="1" color="red">*长度达到最大值</font></span>
        	</div>
    	</div>
		
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="blue_button" id="add_admin_button" style="width: 196px; display: none; " href="javascript:doAddNewAdmin();">
		     	确认添加
		     </a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript" src="js/admin/addAdmin.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
