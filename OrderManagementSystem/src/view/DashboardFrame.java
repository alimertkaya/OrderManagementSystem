package view;

import core.Helper;
import entity.User;

import javax.swing.*;

public class DashboardFrame extends JFrame {
    private JPanel pnl_main;
    private JLabel lbl_welcome;
    private JButton exitButton;
    private JTabbedPane tab_menu;
    private JPanel pnl_customer;
    private JScrollPane scrl_customer;
    private JTable tbl_customer;
    private JTextField fld_f_customer_name;
    private JComboBox cmb_customer_type;
    private JButton btn_customer_filter;
    private JButton btn_customer_filter_reset;
    private JButton btn_customer_new;
    private JLabel lbl_f_customer_name;
    private JPanel pnl_customer_filter;
    private JLabel lbl_f_customer_type;
    private User user;

    public DashboardFrame(User user) {
        this.user = user;
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
    }
}
