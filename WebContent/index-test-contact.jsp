<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
 <head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>テスト</title>
   <script type="text/javascript">

   
   </script>
   <%@ include file="/WEB-INF/header.jsp"%>
 </head>
 <body>
   <%@ include file="/WEB-INF/user-navbar.jsp"%>
   <form action="view-history" method="post">
     <button type="submit" class="btn btn-danger btn-sm">利用者詳細テスト</button>
   </form>
   <form action="form-contact" method="post">
     <button type="submit" class="btn btn-outline-danger btn-sm">リクエストフォーム</button>
   </form>
 </body>
</html>
