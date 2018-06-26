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

import jp.keronos.DataSourceManager;
import jp.keronos.dao.CourseDao;
import jp.keronos.dao.LearningHistoryDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dto.CourseDto;
import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UserDto;


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

        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        request.setCharacterEncoding("UTF-8");

        int unitId = Integer.parseInt(request.getParameter("unit-id"));

        HttpSession session = request.getSession(false);

        UserDto user = (UserDto)session.getAttribute("user");

        int userNo = 0;
        if (user != null) {
            userNo = user.getUserNo();
        }
        userNo = 1;

        try (Connection connection = DataSourceManager.getConnection()) {

            UnitDto unitDto = new UnitDto();
            unitDto.setUnitId(unitId);
            UnitDao unitDao = new UnitDao(connection);
            unitDto = unitDao.selectByUnitId(unitDto);

            CourseDto courseDto = new CourseDto();
            courseDto.setCourseId(unitDto.getCourseId());
            CourseDao courseDao = new CourseDao(connection);
            courseDto = courseDao.selectByCourseId(courseDto);

            LearningHistoryDto learningHistoryDto = new LearningHistoryDto();
            learningHistoryDto.setCourseId(courseDto.getCourseId());
            learningHistoryDto.setUnitId(unitDto.getUnitId());
            learningHistoryDto.setUserNo(userNo);

            LearningHistoryDao learningHistoryDao = new LearningHistoryDao(connection);

            boolean existsLearningHistory = learningHistoryDao.exists(learningHistoryDto);
            if (userNo != 0 && existsLearningHistory == false) {

                learningHistoryDao.insertInit(learningHistoryDto);
            }

            request.setAttribute("unitDto", unitDto);
            request.setAttribute("courseDto", courseDto);

            request.getRequestDispatcher("/WEB-INF/view-unit.jsp").forward(request, response);

        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // システムエラー画面に遷移する
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
            return;
        }
    }
}
