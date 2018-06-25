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
import jp.keronos.dao.UserDao;
import jp.keronos.dto.CorporateDto;
import jp.keronos.dto.UserDto;

/**
 * レスポンスの追加
 * @author Hiroki Nishio
 */

/**
 * Servlet implementation class FormChannelServlet
 */
@WebServlet("/add-user")
public class AddUserServlet extends HttpServlet  {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(AddUserServlet.class);

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

        // ログインユーザ情報を取得する
        CorporateDto admin = (CorporateDto)session.getAttribute("admin");

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            //ログインユーザのIDをゲットする
            CorporateDao adminDao = new CorporateDao(conn);

            admin = adminDao.findByCorporateId(admin);

            // フォームのデータを取得する
            UserDto userDto = new UserDto();
            request.setCharacterEncoding("UTF-8");
            UserDao userDao = new UserDao(conn);

            String userId = request.getParameter("userId");
            String userLastName = request.getParameter("userLastName");
            String userFirstName = request.getParameter("userFirstName");
            userDto.setUserId(userId);
            userDto.setLastName(userLastName);
            userDto.setFirstName(userFirstName);
            userDto.setCorporateNo(admin.getCorporateNo());
            userDao.insertByCorporateNo(userDto);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            // 更新メッセージをリクエストスコープに保持する
            request.setAttribute("message", "ID=" + userId + "で"
                + userLastName + userFirstName + "さんを利用者として追加しました");

            // ユーザ一覧画面に遷移する
            request.getRequestDispatcher("list-user").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}
