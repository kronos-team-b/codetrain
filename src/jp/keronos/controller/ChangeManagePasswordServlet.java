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
import jp.keronos.dao.SystemManageDao;
import jp.keronos.dto.SystemManageDto;

/**
 * Servlet implementation class ChangeUserPasswordServlet
 */
@WebServlet("/change-manage-password")
public class ChangeManagePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ChangeManagePasswordServlet.class);

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

        // セッションを取得する
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("manage") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());
            // チャンネル一覧画面に遷移する
            request.getRequestDispatcher("index-manage.jsp").forward(request, response);
            return;
        }

        //セッションのログイン情報を取得する
        SystemManageDto login_dto = (SystemManageDto) session.getAttribute("manage");
        String manage_id = login_dto.getManageId();

        // フォームのデータを取得する
        request.setCharacterEncoding("UTF-8");
        SystemManageDto dto = new SystemManageDto();
        dto.setManageId(manage_id);
        String existpass = request.getParameter("existing-password");
        String changepass = request.getParameter("change-password");
        dto.setPassword(request.getParameter("confirm-password"));

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            // パスワードチェック
            SystemManageDao loginDao = new SystemManageDao(conn);
            SystemManageDto manageDto = loginDao.findByIdAndPassword(manage_id, existpass);

            session.setAttribute("manage", manageDto);
            session.removeAttribute("errorMessage");

            // パスワードがおかしいとき
            if (manageDto == null) {
                logger.warn(" {} id={} pass={}", request.getRemoteAddr(), manage_id, existpass);
                request.setAttribute("errorMessage", "既存パスワードが間違っています");
            }
        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);}

        //入力チェック
        if("".equals(existpass) || "".equals(changepass) || "".equals(dto.getPassword())) {

            request.setAttribute("errorMessage", "パスワードを入力してください");
        }

        if(existpass.length() > 30 | changepass.length() > 30) {
            request.setAttribute("errorMessage", "パスワードを30文字以内で入力してください");
        }

        if(!(changepass.equals(dto.getPassword()))) {
            request.setAttribute("errorMessage", "確認パスワードと変更パスワードは一致していません");
        }

        // エラーメッセージがある場合に実行
        if (request.getAttribute("errorMessage") != null) {

            request.getRequestDispatcher("WEB-INF/change-manage-password.jsp").forward(request, response);
            return;
        }

        // コネクションを取得する
        try (Connection conn2 = DataSourceManager.getConnection()) {
            // パスワードを更新する
            SystemManageDao dao2 = new SystemManageDao(conn2);
            dao2.updatePassword(dto);

            // 更新メッセージをリクエストスコープに保持する
            request.setAttribute("message", "パスワードを更新しました");

            // トップページに遷移する
            request.getRequestDispatcher("list-response").forward(request, response);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error-manage.jsp").forward(request, response);
        }
    }
}