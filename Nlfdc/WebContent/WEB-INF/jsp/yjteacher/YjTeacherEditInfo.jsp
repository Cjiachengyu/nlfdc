<%@ page pageEncoding="utf-8"%>
<%@ include file="../component/CommonTop.jsp"%>

<title>设置用户信息</title>
<style>
.content { min-height: 600px; margin: 20px 0; }

.col_1 { display: inline-block; width: 150px; margin: 10px; padding:5px; vertical-align: top; text-align: right; } 
.col_2 { display: inline-block; width: 640px; margin: 10px; padding: 5px; } 
.info_div {float: left; margin: 20px 0; }
.check_subject { float: left; width: 120px; margin: 3px; }

#textbook_div { width:690px; max-height:500px; }

</style>

<script type="text/javascript">
    var htmlVal = { 
    		htmlUrl : "yjteachersettingaction"
    	};
</script>

<div class="bg_white simple_shadow">
	<div class="wrap">
		<a class="sub_menu_button font_bold bg_light_gray highlight" >用户信息</a>
		<a class="sub_menu_button" href="yjteachersettingaction?subMenuType=2" >修改密码</a>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">
	<div class="content clearfix">
		<div class="info_div">
            <%@ include file="../component/UserSettingInfo.jsp"%>
			<div>
				<div class="col_1" >
					<span > 教授科目： </span>
				</div>
				<div class="col_2">
                    <c:forEach var="subject" items="${actionBean.subjectList }" >
                        <div class="check_subject" >
                            <input id='check_subject_${subject.subjectId }' class="check_subject_input" 
                                    type='checkbox' value='${subject.subjectId }' 
                                    <c:if test="${subject.isSelected }"> checked='checked' </c:if>
                                    onchange="javascript:doChangeUserSubject(this.checked,${subject.subjectId });"
                            />
                            <label for='check_subject_${subject.subjectId }'> ${subject.subjectName } </label>
                        </div>
                    </c:forEach>
				</div>
			</div>
			<div>
				<div class="col_1" >
					<span > 我的课本： </span>
				</div>
				<div class="col_2" >
					<div id="textbook_div" >
                        <%@ include file="../component/UserBookList.jsp" %>
					</div>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
</div>

<script src="js/yjteacher/yjTeacherEditInfo.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script src="js/component/userEditBookList.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>
