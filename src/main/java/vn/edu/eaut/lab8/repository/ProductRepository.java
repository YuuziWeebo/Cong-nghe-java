package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.Product;
import vn.edu.eaut.lab8.util.DBConnection;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ProductRepository {
    public void add(Product p) throws SQLException {
        try(Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement(
                "INSERT INTO sanpham(ten_san_pham,gia,so_luong) VALUES(?,?,?)")) {
            s.setString(1,p.getTenSanPham()); s.setBigDecimal(2,p.getGia()); s.setInt(3,p.getSoLuong()); s.executeUpdate();
        }
    }
    public List<Product> findAll() throws SQLException {
        List<Product> out=new ArrayList<>();
        try(Connection c=DBConnection.getConnection(); PreparedStatement s=c.prepareStatement("SELECT * FROM sanpham ORDER BY id");
            ResultSet r=s.executeQuery()) {
            while(r.next()) out.add(new Product(r.getInt("id"),r.getString("ten_san_pham"),
                    r.getBigDecimal("gia"),r.getInt("so_luong")));
        }
        return out;
    }
}
