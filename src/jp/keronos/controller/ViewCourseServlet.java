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
import jp.keronos.dao.CategoryDao;
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.LearningHistoryDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dto.CategoryDto;
import jp.keronos.dto.CourseDto;
import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class ViewCourseServlet
 */
@WebServlet("/view-course")
public class ViewCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int courseId;
        if (request.getParameter("courseId") != null) {
            courseId = Integer.parseInt(request.getParameter("courseId"));
        } else {
            courseId = (int)session.getAttribute("sessionCourseId");
        }

        int userNo = 0;
        if (session != null && session.getAttribute("user") != null) {

            UserDto loginUserInfo = (UserDto)session.getAttribute("user");
            userNo = loginUserInfo.getUserNo();
        }

        try(Connection connection = DataSourceManager.getConnection()) {

            // カテゴリ情報をsessionに保存
            CategoryDao categoryDao = new CategoryDao(connection);
            CategoryDto categoryInfo = categoryDao.selectCategoryByCourseId(courseId);
            request.setAttribute("category", categoryInfo);

            // コース情報をsessionに保存
            CourseDao courseDao = new CourseDao(connection);
            CourseDto courseInfo = courseDao.selectByCourseId(courseId);
            request.setAttribute("course", courseInfo);

            // カリキュラム情報をsessionに保存
            UnitDao unitDao = new UnitDao(connection);
            ArrayList<UnitDto> unitInfo = unitDao.selectByCourseId(courseId);
            request.setAttribute("units", unitInfo);

            if (userNo != 0) {
                LearningHistoryDto learningHistoryDto = new LearningHistoryDto();
                learningHistoryDto.setUserNo(userNo);
                learningHistoryDto.setCourseId(courseId);

                LearningHistoryDao learningHistoryDao = new LearningHistoryDao(connection);

                boolean existsLearningHistory = learningHistoryDao.existsByUserNoAndCourseID(learningHistoryDto);

                if (existsLearningHistory == true) {

                    ArrayList<LearningHistoryDto> learnedInfo = learningHistoryDao.selectLearned(userNo, courseId);
                    request.setAttribute("learnedInfo", learnedInfo);
                }

                // 続きから判定
                UnitDto next = learningHistoryDao.selectMinUnitIdByEndFlg(userNo, courseId);
                request.setAttribute("next", next);
            }

            request.getRequestDispatcher("WEB-INF/view-course.jsp").forward(request, response);;

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
