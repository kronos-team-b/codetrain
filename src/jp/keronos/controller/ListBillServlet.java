package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
import jp.keronos.dao.BillDao;
import jp.keronos.dao.CorporateDao;
import jp.keronos.dto.BillDto;
import jp.keronos.dto.CorporateDto;

/**
 * Servlet implementation class ListBillServlet
 */
@WebServlet("/list-bill")
public class ListBillServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ListBillServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{} {}", Thread.currentThread().getStackTrace()[1].getMethodName(), request.getRemoteAddr());

        // �Z�b�V�������擾����
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            logger.warn("�Z�b�V�����^�C���A�E�g {}", request.getRemoteAddr());
            // �g�b�v�y�[�W�ɑJ�ڂ���
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        //�Z�b�V�����̃��O�C�������擾����
        CorporateDto login_dto = (CorporateDto) session.getAttribute("admin");
        String corporate_id = login_dto.getCorporateId();

        // �R�l�N�V�������擾����
        try (Connection conn = DataSourceManager.getConnection()) {

            //���ID�ɊY��������NO���擾����
            CorporateDto corporateDto = new CorporateDto();

            corporateDto.setCorporateId(corporate_id);

            CorporateDao corporateDao = new CorporateDao(conn);
            corporateDto = corporateDao.selectByCorporateId(corporateDto);
            int corporate_no = corporateDto.getCorporateNo();

            //���NO�ɊY�����鐿����񃊃X�g���擾���A���N�G�X�g�X�R�[�v�ɕێ�����
            BillDto billDto = new BillDto();
            billDto.setCorporateNo(corporate_no);

            BillDao billDao = new BillDao(conn);
            List<BillDto> list = billDao.selectByCorporateNo(corporate_no);

            request.setAttribute("list", list);

            // URI�����N�G�X�g�ɕێ�����
            request.setAttribute("uri", request.getRequestURI());

            //list-bill.jsp�ɓ]������
            request.getRequestDispatcher("WEB-INF/list-bill.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            // �V�X�e���G���[��ʂɑJ�ڂ���
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
