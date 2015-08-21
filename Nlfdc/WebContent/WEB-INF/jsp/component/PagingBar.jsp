<%@ page pageEncoding="UTF-8" %> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<style>
#paging_bar { margin: 18px 0 10px 0; }
#paging_bar_rightside { float: right; color: #AAA; }
#paging_bar_rightside a {text-decoration: none; color: #FF703C; }
#paging_bar_leftside { float: left; } 
#dest_page { width: 25px; position: relative; bottom: 2px; }
.skip_button { display: inline; margin: 0; padding: 2px; }
</style>

<div id="paging_bar" class="clearfix font_middle">
	<div id="paging_bar_rightside">
		<c:choose>
			<c:when test="${actionBean.pageModule.currentPage == 1}">   
				<fmt:message key="common.firstPage" />
				<fmt:message key="common.previousPage" />
			</c:when>
			<c:otherwise>
				<a href="javascript:pagingBarFn.gotoPage(1);" class="highlight" >
					<fmt:message key="common.firstPage" />
				</a>
				<a href="javascript:pagingBarFn.gotoPage(${actionBean.pageModule.currentPage - 1});" class="highlight" >
					<fmt:message key="common.previousPage" />
				</a>
			</c:otherwise>
		</c:choose>
	
		<c:choose>
			<c:when test="${actionBean.pageModule.currentPage == actionBean.pageModule.pageCount}">      
				<fmt:message key="common.nextPage" /> 
				<fmt:message key="common.lastPage" />
			</c:when>
			<c:otherwise>
				<a href="javascript:pagingBarFn.gotoPage(${actionBean.pageModule.currentPage + 1});" class="highlight" >
					<fmt:message key="common.nextPage" />
				</a>
				<a href="javascript:pagingBarFn.gotoPage(${actionBean.pageModule.pageCount});" class="highlight" >
					<fmt:message key="common.lastPage" />
				</a>      
			</c:otherwise>
		</c:choose>
	</div>
		
	<div id="paging_bar_leftside">
		<span id="current_page_tip"><fmt:message key="common.currentPage" />：</span>
		<span id="current_page">
			${actionBean.pageModule.currentPage}
		</span>
		/
		<span id="page_count">
			${actionBean.pageModule.pageCount}
		</span>
		&nbsp;&nbsp;
		
		<c:if test="${actionBean.pageModule.pageCount > 1}">
		  <input id="dest_page" type="text"/> 
		  <a class="highlight" href="javascript:pagingBarFn.gotoPage(null);" >
			<fmt:message key="common.pageSkip" />
		  </a>
		</c:if>
	</div>
	
	<div class="clear"></div>

	<script src="js/component/pagingBar.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

</div>

</fmt:bundle>
