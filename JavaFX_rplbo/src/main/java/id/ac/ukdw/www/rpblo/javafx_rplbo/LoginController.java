package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.equals(UserSession.registeredUsername) && pass.equals(UserSession.registeredPassword)) {
            Apps.showMain();  // Arahkan ke halaman utama
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Error");
            alert.setHeaderText("Username atau Password salah!");
            alert.setContentText("Silakan periksa kembali.");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToRegister() {
        Apps.showRegister();
    }
}
