package business;

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
}
