package jp.keronos.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Servlet implementation class FormContactServlet
 */
@WebServlet("/form-change-manage-password")
public class FormChangeManagePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(FormChangeManagePasswordServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションを取得する
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manage") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // ログインページに遷移する
            request.getRequestDispatcher("index-manage.jsp").forward(request, response);
            return;
        }

        // パスワード変更画面に遷移する
        request.getRequestDispatcher("WEB-INF/change-manage-password.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        doGet(request, response);
    }
}