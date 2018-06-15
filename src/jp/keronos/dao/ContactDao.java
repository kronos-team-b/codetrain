package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public ContactDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 連絡情報を追加する
     * @param dto 連絡情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */

    public int insert(ContactDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into CONTACT");
        sb.append("           (");
        sb.append("             CONTACT_ID");
        sb.append("            ,USER_NO");
        sb.append("           )");
        sb.append("      values");
        sb.append("           (");
        sb.append("             ?");
        sb.append("            ,?");
        sb.append("           )");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getContactId());
            ps.setInt(2, dto.getUserNo());

            // SQLを実行する
            return ps.executeUpdate();
        }
    }
}
