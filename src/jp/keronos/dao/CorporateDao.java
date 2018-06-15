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
     * �@�l�A�J�E���g�����擾����
     * @param �@�l�A�J�E���gID�@�p�X���[�h
     * @return �@�l�A�J�E���g���
     * @throws SQLException SQL��O
     */
    public CorporateDto findByIdAndPassword(String id, String password) throws SQLException {
        //SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        CORPORATE_NO");
        sb.append("        CORPORATE_ID");
//      sb.append("       ,PASSWORD");
        sb.append("       ,CORPORATE_NAME");
        sb.append("       ,FIRST_NAME");
        sb.append("       ,LAST_NAME");
        sb.append("       ,POSITION");
        sb.append("       ,DEPARTMENT");
        sb.append("       ,POSTAL_CODE");
        sb.append("       ,PREFECTURE_ID");
        sb.append("       ,ADRESS_LINE_1");
        sb.append("       ,ADRESS_LINE_2");
        sb.append("       ,EMAIL");
        sb.append("       ,DOMAIN");
        sb.append("       ,MANAGE_NO");
        sb.append("   from CORPORATE");
        sb.append("  where CORPORATE_ID = ?");
        sb.append("    and PASSWORD = sha2(?, 256)");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            ps.setString(2, password);

            // SQL�����s����
            ResultSet rs = ps.executeQuery();

            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                CorporateDto corporate = new CorporateDto();
                corporate.setCorporateNo(rs.getInt("CORPORATE_NO"));
                corporate.setCorporateId(rs.getString("CORPORATE_Id"));
//              user.setPassword(rs.getString("PASSWORD"));
                corporate.setCorporateName(rs.getString("CORPORATE_NAME"));
                corporate.setFirstName(rs.getString("FIRST_NAME"));
                corporate.setLastName(rs.getString("LAST_NAME"));
                corporate.setPosition(rs.getString("POSITION"));
                corporate.setDepartment(rs.getString("DEPARTMENT"));
                corporate.setPostalCode(rs.getString("POSTAL_CODE"));
                corporate.setPrefectureId(rs.getInt("PREFECTURE_ID"));
                corporate.setAdressLine1(rs.getString("ADRESS_LINE_1"));
                corporate.setAdressLine2(rs.getString("ADRESS_LINE_2"));
                corporate.setEmail(rs.getString("EMAIL"));
                corporate.setDomain(rs.getString("DOMAIN"));
                corporate.setManageNo(rs.getInt("MANAGE_NO"));

                return corporate;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
            return null;
        }
    }

    /**
     * �@�l�A�J�E���g�����X�V����
     * @param dto �@�l�A�J�E���g���
     * @return �X�V����
     * @throws SQLException SQL��O
     */
    public int updatePassword(CorporateDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append(" update");
        sb.append("        CORPORATE");
        sb.append("    set");
        sb.append("        PASSWORD = sha2(?, 256)");
        sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("  where CORPORATE_NO = ?");
        sb.append("    and UPDATE_NUMBER = ?");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, dto.getPassword());
            ps.setInt(2, dto.getCorporateNo());
            ps.setInt(3, dto.getUpdateNumber());

            // SQL�����s����
            return ps.executeUpdate();
        }
    }
}
