package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.keronos.dto.UnitTestDto;

/**
 * カリキュラムテストのDataAccessObject
 * @author Hiroki Nishio
 */

public class UnitTestDao {
    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public UnitTestDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * カリキュラムテストを追加する
     * @param dto カリキュラムテスト情報
     * @return カリキュラムテストID
     * @throws SQLException SQL例外
     */
    public int insert(UnitTestDto dto) throws SQLException {

        // SQL文を作成する (テスト情報の追加)
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into UNIT_TEST");
        sb.append("           (");
        sb.append("             TEST_TITLE");
        sb.append("            ,ANSWER_TYPE_FLG");
        sb.append("            ,TEST_CONTENT");
        sb.append("            ,MODEL_ANSWER");
        sb.append("            ,UNIT_ID");
        sb.append("            ,COURSE_ID");
        sb.append("            ,UPDATE_NUMBER");
        sb.append("            ,MANAGE_NO");
        sb.append("            ,DELETE_FLG");
        sb.append("           )");
        sb.append("      values");
        sb.append("           (");
        sb.append("             ?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,0");
        sb.append("            ,?");
        sb.append("            ,0");
        sb.append("           )");

        // SQL文を作成する (テストIDの取得)
        StringBuffer sbTestId = new StringBuffer();
        sbTestId.append("     select TEST_ID");
        sbTestId.append("       from UNIT_TEST");
        sbTestId.append("   order by TEST_ID desc limit 1");

        int testId = 0;

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // プレースホルダーに値をセットする
            ps.setString(1, dto.getTestTitle());
            ps.setInt(2, dto.getAnswerTypeFlg());
            ps.setString(3, dto.getTestContent());
            ps.setString(4, dto.getModelAnswer());
            ps.setInt(5, dto.getUnitId());
            ps.setInt(6, dto.getCourseId());
            ps.setInt(7, dto.getManageNo());

            // SQLを実行する
            ps.executeUpdate();
        }

        // ステートメントオブジェクトを作成する
         try (Statement stmt = conn.createStatement()) {
             ResultSet rs = stmt.executeQuery(sbTestId.toString());
             while (rs.next()) {
                 testId = rs.getInt("TEST_ID");
             }
         }
        return testId;
    }

    public UnitTestDto selectByTestId(UnitTestDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("   select");
        sb.append("          TEST_ID");
        sb.append("         ,TEST_TITLE");
        sb.append("         ,ANSWER_TYPE_FLG");
        sb.append("         ,TEST_CONTENT");
        sb.append("         ,MODEL_ANSWER");
        sb.append("         ,UNIT_ID");
        sb.append("         ,COURSE_ID");
        sb.append("         ,UPDATE_NUMBER");
        sb.append("         ,MANAGE_NO");
        sb.append("         ,DELETE_FLG");
        sb.append("     from UNIT_TEST");
        sb.append("    where TEST_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getTestId());

         // SQL文を実行する

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //チャンネルIDに該当するチャンネル名を取得する
                dto.setTestId(rs.getInt("TEST_ID"));
                dto.setTestTitle(rs.getString("TEST_TITLE"));
                dto.setAnswerTypeFlg(rs.getInt("ANSWER_TYPE_FLG"));
                dto.setTestContent(rs.getString("TEST_CONTENT"));
                dto.setModelAnswer(rs.getString("MODEL_ANSWER"));
                dto.setUnitId(rs.getInt("UNIT_ID"));
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));
            }
        }
        return dto;
    }
}
