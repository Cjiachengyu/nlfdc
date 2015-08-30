<%@ page pageEncoding="UTF-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<style>
.admin_menus { height: 225px; padding: 30px 40px 10px 40px; overflow-y: auto; } 
.check_menu { float: left; width: 140px; margin: 15px 10px 5px 40px; }
.buttons { padding: 10px; margin: 15px 50px 0px 50px; float: right; }
.big_button { width: 150px; padding: 8px; }
.clear {clear: both; }
</style>

<div class="admin_menus">
	<c:forEach var="firstMenu" items="${actionBean.allFirstMenuList }" >
		<c:if test="${firstMenu.firstMenuId != 1 }">
		<div class="check_menu" >
            <input id='check_menu_${firstMenu.firstMenuId }' class="check_menu_input" 
                type='checkbox' value='${firstMenu.firstMenuId }' 
                <c:if test="${firstMenu.isSelected }"> checked='checked' </c:if> 
            />
            <label for='check_menu_${firstMenu.firstMenuId }'> ${firstMenu.firstMenuName } </label>
		</div>
		</c:if>
	</c:forEach>
	<div class="clear"></div>
</div>

<div class="buttons">
	<a class="blue_button big_button" href="javascript:save_menus_changes()">确定</a>
</div>