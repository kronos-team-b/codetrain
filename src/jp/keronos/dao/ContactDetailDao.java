package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.keronos.dto.ContactDetailDto;

public class ContactDetailDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public ContactDetailDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * コンタクト詳細情報を更新する
     * @param dto コンタクト詳細情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */
    public int insert(ContactDetailDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into CONTACT_DETAIL");
        sb.append("           (");
        sb.append("             CONTACT_DETAIL_ID");
        sb.append("             CONTACT_ID");
        sb.append("            ,CONTACT_DETAIL");
        sb.append("            ,CONTACT_AT");
        sb.append("            ,REQUEST_OR_RESPONSE_FLG");
        sb.append("            ,MANAGE_NO");
        sb.append("      values");
        sb.append("           (");
        sb.append("             ?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("           )");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setint(1, dto.getContactDetailId());
            ps.setint(2, dto.getContactId());
            ps.setString(3, dto.getContactDetail());
            ps.setTimestamp(4, dto.getContactAt());
            ps.setBoolean(5, getRequestOrResponseFlg());
            ps.setString(6, dto.getManageId());

            // SQLを実行する
            return ps.executeUpdate();
        }
    }

    /**
     * コンタクトIDに該当するコンタクト詳細情報を取得、テーブル結合によりコンタクトIDに紐づく利用者IDも取得
     * * @param ContactId　コンタクトID
     * * @return コンタクト詳細情報
     * @throws SQLException SQL例外
     */
    public List<ContactDetailDto> selectByContactId(int contactId) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("        CONTACT_DETAIL_ID");
        sb.append("        ,CONTACT_ID");
        sb.append("        ,CONTACT_DETAIL");
        sb.append("        ,CONTACT_AT");
        sb.append("        ,REQUEST_OR_RESPONSE_FLG");
        sb.append("        ,MANAGE_NO");
        sb.append("    from");
        sb.append("            CONTACT_DETAIL");
        sb.append(" where CONTACT_ID = ?");
        sb.append(" order by CONTACT_AT DESC");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, contactId);

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            List<ContactDetailDto> List = new ArrayList<>();
            while (rs.next()) {
                ContactDetailDto dto = new ContactDetailDto();
                dto.setContactDetailId(rs.getInt("CONTACT_DETAIL_ID"));
                dto.setContactId(rs.getInt("CONTACT_ID"));
                dto.setContactDetail(rs.getString("CONTACT_DETAIL"));
                dto.setContactAt(rs.getTimestamp("CONTACT_AT"));
                dto.setRequestOrResponseFlg(rs.getBoolean("REQUEST_OR_RESPONSE_FLG"));
                dto.setManageId(rs.getInt("MANAGE_NO"));

                List.add(dto);
            }
            return List;
        }
    }
}
