package id.ac.ukdw.www.rpblo.javafx_rplbo;


import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

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
                });

                btnEdit.setOnAction(e -> {
                    Kategori item = getTableView().getItems().get(getIndex());
                    inputKategori.setText(item.getNama());
                    daftarKategori.remove(item); // Hapus dulu, lalu bisa ditambah lagi
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
    private void tambahKategori() {
        String nama = inputKategori.getText().trim();
        if (!nama.isEmpty()) {
            daftarKategori.add(new Kategori(nama));
            inputKategori.clear();
        }
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
