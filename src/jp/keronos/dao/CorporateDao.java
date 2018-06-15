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
     * 法人アカウント情報を取得する
     * @param 法人アカウントID　パスワード
     * @return 法人アカウント情報
     * @throws SQLException SQL例外
     */
    public CorporateDto findByIdAndPassword(String id, String password) throws SQLException {
        //SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        CORPORATE_NO");
        sb.append("        CORPORATE_ID");
//      sb.append("       ,PASSWORD");
        sb.append("       ,CORPORATE_NAME");
        sb.append("       ,FIRST_NAME");
        sb.append("       ,LAST_NAME");
        sb.append("       ,POSITION");
        sb.append("       ,DEPARTMENT");
        sb.append("       ,POSTAL_CODE");
        sb.append("       ,PREFECTURE_ID");
        sb.append("       ,ADRESS_LINE_1");
        sb.append("       ,ADRESS_LINE_2");
        sb.append("       ,EMAIL");
        sb.append("       ,DOMAIN");
        sb.append("       ,MANAGE_NO");
        sb.append("   from CORPORATE");
        sb.append("  where CORPORATE_ID = ?");
        sb.append("    and PASSWORD = sha2(?, 256)");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setString(1, id);
            ps.setString(2, password);

            // SQLを実行する
            ResultSet rs = ps.executeQuery();

            // 結果をDTOに詰める
            if (rs.next()) {
                CorporateDto corporate = new CorporateDto();
                corporate.setCorporateNo(rs.getInt("CORPORATE_NO"));
                corporate.setCorporateId(rs.getString("CORPORATE_Id"));
//              user.setPassword(rs.getString("PASSWORD"));
                corporate.setCorporateName(rs.getString("CORPORATE_NAME"));
                corporate.setFirstName(rs.getString("FIRST_NAME"));
                corporate.setLastName(rs.getString("LAST_NAME"));
                corporate.setPosition(rs.getString("POSITION"));
                corporate.setDepartment(rs.getString("DEPARTMENT"));
                corporate.setPostalCode(rs.getString("POSTAL_CODE"));
                corporate.setPrefectureId(rs.getInt("PREFECTURE_ID"));
                corporate.setAdressLine1(rs.getString("ADRESS_LINE_1"));
                corporate.setAdressLine2(rs.getString("ADRESS_LINE_2"));
                corporate.setEmail(rs.getString("EMAIL"));
                corporate.setDomain(rs.getString("DOMAIN"));
                corporate.setManageNo(rs.getInt("MANAGE_NO"));

                return corporate;
            }
            // 該当するデータがない場合はnullを返却する
            return null;
        }
    }

    /**
     * 法人アカウント情報を更新する
     * @param dto 法人アカウント情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */
    public int updatePassword(CorporateDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" update");
        sb.append("        CORPORATE");
        sb.append("    set");
        sb.append("        PASSWORD = sha2(?, 256)");
        sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("  where CORPORATE_NO = ?");
        sb.append("    and UPDATE_NUMBER = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setString(1, dto.getPassword());
            ps.setInt(2, dto.getCorporateNo());
            ps.setInt(3, dto.getUpdateNumber());

            // SQLを実行する
            return ps.executeUpdate();
        }
    }
}
