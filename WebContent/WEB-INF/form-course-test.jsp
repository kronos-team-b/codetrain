<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Insert title here</title>
  <%@ include file="header.jsp"%>
</head>
<body>
  <%@ include file="user-navbar.jsp"%>
  <div class="container">
    <div class="row">
      <div class="col-12">
        <p class="h4 mt-3 mb-5 p-3 text-info border-bottom">- 修了テスト - <c:out value="${ course.courseName }" /></p>
        <form action="form-course-test" method="post">
          <c:forEach items="${ unitTestList }" var="unitTest" varStatus="unitTestStatus">
           <input type="hidden" name="course-id" value="${ course.courseId }">
           <input type="hidden" name="unit-test-id[]" value="${ unitTest.testId }">
           <div class="mb-4">
              <p class="font-weight-bold">問<c:out value="${unitTestStatus.count}" /> <c:out value="${ unitTest.testTitle }" /></p>
              <c:if test="${ unitTest.answerTypeFlg eq 0 }">
                <input type="text" name="answer[]" class="form-control ml-3" required>
              </c:if>
              <c:if test="${ unitTest.answerTypeFlg eq 1 }">
                <c:forEach items="${ choicesList }" var="choice" varStatus="choiceStatus">
                  <c:if test="${ unitTest.testId eq choice.testId }">
                    <div class="ml-3">
                      <div class="form-check">
                        <input type="radio" id="radioTest1" name="answer[]" value="1" class="form-check-input" required>
                        <label for="radioTest1">${ choice.choice1 }</label>
                      </div>
                      <div class="form-check">
                        <input type="radio" id="radioTest2" name="answer[]" value="2" class="form-check-input">
                        <label for="radioTest2">${ choice.choice2 }</label>
                      </div>
                      <div class="form-check">
                        <input type="radio" id="radioTest3" name="answer[]" value="3" class="form-check-input">
                        <label for="radioTest3">${ choice.choice3 }</label>
                      </div>
                      <div class="form-check">
                        <input type="radio" id="radioTest4" name="answer[]" value="4" class="form-check-input">
                        <label for="radioTest4">${ choice.choice4 }</label>
                      </div>
                    </div>
                  </c:if>
                </c:forEach>
              </c:if>
           </div>
          </c:forEach>
          <div class="mt-4">
            <button type="submit" class="btn btn-primary">送信</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</body>
</html>