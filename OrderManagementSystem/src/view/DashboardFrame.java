package view;

import core.Helper;
import entity.User;

import javax.swing.*;

public class DashboardFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JLabel welcomeLabel;
    private JButton exitButton;
    private JTabbedPane menuTab;
    private JPanel customerPanel;
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
        this.welcomeLabel.setText("Hoşgeldiniz: " + this.user.getName());

        exitButton.addActionListener(e -> {
            this.dispose();
            LoginFrame loginFrame = new LoginFrame();
        });
    }
}
