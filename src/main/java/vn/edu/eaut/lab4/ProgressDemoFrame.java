package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProgressDemoFrame extends JFrame {
    private JButton btnStart;
    private JProgressBar progressBar;
    private JLabel lblStatus;

    public ProgressDemoFrame() {
        // Cấu hình cửa sổ chính
        setTitle("Mô phỏng tiến trình tải dữ liệu");
        setSize(420, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // 1. Panel ở giữa chứa JProgressBar và JLabel trạng thái
        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 5, 20));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true); // Hiển thị phần trăm % trên thanh
        centerPanel.add(progressBar);

        lblStatus = new JLabel("Trạng thái: Sẵn sàng tải...", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(lblStatus);

        add(centerPanel, BorderLayout.CENTER);

        // 2. Panel phía dưới chứa nút bấm "Tải dữ liệu"
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnStart = new JButton("Tải dữ liệu");
        btnStart.setPreferredSize(new Dimension(130, 35));
        bottomPanel.add(btnStart);

        add(bottomPanel, BorderLayout.SOUTH);

        // Đăng ký sự kiện click cho nút bấm
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startDownloadSimulation();
            }
        });
    }

    private void startDownloadSimulation() {
        // Vô hiệu hóa nút trong quá trình tải và đặt lại tiến độ
        btnStart.setEnabled(false);
        progressBar.setValue(0);
        lblStatus.setText("Trạng thái: Đang tải dữ liệu (0%)...");

        // Khởi chạy SwingWorker
        DownloadWorker worker = new DownloadWorker();
        worker.execute();
    }

    /**
     * SwingWorker<Void, Integer>
     * - Void: Không có kết quả trả về cuối cùng
     * - Integer: Dữ liệu phần trăm tiến độ (%) được đẩy về process()
     */
    private class DownloadWorker extends SwingWorker<Void, Integer> {

        @Override
        protected Void doInBackground() throws Exception {
            // Tổng thời gian là 10 giây (10,000 ms)
            // Lặp 100 lần, mỗi lần tạm dừng 100 ms (100 * 100ms = 10,000ms = 10s)
            for (int i = 1; i <= 100; i++) {
                Thread.sleep(100); // Tạm dừng 100ms
                publish(i);        // Đẩy giá trị % hiện tại ra UI Thread
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Lấy % tiến độ mới nhất
            int currentProgress = chunks.get(chunks.size() - 1);
            progressBar.setValue(currentProgress);
            lblStatus.setText("Trạng thái: Đang tải dữ liệu (" + currentProgress + "%)...");
        }

        @Override
        protected void done() {
            try {
                // Khi tải hoàn tất 100%
                lblStatus.setText("Trạng thái: Tải dữ liệu hoàn tất!");
                progressBar.setValue(100);
                JOptionPane.showMessageDialog(ProgressDemoFrame.this,
                        "Tải dữ liệu thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                lblStatus.setText("Trạng thái: Lỗi khi tải dữ liệu!");
                e.printStackTrace();
            } finally {
                btnStart.setEnabled(true); // Bật lại nút bấm
            }
        }
    }

    public static void main(String[] args) {
        // Khởi chạy ứng dụng trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProgressDemoFrame().setVisible(true);
            }
        });
    }
}