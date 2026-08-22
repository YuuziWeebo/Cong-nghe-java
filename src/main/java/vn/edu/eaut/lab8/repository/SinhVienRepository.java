package vn.edu.eaut.lab8.repository;

import vn.edu.eaut.lab8.model.SinhVien;
import vn.edu.eaut.lab8.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SinhVienRepository {
    public List<SinhVien> findAll() throws SQLException {
        return find("");
    }

    public List<SinhVien> find(String keyword) throws SQLException {
        List<SinhVien> list = new ArrayList<>();
        String sql = "SELECT * FROM sinhvien WHERE ho_ten LIKE ? OR lop LIKE ? ORDER BY id";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            String k = "%" + (keyword == null ? "" : keyword.trim()) + "%";
            ps.setString(1, k); ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new SinhVien(rs.getInt("id"), rs.getString("ma_sinh_vien"),
                            rs.getString("ho_ten"), rs.getString("email"), rs.getString("lop")));
                }
            }
        }
        return list;
    }

    public void add(SinhVien sv) throws SQLException {
        String sql = "INSERT INTO sinhvien(ma_sinh_vien,ho_ten,email,lop) VALUES(?,?,?,?)";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1, sv.getMaSinhVien()); ps.setString(2, sv.getHoTen());
            ps.setString(3, sv.getEmail()); ps.setString(4, sv.getLop()); ps.executeUpdate();
        }
    }

    public void update(SinhVien sv) throws SQLException {
        String sql = "UPDATE sinhvien SET ma_sinh_vien=?,ho_ten=?,email=?,lop=? WHERE id=?";
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement(sql)) {
            ps.setString(1,sv.getMaSinhVien()); ps.setString(2,sv.getHoTen()); ps.setString(3,sv.getEmail());
            ps.setString(4,sv.getLop()); ps.setInt(5,sv.getId()); ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        try (Connection c=DBConnection.getConnection(); PreparedStatement ps=c.prepareStatement("DELETE FROM sinhvien WHERE id=?")) {
            ps.setInt(1,id); ps.executeUpdate();
        }
    }
}
