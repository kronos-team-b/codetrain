<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>利用者詳細</title>
  <%@ include file="/WEB-INF/header.jsp"%>
  <style>
  .box-bottom{
    position:absolute;
    bottom: 60;
    right: 0;
  }
  </style>
</head>
<body>
  <%@ include file="/WEB-INF/user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 p-3 text-info border-bottom">利用者詳細</p>
      </div>
      <div class="col-12">
        <b>利用者：<c:out value="${ user.lastName += ' ' += user.firstName += 'さん'}" /></b>
      </div>
    </div>


  </div>
</body>
</html>
