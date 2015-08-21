<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 弹出框-添加班级，不需要添加title -->
<div>
	<div class="mar20 pad20">
		<div>
			<span class="col_1"> 班级名称：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="cls_name" placeholder="不超过16个字符" maxlength="16" oninput="check(this.value)" onpropertychange="check(this.value)" />
				<span id="msg1" class="none"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
		</div>
		
		<div>
			<span class="col_1"> 入学年份： </span>
			<div class="col_2">
				<select id="classEnterYear" class="classEnterYear" >	
				</select>
			</div>
		</div>
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" id="button" style="width: 246px;display:none; " href="javascript:addOneClass();">添加班级</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

