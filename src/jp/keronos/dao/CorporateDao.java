package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.CorporateDto;

public class CorporateDao {

    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public CorporateDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * ���ID�ɕR�Â����NO���擾����
     * @param dto
     * @return�@���NO
     * @throws SQLException
     */
    public CorporateDto selectByCorporateId(CorporateDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("        CORPORATE_NO");
        sb.append("       ,CORPORATE_ID");
        sb.append("    from");
        sb.append("        CORPORATE");
        sb.append("   where");
        sb.append("        CORPORATE_ID = ?");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, dto.getCorporateId());

            // SQL�������s����
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
                dto.setCorporateId(rs.getString("CORPORATE_ID"));
            }
        }
        return dto;
    }
}
