package view;

import business.BasketController;
import business.CartController;
import business.CustomerController;
import business.ProductController;
import core.Helper;
import core.Item;
import entity.*;

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
    private JComboBox<Item> cmb_f_product_stock;
    private JButton btn_product_filter;
    private JButton btn_product_reset;
    private JButton btn_product_new;
    private JLabel lbl_f_product_name;
    private JLabel lbl_f_product_code;
    private JLabel lbl_f_product_stock;
    private JPanel pnl_basket;
    private JButton btn_basket_reset;
    private JButton btn_basket_new;
    private JComboBox<Item> cmb_f_basket_customer;
    private JLabel lbl_f_basket_customer;
    private JPanel pnl_basket_filter;
    private JLabel lbl_f_basket_price;
    private JLabel lbl_f_basket_count;
    private JTable tbl_basket;
    private JPanel pnl_cart;
    private JScrollPane scrl_cart;
    private JScrollPane scrl_basket;
    private JTable tbl_cart;
    private User user;
    private CustomerController customerController;
    private ProductController productController;
    private BasketController basketController;
    private CartController cartController;
    private DefaultTableModel tmdl_customer = new DefaultTableModel();
    private DefaultTableModel tmdl_product = new DefaultTableModel();
    private DefaultTableModel tmdl_basket = new DefaultTableModel();
    private DefaultTableModel tmdl_cart = new DefaultTableModel();
    private JPopupMenu popup_customer = new JPopupMenu();
    private JPopupMenu popup_product = new JPopupMenu();
    private JPopupMenu popup_cart = new JPopupMenu();

    public DashboardFrame(User user) {
        this.user = user;
        this.customerController = new CustomerController();
        this.productController = new ProductController();
        this.basketController = new BasketController();
        this.cartController = new CartController();
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
        this.cmb_f_product_stock.addItem(new Item(1, "Stokta Var"));
        this.cmb_f_product_stock.addItem(new Item(2, "Stokta Yok"));
        this.cmb_f_product_stock.setSelectedItem(null);

        // BASKET TAB
        loadBasketTable();
        loadBasketButtonEvent();
        loadBasketCustomerCombo();

        // CART TAB
        loadCartTable();
        loadCartPopupMenu();

    }

    private void loadCartPopupMenu() {
        this.tbl_cart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selectedRow = tbl_cart.rowAtPoint(e.getPoint());
                tbl_cart.setRowSelectionInterval(selectedRow, selectedRow);

                if (e.getButton() == MouseEvent.BUTTON3)
                    popup_cart.show(tbl_cart, e.getX(), e.getY());
            }
        });

        this.popup_cart.add("Delete").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_cart.getValueAt(tbl_cart.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.cartController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadCartTable();
                }
                else {
                    Helper.showMsg("error");
                }
            }
        });
        this.popup_cart.setComponentPopupMenu(popup_cart);
    }

    private void loadCartTable() {
        Object[] columnCart = {"ID", "Müşteri Adı", "Ürün Adı", "Fiyat", "Sipariş Tarihi", "Not"};
        ArrayList<Cart> carts = this.cartController.findAll();

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_cart.getModel();
        clearModel.setRowCount(0);

        this.tmdl_cart.setColumnIdentifiers(columnCart);
        for (Cart cart : carts) {
            Object[] rowObject = {
                    cart.getId(),
                    cart.getCustomer().getName(),
                    cart.getProduct().getName(),
                    cart.getPrice(),
                    cart.getDate(),
                    cart.getNote()
            };
            this.tmdl_cart.addRow(rowObject);
        }

        this.tbl_cart.setModel(tmdl_cart);
        this.tbl_cart.getTableHeader().setReorderingAllowed(false);
        this.tbl_cart.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_cart.setEnabled(false);
    }

    private void loadBasketCustomerCombo() {
        ArrayList<Customer> customers = this.customerController.findAll();
        this.cmb_f_basket_customer.removeAllItems();
        for (Customer customer : customers) {
            this.cmb_f_basket_customer.addItem(new Item(customer.getId(), customer.getName()));
        }
        this.cmb_f_basket_customer.setSelectedItem(null);
    }

    private void loadBasketButtonEvent() {
        this.btn_basket_reset.addActionListener(e -> {
            if (this.basketController.clear()) {
                Helper.showMsg("done");
                loadBasketTable();
            } else {
                Helper.showMsg("error");
            }
        });

        this.btn_basket_new.addActionListener(e -> {
            Item selectedCustomer = (Item) this.cmb_f_basket_customer.getSelectedItem();
            if (selectedCustomer == null)
                Helper.showMsg("Lütfen bir müşteri seçiniz!");
            else {
                Customer customer = this.customerController.getById(selectedCustomer.getKey());
                ArrayList<Basket> baskets = this.basketController.findAll();
                if (customer.getId() == 0)
                    Helper.showMsg("Böyle bir müşteri bulunamadı!");
                else if (baskets.isEmpty())
                    Helper.showMsg("Lütfen sepete ürün ekleyeniniz");
                else {
                    CartFrame cartFrame = new CartFrame(customer);
                    cartFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            loadBasketTable();
                            loadProductTable(null);
                        }
                    });
                }

            }
        });
    }

    private void loadBasketTable() {
        Object[] columnBasket = {"ID", "Ürün Adı", "Ürün Code", "Fiyat", "Stok"};
        ArrayList<Basket> baskets = this.basketController.findAll();

        DefaultTableModel clearModel = (DefaultTableModel) this.tbl_basket.getModel();
        clearModel.setRowCount(0);

        this.tmdl_basket.setColumnIdentifiers(columnBasket);
        int totalPrice = 0;
        for (Basket basket : baskets) {
            Object[] rowObject = {
                    basket.getId(),
                    basket.getProduct().getName(),
                    basket.getProduct().getCode(),
                    basket.getProduct().getPrice(),
                    basket.getProduct().getStock()
            };
            this.tmdl_basket.addRow(rowObject);
            totalPrice += basket.getProduct().getPrice();
        }
        this.lbl_f_basket_price.setText(totalPrice + " TL");
        this.lbl_f_basket_count.setText(baskets.size() + " Adet");
        this.tbl_basket.setModel(tmdl_basket);
        this.tbl_basket.getTableHeader().setReorderingAllowed(false);
        this.tbl_basket.getColumnModel().getColumn(0).setMaxWidth(50);
        this.tbl_basket.setEnabled(false);
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

        this.btn_product_filter.addActionListener(e -> {
            ArrayList<Product> filteredProducts = this.productController.filter(
                    this.fld_f_product_name.getText(),
                    this.fld_f_product_code.getText(),
                    (Item) this.cmb_f_product_stock.getSelectedItem()
            );
            loadProductTable(filteredProducts);
        });

        this.btn_product_reset.addActionListener(e -> {
            loadProductTable(null);
            this.fld_f_product_name.setText(null);
            this.fld_f_product_code.setText(null);
            this.cmb_f_product_stock.setSelectedItem(null);
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

        this.popup_product.add("Add Basket").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            Product basketProduct = this.productController.getById(selectId);
            if (basketProduct.getStock() <= 0) {
                Helper.showMsg("Bu ürün stokta yoktur!");
            } else {
                Basket basket = new Basket(basketProduct.getId());
                if (this.basketController.save(basket)) {
                    Helper.showMsg("done");
                    loadBasketTable();
                }
                else
                    Helper.showMsg("error");
            }
        });

        this.popup_product.add("Update").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            ProductFrame productFrame = new ProductFrame(this.productController.getById(selectId));
            productFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadProductTable(null);
                    loadBasketTable();
                }
            });
        });
        this.popup_product.add("Delete").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_product.getValueAt(tbl_product.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.productController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadProductTable(null);
                    loadBasketTable();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
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
                    loadBasketCustomerCombo();
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
                    loadBasketCustomerCombo();
                }
            });
        });
        this.popup_customer.add("Delete").addActionListener(e -> {
            int selectId = Integer.parseInt(tbl_customer.getValueAt(tbl_customer.getSelectedRow(), 0).toString());
            if (Helper.confirm("sure")) {
                if (this.customerController.delete(selectId)) {
                    Helper.showMsg("done");
                    loadCustomerTable(null);
                    loadBasketCustomerCombo();
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
