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

    /**
     * 回答データを追加、更新する
     * @param courseId
     * @param userNo
     * @param pass_flag
     * @return 追加、更新数
     * @throws SQLException
     */
    public int duplicate(int courseId, int userNo, byte passFlag) throws SQLException {

        String sql = "insert into LEARNING_COURSE( "
                + "COURSE_ID, "
                + "USER_NO, "
                + "LEARNING_TYPE, "
                + "PASS_FLG "
                + ") "
                + "values (?,?,1,?) "
                + "on duplicate key update "
                + "PASS_FLG = values (PASS_FLG)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, courseId);
            preparedStatement.setInt(2, userNo);
            preparedStatement.setByte(3, passFlag);

            return preparedStatement.executeUpdate();
        }

    }
}
