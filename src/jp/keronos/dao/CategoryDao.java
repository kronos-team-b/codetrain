package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import jp.keronos.dto.CategoryDto;

public class CategoryDao {

    protected Connection connection;

    public CategoryDao(Connection connection) {
        this.connection = connection;
    }

    /**
     * コースIDに紐づくカテゴリ情報を取得
     * @param courseId
     * @return カテゴリ情報
     * @throws SQLException
     */
    public CategoryDto selectCategoryByCourseId(int courseId) throws SQLException {

        StringBuffer sb = new StringBuffer();
        sb.append("     select ");
        sb.append("            CA.CATEGORY_ID,");
        sb.append("            CA.CATEGORY_NAME");
        sb.append("       from CATEGORY as CA");
        sb.append(" inner join COURSE as CO");
        sb.append("         on CA.CATEGORY_ID = CO.CATEGORY_ID");
        sb.append("      where CO.COURSE_ID = ?");

        CategoryDto categoryDto = null;

        try(PreparedStatement preparedStatement = connection.prepareStatement(sb.toString())) {

            preparedStatement.setInt(1, courseId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()) {

                categoryDto = new CategoryDto();
                categoryDto.setCategoryName(resultSet.getString("CATEGORY_ID"));
                categoryDto.setCategoryName(resultSet.getString("CATEGORY_NAME"));
            }
        }

        return categoryDto;
    }


    /**
     * カテゴリ情報リストを取得する
     * @return カテゴリ情報リスト
     * @throws SQLException SQL例外
     */
    public ArrayList<CategoryDto> selectAll() throws SQLException {
        // SQL文を作成する
        StringBuffer sb = new StringBuffer();
        sb.append("     select ");
        sb.append("            CATEGORY_ID,");
        sb.append("            CATEGORY_NAME");
        sb.append("       from CATEGORY");
        sb.append(" order by CATEGORY_ID");

        ArrayList<CategoryDto> list = new ArrayList<CategoryDto>();

        // ステートメントオブジェクトを作成する
        try (Statement stmt = connection.createStatement()) {

            // SQL文を実行する
            ResultSet rs = stmt.executeQuery(sb.toString());
            while (rs.next()) {
                CategoryDto dto = new CategoryDto();
                dto.setCategoryId(rs.getInt("CATEGORY_ID"));
                dto.setCategoryName(rs.getString("CATEGORY_NAME"));
                list.add(dto);
            }
            return list;
        }
    }
}
