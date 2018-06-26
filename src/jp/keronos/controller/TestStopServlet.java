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

import jp.keronos.DataSourceManager;
import jp.keronos.dao.LearningHistoryDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class TestSkipServlet
 */
@WebServlet("/test-stop")
public class TestStopServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        int unitId = Integer.parseInt(request.getParameter("unit-id"));

        HttpSession session = request.getSession(false);

        UserDto user = (UserDto)session.getAttribute("user");

        int userNo = 0;
        if (user != null) {
            userNo = user.getUserNo();
        }

        userNo = 1;

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitDto unitDto = new UnitDto();
            unitDto.setUnitId(unitId);
            UnitDao unitDao = new UnitDao(connection);
            unitDto = unitDao.selectByUnitId(unitDto);

            LearningHistoryDto learningHistoryDto = new LearningHistoryDto();
            learningHistoryDto.setCourseId(unitDto.getCourseId());
            learningHistoryDto.setUnitId(unitDto.getUnitId());
            learningHistoryDto.setUserNo(userNo);
            learningHistoryDto.setSkipFlg(1);

            LearningHistoryDao learningHistoryDao = new LearningHistoryDao(connection);

            if (userNo != 0) {

                learningHistoryDao.updateSkipFlg(learningHistoryDto);
            }

            int courseId = unitDto.getCourseId();

            request.setAttribute("courseId", courseId);
            session.setAttribute("sessionCourseId", courseId);
            request.getRequestDispatcher("view-course").forward(request, response);;

        } catch (SQLException | NamingException e) {

            e.printStackTrace();
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
