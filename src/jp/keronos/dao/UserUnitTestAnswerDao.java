package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.keronos.dto.UserCourseTestAnswerDto;

public class UserUnitTestAnswerDao {

    protected Connection connection;

    public UserUnitTestAnswerDao(Connection connection) {
        this.connection = connection;
    }

    public int insert(int userNo, String answer, int unitId, int testId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("insert into USER_UNIT_TEST_ANSWER(");
        sb.append("            USER_NO,");
        sb.append("            USER_ANSWER,");
        sb.append("            TEST_ID,");
        sb.append("            UNIT_ID");
        sb.append(")");
        sb.append("     values (");
        sb.append("             ?,?,?,?");
        sb.append(")");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setString(2, answer);
            preparedStatement.setInt(3, unitId);
            preparedStatement.setInt(4, testId);

            return preparedStatement.executeUpdate();
        }
    }

    public int insertTest(int userNo, String[] answer, int[] unitId, int[] testId) {

        connection.setAutoCommit(false);

        StringBuffer sb = new StringBuffer();
        sb.append("insert into USER_UNIT_TEST_ANSWER(");
        sb.append("            USER_NO,");
        sb.append("            USER_ANSWER,");
        sb.append("            TEST_ID,");
        sb.append("            UNIT_ID");
        sb.append(")");
        sb.append("     values (");
        sb.append("             ?,?,?,?");
        sb.append(")");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            for (UserCourseTestAnswerDto dto : list) {
                preparedStatement.setInt(1, dto.getUserNo());
                preparedStatement.setString(2, dto.getUserAnswer());
                preparedStatement.setInt(3, dto.getTestId());
                preparedStatement.setInt(4, dto.getCourseId());
            }
            preparedStatement.addBatch();
            connection.commit();

            return preparedStatement.executeUpdate();
        }
    }

}
