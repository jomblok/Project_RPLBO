package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;


public class MainController {


    @FXML
    private TableColumn<ToDo, String> aksi;

    @FXML
    private Button btnKeluar;

    @FXML
    private TableColumn<ToDo, String> deadline;

    @FXML
    private TableColumn<ToDo, String> kategori;

    @FXML
    private TableColumn<ToDo, String> todolist;

    @FXML
    private TableColumn<ToDo, Void> noColumn; // Void karena tidak pakai property langsung


    @FXML
    void SearchByCategory(ActionEvent event) {

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
    private TableView<ToDo> TableView;

    private static ObservableList<ToDo> toDoList = FXCollections.observableArrayList();

    public static ObservableList<ToDo> getToDoList() {
        return toDoList;
    }
    @FXML
    void Keluar() {
        Platform.exit();
    }


    @FXML
    public void initialize() {
        // Set data binding
        todolist.setCellValueFactory(cellData -> cellData.getValue().judulProperty());
        deadline.setCellValueFactory(cellData -> cellData.getValue().deadlineProperty());
        kategori.setCellValueFactory(cellData -> cellData.getValue().kategoriProperty());

        // Kolom "Aksi" - Tambahkan tombol edit dan hapus
        aksi.setCellFactory(col -> new TableCell<ToDo, String>() {
            private final javafx.scene.control.Button editBtn = new javafx.scene.control.Button("Edit");
            private final javafx.scene.control.Button hapusBtn = new javafx.scene.control.Button("Hapus");
            private final javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox(5, editBtn, hapusBtn);

            {
                editBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                hapusBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white;");

                hapusBtn.setOnAction(event -> {
                    ToDo todo = getTableView().getItems().get(getIndex());
                    MainController.getToDoList().remove(todo);
                });

                editBtn.setOnAction(event -> {
                    ToDo todo = getTableView().getItems().get(getIndex());
                    Apps.showTambahToDo(todo); // pastikan kamu punya metode showTambahToDo(ToDo todo)
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(hbox);
                }
            }
        });




        // Kolom "No"
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

        TableView.setItems(toDoList);
    }}
