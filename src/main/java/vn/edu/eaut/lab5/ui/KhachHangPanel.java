package vn.edu.eaut.lab5.ui;

import vn.edu.eaut.lab5.bus.KhachHangBUS;
import vn.edu.eaut.lab5.model.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.util.List;

// Filter chặn gõ ký tự lạ / quá 10 số
class PhoneDocumentFilter extends DocumentFilter {
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) return;
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + string + currentText.substring(offset);
        if (newText.matches("\\d{0,10}")) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
        String newText = currentText.substring(0, offset) + (text == null ? "" : text) + currentText.substring(offset + length);
        if (newText.matches("\\d{0,10}")) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}

public class KhachHangPanel extends JPanel {
    private final KhachHangBUS khachHangBUS = new KhachHangBUS();

    private JTextField txtMaKh, txtTenKh, txtSdt, txtDiaChi, txtTimKiem;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public KhachHangPanel() {
        setLayout(new BorderLayout(10, 10));
        initComponents();
        loadDataToTable();
    }

    private void initComponents() {
        // --- Form Nhập liệu ---
        JPanel panelForm = new JPanel(new GridLayout(5, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Thong tin khach hang"));

        panelForm.add(new JLabel("Ma KH:"));
        txtMaKh = new JTextField();
        txtMaKh.setEditable(false);
        panelForm.add(txtMaKh);

        panelForm.add(new JLabel("Ten KH:"));
        txtTenKh = new JTextField();
        panelForm.add(txtTenKh);

        panelForm.add(new JLabel("So dien thoai (toi da 10 so):"));
        txtSdt = new JTextField();
        // Áp dụng DocumentFilter cho ô SDT
        ((AbstractDocument) txtSdt.getDocument()).setDocumentFilter(new PhoneDocumentFilter());
        panelForm.add(txtSdt);

        panelForm.add(new JLabel("Dia chi:"));
        txtDiaChi = new JTextField();
        panelForm.add(txtDiaChi);

        panelForm.add(new JLabel("Tim kiem ten:"));
        txtTimKiem = new JTextField();
        panelForm.add(txtTimKiem);

        add(panelForm, BorderLayout.NORTH);

        // --- Bảng Dữ Liệu ---
        tableModel = new DefaultTableModel(new String[]{"Ma KH", "Ten KH", "SDT", "Dia chi"}, 0);
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(e -> onTableSelectRow());
        add(new JScrollPane(table), BorderLayout.CENTER);

        // --- Nút Chức Năng ---
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
            List<KhachHang> list = khachHangBUS.findAll();
            renderTable(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi tai du lieu: " + e.getMessage());
        }
    }

    private void renderTable(List<KhachHang> list) {
        tableModel.setRowCount(0);
        for (KhachHang kh : list) {
            tableModel.addRow(new Object[]{kh.getMaKh(), kh.getTenKh(), kh.getSdt(), kh.getDiaChi()});
        }
    }

    private void onTableSelectRow() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaKh.setText(tableModel.getValueAt(row, 0).toString());
            txtTenKh.setText(tableModel.getValueAt(row, 1).toString());
            txtSdt.setText(tableModel.getValueAt(row, 2) != null ? tableModel.getValueAt(row, 2).toString() : "");
            txtDiaChi.setText(tableModel.getValueAt(row, 3) != null ? tableModel.getValueAt(row, 3).toString() : "");
        }
    }

    private void btnSaveAction(boolean isInsert) {
        try {
            KhachHang kh = new KhachHang();
            kh.setMaKh(txtMaKh.getText().isEmpty() ? 0 : Integer.parseInt(txtMaKh.getText()));
            kh.setTenKh(txtTenKh.getText());
            kh.setSdt(txtSdt.getText().trim());
            kh.setDiaChi(txtDiaChi.getText());

            if (isInsert) kh.setMaKh(0);

            if (khachHangBUS.save(kh)) {
                JOptionPane.showMessageDialog(this, "Luu thanh cong!");
                clearForm();
                loadDataToTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi: " + e.getMessage());
        }
    }

    private void btnXoaAction() {
        if (txtMaKh.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long chon khach hang de xoa!");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Ban co chac muon xoa?", "Xac nhan", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int maKh = Integer.parseInt(txtMaKh.getText());
                if (khachHangBUS.delete(maKh)) {
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
            List<KhachHang> list = khachHangBUS.searchByName(keyword);
            renderTable(list);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Loi tim kiem: " + e.getMessage());
        }
    }

    private void clearForm() {
        txtMaKh.setText("");
        txtTenKh.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        txtTimKiem.setText("");
        table.clearSelection();
        loadDataToTable();
    }
}