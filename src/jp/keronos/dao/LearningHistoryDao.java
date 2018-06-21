package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.UnitDto;

public class LearningHistoryDao {

    protected Connection connection;

    public LearningHistoryDao(Connection connection) {
        this.connection = connection;
    }

    public int count(int userNo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       COUNT(*) AS RECORD_COUNT");
        sb.append("  from LEARNING_HISTORY");
        sb.append(" where USER_NO = ?");

        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("RECORD_COUNT");
            }
        }
        return count;
    }

    public UnitDto selectUnitIdByUserNoAndCourseId(int userNo, int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" select UNIT_ID");
        sb.append("   from LEARNING_HISTORY");
        sb.append("  where USER_NO = ?");
        sb.append("    and COURSE_ID = ?");
        sb.append("    and SKIP_FLG = true");

        UnitDto unit = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                unit = new UnitDto();
                unit.setUnitId(resultSet.getInt("UNIT_ID"));
            }
        }

        return unit;
    }
}
