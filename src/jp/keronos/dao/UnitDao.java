package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.UnitDto;

public class UnitDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public UnitDao(Connection conn) {
        this.conn = conn;
    }

    public UnitDto selectByUnitId(UnitDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("   select");
        sb.append("         UNIT_ID");
        sb.append("        ,UNIT_TITLE");
        sb.append("        ,UNIT_TEXT");
        sb.append("        ,UNIT.COURSE_ID");
        sb.append("        ,UNIT.UPDATE_NUMBER");
        sb.append("        ,UNIT.MANAGE_NO");
        sb.append("        ,UNIT.DELETE_FLG");
        sb.append("        ,COURSE_NAME");
        sb.append("    from");
        sb.append("         UNIT LEFT JOIN COURSE");
        sb.append("     on UNIT.COURSE_ID = COURSE.COURSE_ID");
        sb.append("   where");
        sb.append("         UNIT_ID = ?");
        sb.append("    and  UNIT.DELETE_FLG = 0;");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getUnitId());

         // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new UnitDto();
                dto.setUnitId(rs.getInt("UNIT_ID"));
                dto.setUnitTitle(rs.getString("UNIT_TITILE"));
                dto.setUnitText(rs.getString("UNIT_TEXT"));
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setCourseName(rs.getString("COURSE_NAME"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));

            }
            return dto;
        }
    }
}
