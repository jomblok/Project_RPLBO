package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class MainController {

    @FXML private TableColumn<ToDo, String> aksi;
    @FXML private Button btnKeluar;
    @FXML private TableColumn<ToDo, String> deadline;
    @FXML private TableColumn<ToDo, String> kategori;
    @FXML private TableColumn<ToDo, String> todolist;
    @FXML private TableColumn<ToDo, Void> noColumn;
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
        }
    }

    @FXML
    void addCategory(ActionEvent event) {
        Apps.showKategori();
        // Jika kamu menyimpan kategori di tempat lain, kamu bisa perbarui ComboBox di sini.
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
        // Setup kolom tabel
        todolist.setCellValueFactory(cellData -> cellData.getValue().judulProperty());
        deadline.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        kategori.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());

        // Setup kolom aksi (edit & hapus)
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
                    applyFilter(); // Pastikan filter diperbarui setelah hapus
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

        // Setup kolom nomor
        noColumn.setCellFactory(col -> new TableCell<ToDo, Void>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getIndex() >= TableView.getItems().size()) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
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


    }

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
            TableView.setItems(toDoList); // tampilkan semua
            return;
        }

        String search = keyword.toLowerCase().trim();

        ObservableList<ToDo> hasilFilter = FXCollections.observableArrayList();
        for (ToDo todo : toDoList) {
            String judul = todo.getJudul() != null ? todo.getJudul().toLowerCase() : "";
            String kategori = todo.getKategori() != null ? todo.getKategori().toLowerCase() : "";
            String deadline = todo.getDeadline() != null ? todo.getDeadline().toLowerCase() : "";

            if (judul.contains(search) || kategori.contains(search) || deadline.contains(search)) {
                hasilFilter.add(todo);
            }
        }

        TableView.setItems(hasilFilter);
    }



}
