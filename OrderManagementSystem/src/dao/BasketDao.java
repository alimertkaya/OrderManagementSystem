package dao;

import core.Database;
import entity.Basket;
import entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BasketDao {
    private Connection connection;
    private ProductDao productDao;

    public BasketDao() {
        this.connection = Database.getInstance();
        this.productDao = new ProductDao();
    }

    public boolean save(Basket basket) {
        String query = "INSERT INTO basket" +
                "(" +
                "product_id" +
                ")" +
                " VALUES (?)";

        try {
            PreparedStatement pr = this.connection.prepareStatement(query);
            pr.setInt(1, basket.getProductId());
            return pr.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Basket match(ResultSet rs) throws SQLException {
        Basket basket = new Basket();
        basket.setId(rs.getInt("id"));
        basket.setProductId(rs.getInt("product_id"));
        basket.setProduct(this.productDao.getById(rs.getInt("product_id")));
        return basket;
    }
}
