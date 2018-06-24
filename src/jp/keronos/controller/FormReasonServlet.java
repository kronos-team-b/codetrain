package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.keronos.dao.UserDao;
import jp.keronos.dto.UserDto;


/**
 * Servlet implementation class FormContactServlet
 */
@WebServlet("/form-reason")
public class FormReasonServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(FormReasonServlet.class);

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

        // セッションを取得する

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // ログイン一覧画面に遷移する
            request.getRequestDispatcher("index-admin.jsp").forward(request, response);
            return;
        }

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {

        	// 休止ユーザのNOをゲットする
        	int userNo = Integer.parseInt(request.getParameter("userNo"));
        	
            // ログインユーザ情報を取得する
            UserDto user = new UserDto();
            UserDao userDao = new UserDao(conn);
            
        	user = userDao.findByNo(userNo);
        	
            // コンタクトIDを保持する
            request.setAttribute("user", user);

        	// アカウント休止理由追加画面に遷移する
            request.getRequestDispatcher("view-reason.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}
