package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.keronos.dto.BillDto;

public class BillDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public BillDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 会社NOに該当する請求情報を取得する
     * @param coporateNo
     * @return 請求一覧情報
     * @throws SQLException
     */
    public List<BillDto> selectByCorporateNo(int coporateNo) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("        BILLING_ID");
        sb.append("        ,CORPORATE_NO");
        sb.append("        ,BILLING_DATE");
        sb.append("        ,PRICE_ID");
        sb.append("        ,NUMBER_OF_ACTIVE_ACCOUNT");
        sb.append("        ,NUMBER_OF_INACTIVE_ACCOUNT");
        sb.append("    from");
        sb.append("        BILL");
        sb.append("   where");
        sb.append("        CORPORATE_NO = ?");
  //      sb.append("order by BILLING_DATE DESC");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, coporateNo);

         // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            List<BillDto> List = new ArrayList<>();
            while (rs.next()) {
                BillDto dto = new BillDto();
                dto.setBillingId(rs.getInt("BILLING_ID"));
                dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
                dto.setPriceId(rs.getInt("PRICE_ID"));
                dto.setBillingDate(rs.getDate("BILLING_DATE"));
                dto.setNumberOfActiveAccount(rs.getInt("NUMBER_OF_ACTIVE_ACCOUNT"));
                dto.setNumberOfInactiveAccount(rs.getInt("NUMBER_OF_INACTIVE_ACCOUNT"));

                List.add(dto);
            }
            return List;


        }
    }
}
