<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>
      <c:out value="${ unitDto.unitTitle }" />
    </title>
    <%@ include file="header.jsp"%>
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
              <c:out value="${ courseDto.courseName }" />
              <div>
              </div>
            <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">
                <c:out value="${ unitDto.unitTitle }" /></p>
            <p class ="mb='20'">
              <c:forEach var="str" items="${ fn:split(unitDto.unitText,'%') }" >${str}<br>
              </c:forEach>
             </p>
          <div class="form-group row">
          <div class="mx-auto">
          <form action="form-unit-test" method="get" id="form-unit-test"></form>
          <form action="test-stop" method="get" id="test-stop"></form>
          <input type="hidden" form="form-unit-test" name="unit-id" value="${ unitDto.unitId }">
          <input type="hidden" form="test-stop" name="unit-id" value="${ unitDto.unitId }">
          <button type="submit" form="form-unit-test" class="btn btn-primary">テスト開始</button>
          <button type="submit" form="test-stop" class="btn btn-danger">中断</button>
          </div>
        </div>
        </div>
      </div>
      </div>
  </body>
</html>
