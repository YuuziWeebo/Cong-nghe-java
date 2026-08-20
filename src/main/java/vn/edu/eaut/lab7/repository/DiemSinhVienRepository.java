package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.DiemSinhVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DiemSinhVienRepository {

    public List<DiemSinhVien> findAll() {
        List<DiemSinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM diem_sinh_vien";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DiemSinhVien(
                    rs.getInt("id"), rs.getString("ma_sinh_vien"), rs.getString("ho_ten"),
                    rs.getDouble("diem_chuyen_can"), rs.getDouble("diem_giua_ky"), rs.getDouble("diem_cuoi_ky")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public DiemSinhVien findById(int id) {
        String sql = "SELECT * FROM diem_sinh_vien WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DiemSinhVien(
                        rs.getInt("id"), rs.getString("ma_sinh_vien"), rs.getString("ho_ten"),
                        rs.getDouble("diem_chuyen_can"), rs.getDouble("diem_giua_ky"), rs.getDouble("diem_cuoi_ky")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void add(DiemSinhVien d) {
        String sql = "INSERT INTO diem_sinh_vien (ma_sinh_vien, ho_ten, diem_chuyen_can, diem_giua_ky, diem_cuoi_ky) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getMaSinhVien());
            ps.setString(2, d.getHoTen());
            ps.setDouble(3, d.getDiemChuyenCan());
            ps.setDouble(4, d.getDiemGiuaKy());
            ps.setDouble(5, d.getDiemCuoiKy());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(DiemSinhVien d) {
        String sql = "UPDATE diem_sinh_vien SET ma_sinh_vien=?, ho_ten=?, diem_chuyen_can=?, diem_giua_ky=?, diem_cuoi_ky=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getMaSinhVien());
            ps.setString(2, d.getHoTen());
            ps.setDouble(3, d.getDiemChuyenCan());
            ps.setDouble(4, d.getDiemGiuaKy());
            ps.setDouble(5, d.getDiemCuoiKy());
            ps.setInt(6, d.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM diem_sinh_vien WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<DiemSinhVien> search(String key) {
        if (key == null || key.trim().isEmpty()) return findAll();
        List<DiemSinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM diem_sinh_vien WHERE LOWER(ho_ten) LIKE ? OR LOWER(ma_sinh_vien) LIKE ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + key.toLowerCase() + "%";
            ps.setString(1, k); ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new DiemSinhVien(
                        rs.getInt("id"), rs.getString("ma_sinh_vien"), rs.getString("ho_ten"),
                        rs.getDouble("diem_chuyen_can"), rs.getDouble("diem_giua_ky"), rs.getDouble("diem_cuoi_ky")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}