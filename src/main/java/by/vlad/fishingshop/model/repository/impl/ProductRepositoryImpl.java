package by.vlad.fishingshop.model.repository.impl;

import by.vlad.fishingshop.model.connection.CustomConnectionPool;
import by.vlad.fishingshop.model.entity.ManufactureType;
import by.vlad.fishingshop.model.entity.Product;
import by.vlad.fishingshop.model.entity.ProductType;
import by.vlad.fishingshop.exception.RepositoryException;
import by.vlad.fishingshop.model.repository.Repository;
import by.vlad.fishingshop.model.repository.Specification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static by.vlad.fishingshop.model.repository.ColumnName.*;

public class ProductRepositoryImpl implements Repository<Product> {
    private static final Logger logger = LogManager.getLogger();
    private static final String ADD_PRODUCT_QUERY = "INSERT INTO products (vendor,name, manufacture_id, type_id, description, image, price, number_in_stock)" +
            " VALUES (?,?,(SELECT id FROM product_manufacture WHERE manufacture = ?),(SELECT id FROM product_types WHERE type = ?),?,?,?,?);";
//    private static final String ADD_PRODUCT_QUERY =
//            "WITH manufacture_cte AS (SELECT id FROM product_manufacture WHERE manufacture = ?), " +
//                    "type_cte AS (SELECT id FROM product_types WHERE type = ?) " +
//                    "INSERT INTO products (vendor, name, manufacture_id, type_id, description, image, price, number_in_stock) " +
//                    "SELECT ?, ?, manufacture_cte.id, type_cte.id, ?, ?, ?, ? FROM manufacture_cte, type_cte;";


    private static final String UPDATE_PRODUCT_QUERY = "UPDATE products SET vendor = ? , name = ? ,manufacture_id = (SELECT id FROM product_manufacture WHERE manufacture = ?)," +
            "type_id = (SELECT id FROM product_types WHERE type = ? ),description = ? ,price= ? , number_in_stock = ? WHERE id = ? ;";
    private static ProductRepositoryImpl instance;

    private ProductRepositoryImpl() {
    }

    public static ProductRepositoryImpl getInstance() {
        if (instance == null) {
            instance = new ProductRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void insert(Product product) throws RepositoryException {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/fishing?sslmode=disable",
                "postgres", "1352")){
            conn.setAutoCommit(false);
            PreparedStatement statement = conn.prepareStatement(ADD_PRODUCT_QUERY);
            statement.setString(1, product.getVendor());
            statement.setString(2, product.getName());
            statement.setString(3, product.getManufacture().getValue()); // передаем manufacture
            statement.setString(4, product.getProductType().getType()); // передаем type
            statement.setString(5, product.getDescription());
            statement.setBinaryStream(6, product.getImage());
            statement.setBigDecimal(7, product.getPrice());
            statement.setInt(8, product.getNumberInStock());

            int updated = statement.executeUpdate();
            logger.info("Rows inserted: {}", updated);
            conn.commit();
        } catch (SQLException e) {
            logger.error("Can not create query. Exception: ", e);
            throw new RepositoryException("Can not create query. Exception:", e);
        }
    }

    @Override
    public void update(Product product) throws RepositoryException {
        try (Connection connection = CustomConnectionPool.getInstance().getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_PRODUCT_QUERY);
            statement.setString(1, product.getVendor());
            statement.setString(2, product.getName());
            statement.setString(3, product.getManufacture().getValue());
            statement.setString(4, product.getProductType().getType());
            statement.setString(5, product.getDescription());
            statement.setBigDecimal(6, product.getPrice());
            statement.setInt(7, product.getNumberInStock());
            statement.setInt(8, product.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Can not create query. Exception: ", e);
            throw new RepositoryException("Can not create query. Exception: ", e);
        }
    }

    @Override
    public List<Product> query(Specification specification) throws RepositoryException {
        List<Product> productList = new ArrayList<>();
        PreparedStatement statement = (PreparedStatement) specification.getStatement();
        ResultSet resultSet;
        try {
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Product product = new Product.ProductBuilder()
                        .setId(resultSet.getInt(PRODUCTS_ID))
                        .setVendor(resultSet.getString(PRODUCTS_VENDOR))
                        .setName(resultSet.getString(PRODUCTS_NAME))
                        .setManufacture(ManufactureType.valueOf(resultSet.getString(PRODUCT_MANUFACTURE).toUpperCase()))
                        .setType(ProductType.valueOf(resultSet.getString(PRODUCT_TYPES_TYPE).toUpperCase()))
                        .setImage(resultSet.getBinaryStream(PRODUCTS_IMAGE))
                        .setDescription(resultSet.getString(PRODUCTS_DESCRIPTION))
                        .setPrice(resultSet.getBigDecimal(PRODUCTS_PRICE))
                        .setNumberInStock(resultSet.getInt(PRODUCTS_NUMBER_IN_STOCK))
                        .build();

                productList.add(product);
            }

        } catch (SQLException e) {
            logger.error("Can not create query. Exception: ", e);
            throw new RepositoryException("Can not create query. Exception: ", e);
        }
        return productList;
    }
}
