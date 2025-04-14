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

    public Cart getById(int id) {
        return this.cartDao.getById(id);
    }
}
