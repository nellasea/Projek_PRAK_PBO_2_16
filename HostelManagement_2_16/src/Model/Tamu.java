/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;

public class Tamu {
    private int id;
    private String nama;
    private String noKtp;
    private String noTelepon;
    private String alamat;
    private Date tanggalLahir;
    
    public Tamu() {}
    
    public Tamu(int id, String nama, String noKtp, String noTelepon, String alamat, Date tanggalLahir) {
        this.id = id;
        this.nama = nama;
        this.noKtp = noKtp;
        this.noTelepon = noTelepon;
        this.alamat = alamat;
        this.tanggalLahir = tanggalLahir;
    }
    
    // Getters dan Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNoKtp() { return noKtp; }
    public void setNoKtp(String noKtp) { this.noKtp = noKtp; }
    public String getNoTelepon() { return noTelepon; }
    public void setNoTelepon(String noTelepon) { this.noTelepon = noTelepon; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
    public Date getTanggalLahir() { return tanggalLahir; }
    public void setTanggalLahir(Date tanggalLahir) { this.tanggalLahir = tanggalLahir; }
}