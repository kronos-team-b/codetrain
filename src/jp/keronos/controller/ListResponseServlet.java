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
import jp.keronos.dao.SystemManageDao;
import jp.keronos.dto.ContactDto;
import jp.keronos.dto.SystemManageDto;

/**
 * Servlet implementation class ListCourseServlet
 */
@WebServlet("/list-response")
public class ListResponseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ListResponseServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

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
            //ログインマナージのNOをゲットする
            SystemManageDao manageDao = new SystemManageDao(conn);

            manage = manageDao.findById(manage.getManageId());

            // フォームのデータを取得する
            ContactDao contactDao = new ContactDao(conn);
            ArrayList<ContactDto> list = contactDao.selectLatestAll();

            // リクエスト一覧データをリクエストに保持する
            request.setAttribute("list", list);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            // チャンネル一覧画面に遷移する
            request.getRequestDispatcher("WEB-INF/list-response.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error-manage.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}



