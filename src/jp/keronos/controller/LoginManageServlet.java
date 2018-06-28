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
import jp.keronos.dao.SystemManageDao;
import jp.keronos.dto.SystemManageDto;


/**
 * Servlet implementation class LoginUserServlet
 */
@WebServlet(urlPatterns={"/login-manage"}, initParams={@WebInitParam(name="password", value="password")})
public class LoginManageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(LoginManageServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // トップページに遷移する
        response.sendRedirect("index-manage.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // フォームのデータを取得する
        String manageId = request.getParameter("id");
        String managePassword = request.getParameter("password");
        String uri = request.getParameter("uri");

        if (uri == null) {
            uri = "index-manage.jsp";
        }
        // セッションを取得する
        HttpSession session = request.getSession(true);

        // ログインID、パスワードが未入力の場合
        if ("".equals(manageId) || "".equals(managePassword)) {
            logger.warn("ログイン失敗 {}", request.getRemoteAddr());

            session.setAttribute("errorMessage", "運営者ID、パスワードを入力してください");

            // ログイン処理前にページ情報が存在しない場合は利用者ログインページに遷移する
            response.sendRedirect(uri);
            return;
        }

        try (Connection conn = DataSourceManager.getConnection()) {

            // ログイン処理
            SystemManageDao loginDao = new SystemManageDao(conn);
            SystemManageDto manageDto = loginDao.findByIdAndPassword(manageId, managePassword);

            session.setAttribute("manage", manageDto);
            session.removeAttribute("errorMessage");

            // ログイン失敗時
            if (manageDto == null) {
                logger.warn("ログイン失敗 {} id={} pass={}", request.getRemoteAddr(), manageId, managePassword);
                request.setAttribute("errorMessage", "IDまたはパスワードが間違っています");
            }

            // もしエラーメッセージがある場合はログインページに遷移する
            if (request.getAttribute("errorMessage") != null) {
                //logger.debug(uri);
                request.getRequestDispatcher("WEB-INF/login-manage.jsp").forward(request, response);
                return;
            }

            // 初回ログイン時
            if (adminDto.getUpdateNumber() == 0) {
                request.getRequestDispatcher("WEB-INF/change-manage-password.jsp").forward(request, response);
                return;
            }

            request.getRequestDispatcher("list-response").forward(request, response);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error-manage.jsp").forward(request, response);
        }
    }
}


