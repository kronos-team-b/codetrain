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

import jp.keronos.DataSourceManager;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dto.UnitTestDto;

/**
 * Servlet implementation class FormCourseTestServlet
 */
@WebServlet("/form-course-test")
public class FormCourseTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormCourseTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            int unitTestAmount = unitTestDao.count();

            ArrayList<UnitTestDto> unitTestList = new ArrayList<>();

            for (int unitId = 0; unitId < unitTestAmount; unitId++) {
                 unitTestList.add(unitTestDao.randomSelectByUnitId(unitId));
            }

            request.setAttribute("unitTestList", unitTestList);

            //TODO unit_test_choicesリストを取得（照合はJSPから）

        } catch (SQLException | NamingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //TODO テスト結果を保存する処理

    }

}
