package hu.kovacspeterzoltan.bootcamp.store;

import java.sql.*;

public class H2Repo implements StorePersistance {
    private Connection connection;
    private Statement statement;

    private static final String createTableSQL = """
        DROP TABLE IF EXISTS product;
        CREATE TABLE IF NOT EXISTS product (
            productName varchar(255),
            numberOfProduct int
        );
        """;
    private static final String selectByNameSQL = """
        SELECT productName, numberOfProduct FROM product WHERE productName = ?;
            """;
    private static final String insertOrUpdateSQL = """
        MERGE INTO product KEY (productName) VALUES (?, ?);
        """;

    public H2Repo() {
        try {
            createConnectionAndStatement();
            createTable();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
    }
    private void createConnectionAndStatement() throws SQLException {
        connection = H2JDBCUtils.getConnection();
        statement = connection.createStatement();
    }
    private void createTable() throws SQLException {
        statement.execute(createTableSQL);
    }
    @Override
    public StoreItem loadItem(String productName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(selectByNameSQL);
            preparedStatement.setString(1, productName);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                StoreItem item = new StoreItem();
                item.productName = productName;
                item.numberOfProduct = rs.getInt("numberOfProduct");
                return item;
            }
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
        return null;
    }
    @Override
    public void saveItem(StoreItem item) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertOrUpdateSQL);
            preparedStatement.setString(1, item.productName);
            preparedStatement.setInt(2, item.numberOfProduct);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            H2JDBCUtils.printSQLException(e);
        }
    }
}