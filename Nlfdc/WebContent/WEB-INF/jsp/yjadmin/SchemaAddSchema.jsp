<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 弹出框-添加大纲，不需要添加title -->
<style>
.col_1 { display: inline-block; width: 100px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 200px; height: 30px; margin: 10px; } 
.input_text { width: 200px; height: 30px; font-size: 17px; }
.subject { width: 206px; height: 34px; font-size: 17px;  }
</style>

<div id="admin_add_schema" class="clearfix">
	<div class="mar20">
		<div>
			<span class="col_1"> 大纲名称：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="scmName" maxlength="16" oninput="check1(this.value)" onpropertychange="check1(this.value)" placeholder="不超过16个字符"/>
				<span id="msg1" class="hide"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
		</div>
		
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" id="button" style="width: 196px; display:none;" href="javascript:doAddNewSchema();">添加新大纲</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

<script>
var htmlVal = { 
	htmlUrl: "yjadminschemaaction",
};
</script>

<script src="js/yjadmin/yjAdminSchemaAddSchema.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

