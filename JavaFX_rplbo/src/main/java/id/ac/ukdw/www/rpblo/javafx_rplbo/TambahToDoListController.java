package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TambahToDoListController implements Initializable {

    @FXML
    private DatePicker deadline;

    @FXML
    private TextArea deskripsi;

    @FXML
    private TextArea judul;

    @FXML
    private ComboBox<String> kategoriComboBox;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private CheckBox prioritas;
    private ToDo currentToDo;  // Mendeklarasikan currentToDo

    // Setter untuk menginisialisasi currentToDo
    public void setCurrentToDo(ToDo toDo) {
        this.currentToDo = toDo;
        if (judul != null) {
            prefillForm();
        }
    }

    // Getter untuk mengambil currentToDo (jika diperlukan)
    public ToDo getCurrentToDo() {
        return currentToDo;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kategoriComboBox.getItems().addAll("Kuliah", "Kerja", "Belanja", "Lainnya");
    }


    @FXML
    void submit(ActionEvent event) {
        String taskJudul = judul.getText();
        String taskDeskripsi = deskripsi.getText();
        String taskDeadline = deadline.getValue() != null ? deadline.getValue().toString() : "";
        String taskKategori = kategoriComboBox.getValue();
        boolean isPrioritas = prioritas.isSelected();

        if (currentToDo != null) {
            currentToDo.setJudul(taskJudul);
            currentToDo.setDeskripsi(taskDeskripsi);
            currentToDo.setDeadline(taskDeadline);
            currentToDo.setKategori(taskKategori);
            currentToDo.setPrioritas(isPrioritas);
        } else {
            ToDo newToDo = new ToDo(taskJudul, taskDeskripsi, taskDeadline, taskKategori, isPrioritas);
            MainController.getToDoList().add(newToDo);
        }

        // Kembali ke main
        stage.close();
    }



    @FXML
    void goback(ActionEvent event) {
        stage.close();
    }

    @FXML
    void list_kategori(ActionEvent event) {
        String kategoriDipilih = kategoriComboBox.getValue();
        if (kategoriDipilih != null) {
            System.out.println("Kategori dipilih: " + kategoriDipilih);
        }
    }

    public void prefillForm() {
        if (currentToDo != null) {
            judul.setText(currentToDo.getJudul());
            deskripsi.setText(currentToDo.getDeskripsi());
            if (currentToDo.getDeadline() != null && !currentToDo.getDeadline().isEmpty()) {
                deadline.setValue(LocalDate.parse(currentToDo.getDeadline())); // pastikan format benar
            }
            kategoriComboBox.setValue(currentToDo.getKategori());
            prioritas.setSelected(currentToDo.isPrioritas());
        }
    }
}
