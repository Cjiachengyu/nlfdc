<!DOCTYPE html>
<%@ page pageEncoding="UTF-8" %> 
<%@ page contentType="text/html; charset=UTF-8"%> 
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<html>
<head>
 
<title>错误提示</title>
<meta http-equiv="refresh" content="3;url=yjmastersettingaction" >
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>

<script type="text/javascript">
var i = 5;
window.onload=function fun(){
 i--;
 document.getElementById("msg").innerHTML = "页面将在"+i+"秒后跳转到设置主页！";
 setTimeout(fun,1000);
}
</script>
<body>
<div style="width:80%; margin-left: 10%; margin-top:300px; ">
<fieldset>
		<legend>Infomation</legend>

		<h2 align="center">
			<font color="red">更换校徽失败！ 上传文件出现异常！</font>
		</h2>
		<h5 align="center" id="msg"></h5>
</fieldset>
</div>
</body>
</html>