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
import jp.keronos.dao.CorporateDao;
import jp.keronos.dto.CorporateDto;



/**
 * Servlet implementation class LoginUserServlet
 */
@WebServlet(urlPatterns={"/login-admin"}, initParams={@WebInitParam(name="password", value="000000")})
public class LoginAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(LoginAdminServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // トップページに遷移する
        response.sendRedirect("index-admin.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // フォームのデータを取得する
        String adminId = request.getParameter("id");
        String adminPassword = request.getParameter("password");
        String uri = request.getParameter("uri");

        if (uri == null) {
            uri = "index-admin.jsp";
        }
        // セッションを取得する
        HttpSession session = request.getSession(true);

        // ログインID、パスワードが未入力の場合
        if ("".equals(adminId) || "".equals(adminPassword)) {
            logger.warn("ログイン失敗 {}", request.getRemoteAddr());

            session.setAttribute("errorMessage", "運営者ID、パスワードを入力してください");

            // ログイン処理前にページ情報が存在しない場合は利用者ログインページに遷移する
            response.sendRedirect(uri);
            return;
        }

        try (Connection conn = DataSourceManager.getConnection()) {

            // ログイン処理
            CorporateDao loginDao = new CorporateDao(conn);
            CorporateDto adminDto = loginDao.findByIdAndPassword(adminId, adminPassword);

            session.setAttribute("admin", adminDto);
            session.removeAttribute("errorMessage");

            // ログイン失敗時
            if (adminDto == null) {
                logger.warn("ログイン失敗 {} id={} pass={}", request.getRemoteAddr(), adminId, adminPassword);
                request.setAttribute("errorMessage", "IDまたはパスワードが間違っています");
            }

            // 初回ログイン時
            //if (getInitParameter("password").equals(adminPassword)) {
            if (adminDto.getUpdateNumber() == 0) {
                request.getRequestDispatcher("WEB-INF/change-admin-password.jsp").forward(request, response);
                return;
            }

            // もしエラーメッセージがある場合はログインページに遷移する
            if (request.getAttribute("errorMessage") != null) {
                logger.debug(uri);
                request.getRequestDispatcher("WEB-INF/login-admin.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("list-user").forward(request, response);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}

