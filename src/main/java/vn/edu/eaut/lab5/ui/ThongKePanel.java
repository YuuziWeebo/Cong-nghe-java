package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.ThongKeBUS;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ThongKePanel extends JPanel {
    private final ThongKeBUS thongKeBUS = new ThongKeBUS();

    private JTextField txtTuNgay, txtDenNgay;
    private JButton btnThongKeDoanhThu, btnHoaDonCaoNhat, btnSpBanChay;
    private JLabel lblKetQuaDoanhThu, lblKetQuaHoaDon, lblKetQuaSp;

    public ThongKePanel() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        initComponents();
    }

    private void initComponents() {
        JPanel panelMain = new JPanel(new GridLayout(3, 1, 10, 10));

        // --- Block 1: Thống kê Doanh Thu ---
        JPanel panelDoanhThu = new JPanel(new BorderLayout(10, 10));
        panelDoanhThu.setBorder(BorderFactory.createTitledBorder("1. Thong ke doanh thu theo khoảng ngay"));

        JPanel panelInputs = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelInputs.add(new JLabel("Tu ngay (YYYY-MM-DD):"));
        txtTuNgay = new JTextField("2026-01-01", 10);
        panelInputs.add(txtTuNgay);

        panelInputs.add(new JLabel("Den ngay (YYYY-MM-DD):"));
        txtDenNgay = new JTextField(LocalDate.now().toString(), 10);
        panelInputs.add(txtDenNgay);

        btnThongKeDoanhThu = new JButton("Tinh Doanh Thu");
        panelInputs.add(btnThongKeDoanhThu);

        lblKetQuaDoanhThu = new JLabel("Doanh thu: 0 VNĐ");
        lblKetQuaDoanhThu.setFont(new Font("Arial", Font.BOLD, 14));
        lblKetQuaDoanhThu.setForeground(new Color(0, 102, 204));

        panelDoanhThu.add(panelInputs, BorderLayout.NORTH);
        panelDoanhThu.add(lblKetQuaDoanhThu, BorderLayout.CENTER);

        // --- Block 2: Hóa Đơn Cao Nhất ---
        JPanel panelHoaDon = new JPanel(new BorderLayout(10, 10));
        panelHoaDon.setBorder(BorderFactory.createTitledBorder("2. Hoa don co gia tri cao nhat"));

        btnHoaDonCaoNhat = new JButton("Xem Hoa Don Cao Nhat");
        lblKetQuaHoaDon = new JLabel("Thông tin: Chưa tải");
        lblKetQuaHoaDon.setFont(new Font("Arial", Font.ITALIC, 13));

        JPanel panelHdBtn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelHdBtn.add(btnHoaDonCaoNhat);

        panelHoaDon.add(panelHdBtn, BorderLayout.NORTH);
        panelHoaDon.add(lblKetQuaHoaDon, BorderLayout.CENTER);

        // --- Block 3: Sản Phẩm Bán Chạy Nhất ---
        JPanel panelSp = new JPanel(new BorderLayout(10, 10));
        panelSp.setBorder(BorderFactory.createTitledBorder("3. San pham ban chay nhat"));

        btnSpBanChay = new JButton("Xem San Pham Ban Chay");
        lblKetQuaSp = new JLabel("Thông tin: Chưa tải");
        lblKetQuaSp.setFont(new Font("Arial", Font.ITALIC, 13));

        JPanel panelSpBtn = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSpBtn.add(btnSpBanChay);

        panelSp.add(panelSpBtn, BorderLayout.NORTH);
        panelSp.add(lblKetQuaSp, BorderLayout.CENTER);

        // Gom các block vào
        panelMain.add(panelDoanhThu);
        panelMain.add(panelHoaDon);
        panelMain.add(panelSp);

        add(panelMain, BorderLayout.CENTER);

        // Event Listeners
        btnThongKeDoanhThu.addActionListener(e -> thongKeDoanhThuAction());
        btnHoaDonCaoNhat.addActionListener(e -> xemHoaDonCaoNhatAction());
        btnSpBanChay.addActionListener(e -> xemSpBanChayAction());
    }

    // --- Action 1: Doanh Thu dùng SwingWorker ---
    private void thongKeDoanhThuAction() {
        try {
            LocalDate tuNgay = LocalDate.parse(txtTuNgay.getText().trim());
            LocalDate denNgay = LocalDate.parse(txtDenNgay.getText().trim());

            btnThongKeDoanhThu.setEnabled(false);
            lblKetQuaDoanhThu.setText("Đang tính toán...");

            SwingWorker<BigDecimal, Void> worker = new SwingWorker<>() {
                @Override
                protected BigDecimal doInBackground() throws Exception {
                    return thongKeBUS.tinhDoanhThu(tuNgay, denNgay);
                }

                @Override
                protected void done() {
                    try {
                        BigDecimal doanhThu = get();
                        lblKetQuaDoanhThu.setText("Doanh thu: " + doanhThu.toPlainString() + " VNĐ");
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(ThongKePanel.this, "Lỗi: " + e.getMessage());
                        lblKetQuaDoanhThu.setText("Doanh thu: Lỗi tính toán!");
                    } finally {
                        btnThongKeDoanhThu.setEnabled(true);
                    }
                }
            };
            worker.execute();

        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Định dạng ngày phải là YYYY-MM-DD (VD: 2026-05-20)");
        }
    }

    // --- Action 2: Hóa Đơn Cao Nhất dùng SwingWorker ---
    private void xemHoaDonCaoNhatAction() {
        btnHoaDonCaoNhat.setEnabled(false);
        lblKetQuaHoaDon.setText("Đang tải dữ liệu...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                return thongKeBUS.getHoaDonCaoNhat();
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    lblKetQuaHoaDon.setText("Thông tin: " + result);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ThongKePanel.this, "Lỗi: " + e.getMessage());
                } finally {
                    btnHoaDonCaoNhat.setEnabled(true);
                }
            }
        };
        worker.execute();
    }

    // --- Action 3: Sản Phẩm Bán Chạy dùng SwingWorker ---
    private void xemSpBanChayAction() {
        btnSpBanChay.setEnabled(false);
        lblKetQuaSp.setText("Đang tải dữ liệu...");

        SwingWorker<String, Void> worker = new SwingWorker<>() {
            @Override
            protected String doInBackground() throws Exception {
                return thongKeBUS.getSanPhamBanChayNhat();
            }

            @Override
            protected void done() {
                try {
                    String result = get();
                    lblKetQuaSp.setText("Thông tin: " + result);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(ThongKePanel.this, "Lỗi: " + e.getMessage());
                } finally {
                    btnSpBanChay.setEnabled(true);
                }
            }
        };
        worker.execute();
    }
}