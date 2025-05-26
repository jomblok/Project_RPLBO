package id.ac.ukdw.www.rpblo.javafx_rplbo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import id.ac.ukdw.www.rpblo.javafx_rplbo.Manager.MariaDBDriver;
import id.ac.ukdw.www.rpblo.javafx_rplbo.Manager.Sessionmanager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Mendapatkan koneksi dari singleton instance MariaDBDriver
        try (Connection connection = MariaDBDriver. getInstance().getConnection()) {
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Login berhasil
                int userId = resultSet.getInt("id");
                String usernameFromDB = resultSet.getString("username");

                // Set session user
                Sessionmanager.setCurrentUser(usernameFromDB, userId);
                System.out.println("✅ Login berhasil sebagai: " + username);

                // Ganti scene ke halaman utama
                Apps.showMain();
            } else {
                System.out.println("❌ Login gagal. Username atau password salah.");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToRegister() {
        Apps.showRegister();
    }

}