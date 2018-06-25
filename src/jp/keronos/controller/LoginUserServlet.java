package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
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
 * Servlet implementation class LoginUserServlet
 */
@WebServlet(urlPatterns={"/login-user"}, initParams={@WebInitParam(name="password", value="password")})
public class LoginUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(LoginUserServlet.class);

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

        // フォームのデータを取得する
        String userId = request.getParameter("id");
        String userPassword = request.getParameter("password");
        String uri = request.getParameter("uri");

        // セッションを取得する
        HttpSession session = request.getSession(true);

        // ログインID、パスワードが未入力の場合
        if ("".equals(userId) || "".equals(userPassword)) {
            logger.warn("ログイン失敗 {}", request.getRemoteAddr());

            session.setAttribute("navbarMessage", "利用者ID、パスワードを入力してください");

            // ログイン処理前にページ情報が存在しない場合は利用者ログインページに遷移する
            response.sendRedirect(uri);
            return;
        }


        try (Connection conn = DataSourceManager.getConnection()) {

            // ログイン処理
            UserDao loginDao = new UserDao(conn);
            UserDto userDto = loginDao.findByIdAndPassword(userId, userPassword);

            session.setAttribute("user", userDto);
            session.removeAttribute("navbarMessage");

            // ログイン失敗時
            if (userDto == null) {
                logger.warn("ログイン失敗 {} id={} pass={}", request.getRemoteAddr(), userId, userPassword);
                session.setAttribute("navbarMessage", "利用者IDまたはパスワードが間違っています");
            }

            // 初回ログイン時
            if (getInitParameter("password").equals(userPassword)) {
                request.getRequestDispatcher("WEB-INF/change-user-password.jsp").forward(request, response);
                return;
            }

            // ログイン処理前にページ情報が存在しない場合は利用者ログインページに遷移する
            logger.debug(uri);
            response.sendRedirect(uri);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}