package vn.edu.eaut.lab8.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Sach {
    private int id;
    @NotBlank(message = "Tên sách không được để trống")
    private String tenSach;
    @NotBlank(message = "Tác giả không được để trống")
    private String tacGia;
    @Min(value = 1000, message = "Năm xuất bản không hợp lệ")
    private int namXuatBan;

    public Sach() {}
    public Sach(int id, String tenSach, String tacGia, int namXuatBan) {
        this.id = id; this.tenSach = tenSach; this.tacGia = tacGia; this.namXuatBan = namXuatBan;
    }
    public int getId() { return id; }
    public void setId(int v) { id = v; }
    public String getTenSach() { return tenSach; }
    public void setTenSach(String v) { tenSach = v; }
    public String getTacGia() { return tacGia; }
    public void setTacGia(String v) { tacGia = v; }
    public int getNamXuatBan() { return namXuatBan; }
    public void setNamXuatBan(int v) { namXuatBan = v; }
}
