package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.CourseDto;

public class CourseDao {

    protected Connection connection;

    public CourseDao(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Object> selectCourseList() throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("    select");
        sb.append("            CA.CATEGORY_NAME,");
        sb.append("            CO.COURSE_ID,");
        sb.append("            CO.COURSE_NAME,");
        sb.append("            CO.OVERVIEW,");
        sb.append("            CO.REQUIRED_TIME,");
        sb.append("            CO.IS_FREE_FLG");
        sb.append("       from COURSE as CO");
        sb.append(" inner join CATEGORY as CA");
        sb.append("         on CO.CATEGORY_ID = CA.CATEGORY_ID");

        ArrayList<Object> list = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            CourseDto courseDto;

            while(resultSet.next()) {

                courseDto = new CourseDto();
                courseDto.setCategoryName(resultSet.getString("CATEGORY_NAME"));
                courseDto.setCourseId(resultSet.getInt("COURSE_ID"));
                courseDto.setCourseName(resultSet.getString("COURSE_NAME"));
                courseDto.setOverview(resultSet.getString("OVERVIEW"));
                courseDto.setRequiredTime(resultSet.getInt("REQUIRED_TIME"));
                courseDto.setIsFreeFlg(resultSet.getByte("IS_FREE_FLG"));

                list.add(courseDto);
            }
        }

        return list;
    }

    public ArrayList<Integer> selectCourseIds() throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("    select");
        sb.append("            COURSE_ID");
        sb.append("      from COURSE");

        ArrayList<Integer> list = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {

                list.add(resultSet.getInt("COURSE_ID"));
            }
        }

        return list;

    }


}
