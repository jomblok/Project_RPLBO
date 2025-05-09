package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Apps extends Application {
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLogin(); // Awal mula: tampilkan login
    }

    public static void showLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(Apps.class.getResource("/views/login.fxml"));
            Scene scene = new Scene(loader.load(), 400, 300);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(Apps.class.getResource("/views/register.fxml"));
            Scene scene = new Scene(loader.load(), 400, 350);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Register");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showMain() {
        try {
            URL fxml = Apps.class.getResource("/views/main.fxml");
            if (fxml == null) {
                System.out.println("❌ File main.fxml tidak ditemukan!");
                return;
            }
            FXMLLoader loader = new FXMLLoader(fxml);
            Scene scene = new Scene(loader.load(), 800, 600);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Main To-Do List");
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showTambahToDo(ToDo todo) {
        try {
            FXMLLoader loader = new FXMLLoader(Apps.class.getResource("/views/TambahToDoList.fxml"));
            Parent root = loader.load();

            TambahToDoListController controller = loader.getController();
            if (todo != null) {
                controller.setCurrentToDo(todo);
            }

            Stage stage = new Stage();
            controller.setStage(stage);

            stage.setScene(new Scene(root));
            stage.setTitle(todo == null ? "Tambah Tugas" : "Edit Tugas");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void showKategori() {
        try {
            FXMLLoader loader = new FXMLLoader(Apps.class.getResource("/views/kategori.fxml"));
            Parent root = loader.load();
            primaryStage.setTitle("Manajemen Kategori");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
