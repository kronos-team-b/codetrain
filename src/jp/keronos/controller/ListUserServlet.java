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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.keronos.DataSourceManager;
import jp.keronos.dao.CorporateDao;
import jp.keronos.dao.UserDao;
import jp.keronos.dto.CorporateDto;
import jp.keronos.dto.UserDto;

@WebServlet("/list-user")
public class ListUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private Logger logger = LoggerFactory.getLogger(ListUserServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("start:{}", Thread.currentThread().getStackTrace()[1].getMethodName());

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {

            logger.warn("セッションタイムアウト {}", request.getRemoteAddr());

            // トップページに遷移
            request.getRequestDispatcher("index-admin.jsp").forward(request, response);
            return;
        }

        //　ログイン情報（コーポレートId取得）
        CorporateDto login_dto = (CorporateDto) session.getAttribute("admin");
        String corporateId = login_dto.getCorporateId();


        try (Connection conn = DataSourceManager.getConnection()) {

            //会社IDに該当する会社NOを取得する
            CorporateDto corporateDto = new CorporateDto();
            corporateDto.setCorporateId(corporateId);
            CorporateDao corporateDao = new CorporateDao(conn);
            corporateDto = corporateDao.findByCorporateId(corporateDto);
            int corporateNo = corporateDto.getCorporateNo();

            //会社NOに該当する利用者情報リストを取得し、リクエストスコープに保持する
            UserDao userDao = new UserDao(conn);
            ArrayList<UserDto> userList = userDao.findByCorporateNo(corporateNo);

            request.setAttribute("user", userList);

            // URIをリクエストに保持する
            request.setAttribute("uri", request.getRequestURI());
            request.getRequestDispatcher("WEB-INF/list-user.jsp").forward(request, response);
        } catch (SQLException | NamingException e) {

            logger.error("{} {}", e.getClass(), e.getMessage());

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	doGet(request, response);
    }

}