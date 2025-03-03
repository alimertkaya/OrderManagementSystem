package view;

import core.Helper;
import entity.User;

import javax.swing.*;

public class DashboardFrame extends JFrame {
    private JPanel mainPanel;
    private User user;

    public DashboardFrame(User user) {
        this.user = user;
        if (user == null) {
            Helper.showMsg("error");
            this.dispose();
        }

        this.add(mainPanel);
        this.setTitle("Müşteri Yönetim Sistemi");
        this.setSize(1000,500);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        System.out.println(user.toString());
    }
}
