<%@ page pageEncoding="utf-8"%>
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 弹出添加学校框页面 ，不需要title  -->
<style>
.col_1 { display: inline-block; width: 100px; height: 30px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 200px; height: 30px; margin: 10px; } 
.input_text { width: 200px; height: 30px; font-size: 17px; }
.provincesIn { width: 204px; height: 34px; padding: 3px; font-size: 17px; }
.citiesIn { width: 204px; height: 34px; padding: 3px; font-size: 17px; }
.none { display: none; }
</style>


<div class="clearfix">
	<div class="mar20">
		<div>
			<span class="col_1">
				园名：
			</span>
			<div class="col_2">
				<input type='text' class="input_text" id="schoolName" maxlength="30" oninput="check1()" onpropertychange="check1()" placeholder="不超过16个字符"/>
				<span id="msg1" class="none"><font size="1" color="red">*长度达到最大值</font></span>
			</div>
		</div>
		
		<div>
		<span class="col_1">省份：</span>
		<div class="col_2">
			<select class="provincesIn" id="provincesIn" onchange="getSchoolCityIn(this.value)">
            	<option value="-1">选择省份</option>
                <c:forEach var="province" items="${actionBean.provinceList}">
					<option value="${province.provinceId}">${province.provinceName}</option>
				</c:forEach> 
            </select>
         </div>
    	</div>
		
		<div>
		<span class="col_1">城市：</span>
		<div class="col_2">
	        <select id="citiesIn" class="citiesIn" onchange="check1()">
            	<option value="-1">选择城市</option>
            </select>
        </div>
		</div>
		
		<div>
		  <span class="col_1"></span>
		  <div class="col_2" >
		     <a class="pink_button" id="button" style="width: 196px; display: none; " href="javascript:doAddNewSchool();">
		     	添加园所
		     </a>
	      </div>
	    </div>
		<div class="clear"></div>
	</div>
</div>

<script type="text/javascript" src="js/yjadmin/yjAdminAddSchool.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
