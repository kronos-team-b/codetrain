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
import jp.keronos.dao.InactiveReasonDao;
import jp.keronos.dao.UserDao;
import jp.keronos.dto.UserDto;
import jp.keronos.dto.InactiveReasonDto;

/**
 * リクエストの追加
 * @author Hiroki Nishio
 */

/**
 * Servlet implementation class FormChannelServlet
 */
@WebServlet("/add-reason")
public class AddReasonServlet extends HttpServlet  {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(AddReasonServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            // トップページに遷移する
            request.getRequestDispatcher("index-admin.jsp").forward(request, response);
            return;
        }

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            //　休止ユーザのNOをゲットする
            // ログインユーザ情報を取得する
            UserDao userDao = new UserDao(conn);
            UserDto userDto = new UserDto();
            int userNo = Integer.parseInt(request.getParameter("userNo"));
            userDto.setUserNo(userNo);
            userDto = userDao.findByUserNo(userDto.getUserNo());

            // フォームのデータを取得する
            InactiveReasonDto inactiveReasonDto = new InactiveReasonDto();

            inactiveReasonDto.setReason(request.getParameter("reason"));
            inactiveReasonDto.setUserNo(userDto.getUserNo());
            inactiveReasonDto.setActiveAt(userDto.getUpdateAt());

            InactiveReasonDao inactiveReasonDao = new InactiveReasonDao(conn);
            inactiveReasonDao.insertReason(inactiveReasonDto);

            String inactiveMessage = userDto.getLastName() + userDto.getFirstName() + "さんのアカウントを休止しました";
            // 更新メッセージをリクエストスコープに保持する
            request.setAttribute("message", inactiveMessage);

            // 利用者一覧画面に遷移する
            request.getRequestDispatcher("list-user").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error-admin.jsp").forward(request, response);
        }
    }
}
