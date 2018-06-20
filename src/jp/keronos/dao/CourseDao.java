package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.CourseDto;

public class CourseDao {

    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public CourseDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * �R�[�X�����擾����
     * @return �R�[�X��񃊃X�g
     * @throws SQLException SQL��O
     */
    public CourseDto selectByCourseId(CourseDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         COURSE_ID");
        sb.append("        ,COURSE_NAME");
        sb.append("        ,OVERVIEW");
        sb.append("        ,REQUIRED_TIME");
        sb.append("        ,PRECONDITION");
        sb.append("        ,GOAL");
        sb.append("        ,IS_FREE_FLG");
        sb.append("        ,CATEGORY_ID");
        sb.append("        ,UPDATE_NUMBER");
        sb.append("        ,MANAGE_NO");
        sb.append("        ,DELETE_FLG");
        sb.append("    from COURSE");
        sb.append("   where COURSE_ID = ?");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getCourseId());

            // SQL�������s����
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new CourseDto();
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setCourseName(rs.getString("COURSE_NAME"));
                dto.setOverview(rs.getString("OVERVIEW"));
                dto.setRequiredTime(rs.getInt("REQUIRED_TIME"));
                dto.setPrecondition(rs.getString("PRECONDITION"));
                dto.setGoal(rs.getString("GOAL"));
                dto.setIsFreeFlg(rs.getInt("IS_FREE_FLG"));
                dto.setCategoryId(rs.getInt("CATEGORY_ID"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
            }
        }
            return dto;
    }
}
