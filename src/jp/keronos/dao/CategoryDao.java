package jp.keronos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}
