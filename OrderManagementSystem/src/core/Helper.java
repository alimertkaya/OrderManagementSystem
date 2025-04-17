package core;

import javax.swing.*;

public class Helper {

    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("FlatLaf")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static boolean isFieldEmpty(JTextField field) {
        return field.getText().trim().isEmpty();
    }

    public static boolean isFieldListEmpty(JTextField[] fields) {
        for (JTextField field : fields) {
            if (isFieldEmpty(field)) return true;
        }
        return false;
    }

    public static boolean isEmailValid(String mail) {
        if (mail == null || mail.trim().isEmpty()) return false;

        if (!mail.contains("@")) return false;

        String[] parts = mail.split("@");
        if (parts.length != 2) return false;

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false;

        if (!parts[1].trim().contains(".")) return false;

        return true;
    }

    /*
    public static void optionPaneDialogTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
    }
     */

    public static void showMsg(String message) {
        String msg;
        String title;

        switch (message) {
            case "fill" -> {
                msg = "Please fill in all fields!";
                title = "ERROR";
            }
            case "done" -> {
                msg = "Operation completed successfully.";
                title = "Result";
            }
            case "error" -> {
                msg = "An error occurred!";
                title = "ERROR";
            }
            default -> {
                msg = message;
                title = "Message";
            }
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        String msg;

        if (str.equals("sure"))
            msg = "Are you sure you want to perform this action?";
        else
            msg = str;

        return JOptionPane.showConfirmDialog(null, msg, "Are you sure?", JOptionPane.YES_NO_OPTION) == 0;
    }
}
