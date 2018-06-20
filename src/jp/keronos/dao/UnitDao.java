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
        sb.append("  select");
        sb.append("         UNIT_ID");
        sb.append("        ,UNIT_TITLE");
        sb.append("        ,UNIT_TEXT");
        sb.append("        ,COURSE_ID");
        sb.append("        ,UPDATE_NUMBER");
        sb.append("        ,MANAGE_NO");
        sb.append("        ,DELETE_FLG");
        sb.append("    from");
        sb.append("         UNIT");
        sb.append("   where UNIT_ID = ?");
        sb.append("     and DELETE_FLG = 0");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getUnitId());

         // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new UnitDto();
                dto.setUnitId(rs.getInt("UNIT_ID"));
                dto.setUnitTitle(rs.getString("UNIT_TITLE"));
                dto.setUnitText(rs.getString("UNIT_TEXT"));
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));

            }
            return dto;
        }
    }
}
