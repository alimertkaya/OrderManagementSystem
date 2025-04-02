package business;

import dao.BasketDao;
import entity.Basket;

public class BasketController {
    private final BasketDao basketDao = new BasketDao();

    public boolean save(Basket basket) {
        return this.basketDao.save(basket);
    }
}
