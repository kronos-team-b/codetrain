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
        //テストのためにコメントアウト
        //unitDto.setUnitId(Integer.parseInt(request.getParameter("unitId")));
        //ここまで

        //テストのためについか
        unitDto.setUnitId(2);
        //ここまで

        // コネクションを取得する
        try (Connection conn = DataSourceManager.getConnection()) {

            //カリキュラムIDに紐づくカリキュラム情報リストを取得すし、リクエストスコープにナレッジ情報リストを保持する
            UnitDao unitDao = new UnitDao(conn);
            unitDto = unitDao.selectByUnitId(unitDto);

            request.setAttribute("unitDto", unitDto);

            // セッションを取得する
            HttpSession session2 = request.getSession(true);
            session2.removeAttribute("queries");

            // コース情報を取得する
            courseDto.setCourseId(unitDto.getCourseId());
            CourseDao courseDao = new CourseDao(conn);
            courseDto = courseDao.selectByCourseId(courseDto);

            // チャンネル一覧データをリクエストに保持する
            request.setAttribute("courseDto", courseDto);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
            return;
        }

        // view-unit.jspに転送する
        request.getRequestDispatcher("/WEB-INF/view-unit.jsp").forward(request, response);
    }
}
