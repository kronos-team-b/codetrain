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

import jp.keronos.dto.CourseDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.DataSourceManager;
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.UnitDao;


/**
 * テスト作成機能
 * @author Hiroki Nishio
 */

/**
 * Servlet implementation class FormTestManagelServlet
 */
@WebServlet("/form-test-manage")
public class FormTestManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(FormTestManageServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // トップページに遷移する
        response.sendRedirect("index.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // セッションを取得する
        HttpSession session = request.getSession(false);

        //テストのためにコメントアウト
        //if (session == null || session.getAttribute("manage") == null) {
        //ここまで

        //テストのために代入
        if (session == null ) {
        //ここまで
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // トップページに遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
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
        // テスト作成画面に遷移する
        request.getRequestDispatcher("/WEB-INF/view-test-manage.jsp").forward(request, response);
    }
}
