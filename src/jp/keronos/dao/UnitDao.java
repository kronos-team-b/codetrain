package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.UnitDto;

public class UnitDao {

    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public UnitDao(Connection conn) {
        this.conn = conn;
    }

    public UnitDto selectByUnitId(UnitDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         UNIT_ID");
        sb.append("        ,UNIT_TITLE");
        sb.append("        ,UNIT_TEXT");
        sb.append("        ,COURSE_ID");
        sb.append("        ,UPDATE_NUMBER");
        sb.append("        ,MANAGE_NO");
        sb.append("        ,DELETE_FLG");
        sb.append("    from");
        sb.append("         UNIT");
        sb.append("   where UNIT_ID = ?");
        sb.append("     and DELETE_FLG = 0");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getUnitId());

         // SQL�������s����
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new UnitDto();
                dto.setUnitId(rs.getInt("UNIT_ID"));
                dto.setUnitTitle(rs.getString("UNIT_TITLE"));
                dto.setUnitText(rs.getString("UNIT_TEXT"));
                dto.setCourseId(rs.getInt("COURSE_ID"));
                dto.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                dto.setManageNo(rs.getInt("MANAGE_NO"));
                dto.setDeleteFlg(rs.getInt("DELETE_FLG"));

            }
            return dto;
        }
    }
}
