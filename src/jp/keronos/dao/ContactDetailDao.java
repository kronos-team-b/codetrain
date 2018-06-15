package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.keronos.dto.ContactDetailDto;

public class ContactDetailDao {

    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public ContactDetailDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * �R���^�N�g�ڍ׏����X�V����
     * @param dto �R���^�N�g�ڍ׏��
     * @return �X�V����
     * @throws SQLException SQL��O
     */
    public int insert(ContactDetailDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append(" insert into CONTACT_DETAIL");
        sb.append("           (");
        sb.append("             CONTACT_DETAIL_ID");
        sb.append("             CONTACT_ID");
        sb.append("            ,CONTACT_DETAIL");
        sb.append("            ,CONTACT_AT");
        sb.append("            ,REQUEST_OR_RESPONSE_FLG");
        sb.append("            ,MANAGE_NO");
        sb.append("      values");
        sb.append("           (");
        sb.append("             ?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("            ,?");
        sb.append("           )");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setint(1, dto.getContactDetailId());
            ps.setint(2, dto.getContactId());
            ps.setString(3, dto.getContactDetail());
            ps.setTimestamp(4, dto.getContactAt());
            ps.setBoolean(5, getRequestOrResponseFlg());
            ps.setString(6, dto.getManageId());

            // SQL�����s����
            return ps.executeUpdate();
        }
    }

    /**
     * �R���^�N�gID�ɊY������R���^�N�g�ڍ׏����擾�A�e�[�u�������ɂ��R���^�N�gID�ɕR�Â����p��ID���擾
     * * @param ContactId�@�R���^�N�gID
     * * @return �R���^�N�g�ڍ׏��
     * @throws SQLException SQL��O
     */
    public List<ContactDetailDto> selectByContactId(int contactId) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("select");
        sb.append("        CONTACT_DETAIL_ID");
        sb.append("        ,CONTACT_ID");
        sb.append("        ,CONTACT_DETAIL");
        sb.append("        ,CONTACT_AT");
        sb.append("        ,REQUEST_OR_RESPONSE_FLG");
        sb.append("        ,MANAGE_NO");
        sb.append("    from");
        sb.append("            CONTACT_DETAIL");
        sb.append(" where CONTACT_ID = ?");
        sb.append(" order by CONTACT_AT DESC");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, contactId);

            // SQL�������s����
            ResultSet rs = ps.executeQuery();
            List<ContactDetailDto> List = new ArrayList<>();
            while (rs.next()) {
                ContactDetailDto dto = new ContactDetailDto();
                dto.setContactDetailId(rs.getInt("CONTACT_DETAIL_ID"));
                dto.setContactId(rs.getInt("CONTACT_ID"));
                dto.setContactDetail(rs.getString("CONTACT_DETAIL"));
                dto.setContactAt(rs.getTimestamp("CONTACT_AT"));
                dto.setRequestOrResponseFlg(rs.getBoolean("REQUEST_OR_RESPONSE_FLG"));
                dto.setManageId(rs.getInt("MANAGE_NO"));

                List.add(dto);
            }
            return List;
        }
    }
}
