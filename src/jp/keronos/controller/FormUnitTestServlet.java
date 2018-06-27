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
import jp.keronos.dao.UnitDao;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dao.UserUnitTestAnswerDao;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;
import jp.keronos.dto.UserDto;
import jp.keronos.dto.UserUnitTestAnswerDto;

/**
 * Servlet implementation class FormUnitTestServlet
 */
@WebServlet("/form-unit-test")
public class FormUnitTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        int unitId = Integer.parseInt(request.getParameter("unit-id"));

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitDto unitDto = new UnitDto();
            unitDto.setUnitId(unitId);

            UnitDao unitDao = new UnitDao(connection);
            UnitDto unit = unitDao.selectByUnitId(unitDto);

            UnitTestDto unitTestDto = new UnitTestDto();
            unitTestDto.setUnitId(unitId);

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            ArrayList<UnitTestDto> unitTestList = unitTestDao.selectByUnitId(unitTestDto);

            int unitTestAmount = unitTestDao.countByUnitId(unit.getUnitId());

            ArrayList<UnitTestChoicesDto> choicesList = unitTestDao.selectUnitTestChoise();

            request.setAttribute("unit", unit);
            request.setAttribute("unitTestAmount", unitTestAmount);
            request.setAttribute("unitTestList", unitTestList);
            request.setAttribute("choicesList", choicesList);

            session.setAttribute("sessionUnitTestList", unitTestList);
            session.setAttribute("sessionChoicesList", choicesList);

            request.getRequestDispatcher("WEB-INF/list-unit-test.jsp").forward(request, response);

        } catch (SQLException | NamingException e) {

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        UserDto user = (UserDto)session.getAttribute("user");

        int userNo = 0;
        if (user != null) {
            userNo = user.getUserNo();
        }

        int unitTestAmount = Integer.parseInt(request.getParameter("unit-test-amount"));

        int unitId = Integer.parseInt(request.getParameter("unit-id"));
        request.setAttribute("unitId", unitId);

        String[] answers = new String[unitTestAmount];
        for (int i = 0; i < unitTestAmount; i++) {
            String param = String.format("answer[%d]", i);
            answers[i] = request.getParameter(param);
        }

        int[] testIds = Stream.of(request.getParameterValues("unit-test-id[]")).mapToInt(Integer::parseInt).toArray();

        ArrayList<UserUnitTestAnswerDto> list = setTestData(unitId, answers, testIds, userNo);
        request.setAttribute("answers", list);

        try(Connection connection = DataSourceManager.getConnection()) {

            // ログインしていれば回答を登録
            if (userNo != 0) {
                UserUnitTestAnswerDao userUnitTestAnswerDao = new UserUnitTestAnswerDao(connection);

                for (UserUnitTestAnswerDto userUnitTestAnswerDto : list) {
                    int tempUserNo = userUnitTestAnswerDto.getUserNo();
                    String tempAnswer = userUnitTestAnswerDto.getUserAnswer();
                    int tempUnitId = userUnitTestAnswerDto.getUnitId();
                    int tempTestId = userUnitTestAnswerDto.getTestId();
                    userUnitTestAnswerDao.insert(tempUserNo, tempAnswer, tempUnitId, tempTestId);
                }

            }
            request.getRequestDispatcher("ans-unit-test").forward(request, response);;

        } catch (SQLException | NamingException e) {

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * 回答データをリストに格納する
     * @param courseId
     * @param answers
     * @param testIds
     * @param userNo
     * @return リスト形式の回答データ
     */
    private ArrayList<UserUnitTestAnswerDto> setTestData(int unitId, String[] answers, int[] testIds, int userNo) {

        ArrayList<UserUnitTestAnswerDto> list = new ArrayList<>();
        UserUnitTestAnswerDto userUnitTestAnswerDto = null;

        for (int i = 0; i < answers.length; i++) {
            userUnitTestAnswerDto = new UserUnitTestAnswerDto();
            userUnitTestAnswerDto.setUnitId(unitId);
            userUnitTestAnswerDto.setUserAnswer(answers[i]);
            userUnitTestAnswerDto.setTestId(testIds[i]);
            userUnitTestAnswerDto.setUserNo(userNo);
            list.add(userUnitTestAnswerDto);
        }

        return list;
    }

}
