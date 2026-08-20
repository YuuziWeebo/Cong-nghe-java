package vn.edu.eaut.lab5.ui;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        setTitle("Quan ly ban hang MiniShop");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo JTabbedPane chứa 4 tab chính theo yêu cầu
        JTabbedPane tabbedPane = new JTabbedPane();

        // Bài 2: Tab Sản phẩm
        tabbedPane.addTab("San Pham", new SanPhamPanel());

        // Bài 3: Tab Khách hàng
        tabbedPane.addTab("Khach Hang", new KhachHangPanel());

        // Bài 4: Tab Hóa đơn
        tabbedPane.addTab("Hoa Don", new HoaDonPanel());

        // Bài 5: Tab Thống kê (tạm thời để JPanel trống cho bài 5)
        tabbedPane.addTab("Thong Ke", new ThongKePanel());

        add(tabbedPane);
    }
}