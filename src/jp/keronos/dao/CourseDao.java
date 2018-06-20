package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.keronos.dto.CourseDto;

public class CourseDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public CourseDao(Connection conn) {
        this.conn = conn;
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

        try(PreparedStatement preparedStatement = conn.prepareStatement(sb.toString())) {

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

        try(PreparedStatement preparedStatement = conn.prepareStatement(sb.toString())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {

                list.add(resultSet.getInt("COURSE_ID"));
            }
        }

        return list;

    }

    /**
     * コース情報リストを取得する
     * @return コース情報リスト
     * @throws SQLException SQL例外
     */
    public ArrayList<CourseDto> selectAll() throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("   select");
        sb.append("          COURSE_ID");
        sb.append("         ,COURSE_NAME");
        sb.append("         ,OVERVIEW");
        sb.append("         ,REQUIRED_TIME");
        sb.append("         ,PRECONDITION");
        sb.append("         ,GOAL");
        sb.append("         ,IS_FREE_FLG");
        sb.append("         ,CATEGORY_ID");
        sb.append("         ,UPDATE_NUMBER");
        sb.append("         ,MANAGE_NO");
        sb.append("         ,DELETE_FLG");
        sb.append("     from COURSE");
        sb.append(" order by COURSE_ID desc");

        ArrayList<CourseDto> list = new ArrayList<CourseDto>();

        // ステートメントオブジェクトを作成する
        try (Statement stmt = conn.createStatement()) {

            // SQL文を実行する
            ResultSet rs = stmt.executeQuery(sb.toString());
            while (rs.next()) {
                CourseDto dto = new CourseDto();
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setCourseName(rs.getString("COURSE_Name"));
                dto.setOverview(rs.getString("OVERVIEW"));
                dto.setRequiredTime(rs.getInt("REQUIRED_TIME"));
                dto.setPrecondition(rs.getString("PRECONDITION"));
                dto.setGoal(rs.getString("GOAL"));
                dto.setIsFreeFlg(rs.getInt("IS_FREE_FLG"));
                dto.setCategoryId(rs.getInt("CATEGORY_ID"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setUpdateNumber(rs.getInt("DELETE_FLG"));
                list.add(dto);
            }
            return list;
        }
    }
}
