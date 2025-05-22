package by.vlad.fishingshop.model.repository.impl.specification;

import by.vlad.fishingshop.model.connection.CustomConnectionPool;
import by.vlad.fishingshop.model.entity.ProductType;
import by.vlad.fishingshop.model.repository.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class FindProductsByTypeSpecification implements Specification {
    private static final Logger logger = LogManager.getLogger();
    private static final String GET_PRODUCT = "" +
            "SELECT products.id,vendor,name,manufacture,type,description,image,price,number_in_stock FROM products\n" +
            "INNER JOIN product_manufacture AS pm ON products.manufacture_id = pm.id\n" +
            "INNER JOIN product_types AS pt ON products.type_id = pt.id\n" +
            "WHERE type = ?;";
    private String type;

    public FindProductsByTypeSpecification(ProductType productType) {
        this.type = productType.getType();
    }

    @Override
    public Statement getStatement() {
        PreparedStatement preparedStatement = null;
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            preparedStatement = connection.prepareStatement(GET_PRODUCT);
            preparedStatement.setString(1, type);
        } catch (SQLException e) {
            logger.error("Can not create query. Exception: ", e);
        }
        return preparedStatement;
    }
}
