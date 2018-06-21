package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.SystemManageDto;

public class SystemManagementDao {
    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public SystemManagementDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 運営担当者情報を取得する
     * @param 運営担当者ID　パスワード
     * @return 運営担当者情報
     * @throws SQLException SQL例外
     */

    public SystemManageDto findByIdAndPassword(String id, String password) throws SQLException {

        //SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        MANAGE_NO");
        sb.append("       ,MANAGE_ID");
//        sb.append("       ,PASSWORD");
        sb.append("       ,FIRST_NAME");
        sb.append("       ,LAST_NAME");
        sb.append("       ,SUPER_USER_FLG");
        sb.append("       ,EDIT_CORPORATE_FLG");
        sb.append("       ,EDIT_TEXT_FLG");
        sb.append("       ,DELETE_FLG");
        sb.append("   from SYSTEM_MANAGE");
        sb.append("  where MANAGE_ID = ?");
        sb.append("    and PASSWORD = sha2(?, 256)");
        sb.append("    and DELETE_FLG = 0");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setString(1, id);
            ps.setString(2, password);

            // SQLを実行する
            ResultSet rs = ps.executeQuery();

            // 結果をDTOに詰める
            if (rs.next()) {
                SystemManageDto systemManage = new SystemManageDto();
                systemManage.setManageNo(rs.getInt("MANAGE_NO"));
                systemManage.setManageId(rs.getString("MANAGE_Id"));
//                systemManage.setPassword(rs.getString("PASSWORD"));
                systemManage.setFirstName(rs.getString("FIRST_NAME"));
                systemManage.setLastName(rs.getString("LAST_NAME"));
                systemManage.setSuperUserFlg(rs.getInt("SUPER_USER_FLG"));
                systemManage.setEditCorporateFlg(rs.getInt("EDIT_CORPORATE_FLG"));
                systemManage.setEditTextFlg(rs.getInt("EDIT_TEXT_FLG"));
                systemManage.setDeleteFlg(rs.getInt("DELETE_FLG"));
                return systemManage;
            }
            // 該当するデータがない場合はnullを返却する
            return null;
        }
    }

    /**
     * 運営担当者情報を取得する
     * @param 運営担当者ID
     * @return 運営担当者情報
     * @throws SQLException SQL例外
     */

    public SystemManageDto findById(String id) throws SQLException {

        //SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        MANAGE_NO");
        sb.append("       ,MANAGE_ID");
//        sb.append("       ,PASSWORD");
        sb.append("       ,FIRST_NAME");
        sb.append("       ,LAST_NAME");
        sb.append("       ,SUPER_USER_FLG");
        sb.append("       ,EDIT_CORPORATE_FLG");
        sb.append("       ,EDIT_TEXT_FLG");
        sb.append("       ,DELETE_FLG");
        sb.append("   from SYSTEM_MANAGE");
        sb.append("  where MANAGE_ID = ?");
        sb.append("    and DELETE_FLG = 0");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setString(1, id);

            // SQLを実行する
            ResultSet rs = ps.executeQuery();

            // 結果をDTOに詰める
            if (rs.next()) {
                SystemManageDto systemManage = new SystemManageDto();
                systemManage.setManageNo(rs.getInt("MANAGE_NO"));
                systemManage.setManageId(rs.getString("MANAGE_Id"));
//                systemManage.setPassword(rs.getString("PASSWORD"));
                systemManage.setFirstName(rs.getString("FIRST_NAME"));
                systemManage.setLastName(rs.getString("LAST_NAME"));
                systemManage.setSuperUserFlg(rs.getInt("SUPER_USER_FLG"));
                systemManage.setEditCorporateFlg(rs.getInt("EDIT_CORPORATE_FLG"));
                systemManage.setEditTextFlg(rs.getInt("EDIT_TEXT_FLG"));
                systemManage.setDeleteFlg(rs.getInt("DELETE_FLG"));
                return systemManage;
            }
            // 該当するデータがない場合はnullを返却する
            return null;
        }
    }

    /**
     * 運営担当者情報を更新する
     * @param dto 運営担当者情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */
    public int updatePassword(SystemManageDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" update");
        sb.append("             SYSTEM_MANAGE");
        sb.append("       set");
        sb.append("             PASSWORD = sha2(?, 256)");
        sb.append("            ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("  where MANAGE_NO = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setString(1, dto.getPassword());
            ps.setInt(2, dto.getManageNo());

            // SQLを実行する
            return ps.executeUpdate();
        }
    }
}