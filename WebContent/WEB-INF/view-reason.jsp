<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>利用者休止</title>
  <script type="text/javascript">
  <!--
  function addReasonConfirm() {
    return window.confirm("本当に休止にしますか？");
  }
  // -->
  </script>
  <%@ include file="/WEB-INF/header.jsp"%>
</head>
<body>
  <%@ include file="admin-navbar.jsp"%>
  <div class="container">
    <div class="row">
        <c:if test="${ not empty errorMessage }">
          <div class="mx-auto text-danger">&nbsp;<c:out value="${ errorMessage }" /></div>
        </c:if>
        <c:if test="${ not empty message }">
          <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
        </c:if>
    </div>
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">利用者休止</p>
        <div class="col-sm-8 offset-sm-2">
          <form action="add-reason" method="post">
            <div class="form-group row">
              <label class="col-form-label" for="confirm-password">理由（必須）</label>
              <input type="text" id="reason" class="form-control" name="reason" maxlength="50" value="${ reason }"/>
              <input type="hidden" id="userNo" name="userNo" value="${ user.userNo }">
              <input type="hidden" id="userLastName" name="userLastName" value="${ user.lastName }">
              <input type="hidden" id="userFirstName" name="userFirstName" value="${ user.firstName }">
            </div>
            <div class="form-group row pt-3">
              <div class="mx-auto">
                <button type="submit" class="btn btn-primary pl-5 pr-5" onclick="addReasonConfirm()">アカウントを休止する</button>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </div>
</body>
</html>