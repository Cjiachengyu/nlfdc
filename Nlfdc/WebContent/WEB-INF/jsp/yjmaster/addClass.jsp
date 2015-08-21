<%@ page pageEncoding="utf-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 弹出框-添加班级，不需要添加title -->
<style>
.col_1 { display: inline-block; width: 100px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 250px; height: 30px; margin: 10px; } 
.input_text { width: 250px; height: 30px; font-size: 17px; }
.classEnterYear { width: 254px; height: 34px; font-size: 17px;  }
</style>

<div id="user_edit_pwd" class="clearfix">
	<div class="mar20 pad20">
		<div>
			<span class="col_1"> 班级名称：</span>
			<div class="col_2">
				<input type='text' class="input_text" id="cls_name" name='clsName' maxlength="16"/>
			</div>
		</div>
		
		<div>
			<span class="col_1"> 入学年份： </span>
			<div class="col_2">
				<select name="classEnterYear" id="classEnterYear" class="classEnterYear" >	
				  <c:forEach var="num" begin="${actionBean.minYear }" end="${actionBean.thisYear }" >
				      <option value ="${num }" 
				         <c:if test="${num == actionBean.thisYear }"> selected</c:if> >${num } 年</option> 
				   </c:forEach>
				</select>
			</div>
		</div>
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" style="width: 246px;" href="javascript:doAddClass();">添加班级</a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

