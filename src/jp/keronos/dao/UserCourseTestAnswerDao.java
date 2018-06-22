package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.UnitDto;
import jp.keronos.dto.UserCourseTestAnswerDto;

public class UserCourseTestAnswerDao {

    protected Connection connection;

    public UserCourseTestAnswerDao(Connection connection) {

        this.connection = connection;
    }

    /**
     * 不正解のカリキュラムリストを取得する
     * @param userNo
     * @param courseId
     * @return 不正解のカリキュラムリスト
     * @throws SQLException
     */
    public ArrayList<UnitDto> selectTestResultsDecisionListByUserNoAndCourseId(int userNo, int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("     select distinct");
        sb.append("            UT.UNIT_ID,");
        sb.append("            U.UNIT_TITLE");
        sb.append("       from USER_COURSE_TEST_ANSWER as UCTA");
        sb.append(" inner join UNIT_TEST as UT");
        sb.append("         on UCTA.TEST_ID = UT.TEST_ID");
        sb.append(" inner join UNIT as U");
        sb.append("         on UT.UNIT_ID = U.UNIT_ID");
        sb.append("      where UCTA.USER_NO = ?");
        sb.append("        and UCTA.COURSE_ID = ?");
        sb.append("        and UCTA.USER_ANSWER <> UT.MODEL_ANSWER ");

        ArrayList<UnitDto> list = new ArrayList<>();
        UnitDto unitDto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                unitDto = new UnitDto();
                unitDto.setUnitId(resultSet.getInt("UNIT_ID"));
                unitDto.setUnitTitle(resultSet.getString("UNIT_TITLE"));

                list.add(unitDto);
            }
        }

        return null;
    }

    /**
     * 回答をテーブルに追加
     * @param list
     * @return 追加した行数
     * @throws SQLException
     */
    public int[] insert(ArrayList<UserCourseTestAnswerDto> list) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into USER_COURSE_TEST_ANSWER (");
        sb.append("            USER_NO,");
        sb.append("            USER_ANSWER,");
        sb.append("            TEST_ID,");
        sb.append("            COURSE_ID,");
        sb.append("            TIME");
        sb.append(") ");
        sb.append("     values (");
        sb.append("            ?,?,?,?,?");
        sb.append(")");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            int max = selectMaxTime();

            for (UserCourseTestAnswerDto dto : list) {
                preparedStatement.setInt(1, dto.getUserNo());
                preparedStatement.setString(2, dto.getUserAnswer());
                preparedStatement.setInt(3, dto.getTestId());
                preparedStatement.setInt(4, dto.getCourseId());
                preparedStatement.setInt(5, max == 0 ? 1 : max + 1);
                preparedStatement.addBatch();
            }

            return preparedStatement.executeBatch();
        }
    }

    public int selectMaxTime() throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("     max(time) AS RECORD_MAX");
        sb.append(" from user_course_test_answer");

        int max = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                max = resultSet.getInt("RECORD_MAX");
            }
        }
        return max;
    }
}
