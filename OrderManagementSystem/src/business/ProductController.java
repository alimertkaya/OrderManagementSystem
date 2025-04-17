package business;

import core.Helper;
import core.Item;
import dao.ProductDao;
import entity.Product;

import java.util.ArrayList;

public class ProductController {
    private final ProductDao productDao = new ProductDao();

    public ArrayList<Product> findAll() {
        return this.productDao.findAll();
    }

    public boolean save(Product product) {
        return this.productDao.save(product);
    }

    public boolean update(Product product) {
        if (this.getById(product.getId()) == null) {
            Helper.showMsg("There is no product associated with ID " + product.getId() + "!");
            return false;
        }
        return this.productDao.update(product);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg("There is no product associated with ID " + id + "!");
            return false;
        }
        return this.productDao.delete(id);
    }

    public ArrayList<Product> filter(String name, String code, Item isStock) {
        String query = "SELECT * FROM product";
        ArrayList<String> whereList = new ArrayList<>();
        ArrayList<Object> parameters = new ArrayList<>();

        if (!name.isEmpty()) {
            whereList.add("name LIKE ?");
            parameters.add("%" + name + "%");
        }

        if (!code.isEmpty()) {
            whereList.add("code LIKE ?");
            parameters.add("%" + code + "%");
        }

        if (isStock != null) {
            if (isStock.getKey() == 1) {
                whereList.add("stock > ?");
                parameters.add(0);
            } else {
                whereList.add("stock <= ?");
                parameters.add(0);
            }
        }

        if (!whereList.isEmpty()) {
            query += " WHERE " + String.join(" AND ", whereList);
        }
        return this.productDao.queryPrepared(query, parameters);
    }

    public Product getById(int id) {
        return this.productDao.getById(id);
    }
}
