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
import jp.keronos.dto.UserDto;
import jp.keronos.dto.ContactDto;

/**
 * リクエスト詳細確認および返信機能
 * @author Hiroki Nishio
 */

/**
 * Servlet implementation class ViewRequestServlet
 */
@WebServlet("/view-request")
public class ViewRequestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ViewRequestServlet.class);

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

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {

            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // トップページに遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        // ログインユーザ情報を取得する
        UserDto userDto = (UserDto)session.getAttribute("user");

        // フォームのデータを取得する
        ContactDto dto = new ContactDto();
        request.setCharacterEncoding("UTF-8");

        int contactId = Integer.parseInt(request.getParameter("contactId"));


        dto.setContactId(contactId);
        dto.setUserNo(userDto.getUserNo());

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {

            // コンタクト情報を取得する
            ContactDao dao = new ContactDao(conn);
            request.setCharacterEncoding("UTF-8");

            ArrayList<ContactDto> arrayContactList = dao.selectByContactId(contactId);

            // コンタクト一覧データをリクエストに保持する
            request.setAttribute("contactDetailList", arrayContactList);

            // コンタクトIDをリクエストに保持する
            request.setAttribute("contactId", dto.getContactId());

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            // コンタクト詳細表に遷移する
            request.getRequestDispatcher("/WEB-INF/view-request-detail.jsp").forward(request, response);

        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
}