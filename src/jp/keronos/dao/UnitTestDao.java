package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jp.keronos.dto.UnitTestChoicesDto;
import jp.keronos.dto.UnitTestDto;

/**
 * カリキュラムテストのDataAccessObject
 * @author Hiroki Nishio
 */

/**
 * @author Hiroto
 *
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
        sb.append("    where TEST_ID = ? and DELETE_FLG = 0");

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

    public ArrayList<UnitTestDto> selectByUnitId(UnitTestDto dto) throws SQLException {

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
        sb.append("    where UNIT_ID = ? and DELETE_FLG = 0");

        ArrayList<UnitTestDto> list = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            ps.setInt(1, dto.getUnitId());

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                dto = new UnitTestDto();
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

                list.add(dto);
            }
        }
        return list;
    }

    /**
     * unit idに紐づくテストをランダムで一件取得する
     * @param unitId
     * @return テスト情報
     * @throws SQLException
     */
    public UnitTestDto randomSelectByUnitId(int unitId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("   select");
        sb.append("            TEST_ID,");
        sb.append("            TEST_TITLE,");
        sb.append("            ANSWER_TYPE_FLG,");
        sb.append("            TEST_CONTENT,");
        sb.append("            MODEL_ANSWER,");
        sb.append("            UNIT_ID,");
        sb.append("            COURSE_ID,");
        sb.append("            UPDATE_NUMBER,");
        sb.append("            MANAGE_NO");
        sb.append("       from UNIT_TEST");
        sb.append("      where UNIT_ID = ? and DELETE_FLG = 0");
        sb.append("   order by rand()");
        sb.append("      limit 1");

        UnitTestDto unitTestDto = null;

        try(PreparedStatement preparedStatement = conn.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, unitId);

            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                unitTestDto = new UnitTestDto();
                unitTestDto.setTestId(rs.getInt("TEST_ID"));
                unitTestDto.setTestTitle(rs.getString("TEST_TITLE"));
                unitTestDto.setAnswerTypeFlg(rs.getInt("ANSWER_TYPE_FLG"));
                unitTestDto.setTestContent(rs.getString("TEST_CONTENT"));
                unitTestDto.setModelAnswer(rs.getString("MODEL_ANSWER"));
                unitTestDto.setUnitId(rs.getInt("UNIT_ID"));
                unitTestDto.setCourseId(rs.getInt("COURSE_ID"));
                unitTestDto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                unitTestDto.setManageNo(rs.getInt("MANAGE_NO"));
            }
        }

        return unitTestDto;
    }

    /**
     * unit testの数を取得
     * @param userNo
     * @return unit testの数
     * @throws SQLException
     */
    public int count(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       count(distinct UNIT_ID) AS RECORD_COUNT");
        sb.append("  from UNIT_TEST");
        sb.append(" where DELETE_FLG = 0");
        sb.append("   and COURSE_ID = ?");

        int count = 0;

        try (PreparedStatement preparedStatement = conn.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("RECORD_COUNT");
            }
        }
        return count;
    }

    public ArrayList<UnitTestChoicesDto> selectUnitTestChoise() throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("        TEST_ID,");
        sb.append("        CHOICE1,");
        sb.append("        CHOICE2,");
        sb.append("        CHOICE3,");
        sb.append("        CHOICE4");
        sb.append("   from UNIT_TEST_CHOICES");

        ArrayList<UnitTestChoicesDto> list = new ArrayList<>();
        UnitTestChoicesDto dto = null;

        try(PreparedStatement preparedStatement = conn.prepareStatement(sb.toString())) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                dto = new UnitTestChoicesDto();
                dto.setTestId(resultSet.getInt("TEST_ID"));
                dto.setChoice1(resultSet.getString("CHOICE1"));
                dto.setChoice2(resultSet.getString("CHOICE2"));
                dto.setChoice3(resultSet.getString("CHOICE3"));
                dto.setChoice4(resultSet.getString("CHOICE4"));

                list.add(dto);
            }
        }

        return list;
    }

    /**
     * 正解リストを取得する
     * @param courseId
     * @param time
     * @return 正解リスト
     * @throws SQLException
     */
    public ArrayList<UnitTestDto> selectAnswerByTestIds(ArrayList<Integer> testIds) throws SQLException {

        List<String> queries = new ArrayList<>();
        for (Integer testId : testIds) {
            queries.add(" TEST_ID = " + testId);
        }
        String query = String.join(" or", queries);

        String sql =
                "select "
                + "MODEL_ANSWER "
                + "from UNIT_TEST "
                + "where "
                + query;

        ArrayList<UnitTestDto> list = new ArrayList<>();
        UnitTestDto unitTestDto = null;

        try(PreparedStatement preparedStatement = conn.prepareStatement(sql)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                unitTestDto = new UnitTestDto();
                unitTestDto.setModelAnswer(resultSet.getString("MODEL_ANSWER"));

                list.add(unitTestDto);
            }

            return list;
        }
    }
}
