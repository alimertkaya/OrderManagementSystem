package view;

import business.BasketController;
import business.CartController;
import core.Helper;
import entity.Basket;
import entity.Customer;

import javax.swing.*;
import java.util.ArrayList;

public class CartFrame extends JFrame {
    private JPanel mainPanel;
    private JLabel lbl_title;
    private JLabel lbl_customer_name;
    private JTextField fld_cart_date;
    private JLabel lbl_cart_date;
    private JTextArea tarea_cart_note;
    private JLabel lbl_cart_note;
    private JButton btn_cart;
    private Customer customer;
    private BasketController basketController;
    private CartController cartController;

    public CartFrame(Customer customer) {
        this.customer = customer;
        this.basketController = new BasketController();
        this.cartController = new CartController();
        this.add(mainPanel);
        this.setTitle("Sipariş Oluştur");
        this.setSize(300,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if (customer.getId() == 0) {
            Helper.showMsg("Lütfen geçerli bir müşteri seçiniz!");
            this.dispose();
        }

        ArrayList<Basket> baskets = this.basketController.findAll();
        if (baskets.isEmpty()) {
            Helper.showMsg("Lütfen sepete ürün ekleyiniz!");
            this.dispose();
        }
        this.lbl_customer_name.setText("Müşteri: " + customer.getName());
    }
}
