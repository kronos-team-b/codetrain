package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.LearningCourseDto;

public class LearningCourseDao {

    protected Connection connection;

    public LearningCourseDao(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<LearningCourseDto> selectPassLFlugByUserNo(int userNo, int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       COURSE_ID,");
        sb.append("       PASS_FLG");
        sb.append("  from LEARNING_COURSE");
        sb.append(" where USER_NO = ? and COURSE_ID = ?");

        LearningCourseDto learningCourseDto;
        ArrayList<LearningCourseDto> list = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                learningCourseDto = new LearningCourseDto();
                learningCourseDto.setCourseId(resultSet.getInt("COURSE_ID"));
                learningCourseDto.setPassFlg(resultSet.getByte("PASS_FLG"));
                list.add(learningCourseDto);
            }
        }

        return list;
    }
}
