package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.UnitDto;

public class UnitDao {

    protected Connection connection;

    public UnitDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * カリキュラムテストの数を取得
     * @param courseId
     * @return カリキュラムテストの数
     * @throws SQLException
     */
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

    public ArrayList<UnitDto> selectByCourseId(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append("       UNIT_ID,");
        sb.append("       UNIT_TITLE,");
        sb.append("       UNIT_TEXT,");
        sb.append("       COURSE_ID");
        sb.append("  from UNIT");
        sb.append(" where COURSE_ID = ?");

        ArrayList<UnitDto> list = new ArrayList<>();
        UnitDto unitDto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {

                unitDto = new UnitDto();
                unitDto.setUnitId(resultSet.getInt("UNIT_ID"));
                unitDto.setUnitTitle(resultSet.getString("UNIT_TITLE"));
                unitDto.setUnitText(resultSet.getString("UNIT_TEXT"));
                unitDto.setCourseId(resultSet.getInt("COURSE_ID"));

                list.add(unitDto);
            }
        }

        return list;
    }

}
