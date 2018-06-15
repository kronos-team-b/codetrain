<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>リクエスト詳細</title>
    <script type="text/javascript"></script>
    <!-- BootstrapのCSS読み込み -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- jQuery読み込み -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript" src="jquery.highlight-5.js"></script>
    <!-- BootstrapのJS読み込み -->
    <script src="js/bootstrap.min.js"></script>
    <%@ include file="header.jsp"%>
  </head>
  <body>
    <%@ include file="user-navbar.jsp"%>
    <div class="container">
      <div class="row">
        <div class="mx-auto text-primary">&nbsp;<c:out value="${ message }" /></div>
      </div>

      <div class="row">
        <div class="col-12">
          <p class="h4 mt-3 mb-5 p-3 text-info border-bottom">リクエスト詳細</p>
          <c:forEach items="${ contactDetailList }" var="dto" varStatus="status">
            <div class="panel panel-info" style="padding: 10px; margin-bottom: 10px; border: 1px">
              <div class="panel-heading ">
                <c:if test="${ dto.RequestOrResponseFlg eq 0}">
                  <h5>お客様</h5>
                </c:if>
                <c:if test="${ dto.RequestOrResponseFlg eq 1}">
                  <h5>運営</h5>
                </c:if>
                </div>
              <div class="panel-body">
                <c:out value="${ dto.contactDetail}" />
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
      <div class="row">
        <div class="col-12">
          <div class="col-sm-8 offset-sm-2">
            <form acrion="add-request" method="post">
              <div class="form-group row">
                <label class="col-form-label" for="request">返信内容</label>
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
