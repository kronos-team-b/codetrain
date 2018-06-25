package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jp.keronos.dto.InactiveReasonDto;

public class InactiveReasonDao {

	
    protected Connection conn;

    public InactiveReasonDao(Connection conn) {
        this.conn = conn;
    }
    
    /**
     * コンタクトを新規作成する
     * @param dto コンタクト情報情報
     * @return コンタクトID
     * @throws SQLException SQL例外
     */

     public int insertReason(InactiveReasonDto dto) throws SQLException {

        // SQL文を作成する (休止情報の追加)
        StringBuffer sbReason = new StringBuffer();
        sbReason.append(" insert into INACTIVE_REASON");
        sbReason.append("           (");
        sbReason.append("             USER_NO");
        sbReason.append("            ,REASON");
        sbReason.append("            ,ACTIVE_AT");
        sbReason.append("            ,INACTIVE_AT");
        sbReason.append("           )");
        sbReason.append("      values");
        sbReason.append("           (");
        sbReason.append("             ?");
        sbReason.append("            ,?");
        sbReason.append("            ,?");
        sbReason.append("            ,current_timestamp");
        sbReason.append("           )");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement psReason = conn.prepareStatement(sbReason.toString())) {

            // プレースホルダーに値をセットする
            psReason.setInt(1, dto.getUserNo());
            psReason.setString(2, dto.getReason());
            psReason.setTimestamp(3, dto.getActiveAt());

            // SQLを実行する
            psReason.executeUpdate();
        }
        
        // SQL文を作成する (コンタクトIDの取得)
        StringBuffer sbUser = new StringBuffer();
        sbUser.append(" update");
        sbUser.append("        USER");
        sbUser.append("    set");
        sbUser.append("        INACTIVE_FLG = 1");
        sbUser.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sbUser.append("       ,UPDATE_AT = current_timestamp");
        sbUser.append("  where USER_NO = ?");

        // ステートメントオブジェクトを作成する
         try (PreparedStatement psUser = conn.prepareStatement(sbUser.toString())) {
        	// プレースホルダーに値をセットする
             psUser.setInt(1, dto.getUserNo());

             // SQLを実行する
             psUser.executeUpdate();
         }
        return 1;
    }
}
