<!DOCTYPE html>
<%@ page pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge"/>
</head>
<body>
<script>
    window.onload = function () {
        window.location.href = "${actionBean.redirectUrl}";
    };
</script>
</body>
</html>
