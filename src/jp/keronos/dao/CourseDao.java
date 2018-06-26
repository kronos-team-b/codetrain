package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.keronos.dto.CourseDto;
import jp.keronos.dto.LearningCourseDto;


public class CourseDao {

    /** コネクション */
    protected Connection connection;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param connection コネクション
     */
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
        sb.append(" order by COURSE_ID");

        ArrayList<CourseDto> list = new ArrayList<CourseDto>();

        // ステートメントオブジェクトを作成する
        try (Statement stmt = connection.createStatement()) {

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

    /**
     * コース情報リストと学習履歴を取得する
     * @return コース情報リスト
     * @param ユーザNO
     * @throws SQLException SQL例外
     */
    public ArrayList<LearningCourseDto> selectAllAndUserHistory(int userNo) throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("          select");
        sb.append("                 LC.LEARNING_TYPE");
        sb.append("                ,LC.USER_NO");
        sb.append("                ,LC.PASS_FLG");
        sb.append("                ,LC.UPDATE_AT");
        sb.append("                ,CO.COURSE_ID");
        sb.append("                ,CO.COURSE_NAME");
        sb.append("                ,CO.IS_FREE_FLG");
        sb.append("                ,CO.CATEGORY_ID");
        sb.append("                ,CO.DELETE_FLG");
        sb.append("     from COURSE as CO");
        sb.append(" left outer join LEARNING_COURSE as LC");
        sb.append("              on LC.COURSE_ID = CO.COURSE_ID");
        sb.append("             and LC.USER_NO = ?");
        sb.append("           where CO.DELETE_FLG = 0");
        sb.append("        order by CO.COURSE_ID");

        ArrayList<LearningCourseDto> list = new ArrayList<LearningCourseDto>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);

            // SQL文を実行する
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LearningCourseDto dto = new LearningCourseDto();
                dto.setLearnigType(rs.getInt("LEARNING_TYPE"));
                dto.setUserNo(rs.getInt("USER_NO"));
                dto.setPassFlg(rs.getInt("PASS_FLG"));
                if(rs.getTimestamp("UPDATE_AT") != null) {
                    dto.setUpdateAt(sdf.format(rs.getTimestamp("UPDATE_AT")));
                }
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setCourseName(rs.getString("COURSE_Name"));
                dto.setIsFreeFlg(rs.getInt("IS_FREE_FLG"));
                dto.setCategoryId(rs.getInt("CATEGORY_ID"));
                dto.setUpdateNumber(rs.getInt("DELETE_FLG"));
                list.add(dto);
            }
            return list;
        }
    }



    public CourseDto selectByCourseId(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        COURSE_ID,");
        sb.append("        COURSE_NAME,");
        sb.append("        OVERVIEW,");
        sb.append("        REQUIRED_TIME,");
        sb.append("        PRECONDITION,");
        sb.append("        GOAL,");
        sb.append("        CATEGORY_ID");
        sb.append("   from COURSE");
        sb.append("  where COURSE_ID = ?");

        CourseDto courseDto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                courseDto = new CourseDto();
                courseDto.setCourseId(resultSet.getInt("COURSE_ID"));
                courseDto.setCourseName(resultSet.getString("COURSE_NAME"));
                courseDto.setOverview(resultSet.getString("OVERVIEW"));
                courseDto.setRequiredTime(resultSet.getInt("REQUIRED_TIME"));
                courseDto.setPrecondition(resultSet.getString("PRECONDITION"));
                courseDto.setGoal(resultSet.getString("GOAL"));
                courseDto.setCategoryId(resultSet.getInt("CATEGORY_ID"));
            }
        }

        return courseDto;
    }

/**
     * コース情報を取得する
     * @return コース情報リスト
     * @throws SQLException SQL例外
     */
    public CourseDto selectByCourseId(CourseDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         COURSE_ID");
        sb.append("        ,COURSE_NAME");
        sb.append("        ,OVERVIEW");
        sb.append("        ,REQUIRED_TIME");
        sb.append("        ,PRECONDITION");
        sb.append("        ,GOAL");
        sb.append("        ,IS_FREE_FLG");
        sb.append("        ,CATEGORY_ID");
        sb.append("        ,UPDATE_NUMBER");
        sb.append("        ,MANAGE_NO");
        sb.append("        ,DELETE_FLG");
        sb.append("    from COURSE");
        sb.append("   where COURSE_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = connection.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getCourseId());

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new CourseDto();
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setCourseName(rs.getString("COURSE_NAME"));
            }
        }
        return dto;
    }
}
