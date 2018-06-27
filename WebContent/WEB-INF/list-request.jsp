<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>リクエスト一覧</title>
    <script type="text/javascript">
    </script>
    <%@ include file="header.jsp"%>
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
          <p class="h4 mt-3 p-3 text-info border-bottom">リクエスト一覧</p>
          <p>&nbsp;</p>
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">連絡事項</th>
                <th scope="col">リクエスト日</th>
                <th scope="col">最新連絡日</th>
                <th scope="col"></th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${ list }" var="dto" varStatus="status">
                <tr>
                  <td><c:out value="${ dto.contactDetail }" /></td>
                  <td><c:out value="${ dto.firstAt }" /></td>
                  <td><c:out value="${ dto.lastAt }" /></td>
                  <c:if test="${ dto.requestOrResponseFlg eq 1}">
                    <td class="text-danger">返信あり</td>
                  </c:if>
                  <c:if test="${ dto.requestOrResponseFlg eq 0}">
                    <td></td>
                  </c:if>
                  <td><form action="view-request" method="post">
                        <input type="hidden" name="contactId" value="${ dto.contactId }">
                        <button type="submit" class="btn btn-secondary btn-sm">詳細</button>
                      </form>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </body>
</html>
