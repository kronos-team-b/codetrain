package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.keronos.DataSourceManager;
import jp.keronos.dao.UserDao;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class LoginUserServlet
 */
@WebServlet(urlPatterns={"/login-user"}, initParams={@WebInitParam(name="password", value="000000")})
public class LoginUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(LoginUserServlet.class);

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

        // �t�H�[���̃f�[�^���擾����
        String userId = request.getParameter("user-id");
        String userPassword = request.getParameter("user-password");
        String uri = request.getParameter("uri");

        // �Z�b�V�������擾����
        HttpSession session = request.getSession(true);

        // ���O�C��ID�A�p�X���[�h�������͂̏ꍇ
        if ("".equals(userId) || "".equals(userPassword)) {
            logger.warn("���O�C�����s {}", request.getRemoteAddr());

            session.setAttribute("navbarMessage", "���p��ID�A�p�X���[�h����͂��Ă�������");

            // ���O�C�������O�Ƀy�[�W��񂪑��݂��Ȃ��ꍇ�͗��p�҃��O�C���y�[�W�ɑJ�ڂ���
            response.sendRedirect(uri);
            return;
        }

        try (Connection conn = DataSourceManager.getConnection()) {

            // ���O�C������
            UserDao loginDao = new UserDao(conn);
            UserDto userDto = loginDao.findByIdAndPassword(userId, userPassword);

            session.setAttribute("user", userDto);
            session.removeAttribute("navbarMessage");

            // ���O�C�����s��
            if (userDto == null) {
                logger.warn("���O�C�����s {} id={} pass={}", request.getRemoteAddr(), userId, userPassword);
                session.setAttribute("navbarMessage", "���p��ID�܂��̓p�X���[�h���Ԉ���Ă��܂�");
            }

            // ���񃍃O�C����
            if (getInitParameter("password").equals(userPassword)) {
                request.getRequestDispatcher("WEB-INF/change-user-password.jsp").forward(request, response);
                return;
            }

            // ���O�C�������O�Ƀy�[�W��񂪑��݂��Ȃ��ꍇ�͗��p�҃��O�C���y�[�W�ɑJ�ڂ���
            logger.debug(uri);
            response.sendRedirect(uri);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // �V�X�e���G���[�ɑJ�ڂ���
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

}
