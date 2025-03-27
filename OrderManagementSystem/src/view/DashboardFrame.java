package view;

import business.CustomerController;
import business.ProductController;
import core.Helper;
import entity.Customer;
import entity.Product;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.util.ArrayList;

public class DashboardFrame extends JFrame {
    private JPanel pnl_main;
    private JLabel lbl_welcome;
    private JButton exitButton;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JTextField fld_f_customer_name;
    private JComboBox cmb_f_customer_type;
    private JButton btn_customer_filter;
    private JButton btn_customer_filter_reset;
    private JButton btn_customer_new;
    private JLabel lbl_f_customer_name;
    private JPanel pnl_customer_filter;
    private JLabel lbl_f_customer_type;
    private JPanel pnl_product;
    private JScrollPane scrl_panel;
    private JTable tbl_product;
    private JPanel pnl_product_filter;
    private JTextField fld_f_product_name;
    private JTextField fld_f_product_code;
    private JComboBox cmb_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_reset;
    private JButton btn_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_code;
    private JLabel lbl_f_product_stock;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private DefaultTableModel tmdl_customer = new DefaultTableModel();
    private DefaultTableModel tmdl_product = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();
    private JPopupMenu popup_product = new JPopupMenu();

    public DashboardFrame(User user) {
        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        if (user == null) {
            Helper.showMsg("error");
            this.dispose();
        }

        this.add(pnl_main);
        this.setTitle("Müşteri Yönetim Sistemi");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.lbl_welcome.setText("Hoşgeldiniz: " + this.user.getName());

        exitButton.addActionListener(e -> {
            this.dispose();
            LoginFrame loginFrame = new LoginFrame();
        });

        // CUSTOMER TAB
        loadCustomerTable(null);
        loadCustomerPopupMenu();
        loadCustomerButtonEvent();
        this.cmb_f_customer_type.setModel(new DefaultComboBoxModel<>(Customer.TYPE.values()));
        this.cmb_f_customer_type.setSelectedItem(null);

        // PRODUCT TAB
        loadProductTable(null);
        loadProductPopupMenu();
        loadProductButtonEvent();

    }

    private void loadProductButtonEvent() {
        this.btn_product_new.addActionListener(e -> {
            ProductFrame productFrame = new ProductFrame(new Product());
            productFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });
    }

    private void loadProductPopupMenu() {
        this.tbl_product.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_product.rowAtPoint(e.getPoint());
                tbl_product.setRowSelectionInterval(selectedRow, selectedRow);

                if (e.getButton() == MouseEvent.BUTTON3)
                    popup_product.show(tbl_product, e.getX(), e.getY());
            }
        });

        this.popup_product.add("Update").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            ProductFrame productFrame = new ProductFrame(this.productController.getById(selectId));
            productFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                }
            });
        });
        this.popup_product.add("Delete");
        this.popup_product.setComponentPopupMenu(popup_product);
    }

    private void loadProductTable(ArrayList<Product> products) {
        Object[] columnProduct = {"ID", "Ürün Adı", "Ürün Code", "Fiyat", "Stok"};

        if (products == null) {
            products = this.productController.findAll();
        }

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_product.getModel();
        clearModel.setRowCount(0);

        this.tmdl_product.setColumnIdentifiers(columnProduct);
        for (Product product : products) {
            Object[] rowObject = {
                    product.getId(),
                    product.getName(),
                    product.getCode(),
                    product.getPrice(),
                    product.getStock()
            };
            this.tmdl_product.addRow(rowObject);
        }

        this.tbl_product.setModel(tmdl_product);
        this.tbl_product.getTableHeader().setReorderingAllowed(false);
        this.tbl_product.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_product.setEnabled(false);
    }

    private void loadCustomerButtonEvent() {
        this.btn_customer_new.addActionListener(e -> {
            CustomerFrame customerFrame = new CustomerFrame(new Customer());
            customerFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });

        this.btn_customer_filter.addActionListener(e -> {
            ArrayList<Customer> filteredCustomers = this.customerController.filter(
                    this.fld_f_customer_name.getText(),
                    (Customer.TYPE) this.cmb_f_customer_type.getSelectedItem()
            );
            loadCustomerTable(filteredCustomers);
        });

        this.btn_customer_filter_reset.addActionListener(e -> {
            loadCustomerTable(null);
            this.fld_f_customer_name.setText(null);
            this.cmb_f_customer_type.setSelectedItem(null);
        });
    }

    private void loadCustomerPopupMenu() {
        this.tbl_customer.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_customer.rowAtPoint(e.getPoint());
                tbl_customer.setRowSelectionInterval(selectedRow, selectedRow); // tek bir satırı secmek istedigimiz icin 2 defa aynı parametreyi gectik

                if (e.getButton() == MouseEvent.BUTTON3) // right click
                    popup_customer.show(tbl_customer, e.getX(), e.getY());
            }
        });

        this.popup_customer.add("Update").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            CustomerFrame customerFrame = new CustomerFrame(this.customerController.getById(selectId));
            customerFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadCustomerTable(null);
                }
            });
        });
        this.popup_customer.add("Delete").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.customerController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });

        this.tbl_customer.setComponentPopupMenu(this.popup_customer);
    }

    private void loadCustomerTable(ArrayList<Customer> customers) {
        Object[] columnCustomer = {"ID", "Müşteri Adı", "Tipi", "Telefon", "E-posta", "Adres"};

        if (customers == null) {
            customers = this.customerController.findAll();
        }

        // table refresh
        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_customer.getModel();
        clearModel.setRowCount(0);

        this.tmdl_customer.setColumnIdentifiers(columnCustomer);
        for (Customer customer : customers) {
            Object[] rowObject = {
                    customer.getId(),
                    customer.getName(),
                    customer.getType(),
                    customer.getPhone(),
                    customer.getMail(),
                    customer.getAddress()
            };
            this.tmdl_customer.addRow(rowObject);
        }

        this.tbl_customer.setModel(tmdl_customer);
        this.tbl_customer.getTableHeader().setReorderingAllowed(false); // columnların yer değiştirmesini engeller
        this.tbl_customer.getColumnModel().getColumn(0).setMaxWidth(50); // id column genişliğini ayarlar
        this.tbl_customer.setEnabled(false); // kullanıcı tabloyu sadece görebilir, düzenleyemez
    }
}
