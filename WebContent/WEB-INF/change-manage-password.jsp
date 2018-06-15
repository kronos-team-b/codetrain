<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@ include file="header.jsp"%>
    <title>初期パスワード変更</title>
  </head>
  <body>
    <%@ include file="manage-navbar.jsp"%>
    <div class="container">
      <div class="row">
        <div class="col-12">
          <p class="h4 mt-3 p-3 text-info border-bottom mb-3">初期パスワード変更</p>
          <div class="offset-2 col-8">
            <form method="post">
              <div class="form-group row">
                <label class="col-form-label" for="password">変更パスワード</label>
                <input type="text" id="password" name="password" class="form-control" maxlength="30" />
              </div>
              <div class="form-group row">
                <label class="col-form-label" for="confirm-password">確認パスワード</label>
                <input type="text" id="confirm-password" name="confirm-password" class="form-control" maxlength="30" />
              </div>
              <div class="form-group row">
                <div class="mx-auto">
                  <button type="submit" class="btn btn-primary pl-5 pr-5">変更</button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>