<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 弹出框-添加大纲，不需要添加title -->
<style type="text/css">
.col_1 { display: inline-block; width: 100px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 200px; height: 30px; margin: 10px; } 
.input_text { width: 200px; height: 30px; font-size: 17px; }
</style>

<input type="hidden" id="old_res_name" value="${actionBean.editingRes.resName }">
<div id="yj_edit_res_name" class="clearfix">
	<div class="mar20">
		<div>
			<span class="col_1"> 资源名称：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="new_res_name" maxlength="80" value="${actionBean.editingRes.resName }" 
					oninput="checkResName();" onpropertychange="checkResName();" />
				<span id="msg1" class="hide"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
		</div>
		
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" id="do_change_res_name_btn" style="width: 196px; display:none;" href="javascript:doChangeResName()" >确认修改</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

<script src="js/component/editResName.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
