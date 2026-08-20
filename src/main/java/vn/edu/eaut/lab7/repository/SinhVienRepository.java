package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.SinhVien;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SinhVienRepository {

    public List<SinhVien> findAll() {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sinh_vien";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new SinhVien(
                    rs.getInt("id"),
                    rs.getString("ma_sinh_vien"),
                    rs.getString("ho_ten"),
                    rs.getString("email"),
                    rs.getString("lop")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public SinhVien findById(int id) {
        String sql = "SELECT * FROM sinh_vien WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SinhVien(
                        rs.getInt("id"),
                        rs.getString("ma_sinh_vien"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("lop")
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void add(SinhVien sv) {
        String sql = "INSERT INTO sinh_vien (ma_sinh_vien, ho_ten, email, lop) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSinhVien());
            ps.setString(2, sv.getHoTen());
            ps.setString(3, sv.getEmail());
            ps.setString(4, sv.getLop());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(SinhVien sv) {
        String sql = "UPDATE sinh_vien SET ma_sinh_vien=?, ho_ten=?, email=?, lop=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSinhVien());
            ps.setString(2, sv.getHoTen());
            ps.setString(3, sv.getEmail());
            ps.setString(4, sv.getLop());
            ps.setInt(5, sv.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM sinh_vien WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<SinhVien> search(String key) {
        if (key == null || key.trim().isEmpty()) return findAll();
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sinh_vien WHERE LOWER(ho_ten) LIKE ? OR LOWER(lop) LIKE ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + key.toLowerCase() + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SinhVien(
                        rs.getInt("id"),
                        rs.getString("ma_sinh_vien"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("lop")
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public int count(String keyword) {
        String sql = "SELECT COUNT(*) FROM sinh_vien WHERE LOWER(ho_ten) LIKE ? OR LOWER(lop) LIKE ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = (keyword == null) ? "%%" : "%" + keyword.toLowerCase() + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return 0;
    }

    // Lấy danh sách theo trang (page: trang hiện tại, pageSize: số dòng/trang)
    public List<SinhVien> findByPage(String keyword, int page, int pageSize) {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sinh_vien WHERE LOWER(ho_ten) LIKE ? OR LOWER(lop) LIKE ? LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = (keyword == null) ? "%%" : "%" + keyword.toLowerCase() + "%";
            ps.setString(1, k);
            ps.setString(2, k);
            ps.setInt(3, pageSize);
            ps.setInt(4, offset);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SinhVien(
                        rs.getInt("id"),
                        rs.getString("ma_sinh_vien"),
                        rs.getString("ho_ten"),
                        rs.getString("email"),
                        rs.getString("lop")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}