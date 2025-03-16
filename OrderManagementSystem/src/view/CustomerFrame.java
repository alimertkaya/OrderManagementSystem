package view;

import business.CustomerController;
import entity.Customer;

import javax.swing.*;

public class CustomerFrame extends JFrame {
    private JPanel mainPanel;
    private JLabel lbl_customer_name;
    private JTextField fld_customer_name;
    private JLabel lbl_customer_type;
    private JComboBox<Customer.TYPE> cmb_customer_type;
    private JLabel lbl_customer_phone;
    private JTextField fld_customer_phone;
    private JLabel lbl_customer_mail;
    private JTextField fld_customer_mail;
    private JLabel lbl_customer_address;
    private JTextArea tarea_customer_address;
    private JButton btn_customer_save;
    private JLabel lbl_title;
    private Customer customer;
    private CustomerController customerController;

    public CustomerFrame(Customer customer) {
        this.customer = customer;
        this.customerController = new CustomerController();
        this.add(mainPanel);
        this.setTitle("Müşteri Ekle/Düzenle");
        this.setSize(300,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.cmb_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values())); // comboBox da verileri gosterir

        if (this.customer.getId() == 0)
            this.lbl_title.setText("Müşteri Ekle");
        else {
            this.lbl_title.setText("Müşteri Düzenle");
            this.fld_customer_name.setText(this.customer.getName());
            this.fld_customer_phone.setText(this.customer.getPhone());
            this.fld_customer_mail.setText(this.customer.getMail());
            this.tarea_customer_address.setText(this.customer.getAddress());
            this.cmb_customer_type.getModel().setSelectedItem(this.customer.getType());
        }

    }
}
