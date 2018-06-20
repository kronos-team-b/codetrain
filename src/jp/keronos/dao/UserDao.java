package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.UserDto;

public class UserDao {
    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * 利用者情報を取得する
     * @param 利用者ID　パスワード
     * @return 利用者情報
     * @throws SQLException SQL例外
     */
    public UserDto findByIdAndPassword(String id, String password) throws SQLException {

        //SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        USER_NO");
        sb.append("       ,USER_ID");
//        sb.append("       ,PASSWORD");
        sb.append("       ,FIRST_NAME");
        sb.append("       ,LAST_NAME");
        sb.append("       ,INACTIVE_FLG");
        sb.append("       ,CORPORATE_NO");
        sb.append("       ,UPDATE_NUMBER");
        sb.append("       ,DELETE_FLG");
        sb.append("   from USER");
        sb.append("  where USER_ID = ?");
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
                UserDto user = new UserDto();
                user.setUserNo(rs.getInt("USER_NO"));
                user.setUserId(rs.getString("USER_Id"));
//                user.setPassword(rs.getString("PASSWORD"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setInactiveFlg(rs.getInt("INACTIVE_FLG"));
                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                user.setDeleteFlg(rs.getInt("DELETE_FLG"));
                return user;
            }
            // 該当するデータがない場合はnullを返却する
            return null;
        }
    }

    /**
     * 利用者情報を更新する
     * @param dto 利用者情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */
    public int updatePassword(UserDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" update");
        sb.append("        USER");
        sb.append("    set");
        sb.append("        PASSWORD = sha2(?, 256)");
        sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("  where USER_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setString(1, dto.getPassword());
            ps.setString(2, dto.getUserId());

            // SQLを実行する
            return ps.executeUpdate();
        }
    }
}