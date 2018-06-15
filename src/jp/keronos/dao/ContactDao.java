package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.ContactDto;

/**
 * コンタクトテーブルおよびコンタクト詳細表のDataAccessObject
 * @author Hiroki Nishio
 */
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
     * コンタクト情報リストを取得する
     * @param contactId コンタクトID
     * @return コンタクト情報リスト
     * @throws SQLException SQL例外
     */
    public ArrayList<ContactDto> selectByContactId(int contactId) throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("   select");
        sb.append("          CONTACT_DETAIL_ID");
        sb.append("         ,CONTACT_ID");
        sb.append("         ,CONTACT_DETAIL");
        sb.append("         ,CONTACT_AT");
        sb.append("         ,REQUEST_OR_RESPONSE_FLG");
        sb.append("         ,MANAGE_NO");
        sb.append("     from CONTACT_DETAIL");
        sb.append("    where CONTACT_ID = ?");
        sb.append(" order by CONTACT_DETAIL_ID");

        ArrayList<ContactDto> list = new ArrayList<ContactDto>();

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, contactId);

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ContactDto dto = new ContactDto();
                dto.setContactDetailId(rs.getInt("CONTACT_DETAIL_ID"));
                dto.setContactId(rs.getInt("CONTACT_ID"));
                dto.setContactDetail(rs.getString("CONTACT_DETAIL"));
                dto.setContactAt(rs.getTimestamp("CONTACT_AT"));
                dto.setRequestOrResponseFlg(rs.getInt("REQUEST_OR_RESPONSE_FLG"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                list.add(dto);
            }
            return list;
        }
    }
}
