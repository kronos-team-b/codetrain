package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.UserDto;
import jp.kronos.dto.ChannelDto;

public class UserDao {
    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * ���p�ҏ����擾����
     * @param ���p��ID�@�p�X���[�h
     * @return ���p�ҏ��
     * @throws SQLException SQL��O
     */
    public UserDto findByIdAndPassword(String id, String password) throws SQLException {

        //SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append(" select");
        sb.append("        USER_NO");
        sb.append("        USER_ID");
//        sb.append("       ,PASSWORD");
        sb.append("       ,FIRST_NAME");
        sb.append("       ,LAST_NAME");
        sb.append("       ,INACTIVE_FLG");
        sb.append("       ,CORPORATE_NO");
        sb.append("       ,UPDATE_NUMBER");
        sb.append("       ,DELETE_FLG");
        sb.append("   from USER");
        sb.append("  where USER_ID = ?");
        sb.append("    and PASSWORD = sha2(?, 256)");
        sb.append("    and DELETE_FLG = 0");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, id);
            ps.setString(2, password);

            // SQL�����s����
            ResultSet rs = ps.executeQuery();

            // ���ʂ�DTO�ɋl�߂�
            if (rs.next()) {
                UserDto user = new UserDto();
                user.setUserNo(rs.getInt("USER_NO"));
                user.setUserId(rs.getString("USER_Id"));
//                user.setPassword(rs.getString("PASSWORD"));
                user.setFirstName(rs.getString("FIRST_NAME"));
                user.setLastName(rs.getString("LAST_NAME"));
                user.setInactiveFlg(rs.getBoolean("INACTIVE_FLG"));
                user.setUpdateNumber(rs.getInt("UPDATE_NUMBER"));
                user.setDeleteFlg(rs.getBoolean("DELETE_FLG"));
                return user;
            }
            // �Y������f�[�^���Ȃ��ꍇ��null��ԋp����
            return null;
        }
    }

    /**
     * ���p�ҏ����X�V����
     * @param dto ���p�ҏ��
     * @return �X�V����
     * @throws SQLException SQL��O
     */
    public int updatePassword(UserDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append(" update");
        sb.append("        USER");
        sb.append("    set");
        sb.append("        PASSWORD = sha2(?, 256)");
        sb.append("       ,UPDATE_NUMBER = UPDATE_NUMBER + 1");
        sb.append("  where USER_NO = ?");
        sb.append("    and UPDATE_NUMBER = ?");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {

            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setString(1, dto.getPassword());
            ps.setInt(2, dto.getUserNo());
            ps.setInt(3, dto.getUpdateNumber());

            // SQL�����s����
            return ps.executeUpdate();
        }
    }
}
