<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>利用者一覧</title>
  <script>

  function activeConfirm(e) {
      if (window.confirm('復帰しますか')) {
        $('#active').attr('action', 'active-user');
        $('#active').submit();
      } else {
        return false;
      }
    }

  function deleteConfirm(e) {
    if (window.confirm('本当に削除しますか')) {
      $('#delete').attr('action', 'delete-user');
      $('#delete').submit();
    } else {
      return false;
    }
  }
  </script>
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
  <%@ include file="admin-navbar.jsp"%>
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
        <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">利用者一覧</p>
          <form action="add-user" method="post" id="add"></form>
          <table class="table table-striped">
            <thead>
              <tr>
                <th>ID(英数字4～8文字)</th>
                <th>姓</th>
                <th>名</th>
                <th>状態</th>
                <th></th>
                <th></th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td><input type="text" form="add" name="userId" maxlength="8" pattern="^([a-zA-Z0-9]{4,})$" class="form-control" placeholder="例) code1" value="${ userId }" required ></td>
                <td><input type="text" form="add" name="userLastName" class="form-control" placeholder="新幹線" value="${ userLastName }" required></td>
                <td><input type="text" form="add" name="userFirstName" class="form-control" placeholder="のぞみ" value="${ userFirstName }" required></td>
                <td></td>
                <td><button type="submit" form="add" class="btn btn-primary" >登録</button></td>
                <td></td>
                <td></td>
              </tr>
              <c:forEach items="${ user }" var="dto" varStatus="status">
                <c:if test="${ dto.deleteFlg == 0 }">
                  <tr>
                    <td><c:out value="${ dto.userId }" /></td>
                    <td><c:out value="${ dto.lastName }" /></td>
                    <td><c:out value="${ dto.firstName }" /></td>
                    <td>
                      <c:choose>
                        <c:when test="${ dto.inactiveFlg == 1}"><p class="mx-auto text-danger" align="left">休止中</p></c:when>
                        <c:otherwise><p class="mx-auto text-success" align="left">アクティブ</p></c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <form action="view-history" method="post" id="view">
                        <input type="hidden" name="userNo" id="userNo" value="${ dto.userNo }">
                        <button type="submit" class="btn btn-secondary">詳細</button>
                      </form>
                    </td>
                    <td>
                      <c:choose>
                        <c:when test="${ dto.inactiveFlg == 1}">
                          <form action="active-user" method="post" id="active">
                            <input type="hidden" name="userNo" class="form-control" id="userNo" value="${ dto.userNo }">
                            <button type="submit" class="btn btn-primary" onClick="return activeConfirm()">復帰</button>
                          </form>
                        </c:when>
                        <c:otherwise>
                          <form action="form-reason" method="post" id="inactive">
                            <input type="hidden" name="userNo" id="userNo" value="${ dto.userNo }">
                            <input type="hidden" name="userLastName" id="userLastName" value="${ dto.lastName }">
                            <input type="hidden" name="userFirstName" id="userFirstName" value="${ dto.firstName }">
                            <button type="submit" class="btn btn-secondary">休止</button>
                          </form>
                        </c:otherwise>
                      </c:choose>
                    </td>
                    <td>
                      <form action="delete-user" method="post" id="delete">
                        <input type="hidden" name="userId" class="form-control" id="userId" value="${ dto.userId }">
                        <input type="hidden" name="userLastName" class="form-control" id="userLastName" value="${ dto.lastName }">
                        <input type="hidden" name="userFirstName" class="form-control" id="userFirstName" value="${ dto.firstName }">
                        <button type="submit" class="btn btn-danger" onClick="return deleteConfirm()">削除</button>
                      </form>
                    </td>
                  </tr>
                </c:if>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </body>
</html>