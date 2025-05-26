package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableRow;
import javafx.util.Duration;

import java.time.LocalDate;

public class MainController {

    @FXML public TextField searchKataKunci;
    @FXML private TableColumn<ToDo, String> aksi;
    @FXML private Button btnKeluar;
    @FXML private TableColumn<ToDo, String> deadline;
    @FXML private TableColumn<ToDo, String> kategori;
    @FXML private TableColumn<ToDo, String> todolist;
    @FXML private TableColumn<ToDo, Void> noColumn;
    @FXML private TableColumn<ToDo, Boolean> checkboxColumn;
    @FXML private TableView<ToDo> TableView;
    @FXML private ComboBox<String> comboKategori;
    @FXML private TextField searchKategori;

    private static final ObservableList<ToDo> toDoList = FXCollections.observableArrayList();

    public static ObservableList<ToDo> getToDoList() {
        return toDoList;
    }

    @FXML
    void SearchByCategory(ActionEvent event) {
        applyFilter();
    }

    private void applyFilter() {
        String kategoriDipilih = comboKategori.getValue();
        if (kategoriDipilih == null || kategoriDipilih.equals("Semua")) {
            TableView.setItems(toDoList);
        } else {
            ObservableList<ToDo> hasilFilter = FXCollections.observableArrayList();
            for (ToDo todo : toDoList) {
                if (todo.getKategori().equalsIgnoreCase(kategoriDipilih)) {
                    hasilFilter.add(todo);
                }
            }
            TableView.setItems(hasilFilter);
            TableView.refresh();
        }
    }

    @FXML
    void addCategory(ActionEvent event) {
        Apps.showKategori();
    }

    @FXML
    void modul_tambah_todolist(ActionEvent event) {
        Apps.showTambahToDo(null);
    }

    @FXML
    void Keluar() {
        Platform.exit();
    }

    @FXML
    public void initialize() {
        // Kolom data utama
        todolist.setCellValueFactory(new PropertyValueFactory<>("judul"));
        deadline.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        kategori.setCellValueFactory(new PropertyValueFactory<>("kategori"));
        TableView.setEditable(true);
        checkboxColumn.setEditable(true);


        // Kolom aksi
        aksi.setCellFactory(col -> new TableCell<ToDo, String>() {
            private final Button editBtn = new Button("Edit");
            private final Button hapusBtn = new Button("Hapus");
            private final javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(5, editBtn, hapusBtn);

            {
                editBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                hapusBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                hapusBtn.setOnAction(event -> {
                    ToDo todo = getTableView().getItems().get(getIndex());
                    toDoList.remove(todo);
                    applyFilter();
                });

                editBtn.setOnAction(event -> {
                    ToDo todo = getTableView().getItems().get(getIndex());
                    Apps.showTambahToDo(todo);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : hbox);
            }
        });

        // Kolom nomor
        noColumn.setCellFactory(col -> new TableCell<ToDo, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(String.valueOf(getTableRow().getIndex() + 1));
                }
            }
        });

        // Kolom checkbox
        checkboxColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        checkboxColumn.setCellFactory(CheckBoxTableCell.forTableColumn(checkboxColumn));

        // Warna hijau jika dicentang
        TableView.setRowFactory(tv -> new TableRow<ToDo>() {
            @Override
            protected void updateItem(ToDo item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.isPrioritas()) {
                        setStyle("-fx-background-color: #e6d3aa;");
                    }
                    else if (item.isSelected()) {
                        setStyle("-fx-background-color: #d4fcd4;");
                    } else {
                        setStyle("");
                    }

                    item.selectedProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal) {
                            setStyle("-fx-background-color: #d4fcd4;");
                        } else {
                            setStyle("");
                        }
                    });
                }
            }
        });

        // Tampilkan semua tugas awalnya
        TableView.setItems(toDoList);

        // Isi ComboBox kategori dan event listener
        comboKategori.setItems(FXCollections.observableArrayList("Semua", "Kuliah", "Pekerjaan", "Pribadi"));
        comboKategori.setValue("Semua");

        // Tambahkan listener agar filtering jalan otomatis saat pilihan berubah
        comboKategori.setOnAction(this::SearchByCategory);

        searchKategori.textProperty().addListener((observable, oldValue, newValue) -> {
            filterByKategoriTextField(newValue);

        });
        // ⏰ Panggil notifikasi deadline H-1
        cekPengingatSekarang();
        mulaiPengingatDeadline();
    }


    //
    private void filterByKategoriTextField(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            TableView.setItems(toDoList); // tampilkan semua
        } else {
            ObservableList<ToDo> hasilFilter = FXCollections.observableArrayList();
            for (ToDo todo : toDoList) {
                String kategori = todo.getKategori();
                if (kategori != null && kategori.toLowerCase().contains(keyword.toLowerCase())) {
                    hasilFilter.add(todo);
                }
            }
            TableView.setItems(hasilFilter);
        }
    }

    private void filterToDoByAllFields(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            TableView.setItems(toDoList);
            return;
        }

        String search = keyword.toLowerCase().trim();

        ObservableList<ToDo> hasilFilter = FXCollections.observableArrayList();
        for (ToDo todo : toDoList) {
            String judul = todo.getJudul() != null ? todo.getJudul().toLowerCase() : "";
            String deadline = todo.getDeadline() != null ? todo.getDeadline().toLowerCase() : "";
            String deskripsi = todo.getDeskripsi() != null ? todo.getDeadline().toLowerCase():"";

            if (judul.contains(search) || deadline.contains(search) || deskripsi.contains(search)){
                hasilFilter.add(todo);
            }
        }

        TableView.setItems(hasilFilter);
        TableView.refresh();
    }

    private void mulaiPengingatDeadline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            periksaDeadlineBesok();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void cekPengingatSekarang() {
        periksaDeadlineBesok();
    }

    private void periksaDeadlineBesok() {
        LocalDate besok = LocalDate.now().plusDays(1);
        for (ToDo todo : toDoList) {
            try {
                LocalDate deadlineToDo = LocalDate.parse(todo.getDeadline());
                String idUnik = todo.getJudul() + "-" + todo.getDeadline();
                if (deadlineToDo.equals(besok) && !sudahDiingatkan.contains(idUnik)) {
                    sudahDiingatkan.add(idUnik);
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Pengingat Deadline");
                        alert.setHeaderText(null);
                        alert.setContentText("Deadline tugas \"" + todo.getJudul() + "\" adalah BESOK!");
                        alert.showAndWait();
                    });
                }
            } catch (Exception e) {
                System.err.println("Gagal memeriksa deadline untuk todo: " + todo.getJudul());
            }
        }
    }

}
