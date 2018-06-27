<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>コース詳細</title>
  <%@ include file="header.jsp"%>
  <style>
  .box-bottom{
    position:absolute;
    bottom: 60;
    right: 0;
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
        <div class="jumbotron pt-5 pb-4">
          <span class="border border-warning rounded pt-1 pb-1 pl-3 pr-3">${ category.categoryName }</span>
          <h4 class="text-info mt-3 mb-3">${ course.courseName }</h4>
          <p>${ course.overview }</p>
          <hr class="my-4 mt-2">
          <div class="row">
            <div class="col-8">
              <p class="small mb-5">修了目安時間: ${ course.requiredTime }時間</p>
              <div>
                <p class="font-weight-bold mb-1">受講における必要条件</p>
                <p class="ml-2">${ course.precondition }</p>
              </div>
              <div>
                <p class="font-weight-bold mb-1">ゴール</p>
                <p class="ml-2">${ course.goal }</p>
              </div>
            </div>
            <div class="col-4 box-bottom">
              <form action="view-unit" method="post" id="unit-test"></form>
              <form action="form-course-test" method="get" id="completion-test"></form>
              <div>
                <input type="hidden" form="unit-test" name="unit-flg" value="0">
                <button type="submit" form="unit-test" name="unit-id" value="${ units[0].unitId }" class="btn btn-primary col-5 mr-4">最初から</button>
                <c:if test="${ not empty next.unitId }">
                  <input type="hidden" name="unit-flg" value="0">
                  <button type="submit" form="unit-test" name="unit-id" value="${ next.unitId }" class="btn btn-primary col-5">続きから</button>
                </c:if>
                <c:if test="${ empty next.unitId }">
                  <button class="btn btn-primary col-5" disabled>続きから</button>
                </c:if>
              </div>
              <div>
                <input type="hidden" form="completion-test" name="courseId" value="${ course.courseId }">
                <button type="submit" form="completion-test" class="btn btn-primary col-11 mt-2">修了テスト</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div>
      <h5>このコースのカリキュラム</h5>
      <div class="dropdown">
        <button class="btn btn-light dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">選択してください</button>
        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
          <a class="dropdown-item" href="#">カリキュラム</a>
          <a class="dropdown-item" href="#">間違えたもの</a>
        </div>
      </div>
      <table class="table mt-4">
        <tbody>
          <c:forEach items="${ units }" var="unit" varStatus="status">
            <tr>
              <td><a href="view-unit?unit-id=${ unit.unitId }&unit-flg=1">${ unit.unitTitle }</a></td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
</body>
</html>