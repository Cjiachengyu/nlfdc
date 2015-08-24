<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<link href="css/component/menuSelector.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<div class="all">
    <div class="content">
        <ul class="menu">
          <c:forEach var="firstMenu" items="${actionBean.menuSelector.firstMenuList }">
          	  <c:if test="${firstMenu.firstMenuId != 1}">
	          <li <c:if test="${firstMenu.firstMenuId  == actionBean.menuSelector.currentFirstMenuId }">
	          		class="item fb cover"
	          	  </c:if>
	          	  <c:if test="${firstMenu.firstMenuId  != actionBean.menuSelector.currentFirstMenuId }">
	          		class="item fb"
	          	  </c:if>
	          	 value="${firstMenu.firstMenuId }">
	          	${firstMenu.firstMenuName }
	            <span></span>
	            <ul class="menu hide">
	              <c:forEach var="secondMenu" items="${firstMenu.secondMenuList }">
	              	  <li <c:if test="${secondMenu.secondMenuId == actionBean.menuSelector.currentSecondMenuId }">
			              	  	class="citem selected_citem" 
	              	  	  </c:if>
	              	  	  <c:if test="${secondMenu.secondMenuId != actionBean.menuSelector.currentSecondMenuId }">
			              	  	class="citem" 
	              	  	  </c:if> 
	              	  	value="${secondMenu.secondMenuId }" >${secondMenu.secondMenuName }</li>
	              </c:forEach>
	            </ul>
	          </li>
          	  </c:if>
          </c:forEach>
        </ul>
    </div>
</div>

<input type="hidden" id="current_first_menuid" value="${actionBean.menuSelector.currentFirstMenuId }">
<input type="hidden" id="current_second_menuid" value="${actionBean.menuSelector.currentSecondMenuId }">

<script src='js/component/menuSelector.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
