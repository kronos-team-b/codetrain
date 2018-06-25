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
import jp.keronos.dao.ContactDao;
import jp.keronos.dao.SystemManageDao;
import jp.keronos.dto.ContactDto;
import jp.keronos.dto.SystemManageDto;

/**
 * レスポンスの追加
 * @author Hiroki Nishio
 */

/**
 * Servlet implementation class FormChannelServlet
 */
@WebServlet("/add-response")
public class AddResponseServlet extends HttpServlet  {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(AddResponseServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

            // トップページに遷移する
            request.getRequestDispatcher("index-manage.jsp").forward(request, response);
            return;
        }

        // ログインユーザ情報を取得する
        SystemManageDto manage = (SystemManageDto)session.getAttribute("manage");

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            //ログインユーザのIDをゲットする
            SystemManageDao manageDao = new SystemManageDao(conn);

            manage = manageDao.findById(manage.getManageId());

            // フォームのデータを取得する
            ContactDto contactDto = new ContactDto();
            request.setCharacterEncoding("UTF-8");

            contactDto.setContactDetail(request.getParameter("response"));
            contactDto.setContactId(Integer.parseInt(request.getParameter("contactId")));
            contactDto.setManageNo(manage.getManageNo());

            ContactDao contactDao = new ContactDao(conn);
            contactDao.insertResponse(contactDto);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            // コンタクトIDを保持する
            request.setAttribute("contactId", contactDto.getContactId());

            // リクエスト詳細画面に遷移する
            request.getRequestDispatcher("view-response").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

}


