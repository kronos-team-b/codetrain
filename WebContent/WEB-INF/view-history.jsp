<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>利用者詳細</title>
  <%@ include file="header.jsp"%>
  <style>
  .box-bottom{
    position:absolute;
    bottom: 60;
    right: 0;
  }
  hr {
  border-width: 2px 0 0 0; /* 太さ */
  border-style: solid; /* 種類 */
  border-color: silver; /* 色 */
  }
  </style>
</head>
<body>
  <%@ include file="admin-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 p-3 text-primary border-bottom">利用者詳細</p>
      </div>
      <div class="col-12 ml-4 ">
        <b>利用者：<c:out value="${ user.lastName += ' ' += user.firstName += 'さん'}" /></b>
      </div>
      <div class="col-12">
        <p class="h5 mt-3 p-2 border-bottom"><b>学習履歴</b></p>
      </div>
    </div>

  <c:forEach items="${ categories }" var="category" varStatus="categoryStatus">
      <c:forEach items="${ courses }" var="course" varStatus="courseStatus">
      <c:if test="${ category.categoryId eq course.categoryId}">
      <div>
        <span class="border border-warning rounded mt-2 ml-3 pt-1 pb-1 pl-3 pr-3">${ category.categoryName }</span>


        <c:out value="${ course.courseName }"/>
         <div class="col-7">
          <table class="table mt-2 col-7 table-condensed">
            <thead>
              <tr>
                <th scope="col">カリキュラム</th>
                <th scope="col">実施日</th>
                <th scope="col">点数</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${ units }" var="unit" varStatus="unitStatus">
                <c:if test="${ course.courseId eq unit.courseId}">
                  <tr>
                    <td><c:out value="${ unit.unitTitle }"/></td>
                    <c:if test="${ empty unit.testAt }">
                      <td></td>
                      <td></td>
                    </c:if>
                    <c:if test="${not empty unit.testAt }">
                      <td><c:out value="${ unit.testAt }"/></td>
                      <td>
                        <c:choose>
                          <c:when test="${ unit.unitTestPoint == 100}"><p class="mx-auto text-primary" align="right"><b><c:out value="${ unit.unitTestPoint }点"/></b></p></c:when>
                          <c:when test="${ unit.unitTestPoint >= 70}"><p class="mx-auto text-info" align="right"><b><c:out value="${ unit.unitTestPoint }点"/></b></p></c:when>
                          <c:otherwise><p class="mx-auto text-danger" align="right"><b><c:out value="${ unit.unitTestPoint }点"/></b></p></c:otherwise>
                        </c:choose>
                      </td>
                    </c:if>
                  </tr>
                </c:if>
              </c:forEach>
            </tbody>
          </table>
          <div>
            <p class="h7"><b>修了テスト：</b>
             <c:choose>
               <c:when test="${ course.userNo == 0}"><b>未受験</b></c:when>
               <c:when test="${ course.passFlg == 1}"><span class="alert alert-success pt-1 pb-1 pl-3 pr-3"><b>合格</b></span></c:when>
               <c:when test="${ course.passFlg == 0}"><span class="alert alert-danger pt-1 pb-1 pl-3 pr-3"><b>不合格</b></span></c:when>
             </c:choose>
            </p>
          </div>
          </div>
        </div>
        <hr>
        </c:if>
      </c:forEach>
    </c:forEach>
  </div>
</body>
</html>
