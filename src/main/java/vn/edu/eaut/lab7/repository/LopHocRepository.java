package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.LopHoc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LopHocRepository {

    public List<LopHoc> findAll() {
        List<LopHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM lop_hoc";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new LopHoc(
                    rs.getInt("id"), rs.getString("ma_lop"), rs.getString("ten_lop"),
                    rs.getString("co_van_hoc_tap"), rs.getInt("so_luong_sinh_vien")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public LopHoc findById(int id) {
        String sql = "SELECT * FROM lop_hoc WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new LopHoc(
                        rs.getInt("id"), rs.getString("ma_lop"), rs.getString("ten_lop"),
                        rs.getString("co_van_hoc_tap"), rs.getInt("so_luong_sinh_vien")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void add(LopHoc lh) {
        String sql = "INSERT INTO lop_hoc (ma_lop, ten_lop, co_van_hoc_tap, so_luong_sinh_vien) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lh.getMaLop());
            ps.setString(2, lh.getTenLop());
            ps.setString(3, lh.getCoVanHocTap());
            ps.setInt(4, lh.getSoLuongSinhVien());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(LopHoc lh) {
        String sql = "UPDATE lop_hoc SET ma_lop=?, ten_lop=?, co_van_hoc_tap=?, so_luong_sinh_vien=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, lh.getMaLop());
            ps.setString(2, lh.getTenLop());
            ps.setString(3, lh.getCoVanHocTap());
            ps.setInt(4, lh.getSoLuongSinhVien());
            ps.setInt(5, lh.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM lop_hoc WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<LopHoc> search(String key) {
        if (key == null || key.trim().isEmpty()) return findAll();
        List<LopHoc> list = new ArrayList<>();
        String sql = "SELECT * FROM lop_hoc WHERE LOWER(ma_lop) LIKE ? OR LOWER(ten_lop) LIKE ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + key.toLowerCase() + "%";
            ps.setString(1, k); ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new LopHoc(
                        rs.getInt("id"), rs.getString("ma_lop"), rs.getString("ten_lop"),
                        rs.getString("co_van_hoc_tap"), rs.getInt("so_luong_sinh_vien")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}   