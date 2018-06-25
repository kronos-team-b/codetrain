package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
            preparedStatement.setInt(3, testId);
            preparedStatement.setInt(4, unitId);

            return preparedStatement.executeUpdate();
        }
    }
}
