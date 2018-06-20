package jp.keronos.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.keronos.dto.CourseDto;

/**
 * コース表のDataAccessObject
 * @author Hiroki Nishio
 */


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
