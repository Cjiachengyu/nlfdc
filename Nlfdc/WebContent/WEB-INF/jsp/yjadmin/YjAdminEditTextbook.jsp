<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%>

<%@ include file="../component/CommonTop.jsp"%>

<title>编辑课本</title>
<link rel="stylesheet" type="text/css" href="css/yjadmin/yjAdminAddTextbook.css?jscssimgversion=${actionBean.jsCssImgVersion}" >

<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" > 当前位置：<a href="yjadmintextbookaction">课本管理</a> &gt; 编辑课本：${actionBean.editingTextbook.bookName}</span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap content" style="margin-top:10px;" >
	<div class="bottom_line_div">
        <span> 基本信息 </span>
	</div>
	
	<div class="edit_book_cover_box">
          <div class="book_cover_box">
	          <img id="edit_textbook_cover" class="textbook_cover" 
	             <c:if test="${actionBean.editingTextbook.coverUrl == ''}"> src="image/common/defaultCover.png" </c:if>
	             <c:if test="${actionBean.editingTextbook.coverUrl != ''}"> src="${actionBean.editingTextbook.coverUrl}" </c:if>
	           >      
	      </div>
          
          <div class="file_choose_div">
	          <form id="submitBookCover" enctype='multipart/form-data' method='post' class="relative" >
	                   <input class="pink_button" style="width: 158px; " type='button' value='更换封面' />
	                   <input class="input_file" type="file" id="bookCover" name="bookCover" onchange="checkBookCover(this.value)"/>
			  </form>
          </div>
	</div>
	
	<div class="book_info_box">
        <div class="input_col">
        	<span class="input_title">课本名：</span>
            <input class="input_text" type="text" maxlength="16" placeholder="课本名-长度不超过16" id="bookName" value="${actionBean.editingTextbook.bookName }">
        </div>  

        <div class="input_col">
        	<span class="input_title">出版社：</span>
            <input class="input_text" type="text" maxlength="16" placeholder="出版社-长度不超过16" id="publisher" value="${actionBean.editingTextbook.publisher }">
        </div>
		
        <div class="input_col">
        	<span class="input_title">科目：</span>
            <select class="input_select" id="subject">
                <option value="-1" >请选择科目</option> 
                <c:forEach var="subj" items="${actionBean.subjectList }">
                    <option value ="${subj.subjectId }" <c:if test="${actionBean.editingTextbook.subjectId == subj.subjectId}">selected</c:if> >${subj.subjectName }</option> 
                </c:forEach>
             </select>
        </div>
		
       	<div class="input_col" ></div>
	    <div class="input_col" ></div>
        
       	<div class="input_col">
        	<span class="input_title"></span>
             <a class="pink_button" id="edit_textbook_button" style="width: 150px; float: right; margin-right: 40px;" href="javascript:void(0)">修改信息</a> 
        </div>
    </div>

    <div class="clear"></div>
</div>
</body>

<script>
var htmlVal = {
		htmlUrl: "yjadmineditbookaction",
		htmlBackUrl: "yjadmintextbookaction",
}
</script>

<script src="js/common/jquery.form.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script type="text/javascript" src="js/yjadmin/yjAdminEditTextbook.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>

