<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>カリキュラムテスト回答</title>
  <%@ include file="header.jsp"%>
</head>
<body>
  <%@ include file="user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-5 p-3 text-info border-bottom">- テスト回答 - <c:out value="${ unitInfo.unitTitle }" /></p>
        <p class="font-weight-bold"><c:out value="${ unitTestPoint }"/>点</p>
          <c:forEach items="${ unitTestList }" var="unitTest" varStatus="unitTestStatus">
           <div class="mb-4">
              <c:if test="${ answeredList[unitTestStatus.index]['userAnswer'] eq answerList[unitTestStatus.index]['modelAnswer'] }">
                <p class="font-weight-bold bg-success p-2 rounded">問<c:out value="${unitTestStatus.count}" /> <c:out value="${ unitTest.testTitle }" /></p>
              </c:if>
              <c:if test="${ answeredList[unitTestStatus.index]['userAnswer'] ne answerList[unitTestStatus.index]['modelAnswer'] }">
                <p class="font-weight-bold bg-danger p-2 rounded">問<c:out value="${unitTestStatus.count}" /> <c:out value="${ unitTest.testTitle }" /></p>
                <p class="small ml-2">正解: <c:out value="${ answerList[unitTestStatus.index]['modelAnswer'] }"/> </p>
              </c:if>
              <c:if test="${ unitTest.answerTypeFlg eq 0 }">
                <input type="text" value="${ answeredList[unitTestStatus.index]['userAnswer'] }" class="form-control ml-2 col-10" disabled>
              </c:if>
              <c:if test="${ unitTest.answerTypeFlg eq 1 }">
                <c:forEach items="${ choicesList }" var="choice" varStatus="choiceStatus">
                  <c:if test="${ unitTest.testId eq choice.testId }">
                    <div class="ml-2">
                      <div class="form-check">
                        <input type="radio" id="radioTest1" class="form-check-input" disabled ${ answeredList[unitTestStatus.index]['userAnswer'] == 1 ? 'checked' : '' }>
                        <label for="radioTest1">${ choice.choice1 }</label>
                      </div>
                      <div class="form-check">
                        <input type="radio" id="radioTest2" class="form-check-input" disabled ${ answeredList[unitTestStatus.index]['userAnswer'] == 2 ? 'checked' : '' }>
                        <label for="radioTest2">${ choice.choice2 }</label>
                      </div>
                      <div class="form-check">
                        <input type="radio" id="radioTest3" class="form-check-input" disabled ${ answeredList[unitTestStatus.index]['userAnswer'] == 3 ? 'checked' : '' }>
                        <label for="radioTest3">${ choice.choice3 }</label>
                      </div>
                      <div class="form-check">
                        <input type="radio" id="radioTest4" class="form-check-input" disabled ${ answeredList[unitTestStatus.index]['userAnswer'] == 4 ? 'checked' : '' }>
                        <label for="radioTest4">${ choice.choice4 }</label>
                      </div>
                    </div>
                  </c:if>
                </c:forEach>
              </c:if>
           </div>
          </c:forEach>
          <c:if test="${ unitFlg eq 1 or unitTestAmount eq unitInfo.unitId }">
            <div class="mt-5 mb-5">
              <a href="view-course?courseId=${ unitInfo.courseId }" class="text-info">戻る</a>
            </div>
          </c:if>
          <c:if test="${ unitFlg eq 0 and unitTestAmount ne unitInfo.unitId }">
            <div class="mt-5 mb-5">
              <a href="view-unit?unit-id=${ unitInfo.unitId + 1 }">次のテキストへ</a>
            </div>
          </c:if>
      </div>
    </div>
  </div>
</body>
</html>