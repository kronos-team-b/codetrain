package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.keronos.DataSourceManager;
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dao.UnitTestChoicesDao;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dto.CourseDto;
import jp.keronos.dto.SystemManageDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;

/**
 * Servlet implementation class AddKnowledgeServlet
 */
@WebServlet("/add-test-manage")
public class AddTestManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(AddTestManageServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // トップページに遷移する
        response.sendRedirect("index.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // セッションを取得する
        //テストのためにコメントアウト
        //HttpSession session = request.getSession(false);
        //ここまで

        //テストのために代入
        HttpSession session = request.getSession(true);
        //ここまで

        //テストのためにコメントアウト
        //if (session == null || session.getAttribute("manage") == null) {
        //ここまで

        //テストのために代入
        if (session == null ) {
        //ここまで
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // トップページに遷移する
            //TODO トップページのURLを代入する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // セッションからログインユーザ情報を取得する
        SystemManageDto manage = (SystemManageDto)session.getAttribute("manage");

        // フォームのデータを取得する
        request.setCharacterEncoding("UTF-8");
        UnitTestDto unitTestDto = new UnitTestDto();
        UnitTestChoicesDto choicesDto = new UnitTestChoicesDto();

        // 入力チェック1
        if ("".equals(request.getParameter("courseId"))
                || request.getParameter("courseId") == null) {
            request.setAttribute("errorMessage", "コースを選択してください");
        } else {
            unitTestDto.setCourseId(Integer.parseInt(request.getParameter("courseId")));
        }
        if ("".equals(request.getParameter("unitId"))
                || request.getParameter("unitId") == null) {
            request.setAttribute("errorMessage", "カリキュラムを選択してください");
        } else {
            unitTestDto.setUnitId(Integer.parseInt(request.getParameter("unitId")));
        }
        if ("".equals(request.getParameter("answerType"))
                || request.getParameter("answerType") == null) {
            request.setAttribute("errorMessage", "回答方式を選択してください");
        } else {
            unitTestDto.setAnswerTypeFlg(Integer.parseInt(request.getParameter("answerType")));
        }

        unitTestDto.setTestTitle(request.getParameter("testTitle"));
        unitTestDto.setTestContent(request.getParameter("testContent"));
        // テストのためにコメントアウト
        //unitTestDto.setManageNo(manage.getManageNo());
        // ここまで
      //テストのために代入
        unitTestDto.setManageNo(1);
        // ここまで
        unitTestDto.setModelAnswer(request.getParameter("modelAnswer"));

        // もし選択問題なら、選択問題の問を取得
        if (unitTestDto.getAnswerTypeFlg() == 0) {
            choicesDto.setChoice1(request.getParameter("choice1"));
            choicesDto.setChoice2(request.getParameter("choice2"));
            choicesDto.setChoice3(request.getParameter("choice3"));
            choicesDto.setChoice4(request.getParameter("choice4"));
        }

        // 入力チェック2
        if ("".equals(unitTestDto.getTestTitle())) {
            request.setAttribute("errorMessage", "テストの問を入力してください");
        }
        if (unitTestDto.getTestTitle().length() > 30) {
            request.setAttribute("errorMessage", "テストの問は30文字以内で入力してください");
        }
        if (unitTestDto.getAnswerTypeFlg() == 0) {
            if ("".equals(unitTestDto.getModelAnswer())) {
                request.setAttribute("errorMessage", "正解番号を選択してください");
            }
            if ("".equals(choicesDto.getChoice1())
                    || "".equals(choicesDto.getChoice2())
                    || "".equals(choicesDto.getChoice3())
                    || "".equals(choicesDto.getChoice4())) {
                request.setAttribute("errorMessage", "選択問題を入力してください");
            }
        }

        // エラーメッセージがある場合に実行
        if (request.getAttribute("errorMessage") != null) {

            // フォームのデータをリクエストスコープに保持する
            request.setAttribute("testData", unitTestDto);

            if (unitTestDto.getAnswerTypeFlg() == 0) {
                request.setAttribute("choicesData", choicesDto);
            }

            // すべてのコースID＆コース名、単元ID＆単元名を取得する
            // コネクションを取得する
            try (Connection conn = DataSourceManager.getConnection()) {
                // セッションを取得する
                session.removeAttribute("queries");

                // コース情報を取得する
                CourseDao courseDao = new CourseDao(conn);
                request.setCharacterEncoding("UTF-8");
                ArrayList<CourseDto> arrayCourseList = courseDao.selectAll();

                //コース一覧をリクエストに保持する
                request.setAttribute("courseList", arrayCourseList);

                //単元情報を取得する
                UnitDao unitDao = new UnitDao(conn);
                request.setCharacterEncoding("UTF-8");
                ArrayList<UnitDto> arrayUnitList = unitDao.selectAll();

                //単元一覧をリクエストに保持する
                request.setAttribute("unitList", arrayUnitList);

                // URIをリクエストに保持する
                request.setAttribute("uri", request.getRequestURI());

                request.setAttribute("isAdd", true);

            } catch (SQLException | NamingException e) {

                logger.error("{} {}", e.getClass(), e.getMessage());

                // システムエラー画面に遷移する
                request.getRequestDispatcher("system-error.jsp").forward(request, response);
            }

            request.getRequestDispatcher("WEB-INF/view-test-manage.jsp").forward(request, response);
            return;
        }

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {

            // テストを追加する
            UnitTestDao unitTestDao = new UnitTestDao(conn);
            int testId = unitTestDao.insert(unitTestDto);
            if (unitTestDto.getAnswerTypeFlg() == 0) {
                // テストの選択肢を追加する
                UnitTestChoicesDao choicesDao = new UnitTestChoicesDao(conn);
                choicesDao.insert(testId, choicesDto);
            }

            // 追加メッセージ
            request.setAttribute("message", "テストを追加しました");

        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // テスト問が重複している場合
            if (e.getMessage().contains("Duplicate entry")) {

                // エラーメッセージをリクエストスコープに保持する
                request.setAttribute("errorMessage", "該当のテストは既に存在します");

                // フォームのデータをリクエストスコープに保持する
                request.setAttribute("testData", unitTestDto);
                if (unitTestDto.getAnswerTypeFlg() == 0) {
                    request.setAttribute("choicesData", choicesDto);
                }

                // ナレッジ登録画面に遷移する
                request.getRequestDispatcher("form-test-manage").forward(request, response);
                return;
            } else {

                // システムエラー画面に遷移する
                request.getRequestDispatcher("system-error.jsp").forward(request, response);
                return;
            }
        }

        // コース一覧画面に遷移する
        request.getRequestDispatcher("view-text-manage").forward(request, response);
    }

}
