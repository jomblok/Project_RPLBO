package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

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
            UserSession.registeredUsername = user;
            UserSession.registeredPassword = pass;

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registrasi Berhasil");
            alert.setHeaderText(null);
            alert.setContentText("Akun berhasil dibuat. Silakan login.");
            alert.showAndWait();

            Apps.showLogin();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password tidak cocok!");
            alert.setContentText("Silakan isi ulang password dengan benar.");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToLogin() {
        Apps.showLogin();
    }
}
