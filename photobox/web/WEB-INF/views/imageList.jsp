<%--
  Created by IntelliJ IDEA.
  User: Sike Huang
  Date: Feb 7, 2009
  Time: 1:20:56 AM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>PhotoBox Desktop</title>
</head>
<body>
<div class="main">
    <%--varStatus="rowCounter"--%>
    <c:forEach items="${images}" var="image">
        <div class="photo">
            <p>
                <c:out value="${image.id}"/><br/>
                <a href="./image.do?id=${image.id}"><img src="./imageThumbnailContent.do?id=${image.id}" alt="photo"
                                                         border="0"/></a><br/>
                <c:out value="${image.description}"/>
            </p>
        </div>
    </c:forEach>
</div>

</body>
</html>