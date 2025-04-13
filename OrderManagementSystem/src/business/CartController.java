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

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg("Bu " + id + " ID'e air bir sipariş bulunamadı!");
            return false;
        }
        return this.cartDao.delete(id);
    }

    public Cart getById(int id) {
        return this.cartDao.getById(id);
    }
}
