package vn.edu.eaut.lab8.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Product {
    private int id;
    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String tenSanPham;
    @DecimalMin(value = "0.01", message = "Giá phải lớn hơn 0")
    private BigDecimal gia = BigDecimal.ZERO;
    @Min(value = 0, message = "Số lượng không được âm")
    private int soLuong;

    public Product() {}
    public Product(int id, String tenSanPham, BigDecimal gia, int soLuong) {
        this.id=id; this.tenSanPham=tenSanPham; this.gia=gia; this.soLuong=soLuong;
    }
    public int getId(){return id;}
    public void setId(int v){id=v;}
    public String getTenSanPham(){return tenSanPham;}
    public void setTenSanPham(String v){tenSanPham=v;}
    public BigDecimal getGia(){return gia;}
    public void setGia(BigDecimal v){gia=v;}
    public int getSoLuong(){return soLuong;}
    public void setSoLuong(int v){soLuong=v;}
}
