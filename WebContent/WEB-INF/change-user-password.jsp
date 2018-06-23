<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>初期パスワード変更</title>
  <%@ include file="header.jsp"%>
</head>
<body>
  <%@ include file="user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-danger">&nbsp;<c:out value="${ errorMessage }" /></div>
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">パスワード変更</p>
        <div class="col-sm-8 offset-sm-2">
          <form action="change-user-password" method="post">
            <div class="form-group row">
              <label class="col-form-label" for="existing-password">既存パスワード</label>
              <input type="password" id="existing-password" class="form-control" name="existing-password" maxlength="30" required value="" />
            </div>
            <div class="form-group row">
              <label class="col-form-label" for="change-password">変更パスワード</label>
              <input type="password" id="change-password" class="form-control" name="change-password" maxlength="30" required value="" />
            </div>
            <div class="form-group row">
              <label class="col-form-label" for="confirm-password">確認パスワード</label>
              <input type="password" id="confirm-password" class="form-control" name="confirm-password" maxlength="30" required value="" />
            </div>
            <div class="form-group row pt-3">
              <div class="mx-auto">
                <button type="submit" class="btn btn-primary pl-5 pr-5">送信</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>
</html>
