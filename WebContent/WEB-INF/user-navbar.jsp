<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="index.jsp"><img src="img/CodeTrain.png" height="30" width="50"/></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="navbar-text small mr-3 text-danger"><c:out value="${ navbarMessage }" /></div>
  <div class="navbar-text small mr-3 text-secondary"><c:out value="${ not empty user.lastName ? user.lastName += ' ' += user.firstName += 'さん、こんにちは' : '' }" /></div>
  <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">

    <c:if test="${ empty user }">
      <div>
        <form id="form-nav" action="login-user" method="post" class="form-inline">
          <input type="text" name="id" class="form-control form-control-sm" placeholder="ログインID">
          <input type="password" name="password" class="form-control form-control-sm" placeholder="パスワード">
          <input type="hidden" name="uri" value="${ requestScope.uri }">
          <button type="submit" class="btn btn-outline-primary btn-sm">ログイン</button>
        </form>
      </div>
    </c:if>

    <c:if test="${ not empty user }">
        <div class="navbar-text small mr-3 text-danger"><c:out value="${ navbarMessage }" /></div>

        <div class="dropdown">
          <a class="nav-item nav-link dropdown-toggle mr-md-2 text-muted" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <c:out value="${ not empty user.lastName ? user.lastName += ' ' += user.firstName += 'さん' : '' }" />
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
            <a class="dropdown-item" href="list-course">コース一覧</a>
            <a class="dropdown-item" href="form-contact">リクエスト</a>
            <a class="dropdown-item" href="list-request">リクエスト履歴</a>
            <a class="dropdown-item" href="form-change-password">パスワード変更</a>
          </div>
        </div>

        <form action="logout-user" method="post" class="form-inline">
          <button type="submit" class="btn btn-outline-danger btn-sm">ログアウト</button>
        </form>
    </c:if>
  </div>

</nav>