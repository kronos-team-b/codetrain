<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><c:out value="${ not empty isAdd ? 'カリキュラムテスト作成' : 'カリキュラムテスト編集'}"/></title>

    <script type="text/javascript">
      <!--
      function deleteConfirm() {
        return window.confirm("テストを本当に削除しますか？");
      }
       -->
     </script>
     <%@ include file="header.jsp"%>
   </head>
  <body>
  <%-- manageのナブバー作成後、下記のコメントアウトを外してjspを追加してください。 --%>
    <%-- <%@ include file="manage-navbar.jsp"%> --%>
        <div class="container">
        <div class="row">
          <div class="mx-auto text-danger">&nbsp;<c:out value="${ errorMessage }" /></div>
        </div>
      <div class="row">
        <div class="col-12">
          <form id='form' action="${ not empty isAdd ? 'add-test-manage' : 'edit-test-manage' }" method="post">
            <p class="h4 mt-3 p-3 bg-light text-info rounded"><c:out value="${ not empty isAdd ? 'カリキュラムテスト追加' : 'カリキュラムテスト編集'}"/></p>
            <div class="form-group row">
              <input type="hidden" name="testId" value="${ data.testId }">
              <input type="hidden" name="updateNumber" value="${ data.updateNumber }" />
            </div>
            <div class="form-group row">
              <label class="col-form-label col-2" for="courseId">コース名</label>
              <div class="col-10">
                <select id="courseId" name="courseId">
                  <c:forEach items="${ courseList }" var="coursedto" varStatus="status">
                    <option value="${ coursedto.courseId }">${ coursedto.courseName }</option>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group row">
              <label class="col-form-label col-2" for="courseId">カリキュラム名</label>
              <div class="col-10">
                <select id="unitId" name="unitId">
                  <c:forEach items="${ unitList }" var="unitdto" varStatus="status">
                    <c:if test = "${ courseId == unitList.courseId }">
                      <option value ="${ unitdto.unitId }">${ coursedto.unitName }</option>
                    </c:if>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group row">
              <label class="col-form-label col-2" for="testName">問</label>
              <div class="col-10">
                <input type="text" id="testName" name="testName" class="form-control" maxlength="30" value="${ data.testName }" />
              </div>
            </div>
            <div class="form-group row">
              <label class="col-form-label col-2" for="testContent">コード（任意）</label>
              <div class="col-10">
                <textarea id="testContent" name="testContent" style="resize: none;" class="form-control" maxlength="255" rows="4"><c:out value="${ data.testContent }" /></textarea>
              </div>
            </div>
            <div class="form-group row">
              <div class="mx-auto">
                <c:if test="${ empty isAdd }">
                  <button type="reset" class="btn btn-secondary">リセット</button>
                </c:if>
                <button type="submit" class="btn btn-primary">${ not empty isAdd ? 'テスト作成' : '修正'}</button>
                <input type="hidden" name="testId" value="${ data.testId }">
                <c:if test="${ empty isAdd }">
                  <input type="hidden" name="courseId" value="${ data.courseId }">
                  <input type="hidden" name="unitId" value="${ data.unitId }">
                  <input type="hidden" name="updateNumber" value="${ data.updateNumber }">
                  <button formaction="delete-test-manage" class="btn btn-danger" onclick="return deleteConfirm()">削除</button>
                </c:if>
              </div>
            </div>
          </form>
        </div>
      </div>
    </div>
  </body>
</html>