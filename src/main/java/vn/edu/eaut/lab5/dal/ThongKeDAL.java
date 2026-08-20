package vn.edu.eaut.lab5.dal;

import vn.edu.eaut.lab5.config.DBHelper;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;

public class ThongKeDAL {

    // Tính doanh thu theo khoảng ngày
    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        String sql = "SELECT COALESCE(SUM(tong_tien), 0) AS doanh_thu FROM hoa_don WHERE ngay_lap BETWEEN ? AND ?";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(tuNgay));
            ps.setDate(2, Date.valueOf(denNgay));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBigDecimal("doanh_thu");
                }
            }
        }
        return BigDecimal.ZERO;
    }

    // Lấy thông tin Hóa đơn có giá trị cao nhất
    public String getHoaDonCaoNhat() throws SQLException {
        String sql = "SELECT ma_hd, ngay_lap, ma_kh, tong_tien FROM hoa_don ORDER BY tong_tien DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return String.format("Mã HD: %d | Ngày lập: %s | Mã KH: %d | Tổng tiền: %s VNĐ",
                        rs.getInt("ma_hd"),
                        rs.getDate("ngay_lap").toString(),
                        rs.getInt("ma_kh"),
                        rs.getBigDecimal("tong_tien").toPlainString());
            }
        }
        return "Chưa có hóa đơn nào.";
    }

    // Lấy thông tin Sản phẩm bán chạy nhất
    public String getSanPhamBanChayNhat() throws SQLException {
        String sql = "SELECT sp.ma_sp, sp.ten_sp, SUM(ct.so_luong) AS tong_so_luong " +
                "FROM chi_tiet_hoa_don ct " +
                "JOIN san_pham sp ON ct.ma_sp = sp.ma_sp " +
                "GROUP BY sp.ma_sp, sp.ten_sp " +
                "ORDER BY tong_so_luong DESC LIMIT 1";
        try (Connection conn = DBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return String.format("Mã SP: %d | Tên SP: %s | Đã bán: %d cái",
                        rs.getInt("ma_sp"),
                        rs.getString("ten_sp"),
                        rs.getInt("tong_so_luong"));
            }
        }
        return "Chưa có sản phẩm nào được bán.";
    }
}