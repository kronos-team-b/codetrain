<%@ page language="java" contentType="text/html; charset=UTF-8"pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>
  <c:forEach items="${ list }" var="dto">
  <c:out value="${ dto.CourseName }" /></c:forEach>
</title>
</head>
<body>
  <%@ include file="user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h1 mt-3 mb-3 p-3 text-info">
          <c:forEach items="${ list }" var="dto">
          <c:out value="${ dto.CourseName }" />
        <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">
          <c:out value="${ dto.UnitTitle }" /></p>
          <c:out value="${ dto.knowledge }" />
          </c:forEach>
        <form action="form-unit-test" method="post" >
          <button type="submit" class="btn btn-primary">テスト開始</button>
        </form>
        <form action="list-channel" method="post" >
          <button type="submit" class="btn btn-primary">中断</button>
        </form>
      </div>
    </div>
  </div>
</body>
</html>