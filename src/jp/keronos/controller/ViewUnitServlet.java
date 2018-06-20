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

        // �g�b�v�y�[�W�ɑJ�ڂ���
        response.sendRedirect("index.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        // �t�H�[���̃f�[�^�i�J���L������ID�j���擾����
        UnitDto unitDto = new UnitDto();
        CourseDto courseDto = new CourseDto();
        request.setCharacterEncoding("UTF-8");
        //�e�X�g�̂��߂ɃR�����g�A�E�g
        //unitDto.setUnitId(Integer.parseInt(request.getParameter("unitId")));
        //�����܂�

        //�e�X�g�̂��߂ɂ���
        unitDto.setUnitId(2);
        //�����܂�

        // �R�l�N�V�������擾����
        try (Connection conn = DataSourceManager.getConnection()) {

            //�J���L������ID�ɕR�Â��J���L��������񃊃X�g���擾�����A���N�G�X�g�X�R�[�v�Ƀi���b�W��񃊃X�g��ێ�����
            UnitDao unitDao = new UnitDao(conn);
            unitDto = unitDao.selectByUnitId(unitDto);

            request.setAttribute("unitDto", unitDto);

            // �Z�b�V�������擾����
            HttpSession session2 = request.getSession(true);
            session2.removeAttribute("queries");

            // �R�[�X�����擾����
            courseDto.setCourseId(unitDto.getCourseId());
            CourseDao courseDao = new CourseDao(conn);
            courseDto = courseDao.selectByCourseId(courseDto);

            // �`�����l���ꗗ�f�[�^�����N�G�X�g�ɕێ�����
            request.setAttribute("courseDto", courseDto);

            // URI�����N�G�X�g�ɕێ�����
            request.setAttribute("uri", request.getRequestURI());
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // �V�X�e���G���[��ʂɑJ�ڂ���
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
            return;
        }

        // view-unit.jsp�ɓ]������
        request.getRequestDispatcher("/WEB-INF/view-unit.jsp").forward(request, response);
    }
}
