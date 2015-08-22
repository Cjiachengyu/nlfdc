<%@ page pageEncoding="utf-8" %> 
<%@ include file="../component/CommonTop.jsp"%>
<title>老师作业统计</title>
<style>
.main_content {width: 100%; margin-bottom: 20px;}
.operation_div { margin: 15px 0 10px 0; }
.subjects { width: 150px; height: 30px; padding: 3px; font-size: 14px; }
</style>
<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<c:if test="${actionBean.statisticsType == 1 }">
            <span class="pad10 inline font_small" > 当前位置：<a href="yjmasterclassaction">班级情况</a> &gt; ${actionBean.classInfo.clsName }的作业统计</span>
		</c:if>
		<c:if test="${actionBean.statisticsType == 2 }">
            <span class="pad10 inline font_small" > 当前位置：<a href="yjmasterteacheraction">教师情况</a> &gt; ${actionBean.teacher.userName }的作业统计</span>
		</c:if>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap">

	<div id="content" class="clearfix">
		 <div class="operation_div">
            <div class="right">
                <select class="subjects" onchange="htmlFn.selectSubject(this.value)" >
                    <option value="0">所有科目</option>
                    <c:forEach var="subject" items="${actionBean.subjects }">
                        <option value="${subject.subjectId }"
                            <c:if test="${subject.subjectId == actionBean.subjectId }">selected</c:if>
                        >${subject.subjectName }</option>
                    </c:forEach>
                </select>
            </div>
            <div class="clear"></div>
        </div>

		<div class="main_content" id="main_content">
			<%@ include file="StatisticsAssignmentListView.jsp"%>
		</div>

		<div class="clear"></div>
	</div>
</div>

<script src="js/yjmaster/yjMasterAsmStatistics.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>