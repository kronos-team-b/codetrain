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
        dto.setUserNo(Integer.parseInt(request.getParameter("userNo")));
        String existpass = request.getParameter("existing-password");
        String changepass = request.getParameter("change-password");
        dto.setPassword(request.getParameter("confirm-password"));
        dto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            // 既存パスワードは正ししいかを確認する
            UserDao dao = new UserDao(conn);
            dao.findByIdAndPassword(user_id, existpass);
        } catch (SQLException | NamingException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        //入力チェック
        if("".equals(existpass) | "".equals(changepass) | "".equals(dto.getPassword())) {

            request.setAttribute("message", "パスワードを入力してください");
        }

        if(existpass.length() > 30 | changepass.length() > 30) {
            request.setAttribute("message", "パスワードを30文字以内で入力してください");
        }

        if(!(changepass.equals(dto.getPassword()))) {
            request.setAttribute("message", "確認パスワードと変更パスワードは一致していません");
        }

        // 更新メッセージをリクエストスコープに保持する
        request.setAttribute("message", "パスワードを更新しました");

     // コネクションを取得する
        try (Connection conn2 = DataSourceManager.getConnection()) {
            // パスワードを更新する
            UserDao dao2 = new UserDao(conn2);
            dao2.updatePassword(dto);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラーに遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }

        // 利用者一覧画面に遷移する
        request.getRequestDispatcher("/list-user").forward(request, response);
    }
}