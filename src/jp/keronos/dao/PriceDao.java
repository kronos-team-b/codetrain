package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.keronos.dto.PriceDto;

public class PriceDao {

    /** �R�l�N�V���� */
    protected Connection conn;

    /**
     * �R���X�g���N�^
     * �R�l�N�V�������t�B�[���h�ɐݒ肷��
     * @param conn �R�l�N�V����
     */
    public PriceDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * �P��ID�ɊY������P�������擾����
     * @param dto
     * @return�@�P�����
     * @throws SQLException
     */
    public PriceDto selectByPriceId(PriceDto dto) throws SQLException {

        // SQL�����쐬����
        StringBuffer sb = new StringBuffer();
        sb.append("  select");
        sb.append("        PRICE_ID");
        sb.append("        ,PRICE");
        sb.append("        ,TAX");
        sb.append("    from");
        sb.append("        PRICE");
        sb.append("   where PRICE_ID = ?");

        // �X�e�[�g�����g�I�u�W�F�N�g���쐬����
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            // �v���[�X�z���_�[�ɒl���Z�b�g����
            ps.setInt(1, dto.getPriceId());

            // SQL�������s����
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                dto = new PriceDto();
                dto.setPriceId(rs.getInt("PRICE_ID"));
                dto.setPrice(rs.getInt("PRICE"));
                dto.setTax(rs.getInt("TAX"));
            }
        }
        return dto;
    }
}
