package id.ac.ukdw.www.rpblo.javafx_rplbo;

import id.ac.ukdw.www.rpblo.javafx_rplbo.Apps;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class RegisterController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private void handleRegister() {
        String user = usernameField.getText();
        String pass = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        if (pass.equals(confirm)) {
            System.out.println("Registrasi sukses untuk: " + user);
            Apps.showLogin(); // Kembali ke login setelah berhasil
        } else {
            System.out.println("Password tidak cocok");
        }
    }

    @FXML
    private void goToLogin() {
        Apps.showLogin();
    }
}
