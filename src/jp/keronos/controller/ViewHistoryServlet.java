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
import jp.keronos.dao.CategoryDao;
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dao.UserDao;
import jp.keronos.dto.CategoryDto;
import jp.keronos.dto.LearningCourseDto;
import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class FormContactServlet
 */
@WebServlet("/view-history")
public class ViewHistoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ViewHistoryServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションを取得する
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // ログインページに遷移する
            request.getRequestDispatcher("index-admin.jsp").forward(request, response);
            return;
        }

        int userNo = Integer.parseInt(request.getParameter("userNo"));
        logger.warn("userNumber {}", request.getParameter("userNo"));


        try(Connection connection = DataSourceManager.getConnection()) {

            // カテゴリ情報をsessionに保存
            CategoryDao categoryDao = new CategoryDao(connection);
            ArrayList<CategoryDto> categoryInfo = categoryDao.selectAll();
            request.setAttribute("categories", categoryInfo);

            // コース情報と，ユーザ情報に基づいた学習履歴を取得
            CourseDao courseDao = new CourseDao(connection);
            ArrayList<LearningCourseDto> courseInfo = courseDao.selectAllAndUserHistory(userNo);
            request.setAttribute("courses", courseInfo);

            // カリキュラム情報と，ユーザ情報に基づいた学習履歴を取得
            UnitDao unitDao = new UnitDao(connection);
            ArrayList<LearningHistoryDto> unitInfo = unitDao.selectAllAndUserHistory(userNo);
            request.setAttribute("units", unitInfo);

            // ユーザ情報を取得
            UserDao userDao = new UserDao(connection);
            UserDto userInfo = userDao.findByUserNo(userNo);
            request.setAttribute("user", userInfo);

            request.getRequestDispatcher("WEB-INF/view-history.jsp").forward(request, response);;

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());
        doGet(request, response);
    }
}

