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
        <c:if test="${ not empty errorMessage }">
          <div class="mx-auto text-danger">&nbsp;<c:out value="${ errorMessage }" /></div>
        </c:if>
        <c:if test="${ not empty message }">
          <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
        </c:if>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-5 p-3 text-info border-bottom">コース一覧</p>
        <table class="table table-striped mt-5">
          <thead>
            <tr>
              <th>カテゴリ</th>
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
                <c:if test="${ list.get(i)[0]['isFreeFlg'] eq 1 }">
                  <td><a href="view-course?courseId=${ list.get(i)[0]['courseId'] }">${ list.get(i)[0]["courseName"] }</a></td>
                </c:if>
                <c:if test="${ list.get(i)[0]['isFreeFlg'] eq 0 }">
                  <c:if test="${ not empty user }">
                    <td><a href="view-course?courseId=${ list.get(i)[0]['courseId'] }">${ list.get(i)[0]["courseName"] }</a></td>
                  </c:if>
                  <c:if test="${ empty user }">
                    <td>${ list.get(i)[0]["courseName"] }</td>
                  </c:if>
                </c:if>
                <td>${ list.get(i)[0]["overview"] }</td>
                <td>${ list.get(i)[0]["requiredTime"] }時間</td>
                <c:if test="${ list.get(i)[0]['isFreeFlg'] eq 1 }">
                  <td><span class="text-success">Free</span></td>
                </c:if>
                <c:if test="${ list.get(i)[0]['isFreeFlg'] eq 0 }">
                  <td><span class="text-warning">Premium</span></td>
                </c:if>
                <c:if test="${ not empty user }">
                  <td><span class="text-primary">${ list.get(i)[1] }%</span></td>
                  <c:if test="${ list.get(i)[2][0]['passFlg'] eq 0 }">
                    <td><span class="text-white bg-danger success-or-failure">不合格</span></td>
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