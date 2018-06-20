package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnitDao {

    protected Connection connection;

    public UnitDao(Connection connection) {
        this.connection = connection;
    }

    public int count(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       COUNT(*) AS RECORD_COUNT");
        sb.append("  from UNIT");
        sb.append(" where COURSE_ID = ?");

        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("RECORD_COUNT");
            }
        }
        return count;
    }

}
