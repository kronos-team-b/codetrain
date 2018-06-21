package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
     * (運営向け)最新のコンタクト情報リストを取得する
     * @return コンタクト情報リスト
     * @throws SQLException SQL例外
     */
    public ArrayList<ContactDto> selectLatestAll() throws SQLException {
        // SQL文を作成する
        StringBuffer sbContact = new StringBuffer();
        sbContact.append("   select");
        sbContact.append("          CONTACT_ID");
        sbContact.append("         ,USER_NO");
        sbContact.append("     from CONTACT");

        ArrayList<ContactDto> list = new ArrayList<ContactDto>();

        // ステートメントオブジェクトを作成する
        try (Statement stmt = conn.createStatement()) {

            ResultSet rs = stmt.executeQuery(sbContact.toString());
                 while (rs.next()) {
                     ContactDto dto = new ContactDto();
                     int contactId = rs.getInt("CONTACT_ID");
                     dto = selectLatestByContactId(contactId);
                     list.add(dto);
                 }
        }
        return list;
    }


    /**
     * (法人利用者向け)ユーザNOに該当するコンタクト情報リストを取得する
     * @param ユーザNO
     * @return コンタクト情報リスト
     * @throws SQLException SQL例外
     */
    public ArrayList<ContactDto> selectLatestByUserNo(int userNo) throws SQLException {
        // SQL文を作成する
        StringBuffer sbContact = new StringBuffer();
        sbContact.append("   select");
        sbContact.append("          CONTACT_ID");
        sbContact.append("         ,USER_NO");
        sbContact.append("     from CONTACT");
        sbContact.append("    where USER_NO = ?");

        ArrayList<ContactDto> list = new ArrayList<ContactDto>();

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sbContact.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, userNo);

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
                 while (rs.next()) {
                     ContactDto dto = new ContactDto();
                     int contactId = rs.getInt("CONTACT_ID");
                     dto = selectLatestByContactId(contactId);
                     list.add(dto);
                 }
        }
        return list;
    }



/**
     * コンタクトIDに該当する最新のコンタクト詳細情報を取得する
     * @param contactId コンタクトID
     * @return コンタクト情報
     * @throws SQLException SQL例外
     */
    public ContactDto selectLatestByContactId(int contactId) throws SQLException {
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
        sb.append(" order by CONTACT_DETAIL_ID desc limit 1");

        ContactDto dto = new ContactDto();

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, contactId);

            // SQL文を実行する
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                dto.setContactDetailId(rs.getInt("CONTACT_DETAIL_ID"));
                dto.setContactId(contactId);
                dto.setContactDetail(rs.getString("CONTACT_DETAIL"));
                dto.setContactAt(rs.getTimestamp("CONTACT_AT"));
                dto.setRequestOrResponseFlg(rs.getInt("REQUEST_OR_RESPONSE_FLG"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
            }
            return dto;
        }
    }

    /**
     * コンタクトIDに該当するコンタクト情報リストを取得する
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

    /**
     * コンタクトを新規作成する
     * @param dto コンタクト情報情報
     * @return コンタクトID
     * @throws SQLException SQL例外
     */

     public int insertContact(ContactDto dto) throws SQLException {

        // SQL文を作成する (コンタクト情報の追加)
        StringBuffer sbContact = new StringBuffer();
        sbContact.append(" insert into CONTACT");
        sbContact.append("           (");
        sbContact.append("             USER_NO");
        sbContact.append("           )");
        sbContact.append("      values");
        sbContact.append("           (");
        sbContact.append("             ?");
        sbContact.append("           )");

        // SQL文を作成する (テストIDの取得)
        StringBuffer sbDetail = new StringBuffer();
        sbDetail.append("     select CONTACT_ID");
        sbDetail.append("       from CONTACT");
        sbDetail.append("   order by CONTACT_ID desc limit 1");

        int contactId = 0;

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sbContact.toString())) {

            // プレースホルダーに値をセットする
            //ps.setInt(1, dto.getUserNo());
            ps.setInt(1, 1);

            // SQLを実行する
            ps.executeUpdate();
        }

        // ステートメントオブジェクトを作成する
         try (Statement stmt = conn.createStatement()) {
             ResultSet rs = stmt.executeQuery(sbDetail.toString());
             while (rs.next()) {
                 contactId = rs.getInt("CONTACT_ID");
             }
         }
        return contactId;
    }

    /**
     * コンタクトにリクエストを登録する
     * @param dto コンタクト情報情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */

     public int insertRequest(ContactDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sbContact = new StringBuffer();
        sbContact.append(" insert into CONTACT_DETAIL");
        sbContact.append("           (");
        sbContact.append("             CONTACT_ID");
        sbContact.append("            ,CONTACT_DETAIL");
        sbContact.append("            ,REQUEST_OR_RESPONSE_FLG");
        sbContact.append("            ,MANAGE_NO");
        sbContact.append("           )");
        sbContact.append("      values");
        sbContact.append("           (");
        sbContact.append("             ?");
        sbContact.append("            ,?");
        sbContact.append("            ,0");
        sbContact.append("            ,0");
        sbContact.append("           )");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sbContact.toString())) {

            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getContactId());
            ps.setString(2, dto.getContactDetail());
            // SQLを実行する
            return ps.executeUpdate();
        }
    }

    /**
     * コンタクトにリクエストを登録する
     * @param dto リクエスト情報情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */

     public int insertResponse(ContactDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sbContact = new StringBuffer();
        sbContact.append(" insert into CONTACT_DETAIL");
        sbContact.append("           (");
        sbContact.append("             CONTACT_ID");
        sbContact.append("            ,CONTACT_DETAIL");
        sbContact.append("            ,REQUEST_OR_RESPONSE_FLG");
        sbContact.append("            ,MANAGE_NO");
        sbContact.append("           )");
        sbContact.append("      values");
        sbContact.append("           (");
        sbContact.append("             ?");
        sbContact.append("            ,?");
        sbContact.append("            ,1");
        sbContact.append("            ,?");
        sbContact.append("           )");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sbContact.toString())) {

            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getContactId());
            ps.setString(2, dto.getContactDetail());
            ps.setInt(3, dto.getManageNo());
            // SQLを実行する
            return ps.executeUpdate();
        }
    }
}
