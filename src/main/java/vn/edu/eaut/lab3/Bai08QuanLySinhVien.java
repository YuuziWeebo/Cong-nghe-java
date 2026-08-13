package vn.edu.eaut.lab3;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Bai08QuanLySinhVien extends JFrame {

    static class SinhVien {
        private String maSV;
        private String hoTen;
        private double diem;

        public SinhVien() {
        }

        public SinhVien(String maSV, String hoTen, double diem) {
            this.maSV = maSV;
            this.hoTen = hoTen;
            this.diem = diem;
        }

        public String getMaSV() { return maSV; }
        public void setMaSV(String maSV) { this.maSV = maSV; }
        public String getHoTen() { return hoTen; }
        public void setHoTen(String hoTen) { this.hoTen = hoTen; }
        public double getDiem() { return diem; }
        public void setDiem(double diem) { this.diem = diem; }

        public String getXepLoai() {
            if (diem >= 8.5) return "Xuất sắc";
            if (diem >= 7.0) return "Khá";
            if (diem >= 5.0) return "Trung bình";
            return "Yếu";
        }
    }

    static class SinhVienManager {
        private List<SinhVien> listSV;

        public SinhVienManager() {
            listSV = new ArrayList<>();
        }

        public List<SinhVien> getAll() {
            return listSV;
        }

        public boolean add(SinhVien sv) {
            if (findByMa(sv.getMaSV()) != null) return false;
            listSV.add(sv);
            return true;
        }

        public boolean update(SinhVien sv) {
            SinhVien exist = findByMa(sv.getMaSV());
            if (exist == null) return false;
            exist.setHoTen(sv.getHoTen());
            exist.setDiem(sv.getDiem());
            return true;
        }

        public boolean delete(String maSV) {
            SinhVien exist = findByMa(maSV);
            if (exist == null) return false;
            listSV.remove(exist);
            return true;
        }

        public SinhVien findByMa(String maSV) {
            for (SinhVien sv : listSV) {
                if (sv.getMaSV().equalsIgnoreCase(maSV)) return sv;
            }
            return null;
        }
    }

    private JTextField txtMaSV;
    private JTextField txtHoTen;
    private JTextField txtDiem;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    private JTable tblSinhVien;
    private DefaultTableModel tableModel;

    private SinhVienManager manager;

    public Bai08QuanLySinhVien() {
        manager = new SinhVienManager();

        setTitle("Quản Lý Sinh Viên");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitle = new JLabel("QUẢN LÝ SINH VIÊN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(new Color(41, 128, 185));
        add(lblTitle, BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(BorderFactory.createTitledBorder("Thông tin sinh viên"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(new JLabel("Mã SV:"), gbc);
        txtMaSV = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panelForm.add(txtMaSV, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panelForm.add(new JLabel("Họ tên:"), gbc);
        txtHoTen = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panelForm.add(txtHoTen, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panelForm.add(new JLabel("Điểm:"), gbc);
        txtDiem = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        panelForm.add(txtDiem, gbc);

        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAdd = new JButton("Thêm");
        btnUpdate = new JButton("Sửa");
        btnDelete = new JButton("Xóa");
        btnClear = new JButton("Nhập mới");

        panelButtons.add(btnAdd);
        panelButtons.add(btnUpdate);
        panelButtons.add(btnDelete);
        panelButtons.add(btnClear);

        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        panelForm.add(panelButtons, gbc);

        add(panelForm, BorderLayout.WEST);

        String[] headers = {"Mã SV", "Họ Tên", "Điểm", "Xếp Loại"};
        tableModel = new DefaultTableModel(headers, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblSinhVien = new JTable(tableModel);
        tblSinhVien.setRowHeight(25);
        JScrollPane scrollTable = new JScrollPane(tblSinhVien);
        scrollTable.setBorder(BorderFactory.createTitledBorder("Danh sách sinh viên"));
        add(scrollTable, BorderLayout.CENTER);

        loadTableData();

        tblSinhVien.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tblSinhVien.getSelectedRow();
                if (row >= 0) {
                    txtMaSV.setText(tableModel.getValueAt(row, 0).toString());
                    txtHoTen.setText(tableModel.getValueAt(row, 1).toString());
                    txtDiem.setText(tableModel.getValueAt(row, 2).toString());
                    txtMaSV.setEditable(false);
                }
            }
        });

        btnAdd.addActionListener(e -> handleAdd());
        btnUpdate.addActionListener(e -> handleUpdate());
        btnDelete.addActionListener(e -> handleDelete());
        btnClear.addActionListener(e -> clearForm());
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        for (SinhVien sv : manager.getAll()) {
            tableModel.addRow(new Object[]{
                    sv.getMaSV(),
                    sv.getHoTen(),
                    sv.getDiem(),
                    sv.getXepLoai()
            });
        }
    }

    private void handleAdd() {
        if (!validateInput()) return;

        String maSV = txtMaSV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        double diem = Double.parseDouble(txtDiem.getText().trim());

        SinhVien sv = new SinhVien(maSV, hoTen, diem);

        if (manager.add(sv)) {
            JOptionPane.showMessageDialog(this, "Thêm sinh viên thành công!");
            loadTableData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Mã sinh viên đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleUpdate() {
        if (txtMaSV.isEditable()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sinh viên trong bảng để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!validateInput()) return;

        String maSV = txtMaSV.getText().trim();
        String hoTen = txtHoTen.getText().trim();
        double diem = Double.parseDouble(txtDiem.getText().trim());

        SinhVien sv = new SinhVien(maSV, hoTen, diem);

        if (manager.update(sv)) {
            JOptionPane.showMessageDialog(this, "Cập nhật sinh viên thành công!");
            loadTableData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy sinh viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleDelete() {
        int row = tblSinhVien.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 sinh viên để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maSV = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc muốn xóa sinh viên " + maSV + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (manager.delete(maSV)) {
                JOptionPane.showMessageDialog(this, "Xóa sinh viên thành công!");
                loadTableData();
                clearForm();
            }
        }
    }

    private void clearForm() {
        txtMaSV.setText("");
        txtHoTen.setText("");
        txtDiem.setText("");
        txtMaSV.setEditable(true);
        tblSinhVien.clearSelection();
    }

    private boolean validateInput() {
        if (txtMaSV.getText().trim().isEmpty() ||
                txtHoTen.getText().trim().isEmpty() ||
                txtDiem.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            double diem = Double.parseDouble(txtDiem.getText().trim());
            if (diem < 0 || diem > 10) {
                JOptionPane.showMessageDialog(this, "Điểm phải nằm trong khoảng từ 0 đến 10!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Điểm phải là số hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> {
            new Bai08QuanLySinhVien().setVisible(true);
        });
    }
}