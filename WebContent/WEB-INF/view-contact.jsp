<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>リクエストフォーム</title>
    <script type="text/javascript">
    </script>
    <%@ include file="header.jsp"%>
  </head>
  <body>
    <%@ include file="user-navbar.jsp"%>
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
          <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">リクエストフォーム</p>
          <div class="col-sm-8 offset-sm-2">
            <form action="add-contact" method="post">
              <div class="form-group row">
                <label class="col-form-label" for="request">リクエスト内容</label>
                <textarea rows="15" cols="50" id="request" class="form-control" name="request" maxlength="65535" required></textarea>
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
