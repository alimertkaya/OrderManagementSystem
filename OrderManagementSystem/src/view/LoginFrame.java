package view;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JLabel titleLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;

    public LoginFrame() {
        this.add(mainPanel);
        this.setTitle("Müşteri Yönetim Sistemi");
        this.setSize(400,400);

        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
        this.setVisible(true);

        loginButton.addActionListener(e -> {
            System.out.println("login yapıldı");
        });
    }
}
