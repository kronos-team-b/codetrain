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
import jp.keronos.dao.LearningCourseDao;
import jp.keronos.dao.UnitTestDao;
import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;
import jp.keronos.dto.UserCourseTestAnswerDto;
import jp.keronos.dto.UserDto;

/**
 * Servlet implementation class AnsCourseTestServlet
 */
@WebServlet("/ans-course-test")
public class AnsCourseTestServlet extends HttpServlet {
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

        int courseId = (int)request.getAttribute("courseId");
        ArrayList<UserCourseTestAnswerDto> answeredListDto = (ArrayList<UserCourseTestAnswerDto>) request.getAttribute("answers");

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            ArrayList<UnitTestDto> answerListDto = unitTestDao.selectAnswerByTestIds(takeTestIds(answeredListDto));

            ArrayList<String> answerList = takeAnswerList(answerListDto);
            ArrayList<String> answeredList = takeAnsweredList(answeredListDto);

            // ログインしていればテスト結果を登録する
            if (userNo != 0) {
                LearningCourseDao learningCourseDao = new LearningCourseDao(connection);
                learningCourseDao.duplicate(courseId, userNo, takePassFlg(answeredList, answerList));
            }

            ArrayList<UnitTestDto> unitTestList = (ArrayList<UnitTestDto>) session.getAttribute("sessionUnitTestList");
            ArrayList<UnitTestChoicesDto> choicesList = (ArrayList<UnitTestChoicesDto>) session.getAttribute("sessionChoicesList");
            UnitTestDto[] arrayAnswerListDto = answerListDto.toArray(new UnitTestDto[answerListDto.size()]);
            UserCourseTestAnswerDto[] arrayAnswerdList  = answeredListDto.toArray(new UserCourseTestAnswerDto[answeredListDto.size()]);

            request.setAttribute("courseId", courseId);
            request.setAttribute("unitTestList", unitTestList);
            request.setAttribute("choicesList", choicesList);
            request.setAttribute("answerList", arrayAnswerListDto);
            request.setAttribute("answeredList", arrayAnswerdList);

            session.removeAttribute("sessionUnitTestList");
            session.removeAttribute("sessionChoicesList");

            request.getRequestDispatcher("WEB-INF/answer-course-test.jsp").forward(request, response);

        } catch (SQLException | NamingException e) {

            request.getRequestDispatcher("system-error.jsp").forward(request, response);
        }
    }

    /**
     * passフラグを取得
     * @param answeredList
     * @param answerList
     * @return passフラグ
     */
    private byte takePassFlg(ArrayList<String> answeredList, ArrayList<String> answerList) {

        byte pass_flag = 0;
        int count = calculateListCompareSameCount(answeredList, answerList).size();
        if (answerList.size() == count) {

            pass_flag = 1;
        }

        return pass_flag;
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
    private ArrayList<String> takeAnsweredList(ArrayList<UserCourseTestAnswerDto> answeredListDto) {

        ArrayList<String> answeredList = new ArrayList<>();
        for (UserCourseTestAnswerDto data : answeredListDto) {
            answeredList.add(data.getUserAnswer());
        }

        return answeredList;
    }

    /**
     * テストIDを取得
     * @param answeredListDto
     * @return テストID
     */
    private ArrayList<Integer> takeTestIds(ArrayList<UserCourseTestAnswerDto> answeredListDto) {

        ArrayList<Integer> testIds = new ArrayList<>();
        for (UserCourseTestAnswerDto list : answeredListDto) {
            testIds.add(list.getTestId());
        }

        return testIds;
    }

}
