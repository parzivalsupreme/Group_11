package group11_Project;

import group11_Project.ui.LoginForm;
import group11_Project.ui.UiUtils;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UiUtils.applyUIDefaults();
                LoginForm frame = new LoginForm();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
