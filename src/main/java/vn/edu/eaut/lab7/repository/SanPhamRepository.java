package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.SanPham;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SanPhamRepository {

    public List<SanPham> findAll() {
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM san_pham";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new SanPham(
                    rs.getInt("id"), rs.getString("ma_san_pham"), rs.getString("ten_san_pham"),
                    rs.getString("mo_ta"), rs.getDouble("gia"), rs.getInt("so_luong")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public SanPham findById(int id) {
        String sql = "SELECT * FROM san_pham WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SanPham(
                        rs.getInt("id"), rs.getString("ma_san_pham"), rs.getString("ten_san_pham"),
                        rs.getString("mo_ta"), rs.getDouble("gia"), rs.getInt("so_luong")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void add(SanPham sp) {
        String sql = "INSERT INTO san_pham (ma_san_pham, ten_san_pham, mo_ta, gia, so_luong) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getTenSanPham());
            ps.setString(3, sp.getMoTa());
            ps.setDouble(4, sp.getGia());
            ps.setInt(5, sp.getSoLuong());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(SanPham sp) {
        String sql = "UPDATE san_pham SET ma_san_pham=?, ten_san_pham=?, mo_ta=?, gia=?, so_luong=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sp.getMaSanPham());
            ps.setString(2, sp.getTenSanPham());
            ps.setString(3, sp.getMoTa());
            ps.setDouble(4, sp.getGia());
            ps.setInt(5, sp.getSoLuong());
            ps.setInt(6, sp.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM san_pham WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<SanPham> search(String key) {
        if (key == null || key.trim().isEmpty()) return findAll();
        List<SanPham> list = new ArrayList<>();
        String sql = "SELECT * FROM san_pham WHERE LOWER(ten_san_pham) LIKE ? OR LOWER(ma_san_pham) LIKE ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + key.toLowerCase() + "%";
            ps.setString(1, k); ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SanPham(
                        rs.getInt("id"), rs.getString("ma_san_pham"), rs.getString("ten_san_pham"),
                        rs.getString("mo_ta"), rs.getDouble("gia"), rs.getInt("so_luong")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}