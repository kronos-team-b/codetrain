package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.keronos.DataSourceManager;
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dao.UserCourseTestAnswerDao;
import jp.keronos.dto.CourseDto;
import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;
import jp.keronos.dto.UserCourseTestAnswerDto;
import jp.keronos.dto.UserDto;

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

//        if (session == null || session.getAttribute("user") == null) {
//
//            request.getRequestDispatcher("index.jsp").forward(request, response);
//            return;
//        }

        int courseId = 1;

        try(Connection connection = DataSourceManager.getConnection()) {

            CourseDao courseDao = new CourseDao(connection);
            CourseDto course = courseDao.selectByCourseId(courseId);
            request.setAttribute("course", course);

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            int unitTestAmount = unitTestDao.count(courseId);

            ArrayList<UnitTestDto> unitTestList = new ArrayList<>();
            for (int unitId = 1; unitId <= unitTestAmount; unitId++) {
                 unitTestList.add(unitTestDao.randomSelectByUnitId(unitId));
            }
            request.setAttribute("unitTestList", unitTestList);

            ArrayList<UnitTestChoicesDto> choicesList = unitTestDao.selectUnitTestChoise();
            request.setAttribute("choicesList", choicesList);

            request.getRequestDispatcher("WEB-INF/form-course-test.jsp").forward(request, response);

        } catch (SQLException | NamingException e) {

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {

          request.getRequestDispatcher("index.jsp").forward(request, response);
          return;
        }

        request.setCharacterEncoding("UTF-8");

        int courseId = Integer.parseInt(request.getParameter("course-id"));

        String[] answers = request.getParameterValues("answer[]");
        int[] testIds = Stream.of(request.getParameterValues("unit-test-id[]")).mapToInt(Integer::parseInt).toArray();

        UserDto user = (UserDto)session.getAttribute("user");
        int userNo = user.getUserNo();

        ArrayList<UserCourseTestAnswerDto> list = setTestData(courseId, answers, testIds, userNo);

        try(Connection connection = DataSourceManager.getConnection()) {

            UserCourseTestAnswerDao userCourseTestAnswerDao = new UserCourseTestAnswerDao(connection);
            userCourseTestAnswerDao.insert(list);

            request.setAttribute("userAnswerList", list);
            request.getRequestDispatcher("ans-course-test").forward(request, response);

        } catch (SQLException | NamingException e) {

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    private ArrayList<UserCourseTestAnswerDto> setTestData(int courseId, String[] answers, int[] testIds, int userNo) {

        ArrayList<UserCourseTestAnswerDto> list = new ArrayList<>();
        UserCourseTestAnswerDto userCourseTestAnswerDto = null;

        for (int i = 0; i < answers.length; i++) {
            userCourseTestAnswerDto = new UserCourseTestAnswerDto();
            userCourseTestAnswerDto.setCourseId(courseId);
            userCourseTestAnswerDto.setUserAnswer(answers[i]);
            userCourseTestAnswerDto.setTestId(testIds[i]);
            userCourseTestAnswerDto.setUserNo(userNo);
            list.add(userCourseTestAnswerDto);
        }

        return list;
    }

}
