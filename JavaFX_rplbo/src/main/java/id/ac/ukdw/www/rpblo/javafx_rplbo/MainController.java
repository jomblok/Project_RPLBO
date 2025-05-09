package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableRow;

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
                    if (item.isSelected()) {
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

        // Data awal
        TableView.setItems(toDoList);

        // Combo box
        ObservableList<String> semuaKategori = FXCollections.observableArrayList();
        semuaKategori.add("Semua");
        semuaKategori.addAll(KategoriController.getKategoriComboBoxItems());
        comboKategori.setItems(semuaKategori);
        comboKategori.setValue("Semua");

        // Pencarian
        searchKategori.textProperty().addListener((obs, oldVal, newVal) -> filterByKategoriTextField(newVal));
        searchKataKunci.textProperty().addListener((obs, oldVal, newVal) -> filterToDoByAllFields(newVal));
    }



    private void filterByKategoriTextField(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            TableView.setItems(toDoList);
        } else {
            ObservableList<ToDo> hasilFilter = FXCollections.observableArrayList();
            for (ToDo todo : toDoList) {
                String kategori = todo.getKategori();
                if (kategori != null && kategori.toLowerCase().contains(keyword.toLowerCase())) {
                    hasilFilter.add(todo);
                }
            }
            TableView.setItems(hasilFilter);
            TableView.refresh();
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
            String kategori = todo.getKategori() != null ? todo.getKategori().toLowerCase() : "";
            String deadline = todo.getDeadline() != null ? todo.getDeadline().toLowerCase() : "";

            if (judul.contains(search) || kategori.contains(search) || deadline.contains(search)) {
                hasilFilter.add(todo);
            }
        }

        TableView.setItems(hasilFilter);
        TableView.refresh();
    }
}
