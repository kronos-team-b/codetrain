<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>運営担当者窓口ログイン</title>
  <%@ include file="header.jsp"%>
</head>
<body>
  <%@ include file="manage-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ errorMessage }" /></div>
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
      <div class="row">
        <div class="col-12">
          <p class="h4 mt-3 mb-5 p-3 text-info border-bottom">運営担当者ログイン</p>
          <div class="col-sm-8 offset-2">
            <form action="login-manage" method="post">
              <div class="form-group row">
                <label class="col-form-label" for="id">ログインID</label>
                <input  type="text" id="id" name="id" class="form-control" maxlength="30" required value=""  />
              </div>
              <div class="form-group row">
                <label class="col-form-label" for="password">パスワード</label>
                <input type="password" id="password" name="password" class="form-control" maxlength="30" required value=""  />
              </div>
              <div class="form-group row">
                <div class="mx-auto">
                  <button type="submit" class="btn btn-primary">ログイン</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>


