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
import jp.keronos.dao.CorporateDao;
import jp.keronos.dto.CorporateDto;
/**
 * Servlet implementation class ChangeUserPasswordServlet
 */
@WebServlet("/change-admin-password")
public class ChangeAdminPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ChangeAdminPasswordServlet.class);

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

        // セッションを取得する
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());
            // ログイン画面に遷移する
            request.getRequestDispatcher("index-admin.jsp").forward(request, response);
            return;
        }

        //セッションのログイン情報を取得する
        CorporateDto login_dto = (CorporateDto) session.getAttribute("admin");
        String admin_id = login_dto.getCorporateId();

        // フォームのデータを取得する
        request.setCharacterEncoding("UTF-8");
        CorporateDto dto = new CorporateDto();
        dto.setCorporateId(admin_id);
        String existpass = request.getParameter("existing-password");
        String changepass = request.getParameter("change-password");
        dto.setPassword(request.getParameter("confirm-password"));

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            // パスワードチェック
            CorporateDao loginDao = new CorporateDao(conn);
            CorporateDto adminDto = loginDao.findByIdAndPassword(admin_id, existpass);

            session.setAttribute("admin", adminDto);
            session.removeAttribute("errorMessage");

            // パスワードがおかしいとき
            if (adminDto == null) {
                logger.warn("ｐ {} id={} pass={}", request.getRemoteAddr(), admin_id, existpass);
                request.setAttribute("errorMessage", "既存パスワードが間違っています");
            }
        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);}

        //入力チェック
        if("".equals(existpass) | "".equals(changepass) | "".equals(dto.getPassword())) {

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

            request.getRequestDispatcher("WEB-INF/change-admin-password.jsp").forward(request, response);
            return;
        }

        // コネクションを取得する
        try (Connection conn2 = DataSourceManager.getConnection()) {
            // パスワードを更新する
            CorporateDao dao2 = new CorporateDao(conn2);
            dao2.updatePassword(dto);

            // 更新メッセージをリクエストスコープに保持する
            request.setAttribute("message", "パスワードを更新しました");

            // トップページに遷移する
            request.getRequestDispatcher("list-user").forward(request, response);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}

