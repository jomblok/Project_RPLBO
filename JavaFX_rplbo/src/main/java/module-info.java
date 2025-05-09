module id.ac.ukdw.www.rpblo.javafx_rplbo {
    requires javafx.controls;
    requires javafx.fxml;  // Menambahkan modul javafx.fxml
    requires javafx.base;
    requires java.desktop;

    opens id.ac.ukdw.www.rpblo.javafx_rplbo to javafx.fxml;  // Membuka paket agar dapat digunakan oleh javafx.fxml
    exports id.ac.ukdw.www.rpblo.javafx_rplbo;
}