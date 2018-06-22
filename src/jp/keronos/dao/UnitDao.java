package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.keronos.dto.UnitDto;


public class UnitDao {

    /** コネクション */
    protected Connection conn;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param conn コネクション
     */
    public UnitDao(Connection conn) {
        this.conn = conn;
    }

    public int count(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       COUNT(*) AS RECORD_COUNT");
        sb.append("  from UNIT");
        sb.append(" where COURSE_ID = ?");

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



    /**
     * 単元情報リストを取得する
     * @return 単元情報リスト
     * @throws SQLException SQL例外
     */

    public ArrayList<UnitDto> selectAll() throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("   select");
        sb.append("          UNIT_ID");
        sb.append("         ,UNIT_TITLE");
        sb.append("         ,UNIT_TEXT");
        sb.append("         ,COURSE_ID");
        sb.append("         ,UPDATE_NUMBER");
        sb.append("         ,MANAGE_NO");
        sb.append("         ,DELETE_FLG");
        sb.append("     from UNIT");
        sb.append(" order by UNIT_ID desc");

        ArrayList<UnitDto> list = new ArrayList<UnitDto>();

        // ステートメントオブジェクトを作成する
        try (Statement stmt = conn.createStatement()) {

            // SQL文を実行する
            ResultSet rs = stmt.executeQuery(sb.toString());
            while (rs.next()) {
                UnitDto dto = new UnitDto();
                dto.setUnitId(rs.getInt("UNIT_ID"));
                dto.setUnitTitle(rs.getString("UNIT_TITLE"));
                dto.setUnitText(rs.getString("UNIT_TEXT"));
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));
                list.add(dto);
            }
            return list;
        }
    }
}


