package business;

import core.Helper;
import dao.CartDao;
import entity.Cart;

import java.util.ArrayList;

public class CartController {
    private final CartDao cartDao = new CartDao();

    public ArrayList<Cart> findAll() {
        return this.cartDao.findAll();
    }

    public boolean save(Cart cart) {
        return this.cartDao.save(cart);
    }

    public boolean deleteById(int cartId) {
        if (this.getById(cartId) == null) {
            Helper.showMsg("Bu " + cartId + " ID'e air bir sipariş bulunamadı!");
            return false;
        }
        return this.cartDao.deleteById(cartId);
    }

    public boolean deleteByCustomerId(int customerId) {
        return this.cartDao.deleteByCustomerId(customerId);
    }

    public ArrayList<Cart> filter(String customerName, String productName, String date) {
        String query = "SELECT c.*, cu.name AS customer_name, p.name AS product_name " +
                        "FROM cart c " +
                        "JOIN customer cu ON c.customer_id = cu.id " +
                        "JOIN product p ON c.product_id = p.id";

        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<Object> parameters = new ArrayList<>();

        if (!customerName.isEmpty()) {
            whereList.add("cu.name LIKE ?");
            parameters.add("%" + customerName + "%");
        }

        if (!productName.isEmpty()) {
            whereList.add("p.name LIKE ?");
            parameters.add("%" + productName + "%");
        }

        if (!date.isEmpty()) {
            whereList.add("c.date LIKE ?");
            parameters.add(date + "%");
        }

        if (!whereList.isEmpty()) {
            query += " WHERE " + String.join(" AND ", whereList);
        }
        return this.cartDao.queryPrepared(query, parameters);
    }

    public Cart getById(int id) {
        return this.cartDao.getById(id);
    }
}
