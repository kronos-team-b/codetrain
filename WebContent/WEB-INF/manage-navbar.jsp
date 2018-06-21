<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="index-manage.jsp"><img src="img/CodeTrain.png" height="30" width="50"/></a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="navbar-text small mr-3 text-danger"><c:out value="${ navbarMessage }" /></div>
  <div class="navbar-text small mr-3 text-secondary"><c:out value="${ not empty manage.lastName ? manage.lastName += ' ' += manage.firstName += 'さん、こんにちは' : '' }" /></div>
  <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">

    <c:if test="${ not empty manage }">
        <div class="navbar-text small mr-3 text-danger"><c:out value="${ navbarMessage }" /></div>
    </c:if>

    <c:if test="${ not empty manage }">
        <div class="navbar-text small mr-3 text-danger"><c:out value="${ navbarMessage }" /></div>

        <a class="nav-item nav-link dropdown-toggle mr-md-2 text-muted" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
           <c:out value="${ not empty manage.lastName ? manage.lastName += ' ' += manage.firstName += 'さん' : '' }" />
        </a>

        <form action="logout-manage" method="post" class="form-inline">
          <button type="submit" class="btn btn-outline-danger btn-sm">ログアウト</button>
        </form>
    </c:if>
  </div>
</nav>