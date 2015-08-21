<%@ page pageEncoding="utf-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!-- 弹出框-添加班级，不需要添加title -->
<div >
	<div class="mar20 pad20">
		<div>
			<span class="col_1"> 公共名称：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="className2" placeholder="不超过10个字符" maxlength="10" oninput="check10(this.value)" onpropertychange="check10(this.value)"/>
				<span id="msg2" class="none"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
		</div>
		
		<div>
			<span class="col_1"> 开始序号：</span>
			<div class="col_2">
				<select id="beginIndex" class="beginIndex" onchange="initEndIndex(this.value)">
				</select>
			</div>
		</div>
		
		<div>
			<span class="col_1"> 结束序号：</span>
			<div class="col_2">
				<select id="endIndex" class="endIndex">
					<option value="-1">请先选择开始序号</option>
				</select>
			</div>
		</div>
		
		<div>
			<span class="col_1"> 入学年份： </span>
			<div class="col_2">
				<select id="classEnterYear2" class="classEnterYear" >	
				</select>
			</div>
		</div>
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" id="button2" style="width: 246px;display:none; " href="javascript:addBatchClass();">添加班级</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>


