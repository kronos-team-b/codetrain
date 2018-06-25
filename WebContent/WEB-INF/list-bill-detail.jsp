<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>請求明細</title>
    <%@ include file="header.jsp"%>
    <style type="text/css">
      th {
      font-weight: normal;
      }
    </style>
  </head>
  <body>
    <%@ include file="admin-navbar.jsp"%>
    <div class="container">
     <div class="row">
        <div class="col-12">
         <div class="offset-1 col-10">
          <p class="h4 mt-3 p-3 text-info border-bottom mb-3">請求明細</p>
          <p align="right"><b><c:out value="${ billDto.getBillingDate() }"/> 現在</b></p>
          <div class="form-group row mb-5">
            <div class="col-6 text-left">
              <p><b><c:out value="${ corporateDto.getCorporateName() }"/></b></p>
              <p><b><c:out value="${ corporateDto.getLastName() }"/> <c:out value="${ corporateDto.getFirstName() }"/> 様</b></p>
            </div>
            <div class="col-6 text-right">
              <p><img src="img/kelonos.png"></p>
            </div>
          </div>
          <div class="col-3 border-bottom mb-5">
            <p><b>金額 　　　¥<c:out value="${ billDto.getTotalPrice() }"/></b></p>
          </div>
          <table class="table">
            <thead>
            <tr>
              <th>内容</th>
              <th>数</th>
              <th>単価</th>
              <th>消費税</th>
              <th>金額（税込み）</th>
            </tr>
            </thead>
              <tr>
                <th>利用アカウント</th>
                <th><c:out value="${ billDto.getNumberOfActiveAccount() }"/></th>
                <th><c:out value="${ billDto.getPrice() }"/></th>
                <th><c:out value="${ billDto.getTaxOfActiveAccount() }"/></th>
                <th><c:out value="${ billDto.getPriceOfActiveAccountWithTax() }"></c:out></th>
              </tr>
              <tr>
                <th>休止アカウント</th>
                <th><c:out value="${ billDto.getNumberOfInactiveAccount() }"/></th>
                <th><c:out value="${ billDto.getInactivePrice() }"/></th>
                <th><c:out value="${ billDto.getTaxOfInactiveAccount() }"/></th>
                <th><c:out value="${ billDto.getPriceOfInactiveAccountWithTax() }"></c:out></th>
              </tr>
            </table>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>