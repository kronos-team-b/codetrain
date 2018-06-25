package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.UnitTestChoicesDto;

/**
 * テスト選択肢表のDataAccessObject
 * @author Hiroki Nishio
 */

public class UnitTestChoicesDao {
    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public UnitTestChoicesDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * テスト選択肢を追加する
     * @param testid テストID
     * @param dto テスト選択肢情報
     * @throws SQLException SQL例外
     */
    public void insert(int testId, UnitTestChoicesDto dto) throws SQLException {

        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into UNIT_TEST_CHOICES");
        sb.append("           (");
        sb.append("             TEST_ID");
        sb.append("            ,CHOICE1");
        sb.append("            ,CHOICE2");
        sb.append("            ,CHOICE3");
        sb.append("            ,CHOICE4");
        sb.append("           )");
        sb.append("      values");
        sb.append("           (");
        sb.append("             ?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("           )");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())){
            // プレースホルダーに値をセットする
            ps.setInt(1, testId);
            ps.setString(2, dto.getChoice1());
            ps.setString(3, dto.getChoice2());
            ps.setString(4, dto.getChoice3());
            ps.setString(5, dto.getChoice4());

            // SQLを実行する
            ps.executeUpdate();
        }

    }

    public UnitTestChoicesDto selectByTestId(UnitTestChoicesDto dto) throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        TEST_ID");
        sb.append("       ,CHOICE1");
        sb.append("       ,CHOICE2");
        sb.append("       ,CHOICE3");
        sb.append("       ,CHOICE4");
        sb.append("   from UNIT_TEST_CHOICES");
        sb.append("  where TEST_ID = ?");

        // ステートメントオブジェクトを作成する
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // プレースホルダーに値をセットする
            ps.setInt(1, dto.getTestId());

         // SQL文を実行する

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                //チャンネルIDに該当するチャンネル名を取得する
                dto.setTestId(rs.getInt("TEST_ID"));
                dto.setChoice1(rs.getString("CHOICE1"));
                dto.setChoice2(rs.getString("CHOICE2"));
                dto.setChoice3(rs.getString("CHOICE3"));
                dto.setChoice4(rs.getString("CHOICE4"));
            }
        }
        return dto;
    }
}
