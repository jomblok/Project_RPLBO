package id.ac.ukdw.www.rpblo.javafx_rplbo;

import id.ac.ukdw.www.rpblo.javafx_rplbo.Apps;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (user.equals("admin") && pass.equals("123")) {
            Apps.showMain();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Password / Username Incorrect");
            alert.setContentText("Please enter a valid password");
            alert.showAndWait();
        }
    }

    @FXML
    private void goToRegister() {
        Apps.showRegister();
    }
}
