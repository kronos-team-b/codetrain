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
     * ���NO�ɊY�����鐿�������擾���A�����ɂ���ĒP���Ɛŗ������擾����
     * @param dto
     * @return �����ꗗ���
     * @throws SQLException
     */
    public List<BillDto> selectByCorporateNo(int corporateNo) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         BILLING_ID");
        sb.append("        ,CORPORATE_NO");
        sb.append("        ,BILLING_DATE");
        sb.append("        ,BILL.PRICE_ID");
        sb.append("        ,NUMBER_OF_ACTIVE_ACCOUNT");
        sb.append("        ,NUMBER_OF_INACTIVE_ACCOUNT");
        sb.append("        ,PRICE");
        sb.append("        ,TAX");
        sb.append("    from");
        sb.append("        BILL left join PRICE");
        sb.append("     on BILL.PRICE_ID = PRICE.PRICE_ID");
        sb.append("  where CORPORATE_NO = ?");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, corporateNo);

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
                dto.setPrice(rs.getInt("PRICE"));
                dto.setTax(rs.getDouble("TAX"));
                double totalPrice = ((dto.getPrice() * dto.getNumberOfActiveAccount())
                        + (dto.getPrice() / 2 * dto.getNumberOfInactiveAccount())) * (1 + dto.getTax());
                dto.setTotalPrice((int) totalPrice);
                List.add(dto);
            }
            return List;
        }
    }

    /**
     * ����ID�ɊY�����鐿�������擾���A�����ɂ���ĒP���Ɛŗ������擾����
     * @param dto
     * @return�@�������A�P�����
     * @throws SQLException
     */
    public BillDto selectByBillingId(BillDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("         BILLING_ID");
        sb.append("        ,CORPORATE_NO");
        sb.append("        ,BILLING_DATE");
        sb.append("        ,BILL.PRICE_ID");
        sb.append("        ,NUMBER_OF_ACTIVE_ACCOUNT");
        sb.append("        ,NUMBER_OF_INACTIVE_ACCOUNT");
        sb.append("        ,PRICE");
        sb.append("        ,TAX");
        sb.append("    from");
        sb.append("        BILL left join PRICE");
        sb.append("     on BILL.PRICE_ID = PRICE.PRICE_ID");
        sb.append("  where BILLING_ID = ?");

     // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getCorporateNo());

            // SQL�������s����
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto.setBillingId(rs.getInt("BILLING_ID"));
                dto.setCorporateNo(rs.getInt("CORPORATE_NO"));
                dto.setPriceId(rs.getInt("PRICE_ID"));
                dto.setBillingDate(rs.getDate("BILLING_DATE"));
                dto.setNumberOfActiveAccount(rs.getInt("NUMBER_OF_ACTIVE_ACCOUNT"));
                dto.setNumberOfInactiveAccount(rs.getInt("NUMBER_OF_INACTIVE_ACCOUNT"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setTax(rs.getDouble("TAX"));

            }
            return dto;
        }
    }
}
