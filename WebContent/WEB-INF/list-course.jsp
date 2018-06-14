<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Insert title here</title>
  <%@ include file="header.jsp"%>
  <style type="text/css">
    .success-or-failure {
      border-radius: 30px;
      padding: 3px 9px;
      font-size: 15px;
    }
  </style>
</head>
<body>
  <%@ include file="user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">コース一覧</p>

        <div class="dropdown text-right">
          <button class="btn btn-light dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">カテゴリ</button>
          <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
            <a class="dropdown-item" href="#">テスト</a>
          </div>
        </div>

        <table class="table table-striped mt-4">
          <thead>
            <tr>
              <th scope="col">カテゴリ</th>
              <th>コース名</th>
              <th>概要</th>
              <th>目安時間</th>
              <th>プラン</th>
<!--          TODO: ログイン/ログアウト時で表示の変更 -->
              <th>進捗</th>
              <th>合否</th>

            </tr>
          </thead>
          <tbody>
<!--          TODO: コース数の分だけ表示 -->
              <tr>
                <td>カテゴリ</td>
                <td><a href="#">コース名</a></td>
                <td>概要概要概要概要概要概要概要概要概要概要概要概要概要</td>
                <td>1時間</td>
                <td><span class="text-warning">Premium</span></td>
<!--            TODO: ログイン/ログアウト時で表示の変更 -->
                <td><span class="text-primary">100%</span></td>
                <td><span class="text-white bg-success success-or-failure">合格</span></td>

              </tr>

          </tbody>
        </table>
      </div>
    </div>
  </div>
</body>
</html>