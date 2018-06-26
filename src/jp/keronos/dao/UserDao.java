package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
        sb.append("       ,UPDATE_AT");
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
                user.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                user.setDeleteFlg(rs.getInt("DELETE_FLG"));
                return user;
            }
            // 該当するデータがない場合はnullを返却する
            return null;
        }
    }

     /**
     * 利用者情報を取得する
     * @param 利用者ID
     * @return 利用者情報
     * @throws SQLException SQL例外
     */
    public UserDto findById(String id) throws SQLException {

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
        sb.append("       ,UPDATE_AT");
        sb.append("       ,UPDATE_NUMBER");
        sb.append("       ,DELETE_FLG");
        sb.append("   from USER");
        sb.append("  where USER_ID = ?");
        sb.append("    and DELETE_FLG = 0");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setString(1, id);

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
                user.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                user.setDeleteFlg(rs.getInt("DELETE_FLG"));
                return user;
            }
            // 該当するデータがない場合はnullを返却する
            return null;
        }
    }

    /**
    * 利用者情報を取得する
    * @param 利用者NO
    * @return 利用者情報
    * @throws SQLException SQL例外
    */
   public UserDto findByUserNo(int userNo) throws SQLException {

       //SQL文を作成する
       StringBuffer sb = new StringBuffer();
       sb.append(" select");
       sb.append("        USER_NO");
       sb.append("       ,USER_ID");
//       sb.append("       ,PASSWORD");
       sb.append("       ,FIRST_NAME");
       sb.append("       ,LAST_NAME");
       sb.append("       ,INACTIVE_FLG");
       sb.append("       ,CORPORATE_NO");
       sb.append("       ,UPDATE_AT");
       sb.append("       ,UPDATE_NUMBER");
       sb.append("       ,DELETE_FLG");
       sb.append("   from USER");
       sb.append("  where USER_NO = ?");
       sb.append("    and DELETE_FLG = 0");

       // ステートメントオブジェクトを作成する
       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
           // プレースホルダーに値をセットする
           ps.setInt(1, userNo);

           // SQLを実行する
           ResultSet rs = ps.executeQuery();

           // 結果をDTOに詰める
           if (rs.next()) {
               UserDto user = new UserDto();
               user.setUserNo(rs.getInt("USER_NO"));
               user.setUserId(rs.getString("USER_Id"));
//               user.setPassword(rs.getString("PASSWORD"));
               user.setFirstName(rs.getString("FIRST_NAME"));
               user.setLastName(rs.getString("LAST_NAME"));
               user.setInactiveFlg(rs.getInt("INACTIVE_FLG"));
               user.setCorporateNo(rs.getInt("CORPORATE_NO"));
               user.setUpdateAt(rs.getTimestamp("UPDATE_AT"));
               user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
               user.setDeleteFlg(rs.getInt("DELETE_FLG"));
               return user;
           }
           // 該当するデータがない場合はnullを返却する
           return null;
       }
   }



   public ArrayList<UserDto> findByCorporateNo(int corporateNo) throws SQLException {

       // SQL文を作成する
       StringBuffer sb = new StringBuffer();
       sb.append("  select");
       sb.append("         USER_NO");
       sb.append("        ,USER_ID");
       sb.append("        ,PASSWORD");
       sb.append("        ,LAST_NAME");
       sb.append("        ,FIRST_NAME");
       sb.append("        ,INACTIVE_FLG");
       sb.append("        ,CORPORATE_NO");
       sb.append("        ,UPDATE_NUMBER");
       sb.append("        ,DELETE_FLG");
       sb.append("    from");
       sb.append("         USER");
       sb.append("   where");
       sb.append("         DELETE_FLG = 0");
       sb.append("     and CORPORATE_NO = ?");

       // ステートメントオブジェクトを作成する
       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
           // プレースホルダーに値をセットする
           ps.setInt(1, corporateNo);
           // SQL文を実行する
           ResultSet rs = ps.executeQuery();
           ArrayList<UserDto> List = new ArrayList<>();
           while (rs.next()) {
               UserDto dto = new UserDto();
               dto.setUserNo(rs.getInt("USER_NO"));
               dto.setUserId(rs.getString("USER_ID"));
               dto.setPassword(rs.getString("PASSWORD"));
               dto.setFirstName(rs.getString("FIRST_NAME"));
               dto.setLastName(rs.getString("LAST_NAME"));
               dto.setInactiveFlg(rs.getInt("INACTIVE_FLG"));
               dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
               dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
               dto.setDeleteFlg(rs.getInt("DELETE_FLG"));
               List.add(dto);
           }
           return List;
       }
   }

   public int insertByCorporateNo(UserDto dto) throws SQLException {

       // SQL文を作成する
       StringBuffer sb = new StringBuffer();
       sb.append("   insert into");
       sb.append("          USER");
       sb.append("          (");
       sb.append("          USER_ID");
       sb.append("         ,PASSWORD");
       sb.append("         ,LAST_NAME");
       sb.append("         ,FIRST_NAME");
       sb.append("         ,INACTIVE_FLG");
       sb.append("         ,CORPORATE_NO");
       sb.append("         ,UPDATE_NUMBER");
       sb.append("         ,DELETE_FLG");
       sb.append("          )");
       sb.append("   values");
       sb.append("          (");
       sb.append("          ?");
       sb.append("         ,?");
       sb.append("         ,?");
       sb.append("         ,?");
       sb.append("         ,0");
       sb.append("         ,?");
       sb.append("         ,0");
       sb.append("         ,0");
       sb.append("          )");

       // ステートメントオブジェクトを作成する
       try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

           // プレースホルダーに値をセットする
           ps.setString(1, dto.getUserId());
           ps.setString(2, dto.getUserId());
           ps.setString(3, dto.getLastName());
           ps.setString(4, dto.getFirstName());
           ps.setInt(5, dto.getCorporateNo());

           // SQLを実行する
           return ps.executeUpdate();
       }
   }


   public int updateToActive(UserDto dto) throws SQLException {

    // SQL文を作成する (休止情報の追加)
        StringBuffer sbReason = new StringBuffer();
        sbReason.append(" update");
        sbReason.append("        INACTIVE_REASON");
        sbReason.append("    set");
        sbReason.append("        ACTIVE_AT = current_timestamp");
        sbReason.append("  where USER_NO = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement psReason = conn.prepareStatement(sbReason.toString())) {
            // プレースホルダーに値をセットする
            psReason.setInt(1, dto.getUserNo());

            // SQLを実行する
            psReason.executeUpdate();
        }

        // SQL文を作成する (ユーザの活動フラグを追加)
        StringBuffer sbUser = new StringBuffer();
        sbUser.append(" update");
        sbUser.append("        USER");
        sbUser.append("    set");
        sbUser.append("        INACTIVE_FLG = 0");
        sbUser.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sbUser.append("       ,UPDATE_AT = current_timestamp");
        sbUser.append("       ,CORPORATE_NO = ?");
        sbUser.append("  where USER_NO = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement psUser = conn.prepareStatement(sbUser.toString())) {
            // プレースホルダーに値をセットする
            psUser.setInt(1, dto.getCorporateNo());
            psUser.setInt(2, dto.getUserNo());

            // SQLを実行する
            return psUser.executeUpdate();
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

    /**
     * 利用者情報を更新する
     * @param dto 利用者情報
     * @return 更新件数
     * @throws SQLException SQL例外
     */
    public int deleteByCorporateNo(UserDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" update");
        sb.append("        USER");
        sb.append("    set");
        sb.append("        CORPORATE_NO = ?");
        sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("       ,DELETE_FLG = 1");
        sb.append("  where USER_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getCorporateNo());
            ps.setString(2, dto.getUserId());

            // SQLを実行する
            return ps.executeUpdate();
        }
    }
}


