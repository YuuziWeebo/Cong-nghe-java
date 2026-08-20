package vn.edu.eaut.lab5.util;

import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HoaDonExportUtil {

    private static final DecimalFormat fmtMoney = new DecimalFormat("#,##0 VNĐ");

    public static void exportToTxt(HoaDon hd, List<ChiTietHoaDon> chiTietList, File file) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            writer.write("================================================");
            writer.newLine();
            writer.write("                HOÁ ĐƠN BÁN HÀNG               ");
            writer.newLine();
            writer.write("================================================");
            writer.newLine();
            writer.write("Mã hóa đơn : " + hd.getMaHd());
            writer.newLine();
            writer.write("Ngày lập   : " + hd.getNgayLap().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            writer.newLine();
            writer.write("Khách hàng : " + (hd.getTenKh() != null ? hd.getTenKh() : "Mã KH " + hd.getMaKh()));
            writer.newLine();
            writer.write("------------------------------------------------");
            writer.newLine();
            writer.write(String.format("%-20s %-8s %-12s %-12s", "Tên SP", "SL", "Đơn giá", "Thành tiền"));
            writer.newLine();
            writer.write("------------------------------------------------");
            writer.newLine();

            for (ChiTietHoaDon ct : chiTietList) {
                writer.write(String.format("%-20s %-8d %-12s %-12s",
                        truncate(ct.getTenSp(), 18),
                        ct.getSoLuong(),
                        fmtMoney.format(ct.getDonGia()),
                        fmtMoney.format(ct.getThanhTien())));
                writer.newLine();
            }

            writer.write("------------------------------------------------");
            writer.newLine();
            writer.write("TỔNG TIỀN: " + fmtMoney.format(hd.getTongTien()));
            writer.newLine();
            writer.write("================================================");
            writer.newLine();
            writer.write("        Cảm ơn quý khách và hẹn gặp lại!        ");
            writer.newLine();
        }
    }

    public static void exportToCsv(HoaDon hd, List<ChiTietHoaDon> chiTietList, File file) throws Exception {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) {

            // Ghi UTF-8 BOM để Excel hiển thị đúng tiếng Việt không bị lỗi font
            writer.write('\ufeff');

            // Thông tin chung
            writer.write("Mã hóa đơn," + hd.getMaHd());
            writer.newLine();
            writer.write("Ngày lập," + hd.getNgayLap());
            writer.newLine();
            writer.write("Khách hàng," + (hd.getTenKh() != null ? "\"" + hd.getTenKh() + "\"" : hd.getMaKh()));
            writer.newLine();
            writer.newLine();

            // Header bảng chi tiết
            writer.write("Mã SP,Tên sản phẩm,Số lượng,Đơn giá,Thành tiền");
            writer.newLine();

            // Dữ liệu sản phẩm
            for (ChiTietHoaDon ct : chiTietList) {
                writer.write(String.format("%d,\"%s\",%d,%.2f,%.2f",
                        ct.getMaSp(),
                        ct.getTenSp().replace("\"", "\"\""), // Escape dấu nháy kép cho CSV
                        ct.getSoLuong(),
                        ct.getDonGia(),
                        ct.getThanhTien()));
                writer.newLine();
            }

            // Tổng tiền
            writer.write(String.format(",,,Tổng tiền,%.2f", hd.getTongTien()));
            writer.newLine();
        }
    }

    private static String truncate(String text, int length) {
        if (text == null) return "";
        return text.length() > length ? text.substring(0, length - 2) + ".." : text;
    }
}