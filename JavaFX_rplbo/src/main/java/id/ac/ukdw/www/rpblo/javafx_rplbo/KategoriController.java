package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class KategoriController {
    @FXML
    private TextField inputKategori;

    @FXML
    private TableView<Kategori> tabelKategori;

    @FXML
    private TableColumn<Kategori, String> kolomNama;

    @FXML
    private TableColumn<Kategori, Void> kolomAksi;

    private final ObservableList<Kategori> daftarKategori = FXCollections.observableArrayList();

    // ObservableList untuk digunakan oleh ComboBox di controller lain
    private static ObservableList<String> kategoriComboBoxItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        kolomNama.setCellValueFactory(data -> data.getValue().namaProperty());

        kolomAksi.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("✏");
            private final Button btnHapus = new Button("🗑");
            private final HBox actionBox = new HBox(5, btnEdit, btnHapus);

            {
                btnHapus.setOnAction(e -> {
                    Kategori item = getTableView().getItems().get(getIndex());
                    daftarKategori.remove(item);
                    kategoriComboBoxItems.remove(item.getNama()); // Hapus kategori dari ComboBox
                });

                btnEdit.setOnAction(e -> {
                    Kategori item = getTableView().getItems().get(getIndex());
                    inputKategori.setText(item.getNama());
                    daftarKategori.remove(item); // Hapus dulu, lalu bisa ditambah lagi
                    kategoriComboBoxItems.remove(item.getNama()); // Hapus kategori dari ComboBox
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        });

        tabelKategori.setItems(daftarKategori);
    }

    @FXML
    void toHalamanUtama(ActionEvent event) {
        Apps.showMain();
    }

    @FXML
    private void tambahKategori() {
        String nama = inputKategori.getText().trim();
        if (!nama.isEmpty()) {
            Kategori kategoriBaru = new Kategori(nama);
            daftarKategori.add(kategoriBaru);
            kategoriComboBoxItems.add(nama);  // Tambahkan kategori ke ComboBox di controller lain
            inputKategori.clear();
        }
    }
    static {
        kategoriComboBoxItems.addAll("Kuliah", "Kerja", "Belanja", "Lainnya");
    }

    public static ObservableList<String> getKategoriComboBoxItems() {
        return kategoriComboBoxItems;
    }


    public static class Kategori {
        private final SimpleStringProperty nama;

        public Kategori(String nama) {
            this.nama = new SimpleStringProperty(nama);
        }

        public String getNama() {
            return nama.get();
        }

        public void setNama(String nama) {
            this.nama.set(nama);
        }

        public SimpleStringProperty namaProperty() {
            return nama;
        }
    }
}
