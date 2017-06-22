 <%@page import="java.io.PrintWriter"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="org.hibernate.Query"%>
<%@page import="org.hibernate.Transaction"%>
<%@page import="org.hibernate.Session"%>
<%@page import="com.util.HibernateUtils"%>
<%@page import="org.hibernate.SessionFactory"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.List , com.entities.Files , java.io.PrintWriter" %>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<% 
SessionFactory sessionFactory = HibernateUtils.getSessionFactory();
Session hsession = sessionFactory.openSession();
Transaction tr= hsession.beginTransaction();

Query query = hsession.createQuery(" from Files ");
query.setMaxResults(10);
List<Files> flst= (List<Files>)query.list();

tr.commit();
hsession.close();

pageContext.setAttribute("flst", flst);


%>

<c:if test="${not empty message1}">
<div style="background-color: red;">
<c:out value="${message1}" />
</div>
</c:if>

<c:if test="${not empty message2}">
<div style="background-color: red;">
<c:out value="${message2}" />
</div>
</c:if>
<div class="container">
          
  <table class="table">
    <thead>
      <tr>
        <th>File Name </th>
        <th> Download</th>
      </tr>
    </thead>
    <tbody>
      <tr>

 <c:forEach items="${flst}" var="files">
   <tr>
   
        <td>${files.file_name}</td>
        <td><a href="download?id=${files.id}">download</a></td>
      </tr>
 
</c:forEach>

    </tbody>
  </table>
</div>
<br/>
<div class="container" style="color: blue;">
<a href="upload.jsp"> upload file</a>
</div>
</body>
</html>