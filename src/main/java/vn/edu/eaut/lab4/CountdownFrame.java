package vn.edu.eaut.lab4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CountdownFrame extends JFrame {
    private JTextField txtSeconds;
    private JButton btnStart;
    private JLabel lblTimeLeft;
    private CountdownWorker worker;

    public CountdownFrame() {
        // Cấu hình giao diện chính
        setTitle("Đồng hồ đếm ngược");
        setSize(380, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // 1. Panel nhập liệu phía trên
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.add(new JLabel("Số giây:"));
        txtSeconds = new JTextField(8);
        topPanel.add(txtSeconds);

        btnStart = new JButton("Bắt đầu");
        topPanel.add(btnStart);
        add(topPanel, BorderLayout.NORTH);

        // 2. Label hiển thị thời gian ở trung tâm
        lblTimeLeft = new JLabel("Thời gian còn lại: --", SwingConstants.CENTER);
        lblTimeLeft.setFont(new Font("Arial", Font.BOLD, 22));
        add(lblTimeLeft, BorderLayout.CENTER);

        // Bắt sự kiện khi nhấn nút "Bắt đầu"
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCountdown();
            }
        });
    }

    private void startCountdown() {
        String input = txtSeconds.getText().trim();
        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số giây!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int seconds = Integer.parseInt(input);
            if (seconds <= 0) {
                JOptionPane.showMessageDialog(this, "Số giây phải lớn hơn 0!", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Vô hiệu hóa nút Bắt đầu trong lúc đếm ngược
            btnStart.setEnabled(false);

            // Khởi chạy SwingWorker
            worker = new CountdownWorker(seconds);
            worker.execute();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Số giây phải là một số nguyên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * SwingWorker<Void, Integer>
     * - Void: Không trả về kết quả cuối cùng
     * - Integer: Dùng để đẩy số giây còn lại (publish) về giao diện UI
     */
    private class CountdownWorker extends SwingWorker<Void, Integer> {
        private final int totalSeconds;

        public CountdownWorker(int totalSeconds) {
            this.totalSeconds = totalSeconds;
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int i = totalSeconds; i >= 0; i--) {
                // Gửi số giây còn lại sang hàm process() để cập nhật UI
                publish(i);

                // Tạm dừng luồng ngầm 1 giây (1000ms)
                Thread.sleep(1000);
            }
            return null;
        }

        @Override
        protected void process(List<Integer> chunks) {
            // Lấy giá trị thời gian mới nhất được gửi về
            int currentSecond = chunks.get(chunks.size() - 1);
            lblTimeLeft.setText("Thời gian còn lại: " + currentSecond + "s");
        }

        @Override
        protected void done() {
            // Khi đếm ngược hoàn tất
            lblTimeLeft.setText("Hết giờ!");
            btnStart.setEnabled(true); // Bật lại nút Bắt đầu
            JOptionPane.showMessageDialog(CountdownFrame.this, "Đã hết thời gian!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        // Chạy ứng dụng trên Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CountdownFrame().setVisible(true);
            }
        });
    }
}