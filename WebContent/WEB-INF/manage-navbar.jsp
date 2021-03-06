<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <img src="img/CodeTrain.png" height="40" width="70"/>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">

    <c:if test="${ not empty manage }">
      <c:out value="${ not empty manage.lastName ? manage.lastName += ' ' += manage.firstName += 'さん' : '' }" />
      <form action="logout-manage" method="post" class="form-inline">
        <button type="submit" class="btn btn-outline-danger btn-sm">ログアウト</button>
      </form>
    </c:if>
  </div>
</nav>