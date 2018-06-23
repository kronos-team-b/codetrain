package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

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
import jp.keronos.dao.ContactDao;
import jp.keronos.dao.UserDao;
import jp.keronos.dto.UserDto;
import jp.keronos.dto.ContactDto;

/**
 * コンタクトの追加
 * @author Hiroki Nishio
 */

/**
 * Servlet implementation class FormChannelServlet
 */
@WebServlet("/add-contact")
public class AddContactServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(AddContactServlet.class);

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

            // トップページに遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        UserDto userDto = (UserDto)session.getAttribute("user");


        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            //ログインユーザのIDをゲットする
            UserDao userDao = new UserDao(conn);
            userDto = userDao.findById(userDto.getUserId());

            // フォームのデータを取得する
            ContactDto contactDto = new ContactDto();
            request.setCharacterEncoding("UTF-8");

            contactDto.setContactDetail(request.getParameter("request"));

            ContactDao contactDao = new ContactDao(conn);
            contactDto.setContactId(contactDao.insertContact(contactDto));
            contactDao.insertRequest(contactDto);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            // コンタクトIDを保持する
            request.setAttribute("contactId", contactDto.getContactId());
            request.setAttribute("message", "リクエストを送信しました。");

            // リクエスト詳細画面に遷移する
            //request.getRequestDispatcher("view-request").forward(request, response);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}
