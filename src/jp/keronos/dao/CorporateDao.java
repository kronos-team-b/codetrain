package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.CorporateDto;

public class CorporateDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public CorporateDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 会社IDに紐づく会社NOを取得する
     * @param dto
     * @return　会社NO
     * @throws SQLException
     */
    public CorporateDto selectByCorporateId(CorporateDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("        CORPORATE_NO");
        sb.append("       ,CORPORATE_ID");
        sb.append("    from");
        sb.append("        CORPORATE");
        sb.append("   where");
        sb.append("        CORPORATE_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setString(1, dto.getCorporateId());

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
                dto.setCorporateId(rs.getString("CORPORATE_ID"));
            }
        }
        return dto;
    }
}
