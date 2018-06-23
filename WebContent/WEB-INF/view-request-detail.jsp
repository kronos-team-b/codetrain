<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>リクエスト詳細</title>
    <script type="text/javascript">
    </script>
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
          <p class="h4 mt-3 mb-3 p-3 text-info border-bottom">リクエスト詳細</p>
          <c:forEach items="${ contactDetailList }" var="dto" varStatus="status">
            <div class="panel panel-info" style="padding: 10px; margin-bottom: 10px; border: 1px">
              <div class="panel-heading ">
                <c:if test="${ dto.requestOrResponseFlg eq 0}">
                  <b>お客様</b>
                </c:if>
                <c:if test="${ dto.requestOrResponseFlg eq 1}">
                  <b>運営</b>
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
            <form action="add-request" method="post">
              <div class="form-group row">
                <label class="col-form-label" for="request">返信内容</label>
                <textarea rows="15" cols="50" id="response" class="form-control" name="response" maxlength="65535" required></textarea>
              </div>
              <div class="form-group row pt-3">
                <div class="mx-auto">
                  <input type="hidden" id="contactId" name="contactId" value="${ contactId }">
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