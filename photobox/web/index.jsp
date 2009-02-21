<%--
  Created by IntelliJ IDEA.
  User: Sike Huang
  Date: Feb 7, 2009
  Time: 12:28:21 AM
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
    <title>Upload a file please</title>
</head>
<body>
<h1>Please upload a file</h1>

<form method="post" action="./imageUpload.do" enctype="multipart/form-data">
    <input type="file" name="image"/>
    <textarea name="description" rows="20" cols="20">some text</textarea>
    <input type="submit"/>
</form>

</body>
</html>