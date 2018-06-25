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

import jp.keronos.dao.UserDao;
import jp.keronos.dto.UserDto;
import jp.keronos.DataSourceManager;
/**
 * Servlet implementation class ChangeUserPasswordServlet
 */
@WebServlet("/change-user-password")
public class ChangeUserPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ChangeUserPasswordServlet.class);

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

        // セッションを取得する
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());
            // チャンネル一覧画面に遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //セッションのログイン情報を取得する
        UserDto login_dto = (UserDto) session.getAttribute("user");
        String user_id = login_dto.getUserId();

        // フォームのデータを取得する
        request.setCharacterEncoding("UTF-8");
        UserDto dto = new UserDto();
        dto.setUserId(user_id);
        String existpass = request.getParameter("existing-password");
        String changepass = request.getParameter("change-password");
        dto.setPassword(request.getParameter("confirm-password"));

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            // パスワードチェック
            UserDao loginDao = new UserDao(conn);
            UserDto userDto = loginDao.findByIdAndPassword(user_id, existpass);

            request.setAttribute("user", userDto);
            request.removeAttribute("errorMessage");

            // パスワードがおかしいとき
            if (userDto == null) {
                logger.warn("ログイン失敗 {} mail={} pass={}", request.getRemoteAddr(), user_id, existpass);
                request.setAttribute("errorMessage", "既存のパスワードが間違っています");
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

            request.getRequestDispatcher("WEB-INF/change-user-password.jsp").forward(request, response);
            return;
        }

        // コネクションを取得する
        try (Connection conn2 = DataSourceManager.getConnection()) {
            // パスワードを更新する
            UserDao dao2 = new UserDao(conn2);
            dao2.updatePassword(dto);

            // 更新メッセージをリクエストスコープに保持する
            request.setAttribute("message", "パスワードを更新しました");
            request.removeAttribute("errorMessage");

            // トップページに遷移する
            request.getRequestDispatcher("list-course").forward(request, response);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}

