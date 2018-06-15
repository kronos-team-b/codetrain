package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ContactDao {

    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public ContactDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * �A������ǉ�����
     * @param dto �A�����
     * @return �X�V����
     * @throws SQLException SQL��O
     */

    public int insert(ContactDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into CONTACT");
        sb.append("           (");
        sb.append("             CONTACT_ID");
        sb.append("            ,USER_NO");
        sb.append("           )");
        sb.append("      values");
        sb.append("           (");
        sb.append("             ?");
        sb.append("            ,?");
        sb.append("           )");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getContactId());
            ps.setInt(2, dto.getUserNo());

            // SQL�����s����
            return ps.executeUpdate();
        }
    }
}
