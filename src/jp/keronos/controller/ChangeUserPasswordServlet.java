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

import jp.keronos.dao.UserDao;
import jp.keronos.dto.UserDto;
import jp.keronos.DataSourceManager;
/**
 * Servlet implementation class ChangeUserPasswordServlet
 */
@WebServlet("/change-user-password")
public class ChangeUserPasswordServlet extends HttpServlet {
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

        // �Z�b�V�������擾����
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            logger.warn("�Z�b�V�����^�C���A�E�g {}", request.getRemoteAddr());
            // �`�����l���ꗗ��ʂɑJ�ڂ���
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //�Z�b�V�����̃��O�C�������擾����
        UserDto login_dto = (UserDto) session.getAttribute("user");
        String user_id = login_dto.getUserId();

        // �t�H�[���̃f�[�^���擾����
        request.setCharacterEncoding("UTF-8");
        UserDto dto = new UserDto();
        dto.setUserNo(Integer.parseInt(request.getParameter("userNo")));
        String existpass = request.getParameter("existing-password");
        String changepass = request.getParameter("change-password");
        dto.setPassword(request.getParameter("confirm-password"));
        dto.setUpdateNumber(Integer.parseInt(request.getParameter("updateNumber")));

        // �R�l�N�V�������擾����
        try (Connection conn = DataSourceManager.getConnection()) {
            // �����p�X���[�h�͐������������m�F����
            UserDao dao = new UserDao(conn);
            dao.findByIdAndPassowrd(user_id, existpass);
        }

        //���̓`�F�b�N
        if("".equals(existpass) | "".equals(changepass) | "".equals(dto.getPassword())) {

            request.setAttribute("message", "�p�X���[�h����͂��Ă�������");
        }

        if(existpass.length() > 30 | changepass.length() > 30) {
            request.setAttribute("message", "�p�X���[�h��30�����ȓ��œ��͂��Ă�������");
        }

        if(!(changepass.equals(dto.getPassword()))) {
            request.setAttribute("message", "�m�F�p�X���[�h�ƕύX�p�X���[�h�͈�v���Ă��܂���");
        }

        // �X�V���b�Z�[�W�����N�G�X�g�X�R�[�v�ɕێ�����
        request.setAttribute("message", "�p�X���[�h���X�V���܂���");

     // �R�l�N�V�������擾����
        try (Connection conn2 = DataSourceManager.getConnection()) {
            // �p�X���[�h���X�V����
            UserDao dao2 = new UserDao(conn2);
            dao2.updatePassword(dto);

        } catch (SQLException | NamingException e) {
            logger.error("{} {}", e.getClass(), e.getMessage());

            // �V�X�e���G���[�ɑJ�ڂ���
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }

        // ���p�҈ꗗ��ʂɑJ�ڂ���
        request.getRequestDispatcher("/list-user").forward(request, response);
    }
}
