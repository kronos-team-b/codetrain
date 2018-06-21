<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>請求明細一覧</title>
    <%@ include file="header.jsp"%>
  </head>
  <body>
    <%--テスト用　 <%@ include file="coporate-navbar.jsp"%> --%>
    <div class="container">
      <div class="row">
        <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
      </div>
     <div class="row">
        <div class="col-12">
         <div class="offset-2 col-8">
          <p class="h4 mt-3 p-3 text-info border-bottom mb-5">請求明細一覧</p>
          <div class="col-10">
          <table class="table">
            <thead>
            <tr>
              <th>請求日</th>
              <th>請求額</th>
            </tr>
            </thead>
            <c:forEach items="${ list }" var="dto">
            <tr>
              <th><c:out value="${ dto.billingDate }"></c:out></th>

              <th><button type="submit" class="btn btn-success">詳細</button></th>
             </tr>
            </c:forEach>
          </table>
          </div>
        </div>
        </div>
      </div>
    </div>
  </body>
</html>