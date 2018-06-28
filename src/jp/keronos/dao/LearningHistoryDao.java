package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jp.keronos.dto.LearningHistoryDto;
import jp.keronos.dto.UnitDto;

public class LearningHistoryDao {

    protected Connection connection;

    public LearningHistoryDao(Connection connection) {
        this.connection = connection;
    }

    public int count(int userNo, int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("       COUNT(*) AS RECORD_COUNT");
        sb.append("  from LEARNING_HISTORY");
        sb.append(" where USER_NO = ?");
        sb.append(" and COURSE_ID = ?");
        sb.append(" and END_FLG = true");

        int count = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("RECORD_COUNT");
            }
        }
        return count;
    }

    public UnitDto selectMinUnitIdByEndFlg(int userNo, int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" select UNIT_ID");
        sb.append("   from LEARNING_HISTORY");
        sb.append("  where USER_NO = ?");
        sb.append("    and COURSE_ID = ?");
        sb.append("    and SKIP_FLG = true");

        UnitDto unit = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                unit = new UnitDto();
                unit.setUnitId(resultSet.getInt("UNIT_ID"));
            }
        }

        return unit;
    }

    public ArrayList<LearningHistoryDto> selectByUserNo(int userNo) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("   select LEARNING_HISTORY_ID");
        sb.append("         ,USER_NO");
        sb.append("         ,COURSE_ID");
        sb.append("         ,UNIT_ID");
        sb.append("         ,SKIP_FLG");
        sb.append("         ,END_FLG");
        sb.append("         ,UNIT_TEST_POINT");
        sb.append("         ,TEST_AT");
        sb.append("   from LEARNING_HISTORY");
        sb.append("  where USER_NO = ?");

        ArrayList<LearningHistoryDto> list = new ArrayList<LearningHistoryDto>();

        // ステートメントオブジェクトを作成する
        try (PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {
            preparedStatement.setInt(1, userNo);

            // SQL文を実行する
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                LearningHistoryDto dto = new LearningHistoryDto();
                dto.setLearningHistoryId(rs.getInt("LEARNING_HISTORY_ID"));
                dto.setUserNo(rs.getInt("USER_NO"));
                dto.setUserNo(rs.getInt("COURSE_ID"));
                dto.setUserNo(rs.getInt("UNIT_ID"));
                dto.setUserNo(rs.getInt("SKIP_FLG"));
                dto.setUserNo(rs.getInt("END_FLG"));
                dto.setUserNo(rs.getInt("UNIT_TEST_POINT"));
                dto.setUserNo(rs.getInt("TEST_AT"));
                list.add(dto);
            }
        }
        return list;
    }

    public void insertInit(LearningHistoryDto dto) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" insert into LEARNING_HISTORY(");
        sb.append("     USER_NO,");
        sb.append("     COURSE_ID,");
        sb.append("     UNIT_ID");
        sb.append(" )");
        sb.append(" values(");
        sb.append("     ?,?,?");
        sb.append(" )");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, dto.getUserNo());
            preparedStatement.setInt(2, dto.getCourseId());
            preparedStatement.setInt(3, dto.getUnitId());

            preparedStatement.executeUpdate();
        }
    }

    public void insert(LearningHistoryDto dto) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" insert into LEARNING_HISTORY(");
        sb.append("     USER_NO,");
        sb.append("     COURSE_ID,");
        sb.append("     UNIT_ID,");
        sb.append("     UNIT_TEST_POINT,");
        sb.append("     END_FLG");
        sb.append("     SKIP_FLG");
        sb.append(" )");
        sb.append(" values(");
        sb.append("     ?,?,?,?,1,0");
        sb.append(" )");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, dto.getUserNo());
            preparedStatement.setInt(2, dto.getCourseId());
            preparedStatement.setInt(3, dto.getUnitId());
            preparedStatement.setInt(4, dto.getUnitTestPoint());

            preparedStatement.executeUpdate();
        }
    }

    public void update(LearningHistoryDto dto) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" update LEARNING_HISTORY");
        sb.append(" set");
        sb.append("     SKIP_FLG = 0,");
        sb.append("     END_FLG = 1,");
        sb.append("     UNIT_TEST_POINT = ?");
        sb.append(" where LEARNING_HISTORY_ID = ?");

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, dto.getUnitTestPoint());
            preparedStatement.setInt(2, dto.getLearningHistoryId());

            preparedStatement.executeUpdate();
        }
    }

    public int selectLearningHistory(LearningHistoryDto learningHistoryDto) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("     LEARNING_HISTORY_ID");
        sb.append(" from LEARNING_HISTORY");
        sb.append(" where USER_NO = ?");
        sb.append(" and COURSE_ID = ?");
        sb.append(" and UNIT_ID = ?");

        int id = 0;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, learningHistoryDto.getUserNo());
            preparedStatement.setInt(2, learningHistoryDto.getCourseId());
            preparedStatement.setInt(3, learningHistoryDto.getUnitId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                id = resultSet.getInt("LEARNING_HISTORY_ID");
            }
        }
        return id;
    }

    public boolean exists(LearningHistoryDto dto) throws SQLException {

        String sql = ""
                + "select LEARNING_HISTORY_ID "
                + "from LEARNING_HISTORY "
                + "where USER_NO = ? "
                + "and COURSE_ID = ? "
                + "and UNIT_ID = ?";

        boolean exists = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, dto.getUserNo());
            preparedStatement.setInt(2, dto.getCourseId());
            preparedStatement.setInt(3, dto.getUnitId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }
        }

        return exists;
    }

    public boolean existsByUserNoAndCourseID(LearningHistoryDto dto) throws SQLException {

        String sql = ""
                + "select LEARNING_HISTORY_ID "
                + "from LEARNING_HISTORY "
                + "where USER_NO = ? "
                + "and COURSE_ID = ? ";

        boolean exists = false;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, dto.getUserNo());
            preparedStatement.setInt(2, dto.getCourseId());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                exists = true;
            }
        }

        return exists;
    }

    public void updateSkipFlg(LearningHistoryDto dto) throws SQLException {

        String sql = ""
                + "update LEARNING_HISTORY "
                + "set SKIP_FLG = ? "
                + "where USER_NO = ? "
                + "and UNIT_ID = ? "
                + "and COURSE_ID = ? ";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, dto.getSkipFlg());
            preparedStatement.setInt(2, dto.getUserNo());
            preparedStatement.setInt(3, dto.getUnitId());
            preparedStatement.setInt(4, dto.getCourseId());

            preparedStatement.executeUpdate();
        }
    }

    public ArrayList<LearningHistoryDto> selectLearned(int userNo, int courseId) throws SQLException {

        String sql = ""
                + "select UNIT_ID, UNIT_TEST_POINT "
                + "from LEARNING_HISTORY "
                + "where USER_NO = ? "
                + "and COURSE_ID = ? ";

        LearningHistoryDto dto;
        ArrayList<LearningHistoryDto> list = new ArrayList<>();

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userNo);
            preparedStatement.setInt(2, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                dto = new LearningHistoryDto();
                dto.setUnitId(resultSet.getInt("UNIT_ID"));
                dto.setUnitTestPoint(resultSet.getInt("UNIT_TEST_POINT"));

                list.add(dto);
            }
        }

        return list;
    }
}

