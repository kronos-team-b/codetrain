<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>コースリスト</title>
  <%@ include file="header.jsp"%>
  <style type="text/css">
    .success-or-failure {
      border-radius: 30px;
      padding: 3px 9px;
      font-size: 15px;
    }
  </style>
</head>
<body>
  <%@ include file="user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">コース一覧</p>

        <div class="dropdown text-right">
          <button class="btn btn-light dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">カテゴリ</button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item" href="#">テスト</a>
          </div>
        </div>

        <table class="table table-striped mt-4">
          <thead>
            <tr>
              <th scope="col">カテゴリ</th>
              <th>コース名</th>
              <th>概要</th>
              <th>目安時間</th>
              <th>プラン</th>
              <c:if test="${ not empty user }">
                <th>進捗</th>
                <th>合否</th>
              </c:if>
            </tr>
          </thead>
          <tbody>
            <c:set var="i" value="0" />
            <c:forEach items="${ list }" var="dto" varStatus="status">
              <tr>
                <td>${ list.get(i)[0]["categoryName"] }</td>
                <td><a href="#">${ list.get(i)[0]["courseName"] }</a></td>
                <td>${ list.get(i)[0]["overview"] }</td>
                <td>${ list.get(i)[0]["requiredTime"] }時間</td>
                <c:if test="${ list.get(i)[0]['isFreeFlg'] eq 1 }">
                  <td><span class="text-success">Free</span></td>
                </c:if>
                <c:if test="${ list.get(i)[0]['isFreeFlg'] eq 0 }">
                  <td><span class="text-warning">Premium</span></td>
                </c:if>
                <c:if test="${ not empty user }">
                  <td><span class="text-primary">${ list.get(i)[1] }</span></td>
                  <c:if test="${ list.get(i)[2][0]['passFlg'] eq 0 }">
                    <td><span class="text-white bg-success success-or-failure">不合格</span></td>
                  </c:if>
                  <c:if test="${ list.get(i)[2][0]['passFlg'] eq 1 }">
                    <td><span class="text-white bg-success success-or-failure">合格</span></td>
                  </c:if>
                </c:if>
              </tr>
              <c:set var="i" value="${i + 1}" />
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>
</html>