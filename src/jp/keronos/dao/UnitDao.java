package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UnitDto;


public class UnitDao {

    /** コネクション */
    protected Connection connection;

    /**
     * コンストラクタ
     * コネクションをフィールドに設定する
     * @param connection コネクション
     */
    public UnitDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * カリキュラムテストの数を取得
     * @param courseId
     * @return カリキュラムテストの数
     * @throws SQLException
     */
    public int count(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       COUNT(*) AS RECORD_COUNT");
        sb.append("  from UNIT");
        sb.append(" where COURSE_ID = ?");

        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

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
        sb.append(" order by UNIT_ID");

        ArrayList<UnitDto> list = new ArrayList<UnitDto>();

        // ステートメントオブジェクトを作成する
        try (Statement stmt = connection.createStatement()) {

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
    
    /**
     * 単元情報リストを取得する
     * @return 単元情報リスト
     * @throws SQLException SQL例外
     */

    public ArrayList<LearningHistoryDto> selectAllAndUserHistory(int userNo) throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("          select");
        sb.append("                 LH.USER_NO");
        sb.append("                ,LH.UNIT_ID");
        sb.append("                ,LH.SKIP_FLG");
        sb.append("                ,LH.END_FLG");
        sb.append("                ,LH.UNIT_TEST_POINT");
        sb.append("                ,LH.TEST_AT");
        sb.append("                ,UN.UNIT_TITLE");
        sb.append("                ,UN.COURSE_ID");
        sb.append("            from UNIT as UN");
        sb.append(" left outer join LEARNING_HISTORY as LH");
        sb.append("              on UN.UNIT_ID = LH.UNIT_ID");
        sb.append("             and LH.USER_NO = ?");
        sb.append("           where UN.DELETE_FLG = 0");
        sb.append("        order by UN.UNIT_ID");

        ArrayList<LearningHistoryDto> list = new ArrayList<LearningHistoryDto>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        // ステートメントオブジェクトを作成する
        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

        	preparedStatement.setInt(1, userNo);
        	
            // SQL文を実行する
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	LearningHistoryDto dto = new LearningHistoryDto();
            	dto.setUserNo(rs.getInt("USER_NO"));
            	dto.setUnitId(rs.getInt("UNIT_ID"));
            	dto.setSkipFlg(rs.getInt("SKIP_FLG"));
            	dto.setEndFlg(rs.getInt("END_FLG"));
            	dto.setUnitTestPoint(rs.getInt("UNIT_TEST_POINT"));
            	if(rs.getTimestamp("TEST_AT") != null) {
                	dto.setTestAt(sdf.format(rs.getTimestamp("TEST_AT")));            		
            	}
            	dto.setUnitTitle(rs.getString("UNIT_TITLE"));
            	dto.setCourseId(rs.getInt("COURSE_ID"));
                list.add(dto);
            }
            return list;
        }
    }


  public ArrayList<UnitDto> selectByCourseId(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select ");
        sb.append("       UNIT_ID,");
        sb.append("       UNIT_TITLE,");
        sb.append("       UNIT_TEXT,");
        sb.append("       COURSE_ID");
        sb.append("  from UNIT");
        sb.append(" where COURSE_ID = ?");

        ArrayList<UnitDto> list = new ArrayList<>();
        UnitDto unitDto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {

                unitDto = new UnitDto();
                unitDto.setUnitId(resultSet.getInt("UNIT_ID"));
                unitDto.setUnitTitle(resultSet.getString("UNIT_TITLE"));
                unitDto.setUnitText(resultSet.getString("UNIT_TEXT"));
                unitDto.setCourseId(resultSet.getInt("COURSE_ID"));

                list.add(unitDto);
            }
        }

        return list;
    }

}


