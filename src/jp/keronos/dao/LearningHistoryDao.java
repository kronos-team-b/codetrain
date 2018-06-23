package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.keronos.dto.CategoryDto;
import jp.keronos.dto.LearningHistoryDto;
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

    public ArrayList<LearningHistoryDto> selectByUserNo(int userNo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("   select LEARNING_HISTORY_ID");
        sb.append("         ,USER_NO");
        sb.append("         ,COURSE_ID");
        sb.append("         ,UNIT_ID");
        sb.append("         ,SKIP_FLG");
        sb.append("         ,END_FLG");
        sb.append("         ,UNIT_TEST_POINT");
        sb.append("         ,TEST_AT");
        sb.append("   from LEARNING_HISTORY");
        sb.append("  where USER_NO = ?");

        ArrayList<LearningHistoryDto> list = new ArrayList<LearningHistoryDto>();

        // ステートメントオブジェクトを作成する
        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {
            preparedStatement.setInt(1, userNo);

            // SQL文を実行する
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LearningHistoryDto dto = new LearningHistoryDto();
                dto.setLearningHistoryId(rs.getInt("LEARNING_HISTORY_ID"));
                dto.setUserNo(rs.getInt("USER_NO"));
                dto.setUserNo(rs.getInt("COURSE_ID"));
                dto.setUserNo(rs.getInt("UNIT_ID"));
                dto.setUserNo(rs.getInt("SKIP_FLG"));
                dto.setUserNo(rs.getInt("END_FLG"));
                dto.setUserNo(rs.getInt("UNIT_TEST_POINT"));
                dto.setUserNo(rs.getInt("TEST_AT"));
                list.add(dto);
            }
        }
        return list;
    }
}

