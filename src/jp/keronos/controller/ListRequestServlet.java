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
import jp.keronos.dto.ContactDto;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class ListCourseServlet
 */
@WebServlet("/list-request")
public class ListRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ListRequestServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // トップページに遷移する
        response.sendRedirect("index.jsp");
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // セッションを取得する
        // テストのためにコメントアウト
        //HttpSession session = request.getSession(false);
        //if (session == null || session.getAttribute("user") == null) {
        //ここまで

        //テストのためについか
        HttpSession session = request.getSession(true);
        if (session == null) {
        //ここまで
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // トップページに遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //テストのためにコメントアウト
        // ログインユーザ情報を取得する
        //UserDto user = (UserDto)session.getAttribute("user");
        //ここまで

        //テストのために追加
        UserDto user = new UserDto();
        user.setUserId("nobunaga_oda");
        //ここまで

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            //ログインユーザのIDをゲットする
            UserDao userDao = new UserDao(conn);

            user = userDao.findById(user.getUserId());

            // フォームのデータを取得する
            ContactDao contactDao = new ContactDao(conn);
            ContactDto contactDto = new ContactDto();
            ArrayList<ContactDto> list = contactDao.selectLatestByUserNo(userNo);

            
            contactDao.insertRequest(contactDto);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            // コンタクトIDを保持する
            request.setAttribute("contactId", contactDto.getContactId());



            // リクエスト詳細画面に遷移する
            request.getRequestDispatcher("view-request").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

}

