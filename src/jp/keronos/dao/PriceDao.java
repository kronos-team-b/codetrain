package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.PriceDto;

public class PriceDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public PriceDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 単価IDに該当する単価情報を取得する
     * @param dto
     * @return　単価情報
     * @throws SQLException
     */
    public PriceDto selectByPriceId(PriceDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("        PRICE_ID");
        sb.append("        ,PRICE");
        sb.append("        ,TAX");
        sb.append("    from");
        sb.append("        PRICE");
        sb.append("   where PRICE_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getPriceId());

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new PriceDto();
                dto.setPriceId(rs.getInt("PRICE_ID"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setTax(rs.getInt("TAX"));
            }
        }
        return dto;
    }
}
