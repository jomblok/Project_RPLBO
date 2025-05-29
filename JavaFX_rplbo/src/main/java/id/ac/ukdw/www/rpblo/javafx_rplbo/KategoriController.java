package id.ac.ukdw.www.rpblo.javafx_rplbo;

import id.ac.ukdw.www.rpblo.javafx_rplbo.Manager.SqliteDB;
import id.ac.ukdw.www.rpblo.javafx_rplbo.Manager.Sessionmanager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.sql.*;

public class KategoriController {

    @FXML
    private TextField inputKategori;

    @FXML
    private TableView<Kategori> tabelKategori;

    @FXML
    private TableColumn<Kategori, String> kolomNama;

    @FXML
    private TableColumn<Kategori, Void> kolomAksi;

    private Kategori editingItem = null;
    private final ObservableList<Kategori> daftarKategori = FXCollections.observableArrayList();
    private static final ObservableList<String> kategoriComboBoxItems = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        kolomNama.setCellValueFactory(data -> data.getValue().namaProperty());
        kolomAksi.setCellFactory(col -> new TableCell<>() {
            private final Button btnEdit = new Button("✏");
            private final Button btnHapus = new Button("🗑");
            private final HBox actionBox = new HBox(5, btnEdit, btnHapus);

            {
                btnEdit.setOnAction(e -> {
                    editingItem = getTableView().getItems().get(getIndex());
                    inputKategori.setText(editingItem.getNama());
                });

                btnHapus.setOnAction(e -> {
                    Kategori item = getTableView().getItems().get(getIndex());

                    // Hapus dari database
                    hapusKategoriDariDatabase(item.getNama());

                    // Baru hapus dari UI setelah berhasil
                    daftarKategori.remove(item);
                    kategoriComboBoxItems.remove(item.getNama());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionBox);
            }
        });

        tabelKategori.setItems(daftarKategori);
        loadKategoriDariDatabase();
    }

    @FXML
    void toHalamanUtama(ActionEvent event) {
        inputKategori.clear();
        editingItem = null;

        // Tutup window kategori (yang sekarang aktif)
        Stage currentStage = (Stage) inputKategori.getScene().getWindow();
        currentStage.close();

        // Tampilkan kembali halaman utama (di primaryStage)
        Apps.showMain();
    }

    @FXML
    private void tambahKategori() {
        String nama = inputKategori.getText().trim();
        int userId = Sessionmanager.getCurrentUserId();

        if (!nama.isEmpty()) {
            if (editingItem != null) {
                try {
                    Connection conn = SqliteDB.getInstance().getConnection();
                    String sql = "UPDATE kategori SET nama = ? WHERE nama = ? AND user_id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nama); // nama baru
                    stmt.setString(2, editingItem.getNama()); // nama lama
                    stmt.setInt(3, userId);
                    stmt.executeUpdate();
                    stmt.close();
                } catch (SQLException e) {
                    System.out.println("Gagal mengedit kategori di database: " + e.getMessage());
                }
                // Mode edit (opsional: bisa tambahkan update ke database jika nama kategori bisa diubah)
            } else {
                // Mode tambah baru - TAMBAHKAN KE DATABASE
                try {
                    Connection conn = SqliteDB.getInstance().getConnection();
                    String sql = "INSERT INTO kategori (nama, user_id) VALUES (?, ?)";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setString(1, nama);
                    stmt.setInt(2, userId);
                    stmt.executeUpdate();
                    stmt.close();

                    // Tambahkan ke tampilan setelah berhasil simpan
                    Kategori kategoriBaru = new Kategori(nama);
                    daftarKategori.add(kategoriBaru);
                    kategoriComboBoxItems.add(nama);

                } catch (SQLException e) {
                    System.out.println("Gagal menambah kategori ke database: " + e.getMessage());
                }
            }
            inputKategori.clear();
        }
    }

    private void loadKategoriDariDatabase() {
        daftarKategori.clear();
        kategoriComboBoxItems.clear();
        int userId = Sessionmanager.getCurrentUserId();
        try {
            Connection conn = SqliteDB.getInstance().getConnection();
            String sql = "SELECT nama FROM kategori WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String nama = rs.getString("nama");
                Kategori kategori = new Kategori(nama);
                daftarKategori.add(kategori);
                kategoriComboBoxItems.add(nama);
            }
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Gagal memuat kategori: " + e.getMessage());
        }
    }

    private void hapusKategoriDariDatabase(String nama) {
        int userId = Sessionmanager.getCurrentUserId();
        try {
            Connection conn = SqliteDB.getInstance().getConnection();
            String sql = "DELETE FROM kategori WHERE nama = ? AND user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nama);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            System.out.println("Gagal menghapus kategori: " + e.getMessage());
        }
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