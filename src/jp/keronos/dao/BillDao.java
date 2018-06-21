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
     * 会社NOに該当する請求情報を取得し、結合によって単価と税率情報も取得する
     * @param dto
     * @return 請求一覧情報
     * @throws SQLException
     */
    public List<BillDto> selectByCorporateNo(int corporateNo) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         BILLING_ID");
        sb.append("        ,CORPORATE_NO");
        sb.append("        ,BILLING_DATE");
        sb.append("        ,BILL.PRICE_ID");
        sb.append("        ,NUMBER_OF_ACTIVE_ACCOUNT");
        sb.append("        ,NUMBER_OF_INACTIVE_ACCOUNT");
        sb.append("        ,PRICE");
        sb.append("        ,TAX");
        sb.append("    from");
        sb.append("        BILL left join PRICE");
        sb.append("     on BILL.PRICE_ID = PRICE.PRICE_ID");
        sb.append("  where CORPORATE_NO = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, corporateNo);

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
                dto.setPrice(rs.getInt("PRICE"));
                dto.setTax(rs.getDouble("TAX"));
                double totalPrice = ((dto.getPrice() * dto.getNumberOfActiveAccount())
                        + (dto.getPrice() / 2 * dto.getNumberOfInactiveAccount())) * (1 + dto.getTax());
                dto.setTotalPrice((int) totalPrice);
                List.add(dto);
            }
            return List;
        }
    }

    /**
     * 請求IDに該当する請求情報を取得し、結合によって単価と税率情報も取得する
     * @param dto
     * @return　請求情報、単価情報
     * @throws SQLException
     */
    public BillDto selectByBillingId(BillDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         BILLING_ID");
        sb.append("        ,CORPORATE_NO");
        sb.append("        ,BILLING_DATE");
        sb.append("        ,BILL.PRICE_ID");
        sb.append("        ,NUMBER_OF_ACTIVE_ACCOUNT");
        sb.append("        ,NUMBER_OF_INACTIVE_ACCOUNT");
        sb.append("        ,PRICE");
        sb.append("        ,TAX");
        sb.append("    from");
        sb.append("        BILL left join PRICE");
        sb.append("     on BILL.PRICE_ID = PRICE.PRICE_ID");
        sb.append("  where BILLING_ID = ?");

     // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getCorporateNo());

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto.setBillingId(rs.getInt("BILLING_ID"));
                dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
                dto.setPriceId(rs.getInt("PRICE_ID"));
                dto.setBillingDate(rs.getDate("BILLING_DATE"));
                dto.setNumberOfActiveAccount(rs.getInt("NUMBER_OF_ACTIVE_ACCOUNT"));
                dto.setNumberOfInactiveAccount(rs.getInt("NUMBER_OF_INACTIVE_ACCOUNT"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setTax(rs.getDouble("TAX"));

            }
            return dto;
        }
    }
}
