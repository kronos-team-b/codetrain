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
import jp.keronos.dao.UnitDao;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;

/**
 * Servlet implementation class FormUnitTestServlet
 */
@WebServlet("/form-unit-test")
public class FormUnitTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FormUnitTestServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int unitId = Integer.parseInt(request.getParameter("unitId"));
        int testType = Integer.parseInt(request.getParameter("testType"));

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitDto unitDto = new UnitDto();
            unitDto.setUnitId(unitId);

            UnitDao unitDao = new UnitDao(connection);
            UnitDto unit = unitDao.selectByUnitId(unitDto);

            UnitTestDto unitTestDto = new UnitTestDto();
            unitTestDto.setUnitId(unitId);

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            ArrayList<UnitTestDto> unitTestList = unitTestDao.selectByUnitId(unitTestDto);

            int unitTestAmount = unitTestDao.count(courseId);

            ArrayList<UnitTestChoicesDto> choicesList = unitTestDao.selectUnitTestChoise();

            request.setAttribute("unit", unit);
            request.setAttribute("unitTestAmount", unitTestAmount);
            request.setAttribute("courseId", courseId);
            request.setAttribute("testType", testType);
            request.setAttribute("unitTestList", unitTestList);
            request.setAttribute("choicesList", choicesList);

            session.setAttribute("sessionUnitTestList", unitTestList);
            session.setAttribute("sessionChoicesList", choicesList);


        } catch (SQLException | NamingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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
