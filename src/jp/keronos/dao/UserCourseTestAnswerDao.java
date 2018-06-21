package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.UnitDto;

public class UserCourseTestAnswerDao {

    protected Connection connection;

    public UserCourseTestAnswerDao(Connection connection) {

        this.connection = connection;
    }

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

}
