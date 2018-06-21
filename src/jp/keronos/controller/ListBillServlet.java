package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.keronos.dao.BillDao;
import jp.keronos.dto.BillDto;

/**
 * Servlet implementation class ListBillServlet
 */
@WebServlet("/list-bill")
public class ListBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ListBillServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{} {}", Thread.currentThread().getStackTrace()[1].getMethodName(), request.getRemoteAddr());

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {
            // セッションを取得する
            HttpSession session = request.getSession(true);
            session.removeAttribute("queries");

            //テストのためにコメントアウト
            //会社NOを取得する
   //         int corporateNo = Integer.parseInt(request.getParameter("coporateNo"));

            //会社NOに該当する請求情報リストを取得する
            BillDao dao = new BillDao(conn);
   //         List<BillDto> list = dao.selectByCorporateNo(corporateNo);
            //ここまで

            //テストのためにコメントイン
            List<BillDto> list = dao.selectByCorporateNo(1);

            //リクエストスコープにナレッジ情報リストを保持する
            request.setAttribute("list", list);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());

            //list-bill.jspに転送する
            request.getRequestDispatcher("WEB-INF/list-bill.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
