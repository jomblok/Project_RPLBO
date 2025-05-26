package id.ac.ukdw.www.rpblo.javafx_rplbo;

import id.ac.ukdw.www.rpblo.javafx_rplbo.Apps;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

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

        if (pass.equals(confirm)) {
            try {
                Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\User\\Desktop\\Project\\JavaFX_rplbo\\user.db"); // Ganti dengan URL database kamu
                String sql = "INSERT INTO user (username, password) VALUES (?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, user);
                stmt.setString(2, pass); // Disarankan enkripsi dulu, misal dengan hashing

                stmt.executeUpdate();
                stmt.close();
                conn.close();

                System.out.println("Registrasi sukses untuk: " + user);
                Apps.showLogin();
            } catch (SQLException e) {
                System.out.println("Gagal registrasi: " + e.getMessage());
            }
        } else {
            System.out.println("Password tidak cocok");
        }
    }

    @FXML
    private void goToLogin() {
        Apps.showLogin();
    }

}