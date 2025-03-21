package business;

import core.Helper;
import dao.CustomerDao;
import entity.Customer;

import java.util.ArrayList;

public class CustomerController {
    private final CustomerDao customerDao = new CustomerDao();

    public ArrayList<Customer> findAll() {
        return this.customerDao.findAll();
    }

    public boolean save(Customer customer) {
        return this.customerDao.save(customer);

    }

    public boolean update(Customer customer) {
        if (this.getById(customer.getId()) == null) {
            Helper.showMsg("Bu " + customer.getId() + " ID'e kayıtlı bir kullanıcı bulunamadı!");
            return false;
        }
        return this.customerDao.update(customer);
    }

    public boolean delete(int id) {
        if (this.getById(id) == null) {
            Helper.showMsg("Bu " + id + " ID'e kayıtlı bir kullanıcı bulunamadı!");
            return false;
        }
        return this.customerDao.delete(id);
    }

    public Customer getById(int id) {
        return this.customerDao.getById(id);
    }
}