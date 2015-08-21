<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://stripes.sourceforge.net/stripes.tld" prefix="stripes" %>

<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />

<title>重置密码</title>
<link href="image/common/fenghuang.ico" type="image/x-icon" rel="shortcut icon" /> 
<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />

<style type="text/css">
.content { min-height: 600px; margin: 20px 0; padding: 40px 0; }
</style>

</head>
<body>



<div class="wrap">
	<div class="content simple_content_bg clearfix">
		<div>
			<p>您的密码过于简单，请重新设置密码</p>
		</div>
		<%@ include file="../component/UserEditPwd.jsp"%>
	</div>
</div>

<div style="display: none;">
    <div id="lang" style="display: none;">${sessionScope.lang}</div>
</div>
</body>

<script src="js/common/js.message.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>

<script type="text/javascript">
    var htmlVal = { 
		htmlUrl: "userchangepwdaction" , 
        changePwdSuccessUrl : "userchangepwdaction?gotoadminmainpage", 
    };
</script>
<script src='js/jquery-1.7.1.min.js' type='text/javascript' ></script>
	
<link rel="stylesheet" href="jqueryplugin/zebra_dialog/zebra_dialog.css" type="text/css" />
<script src="jqueryplugin/zebra_dialog/jquery.zebra_dialog.js" type="text/javascript"></script>

<script src='js/common/global.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
</html>
