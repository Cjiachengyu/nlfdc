<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 

<%@ include file="../component/CommonTop.jsp"%>

<title>添加课本</title>
<link rel="stylesheet" type="text/css" href="css/yjadmin/yjAdminAddTextbook.css?jscssimgversion=${actionBean.jsCssImgVersion}" >
<link rel="stylesheet" type="text/css" href="css/yjadmin/textArea.css?jscssimgversion=${actionBean.jsCssImgVersion}">

<body onLoad="keyUp();">
<div class="bg_white simple_shadow">
	<div class="wrap">
		<span class="current_location"></span> 
		<span class="pad10 inline font_small" > 当前位置：<a href="yjadmintextbookaction">课本管理</a> &gt; 添加课本</span>
		<div class="clear"></div>
	</div>
</div>

<div class="wrap" style="margin-top:10px;" >
	<div class="bottom_line_div">
        <span> 基本信息 （封面可以为空）</span>
	</div>
	
	<div>
	<div class="edit_book_cover_box" style="height: 220px;">
          <div class="book_cover_box">
	          <img id="add_textbook_cover" class="textbook_cover" 
	             <c:if test="${actionBean.addedBookCoverUrl == ''}"> src="image/common/defaultCover.png" </c:if>
	             <c:if test="${actionBean.addedBookCoverUrl != ''}"> src="${actionBean.addedBookCoverUrl}" </c:if>
	           >      
	      </div>
          
          <div class="file_choose_div" style="margin-top: 10px; ">
	          <form id="submitBookCover" enctype='multipart/form-data' method='post' class="relative" >
	                   <input class="pink_button" style="width: 158px; " type='button' value='添加封面' />
	                   <input class="input_file" type="file" id="bookCover" name="bookCover" onchange="checkBookCover(this.value)"/>
				</form>
          </div>
	</div>
	
	<div class="book_info_box">
        <div class="input_col">
        	<span class="input_title">课本名：</span>
            <input class="input_text" type="text" maxlength="16" placeholder="课本名-长度不超过16" id="bookName">
        </div>  

        <div class="input_col">
        	<span class="input_title">出版社：</span>
            <input class="input_text" type="text" maxlength="16" placeholder="出版社-长度不超过16" id="publisher" >
        </div>

        <div class="input_col">
        	<span class="input_title">科目：</span>
            <select class="input_select" id="subject">
                <option value="-1" selected >请选择科目</option> 
                <c:forEach var="subj" items="${actionBean.subjectList }">
                    <option value ="${subj.subjectId }" >${subj.subjectName }</option> 
                </c:forEach>
             </select>
        </div>
    </div>
	<div class="clear"></div>
	</div>
	
	<div class="bottom_line_div">
        <span> 章节信息 </span>
	</div>
    <div class="tip_div">
        <span class="tip">提示：章名以“#”开头；章名每个占一行；每行开头不要有空格，例：</span>
        <pre class="tip_pre" >
#语文八年级上第一章
#语文八年级上第二章
#语文八年级上第三章
		</pre>
    </div>

    <div class="text_content">
        <div id="ol">
       		 <textarea cols="2" rows="10" id="li" disabled></textarea>
   		</div>
    	<textarea name="co" cols="60" rows="10" wrap="off" id="c2" onblur="check('2')" onkeyup="keyUp()" onFocus="clearValue('2')" 
    		onscroll="G('li').scrollTop = this.scrollTop;" oncontextmenu="return false" class="grey"></textarea>
    </div>

    <div class="btn_div">
        <a class="pink_button" id="add_textbook_button" style="width: 150px;" href="javascript:void(0)">确认添加</a>
    </div>
    <div class="clear"></div>
</div>
</body>

<script>
var htmlVal = {
		htmlUrl: "yjadmineditbookaction",
}
</script>

<script src="js/common/jquery.form.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script type="text/javascript" src="js/yjadmin/textArea.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
<script type="text/javascript" src="js/yjadmin/yjAdminAddTextbook.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<%@ include file="../component/CommonBottom.jsp"%>

