<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>請求明細一覧</title>
    <%@ include file="header.jsp"%>
    <style type="text/css">
      th {
      font-weight: normal;
      }
    </style>
  </head>
  <body>
    <%@ include file="admin-navbar.jsp"%>
    <div class="container">
     <div class="row">
        <div class="col-12">
         <div class="offset-1 col-10">
          <p class="h4 mt-3 p-3 text-info border-bottom mb-5">請求明細一覧</p>
          <div class="col-8">
          <table class="table">
            <thead>
            <tr>
              <th>請求日</th>
              <th>請求額</th>
              <th> </th>
            </tr>
            </thead>
            <c:forEach items="${ list }" var="dto">
            <tr>
              <th><c:out value="${ dto.billingDate }"></c:out></th>
              <th>¥<c:out value="${ dto.getTotalPrice() }"></c:out></th>
              <th><form action="list-bill-detail" method="post">
                <input type="hidden" name="billingId" value="${ dto.billingId }">
                <button type="submit" class="btn btn-success">詳細</button>
              </form></th>
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