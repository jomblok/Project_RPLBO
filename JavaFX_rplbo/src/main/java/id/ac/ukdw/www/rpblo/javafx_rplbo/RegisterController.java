package id.ac.ukdw.www.rpblo.javafx_rplbo;

import id.ac.ukdw.www.rpblo.javafx_rplbo.Apps;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

        // Validasi username
        if (user.length() < 8) {
            showAlert(Alert.AlertType.WARNING, "Username Tidak Valid", "Username harus memiliki minimal 8 karakter.");
            return;
        }

        // Validasi password
        if (!isValidPassword(pass)) {
            showAlert(Alert.AlertType.WARNING, "Password Tidak Valid",
                    "Password harus memiliki minimal 8 karakter dan mengandung huruf kapital, huruf kecil, angka, dan spesial karakter.");
            return;
        }

        if (!pass.equals(confirm)) {
            showAlert(Alert.AlertType.WARNING, "Password Tidak Cocok", "Password dan Konfirmasi Password harus sama.");
            return;
        }

        // Proses penyimpanan ke database
        }
    }

    @FXML
    private void goToLogin() {
        Apps.showLogin();
    }

}