package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jp.keronos.dto.BillDto;

public class BillDao {

    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public BillDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * ���NO�ɊY�����鐿�������擾����
     * @param coporateNo
     * @return �����ꗗ���
     * @throws SQLException
     */
    public List<BillDto> selectByCorporateNo(int coporateNo) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("        BILLING_ID");
        sb.append("        ,CORPORATE_NO");
        sb.append("        ,BILLING_DATE");
        sb.append("        ,PRICE_ID");
        sb.append("        ,NUMBER_OF_ACTIVE_ACCOUNT");
        sb.append("        ,NUMBER_OF_INACTIVE_ACCOUNT");
        sb.append("    from");
        sb.append("        BILL");
        sb.append("   where");
        sb.append("        CORPORATE_NO = ?");
  //      sb.append("order by BILLING_DATE DESC");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, coporateNo);

         // SQL�������s����
            ResultSet rs = ps.executeQuery();
            List<BillDto> List = new ArrayList<>();
            while (rs.next()) {
                BillDto dto = new BillDto();
                dto.setBillingId(rs.getInt("BILLING_ID"));
                dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
                dto.setPriceId(rs.getInt("PRICE_ID"));
                dto.setBillingDate(rs.getDate("BILLING_DATE"));
                dto.setNumberOfActiveAccount(rs.getInt("NUMBER_OF_ACTIVE_ACCOUNT"));
                dto.setNumberOfInactiveAccount(rs.getInt("NUMBER_OF_INACTIVE_ACCOUNT"));

                List.add(dto);
            }
            return List;


        }
    }
}
