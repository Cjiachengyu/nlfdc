<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
<meta charset="utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
  
<link href="css/common/global.css?jscssimgversion=${actionBean.jsCssImgVersion}" rel="stylesheet" />
<!--[if lt IE 9]><style>.shadow { border: solid 1px lightgray; }</style><![endif]-->
</head>
<body>

<fmt:setLocale value="${sessionScope.lang}" />
<fmt:bundle basename="messages">

<div>
</div>

</fmt:bundle>

<script src='js/jquery-1.7.1.min.js' type='text/javascript' ></script>

<link rel="stylesheet" href="jqueryplugin/zebra_dialog/zebra_dialog.css" type="text/css" />
<script src="jqueryplugin/zebra_dialog/jquery.zebra_dialog.js" type="text/javascript"></script>

<script src='js/common/global.js?jscssimgversion=${actionBean.jsCssImgVersion}' type='text/javascript' ></script>
<script src="js/common/js.message.js?jscssimgversion=${actionBean.jsCssImgVersion}"></script>
