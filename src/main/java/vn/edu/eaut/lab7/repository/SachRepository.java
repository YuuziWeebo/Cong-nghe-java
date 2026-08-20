package vn.edu.eaut.lab7.repository;

import vn.edu.eaut.lab7.model.Sach;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SachRepository {

    public List<Sach> findAll() {
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM sach";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Sach(
                    rs.getInt("id"), rs.getString("ma_sach"), rs.getString("ten_sach"),
                    rs.getString("tac_gia"), rs.getString("nha_xuat_ban"), rs.getInt("nam_xuat_ban")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public Sach findById(int id) {
        String sql = "SELECT * FROM sach WHERE id = ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sach(
                        rs.getInt("id"), rs.getString("ma_sach"), rs.getString("ten_sach"),
                        rs.getString("tac_gia"), rs.getString("nha_xuat_ban"), rs.getInt("nam_xuat_ban")
                    );
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public void add(Sach s) {
        String sql = "INSERT INTO sach (ma_sach, ten_sach, tac_gia, nha_xuat_ban, nam_xuat_ban) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getMaSach());
            ps.setString(2, s.getTenSach());
            ps.setString(3, s.getTacGia());
            ps.setString(4, s.getNhaXuatBan());
            ps.setInt(5, s.getNamXuatBan());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void update(Sach s) {
        String sql = "UPDATE sach SET ma_sach=?, ten_sach=?, tac_gia=?, nha_xuat_ban=?, nam_xuat_ban=? WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, s.getMaSach());
            ps.setString(2, s.getTenSach());
            ps.setString(3, s.getTacGia());
            ps.setString(4, s.getNhaXuatBan());
            ps.setInt(5, s.getNamXuatBan());
            ps.setInt(6, s.getId());
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void delete(int id) {
        String sql = "DELETE FROM sach WHERE id=?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public List<Sach> search(String key) {
        if (key == null || key.trim().isEmpty()) return findAll();
        List<Sach> list = new ArrayList<>();
        String sql = "SELECT * FROM sach WHERE LOWER(ten_sach) LIKE ? OR LOWER(tac_gia) LIKE ?";
        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String k = "%" + key.toLowerCase() + "%";
            ps.setString(1, k); ps.setString(2, k);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Sach(
                        rs.getInt("id"), rs.getString("ma_sach"), rs.getString("ten_sach"),
                        rs.getString("tac_gia"), rs.getString("nha_xuat_ban"), rs.getInt("nam_xuat_ban")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}