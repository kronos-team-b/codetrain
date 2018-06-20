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
 * Servlet implementation class ViewKnowledge
 */
@WebServlet("/view-test-manage")
public class ViewTestManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ViewTestManageServlet.class);


    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start:{} {}", Thread.currentThread().getStackTrace()[1].getMethodName(), request.getRemoteAddr());

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

            // トップ画面に遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // ログインユーザ情報を取得する
         SystemManageDto manage = (SystemManageDto)session.getAttribute("manage");

        // フォームのデータを取得する
        UnitTestDto unitTestDto = new UnitTestDto();
        UnitTestChoicesDto choicesDto = new UnitTestChoicesDto();
        request.setCharacterEncoding("UTF-8");
        int testId = Integer.parseInt(request.getParameter("testId"));
        unitTestDto.setTestId(testId);
        //テストのためにコメントアウト
        //unitTestDto.setManageNo(manage.getManageNo());
        //ここまで
        choicesDto.setTestId(testId);

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {

            // テスト情報を取得する
             UnitTestDao unitTestDao = new UnitTestDao(conn);
             UnitTestChoicesDao choicesDao = new UnitTestChoicesDao(conn);

             unitTestDto = unitTestDao.selectByTestId(unitTestDto);
             choicesDto = choicesDao.selectByTestId(choicesDto);

             request.setAttribute("testData", unitTestDto);
             request.setAttribute("choicesData", choicesDto);

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

        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
            return;
        }

        // テスト編集画面に遷移する
        request.getRequestDispatcher("WEB-INF/view-test-manage.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
