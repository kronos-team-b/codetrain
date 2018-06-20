<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <%@ include file="header.jsp"%>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ログイン</title>
  </head>
  <body>
    <div class="container">
      <div class="col-12">
        <p class="h4 mt-3 mb-5 p-3 text-info border-bottom">ログイン</p>
        <div class="row">
          <div class="mx-auto text-danger">&nbsp;<c:out value="${ errorMessage }" /></div>
        </div>
        <div class="row">
          <div class="col-sm-8 offset-2">
            <form action="/login-admin" method="post">
              <div class="form-group row">
                <label for="">ログインID</label>
                  <input  type="text" id="channelName" name="channelName" class="form-control" maxlength="30" value="${ data.channelName }" />
              </div>
              <div class="form-group row">
                <label for="password">パスワード</label>
                  <input type="text" id="password" name="password" class="form-control" maxlength="30" value="${ data.password }" />
              </div>
            </form>
            <div class="form-group row">
              <div class="mx-auto">
                <button type="submit" class="btn btn-primary">ログイン</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>