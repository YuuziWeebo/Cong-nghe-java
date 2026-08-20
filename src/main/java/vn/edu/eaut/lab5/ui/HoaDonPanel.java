package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.HoaDonBUS;
import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.ChiTietHoaDon;
import vn.edu.eaut.lab5.model.HoaDon;
import vn.edu.eaut.lab5.model.KhachHang;
import vn.edu.eaut.lab5.model.SanPham;
import vn.edu.eaut.lab5.util.HoaDonExportUtil;
import vn.edu.eaut.lab5.util.MessageUtil;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HoaDonPanel extends JPanel {
    private final HoaDonBUS hoaDonBUS = new HoaDonBUS();
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JComboBox<KhachHang> cbKhachHang;
    private JComboBox<SanPham> cbSanPham;
    private JSpinner spinnerSoLuong;
    private JButton btnThemSp, btnXoaSp, btnLuuHoaDon, btnXuatHoaDon, btnLamMoi;
    private JLabel lblTongTien;

    private JTable tableGioHang;
    private DefaultTableModel tableModel;

    private final List<ChiTietHoaDon> dsChiTiet = new ArrayList<>();
    private HoaDon hoaDonVuaTao = null; // Lưu lại hóa đơn vừa tạo thành công để xuất file

    public HoaDonPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadComboboxData();
    }

    private void initComponents() {
        // --- Top Panel: Chọn Khách hàng & Chọn Sản phẩm ---
        JPanel panelTop = new JPanel(new GridLayout(3, 2, 10, 10));
        panelTop.setBorder(BorderFactory.createTitledBorder("Thong tin lap hoa don"));

        panelTop.add(new JLabel("Chon Khach Hang:"));
        cbKhachHang = new JComboBox<>();
        panelTop.add(cbKhachHang);

        panelTop.add(new JLabel("Chon San Pham:"));
        cbSanPham = new JComboBox<>();
        panelTop.add(cbSanPham);

        panelTop.add(new JLabel("So Luong:"));
        spinnerSoLuong = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        panelTop.add(spinnerSoLuong);

        add(panelTop, BorderLayout.NORTH);

        // --- Center Panel: Bảng Giỏ hàng ---
        tableModel = new DefaultTableModel(new String[]{"Ma SP", "Ten SP", "So Luong", "Don Gia", "Thanh Tien"}, 0);
        tableGioHang = new JTable(tableModel);

        JPanel panelCenter = new JPanel(new BorderLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Danh sach san pham da chon"));
        panelCenter.add(new JScrollPane(tableGioHang), BorderLayout.CENTER);

        JPanel panelActionSp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThemSp = new JButton("Them vao gio");
        btnXoaSp = new JButton("Xoa khoi gio");
        panelActionSp.add(btnThemSp);
        panelActionSp.add(btnXoaSp);
        panelCenter.add(panelActionSp, BorderLayout.SOUTH);

        add(panelCenter, BorderLayout.CENTER);

        // --- Bottom Panel: Tổng tiền & Nút Thao tác ---
        JPanel panelBottom = new JPanel(new BorderLayout());

        lblTongTien = new JLabel("Tong tien: 0 VND");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(Color.RED);
        panelBottom.add(lblTongTien, BorderLayout.WEST);

        JPanel panelBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnLuuHoaDon = new JButton("Luu Hoa Don");
        btnXuatHoaDon = new JButton("Xuat File Hoa Don");
        btnXuatHoaDon.setEnabled(false); // Chỉ bật sau khi đã lưu hóa đơn
        btnLamMoi = new JButton("Lam moi gio hang");

        panelBtns.add(btnLuuHoaDon);
        panelBtns.add(btnXuatHoaDon);
        panelBtns.add(btnLamMoi);

        panelBottom.add(panelBtns, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        // Event Listeners
        btnThemSp.addActionListener(e -> btnThemSpAction());
        btnXoaSp.addActionListener(e -> btnXoaSpAction());
        btnLuuHoaDon.addActionListener(e -> btnLuuHoaDonAction());
        btnXuatHoaDon.addActionListener(e -> btnXuatHoaDonAction());
        btnLamMoi.addActionListener(e -> clearGioHang());
    }

    private void loadComboboxData() {
        try {
            cbKhachHang.removeAllItems();
            for (KhachHang kh : khachHangBUS.findAll()) {
                cbKhachHang.addItem(kh);
            }

            cbSanPham.removeAllItems();
            for (SanPham sp : sanPhamBUS.findAll()) {
                cbSanPham.addItem(sp);
            }
        } catch (Exception e) {
            MessageUtil.showError(this, "Loi tai danh sach: " + e.getMessage());
        }
    }

    private void btnThemSpAction() {
        SanPham selectedSp = (SanPham) cbSanPham.getSelectedItem();
        if (selectedSp == null) return;

        int soLuong = (int) spinnerSoLuong.getValue();

        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct.getMaSp() == selectedSp.getMaSp()) {
                ct.setSoLuong(ct.getSoLuong() + soLuong);
                renderTableGioHang();
                return;
            }
        }

        ChiTietHoaDon ct = new ChiTietHoaDon(selectedSp.getMaSp(), selectedSp.getTenSp(), soLuong, selectedSp.getDonGia());
        dsChiTiet.add(ct);
        renderTableGioHang();
    }

    private void btnXoaSpAction() {
        int row = tableGioHang.getSelectedRow();
        if (row >= 0) {
            dsChiTiet.remove(row);
            renderTableGioHang();
        } else {
            MessageUtil.showWarning(this, "Vui long chon dong de xoa khoi gio!");
        }
    }

    private void renderTableGioHang() {
        tableModel.setRowCount(0);
        BigDecimal tongTien = BigDecimal.ZERO;

        for (ChiTietHoaDon ct : dsChiTiet) {
            tableModel.addRow(new Object[]{
                    ct.getMaSp(),
                    ct.getTenSp(),
                    ct.getSoLuong(),
                    ct.getDonGia(),
                    ct.getThanhTien()
            });
            tongTien = tongTien.add(ct.getThanhTien());
        }

        lblTongTien.setText("Tong tien: " + tongTien.toPlainString() + " VND");
    }

    private void btnLuuHoaDonAction() {
        KhachHang selectedKh = (KhachHang) cbKhachHang.getSelectedItem();
        if (selectedKh == null) {
            MessageUtil.showWarning(this, "Vui long chon khach hang!");
            return;
        }

        try {
            int maHd = hoaDonBUS.taoHoaDon(selectedKh.getMaKh(), dsChiTiet);

            // Lưu lại đối tượng Hóa đơn vừa tạo
            BigDecimal tongTien = dsChiTiet.stream()
                    .map(ChiTietHoaDon::getThanhTien)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            hoaDonVuaTao = new HoaDon(maHd, LocalDate.now(), selectedKh.getMaKh(), tongTien);
            if (hoaDonVuaTao.getTenKh() != null) {
                hoaDonVuaTao.setTenKh(selectedKh.getTenKh());
            }

            MessageUtil.showInfo(this, "Tao hoa don thanh cong! Ma HD: " + maHd);
            btnXuatHoaDon.setEnabled(true); // Bật nút Xuất File
        } catch (Exception e) {
            MessageUtil.showError(this, "Loi tao hoa don: " + e.getMessage());
        }
    }

    // --- Xử lý sự kiện Bài 8: Xuất hóa đơn ra file TXT / CSV ---
    private void btnXuatHoaDonAction() {
        if (hoaDonVuaTao == null || dsChiTiet.isEmpty()) {
            MessageUtil.showWarning(this, "Khong co hoa don nao de xuat!");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chon noi luu hoa don");
        fileChooser.setSelectedFile(new File("HoaDon_" + hoaDonVuaTao.getMaHd() + ".txt"));

        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Text File (*.txt)", "txt"));
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV File (*.csv)", "csv"));
        fileChooser.setAcceptAllFileFilterUsed(false);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();

            try {
                FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
                String ext = filter.getExtensions()[0];

                if (!filePath.toLowerCase().endsWith("." + ext)) {
                    fileToSave = new File(filePath + "." + ext);
                }

                if ("csv".equalsIgnoreCase(ext)) {
                    HoaDonExportUtil.exportToCsv(hoaDonVuaTao, dsChiTiet, fileToSave);
                } else {
                    HoaDonExportUtil.exportToTxt(hoaDonVuaTao, dsChiTiet, fileToSave);
                }

                MessageUtil.showInfo(this, "Xuat hoa don thanh cong tai:\n" + fileToSave.getAbsolutePath());
                clearGioHang();
            } catch (Exception ex) {
                MessageUtil.showError(this, "Loi khi xuat file: " + ex.getMessage());
            }
        }
    }

    private void clearGioHang() {
        dsChiTiet.clear();
        hoaDonVuaTao = null;
        renderTableGioHang();
        spinnerSoLuong.setValue(1);
        btnXuatHoaDon.setEnabled(false);
        loadComboboxData(); // Reload lại danh sách sản phẩm để cập nhật số lượng tồn kho mới
    }
}