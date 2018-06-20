<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>チャンネル一覧</title>
    <script type="text/javascript">
    <!--
    function deleteConfirm(channelName) {
      return window.confirm("「" + channelName + "」チャンネルを本当に削除しますか？");
    }
    // -->
    </script>
    <%@ include file="/WEB-INF/header.jsp"%>
  </head>
  <body>
    <%@ include file="/WEB-INF/user-navbar.jsp"%>
    <form action="form-test-manage" method="post">
      <button type="submit" class="btn btn-danger btn-sm">テスト作成テスト</button>
    </form>
      <a href="view-test-manage?testId=2">テスト編集テスト</a>
  </body>
</html>