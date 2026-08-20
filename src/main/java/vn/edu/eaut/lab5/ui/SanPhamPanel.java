package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.SanPhamBUS;
import vn.edu.eaut.lab5.model.SanPham;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.math.BigDecimal;
import java.util.List;

public class SanPhamPanel extends JPanel {
    private final SanPhamBUS sanPhamBUS = new SanPhamBUS();

    private JTextField txtMaSp, txtTenSp, txtDonGia, txtSoLuong, txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public SanPhamPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        // --- Panel Form phía trên ---
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thong tin san pham"));

        panelForm.add(new JLabel("Ma SP:"));
        txtMaSp = new JTextField();
        txtMaSp.setEditable(false);
        panelForm.add(txtMaSp);

        panelForm.add(new JLabel("Ten SP:"));
        txtTenSp = new JTextField();
        panelForm.add(txtTenSp);

        panelForm.add(new JLabel("Don gia:"));
        txtDonGia = new JTextField();
        panelForm.add(txtDonGia);

        panelForm.add(new JLabel("So luong:"));
        txtSoLuong = new JTextField();
        panelForm.add(txtSoLuong);

        panelForm.add(new JLabel("Tim kiem ten:"));
        txtTimKiem = new JTextField();
        panelForm.add(txtTimKiem);

        add(panelForm, BorderLayout.NORTH);

        // --- Bảng dữ liệu ở giữa ---
        tableModel = new DefaultTableModel(new String[]{"Ma SP", "Ten SP", "Don gia", "So luong"}, 0);
        table = new JTable(tableModel);

        // Cấu hình Renderer tô màu đỏ nhạt cho dòng có Số lượng < 5 (Bài 7)
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Lấy số lượng an toàn từ cột index 3
                Object val = table.getValueAt(row, 3);
                int soLuong = 0;
                if (val != null) {
                    try {
                        soLuong = Integer.parseInt(val.toString());
                    } catch (NumberFormatException ignored) {}
                }

                if (!isSelected) {
                    if (soLuong < 5) {
                        c.setBackground(new Color(255, 200, 200)); // Màu đỏ nhạt cảnh báo sắp hết hàng
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                } else {
                    c.setBackground(table.getSelectionBackground());
                }
                return c;
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> onTableSelectRow());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Panel Nút chức năng phía dưới ---
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnThem = new JButton("Them");
        btnSua = new JButton("Sua");
        btnXoa = new JButton("Xoa");
        btnLamMoi = new JButton("Lam moi");
        btnTimKiem = new JButton("Tim kiem");

        panelButtons.add(btnThem);
        panelButtons.add(btnSua);
        panelButtons.add(btnXoa);
        panelButtons.add(btnLamMoi);
        panelButtons.add(btnTimKiem);

        add(panelButtons, BorderLayout.SOUTH);

        // Event Listeners
        btnThem.addActionListener(e -> btnSaveAction(true));
        btnSua.addActionListener(e -> btnSaveAction(false));
        btnXoa.addActionListener(e -> btnXoaAction());
        btnLamMoi.addActionListener(e -> clearForm());
        btnTimKiem.addActionListener(e -> btnTimKiemAction());
    }

    private void loadDataToTable() {
        try {
            List<SanPham> list = sanPhamBUS.findAll();
            renderTable(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi tai du lieu: " + e.getMessage());
        }
    }

    private void renderTable(List<SanPham> list) {
        tableModel.setRowCount(0);
        for (SanPham sp : list) {
            tableModel.addRow(new Object[]{sp.getMaSp(), sp.getTenSp(), sp.getDonGia(), sp.getSoLuong()});
        }
    }

    private void onTableSelectRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaSp.setText(tableModel.getValueAt(row, 0).toString());
            txtTenSp.setText(tableModel.getValueAt(row, 1).toString());
            txtDonGia.setText(tableModel.getValueAt(row, 2).toString());
            txtSoLuong.setText(tableModel.getValueAt(row, 3).toString());
        }
    }

    private void btnSaveAction(boolean isInsert) {
        try {
            SanPham sp = new SanPham();
            sp.setMaSp(txtMaSp.getText().isEmpty() ? 0 : Integer.parseInt(txtMaSp.getText()));
            sp.setTenSp(txtTenSp.getText());
            sp.setDonGia(new BigDecimal(txtDonGia.getText().trim()));
            sp.setSoLuong(Integer.parseInt(txtSoLuong.getText().trim()));

            if (isInsert) sp.setMaSp(0);

            if (sanPhamBUS.save(sp)) {
                JOptionPane.showMessageDialog(this, "Luu thanh cong!");
                clearForm();
                loadDataToTable();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Don gia va So luong phai la so hop le!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi: " + e.getMessage());
        }
    }

    private void btnXoaAction() {
        if (txtMaSp.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long chon san pham de xoa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maSp = Integer.parseInt(txtMaSp.getText());
                if (sanPhamBUS.delete(maSp)) {
                    JOptionPane.showMessageDialog(this, "Xoa thanh cong!");
                    clearForm();
                    loadDataToTable();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Loi xoa: " + e.getMessage());
            }
        }
    }

    private void btnTimKiemAction() {
        String keyword = txtTimKiem.getText().trim();
        try {
            List<SanPham> list = sanPhamBUS.searchByName(keyword);
            renderTable(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi tim kiem: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtMaSp.setText("");
        txtTenSp.setText("");
        txtDonGia.setText("");
        txtSoLuong.setText("");
        txtTimKiem.setText("");
        table.clearSelection();
        loadDataToTable();
    }
}