<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="header.jsp"%>
    <title>初期パスワード変更</title>
  </head>
  <body>
    <%@ include file="manage-navbar.jsp"%>
    <form method="Post">
    <div class="container">
    <div class="row">
    <div class="col-2"></div>
    <div class="col-8"><br>
      <p class="h4 mt-3 p-3 text-info rounded">初期パスワード変更</p>
    <hr size="2"><br>
    <div class="form-group row">
      <label class="mt-1 p-1">変更パスワード</label>
      <input type="text" id="password" name="password" class="form-control" maxlength="30" />
    </div>
    <div class="form-group row">
      <label class="mt-1 p-1">確認パスワード</label>
      <input type="text" id="password" name="password" class="form-control" maxlength="30" />
    </div>
    </div>
    </div>
    </div>
    <br>
    <div class="form-group row">
    <div class="mx-auto">
      <button type="submit" class="btn btn-primary">変更</button>
    </div>
    </div>
    </form>
  </body>
</html>