package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.ThongKeDAL;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;

public class ThongKeBUS {
    private final ThongKeDAL thongKeDAL = new ThongKeDAL();

    public BigDecimal tinhDoanhThu(LocalDate tuNgay, LocalDate denNgay) throws SQLException {
        if (tuNgay == null || denNgay == null) {
            throw new IllegalArgumentException("Ngày bắt đầu và ngày kết thúc không được để trống!");
        }
        if (tuNgay.isAfter(denNgay)) {
            throw new IllegalArgumentException("Từ ngày không được lớn hơn Đến ngày!");
        }
        return thongKeDAL.tinhDoanhThu(tuNgay, denNgay);
    }

    public String getHoaDonCaoNhat() throws SQLException {
        return thongKeDAL.getHoaDonCaoNhat();
    }

    public String getSanPhamBanChayNhat() throws SQLException {
        return thongKeDAL.getSanPhamBanChayNhat();
    }
}