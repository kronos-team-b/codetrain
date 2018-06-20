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
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.LearningCourseDao;
import jp.keronos.dao.LearningHistoryDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dto.LearningCourseDto;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class ListCourseServlet
 */
@WebServlet("/list-course")
public class ListCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListCourseServlet() {
        super();

    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        try (Connection connection = DataSourceManager.getConnection()) {

            CourseDao courseDao = new CourseDao(connection);
            ArrayList<Object> courseList = courseDao.selectCourseList();
            ArrayList<Object> list = new ArrayList<>();

            if (session != null && session.getAttribute("user") != null) {

                UserDto user = (UserDto)session.getAttribute("user");

                int userNo = user.getUserNo();
                ArrayList<Integer> courseIds = courseDao.selectCourseIds();

                // コースの数だけループして、コースリストと進捗、合否を結合させる
                int i = 0;
                for(Object courseInfo : courseList) {

                    Object[] subList = {
                            courseInfo,
                            calculateProgress(connection, userNo, courseIds.get(i)),
                            judgePassingStatus(connection, userNo, courseIds.get(i))
                    };

                    list.add(subList);
                    ++i;
                }
            } else {

                for(Object courseInfo : courseList) {

                    // オブジェクト配列の形合わせ
                    Object[] subList = {
                            courseInfo
                    };

                    list.add(subList);
                }
            }

            request.setAttribute("list", list);
            request.getRequestDispatcher("WEB-INF/list-course.jsp").forward(request, response);

        } catch (SQLException | NamingException e) {
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        doGet(request, response);
    }

    private int calculateProgress(Connection connection, int userNo, int courseId) {

        UnitDao unitDao = new UnitDao(connection);
        LearningHistoryDao learningHistoryDao = new LearningHistoryDao(connection);

        int countUnit = 0;
        int countLearningHistory = 0;

        try {
            countUnit = unitDao.count(courseId);
            countLearningHistory = learningHistoryDao.count(userNo);

        } catch (SQLException e) {

            return 0;
        }

        if (countUnit == 0) {

            return 0;
        }

        return countLearningHistory / countUnit;
    }

    private ArrayList<LearningCourseDto> judgePassingStatus(Connection connection, int userNo, int courseId) {

        LearningCourseDao learningCourseDao = new LearningCourseDao(connection);

        ArrayList<LearningCourseDto> passFlag = null;

        try {
            passFlag = learningCourseDao.selectPassLFlugByUserNo(userNo, courseId);
        } catch (SQLException e) {

            return null;
        }

        return passFlag;

    }
}
