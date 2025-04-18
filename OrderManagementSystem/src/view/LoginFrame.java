package view;

import business.UserController;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JPanel mainPanel;
    private JPanel topPanel;
    private JLabel  titleLabel;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private UserController userController;

    public LoginFrame() {
        this.userController = new UserController();
        this.add(mainPanel);
        this.setTitle("Order Management System");
        this.setSize(400,400);
        /*
        * screen location 1
        int x = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2;
        int y = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2;
        this.setLocation(x, y);
         */
        // screen location 2
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        loginButton.addActionListener(e -> {
            JTextField[] checkList = {this.passwordField, this.emailField};
            if (!Helper.isEmailValid(this.emailField.getText()))
                Helper.showMsg("Please enter a valid email address!");

            if (Helper.isFieldListEmpty(checkList))
                Helper.showMsg("fill");
            else {
                User user = this.userController.findByLogin(this.emailField.getText(), this.passwordField.getText());
                if (user == null)
                    Helper.showMsg("No user found with the entered credentials!");
                else {
                    this.dispose();
                    DashboardFrame dashboardFrame = new DashboardFrame(user);
                }
            }
        });
    }
}
