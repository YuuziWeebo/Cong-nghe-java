package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;

public class HoaDonDAL {

    public int insertHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        String sqlHoaDon = "INSERT INTO hoa_don(ngay_lap, ma_kh, tong_tien) VALUES (?, ?, ?)";
        String sqlChiTiet = "INSERT INTO chi_tiet_hoa_don(ma_hd, ma_sp, so_luong, don_gia, thanh_tien) VALUES (?, ?, ?, ?, ?)";
        String sqlTruKho = "UPDATE san_pham SET so_luong = so_luong - ? WHERE ma_sp = ?";

        Connection conn = null;
        try {
            conn = DBHelper.getConnection();
            conn.setAutoCommit(false); // Bắt đầu Transaction

            BigDecimal tongTien = tinhTongTien(chiTietList);
            int maHd = 0;

            // 1. Thêm Hóa đơn
            try (PreparedStatement ps = conn.prepareStatement(sqlHoaDon, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(LocalDate.now()));
                ps.setInt(2, maKh);
                ps.setBigDecimal(3, tongTien);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        maHd = rs.getInt(1);
                    } else {
                        throw new SQLException("Không lấy được mã hóa đơn.");
                    }
                }
            }

            // 2. Thêm Chi tiết hóa đơn
            try (PreparedStatement ps = conn.prepareStatement(sqlChiTiet)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    ps.setInt(1, maHd);
                    ps.setInt(2, ct.getMaSp());
                    ps.setInt(3, ct.getSoLuong());
                    ps.setBigDecimal(4, ct.getDonGia());
                    ps.setBigDecimal(5, ct.getThanhTien());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            // 3. Trừ số lượng tồn kho của Sản phẩm (Yêu cầu Bài 7)
            try (PreparedStatement ps = conn.prepareStatement(sqlTruKho)) {
                for (ChiTietHoaDon ct : chiTietList) {
                    ps.setInt(1, ct.getSoLuong());
                    ps.setInt(2, ct.getMaSp());
                    ps.addBatch();
                }
                ps.executeBatch();
            }

            conn.commit(); // Commit nếu tất cả công việc thành công
            return maHd;

        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback nếu xảy ra lỗi
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

    private BigDecimal tinhTongTien(List<ChiTietHoaDon> chiTietList) {
        BigDecimal tong = BigDecimal.ZERO;
        for (ChiTietHoaDon ct : chiTietList) {
            tong = tong.add(ct.getThanhTien());
        }
        return tong;
    }
}