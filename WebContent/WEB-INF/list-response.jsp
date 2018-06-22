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
    <%@ include file="manage-navbar.jsp"%>
    <div class="container">
      <div class="row">
        <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
      </div>
      <div class="row">
        <div class="col-12">
          <p class="h4 mt-3 p-3 text-info border-bottom">リクエスト一覧</p>
          <p>&nbsp;</p>
          <table class="table table-striped">
            <thead>
              <tr>
                <th scope="col">連絡事項</th>
                <th scope="col">返信</th>
                <th scope="col">最新連絡日</th>
                <th scope="col"></th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${ list }" var="dto" varStatus="status">
                <tr>
                  <td><c:out value="${ dto.contactDetail }" /></td>
                  <c:if test="${ dto.requestOrResponseFlg eq 0}">
                    <td class="text-danger">未返信</td>
                  </c:if>
                  <c:if test="${ dto.requestOrResponseFlg eq 1}">
                    <td class="text-success">返信済</td>
                  </c:if>
                  <td><c:out value="${ dto.contactAt }" /></td>
                  <td><form action="view-response" method="post">
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
