package vn.edu.eaut.lab5.bus;

import vn.edu.eaut.lab5.dal.HoaDonDAL;
import vn.edu.eaut.lab5.dal.SanPhamDAL;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.SanPham;

import java.sql.SQLException;
import java.util.List;

public class HoaDonBUS {
    private final HoaDonDAL hoaDonDAL = new HoaDonDAL();
    private final SanPhamDAL sanPhamDAL = new SanPhamDAL();

    public int taoHoaDon(int maKh, List<ChiTietHoaDon> chiTietList) throws SQLException {
        if (maKh <= 0) {
            throw new IllegalArgumentException("Vui lòng chọn khách hàng!");
        }
        if (chiTietList == null || chiTietList.isEmpty()) {
            throw new IllegalArgumentException("Danh sách sản phẩm trong hóa đơn không được rỗng!");
        }

        // Kiểm tra tồn kho từng sản phẩm
        List<SanPham> dsSanPham = sanPhamDAL.findAll();
        for (ChiTietHoaDon ct : chiTietList) {
            SanPham spInDb = dsSanPham.stream()
                    .filter(sp -> sp.getMaSp() == ct.getMaSp())
                    .findFirst()
                    .orElse(null);

            if (spInDb == null) {
                throw new IllegalArgumentException("Sản phẩm ID " + ct.getMaSp() + " không tồn tại!");
            }
            if (spInDb.getSoLuong() <= 0) {
                throw new IllegalArgumentException("Sản phẩm '" + spInDb.getTenSp() + "' đã hết hàng!");
            }
            if (ct.getSoLuong() > spInDb.getSoLuong()) {
                throw new IllegalArgumentException("Sản phẩm '" + spInDb.getTenSp() + "' chỉ còn " + spInDb.getSoLuong() + " sản phẩm trong kho!");
            }
        }

        return hoaDonDAL.insertHoaDon(maKh, chiTietList);
    }
}