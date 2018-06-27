<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <img src="img/CodeTrain.png" height="40" width="70"/>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">

    <c:if test="${ not empty admin }">

        <div class="dropdown">
          <a class="nav-item nav-link dropdown-toggle mr-md-2 text-muted" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <c:out value="${ not empty admin.lastName ? admin.lastName += ' ' += admin.firstName += 'さん' : '' }" />
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
            <a class="dropdown-item" href="list-user">利用者一覧</a>
            <a class="dropdown-item" href="list-bill">請求一覧</a>
            <a class="dropdown-item" href="form-change-admin-password">パスワード変更</a>
          </div>
        </div>

        <form action="logout-admin" method="post" class="form-inline">
          <button type="submit" class="btn btn-outline-danger btn-sm">ログアウト</button>
        </form>
    </c:if>
  </div>
</nav>