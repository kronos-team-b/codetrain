package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.CourseDto;

public class CourseDao {

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
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getCourseId());

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new CourseDto();
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setCourseName(rs.getString("COURSE_NAME"));
                dto.setOverview(rs.getString("OVERVIEW"));
                dto.setRequiredTime(rs.getInt("REQUIRED_TIME"));
                dto.setPrecondition(rs.getString("PRECONDITION"));
                dto.setGoal(rs.getString("GOAL"));
                dto.setIsFreeFlg(rs.getInt("IS_FREE_FLG"));
                dto.setCategoryId(rs.getInt("CATEGORY_ID"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
            }
        }
            return dto;
    }
}
