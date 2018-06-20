<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><c:out value="${ not empty isAdd ? 'カリキュラムテスト作成' : 'カリキュラムテスト編集'}"/></title>
    <!-- jQuery読み込み -->
    <script type="text/javascript"  src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script type="text/javascript">
    <!--
      function deleteConfirm() {
        return window.confirm("テストを本当に削除しますか？");
      }

      function checkRadioSelect() {
        document.getElementById('select').style.display = 'block';
        document.getElementById('input').style.display = 'none';
      }

      function checkRadioInput() {
          document.getElementById('select').style.display = 'none';
          document.getElementById('input').style.display = 'block';
      }

      $(function() {
        var $children = $('.children'); //単元の要素を変数に入れます。
        var original = $children.html(); //後のイベントで、不要なoption要素を削除するため、オリジナルをとっておく

        //コース側のselect要素が変更になるとイベントが発生
        $('.parent').change(function() {

          //選択されたコースのvalueを取得し変数に入れる
          var val1 = $(this).val();

          //削除された要素をもとに戻すため.html(original)を入れておく
          $children.html(original).find('option').each(function() {
            var val2 = $(this).data('val'); //data-valの値を取得

            //valueと異なるdata-valを持つ要素を削除
            if (val1 != val2) {
              $(this).not(':first-child').remove();
            }

          });

          //コース側のselect要素が未選択の場合、カリキュラム名をdisabledにする
          if ($(this).val() == "") {
            $children.attr('disabled', 'disabled');
          } else {
            $children.removeAttr('disabled');
          }

        });
      });
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
            <p class="h4 mt-3 mb-5 p-3 text-info border-bottom"><c:out value="${ not empty isAdd ? 'カリキュラムテスト追加' : 'カリキュラムテスト編集'}"/></p>
            <div class="form-group row">
              <input type="hidden" name="testId" value="${ testData.testId }">
              <input type="hidden" name="updateNumber" value="${ testData.updateNumber }" />
            </div>
            <div class="form-group row">
              <div class="col-10">
                <select class="parent" id="courseId" name="courseId"  required>
                  <option value="" disabled selected>---コース名---</option>
                  <c:forEach items="${ courseList }" var="coursedto" varStatus="status">
                    <c:choose>
                      <c:when test="${ coursedto.courseId eq testData.courseId }">
                        <option value="${ coursedto.courseId }"  selected>${ coursedto.courseName }</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${ coursedto.courseId }">${ coursedto.courseName }</option>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div class="form-group row">
              <div class="col-10">
                <select class="children" id="unitId" name="unitId" required>
                  <option value="" disabled selected>---カリキュラム名---</option>
                  <c:forEach items="${ unitList }" var="unitdto" varStatus="status">
                    <c:choose>
                      <c:when test="${ unitdto.unitId eq testData.unitId }">
                        <option value ="${ unitdto.unitId }" data-val="${ unitdto.courseId }" selected>${ unitdto.unitTitle }</option>
                      </c:when>
                      <c:otherwise>
                        <option value ="${ unitdto.unitId }" data-val="${ unitdto.courseId }">${ unitdto.unitTitle }</option>
                      </c:otherwise>
                    </c:choose>
                  </c:forEach>
                </select>
              </div>
            </div>
            <div>
              <label></label>
            </div>
            <div class="form-group row">
              <label class="col-form-label col-2" for="testTitle">問</label>
              <div class="col-10">
                <input type="text" id="testTitle" name="testTitle" class="form-control" maxlength="30" value="${ testData.testTitle }" />
              </div>
            </div>
            <div>
              <label>コード（任意）</label>
            </div>
            <div class="form-group row">
              <textarea id="testContent" name="testContent" style="resize: none;" class="form-control" maxlength="255" rows="7"><c:out value="${ testData.testContent }" /></textarea>
            </div>
            <div>
              <label>いずれかを選択してください</label>
            </div>
            <div>

              <c:choose>
                <c:when test="${ unitdto.unitId eq testData.unitId }">
                  <option value ="${ unitdto.unitId }" data-val="${ unitdto.courseId }" selected>${ unitdto.unitTitle }</option>
                </c:when>
                  <c:otherwise>
                    <input type="radio" name="answerType" value="0" onclick="checkRadioSelect();">選択問題
                  </c:otherwise>
              </c:choose>


              <input type="radio" name="answerType" value="0" onclick="checkRadioSelect();">選択問題
            </div>
            <span id="select" style="padding : 20px; display:none;">
                <input type="radio" name="modelAnswer" value="1">1
                <input type="text" id="choice1" name="choice1" class="form-control" maxlength="30" value="${ choicesData.choice1 }" />
                <input type="radio" name="modelAnswer" value="2">2
                <input type="text" id="choice2" name="choice2" class="form-control" maxlength="30" value="${ choicesData.choice2 }" />
                <input type="radio" name="modelAnswer" value="3">3
                <input type="text" id="choice3" name="choice3" class="form-control" maxlength="30" value="${ choicesData.choice3 }" />
                <input type="radio" name="modelAnswer" value="4">4
                <input type="text" id="choice4" name="choice4" class="form-control" maxlength="30" value="${ choicesData.choice4 }" />
            </span>
            <div>
            <input type="radio" name="answerType" value="1" onclick="checkRadioInput();">テキスト入力問題
              <div class="form-group row">
                <span id="input"  style="padding : 30px; display:none;">
                  <label for="modelAnswer" class="form-group row">答え
                    <input type="text" id="modelAnswer" name="modelAnswer" class="form-control" maxlength="30" value="${ testData.modelAnswer }" />
                  </label>
                </span>
              </div>
            </div>
            <div class="form-group row">
              <div class="mx-auto">
                <c:if test="${ empty isAdd }">
                  <button type="reset" class="btn btn-secondary">リセット</button>
                </c:if>
                <button type="submit" class="btn btn-primary">${ not empty isAdd ? 'テスト作成' : '修正'}</button>
                <input type="hidden" name="testId" value="${ testData.testId }">
                <c:if test="${ empty isAdd }">
                  <input type="hidden" name="courseId" value="${ testData.courseId }">
                  <input type="hidden" name="unitId" value="${ testData.unitId }">
                  <input type="hidden" name="updateNumber" value="${ testData.updateNumber }">
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