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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.keronos.DataSourceManager;
import jp.keronos.dao.BillDao;
import jp.keronos.dto.BillDto;

/**
 * Servlet implementation class ListBillDetailServlet
 */
@WebServlet("/list-bill-detail")
public class ListBillDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ListBillDetailServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // セッションを取得する
        /*HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());
            // トップページに遷移する
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }*/

        try (Connection conn = DataSourceManager.getConnection()) {

            //請求IDを取得する
            int billingId = Integer.parseInt(request.getParameter("billingId"));

            //請求IDに該当する請求情報を取得する
            BillDto billDto = new BillDto();
            billDto.setBillingId(billingId);

            BillDao billDao = new BillDao(conn);
            billDto = billDao.selectByBillingId(billDto);

            request.setAttribute("billDto", billDto);

            //請求額を算出する
            int price = billDto.getPrice();
            int inactive_price = price / 2;
            double tax = billDto.getTax();
            int number_of_active_acount = billDto.getNumberOfActiveAccount();
            int number_of_inactive_acount = billDto.getNumberOfInactiveAccount();

            double tax_for_active_price = price * number_of_active_acount * tax;
            double tax_for_inactive_price = price / 2 * number_of_inactive_acount * tax;
            double total_price_with_tax = (price * number_of_active_acount + price / 2 * number_of_inactive_acount) * (1 + tax);

            request.setAttribute("price", price);
            request.setAttribute("inactive_price", inactive_price);
            request.setAttribute("tax", tax);
            request.setAttribute("number_of_active_acount", number_of_active_acount);
            request.setAttribute("number_of_inactive_acount", number_of_inactive_acount);
            request.setAttribute("tax_for_active_price", tax_for_active_price);
            request.setAttribute("tax_for_inactive_price", tax_for_inactive_price);
            request.setAttribute("total_price_with_tax", total_price_with_tax);

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
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
