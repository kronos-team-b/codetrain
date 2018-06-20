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
   <form action="view-unit" method="post">
     <button type="submit" class="btn btn-danger btn-sm">テスト</button>
   </form>
   <form action="user-logout" method="post" class="form-inline">
     <button type="submit" class="btn btn-outline-danger btn-sm">ログアウト</button>
   </form>
 </body>
</html>
