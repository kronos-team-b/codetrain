package jp.keronos.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.keronos.DataSourceManager;
import jp.keronos.dao.LearningHistoryDao;
import jp.keronos.dao.UnitDao;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;
import jp.keronos.dto.UserDto;
import jp.keronos.dto.UserUnitTestAnswerDto;

/**
 * Servlet implementation class AnsUnitTestServlet
 */
@WebServlet("/ans-unit-test")
public class AnsUnitTestServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.sendRedirect("index.jsp");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession(false);

        UserDto user = (UserDto)session.getAttribute("user");

        // ログインしていなければuserNoを0にセット
        int userNo = 0;
        if (user != null) {
            userNo = user.getUserNo();
        }

        int unitId = (int)request.getAttribute("unitId");
        UnitDto unitDto = new UnitDto();
        unitDto.setUnitId(unitId);

        ArrayList<UserUnitTestAnswerDto> answeredListDto = (ArrayList<UserUnitTestAnswerDto>) request.getAttribute("answers");

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            ArrayList<UnitTestDto> answerListDto = unitTestDao.selectAnswerByTestIds(takeTestIds(answeredListDto));

            UnitDao unitDao = new UnitDao(connection);
            UnitDto unitInfo = unitDao.selectByUnitId(unitDto);

            UnitDto unit = unitDao.selectByUnitId(unitDto);
            int unitTestAmount = unitTestDao.count(unit.getCourseId());

            ArrayList<String> answerList = takeAnswerList(answerListDto);
            ArrayList<String> answeredList = takeAnsweredList(answeredListDto);

            int unitTestPoint = takeTestPoint(answeredList, answerList);

            // ログインしていればテスト結果を登録する
            if (userNo != 0) {
                LearningHistoryDao learningHistoryDao = new LearningHistoryDao(connection);

                LearningHistoryDto learningHistoryDto = new LearningHistoryDto();

                learningHistoryDto.setUserNo(userNo);
                learningHistoryDto.setCourseId(unitInfo.getCourseId());
                learningHistoryDto.setUnitId(unitId);
                learningHistoryDto.setUnitTestPoint(unitTestPoint);
                learningHistoryDto.setUnitTestPoint(unitTestPoint);

                int learningHistoryId = learningHistoryDao.selectLearningHistory(learningHistoryDto);

                learningHistoryDto.setLearningHistoryId(learningHistoryId);

                if(learningHistoryId != 0) {

                    learningHistoryDao.update(learningHistoryDto);

                } else {

                    learningHistoryDao.insert(learningHistoryDto);
                }
            }

            ArrayList<UnitTestDto> unitTestList = (ArrayList<UnitTestDto>) session.getAttribute("sessionUnitTestList");
            ArrayList<UnitTestChoicesDto> choicesList = (ArrayList<UnitTestChoicesDto>) session.getAttribute("sessionChoicesList");
            UnitTestDto[] arrayAnswerListDto = answerListDto.toArray(new UnitTestDto[answerListDto.size()]);
            UserUnitTestAnswerDto[] arrayAnswerdList  = answeredListDto.toArray(new UserUnitTestAnswerDto[answeredListDto.size()]);

            request.setAttribute("unitInfo", unitInfo);
            request.setAttribute("unitTestAmount", unitTestAmount);
            request.setAttribute("unitTestList", unitTestList);
            request.setAttribute("choicesList", choicesList);
            request.setAttribute("answerList", arrayAnswerListDto);
            request.setAttribute("answeredList", arrayAnswerdList);
            request.setAttribute("unitTestPoint", unitTestPoint);

            session.removeAttribute("sessionUnitTestList");
            session.removeAttribute("sessionChoicesList");

            request.setAttribute("unitFlg", 0);

            request.getRequestDispatcher("WEB-INF/answer-unit-test.jsp").forward(request, response);


        } catch (SQLException | NamingException e) {
            e.printStackTrace();
            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * テストの点数を取得
     * @param answeredList
     * @param answerList
     * @return テストの点数
     */
    private int takeTestPoint(ArrayList<String> answeredList, ArrayList<String> answerList) {

        int count = calculateListCompareSameCount(answeredList, answerList).size();
        int answeredSize = answeredList.size();

        int testPoint = (int)((double)count / answeredSize * 100);

        return testPoint;
    }

    /**
     * 一致するリストを抽出する
     * @param answeredList
     * @param answerList
     * @return 一致したリスト
     */
    private List<String> calculateListCompareSameCount(ArrayList<String> firstList, ArrayList<String> secondList) {

        List<String> list = firstList.stream()
                .filter(o -> secondList.contains(o))
                .collect(Collectors.toList());

        return list;
    }

    /**
     * 正解リストを取得
     * @param answerListDto
     * @return 正解リスト
     */
    private ArrayList<String> takeAnswerList(ArrayList<UnitTestDto> answerListDto) {

        ArrayList<String> answerList = new ArrayList<>();
        for (UnitTestDto data : answerListDto) {
            answerList.add(data.getModelAnswer());
        }
        return answerList;
    }

    /**
     * 回答リストを取得
     * @param answeredListDto
     * @return 回答リスト
     */
    private ArrayList<String> takeAnsweredList(ArrayList<UserUnitTestAnswerDto> answeredListDto) {

        ArrayList<String> answeredList = new ArrayList<>();
        for (UserUnitTestAnswerDto data : answeredListDto) {
            answeredList.add(data.getUserAnswer());
        }

        return answeredList;
    }

    /**
     * テストIDを取得
     * @param answeredListDto
     * @return テストID
     */
    private ArrayList<Integer> takeTestIds(ArrayList<UserUnitTestAnswerDto> answeredListDto) {

        ArrayList<Integer> testIds = new ArrayList<>();
        for (UserUnitTestAnswerDto list : answeredListDto) {
            testIds.add(list.getTestId());
        }

        return testIds;
    }
}
