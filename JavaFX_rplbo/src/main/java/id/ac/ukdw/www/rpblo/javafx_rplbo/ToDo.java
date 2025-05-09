package id.ac.ukdw.www.rpblo.javafx_rplbo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.time.LocalDate;

public class ToDo {
    private final StringProperty judul;
    private final StringProperty deskripsi;
    private final StringProperty deadline;
    private final StringProperty kategori;
    private final BooleanProperty prioritas;
    private ToDo currentToDo;

    // Constructor
    public ToDo(String judul, String deskripsi, String deadline, String kategori, boolean prioritas) {
        this.judul = new SimpleStringProperty(judul);
        this.deskripsi = new SimpleStringProperty(deskripsi);
        this.deadline = new SimpleStringProperty(deadline);
        this.kategori = new SimpleStringProperty(kategori);
        this.prioritas = new SimpleBooleanProperty(prioritas);
    }

    // Getter and Setter methods
    public StringProperty judulProperty() { return judul; }
    public StringProperty deskripsiProperty() { return deskripsi; }
    public StringProperty deadlineProperty() { return deadline; }
    public StringProperty kategoriProperty() { return kategori; }
    public BooleanProperty prioritasProperty() { return prioritas; }

    public String getJudul() { return judul.get(); }
    public String getDeskripsi() { return deskripsi.get(); }
    public String getDeadline() { return deadline.get(); }
    public String getKategori() { return kategori.get(); }
    public boolean isPrioritas() { return prioritas.get(); }

    // Setters
    public void setJudul(String judul) { this.judul.set(judul); }
    public void setDeskripsi(String deskripsi) { this.deskripsi.set(deskripsi); }
    public void setDeadline(String deadline) { this.deadline.set(deadline); }
    public void setKategori(String kategori) { this.kategori.set(kategori); }
    public void setPrioritas(boolean prioritas) { this.prioritas.set(prioritas); }
}
