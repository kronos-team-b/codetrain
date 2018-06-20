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

import jp.keronos.dao.CourseDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dto.CourseDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.DataSourceManager;


/**
 * Servlet implementation class ViewUnitServlet
 */
@WebServlet("/view-unit")
public class ViewUnitServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ViewUnitServlet.class);

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

        // フォームのデータ（カリキュラムID）を取得する
        UnitDto unitDto = new UnitDto();
        CourseDto courseDto = new CourseDto();
        request.setCharacterEncoding("UTF-8");
        unitDto.setUnitId(Integer.parseInt(request.getParameter("unitId")));

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {

            //カリキュラムIDに紐づくカリキュラム情報リストを取得すし、リクエストスコープにナレッジ情報リストを保持する
            UnitDao dao = new UnitDao(conn);
            unitDto = dao.selectByUnitId(unitDto);

            request.setAttribute("unitData", unitDto);

            // セッションを取得する
            HttpSession session2 = request.getSession(true);
            session2.removeAttribute("queries");

            // コース一覧を取得する
            CourseDao dao2 = new CourseDao(conn);
            courseDto = dao2.selectByCourseId(courseDto);

            // チャンネル一覧データをリクエストに保持する
            request.setAttribute("courseData", courseDto);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
            return;
        }

        // view-knowledge.jspに転送する
        request.getRequestDispatcher("view-unit.jsp").forward(request, response);
    }
}
