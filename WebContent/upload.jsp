<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body bgcolor="lightyellow" style="padding-left: 10px;">
<c:if test="${not empty message}">
<div style="background-color: red;">
<c:out value="${message}" />
</div>
</c:if>
<form action="upload" method="post" enctype="multipart/form-data">

File to Upload : <input type="file" name="fileName" value="chooes file"/> <br/><br/>
<input type="submit" value="upload" />

</form>
<br/>
<br/>
<a href="download.jsp">download file</a>
</body>
</html>