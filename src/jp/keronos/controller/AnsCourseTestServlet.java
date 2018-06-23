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
import jp.keronos.dto.UnitTestDto;
import jp.keronos.dto.UserCourseTestAnswerDto;

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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

//        if (session == null || session.getAttribute("user") == null) {
//
//          request.getRequestDispatcher("index.jsp").forward(request, response);
//          return;
//        }

//        UserDto user = (UserDto)session.getAttribute("user");
//        int userNo = user.getUserNo();
        int userNo = 1;
        int courseId = (int)request.getAttribute("courseId");
        @SuppressWarnings("unchecked")
        ArrayList<UserCourseTestAnswerDto> answeredListDto = (ArrayList<UserCourseTestAnswerDto>) request.getAttribute("answers");
        request.setAttribute("answeredList", answeredListDto);

        ArrayList<Integer> testIds = takeTestIds(answeredListDto);

        try(Connection connection = DataSourceManager.getConnection()) {

            UnitTestDao unitTestDao = new UnitTestDao(connection);
            ArrayList<UnitTestDto> answerListDto = unitTestDao.selectAnswerByTestIds(testIds);
            request.setAttribute("answerList", answerListDto);

            ArrayList<String> answerList = takeAnswerList(answerListDto);
            ArrayList<String> answeredList = takeAnsweredList(answeredListDto);

            byte passFlg = takePassFlg(answeredList, answerList);
            LearningCourseDao learningCourseDao = new LearningCourseDao(connection);
            learningCourseDao.duplicate(courseId, userNo, passFlg);

            request.getRequestDispatcher("answer-course-test.jsp").forward(request, response);

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
