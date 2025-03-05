import business.UserController;
import core.Database;
import core.Helper;
import entity.User;
import view.DashboardFrame;
import view.LoginFrame;

import java.sql.Connection;

public class App {
    public static void main(String[] args) {
        Helper.setTheme();
        // LoginFrame frame = new LoginFrame();
        UserController userController = new UserController();
        User user = userController.findByLogin("mert@dev.com", "m123");
        DashboardFrame dashboardFrame = new DashboardFrame(user);
    }
}